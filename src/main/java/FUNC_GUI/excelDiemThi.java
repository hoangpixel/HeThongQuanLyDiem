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
public class excelDiemThi extends formExcelNguyenMauProMax{
    
    public excelDiemThi(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel điểm thi");
        labelExcel.setText("Excel điểm thi");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel điểm thi"));
    }
}
