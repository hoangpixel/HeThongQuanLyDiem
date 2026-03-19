package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
        // 3. GỌI HÀM TẢI DỮ LIỆU LÊN BẢNG KHI VỪA MỞ FORM LÊN
        loadDataToTable();
    }
    
    // ==============================================================
    // HÀM HIỂN THỊ DANH SÁCH (LẤY TỪ DATABASE)
    // ==============================================================
    private void loadDataToTable() {
        // Khởi tạo một danh sách để chứa các dòng dữ liệu
        List<Object[]> dataList = new ArrayList<>();
        
        /* * THỰC TẾ DÙNG HIBERNATE/JDBC SẼ VIẾT NHƯ SAU:
         * * List<NguyenVongDTO> list = nguyenVongBUS.getAllNguyenVong();
         * for (NguyenVongDTO nv : list) {
         * dataList.add(new Object[]{nv.getId(), nv.getCccd(), nv.getMaNganh(), nv.getDiemXetTuyen()});
         * }
         */

        // -------- CODE TEST: TẠO DATA GIẢ ĐỂ TEST PHÂN TRANG --------
        // Tui tạo thử 55 dòng để test xem hệ thống có tự chia thành 3 trang không (20 - 20 - 15)
        for (int i = 1; i <= 55; i++) {
            // Random điểm từ 20 đến 30 cho giống thực tế
            double diemRandom = Math.round((Math.random() * 10 + 20) * 100.0) / 100.0; 
            
            dataList.add(new Object[]{
                "NV00" + i, 
                "07920300" + i, 
                (i % 2 == 0) ? "7480201" : "7810202", // Đảo xen kẽ 2 mã ngành
                diemRandom
            });
        }
        // -------------------------------------------------------------
        
        // BƯỚC CUỐI: Ném cái List này vào hàm của BaseTableGUI
        // Nó sẽ tự động xóa bảng cũ, cắt ra 20 dòng đầu tiên hiển thị lên và setup các nút bấm qua trang!
        setTableData(dataList);
    }

    // Hàm mở JDialog thêm mới (Giữ nguyên như cũ)
    private void hienThiDialogThemMoi() {
        JDialog dialogThem = new JDialog();
        dialogThem.setTitle("Thêm mới Nguyện Vọng");
        dialogThem.setSize(400, 300);
        dialogThem.setLocationRelativeTo(null); 
        dialogThem.setModal(true); 
        
        dialogThem.setLayout(new java.awt.FlowLayout());
        dialogThem.add(new JLabel("Nhập CCCD: "));
        dialogThem.add(new javax.swing.JTextField(20));
        dialogThem.add(new javax.swing.JButton("Lưu thông tin"));
        
        dialogThem.setVisible(true);
    }
}