package BUS;

import DAO.diemCongDAO;
import DAO.nguyenVongXetTuyenDAO;
import EXCEL.ExcelHelper;
import Entity.diemCongETT;
import Entity.nguyenVongXetTuyenETT;
import Entity.thiSinhXetTuyenETT;
import FUNC_GUI.excelDiemCong;
import BUS.diemCongBUS;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
    public void hienThiExcel()
    {
        JFrame topFrame = (JFrame) SwingUtilities.windowForComponent(null);
        excelDiemCong dialog = new excelDiemCong(topFrame, true);
        dialog.setVisible(true);
        if(dialog.getXacNhanImport())
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Gọi BUS xử lý và nhận thông báo kết quả
            String thongBao = this.nhapDuLieuTuExcel(filePath);
            
            JOptionPane.showMessageDialog(null, thongBao, "Kết quả Nhập Excel", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            diemCongBUS.ds = null; // reset cache (nếu có)
            // Xong xuôi thì làm mới lại cái bảng trên màn hình
            diemCongBUS.ds = null;
        }
        }else if(dialog.getXacNhanExport())
        {
            ArrayList<diemCongETT> fullDanhSach = this.layDanhSach();
            ExcelHelper.xuatDanhSachDiemCongRaExcel(fullDanhSach,null, "DanhSachDiemCong");
        }
    }
    public ArrayList<diemCongETT> timKiemCoBan(String key, int index) {
        if (ds == null) layDanhSach();

        ArrayList<diemCongETT> result = new ArrayList<>();

        for (diemCongETT dc : ds) {
            switch (index) {
                case 0: // CCCD
                    if (dc.getTsCccd().contains(key)) result.add(dc);
                    break;

                case 1: // Mã ngành
                    if (dc.getMaNganh().contains(key)) result.add(dc);
                    break;

                case 2: // Mã tổ hợp
                    if (dc.getMaToHop().contains(key)) result.add(dc);
                    break;

                case 3: // Phương thức
                    if (dc.getPhuongThuc().toLowerCase().contains(key.toLowerCase()))
                        result.add(dc);
                    break;
            }
        }

        return result;
    }
    private double parseDoubleSafe(String val) {
        try {
            if (val == null || val.trim().isEmpty()) return 0.0;

            // đổi dấu phẩy thành dấu chấm
            val = val.replace(",", ".");

            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    public String nhapDuLieuTuExcel(String filePath) {
        try {
            ArrayList<ArrayList<String>> data = EXCEL.ExcelHelper.docFileExcel(filePath);

            if (data.size() <= 1) {
                return "File Excel trống hoặc chỉ có mỗi dòng Tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            for (int i = 1; i < data.size(); i++) {
                ArrayList<String> row = data.get(i);

                try {
                    // ⚠️ check đủ cột
                    if (row.size() < 8) {
                        throw new RuntimeException("Thiếu cột dữ liệu");
                    }

                    diemCongETT dc = new diemCongETT();

                    // mapping
                    dc.setTsCccd(row.get(0).trim());
                    dc.setMaNganh(row.get(1).trim());
                    dc.setMaToHop(row.get(2).trim());
                    dc.setPhuongThuc(row.get(3).trim());
                    dc.setDcKeys(dc.getTsCccd() + "_" 
                        + dc.getMaNganh() + "_" 
                        + dc.getMaToHop() + "_" 
                        + dc.getPhuongThuc());
                    dc.setDiemCC(parseDoubleSafe(row.get(4)));
                    dc.setDiemUtxt(parseDoubleSafe(row.get(5)));
                    dc.setDiemTong(parseDoubleSafe(row.get(6)));

                    dc.setGhiChu(row.get(7) != null ? row.get(7) : "");

                    // ⚠️ validate cơ bản
                    if (dc.getTsCccd().isEmpty()) {
                        throw new RuntimeException("CCCD rỗng");
                    }

                    // insert DB
                    if (dao.themDiemCong(dc)) {
                        soDongThanhCong++;
                    } else {
                        System.out.println("❌ DB từ chối dòng " + i);
                        soDongThatBai++;
                    }

                } catch (Exception ex) {
                    System.out.println("❌ Lỗi dòng " + i + ": " + ex.getMessage());
                    soDongThatBai++;
                }
            }

            return "Nhập thành công: " + soDongThanhCong +
                   " dòng.\nLỗi: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
}