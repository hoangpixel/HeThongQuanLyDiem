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
        boolean isSuccess = data.themNganh(nganhMoi);
        if (isSuccess) {
            if (ds != null) 
            {
                ds.add(nganhMoi);
            }
        }

        return isSuccess;
    }

    public boolean suaNganh(nganhETT nganhDaSua) {
        // 1. Lưu xuống Database trước qua DAO
        if (data.suaNganh(nganhDaSua)) { 
        
            // 2. Nếu Database OK, tiến hành cập nhật lại biến ds tĩnh (RAM)
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng idnganh để dò tìm vì nó là khóa chính duy nhất (Unique)
                    // idnganh là kiểu int nên dùng == để so sánh
                    if (ds.get(i).getIdnganh() == nganhDaSua.getIdnganh()) {
                    ds.set(i, nganhDaSua); // Đè đối tượng mới vào vị trí cũ để UI cập nhật ngay
                    break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean xoaNganh(Entity.nganhETT nganhCanXoa) {
        // Sửa data.xoaNganh thành data.xoa
        if (data.xoaNganh(nganhCanXoa)) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dò đúng cái ID đó thì gạch tên khỏi danh sách
                    if (ds.get(i).getIdnganh() == nganhCanXoa.getIdnganh()) {
                        ds.remove(i); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }  

}

