/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.taiKhoanDAO;
import Entity.taiKhoanETT;
import UTIL.MD5Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhoang
 */
public class taiKhoanBUS {
    taiKhoanDAO data = new taiKhoanDAO();
    public static ArrayList<taiKhoanETT> ds;
    
    public ArrayList<taiKhoanETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
    // Biến static lưu tài khoản đang đăng nhập để xài toàn cục
    public static taiKhoanETT taiKhoanHienTai = null;

    public boolean login(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        
        taiKhoanETT tk = data.kiemTraDangNhap(username, password);
        if (tk != null) {
            taiKhoanHienTai = tk; // Lưu lên RAM
            return true;
        }
        return false;
    }
    
    public void logout() {
        taiKhoanHienTai = null;
        // Lát nữa sẽ gọi thêm hàm xóa RAM phân quyền ở đây
    }
    
    // ========== LẤY THEO USERNAME ==========
    public taiKhoanETT layTheoUsername(String username) {
        if (username == null || username.trim().isEmpty()) return null;
        return data.layTheoUsername(username.trim());
    }
    
    // ========== LẤY ALL ==========
     public List<taiKhoanETT> layTatCa() {
        return data.layTatCa();
    }

    // ========== THÊM TÀI KHOẢN ==========
    public boolean themTaiKhoan(String tenDangNhap, String matKhau, String hoTen) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty())
            throw new IllegalArgumentException("Tên đăng nhập không được để trống!");
        if (matKhau == null || matKhau.length() < 6)
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự!");
        if (hoTen == null || hoTen.trim().isEmpty())
            throw new IllegalArgumentException("Họ tên không được để trống!");
        if (data.layTheoUsername(tenDangNhap.trim()) != null)
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại!");

        taiKhoanETT tk = new taiKhoanETT();
        tk.setTenDangNhap(tenDangNhap.trim());
        tk.setMatKhau(MD5Util.md5(matKhau));
        tk.setHoTen(hoTen.trim());
        tk.setTrangThai(1);
        return data.themTaiKhoan(tk);
    }

    // ========== CẬP NHẬT TÀI KHOẢN ==========
    public boolean capNhatTaiKhoan(taiKhoanETT tk) {
        
        if (tk == null)
            throw new IllegalArgumentException("Tài khoản không hợp lệ!");
        if (tk.getHoTen() == null || tk.getHoTen().trim().isEmpty())
            throw new IllegalArgumentException("Họ tên không được để trống!");
        // Lấy tài khoản cũ
        taiKhoanETT tkCu = data.layTheoUsername(tk.getTenDangNhap());
        if (tkCu == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        // Giữ lại mật khẩu cũ
        tk.setMatKhau(tkCu.getMatKhau());
        return data.capNhatTaiKhoan(tk);
    }
    
    // ========== XÓA TÀI KHOẢN ==========
    public boolean xoaTaiKhoan(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Tên đăng nhập không hợp lệ!");
        if (data.layTheoUsername(username.trim()) == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        return data.xoaTaiKhoan(username.trim());
    }

    // ========== KHOÁ / MỞ KHOÁ ==========
    public boolean khoaTaiKhoan(String username) {
        if (data.layTheoUsername(username) == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        return data.doiTrangThai(username, 0);
    }

    public boolean moKhoaTaiKhoan(String username) {
        if (data.layTheoUsername(username) == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        return data.doiTrangThai(username, 1);
    }

    // ========== KIỂM TRA ACTIVE ==========
    public boolean isActive(String username) {
        taiKhoanETT tk = data.layTheoUsername(username);
        return tk != null && tk.getTrangThai() != null && tk.getTrangThai() == 1;
    }

    public String nhapDuLieuTuExcel(String filePath) {

        try {
            // 1. Đọc dữ liệu Excel
            ArrayList<ArrayList<String>> dataExcel = EXCEL.ExcelHelper.docFileExcel(filePath);
            if (dataExcel.size() <= 1) {
                return "File Excel trống hoặc chỉ có dòng tiêu đề!";
            }
            int soDongThanhCong = 0;
            int soDongThatBai = 0;
            // 2. Bỏ dòng header
            for (int i = 1; i < dataExcel.size(); i++) {
                ArrayList<String> row = dataExcel.get(i);
                try {
                    taiKhoanETT tk = new taiKhoanETT();

                    // Mapping cột Excel → Entity
                    tk.setTenDangNhap(row.get(0));
                    tk.setMatKhau(MD5Util.md5(row.get(1))); // mã hóa mật khẩu
                    tk.setHoTen(row.get(2));
                    tk.setTrangThai(Integer.parseInt(row.get(3)));

                    // 3. Thêm vào database
                    if (data.themTaiKhoan(tk)) {
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    soDongThatBai++;
                }
            }
            return "Nhập thành công: " + soDongThanhCong +
                   " dòng.\nLỗi/Trùng lặp: " + soDongThatBai + " dòng.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
}
