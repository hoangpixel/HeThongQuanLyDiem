/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.bangQuyDoiETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mhoang
 */
public class bangQuyDoiDAO {
        public ArrayList<bangQuyDoiETT> layDanhSach() {
        ArrayList<bangQuyDoiETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<bangQuyDoiETT> listTuDB = session.createQuery("FROM bangQuyDoiETT", bangQuyDoiETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
        
     public bangQuyDoiETT timMocQuyDoi(String tenMon, double diemThi) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        // Câu query này tìm dòng mà: diem_a < diemThi <= diem_b
        String hql = "FROM bangQuyDoiETT b WHERE b.mon = :tenMon "
                   + "AND :diemThi > b.diemA AND :diemThi <= b.diemB";
        
        return session.createQuery(hql, bangQuyDoiETT.class)
                      .setParameter("tenMon", tenMon)
                      .setParameter("diemThi", diemThi)
                      .uniqueResult(); // Trả về đúng 1 dòng duy nhất thỏa mãn
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
