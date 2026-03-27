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
import java.util.Collections;
import javax.swing.JOptionPane;
import CAL.*;

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
        btnReFresh.addActionListener(e -> thucHienRefresh());
        
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

//    if (dialog.xacNhanThem()) {
//        nguyenVongXetTuyenETT nv = dialog.getNguyenVong();
//
//        // 🔥 Convert object → Vector
//        Vector row = new Vector();
//        row.add(nv.getIdNv());
//        row.add(nv.getNnCccd());
//        row.add(nv.getNvMaNganh());
//        row.add(nv.getNvTt());
//        row.add(nv.getDiemThxt());
//        row.add(nv.getDiemUtqd());
//        row.add(nv.getDiemCong());
//        row.add(nv.getDiemXetTuyen());
//        row.add(nv.getNvKetQua());
//        row.add(nv.getNvKeys());
//        row.add(nv.getTtPhuongThuc());
//        row.add(nv.getTtThm());
//
//        // 🔥 ADD vào fullDataList (QUAN TRỌNG)
//        fullDataList.add(row);
//
//        // 🔥 Cập nhật lại totalPages
//        totalPages = (int) Math.ceil((double) fullDataList.size() / rowsPerPage);
//
//        // 🔥 Nếu muốn nhảy tới trang cuối
//        currentPage = totalPages;
//
//        // 🔥 Render lại
//        renderCurrentPage();
//    }
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
            Entity.nguyenVongXetTuyenETT nguyenVongCu = busNguyenVong.ds.get(absoluteIndex);
            JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(this);
            
            // Giả sử form của bạn tên là updateNguyenVong
            updateNguyenVong dialog = new updateNguyenVong(topFrame, true, nguyenVongCu);
            dialog.setVisible(true); // Form Sửa hiện lên
            
            // 3. Sau khi người dùng Sửa và bấm LƯU thành công
            if (dialog.xacNhanThem()) { 
                Entity.nguyenVongXetTuyenETT nvMoi = dialog.getNguyenVong();
                
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
     
private void thucHienRefresh() {
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
        "CẢNH BÁO: Hành động này sẽ khóa sổ và xét duyệt toàn bộ nguyện vọng trên hệ thống.\n" +
        "Hệ thống sẽ tự động quy đổi điểm V-SAT/ĐGNL và tính điểm ưu tiên.\n" +
        "Bạn có chắc chắn muốn tiến hành xét tuyển?", 
        "Xác nhận Chốt Sổ", 
        javax.swing.JOptionPane.YES_NO_OPTION, 
        javax.swing.JOptionPane.WARNING_MESSAGE);
        
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            // Lệnh SQL này sẽ đếm số lượng XTT thực tế và trừ thẳng vào chỉ tiêu THPT
            String sqlUpdateChiTieu = 
                "UPDATE xt_nganh n " +
                "SET n.sl_thpt = n.n_chitieu - " +
                "    IFNULL((SELECT COUNT(*) FROM xt_nguyenvongxettuyen nv WHERE nv.nv_manganh = n.manganh AND nv.tt_phuongthuc = 'Xét tuyển thẳng'), 0) " +
                "    - n.sl_dgnl - n.sl_vsat";
                
            session.createNativeQuery(sqlUpdateChiTieu).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Hệ thống đã tự động cất ghế cho team Tuyển thẳng và cập nhật lại chỉ tiêu THPT!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // Khai báo đối tượng BUS ở ngoài vòng lặp để xài chung
        nganhToHopBUS busNganhToHop = new nganhToHopBUS(); 

        busNguyenVong.napDiemAoChoDanhSach();

        for (nguyenVongXetTuyenETT nv : busNguyenVong.ds) {
            double diemChuanHoa = 0;
            String phuongThuc = nv.getTtPhuongThuc();

            // Lấy hệ số môn học
            double w1 = nv.getHsMon1();
            double w2 = nv.getHsMon2();
            double w3 = nv.getHsMon3();
            double W = w1 + w2 + w3;

            // --- TẬN DỤNG HÀM BUS ĐỂ LẤY ĐỘ LỆCH DỄ DÀNG ---
            double doLechDiem = 0.0;
            if (phuongThuc.equals("Xét THPT") || phuongThuc.equals("Đánh giá V-SAT")) {
                doLechDiem = busNganhToHop.layDoLechDiem(nv.getNvMaNganh(), nv.getTtThm()); 
            }
            
            if (phuongThuc.equals("Đánh giá V-SAT")) {
                double d1 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon1(), nv.getDiemMon1());
                double d2 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon2(), nv.getDiemMon2());
                double d3 = CAL.AdmissionsConverter.quyDoiVsat(nv.getTenMon3(), nv.getDiemMon3());
                diemChuanHoa = ((d1*w1 + d2*w2 + d3*w3) / W) * 3.0;
            }
            else if (phuongThuc.equals("ĐGNL HCM")) {
                diemChuanHoa = (nv.getDiemMon1() / 1200.0) * 30.0;
            }
            else if (phuongThuc.equals("Xét tuyển thẳng")) {
                diemChuanHoa = 30.0; // Auto 30 điểm gốc
                try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
                    // Lấy CẢ Cấp giải (Quốc gia/Tỉnh) và Loại giải (Nhất/Nhì/Ba/Khuyến khích)
                    String sqlXTT = "SELECT cap_giai, loai_giai FROM xt_giathuong WHERE cccd = :cccd LIMIT 1";
                    Object[] giaiData = (Object[]) session.createNativeQuery(sqlXTT).setParameter("cccd", nv.getNnCccd()).uniqueResult();

                    if (giaiData != null) {
                        String capGiai = giaiData[0] != null ? giaiData[0].toString() : "";
                        String loaiGiai = giaiData[1] != null ? giaiData[1].toString() : "";
                        
                        double diemAn = 0.0;
                        
                        // TH1: Phân lô bảng điểm cho team QUỐC GIA (Từ 0.006 -> 0.009)
                        if (capGiai.equalsIgnoreCase("Quốc gia")) {
                            if (loaiGiai.contains("Nhất")) diemAn = 0.009;
                            else if (loaiGiai.contains("Nhì")) diemAn = 0.008;
                            else if (loaiGiai.contains("Ba")) diemAn = 0.007;
                            else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.006;
                        } 
                        // TH2: Phân lô bảng điểm cho team CẤP TỈNH (Từ 0.002 -> 0.005)
                        else if (capGiai.equalsIgnoreCase("Cấp tỉnh") || capGiai.equalsIgnoreCase("Tỉnh")) {
                            if (loaiGiai.contains("Nhất")) diemAn = 0.005;
                            else if (loaiGiai.contains("Nhì")) diemAn = 0.004;
                            else if (loaiGiai.contains("Ba")) diemAn = 0.003;
                            else if (loaiGiai.contains("Khuyến khích")) diemAn = 0.002;
                        }
                        
                        // Bơm điểm ẩn vào
                        diemChuanHoa += diemAn;
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
            else {
                // Xét THPT
                double d1 = nv.getDiemMon1();
                double d2 = nv.getDiemMon2();
                double d3 = nv.getDiemMon3();
                diemChuanHoa = ((d1*w1 + d2*w2 + d3*w3) / W) * 3.0;
            }

// ... (Đoạn trên tính diemChuanHoa và doLechDiem giữ nguyên) ...

            // --- CODE MỚI: KIỂM TRA NULL TRƯỚC KHI CỘNG ĐỂ CHỐNG CRASH ---
            double diemUuTienAnToan = (nv.getDiemUtqd() != null) ? nv.getDiemUtqd() : 0.0;
            double diemCongAnToan = (nv.getDiemCong() != null) ? nv.getDiemCong() : 0.0;

            // Tính tổng cuối có cộng Độ Lệch và tách bạch điểm chuẩn ý thầy
            double tongCuoi = diemChuanHoa + diemUuTienAnToan + diemCongAnToan + doLechDiem;

            if (!phuongThuc.equals("Xét tuyển thẳng")) {
                tongCuoi = Math.min(30.0, tongCuoi);
            }

            tongCuoi = Math.round(tongCuoi * 1000.0) / 1000.0; 
            nv.setDiemXetTuyen(tongCuoi);
        }

        busNguyenVong.sapXepKetQuaTheoChiTieu();
        busNguyenVong.capNhatDiemChuanTuDong();
        
        busNguyenVong.ds = null; 
        loadDataToTable(); 
        
        javax.swing.JOptionPane.showMessageDialog(this, "Hệ thống đã quy đổi điểm và xét duyệt thành công!");
    }
}
}