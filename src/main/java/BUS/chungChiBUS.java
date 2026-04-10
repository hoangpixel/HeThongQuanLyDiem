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

    public boolean themCC(chungChiETT cc) {
        boolean isSuccess = data.themCC(cc);
        if (isSuccess) {
            if (ds != null) {
                ds.add(cc);
            }
        }
        return isSuccess;
    }

    public boolean suaCC(chungChiETT ccMoi) {
        if (data.suaCC(ccMoi)) { 

            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng ID làm khóa định danh duy nhất
                    if (ds.get(i).getIdCc() == ccMoi.getIdCc()) {
                        ds.set(i, ccMoi); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean xoaCC(int idCc) {
        if (data.xoaCC(idCc)) {
            // Xóa trên RAM để GUI render lại chính xác
            if (ds != null) {
                ds.removeIf(item -> item.getIdCc() == idCc);
            }
            return true;
        }
        return false;
    }
    
    public boolean kiemTraRong(String text) {
        return text == null || text.trim().isEmpty();
    }

    public boolean kiemTraSoThuc(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean checkToHopCoMonAnh(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return false;
        }
        return data.checkToHopCoMonAnh(cccd);
    }
    
    public double[] layDiemIELTS(String cccd) {
        if (kiemTraRong(cccd)) {
            return new double[]{0.0, 0.0};
        }
        return data.layDiemIELTS(cccd);
    }
}
