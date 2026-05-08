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
public class excelGiaiThuong extends formExcelNguyenMauProMax{

    public excelGiaiThuong(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel giải thưởng");
        labelExcel.setText("Excel giải thưởng");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel giải thưởng"));
    }
}
