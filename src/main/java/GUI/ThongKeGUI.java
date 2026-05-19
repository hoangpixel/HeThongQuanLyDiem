package GUI;

import BUS.*;
import Entity.thiSinhXetTuyenETT;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableCellRenderer;

public class ThongKeGUI extends JPanel {

    private final Color COLOR_BG = new Color(242, 245, 249);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_TEXT = new Color(44, 62, 80);
    private final Color[] CHART_COLORS = {
        new Color(52, 152, 219), new Color(46, 204, 113), new Color(241, 196, 15),
        new Color(231, 76, 60), new Color(155, 89, 182), new Color(52, 73, 94),
        new Color(26, 188, 156), new Color(230, 126, 34)
    };

    private JPanel mainContent;
    
    // BUS instances
    private thiSinhXetTuyenBUS tsBus = new thiSinhXetTuyenBUS();
    private nguyenVongXetTuyenBUS nvBus = new nguyenVongXetTuyenBUS();
    private chungChiBUS ccBus = new chungChiBUS();
    private giaiThuongBUS gtBus = new giaiThuongBUS();
    private taiKhoanBUS tkBus = new taiKhoanBUS();

    public ThongKeGUI() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        // --- HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        JLabel lblTitle = new JLabel("HỆ THỐNG BÁO CÁO TỔNG QUAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_TEXT);
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        
        JButton btnRefresh = new JButton("Làm mới báo cáo");
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBackground(new Color(41, 128, 185));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setPreferredSize(new Dimension(160, 40));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> initDashboard());
        pnlHeader.add(btnRefresh, BorderLayout.EAST);
        
        add(pnlHeader, BorderLayout.NORTH);

        // --- SCROLLABLE CONTENT ---
        mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(COLOR_BG);
        mainContent.setBorder(new EmptyBorder(25, 40, 30, 40));

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        add(scrollPane, BorderLayout.CENTER);

        initDashboard();
    }

    private void initDashboard() {
        mainContent.removeAll();

        // 1. Summary Cards
        JPanel pnlSummary = new JPanel(new GridLayout(1, 4, 25, 0));
        pnlSummary.setOpaque(false);
        pnlSummary.setPreferredSize(new Dimension(0, 110)); 
        pnlSummary.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        
        pnlSummary.add(createMiniCard("THÍ SINH", tsBus.layDanhSach().size(), "Hồ sơ", new Color(52, 152, 219)));
        
        // Tính số lượng nguyện vọng đăng ký thực tế (không trùng lặp cccd và thứ tự nguyện vọng)
        int soLuongNVDangKy = (int) nvBus.layDanhSach().stream()
                .filter(nv -> nv != null && nv.getNnCccd() != null)
                .map(nv -> nv.getNnCccd() + "_" + nv.getNvTt())
                .distinct()
                .count();
        pnlSummary.add(createMiniCard("NGUYỆN VỌNG", soLuongNVDangKy, "Đăng ký", new Color(46, 204, 113)));
        pnlSummary.add(createMiniCard("CHỨNG CHỈ", ccBus.layDanhSach().size(), "Văn bằng", new Color(241, 196, 15)));
        pnlSummary.add(createMiniCard("GIẢI THƯỞNG", gtBus.layDanhSach().size(), "Giải thưởng", new Color(231, 76, 60)));
        
        mainContent.add(pnlSummary);
        mainContent.add(Box.createVerticalStrut(25));

        // 2. Line Chart: Trend
        Map<String, Integer> birthYearMap = new TreeMap<>();
        for (thiSinhXetTuyenETT ts : tsBus.layDanhSach()) {
            if (ts.getNgaySinh() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts.getNgaySinh());
                birthYearMap.put(String.valueOf(cal.get(Calendar.YEAR)), birthYearMap.getOrDefault(String.valueOf(cal.get(Calendar.YEAR)), 0) + 1);
            }
        }
        mainContent.add(createChartPanel("Xu hướng Năm sinh của Thí sinh (Biểu đồ Đường)", birthYearMap, "line", 380));
        mainContent.add(Box.createVerticalStrut(25));

        // 3. Bar & Pie Row
        JPanel pnlRow2 = new JPanel(new GridLayout(1, 2, 25, 0));
        pnlRow2.setOpaque(false);
        pnlRow2.setPreferredSize(new Dimension(0, 400));
        pnlRow2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        
        pnlRow2.add(createChartPanel("Thí sinh theo Khu vực (Biểu đồ Cột)", tsBus.thongKeTheoKhuVuc(), "bar", 400));
        pnlRow2.add(createChartPanel("Thí sinh theo Đối tượng (Biểu đồ Tròn)", tsBus.thongKeTheoDoiTuong(), "pie", 400));
        
        mainContent.add(pnlRow2);
        mainContent.add(Box.createVerticalStrut(25));

        // 4. Accounts & Certificates
        JPanel pnlRow3 = new JPanel(new GridLayout(1, 2, 25, 0));
        pnlRow3.setOpaque(false);
        pnlRow3.setPreferredSize(new Dimension(0, 400));
        pnlRow3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        Map<String, Integer> mapTK = tkBus.layDanhSach().stream()
                .collect(Collectors.groupingBy(tk -> tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa", Collectors.summingInt(e -> 1)));
        pnlRow3.add(createChartPanel("Trạng thái Tài khoản (Biểu đồ Tròn)", mapTK, "pie", 400));

        Map<String, Integer> mapCC = ccBus.layDanhSach().stream()
                .collect(Collectors.groupingBy(cc -> cc.getLoaiChungChi() == null || cc.getLoaiChungChi().isEmpty() ? "Khác" : cc.getLoaiChungChi(), Collectors.summingInt(e -> 1)));
        pnlRow3.add(createChartPanel("Phân loại Chứng chỉ (Biểu đồ Ngang)", mapCC, "hbar", 400));

        mainContent.add(pnlRow3);
        mainContent.add(Box.createVerticalStrut(25));

        // ====================================================================
        // 🚀 THÊM BẢNG 1: THỐNG KÊ SỐ LƯỢNG TRÚNG TUYỂN THEO TỪNG PHƯƠNG THỨC
        // ====================================================================
        mainContent.add(createThongKePhuongThucTable());
        mainContent.add(Box.createVerticalStrut(25));

        // ====================================================================
        // 🚀 THÊM BẢNG 2: DANH SÁCH CHI TIẾT THÍ SINH ĐẬU (DÀI HƠN)
        // ====================================================================
        mainContent.add(createChiTietTrungTuyenTable());
        mainContent.add(Box.createVerticalStrut(40)); // Padding đít cho dễ cuộn

        mainContent.revalidate();
        mainContent.repaint();
    }
    
//    private void initDashboard() {
//        mainContent.removeAll();
//
//        // 1. Summary Cards
//        JPanel pnlSummary = new JPanel(new GridLayout(1, 4, 25, 0));
//        pnlSummary.setOpaque(false);
//        pnlSummary.setPreferredSize(new Dimension(0, 110)); 
//        pnlSummary.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
//        
//        pnlSummary.add(createMiniCard("THÍ SINH", tsBus.layDanhSach().size(), "Hồ sơ", new Color(52, 152, 219)));
//        pnlSummary.add(createMiniCard("NGUYỆN VỌNG", nvBus.layDanhSach().size(), "Lượt chọn", new Color(46, 204, 113)));
//        pnlSummary.add(createMiniCard("CHỨNG CHỈ", ccBus.layDanhSach().size(), "Văn bằng", new Color(241, 196, 15)));
//        pnlSummary.add(createMiniCard("GIẢI THƯỞNG", gtBus.layDanhSach().size(), "Giải thưởng", new Color(231, 76, 60)));
//        
//        mainContent.add(pnlSummary);
//        mainContent.add(Box.createVerticalStrut(25));
//
//        // 2. Line Chart: Trend
//        Map<String, Integer> birthYearMap = new TreeMap<>();
//        for (thiSinhXetTuyenETT ts : tsBus.layDanhSach()) {
//            if (ts.getNgaySinh() != null) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(ts.getNgaySinh());
//                birthYearMap.put(String.valueOf(cal.get(Calendar.YEAR)), birthYearMap.getOrDefault(String.valueOf(cal.get(Calendar.YEAR)), 0) + 1);
//            }
//        }
//        mainContent.add(createChartPanel("Xu hướng Năm sinh của Thí sinh (Biểu đồ Đường)", birthYearMap, "line", 380));
//        mainContent.add(Box.createVerticalStrut(25));
//
//        // 3. Bar & Pie Row
//        JPanel pnlRow2 = new JPanel(new GridLayout(1, 2, 25, 0));
//        pnlRow2.setOpaque(false);
//        pnlRow2.setPreferredSize(new Dimension(0, 400));
//        pnlRow2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
//        
//        pnlRow2.add(createChartPanel("Thí sinh theo Khu vực (Biểu đồ Cột)", tsBus.thongKeTheoKhuVuc(), "bar", 400));
//        pnlRow2.add(createChartPanel("Thí sinh theo Đối tượng (Biểu đồ Tròn)", tsBus.thongKeTheoDoiTuong(), "pie", 400));
//        
//        mainContent.add(pnlRow2);
//        mainContent.add(Box.createVerticalStrut(25));
//
//        // 4. Accounts & Certificates
//        JPanel pnlRow3 = new JPanel(new GridLayout(1, 2, 25, 0));
//        pnlRow3.setOpaque(false);
//        pnlRow3.setPreferredSize(new Dimension(0, 400));
//        pnlRow3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
//
//        Map<String, Integer> mapTK = tkBus.layDanhSach().stream()
//                .collect(Collectors.groupingBy(tk -> tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa", Collectors.summingInt(e -> 1)));
//        pnlRow3.add(createChartPanel("Trạng thái Tài khoản (Biểu đồ Tròn)", mapTK, "pie", 400));
//
//        Map<String, Integer> mapCC = ccBus.layDanhSach().stream()
//                .collect(Collectors.groupingBy(cc -> cc.getLoaiChungChi() == null || cc.getLoaiChungChi().isEmpty() ? "Khác" : cc.getLoaiChungChi(), Collectors.summingInt(e -> 1)));
//        pnlRow3.add(createChartPanel("Phân loại Chứng chỉ (Biểu đồ Ngang)", mapCC, "hbar", 400));
//
//        mainContent.add(pnlRow3);
//
//        mainContent.revalidate();
//        mainContent.repaint();
//    }

    private JPanel createMiniCard(String title, int value, String unit, Color color) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.setColor(color);
                g2.fillRect(0, 0, 6, getHeight());
            }
        };
        card.setBackground(COLOR_CARD);
        card.setBorder(new EmptyBorder(12, 20, 12, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(new Color(127, 140, 141));
        
        JLabel lblValue = new JLabel(String.format("%,d", value));
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(COLOR_TEXT);
        
        JLabel lblUnit = new JLabel(unit);
        lblUnit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUnit.setForeground(Color.GRAY);

        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setOpaque(false);
        pnl.add(lblTitle);
        pnl.add(Box.createVerticalStrut(3));
        pnl.add(lblValue);
        pnl.add(lblUnit);
        
        card.add(pnl, BorderLayout.CENTER);
        return card;
    }

    private JPanel createChartPanel(String title, Map<String, Integer> data, String type, int height) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setBackground(COLOR_CARD);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(0, height));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(COLOR_TEXT);
        lblTitle.setBorder(new EmptyBorder(0, 5, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel chart;
        switch (type) {
            case "bar": chart = new BarChart(data); break;
            case "hbar": chart = new HorizontalBarChart(data); break;
            case "pie": chart = new PieChart(data); break;
            case "line": chart = new LineChart(data); break;
            default: chart = new JPanel();
        }
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    private class BarChart extends JPanel {
        private Map<String, Integer> data;
        public BarChart(Map<String, Integer> data) { this.data = data; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), m = 40, cW = w - m * 2, cH = h - m * 2;
            int n = data.size(), barW = Math.min(45, cW / (n * 2)), gap = (cW - barW * n) / (n + 1);
            int max = data.values().stream().max(Integer::compare).orElse(1);
            int i = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                int val = entry.getValue(), bH = (int) ((double) val / max * cH);
                int x = m + gap + i * (barW + gap), y = h - m - bH;
                g2.setColor(CHART_COLORS[i % CHART_COLORS.length]);
                g2.fillRoundRect(x, y, barW, bH, 10, 10);
                g2.setColor(COLOR_TEXT);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.drawString(String.format("%,d", val), x + (barW - g2.getFontMetrics().stringWidth(String.format("%,d", val))) / 2, y - 8);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String label = entry.getKey();
                if (label.length() > 10) label = label.substring(0, 8) + "..";
                g2.drawString(label, x + (barW - g2.getFontMetrics().stringWidth(label)) / 2, h - m + 22);
                i++;
            }
        }
    }

    private class HorizontalBarChart extends JPanel {
        private Map<String, Integer> data;
        public HorizontalBarChart(Map<String, Integer> data) { this.data = data; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth(), h = getHeight();
            int leftM = 190, rightM = 60, topM = 10, bottomM = 10; // Tăng leftM lên 190
            int cW = w - leftM - rightM, cH = h - topM - bottomM;

            java.util.List<Map.Entry<String, Integer>> list = data.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10).collect(Collectors.toList());

            int n = list.size();
            int barH = Math.min(25, cH / (n > 0 ? n : 1) - 10);
            int gap = (cH - barH * n) / (n + 1);
            int max = list.stream().map(Map.Entry::getValue).max(Integer::compare).orElse(1);

            int i = 0;
            for (Map.Entry<String, Integer> entry : list) {
                int val = entry.getValue(), bW = (int) ((double) val / max * cW);
                int x = leftM, y = topM + gap + i * (barH + gap);

                g2.setColor(CHART_COLORS[i % CHART_COLORS.length]);
                g2.fillRoundRect(x, y, bW, barH, 5, 5);

                g2.setColor(COLOR_TEXT);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String label = entry.getKey(); // HIỂN THỊ ĐẦY ĐỦ TÊN
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(label, leftM - fm.stringWidth(label) - 10, y + barH/2 + 5);

                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                g2.drawString(String.format("%,d", val), x + bW + 5, y + barH/2 + 5);
                i++;
            }
        }
    }

    private class PieChart extends JPanel {
        private Map<String, Integer> data;
        public PieChart(Map<String, Integer> data) { this.data = data; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int size = Math.min(h - 60, (int)(w * 0.45)), x = 20, y = (h - size) / 2;
            double total = data.values().stream().mapToInt(Integer::intValue).sum(), startAngle = 90;
            int i = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                double angle = (entry.getValue() / total) * 360;
                g2.setColor(CHART_COLORS[i % CHART_COLORS.length]);
                g2.fill(new Arc2D.Double(x, y, size, size, startAngle, angle, Arc2D.PIE));
                g2.setColor(Color.WHITE); g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new Arc2D.Double(x, y, size, size, startAngle, angle, Arc2D.PIE));
                startAngle += angle; i++;
            }
            i = 0;
            int legendX = x + size + 35, legendY = (h - (data.size() * 22)) / 2;
            if (legendY < 10) legendY = 10;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                g2.setColor(CHART_COLORS[i % CHART_COLORS.length]); g2.fillRoundRect(legendX, legendY, 13, 13, 4, 4);
                g2.setColor(COLOR_TEXT); g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String text = entry.getKey() + " (" + String.format("%.1f%%", (entry.getValue() / total * 100)) + ")";
                g2.drawString(text, legendX + 22, legendY + 11);
                legendY += 22; i++;
            }
        }
    }

    private class LineChart extends JPanel {
        private Map<String, Integer> data;
        public LineChart(Map<String, Integer> data) { this.data = data; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), m = 50, cW = w - m * 2, cH = h - m * 2;
            int n = data.size(), gap = cW / (n > 1 ? n - 1 : 1);
            int max = data.values().stream().max(Integer::compare).orElse(1);
            g2.setColor(new Color(235, 235, 235));
            g2.drawLine(m, h - m, w - m, h - m);
            g2.drawLine(m, m, m, h - m);
            int i = 0, prevX = -1, prevY = -1;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                int val = entry.getValue(), x = m + i * gap, y = h - m - (int) ((double) val / max * cH);
                g2.setColor(CHART_COLORS[0]); g2.fillOval(x - 5, y - 5, 10, 10);
                if (prevX != -1) { g2.setStroke(new BasicStroke(3f)); g2.drawLine(prevX, prevY, x, y); }
                g2.setColor(COLOR_TEXT); g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.drawString(entry.getKey(), x - 12, h - m + 22);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                g2.drawString(String.format("%,d", val), x - 12, y - 12);
                prevX = x; prevY = y; i++;
            }
        }
    }
    
// ========================================================================
    // 🚀 BẢNG 1: THỐNG KÊ PHƯƠNG THỨC (ĐÃ ÁP DỤNG KHUÔN PHÂN TRANG + TÌM KIẾM)
    // ========================================================================
    private JPanel createThongKePhuongThucTable() {
        String[] cols = {"Mã Ngành", "Tên Ngành", "Xét Tuyển Thẳng", "Xét THPT", "Đánh giá V-SAT", "ĐGNL HCM", "Tổng Đậu"};
        java.util.List<Object[]> dataList = new ArrayList<>();

        java.util.HashMap<String, java.util.HashMap<String, Integer>> thongKeMap = new java.util.HashMap<>();
        for (Entity.nguyenVongXetTuyenETT nv : nvBus.layDanhSach()) {
            if ("Đã đậu".equals(nv.getNvKetQua())) {
                String nganh = nv.getNvMaNganh();
                String pt = nv.getTtPhuongThuc();
                thongKeMap.putIfAbsent(nganh, new java.util.HashMap<>());
                thongKeMap.get(nganh).put(pt, thongKeMap.get(nganh).getOrDefault(pt, 0) + 1);
            }
        }
        
        BUS.nganhBUS nBus = new BUS.nganhBUS();
        java.util.HashMap<String, String> mapTenNganh = new java.util.HashMap<>();
        for (Entity.nganhETT ng : nBus.layDanhSach()) { mapTenNganh.put(ng.getManganh(), ng.getTennganh()); }

        for (String maNganh : thongKeMap.keySet()) {
            java.util.HashMap<String, Integer> ptMap = thongKeMap.get(maNganh);
            int slXTT = ptMap.getOrDefault("Xét tuyển thẳng", 0);
            int slTHPT = ptMap.getOrDefault("Xét THPT", 0);
            int slVSAT = ptMap.getOrDefault("Đánh giá V-SAT", 0);
            int slDGNL = ptMap.getOrDefault("ĐGNL HCM", 0);
            int tong = slXTT + slTHPT + slVSAT + slDGNL;
            dataList.add(new Object[]{maNganh, mapTenNganh.getOrDefault(maNganh, "Chưa xác định"), slXTT, slTHPT, slVSAT, slDGNL, tong});
        }

        // 🚀 Gọi Khuôn thần thánh ra xài
        return new PaginatedTablePanel("Thống kê Số lượng Trúng tuyển theo Phương thức", cols, dataList);
    }

    // ========================================================================
    // 🚀 BẢNG 2: CHI TIẾT TRÚNG TUYỂN (ĐÃ ÁP DỤNG KHUÔN PHÂN TRANG + TÌM KIẾM)
    // ========================================================================
    private JPanel createChiTietTrungTuyenTable() {
        String[] cols = {"Mã Ngành", "CCCD", "Thứ Tự NV", "Điểm Xét Tuyển", "Phương Thức", "Tổ Hợp"};
        java.util.List<Object[]> dataList = new ArrayList<>();

        ArrayList<Entity.nguyenVongXetTuyenETT> dsDau = new ArrayList<>();
        for (Entity.nguyenVongXetTuyenETT nv : nvBus.layDanhSach()) {
            if ("Đã đậu".equals(nv.getNvKetQua())) dsDau.add(nv);
        }
        
        dsDau.sort((nv1, nv2) -> {
            int nganhCompare = nv1.getNvMaNganh().compareTo(nv2.getNvMaNganh());
            if (nganhCompare != 0) return nganhCompare;
            return nv1.getNnCccd().compareTo(nv2.getNnCccd()); 
        });

        for (Entity.nguyenVongXetTuyenETT nv : dsDau) {
            dataList.add(new Object[]{nv.getNvMaNganh(), nv.getNnCccd(), nv.getNvTt(), nv.getDiemXetTuyen(), nv.getTtPhuongThuc(), nv.getTtThm()});
        }

        // 🚀 Gọi Khuôn thần thánh ra xài
        return new PaginatedTablePanel("Danh sách Chi tiết Thí sinh Trúng tuyển", cols, dataList);
    }

    // Vẫn giữ lại cái hàm styleTable nha ông
    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);

        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(100, 35));
        header.setBackground(new Color(41, 128, 185)); 
        header.setForeground(Color.WHITE);
        ((javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                return c;
            }
        });
    }
    
    // ========================================================================
    // 🚀 KHUÔN ĐÚC BẢNG NĂNG ĐỘNG (CÓ SEARCH ĐỘNG + PHÂN TRANG)
    // ========================================================================
// ========================================================================
    // 🚀 KHUÔN ĐÚC BẢNG NĂNG ĐỘNG (ĐÃ THÊM VIỀN BO GÓC GIỐNG CHART)
    // ========================================================================
    private class PaginatedTablePanel extends JPanel {
        private JTable table;
        private javax.swing.table.DefaultTableModel model;
        private java.util.List<Object[]> allData;
        private java.util.List<Object[]> filteredData;
        private int currentPage = 1;
        private int rowsPerPage = 15; // Mỗi trang 15 dòng
        private JLabel lblPage;
        private JButton btnPrev, btnNext;
        private JTextField txtSearch;

        public PaginatedTablePanel(String title, String[] columns, java.util.List<Object[]> data) {
            this.allData = data;
            this.filteredData = new ArrayList<>(data);

            setLayout(new BorderLayout(0, 10));
            
            // 🚀 TẮT OPAQUE ĐỂ VẼ ĐƯỢC BO GÓC KHÔNG BỊ LẸM TRẮNG
            setOpaque(false);
            setBackground(COLOR_CARD);
            
            // 🚀 Chỉnh lại lề cho y chang các biểu đồ khác (20px)
            setBorder(new EmptyBorder(20, 20, 20, 20)); 
            setPreferredSize(new Dimension(0, 450)); 
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));

            // --- HEADER: TITLE + THANH TÌM KIẾM ---
            JPanel pnlTop = new JPanel(new BorderLayout());
            pnlTop.setOpaque(false);
            
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
            lblTitle.setForeground(COLOR_TEXT);
            pnlTop.add(lblTitle, BorderLayout.WEST);

            JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            pnlSearch.setOpaque(false);
            JLabel lblLoc = new JLabel("Lọc nhanh:");
            lblLoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            txtSearch = new JTextField(20);
            txtSearch.setPreferredSize(new Dimension(200, 30));
            txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    updateFilter();
                }
            });
            pnlSearch.add(lblLoc);
            pnlSearch.add(txtSearch);
            pnlTop.add(pnlSearch, BorderLayout.EAST);

            add(pnlTop, BorderLayout.NORTH);

            // --- TABLE ---
            model = new javax.swing.table.DefaultTableModel(columns, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            table = new JTable(model);
            styleTable(table); 
            
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230))); // Thêm viền nhẹ cho bảng bên trong
            add(scroll, BorderLayout.CENTER);

            // --- BỘ ĐIỀU KHIỂN PHÂN TRANG ---
            JPanel pnlBot = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
            pnlBot.setOpaque(false);
            
            btnPrev = new JButton("<< Trước");
            btnNext = new JButton("Sau >>");
            lblPage = new JLabel("1/1");
            lblPage.setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            Font btnFont = new Font("Segoe UI", Font.PLAIN, 12);
            btnPrev.setFont(btnFont); btnNext.setFont(btnFont);
            btnPrev.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnNext.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnPrev.addActionListener(e -> { if(currentPage > 1) { currentPage--; renderPage(); }});
            btnNext.addActionListener(e -> { if(currentPage < getTotalPages()) { currentPage++; renderPage(); }});
            
            pnlBot.add(btnPrev); 
            pnlBot.add(lblPage); 
            pnlBot.add(btnNext);
            add(pnlBot, BorderLayout.SOUTH);

            renderPage(); 
        }

        // 🚀 THÊM HÀM VẼ BO GÓC BẰNG GRAPHICS2D (Tương tự CreateChartPanel)
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Bo góc 20px
            super.paintComponent(g);
        }

        private void updateFilter() {
            String text = txtSearch.getText().trim().toLowerCase();
            filteredData = allData.stream().filter(row -> {
                for(Object cell : row) {
                    if(cell != null && cell.toString().toLowerCase().contains(text)) return true;
                }
                return false;
            }).collect(Collectors.toList());
            currentPage = 1; 
            renderPage();
        }

        private int getTotalPages() {
            int total = (int) Math.ceil((double)filteredData.size() / rowsPerPage);
            return total == 0 ? 1 : total;
        }

        private void renderPage() {
            model.setRowCount(0); 
            int start = (currentPage - 1) * rowsPerPage;
            int end = Math.min(start + rowsPerPage, filteredData.size());
            
            for(int i = start; i < end; i++) {
                model.addRow(filteredData.get(i));
            }
            
            lblPage.setText("Trang " + currentPage + " / " + getTotalPages());
            btnPrev.setEnabled(currentPage > 1);
            btnNext.setEnabled(currentPage < getTotalPages());
        }
    }
}
