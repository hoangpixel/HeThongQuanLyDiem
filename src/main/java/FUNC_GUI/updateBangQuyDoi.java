package FUNC_GUI;

import BUS.bangQuyDoiBUS;
import Entity.bangQuyDoiETT;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class updateBangQuyDoi extends JDialog {

    public boolean xacNhan = false;
    private bangQuyDoiETT quyDoi;

    private final bangQuyDoiBUS bus = new bangQuyDoiBUS();

    private JTextField txtMaQuyDoi;
    private JTextField txtPhuongThuc;
    private JTextField txtToHop;
    private JTextField txtMon;
    private JTextField txtPhanVi;
    private JTextField txtDiemA;
    private JTextField txtDiemB;
    private JTextField txtDiemC;
    private JTextField txtDiemD;

    private final String oldMaQuyDoi;

    public updateBangQuyDoi(Frame parent, boolean modal, bangQuyDoiETT current) {
        super(parent, modal);
        this.quyDoi = current;
        this.oldMaQuyDoi = current != null ? current.getMaQuyDoi() : null;

        setTitle("Sửa mốc quy đổi V-SAT");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(520, 420));
        setLayout(new BorderLayout(10, 10));

        if (bangQuyDoiBUS.ds == null) {
            bus.layDanhSach();
        }

        add(buildFormPanel(), BorderLayout.CENTER);
        add(buildActionPanel(), BorderLayout.SOUTH);

        fillForm();

        pack();
        setLocationRelativeTo(parent);
    }

    public bangQuyDoiETT getQuyDoi() {
        return quyDoi;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtMaQuyDoi = new JTextField();
        txtPhuongThuc = new JTextField();
        txtToHop = new JTextField();
        txtMon = new JTextField();
        txtPhanVi = new JTextField();
        txtDiemA = new JTextField();
        txtDiemB = new JTextField();
        txtDiemC = new JTextField();
        txtDiemD = new JTextField();

        int row = 0;
        row = addRow(panel, gbc, row, "Mã quy đổi (*)", txtMaQuyDoi);
        row = addRow(panel, gbc, row, "Phương thức", txtPhuongThuc);
        row = addRow(panel, gbc, row, "Tổ hợp", txtToHop);
        row = addRow(panel, gbc, row, "Môn (*)", txtMon);
        row = addRow(panel, gbc, row, "Phân vị", txtPhanVi);
        row = addRow(panel, gbc, row, "Điểm A (*)", txtDiemA);
        row = addRow(panel, gbc, row, "Điểm B (*)", txtDiemB);
        row = addRow(panel, gbc, row, "Điểm C (*)", txtDiemC);
        row = addRow(panel, gbc, row, "Điểm D (*)", txtDiemD);

        return panel;
    }

    private int addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;
        panel.add(field, gbc);

        return row + 1;
    }

    private JPanel buildActionPanel() {
        JPanel panel = new JPanel();

        JButton btnLuu = new JButton("Lưu");
        JButton btnThoat = new JButton("Thoát");

        btnLuu.addActionListener(e -> onSave());
        btnThoat.addActionListener(e -> dispose());

        panel.add(btnLuu);
        panel.add(btnThoat);
        return panel;
    }

    private void fillForm() {
        if (quyDoi == null)
            return;

        txtMaQuyDoi.setText(nvl(quyDoi.getMaQuyDoi()));
        txtPhuongThuc.setText(nvl(quyDoi.getPhuongThuc()));
        txtToHop.setText(nvl(quyDoi.getToHop()));
        txtMon.setText(nvl(quyDoi.getMon()));
        txtPhanVi.setText(nvl(quyDoi.getPhanVi()));
        txtDiemA.setText(quyDoi.getDiemA() != null ? String.valueOf(quyDoi.getDiemA()) : "");
        txtDiemB.setText(quyDoi.getDiemB() != null ? String.valueOf(quyDoi.getDiemB()) : "");
        txtDiemC.setText(quyDoi.getDiemC() != null ? String.valueOf(quyDoi.getDiemC()) : "");
        txtDiemD.setText(quyDoi.getDiemD() != null ? String.valueOf(quyDoi.getDiemD()) : "");
    }

    private void onSave() {
        if (quyDoi == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mốc quy đổi để sửa");
            return;
        }

        String newMa = txtMaQuyDoi.getText() != null ? txtMaQuyDoi.getText().trim() : null;
        if (newMa != null && !newMa.isEmpty() && oldMaQuyDoi != null && !oldMaQuyDoi.equalsIgnoreCase(newMa)) {
            if (bangQuyDoiBUS.ds != null) {
                for (bangQuyDoiETT item : bangQuyDoiBUS.ds) {
                    if (item != null && item.getMaQuyDoi() != null && item.getMaQuyDoi().equalsIgnoreCase(newMa)) {
                        JOptionPane.showMessageDialog(this, "Mã quy đổi đã tồn tại");
                        return;
                    }
                }
            }
        }

        quyDoi.setMaQuyDoi(newMa);
        quyDoi.setPhuongThuc(txtPhuongThuc.getText() != null ? txtPhuongThuc.getText().trim() : null);
        quyDoi.setToHop(txtToHop.getText() != null ? txtToHop.getText().trim() : null);
        quyDoi.setMon(txtMon.getText() != null ? txtMon.getText().trim() : null);
        quyDoi.setPhanVi(txtPhanVi.getText() != null ? txtPhanVi.getText().trim() : null);

        quyDoi.setDiemA(parseDoubleOrNull(txtDiemA.getText()));
        quyDoi.setDiemB(parseDoubleOrNull(txtDiemB.getText()));
        quyDoi.setDiemC(parseDoubleOrNull(txtDiemC.getText()));
        quyDoi.setDiemD(parseDoubleOrNull(txtDiemD.getText()));

        String err = bus.validate(quyDoi, false);
        if (err != null) {
            JOptionPane.showMessageDialog(this, err);
            return;
        }

        if (!bus.suaQuyDoi(quyDoi)) {
            JOptionPane.showMessageDialog(this, "Cập nhật mốc quy đổi thất bại (có thể do lỗi CSDL)");
            return;
        }

        xacNhan = true;
        dispose();
    }

    private String nvl(String s) {
        return s == null ? "" : s;
    }

    private Double parseDoubleOrNull(String text) {
        if (text == null)
            return null;
        String t = text.trim();
        if (t.isEmpty())
            return null;
        try {
            return Double.valueOf(t);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
