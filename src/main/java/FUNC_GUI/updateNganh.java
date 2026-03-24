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
public class updateNganh extends javax.swing.JDialog {
    
    public boolean xacNhan = false;
    public nganhETT nganh;
    
    // Đón nhận kiện hàng mang từ kho lên để bày ra cửa sổ
    public updateNganh(java.awt.Frame parent, boolean modal, nganhETT data) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        // 1. Dọn dẹp tàn dư: Khóa chặt Mã Ngành, mở toang các cửa khác cho phép gõ sửa
        txtMaNganh.setEditable(false); 
        txtTenNganh.setEditable(true);
        txtToHopGoc.setEditable(true);
        txtChiTieu.setEditable(true);
        txtDiemSan.setEditable(true);
        txtDiemTrungTuyen.setEditable(true);
        
        // Giấu mấy cái nút bấm ba chấm không cần thiết
        btnChonTenNganh.setVisible(false);
        btnChonToHopGoc.setVisible(false);
        
        // 2. Bày thông tin cũ ra các ô tương ứng
        txtMaNganh.setText(data.getManganh());
        txtTenNganh.setText(data.getTennganh());
        txtToHopGoc.setText(data.getN_tohopgoc());
        
        if (data.getN_chitieu() != null) txtChiTieu.setText(String.valueOf(data.getN_chitieu()));
        if (data.getN_diemsan() != null) txtDiemSan.setText(String.valueOf(data.getN_diemsan()));
        if (data.getN_diemtrungtuyen() != null) txtDiemTrungTuyen.setText(String.valueOf(data.getN_diemtrungtuyen()));
        
        // 3. Xử lý 4 ô đánh dấu: Nếu ngày xưa có dùng thì đánh dấu tích và hiện số lượng cũ lên
        thietLapOChon(jCheckBox1, txtTuyenThang, data.getN_tuyenthang(), data.getSl_xtt() != null ? String.valueOf(data.getSl_xtt()) : "");
        thietLapOChon(jCheckBox2, txtThiDGNL, data.getN_dgnl(), data.getSl_dgnl() != null ? String.valueOf(data.getSl_dgnl()) : "");
        thietLapOChon(jCheckBox3, txtThiTHPT, data.getN_thpt(), data.getSl_thpt() != null ? String.valueOf(data.getSl_thpt()) : "");
        thietLapOChon(jCheckBox4, txtThiVSAT, data.getN_vsat(), data.getSl_vsat() != null ? String.valueOf(data.getSl_vsat()) : "");
        
        // 4. Lắp công tắc thông minh: Bấm tích thì mới mở khóa ô trống bên cạnh
        jCheckBox1.addActionListener(e -> txtTuyenThang.setEnabled(jCheckBox1.isSelected()));
        jCheckBox2.addActionListener(e -> txtThiDGNL.setEnabled(jCheckBox2.isSelected()));
        jCheckBox3.addActionListener(e -> txtThiTHPT.setEnabled(jCheckBox3.isSelected()));
        jCheckBox4.addActionListener(e -> txtThiVSAT.setEnabled(jCheckBox4.isSelected()));
        
        // Cực kỳ quan trọng: Giữ lại cái số thẻ bài (ID) để lát đem cất đè lên đúng chỗ cũ
        this.nganh = new nganhETT();
        this.nganh.setIdnganh(data.getIdnganh());
    }

    // Hành động phụ để dọn dẹp màn hình cho lẹ
    private void thietLapOChon(javax.swing.JCheckBox oDanhDau, javax.swing.JTextField oDienSo, String coDung, String soLuong) {
        if ("1".equals(coDung)) {
            oDanhDau.setSelected(true);
            oDienSo.setEnabled(true);
            oDienSo.setText(soLuong);
        } else {
            oDanhDau.setSelected(false);
            oDienSo.setEnabled(false);
            oDienSo.setText("");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNganh = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTenNganh = new javax.swing.JTextField();
        btnChonTenNganh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtToHopGoc = new javax.swing.JTextField();
        btnChonToHopGoc = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtChiTieu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDiemSan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
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
        btnSua = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel10.setText("Chỉ tiêu :");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sửa thông tin Ngành Học");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết Ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setText("Mã ngành (*) : ");

        txtMaNganh.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel1.setText("Tên ngành (*) :");

        btnChonTenNganh.setText("...");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel3.setText("Tổ hợp gốc :");

        btnChonToHopGoc.setText("...");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel11.setText("Chỉ tiêu (*) :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel4.setText("Điểm sàn :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel12.setText("Điểm trúng tuyển :");

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jCheckBox1.setText("Tuyển thẳng");

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jCheckBox2.setText("Thi ĐGNL");

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jCheckBox3.setText("Thi THPT");

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jCheckBox4.setText("Thi V-SAT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(btnChonTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChonToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTuyenThang, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtThiDGNL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDiemTrungTuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDiemSan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtThiTHPT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonTenNganh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtToHopGoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonToHopGoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiemSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
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
                    .addComponent(jCheckBox4)
                    .addComponent(txtThiVSAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnSua.setText("Cập nhật");
        btnSua.addActionListener(this::btnSuaActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Sửa Ngành Học");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
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
    }// </editor-fold>                        

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {
        xacNhan = false;
        dispose();
    }

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {
        // Kiểm tra xem gõ đủ chữ và đúng số liệu chưa
        if(!kiemTraHopLe()) {
            return;
        }
        
        // Đóng gói đồ đạc mới để chuẩn bị đem cất
        this.nganh.setManganh(txtMaNganh.getText().trim());
        this.nganh.setTennganh(txtTenNganh.getText().trim());
        this.nganh.setN_tohopgoc(txtToHopGoc.getText().trim());
        this.nganh.setN_chitieu(Integer.parseInt(txtChiTieu.getText().trim()));
        
        try {
            if(!txtDiemSan.getText().trim().isEmpty()) this.nganh.setN_diemsan(Double.parseDouble(txtDiemSan.getText().trim()));
            if(!txtDiemTrungTuyen.getText().trim().isEmpty()) this.nganh.setN_diemtrungtuyen(Double.parseDouble(txtDiemTrungTuyen.getText().trim()));
        } catch(Exception e){}
        
        // Ghi lại thông tin 4 phương thức
        this.nganh.setN_tuyenthang(jCheckBox1.isSelected() ? "1" : "0");
        this.nganh.setSl_xtt(jCheckBox1.isSelected() ? Integer.parseInt(txtTuyenThang.getText().trim()) : 0);

        this.nganh.setN_dgnl(jCheckBox2.isSelected() ? "1" : "0");
        this.nganh.setSl_dgnl(jCheckBox2.isSelected() ? Integer.parseInt(txtThiDGNL.getText().trim()) : 0);

        this.nganh.setN_thpt(jCheckBox3.isSelected() ? "1" : "0");
        this.nganh.setSl_thpt(jCheckBox3.isSelected() ? Integer.parseInt(txtThiTHPT.getText().trim()) : 0);

        this.nganh.setN_vsat(jCheckBox4.isSelected() ? "1" : "0");
        this.nganh.setSl_vsat(jCheckBox4.isSelected() ? Integer.parseInt(txtThiVSAT.getText().trim()) : 0);
        
        // Báo hiệu xong việc
        this.xacNhan = true;
        dispose();
    }

    public boolean kiemTraHopLe() {
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
        
        try { 
            Integer.parseInt(txtChiTieu.getText().trim()); 
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Tổng chỉ tiêu phải gõ bằng số"); 
            txtChiTieu.requestFocus();
            return false; 
        }
        
        if (jCheckBox1.isSelected()) {
            try { Integer.parseInt(txtTuyenThang.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Số lượng Tuyển thẳng phải gõ bằng số"); return false; }
        }
        if (jCheckBox2.isSelected()) {
            try { Integer.parseInt(txtThiDGNL.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Số lượng ĐGNL phải gõ bằng số"); return false; }
        }
        if (jCheckBox3.isSelected()) {
            try { Integer.parseInt(txtThiTHPT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Số lượng THPT phải gõ bằng số"); return false; }
        }
        if (jCheckBox4.isSelected()) {
            try { Integer.parseInt(txtThiVSAT.getText().trim()); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Số lượng V-SAT phải gõ bằng số"); return false; }
        }
        
        return true;
    }
    
    public boolean xacNhanSua() {
        return xacNhan;
    }
    
    public nganhETT getNganh() {
        return nganh;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnChonTenNganh;
    private javax.swing.JButton btnChonToHopGoc;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThoat;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    // End of variables declaration                    
}