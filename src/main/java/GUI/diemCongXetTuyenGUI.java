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
    
    // Trình quản lý lọc nâng cao qua JDialog đã có sẵn 
    
    private Map<String, String> mapTenNganh = new HashMap<>();

    public diemCongXetTuyenGUI() {
        super();
        setTableNameForTitle("Quản lý Điểm cộng");
        
        loadNganhMap();
        headerTable();
        loadComboBox();

        btnLoc.setVisible(true);

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnTimKiem.addActionListener(e -> thucHienLocVaTimKiem());
        btnLoc.addActionListener(e -> thucHienTimKiemNangCao());
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

    // Đã gỡ bỏ toàn bộ code dựng bộ lọc thủ công tại đây để chuyển sang dùng timKiemNangCaoDiemCong chuẩn hóa

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
            }
        } else if (dialog.getXacNhanExport()) {
            ExcelHelper.xuatDanhSachDiemCongRaExcel(busDiemCong.layDanhSach(), this, "DanhSachDiemCong");
        }
    }

    public void thucHienLocVaTimKiem() {
        String tim = txtTimKiem.getText().trim().toLowerCase();
        int searchIndex = cbxTimKiem.getSelectedIndex();
        if (diemCongBUS.ds == null) busDiemCong.layDanhSach();
        ArrayList<Vector> filteredData = new ArrayList<>();
        for (diemCongETT dc : busDiemCong.ds) {
            boolean matchSearch = true;
            if (!tim.isEmpty()) {
                matchSearch = false;
                String tenNganhOfDc = getTenNganh(dc.getMaNganh());
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
            }
            if (matchSearch) filteredData.add(convertEntityToVector(dc));
        }
        setTableData(filteredData);
    }

    public void thucHienTimKiemNangCao() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        FUNC_GUI.timKiemNangCaoDiemCong dialog = new FUNC_GUI.timKiemNangCaoDiemCong(topFrame, true);
        dialog.setVisible(true);
        
        if (dialog.getXacNhan()) {
            ArrayList<Entity.diemCongETT> danhSachDaLoc = dialog.getDanhSachLoc();
            ArrayList<Vector> dsHienThi = new ArrayList<>();
            for (diemCongETT dc : danhSachDaLoc) {
                if (dc != null) {
                    dsHienThi.add(convertEntityToVector(dc));
                }
            }
            setTableData(dsHienThi);
            if (dsHienThi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp với bộ lọc!");
            }
        }
    }

    public void thucHienRefresh() {
        txtTimKiem.setText("");
        cbxTimKiem.setSelectedIndex(0);
        diemCongBUS.ds = null;
        loadNganhMap();
        loadDataToTable();
    }
}
