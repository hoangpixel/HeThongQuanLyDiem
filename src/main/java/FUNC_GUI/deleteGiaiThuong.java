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
public class deleteGiaiThuong extends formDeleteNguyenMauProMax {
    public deleteGiaiThuong(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormChungChi();
    }
    void loadFormChungChi() {
        setTitle("Xóa giải thưởng");
        labelXoa.setText("Xóa giải thưởng");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("Thực hiện xóa giải thưởng"));
        mess = "Xóa giải thưởng thành công";
    }
}
