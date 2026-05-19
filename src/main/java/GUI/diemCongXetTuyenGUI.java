package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import BUS.diemCongBUS;
import BUS.phanQuyenBUS;
import BUS.nganhBUS;
import Entity.diemCongETT;
import Entity.nganhETT;
import EXCEL.ExcelHelper;
import FUNC_GUI.excelDiemCong;
import FUNC_GUI.insertDiemCong;
import FUNC_GUI.updateDiemCong;
import FUNC_GUI.deleteDiemCong;
import FUNC_GUI.detailDiemCong;
import CONFIG.RoundedButton;

public class diemCongXetTuyenGUI extends BaseTableGUI {
    diemCongBUS busDiemCong = new diemCongBUS();
    nganhBUS busNganh = new nganhBUS();
    private boolean xacNhan = false;
    
    private JComboBox<String> cbxLocNganh, cbxLocToHop, cbxLocPhuongThuc;
    private JButton btnLoc; 
    private JDialog filterDialog; 
    
    private Map<String, String> mapTenNganh = new HashMap<>();

    public diemCongXetTuyenGUI() {
        super();
        setTableNameForTitle("Quản lý Điểm cộng");
        
        loadNganhMap();
        headerTable();
        loadComboBox();
        initFilterDialog();

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnTimKiem.addActionListener(e -> thucHienLocVaTimKiem());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnChiTiet.addActionListener(e -> hienThiDialogChiTiet());
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) hienThiDialogChiTiet();
                }
            }
        });

        loadDataToTable();
        phanQuyenGiaoDien();
        updateFilterComboBoxes();
        setupColumnWidths();
    }

    private void setupColumnWidths() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(220);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(130);
        columnModel.getColumn(5).setPreferredWidth(130);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(150);
    }

    private void loadNganhMap() {
        ArrayList<nganhETT> dsNganh = busNganh.layDanhSach();
        mapTenNganh.clear();
        for (nganhETT n : dsNganh) {
            mapTenNganh.put(n.getManganh(), n.getTennganh());
        }
    }

    private String getTenNganh(String maNganh) {
        return mapTenNganh.getOrDefault(maNganh, maNganh);
    }

    private void initFilterDialog() {
        // 👉 Đổi sang dùng JButton thường thay vì RoundedButton để đồng bộ tuyệt đối với btnTimKiem
        btnLoc = new JButton("LỌC");
        btnLoc.setBackground(Color.WHITE);
        btnLoc.setForeground(Color.BLACK);
        
        // Lấy font chuẩn từ hệ thống (giống y hệt nút Tìm Kiếm)
        Font systemFont = btnTimKiem.getFont();
        if (systemFont != null) {
            btnLoc.setFont(systemFont.deriveFont(Font.PLAIN));
        } else {
            btnLoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        
        btnLoc.setPreferredSize(new Dimension(85, 32));
        btnLoc.setFocusPainted(false);
        btnLoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Container searchParent = btnTimKiem.getParent();
        if (searchParent != null) {
            searchParent.add(btnLoc, 0);
            searchParent.revalidate();
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        filterDialog = new JDialog(topFrame, false);
        filterDialog.setUndecorated(true);
        filterDialog.setLayout(new BorderLayout());
        filterDialog.setBackground(Color.WHITE);

        JPanel pnlInnerFilter = new JPanel();
        pnlInnerFilter.setLayout(new BoxLayout(pnlInnerFilter, BoxLayout.Y_AXIS));
        pnlInnerFilter.setBackground(Color.WHITE);
        pnlInnerFilter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        cbxLocNganh = new JComboBox<>(new String[]{"Tất cả"});
        cbxLocToHop = new JComboBox<>(new String[]{"Tất cả"});
        cbxLocPhuongThuc = new JComboBox<>(new String[]{"Tất cả"});

        Dimension cbSize = new Dimension(280, 35);
        cbxLocNganh.setPreferredSize(cbSize);
        cbxLocNganh.setMaximumSize(cbSize);
        cbxLocToHop.setPreferredSize(cbSize);
        cbxLocToHop.setMaximumSize(cbSize);
        cbxLocPhuongThuc.setPreferredSize(cbSize);
        cbxLocPhuongThuc.setMaximumSize(cbSize);

        pnlInnerFilter.add(createFilterLabel("Lọc theo Ngành:"));
        pnlInnerFilter.add(cbxLocNganh);
        pnlInnerFilter.add(Box.createVerticalStrut(12));
        pnlInnerFilter.add(createFilterLabel("Lọc theo Tổ hợp:"));
        pnlInnerFilter.add(cbxLocToHop);
        pnlInnerFilter.add(Box.createVerticalStrut(12));
        pnlInnerFilter.add(createFilterLabel("Lọc theo Phương thức:"));
        pnlInnerFilter.add(cbxLocPhuongThuc);
        
        JButton btnCloseFilter = new JButton("Đóng");
        btnCloseFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCloseFilter.addActionListener(e -> filterDialog.setVisible(false));
        pnlInnerFilter.add(Box.createVerticalStrut(15));
        pnlInnerFilter.add(btnCloseFilter);

        filterDialog.add(pnlInnerFilter);
        filterDialog.pack();

        btnLoc.addActionListener(e -> {
            if (filterDialog.isVisible()) {
                filterDialog.setVisible(false);
            } else {
                Point p = btnLoc.getLocationOnScreen();
                filterDialog.setLocation(p.x, p.y + btnLoc.getHeight());
                filterDialog.setVisible(true);
            }
        });

        filterDialog.addWindowFocusListener(new WindowFocusListener() {
            @Override public void windowGainedFocus(WindowEvent e) {}
            @Override public void windowLostFocus(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (filterDialog != null && !filterDialog.isFocused()) filterDialog.setVisible(false);
                });
            }
        });

        cbxLocNganh.addActionListener(e -> thucHienLocVaTimKiem());
        cbxLocToHop.addActionListener(e -> thucHienLocVaTimKiem());
        cbxLocPhuongThuc.addActionListener(e -> thucHienLocVaTimKiem());
    }

    private JLabel createFilterLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private void updateFilterComboBoxes() {
        if (diemCongBUS.ds == null) busDiemCong.layDanhSach();
        Set<String> nganhs = new HashSet<>();
        Set<String> toHops = new HashSet<>();
        Set<String> phuongThucs = new HashSet<>();
        for (diemCongETT dc : diemCongBUS.ds) {
            if (dc.getMaNganh() != null && !dc.getMaNganh().isEmpty()) nganhs.add(getTenNganh(dc.getMaNganh()));
            if (dc.getMaToHop() != null && !dc.getMaToHop().isEmpty()) toHops.add(dc.getMaToHop());
            if (dc.getPhuongThuc() != null && !dc.getPhuongThuc().isEmpty()) phuongThucs.add(dc.getPhuongThuc());
        }
        updateCombo(cbxLocNganh, nganhs);
        updateCombo(cbxLocToHop, toHops);
        updateCombo(cbxLocPhuongThuc, phuongThucs);
    }

    private void updateCombo(JComboBox<String> cbx, Set<String> items) {
        String current = (String) cbx.getSelectedItem();
        cbx.removeAllItems();
        cbx.addItem("Tất cả");
        ArrayList<String> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems);
        for (String item : sortedItems) cbx.addItem(item);
        if (current != null) cbx.setSelectedItem(current);
        else cbx.setSelectedIndex(0);
    }

    private void phanQuyenGiaoDien() {
        String bangHienTai = "xt_diemcongxettuyen";
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) return;
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); btnXoa.setEnabled(false); btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                SwingUtilities.invokeLater(() -> {
                    boolean isSelected = table.getSelectedRow() != -1;
                    btnSua.setEnabled(isSelected && phanQuyenBUS.checkQuyenSua(bangHienTai));
                    btnXoa.setEnabled(isSelected && phanQuyenBUS.checkQuyenXoa(bangHienTai));
                    btnChiTiet.setEnabled(isSelected && phanQuyenBUS.checkQuyenXem(bangHienTai)); 
                });
            }
        });
    }

    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID"); header.add("CCCD"); header.add("Tên ngành"); header.add("Mã tổ hợp");
        header.add("Phương thức"); header.add("Điểm chứng chỉ"); header.add("Điểm giải");
        header.add("Điểm tổng"); header.add("Ghi chú");
        tableModel.setColumnIdentifiers(header);
    }

    public void loadComboBox() {
        cbxTimKiem.removeAllItems();
        cbxTimKiem.addItem("CCCD"); cbxTimKiem.addItem("Tên ngành");
        cbxTimKiem.addItem("Mã tổ hợp"); cbxTimKiem.addItem("Phương thức");
    }

    public void loadDataToTable() {
        if (busDiemCong.ds == null) busDiemCong.layDanhSach();
        ArrayList<Vector> dataList = new ArrayList<>();
        for (diemCongETT dc : busDiemCong.ds) dataList.add(convertEntityToVector(dc));
        setTableData(dataList);
    }

    private Vector convertEntityToVector(diemCongETT dc) {
        Vector row = new Vector();
        row.add(dc.getIdDiemCong()); row.add(dc.getTsCccd());
        row.add(getTenNganh(dc.getMaNganh())); row.add(dc.getMaToHop());
        row.add(dc.getPhuongThuc()); row.add(dc.getDiemCC());
        row.add(dc.getDiemUtxt()); row.add(dc.getDiemTong());
        row.add(dc.getGhiChu());
        return row;
    }

    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertDiemCong dialog = new insertDiemCong(topFrame, true);
        dialog.setVisible(true);
        if (dialog.isXacNhan()) {
            diemCongETT dc = dialog.getDiemCong();
            fullDataList.add(convertEntityToVector(dc));
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            currentPage = totalPages;
            renderCurrentPage();
            updateFilterComboBoxes();
        }
    }

    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            diemCongETT dc = busDiemCong.findById(id);
            if (dc == null) return;
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateDiemCong dialog = new updateDiemCong(topFrame, true, dc);
            dialog.setVisible(true);
            if (dialog.isXacNhan()) {
                diemCongETT updated = dialog.getDiemCong();
                fullDataList.set(absoluteIndex, convertEntityToVector(updated));
                renderCurrentPage();
                updateFilterComboBoxes();
            }
        }
    }

    private void hienThiDialogXoa() {
        int row = table.getSelectedRow();
        if (row != -1) {
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            deleteDiemCong dialog = new deleteDiemCong(topFrame, true);
            dialog.setVisible(true);
            if (dialog.getXacNhanXoa()) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                diemCongETT dcCanXoa = busDiemCong.findById(id);
                if (dcCanXoa != null && busDiemCong.xoaDiemCong(dcCanXoa)) {
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                    fullDataList.remove(absoluteIndex);
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
                    renderCurrentPage();
                    updateFilterComboBoxes();
                }
            }
        }
    }

    private void hienThiDialogChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        diemCongETT dc = busDiemCong.findById(id);
        if (dc != null) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            detailDiemCong dialog = new detailDiemCong(topFrame, true, dc);
            dialog.setVisible(true);
        }
    }

    public void hienThiExcel() {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelDiemCong dialog = new excelDiemCong(topFrame, true);
        dialog.setVisible(true);
        if (dialog.getXacNhanImport()) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String thongBao = busDiemCong.nhapDuLieuTuExcel(fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, thongBao);
                diemCongBUS.ds = null;
                loadDataToTable();
                updateFilterComboBoxes();
            }
        } else if (dialog.getXacNhanExport()) {
            ExcelHelper.xuatDanhSachDiemCongRaExcel(busDiemCong.layDanhSach(), this, "DanhSachDiemCong");
        }
    }

    public void thucHienLocVaTimKiem() {
        String tim = txtTimKiem.getText().trim().toLowerCase();
        int searchIndex = cbxTimKiem.getSelectedIndex();
        String locNganh = (String) cbxLocNganh.getSelectedItem();
        String locToHop = (String) cbxLocToHop.getSelectedItem();
        String locPT = (String) cbxLocPhuongThuc.getSelectedItem();
        if (diemCongBUS.ds == null) busDiemCong.layDanhSach();
        ArrayList<Vector> filteredData = new ArrayList<>();
        for (diemCongETT dc : busDiemCong.ds) {
            boolean matchLoc = true;
            String tenNganhOfDc = getTenNganh(dc.getMaNganh());
            if (locNganh != null && !locNganh.equals("Tất cả")) {
                if (tenNganhOfDc == null || !tenNganhOfDc.equals(locNganh)) matchLoc = false;
            }
            if (locToHop != null && !locToHop.equals("Tất cả")) {
                if (dc.getMaToHop() == null || !dc.getMaToHop().equals(locToHop)) matchLoc = false;
            }
            if (locPT != null && !locPT.equals("Tất cả")) {
                if (dc.getPhuongThuc() == null || !dc.getPhuongThuc().equals(locPT)) matchLoc = false;
            }
            if (matchLoc && !tim.isEmpty()) {
                boolean matchSearch = false;
                switch (searchIndex) {
                    case 0: 
                        if (dc.getTsCccd() != null && dc.getTsCccd().toLowerCase().contains(tim)) matchSearch = true; 
                        break;
                    case 1: 
                        if (tenNganhOfDc != null && tenNganhOfDc.toLowerCase().contains(tim)) matchSearch = true; 
                        break;
                    case 2: 
                        if (dc.getMaToHop() != null && dc.getMaToHop().toLowerCase().contains(tim)) matchSearch = true; 
                        break;
                    case 3: 
                        if (dc.getPhuongThuc() != null && dc.getPhuongThuc().toLowerCase().contains(tim)) matchSearch = true; 
                        break;
                }
                if (!matchSearch) matchLoc = false;
            }
            if (matchLoc) filteredData.add(convertEntityToVector(dc));
        }
        setTableData(filteredData);
    }

    public void thucHienRefresh() {
        txtTimKiem.setText("");
        cbxTimKiem.setSelectedIndex(0);
        cbxLocNganh.setSelectedIndex(0);
        cbxLocToHop.setSelectedIndex(0);
        cbxLocPhuongThuc.setSelectedIndex(0);
        diemCongBUS.ds = null;
        loadNganhMap();
        loadDataToTable();
        updateFilterComboBoxes();
    }
}
