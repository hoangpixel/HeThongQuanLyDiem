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

        // Kiểm tra xem người dùng có bấm nút "Thêm" (xacNhan = true) hay không
        if (dialog.xacNhan) {
        // QUAN TRỌNG: Bạn phải gọi BUS để lưu vào Database tại đây
        if (busNganh.themNganh(dialog.getNganh())) {
            JOptionPane.showMessageDialog(this, "Thêm ngành học thành công!");
            loadDataToTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào Database!");
        }
    }
    }

    private void hienThiDialogSua() {
    try {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 ngành!");
            return;
        }
        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
        
        // Luôn load lại danh sách để đảm bảo không bị lệch Index
        busNganh.layDanhSach();
        nganhETT nganhCu = nganhBUS.ds.get(absoluteIndex);
        
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        updateNganh dialog = new updateNganh(topFrame, true, nganhCu);
        dialog.setVisible(true);

        if (dialog.xacNhan) {
            loadDataToTable(); // Thông báo thành công đã có sẵn trong updateNganh.java của bạn
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi mở form sửa: " + e.getMessage());
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
        busNganh.ds = null; 
        fullDataList.clear();
        headerTable();
        loadDataToTable();
//        table.getColumnModel().getColumn(9).setMinWidth(0);
//        table.getColumnModel().getColumn(9).setMaxWidth(0);
//        table.getColumnModel().getColumn(9).setWidth(0);
    }
}
