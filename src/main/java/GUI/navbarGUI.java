package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class navbarGUI extends JPanel {

    public JButton btnThiSinh, btnDiemThi, btnPhanQuyen, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemCong, btnNguyenVong, btnDangXuat, btnTaiKhoan, btnChungChi, btnGiaiThuong, btnThongKe;

    private final Color COLOR_BG = new Color(15, 124, 133); 
    private final Color COLOR_HOVER = new Color(25, 149, 159); 
    // 👉 1. THÊM MÀU ACTIVE (Sẫm hơn màu nền một chút để tạo độ sâu)
    private final Color COLOR_ACTIVE = new Color(9, 90, 100); 
    private final Color COLOR_TEXT_NORMAL = new Color(220, 242, 242); 
    private final Color COLOR_TEXT_HOVER = Color.WHITE;
    private final Color COLOR_DANGER = new Color(255, 128, 128); 
    
    private Font baseFont;
    
    // 👉 2. BIẾN LƯU TRỮ NÚT ĐANG ĐƯỢC CHỌN
    private JButton currentActiveBtn = null;

    public navbarGUI() {
        baseFont = UIManager.getFont("defaultFont");
        if (baseFont == null) {
            baseFont = new Font("SansSerif", Font.PLAIN, 14);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(270, 800)); 
        setBackground(COLOR_BG); 
        setBorder(new EmptyBorder(30, 20, 30, 20)); 

// ================= TITLE =================
        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ");
        title.setForeground(Color.WHITE);
        title.setFont(baseFont.deriveFont(Font.BOLD, 19f)); 
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subTitle = new JLabel("Tuyển Sinh 2026");
        subTitle.setForeground(new Color(168, 230, 207)); 
        subTitle.setFont(baseFont.deriveFont(Font.BOLD, 15f)); 
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(5)); // Khoảng cách giữa Hệ thống quản lý và Tuyển sinh
        add(subTitle);
        // 👉 GIẢM KHOẢNG TRỐNG DƯỚI CHỮ "TUYỂN SINH 2025" (từ 30 xuống 15)
        add(Box.createVerticalStrut(15)); 

        // ================= HIỂN THỊ TÊN NGƯỜI DÙNG =================
        String tenNguoiDung = "Khách";
        if (BUS.taiKhoanBUS.taiKhoanHienTai != null) {
            tenNguoiDung = BUS.taiKhoanBUS.taiKhoanHienTai.getHoTen();
        }

        JLabel lblWelcome = new JLabel("Xin chào,");
        lblWelcome.setForeground(COLOR_TEXT_NORMAL);
        lblWelcome.setFont(baseFont.deriveFont(Font.PLAIN, 13f)); 
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(tenNguoiDung);
        lblName.setForeground(new Color(255, 213, 79)); 
        lblName.setFont(baseFont.deriveFont(Font.BOLD, 16f)); 
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(lblWelcome);
        add(Box.createVerticalStrut(5)); // Khoảng cách giữa Xin chào và Tên
        add(lblName);
        // 👉 GIẢM KHOẢNG TRỐNG DƯỚI TÊN ADMIN (từ 25 xuống 15)
        add(Box.createVerticalStrut(15));

        // Đường kẻ ngang mờ mờ
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(220, 1));
        separator.setForeground(new Color(36, 160, 170)); 
        separator.setBackground(COLOR_BG);
        add(separator);
        // 👉 GIẢM KHOẢNG TRỐNG DƯỚI ĐƯỜNG KẺ TRƯỚC KHI VÀO MENU (từ 25 xuống 15)
        add(Box.createVerticalStrut(15));


//        // ================= MENU BUTTONS =================
//        btnThiSinh = createButton("Quản lý Thí sinh");
//        btnNganh = createButton("Quản lý Ngành học");
//        btnToHopMon = createButton("Quản lý Tổ hợp môn");
//        btnNganhToHop = createButton("Tổ hợp - Ngành");
//        btnBangQuyDoi = createButton("Bảng quy đổi V-SAT");
//        btnDiemThi = createButton("Quản lý Điểm thi");
//        btnDiemCong = createButton("Điểm cộng & Ưu tiên");
//        btnNguyenVong = createButton("Nguyện vọng xét tuyển");
//        btnTaiKhoan = createButton("Quản lý Tài khoản");
//        btnChungChi = createButton("Chứng chỉ");
//        btnGiaiThuong = createButton("Giải thưởng");
//        btnPhanQuyen = createButton("Phân quyền");
//        
//        JButton[] menus = {btnThiSinh, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemThi, btnDiemCong, btnNguyenVong, btnTaiKhoan, btnChungChi, btnGiaiThuong, btnPhanQuyen};
//        
//        String[] tables = {
//            "xt_thisinhxettuyen25", "xt_nganh", "xt_tohop_monthi", "xt_nganh_tohop", "xt_bangquydoi", 
//            "xt_diemthixettuyen", "xt_diemcongxettuyen", "xt_nguyenvongxettuyen", "xt_taikhoan", "xt_chungchi", "xt_giathuong", "xt_phanquyen"
//        };

// ================= MENU BUTTONS =================
        
        // --- CỤM 1: NGHIỆP VỤ XÉT TUYỂN ---
        btnThiSinh = createButton("Quản lý Thí sinh");
        btnDiemThi = createButton("Quản lý Điểm thi");
        btnChungChi = createButton("Chứng chỉ");
        btnGiaiThuong = createButton("Giải thưởng");
        btnNguyenVong = createButton("Nguyện vọng xét tuyển");
        
        // --- CỤM 2: DANH MỤC & QUY ĐỊNH ---
        btnNganh = createButton("Quản lý Ngành học");
        btnToHopMon = createButton("Quản lý Tổ hợp môn");
        btnNganhToHop = createButton("Tổ hợp - Ngành");
        btnBangQuyDoi = createButton("Bảng quy đổi");
        btnDiemCong = createButton("Điểm cộng & Ưu tiên");
        
        // --- CỤM 3: HỆ THỐNG ---
        btnTaiKhoan = createButton("Quản lý Tài khoản");
        btnPhanQuyen = createButton("Phân quyền");
        
        // 🚀 ĐÃ SỬA: Sắp xếp lại mảng menus theo đúng thứ tự hiển thị ở trên
        JButton[] menus = {
            btnThiSinh, btnDiemThi, btnChungChi, btnGiaiThuong, btnNguyenVong, 
            btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemCong, 
            btnTaiKhoan, btnPhanQuyen
        };
        
        // 🚀 ĐÃ SỬA: Sắp xếp lại mảng tables khớp 100% với thứ tự của mảng menus (Rất quan trọng để phân quyền không bị lộn)
        String[] tables = {
            "xt_thisinhxettuyen25", "xt_diemthixettuyen", "xt_chungchi", "xt_giathuong", "xt_nguyenvongxettuyen", 
            "xt_nganh", "xt_tohop_monthi", "xt_nganh_tohop", "xt_bangquydoi", "xt_diemcongxettuyen", 
            "xt_taikhoan", "xt_phanquyen"
        };


        boolean isAdmin = false;
        if (BUS.taiKhoanBUS.taiKhoanHienTai != null) {
            isAdmin = BUS.taiKhoanBUS.taiKhoanHienTai.getIdTaiKhoan() == 1;
        }

        for (int i = 0; i < menus.length; i++) {
            if (isAdmin || BUS.phanQuyenBUS.checkQuyenXem(tables[i])) {
                add(menus[i]);
                add(Box.createVerticalStrut(8)); 
            }
        }

        // ================= NÚT ĐĂNG XUẤT =================
        add(Box.createVerticalGlue()); 
        
        // ================= NÚT THỐNG KÊ (Dưới cùng trên Đăng xuất) =================
        btnThongKe = createButton("Thống kê");
        add(btnThongKe);
        add(Box.createVerticalStrut(8));
        
        btnDangXuat = createButton("Đăng xuất");
        btnDangXuat.setForeground(COLOR_DANGER); 
        
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangXuat.setBackground(new Color(235, 87, 87)); 
                btnDangXuat.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangXuat.setBackground(COLOR_BG); 
                btnDangXuat.setForeground(COLOR_DANGER);
            }
        });

        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window mainWindow = SwingUtilities.getWindowAncestor(navbarGUI.this);
                int xacNhan = JOptionPane.showConfirmDialog(
                        mainWindow, 
                        "Bạn có chắc chắn muốn đăng xuất khỏi hệ thống?", 
                        "Xác nhận đăng xuất", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (xacNhan == JOptionPane.YES_OPTION) {
                    if (mainWindow != null) {
                        mainWindow.dispose(); 
                    }
                    // new loginGUI().setVisible(true);
                }
            }
        });
        
        add(btnDangXuat);
    }

    // 👉 3. HÀM XỬ LÝ ĐỔI MÀU NÚT ĐANG ACTIVE
    public void setButtonActive(JButton btn) {
        // Nếu có nút cũ đang active, trả nó về màu bình thường
        if (currentActiveBtn != null && currentActiveBtn != btn) {
            currentActiveBtn.setBackground(COLOR_BG);
            currentActiveBtn.setForeground(COLOR_TEXT_NORMAL);
        }
        
        // Cập nhật nút mới và tô màu sẫm
        currentActiveBtn = btn;
        currentActiveBtn.setBackground(COLOR_ACTIVE);
        currentActiveBtn.setForeground(COLOR_TEXT_HOVER);
    }

private JButton createButton(String text) {
        JButton btn = new JButton(text);
        
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42)); 
        btn.setPreferredSize(new Dimension(240, 42)); 
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 👉 1. ĐỔI THÀNH CENTER ĐỂ CHỮ RA GIỮA
        btn.setHorizontalAlignment(SwingConstants.CENTER); 
        
        // 👉 2. TRẢ LỀ TRÁI VỀ 0 ĐỂ CHỮ KHÔNG BỊ LỆCH
        btn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 

        btn.setFocusPainted(false);
        btn.setBackground(COLOR_BG); 
        btn.setForeground(COLOR_TEXT_NORMAL);
        btn.setFont(baseFont.deriveFont(Font.BOLD, 14f)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 👉 4. CHỈNH SỬA LẠI SỰ KIỆN CHUỘT
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Chỉ đổi màu hover nếu nút này KHÔNG PHẢI là nút đang active
                if (btn != currentActiveBtn) {
                    btn.setBackground(COLOR_HOVER); 
                    btn.setForeground(COLOR_TEXT_HOVER);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Chỉ trả về màu nền cũ nếu nút này KHÔNG PHẢI là nút đang active
                if (btn != currentActiveBtn) {
                    btn.setBackground(COLOR_BG); 
                    btn.setForeground(COLOR_TEXT_NORMAL);
                }
            }
        });

        // Bắt sự kiện Click để tự động set Active
        btn.addActionListener(e -> setButtonActive(btn));

        return btn;
    }
}