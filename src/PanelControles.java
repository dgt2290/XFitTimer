import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

class PanelControles extends JPanel {
    
    private PanelDisplay display;
    private ArrayList<AbstractButton> botones = new ArrayList<AbstractButton>();    
       
    public PanelControles(PanelDisplay display) {
       
        this.display = display;
        /* Usamos ButtonGroup para que cada JToggleButtons cambie de estado
            tras presionar cualquier otro botón */
        ButtonGroup buttonGroup = new ButtonGroup();
        //FlowLayout controlesLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        GridLayout controlesLayout = new GridLayout();
        setLayout(controlesLayout);
        //Box cajaBotones = Box.createHorizontalBox();
                     
        botones.add(new JToggleButton(new ControlTimer("TIMER", 0)));
        botones.add(new JToggleButton(new ControlTimer("EMOM", 1)));
        botones.add(new JToggleButton(new ControlTimer("CLOCK", 2)));
        botones.add(new JToggleButton(new ControlTimer("TABATA", 3)));
        botones.add(new JToggleButton(new ControlTimer("START", 4)));
        botones.add(new JButton(new ControlTimer("STOP", 5)));
        botones.add(new JButton(new ControlTimer("+", 6)));
                     
        for (AbstractButton b: botones) {
            b.setSize(display.getWidth()/botones.size(), display.getHeight()/4);
            buttonGroup.add(b);
            //cajaBotones.add(b);
            add(b);
           
        }
       
        //add(cajaBotones);

               
    }  

    public PanelDisplay getDisplay() {
        return display;
    }
   
    public AbstractButton getButton(int i){
        return botones.get(i);
    }

    class ControlTimer extends AbstractAction {
        
        private int min = 0;
        private int sec = 0;
        private int rounds = 1;
        private ArrayList<Integer> intervalos = new ArrayList();
            
        public ControlTimer(String nombre, int id) {
            putValue(Action.NAME, nombre);
            putValue("id", id);
        }
        
        /* * * * * GETTERS & SETTERS * * * * */
        
        public int getMin() { return min; }
        public void setMin(int min) { this.min = min; }
        public int getSec() { return sec; }
        public void setSec(int sec) { this.sec = sec; }
        public int getRounds() { return rounds; }
        public void setRounds(int rounds) { this.rounds = rounds; }
        public ArrayList<Integer> getIntervalos() { return this.intervalos; }
        
        /* * * * * AUX METHODS * * * * * */
        
        public void intervalosAdd(int min, int sec) { getIntervalos().add(0, min + sec); }
        public void intervalosAdd(int sec) { getIntervalos().add(0, sec); }
        public int tiempo() { return getMin()*60 + getSec(); }

        /* * * * * MAIN FUNCTIONS * * * * * */
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String id = (String) getValue(Action.NAME);
                       
            switch (id) {   /* TIMER */
                case    "TIMER":
                            setMin(Integer.valueOf(JOptionPane.showInputDialog(getDisplay(), "Ingrese minutos", "TIMER", JOptionPane.QUESTION_MESSAGE)));
                            setSec(Integer.valueOf(JOptionPane.showInputDialog(getDisplay(), "Ingrese segundos", "TIMER", JOptionPane.QUESTION_MESSAGE)));
                            getDisplay().setTimer(new ContadorTiempo(tiempo()), id);
                            break;
                           
                            /* EMOM */
                case    "EMOM":
                            setRounds(Integer.valueOf(JOptionPane.showInputDialog(getDisplay(), "Ingrese rounds", "EMOM", JOptionPane.QUESTION_MESSAGE)));
                            getDisplay().setTimer(new ContadorTiempo(ContadorTiempo.EMOM, getRounds()), id);
                            break;
                           
                            /* CLOCK */
                case    "CLOCK":
                            getDisplay().setTimer(new ContadorTiempo(), id);
                            break;
                           
                            /* TABATA */
                case    "TABATA":
                            intervalosAdd(ContadorTiempo.TABATA_ON);
                            intervalosAdd(ContadorTiempo.TABATA_OFF);
                            getDisplay().setTimer(new ContadorTiempo(getIntervalos(), ContadorTiempo.TABATA_ROUNDS), id);
                            break;
                           
                            /* START */
                case    "START":
                            getDisplay().start();
                            break;
                           
                            /* STOP */
                case    "STOP":
                            getDisplay().setTimer(new ContadorTiempo(0,0), id);
                            getDisplay().stop();
                            break;
                     
            }
           
        }

        
    }
}
