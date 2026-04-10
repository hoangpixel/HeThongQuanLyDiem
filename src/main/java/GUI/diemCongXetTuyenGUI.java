package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import BUS.diemCongBUS;
import BUS.thiSinhXetTuyenBUS;
import EXCEL.ExcelHelper;
import Entity.diemCongETT;
import Entity.thiSinhXetTuyenETT;
import FUNC_GUI.excelDiemCong;
import FUNC_GUI.insertDiemCong;
import FUNC_GUI.updateDiemCong;
import FUNC_GUI.deleteDiemCong;
import FUNC_GUI.deleteNguyenVong;
import FUNC_GUI.excelThiSinh;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LE MINH HUY
 */

public class diemCongXetTuyenGUI extends BaseTableGUI {
    diemCongBUS busDiemCong =new diemCongBUS();
    private boolean xacNhan = false;
    public diemCongXetTuyenGUI() {
        super();

        setTableNameForTitle("Quản lý Điểm cộng");
        headerTable();
        loadComboBox();
        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) hienThiChiTiet(row);
                }
            }
        });

        loadDataToTable();
    }
    
    // ================= HEADER =================
    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("CCCD");
        header.add("Mã ngành");
        header.add("Mã tổ hợp");
        header.add("Phương thức");
        header.add("Điểm cộng");
        header.add("Điểm ưu tiên");
        header.add("Điểm tổng");
        header.add("Ghi chú");

        tableModel.setColumnIdentifiers(header);
    }
    public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("Mã ngành");
        cbxTimKiem.addItem("Mã tổ hợp");
        cbxTimKiem.addItem("Phương thức");
    }
    // ================= LOAD DATA =================
    public void loadDataToTable() {
        diemCongBUS bus = new diemCongBUS();
        if (bus.ds == null) bus.layDanhSach();

        java.util.List<Vector> dataList = new java.util.ArrayList<>();

        for (diemCongETT dc : bus.ds) {
            Vector row = new Vector();
            row.add(dc.getIdDiemCong());
            row.add(dc.getTsCccd());
            row.add(dc.getMaNganh());
            row.add(dc.getMaToHop());
            row.add(dc.getPhuongThuc());
            row.add(dc.getDiemCC());
            row.add(dc.getDiemUtxt());
            row.add(dc.getDiemTong());
            row.add(dc.getGhiChu());

            dataList.add(row);
        }

        setTableData(dataList);
    }
    
    // ================= THÊM =================
    private void hienThiDialogThem() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertDiemCong dialog = new insertDiemCong(topFrame, true);
        dialog.setVisible(true);

        if (dialog.isXacNhan()) {
            diemCongETT dc = dialog.getDiemCong();

            tableModel.addRow(new Object[]{
                dc.getIdDiemCong(),
                dc.getTsCccd(),
                dc.getMaNganh(),
                dc.getMaToHop(),
                dc.getPhuongThuc(),
                dc.getDiemCC(),
                dc.getDiemUtxt(),
                dc.getDiemTong(),
                dc.getGhiChu()
            });
        }
    }

    // ================= SỬA =================
    private void hienThiDialogSua() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng để sửa!");
            return;
        }

        int index = table.convertRowIndexToModel(row);

        diemCongBUS bus = new diemCongBUS();
        bus.layDanhSach();

        diemCongETT dc = bus.ds.get(index);

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        updateDiemCong dialog = new updateDiemCong(topFrame, true, dc);
        dialog.setVisible(true);

        if (dialog.isXacNhan()) {
            diemCongETT updated = dialog.getDiemCong();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.setValueAt(updated.getIdDiemCong(), index, 0);
            model.setValueAt(updated.getTsCccd(), index, 1);
            model.setValueAt(updated.getMaNganh(), index, 2);
            model.setValueAt(updated.getMaToHop(), index, 3);
            model.setValueAt(updated.getPhuongThuc(), index, 4);
            model.setValueAt(updated.getDiemCC(), index, 5);
            model.setValueAt(updated.getDiemUtxt(), index, 6);
            model.setValueAt(updated.getDiemTong(), index, 7);
            model.setValueAt(updated.getGhiChu(), index, 8);
        }
    }

    // ================= XÓA =================
    private void hienThiDialogXoa() {
            int row = table.getSelectedRow();

        if (row != -1) {

            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            deleteDiemCong dialog = new deleteDiemCong(topFrame, true);
            dialog.setVisible(true);

            if (dialog.getXacNhanXoa()) {

                // 🔥 Tính index
                int modelIndex = table.convertRowIndexToModel(row);

                diemCongBUS bus = new diemCongBUS();
                bus.layDanhSach();

                diemCongETT dcCanXoa = bus.ds.get(modelIndex);

                if (bus.xoaDiemCong(dcCanXoa)) {
                    ((DefaultTableModel) table.getModel()).removeRow(modelIndex);
                }

                } else {
                    JOptionPane.showMessageDialog(this, "Xóa điểm cộng thất bại");
                }
            }
    }

    // ================= CHI TIẾT =================
    private void hienThiChiTiet(int row) {
        String cccd = table.getValueAt(row, 1).toString();

        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết điểm cộng");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        dialog.setLayout(new java.awt.FlowLayout());

        dialog.add(new JLabel("CCCD: " + cccd));

        dialog.setVisible(true);
    }
    public boolean isXacNhan() {
        return xacNhan;
    }
     public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelDiemCong dialog = new excelDiemCong(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Gọi BUS xử lý và nhận thông báo kết quả
            String thongBao = busDiemCong.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            diemCongBUS.ds = null;
            loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<diemCongETT> fullDanhSach = busDiemCong.layDanhSach();
            ExcelHelper.xuatDanhSachDiemCongRaExcel(fullDanhSach, this, "DanhSachDiemCong");
        }
    }
    public void thucHienTimKiem() {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            return;
        }

        if (diemCongBUS.ds == null) busDiemCong.layDanhSach();

        ArrayList<diemCongETT> result = new ArrayList<>();

        for (diemCongETT dc : diemCongBUS.ds) {
            switch (index) {
                case 0:
                    if (dc.getTsCccd().contains(tim)) result.add(dc);
                    break;
                case 1:
                    if (dc.getMaNganh().contains(tim)) result.add(dc);
                    break;
                case 2:
                    if (dc.getMaToHop().contains(tim)) result.add(dc);
                    break;
                case 3:
                    if (dc.getPhuongThuc().toLowerCase().contains(tim.toLowerCase())) result.add(dc);
                    break;
            }
        }

        ArrayList<Vector> data = new ArrayList<>();

        for (diemCongETT dc : result) {
            Vector row = new Vector();
            row.add(dc.getIdDiemCong());
            row.add(dc.getTsCccd());
            row.add(dc.getMaNganh());
            row.add(dc.getMaToHop());
            row.add(dc.getPhuongThuc());
            row.add(dc.getDiemCC());
            row.add(dc.getDiemUtxt());
            row.add(dc.getDiemTong());
            row.add(dc.getGhiChu());
            data.add(row);
        }

        setTableData(data);

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu");
        }
    }
    public void thucHienRefresh() {
        txtTimKiem.setText("");
        cbxTimKiem.setSelectedIndex(0);

        diemCongBUS.ds = null;
        loadDataToTable();
    }
}

