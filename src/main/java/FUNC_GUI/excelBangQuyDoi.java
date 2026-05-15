/*
 * Excel import/export form for conversion table (Bảng quy đổi)
 */
package FUNC_GUI;

/**
 *
 * @author Admin
 */
public class excelBangQuyDoi extends javax.swing.JDialog {
    
    public boolean xacNhanImport = false, xacNhanExport = false;
    
    public excelBangQuyDoi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelExcel = new javax.swing.JLabel();
        groupBoxExcel = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Excel bảng quy đổi");

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        labelExcel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        labelExcel.setText("Excel Bảng Quy Đổi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(labelExcel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(labelExcel)
                .addGap(21, 21, 21))
        );

        groupBoxExcel.setBackground(new java.awt.Color(255, 255, 255));
        groupBoxExcel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thực hiện Import/Export excel Bảng Quy Đổi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14)));

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(this::btnThoatActionPerformed);

        btnExport.setBackground(new java.awt.Color(102, 102, 255));
        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnExport.setText("Export");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport.addActionListener(this::btnExportActionPerformed);

        btnImport.setBackground(new java.awt.Color(255, 255, 204));
        btnImport.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnImport.setText("Import");
        btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImport.addActionListener(this::btnImportActionPerformed);

        javax.swing.GroupLayout groupBoxExcelLayout = new javax.swing.GroupLayout(groupBoxExcel);
        groupBoxExcel.setLayout(groupBoxExcelLayout);
        groupBoxExcelLayout.setHorizontalGroup(
            groupBoxExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupBoxExcelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        groupBoxExcelLayout.setVerticalGroup(
            groupBoxExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupBoxExcelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(groupBoxExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(groupBoxExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupBoxExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        xacNhanExport = true;
        dispose();
    }

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {
        xacNhanExport = false;
        xacNhanImport = false;
        dispose();
    }

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {
        xacNhanImport = true;
        dispose();
    }

    public boolean getXacNhanImport() {
        return xacNhanImport;
    }

    public boolean getXacNhanExport() {
        return xacNhanExport;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExport;
    public javax.swing.JButton btnImport;
    public javax.swing.JButton btnThoat;
    public javax.swing.JPanel groupBoxExcel;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JLabel labelExcel;
    // End of variables declaration//GEN-END:variables
}
