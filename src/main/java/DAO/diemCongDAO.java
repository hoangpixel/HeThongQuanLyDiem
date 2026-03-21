/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.diemCongETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
/**
 *
 * @author mhoang
 */
public class diemCongDAO {
    public ArrayList<diemCongETT> layDanhSach() {
        ArrayList<diemCongETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<diemCongETT> listTuDB = session.createQuery("FROM diemCongETT", diemCongETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
