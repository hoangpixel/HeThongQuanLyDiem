/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.quyTacGiaiThuongETT;
import CONFIG.HibernateUtil;
import org.hibernate.Session;
import java.util.List;
/**
 *
 * @author mhoang
 */
public class quyTacGiaiThuongDAO {
    public List<quyTacGiaiThuongETT> layDanhSach() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM quyTacGiaiThuongETT", quyTacGiaiThuongETT.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Thêm hàm cập nhật quy tắc cho Admin
    public boolean luuQuyTac(quyTacGiaiThuongETT qt) {
        org.hibernate.Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(qt);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        }
    }
}