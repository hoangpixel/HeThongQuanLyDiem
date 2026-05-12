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
    
public boolean capNhatChiTieuThucTe() {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            
            // THAY ĐỔI SQL: Trừ trực tiếp các cột chỉ tiêu thành phần (sl_xtt, sl_dgnl, sl_vsat)
            String sql = "UPDATE xt_nganh n " +
                         "SET n.sl_thpt = n.n_chitieu - " +
                         "    IFNULL(n.sl_xtt, 0) - " +
                         "    IFNULL(n.sl_dgnl, 0) - " +
                         "    IFNULL(n.sl_vsat, 0)";
                         
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//// Hàm nhận nguyên cái Map điểm chuẩn từ BUS và Update 1 lần duy nhất
//    public boolean capNhatDanhSachDiemChuan(java.util.HashMap<String, Double> diemChuanMap) {
//        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
//            session.beginTransaction(); // MỞ KẾT NỐI 1 LẦN
//
//            // Quét qua cái Map
//            for (java.util.Map.Entry<String, Double> entry : diemChuanMap.entrySet()) {
//                String[] parts = entry.getKey().split("_");
//                String maNganh = parts[0];
//                String phuongThuc = parts[1];
//                double diemChuan = entry.getValue();
//
//                // Dùng switch-case cho sạch đẹp thay vì if-else lằng nhằng
//                String sqlUpdate = "";
//                switch (phuongThuc) {
//                    case "Đánh giá V-SAT":
//                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_vsat = :dc WHERE manganh = :ma"; break;
//                    case "ĐGNL HCM":
//                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_dgnl = :dc WHERE manganh = :ma"; break;
//                    case "Xét THPT":
//                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_thpt = :dc WHERE manganh = :ma"; break;
//                    case "Xét tuyển thẳng":
//                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_xtt = :dc WHERE manganh = :ma"; break;
//                }
//
//                if (!sqlUpdate.isEmpty()) {
//                    session.createNativeQuery(sqlUpdate)
//                           .setParameter("dc", diemChuan)
//                           .setParameter("ma", maNganh)
//                           .executeUpdate(); // Chạy ngầm Update
//                }
//            }
//            
//            session.getTransaction().commit(); // CHỐT SỔ TẤT CẢ VÀ ĐÓNG KẾT NỐI
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    
    // =========================================================
    // 🔥 CẬP NHẬT ĐIỂM CHUẨN VÀ TỔNG SỐ ĐĂNG KÝ BẰNG NATIVE SQL
    // =========================================================
    public boolean capNhatDanhSachDiemChuanVaSoLuong(java.util.HashMap<String, Double> diemChuanMap, java.util.HashMap<String, Integer> slDangKyMap) {
        // Nhớ check lại đường dẫn CONFIG.HibernateUtil hay HibernateUtil nha Boss
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // MỞ KẾT NỐI 1 LẦN

            // ---------------------------------------------------------
            // 1. QUÉT MAP ĐIỂM CHUẨN (Giữ nguyên logic xịn xò của Boss)
            // ---------------------------------------------------------
            for (java.util.Map.Entry<String, Double> entry : diemChuanMap.entrySet()) {
                String[] parts = entry.getKey().split("_");
                if (parts.length < 2) continue; // Chặn lỗi out of bounds
                
                String maNganh = parts[0];
                String phuongThuc = parts[1];
                double diemChuan = entry.getValue();

                String sqlUpdate = "";
                switch (phuongThuc) {
                    case "Đánh giá V-SAT":
                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_vsat = :dc WHERE manganh = :ma"; break;
                    case "ĐGNL HCM":
                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_dgnl = :dc WHERE manganh = :ma"; break;
                    case "Xét THPT":
                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_thpt = :dc WHERE manganh = :ma"; break;
                    case "Xét tuyển thẳng":
                        sqlUpdate = "UPDATE xt_nganh SET diemchuan_xtt = :dc WHERE manganh = :ma"; break;
                }

                if (!sqlUpdate.isEmpty()) {
                    session.createNativeQuery(sqlUpdate)
                           .setParameter("dc", diemChuan)
                           .setParameter("ma", maNganh)
                           .executeUpdate(); // Chạy ngầm Update
                }
            }

            // ---------------------------------------------------------
            // 2. QUÉT MAP SỐ LƯỢNG ĐĂNG KÝ VÀ UPDATE
            // ---------------------------------------------------------
            if (slDangKyMap != null) {
                for (java.util.Map.Entry<String, Integer> entry : slDangKyMap.entrySet()) {
                    String maNganh = entry.getKey();
                    int soLuong = entry.getValue();

                    // Cập nhật số lượng cho ngành tương ứng
                    String sqlUpdateSL = "UPDATE xt_nganh SET sl_dangky = :sl WHERE manganh = :ma";
                    session.createNativeQuery(sqlUpdateSL)
                           .setParameter("sl", soLuong)
                           .setParameter("ma", maNganh)
                           .executeUpdate();
                }
            }
            
            transaction.commit(); // CHỐT SỔ TẤT CẢ VÀ ĐÓNG KẾT NỐI
            return true;
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Có lỗi thì quay xe, không lưu bậy
            }
            e.printStackTrace();
            return false;
        }
    }
}