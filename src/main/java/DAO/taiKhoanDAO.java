/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.taiKhoanETT;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;
import UTIL.MD5Util;
import org.hibernate.Transaction;
/**
 *
 * @author mhoang
 */
public class taiKhoanDAO {
    
    
    public taiKhoanETT kiemTraDangNhap(String username, String password) {
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            // Lưu ý: Tên Class và Tên Thuộc tính phải khớp với Entity của ông nha
            String hql = "FROM taiKhoanETT WHERE tenDangNhap = :user AND matKhau = :pass AND trangThai = 1";
            Query<taiKhoanETT> query = session.createQuery(hql, taiKhoanETT.class);
            query.setParameter("user", username);
            query.setParameter("pass", MD5Util.md5(password)); // Nhớ mã hóa MD5 nếu DB ông lưu mã hóa
            
            return query.uniqueResult(); // Trả về 1 dòng duy nhất
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public taiKhoanETT layTheoUsername(String username) {
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM taiKhoanETT WHERE tenDangNhap = :user";
            Query<taiKhoanETT> query = session.createQuery(hql, taiKhoanETT.class);
            query.setParameter("user", username);

            return query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<taiKhoanETT> layTatCa() {
    List<taiKhoanETT> list = new ArrayList<>();

    try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {

        String hql = "FROM taiKhoanETT";
        Query<taiKhoanETT> query = session.createQuery(hql, taiKhoanETT.class);

        list = query.list();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    
    public boolean themTaiKhoan(taiKhoanETT tk) {
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(tk);
            session.getTransaction().commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatTaiKhoan(taiKhoanETT tk) {
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.merge(tk);
            session.getTransaction().commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaTaiKhoan(String username) {
        Transaction transaction = null;
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            taiKhoanETT tk = layTheoUsername(username);
            if (tk != null) {
                session.remove(session.merge(tk));
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean doiTrangThai(String username, int trangThai) {
        Transaction transaction = null;
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            taiKhoanETT tk = layTheoUsername(username);
            if (tk != null) {
                tk.setTrangThai(trangThai);
                session.merge(tk);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<taiKhoanETT> layDanhSach()
    {
        ArrayList<taiKhoanETT> ds = new ArrayList<>();
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<taiKhoanETT> listTuDB = session.createQuery(
                "FROM taiKhoanETT", 
                taiKhoanETT.class).list();
            ds = new ArrayList<>(listTuDB);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ds;
    }
}
