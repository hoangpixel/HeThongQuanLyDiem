/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.Font;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author LE MINH HUY
 */
class Item {
    String name;
    int code;

    public Item(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name; // 👉 chỉ hiện tên
    }
}
public class updateThiSinh extends javax.swing.JDialog {
    private boolean xacNhan = false;
    public Entity.thiSinhXetTuyenETT thiSinh;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(updateThiSinh.class.getName());

    /**
     * Creates new form updateThisinh
     */
    public updateThiSinh(java.awt.Frame parent, boolean modal, Entity.thiSinhXetTuyenETT data) {
        super(parent, modal);
        initComponents();
        loadComboBox();
        loadTinh();
        cboNoiSinh.addActionListener(e -> {
            if (cboNoiSinh.getSelectedItem() == null) return;
            try {
                Item item = (Item) cboNoiSinh.getSelectedItem();
                int code = item.code;
                String json = readAPI("https://provinces.open-api.vn/api/p/" + code + "?depth=2");
                JSONObject obj = new JSONObject(json);
                JSONArray districts = obj.getJSONArray("districts");

                cboNoiSinh4.removeAllItems();

                // Lấy tên Huyện cũ từ DB
                String huyenCu = "";
                String[] parts = thiSinh.getNoiSinh().split(" - ");
                if (parts.length >= 2) huyenCu = parts[1].trim();

                Item huyenToSelect = null;
                for (int i = 0; i < districts.length(); i++) {
                    JSONObject d = districts.getJSONObject(i);
                    Item newHuyen = new Item(d.getString("name"), d.getInt("code"));
                    cboNoiSinh4.addItem(newHuyen);
                    if (newHuyen.name.equals(huyenCu)) {
                        huyenToSelect = newHuyen;
                    }
                }
                // Tự động chọn Huyện
                if (huyenToSelect != null) cboNoiSinh4.setSelectedItem(huyenToSelect);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        cboNoiSinh4.addActionListener(e -> {
            if (cboNoiSinh4.getSelectedItem() == null) return;
            try {
                Item item = (Item) cboNoiSinh4.getSelectedItem();
                int code = item.code;
                String json = readAPI("https://provinces.open-api.vn/api/d/" + code + "?depth=2");
                JSONObject obj = new JSONObject(json);
                JSONArray wards = obj.getJSONArray("wards");

                cboNoiSinh5.removeAllItems();

                // Lấy tên Xã cũ từ DB
                String xaCu = "";
                String[] parts = thiSinh.getNoiSinh().split(" - ");
                if (parts.length >= 3) xaCu = parts[2].trim();

                for (int i = 0; i < wards.length(); i++) {
                    JSONObject w = wards.getJSONObject(i);
                    Item newXa = new Item(w.getString("name"), 0);
                    cboNoiSinh5.addItem(newXa);
                    if (newXa.name.equals(xaCu)) {
                        cboNoiSinh5.setSelectedItem(newXa);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.thiSinh = data; // 🔥 lưu lại

        dateNgaySinh = new JDateChooser();
        dateNgaySinh.setDateFormatString("dd/MM/yyyy");
        dateNgaySinh.setMaxSelectableDate(new Date());
        dateNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboNgaySinh.setVisible(false);
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) jPanel5.getLayout();
        layout.replace(cboNgaySinh, dateNgaySinh);
        cboNgaySinh.setVisible(false);
        // 🔥 refresh UI
        jPanel2.revalidate();
        jPanel2.repaint();

        setLocationRelativeTo(parent);

        // 🔥 ĐỔ DỮ LIỆU
        txtCCCD.setText(data.getCccd());
        txtHoTS.setText(data.getHo());
        txtTenTS.setText(data.getTen());
        txtDienThoai.setText(data.getDienThoai());
        txtEmail.setText(data.getEmail());
        txtSoBaoDanh.setText(data.getSoBaoDanh());
        txtPassword.setText(data.getPassword());
        txtCCCD.setEditable(false);
        txtSoBaoDanh.setEditable(false);
        txtCCCD.setEnabled(false);
        txtSoBaoDanh.setEnabled(false);
        txtCCCD.setBackground(new java.awt.Color(240, 240, 240));
        txtSoBaoDanh.setBackground(new java.awt.Color(240, 240, 240));
        dateNgaySinh.setDate(data.getNgaySinh());

        cboNoiSinh1.setSelectedItem(data.getGioiTinh());
        String dtTuDB = data.getDoiTuong(); // Ví dụ: "1"
        for (int i = 0; i < cboNoiSinh2.getItemCount(); i++) {
            if (cboNoiSinh2.getItemAt(i).toString().startsWith(dtTuDB + " - ")) {
                cboNoiSinh2.setSelectedIndex(i);
                break;
            }
        }
        String kvGoc = data.getKhuVuc();
        if (kvGoc != null) {
            // Tự động chuyển đổi nếu dữ liệu cũ trong DB là "KV2-NT"
            if (kvGoc.equalsIgnoreCase("KV2-NT")) kvGoc = "KV2NT";
            cboKhuVuc.setSelectedItem(kvGoc.trim().toUpperCase());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHoTS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenTS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboNoiSinh = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtDienThoai = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboNoiSinh1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSoBaoDanh = new javax.swing.JTextField();
        cboNoiSinh2 = new javax.swing.JComboBox<>();
        cboKhuVuc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        cboNoiSinh4 = new javax.swing.JComboBox<>();
        cboNoiSinh5 = new javax.swing.JComboBox<>();
        cboNgaySinh = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Sửa Thí Sinh");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý sửa TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(this::btnSuaActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sửa thí sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Họ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên");

        txtTenTS.addActionListener(this::txtTenTSActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Ngày sinh");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Điện thoại");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Giới tính");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Nơi sinh");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Đối tượng");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Khu vực");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("CCCD Thí sinh (*) : ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Số báo danh");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Mật khẩu");

        cboNoiSinh4.addActionListener(this::cboNoiSinh4ActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboNoiSinh5, 0, 235, Short.MAX_VALUE)
                            .addComponent(cboNoiSinh4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboNoiSinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtHoTS))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCCCD))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboNgaySinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboNoiSinh1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDienThoai))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboNoiSinh2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSoBaoDanh, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cboNoiSinh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cboNoiSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNoiSinh4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboNoiSinh5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoBaoDanh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNoiSinh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(398, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void loadComboBox() {

        // ===== GIỚI TÍNH =====
        cboNoiSinh1.removeAllItems();
        cboNoiSinh1.addItem("Nam");
        cboNoiSinh1.addItem("Nữ");
        cboNoiSinh1.addItem("Khác");

        // ===== ĐỒI TƯỢNG (Đồng bộ với logic tính điểm Nguyện vọng) =====
        cboNoiSinh2.removeAllItems();
        cboNoiSinh2.addItem("Không - Không ưu tiên");
        cboNoiSinh2.addItem("ĐT1 - Người dân tộc thiểu số");
        cboNoiSinh2.addItem("ĐT2 - Công nhân trực tiếp");
        cboNoiSinh2.addItem("ĐT3 - Thương binh, bệnh binh");
        cboNoiSinh2.addItem("ĐT4 - Con liệt sĩ, con thương binh");
        cboNoiSinh2.addItem("ĐT5 - Thanh niên xung phong");
        cboNoiSinh2.addItem("ĐT6 - Con người có công");
        cboNoiSinh2.addItem("ĐT7 - Người khuyết tật");

        // ===== KHU VỰC =====
        cboKhuVuc.removeAllItems();
        cboKhuVuc.addItem("KV1");
        cboKhuVuc.addItem("KV2");
        cboKhuVuc.addItem("KV2NT");
        cboKhuVuc.addItem("KV3");
    }
    private void loadTinh() {
        new Thread(() -> {
            try {
                String json = readAPI("https://provinces.open-api.vn/api/p/");
                org.json.JSONArray arr = new org.json.JSONArray(json);

                String[] parts = thiSinh.getNoiSinh().split(" - ");
                final String tinhCu = parts.length > 0 ? parts[0].trim() : "";

                javax.swing.SwingUtilities.invokeLater(() -> {
                    cboNoiSinh.removeAllItems();
                    Item selectedItem = null;
                    for (int i = 0; i < arr.length(); i++) {
                        org.json.JSONObject obj = arr.getJSONObject(i);
                        Item newItem = new Item(obj.getString("name"), obj.getInt("code"));
                        cboNoiSinh.addItem(newItem);
                        if (newItem.name.equals(tinhCu)) {
                            selectedItem = newItem;
                        }
                    }
                    // Sau khi add hết mới chọn Tỉnh, việc này sẽ kích hoạt ActionListener của Tỉnh
                    if (selectedItem != null) {
                        cboNoiSinh.setSelectedItem(selectedItem);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        xacNhan = false;
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        // 1. Kiểm tra bắt buộc nhập CCCD
//        if(txtCCCD.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được để trống CCCD!", "Thông báo", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if (dateNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            return;
        }

        // 2. Gom dữ liệu từ các ô Text nhập liệu vào đối tượng ETT
        Entity.thiSinhXetTuyenETT ts = new Entity.thiSinhXetTuyenETT();
        ts.setIdThiSinh(thiSinh.getIdThiSinh());
        ts.setCccd(txtCCCD.getText().trim());
        ts.setHo(txtHoTS.getText().trim());
        ts.setTen(txtTenTS.getText().trim());
        ts.setDienThoai(txtDienThoai.getText().trim());
        ts.setEmail(txtEmail.getText().trim());
        ts.setSoBaoDanh(txtSoBaoDanh.getText().trim());
        ts.setPassword(txtPassword.getText().trim());
        ts.setNgaySinh(dateNgaySinh.getDate());
        ts.setGioiTinh(getComboValue(cboNoiSinh1));
        Item tinhItem = (Item) cboNoiSinh.getSelectedItem();
        Item huyenItem = (Item) cboNoiSinh4.getSelectedItem();

        String tinh = tinhItem.name;
        String huyen = huyenItem.name;
        String xa = cboNoiSinh5.getSelectedItem().toString();
        ts.setNoiSinh(tinh + " - " + huyen + " - " + xa);
        String doiTuongFull = getComboValue(cboNoiSinh2);
        String maDoiTuong = doiTuongFull.split(" - ")[0].trim(); // Lấy "ĐT1" hoặc "ĐT2"
        ts.setDoiTuong(maDoiTuong);
        ts.setKhuVuc(getComboValue(cboKhuVuc).trim().toUpperCase());

        // 3. Gọi BUS để lưu xuống Database
        BUS.thiSinhXetTuyenBUS bus = new BUS.thiSinhXetTuyenBUS();
        String ketQua = bus.suaThiSinh(ts);
        if (ketQua.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Cập nhật thí sinh thành công!");
            xacNhan = true;
            thiSinh = ts;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, ketQua);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTenTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenTSActionPerformed

    private void cboNoiSinh4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNoiSinh4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNoiSinh4ActionPerformed
    private String getComboValue(javax.swing.JComboBox<?> cbo) {
        if (cbo.getSelectedItem() == null) return "";

        Object item = cbo.getSelectedItem();

        if (item instanceof Item) {
            return ((Item) item).name;
        }

        return item.toString();
    }   
    public boolean isXacNhan() {
        return xacNhan;
    }
    public Entity.thiSinhXetTuyenETT getThiSinh() {
        return thiSinh;
    }
    private String readAPI(String urlString) {
    StringBuilder result = new StringBuilder();
    try {
        java.net.URL url = new java.net.URL(urlString);
        java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(url.openStream(), "UTF-8"));

        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result.toString();
}
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThoat;
    private javax.swing.JComboBox<String> cboKhuVuc;
    private javax.swing.JComboBox<Item> cboNgaySinh;
    private javax.swing.JComboBox<Item> cboNoiSinh;
    private javax.swing.JComboBox<String> cboNoiSinh1;
    private javax.swing.JComboBox<String> cboNoiSinh2;
    private javax.swing.JComboBox<Item> cboNoiSinh4;
    private javax.swing.JComboBox<Item> cboNoiSinh5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTS;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSoBaoDanh;
    private javax.swing.JTextField txtTenTS;
    // End of variables declaration//GEN-END:variables
}
