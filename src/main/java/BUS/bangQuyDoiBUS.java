package BUS;

import DAO.bangQuyDoiDAO;
import Entity.bangQuyDoiETT;
import java.util.ArrayList;

public class bangQuyDoiBUS {

    public static ArrayList<bangQuyDoiETT> ds;
    private final bangQuyDoiDAO data = new bangQuyDoiDAO();

    public ArrayList<bangQuyDoiETT> layDanhSach() {
        ds = data.layDanhSach();
        return ds;
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

    public String validate(bangQuyDoiETT obj, boolean isCreate) {
        if (obj == null) {
            return "Dữ liệu quy đổi không hợp lệ";
        }
        if (kiemTraRong(obj.getMaQuyDoi())) {
            return "Mã quy đổi không được để trống";
        }
        if (kiemTraRong(obj.getMon())) {
            return "Môn không được để trống";
        }
        if (obj.getDiemA() == null || obj.getDiemB() == null || obj.getDiemC() == null || obj.getDiemD() == null) {
            return "Điểm A/B/C/D không được để trống";
        }
        if (obj.getDiemA() >= obj.getDiemB()) {
            return "Điểm A phải nhỏ hơn Điểm B";
        }
        if (obj.getDiemC() >= obj.getDiemD()) {
            return "Điểm C phải nhỏ hơn Điểm D";
        }

        if (isCreate && ds != null) {
            for (bangQuyDoiETT item : ds) {
                if (item != null && item.getMaQuyDoi() != null
                        && item.getMaQuyDoi().equalsIgnoreCase(obj.getMaQuyDoi())) {
                    return "Mã quy đổi đã tồn tại";
                }
            }
        }

        return null;
    }

    public boolean themQuyDoi(bangQuyDoiETT obj) {
        String err = validate(obj, true);
        if (err != null) {
            return false;
        }

        boolean ok = data.themQuyDoi(obj);
        if (ok) {
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds.add(obj);
        }
        return ok;
    }

    public boolean suaQuyDoi(bangQuyDoiETT obj) {
        String err = validate(obj, false);
        if (err != null) {
            return false;
        }

        boolean ok = data.suaQuyDoi(obj);
        if (ok && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getIdqd() == obj.getIdqd()) {
                    ds.set(i, obj);
                    break;
                }
            }
        }
        return ok;
    }

    public boolean xoaQuyDoi(bangQuyDoiETT obj) {
        boolean ok = data.xoaQuyDoi(obj);
        if (ok && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getIdqd() == obj.getIdqd()) {
                    ds.remove(i);
                    break;
                }
            }
        }
        return ok;
    }
    
    public String nhapDuLieuTuExcel(String filePath) {
        try {
            ArrayList<ArrayList<String>> dataExcel = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (dataExcel.size() <= 1) {
                return "File Excel trống hoặc chỉ có dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            for (int i = 1; i < dataExcel.size(); i++) {
                ArrayList<String> row = dataExcel.get(i);
                
                try {
                    Entity.bangQuyDoiETT qd = new Entity.bangQuyDoiETT();
                    
                    // Cột 0 là ID -> Tự tăng trong DB nên mình bỏ qua không đọc
                    
                    // Bắt đầu đọc từ cột 1
                    qd.setPhuongThuc(row.get(1).trim());
                    qd.setToHop(row.get(2).trim());
                    qd.setMon(row.get(3).trim());
                    
                    qd.setDiemA(parseDoubleSafe(row.get(4)));
                    qd.setDiemB(parseDoubleSafe(row.get(5)));
                    qd.setDiemC(parseDoubleSafe(row.get(6)));
                    qd.setDiemD(parseDoubleSafe(row.get(7)));
                    
                    // Mã quy đổi (Unique - Cẩn thận trùng)
                    String maQuyDoi = row.get(8).trim();
                    if (maQuyDoi.isEmpty()) {
                        soDongThatBai++;
                        continue; // Bắt buộc phải có mã quy đổi
                    }
                    qd.setMaQuyDoi(maQuyDoi);
                    
                    qd.setPhanVi(row.get(9).trim());

                    // 🚀 GỌI HÀM DAO ĐỂ LƯU XUỐNG DB (Sửa tên cho khớp hàm DAO của ông nha)
                    if (data.themQuyDoi(qd)) { 
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    soDongThatBai++; // Lỗi parse số hoặc thiếu cột
                }
            }
            return "Nhập thành công: " + soDongThanhCong + " dòng.\nLỗi/Trùng lặp: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }

    // Tiện ích chống lỗi parse số Double
    private Double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
