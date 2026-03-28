package EXCEL;

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
import Entity.nguyenVongXetTuyenETT; // Nhớ import entity của ông nha

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
    // HÀM 2: XUẤT FULL DATA TỪ ARRAYLIST (Chữa dứt điểm bệnh phân trang)
    // =========================================================================
// =========================================================================
    // HÀM 2: XUẤT FULL DATA TỪ ARRAYLIST (BẢN ĐỘ GIAO DIỆN CỰC ĐẸP)
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

                // ================== GÓC ĐỘ GIAO DIỆN (STYLING) ==================
                // 1. Style cho Dòng Tiêu Đề (Header): Nền xanh đậm, chữ trắng, in đậm
                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
                headerFont.setFontHeightInPoints((short) 12);

                org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.DARK_BLUE.getIndex());
                headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
                headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
                headerStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
                // Kẻ viền cho header
                headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);

                // 2. Style cho Dữ liệu (Data): Kẻ ô vuông vức, căn giữa toàn bộ
                org.apache.poi.ss.usermodel.CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                dataStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                dataStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                dataStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
                dataStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
                dataStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
                // ===============================================================

                // 3. In Header và áp dụng Style
                String[] headers = {
                    "ID NV", "CCCD", "ID Ngành", "Thứ Tự", "Điểm THXT", 
                    "Điểm UTQD", "Điểm Cộng", "Điểm Tổng", "Kết Quả NV", 
                    "Keys", "Phương Thức", "Tổ Hợp"
                };
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(25); // Kéo cho dòng tiêu đề cao lên xíu cho sang
                
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle); // Khoác áo VIP cho Header
                }

                // 4. In Data và áp dụng Style
                int rowNum = 1;
                for (nguyenVongXetTuyenETT nv : ds) {
                    Row row = sheet.createRow(rowNum++);
                    
                    // Tạo một mảng object để lặp qua tạo cell cho lẹ, code đỡ dài
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
                        // Kích hoạt Style kẻ ô cho dòng dữ liệu
                        cell.setCellStyle(dataStyle); 
                        
                        // Xét kiểu dữ liệu để đẩy vào Excel cho chuẩn (Số ra số, chữ ra chữ)
                        if (rowData[i] instanceof Number) {
                            cell.setCellValue(((Number) rowData[i]).doubleValue());
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }

                // 5. Căn chỉnh auto-size cho tất cả các cột
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                    // Cộng thêm tí padding (khoảng trống) cho nó thoáng mắt
                    sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000); 
                }
                
                // 6. TÍNH NĂNG ĂN TIỀN: Khóa dòng tiêu đề (Freeze Pane)
                // Cuộn chuột ngàn dòng thì cái Header xanh đậm vẫn dính chặt ở trên cùng
                sheet.createFreezePane(0, 1);

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