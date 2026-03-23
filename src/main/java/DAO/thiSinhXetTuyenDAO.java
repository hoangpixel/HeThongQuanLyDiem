/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.thiSinhXetTuyenETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author LE MINH HUY
 */
public class thiSinhXetTuyenDAO {

    // ================== LẤY DANH SÁCH ==================
    public ArrayList<thiSinhXetTuyenETT> layDanhSach() {
        ArrayList<thiSinhXetTuyenETT> ds = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<thiSinhXetTuyenETT> listTuDB =
                    session.createQuery("FROM thiSinhXetTuyenETT", thiSinhXetTuyenETT.class).list();

            ds = new ArrayList<>(listTuDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    // ================== THÊM ==================
    public boolean themThiSinh(thiSinhXetTuyenETT ts) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(ts);

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ================== UPDATE ==================
    public boolean suaThiSinh(thiSinhXetTuyenETT ts) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(ts);

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ================== TÌM KIẾM ==================
    public ArrayList<thiSinhXetTuyenETT> timKiem(String keyword) {
        ArrayList<thiSinhXetTuyenETT> ds = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<thiSinhXetTuyenETT> listTuDB = session.createQuery(
                    "FROM thiSinhXetTuyenETT WHERE cccd LIKE :kw OR CONCAT(ho, ' ', ten) LIKE :kw",
                    thiSinhXetTuyenETT.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .list();

            ds = new ArrayList<>(listTuDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
    public boolean xoaThiSinh(Entity.thiSinhXetTuyenETT nv) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // 1. Tìm đối tượng dưới DB dựa vào ID
            Entity.thiSinhXetTuyenETT nvToDelete = session.get(Entity.thiSinhXetTuyenETT.class, nv.getIdThiSinh());
            
            // 2. Nếu tìm thấy thì đem đi hủy
            if (nvToDelete != null) {
                session.remove(nvToDelete); 
            }
            
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Lỗi thì quay xe
            }
            e.printStackTrace();
            return false;
        }
    }
    // ================== PHÂN TRANG ==================
    public ArrayList<thiSinhXetTuyenETT> layDanhSachPhanTrang(int page) {
        ArrayList<thiSinhXetTuyenETT> ds = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<thiSinhXetTuyenETT> listTuDB = session.createQuery(
                    "FROM thiSinhXetTuyenETT", thiSinhXetTuyenETT.class)
                    .setFirstResult((page - 1) * 20)
                    .setMaxResults(20)
                    .list();

            ds = new ArrayList<>(listTuDB);
            System.out.println("DAO size: " + listTuDB.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
   
}