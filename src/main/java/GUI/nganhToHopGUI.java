/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Entity.nganhToHopETT;
import BUS.nganhToHopBUS;
import FUNC_GUI.insertNganhToHop;
import FUNC_GUI.updateNganhToHop;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
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
        table.getColumnModel().getColumn(9).setMinWidth(0);
        table.getColumnModel().getColumn(9).setMaxWidth(0);
        table.getColumnModel().getColumn(9).setWidth(0);
        
        btnThem.addActionListener(e -> hienThiThem());
        btnSua.addActionListener(e -> hienThiSua());
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
        public void loadDataToTable() {

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
            
            nganhToHopETT data = bus.ds.get(absoluteIndex);
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
