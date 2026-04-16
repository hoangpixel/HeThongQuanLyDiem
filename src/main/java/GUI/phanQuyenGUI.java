/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import FUNC_GUI.insertPhanQuyen;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Entity.phanQuyenETT;
import BUS.phanQuyenBUS;
import EXCEL.ExcelHelper;
import FUNC_GUI.deletePhanQuyen;
import FUNC_GUI.detailPhanQuyen;
import FUNC_GUI.excelPhanQuyen;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author mhoang
 */
public class phanQuyenGUI extends BaseTableGUI{
    phanQuyenBUS busPhanQuyen = new phanQuyenBUS();
    public phanQuyenGUI()
    {
        super();
        setTableNameForTitle("Phân quyền");
        headerTable();
        
        // =======================================================
        // 🔥 XÓA NÚT SỬA VÀ DỒN GIAO DIỆN LẠI THÀNH 5 CỘT 🔥
        // =======================================================
        btnSua.setVisible(false); // Ẩn nút đi
        javax.swing.JPanel pnlParent = (javax.swing.JPanel) btnSua.getParent(); // "Mượn" btnSua để móc ra pnlActions
        pnlParent.remove(btnSua); // Nhổ hẳn cái nút này ra khỏi Panel
        
        // Cài lại GridLayout thành 1 hàng, 5 cột (thay vì 6 như BaseTableGUI gốc)
        pnlParent.setLayout(new java.awt.GridLayout(1, 5, 13, 10)); 
        
        // Ép giao diện vẽ lại ngay lập tức
        pnlParent.revalidate();
        pnlParent.repaint();
        // =======================================================
        
        btnThem.addActionListener(e -> thucHienThem());
        btnXoa.addActionListener(e -> thucHienXoa());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> thucHienChiTiet());
        
        loadComboBox();
        loadDataToTable();
    }
private void thucHienThem() {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        insertPhanQuyen dialog = new insertPhanQuyen(topFrame, true);
        dialog.setVisible(true);
        
        if (dialog.getXacNhan()) {
            ArrayList<phanQuyenETT> dsMoi = dialog.getDanhSachThem();
            
            if (dsMoi != null && !dsMoi.isEmpty()) {
                int idTkEditing = dsMoi.get(0).getIdTaiKhoan();

                // 🔥 QUÉT ĐỂ GHI ĐÈ / THÊM MỚI LÊN RAM 🔥
                for (phanQuyenETT pqMoi : dsMoi) {
                    boolean daCoTrongBang = false;
                    
                    for (int i = 0; i < fullDataList.size(); i++) {
                        Vector row = (Vector) fullDataList.get(i);
                        
                        int rId = (int) row.get(0);
                        String rTenBang = (String) row.get(1);
                        
                        // Tìm thấy dòng của Bảng này rồi -> GHI ĐÈ quyền mới vào!
                        if (rId == idTkEditing && rTenBang.equals(pqMoi.getTenBang())) {
                            row.set(2, pqMoi.getQuyenXem());
                            row.set(3, pqMoi.getQuyenThem());
                            row.set(4, pqMoi.getQuyenSua());
                            row.set(5, pqMoi.getQuyenXoa());
                            
                            daCoTrongBang = true;
                            break; 
                        }
                    }
                    
                    // Nếu quét hết mà chưa có -> Add thêm dòng mới xuống đáy
                    if (!daCoTrongBang) {
                        Vector newRow = new Vector();
                        newRow.add(pqMoi.getIdTaiKhoan());
                        newRow.add(pqMoi.getTenBang());
                        newRow.add(pqMoi.getQuyenXem());
                        newRow.add(pqMoi.getQuyenThem());
                        newRow.add(pqMoi.getQuyenSua());
                        newRow.add(pqMoi.getQuyenXoa());
                        
                        fullDataList.add(newRow);
                    }
                }

                // ==========================================================
                // 🚀 BƯỚC MỚI: SẮP XẾP LẠI RAM TRƯỚC KHI RENDER
                // ==========================================================
                java.util.List<String> thuTuBang = java.util.Arrays.asList(
                    "xt_nguyenvongxettuyen", "xt_bangquydoi", "xt_chungchi", "xt_giathuong",
                    "xt_diemthixettuyen", "xt_diemcongxetuyen", "xt_nganh", "xt_nganh_tohop",
                    "xt_tohop_monthi", "xt_thisinhxettuyen25", "xt_taikhoan", "xt_phanquyen"
                );

                fullDataList.sort((row1, row2) -> {
                    int id1 = (int) row1.get(0);
                    int id2 = (int) row2.get(0);
                    
                    // Ưu tiên 1: Xếp theo ID Tài khoản tăng dần
                    if (id1 != id2) {
                        return Integer.compare(id1, id2);
                    }
                    
                    // Ưu tiên 2: Cùng ID thì xếp theo thứ tự bảng
                    String tb1 = (String) row1.get(1);
                    String tb2 = (String) row2.get(1);
                    
                    int index1 = thuTuBang.indexOf(tb1);
                    int index2 = thuTuBang.indexOf(tb2);
                    
                    if (index1 == -1) index1 = 999;
                    if (index2 == -1) index2 = 999;
                    
                    return Integer.compare(index1, index2);
                });

                // ==========================================================

                // Cập nhật lại phân trang và giao diện
                totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                
                // Render lại trang hiện tại để thấy ngay kết quả
                renderCurrentPage();
            }
        }
    }
    private void thucHienXoa() 
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Hỏi lại cho chắc ăn (Hạn chế xóa nhầm)
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            deletePhanQuyen dialog = new deletePhanQuyen(topFrame, true);
            dialog.setVisible(true);
            
            if(dialog.getXacNhanXoa()) {
                // 2. Tính Index thực sự y như hàm Sửa
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                // 🔥 SỬA TẠI ĐÂY: KHÔNG DÙNG busPhanQuyen.ds.get() NỮA 🔥
                // Lấy thông tin trực tiếp từ dòng dữ liệu trên RAM giao diện (An toàn 100%)
                Vector rowData = (Vector) fullDataList.get(absoluteIndex);
                int idTk = (int) rowData.get(0);
                String tenBang = (String) rowData.get(1);
                
                // Tạo một đối tượng tạm chỉ chứa ID và Tên Bảng để gửi cho BUS
                phanQuyenETT nvCanXoa = new phanQuyenETT();
                nvCanXoa.setIdTaiKhoan(idTk);
                nvCanXoa.setTenBang(tenBang);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (busPhanQuyen.xoaPhanQuyen(nvCanXoa)) {
                    
                    // 🔥 Xóa luôn phần tử đó trong fullDataList để đồng bộ
                    fullDataList.remove(absoluteIndex);
                    
                    // 🔥 Tính lại tổng số trang
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    
                    // 🔥 Nếu trang hiện tại bị rỗng, lùi lại 1 trang
                    if (currentPage > totalPages && totalPages > 0) {
                        currentPage = totalPages;
                    }
                    
                    // 🔥 Vẽ lại bảng cực mượt
                    renderCurrentPage();
                    btnXoa.setEnabled(false); // Xóa xong thì khóa nút lại cho an toàn
                    // Tùy chỉnh thông báo nếu thích
                    // JOptionPane.showMessageDialog(this, "Xóa phân quyền thành công"); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa phân quyền thất bại");
                }
            }
        }
    }

    public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelPhanQuyen dialog = new excelPhanQuyen(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Gọi BUS xử lý và nhận thông báo kết quả
            String thongBao = busPhanQuyen.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            busPhanQuyen.ds = null;
            loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<phanQuyenETT> fullDanhSach = busPhanQuyen.layDanhSach();
            ExcelHelper.xuatDanhSachPhanQuyenRaExcel(fullDanhSach, this, "DanhSachPhanQuyen");
        }
    }
    
    public void thucHienRefresh()
    {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);
        
        busPhanQuyen.layDanhSach();

        fullDataList.clear();
        List<Vector> dataList = new ArrayList<>();

        for (phanQuyenETT ct : busPhanQuyen.ds) {
            Vector row = new Vector();
            row.add(ct.getIdTaiKhoan());
            row.add(ct.getTenBang());
            row.add(ct.getQuyenXem());
            row.add(ct.getQuyenThem());
            row.add(ct.getQuyenSua());
            row.add(ct.getQuyenXoa());

            dataList.add(row);
        }
        setTableData(dataList);
    }
    
    public void thucHienTimKiem()
    {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        if(tim.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }
        
        ArrayList<phanQuyenETT> dskq = busPhanQuyen.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();
        
        
        for (phanQuyenETT ct : dskq) 
        {
            Vector row = new Vector();
            row.add(ct.getIdTaiKhoan());
            row.add(ct.getTenBang());
            row.add(ct.getQuyenXem());
            row.add(ct.getQuyenThem());
            row.add(ct.getQuyenSua());
            row.add(ct.getQuyenXoa());

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);
        if(dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
    }
    
private void thucHienChiTiet() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            
            // 👉 1. KIỂM TRA NULL TRƯỚC KHI LẤY DỮ LIỆU
            if (busPhanQuyen.ds == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Danh sách quyền chưa được nạp từ Database!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                // GỌI HÀM NẠP DỮ LIỆU Ở ĐÂY (Ví dụ: busPhanQuyen.docDanhSach();)
                return; // Dừng hàm lại, không chạy cái get() ở dưới nữa
            }
            
            // 👉 2. CHECK LUÔN LỠ CÁI VỊ TRÍ CLICK NÓ VƯỢT QUÁ SỐ LƯỢNG MẢNG
            if (absoluteIndex < 0 || absoluteIndex >= busPhanQuyen.ds.size()) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy dữ liệu ở dòng này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nếu vượt qua 2 ải trên thì lấy ra an toàn tuyệt đối
            phanQuyenETT pq = busPhanQuyen.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            detailPhanQuyen dialog = new detailPhanQuyen(topFrame, true, pq);
            dialog.setVisible(true);
        }
    }
    
    public void loadComboBox() 
    {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID tài khoản");
        cbxTimKiem.addItem("Tên bảng");
        cbxTimKiem.addItem("Xem");
        cbxTimKiem.addItem("Thêm");
        cbxTimKiem.addItem("Sửa");
        cbxTimKiem.addItem("Xóa");
    }
    
    public void headerTable() 
    {
        Vector<String> header = new Vector<>();
        header.add("ID TK");
        header.add("Bảng");
        header.add("Xem");
        header.add("Thêm");
        header.add("Sửa");
        header.add("Xóa");
        tableModel.setColumnIdentifiers(header);
        
        javax.swing.SwingUtilities.invokeLater(() -> 
        {
            QuyenCellRenderer quyenRenderer = new QuyenCellRenderer();
                for (int i = 0; i < table.getColumnCount(); i++) 
                {
                    table.getColumnModel().getColumn(i).setCellRenderer(quyenRenderer);
                }
        });
    }
    public void loadDataToTable() {

        if (busPhanQuyen.ds == null) {
            busPhanQuyen.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (phanQuyenETT ct : busPhanQuyen.ds) {
            Vector row = new Vector();
            row.add(ct.getIdTaiKhoan());
            row.add(ct.getTenBang());
            row.add(ct.getQuyenXem());
            row.add(ct.getQuyenThem());
            row.add(ct.getQuyenSua());
            row.add(ct.getQuyenXoa());

            dataList.add(row);
        }
        setTableData(dataList);
    }
    
    private class QuyenCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Ép chữ ra giữa cho ĐẸP TOÀN DIỆN
            setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            
            // 🔥 1. KEM NỀN (Zebra Stripe) - Áp dụng cho TẤT CẢ các cột 🔥
            if (isSelected) {
                c.setBackground(new java.awt.Color(52, 152, 219)); 
                c.setForeground(java.awt.Color.WHITE);
            } else {
                if (row % 2 == 0) {
                    c.setBackground(java.awt.Color.WHITE);
                } else {
                    c.setBackground(new java.awt.Color(245, 245, 245)); // Xám nhạt
                }
                c.setForeground(new java.awt.Color(50, 50, 50)); // Chữ màu tối mặc định cho ID và Bảng
            }

            if (column >= 2 && column <= 5) {
                
                // 👉 THÊM DÒNG NÀY: Ép font hiện tại thành in đậm (BOLD)
                c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD)); 

                if (value != null && value instanceof Integer) {
                    int val = (int) value;
                    if (val == 1) {
                        setText("Có");
                        if (!isSelected) c.setForeground(new java.awt.Color(46, 204, 113)); 
                    } else {
                        setText("Không");
                        if (!isSelected) c.setForeground(new java.awt.Color(231, 76, 60)); 
                    }
                }
            } else {
                c.setFont(c.getFont().deriveFont(java.awt.Font.PLAIN)); 
                setText(value != null ? value.toString() : "");
            }
            
            return c;
        }
    }
}
