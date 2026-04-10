/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.taiKhoanBUS;
import Entity.taiKhoanETT;
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
        btnExcel.addActionListener(e -> hienThiExcel());
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
        List<taiKhoanETT> ds = bus.layTatCa();
        List<Vector> dataList = new ArrayList<>();

        for (taiKhoanETT tk : ds) {

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
        for (Vector row : fullDataList) {
            String giaTriCot = row.get(index + 1) != null ? row.get(index + 1).toString().toLowerCase() : "";
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
        // TODO: Mở dialog thêm tài khoản
        // JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        // insertTaiKhoan dialog = new insertTaiKhoan(topFrame, true);
        // dialog.setVisible(true);
        // if (dialog.xacNhanThem()) { loadDataToTable(); }
        JOptionPane.showMessageDialog(this, "Mở form THÊM tài khoản");
    }

    // ===== SỬA =====
    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để sửa!");
            return;
        }

        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

        // taiKhoanETT tk = bus.layTheoUsername(tableModel.getValueAt(row, 1).toString());
        // JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        // updateTaiKhoan dialog = new updateTaiKhoan(topFrame, true, tk);
        // dialog.setVisible(true);
        // if (dialog.xacNhanThem()) {
        //     Vector rowData = new Vector();
        //     rowData.add(...);
        //     fullDataList.set(absoluteIndex, rowData);
        //     renderCurrentPage();
        // }
        JOptionPane.showMessageDialog(this, "Mở form SỬA tài khoản");
    }

    // ===== XÓA =====
    private void hienThiDialogXoa() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String tenDangNhap = tableModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa tài khoản \"" + tenDangNhap + "\" không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            // TODO: bus.xoaTaiKhoan(tenDangNhap)
            fullDataList.remove(absoluteIndex);
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
            renderCurrentPage();
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
    public void hienThiExcel() {
        // TODO: Xử lý xuất/nhập Excel tài khoản
        JOptionPane.showMessageDialog(this, "Chức năng Excel đang phát triển!");
    }

    // ===== REFRESH =====
    public void thucHienRefresh() {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);
        loadDataToTable();
    }

    // ===== CHI TIẾT (ẩn nhưng vẫn giữ override phòng khi cần) =====
    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        // TODO: Mở dialog chi tiết nếu cần sau này
    }
}
