/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import Entity.nganhETT;
import javax.swing.JOptionPane;
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
        
        // Mở khóa để người dùng tự gõ thông tin cơ bản
        txtMaNganh.setEditable(true);
        txtTenNganh.setEditable(true);
        txtToHopGoc.setEditable(true);
        txtChiTieu.setEditable(true);
        txtDiemSan.setEditable(true);
        txtDiemTrungTuyen.setEditable(true);
        
        // Giấu các nút chọn dư thừa
        btnChonMaNganh.setVisible(false);
        btnChonTenNganh.setVisible(false);
        btnChonToHopGoc.setVisible(false);
        
        // Khóa các ô số lượng phương thức ban đầu
        txtTuyenThang.setEnabled(false);
        txtThiDGNL.setEnabled(false);
        txtThiTHPT.setEnabled(false);
        txtThiVSAT.setEnabled(false);
        
        // Lắp ráp ổ khóa thông minh: tích vào ô vuông thì mới mở ô nhập số
        jCheckBox1.addActionListener(evt -> txtTuyenThang.setEnabled(jCheckBox1.isSelected()));
        jCheckBox2.addActionListener(evt -> txtThiDGNL.setEnabled(jCheckBox2.isSelected()));
        jCheckBox3.addActionListener(evt -> txtThiTHPT.setEnabled(jCheckBox3.isSelected()));
        jCheckBox4.addActionListener(evt -> txtThiVSAT.setEnabled(jCheckBox4.isSelected()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenNganh = new javax.swing.JTextField();
        btnChonMaNganh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtMaNganh = new javax.swing.JTextField();
        btnChonTenNganh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiemSan = new javax.swing.JTextField();
        txtToHopGoc = new javax.swing.JTextField();
        btnChonToHopGoc = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtChiTieu = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDiemTrungTuyen = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        txtTuyenThang = new javax.swing.JTextField();
        txtThiDGNL = new javax.swing.JTextField();
        txtThiTHPT = new javax.swing.JTextField();
        txtThiVSAT = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm ngành học");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên ngành (*) :");

        txtTenNganh.setEditable(false);
        txtTenNganh.addActionListener(this::txtTenNganhActionPerformed);

        btnChonMaNganh.setText("...");
        btnChonMaNganh.setToolTipText("");
        btnChonMaNganh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChonMaNganh.addActionListener(this::btnChonMaNganhActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã ngành (*) : ");

        txtMaNganh.setEditable(false);
        txtMaNganh.addActionListener(this::txtMaNganhActionPerformed);

        btnChonTenNganh.setText("...");
        btnChonTenNganh.setToolTipText("");
        btnChonTenNganh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChonTenNganh.addActionListener(this::btnChonTenNganhActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tổ hợp gốc :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Điểm sàn :");

        txtDiemSan.setEditable(false);

        txtToHopGoc.setEditable(false);
        txtToHopGoc.addActionListener(this::txtToHopGocActionPerformed);

        btnChonToHopGoc.setText("...");
        btnChonToHopGoc.setToolTipText("");
        btnChonToHopGoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChonToHopGoc.addActionListener(this::btnChonToHopGocActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Chỉ tiêu :");

        txtChiTieu.setEditable(false);
        txtChiTieu.addActionListener(this::txtChiTieuActionPerformed);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Điểm trúng tuyển :");

        txtDiemTrungTuyen.setEditable(false);

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBox1.setText("Tuyển thẳng");
        jCheckBox1.addActionListener(this::jCheckBox1ActionPerformed);

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBox2.setText("Thi ĐGNL");

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBox3.setText("Thi THPT");

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBox4.setText("Thi V-SAT");
        jCheckBox4.addActionListener(this::jCheckBox4ActionPerformed);

        txtTuyenThang.setEditable(false);

        txtThiDGNL.setEditable(false);

        txtThiTHPT.setEditable(false);

        txtThiVSAT.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnChonToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnChonMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnChonTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiemSan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChonMaNganh)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChonTenNganh)
                    .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonToHopGoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiemSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(txtTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(txtThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(txtThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox4))
                .addContainerGap(20, Short.MAX_VALUE))
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
        // Chạy qua bộ kiểm tra xem có gõ thiếu hay gõ sai chỗ nào không
        if(!kiemTraHopLe()) {
            return;
        }
        
        // Gom thông tin tạo thành một khối dữ liệu mới
        nganhETT nganhMoi = new nganhETT();
        nganhMoi.setManganh(txtMaNganh.getText().trim());
        nganhMoi.setTennganh(txtTenNganh.getText().trim());
        nganhMoi.setN_tohopgoc(txtToHopGoc.getText().trim());
        nganhMoi.setN_chitieu(Integer.parseInt(txtChiTieu.getText().trim()));
        
        // Móc điểm sàn và điểm chuẩn (nếu để trống thì bỏ qua)
        try {
            if(!txtDiemSan.getText().trim().isEmpty()) nganhMoi.setN_diemsan(Double.parseDouble(txtDiemSan.getText().trim()));
            if(!txtDiemTrungTuyen.getText().trim().isEmpty()) nganhMoi.setN_diemtrungtuyen(Double.parseDouble(txtDiemTrungTuyen.getText().trim()));
        } catch(Exception e){}
        
        // Ghi nhận các phương thức tuyển sinh
        if (jCheckBox1.isSelected()) {
            nganhMoi.setN_tuyenthang("1");
            nganhMoi.setSl_xtt(Integer.parseInt(txtTuyenThang.getText().trim()));
        }
        
        if (jCheckBox2.isSelected()) {
            nganhMoi.setN_dgnl("1");
            nganhMoi.setSl_dgnl(Integer.parseInt(txtThiDGNL.getText().trim()));
        }
        
        if (jCheckBox3.isSelected()) {
            nganhMoi.setN_thpt("1");
            // Kho lưu trữ của bạn để cột này dạng chữ, nên cứ nhét thẳng nội dung vào
            nganhMoi.setSl_dgnl(Integer.parseInt(txtThiTHPT.getText().trim()));
        }
        
        if (jCheckBox4.isSelected()) {
            nganhMoi.setN_vsat("1");
            nganhMoi.setSl_vsat(Integer.parseInt(txtThiVSAT.getText().trim()));
        }
        
        // Xác nhận hoàn thành công việc đóng gói và đóng cửa sổ lại
        this.nganh = nganhMoi;
        this.xacNhan = true;
        dispose();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTenNganhActionPerformed(java.awt.event.ActionEvent evt) {}
    private void btnChonMaNganhActionPerformed(java.awt.event.ActionEvent evt) {}
    private void txtMaNganhActionPerformed(java.awt.event.ActionEvent evt) {}
    private void btnChonTenNganhActionPerformed(java.awt.event.ActionEvent evt) {}
    private void txtToHopGocActionPerformed(java.awt.event.ActionEvent evt) {}
    private void btnChonToHopGocActionPerformed(java.awt.event.ActionEvent evt) {}
    private void txtChiTieuActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {}


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
            JOptionPane.showMessageDialog(this, "Tổng chỉ tiêu phải là một con số"); 
            txtChiTieu.requestFocus();
            return false; 
        }
        
        if (jCheckBox1.isSelected()) {
            try { Integer.parseInt(txtTuyenThang.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu Tuyển thẳng phải là con số"); return false; }
        }
        if (jCheckBox2.isSelected()) {
            try { Integer.parseInt(txtThiDGNL.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu ĐGNL phải là con số"); return false; }
        }
        if (jCheckBox3.isSelected()) {
            try { Integer.parseInt(txtThiTHPT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu THPT phải là con số"); return false; }
        }
        if (jCheckBox4.isSelected()) {
            try { Integer.parseInt(txtThiVSAT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu V-SAT phải là con số"); return false; }
        }
        
        return true;
    }
    
    public boolean xacNhanThem() {
        return xacNhan;
    }
    
    public nganhETT getNganh() {
        return nganh;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonMaNganh;
    private javax.swing.JButton btnChonTenNganh;
    private javax.swing.JButton btnChonToHopGoc;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtChiTieu;
    private javax.swing.JTextField txtDiemSan;
    private javax.swing.JTextField txtDiemTrungTuyen;
    private javax.swing.JTextField txtMaNganh;
    private javax.swing.JTextField txtTenNganh;
    private javax.swing.JTextField txtThiDGNL;
    private javax.swing.JTextField txtThiTHPT;
    private javax.swing.JTextField txtThiVSAT;
    private javax.swing.JTextField txtToHopGoc;
    private javax.swing.JTextField txtTuyenThang;
    // End of variables declaration//GEN-END:variables
}