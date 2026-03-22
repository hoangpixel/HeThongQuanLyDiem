package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import BUS.thiSinhXetTuyenBUS;
import Entity.thiSinhXetTuyenETT;

import FUNC_GUI.insertThiSinh; 

public class thiSinhXetTuyenGUI extends BaseTableGUI {

    public thiSinhXetTuyenGUI() {
        super(); 
        
        // 1. Đổi tên GroupBox và Cột
        setTableNameForTitle("Quản lý Thí Sinh"); 
        headerTable();
        
        // 2. Gắn sự kiện nút bấm Thêm
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        
        // ==============================================================
        // GẮN SỰ KIỆN DOUBLE-CLICK CHO TABLE ĐỂ XEM CHI TIẾT
        // ==============================================================
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        hienThiDialogChiTiet(selectedRow);
                    }
                }
            }
        });
        
        // 3. GỌI HÀM TẢI DỮ LIỆU LÊN BẢNG KHI VỪA MỞ FORM LÊN (Đã mở khóa)
        loadDataToTable();
    }
    
    // ==============================================================
    // Hàm tạo header và truyền dữ liệu vào table
    // ==============================================================
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID Thí sinh");
        header.add("CCCD");
        header.add("Số báo danh");
        header.add("Họ");
        header.add("Tên");
        header.add("Ngày sinh");
        header.add("Giới tính");
        header.add("Điện thoại");
        header.add("Email");
        header.add("Nơi sinh");
        header.add("Đối tượng");
        header.add("Khu vực");
        
        tableModel.setColumnIdentifiers(header);
    }
    
    // ==============================================================
    // Hàm load dữ liệu từ Database lên Table (Đã sửa để dùng được Phân trang)
    // ==============================================================
    
    public void loadDataToTable() {
        thiSinhXetTuyenBUS bus = new thiSinhXetTuyenBUS();
        if (bus.ds == null) {
            bus.layDanhSach();
        }

        // Tạo List chứa dữ liệu chuẩn bị cho BaseTableGUI chia trang
        java.util.List<Vector> dataList = new java.util.ArrayList<>();

        for (thiSinhXetTuyenETT ts : bus.ds) {
            Vector row = new Vector();
            row.add(ts.getIdThiSinh());
            row.add(ts.getCccd());
            row.add(ts.getSoBaoDanh());
            row.add(ts.getHo());
            row.add(ts.getTen());
            row.add(ts.getNgaySinh());
            row.add(ts.getGioiTinh());
            row.add(ts.getDienThoai());
            row.add(ts.getEmail());
            row.add(ts.getNoiSinh());
            row.add(ts.getDoiTuong());
            row.add(ts.getKhuVuc());

            // Thêm vào List thay vì thêm thẳng vào tableModel
            dataList.add(row);
        }
        
        // Truyền List vào cho BaseTableGUI vẽ và phân trang
        setTableData(dataList);
    }
    

    // ==============================================================
    // HÀM MỞ FORM JDIALOG ĐỂ THÊM THÍ SINH MỚI
    // ==============================================================
    private void hienThiDialogThemMoi() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertThiSinh dialog = new insertThiSinh(topFrame, true);
        dialog.setVisible(true);
        
        // Load lại bảng sau khi tắt form thêm
        loadDataToTable(); 
    }
    
    // ==============================================================
    // HÀM HIỂN THỊ DIALOG KHI DOUBLE CLICK (XEM CHI TIẾT / SỬA)
    // ==============================================================
    private void hienThiDialogChiTiet(int rowIndex) {
        String idThiSinh = table.getValueAt(rowIndex, 0) != null ? table.getValueAt(rowIndex, 0).toString() : "";
        String cccd = table.getValueAt(rowIndex, 1) != null ? table.getValueAt(rowIndex, 1).toString() : "";
        String ho = table.getValueAt(rowIndex, 3) != null ? table.getValueAt(rowIndex, 3).toString() : "";
        String ten = table.getValueAt(rowIndex, 4) != null ? table.getValueAt(rowIndex, 4).toString() : "";

        JDialog dialogChiTiet = new JDialog();
        dialogChiTiet.setTitle("Chi tiết Thí Sinh: " + cccd);
        dialogChiTiet.setSize(400, 300);
        dialogChiTiet.setLocationRelativeTo(null); 
        dialogChiTiet.setModal(true); 
        
        dialogChiTiet.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        
        dialogChiTiet.add(new JLabel("ID Thí sinh: " + idThiSinh));
        dialogChiTiet.add(new JLabel("CCCD: " + cccd));
        dialogChiTiet.add(new JLabel("Họ tên: " + ho + " " + ten));
        
        dialogChiTiet.add(new javax.swing.JButton("Cập nhật thông tin"));
        
        dialogChiTiet.setVisible(true);
    }
}