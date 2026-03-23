/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import FUNC_GUI.formDeleteNguyenMauProMax;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.text.html.HTML;

/**
 *
 * @author Dang Khoa
 */
public class deleteToHop extends formDeleteNguyenMauProMax{
    
    public deleteToHop(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNguyenVong();
    }
    void loadFormNguyenVong()
    {
        setTitle("Xóa Tổ Hợp");
        labelXoa.setText("Xóa Tổ Hợp");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa tổ hợp"));
        mess = "Xóa Tổ Hợp thành công";
    }
}