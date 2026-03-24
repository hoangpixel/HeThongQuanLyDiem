package CAL;

import DAO.bangQuyDoiDAO;
import Entity.bangQuyDoiETT;

public class AdmissionsConverter {

    // 1. Công thức nội suy tổng quát (Giữ lại để tính toán)
    public static double interpolate(double x, double a, double b, double c, double d) {
        if (x <= a) return c;
        if (x >= b) return d;
        double result = c + ((x - a) / (b - a)) * (d - c);
        return Math.round(result * 100.0) / 100.0;
    }

    // 2. HÀM TỔNG LỰC: Quy đổi mọi môn V-SAT bằng Database
//    // Bạn dùng hàm này thay cho switch-case hay if-else thủ công
//    public static double quyDoiVsat(String tenMon, double diemGoc) {
//        // Nếu điểm bằng 0 thì khỏi tra cho tốn tài nguyên
//        if (diemGoc <= 0) return 0;
//
//        bangQuyDoiDAO dao = new bangQuyDoiDAO();
//        // Gọi hàm tìm mốc từ DB mà mình vừa viết lúc nãy
//        bangQuyDoiETT moc = dao.timMocQuyDoi(tenMon, diemGoc);
//        
//        if (moc != null) {
//            // Có mốc rồi thì lắp vào công thức nội suy thôi
//            return interpolate(diemGoc, moc.getDiemA(), moc.getDiemB(), moc.getDiemC(), moc.getDiemD());
//        }
//        
//        // Trường hợp không tìm thấy mốc (có thể do điểm quá thấp hoặc quá cao ngoài bảng)
//        // Mình có thể trả về điểm gốc hoặc một mức sàn mặc định
//        return (diemGoc > 0) ? 1.0 : 0; 
//    }
    public static double quyDoiVsat(String tenMon, double diemGoc) {
    if (diemGoc <= 0) return 0;
    
    DAO.bangQuyDoiDAO dao = new DAO.bangQuyDoiDAO();
    // Dùng .trim() để lỡ tên môn có khoảng trắng dư (như "TO ") thì nó cắt đi luôn
    Entity.bangQuyDoiETT moc = dao.timMocQuyDoi(tenMon.trim(), diemGoc);
    
    if (moc != null) {
        double diemQuyDoi = interpolate(diemGoc, moc.getDiemA(), moc.getDiemB(), moc.getDiemC(), moc.getDiemD());
        // In ra để check xem máy tính đúng không
        System.out.println("✅ Đã quy đổi môn " + tenMon + ": Điểm V-SAT " + diemGoc + " -> Điểm THPT " + diemQuyDoi);
        return diemQuyDoi;
    } else {
        // Nếu in ra dòng này, 100% là do Database chưa có dòng mốc điểm đó
        System.out.println("❌ CẢNH BÁO: Database thiếu mốc quy đổi cho môn [" + tenMon + "] với mức điểm [" + diemGoc + "]");
        return 1.0; 
    }
}
}