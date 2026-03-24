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
    
    public void sapXepKetQuaTuDong()
    {
        String cccdHientai = "";
        boolean daDauNguyenVong = false;
        nganhBUS busNganh = new nganhBUS();
        for(nguyenVongXetTuyenETT ct : ds)
        {
            if(!ct.getNnCccd().equals(cccdHientai))
            {
                cccdHientai = ct.getNnCccd();
                daDauNguyenVong = false;
            }
            
            if(daDauNguyenVong)
            {
                ct.setNvKetQua("Không xét");
                data.suaNguyenVong(ct);
                continue;
            }
            
            String maNganh = ct.getNvMaNganh();
            String phuongThuc = ct.getTtPhuongThuc();
            double diemChuanNganh = busNganh.layDiemChuanTheoPhuongThuc(maNganh, phuongThuc);
            
            if(ct.getDiemXetTuyen() >= diemChuanNganh) {
                ct.setNvKetQua("Đã đậu");
                daDauNguyenVong = true; // BẬT CỜ: Đã đậu rồi, các NV sau khỏi xét!
            } else {
                ct.setNvKetQua("Đã trượt");
            }
            
            // Cập nhật phán quyết xuống Database
            data.suaNguyenVong(ct);
        }
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

        // BƯỚC 1: TẠO BẢN ĐỒ CHỈ TIÊU 
        // Lấy chỉ tiêu từ Database lên RAM để check cho lẹ, Key = "MaNganh_PhuongThuc"
        HashMap<String, Integer> mapChiTieu = new HashMap<>();
        for (Entity.nganhETT nganh : dsNganh) {
            mapChiTieu.put(nganh.getManganh() + "_Xét THPT", nganh.getSl_thpt() != null ? nganh.getSl_thpt(): 0);
            mapChiTieu.put(nganh.getManganh() + "_ĐGNL HCM", nganh.getSl_dgnl() != null ? nganh.getSl_dgnl() : 0);
            mapChiTieu.put(nganh.getManganh() + "_Đánh giá V-SAT", nganh.getSl_vsat() != null ? nganh.getSl_vsat() : 0);
            mapChiTieu.put(nganh.getManganh() + "_Xét tuyển thẳng", nganh.getSl_xtt() != null ? nganh.getSl_xtt() : 0);
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

                // Sắp xếp tụi trong Rổ theo Điểm giảm dần (Đứa cao điểm đứng đầu)
                roHienTai.sort((nv1, nv2) -> Double.compare(nv2.getDiemXetTuyen(), nv1.getDiemXetTuyen()));

                int chiTieu = mapChiTieu.getOrDefault(keyRo, 0);

                // Nếu Rổ đang chứa nhiều đứa hơn số Chỉ Tiêu -> Chém rớt tụi chót bảng
                if (roHienTai.size() > chiTieu) {
                    for (int i = chiTieu; i < roHienTai.size(); i++) {
                        nguyenVongXetTuyenETT nvBiTruot = roHienTai.get(i);
                        String cccdBiTruot = nvBiTruot.getNnCccd();
                        
                        // Đứa bị trượt phải lùi con trỏ xuống NV tiếp theo của nó
                        conTroNV.put(cccdBiTruot, conTroNV.get(cccdBiTruot) + 1);
                        
                        // KÍCH HOẠT DOMINO: Vì có người bị lùi NV, hệ thống bắt buộc phải xếp rổ lại 1 lần nữa!
                        coSuThayDoi = true; 
                    }
                    // Quét sạch xác tụi bị chém ra khỏi Rổ hiện tại
                    roHienTai.subList(chiTieu, roHienTai.size()).clear();
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

                        // Lấy điểm thi dựa trên tên môn vừa tìm được (Dùng dấu backtick ` để chống lỗi từ khóa TO)
                        String sqlDiem = "SELECT `" + m1 + "`, `" + m2 + "`, `" + m3 + "` FROM xt_diemthixettuyen WHERE cccd = :cccd";
                        Object[] diem = (Object[]) session.createNativeQuery(sqlDiem)
                                .setParameter("cccd", cccd).uniqueResult();

                        if (diem != null) {
                            nv.setDiemMon1(diem[0] == null ? 0.0 : ((Number) diem[0]).doubleValue());
                            nv.setDiemMon2(diem[1] == null ? 0.0 : ((Number) diem[1]).doubleValue());
                            nv.setDiemMon3(diem[2] == null ? 0.0 : ((Number) diem[2]).doubleValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
