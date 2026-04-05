package GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import CONFIG.RoundedButton;

public class BaseTableGUI extends JPanel {

    // Khai báo các components
    public JButton btnThem, btnSua, btnXoa, btnExcel, btnReFresh, btnTimKiem, btnChiTiet;
    public JComboBox<String> cbxTimKiem;
    public JTextField txtTimKiem;
    public JTable table;
    public DefaultTableModel tableModel;
    
    // Components cho phân trang
    public JButton btnFirst, btnPrev, btnNext, btnLast;
    public JLabel lblPageInfo;
    
    // Panels chứa TitledBorder để có thể đổi tên động
    private JPanel pnlActions;
    private TitledBorder actionBorder;

    // ================== BIẾN QUẢN LÝ PHÂN TRANG ==================
    public int currentPage = 1;
    public final int rowsPerPage = 20; // CHỐT HIỂN THỊ 20 DÒNG 1 TRANG
    public int totalPages = 1;
//    private List<Object[]> fullDataList = new ArrayList<>(); // Biến lưu trữ toàn bộ dữ liệu
    public List<Vector> fullDataList = new ArrayList<>();

    public BaseTableGUI() {
        initComponents();
        styleComponents(); 
        setupPaginationEvents(); // Kích hoạt sự kiện bấm nút phân trang
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 15)); 
        setBorder(new EmptyBorder(15, 15, 15, 15)); 
        setBackground(Color.WHITE);

        // ==================== VÙNG TRÊN (TOP): GROUPBOXES ====================
        JPanel pnlTop = new JPanel(new BorderLayout(15, 0)); 
        pnlTop.setBackground(Color.WHITE);

        // 1. GroupBox: Xử lý chức năng (Bên trái)
//        pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
//        pnlActions.setBackground(Color.WHITE);
JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
wrapper.setBackground(Color.WHITE);

pnlActions = new JPanel(new GridLayout(1, 6, 13, 10));
pnlActions.setBackground(Color.WHITE);

wrapper.add(pnlActions);
        
        actionBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Xử lý chức năng của table ...", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        );
        pnlActions.setBorder(BorderFactory.createCompoundBorder(actionBorder, new EmptyBorder(5, 5, 5, 5)));

//        btnThem = new JButton("THÊM");
//        btnSua = new JButton("SỬA");
//        btnXoa = new JButton("XÓA");
//        btnChiTiet = new JButton("CHI TIẾT");
//        btnExcel = new JButton("EXCEL");
//        btnReFresh = new JButton("Refresh");
btnThem = new RoundedButton("THÊM");
btnSua = new RoundedButton("SỬA");
btnXoa = new RoundedButton("XÓA");
btnChiTiet = new RoundedButton("CHI TIẾT");
btnExcel = new RoundedButton("EXCEL");
btnReFresh = new RoundedButton("Refresh");
btnTimKiem = new RoundedButton("TÌM KIẾM");
        
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        
        pnlActions.add(btnThem);
        pnlActions.add(btnSua);
        pnlActions.add(btnXoa);
        pnlActions.add(btnChiTiet);
        pnlActions.add(btnExcel);
        pnlActions.add(btnReFresh);

        // 2. GroupBox: Tìm kiếm (Bên phải)
        // 🔥 FIX: Thay FlowLayout bằng GridBagLayout để tự động căn giữa theo chiều dọc
        JPanel pnlSearch = new JPanel(new GridBagLayout());
        pnlSearch.setBackground(Color.WHITE);
        
        TitledBorder searchBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Tìm kiếm", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        );
        pnlSearch.setBorder(BorderFactory.createCompoundBorder(searchBorder, new EmptyBorder(5, 5, 5, 5)));

        cbxTimKiem = new JComboBox<>(new String[]{"Messi", "Bucac", "Anh 7"});
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("TÌM KIẾM");
        
        // Tạo một panel con (inner panel) dùng FlowLayout để dàn hàng ngang
        JPanel innerSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        innerSearch.setBackground(Color.WHITE);
        
        innerSearch.add(cbxTimKiem);
        innerSearch.add(txtTimKiem);
        innerSearch.add(btnTimKiem);

        // Thêm panel con vào panel Tìm kiếm
        pnlSearch.add(innerSearch);

        pnlTop.add(pnlActions, BorderLayout.CENTER); 
        pnlTop.add(pnlSearch, BorderLayout.EAST);    

        // ==================== VÙNG GIỮA (CENTER): TABLE ====================

        String[] columns = {"Cột 1", "Cột 2", "Cột 3", "Cột 4"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Click vô dòng thêm 1 lần nữa là bỏ chọn dòng đó kaka
        int[] lastSelectedRow = {-1};

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                if (row == lastSelectedRow[0]) {
                    table.clearSelection();
                    table.transferFocus();
                    // 🔥 FIX TRIỆT ĐỂ BORDER
                    table.getSelectionModel().clearSelection();
                    table.getColumnModel().getSelectionModel().clearSelection();

                    lastSelectedRow[0] = -1;
                } else {
                    lastSelectedRow[0] = row;
                }
            }
        });
        table.getSelectionModel().addListSelectionListener(e -> {
    // tránh trigger 2 lần
    if (!e.getValueIsAdjusting()) {
        boolean isSelected = table.getSelectedRow() != -1;

        btnSua.setEnabled(isSelected);
        btnXoa.setEnabled(isSelected);
        btnChiTiet.setEnabled(isSelected);
    }
});
        table.setRowHeight(30); 
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // ==================== VÙNG DƯỚI (SOUTH): PHÂN TRANG ====================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBottom.setBackground(Color.WHITE);
        
        btnFirst = new JButton("<<");
        btnPrev = new JButton("<");
        lblPageInfo = new JLabel("Trang 1 / 1");
        lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNext = new JButton(">");
        btnLast = new JButton(">>");
        
        pnlBottom.add(btnFirst);
        pnlBottom.add(btnPrev);
        pnlBottom.add(lblPageInfo);
        pnlBottom.add(btnNext);
        pnlBottom.add(btnLast);

        add(pnlTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    // ================== LOGIC PHÂN TRANG 20 DÒNG ==================
    
    public void setTableData(List<Vector> data)
    {
    this.fullDataList = data != null ? data : new ArrayList<>();

    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
    if (totalPages == 0) totalPages = 1;

    // 🔥 FIX: reset + đảm bảo không vượt
    currentPage = 1;

    renderCurrentPage();
    }

    public void renderCurrentPage() {
        // 🔥 1. Chặn page vượt giới hạn
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        // 🔥 2. Clear table
        tableModel.setRowCount(0);

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, fullDataList.size());

        // 🔥 3. Nếu vượt thì KHÔNG return sớm (vì sẽ skip disable nút)
        if (start < fullDataList.size()) {
            for (int i = start; i < end; i++) {
                tableModel.addRow(fullDataList.get(i));
            }
        }

        // 🔥 4. Update label
        lblPageInfo.setText("Trang " + currentPage + " / " + totalPages);

        // 🔥 5. Disable/Enable đúng chuẩn
        btnFirst.setEnabled(currentPage > 1);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        btnLast.setEnabled(currentPage < totalPages);

        // 🔥 6. Nếu chỉ có 1 trang → ẩn luôn
        boolean multiPage = totalPages > 1;

        btnFirst.setVisible(multiPage);
        btnPrev.setVisible(multiPage);
        btnNext.setVisible(multiPage);
        btnLast.setVisible(multiPage);
        lblPageInfo.setVisible(multiPage);

        // 🔥 DEBUG (tuỳ bạn giữ hay xoá)
        System.out.println("Rows: " + fullDataList.size());
        System.out.println("Pages: " + totalPages);
        System.out.println("Current: " + currentPage);
    }

    // Gắn sự kiện click cho 4 nút chuyển trang
    private void setupPaginationEvents() {
        btnFirst.addActionListener(e -> {
            currentPage = 1;
            renderCurrentPage();
        });
        
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                renderCurrentPage();
            }
        });
        
        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                renderCurrentPage();
            }
        });
        
        btnLast.addActionListener(e -> {
            currentPage = totalPages;
            renderCurrentPage();
        });
    }

    // ================== CÁC HÀM TIỆN ÍCH KHÁC ==================

    public void setTableNameForTitle(String tableName) {
        actionBorder.setTitle("Xử lý chức năng của table " + tableName);
        pnlActions.repaint(); 
    }

private void styleComponents() {
    Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);

    // ==========================================
    // ===== BỔ SUNG: SET ICON CHO BUTTONS ======
    // ==========================================
    
    // Hàm phụ trợ để load icon an toàn (tránh văng lỗi nếu lỡ quên chép hình vào)
    java.util.function.Function<String, ImageIcon> loadIcon = (path) -> {
        try {
            // SỬA ĐƯỜNG DẪN Ở DÒNG NÀY: Đổi "/icons/" thành "/IMG/"
            java.net.URL imgURL = getClass().getResource("/IMG/" + path);
            
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Không tìm thấy file icon: " + path);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    };

    int iconSize = 45;
    
    // Gắn icon vào các nút tương ứng
    btnThem.setIcon(loadAndScaleIcon("add.png", iconSize));
    btnSua.setIcon(loadAndScaleIcon("edit.png", iconSize));
    btnXoa.setIcon(loadAndScaleIcon("delete.png", iconSize));
    btnExcel.setIcon(loadAndScaleIcon("excel.png", iconSize));
    btnReFresh.setIcon(loadAndScaleIcon("refresh.png", iconSize));
    btnChiTiet.setIcon(loadAndScaleIcon("information.png", iconSize));
    btnTimKiem.setIcon(loadAndScaleIcon("search.png", 24));

    // Ép chữ nằm phía DƯỚI icon và căn giữa cho 5 nút chức năng
    JButton[] actionBtns = {btnThem, btnSua, btnXoa, btnExcel, btnReFresh,btnChiTiet};
    for (JButton btn : actionBtns) {
        if (btn.getIcon() != null) {
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);   // Chữ nằm dưới hình
            btn.setHorizontalTextPosition(SwingConstants.CENTER); // Căn giữa chữ
            // Mở rộng viền nút ra 1 chút để hình không bị ép sát lề
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); 
        }
    }
    
    // Riêng nút Tìm Kiếm thì để chữ ngang hàng với Icon cho đỡ tốn diện tích
    if (btnTimKiem.getIcon() != null) {
        btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
        btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
    }

    // ===== STYLE BUTTON CHUNG =====
    JButton[] allBtns = {btnThem, btnSua, btnXoa, btnExcel, btnReFresh, btnTimKiem, btnFirst, btnPrev, btnNext, btnLast, btnChiTiet};
    for (JButton btn : allBtns) {
        btn.setFont(mainFont);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Nút Xóa vẫn để màu đỏ cho cảnh báo nguy hiểm
//    btnXoa.setBackground(new Color(231, 76, 60));
//    btnXoa.setForeground(Color.WHITE);

    // ===== INPUT =====
    txtTimKiem.setFont(mainFont);
    txtTimKiem.setPreferredSize(new Dimension(200, 32));

    cbxTimKiem.setFont(mainFont);
    cbxTimKiem.setPreferredSize(new Dimension(150, 32));

    // ===== HEADER TABLE =====
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    header.setPreferredSize(new Dimension(100, 35));
    header.setBackground(new Color(52, 73, 94)); // xanh đậm
    header.setForeground(Color.WHITE);

    // Căn giữa header
    DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
    headerRenderer.setHorizontalAlignment(JLabel.CENTER);

    // Không cho kéo + resize cột
    header.setReorderingAllowed(false);
    header.setResizingAllowed(false);

    // ===== TABLE =====
    table.setFont(mainFont);
    table.setRowHeight(32);
    table.setSelectionBackground(new Color(52, 152, 219));
    table.setSelectionForeground(Color.WHITE);

    // Bỏ grid cho đẹp (Bạn đã làm rất chuẩn chỗ này)
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));

    // ===== RENDERER (CĂN GIỮA + ZEBRA STRIPE) =====
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
            );

            // Căn giữa nội dung các ô
            setHorizontalAlignment(JLabel.CENTER);

            // Màu xen kẽ ngựa vằn (Zebra stripe)
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(new Color(245, 245, 245));
                }
            }
            return c;
        }
    });
}

// BỔ SUNG: Hàm phụ trợ để load và tự động thay đổi kích thước icon (chống bể hình)
    private ImageIcon loadAndScaleIcon(String path, int size) {
        try {
            java.net.URL imgURL = getClass().getResource("/IMG/" + path);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image img = originalIcon.getImage();
                
                // Thuật toán SCALE_SMOOTH giúp hình to lên/nhỏ đi mà không bị mờ hay răng cưa
                Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } else {
                System.err.println("Không tìm thấy file icon: " + path);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}