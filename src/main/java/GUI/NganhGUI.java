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
        header.add("SL ĐK"); // <-- Cột số 9
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
        //cbxTimKiem.addItem("Điểm Chuẩn");
    }

public void loadDataToTable() {
        // Phá bỏ bộ nhớ đệm (RAM cũ) để ÉP hệ thống luôn kéo dữ liệu MỚI NHẤT từ DB
        busNganh.ds = null;
        busNganh.layDanhSach();

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
            
            // Check an toàn, lỡ Ngành đó chưa có ai đăng ký (trong DB là NULL) thì in ra số 0
            row.add(item.getSlDangKy() != null ? item.getSlDangKy() : 0);
            
            dataList.add(row);
        }

        // Giao dữ liệu cho BaseTableGUI để tính toán
        setTableData(dataList);
        
        // 🚀 ĐÃ THÊM: BẮT BUỘC PHẢI GỌI HÀM NÀY ĐỂ VẼ BẢNG
        renderCurrentPage(); 
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
        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

        // 1. Lấy ID từ Vector để đảm bảo tìm đúng đối tượng trong danh sách gốc
        Vector selectedRowData = (Vector) fullDataList.get(absoluteIndex);
        int idCanTim = (int) selectedRowData.get(0); 

        // 2. Tìm đối tượng trong BUS (Dùng loop cho an toàn nếu BUS chưa có findById)
        Entity.nganhETT nganhCu = null;
        for (Entity.nganhETT item : busNganh.ds) {
            if (item.getIdnganh() == idCanTim) {
                nganhCu = item;
                break;
            }
        }

        if (nganhCu != null) {
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            updateNganh dialog = new updateNganh(topFrame, true, nganhCu);
            dialog.setVisible(true);

            if (dialog.xacNhan) {
                Entity.nganhETT nvMoi = dialog.getNganh();
                Vector rowData = new java.util.Vector();
                rowData.add(nvMoi.getIdnganh()); 
                rowData.add(nvMoi.getManganh());
                rowData.add(nvMoi.getTennganh());
                rowData.add(nvMoi.getN_tohopgoc());
                rowData.add(nvMoi.getN_chitieu());
                
                rowData.add(nvMoi.getN_diemsanthpt() != null ? nvMoi.getN_diemsanthpt() : "");
                rowData.add(nvMoi.getN_diemsanvsat() != null ? nvMoi.getN_diemsanvsat() : "");
                rowData.add(nvMoi.getN_diemsandgnl() != null ? nvMoi.getN_diemsandgnl() : "");
                rowData.add(nvMoi.getN_diemtrungtuyen() != null ? nvMoi.getN_diemtrungtuyen() : "");

                fullDataList.set(absoluteIndex, rowData);
                renderCurrentPage(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu gốc!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngành để sửa!");
    }
}
private void hienThiDialogXoa() {
    int row = table.getSelectedRow();
    if (row != -1) {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        deleteNganh dialog = new deleteNganh(topFrame, true);
        dialog.setVisible(true);
        
        if (dialog.getXacNhanXoa()) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            Vector selectedRowData = (Vector) fullDataList.get(absoluteIndex);
            int idCanTim = (int) selectedRowData.get(0); 

            Entity.nganhETT nganhCanXoa = null;
            for (Entity.nganhETT item : busNganh.ds) {
                if (item.getIdnganh() == idCanTim) {
                    nganhCanXoa = item;
                    break;
                }
            }
            
            if (nganhCanXoa != null && busNganh.xoaNganh(nganhCanXoa)) {
                fullDataList.remove(absoluteIndex);
                totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                
                if (currentPage > totalPages && totalPages > 0) {
                    currentPage = totalPages;
                }
                renderCurrentPage();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa ngành thất bại!");
            }
        }
    }
}

private void thucHienRefresh() {
        // 1. Tải lại dữ liệu mới nhất từ Database vào RAM
        busNganh.ds = null; // Phá RAM cũ đi cho chắc
        busNganh.layDanhSach(); 

        // 2. Xóa sạch dữ liệu cũ trên bảng (Vector hiển thị)
        fullDataList.clear();

        // 3. Đổ lại dữ liệu từ RAM vào fullDataList (Phải GIỐNG HỆT thứ tự cột của loadDataToTable)
        for (int i = 0; i < busNganh.ds.size(); i++) {
            Entity.nganhETT item = busNganh.ds.get(i);
            java.util.Vector row = new java.util.Vector();
            row.add(item.getIdnganh()); // Dùng ID thật, không dùng i+1 để tránh lỗi khi Sửa/Xóa
            row.add(item.getManganh());
            row.add(item.getTennganh());
            row.add(item.getN_tohopgoc());
            row.add(item.getN_chitieu());
            row.add(item.getN_diemsanthpt() != null ? item.getN_diemsanthpt() : "");
            row.add(item.getN_diemsanvsat() != null ? item.getN_diemsanvsat() : "");
            row.add(item.getN_diemsandgnl() != null ? item.getN_diemsandgnl() : "");
            
            // 🚀 ĐÃ SỬA: Đưa cột SL ĐK vào thay cho cột Điểm Trung Tuyển
            row.add(item.getSlDangKy() != null ? item.getSlDangKy() : 0);
            
            fullDataList.add(row);
        }

        // 4. Tính toán lại tổng số trang
        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        currentPage = 1; // Nhảy về trang 1 cho khỏi lỗi out of bounds
        
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
            
            // 🚀 ĐÃ XÓA DÒNG N_diemtrungtuyen ĐỂ KHÔNG BỊ LỆCH CỘT
            
            // Cột số 9: Số lượng đăng ký
            row.add(ct.getSlDangKy() != null ? ct.getSlDangKy() : 0);

            dsHienThi.add(row);
        }

        // Giao data cho hệ thống phân trang
        setTableData(dsHienThi);
        
        // 🚀 ĐÃ THÊM: Bắt buộc gọi hàm này để nó load UI lên màn hình
        renderCurrentPage();
        
        if(dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }

    private void hienThiChiTietNganh() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            // 1. Lấy ID từ Vector của dòng đang chọn trên UI
            Vector selectedRowData = (Vector) fullDataList.get(absoluteIndex);

            // 🔥 Vẫn là nguyên tắc cũ: Đảm bảo cột 0 của bạn đang chứa idnganh
            int idCanTim = (int) selectedRowData.get(0); 

            // 2. Quét danh sách gốc để tìm "chính chủ"
            Entity.nganhETT nganhChiTiet = null;
            for (Entity.nganhETT item : busNganh.ds) {
                if (item.getIdnganh() == idCanTim) { 
                    nganhChiTiet = item;
                    break;
                }
            }

            // 3. Có data thì mở Form, không thì báo lỗi (bắt case ngoại lệ)
            if (nganhChiTiet != null) {
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                detailNganh dialog = new detailNganh(topFrame, true, nganhChiTiet);
                dialog.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy dữ liệu chi tiết trong Database/RAM!");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngành để xem chi tiết!");
        }
    }
}
