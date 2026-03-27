/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.nganhETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dat
 */
public class nganhDAO {
    public ArrayList<nganhETT> layDanhSach() {
        // Khởi tạo sẵn một ArrayList rỗng để nếu lỗi thì nó trả về rỗng, không bị văng app
        ArrayList<nganhETT> ds = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bước 1: Gọi hàm .list() ở cuối để Hibernate chạy câu lệnh
            List<nganhETT> listTuDB = session.createQuery("FROM nganhETT", nganhETT.class).list();
            // Bước 2: Chuyển cái List của Hibernate thành ArrayList của bạn
            ds = new ArrayList<>(listTuDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public boolean themNganh(nganhETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Lưu object xuống database (Hibernate tự sinh câu lệnh INSERT)
            session.persist(obj); 
            
            transaction.commit();
            return true; // Báo lưu thành công
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Nếu lỗi thì hoàn tác lại
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean suaNganh(Entity.nganhETT obj) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Dùng merge() để cập nhật dòng dữ liệu
            session.merge(obj); 
            
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Lỗi thì quay xe, không lưu bậy bạ
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaNganh(Entity.nganhETT obj) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // 1. Tìm đối tượng dưới DB dựa vào ID
            Entity.nganhETT nganhToDelete = session.get(Entity.nganhETT.class, obj.getIdnganh());
            
            // 2. Nếu tìm thấy thì đem đi hủy
            if (nganhToDelete != null) {
                session.remove(nganhToDelete); 
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
}