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
    
    public void sapXepKetQuaTheoChiTieu() {
        if(ds == null || ds.isEmpty()) {
            layDanhSach();
        }

        nganhBUS busNganh = new nganhBUS();
        ArrayList<Entity.nganhETT> dsNganh = busNganh.layDanhSach();
        HashMap<String, String> mapToHopGoc = new HashMap<>();

        // ====================================================================
        // BƯỚC 1: LẤY CHỈ TIÊU TỔNG CỦA NGÀNH (KHÔNG PHÂN BIỆT PHƯƠNG THỨC NỮA)
        // ====================================================================
        HashMap<String, Integer> mapChiTieuNganh = new HashMap<>();
        for (Entity.nganhETT nganh : dsNganh) {
            // Gom hết chỉ tiêu của 4 phương thức lại thành 1 cục TỔNG CHỈ TIÊU cho ngành đó
            int tongChiTieu = (nganh.getSl_thpt() != null ? nganh.getSl_thpt() : 0) +
                              (nganh.getSl_dgnl() != null ? nganh.getSl_dgnl() : 0) +
                              (nganh.getSl_vsat() != null ? nganh.getSl_vsat() : 0) +
                              (nganh.getSl_xtt() != null ? nganh.getSl_xtt() : 0);
                              
            mapChiTieuNganh.put(nganh.getManganh(), tongChiTieu);
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
        
        // ====================================================================
        // BƯỚC 2: GOM NHÓM HỌC SINH VÀ LỌC LẤY PHƯƠNG THỨC "HOA HẬU" CỦA MỖI NV
        // ====================================================================
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new java.util.LinkedHashMap<>();
        
        for (nguyenVongXetTuyenETT nv : ds) {
            String cccd = nv.getNnCccd();
            if (!mapHocSinh.containsKey(cccd)) {
                mapHocSinh.put(cccd, new ArrayList<>());
            }
            mapHocSinh.get(cccd).add(nv);
        }

        // Tối ưu hóa: 1 NV (vd NV1) có thể có 3 phương thức. Chọn ra phương thức điểm cao nhất đại diện đi thi đấu.
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh_DaLoc = new java.util.LinkedHashMap<>();
        
        for (String cccd : mapHocSinh.keySet()) {
            ArrayList<nguyenVongXetTuyenETT> listGoc = mapHocSinh.get(cccd);
            
            // Chia list theo Số thứ tự NV
            HashMap<Integer, ArrayList<nguyenVongXetTuyenETT>> mapTheoThuTu = new HashMap<>();
            for (nguyenVongXetTuyenETT nv : listGoc) {
                if (!mapTheoThuTu.containsKey(nv.getNvTt())) {
                    mapTheoThuTu.put(nv.getNvTt(), new ArrayList<>());
                }
                mapTheoThuTu.get(nv.getNvTt()).add(nv);
            }
            
            ArrayList<nguyenVongXetTuyenETT> listDaLoc = new ArrayList<>();
            
            // Với mỗi Số thứ tự NV (1, 2, 3...), tìm ra 1 thằng điểm cao nhất làm đại diện
            for (Integer stt : mapTheoThuTu.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> cacPhuongThucCua1NV = mapTheoThuTu.get(stt);
                
                // Mặc định thằng đầu tiên là tạm giữ chức Hoa hậu
                nguyenVongXetTuyenETT nvHoaHau = cacPhuongThucCua1NV.get(0); 
                
                for (nguyenVongXetTuyenETT pt : cacPhuongThucCua1NV) {
                    if (pt.getDiemXetTuyen() > nvHoaHau.getDiemXetTuyen()) {
                        nvHoaHau = pt;
                    }
                    // Đánh dấu "Rớt nội bộ" cho tất cả các thằng trước khi thi đấu vòng ngoài
                    pt.setNvKetQua("Bỏ qua"); 
                }
                
                // Thằng Hoa hậu được phục hồi trạng thái để lát nữa đi thi đấu
                nvHoaHau.setNvKetQua("Chờ xét");
                listDaLoc.add(nvHoaHau);
            }
            
            // Sắp xếp các NV đại diện tăng dần (NV1, NV2...)
            listDaLoc.sort(java.util.Comparator.comparingInt(nguyenVongXetTuyenETT::getNvTt));
            mapHocSinh_DaLoc.put(cccd, listDaLoc);
        }

        // Tạo 1 con trỏ cho mỗi học sinh, ban đầu ai cũng trỏ vào NV ĐẠI DIỆN đầu tiên (index = 0)
        HashMap<String, Integer> conTroNV = new HashMap<>();
        for (String cccd : mapHocSinh_DaLoc.keySet()) {
            conTroNV.put(cccd, 0);
        }

        // ====================================================================
        // BƯỚC 3: VÒNG LẶP DOMINO (ĐẤU TRƯỜNG CHUNG CHO TOÀN NGÀNH)
        // ====================================================================
        boolean coSuThayDoi = true;
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapRoXetTuyen = new java.util.LinkedHashMap<>();

        while (coSuThayDoi) {
            coSuThayDoi = false; 
            mapRoXetTuyen.clear(); 

            // 3.1. Nhặt học sinh bỏ vào Rổ NGÀNH (Không quan tâm phương thức nữa)
            for (String cccd : mapHocSinh_DaLoc.keySet()) {
                int indexNV = conTroNV.get(cccd);
                ArrayList<nguyenVongXetTuyenETT> listNV_DaiDien = mapHocSinh_DaLoc.get(cccd);

                if (indexNV < listNV_DaiDien.size()) { 
                    nguyenVongXetTuyenETT nvHienTai = listNV_DaiDien.get(indexNV);
                    // Rổ bây giờ chỉ có Key là Mã Ngành (Ví dụ: "7480201")
                    String keyRo_Nganh = nvHienTai.getNvMaNganh(); 

                    if (!mapRoXetTuyen.containsKey(keyRo_Nganh)) {
                        mapRoXetTuyen.put(keyRo_Nganh, new ArrayList<>());
                    }
                    mapRoXetTuyen.get(keyRo_Nganh).add(nvHienTai);
                }
            }

            // 3.2. Cắt chỉ tiêu trên Rổ NGÀNH
            for (String maNganhHienTai : mapRoXetTuyen.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> roHienTai = mapRoXetTuyen.get(maNganhHienTai);
                String toHopGoc = mapToHopGoc.getOrDefault(maNganhHienTai, "");

                roHienTai.sort((nv1, nv2) -> {
                    // Ưu tiên 1: Điểm Xét Tuyển 
                    int diemCompare = Double.compare(nv2.getDiemXetTuyen(), nv1.getDiemXetTuyen());
                    if (diemCompare != 0) return diemCompare;
                    
                    // Ưu tiên 1.5 - ĐỘ ƯU TIÊN GIẢI THƯỞNG 
                    int uuTien1 = mapUuTienGiai.getOrDefault(nv1.getNnCccd(), 0);
                    int uuTien2 = mapUuTienGiai.getOrDefault(nv2.getNnCccd(), 0);
                    int uuTienCompare = Integer.compare(uuTien2, uuTien1); 
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

                    return Integer.compare(nv1.getIdNv(), nv2.getIdNv()); 
                });

                // Lấy Tổng chỉ tiêu của Ngành
                int chiTieu = mapChiTieuNganh.getOrDefault(maNganhHienTai, 0);

                if (chiTieu == 0) {
                    for (int i = 0; i < roHienTai.size(); i++) {
                        nguyenVongXetTuyenETT nvBiTruot = roHienTai.get(i);
                        String cccdBiTruot = nvBiTruot.getNnCccd();
                        conTroNV.put(cccdBiTruot, conTroNV.get(cccdBiTruot) + 1);
                        coSuThayDoi = true; 
                    }
                    roHienTai.clear();
                } 
                else if (roHienTai.size() > chiTieu) {
                    int diemCatThucTe = chiTieu;
                    nguyenVongXetTuyenETT nguoiCuoiCungDau = roHienTai.get(chiTieu - 1); 
                    
                    boolean isNguoiCuoiGoc = nguoiCuoiCungDau.getTtThm() != null && nguoiCuoiCungDau.getTtThm().equals(toHopGoc);
                    
                    while (diemCatThucTe < roHienTai.size()) {
                        nguyenVongXetTuyenETT nguoiTiepTheo = roHienTai.get(diemCatThucTe);
                        boolean isNguoiTiepTheoGoc = nguoiTiepTheo.getTtThm() != null && nguoiTiepTheo.getTtThm().equals(toHopGoc);
                        
                        int uuTienNguoiCuoi = mapUuTienGiai.getOrDefault(nguoiCuoiCungDau.getNnCccd(), 0);
                        int uuTienNguoiTiepTheo = mapUuTienGiai.getOrDefault(nguoiTiepTheo.getNnCccd(), 0);
                        
                        if (nguoiTiepTheo.getDiemXetTuyen() == nguoiCuoiCungDau.getDiemXetTuyen() &&
                            uuTienNguoiTiepTheo == uuTienNguoiCuoi && 
                            isNguoiTiepTheoGoc == isNguoiCuoiGoc && 
                            nguoiTiepTheo.getDiemMon1() == nguoiCuoiCungDau.getDiemMon1() &&
                            nguoiTiepTheo.getNvTt() == nguoiCuoiCungDau.getNvTt()) {
                            
                            diemCatThucTe++; 
                        } else {
                            break; 
                        }
                    }

                    // Chém từ vạch cắt
                    for (int i = diemCatThucTe; i < roHienTai.size(); i++) {
                        nguyenVongXetTuyenETT nvBiTruot = roHienTai.get(i);
                        String cccdBiTruot = nvBiTruot.getNnCccd();
                        
                        conTroNV.put(cccdBiTruot, conTroNV.get(cccdBiTruot) + 1);
                        coSuThayDoi = true; 
                    }
                    
                    roHienTai.subList(diemCatThucTe, roHienTai.size()).clear();
                }
            }
        }

        // ====================================================================
        // BƯỚC 4: LỌC ẢO XONG - BẮT ĐẦU CHỐT KẾT QUẢ VÀO DANH SÁCH GỐC
        // ====================================================================
        for (String cccd : mapHocSinh_DaLoc.keySet()) {
            int indexDau = conTroNV.get(cccd); 
            ArrayList<nguyenVongXetTuyenETT> listNV_DaiDien = mapHocSinh_DaLoc.get(cccd);

            for (int i = 0; i < listNV_DaiDien.size(); i++) {
                nguyenVongXetTuyenETT nvHoaHau = listNV_DaiDien.get(i);
                
                if (i < indexDau) {
                    nvHoaHau.setNvKetQua("Đã trượt");
                } else if (i == indexDau && indexDau < listNV_DaiDien.size()) {
                    nvHoaHau.setNvKetQua("Đã đậu");
                } else if (i > indexDau) {
                    nvHoaHau.setNvKetQua("Không xét");
                } else if (indexDau == listNV_DaiDien.size()) {
                    nvHoaHau.setNvKetQua("Đã trượt");
                }
            }
        }

        // BƯỚC 5: GỌI DAO LƯU MỘT CỤC TOÀN BỘ DANH SÁCH GỐC (Bao gồm cả đứa Hoa hậu và đứa Rớt nội bộ)
        if (data.capNhatKetQuaHangLoat(ds)) {
            System.out.println("CẮT CHỈ TIÊU & LƯU DATABASE HOÀN TẤT!");
        } else {
            System.out.println("LỖI LƯU DATABASE KHI CẮT CHỈ TIÊU!");
        }
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
    
    public int phatSinhThuTuNguyenVongMoi(String cccd) 
    {
        if (cccd == null || cccd.isEmpty()) {
            return 1;
        }
        nguyenVongXetTuyenDAO dao = new nguyenVongXetTuyenDAO();
        int maxHienTai = dao.layThuTuNguyenVongLonNhat(cccd);
        return maxHienTai + 1;
    }
    
    // =========================================================
    // 🔥 BUS: GỌI DAO KIỂM TRA TRÙNG NGUYỆN VỌNG
    // =========================================================
    public boolean kiemTraTrungNganh(String cccd, String maNganh) {
        // Kiểm tra an toàn, nếu rỗng thì bỏ qua
        if (cccd == null || cccd.trim().isEmpty() || maNganh == null || maNganh.trim().isEmpty()) {
            return false;
        }
        
        // Gọi DAO (Nhớ check lại tên class DAO nha)
        DAO.nguyenVongXetTuyenDAO dao = new DAO.nguyenVongXetTuyenDAO();
        return dao.kiemTraTrungNganh(cccd, maNganh);
    }
}
