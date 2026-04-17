/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author Dat
 */
public class excelNganh extends formExcelNguyenMauProMax{

    public excelNganh(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel ngành");
        labelExcel.setText("Excel ngành");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel ngành"));
    }
}
