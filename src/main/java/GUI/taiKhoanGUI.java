/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.taiKhoanBUS;
import Entity.taiKhoanETT;
import FUNC_GUI.deleteTaiKhoan;
import FUNC_GUI.detailTaiKhoan;
import FUNC_GUI.insertTaiKhoan;
import FUNC_GUI.updateTaiKhoan;
import EXCEL.ExcelHelper;
import FUNC_GUI.excelTaiKhoan;
import BUS.phanQuyenBUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Dang Khoa
 */
public class taiKhoanGUI extends BaseTableForNguyenVongGUIonly {
    
    taiKhoanBUS bus = new taiKhoanBUS();

    public taiKhoanGUI() {
        super();
        setTableNameForTitle("Tài Khoản");
        headerTable();

        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnTinhToanKetQua.addActionListener(e -> thucHienKhoaTaiKhoan());
        btnExcel.addActionListener(e -> thucHienExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> hienThiChiTiet());

        // Đổi tên nút TÍNH TOÁN KQ → KHÓA TK
        btnTinhToanKetQua.setText("KHÓA TK");
        btnTinhToanKetQua.setIcon(loadAndScaleIcon("lock.png", 45));

        loadDataToTable();
        loadComboBox();

        // Cập nhật text nút KHÓA/MỞ KHÓA theo trạng thái dòng được chọn
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    btnTinhToanKetQua.setEnabled(true);
                    Object trangThai = tableModel.getValueAt(row, 4);
                    String tt = trangThai != null ? trangThai.toString() : "";
                    btnTinhToanKetQua.setText(tt.equalsIgnoreCase("Hoạt động") ? "KHÓA TK" : "MỞ KHÓA");
                } else {
                    btnTinhToanKetQua.setEnabled(false);
                    btnTinhToanKetQua.setText("KHÓA TK");
                }
            }
        });
        phanQuyenGiaoDien();
    }
    
    private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_taikhoan";

        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }

        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);

        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
        btnTinhToanKetQua.setEnabled(phanQuyenBUS.checkQuyenSua(bangHienTai)); 
        // vì nút này dùng để KHÓA / MỞ KHÓA

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        boolean isSelected = table.getSelectedRow() != -1;

                        if (isSelected) {
                            btnSua.setEnabled(phanQuyenBUS.checkQuyenSua(bangHienTai));
                            btnXoa.setEnabled(phanQuyenBUS.checkQuyenXoa(bangHienTai));
                            btnChiTiet.setEnabled(phanQuyenBUS.checkQuyenXem(bangHienTai));
                        } 
                        else {
                            btnSua.setEnabled(false);
                            btnXoa.setEnabled(false);
                            btnChiTiet.setEnabled(false);
                        }
                    }
                });

            }
        });
    }

    // ===== HEADER TABLE =====
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("Tên đăng nhập");
        header.add("Mật khẩu");
        header.add("Họ tên");
        header.add("Trạng thái");
        tableModel.setColumnIdentifiers(header);
    }

    // ===== LOAD COMBO BOX =====
    public void loadComboBox() {
        cbxTimKiem.removeAllItems();
        cbxTimKiem.addItem("Tên đăng nhập");
        cbxTimKiem.addItem("Họ tên");
        cbxTimKiem.addItem("Trạng thái");
    }

    // ===== LOAD DATA =====
    public void loadDataToTable() {
        if (bus.ds == null) {
            bus.layDanhSach();
        }
        
        List<Vector> dataList = new ArrayList<>();

        for (taiKhoanETT tk : bus.ds) {

            Vector row = new Vector();

            row.add(tk.getIdTaiKhoan());
            row.add(tk.getTenDangNhap());
            row.add("******");
            row.add(tk.getHoTen());
            row.add(tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa");

            dataList.add(row);
        }
        setTableData(dataList);
    }

    // ===== TÌM KIẾM =====
    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim().toLowerCase();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }

        List<Vector> ketQua = new ArrayList<>();
        int[] colMap = {1, 3, 4}; // tên đăng nhập, họ tên, trạng thái
        for (Vector row : fullDataList) {
            String giaTriCot = row.get(colMap[index]).toString().toLowerCase();
            if (giaTriCot.contains(tim)) {
                ketQua.add(row);
            }
        }

        setTableData(ketQua);
        if (ketQua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }

    // ===== THÊM =====
    private void hienThiDialogThemMoi() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertTaiKhoan dialog = new insertTaiKhoan(topFrame, true);
        dialog.setVisible(true);

        if (dialog.getXacNhan()) {
            taiKhoanETT tk = dialog.getTaiKhoanETT();

            Vector row = new Vector();
            row.add(tk.getIdTaiKhoan());
            row.add(tk.getTenDangNhap());
            row.add("******");
            row.add(tk.getHoTen());
            row.add(tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa");
            fullDataList.add(row);

            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            currentPage = totalPages;
            renderCurrentPage();
        }
    }


    // ===== SỬA =====
    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để sửa!");
            return;
        }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        taiKhoanETT tkCu = bus.findById(id);

        if (tkCu != null) {
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            updateTaiKhoan dialog = new updateTaiKhoan(topFrame, true, tkCu);
            dialog.setVisible(true);

            if (dialog.getXacNhan()) {
                taiKhoanETT tkMoi = dialog.getTaiKhoanETT();

                Vector rowData = new Vector();
                rowData.add(tkMoi.getIdTaiKhoan());
                rowData.add(tkMoi.getTenDangNhap());
                rowData.add("******");
                rowData.add(tkMoi.getHoTen());
                rowData.add(tkMoi.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa");

                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                fullDataList.set(absoluteIndex, rowData);
                renderCurrentPage();
            }
        }
    }

    // ===== XÓA =====
    private void hienThiDialogXoa() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!");
            return;
        }

        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

        // Lấy ID
        Vector selectedRowData = fullDataList.get(absoluteIndex);
        int idCanTim = (int) selectedRowData.get(0);

        if (idCanTim == 1) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản Admin!!!");
            return;
        }

        // Tìm object cần xóa
        taiKhoanETT tkCanXoa = null;
        for (taiKhoanETT item : bus.ds) {
            if (item.getIdTaiKhoan() == idCanTim) {
                tkCanXoa = item;
                break;
            }
        }

        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        deleteTaiKhoan dialog = new deleteTaiKhoan(topFrame, true);
        dialog.setVisible(true);

        if (dialog.getXacNhanXoa()) {
            if (tkCanXoa != null && bus.xoaTaiKhoan(tkCanXoa.getTenDangNhap())) {
                fullDataList.remove(absoluteIndex);

                totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                if (currentPage > totalPages && totalPages > 0) {
                    currentPage = totalPages;
                }

                renderCurrentPage();
            } 
            else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!");
            }
        }
    }
    // ===== KHÓA / MỞ KHÓA =====
    private void thucHienKhoaTaiKhoan() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String tenDangNhap = tableModel.getValueAt(row, 1).toString();
        String trangThai = tableModel.getValueAt(row, 4).toString();
        boolean dangHoatDong = trangThai.equalsIgnoreCase("Hoạt động");
        String hanhDong = dangHoatDong ? "KHÓA" : "MỞ KHÓA";

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn " + hanhDong + " tài khoản \"" + tenDangNhap + "\" không?",
                "Xác nhận " + hanhDong,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = dangHoatDong
                        ? bus.khoaTaiKhoan(tenDangNhap)
                        : bus.moKhoaTaiKhoan(tenDangNhap);

                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            hanhDong + " tài khoản thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Cập nhật lại RAM không cần reload DB
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                    Vector rowData = fullDataList.get(absoluteIndex);
                    rowData.set(4, dangHoatDong ? "Bị khóa" : "Hoạt động");
                    renderCurrentPage();
                } else {
                    JOptionPane.showMessageDialog(this,
                            hanhDong + " thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== EXCEL =====
    private void thucHienExcel(){

        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelTaiKhoan dialog = new excelTaiKhoan(topFrame, true);
        dialog.setVisible(true);

        // ================= IMPORT =================

        if(dialog.getXacNhanImport())
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");

            javax.swing.filechooser.FileNameExtensionFilter filter =
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Excel Files (*.xls, *.xlsx)", "xls", "xlsx");

            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION)
            {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                String thongBao = bus.nhapDuLieuTuExcel(filePath);

                JOptionPane.showMessageDialog(
                        this,
                        thongBao,
                        "Kết quả Nhập Excel",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh bảng
                loadDataToTable();
            }
        }

        // ================= EXPORT =================

        else if(dialog.getXacNhanExport()){

            ArrayList<taiKhoanETT> fullDanhSach = new ArrayList<>(bus.layTatCa());

            ExcelHelper.xuatDanhSachTaiKhoanRaExcel(
                    fullDanhSach,
                    this,
                    "DanhSachTaiKhoan"
            );
        }
    }

    // ===== REFRESH =====
    public void thucHienRefresh() {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText("");
        
        bus.layDanhSach();

        fullDataList.clear();
        List<Vector> dataList = new ArrayList<>();

        for (taiKhoanETT tk : taiKhoanBUS.ds) {
            Vector row = new Vector();
            row.add(tk.getIdTaiKhoan());
            row.add(tk.getTenDangNhap());
            row.add("*****");
            row.add(tk.getHoTen());
            row.add(tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa");
            dataList.add(row);
        }

        setTableData(dataList);
    }

    // ===== CHI TIẾT =====
    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xem chi tiết!");
            return;
        }

        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

        // Lấy ID
        Vector selectedRowData = fullDataList.get(absoluteIndex);
        int idCanTim = (int) selectedRowData.get(0);

        // Tìm object
        taiKhoanETT data = null;
        for (taiKhoanETT item : bus.ds) {
            if (item.getIdTaiKhoan() == idCanTim) {
                data = item;
                break;
            }
        }

        if (data != null) {
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            detailTaiKhoan dialog = new detailTaiKhoan(topFrame, true, data);
            dialog.setVisible(true);
        } 
        else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
        }
    }
}
