/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.nganhETT;
import DAO.nganhDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class nganhBUS {
    public static ArrayList<nganhETT> ds;
    nganhDAO data = new nganhDAO();
    public ArrayList<nganhETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
// Nâng cấp: Lấy điểm chuẩn dựa vào cả MÃ NGÀNH và PHƯƠNG THỨC
// ==============================================================
    // HÀM LẤY ĐIỂM CHUẨN CHÍNH XÁC THEO TỪNG PHƯƠNG THỨC
    // ==============================================================
    public double layDiemChuanTheoPhuongThuc(String maNganh, String phuongThuc) {
        // 1. Kiểm tra xem danh sách đã được load lên RAM chưa
        if (ds == null || ds.isEmpty()) {
            layDanhSach();
        }
        
        // 2. Chạy vòng lặp quét tìm đúng mã ngành
        for (Entity.nganhETT nganh : ds) {
            if (nganh.getManganh() != null && nganh.getManganh().equals(maNganh)) {
                
                // 3. RẼ NHÁNH TÌM ĐIỂM CHUẨN DỰA VÀO CHỮ CỦA PHƯƠNG THỨC
                if (phuongThuc.equalsIgnoreCase("Xét THPT")) {
                    return (nganh.getDiemchuan_thpt() != null) ? nganh.getDiemchuan_thpt() : 0.0;
                } 
                else if (phuongThuc.equalsIgnoreCase("ĐGNL HCM")) {
                    return (nganh.getDiemchuan_dgnl() != null) ? nganh.getDiemchuan_dgnl() : 0.0;
                }
                else if (phuongThuc.equalsIgnoreCase("Xét VSAT")) {
                    return (nganh.getDiemchuan_vsat() != null) ? nganh.getDiemchuan_vsat() : 0.0;
                }
                else if (phuongThuc.equalsIgnoreCase("Xét tuyển thẳng")) {
                    return (nganh.getDiemchuan_xtt() != null) ? nganh.getDiemchuan_xtt() : 0.0;
                }
                
                // 4. FALLBACK (Cứu cánh)
                // Lỡ như người dùng nhập một cái phương thức lạ quắc, hoặc chưa có điểm riêng, 
                // thì lấy đỡ cột n_diemtrungtuyen gốc để so sánh tạm.
                if (nganh.getN_diemtrungtuyen() != null) {
                    return nganh.getN_diemtrungtuyen(); 
                }
                // Chữa cháy: Nếu điểm trúng tuyển cũng NULL, lấy luôn điểm sàn ra xét!
                else if (nganh.getN_diemsan() != null) { 
                    return nganh.getN_diemsan();
                }
            }
        }
        return 0.0; // Nếu không tìm thấy ngành, trả về 0
    }
}
