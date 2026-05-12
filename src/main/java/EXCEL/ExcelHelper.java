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
                    "ID NV", "CCCD", "ID Ngành", "Thứ Tự", "Điểm THXT", "Điểm UT Gốc" ,
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
                        nv.getDiemUtqdGoc()!= null ? nv.getDiemUtqdGoc() : 0.0,
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
    
    public static void xuatDanhSachTrungTuyenRaExcel(ArrayList<nguyenVongXetTuyenETT> dsDau, java.awt.Component parent, String tenBang) {
        try {
            if (dsDau == null || dsDau.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Trúng Tuyển");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. LẤY STYLE ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "Mã Ngành", "CCCD", "Thứ Tự NV Đậu", "Điểm Xét Tuyển", "Phương Thức", "Tổ Hợp"
                };
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (nguyenVongXetTuyenETT nv : dsDau) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    
                    Object[] rowData = {
                        nv.getNvMaNganh() != null ? nv.getNvMaNganh() : "",
                        nv.getNnCccd() != null ? nv.getNnCccd() : "",
                        nv.getNvTt(),
                        nv.getDiemXetTuyen() != null ? nv.getDiemXetTuyen() : 0.0,
                        nv.getTtPhuongThuc() != null ? nv.getTtPhuongThuc() : "",
                        nv.getTtThm() != null ? nv.getTtThm() : ""
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle);
                        
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // --- 4. TÚT TÁT LẠI CỘT ---
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                sheet.createFreezePane(0, 1);

                // Xuất file
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                JOptionPane.showMessageDialog(parent, "Xuất danh sách trúng tuyển thành công!\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
public static void xuatThongKePhuongThucRaExcel(java.util.HashMap<String, java.util.HashMap<String, Integer>> thongKeMap, java.util.HashMap<String, String> mapTenNganh, java.awt.Component parent, String tenBang) {
        try {
            if (thongKeMap == null || thongKeMap.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không có dữ liệu thống kê để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Thống kê Số lượng Trúng Tuyển");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Thống Kê");

                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                // 🚀 ĐÃ SỬA: Chèn thêm cột "Tên Ngành"
                String[] headers = {
                    "Mã Ngành", "Tên Ngành", "Xét Tuyển Thẳng", "Xét THPT", "Đánh giá V-SAT", "ĐGNL HCM", "Tổng Đậu"
                };
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (String maNganh : thongKeMap.keySet()) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    java.util.HashMap<String, Integer> pThucMap = thongKeMap.get(maNganh);
                    
                    // 🚀 ĐÃ SỬA: Dò Tên Ngành dựa vào Mã Ngành
                    String tenNganh = mapTenNganh.getOrDefault(maNganh, "Chưa xác định");

                    int slXTT = pThucMap.getOrDefault("Xét tuyển thẳng", 0);
                    int slTHPT = pThucMap.getOrDefault("Xét THPT", 0);
                    int slVSAT = pThucMap.getOrDefault("Đánh giá V-SAT", 0);
                    int slDGNL = pThucMap.getOrDefault("ĐGNL HCM", 0);
                    int tongDau = slXTT + slTHPT + slVSAT + slDGNL;

                    // 🚀 ĐÃ SỬA: Nhét Tên Ngành vào mảng dữ liệu
                    Object[] rowData = {
                        maNganh, tenNganh, slXTT, slTHPT, slVSAT, slDGNL, tongDau
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle);
                        
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).intValue()); // Số lượng là Int
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // --- 4. TÚT TÁT LẠI CỘT ---
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                sheet.createFreezePane(0, 1);

                // Xuất file
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                javax.swing.JOptionPane.showMessageDialog(parent, "Xuất báo cáo thống kê thành công!\n" + filePath, "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
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
    
    public static void xuatDanhSachNganhRaExcel(java.util.ArrayList<Entity.nganhETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Ngành học");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID Ngành", "Mã Ngành", "Tên Ngành", "Tổ Hợp Gốc", "Chỉ Tiêu", 
                    "Sàn THPT", "Sàn V-SAT", "Sàn ĐGNL", "Chuẩn THPT", "Chuẩn V-SAT", "Chuẩn ĐGNL", "SL ĐK"
                };
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); // Khoác áo VIP cho Header
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (Entity.nganhETT ng : ds) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    
                    // Nếu điểm null thì để chuỗi rỗng "", giúp Excel xuất ra ô trống đẹp mắt
                    Object[] rowData = {
                        ng.getIdnganh(),
                        ng.getManganh() != null ? ng.getManganh() : "",
                        ng.getTennganh() != null ? ng.getTennganh() : "",
                        ng.getN_tohopgoc() != null ? ng.getN_tohopgoc() : "",
                        ng.getN_chitieu() != null ? ng.getN_chitieu() : 0,
                        ng.getN_diemsanthpt() != null ? ng.getN_diemsanthpt() : "",
                        ng.getN_diemsanvsat() != null ? ng.getN_diemsanvsat() : "",
                        ng.getN_diemsandgnl() != null ? ng.getN_diemsandgnl() : "",
                        //ng.getN_diemtrungtuyen() != null ? ng.getN_diemtrungtuyen() : ""
                        ng.getDiemchuan_thpt() != null ? ng.getDiemchuan_thpt() : "",
                        ng.getDiemchuan_vsat() != null ? ng.getDiemchuan_vsat() : "",
                        ng.getDiemchuan_dgnl() != null ? ng.getDiemchuan_dgnl() : "",
                        ng.getSlDangKy() != null ? ng.getSlDangKy() : 0
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
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
                sheet.createFreezePane(0, 1); // Khóa hàng tiêu đề không cho cuộn trôi

                // Xuất file
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                javax.swing.JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void xuatDanhSachChungChiRaExcel(java.util.ArrayList<Entity.chungChiETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Chứng chỉ");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID CC", "CCCD Thí Sinh", "Loại Chứng Chỉ", "Điểm Chứng Chỉ", "Điểm Quy Đổi", "Điểm Cộng"
                };
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); 
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (Entity.chungChiETT cc : ds) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    
                    Object[] rowData = {
                        cc.getIdCc(),
                        cc.getCccd() != null ? cc.getCccd() : "",
                        cc.getLoaiChungChi() != null ? cc.getLoaiChungChi() : "",
                        cc.getDiemChungChi() != null ? cc.getDiemChungChi() : "",
                        cc.getDiemQuyDoi() != null ? cc.getDiemQuyDoi() : "",
                        cc.getDiemCong() != null ? cc.getDiemCong() : ""
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle); 
                        
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
                sheet.createFreezePane(0, 1); 

                // Xuất file
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                javax.swing.JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void xuatDanhSachGiaiThuongRaExcel(java.util.ArrayList<Entity.giaiThuongETT> ds, java.awt.Component parent, String tenBang) {
        try {
            if (ds == null || ds.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Không có dữ liệu để xuất!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu Danh sách Giải thưởng");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File(tenBang + ".xlsx"));

            if (fileChooser.showSaveDialog(parent) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) filePath += ".xlsx";

                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(tenBang);

                // --- 1. GỌI HÀM LẤY STYLE CHUNG ---
                org.apache.poi.ss.usermodel.CellStyle headerStyle = taoStyleTieuDe(workbook);
                org.apache.poi.ss.usermodel.CellStyle dataStyle = taoStyleDuLieu(workbook);

                // --- 2. IN HEADER ---
                String[] headers = {
                    "ID Giải Thưởng", "CCCD Thí Sinh", "Cấp Giải", "Mã Môn", "Loại Giải", "Điểm Cộng (Có Môn)", "Điểm Cộng (Không Môn)"
                };
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25);
                
                for (int i = 0; i < headers.length; i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); 
                }

                // --- 3. IN DATA ---
                int rowNum = 1;
                for (Entity.giaiThuongETT gt : ds) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                    
                    // Lưu ý: Nếu ID giải thưởng cậu đặt tên khác thì sửa gt.getIdGt() lại cho khớp nhé
                    Object[] rowData = {
                        gt.getIdGt(),
                        gt.getCccd() != null ? gt.getCccd() : "",
                        gt.getCapGiai() != null ? gt.getCapGiai() : "",
                        gt.getMaMon() != null ? gt.getMaMon() : "",
                        gt.getLoaiGiai() != null ? gt.getLoaiGiai() : "",
                        gt.getDiemCongCoMon() != null ? gt.getDiemCongCoMon() : "",
                        gt.getDiemCongKhongMon() != null ? gt.getDiemCongKhongMon() : ""
                    };

                    for (int i = 0; i < rowData.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
                        cell.setCellStyle(dataStyle); 
                        
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
                sheet.createFreezePane(0, 1); 

                // Xuất file
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                    workbook.write(out);
                }
                workbook.close();
                javax.swing.JOptionPane.showMessageDialog(parent, "Xuất file thành công!\n" + filePath, "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(parent, "Lỗi khi xuất: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
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