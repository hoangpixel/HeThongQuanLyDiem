/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;
import GUI.contentGUI;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
/**
 *
 * @author mhoang
 */
public class main {
        public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new contentGUI().setVisible(true);
        });
    }
}
