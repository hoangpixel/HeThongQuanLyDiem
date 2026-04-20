package FUNC_GUI;

import Entity.diemThiETT;
import CONFIG.RoundedButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class insertDiemThi extends JDialog {

    public boolean xacNhan = false;

    private final Map<String, JTextField> fields = new LinkedHashMap<>();
    private diemThiETT diemThi;

    public insertDiemThi(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Thêm bảng điểm thi");
        initUI();
        pack();
        setMinimumSize(new Dimension(620, 520));
    }

    public diemThiETT getDiemThi() {
        return diemThi;
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        Font baseFont = UIManager.getFont("defaultFont");
        if (baseFont == null) {
            baseFont = new Font("SansSerif", Font.PLAIN, 14);
        }

        JLabel title = new JLabel("THÊM BẢNG ĐIỂM THI", SwingConstants.CENTER);
        title.setFont(baseFont.deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Thông tin định danh
        addField(form, 0, "CCCD", true);
        addField(form, 1, "Số báo danh", false);
        addField(form, 2, "Phương thức", false);

        // Điểm môn / thành phần (theo DB xt_diemthixettuyen)
        String[] scoreKeys = {
            "TO", "LI", "HO", "SI", "SU", "DI", "VA",
            "N1_THI", "N1_CC",
            "CNCN", "CNNN", "TI", "KTPL",
            "NL1",
            "NK1", "NK2", "NK3", "NK4", "NK5", "NK6"
        };

        int row = 3;
        for (String key : scoreKeys) {
            addField(form, row++, key, false);
        }

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scroll, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        RoundedButton btnSave = new RoundedButton("LƯU");
        RoundedButton btnCancel = new RoundedButton("HỦY");

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> onCancel());

        actions.add(btnSave);
        actions.add(btnCancel);
        actions.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(actions, BorderLayout.SOUTH);
    }

    private void addField(JPanel form, int row, String label, boolean required) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = row;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(6, 0, 6, 10);

        JLabel lbl = new JLabel(required ? (label + " *") : label);
        form.add(lbl, gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = row;
        gbcField.weightx = 1;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.insets = new Insets(6, 0, 6, 0);

        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(260, 30));
        form.add(txt, gbcField);
        fields.put(label, txt);
    }

    private void onSave() {
        String cccd = getText("CCCD");
        if (cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống");
            return;
        }

        try {
            diemThiETT obj = new diemThiETT();
            obj.setCccd(cccd);
            obj.setSobaodanh(nullIfBlank(getText("Số báo danh")));
            obj.setdPhuongthuc(nullIfBlank(getText("Phương thức")));

            obj.setTo(parseDoubleOrNull(getText("TO"), "TO"));
            obj.setLi(parseDoubleOrNull(getText("LI"), "LI"));
            obj.setHo(parseDoubleOrNull(getText("HO"), "HO"));
            obj.setSi(parseDoubleOrNull(getText("SI"), "SI"));
            obj.setSu(parseDoubleOrNull(getText("SU"), "SU"));
            obj.setDi(parseDoubleOrNull(getText("DI"), "DI"));
            obj.setVa(parseDoubleOrNull(getText("VA"), "VA"));

            obj.setN1Thi(parseDoubleOrNull(getText("N1_THI"), "N1_THI"));
            obj.setN1Cc(parseDoubleOrNull(getText("N1_CC"), "N1_CC"));

            obj.setCncn(parseDoubleOrNull(getText("CNCN"), "CNCN"));
            obj.setCnnn(parseDoubleOrNull(getText("CNNN"), "CNNN"));
            obj.setTi(parseDoubleOrNull(getText("TI"), "TI"));
            obj.setKtpl(parseDoubleOrNull(getText("KTPL"), "KTPL"));

            obj.setNl1(parseDoubleOrNull(getText("NL1"), "NL1"));

            obj.setNk1(parseDoubleOrNull(getText("NK1"), "NK1"));
            obj.setNk2(parseDoubleOrNull(getText("NK2"), "NK2"));
            obj.setNk3(parseDoubleOrNull(getText("NK3"), "NK3"));
            obj.setNk4(parseDoubleOrNull(getText("NK4"), "NK4"));
            obj.setNk5(parseDoubleOrNull(getText("NK5"), "NK5"));
            obj.setNk6(parseDoubleOrNull(getText("NK6"), "NK6"));

            this.diemThi = obj;
            this.xacNhan = true;
            dispose();
        } catch (IllegalArgumentException ex) {
            // đã show message ở parseDoubleOrNull
        }
    }

    private void onCancel() {
        this.xacNhan = false;
        this.diemThi = null;
        dispose();
    }

    private String getText(String key) {
        JTextField txt = fields.get(key);
        return txt == null || txt.getText() == null ? "" : txt.getText().trim();
    }

    private static String nullIfBlank(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private Double parseDoubleOrNull(String text, String fieldName) {
        if (text == null) {
            return null;
        }
        String t = text.trim();
        if (t.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(t);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ ở " + fieldName + ": " + t);
            throw new IllegalArgumentException("invalid number");
        }
    }
}
