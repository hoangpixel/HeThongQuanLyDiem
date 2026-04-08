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
    
    public String[] layCapVaLoaiGiai(String cccd) {
        if (cccd == null || cccd.isEmpty()) return new String[]{"", ""};
        giaiThuongDAO dao = new giaiThuongDAO();
        return dao.layCapVaLoaiGiai(cccd);
    }
    
    public boolean themGT(giaiThuongETT gt) {
        boolean isSuccess = data.themGiaiThuong(gt);
        if (isSuccess) {
            if (ds != null) {
                ds.add(gt);
            }
        }
        return isSuccess;
    }

    public boolean suaGT(giaiThuongETT gtMoi) {
        if (data.suaGiaiThuong(gtMoi)) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getIdGt() == gtMoi.getIdGt()) {
                        ds.set(i, gtMoi);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean xoaGT(int idGt) {
        if (data.xoaGiaiThuong(idGt)) {
            if (ds != null) {
                ds.removeIf(item -> item.getIdGt() == idGt);
            } 
            return true;
        }
        return false;
    }

    public ArrayList<giaiThuongETT> timKiemCoBan(String tim, int index) {
        if (ds == null) layDanhSach();
        ArrayList<giaiThuongETT> dskq = new ArrayList<>();
        String tuKhoa = tim.trim().toLowerCase();

        for (giaiThuongETT ct : ds) {
            String data = switch (index) {
                case 0 -> String.valueOf(ct.getIdGt());
                case 1 -> ct.getCccd();
                case 2 -> ct.getCapGiai();
                case 3 -> ct.getMaMon();
                case 4 -> ct.getLoaiGiai();
                default -> "";
            };
            if (data.toLowerCase().contains(tuKhoa)) dskq.add(ct);
        }
        return dskq;
    }
}
