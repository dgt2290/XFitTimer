import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.Timer;

class PanelDisplay extends JPanel implements ActionListener {
    //Main Attributes
    private JTextField displayRounds = new JTextField("");
    private JTextField displayTimer = new JTextField("00 : 00");
    private JPopupMenu popupMenu = new JPopupMenu();
    private Timer timer = new Timer(1000, this);
    private ContadorTiempo contador;
    private int height;
    private int width;
    
    //Control Variables
    private String buttonPressed;
    private boolean blackDisplay;
    
    //Others
    private int prepTime = 10;
   
    public PanelDisplay(int height, int width) {
        
        Box miCajaVertical = Box.createVerticalBox();
       
        //FlowLayout timerLayout = new FlowLayout();
        //BorderLayout timerLayout = new BorderLayout();
        //setLayout(timerLayout);
       
        setHeight(height);
        setWidth(width);
        setDisplaysGralProps(displayRounds, 110);
        setDisplaysGralProps(displayTimer, 220);
        
        //add(displayRounds);      
        //add(displayTimer);
        miCajaVertical.add(displayRounds);
        //miCajaVertical.add(Box.createVerticalStrut(5));
        miCajaVertical.add(displayTimer);
        //miCajaVertical.add(Box.createVerticalStrut(5));
        miCajaVertical.add(new PanelControles(this));
        //miCajaVertical.add(Box.createVerticalStrut(5));
        add(miCajaVertical);
        //add(new PanelControles(this));
        setPopupMenu();    
        displayTimer.setComponentPopupMenu(popupMenu);
           
    }
    
    /* * * * * GETTERS & SETTERS * * * * */
    
    public JTextField getDisplayRounds() { return displayRounds; }
    public void setDisplayRounds(JTextField displayRounds) { this.displayRounds = displayRounds; }
    public JTextField getDisplayTimer() { return displayTimer; }
    public void setDisplayTimer(JTextField displayTimer) { this.displayTimer = displayTimer; }
    public Timer getTimer() { return timer; }
    public void setTimer(Timer timer) { this.timer = timer; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public ContadorTiempo getContador() { return this.contador; }
    public void setContador(ContadorTiempo contador) { this.contador = contador; }
    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
    public String getButtonPressed() { return buttonPressed; }
    public void setButtonPressed(String buttonId) { this.buttonPressed = buttonId; }
    public boolean isBlackDisplay() { return blackDisplay; }
    public void setBlackDisplay(boolean blackDisplay) { this.blackDisplay = blackDisplay; }
    
    /* * * * * AUX METHODS * * * * * */
    
    /**
     * Define características generales del display
     */
    private void setDisplaysGralProps(JTextField display, int fontSize) {
        display.setEditable(false);
        blackDisplayFormat();
        display.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        display.setHorizontalAlignment(JTextField.CENTER);
    }
    
    /**
     * Define los componentes del JPopupMenu
     */
    private void setPopupMenu() {
        JMenuItem custom = new JMenuItem("Custom");
        JMenuItem prepTime = new JMenuItem("Prep Time");
        JMenuItem newTimer = new JMenuItem("New Timer");
       
        custom.addActionListener((ActionEvent e) -> {
            
            /*MarcoCustom marcoCustom = new MarcoCustom();
            marcoCustom.setVisible(true);*/
            
            String nombre = JOptionPane.showInputDialog(this, "Nombre", "Custom", JOptionPane.PLAIN_MESSAGE);
            int rounds = Integer.parseInt(JOptionPane.showInputDialog(this, "Rounds", "Custom", JOptionPane.PLAIN_MESSAGE));
            ArrayList<Integer> intervalos = new ArrayList();
            ContadorTiempo contador;
            int min;
            int sec; 
            int i = 1;
            int addIntervalo = 0; // 0 = yes, 1 = no, 2 = cancel
            
            while (addIntervalo == 0) {
                min = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese minutos", "Intervalo " + i, JOptionPane.PLAIN_MESSAGE));
                sec = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese segundos", "Intervalo " + i, JOptionPane.PLAIN_MESSAGE));
                intervalos.add(0, min*60 + sec);
                addIntervalo = JOptionPane.showConfirmDialog(this, "¿Desea agregar otro intervalo?", "Custom", JOptionPane.YES_NO_OPTION);
                i++;
            } 
            
            contador = new ContadorTiempo(intervalos, rounds);
            
            try {
                ObjectOutputStream guardar = new ObjectOutputStream(new FileOutputStream(nombre+".timer"));
                guardar.writeObject(contador);
                guardar.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PanelDisplay.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PanelDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                ObjectInputStream cargar = new ObjectInputStream(new FileInputStream(nombre+".timer"));
                ContadorTiempo prueba = (ContadorTiempo) cargar.readObject();
                System.out.println(prueba.getRounds());
                cargar.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PanelDisplay.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PanelDisplay.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PanelDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            setTimer(contador , "Custom");
            
        });
       
        prepTime.addActionListener((ActionEvent e) -> {
            setPrepTime(Integer.valueOf(JOptionPane.showInputDialog(null, "Ingrese Tiempo de Preparación", "Prep Time", JOptionPane.QUESTION_MESSAGE)));
        });
        
        newTimer.addActionListener((ActionEvent e) -> {
            Runnable r = new MarcoTimer(500, 500, 0, 0);
            Thread t = new Thread(r);
            t.run();
        });
        
        popupMenu.add(custom);
        popupMenu.add(prepTime);
        popupMenu.add(newTimer);
    }
    
    /**
     * Devuelve un String con los rounds según corresponda
     */
    private String formatRounds() {
        
        int R = getContador().getRounds();
        int r = getContador().get_r();
        
        String s = getButtonPressed();
        
        if ( (getTimer().isRunning()) && (R != 0) ) {
            s = "Round " + String.valueOf(R - (r -1)) + " / " + R;
        } else if (R != 0) {
            s = "Rounds " + String.valueOf(R);
        } 
        
        return s;
    }
    
    /**
     * Formatea el tiempo t
     */
    private String formatTimer(int t) {
        int minutos = t/60;
        int segundos = t%60;
        return String.format("%02d", minutos) + " : " + String.format("%02d", segundos);
    }
    
    /**
     * Pinta el fondo del display de Color.BLACK y al texto de Color.WHITE
     */
    private void blackDisplayFormat() {
        super.setBackground(Color.BLACK);
        getDisplayRounds().setForeground(Color.WHITE);
        getDisplayTimer().setForeground(Color.WHITE);
        getDisplayRounds().setBackground(Color.BLACK);
        getDisplayTimer().setBackground(Color.BLACK);
        setBlackDisplay(true);
    }
    
    /**
     * Pinta el fondo del display de Color.RED y al texto de Color.BLACK
     */
    private void redDisplayFormat() {
        super.setBackground(Color.RED);
        getDisplayRounds().setForeground(Color.BLACK);
        getDisplayTimer().setForeground(Color.BLACK);
        getDisplayRounds().setBackground(Color.RED);
        getDisplayTimer().setBackground(Color.RED);
        setBlackDisplay(false);
    }
    
    /* * * * * MAIN FUNCTIONS * * * * * */
    
    public void setTimer(ContadorTiempo contador, String buttonId) {
        
        setContador(contador);
        setButtonPressed(buttonId);
        
        if (getButtonPressed().equals("CLOCK")) {
            setDisplay(true, formatTimer(contador.get_t()));
        } else {
            setDisplay(formatRounds(), formatTimer(contador.getIntervalos()
                    .get(contador.getIntervalos().size() -2)));
        }
        
    }
    
    private void setDisplay(String rounds, String timer) { 
        
        getDisplayRounds().setText(rounds);
        getDisplayTimer().setText(timer);
        
        if (getContador().get_t() > 3) {
            if (!isBlackDisplay()) {
                blackDisplayFormat();
            }
            
        } else {
            if (isBlackDisplay()) {
                redDisplayFormat();
            }
            
        }
    }
    
    private void setDisplay(boolean avanzar, String timer) { 
        
        getDisplayRounds().setText(getButtonPressed());
        getDisplayTimer().setText(timer);
        
        if (avanzar) {
            if (!isBlackDisplay()) {
                blackDisplayFormat();
            }
            
            
        } else if (getContador().get_t() < 3) {
            if (isBlackDisplay()) {
                redDisplayFormat();
            }
        }
    }
        
    public void start() {
        getTimer().start();
        
    }
       
    public void stop() {
        getTimer().stop();
    }
       
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (getButtonPressed().equals("CLOCK")) {
            setDisplay(getContador().avanzar(), formatTimer(getContador().get_t()));
            
        } else if (getContador().contar()) {
            /*ControlDisplay ctrl = new ControlDisplay(getDisplayRounds(), getDisplayTimer());
            Thread t = new Thread(ctrl);
            t.start();*/
            setDisplay(formatRounds(), formatTimer(getContador().get_t()));

        } else {
            stop();
        }      
       
    }      

    private ActionListener ActionListener() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

/*class ControlDisplay implements Runnable {
    
    JTextField rounds, timer;
    
    public ControlDisplay(JTextField rounds, JTextField timer){
        this.rounds = rounds;
        this.timer = timer;        
    }

    @Override
    public void run() {
        
    }
              
       
          
} */


