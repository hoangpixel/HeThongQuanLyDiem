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
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import FUNC_GUI.insertNguyenVong;
import FUNC_GUI.deleteNguyenVong;
import FUNC_GUI.updateNguyenVong;
import FUNC_GUI.excelNguyenVongSybau;
import java.util.Collections;
import javax.swing.JOptionPane;
import EXCEL.ExcelHelper;
import CAL.*;
import FUNC_GUI.detailNguyenVong;
import FUNC_GUI.excelNguyenVong;
import java.lang.reflect.Array;

public class nguyenVongXetTuyenGUI extends BaseTableForNguyenVongGUIonly {

    nguyenVongXetTuyenBUS busNguyenVong = new nguyenVongXetTuyenBUS();
public nguyenVongXetTuyenGUI() {
        super();
        setTableNameForTitle("Nguyện Vọng"); 
        headerTable();
        
        // =======================================================
        // 🔥 ẨN NÚT SỬA, XÓA VÀ DỒN GIAO DIỆN LẠI THÀNH 5 CỘT 🔥
        // =======================================================
        btnSua.setVisible(false); 
        btnXoa.setVisible(false); 
        
        javax.swing.JPanel pnlParent = (javax.swing.JPanel) btnSua.getParent(); 
        
        // Nhổ hẳn 2 cái nút này ra khỏi Panel
        pnlParent.remove(btnSua); 
        pnlParent.remove(btnXoa); 
        
        // Cài lại GridLayout thành 1 hàng, 5 cột (Ban đầu là 7 nút, bỏ 2 còn 5)
        pnlParent.setLayout(new java.awt.GridLayout(1, 5, 13, 10)); 
        
        // Ép giao diện vẽ lại ngay lập tức cho đều và đẹp
        pnlParent.revalidate();
        pnlParent.repaint();
        // =======================================================
        
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e ->hienThiDialogXoa());
        btnTinhToanKetQua.addActionListener(e -> thucHienTinhToanKetQua());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> hienThiChiTietNV());
        
        loadDataToTable();
        table.getColumnModel().getColumn(10).setMinWidth(0);
        table.getColumnModel().getColumn(10).setMaxWidth(0);
        table.getColumnModel().getColumn(10).setWidth(0);
        loadComboBox();
        phanQuyenGiaoDien();
    }
    
    private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_nguyenvongxettuyen";
        
        if (!phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));
        btnTinhToanKetQua.setEnabled(phanQuyenBUS.checkQuyenThem(bangHienTai));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        boolean isSelected = table.getSelectedRow() != -1;
                        
                        if (isSelected) {
                            btnSua.setEnabled(phanQuyenBUS.checkQuyenSua(bangHienTai));
                            btnXoa.setEnabled(phanQuyenBUS.checkQuyenXoa(bangHienTai));
                            btnChiTiet.setEnabled(phanQuyenBUS.checkQuyenXem(bangHienTai)); 
                        } else {
                            btnSua.setEnabled(false);
                            btnXoa.setEnabled(false);
                            btnChiTiet.setEnabled(false);
                        }
                    }
                });
                
            }
        });
    }
    
    public void loadComboBox() {
        cbxTimKiem.removeAllItems();

        cbxTimKiem.addItem("ID NV");
        cbxTimKiem.addItem("CCCD");
        cbxTimKiem.addItem("Mã ngành");
        cbxTimKiem.addItem("Thứ tự NV");
        cbxTimKiem.addItem("Điểm THXT");
        cbxTimKiem.addItem("Điểm UTQD");
        cbxTimKiem.addItem("Điểm cộng");
        cbxTimKiem.addItem("Điểm xét tuyển");
        cbxTimKiem.addItem("Kết quả");
        cbxTimKiem.addItem("Phương thức");
        cbxTimKiem.addItem("Tổ hợp");
        cbxTimKiem.addItem("THM Cao Nhất");
        cbxTimKiem.addItem("Điểm cao nhất");
    }
    
    public void thucHienTimKiem()
    {
        String tim = txtTimKiem.getText().trim();
        int index = cbxTimKiem.getSelectedIndex();
        
        if(tim.isEmpty() && index != 11 && index != 12)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung cần tìm");
            txtTimKiem.requestFocus();
            return;
        }
        
        ArrayList<nguyenVongXetTuyenETT> dskq = busNguyenVong.timKiemCoBan(tim, index);
        List<Vector> dsHienThi = new ArrayList<>();
        
        
        for (nguyenVongXetTuyenETT ct : dskq) 
        {
            Vector row = new Vector();
            row.add(ct.getIdNv());
            row.add(ct.getNnCccd());
            row.add(ct.getNvMaNganh());
            row.add(ct.getNvTt());
            row.add(ct.getDiemThxt());
            row.add(ct.getDiemUtqdGoc());
            row.add(ct.getDiemUtqd());
            row.add(ct.getDiemCong());
            row.add(ct.getDiemXetTuyen());
            row.add(ct.getNvKetQua());
            row.add(ct.getNvKeys());
            row.add(ct.getTtPhuongThuc());
            row.add(ct.getTtThm());

            dsHienThi.add(row);
        }

        setTableData(dsHienThi);
        if(dsHienThi.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp");
        }
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
        header.add("Điểm ƯT Gốc");
        header.add("Điểm ƯTQD");
        header.add("Điểm cộng");
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
            row.add(ct.getDiemUtqdGoc());
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

private void hienThiDialogThemMoi() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        insertNguyenVong dialog = new insertNguyenVong(topFrame, true);
        dialog.setVisible(true);

        if (dialog.xacNhanThem()) {
            // Lấy thằng đại diện ảo từ Form Con mang về (Chứa CCCD và Thứ tự NV)
            nguyenVongXetTuyenETT nvDaiDien = dialog.getNguyenVong();

            // 1. PHÁ HỦY RAM CŨ VÀ LOAD LẠI DATA MỚI TỪ DB
            busNguyenVong.ds = null; 
            busNguyenVong.layDanhSach(); 

            // --- ĐÃ XÓA SẠCH KHÚC SẮP XẾP: Trả về trạng thái nguyên thủy từ Database ---

            // 2. BUILD LẠI BẢNG TỪ RAM
            fullDataList.clear();
            int viTriCuaHangMoiThem = 0; 
            boolean daTimThay = false;

            for (int i = 0; i < busNguyenVong.ds.size(); i++) {
                nguyenVongXetTuyenETT item = busNguyenVong.ds.get(i);
                
                // Convert object → Vector
                Vector row = new Vector();
                row.add(item.getIdNv());
                row.add(item.getNnCccd());
                row.add(item.getNvMaNganh());
                row.add(item.getNvTt());
                row.add(item.getDiemThxt());
                
                // Điểm ưu tiên gốc
                row.add(item.getDiemUtqdGoc()); 
                
                row.add(item.getDiemUtqd());
                row.add(item.getDiemCong());
                row.add(item.getDiemXetTuyen());
                row.add(item.getNvKetQua());
                row.add(item.getNvKeys());
                row.add(item.getTtPhuongThuc());
                row.add(item.getTtThm());

                fullDataList.add(row);

                // 3. DÒ TÌM VỊ TRÍ ĐỂ NHẢY TRANG
                if (nvDaiDien != null && !daTimThay) {
                    if (item.getNnCccd().equals(nvDaiDien.getNnCccd()) && item.getNvTt() == nvDaiDien.getNvTt()) {
                        viTriCuaHangMoiThem = i; // Lấy vị trí dòng cuối cùng vừa được add của thí sinh đó
                        daTimThay = true; 
                    }
                }
            }

            // 4. CẬP NHẬT TRANG
            totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
            if (totalPages == 0) totalPages = 1;

            currentPage = (viTriCuaHangMoiThem / rowsPerPage) + 1;

            // 5. RENDER LÊN GIAO DIỆN
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
            nguyenVongXetTuyenETT nguyenVongCu = busNguyenVong.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            // Giả sử form của bạn tên là updateNguyenVong
            updateNguyenVong dialog = new updateNguyenVong(topFrame, true, nguyenVongCu);
            dialog.setVisible(true); // Form Sửa hiện lên
            
            // 3. Sau khi người dùng Sửa và bấm LƯU thành công
            if (dialog.xacNhanThem()) { 
                nguyenVongXetTuyenETT nvMoi = dialog.getNguyenVong();
                
                // 🔥 Ép kiểu đối tượng mới thành Vector y như hàm Thêm
                Vector rowData = new java.util.Vector();
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
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                nguyenVongXetTuyenETT nvCanXoa = busNguyenVong.findById(id);
                
                // 3. Gọi BUS thực thi lệnh XÓA
                if (busNguyenVong.xoaNguyenVong(nvCanXoa)) {
                    
                    // 🔥 Xóa luôn phần tử đó trong fullDataList để đồng bộ
                    int modelIndex = table.convertRowIndexToModel(row);
                    int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
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
     
    
//    private void thucHienTinhToanKetQua() {
//        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
//            "CẢNH BÁO: Hành động này sẽ khóa sổ và xét duyệt toàn bộ nguyện vọng trên hệ thống.\n" +
//            "Hệ thống sẽ tự động tổng hợp điểm, phân bổ chỉ tiêu và áp dụng tiêu chí phụ.\n" +
//            "Bạn có chắc chắn muốn tiến hành xét tuyển?", 
//            "Xác nhận Chốt Sổ", 
//            javax.swing.JOptionPane.YES_NO_OPTION, 
//            javax.swing.JOptionPane.WARNING_MESSAGE);
//            
//        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
//            
//            // --- BƯỚC 1: GỌI BUS ĐỂ TỰ ĐỘNG CẬP NHẬT CHỈ TIÊU ---
//            // Cất ghế cho team Tuyển thẳng, V-SAT, ĐGNL...
//            nganhBUS nBus = new nganhBUS();
//            if (nBus.capNhatChiTieuThucTe()) {
//                System.out.println("Hệ thống đã cập nhật lại chỉ tiêu thực tế thành công!");
//            }
//            
//            // --- BƯỚC 2: KÉO DỮ LIỆU ĐÃ TÍNH SẴN TỪ DB LÊN ---
//            // (Thầy nói đúng: KHÔNG TÍNH LẠI NỮA, vì ở Form Con đã tính và lưu sẵn diem_xet_tuyen rồi)
//            busNguyenVong.layDanhSach(); 
//
//            // --- BƯỚC 3: THUẬT TOÁN DOMINO CHÉM CHỈ TIÊU VÀ ĐẨY RỚT ---
//            // Thuật toán của ông sẽ dựa vào cái nv.getDiemXetTuyen() đã có sẵn để làm việc
//            busNguyenVong.sapXepKetQuaTheoChiTieu();
//            busNguyenVong.capNhatDiemChuanTuDong();
//            
//            // --- BƯỚC 4: LÀM MỚI GIAO DIỆN ---
//            busNguyenVong.ds = null; // Phá RAM cũ
//            loadDataToTable(); // Load RAM mới đã có trạng thái ĐẬU/RỚT
//            
//            javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã phân bổ chỉ tiêu và xét duyệt thành công!");
//        }
//    }
    
    private void thucHienTinhToanKetQua() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "CẢNH BÁO: Hành động này sẽ khóa sổ và xét duyệt toàn bộ nguyện vọng trên hệ thống.\n" +
            "Bạn có chắc chắn muốn tiến hành xét tuyển?", 
            "Xác nhận Chốt Sổ", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.WARNING_MESSAGE);
            
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            
            // --- ĐÃ XÓA BƯỚC 1 (Gọi hàm capNhatChiTieuThucTe) ---

            // --- BƯỚC 2: KÉO DỮ LIỆU ĐÃ TÍNH SẴN TỪ DB LÊN ---
            busNguyenVong.layDanhSach(); 

            // --- BƯỚC 3: THUẬT TOÁN DOMINO (Giờ nó sẽ lấy n_chitieu ra làm việc) ---
            busNguyenVong.sapXepKetQuaTheoChiTieu();
            busNguyenVong.capNhatDiemChuanTuDong();
            
            // --- BƯỚC 4: LÀM MỚI GIAO DIỆN ---
            busNguyenVong.ds = null; 
            loadDataToTable(); 
            
            javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã phân bổ chỉ tiêu và xét duyệt thành công!");
        }
    }
    
public void hienThiExcel() {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelNguyenVong dialog = new excelNguyenVong(topFrame, true);
        dialog.setVisible(true); 
        
        // --- SAU KHI DIALOG BỊ ĐÓNG (DISPOSE), CODE SẼ TIẾP TỤC CHẠY TỪ ĐÂY ---

        if (dialog.getXacNhanImport()) {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String thongBao = busNguyenVong.nhapDuLieuTuExcel(filePath);
                JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                busNguyenVong.ds = null;
                loadDataToTable();
            }
        } 
        else if (dialog.getXacNhanExport()) {
            // Xuất DS Gốc
            ArrayList<nguyenVongXetTuyenETT> fullDanhSach = busNguyenVong.layDanhSach();
            ExcelHelper.xuatDanhSachNguyenVongRaExcel(fullDanhSach, this, "DanhSachNguyenVong");
        } 
// 🚀 KIỂM TRA NÚT XUẤT DS TRÚNG TUYỂN
        else if (dialog.getXacNhanExportDSTrungTuyen()) {
            ArrayList<nguyenVongXetTuyenETT> dsDau = new ArrayList<>();
            for (nguyenVongXetTuyenETT nv : busNguyenVong.layDanhSach()) {
                if ("Đã đậu".equals(nv.getNvKetQua())) {
                    dsDau.add(nv);
                }
            }
            if (dsDau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có danh sách trúng tuyển. Vui lòng bấm Tính Kết Quả trước!");
                return;
            }
            
            // 🚀 ĐÃ SỬA: Gom theo Mã Ngành cho gọn, rồi bên trong mỗi ngành sẽ Xếp CCCD tăng dần
            dsDau.sort((nv1, nv2) -> {
                int nganhCompare = nv1.getNvMaNganh().compareTo(nv2.getNvMaNganh());
                if (nganhCompare != 0) return nganhCompare;
                
                // So sánh chuỗi CCCD
                return nv1.getNnCccd().compareTo(nv2.getNnCccd()); 
            });
            
            // Gọi Helper
            ExcelHelper.xuatDanhSachTrungTuyenRaExcel(dsDau, this, "DanhSachTrungTuyen");
        } 
        
        // 🚀 KIỂM TRA NÚT XUẤT THỐNG KÊ SL
        else if (dialog.getXacNhanExportSLPhuongThuc()) {
            java.util.HashMap<String, java.util.HashMap<String, Integer>> thongKeMap = new java.util.HashMap<>();
            for (nguyenVongXetTuyenETT nv : busNguyenVong.layDanhSach()) {
                if ("Đã đậu".equals(nv.getNvKetQua())) {
                    String nganh = nv.getNvMaNganh();
                    String pt = nv.getTtPhuongThuc();
                    thongKeMap.putIfAbsent(nganh, new java.util.HashMap<>());
                    java.util.HashMap<String, Integer> ptMap = thongKeMap.get(nganh);
                    ptMap.put(pt, ptMap.getOrDefault(pt, 0) + 1);
                }
            }
            if (thongKeMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có dữ liệu trúng tuyển để thống kê!");
                return;
            }
            
            // 🚀 THÊM MỚI: Kéo danh sách Tên Ngành từ BUS lên để ném qua cho Excel
            BUS.nganhBUS nBus = new BUS.nganhBUS();
            java.util.HashMap<String, String> mapTenNganh = new java.util.HashMap<>();
            for (Entity.nganhETT ng : nBus.layDanhSach()) {
                mapTenNganh.put(ng.getManganh(), ng.getTennganh());
            }

            // Gọi Helper (Truyền thêm cái mapTenNganh vào hàm)
            ExcelHelper.xuatThongKePhuongThucRaExcel(thongKeMap, mapTenNganh, this, "DanhSachTheoTungPT");
        }
    }
    
    public void thucHienRefresh()
    {
        cbxTimKiem.setSelectedIndex(0);
        txtTimKiem.setText(null);
        
        busNguyenVong.layDanhSach();

        fullDataList.clear();
        List<Vector> dataList = new ArrayList<>();

        for (nguyenVongXetTuyenETT ct : busNguyenVong.ds) 
        {
            Vector row = new Vector();
            row.add(ct.getIdNv());
            row.add(ct.getNnCccd());
            row.add(ct.getNvMaNganh());
            row.add(ct.getNvTt());
            row.add(ct.getDiemThxt());
            row.add(ct.getDiemUtqdGoc());
            row.add(ct.getDiemUtqd());
            row.add(ct.getDiemCong());
            row.add(ct.getDiemXetTuyen());
            row.add(ct.getNvKetQua());
            row.add(ct.getNvKeys());
            row.add(ct.getTtPhuongThuc());
            row.add(ct.getTtThm());

            dataList.add(row);
        }

        setTableData(dataList);
    }
    
private void hienThiChiTietNV()
    {
        int row = table.getSelectedRow();
        if (row != -1) {
            int modelIndex = table.convertRowIndexToModel(row);
            int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
            Vector selectedRowData = fullDataList.get(absoluteIndex);
            
            int idNvCanTim = (int) selectedRowData.get(0); 
                        nguyenVongXetTuyenETT nguyenVongCu = null;
            for (nguyenVongXetTuyenETT nv : busNguyenVong.ds) {
                if (nv.getIdNv() == idNvCanTim) {
                    nguyenVongCu = nv;
                    break;
                }
            }
            
            if (nguyenVongCu != null) {
                JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
                detailNguyenVong dialog = new detailNguyenVong(topFrame, true, nguyenVongCu);
                dialog.setVisible(true);
            }
        }
    }
}