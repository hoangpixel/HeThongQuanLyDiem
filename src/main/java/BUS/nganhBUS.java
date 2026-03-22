/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import Entity.nganhETT;
import DAO.nganhDAO;
import java.util.ArrayList;
/**
 *
 * @author mhoang
 */
public class nganhBUS {
    public static ArrayList<nganhETT> ds;
    nganhDAO data = new nganhDAO();
    public ArrayList<nganhETT> layDanhSach()
    {
        if(ds == null)
        {
            ds = data.layDanhSach();
        }
        return ds;
    }
}
