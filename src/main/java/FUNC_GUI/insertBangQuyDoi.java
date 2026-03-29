package FUNC_GUI;

import BUS.bangQuyDoiBUS;
import Entity.bangQuyDoiETT;
import javax.swing.JOptionPane;
import java.awt.Frame;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;

public class insertBangQuyDoi extends JDialog {

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

    public insertBangQuyDoi(Frame parent, boolean modal) {
        super(parent, modal);
        if (bangQuyDoiBUS.ds == null) {
            bus.layDanhSach();
        }

        initComponents();
        setTitle("Thêm mốc quy đổi V-SAT");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public bangQuyDoiETT getQuyDoi() {
        return quyDoi;
    }

    private void initComponents() {
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel pnlRoot = new JPanel();
        pnlRoot.setBackground(Color.WHITE);

        // ===== Header giống form Thí Sinh =====
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(153, 255, 255));
        JLabel lblTitle = new JLabel("Thêm mốc quy đổi V-SAT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        GroupLayout headerLayout = new GroupLayout(pnlHeader);
        pnlHeader.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(lblTitle)
                                .addContainerGap(20, Short.MAX_VALUE)));
        headerLayout.setVerticalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addGap(18)
                                .addComponent(lblTitle)
                                .addGap(18)));

        // ===== GroupBox nhập liệu =====
        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createTitledBorder(
                null,
                "Thêm mốc quy đổi",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14)));

        JLabel lblMa = new JLabel("Mã quy đổi (*) :");
        JLabel lblPhuongThuc = new JLabel("Phương thức :");
        JLabel lblToHop = new JLabel("Tổ hợp :");
        JLabel lblMon = new JLabel("Môn (*) :");
        JLabel lblPhanVi = new JLabel("Phân vị :");
        JLabel lblA = new JLabel("Điểm A (*) :");
        JLabel lblB = new JLabel("Điểm B (*) :");
        JLabel lblC = new JLabel("Điểm C (*) :");
        JLabel lblD = new JLabel("Điểm D (*) :");

        JLabel[] labels = { lblMa, lblPhuongThuc, lblToHop, lblMon, lblPhanVi, lblA, lblB, lblC, lblD };
        for (JLabel l : labels) {
            l.setFont(labelFont);
        }

        txtMaQuyDoi = new JTextField();
        txtPhuongThuc = new JTextField();
        txtToHop = new JTextField();
        txtMon = new JTextField();
        txtPhanVi = new JTextField();
        txtDiemA = new JTextField();
        txtDiemB = new JTextField();
        txtDiemC = new JTextField();
        txtDiemD = new JTextField();

        JTextField[] fields = { txtMaQuyDoi, txtPhuongThuc, txtToHop, txtMon, txtPhanVi, txtDiemA, txtDiemB, txtDiemC,
                txtDiemD };
        for (JTextField f : fields) {
            f.setFont(labelFont);
            f.setBackground(new Color(246, 246, 246));
        }

        GroupLayout formLayout = new GroupLayout(pnlForm);
        pnlForm.setLayout(formLayout);
        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);

        formLayout.setHorizontalGroup(
                formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(formLayout.createSequentialGroup()
                                .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblMa, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPhuongThuc, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblToHop, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMon, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPhanVi, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblA, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblB, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblC, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblD, GroupLayout.PREFERRED_SIZE, 123,
                                                GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMaQuyDoi, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPhuongThuc, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtToHop, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMon, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPhanVi, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiemA, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiemB, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiemC, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiemD, GroupLayout.PREFERRED_SIZE, 330,
                                                GroupLayout.PREFERRED_SIZE))));

        formLayout.setVerticalGroup(
                formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMa)
                                .addComponent(txtMaQuyDoi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPhuongThuc)
                                .addComponent(txtPhuongThuc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblToHop)
                                .addComponent(txtToHop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMon)
                                .addComponent(txtMon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPhanVi)
                                .addComponent(txtPhanVi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblA)
                                .addComponent(txtDiemA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblB)
                                .addComponent(txtDiemB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblC)
                                .addComponent(txtDiemC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblD)
                                .addComponent(txtDiemD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)));

        // ===== GroupBox xử lý nút =====
        JPanel pnlAction = new JPanel();
        pnlAction.setBackground(Color.WHITE);
        pnlAction.setBorder(BorderFactory.createTitledBorder(
                null,
                "Xử lý thêm mốc",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14)));

        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.addActionListener(e -> onCreate());

        JButton btnThoat = new JButton("Thoát");
        btnThoat.setBackground(new Color(255, 102, 102));
        btnThoat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThoat.addActionListener(e -> dispose());

        GroupLayout actionLayout = new GroupLayout(pnlAction);
        pnlAction.setLayout(actionLayout);
        actionLayout.setHorizontalGroup(
                actionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, actionLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThem, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                .addGap(46)
                                .addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                .addGap(35)));
        actionLayout.setVerticalGroup(
                actionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(actionLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(actionLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnThoat, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addComponent(btnThem, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addContainerGap()));

        GroupLayout rootLayout = new GroupLayout(pnlRoot);
        pnlRoot.setLayout(rootLayout);
        rootLayout.setHorizontalGroup(
                rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rootLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlHeader, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(pnlForm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(pnlAction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addContainerGap()));
        rootLayout.setVerticalGroup(
                rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rootLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlForm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlAction, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        setContentPane(pnlRoot);
        pack();
    }

    private void onCreate() {
        bangQuyDoiETT obj = new bangQuyDoiETT();
        obj.setMaQuyDoi(txtMaQuyDoi.getText() != null ? txtMaQuyDoi.getText().trim() : null);
        obj.setPhuongThuc(txtPhuongThuc.getText() != null ? txtPhuongThuc.getText().trim() : null);
        obj.setToHop(txtToHop.getText() != null ? txtToHop.getText().trim() : null);
        obj.setMon(txtMon.getText() != null ? txtMon.getText().trim() : null);
        obj.setPhanVi(txtPhanVi.getText() != null ? txtPhanVi.getText().trim() : null);

        Double diemA = parseDoubleOrNull(txtDiemA.getText());
        Double diemB = parseDoubleOrNull(txtDiemB.getText());
        Double diemC = parseDoubleOrNull(txtDiemC.getText());
        Double diemD = parseDoubleOrNull(txtDiemD.getText());
        obj.setDiemA(diemA);
        obj.setDiemB(diemB);
        obj.setDiemC(diemC);
        obj.setDiemD(diemD);

        String err = bus.validate(obj, true);
        if (err != null) {
            JOptionPane.showMessageDialog(this, err);
            return;
        }

        if (!bus.themQuyDoi(obj)) {
            JOptionPane.showMessageDialog(this, "Thêm mốc quy đổi thất bại (có thể do trùng mã hoặc lỗi CSDL)");
            return;
        }

        this.quyDoi = obj;
        this.xacNhan = true;
        dispose();
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
