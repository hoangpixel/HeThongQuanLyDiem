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
public class deleteChungChi extends formDeleteNguyenMauProMax {
    public deleteChungChi(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormChungChi();
    }
    void loadFormChungChi() {
        setTitle("Xóa chứng chỉ");
        labelXoa.setText("Xóa chứng chỉ");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("Thực hiện xóa chứng chỉ"));
        mess = "Xóa chứng chỉ thành công";
    }
}
