package GUI;

import BUS.phanQuyenBUS;
import java.awt.*;
import javax.swing.*;

public class contentGUI extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    boolean isAdmin = false;

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

        // 1. 🔥 TẠO TRANG CHÀO MỪNG VÀ ADD VÀO ĐẦU TIÊN 🔥
        JPanel pnlWelcome = new JPanel(new BorderLayout());
        pnlWelcome.setBackground(Color.WHITE);
        JLabel lblWelcome = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG TUYỂN SINH", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(52, 152, 219)); // Màu xanh cho đẹp
        pnlWelcome.add(lblWelcome, BorderLayout.CENTER);
        
        contentPanel.add(pnlWelcome, "welcome"); // Đưa lên đầu tiên để làm mặc định!

        if (BUS.taiKhoanBUS.taiKhoanHienTai != null) 
        {
            isAdmin = BUS.taiKhoanBUS.taiKhoanHienTai.getIdTaiKhoan()== 1;
        }
        
        // 2. 👉 Thêm các panel của ông bình thường ở dưới
        // (Lưu ý: Ông có thể bọc if kiểm tra quyền ở đây để khỏi tốn RAM load mấy form không có quyền)
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_nguyenvongxettuyen")) {
            contentPanel.add(new nguyenVongXetTuyenGUI(), "nguyenvong");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_thisinhxettuyen25")) {
            contentPanel.add(new thiSinhXetTuyenGUI(), "thisinh");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_diemcongxettuyen")) {
            contentPanel.add(new diemCongXetTuyenGUI(), "diemcong");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_nganh")) {
            contentPanel.add(new NganhGUI(), "nganh");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_bangquydoi")) {
            contentPanel.add(new bangQuyDoiVSATGUI(), "bangquydoi");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_nganh_tohop")) {
            contentPanel.add(new nganhToHopGUI(), "nganhtohop");
        }
        if (isAdmin || phanQuyenBUS.checkQuyenXem("xt_phanquyen")) {
            contentPanel.add(new phanQuyenGUI(), "phanquyen");
        }
        // contentPanel.add(new toHopGUI(), "tohop");
        // contentPanel.add(new taiKhoanGUI(), "taikhoan");
        
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
          nav.btnPhanQuyen.addActionListener(e -> {
            cardLayout.show(contentPanel, "phanquyen");
        });  
    }
}