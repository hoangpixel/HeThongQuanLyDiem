package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class navbarGUI extends JPanel {

    public JButton btnThiSinh, btnDiemThi, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemCong, btnNguyenVong, btnDangXuat, btnTaiKhoan, btnChungChi, btnGiaiThuong;

    public navbarGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(260, 800)); // Nới rộng ra 1 xíu để chứa icon cho thoải mái
        setBackground(new Color(33, 37, 41)); 
        setBorder(new EmptyBorder(25, 15, 25, 15)); 

        // ================= TITLE =================
        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subTitle = new JLabel("Tuyển Sinh 2025");
        subTitle.setForeground(new Color(52, 152, 219)); 
        subTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(subTitle);
        add(Box.createVerticalStrut(25)); 

        // ================= HIỂN THỊ TÊN NGƯỜI DÙNG =================
        // Lấy tên từ RAM (Kiểm tra null để lỡ ông chạy test file này riêng nó không bị lỗi)
        String tenNguoiDung = "Khách";
        if (BUS.taiKhoanBUS.taiKhoanHienTai != null) {
            tenNguoiDung = BUS.taiKhoanBUS.taiKhoanHienTai.getHoTen();
        }

        JLabel lblWelcome = new JLabel("Xin chào,");
        lblWelcome.setForeground(new Color(170, 170, 170));
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel("👤 " + tenNguoiDung);
        lblName.setForeground(new Color(46, 204, 113)); // Màu xanh lá nổi bật
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(lblWelcome);
        add(Box.createVerticalStrut(5));
        add(lblName);
        add(Box.createVerticalStrut(20));

        // Đường kẻ ngang mờ mờ cho sang trọng
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(200, 1));
        separator.setForeground(new Color(70, 75, 80));
        separator.setBackground(new Color(33, 37, 41));
        add(separator);
        add(Box.createVerticalStrut(20));

        // ================= MENU BUTTONS (CÓ ICON UNICODE) =================
        btnThiSinh = createButton("🎓 1. Quản lý Thí sinh");
        btnNganh = createButton("🏫 2. Quản lý Ngành học");
        btnToHopMon = createButton("📚 3. Quản lý Tổ hợp môn");
        btnNganhToHop = createButton("🔗 4. Tổ hợp - Ngành");
        btnBangQuyDoi = createButton("⚖️ 5. Bảng quy đổi V-SAT");
        btnDiemThi = createButton("📝 6. Quản lý Điểm thi");
        btnDiemCong = createButton("⭐ 7. Điểm cộng & Ưu tiên");
        btnNguyenVong = createButton("🎯 8. Nguyện vọng xét tuyển");
        btnTaiKhoan = createButton("🔐 9. Quản lý Tài Khoản");
        btnChungChi = createButton("📜 10. Chứng Chỉ");
        btnGiaiThuong = createButton("🏆 11. Giải Thưởng");
        
        JButton[] menus = {btnThiSinh, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemThi, btnDiemCong, btnNguyenVong, btnTaiKhoan, btnChungChi, btnGiaiThuong};
        
        String[] tables = {
            "xt_thisinhxettuyen25", "xt_nganh", "xt_tohop_monthi", "xt_nganh_tohop", "xt_bangquydoi", 
            "xt_diemthixettuyen", "xt_diemcongxettuyen", "xt_nguyenvongxettuyen", "xt_taikhoan", "xt_chungchi", "xt_giathuong"
        };

        for (int i = 0; i < menus.length; i++) {
            if (BUS.phanQuyenBUS.checkQuyenXem(tables[i])) {
                add(menus[i]);
                add(Box.createVerticalStrut(8)); 
            }
        }

        // ================= NÚT ĐĂNG XUẤT =================
        add(Box.createVerticalGlue()); 
        btnDangXuat = createButton("🚪 Đăng xuất");
        btnDangXuat.setForeground(new Color(255, 107, 107)); // Đỏ pastel cho đỡ chói
        
        // Ghi đè hiệu ứng Hover riêng cho nút Đăng xuất (Hover vào là đỏ rực)
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangXuat.setBackground(new Color(255, 107, 107)); 
                btnDangXuat.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangXuat.setBackground(new Color(33, 37, 41)); 
                btnDangXuat.setForeground(new Color(255, 107, 107));
            }
        });
        
        add(btnDangXuat);
    }

    // 👉 Hàm tạo button (Đã tinh chỉnh lại Padding để icon và chữ cân đối)
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42)); 
        btn.setPreferredSize(new Dimension(230, 42)); 
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT); 
        
        btn.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0)); 

        btn.setFocusPainted(false);
        btn.setBackground(new Color(33, 37, 41)); 
        btn.setForeground(new Color(210, 210, 210));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Hạ size chữ 1 tí xíu nhường chỗ cho icon
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(73, 80, 87)); // Màu xám sáng hơn 1 chút khi lướt qua
                btn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(33, 37, 41)); 
                btn.setForeground(new Color(210, 210, 210));
            }
        });

        return btn;
    }
}