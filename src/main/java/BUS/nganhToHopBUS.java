/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.nganhToHopETT;
import DAO.nganhToHopDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class nganhToHopBUS {
    public static ArrayList<nganhToHopETT> ds;
    nganhToHopDAO data = new nganhToHopDAO();
    public ArrayList<nganhToHopETT> layDanhSach()
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
    
    public boolean kiemTraSoAm(String text)
    {
        int so = Integer.parseInt(text);
        if(so < 0)
        {
            return false;
        }
        return true;
    }
          
    public boolean kiemTraTrung(String key)
    {
        for(nganhToHopETT ct : ds)
        {
            if(ct.getKey().equals(key))
            {
                return true;
            }
        }
        return false;
    }
        
    // ===================== THÊM =====================
    public boolean themNganhToHop(nganhToHopETT nganhToHopMoi) {
        boolean isSuccess = data.themNganhToHop(nganhToHopMoi);
        if (isSuccess) {
            if (ds != null) {
                ds.add(nganhToHopMoi); // Cập nhật RAM luôn, không cần load lại DB
            }
        }
        return isSuccess;
    }

    // ===================== SỬA =====================
    public boolean suaNganhToHop(nganhToHopETT nganhToHopDaSua) {
        // 1. Lưu xuống Database trước qua DAO
        if (data.suaNganhToHop(nganhToHopDaSua)) {

            // 2. Nếu DB OK → cập nhật lại RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng id (khóa chính) để tìm đúng phần tử
                    if (ds.get(i).getIdNganhToHop() == nganhToHopDaSua.getIdNganhToHop()) {
                        ds.set(i, nganhToHopDaSua); // Đè object mới vào vị trí cũ
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ===================== XÓA =====================
    public boolean xoaNganhToHop(nganhToHopETT nganhToHopCanXoa) {
        // 1. Xóa trong Database trước
        if (data.xoa(nganhToHopCanXoa)) {

            // 2. Nếu DB OK → xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getIdNganhToHop() == nganhToHopCanXoa.getIdNganhToHop()) {
                        ds.remove(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    
    public double layDoLechDiem(String maNganh, String maToHop) {
        if(ds == null || ds.isEmpty()) {
            layDanhSach();
        }
        
        for(nganhToHopETT ct : ds) {
            if(ct.getMaNganh() != null && ct.getMaToHop() != null) {
                if(ct.getMaNganh().equals(maNganh) && ct.getMaToHop().equals(maToHop)) {
                    return ct.getDoLech();
                }
            }
        }
        return 0.0;
    }

// Gọi xuống DAO để lấy hệ số môn
    public double[] layHeSoMon(String maNganh, String maToHop) {
        // Nếu ông muốn kiểm tra rỗng (Validation) thì làm ở đây trước khi gọi DAO
        if (maNganh == null || maNganh.isEmpty() || maToHop == null || maToHop.isEmpty()) {
            return new double[]{1.0, 1.0, 1.0}; // Trả về mặc định nếu đầu vào lỗi
        }
        
        return data.layHeSoMon(maNganh, maToHop);
    }
    
    // Hàm này giống như Kính lúp, chỉ tìm kiếm trên RAM, không đụng tới DB
    public nganhToHopETT layNganhToHopBangKey(String key) {
        // 1. Đảm bảo danh sách trên RAM đã có đồ ăn
        if (ds == null || ds.isEmpty()) {
            layDanhSach(); 
        }

        // 2. Cầm kính lúp đi dò từng thằng một
        for (nganhToHopETT nth : ds) {
            // Nếu tìm thấy thằng nào có cái Mã Key trùng khớp (Ví dụ: "7480201_A00")
            if (nth.getKey() != null && nth.getKey().equals(key)) {
                return nth; // Bắt được nó rồi, ném nó ra ngoài!
            }
        }

        // 3. Tìm đỏ mắt không thấy thì báo Null
        return null; 
    }
    
        private double parseDoubleSafe(String val) {
        try {
            if (val == null || val.trim().isEmpty()) return 0.0;
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    
        public Integer parseIntSafe(String val) {
    try {
        if (val == null || val.trim().isEmpty()) return 0;
        return (int) Double.parseDouble(val.trim()); // xử lý luôn 1.0
    } catch (Exception e) {
        return 0;
    }
}
        public String nhapDuLieuTuExcel(String filePath) {
        try {
            // 1. Lấy ma trận dữ liệu từ Excel
            ArrayList<ArrayList<String>> dataExcel = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (dataExcel.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            for (int i = 1; i < dataExcel.size(); i++) {
                ArrayList<String> row = dataExcel.get(i);
                
                try {
                    nganhToHopETT nv = new nganhToHopETT();
                    
                    nv.setMaNganh(row.get(1));
                    nv.setMaToHop(row.get(2));

                    nv.setMon1(row.get(3));
                    nv.setHeSoMon1(parseIntSafe(row.get(4)));

                    nv.setMon2(row.get(5));
                    nv.setHeSoMon2(parseIntSafe(row.get(6)));

                    nv.setMon3(row.get(7));
                    nv.setHeSoMon3(parseIntSafe(row.get(8)));

                    // Key
                    nv.setKey(nv.getMaNganh() + "_" + nv.getMaToHop());

                    // 🔥 MẤY CỘT MÔN BỊ THIẾU (đây là cái bạn cần)
                    nv.setN1(parseIntSafe(row.get(10)));
                    nv.setTO(parseIntSafe(row.get(11)));
                    nv.setLI(parseIntSafe(row.get(12)));
                    nv.setHO(parseIntSafe(row.get(13)));
                    nv.setSI(parseIntSafe(row.get(14)));
                    nv.setVA(parseIntSafe(row.get(15)));
                    nv.setSU(parseIntSafe(row.get(16)));
                    nv.setDI(parseIntSafe(row.get(17)));
                    nv.setTI(parseIntSafe(row.get(18)));
                    nv.setKHAC(parseIntSafe(row.get(19)));
                    nv.setKTPL(parseIntSafe(row.get(20)));

                    // Độ lệch
                    nv.setDoLech(parseDoubleSafe(row.get(21)));
                    
                    // 4. Đẩy xuống DAO để Thêm vào Database (Nhớ sửa tên hàm cho khớp DAO của ông nha)
                    if (data.themNganhToHop(nv)) { 
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
        
    public ArrayList<nganhToHopETT> timKiemCoBan(String tim, int index)
    {
        if(ds == null)
        {
            layDanhSach();
        }
        
        ArrayList<nganhToHopETT> dskq = new ArrayList<nganhToHopETT>();
        
        for(nganhToHopETT ct : ds)
        {
            switch (index) {
                case 0:
                    if(String.valueOf(ct.getIdNganhToHop()).contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 1:
                    if(ct.getMaNganh() != null && ct.getMaNganh().toLowerCase().contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 2:
                    if(ct.getMaToHop() != null && ct.getMaToHop().toLowerCase().contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 3:
                    if(ct.getMon1() != null && ct.getMon1().toLowerCase().contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 4:
                    if(String.valueOf(ct.getHeSoMon1()).contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 5:
                    if(ct.getMon2() != null && ct.getMon2().toLowerCase().contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 6:
                    if(String.valueOf(ct.getHeSoMon2()).contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 7:
                    if(ct.getMon3() != null && ct.getMon3().toLowerCase().contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 8:
                    if(String.valueOf(ct.getHeSoMon3()).contains(tim))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 9:
                    if(String.valueOf(ct.getDoLech()).contains(tim))
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
    
    public ArrayList<Entity.toHopETT> layDanhSachToHopTheoNganh(String maNganh) {
        // Tránh lỗi null rớ ngớ
        if (maNganh == null || maNganh.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        
        // Nhớ đổi tên class DAO cho đúng với tên file của ông nha
        DAO.nganhToHopDAO dao = new DAO.nganhToHopDAO(); 
        return dao.layDanhSachToHopTheoNganh(maNganh);
    }
}
