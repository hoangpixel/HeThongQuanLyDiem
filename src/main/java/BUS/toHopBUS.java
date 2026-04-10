/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.toHopDAO;
import Entity.toHopETT;
import java.util.ArrayList;

/**
 *
 * @author mhoang
 */
public class toHopBUS {
    public static ArrayList<toHopETT> ds;
    toHopDAO data = new toHopDAO();
    public ArrayList<toHopETT> layDanhSach() 
    {
        if (ds == null) {
            ds = data.layDanhSach();
        }
        return ds;
    }
    
    public boolean kiemTraRong(String text)
    {
        if(text == null || text.trim().isEmpty())
        {
            return true;
        }
        return false;
    }
    
    public boolean kiemTraSo(String text) 
    {
        try 
        {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
    public boolean kiemTraSoThuc(String text) 
    {
    try {
        Double.parseDouble(text);
        return true;
    } catch (NumberFormatException e) 
    {
        return false;
    }
    }
    
    // ===================== THÊM =====================
    public boolean themToHop (toHopETT toHopMoi){
        boolean isSuccess = data.them(toHopMoi);
        if (isSuccess) {
            if (ds != null) {
                ds.add(toHopMoi); // Cập nhật RAM luôn, không cần load lại DB
            }
        }
        return isSuccess;
    }

    // ===================== SỬA =====================
    public boolean suaToHop(toHopETT ToHopDaSua) {
        // 1. Lưu xuống Database trước qua DAO
        if (data.sua(ToHopDaSua)) {

            // 2. Nếu DB OK → cập nhật lại RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    // Dùng id (khóa chính) để tìm đúng phần tử
                    if (ds.get(i).getIdtohop() == ToHopDaSua.getIdtohop()) {
                        ds.set(i, ToHopDaSua); // Đè object mới vào vị trí cũ
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ===================== XÓA =====================
    public boolean xoaToHop(toHopETT ToHopCanXoa) {
        // 1. Xóa trong Database trước
        if (data.xoa(ToHopCanXoa)) {

            // 2. Nếu DB OK → xóa luôn trong RAM
            if (ds != null) {
                for (int i = 0; i < ds.size(); i++) {
                    if (ds.get(i).getIdtohop() == ToHopCanXoa.getIdtohop()) {
                        ds.remove(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public String nhapDuLieuTuExcel(String filePath) {
        try {

            // 1. Đọc dữ liệu từ Excel
            ArrayList<ArrayList<String>> dataExcel = EXCEL.ExcelHelper.docFileExcel(filePath);

            if (dataExcel.size() <= 1) {
                return "File Excel trống hoặc chỉ có dòng tiêu đề!";
            }

            int soDongThanhCong = 0;
            int soDongThatBai = 0;

            // 2. Bỏ dòng header
            for (int i = 1; i < dataExcel.size(); i++) {

                ArrayList<String> row = dataExcel.get(i);

                try {

                    toHopETT th = new toHopETT();

                    // Mapping cột Excel → Entity
                    th.setMatohop(row.get(0));
                    th.setMon1(row.get(1));
                    th.setMon2(row.get(2));
                    th.setMon3(row.get(3));
                    th.setTentohop(row.get(4));

                    // 3. Thêm vào database
                    if (data.them(th)) {
                        soDongThanhCong++;
                    } else {
                        soDongThatBai++;
                    }

                } catch (Exception ex) {
                    soDongThatBai++;
                }
            }

            return "Nhập thành công: " + soDongThanhCong +
                   " dòng.\nLỗi/Trùng lặp: " + soDongThatBai + " dòng.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file Excel: " + e.getMessage();
        }
    }
    
    public ArrayList<toHopETT> timKiemCoBan(String tim, int index)
    {
        ArrayList<toHopETT> ketQua = new ArrayList<>();

        for (toHopETT th : layDanhSach())
        {
            switch (index)
            {
                case 0:
                    if (th.getMatohop().toLowerCase().contains(tim)) ketQua.add(th);
                    break;

                case 1:
                    if (th.getMon1().toLowerCase().contains(tim)) ketQua.add(th);
                    break;

                case 2:
                    if (th.getMon2().toLowerCase().contains(tim)) ketQua.add(th);
                    break;

                case 3:
                    if (th.getMon3().toLowerCase().contains(tim)) ketQua.add(th);
                    break;

                case 4:
                    if (th.getTentohop().toLowerCase().contains(tim)) ketQua.add(th);
                    break;
            }
        }

        return ketQua;
    }
    
}
