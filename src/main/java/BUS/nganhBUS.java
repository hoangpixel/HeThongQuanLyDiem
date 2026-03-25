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
        ds = data.layDanhSach(); 
        return ds;
    }

    public boolean kiemTraRong(String text)
    {
        if(text == null || text.trim().isEmpty())
        {
            return true;
        }
        return false;
    }
    
    public boolean kiemTraSo(String text) 
    {
        try 
        {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
    public boolean kiemTraSoThuc(String text) 
    {
    try {
        Double.parseDouble(text);
        return true;
    } catch (NumberFormatException e) 
    {
        return false;
    }
}
    
// Nâng cấp: Lấy điểm chuẩn dựa vào cả MÃ NGÀNH và PHƯƠNG THỨC
// ==============================================================
    // HÀM LẤY ĐIỂM CHUẨN CHÍNH XÁC THEO TỪNG PHƯƠNG THỨC
    // ==============================================================
    public double layDiemChuanTheoPhuongThuc(String maNganh, String phuongThuc) {
        // Kiểm tra xem danh sách đã được load chưa
        if (ds == null || ds.isEmpty()) {
            layDanhSach();
        }
        
        // Quét tìm đúng mã ngành
        for (Entity.nganhETT nganh : ds) {
            if (nganh.getManganh() != null && nganh.getManganh().equals(maNganh)) {
                
                // Rẽ nhánh tìm điểm chuẩn tùy theo chữ của phương thức
                if (phuongThuc.equalsIgnoreCase("Xét THPT")) {
                    return (nganh.getDiemchuan_thpt() != null) ? nganh.getDiemchuan_thpt() : 0.0;
                } 
                else if (phuongThuc.equalsIgnoreCase("ĐGNL HCM")) {
                    return (nganh.getDiemchuan_dgnl() != null) ? nganh.getDiemchuan_dgnl() : 0.0;
                }
                else if (phuongThuc.equalsIgnoreCase("Xét VSAT")) {
                    return (nganh.getDiemchuan_vsat() != null) ? nganh.getDiemchuan_vsat() : 0.0;
                }
                else if (phuongThuc.equalsIgnoreCase("Xét tuyển thẳng") || phuongThuc.equalsIgnoreCase("Tuyển Thẳng")) {
                    return (nganh.getDiemchuan_xtt() != null) ? nganh.getDiemchuan_xtt() : 0.0;
                }
                
                // Fallback: Lấy điểm chuẩn gốc nếu không khớp phương thức nào
                if (nganh.getN_diemtrungtuyen() != null) {
                    return nganh.getN_diemtrungtuyen(); 
                }
                // Fallback 2: Lấy điểm sàn nếu điểm chuẩn gốc cũng trống
                else if (nganh.getN_diemsan() != null) { 
                    return nganh.getN_diemsan();
                }
            }
        }
        return 0.0; // Trả về 0 nếu không tìm thấy
    }

    public boolean themNganh(nganhETT nganhMoi) {
        // Sửa data.themNganh thành data.them
        if (data.themNganh(nganhMoi)) {
            // Khi thêm thành công, MySQL sẽ tự cấp ID mới. 
            // Ta phải ép BUS gọi lệnh lấy lại toàn bộ danh sách từ DB để cập nhật ID.
            ds = data.layDanhSach(); 
            return true;
        }
        return false;
    }

    public boolean suaNganh(nganhETT nganhDaSua) {
        // Sửa data.suaNganh thành data.sua
        if (data.suaNganh(nganhDaSua)) { 
            // Ép buộc tải lại danh sách mới nhất từ DB, bỏ qua cái kiểm tra if(ds == null)
            ds = data.layDanhSach(); 
            return true;
        }
        return false;
    }
    
    public boolean xoaNganh(Entity.nganhETT nganhCanXoa) {
        // Sửa data.xoaNganh thành data.xoa
        if (data.xoaNganh(nganhCanXoa)) {
            if (ds != null) {
                // Dùng hàm removeIf này code sẽ gọn và chạy nhanh hơn vòng lặp for rất nhiều
                ds.removeIf(n -> n.getIdnganh() == nganhCanXoa.getIdnganh());
            }
            return true;
        }
        return false;
    }  

}

