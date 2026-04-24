package BUS;
import DAO.nguyenVongXetTuyenDAO;
import DAO.thiSinhXetTuyenDAO;
import Entity.nguyenVongXetTuyenETT;
import Entity.thiSinhXetTuyenETT;
import java.util.ArrayList;
import java.util.HashMap;
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

    public thiSinhXetTuyenETT findById(int id) {
        if (ds == null) layDanhSach();
        for (thiSinhXetTuyenETT ts : ds) {
            if (ts.getIdThiSinh() == id) return ts;
        }
        return null;
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
        String loi = validateSuaThiSinh(ts);
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
        if (dao.xoaThiSinh(nv)) {
            
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
    
        public String layKhuVucTheoCCCD(String cccd) {
        // Gọi thẳng xuống DAO
        return dao.layKhuVucTheoCCCD(cccd);
    }
        
        public String layDoiTuongTheoCCCD(String cccd) {
        // Gọi thẳng xuống DAO
        return dao.layDoiTuongTheoCCCD(cccd);
    }
    public ArrayList<thiSinhXetTuyenETT> timKiemCoBan(String key, int index) {
        if (ds == null) layDanhSach();

        ArrayList<thiSinhXetTuyenETT> result = new ArrayList<>();

        for (thiSinhXetTuyenETT ts : ds) {
            switch (index) {
                case 0: if (String.valueOf(ts.getIdThiSinh()).contains(key)) result.add(ts); break;
                case 1: if (ts.getCccd().contains(key)) result.add(ts); break;
                case 2: if (ts.getSoBaoDanh().contains(key)) result.add(ts); break;
                case 3: if (ts.getHo().toLowerCase().contains(key.toLowerCase())) result.add(ts); break;
                case 4: if (ts.getTen().toLowerCase().contains(key.toLowerCase())) result.add(ts); break;
                case 5: if (ts.getDienThoai().contains(key)) result.add(ts); break;
                case 6: if (ts.getEmail().toLowerCase().contains(key.toLowerCase())) result.add(ts); break;
            }
        }

        return result;
    }
    private double parseDoubleSafe(String val) {
        try {
            if (val == null || val.trim().isEmpty()) return 0.0;
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public String nhapDuLieuTuExcel(String filePath) {
        try {
            ArrayList<ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);

            if (data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            for (int i = 1; i < data.size(); i++) {
                ArrayList<String> row = data.get(i);

                try {
                    thiSinhXetTuyenETT ts = new thiSinhXetTuyenETT();

                    // mapping
                    ts.setCccd(row.get(0));
                    ts.setSoBaoDanh(row.get(1));
                    ts.setHo(row.get(2));
                    ts.setTen(row.get(3));

                    // parse ngày
                    String dateStr = row.get(4);
                    java.sql.Date sqlDate = null;

                    try {
                        sqlDate = java.sql.Date.valueOf(dateStr);
                    } catch (Exception e1) {
                        try {
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                            java.util.Date utilDate = sdf.parse(dateStr);
                            sqlDate = new java.sql.Date(utilDate.getTime());
                        } catch (Exception e2) {
                            sqlDate = null;
                        }
                    }

                    ts.setNgaySinh(sqlDate);

                    ts.setDienThoai(row.get(5));
                    ts.setEmail(row.get(6));
                    ts.setGioiTinh(row.get(7));
                    ts.setNoiSinh(row.get(8));
                    ts.setDoiTuong(row.get(9));
                    ts.setKhuVuc(row.get(10));

                    ts.setPassword("123456");

                    if (dao.themThiSinh(ts)) {
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }

                } catch (Exception ex) {
                    System.out.println("❌ Lỗi dòng " + i + ": " + ex.getMessage());
                    soDongThatBai++;
                }
            }

            return "Nhập thành công: " + soDongThanhCong +
                   " dòng.\nLỗi: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
    // Thêm hàm này vào file thiSinhXetTuyenBUS.java
    public String validateSuaThiSinh(thiSinhXetTuyenETT ts) {
        // 1. Bỏ qua kiểm tra 12 số, chỉ cần check không để trống (nếu cần)
        if (ts.getCccd() == null || ts.getCccd().trim().isEmpty()) {
            return "CCCD không được để trống!";
        }

        // 2. Kiểm tra các trường khác như bình thường (Copy từ hàm validate cũ qua)
        if (ts.getHo() == null || ts.getHo().trim().isEmpty()) return "Họ không được để trống!";
        if (ts.getTen() == null || ts.getTen().trim().isEmpty()) return "Tên không được để trống!";
        if (ts.getNgaySinh() == null) return "Vui lòng chọn ngày sinh!";

        // Kiểm tra SĐT, Email, Password... (giữ nguyên logic cũ của ní)
        if (ts.getDienThoai() != null && !ts.getDienThoai().isEmpty()) {
            if (!ts.getDienThoai().matches("0\\d{9}")) return "SĐT phải 10 số và bắt đầu bằng 0!";
        }
        if (ts.getPassword() == null || ts.getPassword().length() < 6) return "Mật khẩu phải >= 6 ký tự!";

        return null; // Mọi thứ ổn
    }
}