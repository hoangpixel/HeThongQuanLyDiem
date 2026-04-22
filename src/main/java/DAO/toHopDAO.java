/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.toHopETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mhoang
 */
public class toHopDAO {

    // ===================== LẤY DANH SÁCH =====================
    public ArrayList<toHopETT> layDanhSach() {
        ArrayList<toHopETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<toHopETT> listTuDB = session.createQuery("FROM toHopETT", toHopETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ===================== THÊM =====================
    public boolean them(toHopETT obj) {
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
    public boolean sua(toHopETT obj) {
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
    public boolean xoa(toHopETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            toHopETT objCanXoa = session.get(toHopETT.class, obj.getIdtohop()); 
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
}
