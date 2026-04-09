/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author LE MINH HUY
 */
public class excelThiSinh extends formExcelNguyenMauProMax{

    public excelThiSinh(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel thí sinh");
        labelExcel.setText("Excel thí sinh");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel thí sinh"));
    }
}
