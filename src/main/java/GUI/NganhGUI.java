package GUI;

import BUS.nganhBUS;
import BUS.phanQuyenBUS;
import Entity.nganhETT;
import Entity.nguyenVongXetTuyenETT;
import FUNC_GUI.insertNganh;
import FUNC_GUI.updateNganh;
import FUNC_GUI.deleteNganh;
import FUNC_GUI.detailNganh;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import EXCEL.ExcelHelper;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dat
 */
public class NganhGUI extends BaseTableGUI {

    // Triệu hồi đội vận chuyển Ngành học
    private nganhBUS busNganh = new nganhBUS();

    public NganhGUI() {
        super();
        setTableNameForTitle("Ngành Học"); 
        headerTable();
        
        // 2. Nối dây điện cho các nút chức năng
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> hienThiChiTietNganh());
        
        // 3. Vừa mở phòng ra là tự động bưng dữ liệu lên bàn ngay
        loadDataToTable();
        loadComboBox();
        phanQuyenGiaoDien();
    }

    private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_nganh";
        
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

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
    
    // Thiết lập danh sách các cột hiển thị (Header)
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID Ngành");
        header.add("Mã Ngành");
        header.add("Tên Ngành");
        header.add("Tổ Hợp Gốc");
        header.add("Chỉ Tiêu");
        header.add("Điểm Sàn THPT");
        header.add("Điểm Sàn V-SAT");
        header.add("Điểm Sàn ĐGNL");
        header.add("Điểm Chuẩn");
        tableModel.setColumnIdentifiers(header);
    }
    
    public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID Ngành");
        cbxTimKiem.addItem("Mã Ngành");
        cbxTimKiem.addItem("Tên Ngành");
        cbxTimKiem.addItem("Tổ Hợp Gốc");
        cbxTimKiem.addItem("Chỉ Tiêu");
        cbxTimKiem.addItem("Sàn THPT");
        cbxTimKiem.addItem("Sàn V-SAT");
        cbxTimKiem.addItem("Sàn ĐGNL");
        cbxTimKiem.addItem("Điểm Chuẩn");
    }

    // Lệnh lôi dữ liệu từ kho (Database) qua BUS rồi đẩy lên bảng Swing
    public void loadDataToTable() {
        // Ra lệnh cho BUS quét dọn danh sách mới nhất
        if (busNganh.ds == null) {
            busNganh.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();
        for (nganhETT item : busNganh.ds) {
            Vector row = new Vector();
            row.add(item.getIdnganh());
            row.add(item.getManganh());
            row.add(item.getTennganh());
            row.add(item.getN_tohopgoc());
            row.add(item.getN_chitieu());
            row.add(item.getN_diemsanthpt() != null ? item.getN_diemsanthpt() : "");
            row.add(item.getN_diemsanvsat() != null ? item.getN_diemsanvsat() : "");
            row.add(item.getN_diemsandgnl() != null ? item.getN_diemsandgnl() : "");
            row.add(item.getN_diemtrungtuyen() != null ? item.getN_diemtrungtuyen() : "");
            dataList.add(row);
        }

        // Giao dữ liệu cho BaseTableGUI để nó tự lo vụ chia trang 5 dòng/trang
        setTableData(dataList);
    }

    // ==============================================================
    // HÀM GỌI CÁC CỬA SỔ PHỤ (DIALOGS)
    // ==============================================================

    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertNganh dialog = new insertNganh(topFrame, true);
        dialog.setVisible(true);

        if (dialog.xacNhan) {
            nganhETT nv = dialog.getNganh();

            // 🔥 Convert object → Vector (Đạt nhớ xếp đúng thứ tự cột trên bảng Ngành nhé)
            Vector row = new Vector();
            row.add(fullDataList.size() + 1); // Số thứ tự tạm thời hoặc ID
            row.add(nv.getManganh());
            row.add(nv.getTennganh());
            row.add(nv.getN_tohopgoc());
            row.add(nv.getN_chitieu());
            row.add(nv.getN_diemsanthpt() != null ? nv.getN_diemsanthpt() : "");
            row.add(nv.getN_diemsanvsat() != null ? nv.getN_diemsanvsat() : "");
            row.add(nv.getN_diemsandgnl() != null ? nv.getN_diemsandgnl() : "");
            row.add(nv.getN_diemtrungtuyen() != null ? nv.getN_diemtrungtuyen() : "");
        
            // Nếu bảng hiển thị các cột sl_... thì add thêm vào đây
            // row.add(nv.getSl_xtt()); 

            // 🔥 ADD vào fullDataList (QUAN TRỌNG)
            fullDataList.add(row);

            // 🔥 Cập nhật lại totalPages
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

            // 🔥 Nếu muốn nhảy tới trang cuối
            currentPage = totalPages;

            // 🔥 Render lại
            renderCurrentPage();
        }
    }

    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Tính toán vị trí chính xác của đối tượng trong danh sách tổng
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; 
        
            // 2. Tìm đúng đối tượng bằng ID từ bảng (Chống lỗi khi Tìm kiếm)
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            Entity.nganhETT nganhCu = busNganh.findById(id);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        
            // 3. Mở Form Sửa và truyền đối tượng cũ vào
            updateNganh dialog = new updateNganh(topFrame, true, nganhCu);
            dialog.setVisible(true); 
        
            // 4. Sau khi người dùng nhấn LƯU thành công
            if (dialog.xacNhan) { 
                Entity.nganhETT nvMoi = dialog.getNganh();
            
                // 🔥 Ép kiểu đối tượng mới thành Vector để cập nhật lại bảng
                Vector rowData = new java.util.Vector();
                rowData.add(absoluteIndex + 1); // Cột Số thứ tự
                rowData.add(nvMoi.getManganh());
                rowData.add(nvMoi.getTennganh());
                rowData.add(nvMoi.getN_tohopgoc());
                rowData.add(nvMoi.getN_chitieu());
            
                // Xử lý hiển thị điểm (nếu null thì hiện chuỗi rỗng)
                rowData.add(nvMoi.getN_diemsanthpt() != null ? nvMoi.getN_diemsanthpt() : "");
                rowData.add(nvMoi.getN_diemsanvsat() != null ? nvMoi.getN_diemsanvsat() : "");
                rowData.add(nvMoi.getN_diemsandgnl() != null ? nvMoi.getN_diemsandgnl() : "");
                rowData.add(nvMoi.getN_diemtrungtuyen() != null ? nvMoi.getN_diemtrungtuyen() : "");

                // 🔥 Đè Vector mới vào đúng vị trí cũ trong danh sách RAM
                fullDataList.set(absoluteIndex, rowData);

                // 🔥 Render lại đúng trang hiện tại
                renderCurrentPage(); 
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngành trên bảng để sửa!");
        }
    }

    private void hienThiDialogXoa()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Hỏi lại cho chắc ăn (Hạn chế xóa nhầm)
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                deleteNganh dialog = new deleteNganh(topFrame, true);
                dialog.setVisible(true);
                
                if(dialog.getXacNhanXoa())
                {
                // 2. Lấy ID từ bảng và tìm đối tượng cần xóa
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                Entity.nganhETT nganhCanXoa = busNganh.findById(id);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (nganhCanXoa != null && busNganh.xoaNganh(nganhCanXoa)) {
                    // Xóa khỏi fullDataList để đồng bộ UI
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                    fullDataList.remove(absoluteIndex);
                    
                    // 🔥 Xóa luôn phần tử đó trong fullDataList để đồng bộ
                    fullDataList.remove(absoluteIndex);
                    
                    // 🔥 Tính lại tổng số trang (Lỡ xóa rớt mất 1 trang thì sao)
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    
                    // 🔥 Nếu trang hiện tại bị rỗng (do vừa xóa thằng cuối cùng của trang), lùi lại 1 trang
                    if (currentPage > totalPages && totalPages > 0) {
                        currentPage = totalPages;
                    }
                    
                    // 🔥 Vẽ lại bảng cực mượt
                    renderCurrentPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa ngành thất bại");
                }
            }
        }
    }

    private void thucHienRefresh()
    {
    // 1. Tải lại dữ liệu mới nhất từ Database vào RAM (busNganh.ds)
    busNganh.layDanhSach(); 

    // 2. Xóa sạch dữ liệu cũ trên bảng (Vector hiển thị)
    fullDataList.clear();

    // 3. Đổ lại dữ liệu từ RAM vào fullDataList
    for (int i = 0; i < busNganh.ds.size(); i++) {
        Entity.nganhETT item = busNganh.ds.get(i);
        java.util.Vector row = new java.util.Vector();
        row.add(i + 1); // Số thứ tự
        row.add(item.getManganh());
        row.add(item.getTennganh());
        row.add(item.getN_tohopgoc());
        row.add(item.getN_chitieu());
        row.add(item.getN_diemsanthpt() != null ? item.getN_diemsanthpt() : "");
        row.add(item.getN_diemsanvsat() != null ? item.getN_diemsanvsat() : "");
        row.add(item.getN_diemsandgnl() != null ? item.getN_diemsandgnl() : "");
        row.add(item.getN_diemtrungtuyen() != null ? item.getN_diemtrungtuyen() : "");
        
        fullDataList.add(row);
    }

    // 4. Tính toán lại tổng số trang
    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
    
    // 5. Vẽ lại bảng
    renderCurrentPage();

    javax.swing.JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu mới nhất từ Database");
    }
    
    public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        // Lưu ý: Cậu cần tạo một JDialog tên là excelNganh tương tự cái của Nguyện Vọng
        FUNC_GUI.excelNganh dialog = new FUNC_GUI.excelNganh(topFrame, true);
        dialog.setVisible(true);
        
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu Ngành học");
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn file
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                // Gọi BUS xử lý và nhận thông báo kết quả
                String thongBao = busNganh.nhapDuLieuTuExcel(filePath);
                
                JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Xong xuôi thì làm mới lại bảng (Dùng luôn hàm Refresh bạn đã viết cho khỏe)
                thucHienRefresh();
            }
        } 
        else if(dialog.getXacNhanExport())
        {
            // Lấy danh sách ngành mới nhất
            java.util.ArrayList<Entity.nganhETT> fullDanhSach = busNganh.layDanhSach();
            
            // Cậu nhớ bổ sung hàm xuatDanhSachNganhRaExcel bên trong class ExcelHelper nhé
            ExcelHelper.xuatDanhSachNganhRaExcel(fullDanhSach, this, "DanhSachNganhHoc");
        }
    }

    public void thucHienTimKiem()
    {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        if(tim.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }

        ArrayList<nganhETT> dskq = busNganh.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();
        
        for (nganhETT ct : dskq) 
        {
            Vector row = new Vector();
            row.add(ct.getIdnganh());
            row.add(ct.getManganh());
            row.add(ct.getTennganh());
            row.add(ct.getN_tohopgoc());
            row.add(ct.getN_chitieu());
            row.add(ct.getN_diemsanthpt() != null ? ct.getN_diemsanthpt() : "");
            row.add(ct.getN_diemsanvsat() != null ? ct.getN_diemsanvsat() : "");
            row.add(ct.getN_diemsandgnl() != null ? ct.getN_diemsandgnl() : "");
            row.add(ct.getN_diemtrungtuyen() != null ? ct.getN_diemtrungtuyen() : "");

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);
        if(dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }

    private void hienThiChiTietNganh()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            
            nganhETT nganhCu = busNganh.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            detailNganh dialog = new detailNganh(topFrame, true, nganhCu);
            dialog.setVisible(true);
        }
    }
}
