package BUS;
import static BUS.nguyenVongXetTuyenBUS.ds;
import DAO.nguyenVongXetTuyenDAO;
import DAO.thiSinhXetTuyenDAO;
import Entity.thiSinhXetTuyenETT;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LE MINH HUY
 */
public class thiSinhXetTuyenBUS {
    public static ArrayList<thiSinhXetTuyenETT> ds;
    thiSinhXetTuyenDAO data = new thiSinhXetTuyenDAO();
    private thiSinhXetTuyenDAO dao = new thiSinhXetTuyenDAO();
    
    public thiSinhXetTuyenBUS() {
        // Constructor có thể để trống
    }

    public ArrayList<thiSinhXetTuyenETT> layDanhSach() {
        if (ds == null) {
            ds = dao.layDanhSach();
        }
        return ds;
    }

    // ================== THÊM ==================
    public String themThiSinh(thiSinhXetTuyenETT ts) {

        // 1. validate
        String loi = validateThiSinh(ts);
        if (loi != null) {
            return loi;
        }

        // 2. lưu DB
        boolean result = dao.themThiSinh(ts);

        if (result) {
            if (ds != null) {
                ds.add(ts);
            }
            return "OK";
        }

        return "Lỗi khi lưu database!";
    }

    // ================== SỬA ==================
    public String suaThiSinh(thiSinhXetTuyenETT ts) {

        // validate
        String loi = validateThiSinh(ts);
        if (loi != null) {
            return loi;
        }

        boolean result = dao.suaThiSinh(ts);

        if (result && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getIdThiSinh() == ts.getIdThiSinh()) {
                    ds.set(i, ts);
                    break;
                }
            }
            return "OK";

        }
        return "Cập nhật thất bại!";
    }
    public boolean xoaThiSinh(Entity.thiSinhXetTuyenETT nv) {
        // 1. Kêu DAO xóa dưới MySQL
        if (data.xoaThiSinh(nv)) {
            
            // 2. Nếu MySQL xóa thành công, xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dò đúng cái ID đó thì gạch tên khỏi danh sách
                    if (ds.get(i).getIdThiSinh() == nv.getIdThiSinh()) {
                        ds.remove(i); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public String validateThiSinh(thiSinhXetTuyenETT ts) {

        // CCCD
        if (ts.getCccd() == null || ts.getCccd().trim().isEmpty()) {
            return "CCCD không được để trống!";
        }
        if (!ts.getCccd().matches("\\d{12}")) {
            return "CCCD phải đúng 12 số!";
        }

        // Check trùng CCCD
        for (thiSinhXetTuyenETT t : layDanhSach()) {
            if (t.getCccd().equals(ts.getCccd()) && t.getIdThiSinh() != ts.getIdThiSinh()) {
                return "CCCD đã tồn tại!";
            }
        }

        // Họ tên
        if (ts.getHo() == null || ts.getHo().trim().isEmpty()) {
            return "Họ không được để trống!";
        }
        if (ts.getTen() == null || ts.getTen().trim().isEmpty()) {
            return "Tên không được để trống!";
        }

        // Ngày sinh
        if (ts.getNgaySinh() == null) {
            return "Vui lòng chọn ngày sinh!";
        }

        // SĐT
        if (ts.getDienThoai() != null && !ts.getDienThoai().isEmpty()) {
            if (!ts.getDienThoai().matches("0\\d{9}")) {
                return "SĐT phải 10 số và bắt đầu bằng 0!";
            }
        }

        // Email
        if (ts.getEmail() != null && !ts.getEmail().trim().isEmpty()) {

            String email = ts.getEmail().trim();

            // format email
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return "Email không hợp lệ!";
            }

            // check trùng email (optional nhưng nên có)
            for (thiSinhXetTuyenETT t : layDanhSach()) {
                if (t.getEmail() != null &&
                    t.getEmail().equalsIgnoreCase(email) &&
                    t.getIdThiSinh() != ts.getIdThiSinh()) {

                    return "Email đã tồn tại!";
                }
            }
        }

        // Giới tính
        if (ts.getGioiTinh() == null || ts.getGioiTinh().isEmpty()) {
            return "Chưa chọn giới tính!";
        }

        // Đối tượng
        if (ts.getDoiTuong() == null || ts.getDoiTuong().isEmpty()) {
            return "Chưa chọn đối tượng!";
        }

        // Khu vực
        if (ts.getKhuVuc() == null || ts.getKhuVuc().isEmpty()) {
            return "Chưa chọn khu vực!";
        }

        // Password
        if (ts.getPassword() == null || ts.getPassword().length() < 6) {
            return "Mật khẩu phải >= 6 ký tự!";
        }

        return null;
    }
    public String taoSoBaoDanh() {
        layDanhSach(); // load ds

        int max = 0;

        for (thiSinhXetTuyenETT ts : ds) {
            String sbd = ts.getSoBaoDanh();

            if (sbd != null && sbd.startsWith("TS")) {
                try {
                    int number = Integer.parseInt(sbd.substring(6)); // lấy phần 001
                    if (number > max) {
                        max = number;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        int next = max + 1;
        return "TS26" + String.format("%03d", next);
    }
}