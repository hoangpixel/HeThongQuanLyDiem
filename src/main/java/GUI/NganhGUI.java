package GUI;

import BUS.nganhBUS;
import Entity.nganhETT;
import FUNC_GUI.insertNganh;
import FUNC_GUI.updateNganh;
import FUNC_GUI.updateNguyenVong;
import FUNC_GUI.deleteNganh;
import FUNC_GUI.deleteNguyenVong;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
        super(); // Kế thừa toàn bộ giao diện chuẩn từ BaseTableGUI
        
        // 1. Đặt tên hiển thị cho GroupBox và kẻ lại dàn cột
        setTableNameForTitle("Ngành Học"); 
        headerTable();
        
        // 2. Nối dây điện cho các nút chức năng
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnReFresh.addActionListener(e -> thucHienRefresh()); // Bấm nút Refresh là load lại kho
        
        // 3. Vừa mở phòng ra là tự động bưng dữ liệu lên bàn ngay
        loadDataToTable();
    }

    // Thiết lập danh sách các cột hiển thị (Header)
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID Ngành");
        header.add("Mã Ngành");
        header.add("Tên Ngành");
        header.add("Tổ Hợp Gốc");
        header.add("Chỉ Tiêu");
        header.add("Điểm Sàn");
        header.add("Điểm Chuẩn");
        tableModel.setColumnIdentifiers(header);
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
            row.add(item.getN_diemsan() != null ? item.getN_diemsan() : "");
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
            row.add(nv.getN_diemsan() != null ? nv.getN_diemsan() : "");
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
        
            // 2. Lấy đối tượng cũ ra và ném vào Form Sửa
            Entity.nganhETT nganhCu = busNganh.ds.get(absoluteIndex);
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
                rowData.add(nvMoi.getN_diemsan() != null ? nvMoi.getN_diemsan() : "");
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
                // 2. Tính Index thực sự y như hàm Sửa
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                Entity.nganhETT nganhCanXoa = busNganh.ds.get(absoluteIndex);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (busNganh.xoaNganh(nganhCanXoa)) {
                    
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
        row.add(item.getN_diemsan() != null ? item.getN_diemsan() : "");
        row.add(item.getN_diemtrungtuyen() != null ? item.getN_diemtrungtuyen() : "");
        
        fullDataList.add(row);
    }

    // 4. Tính toán lại tổng số trang
    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
    
    // 5. Vẽ lại bảng
    renderCurrentPage();

    javax.swing.JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu mới nhất từ Database");
    }
}
