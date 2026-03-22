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
        add(contentPanel, BorderLayout.CENTER);

        // 👉 Bắt sự kiện nút từ navbar
        nav.btnNguyenVong.addActionListener(e -> {
            cardLayout.show(contentPanel, "nguyenvong");
        });
        
        contentPanel.add(new testGUI(),"test");
        add(contentPanel,BorderLayout.CENTER);
        nav.btnDiemThi.addActionListener(e -> {
            cardLayout.show(contentPanel,"test");
        });
        nav.btnThiSinh.addActionListener(e -> {
            cardLayout.show(contentPanel, "thisinh");
        });
    }
}