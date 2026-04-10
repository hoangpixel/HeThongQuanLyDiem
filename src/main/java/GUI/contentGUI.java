package GUI;

import java.awt.*;
import javax.swing.*;

public class contentGUI extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public contentGUI() {
        setTitle("Hệ thống quản lý điểm");
        setSize(1450, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 👉 Navbar bên trái
        navbarGUI nav = new navbarGUI();
        add(nav, BorderLayout.WEST);

        // 👉 Content bên phải
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // 👉 Thêm panel của bạn
        contentPanel.add(new nguyenVongXetTuyenGUI(), "nguyenvong");
        contentPanel.add(new thiSinhXetTuyenGUI(), "thisinh");
        contentPanel.add(new diemCongXetTuyenGUI(), "diemcong");
        contentPanel.add(new NganhGUI(), "nganh");
        contentPanel.add(new bangQuyDoiVSATGUI(), "bangquydoi");
        contentPanel.add(new nganhToHopGUI(), "nganhtohop");
//        contentPanel.add(new toHopGUI(), "tohop");
//        contentPanel.add(new taiKhoanGUI(), "taikhoan");
        
        add(contentPanel, BorderLayout.CENTER);

        // 👉 Bắt sự kiện nút từ navbar
        nav.btnNguyenVong.addActionListener(e -> {
            cardLayout.show(contentPanel, "nguyenvong");
        });

        nav.btnThiSinh.addActionListener(e -> {
            cardLayout.show(contentPanel, "thisinh");
        });
        nav.btnDiemCong.addActionListener(e -> {
            cardLayout.show(contentPanel, "diemcong");
        });
        nav.btnNganh.addActionListener(e -> {
            cardLayout.show(contentPanel, "nganh");
        });

        nav.btnBangQuyDoi.addActionListener(e -> {
            cardLayout.show(contentPanel, "bangquydoi");
        });
        
        nav.btnNganhToHop.addActionListener(e -> {
            cardLayout.show(contentPanel, "nganhtohop");
        });
         nav.btnToHopMon.addActionListener(e -> {
            cardLayout.show(contentPanel, "tohop");
        });
          nav.btnTaiKhoan.addActionListener(e -> {
            cardLayout.show(contentPanel, "taikhoan");
        });
    }
}