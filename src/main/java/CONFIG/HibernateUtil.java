/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONFIG;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author mhoang
 */
public class HibernateUtil {
    private static volatile SessionFactory sessionFactory;
    private static volatile Throwable lastInitError;

    private static SessionFactory buildSessionFactory() {
        try {
            // Tự động đọc file hibernate.cfg.xml để kết nối
            lastInitError = null;
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            lastInitError = ex;
            System.err.println("Kết nối Database thất bại: " + ex);
            return null;
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    sessionFactory = buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

    public static String getLastInitErrorMessage() {
        Throwable err = lastInitError;
        if (err == null)
            return null;
        return err.getMessage();
    }
}
