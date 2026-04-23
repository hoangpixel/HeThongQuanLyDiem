/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Entity.nganhToHopETT;
import BUS.nganhToHopBUS;
import EXCEL.ExcelHelper;
import FUNC_GUI.deleteNganhToHop;
import FUNC_GUI.detailNganhToHop;
import FUNC_GUI.excelNganhToHop;
import FUNC_GUI.insertNganhToHop;
import FUNC_GUI.updateNganhToHop;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author mhoang
 */
public class nganhToHopGUI extends BaseTableGUI{
    private nganhToHopBUS bus = new nganhToHopBUS();
    public nganhToHopGUI()
    {
        super();
        setTableNameForTitle("Ngành tổ hợp"); 
        
        headerTable();
        loadDataToTable();
        loadComboBox();
        
        table.getColumnModel().getColumn(9).setMinWidth(0);
        table.getColumnModel().getColumn(9).setMaxWidth(0);
        table.getColumnModel().getColumn(9).setWidth(0);
        
        btnThem.addActionListener(e -> hienThiThem());
        btnSua.addActionListener(e -> hienThiSua());
        btnXoa.addActionListener(e -> hienThiXoa());
        btnChiTiet.addActionListener(e -> hienThiChiTiet());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnExcel.addActionListener(e -> thucHienExcel());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
    }
    
    public void loadComboBox() 
    {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID");
        cbxTimKiem.addItem("Mã ngành");
        cbxTimKiem.addItem("Mã tổ hợp");
        cbxTimKiem.addItem("Môn 1");
        cbxTimKiem.addItem("Hệ số môn 1");
        cbxTimKiem.addItem("Môn 2");
        cbxTimKiem.addItem("Hệ số môn 2");
        cbxTimKiem.addItem("Môn 3");
        cbxTimKiem.addItem("Hệ số môn 3");
        cbxTimKiem.addItem("Độ lệch");
    }
    
    public void headerTable()
    {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("Mã ngành");
        header.add("Tổ hợp");
        header.add("Môn 1");
        header.add("Hệ số môn 1");
        header.add("Môn 2");
        header.add("Hệ số môn 2");
        header.add("Môn 3");
        header.add("Hệ số môn 3");
        header.add("Keys");
        header.add("Độ lệch");
        tableModel.setColumnIdentifiers(header);
    }
    
    public void loadDataToTable() 
    {

        if (bus.ds == null) {
            bus.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (nganhToHopETT ct : bus.ds) {
            Vector row = new Vector();
            row.add(ct.getIdNganhToHop());
            row.add(ct.getMaNganh());
            row.add(ct.getMaToHop());
            row.add(ct.getMon1());
            row.add(ct.getHeSoMon1());
            row.add(ct.getMon2());
            row.add(ct.getHeSoMon2());
            row.add(ct.getMon3());
            row.add(ct.getHeSoMon3());
            row.add(ct.getKey());
            row.add(ct.getDoLech());

            dataList.add(row);
        }

        setTableData(dataList);
    }
        
    private void hienThiThem()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        insertNganhToHop dialog = new insertNganhToHop(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhan())
        {
            nganhToHopETT nganhToHop = dialog.getNganhToHopETT();
            
            Vector row = new Vector();
            row.add(nganhToHop.getIdNganhToHop());
            row.add(nganhToHop.getMaNganh());
            row.add(nganhToHop.getMaToHop());
            row.add(nganhToHop.getMon1());
            row.add(nganhToHop.getHeSoMon1());
            row.add(nganhToHop.getMon2());
            row.add(nganhToHop.getHeSoMon2());
            row.add(nganhToHop.getMon3());
            row.add(nganhToHop.getHeSoMon3());
            row.add(nganhToHop.getKey());
            row.add(nganhToHop.getDoLech());

            fullDataList.add(row);

            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

            currentPage = totalPages;

            renderCurrentPage();
        }
    }
    
private void hienThiSua()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            
            // Lấy ID của dòng đang chọn
            Vector selectedRowData = fullDataList.get(absoluteIndex);
            int idCanTim = (int) selectedRowData.get(0);
            
            // Tìm đối tượng chuẩn xác trong danh sách gốc
            nganhToHopETT data = null;
            for (nganhToHopETT item : bus.ds) {
                if (item.getIdNganhToHop() == idCanTim) {
                    data = item;
                    break;
                }
            }
            
            if (data != null) {
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                updateNganhToHop dialog = new updateNganhToHop(topFrame, true, data);
                dialog.setVisible(true);
                if(dialog.getXacNhan())
                {
                   nganhToHopETT ngthMoi = dialog.getNganhToHopETT();
                   
                    Vector rowData = new Vector();
                    rowData.add(ngthMoi.getIdNganhToHop());
                    rowData.add(ngthMoi.getMaNganh());
                    rowData.add(ngthMoi.getMaToHop());
                    rowData.add(ngthMoi.getMon1());
                    rowData.add(ngthMoi.getHeSoMon1());
                    rowData.add(ngthMoi.getMon2());
                    rowData.add(ngthMoi.getHeSoMon2());
                    rowData.add(ngthMoi.getMon3());
                    rowData.add(ngthMoi.getHeSoMon3());
                    rowData.add(ngthMoi.getKey());
                    rowData.add(ngthMoi.getDoLech());

                    fullDataList.set(absoluteIndex, rowData);
                    renderCurrentPage(); 
                }
            }
        }
    }
    
private void hienThiXoa()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                deleteNganhToHop dialog = new deleteNganhToHop(topFrame, true);
                dialog.setVisible(true);
                
                if(dialog.getXacNhanXoa())
                {
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                    
                    // Lấy ID của dòng đang chọn
                    Vector selectedRowData = fullDataList.get(absoluteIndex);
                    int idCanTim = (int) selectedRowData.get(0);
                    
                    // Tìm đối tượng chuẩn xác để xóa
                    nganhToHopETT nvCanXoa = null;
                    for (nganhToHopETT item : bus.ds) {
                        if (item.getIdNganhToHop() == idCanTim) {
                            nvCanXoa = item;
                            break;
                        }
                    }
                    
                    if (nvCanXoa != null && bus.xoaNganhToHop(nvCanXoa)) {
                        fullDataList.remove(absoluteIndex);
                        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                        if (currentPage > totalPages && totalPages > 0) {
                            currentPage = totalPages;
                        }
                        renderCurrentPage();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa ngành tổ hợp thất bại");
                    }
            }
        }
    }
    
private void  hienThiChiTiet()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            
            // Lấy ID của dòng đang chọn
            Vector selectedRowData = fullDataList.get(absoluteIndex);
            int idCanTim = (int) selectedRowData.get(0);
            
            // Tìm đối tượng chuẩn xác
            nganhToHopETT data = null;
            for (nganhToHopETT item : bus.ds) {
                if (item.getIdNganhToHop() == idCanTim) {
                    data = item;
                    break;
                }
            }
            
            if (data != null) {
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                detailNganhToHop dialog = new detailNganhToHop(topFrame, true, data);
                dialog.setVisible(true);
            }
        }
    }
    
    private void thucHienRefresh()
    {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);
        
        bus.layDanhSach();

        fullDataList.clear();
        List<Vector> dataList = new ArrayList<>();
        for (nganhToHopETT ct : bus.ds) 
        {
            Vector row = new Vector();
            row.add(ct.getIdNganhToHop());
            row.add(ct.getMaNganh());
            row.add(ct.getMaToHop());
            row.add(ct.getMon1());
            row.add(ct.getHeSoMon1());
            row.add(ct.getMon2());
            row.add(ct.getHeSoMon2());
            row.add(ct.getMon3());
            row.add(ct.getHeSoMon3());
            row.add(ct.getKey());
            row.add(ct.getDoLech());

            dataList.add(row);
        }

        setTableData(dataList);
    }
    
    private void thucHienExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelNganhToHop dialog = new excelNganhToHop(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn file
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Gọi BUS xử lý và nhận thông báo kết quả
                String thongBao = bus.nhapDuLieuTuExcel(filePath);

                JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);

                // Xong xuôi thì làm mới lại cái bảng trên màn hình
                bus.ds = null;
                loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<nganhToHopETT> fullDanhSach = bus.layDanhSach();
            ExcelHelper.xuatDanhSachNganhToHopRaExcel(fullDanhSach, this, "DanhSachNganhToHop");
        }
    }
    
    private void thucHienTimKiem()
    {
        String tim = txtTimKiem.getText().toLowerCase().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        if(tim.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }
        
        ArrayList<nganhToHopETT> dskq = bus.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();
        
        for (nganhToHopETT ct : dskq) 
        {
            Vector row = new Vector();
            row.add(ct.getIdNganhToHop());
            row.add(ct.getMaNganh());
            row.add(ct.getMaToHop());
            row.add(ct.getMon1());
            row.add(ct.getHeSoMon1());
            row.add(ct.getMon2());
            row.add(ct.getHeSoMon2());
            row.add(ct.getMon3());
            row.add(ct.getHeSoMon3());
            row.add(ct.getKey());
            row.add(ct.getDoLech());

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);
        if(dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }
}
