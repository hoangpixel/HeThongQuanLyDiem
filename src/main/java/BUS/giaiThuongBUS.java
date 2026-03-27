/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.giaiThuongETT;
import DAO.giaiThuongDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class giaiThuongBUS {
    public static ArrayList<giaiThuongETT> ds;
    giaiThuongDAO data = new giaiThuongDAO();
    public ArrayList<giaiThuongETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
    // Gọi xuống DAO để dò Giải thưởng
    public Object[] layGiaiThuong(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return null;
        }
        
        giaiThuongDAO dao = new giaiThuongDAO();
        return dao.layGiaiThuong(cccd);
    }
}
