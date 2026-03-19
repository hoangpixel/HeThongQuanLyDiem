package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class nguyenVongXetTuyenGUI extends BaseTableGUI {

    public nguyenVongXetTuyenGUI() {
        super(); // Gọi giao diện cơ bản từ BaseTableGUI lên
        
        // 1. Đổi tên GroupBox và Cột
        setTableNameForTitle("Nguyện Vọng"); 
        String[] columns = {"ID Nguyện Vọng", "CCCD Thí Sinh", "Mã Ngành", "Điểm Xét Tuyển"};
        tableModel.setColumnIdentifiers(columns);
        
        // 2. Gắn sự kiện nút bấm
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        
        // ==============================================================
        // BỔ SUNG: GẮN SỰ KIỆN DOUBLE-CLICK CHO TABLE
        // ==============================================================
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Kiểm tra xem người dùng có click đúp (2 lần) không
                if (evt.getClickCount() == 2) {
                    // Lấy ra vị trí dòng (row) mà người dùng vừa click vào
                    int selectedRow = table.getSelectedRow();
                    
                    // Nếu có dòng được chọn (khác -1)
                    if (selectedRow != -1) {
                        // Gọi hàm hiển thị form và truyền vị trí dòng vào
                        hienThiDialogChiTiet(selectedRow);
                    }
                }
            }
        });
        // ==============================================================
        
        // 3. GỌI HÀM TẢI DỮ LIỆU LÊN BẢNG KHI VỪA MỞ FORM LÊN
        loadDataToTable();
    }
    
    // Hàm hiển thị danh sách (Giữ nguyên như cũ của bạn)
    private void loadDataToTable() {
        List<Object[]> dataList = new ArrayList<>();
        
        for (int i = 1; i <= 55; i++) {
            double diemRandom = Math.round((Math.random() * 10 + 20) * 100.0) / 100.0; 
            dataList.add(new Object[]{
                "NV00" + i, 
                "07920300" + i, 
                (i % 2 == 0) ? "7480201" : "7810202", 
                diemRandom
            });
        }
        setTableData(dataList);
    }

    // Hàm mở JDialog thêm mới (Giữ nguyên)
    private void hienThiDialogThemMoi() {
        // ... (code cũ của bạn) ...
    }
    
    // ==============================================================
    // HÀM MỚI: HIỂN THỊ DIALOG KHI DOUBLE CLICK (XEM CHI TIẾT / SỬA)
    // ==============================================================
    private void hienThiDialogChiTiet(int rowIndex) {
        // 1. LẤY DỮ LIỆU TỪ DÒNG ĐƯỢC CHỌN (Ví dụ lấy ID và CCCD)
        // Lưu ý: getValueAt(rowIndex, columnIndex). Cột 0 là ID, cột 1 là CCCD...
        String idNguyenVong = table.getValueAt(rowIndex, 0).toString();
        String cccd = table.getValueAt(rowIndex, 1).toString();
        String diem = table.getValueAt(rowIndex, 3).toString();

        // 2. TẠO DIALOG CHI TIẾT
        JDialog dialogChiTiet = new JDialog();
        dialogChiTiet.setTitle("Chi tiết Nguyện Vọng: " + idNguyenVong);
        dialogChiTiet.setSize(400, 300);
        dialogChiTiet.setLocationRelativeTo(null); 
        dialogChiTiet.setModal(true); 
        
        dialogChiTiet.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        
        // 3. ĐỔ DỮ LIỆU LÊN GIAO DIỆN DIALOG
        dialogChiTiet.add(new JLabel("Mã Nguyện Vọng: " + idNguyenVong));
        dialogChiTiet.add(new JLabel("CCCD Thí sinh: " + cccd));
        dialogChiTiet.add(new JLabel("Điểm xét tuyển: " + diem));
        
        // Nút lưu nếu bạn muốn làm chức năng SỬA ở đây luôn
        dialogChiTiet.add(new javax.swing.JButton("Cập nhật thông tin"));
        
        dialogChiTiet.setVisible(true);
    }
}