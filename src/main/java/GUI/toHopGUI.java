/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.toHopBUS;
import Entity.toHopETT;
import FUNC_GUI.insertToHop;
import FUNC_GUI.updateToHop;
import FUNC_GUI.deleteToHop;
import FUNC_GUI.detailToHop;
import EXCEL.ExcelHelper;
import FUNC_GUI.excelToHop;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author Dang Khoa
 */
public class toHopGUI extends BaseTableGUI {

    private toHopBUS busToHop = new toHopBUS();

    public toHopGUI() {
        super();

        setTableNameForTitle("Tổ Hợp Môn");

        headerTable();

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnChiTiet.addActionListener(e -> hienThiDialogChiTiet());
        btnExcel.addActionListener(e -> thucHienExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        
        // Override lại ComboBox sau khi base khởi tạo xong
        cbxTimKiem.removeAllItems();
        cbxTimKiem.addItem("ID Tổ Hợp");
        cbxTimKiem.addItem("Mã Tổ Hợp");
        cbxTimKiem.addItem("Môn 1");
        cbxTimKiem.addItem("Môn 2");
        cbxTimKiem.addItem("Môn 3");
        cbxTimKiem.addItem("Tên Tổ Hợp");

        loadDataToTable();
    }

    // ================= HEADER TABLE =================

    public void headerTable() {

        Vector<String> header = new Vector<>();

        header.add("ID Tổ Hợp");
        header.add("Mã Tổ Hợp");
        header.add("Môn 1");
        header.add("Môn 2");
        header.add("Môn 3");
        header.add("Tên Tổ Hợp");

        tableModel.setColumnIdentifiers(header);
    }

    // ================= LOAD DATA =================

    public void loadDataToTable() {

        if (busToHop.ds == null) {
            busToHop.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (toHopETT item : busToHop.ds) {

            Vector row = new Vector();

            row.add(item.getIdtohop());
            row.add(item.getMatohop());
            row.add(item.getMon1());
            row.add(item.getMon2());
            row.add(item.getMon3());
            row.add(item.getTentohop());

            dataList.add(row);
        }

        setTableData(dataList);
    }

    // ================= THÊM =================

    private void hienThiDialogThem() {

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        insertToHop dialog = new insertToHop(topFrame, true);

        dialog.setVisible(true);

        if (dialog.getXacNhan()) {

            toHopETT th = dialog.getToHopETT();

            Vector row = new Vector();

            row.add(fullDataList.size() + 1);
            row.add(th.getMatohop());
            row.add(th.getMon1());
            row.add(th.getMon2());
            row.add(th.getMon3());
            row.add(th.getTentohop());

            fullDataList.add(row);

            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

            currentPage = totalPages;

            renderCurrentPage();
        }
    }

    // ================= SỬA =================

    private void hienThiDialogSua() {

        int row = table.getSelectedRow();

        if (row != -1) {

            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            toHopETT toHopCu = busToHop.ds.get(absoluteIndex);

            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);

            updateToHop dialog = new updateToHop(topFrame, true, toHopCu);

            dialog.setVisible(true);

            if (dialog.getXacNhan()) {

                toHopETT thMoi = dialog.getToHopETT();

                Vector rowData = new Vector();

                rowData.add(absoluteIndex + 1);
                rowData.add(thMoi.getMatohop());
                rowData.add(thMoi.getMon1());
                rowData.add(thMoi.getMon2());
                rowData.add(thMoi.getMon3());
                rowData.add(thMoi.getTentohop());

                fullDataList.set(absoluteIndex, rowData);

                renderCurrentPage();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tổ hợp để sửa!");
        }
    }

    // ================= XÓA =================

    private void hienThiDialogXoa() {

        int row = table.getSelectedRow();

        if (row != -1) {

            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);

            deleteToHop dialog = new deleteToHop(topFrame, true);

            dialog.setVisible(true);

            if (dialog.getXacNhanXoa()) {

                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

                toHopETT toHopXoa = busToHop.ds.get(absoluteIndex);

                if (busToHop.xoaToHop(toHopXoa)) {

                    fullDataList.remove(absoluteIndex);

                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

                    if (currentPage > totalPages && totalPages > 0) {
                        currentPage = totalPages;
                    }

                    renderCurrentPage();

                } else {
                    JOptionPane.showMessageDialog(this, "Xóa tổ hợp thất bại");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tổ hợp cần xóa!");
        }
    }
    
    // ================= CHI TIẾT =================

    private void hienThiDialogChiTiet() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tổ hợp để xem chi tiết!");
            return;
        }

        int modelIndex = table.convertRowIndexToModel(row);
        int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

        toHopETT th = busToHop.ds.get(absoluteIndex);

        if (th == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tổ hợp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        detailToHop dialog = new detailToHop(topFrame, true, th);
        dialog.setVisible(true);
    }

    // ================= REFRESH =================

    private void thucHienRefresh() {

        busToHop.layDanhSach();

        fullDataList.clear();

        for (int i = 0; i < busToHop.ds.size(); i++) {

            toHopETT item = busToHop.ds.get(i);

            Vector row = new Vector();

            row.add(i + 1);
            row.add(item.getMatohop());
            row.add(item.getMon1());
            row.add(item.getMon2());
            row.add(item.getMon3());
            row.add(item.getTentohop());

            fullDataList.add(row);
        }

        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

        renderCurrentPage();

        JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu tổ hợp môn");
    }
    
    private void thucHienExcel(){
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelToHop dialog = new excelToHop(topFrame, true);
        dialog.setVisible(true);

    // ================= IMPORT =================
    
        if(dialog.getXacNhanImport())
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");

            javax.swing.filechooser.FileNameExtensionFilter filter =
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Excel Files (*.xls, *.xlsx)", "xls", "xlsx");

            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION)
            {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                String thongBao = busToHop.nhapDuLieuTuExcel(filePath);

                JOptionPane.showMessageDialog(
                        this,
                        thongBao,
                        "Kết quả Nhập Excel",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh lại bảng
                busToHop.ds = null;
                loadDataToTable();
            }
        }

    // ================= EXPORT =================

        else if(dialog.getXacNhanExport()){
            ArrayList<toHopETT> fullDanhSach = busToHop.layDanhSach();

            ExcelHelper.xuatDanhSachToHopRaExcel(
                    fullDanhSach,
                    this,
                    "DanhSachToHop"
            );
        }
    }
    
    private void thucHienTimKiem()
    {
        String tim = txtTimKiem.getText().toLowerCase().trim();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }

        ArrayList<toHopETT> dskq = busToHop.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();

        for (toHopETT th : dskq)
        {
            Vector row = new Vector();

            row.add(th.getIdtohop());
            row.add(th.getMatohop());
            row.add(th.getMon1());
            row.add(th.getMon2());
            row.add(th.getMon3());
            row.add(th.getTentohop());

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);

        if (dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }
    
}
