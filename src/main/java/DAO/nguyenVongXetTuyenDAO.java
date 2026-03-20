/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.nguyenVongXetTuyenETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mhoang
 */
public class nguyenVongXetTuyenDAO {
    
public ArrayList<nguyenVongXetTuyenETT> layDanhSach() {
        // Khởi tạo sẵn một ArrayList rỗng để nếu lỗi thì nó trả về rỗng, không bị văng app
        ArrayList<nguyenVongXetTuyenETT> ds = new ArrayList<>();
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            // Bước 1: Gọi hàm .list() ở cuối để Hibernate chạy câu lệnh
            // Lưu ý chữ FROM nguyenVongXetTuyenETT phải khớp chính xác với tên Class Entity
            List<nguyenVongXetTuyenETT> listTuDB = session.createQuery("FROM nguyenVongXetTuyenETT", nguyenVongXetTuyenETT.class).list();
            
            // Bước 2: Chuyển cái List của Hibernate thành ArrayList của bạn
            ds = new ArrayList<>(listTuDB);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ds; // Trả về ArrayList ngon lành
    }

    public boolean saveNguyenVong(nguyenVongXetTuyenETT nv) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Lưu object xuống database (Hibernate tự sinh câu lệnh INSERT)
            session.persist(nv); 
            
            transaction.commit();
            return true; // Báo lưu thành công
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Nếu lỗi thì hoàn tác lại
            }
            e.printStackTrace();
            return false; // Báo lưu thất bại
        }
    }
}
