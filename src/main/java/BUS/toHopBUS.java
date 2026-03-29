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
    public boolean xoaNganhToHop(toHopETT ToHopCanXoa) {
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
}
