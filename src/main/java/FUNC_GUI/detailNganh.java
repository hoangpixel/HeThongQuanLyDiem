/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import Entity.nganhETT;

import javax.swing.SwingUtilities;
/**
 *
 * @author Dat
 */
public class detailNganh extends javax.swing.JDialog {
    
    public nganhETT nganh;
    
    public detailNganh(java.awt.Frame parent, boolean modal, nganhETT data) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.nganh = data;
        
        // 1. Đổ dữ liệu vào các ô Text
        txtMaNganh.setText(data.getManganh());
        txtTenNganh.setText(data.getTennganh());
        txtToHopGoc.setText(data.getN_tohopgoc());
        
        txtChiTieu.setText(data.getN_chitieu() != null ? String.valueOf(data.getN_chitieu()) : "0");
        
        txtDiemSanTHPT.setText(data.getN_diemsanthpt() != null ? String.valueOf(data.getN_diemsanthpt()) : "");
        txtDiemSanVSAT.setText(data.getN_diemsanvsat() != null ? String.valueOf(data.getN_diemsanvsat()) : "");
        txtDiemSanDGNL.setText(data.getN_diemsandgnl() != null ? String.valueOf(data.getN_diemsandgnl()) : "");
        txtDiemTrungTuyen.setText(data.getN_diemtrungtuyen() != null ? String.valueOf(data.getN_diemtrungtuyen()) : "");
        txtDiemTrungTuyen.setText("30");
        // Load số lượng phương thức
//      thietLapOChon(chkTuyenThang, txtTuyenThang, data.getN_tuyenthang(), data.getSl_xtt());
//      thietLapOChon(chkThiDGNL, txtThiDGNL, data.getN_dgnl(), data.getSl_dgnl());
//      thietLapOChon(chkThiTHPT, txtThiTHPT, data.getN_thpt(), data.getSl_thpt());
//      thietLapOChon(chkThiVSAT, txtThiVSAT, data.getN_vsat(), data.getSl_vsat());

        // 2. KHÓA TOÀN BỘ Ô NHẬP LIỆU (Vì đây là form xem chi tiết)
        txtMaNganh.setEditable(false);
        txtTenNganh.setEditable(false);
        txtToHopGoc.setEditable(false);
        txtChiTieu.setEditable(false);
        txtDiemSanTHPT.setEditable(false);
        txtDiemSanVSAT.setEditable(false);
        txtDiemSanDGNL.setEditable(false);
        txtDiemTrungTuyen.setEditable(false);
        
        // Đổi màu nền cho đồng bộ giao diện "chỉ đọc"
        java.awt.Color readOnlyColor = new java.awt.Color(246, 246, 246);
        txtMaNganh.setBackground(readOnlyColor);
        txtTenNganh.setBackground(readOnlyColor);
        txtToHopGoc.setBackground(readOnlyColor);
        txtChiTieu.setBackground(readOnlyColor);
        txtDiemSanTHPT.setBackground(readOnlyColor);
        txtDiemSanVSAT.setBackground(readOnlyColor);
        txtDiemSanDGNL.setBackground(readOnlyColor);
        txtDiemTrungTuyen.setBackground(readOnlyColor);
        
        // Ép focus vào nút thoát để tránh nháy con trỏ trong ô text
        SwingUtilities.invokeLater(() -> {
            btnThoat.requestFocusInWindow();
        });
    }

    // Hàm phụ trợ để hiển thị checkbox và số lượng (đã khóa tương tác)
//    private void thietLapOChon(javax.swing.JCheckBox cb, javax.swing.JTextField txt, String n_val, Object sl_val) {
//        int soLuong = 0;
//        if (sl_val != null) {
//            try { soLuong = Integer.parseInt(sl_val.toString()); } catch (Exception e) {}
//        }
//        
//        boolean active = ("1".equals(n_val)) || (soLuong > 0);
//        cb.setSelected(active);
//        cb.setEnabled(false); // Khóa checkbox
//        
//        txt.setText(sl_val != null ? String.valueOf(sl_val) : "0");
//        txt.setEditable(false);
//        txt.setBackground(new java.awt.Color(246, 246, 246));
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiemSanTHPT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDiemTrungTuyen = new javax.swing.JTextField();
        btnChonToHopGoc = new javax.swing.JButton();
        txtMaNganh = new javax.swing.JTextField();
        txtTenNganh = new javax.swing.JTextField();
        txtToHopGoc = new javax.swing.JTextField();
        txtChiTieu = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDiemSanVSAT = new javax.swing.JTextField();
        txtDiemSanDGNL = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi tiết Ngành");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên ngành :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã ngành (*) : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tổ hợp gốc :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Điểm sàn THPT :");

        txtDiemSanTHPT.setEditable(false);
        txtDiemSanTHPT.addActionListener(this::txtDiemSanTHPTActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Chỉ tiêu :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Điểm trúng tuyển :");

        txtDiemTrungTuyen.setEditable(false);
        txtDiemTrungTuyen.addActionListener(this::txtDiemTrungTuyenActionPerformed);

        btnChonToHopGoc.setText("...");
        btnChonToHopGoc.addActionListener(this::btnChonToHopGocActionPerformed);

        txtMaNganh.setBackground(new java.awt.Color(246, 246, 246));
        txtMaNganh.addActionListener(this::txtMaNganhActionPerformed);

        txtTenNganh.setBackground(new java.awt.Color(246, 246, 246));
        txtTenNganh.addActionListener(this::txtTenNganhActionPerformed);

        txtToHopGoc.setBackground(new java.awt.Color(246, 246, 246));
        txtToHopGoc.addActionListener(this::txtToHopGocActionPerformed);

        txtChiTieu.setEditable(false);
        txtChiTieu.addActionListener(this::txtChiTieuActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Điểm sàn V-SAT:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Điểm sàn ĐGNL :");

        txtDiemSanVSAT.setEditable(false);
        txtDiemSanVSAT.addActionListener(this::txtDiemSanVSATActionPerformed);

        txtDiemSanDGNL.setEditable(false);
        txtDiemSanDGNL.addActionListener(this::txtDiemSanDGNLActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtChiTieu))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChonToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiemSanTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiemSanVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiemSanDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(49, 49, 49)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnChonToHopGoc)
                    .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiemSanTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemSanVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemSanDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("asd");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý CT Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setToolTipText("");

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Chi tiết Ngành");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void txtMaNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNganhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNganhActionPerformed

    private void txtTenNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNganhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNganhActionPerformed

    private void btnChonToHopGocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonToHopGocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChonToHopGocActionPerformed

    private void txtToHopGocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToHopGocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToHopGocActionPerformed

    private void txtDiemTrungTuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemTrungTuyenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemTrungTuyenActionPerformed

    private void txtDiemSanTHPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanTHPTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanTHPTActionPerformed

    private void txtChiTieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChiTieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChiTieuActionPerformed

    private void txtDiemSanVSATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanVSATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanVSATActionPerformed

    private void txtDiemSanDGNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanDGNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanDGNLActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonToHopGoc;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtChiTieu;
    private javax.swing.JTextField txtDiemSanDGNL;
    private javax.swing.JTextField txtDiemSanTHPT;
    private javax.swing.JTextField txtDiemSanVSAT;
    private javax.swing.JTextField txtDiemTrungTuyen;
    private javax.swing.JTextField txtMaNganh;
    private javax.swing.JTextField txtTenNganh;
    private javax.swing.JTextField txtToHopGoc;
    // End of variables declaration//GEN-END:variables
}