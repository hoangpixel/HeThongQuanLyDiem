package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import BUS.*;
import Entity.*;
import FUNC_GUI.formDeleteNguyenMauProMax;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import FUNC_GUI.insertNguyenVong;
import FUNC_GUI.deleteNguyenVong;
import FUNC_GUI.updateNguyenVong;
import javax.swing.JOptionPane;

public class nguyenVongXetTuyenGUI extends BaseTableGUI {

    nguyenVongXetTuyenBUS busNguyenVong = new nguyenVongXetTuyenBUS();
    public nguyenVongXetTuyenGUI() {
        super(); // Gọi giao diện cơ bản từ BaseTableGUI lên
        // 1. Đổi tên GroupBox và Cột
        setTableNameForTitle("Nguyện Vọng"); 
        headerTable();
        
        // 2. Gắn sự kiện nút bấm
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e ->hienThiDialogXoa());
        
        // ==============================================================
        // BỔ SUNG: GẮN SỰ KIỆN DOUBLE-CLICK CHO TABLE
        // ==============================================================
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Kiểm tra xem người dùng có click đúp (2 lần) không
                if (evt.getClickCount() == 2) {
                    // Lấy ra vị trí dòng (row) mà người dùng vừa click vào
                    int selectedRow = table.getSelectedRow();
                    
                    // Nếu có dòng được chọn (khác -1)
                    if (selectedRow != -1) {
                        // Gọi hàm hiển thị form và truyền vị trí dòng vào
//                        hienThiDialogChiTiet(selectedRow);
                    }
                }
            }
        });
        // ==============================================================
        
        // 3. GỌI HÀM TẢI DỮ LIỆU LÊN BẢNG KHI VỪA MỞ FORM LÊN
        loadDataToTable();
        table.getColumnModel().getColumn(9).setMinWidth(0);
        table.getColumnModel().getColumn(9).setMaxWidth(0);
        table.getColumnModel().getColumn(9).setWidth(0);
    }
    
    // Hàm tạo header và truyền dữ liệu vào table
    public void headerTable()
    {
        Vector<String> header = new Vector<>();
        header.add("ID NV");
        header.add("CCCD");
        header.add("ID ngành");
        header.add("Thứ tự");
        header.add("Điểm THXT");
        header.add("Điểm UTQD");
        header.add("Điềm cộng");
        header.add("Điểm tổng");
        header.add("Kết quả NV");
        header.add("Keys");
        header.add("Phương thức");
        header.add("Tổ hợp");
        tableModel.setColumnIdentifiers(header);
    }
    
    public void loadDataToTable() {

        if (busNguyenVong.ds == null) {
            busNguyenVong.layDanhSach();
        }

        List<Vector> dataList = new ArrayList<>();

        for (nguyenVongXetTuyenETT ct : busNguyenVong.ds) {
            Vector row = new Vector();
            row.add(ct.getIdNv());
            row.add(ct.getNnCccd());
            row.add(ct.getNvMaNganh());
            row.add(ct.getNvTt());
            row.add(ct.getDiemThxt());
            row.add(ct.getDiemUtqd());
            row.add(ct.getDiemCong());
            row.add(ct.getDiemXetTuyen());
            row.add(ct.getNvKetQua());
            row.add(ct.getNvKeys());
            row.add(ct.getTtPhuongThuc());
            row.add(ct.getTtThm());

            dataList.add(row); // ✅ CHỈ add vào list
        }

        // 🔥 CHỈ GỌI DÒNG NÀY
        setTableData(dataList);
    }

    // Hàm mở JDialog thêm mới (Giữ nguyên)
// ==============================================================
    // HÀM MỞ FORM JDIALOG ĐỂ THÊM NGUYỆN VỌNG MỚI
    // ==============================================================
private void hienThiDialogThemMoi() {
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    insertNguyenVong dialog = new insertNguyenVong(topFrame, true);
    dialog.setVisible(true);

    if (dialog.xacNhanThem()) {
        nguyenVongXetTuyenETT nv = dialog.getNguyenVong();

        // 🔥 Convert object → Vector
        Vector row = new Vector();
        row.add(nv.getIdNv());
        row.add(nv.getNnCccd());
        row.add(nv.getNvMaNganh());
        row.add(nv.getNvTt());
        row.add(nv.getDiemThxt());
        row.add(nv.getDiemUtqd());
        row.add(nv.getDiemCong());
        row.add(nv.getDiemXetTuyen());
        row.add(nv.getNvKetQua());
        row.add(nv.getNvKeys());
        row.add(nv.getTtPhuongThuc());
        row.add(nv.getTtThm());

        // 🔥 ADD vào fullDataList (QUAN TRỌNG)
        fullDataList.add(row);

        // 🔥 Cập nhật lại totalPages
        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

        // 🔥 Nếu muốn nhảy tới trang cuối
        currentPage = totalPages;

        // 🔥 Render lại
        renderCurrentPage();
    }
}

private void hienThiDialogSua() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Tính toán vị trí chính xác của đối tượng trong danh sách tổng
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex; // 🔥 Bí quyết chống lỗi phân trang
            
            // 2. Lấy đối tượng cũ ra và ném vào Form Sửa
            Entity.nguyenVongXetTuyenETT nguyenVongCu = busNguyenVong.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            // Giả sử form của bạn tên là updateNguyenVong
            updateNguyenVong dialog = new updateNguyenVong(topFrame, true, nguyenVongCu);
            dialog.setVisible(true); // Form Sửa hiện lên
            
            // 3. Sau khi người dùng Sửa và bấm LƯU thành công
            if (dialog.xacNhanThem()) { 
                Entity.nguyenVongXetTuyenETT nvMoi = dialog.getNguyenVong();
                
                // 🔥 Ép kiểu đối tượng mới thành Vector y như hàm Thêm
                java.util.Vector rowData = new java.util.Vector();
                rowData.add(nvMoi.getIdNv());
                rowData.add(nvMoi.getNnCccd());
                rowData.add(nvMoi.getNvMaNganh());
                rowData.add(nvMoi.getNvTt());
                rowData.add(nvMoi.getDiemThxt());
                rowData.add(nvMoi.getDiemUtqd());
                rowData.add(nvMoi.getDiemCong());
                rowData.add(nvMoi.getDiemXetTuyen());
                rowData.add(nvMoi.getNvKetQua());
                rowData.add(nvMoi.getNvKeys());
                rowData.add(nvMoi.getTtPhuongThuc());
                rowData.add(nvMoi.getTtThm());

                // 🔥 Đè cái Vector mới này vào đúng vị trí cũ trong fullDataList
                fullDataList.set(absoluteIndex, rowData);

                // 🔥 Render lại đúng trang hiện tại (Tốc độ bàn thờ, không lag, không mất trang)
                renderCurrentPage(); 
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyện vọng trên bảng để sửa!");
        }
    }

    private void hienThiDialogXoa()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            // 1. Hỏi lại cho chắc ăn (Hạn chế xóa nhầm)
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                deleteNguyenVong dialog = new deleteNguyenVong(topFrame, true);
                dialog.setVisible(true);
                
                if(dialog.getXacNhanXoa())
                {
                // 2. Tính Index thực sự y như hàm Sửa
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                Entity.nguyenVongXetTuyenETT nvCanXoa = busNguyenVong.ds.get(absoluteIndex);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (busNguyenVong.xoaNguyenVong(nvCanXoa)) {
                    
                    // 🔥 Xóa luôn phần tử đó trong fullDataList để đồng bộ
                    fullDataList.remove(absoluteIndex);
                    
                    // 🔥 Tính lại tổng số trang (Lỡ xóa rớt mất 1 trang thì sao)
                    totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
                    
                    // 🔥 Nếu trang hiện tại bị rỗng (do vừa xóa thằng cuối cùng của trang), lùi lại 1 trang
                    if (currentPage > totalPages && totalPages > 0) {
                        currentPage = totalPages;
                    }
                    
                    // 🔥 Vẽ lại bảng cực mượt
                    renderCurrentPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nguyện vọng thất bại");
                }
            }
        }
    }
}