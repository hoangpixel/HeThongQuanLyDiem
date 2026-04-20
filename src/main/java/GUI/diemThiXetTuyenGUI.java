package GUI;

import BUS.diemThiBUS;
import BUS.phanQuyenBUS;
import Entity.diemThiETT;
import EXCEL.ExcelHelper;
import FUNC_GUI.insertDiemThi;
import FUNC_GUI.updateDiemThi;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class diemThiXetTuyenGUI extends BaseTableGUI {

    private final diemThiBUS bus = new diemThiBUS();

    public diemThiXetTuyenGUI() {
        super();
        setTableNameForTitle("Bảng điểm thi xét tuyển");
        headerTable();

        cbxTimKiem.setModel(new DefaultComboBoxModel<>(new String[] {
            "Tìm theo CCCD",
            "Tìm theo Số báo danh",
            "Tìm theo Phương thức",
            "Tìm theo ID"
        }));

        btnThem.addActionListener(e -> hienThiDialogThem());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e -> thucHienXoa());
        btnExcel.addActionListener(e -> ExcelHelper.xuatJTableRaExcel(table, this, "BangDiemThi"));
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> thucHienChiTiet());

        loadDataToTable();
        phanQuyenGiaoDien();
    }

    public void headerTable() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("CCCD");
        header.add("SBD");
        header.add("P.Thức");

        header.add("TO");
        header.add("LI");
        header.add("HO");
        header.add("SI");
        header.add("SU");
        header.add("DI");
        header.add("VA");

        header.add("N1_THI");
        header.add("N1_CC");

        header.add("CNCN");
        header.add("CNNN");
        header.add("TI");
        header.add("KTPL");

        header.add("NL1");

        header.add("NK1");
        header.add("NK2");
        header.add("NK3");
        header.add("NK4");
        header.add("NK5");
        header.add("NK6");

        tableModel.setColumnIdentifiers(header);
    }

    public void loadDataToTable() {
        bus.layDanhSach();

        List<Vector> dataList = new ArrayList<>();
        if (diemThiBUS.ds != null) {
            for (diemThiETT item : diemThiBUS.ds) {
                if (item == null) {
                    continue;
                }
                Vector row = new Vector();
                row.add(item.getIddiemthi());
                row.add(item.getCccd());
                row.add(item.getSobaodanh());
                row.add(item.getdPhuongthuc());

                row.add(item.getTo());
                row.add(item.getLi());
                row.add(item.getHo());
                row.add(item.getSi());
                row.add(item.getSu());
                row.add(item.getDi());
                row.add(item.getVa());

                row.add(item.getN1Thi());
                row.add(item.getN1Cc());

                row.add(item.getCncn());
                row.add(item.getCnnn());
                row.add(item.getTi());
                row.add(item.getKtpl());

                row.add(item.getNl1());

                row.add(item.getNk1());
                row.add(item.getNk2());
                row.add(item.getNk3());
                row.add(item.getNk4());
                row.add(item.getNk5());
                row.add(item.getNk6());

                dataList.add(row);
            }
        }

        setTableData(dataList);
    }

    private void phanQuyenGiaoDien() {
        String bangHienTai = "xt_diemthixettuyen";

        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }

        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                SwingUtilities.invokeLater(() -> {
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
                });
            }
        });
    }

    private Integer getSelectedId() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return null;
        }
        int modelIndex = table.convertRowIndexToModel(row);
        Object idVal = tableModel.getValueAt(modelIndex, 0);
        if (idVal == null) {
            return null;
        }
        try {
            return Integer.parseInt(String.valueOf(idVal));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void hienThiDialogThem() {
        try {
            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
            insertDiemThi dialog = new insertDiemThi(frameOwner, true);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);

            if (!dialog.xacNhan) {
                return;
            }

            diemThiETT obj = dialog.getDiemThi();
            if (obj == null) {
                return;
            }

            String err = bus.validate(obj, true);
            if (err != null) {
                JOptionPane.showMessageDialog(this, err);
                return;
            }

            if (!bus.themDiemThi(obj)) {
                JOptionPane.showMessageDialog(this, "Thêm bảng điểm thi thất bại");
                return;
            }

            loadDataToTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi mở form Thêm: " + ex.getMessage());
        }
    }

    private void hienThiDialogSua() {
        try {
            Integer id = getSelectedId();
            if (id == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa");
                return;
            }

            // luôn đảm bảo ds mới nhất
            if (diemThiBUS.ds == null) {
                bus.layDanhSach();
            }

            diemThiETT current = bus.timTheoId(id);
            if (current == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu tương ứng để sửa");
                return;
            }

            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            java.awt.Frame frameOwner = owner instanceof java.awt.Frame ? (java.awt.Frame) owner : null;
            updateDiemThi dialog = new updateDiemThi(frameOwner, true, current);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);

            if (!dialog.xacNhan) {
                return;
            }

            diemThiETT updated = dialog.getDiemThi();
            if (updated == null) {
                return;
            }

            String err = bus.validate(updated, false);
            if (err != null) {
                JOptionPane.showMessageDialog(this, err);
                return;
            }

            if (!bus.suaDiemThi(updated)) {
                JOptionPane.showMessageDialog(this, "Cập nhật bảng điểm thi thất bại");
                return;
            }

            loadDataToTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi mở form Sửa: " + ex.getMessage());
        }
    }

    private void thucHienXoa() {
        try {
            Integer id = getSelectedId();
            if (id == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
                return;
            }

            int ok = JOptionPane.showConfirmDialog(this, "Xóa bảng điểm thi ID = " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) {
                return;
            }

            if (diemThiBUS.ds == null) {
                bus.layDanhSach();
            }

            diemThiETT current = bus.timTheoId(id);
            if (current == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu tương ứng để xóa");
                return;
            }

            if (!bus.xoaDiemThi(current)) {
                JOptionPane.showMessageDialog(this, "Xóa bảng điểm thi thất bại");
                return;
            }

            // remove khỏi fullDataList để render nhanh
            for (int i = 0; i < fullDataList.size(); i++) {
                Object rowId = fullDataList.get(i).get(0);
                if (rowId != null && String.valueOf(rowId).equals(String.valueOf(id))) {
                    fullDataList.remove(i);
                    break;
                }
            }

            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            renderCurrentPage();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage());
        }
    }

    private void thucHienRefresh() {
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Đã đồng bộ dữ liệu mới nhất từ Database");
    }

    private void thucHienTimKiem() {
        if (diemThiBUS.ds == null) {
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
        for (diemThiETT item : diemThiBUS.ds) {
            if (item == null) {
                continue;
            }

            String value;
            switch (mode) {
                case 0:
                    value = item.getCccd();
                    break;
                case 1:
                    value = item.getSobaodanh();
                    break;
                case 2:
                    value = item.getdPhuongthuc();
                    break;
                case 3:
                    value = String.valueOf(item.getIddiemthi());
                    break;
                default:
                    value = item.getCccd();
            }

            if (value != null && value.toLowerCase().contains(keyLower)) {
                Vector row = new Vector();
                row.add(item.getIddiemthi());
                row.add(item.getCccd());
                row.add(item.getSobaodanh());
                row.add(item.getdPhuongthuc());

                row.add(item.getTo());
                row.add(item.getLi());
                row.add(item.getHo());
                row.add(item.getSi());
                row.add(item.getSu());
                row.add(item.getDi());
                row.add(item.getVa());

                row.add(item.getN1Thi());
                row.add(item.getN1Cc());

                row.add(item.getCncn());
                row.add(item.getCnnn());
                row.add(item.getTi());
                row.add(item.getKtpl());

                row.add(item.getNl1());

                row.add(item.getNk1());
                row.add(item.getNk2());
                row.add(item.getNk3());
                row.add(item.getNk4());
                row.add(item.getNk5());
                row.add(item.getNk6());

                filtered.add(row);
            }
        }

        setTableData(filtered);
        if (filtered.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }

    private void thucHienChiTiet() {
        Integer id = getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xem chi tiết");
            return;
        }

        if (diemThiBUS.ds == null) {
            bus.layDanhSach();
        }
        diemThiETT current = bus.timTheoId(id);
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(current.getIddiemthi()).append('\n');
        sb.append("CCCD: ").append(current.getCccd()).append('\n');
        sb.append("SBD: ").append(current.getSobaodanh()).append('\n');
        sb.append("Phương thức: ").append(current.getdPhuongthuc()).append('\n');
        sb.append("TO: ").append(current.getTo()).append(" | LI: ").append(current.getLi()).append(" | HO: ").append(current.getHo()).append('\n');
        sb.append("SI: ").append(current.getSi()).append(" | SU: ").append(current.getSu()).append(" | DI: ").append(current.getDi()).append(" | VA: ").append(current.getVa()).append('\n');
        sb.append("N1_THI: ").append(current.getN1Thi()).append(" | N1_CC: ").append(current.getN1Cc()).append('\n');
        sb.append("CNCN: ").append(current.getCncn()).append(" | CNNN: ").append(current.getCnnn()).append(" | TI: ").append(current.getTi()).append(" | KTPL: ").append(current.getKtpl()).append('\n');
        sb.append("NL1: ").append(current.getNl1()).append('\n');
        sb.append("NK1: ").append(current.getNk1()).append(" | NK2: ").append(current.getNk2()).append(" | NK3: ").append(current.getNk3()).append('\n');
        sb.append("NK4: ").append(current.getNk4()).append(" | NK5: ").append(current.getNk5()).append(" | NK6: ").append(current.getNk6()).append('\n');

        JOptionPane.showMessageDialog(this, sb.toString(), "Chi tiết điểm thi", JOptionPane.INFORMATION_MESSAGE);
    }
}
