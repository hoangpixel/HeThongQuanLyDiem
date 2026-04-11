/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author Dang Khoa
 */
public class deleteTaiKhoan extends formDeleteNguyenMauProMax {
    
     public deleteTaiKhoan(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormNguyenVong();
    }
    void loadFormNguyenVong()
    {
        setTitle("Xóa Tài Khoản");
        labelXoa.setText("Xóa Tài Khoản");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("thực hiện xóa tài khoản"));
        mess = "Xóa Tài Khoản thành công";
    }
}
