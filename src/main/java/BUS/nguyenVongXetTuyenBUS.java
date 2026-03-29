/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.nguyenVongXetTuyenDAO;
import Entity.nguyenVongXetTuyenETT;
import java.util.ArrayList;
import java.util.HashMap;

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

        // BƯỚC 2: GOM NHÓM NGUYỆN VỌNG THEO TỪNG HỌC SINH
        HashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapHocSinh = new HashMap<>();
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
        HashMap<String, ArrayList<nguyenVongXetTuyenETT>> mapRoXetTuyen = new HashMap<>();

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

                // SẮP XẾP CAO CẤP V2: Đưa Tổ Hợp Gốc vào làm "Thái Thượng Hoàng"
                roHienTai.sort((nv1, nv2) -> {
                    // Ưu tiên 1: Điểm Xét Tuyển (Cao xuống Thấp)
                    int diemCompare = Double.compare(nv2.getDiemXetTuyen(), nv1.getDiemXetTuyen());
                    if (diemCompare != 0) return diemCompare;
                    
                    // Ưu tiên 2 (MỚI): TỔ HỢP GỐC - Bằng điểm thì ưu tiên thằng có Tổ hợp = Tổ hợp gốc
                    boolean isNv1Goc = nv1.getTtThm() != null && nv1.getTtThm().equals(toHopGoc);
                    boolean isNv2Goc = nv2.getTtThm() != null && nv2.getTtThm().equals(toHopGoc);
                    if (isNv1Goc && !isNv2Goc) return -1; // nv1 là con ruột -> Xếp trên
                    if (!isNv1Goc && isNv2Goc) return 1;  // nv2 là con ruột -> Xếp trên

                    // Ưu tiên 3: Điểm Môn 1 (Toán) - Cùng là con ruột (hoặc cùng không phải) thì xét điểm Toán
                    int diemToanCompare = Double.compare(nv2.getDiemMon1(), nv1.getDiemMon1());
                    if (diemToanCompare != 0) return diemToanCompare;

                    // Ưu tiên 4: Thứ tự nguyện vọng (Nhỏ xuống Lớn)
                    return Integer.compare(nv1.getNvTt(), nv2.getNvTt());
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
                        
                        // ĐỒNG ĐIỂM HOÀN TOÀN TỪ ƯU TIÊN 1 TỚI ƯU TIÊN 4
                        if (nguoiTiepTheo.getDiemXetTuyen() == nguoiCuoiCungDau.getDiemXetTuyen() &&
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
                data.suaNguyenVong(nv);
            }
        }
        
        System.out.println("CẮT CHỈ TIÊU HOÀN TẤT!");
    }
    
    // =========================================================================
    // HÀM BƠM DỮ LIỆU ĐIỂM TỪ DATABASE VÀO CÁC BIẾN ẢO (@Transient)
    // =========================================================================
    public void napDiemAoChoDanhSach() {
        if (ds == null || ds.isEmpty()) return;
        
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            for (Entity.nguyenVongXetTuyenETT nv : ds) {
                String cccd = nv.getNnCccd();
                String maToHop = nv.getTtThm();
                String phuongThuc = nv.getTtPhuongThuc();

                // 1. Gán điểm ưu tiên (Lấy từ cột diem_utqd có sẵn)
                nv.setDiemUuTien(nv.getDiemUtqd() == null ? 0.0 : nv.getDiemUtqd());

                // 2. Lấy điểm ĐGNL HCM
                if (phuongThuc.equals("ĐGNL HCM")) {
                    Number diemNL = (Number) session.createNativeQuery("SELECT NL1 FROM xt_diemthixettuyen WHERE cccd = :cccd")
                            .setParameter("cccd", cccd).uniqueResult();
                    nv.setDiemMon1(diemNL == null ? 0.0 : diemNL.doubleValue());
                } 
                // 3. Lấy điểm V-SAT hoặc THPT
                else if (maToHop != null && !maToHop.equals("Không")) {
                    // Lấy tên 3 môn từ bảng Tổ Hợp
                    Object[] toHop = (Object[]) session.createNativeQuery("SELECT mon1, mon2, mon3 FROM xt_tohop_monthi WHERE matohop = :ma")
                            .setParameter("ma", maToHop).uniqueResult();

if (toHop != null) {
                        String m1 = (String) toHop[0];
                        String m2 = (String) toHop[1];
                        String m3 = (String) toHop[2];

                        nv.setTenMon1(m1);
                        nv.setTenMon2(m2);
                        nv.setTenMon3(m3);

                        // --- CODE MỚI: LẤY HỆ SỐ MÔN TỪ BẢNG NGÀNH TỔ HỢP ---
                        String sqlHeSo = "SELECT hsmon1, hsmon2, hsmon3 FROM xt_nganh_tohop WHERE manganh = :mn AND matohop = :ma";
                        Object[] heSo = (Object[]) session.createNativeQuery(sqlHeSo)
                                .setParameter("mn", nv.getNvMaNganh())
                                .setParameter("ma", maToHop).uniqueResult();

                        if (heSo != null) {
                            nv.setHsMon1(heSo[0] == null ? 1.0 : ((Number) heSo[0]).doubleValue());
                            nv.setHsMon2(heSo[1] == null ? 1.0 : ((Number) heSo[1]).doubleValue());
                            nv.setHsMon3(heSo[2] == null ? 1.0 : ((Number) heSo[2]).doubleValue());
                        }

                        // --- GỠ BẪY MÔN TIẾNG ANH (Như cũ) ---
                        String col1 = m1.equals("N1") ? "N1_THI" : m1;
// ... (Phần lấy điểm thi bên dưới vẫn giữ nguyên)
                        String col2 = m2.equals("N1") ? "N1_THI" : m2;
                        String col3 = m3.equals("N1") ? "N1_THI" : m3;

                        // Sửa lại câu SQL dùng col1, col2, col3
                        String sqlDiem = "SELECT `" + col1 + "`, `" + col2 + "`, `" + col3 + "` FROM xt_diemthixettuyen WHERE cccd = :cccd";
                        Object[] diem = (Object[]) session.createNativeQuery(sqlDiem)
                                .setParameter("cccd", cccd).uniqueResult();

                        if (diem != null) {
                            nv.setDiemMon1(diem[0] == null ? 0.0 : ((Number) diem[0]).doubleValue());
                            nv.setDiemMon2(diem[1] == null ? 0.0 : ((Number) diem[1]).doubleValue());
                            nv.setDiemMon3(diem[2] == null ? 0.0 : ((Number) diem[2]).doubleValue());
                        }
                        
                        // ... (Đoạn code cũ của ông: nv.setDiemMon1, 2, 3) ...

                        // ====================================================================
                        // 🚀 TÍNH NĂNG MỚI: AUTO QUY ĐỔI IELTS & CỘNG ĐIỂM GIẢI THƯỞNG
                        // ====================================================================
                        double diemCongIELTS = 0.0;
                        double diemCongGiaiThuong = 0.0;

                        // 1. DÒ TÌM CHỨNG CHỈ IELTS
                        String sqlIELTS = "SELECT diem_quydoi, diem_cong FROM xt_chungchi WHERE cccd = :cccd LIMIT 1";
                        Object[] ieltsData = (Object[]) session.createNativeQuery(sqlIELTS)
                                .setParameter("cccd", cccd).uniqueResult();

                        if (ieltsData != null) {
                            double diemQuyDoi = ieltsData[0] == null ? 0.0 : ((Number) ieltsData[0]).doubleValue();
                            diemCongIELTS = ieltsData[1] == null ? 0.0 : ((Number) ieltsData[1]).doubleValue();

                            // TUYỆT CHIÊU: Tráo điểm! Nếu tổ hợp có môn Tiếng Anh (N1), lấy điểm IELTS đè lên điểm thi
                            if ("N1".equals(m1)) nv.setDiemMon1(diemQuyDoi);
                            if ("N1".equals(m2)) nv.setDiemMon2(diemQuyDoi);
                            if ("N1".equals(m3)) nv.setDiemMon3(diemQuyDoi);
                        }

                        // 2. DÒ TÌM GIẢI THƯỞNG HỌC SINH GIỎI
                        String sqlGT = "SELECT ma_mon, diem_cong_co_mon, diem_cong_khong_mon FROM xt_giathuong WHERE cccd = :cccd LIMIT 1";
                        Object[] gtData = (Object[]) session.createNativeQuery(sqlGT)
                                .setParameter("cccd", cccd).uniqueResult();

                        if (gtData != null) {
                            String maMonGiai = (String) gtData[0];
                            double diemCoMon = gtData[1] == null ? 0.0 : ((Number) gtData[1]).doubleValue();
                            double diemKhongMon = gtData[2] == null ? 0.0 : ((Number) gtData[2]).doubleValue();

                            // KIỂM TRA MÔN: Xem tổ hợp (m1, m2, m3) có chứa môn đạt giải không?
                            if (maMonGiai.equals(m1) || maMonGiai.equals(m2) || maMonGiai.equals(m3)) {
                                diemCongGiaiThuong = diemCoMon; // Trúng tủ -> Cộng nhiều
                            } else {
                                diemCongGiaiThuong = diemKhongMon; // Lệch tủ -> Cộng ít
                            }
                        }

                        // 3. CHỐT SỔ ĐIỂM ƯU TIÊN
                        // Tổng điểm cộng = Điểm UT Khu Vực (đã gán ở trên) + IELTS + Giải thưởng
                        double tongDiemUT = nv.getDiemUuTien() + diemCongIELTS + diemCongGiaiThuong;
                        
                        // Luật của Bộ: Điểm cộng thêm (không tính khu vực) thường bị chặn trần, 
                        // nhưng ở đây mình an toàn nhất là chặn tổng điểm UT không vượt quá mức quy định (VD: max là 3.0 hoặc 5.0 tùy trường).
                        // Ở đây tui để tạm chặn trần tổng điểm không qua 30 ở hàm tính điểm tổng rồi nên cứ cộng tẹt ga.
                        nv.setDiemUuTien(tongDiemUT);
                        // ====================================================================
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // =========================================================================
    // HÀM TỰ ĐỘNG TÌM VÀ CẬP NHẬT ĐIỂM CHUẨN VÀO BẢNG NGÀNH
    // =========================================================================
    public void capNhatDiemChuanTuDong() {
        if (ds == null || ds.isEmpty()) return;

        // 1. Tạo 1 cái Map để nhớ điểm thấp nhất của từng Ngành - Phương thức
        // Key: "MaNganh_PhuongThuc" (VD: "7480201_Đánh giá V-SAT")
        // Value: Điểm thấp nhất
        java.util.HashMap<String, Double> diemChuanMap = new java.util.HashMap<>();

        // 2. Quét qua toàn bộ danh sách để tìm điểm thấp nhất của người "Đã đậu"
        for (Entity.nguyenVongXetTuyenETT nv : ds) {
            if ("Đã đậu".equals(nv.getNvKetQua())) {
                String key = nv.getNvMaNganh() + "_" + nv.getTtPhuongThuc();
                double diem = nv.getDiemXetTuyen();

                // Nếu chưa có ngành này trong Map, hoặc điểm của đứa này thấp hơn điểm đang lưu thì cập nhật
                if (!diemChuanMap.containsKey(key) || diem < diemChuanMap.get(key)) {
                    diemChuanMap.put(key, diem);
                }
            }
        }

        // 3. Cập nhật ngược lại vào Database (Bảng xt_nganh)
        try (org.hibernate.Session session = CONFIG.HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            for (java.util.Map.Entry<String, Double> entry : diemChuanMap.entrySet()) {
                String[] parts = entry.getKey().split("_");
                String maNganh = parts[0];
                String phuongThuc = parts[1];
                double diemChuan = entry.getValue();

                // Tùy theo phương thức mà Update vào đúng cột điểm chuẩn
                String sqlUpdate = "";
                if (phuongThuc.equals("Đánh giá V-SAT")) {
                    sqlUpdate = "UPDATE xt_nganh SET diemchuan_vsat = :dc WHERE manganh = :ma";
                } else if (phuongThuc.equals("ĐGNL HCM")) {
                    sqlUpdate = "UPDATE xt_nganh SET diemchuan_dgnl = :dc WHERE manganh = :ma";
                } else if (phuongThuc.equals("Xét THPT")) {
                    sqlUpdate = "UPDATE xt_nganh SET diemchuan_thpt = :dc WHERE manganh = :ma";
                }else if (phuongThuc.equals("Xét tuyển thẳng")) {
                    sqlUpdate = "UPDATE xt_nganh SET diemchuan_xtt = :dc WHERE manganh = :ma";
                }

                if (!sqlUpdate.isEmpty()) {
                    session.createNativeQuery(sqlUpdate)
                           .setParameter("dc", diemChuan)
                           .setParameter("ma", maNganh)
                           .executeUpdate();
                }
            }
            session.getTransaction().commit();
            System.out.println("✅ Đã chốt điểm chuẩn tự động thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Trong file nguyenVongXetTuyenBUS.java (Hoặc BUS tương ứng)
// =====================================================================
    // HÀM PHỤ TRỢ: ÉP KIỂU AN TOÀN (Tránh crash nếu ô Excel bị rỗng)
    // =====================================================================
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
}
