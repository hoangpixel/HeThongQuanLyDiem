package BUS;

import DAO.phanQuyenDAO;
import Entity.phanQuyenETT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class phanQuyenBUS {
    phanQuyenDAO data = new phanQuyenDAO();
    
    // ==============================================================
    // 1. RAM DÀNH CHO CHECK QUYỀN (HỆ THỐNG GIAO DIỆN)
    // ==============================================================
    public static HashMap<String, phanQuyenETT> dsQuyen = new HashMap<>();

    public void loadQuyenLenRAM(int idTaiKhoan) {
        dsQuyen.clear(); 
        List<phanQuyenETT> list = data.layDanhSachQuyen(idTaiKhoan);
        
        if (list != null) {
            for (phanQuyenETT q : list) {
                dsQuyen.put(q.getTenBang(), q);
            }
        }
    }

    public static boolean checkQuyenXem(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) return dsQuyen.get(tenBang).getQuyenXem() == 1;
        return false;
    }

    public static boolean checkQuyenThem(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) return dsQuyen.get(tenBang).getQuyenThem() == 1;
        return false;
    }

    public static boolean checkQuyenSua(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) return dsQuyen.get(tenBang).getQuyenSua() == 1;
        return false;
    }

    public static boolean checkQuyenXoa(String tenBang) {
        if (dsQuyen.containsKey(tenBang)) return dsQuyen.get(tenBang).getQuyenXoa() == 1;
        return false;
    }
    
    // ==============================================================
    // 2. RAM DÀNH CHO QUẢN LÝ (LOAD LÊN JTABLE) VÀ XỬ LÝ LƯU
    // ==============================================================
    
    public static ArrayList<phanQuyenETT> ds; // Biến RAM chứa toàn bộ quyền
    
    public ArrayList<phanQuyenETT> layDanhSach() {
        if (ds == null) {
            // Nhớ viết thêm hàm layDanhSachToanBo() bên DAO nếu chưa có nha Boss
            // Hàm đó đơn giản là: SELECT * FROM phanQuyenETT
            ds = (ArrayList<phanQuyenETT>) data.layDanhSach(); 
        }
        return ds;
    }

public boolean luuPhanQuyen(int idTaiKhoan, ArrayList<phanQuyenETT> dsQuyenMoi) {
        
        // 🚀 BƯỚC 0: GOM TẤT CẢ QUYỀN CŨ TỪ DB LÊN "CÁI RỔ" TỔNG HỢP
        List<phanQuyenETT> dsQuyenCu = data.layDanhSachQuyen(idTaiKhoan);
        HashMap<String, phanQuyenETT> mapTongHop = new HashMap<>();
        
        if (dsQuyenCu != null) {
            for (phanQuyenETT qCu : dsQuyenCu) {
                mapTongHop.put(qCu.getTenBang(), qCu); // Nhét hết vào rổ, kể cả bảng xt_phanquyen đang ẩn
            }
        }

        // 🚀 BƯỚC 1: LẤY QUYỀN MỚI TỪ GIAO DIỆN GHI ĐÈ THẲNG VÀO "CÁI RỔ"
        for (phanQuyenETT pqMoi : dsQuyenMoi) {
            String tenBang = pqMoi.getTenBang();
            
            // 🔥 XÓA PHÉP TOÁN OR CHỖ NÀY 🔥
            // Giao diện gửi xuống sao thì đè nguyên vậy (Tick là 1, bỏ tick là 0).
            // Do bảng xt_phanquyen không được giao diện gửi xuống -> Không bị ghi đè -> Vẫn giữ nguyên quyền cũ!
            mapTongHop.put(tenBang, pqMoi); 
        }

        // 🚀 BƯỚC 2: XÓA SẠCH DB (Đã copy hết lên rổ rồi nên yên tâm xóa)
        data.xoaQuyenCu(idTaiKhoan);
        
        // 🚀 BƯỚC 3: MANG NGUYÊN "CÁI RỔ" ĐI INSERT LẠI VÀO DB
        boolean isSuccess = true;
        for (phanQuyenETT pqFinal : mapTongHop.values()) {
            if (!data.themQuyenMoi(pqFinal)) {
                isSuccess = false; 
            }
        }
        
        // Bước 4: ĐỒNG BỘ RAM
        if (isSuccess) {
            ds = null; // Phá RAM cũ 
            
            // Cập nhật nóng nếu đang tự sửa quyền chính mình
            if (taiKhoanBUS.taiKhoanHienTai != null && taiKhoanBUS.taiKhoanHienTai.getIdTaiKhoan() == idTaiKhoan) {
                loadQuyenLenRAM(idTaiKhoan);
            }
        }
        
        return isSuccess;
    }

    public boolean xoaPhanQuyen(phanQuyenETT ct) 
    {
        // 1. Kêu DAO xóa dưới MySQL
        if (data.xoaPhanQuyen(ct)) {
            
            // 2. Nếu MySQL xóa thành công, xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // 🔥 PHẢI CHECK CẢ ID VÀ TÊN BẢNG THÌ MỚI XÓA ĐÚNG DÒNG 🔥
                    if (ds.get(i).getIdTaiKhoan() == ct.getIdTaiKhoan() && ds.get(i).getTenBang().equals(ct.getTenBang())) {
                        ds.remove(i); 
                        break;
                    }
                }
            }
            
            return true;
        }
        return false;
    }
    
private int parseIntSafe(String val) {
        try {
            if (val == null || val.trim().isEmpty()) return 0;
            // Tuyệt chiêu: Đọc bằng Double trước để trị cái tật "1.0" của Excel, sau đó ép về int
            double d = Double.parseDouble(val.trim()); 
            return (int) d;
        } catch (Exception e) {
            return 0;
        }
    }
    
public String nhapDuLieuTuExcel(String filePath) {
        try {
            ArrayList<ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (data == null || data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;
            phanQuyenDAO dao = new phanQuyenDAO();

            for (int i = 1; i < data.size(); i++) {
                ArrayList<String> row = data.get(i);
                
                try {
                    // Cẩn thận: Kiểm tra xem dòng đó có đủ 6 cột không, lỡ dòng trống nó văng lỗi
                    if (row.size() >= 6) { 
                        phanQuyenETT nv = new phanQuyenETT();
                        
                        // LƯU Ý: NẾU CỘT A LÀ "ID TK" THÌ ĐỂ INDEX NHƯ DƯỚI ĐÂY (0 ĐẾN 5)
                        // Nếu cột A là "STT", cột B là "ID TK" thì phải đổi thành 1 đến 6
                        nv.setIdTaiKhoan(parseIntSafe(row.get(0)));
                        nv.setTenBang(row.get(1));
                        nv.setQuyenXem(parseIntSafe(row.get(2)));
                        nv.setQuyenThem(parseIntSafe(row.get(3)));
                        nv.setQuyenSua(parseIntSafe(row.get(4)));
                        nv.setQuyenXoa(parseIntSafe(row.get(5)));
                        
                        // (Khuyên dùng) DAO nên xài lệnh session.saveOrUpdate() hoặc session.merge()
                        if (dao.themQuyenMoi(nv)) { 
                            soDongThanhCong++;
                        } else {
                            soDongThatBai++;
                        }
                    } else {
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    soDongThatBai++;
                }
            }
            return "Nhập thành công: " + soDongThanhCong + " dòng.\nLỗi/Bỏ qua: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }

    public ArrayList<phanQuyenETT> timKiemCoBan(String tim, int index) 
    {
        ArrayList<phanQuyenETT> dskq = new ArrayList<>();
        
        String tuKhoa = tim.trim().toLowerCase(); 

        for (phanQuyenETT ct : ds) {
            boolean isMatch = false;
            
            switch (index) {
                case 0:
                    if (String.valueOf(ct.getIdTaiKhoan()).contains(tuKhoa)) {
                        isMatch = true;
                    }
                    break;
                    
                case 1:
                    if (ct.getTenBang().toLowerCase().contains(tuKhoa)) {
                        isMatch = true;
                    }
                    break;
                    
                case 2:
                    String strXem = (ct.getQuyenXem() == 1) ? "có" : "không";
                    if (strXem.contains(tuKhoa)) isMatch = true;
                    break;
                    
                case 3:
                    String strThem = (ct.getQuyenThem() == 1) ? "có" : "không";
                    if (strThem.contains(tuKhoa)) isMatch = true;
                    break;
                    
                case 4:
                    String strSua = (ct.getQuyenSua() == 1) ? "có" : "không";
                    if (strSua.contains(tuKhoa)) isMatch = true;
                    break;
                    
                case 5:
                    String strXoa = (ct.getQuyenXoa() == 1) ? "có" : "không";
                    if (strXoa.contains(tuKhoa)) isMatch = true;
                    break;
            }
            if (isMatch) {
                dskq.add(ct);
            }
        }
        
        return dskq;
    }
}