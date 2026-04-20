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

    // =======================================================
    // HÀM QUAN TRỌNG: TÌM BẢNG ĐIỂM CỦA 1 NGƯỜI DỰA VÀO CCCD
    // =======================================================
    public diemThiETT layDiemTheoCCCD(String cccd) {
        // Đảm bảo dữ liệu đã được load lên
        if (ds == null) {
            layDanhSach();
        }
        
        // Chạy vòng lặp tìm ông nào có CCCD khớp thì bế điểm ổng ra
        for (diemThiETT dt : ds) {
            if (dt.getCccd() != null && dt.getCccd().equals(cccd)) {
                return dt;
            }
        }
        
        return null; // Không tìm thấy (Ông này chưa thi hoặc chưa nhập điểm)
    }
}
