package BUS;

import DAO.diemCongDAO;
import Entity.diemCongETT;
import java.util.ArrayList;

public class diemCongBUS {

    public static ArrayList<diemCongETT> ds;
    private diemCongDAO dao = new diemCongDAO();

    // ================= LOAD =================
    public ArrayList<diemCongETT> layDanhSach() {
        if (ds == null) {
            ds = dao.layDanhSach();
        }
        return ds;
    }

    // ================= THÊM =================
    public boolean themDiemCong(diemCongETT dc) {
        boolean result = dao.themDiemCong(dc);
        if (result && ds != null) {
            ds.add(dc);
        }
        return result;
    }

    // ================= SỬA =================
    public boolean suaDiemCong(diemCongETT dc) {
        boolean result = dao.suaDiemCong(dc);

        if (result && ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getIdDiemCong() == dc.getIdDiemCong()) {
                    ds.set(i, dc);
                    break;
                }
            }
        }
        return result;
    }

    // ================= XÓA =================
    public boolean xoaDiemCong(diemCongETT dc) {
        boolean result = dao.xoaDiemCong(dc);

        if (result && ds != null) {
            ds.removeIf(item -> item.getIdDiemCong() == dc.getIdDiemCong());
        }

        return result;
    }
    
    // ================== LẤY ĐIỂM CỘNG ==================
    public diemCongETT layDiemCongChinhXac(String cccd, String maNganh, String maToHop, String phuongThuc) {
        return dao.layDiemCongChinhXac(cccd, maNganh, maToHop, phuongThuc);
    }
}