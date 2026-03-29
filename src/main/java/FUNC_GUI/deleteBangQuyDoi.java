package FUNC_GUI;

import java.awt.Frame;
import javax.swing.BorderFactory;

public class deleteBangQuyDoi extends formDeleteNguyenMauProMax {

    public deleteBangQuyDoi(Frame parent, boolean modal) {
        super(parent, modal);
        loadFormBangQuyDoi();
    }

    private void loadFormBangQuyDoi() {
        setTitle("Xóa mốc quy đổi V-SAT");
        labelXoa.setText("Xóa mốc quy đổi V-SAT");
        groupBoxXoa.setBorder(BorderFactory.createTitledBorder("Thực hiện xóa mốc quy đổi"));
        mess = "Xóa mốc quy đổi thành công";
    }
}
