/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import FUNC_GUI.formDeleteNguyenMauProMax;
import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author Dang Khoa
 */
public class deleteNganhToHop extends formDeleteNguyenMauProMax{
    
    public deleteNganhToHop(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNguyenVong();
    }
    void loadFormNguyenVong()
    {
        setTitle("Xóa Môn Thi");
        labelXoa.setText("Xóa Môn Thi");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa môn thi"));
        mess = "Xóa Môn Thi thành công";
    }
}