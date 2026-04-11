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
public class excelTaiKhoan extends formExcelNguyenMauProMax {
    public excelTaiKhoan(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel tài khoản");
        labelExcel.setText("Excel tài khoản");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel tài khoản"));
    }
}