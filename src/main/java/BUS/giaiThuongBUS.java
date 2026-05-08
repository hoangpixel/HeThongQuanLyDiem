/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.giaiThuongETT;
import DAO.giaiThuongDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class giaiThuongBUS {
    public static ArrayList<giaiThuongETT> ds;
    giaiThuongDAO data = new giaiThuongDAO();
    public ArrayList<giaiThuongETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }

    public giaiThuongETT findById(int id) {
        if (ds == null) layDanhSach();
        for (giaiThuongETT gt : ds) {
            if (gt.getIdGt() == id) return gt;
        }
        return null;
    }
    
    // Gọi xuống DAO để dò Giải thưởng
    public Object[] layGiaiThuong(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return null;
        }
        
        giaiThuongDAO dao = new giaiThuongDAO();
        return dao.layGiaiThuong(cccd);
    }
    
    public String[] layCapVaLoaiGiai(String cccd) {
        if (cccd == null || cccd.isEmpty()) return new String[]{"", ""};
        giaiThuongDAO dao = new giaiThuongDAO();
        return dao.layCapVaLoaiGiai(cccd);
    }
    
    public boolean themGT(giaiThuongETT gt) {
        boolean isSuccess = data.themGiaiThuong(gt);
        if (isSuccess) {
            if (ds != null) {
                ds.add(gt);
            }
        }
        return isSuccess;
    }

    public boolean suaGT(giaiThuongETT gtMoi) {
        if (data.suaGiaiThuong(gtMoi)) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getIdGt() == gtMoi.getIdGt()) {
                        ds.set(i, gtMoi);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean xoaGT(int idGt) {
        if (data.xoaGiaiThuong(idGt)) {
            if (ds != null) {
                ds.removeIf(item -> item.getIdGt() == idGt);
            } 
            return true;
        }
        return false;
    }

    public ArrayList<giaiThuongETT> timKiemCoBan(String tim, int index) {
        // Đảm bảo list ds (ArrayList<giaiThuongETT>) đã khởi tạo
        if(ds == null) {
            layDanhSach();
        }

        ArrayList<giaiThuongETT> dskq = new ArrayList<>();
        String tuKhoa = tim.trim().toLowerCase();

        for(giaiThuongETT ct : ds) {
            switch (index) {
                case 0: // Tìm theo ID (int)
                    if(String.valueOf(ct.getIdGt()).toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 1: // Tìm theo CCCD (String)
                    if(ct.getCccd() != null && ct.getCccd().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 2: // Tìm theo Cấp giải (String)
                    if(ct.getCapGiai() != null && ct.getCapGiai().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 3: // Tìm theo Đối tượng (String)
                    if(ct.getDoiTuong() != null && ct.getDoiTuong().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 4: // Tìm theo Mã môn (String)
                    if(ct.getMaMon() != null && ct.getMaMon().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 5: // Tìm theo Loại giải (String)
                    if(ct.getLoaiGiai() != null && ct.getLoaiGiai().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 6: // Tìm theo Điểm cộng CÓ môn (Double)
                    if(ct.getDiemCongCoMon() != null && String.valueOf(ct.getDiemCongCoMon()).toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 7: // Tìm theo Điểm cộng KHÔNG môn (Double)
                    if(ct.getDiemCongKhongMon() != null && String.valueOf(ct.getDiemCongKhongMon()).toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                default:
                    throw new AssertionError("Index tìm kiếm không hợp lệ: " + index);
            }
        }
        return dskq;
    }
    
    public String nhapDuLieuTuExcel(String filePath) {
        try {
            java.util.ArrayList<java.util.ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThemMoi = 0;
            int soDongCapNhat = 0;
            int soDongThatBai = 0;
            DAO.giaiThuongDAO dao = new DAO.giaiThuongDAO(); 

            // Cập nhật RAM mới nhất trước khi xử lý để đối chiếu
            if (this.ds == null) this.layDanhSach();

            for (int i = 1; i < data.size(); i++) {
                java.util.ArrayList<String> row = data.get(i);
                
                try {
                    // --- ĐỌC ID ĐỂ XÁC ĐỊNH LÀ THÊM HAY SỬA ---
                    // Lưu ý: Tùy Entity cậu đặt là getId() hay getIdGt() mà sửa lại nhé
                    int idGt = (int) parseDoubleSafe(getCellSafe(row, 0)); // Cột A: ID Giải thưởng
                    boolean isUpdate = (idGt > 0);
                    Entity.giaiThuongETT gt = null;
                    
                    // Nếu là Cập nhật, moi cái cũ trong RAM ra
                    if (isUpdate) {
                        for (Entity.giaiThuongETT item : this.ds) {
                            if (item.getIdGt() == idGt) { // Chỗ này check lại tên getter ID của bảng Giải Thưởng
                                gt = item;
                                break;
                            }
                        }
                    }
                    
                    // Nếu không tìm thấy (hoặc ID = 0 là thêm mới), tạo mới tinh
                    if (gt == null) {
                        gt = new Entity.giaiThuongETT();
                        gt.setDoiTuong("Học sinh giỏi"); // Set mặc định theo logic form Insert
                        isUpdate = false;
                    }

                    // --- CHỈ GHI ĐÈ NHỮNG CỘT CÓ TRONG EXCEL ---
                    gt.setCccd(getCellSafe(row, 1));
                    gt.setCapGiai(getCellSafe(row, 2));
                    gt.setMaMon(getCellSafe(row, 3));
                    gt.setLoaiGiai(getCellSafe(row, 4));
                    gt.setDiemCongCoMon(parseDoubleSafe(getCellSafe(row, 5)));
                    gt.setDiemCongKhongMon(parseDoubleSafe(getCellSafe(row, 6)));

                    // --- QUYẾT ĐỊNH THÊM HAY SỬA XUỐNG DB ---
                    boolean success;
                    if (isUpdate) {
                        success = dao.suaGiaiThuong(gt); // GỌI HÀM SỬA
                        if (success) soDongCapNhat++; else soDongThatBai++;
                    } else {
                        success = dao.themGiaiThuong(gt); // GỌI HÀM THÊM
                        if (success) soDongThemMoi++; else soDongThatBai++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                    soDongThatBai++; 
                }
            }
            this.ds = null;       // Hủy ds cũ
            this.layDanhSach();   // Lấy ds mới
            // Thông báo kết quả chi tiết
            return "Thêm mới: " + soDongThemMoi + " dòng.\nCập nhật: " + soDongCapNhat + " dòng.\nLỗi/Bỏ qua: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
    
        private String getCellSafe(java.util.ArrayList<String> row, int index) {
        if (row != null && index < row.size()) {
            return row.get(index);
        }
        return "";
    }
    
    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try { return Double.parseDouble(value.trim()); } 
        catch (Exception e) { return 0.0; }
    }
}
