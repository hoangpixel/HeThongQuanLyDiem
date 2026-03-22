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
public class deleteNguyenVong extends formDeleteNguyenMauProMax{
    
    public deleteNguyenVong(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNguyenVong();
    }
    void loadFormNguyenVong()
    {
        setTitle("Xóa nguyện vọng");
        labelXoa.setText("Xóa nguyện vọng");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa nguyện vọng"));
        mess = "Xóa nguyện vọng thành công";
    }
}
