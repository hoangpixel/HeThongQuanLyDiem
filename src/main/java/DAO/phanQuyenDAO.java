/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.phanQuyenETT;
import java.util.List;
import org.hibernate.Session;
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
}
