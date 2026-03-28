/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.giaiThuongETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mhoang
 */
public class giaiThuongDAO {
        public ArrayList<giaiThuongETT> layDanhSach() {
        ArrayList<giaiThuongETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<giaiThuongETT> listTuDB = session.createQuery("FROM giaiThuongETT", giaiThuongETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
        
    // Trong giaiThuongDAO.java (và gọi lại ở giaiThuongBUS.java)
    public Object[] layGiaiThuong(String cccd) {
        // Trả về [0]: Mã môn, [1]: Điểm có môn, [2]: Điểm không môn
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT ma_mon, diem_cong_co_mon, diem_cong_khong_mon FROM xt_giathuong WHERE cccd = :cccd LIMIT 1";
            return (Object[]) session.createNativeQuery(sql).setParameter("cccd", cccd).uniqueResult();
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
    
    public String[] layCapVaLoaiGiai(String cccd) {
        String[] ketQua = {"", ""}; // [0]: Cấp giải, [1]: Loại giải
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT cap_giai, loai_giai FROM xt_giathuong WHERE cccd = :cccd LIMIT 1";
            Object[] result = (Object[]) session.createNativeQuery(sql).setParameter("cccd", cccd).uniqueResult();
            if (result != null) {
                ketQua[0] = result[0] != null ? result[0].toString() : "";
                ketQua[1] = result[1] != null ? result[1].toString() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
