package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class navbarGUI extends JPanel {

    public JButton btnThiSinh, btnDiemThi, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemCong, btnNguyenVong, btnDangXuat;

    public navbarGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 800));
        setBackground(new Color(33, 37, 41)); // Màu nền tối sâu hơn (chuẩn Dark Theme)
        setBorder(new EmptyBorder(25, 15, 25, 15)); // Tăng lề xung quanh để menu không bị sát viền

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
        add(Box.createVerticalStrut(40)); // Khoảng cách siêu rộng từ Title xuống Menu

        // ================= MENU BUTTONS =================
        btnThiSinh = createButton("1. Quản lý Thí sinh");
        btnNganh = createButton("2. Quản lý Ngành học");
        btnToHopMon = createButton("3. Quản lý Tổ hợp môn");
        btnNganhToHop = createButton("4. Tổ hợp - Ngành");
        btnBangQuyDoi = createButton("5. Bảng quy đổi V-SAT");
        btnDiemThi = createButton("6. Quản lý Điểm thi");
        btnDiemCong = createButton("7. Điểm cộng & Ưu tiên");
        btnNguyenVong = createButton("8. Nguyện vọng xét tuyển");
        
        // Đưa các nút vào mảng để dùng vòng lặp gắn khoảng cách (Strut) cho đều
        JButton[] menus = {btnThiSinh, btnNganh, btnToHopMon, btnNganhToHop, btnBangQuyDoi, btnDiemThi, btnDiemCong, btnNguyenVong};
        for (JButton btn : menus) {
            add(btn);
            add(Box.createVerticalStrut(10)); // Khoảng trống 10px giữa mỗi nút cho thoáng
        }

        // ================= NÚT ĐĂNG XUẤT =================
        add(Box.createVerticalGlue()); // Lò xo đẩy nút đăng xuất xuống sát đáy màn hình
        btnDangXuat = createButton("Đăng xuất");
        btnDangXuat.setForeground(new Color(231, 76, 60)); // Chữ màu đỏ
        add(btnDangXuat);
    }

    // 👉 Hàm tạo button chuẩn Dashboard
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        
        // Ép nút giãn dài ra hết cỡ (Full width) và có chiều cao cố định là 45px
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); 
        btn.setPreferredSize(new Dimension(220, 45)); 
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT); 
        
        // Thụt đầu dòng cho chữ bên trong nút (Padding left)
        btn.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); 

        btn.setFocusPainted(false);
        btn.setBackground(new Color(33, 37, 41)); // Trùng màu nền Navbar để tàng hình khi chưa hover
        btn.setForeground(new Color(220, 220, 220));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng Hover làm sáng nguyên 1 khối màu xám lên
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 58, 64)); // Màu xám nhạt nổi lên
                btn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(33, 37, 41)); // Trả về tàng hình
                btn.setForeground(new Color(220, 220, 220));
            }
        });

        return btn;
    }
}