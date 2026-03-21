package DAO;

import CONFIG.HibernateUtil;
import Entity.thiSinhETT;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class thiSinhDAO {

    public ArrayList<thiSinhETT> layDanhSach() {
        ArrayList<thiSinhETT> ds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<thiSinhETT> listTuDB = session.createQuery("FROM thiSinhETT", thiSinhETT.class).list();
            ds = new ArrayList<>(listTuDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}