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
import FUNC_GUI.excelNguyenVong;
import java.util.Collections;
import javax.swing.JOptionPane;
import EXCEL.ExcelHelper;
import CAL.*;
import FUNC_GUI.detailNguyenVong;
import java.lang.reflect.Array;

public class nguyenVongXetTuyenGUI extends BaseTableForNguyenVongGUIonly {

    nguyenVongXetTuyenBUS busNguyenVong = new nguyenVongXetTuyenBUS();
    public nguyenVongXetTuyenGUI() {
        super();
        setTableNameForTitle("Nguyện Vọng"); 
        headerTable();
        
        btnThem.addActionListener(e -> hienThiDialogThemMoi());
        btnSua.addActionListener(e -> hienThiDialogSua());
        btnXoa.addActionListener(e ->hienThiDialogXoa());
        btnTinhToanKetQua.addActionListener(e -> thucHienTinhToanKetQua());
        btnExcel.addActionListener(e -> hienThiExcel());
        btnReFresh.addActionListener(e -> thucHienRefresh());
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        btnChiTiet.addActionListener(e -> hienThiChiTietNV());
        
        
        loadDataToTable();
        table.getColumnModel().getColumn(9).setMinWidth(0);
        table.getColumnModel().getColumn(9).setMaxWidth(0);
        table.getColumnModel().getColumn(9).setWidth(0);
        loadComboBox();
        phanQuyenGiaoDien();
    }
    
    private void phanQuyenGiaoDien() 
    {
        String bangHienTai = "xt_nguyenvongxettuyen";
        
        if (!BUS.phanQuyenBUS.checkQuyenXem(bangHienTai)) {
            return;
        }
        
        btnThem.setEnabled(BUS.phanQuyenBUS.checkQuyenThem(bangHienTai)); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
        btnChiTiet.setEnabled(false);
        btnExcel.setEnabled(BUS.phanQuyenBUS.checkQuyenThem(bangHienTai));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        boolean isSelected = table.getSelectedRow() != -1;
                        
                        if (isSelected) {
                            btnSua.setEnabled(BUS.phanQuyenBUS.checkQuyenSua(bangHienTai));
                            btnXoa.setEnabled(BUS.phanQuyenBUS.checkQuyenXoa(bangHienTai));
                            btnChiTiet.setEnabled(BUS.phanQuyenBUS.checkQuyenXem(bangHienTai)); 
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

private void hienThiDialogThemMoi() {
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    insertNguyenVong dialog = new insertNguyenVong(topFrame, true);
    dialog.setVisible(true);

if (dialog.xacNhanThem()) {
        nguyenVongXetTuyenETT nv = dialog.getNguyenVong();

        // 1. Lúc này hàm thêm của BUS đã nhét "nv" vào cuối biến "ds" rồi.
        // Việc đầu tiên: SẮP XẾP lại "ds" ngay trên RAM (Tốc độ ánh sáng, không gọi DB)
        Collections.sort(busNguyenVong.ds, new java.util.Comparator<nguyenVongXetTuyenETT>() {
            @Override
            public int compare(nguyenVongXetTuyenETT nv1, nguyenVongXetTuyenETT nv2) {
                int cccdCompare = nv1.getNnCccd().compareTo(nv2.getNnCccd());
                if (cccdCompare != 0) return cccdCompare;
                return Integer.compare(nv1.getNvTt(), nv2.getNvTt());
            }
        });

        // 2. Làm mới toàn bộ fullDataList dựa trên ds đã sắp xếp
        fullDataList.clear();
        int viTriCuaHangMoiThem = 0; // Biến để lưu vết xem thằng mới thêm rớt vào dòng thứ mấy

        for (int i = 0; i < busNguyenVong.ds.size(); i++) {
            nguyenVongXetTuyenETT item = busNguyenVong.ds.get(i);
            
            // Convert object → Vector
            Vector row = new Vector();
            row.add(item.getIdNv());
            row.add(item.getNnCccd());
            row.add(item.getNvMaNganh());
            row.add(item.getNvTt());
            row.add(item.getDiemThxt());
            row.add(item.getDiemUtqd());
            row.add(item.getDiemCong());
            row.add(item.getDiemXetTuyen());
            row.add(item.getNvKetQua());
            row.add(item.getNvKeys());
            row.add(item.getTtPhuongThuc());
            row.add(item.getTtThm());

            fullDataList.add(row);

            // Tìm dấu vết thằng vừa thêm bằng nv_keys (CCCD_ThuTuNV)
            if (item.getNvKeys().equals(nv.getNvKeys())) {
                viTriCuaHangMoiThem = i;
            }
        }

        // 3. Cập nhật lại tổng số trang
        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);

        // 4. 🔥 BÍ QUYẾT: Tính toán xem vị trí mới thêm nó rớt vào Trang số mấy để nhảy tới!
        currentPage = (viTriCuaHangMoiThem / rowsPerPage) + 1;

        // 5. Render lại đúng cái trang chứa nguyện vọng vừa thêm
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
                // 2. Tính Index thực sự y như hàm Sửa
                int modelIndex = table.convertRowIndexToModel(row);
                int absoluteIndex = (currentPage - 1) * rowsPerPage + modelIndex;
                
                nguyenVongXetTuyenETT nvCanXoa = busNguyenVong.ds.get(absoluteIndex);
                
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
     
//    private void thucHienRefresh() {
//    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
//        "CẢNH BÁO: Hành động này sẽ khóa sổ và xét duyệt toàn bộ nguyện vọng trên hệ thống.\n" +
//        "Hệ thống sẽ tự động quy đổi điểm V-SAT/ĐGNL và tính điểm ưu tiên.\n" +
//        "Bạn có chắc chắn muốn tiến hành xét tuyển?", 
//        "Xác nhận Chốt Sổ", 
//        javax.swing.JOptionPane.YES_NO_OPTION, 
//        javax.swing.JOptionPane.WARNING_MESSAGE);
//        
//    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
//        
//        // --- BƯỚC 0: GỌI BUS ĐỂ TỰ ĐỘNG CẬP NHẬT CHỈ TIÊU ---
//        nganhBUS nBus = new nganhBUS();
//        if (nBus.capNhatChiTieuThucTe()) {
//            System.out.println("Hệ thống đã tự động cất ghế cho team Tuyển thẳng và cập nhật lại chỉ tiêu THPT!");
//        }
//        
//        // Khai báo đối tượng BUS ở ngoài vòng lặp để xài chung
//        nganhToHopBUS busNganhToHop = new nganhToHopBUS(); 
//        giaiThuongBUS gtBus = new giaiThuongBUS(); // Bus lấy giải thưởng
//
//        busNguyenVong.napDiemAoChoDanhSach();
//
//        for (nguyenVongXetTuyenETT nv : busNguyenVong.ds) {
//            double diemChuanHoa = 0;
//            String phuongThuc = nv.getTtPhuongThuc();
//
//            // Lấy hệ số môn học
//            double w1 = nv.getHsMon1();
//            double w2 = nv.getHsMon2();
//            double w3 = nv.getHsMon3();
//            double W = w1 + w2 + w3;
//
//            // Lấy độ lệch điểm
//            double doLechDiem = 0.0;
//            if (phuongThuc.equals("Xét THPT") || phuongThuc.equals("Đánh giá V-SAT")) {
//                doLechDiem = busNganhToHop.layDoLechDiem(nv.getNvMaNganh(), nv.getTtThm()); 
//            }
//            
//            // XÉT TỪNG PHƯƠNG THỨC
//            if (phuongThuc.equals("Đánh giá V-SAT")) {
//                double d1 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon1(), nv.getDiemMon1());
//                double d2 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon2(), nv.getDiemMon2());
//                double d3 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon3(), nv.getDiemMon3());
//                diemChuanHoa = ((d1*w1 + d2*w2 + d3*w3) / W) * 3.0;
//            }
//            else if (phuongThuc.equals("ĐGNL HCM")) {
//                diemChuanHoa = (nv.getDiemMon1() / 1200.0) * 30.0;
//            }
//            else if (phuongThuc.equals("Xét tuyển thẳng")) {
//                diemChuanHoa = 30.0; // Auto 30 điểm gốc
//                
//                // GỌI BUS ĐỂ LẤY CẤP VÀ LOẠI GIẢI (SẠCH SẼ!)
//                String[] ttGiai = gtBus.layCapVaLoaiGiai(nv.getNnCccd());
//                String capGiai = ttGiai[0];
//                String loaiGiai = ttGiai[1];
//                
//                double diemAn = 0.0;
//                
//                if (capGiai.equalsIgnoreCase("Quốc gia")) {
//                    if (loaiGiai.contains("Nhất")) diemAn = 0.009;
//                    else if (loaiGiai.contains("Nhì")) diemAn = 0.008;
//                    else if (loaiGiai.contains("Ba")) diemAn = 0.007;
//                    else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.006;
//                } 
//                else if (capGiai.equalsIgnoreCase("Cấp tỉnh") || capGiai.equalsIgnoreCase("Tỉnh")) {
//                    if (loaiGiai.contains("Nhất")) diemAn = 0.005;
//                    else if (loaiGiai.contains("Nhì")) diemAn = 0.004;
//                    else if (loaiGiai.contains("Ba")) diemAn = 0.003;
//                    else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.002;
//                }
//                
//                diemChuanHoa += diemAn;
//            }
//            else {
//                // Xét THPT
//                double d1 = nv.getDiemMon1();
//                double d2 = nv.getDiemMon2();
//                double d3 = nv.getDiemMon3();
//                diemChuanHoa = ((d1*w1 + d2*w2 + d3*w3) / W) * 3.0;
//            }
//
//            // KIỂM TRA NULL TRƯỚC KHI CỘNG ĐỂ CHỐNG CRASH
//            double diemUuTienAnToan = (nv.getDiemUtqd() != null) ? nv.getDiemUtqd() : 0.0;
//            double diemCongAnToan = (nv.getDiemCong() != null) ? nv.getDiemCong() : 0.0;
//
//            // Tính tổng cuối có cộng Độ Lệch và tách bạch điểm chuẩn ý thầy
//            double tongCuoi = diemChuanHoa + diemUuTienAnToan + diemCongAnToan + doLechDiem;
//
//            if (!phuongThuc.equals("Xét tuyển thẳng")) {
//                tongCuoi = Math.min(30.0, tongCuoi);
//            }
//
//            tongCuoi = Math.round(tongCuoi * 1000.0) / 1000.0; 
//            nv.setDiemXetTuyen(tongCuoi);
//        }
//
//        busNguyenVong.sapXepKetQuaTheoChiTieu();
//        busNguyenVong.capNhatDiemChuanTuDong();
//        
//        busNguyenVong.ds = null; 
//        loadDataToTable(); 
//        
//        javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã quy đổi điểm và xét duyệt thành công!");
//    }
//}
    
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
//            // --- BƯỚC 0: GỌI BUS ĐỂ TỰ ĐỘNG CẬP NHẬT CHỈ TIÊU (Đã fix lỗi trừ sai) ---
//            nganhBUS nBus = new nganhBUS();
//            if (nBus.capNhatChiTieuThucTe()) {
//                System.out.println("Hệ thống đã tự động cất ghế cho team Tuyển thẳng, V-SAT, ĐGNL và cập nhật lại chỉ tiêu THPT!");
//            }
//            
//            // --- BƯỚC 1: LẤY ĐIỂM THÀNH PHẦN (Phục vụ cho Tiêu chí phụ Môn Toán lúc xếp hạng) ---
//            busNguyenVong.napDiemAoChoDanhSach();
//
//            nganhToHopBUS busNganhToHop = new nganhToHopBUS(); 
//            giaiThuongBUS gtBus = new giaiThuongBUS(); 
//
//            // --- BƯỚC 2: GOM ĐIỂM THÀNH PHẨM TỪ DATABASE ---
//            for (nguyenVongXetTuyenETT nv : busNguyenVong.ds) {
//                String phuongThuc = nv.getTtPhuongThuc();
//
//                // 1. Lấy độ lệch điểm (Nếu có)
//                double doLechDiem = 0.0;
//                if (phuongThuc.equals("Xét THPT") || phuongThuc.equals("Đánh giá V-SAT")) {
//                    doLechDiem = busNganhToHop.layDoLechDiem(nv.getNvMaNganh(), nv.getTtThm()); 
//                }
//                
//                // 2. Lấy các con số ĐÃ ĐƯỢC CHỐT SỔ TỪ FORM CON / EXCEL (Không tính lại hệ số môn)
//                double diemThxtAnToan = (nv.getDiemThxt() != null) ? nv.getDiemThxt() : 0.0;
//                double diemUuTienKhuVuc = (nv.getDiemUtqd() != null) ? nv.getDiemUtqd() : 0.0;
//                double diemCongThem = (nv.getDiemCong() != null) ? nv.getDiemCong() : 0.0;
//
//                double tongCuoi = 0.0;
//
//                // 3. XỬ LÝ RIÊNG TEAM TUYỂN THẲNG (Gắn điểm ẩn để xếp hạng)
//                if (phuongThuc.equals("Xét tuyển thẳng")) {
//                    diemThxtAnToan = 30.0; // Auto 30 điểm gốc
//                    
//                    String[] ttGiai = gtBus.layCapVaLoaiGiai(nv.getNnCccd());
//                    String capGiai = ttGiai[0];
//                    String loaiGiai = ttGiai[1];
//                    double diemAn = 0.0;
//                    
//                    if (capGiai.equalsIgnoreCase("Quốc gia")) {
//                        if (loaiGiai.contains("Nhất")) diemAn = 0.009;
//                        else if (loaiGiai.contains("Nhì")) diemAn = 0.008;
//                        else if (loaiGiai.contains("Ba")) diemAn = 0.007;
//                        else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.006;
//                    } 
//                    else if (capGiai.equalsIgnoreCase("Cấp tỉnh") || capGiai.equalsIgnoreCase("Tỉnh")) {
//                        if (loaiGiai.contains("Nhất")) diemAn = 0.005;
//                        else if (loaiGiai.contains("Nhì")) diemAn = 0.004;
//                        else if (loaiGiai.contains("Ba")) diemAn = 0.003;
//                        else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.002;
//                    }
//                    tongCuoi = diemThxtAnToan + diemAn;
//                } 
//                else {
//                    // CÁC PHƯƠNG THỨC KHÁC: Tổng hợp và khóa trần 30đ
//                    tongCuoi = diemThxtAnToan + diemUuTienKhuVuc + diemCongThem + doLechDiem;
//                    tongCuoi = Math.min(30.0, tongCuoi); 
//                }
//
//                // Làm tròn 3 chữ số thập phân cho chuẩn
//                tongCuoi = Math.round(tongCuoi * 1000.0) / 1000.0; 
//                nv.setDiemXetTuyen(tongCuoi);
//            }
//
//            // --- BƯỚC 3: THUẬT TOÁN DOMINO CHÉM CHỈ TIÊU ---
//            busNguyenVong.sapXepKetQuaTheoChiTieu();
//            busNguyenVong.capNhatDiemChuanTuDong();
//            
//            // Tải lại bảng lên giao diện
//            busNguyenVong.ds = null; 
//            loadDataToTable(); 
//            
//            javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã phân bổ chỉ tiêu và xét duyệt thành công!");
//        }
//    }
    
    private void thucHienTinhToanKetQua() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "CẢNH BÁO: Hành động này sẽ khóa sổ và xét duyệt toàn bộ nguyện vọng trên hệ thống.\n" +
            "Hệ thống sẽ tự động tổng hợp điểm, phân bổ chỉ tiêu và áp dụng tiêu chí phụ.\n" +
            "Bạn có chắc chắn muốn tiến hành xét tuyển?", 
            "Xác nhận Chốt Sổ", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.WARNING_MESSAGE);
            
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            
            // --- BƯỚC 1: GỌI BUS ĐỂ TỰ ĐỘNG CẬP NHẬT CHỈ TIÊU ---
            // Cất ghế cho team Tuyển thẳng, V-SAT, ĐGNL...
            nganhBUS nBus = new nganhBUS();
            if (nBus.capNhatChiTieuThucTe()) {
                System.out.println("Hệ thống đã cập nhật lại chỉ tiêu thực tế thành công!");
            }
            
            // --- BƯỚC 2: KÉO DỮ LIỆU ĐÃ TÍNH SẴN TỪ DB LÊN ---
            // (Thầy nói đúng: KHÔNG TÍNH LẠI NỮA, vì ở Form Con đã tính và lưu sẵn diem_xet_tuyen rồi)
            busNguyenVong.layDanhSach(); 

            // --- BƯỚC 3: THUẬT TOÁN DOMINO CHÉM CHỈ TIÊU VÀ ĐẨY RỚT ---
            // Thuật toán của ông sẽ dựa vào cái nv.getDiemXetTuyen() đã có sẵn để làm việc
            busNguyenVong.sapXepKetQuaTheoChiTieu();
            busNguyenVong.capNhatDiemChuanTuDong();
            
            // --- BƯỚC 4: LÀM MỚI GIAO DIỆN ---
            busNguyenVong.ds = null; // Phá RAM cũ
            loadDataToTable(); // Load RAM mới đã có trạng thái ĐẬU/RỚT
            
            javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã phân bổ chỉ tiêu và xét duyệt thành công!");
        }
    }
    
//        private void thucHienTinhToanKetQua() {
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
//            // --- BƯỚC 0: GỌI BUS ĐỂ TỰ ĐỘNG CẬP NHẬT CHỈ TIÊU (Đã fix lỗi trừ sai) ---
//            nganhBUS nBus = new nganhBUS();
//            if (nBus.capNhatChiTieuThucTe()) {
//                System.out.println("Hệ thống đã tự động cất ghế cho team Tuyển thẳng, V-SAT, ĐGNL và cập nhật lại chỉ tiêu THPT!");
//            }
//            
//            // --- BƯỚC 1: LẤY ĐIỂM THÀNH PHẦN (Phục vụ cho Tiêu chí phụ Môn Toán lúc xếp hạng) ---
//            busNguyenVong.napDiemAoChoDanhSach();
//            DAO.bangQuyDoiDAO qdDao = new DAO.bangQuyDoiDAO();
//            ArrayList<bangQuyDoiETT> dsQuyDoi = qdDao.layDanhSach();
//
//            nganhToHopBUS busNganhToHop = new nganhToHopBUS(); 
//            giaiThuongBUS gtBus = new giaiThuongBUS(); 
//
//            // --- BƯỚC 2: GOM ĐIỂM THÀNH PHẨM TỪ DATABASE ---
//            for (nguyenVongXetTuyenETT nv : busNguyenVong.ds) {
//                String phuongThuc = nv.getTtPhuongThuc();
//                String maNganh = nv.getNvMaNganh();
//                String toHop = nv.getTtThm();
//
//// 1. Lấy độ lệch điểm và HỆ SỐ MÔN (Mới thêm)
//                double doLechDiem = 0.0;
//                double diemThxtMoi = 0.0; // Biến chứa điểm đã nhân hệ số mới
//
//                if (phuongThuc.equals("ĐGNL HCM")) {
//                    // ĐGNL không nhân hệ số môn, chỉ quy đổi
//                    double diemDgnlGoc = (nv.getDiemMon1() > 0) ? nv.getDiemMon1() : 0.0;
//                    diemThxtMoi = CAL.AdmissionsConverter.quyDoiDiemChung(phuongThuc, toHop, "", diemDgnlGoc, dsQuyDoi);
//                }
//                else if (phuongThuc.equals("Xét THPT") || phuongThuc.equals("Đánh giá V-SAT")) {
//                    
//                    // Lấy Tổ Hợp hiện tại lên để soi Hệ số
//                    Entity.nganhToHopETT toHopHienTai = busNganhToHop.layNganhToHopBangKey(maNganh + "_" + toHop);
//                    
//                    if (toHopHienTai != null) {
//                        doLechDiem = toHopHienTai.getDoLech();
//                        
//                        // 🔥 TÍNH LẠI ĐIỂM THXT THEO HỆ SỐ MỚI NHẤT 🔥
//                        int hs1 = toHopHienTai.getHeSoMon1();
//                        int hs2 = toHopHienTai.getHeSoMon2();
//                        int hs3 = toHopHienTai.getHeSoMon3();
//                        int tongHeSo = hs1 + hs2 + hs3;
//                        
//                        // Nếu tổng hệ số = 0 thì mặc định chia 3 để tránh lỗi / by zero
//                        tongHeSo = (tongHeSo > 0) ? tongHeSo : 3; 
//
//                        if (phuongThuc.equals("Xét THPT")) {
//                            diemThxtMoi = (nv.getDiemMon1()*hs1 + nv.getDiemMon2()*hs2 + nv.getDiemMon3()*hs3) * 3 / tongHeSo;
//                        } else if (phuongThuc.equals("Đánh giá V-SAT")) {
////                            // VSAT Thang 150 -> Quy về 10, nhân hệ số, rồi quy về 30
//                            double d1 = CAL.AdmissionsConverter.quyDoiDiemChung(phuongThuc, toHop, nv.getTenMon1(), nv.getDiemMon1(), dsQuyDoi);
//                            double d2 = CAL.AdmissionsConverter.quyDoiDiemChung(phuongThuc, toHop, nv.getTenMon2(), nv.getDiemMon2(), dsQuyDoi);
//                            double d3 = CAL.AdmissionsConverter.quyDoiDiemChung(phuongThuc, toHop, nv.getTenMon3(), nv.getDiemMon3(), dsQuyDoi);
//                            diemThxtMoi = ((d1*hs1 + d2*hs2 + d3*hs3) / tongHeSo) * 3.0;
//                        }
//                    }
//                }
//
//                // Cập nhật lại điểm THXT mới tính vào Object (Để lát nó Lưu xuống DB luôn)
//                if (!phuongThuc.equals("Xét tuyển thẳng")) {
//                     nv.setDiemThxt(Math.round(diemThxtMoi * 100.0) / 100.0);
//                }
//
//                // 2. Lấy các con số điểm cộng
//                double diemThxtAnToan = (nv.getDiemThxt() != null) ? nv.getDiemThxt() : 0.0;
//                double diemUuTienKhuVuc = (nv.getDiemUtqd() != null) ? nv.getDiemUtqd() : 0.0;
//                double diemCongThem = (nv.getDiemCong() != null) ? nv.getDiemCong() : 0.0;
//
//                double tongCuoi = 0.0;
//
//                // 3. XỬ LÝ RIÊNG TEAM TUYỂN THẲNG (Gắn điểm ẩn để xếp hạng)
//                if (phuongThuc.equals("Xét tuyển thẳng")) {
//                    diemThxtAnToan = 30.0; // Auto 30 điểm gốc
//                    
//                    String[] ttGiai = gtBus.layCapVaLoaiGiai(nv.getNnCccd());
//                    String capGiai = ttGiai[0];
//                    String loaiGiai = ttGiai[1];
//                    double diemAn = 0.0;
//                    
//                    if (capGiai.equalsIgnoreCase("Quốc gia")) {
//                        if (loaiGiai.contains("Nhất")) diemAn = 0.009;
//                        else if (loaiGiai.contains("Nhì")) diemAn = 0.008;
//                        else if (loaiGiai.contains("Ba")) diemAn = 0.007;
//                        else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.006;
//                    } 
//                    else if (capGiai.equalsIgnoreCase("Cấp tỉnh") || capGiai.equalsIgnoreCase("Tỉnh")) {
//                        if (loaiGiai.contains("Nhất")) diemAn = 0.005;
//                        else if (loaiGiai.contains("Nhì")) diemAn = 0.004;
//                        else if (loaiGiai.contains("Ba")) diemAn = 0.003;
//                        else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.002;
//                    }
//                    tongCuoi = diemThxtAnToan + diemAn;
//                } 
//                else {
//                    // CÁC PHƯƠNG THỨC KHÁC: Tổng hợp và khóa trần 30đ
////                    tongCuoi = diemThxtAnToan + diemUuTienKhuVuc + diemCongThem + doLechDiem;
////                    tongCuoi = Math.min(30.0, tongCuoi); 
//                    // CÁC PHƯƠNG THỨC KHÁC: Tổng hợp và khóa trần 30đ
//                    
//                    // 🔥 BẮT ĐẦU LOGIC BÓP ĐIỂM ƯU TIÊN THEO CHUẨN BỘ GD 🔥
//                    // Lưu ý: Biến diemUuTienKhuVuc của ông lúc này chính là MĐƯT (Đã bốc từ DB lên)
//                    double diemXetMoc = diemThxtAnToan + diemCongThem; 
//                    double diemUuTienThucTe = diemUuTienKhuVuc; 
//                    
//                    // Nếu tổng điểm >= 22.5 thì áp dụng công thức 
//                    if (diemXetMoc >= 22.5) {
//                        // Công thức: [(30 - ĐTHXT - ĐC) / 7.5] * MĐƯT 
//                        diemUuTienThucTe = ((30.0 - diemThxtAnToan - diemCongThem) / 7.5) * diemUuTienKhuVuc;
//                        
//                        if (diemUuTienThucTe < 0) {
//                            diemUuTienThucTe = 0.0;
//                        }
//                    }
//
//                    // TÍNH TỔNG ĐIỂM CUỐI: Dùng điểm ưu tiên đã bóp [cite: 76]
//                    tongCuoi = diemThxtAnToan + diemUuTienThucTe + diemCongThem + doLechDiem;
//                    tongCuoi = Math.min(30.0, tongCuoi);
//                }
//
//                // Làm tròn 3 chữ số thập phân cho chuẩn
//                tongCuoi = Math.round(tongCuoi * 100.0) / 100.0; 
//                nv.setDiemXetTuyen(tongCuoi);
//            }
//
//            // --- BƯỚC 3: THUẬT TOÁN DOMINO CHÉM CHỈ TIÊU ---
//            busNguyenVong.sapXepKetQuaTheoChiTieu();
//            busNguyenVong.capNhatDiemChuanTuDong();
//            
//            // Tải lại bảng lên giao diện
//            busNguyenVong.ds = null; 
//            loadDataToTable(); 
//            
//            javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã phân bổ chỉ tiêu và xét duyệt thành công!");
//        }
//    }
    
    public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
        excelNguyenVong dialog = new excelNguyenVong(topFrame, true);
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
            String thongBao = busNguyenVong.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(this, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            busNguyenVong.ds = null;
            loadDataToTable();
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<nguyenVongXetTuyenETT> fullDanhSach = busNguyenVong.layDanhSach();
            ExcelHelper.xuatDanhSachNguyenVongRaExcel(fullDanhSach, this, "DanhSachNguyenVong");
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
            
            nguyenVongXetTuyenETT nguyenVongCu = busNguyenVong.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            detailNguyenVong dialog = new detailNguyenVong(topFrame, true, nguyenVongCu);
            dialog.setVisible(true);
        }
    }
}