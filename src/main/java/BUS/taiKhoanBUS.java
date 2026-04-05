/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.taiKhoanDAO;
import Entity.taiKhoanETT;

/**
 *
 * @author mhoang
 */
public class taiKhoanBUS {
    taiKhoanDAO data = new taiKhoanDAO();
    
    
    
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
}
