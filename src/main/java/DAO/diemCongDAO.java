package DAO;

import CONFIG.HibernateUtil;
import Entity.diemCongETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class diemCongDAO {   // 🔥 THIẾU CÁI NÀY

    // ================== LẤY DANH SÁCH ==================
    public ArrayList<diemCongETT> layDanhSach() {
        ArrayList<diemCongETT> ds = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<diemCongETT> list = session
                    .createQuery("FROM diemCongETT", diemCongETT.class)
                    .list();

            ds = new ArrayList<>(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
    // ================== LẤY ĐIỂM CỘNG CHÍNH XÁC ==================
    public diemCongETT layDiemCongChinhXac(String cccd, String maNganh, String maToHop, String phuongThuc) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM diemCongETT WHERE tsCccd = :cccd "
                       + "AND maNganh = :maNganh "
                       + "AND maToHop = :maToHop "
                       + "AND phuongThuc = :phuongThuc";

            return session.createQuery(hql, diemCongETT.class)
                    .setParameter("cccd", cccd)
                    .setParameter("maNganh", maNganh)
                    .setParameter("maToHop", maToHop)
                    .setParameter("phuongThuc", phuongThuc)
                    .uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // ================== THÊM ==================
    public boolean themDiemCong(diemCongETT dc) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(dc);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ================== SỬA ==================
    public boolean suaDiemCong(diemCongETT dc) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(dc);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // ================== XÓA ==================
    public boolean xoaDiemCong(diemCongETT dc) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.remove(session.contains(dc) ? dc : session.merge(dc));

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}