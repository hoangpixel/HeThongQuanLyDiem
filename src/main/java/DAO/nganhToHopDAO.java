/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.nganhToHopETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author mhoang
 */
public class nganhToHopDAO {
    
    // ===================== LẤY DANH SÁCH =====================
    public ArrayList<nganhToHopETT> layDanhSach() {
        ArrayList<nganhToHopETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<nganhToHopETT> listTuDB = session.createQuery("FROM nganhToHopETT", nganhToHopETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ===================== THÊM =====================
    public boolean them(nganhToHopETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ===================== SỬA =====================
    public boolean sua(nganhToHopETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ===================== XÓA =====================
    public boolean xoa(nganhToHopETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            nganhToHopETT objCanXoa = session.get(nganhToHopETT.class, obj.getIdNganhToHop()); // ← đổi getId() đúng theo @Id
            if (objCanXoa != null) {
                session.remove(objCanXoa);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Trong nganhToHopDAO.java (và gọi lại ở nganhToHopBUS.java)
    public double[] layHeSoMon(String maNganh, String maToHop) {
        double[] heSo = {1.0, 1.0, 1.0}; // Mặc định là 1 hết
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT hsmon1, hsmon2, hsmon3 FROM xt_nganh_tohop WHERE manganh = :mn AND matohop = :th LIMIT 1";
            Object[] result = (Object[]) session.createNativeQuery(sql)
                    .setParameter("mn", maNganh).setParameter("th", maToHop).uniqueResult();
            if (result != null) {
                heSo[0] = result[0] == null ? 1.0 : ((Number) result[0]).doubleValue();
                heSo[1] = result[1] == null ? 1.0 : ((Number) result[1]).doubleValue();
                heSo[2] = result[2] == null ? 1.0 : ((Number) result[2]).doubleValue();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return heSo;
    }    
}
