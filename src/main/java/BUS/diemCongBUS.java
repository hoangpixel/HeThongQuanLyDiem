/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.diemCongDAO;
import Entity.diemCongETT;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class diemCongBUS {
public static ArrayList<diemCongETT> ds;
    diemCongDAO data = new diemCongDAO();

    public ArrayList<diemCongETT> layDanhSach() {
        if (ds == null) {
            ds = data.layDanhSach();
        }
        return ds;
    }

    // =======================================================
    // HÀM QUAN TRỌNG: TÌM ĐIỂM CỘNG DỰA VÀO 4 ĐIỀU KIỆN
    // =======================================================
    public diemCongETT layDiemCongChinhXac(String cccd, String maNganh, String toHop, String phuongThuc) {
        if (ds == null) {
            layDanhSach();
        }
        
        for (diemCongETT dc : ds) {
            // Phải khớp toàn bộ CCCD, Mã Ngành, Tổ Hợp, Phương Thức mới chuẩn xác
            if (dc.getTsCccd() != null && dc.getTsCccd().equals(cccd) &&
                dc.getMaNganh() != null && dc.getMaNganh().equals(maNganh) &&
                dc.getMaToHop() != null && dc.getMaToHop().equals(toHop) &&
                dc.getPhuongThuc() != null && dc.getPhuongThuc().equals(phuongThuc)) {
                return dc;
            }
        }
        
        return null; // Nếu không tìm thấy tức là người này không có điểm cộng cho ngành này
    }
}
