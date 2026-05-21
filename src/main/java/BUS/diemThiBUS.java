/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.diemThiDAO;
import Entity.diemThiETT;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class diemThiBUS {
    public static ArrayList<diemThiETT> ds;
    diemThiDAO data = new diemThiDAO();

    public ArrayList<diemThiETT> layDanhSach() {
        ds = data.layDanhSach();
        return ds;
    }

    public boolean kiemTraRong(String text) {
        return text == null || text.trim().isEmpty();
    }

    public String validate(diemThiETT obj, boolean isCreate) {
        if (obj == null) {
            return "Dữ liệu điểm thi không hợp lệ";
        }
        if (kiemTraRong(obj.getCccd())) {
            return "CCCD không được để trống";
        }
        if (obj.getCccd().trim().length() > 20) {
            return "CCCD tối đa 20 ký tự";
        }

        if (isCreate) {
            if (ds == null) {
                layDanhSach();
            }
            if (ds != null) {
                for (diemThiETT item : ds) {
                    if (item != null && item.getCccd() != null
                            && item.getCccd().equalsIgnoreCase(obj.getCccd())) {
                        return "CCCD đã tồn tại trong bảng điểm";
                    }
                }
            }
        }

        return null;
    }

    public diemThiETT timTheoId(int id) {
        if (ds == null) {
            layDanhSach();
        }
        if (ds == null) {
            return null;
        }
        for (diemThiETT dt : ds) {
            if (dt != null && dt.getIddiemthi() == id) {
                return dt;
            }
        }
        return null;
    }

    public boolean themDiemThi(diemThiETT obj) {
        String err = validate(obj, true);
        if (err != null) {
            return false;
        }
        boolean ok = data.themDiemThi(obj);
        if (ok) {
            if (ds == null) {
                ds = new ArrayList<>();
            }
            ds.add(obj);
        }
        return ok;
    }

    public boolean suaDiemThi(diemThiETT obj) {
        String err = validate(obj, false);
        if (err != null) {
            return false;
        }
        boolean ok = data.suaDiemThi(obj);
        if (ok && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i) != null && ds.get(i).getIddiemthi() == obj.getIddiemthi()) {
                    ds.set(i, obj);
                    break;
                }
            }
        }
        return ok;
    }

    public boolean xoaDiemThi(diemThiETT obj) {
        if (obj == null) {
            return false;
        }
        boolean ok = data.xoaDiemThi(obj);
        if (ok && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i) != null && ds.get(i).getIddiemthi() == obj.getIddiemthi()) {
                    ds.remove(i);
                    break;
                }
            }
        }
        return ok;
    }

    public diemThiETT layDiemTheoCCCD(String cccd) {
        // Đảm bảo dữ liệu đã được load lên
        if (ds == null) {
            layDanhSach();
        }
        
        for (diemThiETT dt : ds) {
            if (dt.getCccd() != null && dt.getCccd().equals(cccd)) {
                return dt;
            }
        }
        
        return null;
    }
    
    public String nhapDuLieuTuExcel(String filePath) {
        try {
            // Lấy ma trận dữ liệu từ Excel
            ArrayList<ArrayList<String>> dataExcel = EXCEL.ExcelHelper.docFileExcel(filePath);
            
            if (dataExcel.size() <= 1) {
                return "File Excel trống hoặc chỉ có dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            for (int i = 1; i < dataExcel.size(); i++) {
                ArrayList<String> row = dataExcel.get(i);
                
                try {
                    Entity.diemThiETT dt = new Entity.diemThiETT();
                    
                    // Cột 0: CCCD
                    String cccd = row.get(0).trim();
                    if (cccd.isEmpty()) {
                        soDongThatBai++;
                        continue; 
                    }
                    dt.setCccd(cccd);

                    // Cột 1 & 2: SBD và Phương thức
                    dt.setSobaodanh(row.get(1).trim());
                    dt.setdPhuongthuc(row.get(2).trim());

                    // Đổ dữ liệu các cột Điểm (Bắt đầu từ index 3)
                    dt.setTo(parseDoubleSafe(row.get(3)));
                    dt.setLi(parseDoubleSafe(row.get(4)));
                    dt.setHo(parseDoubleSafe(row.get(5)));
                    dt.setSi(parseDoubleSafe(row.get(6)));
                    dt.setSu(parseDoubleSafe(row.get(7)));
                    dt.setDi(parseDoubleSafe(row.get(8)));
                    dt.setVa(parseDoubleSafe(row.get(9)));
                    dt.setN1Thi(parseDoubleSafe(row.get(10)));
                    dt.setN1Cc(parseDoubleSafe(row.get(11)));
                    
                    dt.setCncn(parseDoubleSafe(row.get(12)));
                    dt.setCnnn(parseDoubleSafe(row.get(13)));
                    dt.setTi(parseDoubleSafe(row.get(14)));
                    dt.setKtpl(parseDoubleSafe(row.get(15)));
                    dt.setNl1(parseDoubleSafe(row.get(16)));

                    dt.setNk1(parseDoubleSafe(row.get(17)));
                    dt.setNk2(parseDoubleSafe(row.get(18)));
                    dt.setNk3(parseDoubleSafe(row.get(19)));
                    dt.setNk4(parseDoubleSafe(row.get(20)));
                    dt.setNk5(parseDoubleSafe(row.get(21)));
                    dt.setNk6(parseDoubleSafe(row.get(22)));

                    // 🚀 THÊM VÀO DATABASE (Nhớ sửa tên DAO cho khớp)
                    if (data.themDiemThi(dt)) { 
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }
                } catch (Exception ex) {
                    soDongThatBai++; 
                }
            }
            return "Nhập thành công: " + soDongThanhCong + " dòng.\nLỗi/Trùng lặp: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }

    // Tiện ích chống lỗi khi Parse Double (Nếu chuỗi rỗng thì gán Null)
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
