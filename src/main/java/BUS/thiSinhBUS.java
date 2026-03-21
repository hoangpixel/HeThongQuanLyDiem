package BUS;

import DAO.thiSinhDAO;
import Entity.thiSinhETT;
import java.util.ArrayList;

public class thiSinhBUS {
    public static ArrayList<thiSinhETT> ds;
    thiSinhDAO data = new thiSinhDAO();

    public ArrayList<thiSinhETT> layDanhSach() 
    {
        if (ds == null) {
            ds = data.layDanhSach();
        }
        return ds;
    }
}