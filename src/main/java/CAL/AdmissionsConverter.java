package CAL;

import Entity.bangQuyDoiETT;
import java.util.ArrayList;

public class AdmissionsConverter {

    // 1. Công thức nội suy tổng quát (GIỮ NGUYÊN)
    public static double interpolate(double x, double a, double b, double c, double d) {
        if (x <= a) return c;
        if (x >= b) return d;
        double result = c + ((x - a) / (b - a)) * (d - c);
        return Math.round(result * 100.0) / 100.0;
    }

    // 2. HÀM QUY ĐỔI ĐA NĂNG (MỚI)
    public static double quyDoiDiemChung(String phuongThuc, String toHop, String mon, double diemGoc, ArrayList<bangQuyDoiETT> dsQuyDoi) {
        if (diemGoc <= 0) return 0;
        
        Entity.bangQuyDoiETT moc = null;

        if (dsQuyDoi != null) {
            for (bangQuyDoiETT qd : dsQuyDoi) {
                // Chú ý: Ông nhớ đổi tên hàm get() cho khớp với Entity của ông nha (VD: getD_phuongthuc hoặc getDPhuongthuc)
                String ptDB = qd.getPhuongThuc()!= null ? qd.getPhuongThuc().trim() : "";
                
                // Bước 1: Khớp Phương Thức
                if (ptDB.equalsIgnoreCase(phuongThuc.trim())) {
                    boolean isMatch = false;

                    // Bước 2: Rẽ nhánh kiểm tra theo đặc thù của Phương Thức
                    if (phuongThuc.equals("Đánh giá V-SAT")) {
                        // V-SAT: Tra cứu theo Tên Môn
                        String monDB = qd.getMon()!= null ? qd.getMon().trim() : "";
                        if (monDB.equalsIgnoreCase(mon.trim())) {
                            isMatch = true;
                        }
                    } 
                    else if (phuongThuc.equals("ĐGNL HCM")) {
                        // ĐGNL: Tra cứu theo Tổ Hợp
                        String toHopDB = qd.getToHop()!= null ? qd.getToHop().trim() : "";
                        if (toHopDB.equalsIgnoreCase(toHop.trim())) {
                            isMatch = true;
                        }
                    }

                    // Bước 3: Nếu khớp môn/tổ hợp -> Kiểm tra xem điểm có nằm trong khoảng [diema, diemb] không
                    if (isMatch && diemGoc >= qd.getDiemA()&& diemGoc <= qd.getDiemB()) {
                        moc = qd;
                        break; // Bắt được mốc rồi thì thoát vòng lặp
                    }
                }
            }
        }
        
        if (moc != null) {
            return interpolate(diemGoc, moc.getDiemA(), moc.getDiemB(), moc.getDiemC(), moc.getDiemD());
        } else {
            System.out.println("❌ CẢNH BÁO: Không tìm thấy mốc quy đổi cho: " + phuongThuc + " | Tổ hợp: " + toHop + " | Môn: " + mon + " | Điểm: " + diemGoc);
            return 1.0; 
        }
    }
}