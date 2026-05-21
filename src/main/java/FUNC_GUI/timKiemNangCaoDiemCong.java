/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import BUS.nguyenVongXetTuyenBUS;
import Entity.nganhETT;
import Entity.nguyenVongXetTuyenETT;
import Entity.thiSinhXetTuyenETT;
import Entity.toHopETT;
import SELECT_GUI.selectNganh;
import SELECT_GUI.selectThiSinh;
import SELECT_GUI.selectToHop;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

/**
 *
 * @author LE MINH HUY
 */
public class timKiemNangCaoDiemCong extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(timKiemNangCaoDiemCong.class.getName());
    private boolean xacNhan = false;
    private ArrayList<Entity.diemCongETT> dskq = new ArrayList<>();
    private String cccd, maNganh, toHop;
    
    /**
     * Creates new form timKiemNangCao
     */
    public timKiemNangCaoDiemCong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        setTitle("Lọc điểm cộng");
        jLabel1.setText("Lọc điểm cộng");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập tiêu chí lọc điểm cộng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14)));
        
        // Show CCCD fields
        jLabel2.setText("CCCD : ");
        jLabel2.setVisible(true);
        txtCCCD.setVisible(true);
        txtCCCD.setEditable(false);
        btnCCCD.setVisible(true);
        
        // Show Mã ngành fields
        jLabel3.setText("Mã ngành :");
        jLabel3.setVisible(true);
        txtMaNganh.setVisible(true);
        txtMaNganh.setEditable(false);
        btnMaNganh.setVisible(true);
        
        // Show Tổ hợp fields
        jLabel6.setText("Tổ hợp : ");
        jLabel6.setVisible(true);
        txtToHop.setVisible(true);
        txtToHop.setEditable(false);
        btnToHop.setVisible(true);
        
        // Phương thức
        jLabel5.setText("Phương thức : ");
        jLabel5.setVisible(true);
        cboPhuongThuc.setVisible(true);
        cboPhuongThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Xét THPT", "ĐGNL HCM", "Đánh giá V-SAT", "Xét tuyển thẳng" }));
        
        // Hide unused fields (Thứ tự, Kết quả)
        jLabel4.setVisible(false);
        txtThuTu.setVisible(false);
        
        jLabel7.setVisible(false);
        cboKetQua.setVisible(false);

        // Reconstruct layout of jPanel2 to avoid gaps from hidden fields
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        btnCCCD = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtMaNganh = new javax.swing.JTextField();
        btnMaNganh = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtThuTu = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        cboPhuongThuc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtToHop = new javax.swing.JTextField();
        btnToHop = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboKetQua = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lọc nguyện vọng");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thực hiện lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Thoát");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(this::jButton4ActionPerformed);

        btnLoc.setBackground(new java.awt.Color(204, 255, 204));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoc.addActionListener(this::btnLocActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Lọc nguyện vọng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập dữ liệu cần lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("CCCD : ");

        txtCCCD.setEditable(false);
        txtCCCD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnCCCD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCCCD.setText("...");
        btnCCCD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCCCD.addActionListener(this::btnCCCDActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã ngành :");

        txtMaNganh.setEditable(false);
        txtMaNganh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnMaNganh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMaNganh.setText("...");
        btnMaNganh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMaNganh.addActionListener(this::btnMaNganhActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Thứ tự : ");

        txtThuTu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Phương thức :");

        cboPhuongThuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboPhuongThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Xét THPT", "ĐGNL HCM", "Đánh giá V-SAT", "Xét tuyển thẳng" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Tổ hợp : ");

        txtToHop.setEditable(false);
        txtToHop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnToHop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnToHop.setText("...");
        btnToHop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnToHop.addActionListener(this::btnToHopActionPerformed);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Kết quả : ");

        cboKetQua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboKetQua.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chờ xét", "Đã đậu", "Đã trượt", "Không xét", "Bỏ qua" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboKetQua, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThuTu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtThuTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboKetQua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        String cccdLoc = txtCCCD.getText().trim().toLowerCase();
        String maNganhLoc = txtMaNganh.getText().trim().toLowerCase();
        String toHopLoc = txtToHop.getText().trim().toLowerCase();
        String phuongThucLoc = cboPhuongThuc.getSelectedItem().toString();

        BUS.diemCongBUS bus = new BUS.diemCongBUS();
        ArrayList<Entity.diemCongETT> dsGoc = bus.layDanhSach();
        
        dskq = new java.util.ArrayList<>();
        
        if (dsGoc != null) {
            for (Entity.diemCongETT dc : dsGoc) {
                // 1. Lọc theo CCCD
                if (!cccdLoc.isEmpty()) {
                    if (dc.getTsCccd() == null || !dc.getTsCccd().toLowerCase().contains(cccdLoc)) {
                        continue;
                    }
                }
                
                // 2. Lọc theo Mã ngành
                if (!maNganhLoc.isEmpty()) {
                    if (dc.getMaNganh() == null || !dc.getMaNganh().toLowerCase().contains(maNganhLoc)) {
                        continue;
                    }
                }
                
                // 3. Lọc theo Tổ hợp
                if (!toHopLoc.isEmpty()) {
                    if (dc.getMaToHop() == null || !dc.getMaToHop().toLowerCase().contains(toHopLoc)) {
                        continue;
                    }
                }
                
                // 4. Lọc theo Phương thức
                if (!phuongThucLoc.equals("Tất cả")) {
                    if (dc.getPhuongThuc() == null || !dc.getPhuongThuc().equalsIgnoreCase(phuongThucLoc)) {
                        continue;
                    }
                }
                
                dskq.add(dc);
            }
        }
        
        xacNhan = true;
        this.dispose();
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCCCDActionPerformed
        // TODO add your handling code here:
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        selectThiSinh dialog = new selectThiSinh(topFrame, true);
        dialog.setVisible(true);
        
        if(dialog.getXacNhan()) {
            thiSinhXetTuyenETT thiSinh = dialog.getThiSinh();
            cccd = thiSinh.getCccd();
            txtCCCD.setText(cccd);
        }
    }//GEN-LAST:event_btnCCCDActionPerformed

    private void btnMaNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaNganhActionPerformed
        // TODO add your handling code here:
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        selectNganh dialog = new selectNganh(topFrame, true);
        dialog.setVisible(true);
        
        if(dialog.getXacNhan())
        {
            nganhETT nganh = dialog.getNganh();
            maNganh = nganh.getManganh();
            txtMaNganh.setText(maNganh);
        }
    }//GEN-LAST:event_btnMaNganhActionPerformed

    private void btnToHopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToHopActionPerformed
        // TODO add your handling code here:
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        selectToHop dialog = new selectToHop(topFrame, true);
        dialog.setVisible(true);
        
        if(dialog.getXacNhan())
        {
            toHopETT nganh = dialog.getToHopETT();
            toHop = nganh.getMatohop();
            txtToHop.setText(toHop);
        }
    }//GEN-LAST:event_btnToHopActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        xacNhan = false;
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    public boolean getXacNhan() 
    {
        return xacNhan;
    }
    
    public ArrayList<Entity.diemCongETT> getDanhSachLoc() 
    {
        return dskq;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCCCD;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnMaNganh;
    private javax.swing.JButton btnToHop;
    private javax.swing.JComboBox<String> cboKetQua;
    private javax.swing.JComboBox<String> cboPhuongThuc;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtMaNganh;
    private javax.swing.JSpinner txtThuTu;
    private javax.swing.JTextField txtToHop;
    // End of variables declaration//GEN-END:variables
}
