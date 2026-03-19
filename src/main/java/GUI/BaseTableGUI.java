package GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class BaseTableGUI extends JPanel {

    // Khai báo các components
    public JButton btnThem, btnSua, btnXoa, btnExcel, btnThongKe, btnTimKiem;
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
    private int currentPage = 1;
    private final int rowsPerPage = 20; // CHỐT HIỂN THỊ 20 DÒNG 1 TRANG
    private int totalPages = 1;
    private List<Object[]> fullDataList = new ArrayList<>(); // Biến lưu trữ toàn bộ dữ liệu

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
        pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlActions.setBackground(Color.WHITE);
        
        actionBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Xử lý chức năng của table ...", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        );
        pnlActions.setBorder(BorderFactory.createCompoundBorder(actionBorder, new EmptyBorder(5, 5, 5, 5)));

        btnThem = new JButton("THÊM");
        btnSua = new JButton("SỬA");
        btnXoa = new JButton("XÓA");
        btnExcel = new JButton("EXCEL");
        btnThongKe = new JButton("THỐNG KÊ");
        
        pnlActions.add(btnThem);
        pnlActions.add(btnSua);
        pnlActions.add(btnXoa);
        pnlActions.add(btnExcel);
        pnlActions.add(btnThongKe);

        // 2. GroupBox: Tìm kiếm (Bên phải)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlSearch.setBackground(Color.WHITE);
        
        TitledBorder searchBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Tìm kiếm", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        );
        pnlSearch.setBorder(BorderFactory.createCompoundBorder(searchBorder, new EmptyBorder(5, 5, 5, 5)));

        cbxTimKiem = new JComboBox<>(new String[]{"Tìm theo Mã", "Tìm theo Tên", "Tìm theo CCCD"});
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("TÌM KIẾM");
        
        pnlSearch.add(cbxTimKiem);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlTop.add(pnlActions, BorderLayout.CENTER); 
        pnlTop.add(pnlSearch, BorderLayout.EAST);    

        // ==================== VÙNG GIỮA (CENTER): TABLE ====================
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Cột 1", "Cột 2", "Cột 3", "Cột 4"});
        table = new JTable(tableModel);
        table.setRowHeight(30); 
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // ==================== VÙNG DƯỚI (SOUTH): PHÂN TRANG ====================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBottom.setBackground(Color.WHITE);
        
        btnFirst = new JButton("|<");
        btnPrev = new JButton("<");
        lblPageInfo = new JLabel("Trang 1 / 1");
        lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNext = new JButton(">");
        btnLast = new JButton(">|");
        
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
    
    // Gọi hàm này ở form con để ném dữ liệu vào. Form sẽ tự cắt 20 dòng.
    public void setTableData(List<Object[]> data) {
        this.fullDataList = data;
        this.totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
        if (this.totalPages == 0) this.totalPages = 1; // Mặc định luôn có ít nhất 1 trang
        this.currentPage = 1; // Về trang đầu tiên
        renderCurrentPage();
    }

    // Hàm chịu trách nhiệm vẽ đúng 20 dòng của trang hiện tại lên Table
    private void renderCurrentPage() {
        tableModel.setRowCount(0); // Xóa data cũ trên bảng
        
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, fullDataList.size());
        
        // Đổ 20 dòng vào table
        for (int i = startIndex; i < endIndex; i++) {
            tableModel.addRow(fullDataList.get(i));
        }
        
        // Cập nhật text hiển thị trang
        lblPageInfo.setText("Trang " + currentPage + " / " + totalPages);
        
        // Bật/tắt các nút cho hợp lý
        btnFirst.setEnabled(currentPage > 1);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        btnLast.setEnabled(currentPage < totalPages);
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
        
        JButton[] btns = {btnThem, btnSua, btnXoa, btnExcel, btnThongKe, btnTimKiem, btnFirst, btnPrev, btnNext, btnLast};
        for (JButton btn : btns) {
            btn.setFont(mainFont);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnXoa.setBackground(new Color(231, 76, 60));
        btnXoa.setForeground(Color.WHITE);

        txtTimKiem.setFont(mainFont);
        txtTimKiem.setPreferredSize(new Dimension(200, 32));
        cbxTimKiem.setFont(mainFont);
        cbxTimKiem.setPreferredSize(new Dimension(150, 32));
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }
}