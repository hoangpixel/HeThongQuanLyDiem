/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
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
  
    public void sapXepKetQuaTheoChiTieu() {
        if(ds == null || ds.isEmpty()) {
            layDanhSach();
        }

        nganhBUS busNganh = new nganhBUS();
        ArrayList<Entity.nganhETT> dsNganh = busNganh.layDanhSach();
        HashMap<String, String> mapToHopGoc = new HashMap<>();

        // ====================================================================
        // BƯỚC 1: LẤY CHỈ TIÊU TỔNG CỦA NGÀNH (CHỈ DÙNG 1 CỘT n_chitieu DUY NHẤT)
        // ====================================================================
        HashMap<String, Integer> mapChiTieuNganh = new HashMap<>();
        for (Entity.nganhETT nganh : dsNganh) {
            // 🚀 ĐÃ SỬA: Lấy trực tiếp từ cột n_chitieu, bỏ qua mấy cột chia phần trăm
            int tongChiTieu = nganh.getN_chitieu() != null ? nganh.getN_chitieu() : 0;
                              
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

        // Tối ưu hóa: 1 NV có thể có nhiều phương thức. Chọn phương thức điểm cao nhất đại diện.
        LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh_DaLoc = new java.util.LinkedHashMap<>();
        
        for (String cccd : mapHocSinh.keySet()) {
            ArrayList<nguyenVongXetTuyenETT> listGoc = mapHocSinh.get(cccd);
            
            HashMap<Integer, ArrayList<nguyenVongXetTuyenETT>> mapTheoThuTu = new HashMap<>();
            for (nguyenVongXetTuyenETT nv : listGoc) {
                if (!mapTheoThuTu.containsKey(nv.getNvTt())) {
                    mapTheoThuTu.put(nv.getNvTt(), new ArrayList<>());
                }
                mapTheoThuTu.get(nv.getNvTt()).add(nv);
            }
            
            ArrayList<nguyenVongXetTuyenETT> listDaLoc = new ArrayList<>();
            
            for (Integer stt : mapTheoThuTu.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> cacPhuongThucCua1NV = mapTheoThuTu.get(stt);
                
                nguyenVongXetTuyenETT nvHoaHau = cacPhuongThucCua1NV.get(0); 
                
                for (nguyenVongXetTuyenETT pt : cacPhuongThucCua1NV) {
                    if (pt.getDiemXetTuyen() > nvHoaHau.getDiemXetTuyen()) {
                        nvHoaHau = pt;
                    }
                    pt.setNvKetQua("Bỏ qua"); 
                }
                
                nvHoaHau.setNvKetQua("Chờ xét");
                listDaLoc.add(nvHoaHau);
            }
            
            listDaLoc.sort(java.util.Comparator.comparingInt(nguyenVongXetTuyenETT::getNvTt));
            mapHocSinh_DaLoc.put(cccd, listDaLoc);
        }

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

            // 3.1. Nhặt học sinh bỏ vào Rổ NGÀNH
            for (String cccd : mapHocSinh_DaLoc.keySet()) {
                int indexNV = conTroNV.get(cccd);
                ArrayList<nguyenVongXetTuyenETT> listNV_DaiDien = mapHocSinh_DaLoc.get(cccd);

                if (indexNV < listNV_DaiDien.size()) { 
                    nguyenVongXetTuyenETT nvHienTai = listNV_DaiDien.get(indexNV);
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
                    int diemCompare = Double.compare(nv2.getDiemXetTuyen(), nv1.getDiemXetTuyen());
                    if (diemCompare != 0) return diemCompare;
                    
                    int uuTien1 = mapUuTienGiai.getOrDefault(nv1.getNnCccd(), 0);
                    int uuTien2 = mapUuTienGiai.getOrDefault(nv2.getNnCccd(), 0);
                    int uuTienCompare = Integer.compare(uuTien2, uuTien1); 
                    if (uuTienCompare != 0) return uuTienCompare;
                    
                    boolean isNv1Goc = nv1.getTtThm() != null && nv1.getTtThm().equals(toHopGoc);
                    boolean isNv2Goc = nv2.getTtThm() != null && nv2.getTtThm().equals(toHopGoc);
                    if (isNv1Goc && !isNv2Goc) return -1; 
                    if (!isNv1Goc && isNv2Goc) return 1;  

                    int diemToanCompare = Double.compare(nv2.getDiemMon1(), nv1.getDiemMon1());
                    if (diemToanCompare != 0) return diemToanCompare;

                    int nvTtCompare = Integer.compare(nv1.getNvTt(), nv2.getNvTt());
                    if (nvTtCompare != 0) return nvTtCompare;

                    return Integer.compare(nv1.getIdNv(), nv2.getIdNv()); 
                });

                // Lấy Tổng chỉ tiêu của Ngành (n_chitieu)
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

        // BƯỚC 5: LƯU DATABASE
        if (data.capNhatKetQuaHangLoat(ds)) {
            System.out.println("CẮT CHỈ TIÊU & LƯU DATABASE HOÀN TẤT!");
        } else {
            System.out.println("LỖI LƯU DATABASE KHI CẮT CHỈ TIÊU!");
        }
    }
    
    public void capNhatDiemChuanTuDong() {
        if (ds == null || ds.isEmpty()) return;

        // 1. TẠO MAP CHỨA DATA LỌC
        // Map 1: Lưu Điểm Chuẩn (Key: Mã Ngành + Phương Thức)
        HashMap<String, Double> diemChuanMap = new HashMap<>();
        
        // Map 2: Lưu Số lượng Thí sinh Đăng ký (Key: Mã Ngành)
        // ⚠️ Lưu ý: Vì 1 thí sinh có thể có nhiều phương thức, mình dùng HashSet chứa CCCD để KHÔNG ĐẾM TRÙNG 1 thằng 2 lần.
        HashMap<String, java.util.HashSet<String>> mapSoLuongNganh = new HashMap<>();

        // 2. QUÉT QUA DANH SÁCH ĐỂ THU THẬP DỮ LIỆU
        for (nguyenVongXetTuyenETT nv : ds) {
            String maNganh = nv.getNvMaNganh();
            String cccd = nv.getNnCccd();
            String pt = nv.getTtPhuongThuc();
            String ketQua = nv.getNvKetQua();

            // --- A. GHI NHẬN ĐIỂM CHUẨN (Chỉ lấy đứa "Đã đậu") ---
            if ("Đã đậu".equals(ketQua)) {
                String keyDiem = maNganh + "_" + pt;
                double diem = nv.getDiemXetTuyen();

                if (!diemChuanMap.containsKey(keyDiem) || diem < diemChuanMap.get(keyDiem)) {
                    diemChuanMap.put(keyDiem, diem);
                }
            }

            // --- B. ĐẾM SỐ LƯỢNG ĐĂNG KÝ ---
            // Bỏ qua những dòng đã bị hệ thống đánh dấu là "Bỏ qua" (Những dòng rớt nội bộ do thi Hoa hậu)
            // Chỉ đếm những dòng chính thức tham gia xét tuyển (Đã đậu, Đã trượt, Chờ xét, Không xét)
            if (!"Bỏ qua".equals(ketQua)) {
                if (!mapSoLuongNganh.containsKey(maNganh)) {
                    mapSoLuongNganh.put(maNganh, new java.util.HashSet<>());
                }
                // Thêm CCCD vào HashSet, vì HashSet không chứa trùng lặp nên 1 CCCD có xuất hiện 10 lần cũng chỉ đếm 1.
                mapSoLuongNganh.get(maNganh).add(cccd);
            }
        }

        // 3. ĐÓNG GÓI SỐ LƯỢNG ĐĂNG KÝ TỪ HASHSET SANG INTEGER ĐỂ TRUYỀN XUỐNG DAO
        HashMap<String, Integer> slDangKyMap = new HashMap<>();
        for (String maNganh : mapSoLuongNganh.keySet()) {
            slDangKyMap.put(maNganh, mapSoLuongNganh.get(maNganh).size());
        }

        // 4. GIAO VIỆC CHO DAO XUỐNG DATABASE XỬ LÝ 
        DAO.nganhDAO nDao = new DAO.nganhDAO(); 
        
        // Gọi DAO cập nhật ĐỒNG THỜI Điểm chuẩn và Số lượng đăng ký
        if (nDao.capNhatDanhSachDiemChuanVaSoLuong(diemChuanMap, slDangKyMap)) {
            System.out.println("Đã chốt điểm chuẩn & Cập nhật tổng số đăng ký thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi chốt kết quả vào bảng Ngành!");
        }
    }
    

public String nhapDuLieuTuExcel(String filePath) {
        try {
            // 1. Lấy ma trận dữ liệu từ Excel
            ArrayList<ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            // Chặn ngay từ cửa nếu file trống hoặc không đọc được
            if (data == null || data.isEmpty() || data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;
            DAO.nguyenVongXetTuyenDAO dao = new DAO.nguyenVongXetTuyenDAO(); // ⚠️ Nhớ thêm DAO. vào trước cho chắc cú

            // Duyệt từ dòng 1 (bỏ qua dòng 0 là tiêu đề)
            for (int i = 1; i < data.size(); i++) {
                ArrayList<String> row = data.get(i);
                
                // 🛑 CHỐT CHẶN 1: Nếu dòng Excel bị rỗng hoàn toàn (do lỡ tay format ở cuối file)
                if (row == null || row.isEmpty()) {
                    continue; // Bỏ qua không tính là thất bại
                }
                
                try {
                    // 🛑 CHỐT CHẶN 2: File chuẩn của mình có 13 cột (0 đến 12). 
                    // Nếu dòng nào ít hơn 13 cột thì chắc chắn là bị thiếu dữ liệu, cho rớt luôn!
                    if (row.size() < 13) {
                        System.out.println("⚠️ Dòng " + (i+1) + " bị thiếu cột (chỉ có " + row.size() + " cột). Bỏ qua!");
                        soDongThatBai++;
                        continue; 
                    }

                    nguyenVongXetTuyenETT nv = new nguyenVongXetTuyenETT();
                    
                    // --- GÁN DỮ LIỆU --- (Nhớ check lại số thứ tự cột Excel thực tế nha Boss)
                    nv.setNnCccd(row.get(1));              // Cột B: CCCD
                    nv.setNvMaNganh(row.get(2));           // Cột C: Mã Ngành
                    nv.setNvTt((int) parseDoubleSafe(row.get(3))); // Cột D: Thứ Tự NV
                    
                    nv.setDiemThxt(parseDoubleSafe(row.get(4)));   // Cột E: Điểm THXT
                    nv.setDiemUtqdGoc(parseDoubleSafe(row.get(5)));// Cột F: Ưu tiên Gốc
                    nv.setDiemUtqd(parseDoubleSafe(row.get(6)));   // Cột G: Ưu tiên (bị bóp)
                    nv.setDiemCong(parseDoubleSafe(row.get(7)));   // Cột H: Điểm Cộng
                    nv.setDiemXetTuyen(parseDoubleSafe(row.get(8))); // Cột I: Tổng Điểm
                    
                    String ketQua = row.get(9);
                    nv.setNvKetQua(ketQua != null && !ketQua.trim().isEmpty() ? ketQua : "Chờ xét"); // Cột J
                    
                    // Cột K (Index 10) là Keys, lát nữa mình tự tạo, không đọc từ Excel
                    
                    nv.setTtPhuongThuc(row.get(11));       // Cột L: Phương thức
                    nv.setTtThm(row.get(12));              // Cột M: Tổ hợp
                    
                    // Tạo key ẩn để chống trùng
                    nv.setNvKeys(nv.getNnCccd() + "_" + nv.getNvTt() + "_" + nv.getTtPhuongThuc() + "_" + nv.getTtThm());

                    // 4. Đẩy xuống DAO để Thêm vào DB
                    if (dao.saveNguyenVong(nv)) { 
                        soDongThanhCong++;
                    } else {
                        System.out.println("❌ Lỗi lưu DB tại dòng " + (i+1));
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    System.out.println("❌ Lỗi ép kiểu/dữ liệu tại dòng " + (i+1) + ": " + ex.getMessage());
                    soDongThatBai++; 
                }
            }
            
            return "Nhập thành công: " + soDongThanhCong + " dòng.\nLỗi/Trùng lặp/Thiếu: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi cấu trúc khi đọc file Excel: " + e.getMessage();
        }
    }
    
    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
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
        
        if (index == 11) {
            
            LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new LinkedHashMap<>();
            
            for (nguyenVongXetTuyenETT nv : ds) {
                if (nv != null && nv.getNnCccd() != null) {
                    if (!tuKhoa.isEmpty() && !nv.getNnCccd().toLowerCase().equals(tuKhoa)) {
                        continue; 
                    }
                    
                    String keyGomNhom = nv.getNnCccd() + "_" + nv.getNvMaNganh();
                    
                    if (!mapHocSinh.containsKey(keyGomNhom)) {
                        mapHocSinh.put(keyGomNhom, new ArrayList<>());
                    }
                    mapHocSinh.get(keyGomNhom).add(nv);
                }
            }

            for (String keyGomNhom : mapHocSinh.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> listNV = mapHocSinh.get(keyGomNhom);
                double diemMax = -1.0;
                String thmMax = "";
                
                for (nguyenVongXetTuyenETT nv : listNV) {
                    if (nv.getTtThm() != null && !nv.getTtThm().isEmpty() && !nv.getTtThm().equals("Không")) {
                        if (nv.getDiemXetTuyen() > diemMax) {
                            diemMax = nv.getDiemXetTuyen();
                            thmMax = nv.getTtThm();
                        }
                    }
                }
                
                if (!thmMax.isEmpty()) {
                    for (nguyenVongXetTuyenETT nv : listNV) {
                        if (nv.getTtThm() != null && nv.getTtThm().equalsIgnoreCase(thmMax) && nv.getDiemXetTuyen() == diemMax) {
                            dskq.add(nv);
                        }
                    }
                }
            }
            return dskq;
        }
        

        if (index == 12) {
            
            LinkedHashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new LinkedHashMap<>();
            
            for (nguyenVongXetTuyenETT nv : ds) {
                if (nv != null && nv.getNnCccd() != null) {
                    if (!tuKhoa.isEmpty() && !nv.getNnCccd().toLowerCase().equals(tuKhoa)) {
                        continue; 
                    }
                    
                    String cccd = nv.getNnCccd();
                    
                    if (!mapHocSinh.containsKey(cccd)) {
                        mapHocSinh.put(cccd, new ArrayList<>());
                    }
                    mapHocSinh.get(cccd).add(nv);
                }
            }

            for (String cccd : mapHocSinh.keySet()) {
                ArrayList<nguyenVongXetTuyenETT> listNV = mapHocSinh.get(cccd);
                double diemMax = -1.0;
                
                for (nguyenVongXetTuyenETT nv : listNV) {
                    if (nv.getDiemXetTuyen() > diemMax) {
                        diemMax = nv.getDiemXetTuyen();
                    }
                }
                
                for (nguyenVongXetTuyenETT nv : listNV) {
                    if (nv.getDiemXetTuyen() == diemMax) {
                        dskq.add(nv);
                    }
                }
            }
            return dskq;
        }
        
        for(nguyenVongXetTuyenETT ct : ds)
        {
            if(ct == null)
            {
                continue;
            }
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
    
    public boolean kiemTraTrungNganh(String cccd, String maNganh) 
    {
        if (cccd == null || cccd.trim().isEmpty() || maNganh == null || maNganh.trim().isEmpty()) 
        {
            return false;
        }
        
        DAO.nguyenVongXetTuyenDAO dao = new DAO.nguyenVongXetTuyenDAO();
        return dao.kiemTraTrungNganh(cccd, maNganh);
    }
    
    public int demSoThangDangKyKhongTrung(String maNganh) {
        return data.demSoThangDangKyKhongTrung(maNganh);
    }
public ArrayList<nguyenVongXetTuyenETT> timKiemNangCao(String cccd, String maNganh, String toHop, int thuTuNV, String phuongThuc, String ketQua) {
        
        if (ds == null) {
            layDanhSach();
        }
        
        ArrayList<nguyenVongXetTuyenETT> dskq = new ArrayList<>();
        
        String fCccd = (cccd != null) ? cccd.trim().toLowerCase() : "";
        String fMaNganh = (maNganh != null) ? maNganh.trim().toLowerCase() : "";
        String fToHop = (toHop != null) ? toHop.trim().toLowerCase() : "";
        String fPhuongThuc = (phuongThuc != null) ? phuongThuc.trim().toLowerCase() : "";
        String fKetQua = (ketQua != null) ? ketQua.trim().toLowerCase() : "";

        if (fPhuongThuc.equals("tất cả")) fPhuongThuc = "";
        if (fKetQua.equals("tất cả")) fKetQua = "";
        if (fToHop.equals("tất cả")) fToHop = "";

        for (nguyenVongXetTuyenETT ct : ds) {
            if (ct == null) continue;

            boolean isMatch = true; 

            if (!fCccd.isEmpty()) {
                if (ct.getNnCccd() == null || !ct.getNnCccd().toLowerCase().contains(fCccd)) {
                    isMatch = false;
                }
            }

            if (isMatch && !fMaNganh.isEmpty()) {
                if (ct.getNvMaNganh() == null || !ct.getNvMaNganh().toLowerCase().contains(fMaNganh)) {
                    isMatch = false;
                }
            }

            if (isMatch && !fToHop.isEmpty()) {
                if (ct.getTtThm() == null || !ct.getTtThm().toLowerCase().contains(fToHop)) {
                    isMatch = false;
                }
            }

            if (isMatch && thuTuNV > 0) {
                if (ct.getNvTt() != thuTuNV) {
                    isMatch = false;
                }
            }

            if (isMatch && !fPhuongThuc.isEmpty()) {
                if (ct.getTtPhuongThuc() == null || !ct.getTtPhuongThuc().toLowerCase().equals(fPhuongThuc)) {
                    isMatch = false;
                }
            }

            if (isMatch && !fKetQua.isEmpty()) {
                if (ct.getNvKetQua() == null || !ct.getNvKetQua().toLowerCase().equals(fKetQua)) {
                    isMatch = false;
                }
            }

            if (isMatch) {
                dskq.add(ct);
            }
        }

        return dskq;
    }
}
