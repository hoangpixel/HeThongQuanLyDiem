/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.chungChiETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mhoang
 */
public class chungChiDAO {
    public ArrayList<chungChiETT> layDanhSach() {
        ArrayList<chungChiETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<chungChiETT> listTuDB = session.createQuery("FROM chungChiETT", chungChiETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public boolean themCC(chungChiETT ett) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(ett); // Hibernate tự sinh INSERT
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean suaCC(chungChiETT cc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(cc); // Dùng merge() để cập nhật dòng dữ liệu
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback(); // Lỗi thì quay xe 
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaCC(int idCc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Tìm đối tượng dưới DB dựa vào ID
            chungChiETT ettToDelete = session.get(chungChiETT.class, idCc);
            
            if (ettToDelete != null) {
                session.remove(ettToDelete); // Nếu thấy thì remove
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
        
    // Trong chungChiDAO.java (và gọi lại ở chungChiBUS.java)
    public double[] layDiemIELTS(String cccd) {
        double[] diem = {0.0, 0.0}; // [0]: Điểm quy đổi, [1]: Điểm cộng thưởng
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT diem_quydoi, diem_cong FROM xt_chungchi WHERE cccd = :cccd LIMIT 1";
            Object[] result = (Object[]) session.createNativeQuery(sql).setParameter("cccd", cccd).uniqueResult();
            if (result != null) {
                diem[0] = result[0] == null ? 0.0 : ((Number) result[0]).doubleValue();
                diem[1] = result[1] == null ? 0.0 : ((Number) result[1]).doubleValue();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return diem;
    }

    public boolean checkToHopCoMonAnh(String cccd) {
        boolean coMonAnh = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bỏ LIMIT 1 đi để lấy hết tất cả các tổ hợp môn của các nguyện vọng
            String sql = "SELECT th.tentohop FROM xt_thisinhxettuyen25 ts " + 
                         "JOIN xt_nguyenvongxettuyen nv ON ts.cccd = nv.nn_cccd " +
                         "JOIN xt_tohop_monthi th ON nv.tt_thm = th.matohop " +
                         "WHERE ts.cccd = :cccd";

            // Dùng list() thay vì uniqueResult()
            List<String> results = session.createNativeQuery(sql, String.class).setParameter("cccd", cccd).list();

            if (results != null && !results.isEmpty()) {
                // Sửa Object thành String luôn cho nó đồng bộ và mượt mà
                for (String result : results) { 
                    if (result != null) {
                        String tenTohop = result.toLowerCase();
                        if (tenTohop.contains("anh")) {
                            coMonAnh = true;
                            break; // Thấy 1 cái có môn Anh là đủ điều kiện rồi, thoát vòng lặp luôn cho nhẹ máy
                        }
                    }
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return coMonAnh;
    }
}
