/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.chungChiETT;
import java.util.ArrayList;
import DAO.chungChiDAO;
/**
 *
 * @author mhoang
 */
public class chungChiBUS {
    public static ArrayList<chungChiETT> ds;
    chungChiDAO data = new chungChiDAO();
    public ArrayList<chungChiETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }

    public chungChiETT findById(int id) {
        if (ds == null) layDanhSach();
        for (chungChiETT cc : ds) {
            if (cc.getIdCc() == id) return cc;
        }
        return null;
    }

    public boolean themCC(chungChiETT cc) {
        boolean isSuccess = data.themCC(cc);
        if (isSuccess) {
            if (ds != null) {
                ds.add(cc);
            }
        }
        return isSuccess;
    }

    public boolean suaCC(chungChiETT ccMoi) {
        if (data.suaCC(ccMoi)) { 

            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng ID làm khóa định danh duy nhất
                    if (ds.get(i).getIdCc() == ccMoi.getIdCc()) {
                        ds.set(i, ccMoi); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean xoaCC(int idCc) {
        if (data.xoaCC(idCc)) {
            // Xóa trên RAM để GUI render lại chính xác
            if (ds != null) {
                ds.removeIf(item -> item.getIdCc() == idCc);
            }
            return true;
        }
        return false;
    }
    
    public boolean kiemTraRong(String text) {
        return text == null || text.trim().isEmpty();
    }

    public boolean kiemTraSoThuc(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean checkToHopCoMonAnh(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return false;
        }
        return data.checkToHopCoMonAnh(cccd);
    }
    
    public double[] layDiemIELTS(String cccd) {
        if (kiemTraRong(cccd)) {
            return new double[]{0.0, 0.0};
        }
        return data.layDiemIELTS(cccd);
    }
    
    public ArrayList<chungChiETT> timKiemCoBan(String tim, int index) {
        if(ds == null) {
            layDanhSach();
        }

        ArrayList<chungChiETT> dskq = new ArrayList<>();
        String tuKhoa = tim.trim().toLowerCase();

        for(chungChiETT ct : ds) {
            switch (index) {
                case 0: // Tìm theo ID Chứng Chỉ (int)
                    if(String.valueOf(ct.getIdCc()).toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 1: // Tìm theo CCCD (String)
                    if(ct.getCccd() != null && ct.getCccd().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 2: // Tìm theo Loại chứng chỉ (String)
                    if(ct.getLoaiChungChi() != null && ct.getLoaiChungChi().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 3: // Tìm theo Điểm chứng chỉ (String)
                    if(ct.getDiemChungChi() != null && ct.getDiemChungChi().toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 4: // Tìm theo Điểm quy đổi (Double)
                    if(ct.getDiemQuyDoi() != null && String.valueOf(ct.getDiemQuyDoi()).toLowerCase().contains(tuKhoa)) {
                        dskq.add(ct);
                    }
                    break;

                case 5: // Tìm theo Điểm cộng (Double)
                    if(ct.getDiemCong() != null && String.valueOf(ct.getDiemCong()).toLowerCase().contains(tuKhoa)) {
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
            DAO.chungChiDAO dao = new DAO.chungChiDAO(); 

            // Cập nhật RAM mới nhất trước khi xử lý để đối chiếu
            if (this.ds == null) this.layDanhSach();

            for (int i = 1; i < data.size(); i++) {
                java.util.ArrayList<String> row = data.get(i);
                
                try {
                    // --- ĐỌC ID ĐỂ XÁC ĐỊNH LÀ THÊM HAY SỬA ---
                    int idCc = (int) parseDoubleSafe(getCellSafe(row, 0)); // Cột A: ID Chứng Chỉ
                    boolean isUpdate = (idCc > 0);
                    Entity.chungChiETT cc = null;
                    
                    // Nếu là Cập nhật, moi cái cũ trong RAM ra
                    if (isUpdate) {
                        for (Entity.chungChiETT item : this.ds) {
                            if (item.getIdCc() == idCc) {
                                cc = item;
                                break;
                            }
                        }
                    }
                    
                    // Nếu không tìm thấy (hoặc ID = 0 là thêm mới), tạo mới tinh
                    if (cc == null) {
                        cc = new Entity.chungChiETT();
                        isUpdate = false;
                    }

                    // --- CHỈ GHI ĐÈ NHỮNG CỘT CÓ TRONG EXCEL ---
                    cc.setCccd(getCellSafe(row, 1));
                    cc.setLoaiChungChi(getCellSafe(row, 2));
                    cc.setDiemChungChi(getCellSafe(row, 3));
                    cc.setDiemQuyDoi(parseDoubleSafe(getCellSafe(row, 4)));
                    cc.setDiemCong(parseDoubleSafe(getCellSafe(row, 5)));

                    // --- QUYẾT ĐỊNH THÊM HAY SỬA XUỐNG DB ---
                    boolean success;
                    if (isUpdate) {
                        success = dao.suaCC(cc); // GỌI HÀM SỬA
                        if (success) soDongCapNhat++; else soDongThatBai++;
                    } else {
                        success = dao.themCC(cc); // GỌI HÀM THÊM
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
