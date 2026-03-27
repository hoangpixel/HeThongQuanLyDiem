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
}
