/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.chungChiBUS;
import BUS.phanQuyenBUS;
import Entity.chungChiETT;
import FUNC_GUI.insertChungChi;
import FUNC_GUI.updateChungChi; 
import FUNC_GUI.deleteChungChi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import EXCEL.ExcelHelper;
/**
 *
 * @author Dat
 */
public class chungChiGUI extends BaseTableGUI {
    private chungChiBUS busCC = new chungChiBUS();

    public chungChiGUI() {
        super();
        
        // 1. Đặt tên hiển thị cho GroupBox và kẻ lại dàn cột
        setTableNameForTitle("Chứng Chỉ Ngoại Ngữ"); 
        headerTable();
        
        // 2. Nối dây điện cho các nút chức năng
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> hienThiChiTietCC());

        // 3. Tự động bưng dữ liệu lên
        loadDataToTable();
        loadComboBox();
        phanQuyenGiaoDien();
    }

    // Thiết lập danh sách các cột hiển thị cho Chứng chỉ
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("STT");
        header.add("CCCD Thí Sinh");
        header.add("Loại Chứng Chỉ");
        header.add("Điểm Gốc");
        header.add("Điểm Quy Đổi");
        header.add("Điểm Cộng");
        tableModel.setColumnIdentifiers(header);
    }

    public void loadComboBox() {
        cbxTimKiem.removeAllItems();
        cbxTimKiem.addItem("ID CC");
        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("Loại CC");
        cbxTimKiem.addItem("Điểm Gốc");
        cbxTimKiem.addItem("Điểm Quy Đổi");
        cbxTimKiem.addItem("Điểm Cộng");
    }

    public void loadDataToTable() {
        if (busCC.layDanhSach() == null) {
            busCC.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();
        for (chungChiETT item : busCC.ds) {
            Vector row = new Vector();
            row.add(item.getIdCc());
            row.add(item.getCccd());
            row.add(item.getLoaiChungChi());
            row.add(item.getDiemChungChi());
            row.add(item.getDiemQuyDoi());
            row.add(item.getDiemCong());
            dataList.add(row);
        }
        setTableData(dataList);
    }
    
    private void phanQuyenGiaoDien() {
        String bangHienTai = "xt_chungchi";
        
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        // btnChiTiet.setEnabled(false);
        // btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

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
    
    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }
        
        ArrayList<chungChiETT> dskq = busCC.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();

        for (chungChiETT item : dskq) 
            {
                Vector row = new Vector();
                row.add(item.getIdCc());
                row.add(item.getCccd());
                row.add(item.getLoaiChungChi());
                row.add(item.getDiemChungChi());
                row.add(item.getDiemQuyDoi());
                row.add(item.getDiemCong());

                dsHienThi.add(row);
            }

        setTableData(dsHienThi);
        if (dsHienThi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chứng chỉ phù hợp");
        }
    }

    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertChungChi dialog = new insertChungChi(topFrame, true);
        dialog.setVisible(true);

        if (dialog.isXacNhan()) { 
        Entity.chungChiETT cc = dialog.getChungChi();

            // 2. Làm mới toàn bộ fullDataList

                Vector row = new Vector();
                row.add(cc.getIdCc());
                row.add(cc.getCccd());
                row.add(cc.getLoaiChungChi());
                row.add(cc.getDiemChungChi());
                row.add(cc.getDiemQuyDoi());
                row.add(cc.getDiemCong());

                fullDataList.add(row);
            
            // 3. Tính toán lại phân trang và nhảy tới trang cuối hoặc trang chứa dữ liệu mới
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            currentPage = totalPages;
            renderCurrentPage();
        }
    }

    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Logic tính Index thực tế từ trang hiện tại
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; 
        
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            chungChiETT ccCu = busCC.findById(id);
            
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

             updateChungChi dialog = new updateChungChi(topFrame, true, ccCu);
             dialog.setVisible(true);
            if (dialog.isXacNhan()) { 
                Entity.chungChiETT ccMoi = dialog.getChungChi();
                
                // 🔥 Ép kiểu đối tượng mới thành Vector y như hàm Thêm
                Vector rowData = new java.util.Vector();
                rowData.add(ccMoi.getIdCc());
                rowData.add(ccMoi.getCccd());
                rowData.add(ccMoi.getLoaiChungChi());
                rowData.add(ccMoi.getDiemChungChi());
                rowData.add(ccMoi.getDiemQuyDoi());
                rowData.add(ccMoi.getDiemCong());

                // 🔥 Đè cái Vector mới này vào đúng vị trí cũ trong fullDataList
                fullDataList.set(absoluteIndex, rowData);

                // 🔥 Render lại đúng trang hiện tại (Tốc độ bàn thờ, không lag, không mất trang)
                renderCurrentPage(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!");
        }
    }

    private void hienThiDialogXoa() {
    int row = table.getSelectedRow();
    if (row != -1) {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        
        deleteChungChi dialog = new deleteChungChi(topFrame, true);
        dialog.setVisible(true);

        if (dialog.getXacNhanXoa()) {

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            Entity.chungChiETT itemXoa = busCC.findById(id);

            if (itemXoa != null && busCC.xoaCC(itemXoa.getIdCc())) {
                // Xóa khỏi fullDataList để đồng bộ UI
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                fullDataList.remove(absoluteIndex);

                totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

                if (currentPage > totalPages && totalPages > 0) {
                    currentPage = totalPages;
                }

                renderCurrentPage();
                
//                JOptionPane.showMessageDialog(this, "Xóa chứng chỉ thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa chứng chỉ thất bại");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chứng chỉ trên bảng để xóa!");
    }
}

    public void thucHienRefresh() {
        // 1. Reset thanh tìm kiếm cho sạch sẽ
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);

        // 2. Tải lại dữ liệu mới nhất từ Database vào RAM
        busCC.ds = null; // Phá RAM cũ đi để ép nó chọc xuống DB lấy cái mới nhất
        busCC.layDanhSach(); 

        // 3. Xóa sạch dữ liệu cũ trên bảng (Vector chứa full data)
        fullDataList.clear();

        // 4. Đổ lại dữ liệu từ RAM vào fullDataList (Phải GIỐNG HỆT thứ tự cột của loadDataToTable)
        if (busCC.ds != null) {
            for (int i = 0; i < busCC.ds.size(); i++) {
                Entity.chungChiETT item = busCC.ds.get(i);
                java.util.Vector row = new java.util.Vector();

                row.add(item.getIdCc()); 
                row.add(item.getCccd() != null ? item.getCccd() : "");
                row.add(item.getLoaiChungChi() != null ? item.getLoaiChungChi() : "");
                row.add(item.getDiemChungChi() != null ? item.getDiemChungChi() : "");
                row.add(item.getDiemQuyDoi() != null ? item.getDiemQuyDoi() : 0.0);
                row.add(item.getDiemCong() != null ? item.getDiemCong() : 0.0);

                fullDataList.add(row);
            }
        }

        // 5. Tính toán lại tổng số trang cho Pagination
        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        currentPage = 1; // Nhảy về trang 1 cho khỏi lỗi out of bounds

        // 6. Vẽ lại bảng
        renderCurrentPage();

        javax.swing.JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu chứng chỉ mới nhất từ Database!");
    }
    
    private void hienThiChiTietCC() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            Vector selectedRowData = fullDataList.get(absoluteIndex);

            int idCcCanTim = Integer.parseInt(selectedRowData.get(0).toString()); 
            Entity.chungChiETT chungChiCu = null;

            for (Entity.chungChiETT cc : busCC.ds) {
                if (cc.getIdCc() == idCcCanTim) {
                    chungChiCu = cc;
                    break;
                }
            }


            // Gọi dialog hiển thị
            if (chungChiCu != null) {
                JFrame topFrame = (JFrame) javax.swing.SwingUtilities.windowForComponent(this);
                FUNC_GUI.detailChungChi dialog = new FUNC_GUI.detailChungChi(topFrame, true, chungChiCu);
                dialog.setVisible(true);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xem chi tiết!");
        }
    }
    
    public void hienThiExcel()
    {
        javax.swing.JFrame topFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.windowForComponent(this);
        // Nhớ tạo JDialog excelChungChi nhé
        FUNC_GUI.excelChungChi dialog = new FUNC_GUI.excelChungChi(topFrame, true);
        dialog.setVisible(true);
        
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu Chứng chỉ");
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn file
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                // Gọi BUS xử lý và nhận thông báo kết quả (Cậu nhớ viết hàm này trong chungChiBUS)
                String thongBao = busCC.nhapDuLieuTuExcel(filePath);
                
                javax.swing.JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Xong xuôi thì làm mới lại bảng
                thucHienRefresh();
            }
        } 
        else if(dialog.getXacNhanExport())
        {
            // Lấy danh sách chứng chỉ mới nhất
            java.util.ArrayList<Entity.chungChiETT> fullDanhSach = busCC.layDanhSach();
            
            // Nhớ bổ sung hàm xuatDanhSachChungChiRaExcel bên trong class ExcelHelper
            ExcelHelper.xuatDanhSachChungChiRaExcel(fullDanhSach, this, "DanhSachChungChi");
        }
    }
}
