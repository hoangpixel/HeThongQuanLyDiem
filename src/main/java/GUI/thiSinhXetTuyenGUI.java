package GUI;

import BUS.phanQuyenBUS;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import BUS.thiSinhXetTuyenBUS;
import EXCEL.ExcelHelper;
import Entity.nguyenVongXetTuyenETT;
import Entity.thiSinhXetTuyenETT;
import FUNC_GUI.deleteNguyenVong;
import javax.swing.table.DefaultTableModel;
import FUNC_GUI.excelThiSinh;
import FUNC_GUI.insertThiSinh; 
import FUNC_GUI.updateThiSinh;
import FUNC_GUI.deleteThiSinh;
import FUNC_GUI.excelNguyenVongSybau;
import FUNC_GUI.updateNguyenVong;
import FUNC_GUI.detailThiSinhXetTuyen;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class thiSinhXetTuyenGUI extends BaseTableGUI {
    thiSinhXetTuyenBUS busThiSinh =new thiSinhXetTuyenBUS();
    public thiSinhXetTuyenGUI() {
        super(); 
        
        // 1. Đổi tên GroupBox và Cột
        setTableNameForTitle("Quản lý Thí Sinh"); 
        headerTable();
        
        // 2. Gắn sự kiện nút bấm
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        btnSua.addActionListener(e ->hienThiDialogSuaTS());
        btnXoa.addActionListener(e ->hienThiDialogXoaTS());
        btnChiTiet.addActionListener(e -> thucHienChiTiet());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        loadComboBox();
        phanQuyenGiaoDien();
        btnReFresh.addActionListener(e -> thucHienRefresh());
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

    private void thucHienChiTiet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thí sinh trên bảng để xem chi tiết!");
            return;
        }
        hienThiDialogChiTiet(selectedRow);
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
        public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID");
        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("SBD");
        cbxTimKiem.addItem("Họ");
        cbxTimKiem.addItem("Tên");
        cbxTimKiem.addItem("Điện thoại");
        cbxTimKiem.addItem("Email");
    }
    public void loadDataToTable() {
        if (busThiSinh.ds == null) {
            busThiSinh.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (thiSinhXetTuyenETT ct : busThiSinh.ds) {
            Vector row = new Vector();
            row.add(ct.getIdThiSinh());
            row.add(ct.getCccd());
            row.add(ct.getSoBaoDanh());
            row.add(ct.getHo());
            row.add(ct.getTen());
            row.add(ct.getNgaySinh());
            row.add(ct.getGioiTinh());          
            row.add(ct.getDienThoai());
            row.add(ct.getEmail());
            row.add(ct.getNoiSinh());
            row.add(ct.getDoiTuong());
            row.add(ct.getKhuVuc());

            dataList.add(row); // ✅ CHỈ add vào list
        }

        // 🔥 CHỈ GỌI DÒNG NÀY
        setTableData(dataList);
    }
    

    // ==============================================================
    // HÀM MỞ FORM JDIALOG ĐỂ THÊM THÍ SINH MỚI
    // ==============================================================
    private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_thisinhxettuyen25";
        
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
//        btnTinhToanKetQua.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        boolean isSelected = table.getSelectedRow() != -1;
                        
                        if (isSelected) {
                            btnSua.setEnabled(phanQuyenBUS.checkQuyenSua(bangHienTai));
                            btnXoa.setEnabled(phanQuyenBUS.checkQuyenXoa(bangHienTai));
                            btnChiTiet.setEnabled(phanQuyenBUS.checkQuyenXem(bangHienTai)); 
                        } else {
                            btnSua.setEnabled(false);
                            btnXoa.setEnabled(false);
                            btnChiTiet.setEnabled(false);
                        }
                    }
                });
                
            }
        });
    }
    private void hienThiDialogThemMoi() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertThiSinh dialog = new insertThiSinh(topFrame, true);
        dialog.setVisible(true);

        if(dialog.isXacNhan()) {
            thiSinhXetTuyenETT ts = dialog.getThiSinh();

            Vector row =new Vector();
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
             fullDataList.add(row);
               totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
               currentPage = totalPages;
                renderCurrentPage();
        }
    }
    private void hienThiDialogSuaTS() {

        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Tính toán vị trí chính xác của đối tượng trong danh sách tổng
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; // 🔥 Vị trí trong danh sách hiển thị (fullDataList)
            
            // 2. Tìm đúng đối tượng bằng ID từ bảng (Chống lỗi khi Tìm kiếm)
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            Entity.thiSinhXetTuyenETT thiSinhCu = busThiSinh.findById(id);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            // Giả sử form của bạn tên là updateNguyenVong
            updateThiSinh dialog = new updateThiSinh(topFrame, true, thiSinhCu);
            dialog.setVisible(true); // Form Sửa hiện lên

        if (dialog.isXacNhan()) {
            Entity.thiSinhXetTuyenETT tsMoi =dialog.getThiSinh();
            java.util.Vector rowData =new java.util.Vector();
            rowData.add(tsMoi.getIdThiSinh());
            rowData.add(tsMoi.getCccd());
            rowData.add(tsMoi.getSoBaoDanh());
            rowData.add(tsMoi.getHo());
            rowData.add(tsMoi.getTen());
            rowData.add(tsMoi.getNgaySinh());
            rowData.add(tsMoi.getGioiTinh());          
            rowData.add(tsMoi.getDienThoai());
            rowData.add(tsMoi.getEmail());
            rowData.add(tsMoi.getNoiSinh());
            rowData.add(tsMoi.getDoiTuong());
            rowData.add(tsMoi.getKhuVuc());
            
             fullDataList.set(absoluteIndex, rowData);

                // 🔥 Render lại đúng trang hiện tại (Tốc độ bàn thờ, không lag, không mất trang)
                renderCurrentPage(); 
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một thí sinh trên bảng để sửa!");
        }
    }
    private void hienThiDialogXoaTS() {
        int row = table.getSelectedRow();

        if (row != -1) {

            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            deleteThiSinh dialog = new deleteThiSinh(topFrame, true);
            dialog.setVisible(true);

            if (dialog.getXacNhanXoa()) {

                // 🔥 Lấy ID từ bảng và tìm đối tượng cần xóa
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                thiSinhXetTuyenETT tsCanXoa = busThiSinh.findById(id);

                if (tsCanXoa != null && busThiSinh.xoaThiSinh(tsCanXoa)) {
                    // Xóa khỏi fullDataList để đồng bộ UI
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                    fullDataList.remove(absoluteIndex);
                    
                    // Tính toán lại phân trang
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
                    
                    renderCurrentPage();
                }

                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thí sinh thất bại");
                }
            }
        }
    // ==============================================================
    // HÀM HIỂN THỊ DIALOG KHI DOUBLE CLICK (XEM CHI TIẾT / SỬA)
    // ==============================================================
    private void hienThiDialogChiTiet(int rowIndex) {
        Object idVal = table.getValueAt(rowIndex, 0);
        if (idVal == null) {
            JOptionPane.showMessageDialog(this, "Không lấy được ID thí sinh");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(String.valueOf(idVal));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID thí sinh không hợp lệ");
            return;
        }

        if (busThiSinh.ds == null) {
            busThiSinh.layDanhSach();
        }

        thiSinhXetTuyenETT current = null;
        if (busThiSinh.ds != null) {
            for (thiSinhXetTuyenETT ts : busThiSinh.ds) {
                if (ts != null && ts.getIdThiSinh() == id) {
                    current = ts;
                    break;
                }
            }
        }

        if (current == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu thí sinh");
            return;
        }

        java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
        java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
        detailThiSinhXetTuyen dialog = new detailThiSinhXetTuyen(frameOwner, true, current);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }

        ArrayList<thiSinhXetTuyenETT> dskq = busThiSinh.timKiemCoBan(tim, index);

        List<Vector> dsHienThi = new ArrayList<>();

        for (thiSinhXetTuyenETT ts : dskq) {
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

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);

        if (dsHienThi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }
    public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelThiSinh dialog = new excelThiSinh(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Gọi BUS xử lý và nhận thông báo kết quả
            String thongBao = busThiSinh.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            thiSinhXetTuyenBUS.ds = null;
            loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<thiSinhXetTuyenETT> fullDanhSach = busThiSinh.layDanhSach();
            ExcelHelper.xuatDanhSachThiSinhRaExcel(fullDanhSach, this, "DanhSachThiSinh");
        }
    }
    public void thucHienRefresh() {
        txtTimKiem.setText("");
        cbxTimKiem.setSelectedIndex(0);

        thiSinhXetTuyenBUS.ds = null;
        loadDataToTable();
    }
}