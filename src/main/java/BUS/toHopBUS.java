/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.toHopDAO;
import Entity.toHopETT;
import java.util.ArrayList;

/**
 *
 * @author mhoang
 */
public class toHopBUS {
        public static ArrayList<toHopETT> ds;
    toHopDAO data = new toHopDAO();

    public ArrayList<toHopETT> layDanhSach() 
    {
        if (ds == null) {
            ds = data.layDanhSach();
        }
        return ds;
    }
}
