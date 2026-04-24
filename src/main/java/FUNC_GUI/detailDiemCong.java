package FUNC_GUI;

import Entity.diemCongETT;
import javax.swing.JFrame;

public class detailDiemCong extends javax.swing.JDialog {

    public detailDiemCong(java.awt.Frame parent, boolean modal, diemCongETT dc) {
        super(parent, modal);
        initComponents();
        if (dc != null) {
            hienThiData(dc);
        }
        setLocationRelativeTo(parent);
    }

    private void hienThiData(diemCongETT dc) {
        lblID.setText("ID: " + dc.getIdDiemCong());
        lblCCCD.setText("CCCD: " + dc.getTsCccd());
        lblMaNganh.setText("Mã ngành: " + dc.getMaNganh());
        lblMaToHop.setText("Mã tổ hợp: " + dc.getMaToHop());
        lblPhuongThuc.setText("Phương thức: " + dc.getPhuongThuc());
        lblDiemCong.setText("Điểm cộng: " + dc.getDiemCC());
        lblDiemUuTien.setText("Điểm ưu tiên: " + dc.getDiemUtxt());
        lblDiemTong.setText("Điểm tổng: " + dc.getDiemTong());
        txtGhiChu.setText(dc.getGhiChu());
    }

    private void initComponents() {
        jPanelHeader = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelContent = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblCCCD = new javax.swing.JLabel();
        lblMaNganh = new javax.swing.JLabel();
        lblMaToHop = new javax.swing.JLabel();
        lblPhuongThuc = new javax.swing.JLabel();
        lblDiemCong = new javax.swing.JLabel();
        lblDiemUuTien = new javax.swing.JLabel();
        lblDiemTong = new javax.swing.JLabel();
        jLabelGhiChu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi tiết điểm cộng");

        jPanelHeader.setBackground(new java.awt.Color(51, 153, 255));
        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Chi Tiết Điểm Cộng");

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelTitle)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanelContent.setBackground(new java.awt.Color(255, 255, 255));

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblID.setText("ID: ");

        lblCCCD.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblCCCD.setText("CCCD: ");

        lblMaNganh.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblMaNganh.setText("Mã ngành: ");

        lblMaToHop.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblMaToHop.setText("Mã tổ hợp: ");

        lblPhuongThuc.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblPhuongThuc.setText("Phương thức: ");

        lblDiemCong.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblDiemCong.setText("Điểm cộng: ");

        lblDiemUuTien.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblDiemUuTien.setText("Điểm ưu tiên: ");

        lblDiemTong.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblDiemTong.setForeground(new java.awt.Color(255, 51, 51));
        lblDiemTong.setText("Điểm tổng: ");

        jLabelGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 16));
        jLabelGhiChu.setText("Ghi chú:");

        txtGhiChu.setEditable(false);
        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Đóng");
        btnThoat.addActionListener(evt -> dispose());

        javax.swing.GroupLayout jPanelContentLayout = new javax.swing.GroupLayout(jPanelContent);
        jPanelContent.setLayout(jPanelContentLayout);
        jPanelContentLayout.setHorizontalGroup(
            jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContentLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelGhiChu)
                    .addComponent(lblDiemTong)
                    .addComponent(lblDiemUuTien)
                    .addComponent(lblDiemCong)
                    .addComponent(lblPhuongThuc)
                    .addComponent(lblMaToHop)
                    .addComponent(lblMaNganh)
                    .addComponent(lblCCCD)
                    .addComponent(lblID)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanelContentLayout.setVerticalGroup(
            jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCCCD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMaNganh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMaToHop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPhuongThuc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDiemCong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDiemUuTien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDiemTong)
                .addGap(18, 18, 18)
                .addComponent(jLabelGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabelGhiChu;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCCCD;
    private javax.swing.JLabel lblDiemCong;
    private javax.swing.JLabel lblDiemTong;
    private javax.swing.JLabel lblDiemUuTien;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblMaNganh;
    private javax.swing.JLabel lblMaToHop;
    private javax.swing.JLabel lblPhuongThuc;
    private javax.swing.JTextArea txtGhiChu;
}
