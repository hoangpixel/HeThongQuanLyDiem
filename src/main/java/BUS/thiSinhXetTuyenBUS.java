package BUS;
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
public ArrayList<thiSinhXetTuyenETT> ds;
    
    public thiSinhXetTuyenBUS() {
        // Constructor có thể để trống
    }

    public void layDanhSach() {
        thiSinhXetTuyenDAO dao = new thiSinhXetTuyenDAO();
        ds = dao.layDanhSachThíSinh();
    }
    // Hàm gọi DAO để thêm thí sinh
    public boolean themThiSinh(thiSinhXetTuyenETT ts) {
        DAO.thiSinhXetTuyenDAO dao = new DAO.thiSinhXetTuyenDAO();
        return dao.themThiSinh(ts);
    }
}