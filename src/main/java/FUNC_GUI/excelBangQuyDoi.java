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
public class excelBangQuyDoi extends formExcelNguyenMauProMax{
    
    public excelBangQuyDoi(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel bảng quy đổi");
        labelExcel.setText("Excel bảng quy đổi");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel bảng quy đổi"));
    }
}