/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.chungChiETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mhoang
 */
public class chungChiDAO {
        public ArrayList<chungChiETT> layDanhSach() {
        ArrayList<chungChiETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<chungChiETT> listTuDB = session.createQuery("FROM chungChiETT", chungChiETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
        
    // Trong chungChiDAO.java (và gọi lại ở chungChiBUS.java)
    public double[] layDiemIELTS(String cccd) {
        double[] diem = {0.0, 0.0}; // [0]: Điểm quy đổi, [1]: Điểm cộng thưởng
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT diem_quydoi, diem_cong FROM xt_chungchi WHERE cccd = :cccd LIMIT 1";
            Object[] result = (Object[]) session.createNativeQuery(sql).setParameter("cccd", cccd).uniqueResult();
            if (result != null) {
                diem[0] = result[0] == null ? 0.0 : ((Number) result[0]).doubleValue();
                diem[1] = result[1] == null ? 0.0 : ((Number) result[1]).doubleValue();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return diem;
    }
}
