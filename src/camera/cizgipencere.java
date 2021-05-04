/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import javax.swing.JFrame;

public class cizgipencere extends JFrame{
    
    cizgipencere(){
        initUI();
    }
    
    private void initUI(){
        add(new cizgi());
        setTitle("Açı Bul");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}