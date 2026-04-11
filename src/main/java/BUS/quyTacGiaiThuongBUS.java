/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.quyTacGiaiThuongDAO;
import Entity.quyTacGiaiThuongETT;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author mhoang
 */
public class quyTacGiaiThuongBUS {
    private quyTacGiaiThuongDAO dao = new quyTacGiaiThuongDAO();
    
    // Biến cache để dò tìm cho nhanh (Key: "Cấp-Loại")
    private HashMap<String, quyTacGiaiThuongETT> mapQuyTac = new HashMap<>();

    public void loadQuyTac() {
        List<quyTacGiaiThuongETT> list = dao.layDanhSach();
        mapQuyTac.clear();
        if (list != null) {
            for (quyTacGiaiThuongETT qt : list) {
                String key = qt.getCapGiai() + "-" + qt.getLoaiGiai();
                mapQuyTac.put(key, qt);
            }
        }
    }

public quyTacGiaiThuongETT layQuyTac(String cap, String loai) {
        if (mapQuyTac.isEmpty()) loadQuyTac();
        
        // CÁCH 1: Thử dò khớp chính xác 100% trước (Ví dụ: "Quốc gia-Nhất")
        String keyExact = cap + "-" + loai;
        if (mapQuyTac.containsKey(keyExact)) {
            return mapQuyTac.get(keyExact);
        }

        // CÁCH 2: DÒ TÌM THÔNG MINH (Chứa từ khóa)
        // Dùng để trị mấy cái lỗi như "Giải Nhất" khác "Nhất", "Cấp Tỉnh" khác "Tỉnh"
        for (String key : mapQuyTac.keySet()) {
            quyTacGiaiThuongETT quyTac = mapQuyTac.get(key);
            String capQuyTac = quyTac.getCapGiai().toLowerCase();
            String loaiQuyTac = quyTac.getLoaiGiai().toLowerCase();
            
            String capCuaThiSinh = cap.toLowerCase();
            String loaiCuaThiSinh = loai.toLowerCase();

            // Nếu Cấp giải khớp (hoặc chứa nhau) VÀ Loại giải chứa nhau
            // Ví dụ: "giải nhất".contains("nhất") -> TRUE!
            if ((capCuaThiSinh.contains(capQuyTac) || capQuyTac.contains(capCuaThiSinh)) && 
                loaiCuaThiSinh.contains(loaiQuyTac)) {
                
                return quyTac; // Tìm thấy rồi, lụm luôn!
            }
        }
        
        return null; // Quét nát bét mà không thấy thì mới chịu thua
    }
}
