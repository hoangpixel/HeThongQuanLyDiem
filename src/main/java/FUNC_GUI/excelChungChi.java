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
public class excelChungChi extends formExcelNguyenMauProMax{

    public excelChungChi(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel chứng chỉ");
        labelExcel.setText("Excel chứng chỉ");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel chứng chỉ"));
    }
}
