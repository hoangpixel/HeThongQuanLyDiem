/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.text.html.HTML;

/**
 *
 * @author mhoang
 */
public class deletePhanQuyen extends formDeleteNguyenMauProMax{
    
    public deletePhanQuyen(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNguyenVong();
    }
    void loadFormNguyenVong()
    {
        setTitle("Xóa phân quyền");
        labelXoa.setText("Xóa phân quyền");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa phân quyền"));
        mess = "Xóa phân quyền thành công";
    }
}