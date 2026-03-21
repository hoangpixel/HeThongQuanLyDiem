/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import CONFIG.HibernateUtil;
import Entity.diemThiETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
/**
 *
 * @author mhoang
 */
public class diemThiDAO {
    public ArrayList<diemThiETT> layDanhSach() {
        ArrayList<diemThiETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<diemThiETT> listTuDB = session.createQuery("FROM diemThiETT", diemThiETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
