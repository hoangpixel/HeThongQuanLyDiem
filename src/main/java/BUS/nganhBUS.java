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
    
    public boolean capNhatChiTieuThucTe() {
        nganhDAO dao = new nganhDAO();
        return dao.capNhatChiTieuThucTe();
    }
}

