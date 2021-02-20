/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 *
 */
public class XfitTimer {
    public static void main(String[] args) {
        
        int screenHeight, screenWidth;
        int maxWindows = 4;
        int x = 0;
        int y = 0;
        
        Toolkit mytoolkit = Toolkit.getDefaultToolkit();
        Dimension myScreen = mytoolkit.getScreenSize();
        screenHeight = myScreen.height;
        screenWidth = myScreen.width;
        
        MarcoTimer miVentana1 = new MarcoTimer(screenHeight/2, screenWidth/2, x, y);
        miVentana1.run();
        /*miVentana1.setVisible(true);
        
        x = screenWidth/2;
        
        MarcoTimer miVentana2 = new MarcoTimer(screenHeight/2, screenWidth/2, x, y);
        miVentana2.setVisible(true);
        
        x = 0;
        y = screenHeight/2;
        
        MarcoTimer miVentana3 = new MarcoTimer(screenHeight/2, screenWidth/2, x, y);
        miVentana3.setVisible(true);
        
        x = screenWidth/2;        
        
        MarcoTimer miVentana4 = new MarcoTimer(screenHeight/2, screenWidth/2, x, y);
        miVentana4.setVisible(true);*/
        
                
        //if (getX() < getWidth()) {
                //setX(getX() + getWidth()/2);
                //add(new PanelDisplay(getHeight(), getWidth()));
            //} else {
                //setX(0);
                //setY(getY() + getHeight()/2);
            //}
       
    }
}
   
class MarcoTimer extends JFrame implements Runnable {
    
    //public static final int MAX_WINDOWS;
    private static int currentWindows = 0;   
    
    private int height, width;
    private int x;
    private int y;
    
    public MarcoTimer() {
        
    }
       
    public MarcoTimer(int height, int width, int x, int y) {
       
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        
        setTitle("X-Fit Timer");
        setBounds(getX(),getY(),getWidth(),getHeight());
        //pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        setResizable(true);
        // Objeto Listener        
        add(new PanelDisplay(getHeight(), getWidth()));
    }
    
    /* * * * * GETTERS & SETTERS * * * * */

    public static int getCurrentWindows() { return currentWindows; }
    public static void setCurrentWindows(int aCurrentWindows) { currentWindows = aCurrentWindows; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }    
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    //public boolean dispo() { return (getCurrentWindows() < MAX_WINDOWS); }
    
    
    @Override
    public void run() {
        this.setVisible(true);
    }
   
}