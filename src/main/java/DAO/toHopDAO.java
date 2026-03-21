/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.toHopETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
/**
 *
 * @author mhoang
 */
public class toHopDAO {
        public ArrayList<toHopETT> layDanhSach() {
        ArrayList<toHopETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<toHopETT> listTuDB = session.createQuery("FROM toHopETT", toHopETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
