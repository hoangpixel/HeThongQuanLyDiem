/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONFIG.HibernateUtil;
import Entity.nganhToHopETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author mhoang
 */
public class nganhToHopDAO {
    
    // ===================== LẤY DANH SÁCH =====================
    public ArrayList<nganhToHopETT> layDanhSach() {
        ArrayList<nganhToHopETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<nganhToHopETT> listTuDB = session.createQuery("FROM nganhToHopETT", nganhToHopETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ===================== THÊM =====================
public boolean themNganhToHop(Entity.nganhToHopETT nth) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // 1. KHỞI TẠO CỜ (Mặc định tất cả các môn đều bằng 0)
            int N1=0, TO=0, LI=0, HO=0, SI=0, VA=0, SU=0, DI=0, TI=0, KHAC=0, KTPL=0;

            // 2. QUÉT 3 MÔN ĐƯỢC CHỌN (Gặp môn nào thì bật cờ môn đó lên 1)
            String[] mons = {nth.getMon1(), nth.getMon2(), nth.getMon3()};
            for (String m : mons) {
                if (m == null || m.isEmpty()) continue;
                
                // Ép viết hoa cho chắc cú
                switch (m.toUpperCase()) {
                    case "N1": N1 = 1; break;
                    case "TO": TO = 1; break;
                    case "LI": LI = 1; break; // Trong DB của ông là LI (chứ ko phải LY)
                    case "HO": HO = 1; break;
                    case "SI": SI = 1; break;
                    case "VA": VA = 1; break;
                    case "SU": SU = 1; break;
                    case "DI": DI = 1; break;
                    case "TI": TI = 1; break;
                    case "KHAC": KHAC = 1; break;
                    case "KTPL": KTPL = 1; break;
                }
            }

            String sql = "INSERT INTO xt_nganh_tohop " +
                         "(manganh, matohop, th_mon1, hsmon1, th_mon2, hsmon2, th_mon3, hsmon3, tb_keys, dolech, " +
                         "`N1`, `TO`, `LI`, `HO`, `SI`, `VA`, `SU`, `DI`, `TI`, `KHAC`, `KTPL`) " +
                         "VALUES " +
                         "(:ma, :tohop, :m1, :hs1, :m2, :hs2, :m3, :hs3, :keys, :dolech, " +
                         ":fN1, :fTO, :fLI, :fHO, :fSI, :fVA, :fSU, :fDI, :fTI, :fKHAC, :fKTPL)";

            session.createNativeQuery(sql)
                   .setParameter("ma", nth.getMaNganh())
                   .setParameter("tohop", nth.getMaToHop())
                   .setParameter("m1", nth.getMon1())
                   .setParameter("hs1", nth.getHeSoMon1())
                   .setParameter("m2", nth.getMon2())
                   .setParameter("hs2", nth.getHeSoMon2())
                   .setParameter("m3", nth.getMon3())
                   .setParameter("hs3", nth.getHeSoMon3())
                   .setParameter("keys", nth.getKey())
                   .setParameter("dolech", nth.getDoLech())
                   // Truyền đống cờ (Flag) vào
                   .setParameter("fN1", N1).setParameter("fTO", TO).setParameter("fLI", LI)
                   .setParameter("fHO", HO).setParameter("fSI", SI).setParameter("fVA", VA)
                   .setParameter("fSU", SU).setParameter("fDI", DI).setParameter("fTI", TI)
                   .setParameter("fKHAC", KHAC).setParameter("fKTPL", KTPL)
                   .executeUpdate();

            
            Number lastId = (Number) session.createNativeQuery("SELECT LAST_INSERT_ID()").uniqueResult();
            if (lastId != null) {
                // Ép ID mới vào chính cái đối tượng mà GUI truyền xuống
                // Lưu ý: Tên hàm set của ông là setId hay setIdNganhToHop thì ông tự đổi cho khớp nha
                nth.setIdNganhToHop(lastId.intValue()); 
            }
            
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===================== SỬA =====================
public boolean suaNganhToHop(Entity.nganhToHopETT nth) {
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // 1. KHỞI TẠO CỜ LẠI TỪ ĐẦU (Reset tất cả về 0)
            int N1=0, TO=0, LI=0, HO=0, SI=0, VA=0, SU=0, DI=0, TI=0, KHAC=0, KTPL=0;

            // 2. QUÉT LẠI 3 MÔN (Giống y chang hàm Thêm)
            String[] mons = {nth.getMon1(), nth.getMon2(), nth.getMon3()};
            for (String m : mons) {
                if (m == null || m.isEmpty()) continue;
                
                switch (m.toUpperCase()) {
                    case "N1": N1 = 1; break;
                    case "TO": TO = 1; break;
                    case "LI": LI = 1; break; 
                    case "HO": HO = 1; break;
                    case "SI": SI = 1; break;
                    case "VA": VA = 1; break;
                    case "SU": SU = 1; break;
                    case "DI": DI = 1; break;
                    case "TI": TI = 1; break;
                    case "KHAC": KHAC = 1; break;
                    case "KTPL": KTPL = 1; break;
                }
            }

// Tương tự, bọc Backtick ` ` cho phần SET
            String sql = "UPDATE xt_nganh_tohop SET " +
                         "manganh = :ma, matohop = :tohop, " +
                         "th_mon1 = :m1, hsmon1 = :hs1, " +
                         "th_mon2 = :m2, hsmon2 = :hs2, " +
                         "th_mon3 = :m3, hsmon3 = :hs3, " +
                         "tb_keys = :keys, dolech = :dolech, " +
                         "`N1` = :fN1, `TO` = :fTO, `LI` = :fLI, `HO` = :fHO, `SI` = :fSI, " +
                         "`VA` = :fVA, `SU` = :fSU, `DI` = :fDI, `TI` = :fTI, `KHAC` = :fKHAC, `KTPL` = :fKTPL " +
                         "WHERE id = :id";

            session.createNativeQuery(sql)
                   .setParameter("ma", nth.getMaNganh())
                   .setParameter("tohop", nth.getMaToHop())
                   .setParameter("m1", nth.getMon1())
                   .setParameter("hs1", nth.getHeSoMon1())
                   .setParameter("m2", nth.getMon2())
                   .setParameter("hs2", nth.getHeSoMon2())
                   .setParameter("m3", nth.getMon3())
                   .setParameter("hs3", nth.getHeSoMon3())
                   .setParameter("keys", nth.getKey())
                   .setParameter("dolech", nth.getDoLech())
                   // Đống cờ môn học
                   .setParameter("fN1", N1).setParameter("fTO", TO).setParameter("fLI", LI)
                   .setParameter("fHO", HO).setParameter("fSI", SI).setParameter("fVA", VA)
                   .setParameter("fSU", SU).setParameter("fDI", DI).setParameter("fTI", TI)
                   .setParameter("fKHAC", KHAC).setParameter("fKTPL", KTPL)
                   // Cột khóa chính ID để nhận diện
                   .setParameter("id", nth.getIdNganhToHop()) 
                   .executeUpdate();

            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===================== XÓA =====================
    public boolean xoa(nganhToHopETT obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            nganhToHopETT objCanXoa = session.get(nganhToHopETT.class, obj.getIdNganhToHop()); // ← đổi getId() đúng theo @Id
            if (objCanXoa != null) {
                session.remove(objCanXoa);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Trong nganhToHopDAO.java (và gọi lại ở nganhToHopBUS.java)
    public double[] layHeSoMon(String maNganh, String maToHop) {
        double[] heSo = {1.0, 1.0, 1.0}; // Mặc định là 1 hết
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT hsmon1, hsmon2, hsmon3 FROM xt_nganh_tohop WHERE manganh = :mn AND matohop = :th LIMIT 1";
            Object[] result = (Object[]) session.createNativeQuery(sql)
                    .setParameter("mn", maNganh).setParameter("th", maToHop).uniqueResult();
            if (result != null) {
                heSo[0] = result[0] == null ? 1.0 : ((Number) result[0]).doubleValue();
                heSo[1] = result[1] == null ? 1.0 : ((Number) result[1]).doubleValue();
                heSo[2] = result[2] == null ? 1.0 : ((Number) result[2]).doubleValue();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return heSo;
    }    
}
