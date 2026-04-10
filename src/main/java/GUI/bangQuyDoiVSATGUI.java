package GUI;

import BUS.bangQuyDoiBUS;
import Entity.bangQuyDoiETT;
import FUNC_GUI.deleteBangQuyDoi;
import FUNC_GUI.insertBangQuyDoi;
import FUNC_GUI.updateBangQuyDoi;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class bangQuyDoiVSATGUI extends BaseTableGUI {

    private final bangQuyDoiBUS bus = new bangQuyDoiBUS();

    public bangQuyDoiVSATGUI() {
        super();

        setTableNameForTitle("Bảng quy đổi V-SAT");
        headerTable();

        cbxTimKiem.setModel(new DefaultComboBoxModel<>(new String[] {
                "Tìm theo Mã quy đổi",
                "Tìm theo Môn",
                "Tìm theo Tổ hợp",
                "Tìm theo Phương thức"
        }));

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> hienThiDialogXoa());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        // Giả sử cột Phương thức nằm ở vị trí số 3, Tổ hợp số 4 (Nhớ đếm index từ 0
        // nha)
//        table.getColumnModel().getColumn(3).setMinWidth(0);
//        table.getColumnModel().getColumn(3).setMaxWidth(0);
//        table.getColumnModel().getColumn(3).setWidth(0);
//
//        table.getColumnModel().getColumn(4).setMinWidth(0);
//        table.getColumnModel().getColumn(4).setMaxWidth(0);
//        table.getColumnModel().getColumn(4).setWidth(0);
        loadDataToTable();
    }

    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("Mã quy đổi");
        header.add("Môn");
        header.add("Phương thức");
        header.add("Tổ hợp");
        header.add("Phân vị");
        header.add("Điểm A");
        header.add("Điểm B");
        header.add("Điểm C");
        header.add("Điểm D");
        tableModel.setColumnIdentifiers(header);
    }

    public void loadDataToTable() {
        bus.layDanhSach();

        List<Vector> dataList = new ArrayList<>();
        if (bangQuyDoiBUS.ds != null) {
            for (bangQuyDoiETT item : bangQuyDoiBUS.ds) {
                Vector row = new Vector();
                row.add(item.getIdqd());
                row.add(item.getMaQuyDoi());
                row.add(item.getMon());
                row.add(item.getPhuongThuc());
                row.add(item.getToHop());
                row.add(item.getPhanVi());
                row.add(item.getDiemA());
                row.add(item.getDiemB());
                row.add(item.getDiemC());
                row.add(item.getDiemD());
                dataList.add(row);
            }
        }

        setTableData(dataList);
    }

    private void hienThiDialogThem() {
        try {
            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
            insertBangQuyDoi dialog = new insertBangQuyDoi(frameOwner, true);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);

            if (dialog.xacNhan) {
                bangQuyDoiETT item = dialog.getQuyDoi();
                if (item != null) {
                    Vector row = new Vector();
                    row.add(item.getIdqd());
                    row.add(item.getMaQuyDoi());
                    row.add(item.getMon());
                    row.add(item.getPhuongThuc());
                    row.add(item.getToHop());
                    row.add(item.getPhanVi());
                    row.add(item.getDiemA());
                    row.add(item.getDiemB());
                    row.add(item.getDiemC());
                    row.add(item.getDiemD());

                    fullDataList.add(row);
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    currentPage = totalPages;
                    renderCurrentPage();
                } else {
                    thucHienRefresh();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi mở form Thêm: " + ex.getMessage());
        }
    }

    private void hienThiDialogSua() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mốc quy đổi trên bảng để sửa!");
                return;
            }

            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            if (bangQuyDoiBUS.ds == null || absoluteIndex < 0 || absoluteIndex >= bangQuyDoiBUS.ds.size()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu tương ứng để sửa");
                return;
            }

            bangQuyDoiETT cu = bangQuyDoiBUS.ds.get(absoluteIndex);
            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
            updateBangQuyDoi dialog = new updateBangQuyDoi(frameOwner, true, cu);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);

            if (dialog.xacNhan) {
                bangQuyDoiETT moi = dialog.getQuyDoi();
                Vector rowData = new Vector();
                rowData.add(moi.getIdqd());
                rowData.add(moi.getMaQuyDoi());
                rowData.add(moi.getMon());
                rowData.add(moi.getPhuongThuc());
                rowData.add(moi.getToHop());
                rowData.add(moi.getPhanVi());
                rowData.add(moi.getDiemA());
                rowData.add(moi.getDiemB());
                rowData.add(moi.getDiemC());
                rowData.add(moi.getDiemD());

                fullDataList.set(absoluteIndex, rowData);
                renderCurrentPage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi mở form Sửa: " + ex.getMessage());
        }
    }

    private void hienThiDialogXoa() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mốc quy đổi trên bảng để xóa!");
                return;
            }

            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
            deleteBangQuyDoi dialog = new deleteBangQuyDoi(frameOwner, true);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);

            if (!dialog.getXacNhanXoa()) {
                return;
            }

            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;

            if (bangQuyDoiBUS.ds == null || absoluteIndex < 0 || absoluteIndex >= bangQuyDoiBUS.ds.size()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu tương ứng để xóa");
                return;
            }

            bangQuyDoiETT canXoa = bangQuyDoiBUS.ds.get(absoluteIndex);
            if (!bus.xoaQuyDoi(canXoa)) {
                JOptionPane.showMessageDialog(this, "Xóa mốc quy đổi thất bại");
                return;
            }

            fullDataList.remove(absoluteIndex);
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            if (totalPages == 0)
                totalPages = 1;
            if (currentPage > totalPages)
                currentPage = totalPages;
            renderCurrentPage();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi mở form Xóa: " + ex.getMessage());
        }
    }

    private void thucHienRefresh() {
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu mới nhất từ Database");
    }

    private void thucHienTimKiem() {
        if (bangQuyDoiBUS.ds == null) {
            bus.layDanhSach();
        }

        String keyword = txtTimKiem.getText() != null ? txtTimKiem.getText().trim() : "";
        if (keyword.isEmpty()) {
            loadDataToTable();
            return;
        }

        int mode = cbxTimKiem.getSelectedIndex();
        String keyLower = keyword.toLowerCase();

        List<Vector> filtered = new ArrayList<>();
        for (bangQuyDoiETT item : bangQuyDoiBUS.ds) {
            if (item == null)
                continue;

            String value;
            switch (mode) {
                case 0:
                    value = item.getMaQuyDoi();
                    break;
                case 1:
                    value = item.getMon();
                    break;
                case 2:
                    value = item.getToHop();
                    break;
                case 3:
                    value = item.getPhuongThuc();
                    break;
                default:
                    value = item.getMaQuyDoi();
            }

            if (value != null && value.toLowerCase().contains(keyLower)) {
                Vector row = new Vector();
                row.add(item.getIdqd());
                row.add(item.getMaQuyDoi());
                row.add(item.getMon());
                row.add(item.getPhuongThuc());
                row.add(item.getToHop());
                row.add(item.getPhanVi());
                row.add(item.getDiemA());
                row.add(item.getDiemB());
                row.add(item.getDiemC());
                row.add(item.getDiemD());
                filtered.add(row);
            }
        }

        setTableData(filtered);
        if (filtered.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }
}
