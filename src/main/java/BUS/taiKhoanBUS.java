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

    public taiKhoanETT findById(int id) {
        if (ds == null) layDanhSach();
        for (taiKhoanETT tk : ds) {
            if (tk.getIdTaiKhoan() == id) return tk;
        }
        return null;
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
    public boolean themTaiKhoan(taiKhoanETT tk) {
        boolean isSuccess = data.themTaiKhoan(tk);
        if (isSuccess) {
            if (ds != null) {
                ds.add(tk);
            }
        }
        return isSuccess;
    }

    // ========== CẬP NHẬT TÀI KHOẢN ==========
    public boolean capNhatTaiKhoan(taiKhoanETT tkMoi) {
        // Lấy mật khẩu cũ từ DB để xử lý logic giữ/đổi mật khẩu
        taiKhoanETT tkCu = data.layTheoUsername(tkMoi.getTenDangNhap());
        if (tkCu == null) return false;

        tkMoi.setIdTaiKhoan(tkCu.getIdTaiKhoan());

        // Mật khẩu rỗng → giữ cũ, có nội dung → đã được hash sẵn từ GUI
        if (tkMoi.getMatKhau() == null || tkMoi.getMatKhau().trim().isEmpty()) {
            tkMoi.setMatKhau(tkCu.getMatKhau());
        }

        if (data.capNhatTaiKhoan(tkMoi)) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getTenDangNhap().equals(tkMoi.getTenDangNhap())) {
                        ds.set(i, tkMoi);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ========== XÓA TÀI KHOẢN ==========
    public boolean xoaTaiKhoan(String username) {
        if (data.xoaTaiKhoan(username.trim())) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getTenDangNhap().equals(username.trim())) {
                        ds.remove(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ========== KHOÁ / MỞ KHOÁ ==========
    public boolean khoaTaiKhoan(String username) {
        if (data.layTheoUsername(username) == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        boolean ok = data.doiTrangThai(username, 0);
        if (ok && ds != null) {
            for (taiKhoanETT tk : ds) {
                if (tk.getTenDangNhap().equals(username)) {
                    tk.setTrangThai(0);
                    break;
                }
            }
        }
        return ok;
    }

    public boolean moKhoaTaiKhoan(String username) {
        if (data.layTheoUsername(username) == null)
            throw new IllegalArgumentException("Tài khoản không tồn tại!");
        boolean ok = data.doiTrangThai(username, 1);
        if (ok && ds != null) {
            for (taiKhoanETT tk : ds) {
                if (tk.getTenDangNhap().equals(username)) {
                    tk.setTrangThai(1);
                    break;
                }
            }
        }
        return ok;
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
                    tk.setTenDangNhap(row.get(1));
                    tk.setMatKhau(MD5Util.md5(row.get(2))); // mã hóa mật khẩu
                    tk.setHoTen(row.get(3));
                    tk.setTrangThai(Integer.parseInt(row.get(4)));

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
