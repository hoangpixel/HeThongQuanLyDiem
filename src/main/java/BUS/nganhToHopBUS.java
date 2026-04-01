/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.nganhToHopETT;
import DAO.nganhToHopDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class nganhToHopBUS {
    public static ArrayList<nganhToHopETT> ds;
    nganhToHopDAO data = new nganhToHopDAO();
    public ArrayList<nganhToHopETT> layDanhSach()
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
    
    public boolean kiemTraSoAm(String text)
    {
        int so = Integer.parseInt(text);
        if(so < 0)
        {
            return false;
        }
        return true;
    }
          
    public boolean kiemTraTrung(String key)
    {
        for(nganhToHopETT ct : ds)
        {
            if(ct.getKey().equals(key))
            {
                return true;
            }
        }
        return false;
    }
        
    // ===================== THÊM =====================
    public boolean themNganhToHop(nganhToHopETT nganhToHopMoi) {
        boolean isSuccess = data.themNganhToHop(nganhToHopMoi);
        if (isSuccess) {
            if (ds != null) {
                ds.add(nganhToHopMoi); // Cập nhật RAM luôn, không cần load lại DB
            }
        }
        return isSuccess;
    }

    // ===================== SỬA =====================
    public boolean suaNganhToHop(nganhToHopETT nganhToHopDaSua) {
        // 1. Lưu xuống Database trước qua DAO
        if (data.suaNganhToHop(nganhToHopDaSua)) {

            // 2. Nếu DB OK → cập nhật lại RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng id (khóa chính) để tìm đúng phần tử
                    if (ds.get(i).getIdNganhToHop() == nganhToHopDaSua.getIdNganhToHop()) {
                        ds.set(i, nganhToHopDaSua); // Đè object mới vào vị trí cũ
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ===================== XÓA =====================
    public boolean xoaNganhToHop(nganhToHopETT nganhToHopCanXoa) {
        // 1. Xóa trong Database trước
        if (data.xoa(nganhToHopCanXoa)) {

            // 2. Nếu DB OK → xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getIdNganhToHop() == nganhToHopCanXoa.getIdNganhToHop()) {
                        ds.remove(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    
    public double layDoLechDiem(String maNganh, String maToHop) {
        if(ds == null || ds.isEmpty()) {
            layDanhSach();
        }
        
        for(nganhToHopETT ct : ds) {
            if(ct.getMaNganh() != null && ct.getMaToHop() != null) {
                if(ct.getMaNganh().equals(maNganh) && ct.getMaToHop().equals(maToHop)) {
                    return ct.getDoLech();
                }
            }
        }
        return 0.0;
    }

// Gọi xuống DAO để lấy hệ số môn
    public double[] layHeSoMon(String maNganh, String maToHop) {
        // Nếu ông muốn kiểm tra rỗng (Validation) thì làm ở đây trước khi gọi DAO
        if (maNganh == null || maNganh.isEmpty() || maToHop == null || maToHop.isEmpty()) {
            return new double[]{1.0, 1.0, 1.0}; // Trả về mặc định nếu đầu vào lỗi
        }
        
        return data.layHeSoMon(maNganh, maToHop);
    }
    
    // Hàm này giống như Kính lúp, chỉ tìm kiếm trên RAM, không đụng tới DB
    public nganhToHopETT layNganhToHopBangKey(String key) {
        // 1. Đảm bảo danh sách trên RAM đã có đồ ăn
        if (ds == null || ds.isEmpty()) {
            layDanhSach(); 
        }

        // 2. Cầm kính lúp đi dò từng thằng một
        for (nganhToHopETT nth : ds) {
            // Nếu tìm thấy thằng nào có cái Mã Key trùng khớp (Ví dụ: "7480201_A00")
            if (nth.getKey() != null && nth.getKey().equals(key)) {
                return nth; // Bắt được nó rồi, ném nó ra ngoài!
            }
        }

        // 3. Tìm đỏ mắt không thấy thì báo Null
        return null; 
    }
}
