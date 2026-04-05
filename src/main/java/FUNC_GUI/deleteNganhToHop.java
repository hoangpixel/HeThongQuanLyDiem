/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author mhoang
 */
public class deleteNganhToHop extends formDeleteNguyenMauProMax{
    public deleteNganhToHop(Frame parent, boolean modal)
    {
        super(parent, modal);
        loadFormNganhToHop();
    }
    void loadFormNganhToHop() 
    {
        setTitle("Xóa ngành tổ hợp");
        labelXoa.setText("Xóa ngành tổ hợp");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("Thực hiện xóa ngành tổ hợp"));
        mess = "Xóa ngành tổ hợp thành công";
    }
}
