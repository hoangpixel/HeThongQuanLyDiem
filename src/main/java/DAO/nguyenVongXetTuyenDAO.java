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
            
            // List<nguyenVongXetTuyenETT> listTuDB = session.createQuery("FROM nguyenVongXetTuyenETT", nguyenVongXetTuyenETT.class).list();
            
            // Tụi bây xài cái ở trên á cái này là t để sắp xếp cho đẹp
            List<nguyenVongXetTuyenETT> listTuDB = session.createQuery(
                "FROM nguyenVongXetTuyenETT ORDER BY nnCccd ASC, nvTt ASC", 
                nguyenVongXetTuyenETT.class).list();

            // Bước 2: Chuyển cái List của Hibernate thành ArrayList của bạn
            ds = new ArrayList<>(listTuDB);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ds;
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
            return false;
        }
    }
    
    public boolean suaNguyenVong(Entity.nguyenVongXetTuyenETT nv) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Dùng merge() để cập nhật dòng dữ liệu
            session.merge(nv); 
            
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
    
    public boolean xoaNguyenVong(Entity.nguyenVongXetTuyenETT nv) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // 1. Tìm đối tượng dưới DB dựa vào ID
            nguyenVongXetTuyenETT nvToDelete = session.get(nguyenVongXetTuyenETT.class, nv.getIdNv());
            
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
    
    // 1. Thợ mỏ lấy điểm ĐGNL
    public double layDiemDGNL(String cccd) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            Number diemNL = (Number) session.createNativeQuery("SELECT NL1 FROM xt_diemthixettuyen WHERE cccd = :cccd")
                    .setParameter("cccd", cccd).uniqueResult();
            return diemNL == null ? 0.0 : diemNL.doubleValue();
        } catch (Exception e) { e.printStackTrace(); return 0.0; }
    }

    // 2. Thợ mỏ lấy 3 môn từ mã Tổ Hợp
    public String[] layMonTuToHop(String matohop) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            Object[] toHop = (Object[]) session.createNativeQuery("SELECT mon1, mon2, mon3 FROM xt_tohop_monthi WHERE matohop = :ma")
                    .setParameter("ma", matohop).uniqueResult();
            if (toHop != null) {
                return new String[] {(String)toHop[0], (String)toHop[1], (String)toHop[2]};
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 3. Thợ mỏ lấy Điểm 3 Môn Thi Gốc
    public double[] layDiemThiBaMon(String cccd, String col1, String col2, String col3) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT `" + col1 + "`, `" + col2 + "`, `" + col3 + "` FROM xt_diemthixettuyen WHERE cccd = :cccd";
            Object[] diem = (Object[]) session.createNativeQuery(sql)
                    .setParameter("cccd", cccd).uniqueResult();
            if (diem != null) {
                return new double[] {
                    diem[0] == null ? 0.0 : ((Number) diem[0]).doubleValue(),
                    diem[1] == null ? 0.0 : ((Number) diem[1]).doubleValue(),
                    diem[2] == null ? 0.0 : ((Number) diem[2]).doubleValue()
                };
            }
        } catch (Exception e) { e.printStackTrace(); }
        return new double[] {0.0, 0.0, 0.0};
    }

    // 4. Thợ mỏ lấy điểm IELTS Quy Đổi
    public double layDiemIELTS(String cccd) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            Number diem = (Number) session.createNativeQuery("SELECT diem_quydoi FROM xt_chungchi WHERE cccd = :cccd LIMIT 1")
                    .setParameter("cccd", cccd).uniqueResult();
            return diem == null ? 0.0 : diem.doubleValue();
        } catch (Exception e) { e.printStackTrace(); return 0.0; }
    }
    
    
}
