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
public class deleteDiemCong extends formDeleteNguyenMauProMax{
    
    public deleteDiemCong(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormDiemCong();
    }
    void loadFormDiemCong()
    {
        setTitle("Xóa điểm cộng");
        labelXoa.setText("Xóa điểm cộng");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa điểm cộng"));
        mess = "Xóa điểm cộng thành công";
    }
}
