/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.phanQuyenETT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 *
 * @author mhoang
 */
public class phanQuyenDAO {
    public List<phanQuyenETT> layDanhSachQuyen(int idTaiKhoan) {
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM phanQuyenETT WHERE idTaiKhoan = :id";
            Query<phanQuyenETT> query = session.createQuery(hql, phanQuyenETT.class);
            query.setParameter("id", idTaiKhoan);
            
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean xoaQuyenCu(int idTaiKhoan) {
        Transaction transaction = null;
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM phanQuyenETT WHERE idTaiKhoan = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", idTaiKhoan);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    
public boolean xoaPhanQuyen(phanQuyenETT pq) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Dùng HQL để xóa CHÍNH XÁC dòng có ID đó và Tên Bảng đó
            String hql = "DELETE FROM phanQuyenETT WHERE idTaiKhoan = :id AND tenBang = :bang";
            org.hibernate.query.Query query = session.createQuery(hql);
            query.setParameter("id", pq.getIdTaiKhoan());
            query.setParameter("bang", pq.getTenBang());
            
            int result = query.executeUpdate(); // Trả về số dòng bị xóa
            
            transaction.commit();
            return result > 0; // Xóa được ít nhất 1 dòng thì trả về true
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); 
            }
            e.printStackTrace();
            return false;
        }
    }
    
public ArrayList<phanQuyenETT> layDanhSach() {
        ArrayList<phanQuyenETT> ds = new ArrayList<>();
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<phanQuyenETT> listTuDB = session.createQuery(
                "FROM phanQuyenETT", 
                phanQuyenETT.class).list();
            
            ds = new ArrayList<>(listTuDB);
            
            // ==========================================================
            // 🔥 THUẬT TOÁN SẮP XẾP CHUẨN GIAO DIỆN 🔥
            // ==========================================================
            
            // 1. Định nghĩa thứ tự Tên Bảng y xì đúc từ trên xuống dưới theo Form của ông
            List<String> thuTuBang = Arrays.asList(
                "xt_nguyenvongxettuyen",
                "xt_bangquydoi",
                "xt_chungchi",
                "xt_giathuong",
                "xt_diemthixettuyen",
                "xt_diemcongxetuyen",
                "xt_nganh",
                "xt_nganh_tohop",
                "xt_tohop_monthi",
                "xt_thisinhxettuyen25",
                "xt_taikhoan",
                "xt_phanquyen" // Thằng này ẩn nên tui vứt nó xuống bét bảng
            );

            // 2. Dùng hàm sort() để nắn lại đội hình
            ds.sort((q1, q2) -> {
                // Ưu tiên 1: Gom các quyền của cùng 1 ID Tài khoản lại gần nhau (Tăng dần)
                if (q1.getIdTaiKhoan() != q2.getIdTaiKhoan()) {
                    return Integer.compare(q1.getIdTaiKhoan(), q2.getIdTaiKhoan());
                }
                
                // Ưu tiên 2: Nếu cùng 1 ID Tài khoản, thì xếp theo thứ tự bảng trên Form
                int index1 = thuTuBang.indexOf(q1.getTenBang());
                int index2 = thuTuBang.indexOf(q2.getTenBang());

                // Lỡ có bảng nào lạ hoắc không có trong danh sách thuTuBang thì đẩy xuống đáy
                if (index1 == -1) index1 = 999;
                if (index2 == -1) index2 = 999;

                return Integer.compare(index1, index2);
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ds;
    }
    
    // 2. Thêm 1 dòng quyền mới
    public boolean themQuyenMoi(phanQuyenETT quyen) {
        Transaction transaction = null;
        try (Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(quyen);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
