package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

/**
 *
 * @author LE MINH HUY
 */
public class excelDiemCong extends formExcelNguyenMauProMax{

    public excelDiemCong(Frame parent,boolean modal) {
        super(parent,modal);
        loadFormExcel();
    }
    public void loadFormExcel()
    {
        setTitle("Excel điểm cộng");
        labelExcel.setText("Excel điểm cộng");
        groupBoxExcel.setBorder(BorderFactory.createTitledBorder("thực hiện Import/Export excel điểm cộng"));
    }
}
