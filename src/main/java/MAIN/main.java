/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

import GUI.contentGUI;
import CONFIG.HibernateUtil;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

/**
 *
 * @author mhoang
 */
public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            contentGUI frame = new contentGUI();
            frame.setVisible(true);

            // Nếu DB lỗi (sai user/pass, chưa bật MySQL...), vẫn cho UI chạy và báo nhẹ
            if (HibernateUtil.getSessionFactory() == null) {
                String detail = HibernateUtil.getLastInitErrorMessage();
                String msg = "Không thể kết nối Database nên dữ liệu sẽ không tải được.\n"
                        + "Hãy kiểm tra MySQL đang chạy và cấu hình trong hibernate.cfg.xml (username/password).";
                if (detail != null && !detail.trim().isEmpty()) {
                    msg += "\n\nChi tiết: " + detail;
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
    }
}
