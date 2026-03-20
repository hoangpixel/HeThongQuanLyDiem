/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.nguyenVongXetTuyenDAO;
import Entity.nguyenVongXetTuyenETT;
import java.util.ArrayList;

/**
 *
 * @author mhoang
 */
public class nguyenVongXetTuyenBUS {
    public static ArrayList<nguyenVongXetTuyenETT> ds;
    nguyenVongXetTuyenDAO data = new nguyenVongXetTuyenDAO();
    
    public ArrayList<nguyenVongXetTuyenETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
    public boolean themNguyenVong(nguyenVongXetTuyenETT nv) {
        // 1. Kiểm tra logic (như cũ)
        if (nv.getNnCccd() == null || nv.getNnCccd().trim().isEmpty()) {
            System.out.println("Lỗi: CCCD không được để trống!");
            return false;
        }
        
        if (nv.getNvMaNganh() == null || nv.getNvMaNganh().trim().isEmpty()) {
            System.out.println("Lỗi: Mã ngành không được để trống!");
            return false;
        }

        // 2. Gọi DAO lưu xuống MySQL và hứng kết quả
        boolean isSuccess = data.saveNguyenVong(nv);
        
        // 3. ĐỒNG BỘ DỮ LIỆU: Nếu MySQL đã lưu thành công, thì nhét luôn vào static ds
        if (isSuccess) {
            if (ds != null) {
                ds.add(nv); // Thêm vào list tĩnh. GUI gọi lại layDanhSach() sẽ thấy luôn!
            }
        }

        return isSuccess;
    }
}
