/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

import GUI.contentGUI;
import CONFIG.HibernateUtil;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Font;
import java.io.InputStream;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class main {

//    public static void main(String[] args) {
//        try {
//            // 1. TẢI FONT TRƯỚC
//            Font appFont = loadFont("/font/Saira-Regular.ttf", 14f);
//
//            // 2. ÉP FONT CHO FLATLAF (BẮT BUỘC PHẢI ĐẶT TRƯỚC SETUP)
//            UIManager.put("defaultFont", appFont);
//            
//            // (Tuỳ chọn) Chắc cú hơn, ép luôn toàn bộ các key UI của Swing
//            setGlobalFont(appFont);
//
//            // 3. KHỞI TẠO GIAO DIỆN FLATLAF
//            FlatLightLaf.setup();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 4. CHẠY UI TRÊN LUỒNG EVENT DISPATCH THREAD
//        SwingUtilities.invokeLater(() -> {
//            contentGUI frame = new contentGUI();
//            frame.setVisible(true);
//
//            // 5. Check DB
//            if (HibernateUtil.getSessionFactory() == null) {
//                String detail = HibernateUtil.getLastInitErrorMessage();
//                String msg = "Không thể kết nối Database nên dữ liệu sẽ không tải được.\n"
//                        + "Hãy kiểm tra MySQL đang chạy và cấu hình trong hibernate.cfg.xml (username/password).";
//                if (detail != null && !detail.trim().isEmpty()) {
//                    msg += "\n\nChi tiết: " + detail;
//                }
//                JOptionPane.showMessageDialog(frame, msg);
//            }
//        });
//    }

    // ================== LOAD FONT ==================
    public static Font loadFont(String path, float size) {
        try {
            InputStream is = main.class.getResourceAsStream(path);
            if (is == null) {
                // Thêm dòng in lỗi này để bạn biết nếu đường dẫn font bị sai
                System.err.println("⚠️ CẢNH BÁO: Không tìm thấy font tại đường dẫn: " + path);
                throw new RuntimeException("Không tìm thấy font: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("⚠️ Đang dùng font mặc định (Segoe UI) thay thế.");
            // fallback nếu lỗi font
            return new Font("Segoe UI", Font.PLAIN, (int) size);
        }
    }

    // ================== SET GLOBAL FONT ==================
    public static void setGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
//public class main {
//    public static void main(String[] args) {
//        try {
//            // Giữ nguyên cái giao diện FlatLaf cho đẹp
//            UIManager.setLookAndFeel(new FlatLightLaf());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            // Chạy LoginForm trước thay vì contentGUI
//            loginProMax login = new loginProMax();
//            login.setVisible(true);
//        });
//    }
//}
