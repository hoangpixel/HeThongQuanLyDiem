/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.phanQuyenDAO;
import Entity.phanQuyenETT;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author mhoang
 */
public class phanQuyenBUS {
    phanQuyenDAO data = new phanQuyenDAO();
    
    // 💡 BÍ QUYẾT Ở ĐÂY: Dùng HashMap để tra cứu quyền siêu tốc (Key là Tên Bảng)
    public static HashMap<String, phanQuyenETT> dsQuyen = new HashMap<>();

    // Hàm này CHỈ GỌI 1 LẦN DUY NHẤT lúc vừa đăng nhập thành công
    public void loadQuyenLenRAM(int idTaiKhoan) {
        dsQuyen.clear(); // Xóa sạch quyền của người cũ
        List<phanQuyenETT> list = data.layDanhSachQuyen(idTaiKhoan);
        
        if (list != null) {
            for (phanQuyenETT q : list) {
                // Nhét vào Map với chìa khóa là tên bảng (VD: "xt_nguyenvongxettuyen")
                dsQuyen.put(q.getTenBang(), q);
            }
        }
    }

    // ==============================================================
    // CÁC HÀM KIỂM TRA QUYỀN (GỌI Ở BẤT CỨ FORM NÀO CŨNG ĐƯỢC)
    // ==============================================================
    
    public static boolean checkQuyenXem(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) {
            return dsQuyen.get(tenBang).getQuyenXem() == 1; // 1 là có quyền, 0 là cấm
        }
        return false; // Mặc định không có tên trong bảng phân quyền là cấm luôn
    }

    public static boolean checkQuyenThem(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) {
            return dsQuyen.get(tenBang).getQuyenThem() == 1;
        }
        return false;
    }

    public static boolean checkQuyenSua(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) {
            return dsQuyen.get(tenBang).getQuyenSua() == 1;
        }
        return false;
    }

    public static boolean checkQuyenXoa(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) {
            return dsQuyen.get(tenBang).getQuyenXoa() == 1;
        }
        return false;
    }
}
