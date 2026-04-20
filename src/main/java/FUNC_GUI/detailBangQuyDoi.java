/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import Entity.bangQuyDoiETT;

/**
 *
 */
public class detailBangQuyDoi extends javax.swing.JDialog {

    private bangQuyDoiETT quyDoi;

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(detailBangQuyDoi.class.getName());

    public detailBangQuyDoi(java.awt.Frame parent, boolean modal, bangQuyDoiETT current) {
        super(parent, modal);
        this.quyDoi = current;
        initComponents();
        setTitle("Chi tiết bảng quy đổi");
        setLocationRelativeTo(parent);
        fillData(current);
    }

    private void fillData(bangQuyDoiETT current) {
        if (current == null) {
            return;
        }

        txtId.setText(String.valueOf(current.getIdqd()));
        txtMaQuyDoi.setText(nvl(current.getMaQuyDoi()));
        txtMon.setText(nvl(current.getMon()));
        txtPhuongThuc.setText(nvl(current.getPhuongThuc()));
        txtToHop.setText(nvl(current.getToHop()));
        txtPhanVi.setText(nvl(current.getPhanVi()));
        txtDiemA.setText(safe(current.getDiemA()));
        txtDiemB.setText(safe(current.getDiemB()));
        txtDiemC.setText(safe(current.getDiemC()));
        txtDiemD.setText(safe(current.getDiemD()));
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }

    private static String safe(Double d) {
        return d == null ? "" : String.valueOf(d);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblMaQuyDoi = new javax.swing.JLabel();
        txtMaQuyDoi = new javax.swing.JTextField();
        lblMon = new javax.swing.JLabel();
        txtMon = new javax.swing.JTextField();
        lblPhuongThuc = new javax.swing.JLabel();
        txtPhuongThuc = new javax.swing.JTextField();
        lblToHop = new javax.swing.JLabel();
        txtToHop = new javax.swing.JTextField();
        lblPhanVi = new javax.swing.JLabel();
        txtPhanVi = new javax.swing.JTextField();
        lblDiemA = new javax.swing.JLabel();
        txtDiemA = new javax.swing.JTextField();
        lblDiemB = new javax.swing.JLabel();
        txtDiemB = new javax.swing.JTextField();
        lblDiemC = new javax.swing.JLabel();
        txtDiemC = new javax.swing.JTextField();
        lblDiemD = new javax.swing.JLabel();
        txtDiemD = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi tiết bảng quy đổi");

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setText("Chi tiết bảng quy đổi");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabelTitle)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabelTitle)
                .addGap(14, 14, 14))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết bảng quy đổi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblId.setText("ID :");

        txtId.setEditable(false);
        txtId.setBackground(new java.awt.Color(239, 239, 239));

        lblMaQuyDoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaQuyDoi.setText("Mã quy đổi :");

        txtMaQuyDoi.setEditable(false);
        txtMaQuyDoi.setBackground(new java.awt.Color(239, 239, 239));

        lblMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMon.setText("Môn :");

        txtMon.setEditable(false);
        txtMon.setBackground(new java.awt.Color(239, 239, 239));

        lblPhuongThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhuongThuc.setText("Phương thức :");

        txtPhuongThuc.setEditable(false);
        txtPhuongThuc.setBackground(new java.awt.Color(239, 239, 239));

        lblToHop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblToHop.setText("Tổ hợp :");

        txtToHop.setEditable(false);
        txtToHop.setBackground(new java.awt.Color(239, 239, 239));

        lblPhanVi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhanVi.setText("Phân vị :");

        txtPhanVi.setEditable(false);
        txtPhanVi.setBackground(new java.awt.Color(239, 239, 239));

        lblDiemA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiemA.setText("Điểm A :");

        txtDiemA.setEditable(false);
        txtDiemA.setBackground(new java.awt.Color(239, 239, 239));

        lblDiemB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiemB.setText("Điểm B :");

        txtDiemB.setEditable(false);
        txtDiemB.setBackground(new java.awt.Color(239, 239, 239));

        lblDiemC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiemC.setText("Điểm C :");

        txtDiemC.setEditable(false);
        txtDiemC.setBackground(new java.awt.Color(239, 239, 239));

        lblDiemD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiemD.setText("Điểm D :");

        txtDiemD.setEditable(false);
        txtDiemD.setBackground(new java.awt.Color(239, 239, 239));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMon, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhanVi, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiemA, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiemB, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiemC, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiemD, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhanVi, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemA, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemB, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemC, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemD, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaQuyDoi)
                    .addComponent(txtMaQuyDoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMon)
                    .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhuongThuc)
                    .addComponent(txtPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblToHop)
                    .addComponent(txtToHop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhanVi)
                    .addComponent(txtPhanVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiemA)
                    .addComponent(txtDiemA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiemB)
                    .addComponent(txtDiemB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiemC)
                    .addComponent(txtDiemC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiemD)
                    .addComponent(txtDiemD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnThoat.setBackground(new java.awt.Color(255, 101, 101));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiemA;
    private javax.swing.JLabel lblDiemB;
    private javax.swing.JLabel lblDiemC;
    private javax.swing.JLabel lblDiemD;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMaQuyDoi;
    private javax.swing.JLabel lblMon;
    private javax.swing.JLabel lblPhanVi;
    private javax.swing.JLabel lblPhuongThuc;
    private javax.swing.JLabel lblToHop;
    private javax.swing.JTextField txtDiemA;
    private javax.swing.JTextField txtDiemB;
    private javax.swing.JTextField txtDiemC;
    private javax.swing.JTextField txtDiemD;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMaQuyDoi;
    private javax.swing.JTextField txtMon;
    private javax.swing.JTextField txtPhanVi;
    private javax.swing.JTextField txtPhuongThuc;
    private javax.swing.JTextField txtToHop;
    // End of variables declaration//GEN-END:variables
}
