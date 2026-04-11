package EXCEL;

import Entity.nganhToHopETT;
import Entity.toHopETT;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import Entity.thiSinhXetTuyenETT; 
import Entity.diemCongETT;
import Entity.nguyenVongXetTuyenETT; // Nhớ import entity của ông nha
import Entity.phanQuyenETT;
import Entity.taiKhoanETT;


public class ExcelHelper {

    // =========================================================================
    // HÀM 1: XUẤT JTABLE (Dùng cho mấy bảng nhỏ không phân trang)
    // =========================================================================
    public static void xuatJTableRaExcel(JTable table, java.awt.Component parent, String tenBang) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
            fileChooser.setFileFilter(filter);
            
            // FIX LỖI 1: Gắn sẵn tên file mặc định vào hộp thoại
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx")); 

            int userSelection = fileChooser.showSaveDialog(parent);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(table.getColumnName(i));
                }

                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = table.getValueAt(i, j);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // HÀM PHỤ TRỢ 1: TẠO GIAO DIỆN CHO DÒNG TIÊU ĐỀ (HEADER)
    // =========================================================================
    public static org.apache.poi.ss.usermodel.CellStyle taoStyleTieuDe(Workbook workbook) {
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
        headerFont.setFontHeightInPoints((short) 12);

        org.apache.poi.ss.usermodel.CellStyle style = workbook.createCellStyle();
        style.setFont(headerFont);
        style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        
        return style;
    }

    // =========================================================================
    // HÀM PHỤ TRỢ 2: TẠO GIAO DIỆN CHO DÒNG DỮ LIỆU BÌNH THƯỜNG
    // =========================================================================
    public static org.apache.poi.ss.usermodel.CellStyle taoStyleDuLieu(Workbook workbook) {
        org.apache.poi.ss.usermodel.CellStyle style = workbook.createCellStyle();
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        
        return style;
    }
    
    
    // =========================================================================
    // HÀM 2: XUẤT FULL DATA TỪ ARRAYLIST (Chữa dứt điểm bệnh phân trang)
    // =========================================================================

    public static void xuatDanhSachNguyenVongRaExcel(ArrayList<nguyenVongXetTuyenETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Nguyện Vọng");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG (SẠCH SẼ CHƯA!) ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID NV", "CCCD", "ID Ngành", "Thứ Tự", "Điểm THXT", 
                    "Điểm UTQD", "Điểm Cộng", "Điểm Tổng", "Kết Quả NV", 
                    "Keys", "Phương Thức", "Tổ Hợp"
                };
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); // Khoác áo VIP cho Header
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (nguyenVongXetTuyenETT nv : ds) {
                    Row row = sheet.createRow(rowNum++);
                    
                    Object[] rowData = {
                        nv.getIdNv(),
                        nv.getNnCccd() != null ? nv.getNnCccd() : "",
                        nv.getNvMaNganh() != null ? nv.getNvMaNganh() : "",
                        nv.getNvTt(),
                        nv.getDiemThxt() != null ? nv.getDiemThxt() : 0.0,
                        nv.getDiemUtqd() != null ? nv.getDiemUtqd() : 0.0,
                        nv.getDiemCong() != null ? nv.getDiemCong() : 0.0,
                        nv.getDiemXetTuyen() != null ? nv.getDiemXetTuyen() : 0.0,
                        nv.getNvKetQua() != null ? nv.getNvKetQua() : "Chờ xét",
                        nv.getNvKeys() != null ? nv.getNvKeys() : "",
                        nv.getTtPhuongThuc() != null ? nv.getTtPhuongThuc() : "",
                        nv.getTtThm() != null ? nv.getTtThm() : ""
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle); // Gắn Style Dữ liệu
                        
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // --- 4. TÚT TÁT LẠI CỘT CHO ĐẸP ---
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                sheet.createFreezePane(0, 1); // Khóa tiêu đề

                // Xuất file
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        public static void xuatDanhSachNganhToHopRaExcel(ArrayList<nganhToHopETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Ngành tổ hợp");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG (SẠCH SẼ CHƯA!) ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID", "ID ngành", "ID tổ hợp", "Môn 1", "Hệ số môn 1", 
                    "Môn 2", "Hệ số môn 2", "Môn 3", "Hệ số môn 3", 
                    "Keys", "N1", "TO", "LI", "HO", "SI", "VA", "SU", "DI", "TI", "KHAC", "KTPL", "Độ lệch"
                };
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); // Khoác áo VIP cho Header
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (nganhToHopETT nv : ds) {
                    Row row = sheet.createRow(rowNum++);
                    
                    Object[] rowData = {
                        nv.getIdNganhToHop(),
                        nv.getMaNganh() != null ? nv.getMaNganh() : "",
                        nv.getMaToHop() != null ? nv.getMaToHop() : "",
                        nv.getMon1() != null ? nv.getMon1() : "",
                        nv.getHeSoMon1()!= null ? nv.getHeSoMon1() : 0.0,
                        nv.getMon2() != null ? nv.getMon2() : "",
                        nv.getHeSoMon2()!= null ? nv.getHeSoMon2() : 0.0,
                        nv.getMon3() != null ? nv.getMon3() : "",
                        nv.getHeSoMon3() != null ? nv.getHeSoMon3() : 0.0,
                        nv.getKey()!= null ? nv.getKey() : "",
                        nv.getN1() != null ? nv.getN1() : 0,
                        nv.getTO() != null ? nv.getTO() : 0,
                        nv.getLI() != null ? nv.getLI() : 0,
                        nv.getHO() != null ? nv.getHO() : 0,
                        nv.getSI() != null ? nv.getSI() : 0,
                        nv.getVA() != null ? nv.getVA() : 0,
                        nv.getSU() != null ? nv.getSU() : 0,
                        nv.getDI() != null ? nv.getDI() : 0,
                        nv.getTI() != null ? nv.getTI() : 0,
                        nv.getKHAC() != null ? nv.getKHAC() : 0,
                        nv.getKTPL() != null ? nv.getKTPL() : 0,
                        nv.getDoLech()!= null ? nv.getDoLech() : 0.0
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle); // Gắn Style Dữ liệu
                        
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // --- 4. TÚT TÁT LẠI CỘT CHO ĐẸP ---
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                sheet.createFreezePane(0, 1); // Khóa tiêu đề

                // Xuất file
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void xuatDanhSachThiSinhRaExcel(
            ArrayList<thiSinhXetTuyenETT> ds,
            java.awt.Component parent,
            String tenBang) {

        try {
            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Thí Sinh");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // STYLE
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // HEADER
                String[] headers = {
                    "ID", "CCCD", "SBD", "Họ", "Tên", "Ngày sinh",
                    "SĐT", "Email", "Giới tính", "Nơi sinh",
                    "Đối tượng", "Khu vực"
                };

                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // DATA
                int rowNum = 1;
                for (thiSinhXetTuyenETT ts : ds) {
                    Row row = sheet.createRow(rowNum++);

                    Object[] rowData = {
                        ts.getIdThiSinh(),
                        ts.getCccd(),
                        ts.getSoBaoDanh(),
                        ts.getHo(),
                        ts.getTen(),
                        ts.getNgaySinh() != null ? ts.getNgaySinh().toString() : "",
                        ts.getDienThoai(),
                        ts.getEmail(),
                        ts.getGioiTinh(),
                        ts.getNoiSinh(),
                        ts.getDoiTuong(),
                        ts.getKhuVuc()
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle);

                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                        }
                    }
                }

                // AUTO SIZE
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
                }

                sheet.createFreezePane(0, 1);

                // WRITE FILE
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }

                workbook.close();

                JOptionPane.showMessageDialog(parent,
                        "Xuất file thành công!\n" + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                    "Lỗi khi xuất: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void xuatDanhSachDiemCongRaExcel(
        ArrayList<diemCongETT> ds,
        java.awt.Component parent,
        String tenBang) {

    try {
        if (ds == null || ds.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Điểm Cộng");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(tenBang);

            // STYLE
            CellStyle headerStyle = taoStyleTieuDe(workbook);
            CellStyle dataStyle = taoStyleDuLieu(workbook);

            // ✅ HEADER ĐÚNG
            String[] headers = {
                "ID",
                "CCCD",
                "Mã ngành",
                "Mã tổ hợp",
                "Phương thức",
                "Điểm cộng",
                "Điểm ưu tiên",
                "Điểm tổng",
                "Ghi chú"
            };

            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(25);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // ✅ DATA ĐÚNG
            int rowNum = 1;
            for (diemCongETT dc : ds) {
                Row row = sheet.createRow(rowNum++);

                Object[] rowData = {
                    dc.getIdDiemCong(),
                    dc.getTsCccd(),
                    dc.getMaNganh(),
                    dc.getMaToHop(),
                    dc.getPhuongThuc(),
                    dc.getDiemCC(),
                    dc.getDiemUtxt(),
                    dc.getDiemTong(),
                    dc.getGhiChu()
                };

                for (int i = 0; i < rowData.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(dataStyle);

                    if (rowData[i] instanceof Number) {
                        cell.setCellValue(((Number) rowData[i]).doubleValue());
                    } else {
                        cell.setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                    }
                }
            }

            // AUTO SIZE
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
            }

            sheet.createFreezePane(0, 1);

            // WRITE FILE
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }

            workbook.close();

            JOptionPane.showMessageDialog(parent,
                    "Xuất file thành công!\n" + filePath,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(parent,
                "Lỗi khi xuất: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }
}
        
    public static void xuatDanhSachToHopRaExcel(ArrayList<toHopETT> ds, java.awt.Component parent, String tenBang) {
        try {

            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Tổ hợp");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG (SẠCH SẼ CHƯA!) ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // ===== HEADER =====
                String[] headers = {
                    "ID",
                    "Mã tổ hợp",
                    "Môn 1",
                    "Môn 2",
                    "Môn 3",
                    "Tên tổ hợp"
                };

                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // ===== DATA =====
                int rowNum = 1;

                for (toHopETT th : ds) {

                    Row row = sheet.createRow(rowNum++);

                    Object[] rowData = {
                        th.getIdtohop(),
                        th.getMatohop() != null ? th.getMatohop() : "",
                        th.getMon1() != null ? th.getMon1() : "",
                        th.getMon2() != null ? th.getMon2() : "",
                        th.getMon3() != null ? th.getMon3() : "",
                        th.getTentohop() != null ? th.getTentohop() : ""
                    };

                    for (int i = 0; i < rowData.length; i++) {

                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle);

                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // ===== AUTO SIZE CỘT =====
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
                }

                sheet.createFreezePane(0, 1);

                // ===== GHI FILE =====
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }

                workbook.close();

                JOptionPane.showMessageDialog(parent,
                        "Xuất file thành công!\n" + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(parent,
                    "Lỗi khi xuất: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
        public static void xuatDanhSachPhanQuyenRaExcel(ArrayList<phanQuyenETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Phân quyền");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG (SẠCH SẼ CHƯA!) ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID tài khoản", "Tên bảng", "Xem", "Thêm", "Sửa", 
                    "Xóa"
                };
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); // Khoác áo VIP cho Header
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (phanQuyenETT nv : ds) {
                    Row row = sheet.createRow(rowNum++);
                    
                    Object[] rowData = {
                        nv.getIdTaiKhoan(),
                        nv.getTenBang()!= null ? nv.getTenBang() : "",
                        nv.getQuyenXem(),
                        nv.getQuyenThem(),
                        nv.getQuyenSua(),
                        nv.getQuyenXoa(),
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle); // Gắn Style Dữ liệu
                        
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // --- 4. TÚT TÁT LẠI CỘT CHO ĐẸP ---
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                sheet.createFreezePane(0, 1); // Khóa tiêu đề

                // Xuất file
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void xuatDanhSachTaiKhoanRaExcel(ArrayList<taiKhoanETT> ds, java.awt.Component parent, String tenBang) {

        try {

            if (ds == null || ds.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Tài khoản");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(tenBang);

                // ===== STYLE =====
                CellStyle headerStyle = taoStyleTieuDe(workbook);
                CellStyle dataStyle = taoStyleDuLieu(workbook);

                // ===== HEADER =====
                String[] headers = {
                    "ID",
                    "Tên đăng nhập",
                    "Mật khẩu",
                    "Họ tên",
                    "Trạng thái"
                };

                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);

                for (int i = 0; i < headers.length; i++) {

                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // ===== DATA =====
                int rowNum = 1;

                for (taiKhoanETT tk : ds) {

                    Row row = sheet.createRow(rowNum++);

                    Object[] rowData = {

                        tk.getIdTaiKhoan(),
                        tk.getTenDangNhap() != null ? tk.getTenDangNhap() : "",
                        "******", // không xuất password thật
                        tk.getHoTen() != null ? tk.getHoTen() : "",
                        tk.getTrangThai() == 1 ? "Hoạt động" : "Bị khóa"

                    };

                    for (int i = 0; i < rowData.length; i++) {

                        Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle);

                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // ===== AUTO SIZE =====
                for (int i = 0; i < headers.length; i++) {

                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
                }

                sheet.createFreezePane(0, 1);

                // ===== GHI FILE =====
                try (FileOutputStream out = new FileOutputStream(filePath)) {

                    workbook.write(out);
                }

                workbook.close();

                JOptionPane.showMessageDialog(parent,
                        "Xuất file thành công!\n" + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(parent,
                    "Lỗi khi xuất: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // =========================================================================
    // HÀM 3: ĐỌC FILE EXCEL TRẢ VỀ MẢNG DỮ LIỆU THÔ (DÙNG CHUNG CHO MỌI BẢNG)
    // =========================================================================
    public static ArrayList<ArrayList<String>> docFileExcel(String filePath) throws Exception {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        java.io.FileInputStream fis = new java.io.FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Lấy cái Sheet đầu tiên

        org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();

        for (Row row : sheet) {
            ArrayList<String> rowData = new ArrayList<>();
            
            // Lấy độ dài của dòng (Nếu dòng trống thì mặc định là 1 để tránh lỗi)
            int lastColumn = Math.max(row.getLastCellNum(), 1); 
            
            for (int cn = 0; cn < lastColumn; cn++) {
                // Đọc từng ô, nếu ô trống thì tự tạo 1 ô rỗng (CREATE_NULL_AS_BLANK)
                Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                
                // Ép tất cả mọi kiểu dữ liệu (Số, Chữ, Ngày tháng) về kiểu String hết
                String text = formatter.formatCellValue(cell);
                rowData.add(text.trim());
            }
            
            // Bỏ qua nếu nguyên 1 dòng đó toàn là ô trống
            boolean isEmpty = true;
            for(String s : rowData) {
                if(!s.isEmpty()) { isEmpty = false; break; }
            }
            if(!isEmpty) {
                data.add(rowData);
            }
        }
        workbook.close();
        fis.close();
        return data;
    }
}