/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.chungChiBUS;
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
        btnReFresh.addActionListener(e -> thucHienRefresh()); // Bấm nút Refresh là load lại kho
        btnTimKiem.addActionListener(e -> thucHienTimKiem());

        // 3. Tự động bưng dữ liệu lên
        loadDataToTable();
        loadComboBox();
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

    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        ArrayList<chungChiETT> dskq = busCC.layDanhSach(); // Tạm lấy full để lọc tay nếu BUS chưa có hàm timKiem
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
            }

            // 3. Tính toán lại phân trang và nhảy tới trang cuối hoặc trang chứa dữ liệu mới
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

            currentPage = totalPages;

            renderCurrentPage();
        }

    private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Logic tính Index thực tế từ trang hiện tại
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; 
        
            chungChiETT ccCu = busCC.ds.get(absoluteIndex);
            
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

            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            Entity.chungChiETT itemXoa = busCC.ds.get(absoluteIndex);

            if (busCC.xoaCC(itemXoa.getIdCc())) {

                fullDataList.remove(absoluteIndex);

                totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

                if (currentPage > totalPages && totalPages > 0) {
                    currentPage = totalPages;
                }

                renderCurrentPage();
                
                JOptionPane.showMessageDialog(this, "Xóa chứng chỉ thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa chứng chỉ thất bại");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chứng chỉ trên bảng để xóa!");
    }
}

    private void thucHienRefresh() {
        // Tải lại dữ liệu mới nhất từ DB
        busCC.layDanhSach(); 

        // Xóa và đổ lại fullDataList để đồng bộ với BaseTableGUI
        fullDataList.clear();
        for (int i = 0; i < busCC.ds.size(); i++) {
            chungChiETT item = busCC.ds.get(i);
            Vector row = new Vector();
            row.add(i + 1);
            row.add(item.getCccd());
            row.add(item.getLoaiChungChi());
            row.add(item.getDiemChungChi());
            row.add(item.getDiemQuyDoi());
            row.add(item.getDiemCong());
            fullDataList.add(row);
        }

        // Tính toán lại phân trang
        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
        renderCurrentPage();
    }
}
