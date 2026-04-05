/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.taiKhoanETT;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
            query.setParameter("pass", password); // Nhớ mã hóa MD5 nếu DB ông lưu mã hóa
            
            return query.uniqueResult(); // Trả về 1 dòng duy nhất
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
