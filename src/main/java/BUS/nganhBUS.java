/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.nganhETT;
import DAO.nganhDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class nganhBUS {
    public static ArrayList<nganhETT> ds;
    nganhDAO data = new nganhDAO();
    public ArrayList<nganhETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }

    public nganhETT findById(int id) {
        if (ds == null) layDanhSach();
        for (nganhETT item : ds) {
            if (item.getIdnganh() == id) return item;
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

    public boolean themNganh(nganhETT nganhMoi) {
        boolean isSuccess = data.themNganh(nganhMoi);
        if (isSuccess) {
            if (ds != null) 
            {
                ds.add(nganhMoi);
            }
        }

        return isSuccess;
    }

    public boolean suaNganh(nganhETT nganhDaSua) {
        // 1. Lưu xuống Database trước qua DAO
        if (data.suaNganh(nganhDaSua)) { 
        
            // 2. Nếu Database OK, tiến hành cập nhật lại biến ds tĩnh (RAM)
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng idnganh để dò tìm vì nó là khóa chính duy nhất (Unique)
                    // idnganh là kiểu int nên dùng == để so sánh
                    if (ds.get(i).getIdnganh() == nganhDaSua.getIdnganh()) {
                    ds.set(i, nganhDaSua); // Đè đối tượng mới vào vị trí cũ để UI cập nhật ngay
                    break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean xoaNganh(Entity.nganhETT nganhCanXoa) {
        // Sửa data.xoaNganh thành data.xoa
        if (data.xoaNganh(nganhCanXoa)) {
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dò đúng cái ID đó thì gạch tên khỏi danh sách
                    if (ds.get(i).getIdnganh() == nganhCanXoa.getIdnganh()) {
                        ds.remove(i); 
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean capNhatChiTieuThucTe() {
        nganhDAO dao = new nganhDAO();
        return dao.capNhatChiTieuThucTe();
    }

    public ArrayList<nganhETT> timKiemCoBan(String tim, int index)
    {
        if(ds == null)
        {
            layDanhSach();
        }
        
        ArrayList<nganhETT> dskq = new ArrayList<nganhETT>();
        String tuKhoa = tim.trim().toLowerCase();
        
        for(nganhETT ct : ds)
        {
            switch (index) {
                case 0: // Tìm theo ID Ngành
                    if(String.valueOf(ct.getIdnganh()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 1: // Tìm theo Mã Ngành
                    if(ct.getManganh() != null && ct.getManganh().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 2: // Tìm theo Tên Ngành
                    if(ct.getTennganh() != null && ct.getTennganh().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 3: // Tìm theo Tổ hợp gốc
                    if(ct.getN_tohopgoc() != null && ct.getN_tohopgoc().toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 4: // Tìm theo Chỉ tiêu
                    if(ct.getN_chitieu() != null && String.valueOf(ct.getN_chitieu()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 5: // Tìm theo Sàn THPT
                    if(ct.getN_diemsanthpt() != null && String.valueOf(ct.getN_diemsanthpt()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 6: // Tìm theo Sàn V-SAT
                    if(ct.getN_diemsanvsat() != null && String.valueOf(ct.getN_diemsanvsat()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 7: // Tìm theo Sàn ĐGNL
                    if(ct.getN_diemsandgnl() != null && String.valueOf(ct.getN_diemsandgnl()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 8: // Tìm theo Chuẩn THPT
                    if(ct.getDiemchuan_thpt() != null && String.valueOf(ct.getDiemchuan_thpt()).toLowerCase().contains(tuKhoa)) 
                    {
                        dskq.add(ct);
                    }
                    break;
                case 9: // Tìm theo Chuẩn V-SAT
                    if(ct.getDiemchuan_vsat() != null && String.valueOf(ct.getDiemchuan_vsat()).toLowerCase().contains(tuKhoa)) 
                    {
                        dskq.add(ct);
                    }
                    break;
                case 10: // Tìm theo Chuẩn ĐGNL
                    if(ct.getDiemchuan_dgnl() != null && String.valueOf(ct.getDiemchuan_dgnl()).toLowerCase().contains(tuKhoa))
                    {
                        dskq.add(ct);
                    }
                    break;
                case 11: // Tìm theo SL ĐK
                    if(ct.getSlDangKy() != null && String.valueOf(ct.getSlDangKy()).toLowerCase().contains(tuKhoa))
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

    public String nhapDuLieuTuExcel(String filePath) {
        try {
            java.util.ArrayList<java.util.ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThemMoi = 0;
            int soDongCapNhat = 0;
            int soDongThatBai = 0;
            DAO.nganhDAO dao = new DAO.nganhDAO(); 

            // Cập nhật RAM mới nhất trước khi xử lý để đối chiếu
            if (this.ds == null) this.layDanhSach();

            for (int i = 1; i < data.size(); i++) {
                java.util.ArrayList<String> row = data.get(i);
                
                try {
                    // --- ĐỌC ID ĐỂ XÁC ĐỊNH LÀ THÊM HAY SỬA (DÙNG HÀM GET SAFE) ---
                    int idNganh = (int) parseDoubleSafe(getCellSafe(row, 0)); // Cột A: ID Ngành
                    boolean isUpdate = (idNganh > 0);
                    Entity.nganhETT ng = null;
                    
                    // Nếu là Cập nhật, moi cái cũ trong RAM ra để giữ nguyên mấy cái Checkbox
                    if (isUpdate) {
                        for (Entity.nganhETT item : this.ds) {
                            if (item.getIdnganh() == idNganh) {
                                ng = item;
                                break;
                            }
                        }
                    }
                    
                    // Nếu không tìm thấy (hoặc ID = 0 là thêm mới), tạo mới tinh
                    if (ng == null) {
                        ng = new Entity.nganhETT();
                        isUpdate = false;
                        
                        // Mặc định cho dòng mới tinh không có trong Excel
                        ng.setN_tuyenthang("0"); ng.setSl_xtt(0);
                        ng.setN_dgnl("0");       ng.setSl_dgnl(0);
                        ng.setN_thpt("0");       ng.setSl_thpt(0);
                        ng.setN_vsat("0");       ng.setSl_vsat(0);
                    }

                    // --- CHỈ GHI ĐÈ NHỮNG CỘT CÓ TRONG EXCEL (DÙNG HÀM GET SAFE) ---
                    ng.setManganh(getCellSafe(row, 1));
                    ng.setTennganh(getCellSafe(row, 2));
                    ng.setN_tohopgoc(getCellSafe(row, 3));
                    ng.setN_chitieu((int) parseDoubleSafe(getCellSafe(row, 4)));
                    ng.setN_diemsanthpt(parseDoubleForNganh(getCellSafe(row, 5)));
                    ng.setN_diemsanvsat(parseDoubleForNganh(getCellSafe(row, 6)));
                    ng.setN_diemsandgnl(parseDoubleForNganh(getCellSafe(row, 7)));
                    //ng.setN_diemtrungtuyen(parseDoubleForNganh(getCellSafe(row, 8)));
                    ng.setDiemchuan_thpt(parseDoubleForNganh(getCellSafe(row, 8)));
                    ng.setDiemchuan_vsat(parseDoubleForNganh(getCellSafe(row, 9)));
                    ng.setDiemchuan_dgnl(parseDoubleForNganh(getCellSafe(row, 10)));
                    ng.setSlDangKy((int) parseDoubleSafe(getCellSafe(row, 11)));

                    // --- QUYẾT ĐỊNH THÊM HAY SỬA XUỐNG DB ---
                    boolean success;
                    if (isUpdate) {
                        success = dao.suaNganh(ng); // GỌI HÀM SỬA
                        if (success) soDongCapNhat++; else soDongThatBai++;
                    } else {
                        success = dao.themNganh(ng); // GỌI HÀM THÊM
                        if (success) soDongThemMoi++; else soDongThatBai++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(); // <--- Rất quan trọng: In lỗi ra console để dò
                    soDongThatBai++; 
                }
            }
            // Thông báo kết quả chi tiết
            return "Thêm mới: " + soDongThemMoi + " dòng.\nCập nhật: " + soDongCapNhat + " dòng.\nLỗi/Bỏ qua: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }

    // --- CÁC HÀM PHỤ TRỢ ---
    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try { return Double.parseDouble(value.trim()); } 
        catch (Exception e) { return 0.0; }
    }

    private Double parseDoubleForNganh(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try { return Double.valueOf(value.trim()); } 
        catch (Exception e) { return null; }
    }
    
    private String getCellSafe(java.util.ArrayList<String> row, int index) {
        if (row != null && index < row.size()) {
            return row.get(index);
        }
        return "";
    }
    
    public boolean capNhatSoLuongDangKy(String maNganh, int soLuong) {
        if (maNganh == null || maNganh.trim().isEmpty() || soLuong < 0) {
            return false;
        }
        return data.capNhatSoLuongDangKy(maNganh, soLuong);
    }
}