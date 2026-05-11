/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import Entity.nganhETT;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author Dat
 */
public class insertNganh extends javax.swing.JDialog {
    
    public boolean xacNhan = false;
    public nganhETT nganh;
    
    public insertNganh(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        txtDiemTrungTuyen.setEditable(false);
        txtDiemTrungTuyen.setFocusable(false);
        txtChiTieu.setEditable(true); 
        txtChiTieu.setFocusable(true);

        // Mở khóa để người dùng tự gõ thông tin cơ bản
        txtMaNganh.setEditable(true);
        txtTenNganh.setEditable(true);
        txtToHopGoc.setEditable(true);
        txtDiemSanTHPT.setEditable(true);
        txtDiemSanVSAT.setEditable(true);
        txtDiemSanDGNL.setEditable(true);
        
        // Khóa các ô số lượng phương thức ban đầu
        txtDiemTrungTuyen.setEditable(false);
        txtDiemTrungTuyen.setFocusable(false);
        txtDiemTrungTuyen.setText("30");

//        // Lắp ráp ổ khóa thông minh: tích vào ô vuông thì mới mở ô nhập số
//        chkTuyenThang.addActionListener(evt -> {
//            boolean isSelected = chkTuyenThang.isSelected();
//            txtTuyenThang.setEnabled(isSelected);
//            txtTuyenThang.setEditable(isSelected);
//        });
//        chkThiDGNL.addActionListener(evt -> {
//            boolean isSelected = chkThiDGNL.isSelected();
//            txtThiDGNL.setEnabled(isSelected);
//            txtThiDGNL.setEditable(isSelected);
//        });
//        chkThiTHPT.addActionListener(evt -> {
//            boolean isSelected = chkThiTHPT.isSelected();
//            txtThiTHPT.setEnabled(isSelected);
//            txtThiTHPT.setEditable(isSelected);
//        });
//        chkThiVSAT.addActionListener(evt -> {
//            boolean isSelected = chkThiVSAT.isSelected();
//            txtThiVSAT.setEnabled(isSelected);
//            txtThiVSAT.setEditable(isSelected);
//        });

    }

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
        txtDiemSanDGNL = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiemSanVSAT = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm Ngành");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

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
        txtDiemTrungTuyen.setText("30");
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
        jLabel5.setText("Điểm sàn ĐGNL :");

        txtDiemSanDGNL.setEditable(false);
        txtDiemSanDGNL.addActionListener(this::txtDiemSanDGNLActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Điểm sàn V-SAT :");

        txtDiemSanVSAT.setEditable(false);
        txtDiemSanVSAT.addActionListener(this::txtDiemSanVSATActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDiemSanDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDiemSanVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(49, 49, 49)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(jLabel6))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemSanDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("asd");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý thêm Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setToolTipText("");

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.addActionListener(this::btnThemActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Thêm Ngành");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(119, 119, 119))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    // CÁC HÀM XỬ LÝ SỰ KIỆN NÚT BẤM (EVENTS)

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        xacNhan = false;
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

            if(!kiemTraHopLe()) {
                return;
            }

        BUS.nganhBUS busNganh = new BUS.nganhBUS();
        nganhETT nganhMoi = new nganhETT();

        nganhMoi.setManganh(txtMaNganh.getText().trim());
        nganhMoi.setTennganh(txtTenNganh.getText().trim());
        nganhMoi.setN_tohopgoc(txtToHopGoc.getText().trim());

        nganhMoi.setN_chitieu(Integer.parseInt(txtChiTieu.getText().trim()));

        try {
            if(!txtDiemSanTHPT.getText().trim().isEmpty()) 
                nganhMoi.setN_diemsanthpt(Double.parseDouble(txtDiemSanTHPT.getText().trim()));

            if(!txtDiemSanVSAT.getText().trim().isEmpty()) 
                nganhMoi.setN_diemsanvsat(Double.parseDouble(txtDiemSanVSAT.getText().trim()));

            if(!txtDiemSanDGNL.getText().trim().isEmpty()) 
                nganhMoi.setN_diemsandgnl(Double.parseDouble(txtDiemSanDGNL.getText().trim()));

            if(!txtDiemTrungTuyen.getText().trim().isEmpty()) 
                nganhMoi.setN_diemtrungtuyen(Double.parseDouble(txtDiemTrungTuyen.getText().trim()));
        } catch(NumberFormatException e) {
        }
        
        // Gán cứng giá trị 0 cho các cột chi tiết
        nganhMoi.setN_tuyenthang("0");
        nganhMoi.setSl_xtt(0);
        nganhMoi.setN_dgnl("0");
        nganhMoi.setSl_dgnl(0);
        nganhMoi.setN_thpt("0");
        nganhMoi.setSl_thpt(0);
        nganhMoi.setN_vsat("0");
        nganhMoi.setSl_vsat(0);

//        nganhMoi.setN_tuyenthang(chkTuyenThang.isSelected() ? "1" : "0");
//        nganhMoi.setSl_xtt(chkTuyenThang.isSelected() ? Integer.parseInt(txtTuyenThang.getText().trim()) : 0);
//
//        nganhMoi.setN_dgnl(chkThiDGNL.isSelected() ? "1" : "0");
//        nganhMoi.setSl_dgnl(chkThiDGNL.isSelected() ? Integer.parseInt(txtThiDGNL.getText().trim()) : 0);
//
//        nganhMoi.setN_thpt(chkThiTHPT.isSelected() ? "1" : "0");
//        nganhMoi.setSl_thpt(chkThiTHPT.isSelected() ? Integer.parseInt(txtThiTHPT.getText().trim()) : 0);
//
//        nganhMoi.setN_vsat(chkThiVSAT.isSelected() ? "1" : "0");
//        nganhMoi.setSl_vsat(chkThiVSAT.isSelected() ? Integer.parseInt(txtThiVSAT.getText().trim()) : 0);

        if(busNganh.themNganh(nganhMoi)) {
            xacNhan = true;
            JOptionPane.showMessageDialog(this, "Thêm ngành thành công");
            this.nganh = nganhMoi;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm ngành thất bại");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNganhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNganhActionPerformed

    private void txtTenNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNganhActionPerformed
        // TODO add your handling code here:
        // 1. Triệu hồi Dialog chọn Tổ hợp (re-use lại cái bạn đã có)
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SELECT_GUI.selectToHop dialog = new SELECT_GUI.selectToHop(topFrame, true);
        dialog.setVisible(true);

        // 2. Nếu người dùng chọn xong và bấm xác nhận
        if (dialog.getXacNhan()) {
            Entity.toHopETT selectedToHop = dialog.getToHopETT();
            // 3. Đổ mã tổ hợp vào ô text
            txtToHopGoc.setText(selectedToHop.getMatohop());
        }
    }//GEN-LAST:event_txtTenNganhActionPerformed

    private void btnChonToHopGocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonToHopGocActionPerformed
        // TODO add your handling code here:
        // 1. Triệu hồi Dialog chọn Tổ hợp (re-use lại cái bạn đã có)
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SELECT_GUI.selectToHop dialog = new SELECT_GUI.selectToHop(topFrame, true);
        dialog.setVisible(true);

        // 2. Nếu người dùng chọn xong và bấm xác nhận
        if (dialog.getXacNhan()) {
            Entity.toHopETT selectedToHop = dialog.getToHopETT();
            // 3. Đổ mã tổ hợp vào ô text
            txtToHopGoc.setText(selectedToHop.getMatohop());
        }
    }//GEN-LAST:event_btnChonToHopGocActionPerformed

    private void txtToHopGocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToHopGocActionPerformed
        // TODO add your handling code here:
        // 1. Khởi tạo Dialog chọn Tổ hợp (Dùng lại cái bạn đã làm cho Nguyện vọng)
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SELECT_GUI.selectToHop dialog = new SELECT_GUI.selectToHop(topFrame, true);
        dialog.setVisible(true);

        // 2. Nếu người dùng chọn xong và bấm nút "Xác nhận" trên Dialog
        if (dialog.getXacNhan()) {
        Entity.toHopETT selectedToHop = dialog.getToHopETT();
        
            // 3. Đổ Mã tổ hợp vào ô Textbox
            txtToHopGoc.setText(selectedToHop.getMatohop()); 
        }
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

    private void txtDiemSanDGNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanDGNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanDGNLActionPerformed

    private void txtDiemSanVSATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanVSATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanVSATActionPerformed

    // CÁC HÀM XỬ LÝ LOGIC PHỤ TRỢ

    // Kiểm tra người dùng nhập có đúng không
    public boolean kiemTraHopLe() {
        if(txtMaNganh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống Mã ngành");
            txtMaNganh.requestFocus();
            return false;
        }
        
        if(txtTenNganh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống Tên ngành");
            txtTenNganh.requestFocus();
            return false;
        }
        
        if(txtChiTieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống Tổng chỉ tiêu");
            txtChiTieu.requestFocus();
            return false;
        }
        
        // Coi số chỉ tiêu có bị gõ nhầm thành chữ cái không
        try { 
            Integer.parseInt(txtChiTieu.getText().trim()); 
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Chỉ tiêu phải là một con số nguyên hợp lệ"); 
            txtChiTieu.requestFocus();
            return false; 
        }
        
//        if (chkTuyenThang.isSelected()) {
//            try { Integer.parseInt(txtTuyenThang.getText().trim()); } 
//            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu Tuyển thẳng phải là con số"); return false; }
//        }
//        if (chkThiDGNL.isSelected()) {
//            try { Integer.parseInt(txtThiDGNL.getText().trim()); } 
//            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu ĐGNL phải là con số"); return false; }
//        }
//        if (chkThiTHPT.isSelected()) {
//            try { Integer.parseInt(txtThiTHPT.getText().trim()); } 
//            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu THPT phải là con số"); return false; }
//        }
//        if (chkThiVSAT.isSelected()) {
//            try { Integer.parseInt(txtThiVSAT.getText().trim()); } 
//            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu V-SAT phải là con số"); return false; }
//        }
        
        try {
            if (!txtDiemSanTHPT.getText().trim().isEmpty()) Double.parseDouble(txtDiemSanTHPT.getText().trim());
            if (!txtDiemSanVSAT.getText().trim().isEmpty()) Double.parseDouble(txtDiemSanVSAT.getText().trim());
            if (!txtDiemSanDGNL.getText().trim().isEmpty()) Double.parseDouble(txtDiemSanDGNL.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm sàn phải là một con số hợp lệ (ví dụ: 18.5)");
            return false;
        }
        return true;
    }

//    private void tuDongTinhTong() {
//        int xttt = 0, dgnl = 0, vsat = 0, thpt = 0;
//    
//        try {
//            // Chỉ lấy giá trị nếu ô đó đang được chọn (Enabled)
//            if (txtTuyenThang.isEnabled() && !txtTuyenThang.getText().trim().isEmpty()) {
//                xttt = Integer.parseInt(txtTuyenThang.getText().trim());
//            }
//            if (txtThiDGNL.isEnabled() && !txtThiDGNL.getText().trim().isEmpty()) {
//                dgnl = Integer.parseInt(txtThiDGNL.getText().trim());
//            }
//            if (txtThiVSAT.isEnabled() && !txtThiVSAT.getText().trim().isEmpty()) {
//                vsat = Integer.parseInt(txtThiVSAT.getText().trim());
//            }
//            if (txtThiTHPT.isEnabled() && !txtThiTHPT.getText().trim().isEmpty()) {
//                thpt = Integer.parseInt(txtThiTHPT.getText().trim());
//            }
//        } catch (NumberFormatException e) {
//            // Nếu user gõ chữ, ta bỏ qua không cộng phần tử đó
//        }
//
//    // Thực hiện công thức: n_chitieu = sl_xttt + sl_dgnl + sl_vsat + sl_thpt
//    int tong = xttt + dgnl + vsat + thpt;
//    txtChiTieu.setText(String.valueOf(tong));
//}
    
    public boolean xacNhanThem() {
        return xacNhan;
    }
    
    public nganhETT getNganh() {
        return nganh;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonToHopGoc;
    private javax.swing.JButton btnThem;
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