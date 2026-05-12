/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package SELECT_GUI;
import Entity.nganhETT;
import BUS.nganhBUS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
/**
 *
 * @author mhoang
 */
public class selectNganh extends javax.swing.JDialog {
    public nganhETT nganh = new nganhETT();
    public boolean xacNhan = false;
    nganhBUS bus = new nganhBUS();
    DefaultTableModel model = new DefaultTableModel();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(selectNganh.class.getName());
    private nganhBUS busNganh = new nganhBUS();
        int currentPage = 1;
    int rowsPerPage = 20;
    int totalPages = 1;
    /**
     * Creates new form selectThiSinh
     */
    public selectNganh(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        header();
        docSQL();
        loadComboBox();
    }
    
        public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID Ngành");
        cbxTimKiem.addItem("Mã Ngành");
        cbxTimKiem.addItem("Tên Ngành");
        cbxTimKiem.addItem("Tổ Hợp Gốc");
        cbxTimKiem.addItem("Chỉ Tiêu");
        cbxTimKiem.addItem("Sàn THPT");
        cbxTimKiem.addItem("Sàn V-SAT");
        cbxTimKiem.addItem("Sàn ĐGNL");
        cbxTimKiem.addItem("Chuẩn THPT");
        cbxTimKiem.addItem("Chuẩn V-SAT");
        cbxTimKiem.addItem("Chuẩn ĐGNL");
        cbxTimKiem.addItem("SL ĐK");
    }
    
    public void docSQL()
    {
        if(bus.ds == null)
        {
            bus.layDanhSach();
        }

        int totalRows = bus.ds.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);

        currentPage = 1;
        loadPage(currentPage);
    }
    
        public void loadPage(int page)
{
    model.setRowCount(0);

    int start = (page - 1) * rowsPerPage;
    int end = Math.min(start + rowsPerPage, bus.ds.size());

    for(int i = start; i < end; i++)
    {
        nganhETT ct = bus.ds.get(i);
            Vector row = new Vector();
            row.add(ct.getIdnganh());
            row.add(ct.getManganh());
            row.add(ct.getTennganh());
            row.add(ct.getN_tohopgoc());
            row.add(ct.getN_chitieu());
            row.add(ct.getN_diemsanthpt() != null ? ct.getN_diemsanthpt() : "");
            row.add(ct.getN_diemsanvsat() != null ? ct.getN_diemsanvsat() : "");
            row.add(ct.getN_diemsandgnl() != null ? ct.getN_diemsandgnl() : "");
            row.add(ct.getDiemchuan_thpt() != null ? ct.getDiemchuan_thpt() : 0);
            row.add(ct.getDiemchuan_vsat() != null ? ct.getDiemchuan_vsat() : 0);
            row.add(ct.getDiemchuan_dgnl() != null ? ct.getDiemchuan_dgnl() : 0);
            
            // Check an toàn, lỡ Ngành đó chưa có ai đăng ký (trong DB là NULL) thì in ra số 0
            row.add(ct.getSlDangKy() != null ? ct.getSlDangKy() : 0);
            model.addRow(row);
    }

    updatePaginationUI();
}
    public void updatePaginationUI()
{
    lbPageInFo.setText(currentPage + "/" + totalPages);

    btnFirst.setEnabled(currentPage > 1);
    btnPrev.setEnabled(currentPage > 1);

    btnNext.setEnabled(currentPage < totalPages);
    btnLast.setEnabled(currentPage < totalPages);
}
    
    void header()
    {
        Vector headerVec = new Vector();
        headerVec.add("ID Ngành");
        headerVec.add("Mã Ngành");
        headerVec.add("Tên Ngành");
        headerVec.add("Tổ Hợp Gốc");
        headerVec.add("Chỉ Tiêu");
        headerVec.add("Sàn THPT");
        headerVec.add("Sàn V-SAT");
        headerVec.add("Sàn ĐGNL");
        headerVec.add("Chuẩn THPT");
        headerVec.add("Chuẩn V-SAT");
        headerVec.add("Chuẩn ĐGNL");
        headerVec.add("SL ĐK"); // <-- Cột số 9
        model = new DefaultTableModel(headerVec, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        tbNganh.setModel(model);

        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== HEADER =====
        JTableHeader header = tbNganh.getTableHeader(); // ✅ FIX Ở ĐÂY
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(100, 35));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer headerRenderer = 
            (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        // ===== TABLE =====
        tbNganh.setFont(mainFont);
        tbNganh.setRowHeight(32);
        tbNganh.setSelectionBackground(new Color(52, 152, 219));
        tbNganh.setSelectionForeground(Color.WHITE);

        tbNganh.setShowGrid(false);
        tbNganh.setIntercellSpacing(new Dimension(0, 0));

        // ===== RENDER =====
        tbNganh.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                setHorizontalAlignment(JLabel.CENTER);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbxTimKiem = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNganh = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lbPageInFo = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Chọn ngành");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        cbxTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(this::btnTimKiemActionPerformed);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(btnRefresh))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        tbNganh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbNganh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNganhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbNganh);

        jButton1.setBackground(new java.awt.Color(255, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Thoát");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        btnFirst.setText("<<");
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.addActionListener(this::btnFirstActionPerformed);

        btnPrev.setText("<");
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.addActionListener(this::btnPrevActionPerformed);

        lbPageInFo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbPageInFo.setText("1/1");

        btnNext.setText(">");
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addActionListener(this::btnNextActionPerformed);

        btnLast.setText(">>");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.addActionListener(this::btnLastActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(btnFirst)
                        .addGap(31, 31, 31)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbPageInFo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(lbPageInFo)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        xacNhan = false;
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbNganhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNganhMouseClicked
// TODO add your handling code here:
        if(evt.getClickCount() == 2)
        {
            if(tbNganh.isEditing())
            {
                tbNganh.getCellEditor().stopCellEditing();
            }

            int i = tbNganh.getSelectedRow();
            if(i != -1)
            {
                // Lấy vị trí của dòng trong TableModel (chỉ từ 0 đến 19 do phân trang)
                int modelIndex = tbNganh.convertRowIndexToModel(i);
                
                // TÍNH TOÁN LẠI INDEX THỰC TẾ TRONG DANH SÁCH bus.ds
                int realIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                // Lấy đúng phần tử dựa trên realIndex
                nganh = bus.ds.get(realIndex);
                xacNhan = true;
                dispose();
            }
        }
    }//GEN-LAST:event_tbNganhMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();

        if (tim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();

            // Tùy chọn: Nếu để trống ô tìm kiếm và bấm nút, load lại toàn bộ danh sách
            // bus.ds = null;
            // docSQL();
            return;
        }

        // Lấy kết quả tìm kiếm
        ArrayList<nganhETT> dskq = busNganh.timKiemCoBan(tim, index);

        if (dskq == null || dskq.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
            return;
        }

        bus.ds = dskq;

        int totalRows = bus.ds.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;

        currentPage = 1;
        loadPage(currentPage);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText("");
        bus.ds = null;
        docSQL();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        if(currentPage > 1)
        {
            currentPage--;
            loadPage(currentPage);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if(currentPage < totalPages)
        {
            currentPage++;
            loadPage(currentPage);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        currentPage = totalPages;
        loadPage(currentPage);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
    currentPage = 1;
    loadPage(currentPage);
    }//GEN-LAST:event_btnFirstActionPerformed

    public nganhETT getNganh()
    {
        return nganh;
    }
    public boolean getXacNhan()
    {
        return xacNhan;
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbxTimKiem;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbPageInFo;
    private javax.swing.JTable tbNganh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
