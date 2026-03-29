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
import org.hibernate.Transaction;

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

    public boolean themQuyDoi(bangQuyDoiETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaQuyDoi(bangQuyDoiETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaQuyDoi(bangQuyDoiETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            bangQuyDoiETT toDelete = session.get(bangQuyDoiETT.class, obj.getIdqd());
            if (toDelete != null) {
                session.remove(toDelete);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
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
