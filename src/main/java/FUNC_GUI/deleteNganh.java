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
 * @author Dat
 */
public class deleteNganh extends formDeleteNguyenMauProMax {
    
    public deleteNganh(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNganh();
    }
    void loadFormNganh() {
        setTitle("Xóa ngành học");
        labelXoa.setText("Xóa ngành học");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("Thực hiện xóa ngành học"));
        mess = "Xóa ngành học thành công";
    }
}
