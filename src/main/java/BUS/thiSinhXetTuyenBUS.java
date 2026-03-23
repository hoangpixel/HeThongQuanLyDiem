package BUS;
import static BUS.nguyenVongXetTuyenBUS.ds;
import DAO.nguyenVongXetTuyenDAO;
import DAO.thiSinhXetTuyenDAO;
import Entity.thiSinhXetTuyenETT;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LE MINH HUY
 */
public class thiSinhXetTuyenBUS {
    public static ArrayList<thiSinhXetTuyenETT> ds;
    thiSinhXetTuyenDAO data = new thiSinhXetTuyenDAO();
    private thiSinhXetTuyenDAO dao = new thiSinhXetTuyenDAO();
    
    public thiSinhXetTuyenBUS() {
        // Constructor có thể để trống
    }

    public ArrayList<thiSinhXetTuyenETT> layDanhSach() {
        if (ds == null) {
            ds = dao.layDanhSach();
        }
        return ds;
    }

    // ================== THÊM ==================
    public boolean themThiSinh(thiSinhXetTuyenETT ts) {
        boolean result = dao.themThiSinh(ts);
        if (result) {
            if (ds != null) {
                ds.add(ts); // 🔥 cập nhật realtime
            }
        }
        return result;
    }

    // ================== SỬA ==================
    public boolean suaThiSinh(thiSinhXetTuyenETT ts) {
        boolean result = dao.suaThiSinh(ts);
        if (result && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getIdThiSinh() == ts.getIdThiSinh()) {
                    ds.set(i, ts);
                    break;
                }
            }
        }
        return result;
    }
    public boolean xoaThiSinh(Entity.thiSinhXetTuyenETT nv) {
        // 1. Kêu DAO xóa dưới MySQL
        if (data.xoaThiSinh(nv)) {
            
            // 2. Nếu MySQL xóa thành công, xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dò đúng cái ID đó thì gạch tên khỏi danh sách
                    if (ds.get(i).getIdThiSinh() == nv.getIdThiSinh()) {
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