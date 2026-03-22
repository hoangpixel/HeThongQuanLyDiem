/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.thiSinhXetTuyenETT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author LE MINH HUY
 */
public class thiSinhXetTuyenDAO {
// Cấu hình kết nối DB (Đổi lại cho khớp với user/pass MySQL của bạn)
    private final String url = "jdbc:mysql://localhost:3306/hethongquanlydiem"; 
    private final String user = "root";
    private final String password = "061005";

    public ArrayList<thiSinhXetTuyenETT> layDanhSachThíSinh() {
        ArrayList<thiSinhXetTuyenETT> ds = new ArrayList<>();
        String sql = "SELECT * FROM xt_thisinhxettuyen25";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                thiSinhXetTuyenETT ts = new thiSinhXetTuyenETT();
                ts.setIdThiSinh(rs.getInt("idthisinh"));
                ts.setCccd(rs.getString("cccd"));
                ts.setSoBaoDanh(rs.getString("sobaodanh"));
                ts.setHo(rs.getString("ho"));
                ts.setTen(rs.getString("ten"));
                ts.setNgaySinh(rs.getString("ngay_sinh"));
                ts.setDienThoai(rs.getString("dien_thoai"));
                ts.setPassword(rs.getString("password"));
                ts.setGioiTinh(rs.getString("gioi_tinh"));
                ts.setEmail(rs.getString("email"));
                ts.setNoiSinh(rs.getString("noi_sinh"));
                ts.setUpdatedAt(rs.getDate("updated_at"));
                ts.setDoiTuong(rs.getString("doi_tuong"));
                ts.setKhuVuc(rs.getString("khu_vuc"));

                ds.add(ts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lấy danh sách thí sinh từ DB!");
        }
        return ds;
    }
    // Hàm thêm thí sinh vào Database
    public boolean themThiSinh(thiSinhXetTuyenETT ts) {
        String sql = "INSERT INTO xt_thisinhxettuyen25 (cccd, sobaodanh, ho, ten, ngay_sinh, dien_thoai, password, gioi_tinh, email, noi_sinh, doi_tuong, khu_vuc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, ts.getCccd());
            pst.setString(2, ts.getSoBaoDanh());
            pst.setString(3, ts.getHo());
            pst.setString(4, ts.getTen());
            pst.setString(5, ts.getNgaySinh());
            pst.setString(6, ts.getDienThoai());
            pst.setString(7, ts.getPassword());
            pst.setString(8, ts.getGioiTinh());
            pst.setString(9, ts.getEmail());
            pst.setString(10, ts.getNoiSinh());
            pst.setString(11, ts.getDoiTuong());
            pst.setString(12, ts.getKhuVuc());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
