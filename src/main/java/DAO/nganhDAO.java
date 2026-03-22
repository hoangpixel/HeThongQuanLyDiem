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

/**
 *
 * @author mhoang
 */
public class nganhDAO {
        public ArrayList<nganhETT> layDanhSach() {
        ArrayList<nganhETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<nganhETT> listTuDB = session.createQuery("FROM nganhETT", nganhETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
