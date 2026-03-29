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
}
