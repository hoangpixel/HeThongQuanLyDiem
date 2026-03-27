/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.chungChiETT;
import java.util.ArrayList;
import DAO.chungChiDAO;
/**
 *
 * @author mhoang
 */
public class chungChiBUS {
    public static ArrayList<chungChiETT> ds;
    chungChiDAO data = new chungChiDAO();
    public ArrayList<chungChiETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
    // Gọi xuống DAO để dò điểm IELTS
    public double[] layDiemIELTS(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return new double[]{0.0, 0.0};
        }
        
        chungChiDAO dao = new chungChiDAO();
        return dao.layDiemIELTS(cccd);
    }
}
