/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FUNC_GUI;

import Entity.diemThiETT;
import javax.swing.JOptionPane;

/**
 *
 */
public class updateDiemThi extends javax.swing.JDialog {

    public boolean xacNhan = false;
    private diemThiETT diemThi;
    private final diemThiETT original;

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(updateDiemThi.class.getName());

    public updateDiemThi(java.awt.Frame parent, boolean modal, diemThiETT current) {
        super(parent, modal);
        this.original = current;
        initComponents();
        setTitle("Cập nhật bảng điểm thi");
        setLocationRelativeTo(parent);
        fillData(current);
    }

    public diemThiETT getDiemThi() {
        return diemThi;
    }

    private void fillData(diemThiETT current) {
        if (current == null) {
            return;
        }
        txtID.setText(String.valueOf(current.getIddiemthi()));
        txtCCCD.setText(current.getCccd() != null ? current.getCccd() : "");
        txtSBD.setText(current.getSobaodanh() != null ? current.getSobaodanh() : "");
        txtPhuongThuc.setText(current.getdPhuongthuc() != null ? current.getdPhuongthuc() : "");

        txtTO.setText(safe(current.getTo()));
        txtLI.setText(safe(current.getLi()));
        txtHO.setText(safe(current.getHo()));
        txtSI.setText(safe(current.getSi()));
        txtSU.setText(safe(current.getSu()));
        txtDI.setText(safe(current.getDi()));
        txtVA.setText(safe(current.getVa()));
        txtN1THI.setText(safe(current.getN1Thi()));
        txtN1CC.setText(safe(current.getN1Cc()));
        txtCNCN.setText(safe(current.getCncn()));
        txtCNNN.setText(safe(current.getCnnn()));
        txtTI.setText(safe(current.getTi()));
        txtKTPL.setText(safe(current.getKtpl()));
        txtNL1.setText(safe(current.getNl1()));
        txtNK1.setText(safe(current.getNk1()));
        txtNK2.setText(safe(current.getNk2()));
        txtNK3.setText(safe(current.getNk3()));
        txtNK4.setText(safe(current.getNk4()));
        txtNK5.setText(safe(current.getNk5()));
        txtNK6.setText(safe(current.getNk6()));
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
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblCCCD = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        lblSBD = new javax.swing.JLabel();
        txtSBD = new javax.swing.JTextField();
        lblPT = new javax.swing.JLabel();
        txtPhuongThuc = new javax.swing.JTextField();
        lblTO = new javax.swing.JLabel();
        txtTO = new javax.swing.JTextField();
        lblLI = new javax.swing.JLabel();
        txtLI = new javax.swing.JTextField();
        lblHO = new javax.swing.JLabel();
        txtHO = new javax.swing.JTextField();
        lblSI = new javax.swing.JLabel();
        txtSI = new javax.swing.JTextField();
        lblSU = new javax.swing.JLabel();
        txtSU = new javax.swing.JTextField();
        lblDI = new javax.swing.JLabel();
        txtDI = new javax.swing.JTextField();
        lblVA = new javax.swing.JLabel();
        txtVA = new javax.swing.JTextField();
        lblN1THI = new javax.swing.JLabel();
        txtN1THI = new javax.swing.JTextField();
        lblN1CC = new javax.swing.JLabel();
        txtN1CC = new javax.swing.JTextField();
        lblCNCN = new javax.swing.JLabel();
        txtCNCN = new javax.swing.JTextField();
        lblCNNN = new javax.swing.JLabel();
        txtCNNN = new javax.swing.JTextField();
        lblTI = new javax.swing.JLabel();
        txtTI = new javax.swing.JTextField();
        lblKTPL = new javax.swing.JLabel();
        txtKTPL = new javax.swing.JTextField();
        lblNL1 = new javax.swing.JLabel();
        txtNL1 = new javax.swing.JTextField();
        lblNK1 = new javax.swing.JLabel();
        txtNK1 = new javax.swing.JTextField();
        lblNK2 = new javax.swing.JLabel();
        txtNK2 = new javax.swing.JTextField();
        lblNK3 = new javax.swing.JLabel();
        txtNK3 = new javax.swing.JTextField();
        lblNK4 = new javax.swing.JLabel();
        txtNK4 = new javax.swing.JTextField();
        lblNK5 = new javax.swing.JLabel();
        txtNK5 = new javax.swing.JTextField();
        lblNK6 = new javax.swing.JLabel();
        txtNK6 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cập nhật bảng điểm thi");

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setText("Sửa bảng điểm thi");

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
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sửa bảng điểm thi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblID.setText("ID :");

        txtID.setBackground(new java.awt.Color(246, 246, 246));
        txtID.setEditable(false);

        lblCCCD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCCCD.setText("CCCD (*) :");

        txtCCCD.setBackground(new java.awt.Color(246, 246, 246));

        lblSBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSBD.setText("Số báo danh :");

        txtSBD.setBackground(new java.awt.Color(246, 246, 246));

        lblPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPT.setText("Phương thức :");

        txtPhuongThuc.setBackground(new java.awt.Color(246, 246, 246));

        lblTO.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTO.setText("TO :");

        txtTO.setBackground(new java.awt.Color(246, 246, 246));

        lblLI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLI.setText("LI :");

        txtLI.setBackground(new java.awt.Color(246, 246, 246));

        lblHO.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHO.setText("HO :");

        txtHO.setBackground(new java.awt.Color(246, 246, 246));

        lblSI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSI.setText("SI :");

        txtSI.setBackground(new java.awt.Color(246, 246, 246));

        lblSU.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSU.setText("SU :");

        txtSU.setBackground(new java.awt.Color(246, 246, 246));

        lblDI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDI.setText("DI :");

        txtDI.setBackground(new java.awt.Color(246, 246, 246));

        lblVA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblVA.setText("VA :");

        txtVA.setBackground(new java.awt.Color(246, 246, 246));

        lblN1THI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblN1THI.setText("N1_THI :");

        txtN1THI.setBackground(new java.awt.Color(246, 246, 246));

        lblN1CC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblN1CC.setText("N1_CC :");

        txtN1CC.setBackground(new java.awt.Color(246, 246, 246));

        lblCNCN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCNCN.setText("CNCN :");

        txtCNCN.setBackground(new java.awt.Color(246, 246, 246));

        lblCNNN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCNNN.setText("CNNN :");

        txtCNNN.setBackground(new java.awt.Color(246, 246, 246));

        lblTI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTI.setText("TI :");

        txtTI.setBackground(new java.awt.Color(246, 246, 246));

        lblKTPL.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKTPL.setText("KTPL :");

        txtKTPL.setBackground(new java.awt.Color(246, 246, 246));

        lblNL1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNL1.setText("NL1 :");

        txtNL1.setBackground(new java.awt.Color(246, 246, 246));

        lblNK1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK1.setText("NK1 :");

        txtNK1.setBackground(new java.awt.Color(246, 246, 246));

        lblNK2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK2.setText("NK2 :");

        txtNK2.setBackground(new java.awt.Color(246, 246, 246));

        lblNK3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK3.setText("NK3 :");

        txtNK3.setBackground(new java.awt.Color(246, 246, 246));

        lblNK4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK4.setText("NK4 :");

        txtNK4.setBackground(new java.awt.Color(246, 246, 246));

        lblNK5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK5.setText("NK5 :");

        txtNK5.setBackground(new java.awt.Color(246, 246, 246));

        lblNK6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNK6.setText("NK6 :");

        txtNK6.setBackground(new java.awt.Color(246, 246, 246));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSBD, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPT, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTO, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLI, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHO, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSI, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSU, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDI, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVA, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN1THI, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN1CC, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNCN, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNNN, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTI, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKTPL, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNL1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNK6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSBD, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTO, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLI, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHO, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSI, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSU, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDI, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVA, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtN1THI, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtN1CC, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCNCN, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCNNN, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTI, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKTPL, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNL1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNK6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCCCD)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSBD)
                    .addComponent(txtSBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPT)
                    .addComponent(txtPhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTO)
                    .addComponent(txtTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLI)
                    .addComponent(txtLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHO)
                    .addComponent(txtHO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSI)
                    .addComponent(txtSI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSU)
                    .addComponent(txtSU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDI)
                    .addComponent(txtDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVA)
                    .addComponent(txtVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblN1THI)
                    .addComponent(txtN1THI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblN1CC)
                    .addComponent(txtN1CC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCNCN)
                    .addComponent(txtCNCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCNNN)
                    .addComponent(txtCNNN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTI)
                    .addComponent(txtTI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKTPL)
                    .addComponent(txtKTPL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNL1)
                    .addComponent(txtNL1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK1)
                    .addComponent(txtNK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK2)
                    .addComponent(txtNK2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK3)
                    .addComponent(txtNK3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK4)
                    .addComponent(txtNK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK5)
                    .addComponent(txtNK5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNK6)
                    .addComponent(txtNK6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý lưu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLuu.addActionListener(this::btnLuuActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
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

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        String cccd = txtCCCD.getText() != null ? txtCCCD.getText().trim() : "";
        if (cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống");
            return;
        }

        try {
            diemThiETT obj = new diemThiETT();
            obj.setIddiemthi(original != null ? original.getIddiemthi() : 0);
            obj.setCccd(cccd);
            obj.setSobaodanh(nullIfBlank(txtSBD.getText()));
            obj.setdPhuongthuc(nullIfBlank(txtPhuongThuc.getText()));

            obj.setTo(parseDoubleOrNull(txtTO.getText(), "TO"));
            obj.setLi(parseDoubleOrNull(txtLI.getText(), "LI"));
            obj.setHo(parseDoubleOrNull(txtHO.getText(), "HO"));
            obj.setSi(parseDoubleOrNull(txtSI.getText(), "SI"));
            obj.setSu(parseDoubleOrNull(txtSU.getText(), "SU"));
            obj.setDi(parseDoubleOrNull(txtDI.getText(), "DI"));
            obj.setVa(parseDoubleOrNull(txtVA.getText(), "VA"));

            obj.setN1Thi(parseDoubleOrNull(txtN1THI.getText(), "N1_THI"));
            obj.setN1Cc(parseDoubleOrNull(txtN1CC.getText(), "N1_CC"));

            obj.setCncn(parseDoubleOrNull(txtCNCN.getText(), "CNCN"));
            obj.setCnnn(parseDoubleOrNull(txtCNNN.getText(), "CNNN"));
            obj.setTi(parseDoubleOrNull(txtTI.getText(), "TI"));
            obj.setKtpl(parseDoubleOrNull(txtKTPL.getText(), "KTPL"));

            obj.setNl1(parseDoubleOrNull(txtNL1.getText(), "NL1"));

            obj.setNk1(parseDoubleOrNull(txtNK1.getText(), "NK1"));
            obj.setNk2(parseDoubleOrNull(txtNK2.getText(), "NK2"));
            obj.setNk3(parseDoubleOrNull(txtNK3.getText(), "NK3"));
            obj.setNk4(parseDoubleOrNull(txtNK4.getText(), "NK4"));
            obj.setNk5(parseDoubleOrNull(txtNK5.getText(), "NK5"));
            obj.setNk6(parseDoubleOrNull(txtNK6.getText(), "NK6"));

            this.diemThi = obj;
            this.xacNhan = true;
            dispose();
        } catch (IllegalArgumentException ex) {
            // đã show message ở parseDoubleOrNull
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        xacNhan = false;
        diemThi = null;
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThoat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCCCD;
    private javax.swing.JLabel lblCNNN;
    private javax.swing.JLabel lblCNCN;
    private javax.swing.JLabel lblDI;
    private javax.swing.JLabel lblHO;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKTPL;
    private javax.swing.JLabel lblLI;
    private javax.swing.JLabel lblN1CC;
    private javax.swing.JLabel lblN1THI;
    private javax.swing.JLabel lblNK1;
    private javax.swing.JLabel lblNK2;
    private javax.swing.JLabel lblNK3;
    private javax.swing.JLabel lblNK4;
    private javax.swing.JLabel lblNK5;
    private javax.swing.JLabel lblNK6;
    private javax.swing.JLabel lblNL1;
    private javax.swing.JLabel lblPT;
    private javax.swing.JLabel lblSBD;
    private javax.swing.JLabel lblSI;
    private javax.swing.JLabel lblSU;
    private javax.swing.JLabel lblTI;
    private javax.swing.JLabel lblTO;
    private javax.swing.JLabel lblVA;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtCNNN;
    private javax.swing.JTextField txtCNCN;
    private javax.swing.JTextField txtDI;
    private javax.swing.JTextField txtHO;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtKTPL;
    private javax.swing.JTextField txtLI;
    private javax.swing.JTextField txtN1CC;
    private javax.swing.JTextField txtN1THI;
    private javax.swing.JTextField txtNK1;
    private javax.swing.JTextField txtNK2;
    private javax.swing.JTextField txtNK3;
    private javax.swing.JTextField txtNK4;
    private javax.swing.JTextField txtNK5;
    private javax.swing.JTextField txtNK6;
    private javax.swing.JTextField txtNL1;
    private javax.swing.JTextField txtPhuongThuc;
    private javax.swing.JTextField txtSBD;
    private javax.swing.JTextField txtSI;
    private javax.swing.JTextField txtSU;
    private javax.swing.JTextField txtTI;
    private javax.swing.JTextField txtTO;
    private javax.swing.JTextField txtVA;
    // End of variables declaration//GEN-END:variables
}
