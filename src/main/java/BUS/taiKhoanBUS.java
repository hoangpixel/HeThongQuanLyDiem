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
        if (tk == null) throw new IllegalArgumentException("Tài khoản không hợp lệ!");
        if (tk.getHoTen() == null || tk.getHoTen().trim().isEmpty())
            throw new IllegalArgumentException("Họ tên không được để trống!");
        return data.capNhatTaiKhoan(tk);
    }

    // ========== ĐỔI MẬT KHẨU ==========
    public boolean doiMatKhau(String username, String matKhauCu, String matKhauMoi, String xacNhan) {
        if (matKhauMoi == null || matKhauMoi.length() < 6)
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 6 ký tự!");
        if (!matKhauMoi.equals(xacNhan))
            throw new IllegalArgumentException("Xác nhận mật khẩu không khớp!");
        if (data.kiemTraDangNhap(username, matKhauCu) == null)
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác!");
        return data.doiMatKhau(username, matKhauMoi);
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
}

