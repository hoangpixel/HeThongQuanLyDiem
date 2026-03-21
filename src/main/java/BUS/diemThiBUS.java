/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.diemThiDAO;
import Entity.diemThiETT;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class diemThiBUS {
    public static ArrayList<diemThiETT> ds;
    diemThiDAO data = new diemThiDAO();

    public ArrayList<diemThiETT> layDanhSach() {
        if (ds == null) {
            ds = data.layDanhSach();
        }
        return ds;
    }

    // =======================================================
    // HÀM QUAN TRỌNG: TÌM BẢNG ĐIỂM CỦA 1 NGƯỜI DỰA VÀO CCCD
    // =======================================================
    public diemThiETT layDiemTheoCCCD(String cccd) {
        // Đảm bảo dữ liệu đã được load lên
        if (ds == null) {
            layDanhSach();
        }
        
        // Chạy vòng lặp tìm ông nào có CCCD khớp thì bế điểm ổng ra
        for (diemThiETT dt : ds) {
            if (dt.getCccd() != null && dt.getCccd().equals(cccd)) {
                return dt;
            }
        }
        
        return null; // Không tìm thấy (Ông này chưa thi hoặc chưa nhập điểm)
    }
}
