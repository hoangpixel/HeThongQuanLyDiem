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
import Entity.diemCongETT;
import Entity.thiSinhXetTuyenETT;

import FUNC_GUI.insertDiemCong;
import FUNC_GUI.updateDiemCong;
import FUNC_GUI.deleteDiemCong;
import FUNC_GUI.deleteNguyenVong;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LE MINH HUY
 */

public class diemCongXetTuyenGUI extends BaseTableGUI {
    private boolean xacNhan = false;
    public diemCongXetTuyenGUI() {
        super();

        setTableNameForTitle("Quản lý Điểm cộng");
        headerTable();

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());

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
}

