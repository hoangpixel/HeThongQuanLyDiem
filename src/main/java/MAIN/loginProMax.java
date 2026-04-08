package MAIN;

import GUI.contentGUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class loginProMax extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnThoat;

public loginProMax() {

        setTitle("Hệ Thống Quản Lý Điểm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 1. Tăng size cửa sổ lên một chút xíu để có không gian cho viền thở
        setSize(850, 540); 
        setLocationRelativeTo(null);
        setResizable(false);

        // MAIN
        // 2. Tăng số 40 lên 60 để khoảng cách giữa (Ảnh) và (Form) xa nhau ra
        JPanel main = new JPanel(new BorderLayout(60, 0)); 
        
        // 3. Chỉnh viền ngoài cùng đều đặn: Trên 30, Trái 45, Dưới 30, Phải 45
        main.setBorder(new EmptyBorder(30, 45, 30, 45)); 
        main.setBackground(new Color(240, 242, 245));

        // ================= LEFT (IMAGE) =================
        JPanel left = new JPanel(new GridBagLayout());
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(320, 0));

        JLabel lblImg;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/IMG/dhSGU.png"));
            Image img = icon.getImage().getScaledInstance(300, 420, Image.SCALE_SMOOTH);
            lblImg = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            lblImg = new JLabel("IMAGE", SwingConstants.CENTER);
        }

        left.add(lblImg);

        // ================= RIGHT (CARD LOGIN) =================
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);

        JPanel shadow = createShadowPanel();
        JPanel card = createCardPanel();

        shadow.setLayout(new BorderLayout());
        shadow.add(card, BorderLayout.CENTER);
        
        // Đã xóa dòng right.setBorder(0, 20, 0, 0) ở đây để form không bị lệch phải nữa
        right.add(shadow);

        // ================= ADD =================
        main.add(left, BorderLayout.WEST);
        main.add(right, BorderLayout.CENTER);

        add(main);

        // ================= EVENT =================
        btnDangNhap.addActionListener(e -> login());
        txtMatKhau.addActionListener(e -> login());
        btnThoat.addActionListener(e -> System.exit(0));
    }

    // ================= CARD =================
    private JPanel createCardPanel() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 35, 30, 35));
        card.setPreferredSize(new Dimension(360, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1;

        // TITLE
        JLabel title = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(52, 152, 219));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        card.add(title, gbc);

        // USER
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        card.add(new JLabel("Tài khoản"), gbc);

        gbc.gridy++;
        txtTaiKhoan = new JTextField();
        styleInput(txtTaiKhoan);
        card.add(txtTaiKhoan, gbc);

        // PASS
        gbc.gridy++;
        card.add(new JLabel("Mật khẩu"), gbc);

        gbc.gridy++;
        txtMatKhau = new JPasswordField();
        styleInput(txtMatKhau);
        card.add(txtMatKhau, gbc);

        // BUTTON
        gbc.gridy++;
        gbc.insets = new Insets(25, 0, 10, 0);

        btnDangNhap = new JButton("Đăng nhập");
        styleButton(btnDangNhap, new Color(52, 152, 219));

        btnThoat = new JButton("Thoát");
        styleButton(btnThoat, new Color(231, 76, 60));

        JPanel pnlBtn = new JPanel(new GridLayout(1, 2, 12, 0));
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnDangNhap);
        pnlBtn.add(btnThoat);

        card.add(pnlBtn, gbc);

        return card;
    }

    // ================= SHADOW =================
private JPanel createShadowPanel() {
    return new JPanel() {
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Shadow nhiều lớp cho mềm
            for (int i = 0; i < 8; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i * 2));
                g2.fillRoundRect(10 - i, 10 - i,
                        getWidth() - (20 - i * 2),
                        getHeight() - (20 - i * 2),
                        25, 25);
            }

            super.paintComponent(g);
        }

        public boolean isOpaque() {
            return false;
        }
    };
}

    // ================= STYLE =================
private void styleInput(JTextField txt) {
    txt.setPreferredSize(new Dimension(200, 42));
    txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            new EmptyBorder(5, 12, 5, 12)
    ));

    // Focus effect
    txt.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent e) {
            txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                    new EmptyBorder(5, 12, 5, 12)
            ));
        }

        public void focusLost(java.awt.event.FocusEvent e) {
            txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    new EmptyBorder(5, 12, 5, 12)
            ));
        }
    });
}

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 42));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
    }

    // ================= LOGIN =================
    private void login() {
        String user = txtTaiKhoan.getText();
        String pass = new String(txtMatKhau.getPassword());

        if (user.equals("admin") && pass.equals("123")) {
            this.dispose();
            openMain();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }
    }

    private void openMain() {
        SwingUtilities.invokeLater(() -> new contentGUI().setVisible(true));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {}
        new loginProMax().setVisible(true);
    }
}