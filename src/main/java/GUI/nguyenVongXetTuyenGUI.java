package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import BUS.*;
import Entity.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import FUNC_GUI.insertNguyenVong;

public class nguyenVongXetTuyenGUI extends BaseTableGUI {

    public nguyenVongXetTuyenGUI() {
        super(); // Gọi giao diện cơ bản từ BaseTableGUI lên
        // 1. Đổi tên GroupBox và Cột
        setTableNameForTitle("Nguyện Vọng"); 
        headerTable();
        
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
    
    // Hàm tạo header và truyền dữ liệu vào table
    public void headerTable()
    {
        Vector<String> header = new Vector<>();
        header.add("Mã nguyện vọng");
        header.add("CCCD");
        header.add("Mã ngành");
        header.add("Thứ tự");
        header.add("Điểm cộng môn chính");
        header.add("Điểm UTQD");
        header.add("Điềm cộng");
        header.add("Điểm xét tuyển");
        header.add("Kết quả NV");
        header.add("Keys");
        header.add("Phương thức");
        header.add("thm");
        tableModel.setColumnIdentifiers(header);
    }
    
    public void loadDataToTable()
    {
        tableModel.setRowCount(0);

        nguyenVongXetTuyenBUS bus = new nguyenVongXetTuyenBUS();
        if (bus.ds == null) 
        {
            bus.layDanhSach();
        }

        for (nguyenVongXetTuyenETT ct : bus.ds) 
        {
            Vector row = new Vector();
            row.add(ct.getIdNv());
            row.add(ct.getNnCccd());
            row.add(ct.getNvMaNganh());
            row.add(ct.getNvTt());
            row.add(ct.getDiemThxt());
            row.add(ct.getDiemUtqd());
            row.add(ct.getDiemCong());
            row.add(ct.getDiemXetTuyen());
            row.add(ct.getNvKetQua());
            row.add(ct.getNvKeys());
            row.add(ct.getTtPhuongThuc());
            row.add(ct.getTtThm());

            tableModel.addRow(row);
        }
    }

    // Hàm mở JDialog thêm mới (Giữ nguyên)
// ==============================================================
    // HÀM MỞ FORM JDIALOG ĐỂ THÊM NGUYỆN VỌNG MỚI
    // ==============================================================
    private void hienThiDialogThemMoi()
    {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertNguyenVong dialog = new insertNguyenVong(topFrame, true);
        dialog.setVisible(true);
    }
//    private void hienThiDialogThemMoi() {
//        // 1. Tạo và cấu hình khung Dialog
//        JDialog dialogThem = new JDialog();
//        dialogThem.setTitle("Thêm Nguyện Vọng Mới");
//        dialogThem.setSize(450, 500); // Rộng 450, Cao 500
//        dialogThem.setLocationRelativeTo(null); // Hiện ra giữa màn hình
//        dialogThem.setModal(true); // Khóa màn hình chính lại khi Dialog đang mở
//        dialogThem.setLayout(new java.awt.BorderLayout());
//
//        // 2. Tạo Panel chứa các Form nhập liệu (dùng GridLayout 8 dòng, 2 cột)
//        javax.swing.JPanel pnlForm = new javax.swing.JPanel(new java.awt.GridLayout(8, 2, 10, 15));
//        pnlForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Khai báo các ô nhập liệu
//        javax.swing.JTextField txtCCCD = new javax.swing.JTextField();
//        javax.swing.JTextField txtMaNganh = new javax.swing.JTextField();
//        javax.swing.JTextField txtThuTu = new javax.swing.JTextField();
//        javax.swing.JTextField txtDiemTHXT = new javax.swing.JTextField();
//        javax.swing.JTextField txtDiemUTQD = new javax.swing.JTextField();
//        javax.swing.JTextField txtDiemCong = new javax.swing.JTextField();
//        
//        // Dùng ComboBox cho Phương thức và Tổ hợp để tránh người dùng gõ sai chính tả
//        javax.swing.JComboBox<String> cbxPhuongThuc = new javax.swing.JComboBox<>(new String[]{"Xét THPT", "Xét Học Bạ", "ĐGNL HCM", "Tuyển Thẳng"});
//        javax.swing.JComboBox<String> cbxToHop = new javax.swing.JComboBox<>(new String[]{"A00", "A01", "B00", "D01", "NL1"});
//
//        // Đắp label và ô nhập vào Panel
//        pnlForm.add(new JLabel("CCCD Thí Sinh (*):")); pnlForm.add(txtCCCD);
//        pnlForm.add(new JLabel("Mã Ngành (*):"));      pnlForm.add(txtMaNganh);
//        pnlForm.add(new JLabel("Thứ tự Nguyện Vọng:")); pnlForm.add(txtThuTu);
//        pnlForm.add(new JLabel("Tổ hợp môn:"));         pnlForm.add(cbxToHop);
//        pnlForm.add(new JLabel("Phương thức:"));        pnlForm.add(cbxPhuongThuc);
//        pnlForm.add(new JLabel("Điểm THXT:"));          pnlForm.add(txtDiemTHXT);
//        pnlForm.add(new JLabel("Điểm Ưu Tiên (UTQĐ):"));pnlForm.add(txtDiemUTQD);
//        pnlForm.add(new JLabel("Điểm Cộng:"));          pnlForm.add(txtDiemCong);
//
//        // 3. Tạo Panel chứa 2 nút bấm LƯU và HỦY ở dưới đáy
//        javax.swing.JPanel pnlButtons = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));
//        javax.swing.JButton btnLuu = new javax.swing.JButton("LƯU DỮ LIỆU");
//        javax.swing.JButton btnHuy = new javax.swing.JButton("HỦY BỎ");
//        
//        btnLuu.setBackground(new java.awt.Color(46, 204, 113)); // Màu xanh lá
//        btnLuu.setForeground(java.awt.Color.WHITE);
//        btnHuy.setBackground(new java.awt.Color(231, 76, 60)); // Màu đỏ
//        btnHuy.setForeground(java.awt.Color.WHITE);
//
//        pnlButtons.add(btnLuu);
//        pnlButtons.add(btnHuy);
//
//        // Ráp 2 Panel vào Dialog
//        dialogThem.add(pnlForm, java.awt.BorderLayout.CENTER);
//        dialogThem.add(pnlButtons, java.awt.BorderLayout.SOUTH);
//
//        // =========================================================
//        // 4. XỬ LÝ SỰ KIỆN NÚT BẤM
//        // =========================================================
//        btnHuy.addActionListener(e -> dialogThem.dispose()); // Tắt Dialog
//
//        btnLuu.addActionListener(e -> {
//            try {
//                // Lấy chữ từ giao diện
//                String cccd = txtCCCD.getText().trim();
//                String maNganh = txtMaNganh.getText().trim();
//                
//                // Kiểm tra rỗng
//                if (cccd.isEmpty() || maNganh.isEmpty()) {
//                    javax.swing.JOptionPane.showMessageDialog(dialogThem, "Vui lòng nhập đầy đủ CCCD và Mã ngành!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                // Ép kiểu các ô nhập số (Nếu người dùng để trống thì mặc định là 0)
//                int thuTu = txtThuTu.getText().isEmpty() ? 1 : Integer.parseInt(txtThuTu.getText());
//                double thxt = txtDiemTHXT.getText().isEmpty() ? 0.0 : Double.parseDouble(txtDiemTHXT.getText());
//                double utqd = txtDiemUTQD.getText().isEmpty() ? 0.0 : Double.parseDouble(txtDiemUTQD.getText());
//                double diemCong = txtDiemCong.getText().isEmpty() ? 0.0 : Double.parseDouble(txtDiemCong.getText());
//
//                // Tính toán tự động và gán dữ liệu mặc định
//                double diemXetTuyen = thxt + utqd; // Điểm XT = THXT + Ưu Tiên
//                String keys = cccd + "_" + thuTu; // Key chống trùng = CCCD_ThuTu
//                String ketQua = "Chờ xét"; // Mặc định khi mới nộp hồ sơ
//
//                // Gom hết vào đối tượng Entity
//                Entity.nguyenVongXetTuyenETT nvNew = new Entity.nguyenVongXetTuyenETT();
//                nvNew.setNnCccd(cccd);
//                nvNew.setNvMaNganh(maNganh);
//                nvNew.setNvTt(thuTu);
//                nvNew.setTtThm(cbxToHop.getSelectedItem().toString());
//                nvNew.setTtPhuongThuc(cbxPhuongThuc.getSelectedItem().toString());
//                nvNew.setDiemThxt(thxt);
//                nvNew.setDiemUtqd(utqd);
//                nvNew.setDiemCong(diemCong);
//                nvNew.setDiemXetTuyen(diemXetTuyen);
//                nvNew.setNvKeys(keys);
//                nvNew.setNvKetQua(ketQua);
//
//                // Gọi BUS lưu xuống Database
//                BUS.nguyenVongXetTuyenBUS bus = new BUS.nguyenVongXetTuyenBUS();
//                boolean isSuccess = bus.themNguyenVong(nvNew); // Hàm này mình đã sửa lúc nãy (nó lưu DB + đẩy vào static ds)
//
//                if (isSuccess) {
//                    javax.swing.JOptionPane.showMessageDialog(dialogThem, "Thêm Nguyện Vọng thành công!");
//                    dialogThem.dispose(); // Đóng form
//                    loadDataToTable(); // Load lại bảng để hiện người vừa thêm
//                } else {
//                    javax.swing.JOptionPane.showMessageDialog(dialogThem, "Thêm thất bại. Có thể CCCD đã tồn tại!", "Lỗi Database", javax.swing.JOptionPane.ERROR_MESSAGE);
//                }
//
//            } catch (NumberFormatException ex) {
//                // Bắt lỗi nếu gõ chữ vào ô nhập số điểm
//                javax.swing.JOptionPane.showMessageDialog(dialogThem, "Sai định dạng số ở các ô Điểm hoặc Thứ tự!", "Lỗi nhập liệu", javax.swing.JOptionPane.WARNING_MESSAGE);
//            }
//        });
//
//        // Cuối cùng: Cho hiển thị form lên
//        dialogThem.setVisible(true);
//    }
    
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