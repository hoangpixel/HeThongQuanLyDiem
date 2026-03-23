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
 * @author LE MINH HUY
 */   
public class deleteThiSinh extends formDeleteNguyenMauProMax{
    
    public deleteThiSinh(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormThiSinh();
    }
    void loadFormThiSinh()
    {
        setTitle("Xóa thí sinh");
        labelXoa.setText("Xóa thí sinh");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa thí sinh"));
        mess = "Xóa thí sinh thành công";
    }
}
