package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import BUS.diemCongBUS;
import BUS.phanQuyenBUS;
import BUS.thiSinhXetTuyenBUS;
import EXCEL.ExcelHelper;
import Entity.diemCongETT;
import Entity.thiSinhXetTuyenETT;
import FUNC_GUI.excelDiemCong;
import FUNC_GUI.insertDiemCong;
import FUNC_GUI.updateDiemCong;
import FUNC_GUI.deleteDiemCong;
import FUNC_GUI.deleteNguyenVong;
import FUNC_GUI.detailDiemCong;
import FUNC_GUI.excelThiSinh;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LE MINH HUY
 */

public class diemCongXetTuyenGUI extends BaseTableGUI {
    diemCongBUS busDiemCong =new diemCongBUS();
    private boolean xacNhan = false;
    public diemCongXetTuyenGUI() {
        super();

        setTableNameForTitle("Quản lý Điểm cộng");
        headerTable();
        loadComboBox();
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
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
    }
     private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_diemcongxettuyen";
        
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
//        btnTinhToanKetQua.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        boolean isSelected = table.getSelectedRow() != -1;
                        
                        if (isSelected) {
                            btnSua.setEnabled(phanQuyenBUS.checkQuyenSua(bangHienTai));
                            btnXoa.setEnabled(phanQuyenBUS.checkQuyenXoa(bangHienTai));
                            btnChiTiet.setEnabled(phanQuyenBUS.checkQuyenXem(bangHienTai)); 
                        } else {
                            btnSua.setEnabled(false);
                            btnXoa.setEnabled(false);
                            btnChiTiet.setEnabled(false);
                        }
                    }
                });
                
            }
        });
    }
    // ================= HEADER =================
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("CCCD");
        header.add("Mã ngành");
        header.add("Mã tổ hợp");
        header.add("Phương thức");
        header.add("Điểm cộng");
        header.add("Điểm ưu tiên");
        header.add("Điểm tổng");
        header.add("Ghi chú");

        tableModel.setColumnIdentifiers(header);
    }
    public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("Mã ngành");
        cbxTimKiem.addItem("Mã tổ hợp");
        cbxTimKiem.addItem("Phương thức");
    }
    // ================= LOAD DATA =================
    public void loadDataToTable() {
        diemCongBUS bus = new diemCongBUS();
        if (bus.ds == null) bus.layDanhSach();

        java.util.List<Vector> dataList = new java.util.ArrayList<>();

        for (diemCongETT dc : bus.ds) {
            Vector row = new Vector();
            row.add(dc.getIdDiemCong());
            row.add(dc.getTsCccd());
            row.add(dc.getMaNganh());
            row.add(dc.getMaToHop());
            row.add(dc.getPhuongThuc());
            row.add(dc.getDiemCC());
            row.add(dc.getDiemUtxt());
            row.add(dc.getDiemTong());
            row.add(dc.getGhiChu());

            dataList.add(row);
        }

        setTableData(dataList);
    }
    
    // ================= THÊM =================
    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertDiemCong dialog = new insertDiemCong(topFrame, true);
        dialog.setVisible(true);

        if (dialog.isXacNhan()) {
            diemCongETT dc = dialog.getDiemCong();

            // 🔥 Thêm vào danh sách tổng và render lại
            Vector row = new Vector();
            row.add(dc.getIdDiemCong());
            row.add(dc.getTsCccd());
            row.add(dc.getMaNganh());
            row.add(dc.getMaToHop());
            row.add(dc.getPhuongThuc());
            row.add(dc.getDiemCC());
            row.add(dc.getDiemUtxt());
            row.add(dc.getDiemTong());
            row.add(dc.getGhiChu());

            fullDataList.add(row);
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            currentPage = totalPages;
            renderCurrentPage();
        }
    }

    // ================= SỬA =================
    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            // Tìm bằng ID từ bảng
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            diemCongETT dc = busDiemCong.findById(id);

            if (dc == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu!");
                return;
            }

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            updateDiemCong dialog = new updateDiemCong(topFrame, true, dc);
            dialog.setVisible(true);

            if (dialog.isXacNhan()) {
                diemCongETT updated = dialog.getDiemCong();

                // Cập nhật lại fullDataList
                Vector rowData = new Vector();
                rowData.add(updated.getIdDiemCong());
                rowData.add(updated.getTsCccd());
                rowData.add(updated.getMaNganh());
                rowData.add(updated.getMaToHop());
                rowData.add(updated.getPhuongThuc());
                rowData.add(updated.getDiemCC());
                rowData.add(updated.getDiemUtxt());
                rowData.add(updated.getDiemTong());
                rowData.add(updated.getGhiChu());

                fullDataList.set(absoluteIndex, rowData);
                renderCurrentPage();
            }
        }
    }

    // ================= XÓA =================
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
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa điểm cộng thất bại");
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
    public boolean isXacNhan() {
        return xacNhan;
    }
     public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelDiemCong dialog = new excelDiemCong(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Gọi BUS xử lý và nhận thông báo kết quả
            String thongBao = busDiemCong.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            diemCongBUS.ds = null;
            loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<diemCongETT> fullDanhSach = busDiemCong.layDanhSach();
            ExcelHelper.xuatDanhSachDiemCongRaExcel(fullDanhSach, this, "DanhSachDiemCong");
        }
    }
    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            return;
        }

        if (diemCongBUS.ds == null) busDiemCong.layDanhSach();

        ArrayList<diemCongETT> result = new ArrayList<>();

        for (diemCongETT dc : diemCongBUS.ds) {
            switch (index) {
                case 0:
                    if (dc.getTsCccd().contains(tim)) result.add(dc);
                    break;
                case 1:
                    if (dc.getMaNganh().contains(tim)) result.add(dc);
                    break;
                case 2:
                    if (dc.getMaToHop().contains(tim)) result.add(dc);
                    break;
                case 3:
                    if (dc.getPhuongThuc().toLowerCase().contains(tim.toLowerCase())) result.add(dc);
                    break;
            }
        }

        ArrayList<Vector> data = new ArrayList<>();

        for (diemCongETT dc : result) {
            Vector row = new Vector();
            row.add(dc.getIdDiemCong());
            row.add(dc.getTsCccd());
            row.add(dc.getMaNganh());
            row.add(dc.getMaToHop());
            row.add(dc.getPhuongThuc());
            row.add(dc.getDiemCC());
            row.add(dc.getDiemUtxt());
            row.add(dc.getDiemTong());
            row.add(dc.getGhiChu());
            data.add(row);
        }

        setTableData(data);

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu");
        }
    }
    public void thucHienRefresh() {
        txtTimKiem.setText("");
        cbxTimKiem.setSelectedIndex(0);

        diemCongBUS.ds = null;
        loadDataToTable();
    }
}

