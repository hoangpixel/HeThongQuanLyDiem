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
public class updateNganh extends javax.swing.JDialog {
    
    public boolean xacNhan = false;
    public nganhETT nganh;
    BUS.nganhBUS bus = new BUS.nganhBUS();
    
    public updateNganh(java.awt.Frame parent, boolean modal, nganhETT data) {
        super(parent, modal);
        initComponents();
        this.nganh = data; 
        setLocationRelativeTo(parent);
    
        // 1. Đổ thông tin cơ bản
        txtMaNganh.setText(data.getManganh());
        txtMaNganh.setEditable(false);
        txtTenNganh.setText(data.getTennganh());
        txtToHopGoc.setText(data.getN_tohopgoc());
        txtChiTieu.setText(data.getN_chitieu() != null ? String.valueOf(data.getN_chitieu()) : "0");
        txtDiemSanTHPT.setText(data.getN_diemsan() != null ? String.valueOf(data.getN_diemsan()) : "");
        txtDiemTrungTuyen.setText(data.getN_diemtrungtuyen() != null ? String.valueOf(data.getN_diemtrungtuyen()) : "");

        // 2. Cấu hình ô Tổng chỉ tiêu
        txtChiTieu.setEditable(false);
        txtChiTieu.setFocusable(false);
        txtChiTieu.setBackground(new java.awt.Color(240, 240, 240));

        // 3. Khởi tạo các phương thức (Chỉ cần gọi 1 hàm duy nhất này)
        thietLapOChon(chkTuyenThang, txtTuyenThang, data.getN_tuyenthang(), data.getSl_xtt());
        thietLapOChon(chkThiDGNL, txtThiDGNL, data.getN_dgnl(), data.getSl_dgnl());
        thietLapOChon(chkThiTHPT, txtThiTHPT, data.getN_thpt(), data.getSl_thpt());
        thietLapOChon(chkThiVSAT, txtThiVSAT, data.getN_vsat(), data.getSl_vsat());

        // 4. Tính toán tổng lần đầu tiên khi mở form
        //tuDongTinhTong();
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
        chkThiDGNL = new javax.swing.JCheckBox();
        chkThiTHPT = new javax.swing.JCheckBox();
        txtTuyenThang = new javax.swing.JTextField();
        txtThiDGNL = new javax.swing.JTextField();
        txtThiTHPT = new javax.swing.JTextField();
        txtThiVSAT = new javax.swing.JTextField();
        btnChonToHopGoc = new javax.swing.JButton();
        txtMaNganh = new javax.swing.JTextField();
        txtTenNganh = new javax.swing.JTextField();
        txtToHopGoc = new javax.swing.JTextField();
        txtChiTieu = new javax.swing.JTextField();
        chkTuyenThang = new javax.swing.JCheckBox();
        chkThiVSAT = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDiemSanVSAT = new javax.swing.JTextField();
        txtDiemSanDGNL = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sửa Ngành");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sửa Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

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

        chkThiDGNL.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkThiDGNL.setText("Thi ĐGNL");
        chkThiDGNL.addActionListener(this::chkThiDGNLActionPerformed);

        chkThiTHPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkThiTHPT.setText("Thi THPT");

        txtTuyenThang.setEditable(false);
        txtTuyenThang.addActionListener(this::txtTuyenThangActionPerformed);
        txtTuyenThang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTuyenThangKeyReleased(evt);
            }
        });

        txtThiDGNL.setEditable(false);
        txtThiDGNL.addActionListener(this::txtThiDGNLActionPerformed);
        txtThiDGNL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThiDGNLKeyReleased(evt);
            }
        });

        txtThiTHPT.setEditable(false);
        txtThiTHPT.addActionListener(this::txtThiTHPTActionPerformed);
        txtThiTHPT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThiTHPTKeyReleased(evt);
            }
        });

        txtThiVSAT.setEditable(false);
        txtThiVSAT.addActionListener(this::txtThiVSATActionPerformed);
        txtThiVSAT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThiVSATKeyReleased(evt);
            }
        });

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

        chkTuyenThang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkTuyenThang.setText("Tuyển thẳng");
        chkTuyenThang.addActionListener(this::chkTuyenThangActionPerformed);

        chkThiVSAT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkThiVSAT.setText("Thi V-SAT");
        chkThiVSAT.addActionListener(this::chkThiVSATActionPerformed);

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTuyenThang)
                    .addComponent(txtTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkThiDGNL)
                    .addComponent(txtThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkThiTHPT)
                    .addComponent(txtThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkThiVSAT)
                    .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("asd");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý sửa Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setToolTipText("");

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setText("Sửa");
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

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Sửa Ngành");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(131, 131, 131)
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

    // CÁC HÀM XỬ LÝ SỰ KIỆN NÚT BẤM (EVENTS)

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        if(!kiemTraHopLe()) 
        {
            return;
        }

        nganh.setTennganh(txtTenNganh.getText().trim());
        nganh.setN_tohopgoc(txtToHopGoc.getText().trim());
        // Lấy con số tổng tự động
        nganh.setN_chitieu(Integer.parseInt(txtChiTieu.getText().trim()));

        try {
            nganh.setN_diemsan(txtDiemSanTHPT.getText().isEmpty() ? null : Double.valueOf(txtDiemSanTHPT.getText()));
            nganh.setN_diemtrungtuyen(txtDiemTrungTuyen.getText().isEmpty() ? null : Double.valueOf(txtDiemTrungTuyen.getText()));
        } catch(Exception e){e.printStackTrace();}

        nganh.setN_tuyenthang(chkTuyenThang.isSelected() ? "1" : "0");
        nganh.setSl_xtt(chkTuyenThang.isSelected() ? Integer.parseInt(txtTuyenThang.getText().trim()) : 0);
        nganh.setN_dgnl(chkThiDGNL.isSelected() ? "1" : "0");
        nganh.setSl_dgnl(chkThiDGNL.isSelected() ? Integer.parseInt(txtThiDGNL.getText().trim()) : 0);
        this.nganh.setN_thpt(chkThiTHPT.isSelected() ? "1" : "0");
        this.nganh.setSl_thpt(chkThiTHPT.isSelected() ? Integer.parseInt(txtThiTHPT.getText().trim()) : 0);
        nganh.setN_vsat(chkThiVSAT.isSelected() ? "1" : "0");
        nganh.setSl_vsat(chkThiVSAT.isSelected() ? Integer.parseInt(txtThiVSAT.getText().trim()) : 0);

        if (bus.suaNganh(this.nganh)) {
            this.xacNhan = true;
            JOptionPane.showMessageDialog(this, "Sửa ngành thành công");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa ngành thất bại");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        xacNhan = false;
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

    private void txtTuyenThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTuyenThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTuyenThangActionPerformed

    private void txtThiDGNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThiDGNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThiDGNLActionPerformed

    private void txtThiTHPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThiTHPTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThiTHPTActionPerformed

    private void txtThiVSATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThiVSATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThiVSATActionPerformed

    private void chkTuyenThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTuyenThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTuyenThangActionPerformed

    private void chkThiDGNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThiDGNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThiDGNLActionPerformed

    private void chkThiVSATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThiVSATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThiVSATActionPerformed

    private void txtTuyenThangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTuyenThangKeyReleased
        // TODO add your handling code here:
        tuDongTinhTong();
    }//GEN-LAST:event_txtTuyenThangKeyReleased

    private void txtThiDGNLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThiDGNLKeyReleased
        // TODO add your handling code here:
        tuDongTinhTong();
    }//GEN-LAST:event_txtThiDGNLKeyReleased

    private void txtThiTHPTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThiTHPTKeyReleased
        // TODO add your handling code here:
        tuDongTinhTong();
    }//GEN-LAST:event_txtThiTHPTKeyReleased

    private void txtThiVSATKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThiVSATKeyReleased
        // TODO add your handling code here:
        tuDongTinhTong();
    }//GEN-LAST:event_txtThiVSATKeyReleased

    private void txtDiemSanVSATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanVSATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanVSATActionPerformed

    private void txtDiemSanDGNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemSanDGNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemSanDGNLActionPerformed

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
        
        if (chkTuyenThang.isSelected()) {
            try { Integer.parseInt(txtTuyenThang.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu Tuyển thẳng phải là con số"); return false; }
        }
        if (chkThiDGNL.isSelected()) {
            try { Integer.parseInt(txtThiDGNL.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu ĐGNL phải là con số"); return false; }
        }
        if (chkThiTHPT.isSelected()) {
            try { Integer.parseInt(txtThiTHPT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu THPT phải là con số"); return false; }
        }
        if (chkThiVSAT.isSelected()) {
            try { Integer.parseInt(txtThiVSAT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Chỉ tiêu V-SAT phải là con số"); return false; }
        }
        
        return true;
    }

    private void tuDongTinhTong() {
        int xttt = 0, dgnl = 0, vsat = 0, thpt = 0;
        try {
            // Sử dụng helper để parse số an toàn
            xttt = parseSafeInt(txtTuyenThang.getText());
            dgnl = parseSafeInt(txtThiDGNL.getText());
            vsat = parseSafeInt(txtThiVSAT.getText());
            thpt = parseSafeInt(txtThiTHPT.getText());
        } catch (Exception e) {}

        int tong = xttt + dgnl + vsat + thpt;
        txtChiTieu.setText(String.valueOf(tong));
    }

        // Hàm phụ trợ để tránh lỗi khi ô text trống
    private int parseSafeInt(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void thietLapOChon(javax.swing.JCheckBox cb, javax.swing.JTextField txt, String n_val, Object sl_val) {
        int soLuong = 0;
        if (sl_val != null) {
            try {
                soLuong = Integer.parseInt(sl_val.toString());
            } catch (Exception e) {}
        }
        
        boolean active = ("1".equals(n_val)) || (soLuong > 0);
        cb.setSelected(active);
        txt.setEnabled(active);
        txt.setEditable(active);
        txt.setText(sl_val != null ? String.valueOf(sl_val) : "0");
        txt.setBackground(active ? java.awt.Color.WHITE : new java.awt.Color(240, 240, 240));

        // Gắn công tắc thông minh ngay tại đây
        cb.addActionListener(e -> {
            boolean s = cb.isSelected();
            txt.setEnabled(s);
            txt.setEditable(s);
            if (!s) txt.setText("0"); // Bỏ tích thì reset về 0
            txt.setBackground(s ? java.awt.Color.WHITE : new java.awt.Color(240, 240, 240));
            tuDongTinhTong(); // Tự động tính lại tổng chỉ tiêu
        });
    }
    
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
    private javax.swing.JCheckBox chkThiDGNL;
    private javax.swing.JCheckBox chkThiTHPT;
    private javax.swing.JCheckBox chkThiVSAT;
    private javax.swing.JCheckBox chkTuyenThang;
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
    private javax.swing.JTextField txtThiDGNL;
    private javax.swing.JTextField txtThiTHPT;
    private javax.swing.JTextField txtThiVSAT;
    private javax.swing.JTextField txtToHopGoc;
    private javax.swing.JTextField txtTuyenThang;
    // End of variables declaration//GEN-END:variables
}