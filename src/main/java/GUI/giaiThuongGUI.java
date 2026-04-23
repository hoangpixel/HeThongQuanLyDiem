/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.giaiThuongBUS;
import BUS.phanQuyenBUS;
import Entity.giaiThuongETT;
import FUNC_GUI.insertGiaiThuong;
import FUNC_GUI.updateGiaiThuong;
import FUNC_GUI.deleteGiaiThuong;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
/**
 *
 * @author Dat
 */
public class giaiThuongGUI extends BaseTableGUI {
    private giaiThuongBUS busGT = new giaiThuongBUS();

    public giaiThuongGUI() {
        super();
        setTableNameForTitle("Quản Lý Giải Thưởng");
        headerTable();
        
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());

        loadDataToTable();
        loadComboBox();
        phanQuyenGiaoDien();
    }

    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID"); header.add("CCCD"); 
        header.add("Cấp Giải");
        header.add("Môn"); 
        header.add("Loại Giải"); 
        header.add("Điểm Có Môn"); 
        header.add("Điểm Không Môn");
        tableModel.setColumnIdentifiers(header);
    }

    public void loadComboBox() {
        cbxTimKiem.removeAllItems();
        cbxTimKiem.addItem("ID Giải");
        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("Cấp Giải");
        cbxTimKiem.addItem("Mã Môn");
        cbxTimKiem.addItem("Loại Giải");
    }
    
    private void phanQuyenGiaoDien() {
        String bangHienTai = "xt_giathuong";
        
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
        ArrayList<giaiThuongETT> dskq = busGT.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();

        for (giaiThuongETT ct : dskq) 
        {
            Vector row = new Vector();
            row.add(ct.getIdGt());
            row.add(ct.getCccd());
            row.add(ct.getCapGiai());
            row.add(ct.getMaMon());
            row.add(ct.getLoaiGiai());
            row.add(ct.getDiemCongCoMon());
            row.add(ct.getDiemCongKhongMon());
            
            dsHienThi.add(row);
        }
        setTableData(dsHienThi);
    }

    public void loadDataToTable() {
        if (busGT.ds == null) {
            busGT.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (giaiThuongETT ct : busGT.ds) {
            Vector row = new Vector();
            row.add(ct.getIdGt());
            row.add(ct.getCccd());
            row.add(ct.getCapGiai());
            row.add(ct.getMaMon());
            row.add(ct.getLoaiGiai());
            row.add(ct.getDiemCongCoMon());
            row.add(ct.getDiemCongKhongMon());
            dataList.add(row); // ✅ CHỈ add vào list
        }

        // 🔥 CHỈ GỌI DÒNG NÀY
        setTableData(dataList);
    }

    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertGiaiThuong dialog = new insertGiaiThuong(topFrame, true);
        dialog.setVisible(true);

        if (dialog.xacNhanThem()) {
            giaiThuongETT gt = dialog.getGiaiThuong();

            // 🔥 Convert object → Vector
            Vector row = new Vector();
            row.add(gt.getIdGt());
            row.add(gt.getCccd());
            row.add(gt.getCapGiai());
            row.add(gt.getMaMon());
            row.add(gt.getLoaiGiai());
            row.add(gt.getDiemCongCoMon());
            row.add(gt.getDiemCongKhongMon());

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
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; // 🔥 Bí quyết chống lỗi phân trang
            
            // 2. Lấy đối tượng cũ ra và ném vào Form Sửa
            Entity.giaiThuongETT giaiThuongCu = busGT.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            // Giả sử form của bạn tên là updateGiaiThuong
            updateGiaiThuong dialog = new updateGiaiThuong(topFrame, true, giaiThuongCu);
            dialog.setVisible(true); // Form Sửa hiện lên
            
            // 3. Sau khi người dùng Sửa và bấm LƯU thành công
            if (dialog.xacNhanThem()) { 
                Entity.giaiThuongETT gtMoi = dialog.getGiaiThuong();
                
                // 🔥 Ép kiểu đối tượng mới thành Vector y như hàm Thêm
                Vector rowData = new java.util.Vector();
                rowData.add(gtMoi.getIdGt());
                rowData.add(gtMoi.getCccd());
                rowData.add(gtMoi.getCapGiai());
                rowData.add(gtMoi.getMaMon());
                rowData.add(gtMoi.getLoaiGiai());
                rowData.add(gtMoi.getDiemCongCoMon());
                rowData.add(gtMoi.getDiemCongKhongMon());

                // 🔥 Đè cái Vector mới này vào đúng vị trí cũ trong fullDataList
                fullDataList.set(absoluteIndex, rowData);

                // 🔥 Render lại đúng trang hiện tại (Tốc độ bàn thờ, không lag, không mất trang)
                renderCurrentPage(); 
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một giải thưởng trên bảng để sửa!");
        }
    }

    private void hienThiDialogXoa() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Hỏi lại cho chắc ăn (Hạn chế xóa nhầm)
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                deleteGiaiThuong dialog = new deleteGiaiThuong(topFrame, true);
                dialog.setVisible(true);
                
                if(dialog.getXacNhanXoa())
                {
                // 2. Tính Index thực sự y như hàm Sửa
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                Entity.giaiThuongETT gtCanXoa = busGT.ds.get(absoluteIndex);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (busGT.xoaGT(gtCanXoa.getIdGt())) {
                    
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
                    JOptionPane.showMessageDialog(this, "Xóa nguyện vọng thất bại");
                }
            }
        }
    }

    private void thucHienRefresh() {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);
        
        busGT.layDanhSach();

        fullDataList.clear();
        List<Vector> dataList = new ArrayList<>();

        for (giaiThuongETT ct : busGT.ds) 
        {
            Vector row = new Vector();
            row.add(ct.getIdGt());
            row.add(ct.getCccd());
            row.add(ct.getCapGiai());
            row.add(ct.getMaMon());
            row.add(ct.getLoaiGiai());
            row.add(ct.getDiemCongCoMon());
            row.add(ct.getDiemCongKhongMon());

            dataList.add(row);
        }

        setTableData(dataList);
    }
}
