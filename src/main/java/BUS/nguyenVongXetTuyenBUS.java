/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.nganhDAO;
import DAO.nguyenVongXetTuyenDAO;
import Entity.nguyenVongXetTuyenETT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author mhoang
 */
public class nguyenVongXetTuyenBUS {
    public static ArrayList<nguyenVongXetTuyenETT> ds;
    nguyenVongXetTuyenDAO data = new nguyenVongXetTuyenDAO();
    
    public ArrayList<nguyenVongXetTuyenETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }

    public nguyenVongXetTuyenETT findById(int id) {
        if (ds == null) layDanhSach();
        for (nguyenVongXetTuyenETT nv : ds) {
            if (nv.getIdNv() == id) return nv;
        }
        return null;
    }
    
    public boolean kiemTraRong(String text)
    {
        if(text == null || text.trim().isEmpty())
        {
            return true;
        }
        return false;
    }
    
    public boolean kiemTraSo(String text) 
    {
        try 
        {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
    public boolean kiemTraSoThuc(String text) 
    {
    try {
        Double.parseDouble(text);
        return true;
    } catch (NumberFormatException e) 
    {
        return false;
    }
}
    
    public boolean themNguyenVong(nguyenVongXetTuyenETT nv) {
        boolean isSuccess = data.saveNguyenVong(nv);
        if (isSuccess) {
            if (ds != null) 
            {
                ds.add(nv);
            }
        }

        return isSuccess;
    }
    
    public boolean suaNguyenVong(Entity.nguyenVongXetTuyenETT nvMoi) {
        // 1. Lưu xuống Database trước
        if (data.suaNguyenVong(nvMoi)) {
            
            // 2. Nếu Database OK, tiến hành cập nhật lại biến ds tĩnh
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng nv_keys để dò tìm vì nó là duy nhất (Unique)
                    // (Hồi nãy mình đã khóa ô CCCD và Thứ tự lại nên nv_keys chắc chắn không bị đổi)
                    if (ds.get(i).getNvKeys().equals(nvMoi.getNvKeys())) {
                        ds.set(i, nvMoi); // Đè đối tượng mới vào vị trí cũ
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean xoaNguyenVong(Entity.nguyenVongXetTuyenETT nv) {
        // 1. Kêu DAO xóa dưới MySQL
        if (data.xoaNguyenVong(nv)) {
            
            // 2. Nếu MySQL xóa thành công, xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dò đúng cái ID đó thì gạch tên khỏi danh sách
                    if (ds.get(i).getIdNv() == nv.getIdNv()) {
                        ds.remove(i); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    
    // ==================================================================================
    // 🏆 THUẬT TOÁN BOSS: LỌC ẢO (DOMINO) CẮT THEO CHỈ TIÊU TỪNG PHƯƠNG THỨC
    // ==================================================================================
    public void sapXepKetQuaTheoChiTieu() {
        if(ds == null || ds.isEmpty()) {
            layDanhSach();
        }

        nganhBUS busNganh = new nganhBUS();
        ArrayList<Entity.nganhETT> dsNganh = busNganh.layDanhSach();
        HashMap<String, String> mapToHopGoc = new HashMap<>();

        // BƯỚC 1: TẠO BẢN ĐỒ CHỈ TIÊU 
        // Lấy chỉ tiêu từ Database lên RAM để check cho lẹ, Key = "MaNganh_PhuongThuc"
        HashMap<String, Integer> mapChiTieu = new HashMap<>();
        for (Entity.nganhETT nganh : dsNganh) {
            mapChiTieu.put(nganh.getManganh() + "_Xét THPT", nganh.getSl_thpt() != null ? nganh.getSl_thpt(): 0);
            mapChiTieu.put(nganh.getManganh() + "_ĐGNL HCM", nganh.getSl_dgnl() != null ? nganh.getSl_dgnl() : 0);
            mapChiTieu.put(nganh.getManganh() + "_Đánh giá V-SAT", nganh.getSl_vsat() != null ? nganh.getSl_vsat() : 0);
            mapChiTieu.put(nganh.getManganh() + "_Xét tuyển thẳng", nganh.getSl_xtt() != null ? nganh.getSl_xtt() : 0);
            mapToHopGoc.put(nganh.getManganh(), nganh.getN_tohopgoc());
        }
        
        BUS.giaiThuongBUS gtBus = new BUS.giaiThuongBUS();
        BUS.quyTacGiaiThuongBUS qtBus = new BUS.quyTacGiaiThuongBUS();
        qtBus.loadQuyTac();
        
        HashMap<String, Integer> mapUuTienGiai = new HashMap<>();
        for (nguyenVongXetTuyenETT nv : ds) {
            if (nv.getTtPhuongThuc().equals("Xét tuyển thẳng")) {
                String[] ttGiai = gtBus.layCapVaLoaiGiai(nv.getNnCccd());
                Entity.quyTacGiaiThuongETT quyTac = qtBus.layQuyTac(ttGiai[0], ttGiai[1]);
                if (quyTac != null) {
                    mapUuTienGiai.put(nv.getNnCccd(), quyTac.getDoUuTien());
                }
            }
        }
        
        // BƯỚC 2: GOM NHÓM NGUYỆN VỌNG THEO TỪNG HỌC SINH
//        HashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new HashMap<>();
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new java.util.LinkedHashMap<>();
        for (nguyenVongXetTuyenETT nv : ds) {
            String cccd = nv.getNnCccd();
            if (!mapHocSinh.containsKey(cccd)) {
                mapHocSinh.put(cccd, new ArrayList<>());
            }
            mapHocSinh.get(cccd).add(nv);
        }

        // Sắp xếp các nguyện vọng của mỗi đứa theo thứ tự tăng dần (NV1, NV2, NV3...)
        for (ArrayList<nguyenVongXetTuyenETT> listNV : mapHocSinh.values()) {
            listNV.sort(java.util.Comparator.comparingInt(nguyenVongXetTuyenETT::getNvTt));
        }

        // Tạo 1 con trỏ cho mỗi học sinh, ban đầu ai cũng trỏ vào NV đầu tiên (index = 0)
        HashMap<String, Integer> conTroNV = new HashMap<>();
        for (String cccd : mapHocSinh.keySet()) {
            conTroNV.put(cccd, 0);
        }

        // BƯỚC 3: VÒNG LẶP DOMINO (CHÉM DÂY CHUYỀN)
        boolean coSuThayDoi = true;
//        HashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapRoXetTuyen = new HashMap<>();
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapRoXetTuyen = new java.util.LinkedHashMap<>();

        // Vòng lặp sẽ chạy liên tục cho đến khi không còn ai bị rớt nữa mới dừng
        while (coSuThayDoi) {
            coSuThayDoi = false; // Mặc định là không có ai rớt
            mapRoXetTuyen.clear(); // Làm rỗng rổ để xếp lại từ đầu

            // 3.1. Nhặt từng học sinh bỏ vào Rổ Ngành theo cái NV mà nó đang trỏ tới
            for (String cccd : mapHocSinh.keySet()) {
                int indexNV = conTroNV.get(cccd);
                ArrayList<nguyenVongXetTuyenETT> listNV = mapHocSinh.get(cccd);

                if (indexNV < listNV.size()) { // Nếu học sinh vẫn còn NV để xét
                    nguyenVongXetTuyenETT nvHienTai = listNV.get(indexNV);
                    String keyRo = nvHienTai.getNvMaNganh() + "_" + nvHienTai.getTtPhuongThuc();

                    if (!mapRoXetTuyen.containsKey(keyRo)) {
                        mapRoXetTuyen.put(keyRo, new ArrayList<>());
                    }
                    mapRoXetTuyen.get(keyRo).add(nvHienTai);
                }
            }

            // 3.2. Đi từng Rổ kiểm tra xem có bị lố chỉ tiêu không
            for (String keyRo : mapRoXetTuyen.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> roHienTai = mapRoXetTuyen.get(keyRo);
                
                // Lấy Mã ngành hiện tại từ keyRo (Ví dụ "7480201_Xét THPT" -> Lấy "7480201")
                String maNganhHienTai = keyRo.split("_")[0];
                String toHopGoc = mapToHopGoc.getOrDefault(maNganhHienTai, "");

                roHienTai.sort((nv1, nv2) -> {
                    // Ưu tiên 1: Điểm Xét Tuyển (Cao xuống Thấp)
                    int diemCompare = Double.compare(nv2.getDiemXetTuyen(), nv1.getDiemXetTuyen());
                    if (diemCompare != 0) return diemCompare;
                    
                    // ============================================================
                    // 🔥 Ưu tiên 1.5 - ĐỘ ƯU TIÊN GIẢI THƯỞNG 🔥
                    // ============================================================
                    int uuTien1 = mapUuTienGiai.getOrDefault(nv1.getNnCccd(), 0);
                    int uuTien2 = mapUuTienGiai.getOrDefault(nv2.getNnCccd(), 0);
                    int uuTienCompare = Integer.compare(uuTien2, uuTien1); // nv2 so với nv1 để xếp Giảm Dần
                    if (uuTienCompare != 0) return uuTienCompare;
                    
                    // Ưu tiên 2: TỔ HỢP GỐC
                    boolean isNv1Goc = nv1.getTtThm() != null && nv1.getTtThm().equals(toHopGoc);
                    boolean isNv2Goc = nv2.getTtThm() != null && nv2.getTtThm().equals(toHopGoc);
                    if (isNv1Goc && !isNv2Goc) return -1; 
                    if (!isNv1Goc && isNv2Goc) return 1;  

                    // Ưu tiên 3: Điểm Môn 1 (Toán) 
                    int diemToanCompare = Double.compare(nv2.getDiemMon1(), nv1.getDiemMon1());
                    if (diemToanCompare != 0) return diemToanCompare;

                    // Ưu tiên 4: Thứ tự nguyện vọng 
                    int nvTtCompare = Integer.compare(nv1.getNvTt(), nv2.getNvTt());
                    if (nvTtCompare != 0) return nvTtCompare;

                    // =========================================================
                    // 🔥 MỚI: ƯU TIÊN 5 (CHỐT HẠ): AI NỘP TRƯỚC ĐẬU TRƯỚC 
                    // Dựa vào ID Nguyện Vọng (Khóa chính trong DB). ID nhỏ hơn -> Xếp lên trên.
                    // (BOSS LƯU Ý: Nếu Entity của ông đặt tên hàm là getId() hay getMaNv() thì đổi lại cho đúng chỗ này nha)
                    // =========================================================
                    return Integer.compare(nv1.getIdNv(), nv2.getIdNv()); 
                });

                int chiTieu = mapChiTieu.getOrDefault(keyRo, 0);

                if (chiTieu == 0) {
                    // Nếu ngành/phương thức đó không có chỉ tiêu (0) -> Rớt sạch
                    for (int i = 0; i < roHienTai.size(); i++) {
                        nguyenVongXetTuyenETT nvBiTruot = roHienTai.get(i);
                        String cccdBiTruot = nvBiTruot.getNnCccd();
                        conTroNV.put(cccdBiTruot, conTroNV.get(cccdBiTruot) + 1);
                        coSuThayDoi = true; 
                    }
                    roHienTai.clear();
                } 
                else if (roHienTai.size() > chiTieu) {
                    // CẮT CHỈ TIÊU & NỚI RỔ
                    int diemCatThucTe = chiTieu;
                    nguyenVongXetTuyenETT nguoiCuoiCungDau = roHienTai.get(chiTieu - 1); 
                    
                    // NỚI RỔ: Cập nhật điều kiện đồng điểm (Thêm vụ giống nhau về Tổ hợp gốc)
                    boolean isNguoiCuoiGoc = nguoiCuoiCungDau.getTtThm() != null && nguoiCuoiCungDau.getTtThm().equals(toHopGoc);
                    
                    while (diemCatThucTe < roHienTai.size()) {
                        nguyenVongXetTuyenETT nguoiTiepTheo = roHienTai.get(diemCatThucTe);
                        boolean isNguoiTiepTheoGoc = nguoiTiepTheo.getTtThm() != null && nguoiTiepTheo.getTtThm().equals(toHopGoc);
                        
                        // 🔥 MỚI: Lấy độ ưu tiên của 2 đứa ra để so sánh
                        int uuTienNguoiCuoi = mapUuTienGiai.getOrDefault(nguoiCuoiCungDau.getNnCccd(), 0);
                        int uuTienNguoiTiepTheo = mapUuTienGiai.getOrDefault(nguoiTiepTheo.getNnCccd(), 0);
                        
                        // ĐỒNG ĐIỂM HOÀN TOÀN TỪ ƯU TIÊN 1 TỚI ƯU TIÊN 4 (Thêm check uuTienNguoiTiepTheo == uuTienNguoiCuoi)
                        if (nguoiTiepTheo.getDiemXetTuyen() == nguoiCuoiCungDau.getDiemXetTuyen() &&
                            uuTienNguoiTiepTheo == uuTienNguoiCuoi && // 🚀 CHỐT CHẶN TIÊU CHÍ PHỤ CỦA GIẢI THƯỞNG
                            isNguoiTiepTheoGoc == isNguoiCuoiGoc && 
                            nguoiTiepTheo.getDiemMon1() == nguoiCuoiCungDau.getDiemMon1() &&
                            nguoiTiepTheo.getNvTt() == nguoiCuoiCungDau.getNvTt()) {
                            
                            diemCatThucTe++; // Cứu nó!
                        } else {
                            break; 
                        }
                    }

                    // Bắt đầu chém từ cái vạch cắt thực tế (diemCatThucTe)
                    for (int i = diemCatThucTe; i < roHienTai.size(); i++) {
                        nguyenVongXetTuyenETT nvBiTruot = roHienTai.get(i);
                        String cccdBiTruot = nvBiTruot.getNnCccd();
                        
                        // Đứa bị trượt phải lùi con trỏ xuống NV tiếp theo của nó
                        conTroNV.put(cccdBiTruot, conTroNV.get(cccdBiTruot) + 1);
                        
                        // KÍCH HOẠT DOMINO: Bắt buộc phải xếp rổ lại 1 lần nữa!
                        coSuThayDoi = true; 
                    }
                    
                    // Quét xác tụi bị chém ra khỏi rổ
                    roHienTai.subList(diemCatThucTe, roHienTai.size()).clear();
                }
            }
        }

        // BƯỚC 4: LỌC ẢO XONG - BẮT ĐẦU CHỐT KẾT QUẢ XUỐNG BẢNG ĐIỂM
        for (String cccd : mapHocSinh.keySet()) {
            int indexDau = conTroNV.get(cccd); // NV cuối cùng mà nó dừng lại
            ArrayList<nguyenVongXetTuyenETT> listNV = mapHocSinh.get(cccd);

            for (int i = 0; i < listNV.size(); i++) {
                nguyenVongXetTuyenETT nv = listNV.get(i);
                if (i < indexDau) {
                    // Những NV nằm TRƯỚC NV nó đang dừng -> Bị chém rớt rồi
                    nv.setNvKetQua("Đã trượt");
                } else if (i == indexDau && indexDau < listNV.size()) {
                    // NV nó ĐANG DỪNG LẠI và trụ vững -> Đã đậu
                    nv.setNvKetQua("Đã đậu");
                } else if (i > indexDau) {
                    // Những NV nằm SAU NV đã đậu -> Không thèm xét nữa
                    nv.setNvKetQua("Không xét");
                } else if (indexDau == listNV.size()) {
                    // Đứa xui xẻo rớt hết toàn bộ các NV của nó
                    nv.setNvKetQua("Đã trượt");
                }
                
                // Lưu phán quyết xuống Database
//                data.suaNguyenVong(nv);
            }
        }
        // 🔥 BƯỚC 5 (MỚI): GỌI DAO LƯU MỘT CỤC (BATCH UPDATE) 🔥
        if (data.capNhatKetQuaHangLoat(ds)) {
            System.out.println("CẮT CHỈ TIÊU & LƯU DATABASE HOÀN TẤT!");
        } else {
            System.out.println("LỖI LƯU DATABASE KHI CẮT CHỈ TIÊU!");
        }
        System.out.println("CẮT CHỈ TIÊU HOÀN TẤT!");
    }
    
    
    
    public void capNhatDiemChuanTuDong() {
        if (ds == null || ds.isEmpty()) return;

        // 1. TẠO MAP CHỨA DATA LỌC (LOGIC TẦNG BUS)
        HashMap<String, Double> diemChuanMap = new HashMap<>();

        // Quét qua để tìm "Kẻ thủ khoa ngược" (Đứa đậu với điểm thấp nhất)
        for (nguyenVongXetTuyenETT nv : ds) {
            if ("Đã đậu".equals(nv.getNvKetQua())) {
                String key = nv.getNvMaNganh() + "_" + nv.getTtPhuongThuc();
                double diem = nv.getDiemXetTuyen();

                if (!diemChuanMap.containsKey(key) || diem < diemChuanMap.get(key)) {
                    diemChuanMap.put(key, diem);
                }
            }
        }

        // 2. GIAO VIỆC CHO DAO XUỐNG DATABASE XỬ LÝ (TÁCH BẠCH)
        // Gọi nganhDAO vì mình đang update vào bảng Ngành
        nganhDAO nDao = new nganhDAO(); 
        
        if (nDao.capNhatDanhSachDiemChuan(diemChuanMap)) {
            System.out.println("Đã chốt điểm chuẩn tự động thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi chốt điểm chuẩn!");
        }
    }
    
    private double parseDoubleSafe(String val) {
        try {
            if (val == null || val.trim().isEmpty()) return 0.0;
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    // =====================================================================
    // HÀM IMPORT CHÍNH THỨC ĐÃ FIX LỖI ĐIỂM SỐ 0.0
    // =====================================================================
    public String nhapDuLieuTuExcel(String filePath) {
        try {
            // 1. Lấy ma trận dữ liệu từ Excel
            ArrayList<ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;
            nguyenVongXetTuyenDAO dao = new nguyenVongXetTuyenDAO();

            for (int i = 1; i < data.size(); i++) {
                ArrayList<String> row = data.get(i);
                
                try {
                    nguyenVongXetTuyenETT nv = new nguyenVongXetTuyenETT();
                    
                    nv.setNnCccd(row.get(1));              // Cột B (Index 1): CCCD
                    nv.setNvMaNganh(row.get(2));           // Cột C (Index 2): Mã Ngành
                    
                    // Ép kiểu Thứ tự an toàn (Xử lý luôn vụ Excel tự chèn số thực kiểu "1.0")
                    nv.setNvTt((int) parseDoubleSafe(row.get(3))); // Cột D (Index 3): Thứ Tự NV
                    
                    // --- ĐỌC TRỰC TIẾP ĐIỂM SỐ TỪ FILE EXCEL (KHÔNG GÁN MÙ QUÁNG NỮA) ---
                    nv.setDiemThxt(parseDoubleSafe(row.get(4)));   // Cột E (Index 4): Điểm THXT
                    nv.setDiemUtqd(parseDoubleSafe(row.get(5)));   // Cột F (Index 5): Điểm Ưu tiên (Khu vực)
                    nv.setDiemCong(parseDoubleSafe(row.get(6)));   // Cột G (Index 6): Điểm Cộng (Giải/IELTS)
                    nv.setDiemXetTuyen(parseDoubleSafe(row.get(7))); // Cột H (Index 7): Tổng Điểm
                    
                    String ketQua = row.get(8);
                    nv.setNvKetQua(ketQua != null && !ketQua.isEmpty() ? ketQua : "Chờ xét"); // Cột I (Index 8)
                    
                    nv.setTtPhuongThuc(row.get(10));       // Cột K (Index 10): Phương thức
                    nv.setTtThm(row.get(11));              // Cột L (Index 11): Tổ hợp
                    
                    // Tạo key ẩn
                    nv.setNvKeys(nv.getNnCccd() + "_" + nv.getNvTt());

                    // 4. Đẩy xuống DAO để Thêm vào Database (Nhớ sửa tên hàm cho khớp DAO của ông nha)
                    if (dao.saveNguyenVong(nv)) { 
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    soDongThatBai++; // Lỗi ép kiểu hoặc thiếu dữ liệu thì tính là thất bại dòng đó
                }
            }
            return "Nhập thành công: " + soDongThanhCong + " dòng.\nLỗi/Trùng lặp: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
    
    public ArrayList<nguyenVongXetTuyenETT> timKiemCoBan(String tim, int index)
    {
        if(ds == null)
        {
            layDanhSach();
        }
        
        ArrayList<nguyenVongXetTuyenETT> dskq = new ArrayList<nguyenVongXetTuyenETT>();
        String tuKhoa = tim.trim().toLowerCase();
        
        for(nguyenVongXetTuyenETT ct : ds)
        {
            switch (index) {
                case 0:
                    if(String.valueOf(ct.getIdNv()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 1:
                    if(ct.getNnCccd() != null && ct.getNnCccd().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 2:
                    if(ct.getNvMaNganh() != null && ct.getNvMaNganh().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 3:
                    if(String.valueOf(ct.getNvTt()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 4:
                    if(String.valueOf(ct.getDiemThxt()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 5:
                    if(String.valueOf(ct.getDiemUtqd()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 6:
                    if(String.valueOf(ct.getDiemCong()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 7:
                    if(String.valueOf(ct.getDiemXetTuyen()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 8:
                    if(ct.getNvKetQua() != null && ct.getNvKetQua().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 9:
                    if(ct.getTtPhuongThuc() != null && ct.getTtPhuongThuc().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 10:
                    if(ct.getTtThm() != null && ct.getTtThm().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return dskq;
    }
}
