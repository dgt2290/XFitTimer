
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FX
 */
public class ContadorTiempo implements Serializable {
    
    public static final int EMOM = 60;
    public static final int TABATA_ON = 20;
    public static final int TABATA_OFF = 10;
    public static final int TABATA_ROUNDS = 8;
    public static final int PREP_TIME = 10;

    private int t, i, r; //Contadores
    private int rounds;
    private ArrayList<Integer> intervalos = new ArrayList();

    public ContadorTiempo(ArrayList intervalos, int rounds) {
        this.intervalos = intervalos;
        getIntervalos().add(PREP_TIME +1);
        this.i = getIntervalos().size() - 1; //Posición del intervalo prepTime
        this.t = getIntervalos().get(get_i());
        getIntervalos().trimToSize();
        this.rounds = rounds;
        this.r = rounds;
    }

    public ContadorTiempo(ArrayList intervalos) {
        this.intervalos = intervalos;
        getIntervalos().add(PREP_TIME +1);
        this.i = getIntervalos().size() - 1; //Posición del intervalo prepTime
        this.t = getIntervalos().get(get_i());
        getIntervalos().trimToSize();
        
    }
    
    public ContadorTiempo(int sec, int rounds) {
        intervalos.add(sec);
        getIntervalos().add(PREP_TIME +1);
        this.i = getIntervalos().size() - 1; //Posición del intervalo prepTime
        this.t = getIntervalos().get(get_i());
        getIntervalos().trimToSize();
        this.rounds = rounds;
        this.r = rounds;
    }
    
    public ContadorTiempo(int sec) { 
        getIntervalos().add(sec);
        getIntervalos().add(PREP_TIME +1);
        this.i = getIntervalos().size() - 1; //Posición del intervalo prepTime
        this.t = getIntervalos().get(get_i());
        getIntervalos().trimToSize();
    }
    
    public ContadorTiempo() {
        this.i = PREP_TIME +1;
    }

    /* * * * * GETTERS & SETTERS * * * * */

    public int get_t() { return this.t; }
    public void set_t(int t) { this.t = t; }
    public int get_i() { return this.i; }
    public void set_i(int i) { this.i = i; }
    public int get_r() { return this.r; }
    public void set_r(int r) { this.r = r; }     
    public int getRounds() { return this.rounds; }
    public void setRounds(int rounds) { this.rounds = rounds; } 
    public ArrayList<Integer> getIntervalos() { return this.intervalos; }
    public void setIntervalos(ArrayList<Integer> intervalos) { this.intervalos = intervalos; } 

    /* * * * * AUX METHODS * * * * * */

    /**
     * Reducen los contadores en -1
     */
    private void t_1() { set_t(get_t() -1); }
    private void i_1() { set_i(get_i() -1); }
    private void r_1() { set_r(get_r() -1); }

    /**
     * Comprueban si ( contadores > x )
     */
    private boolean t0() { return (get_t() > 0); }
    private boolean i0() { return (get_i() > 0); }
    private boolean r1() { return (get_r() > 1); }
    
    /**
     * Resetean los contadores
     */
    private void reset_t () { set_t(getIntervalos().get(get_i()) -1); } //Al llegar a 00:00 empieza a contar desde 00:59
    private void reset_i () { set_i(getIntervalos().size() -2); } //Ignora el prepTime
    
    /* * * * * MAIN FUNCTIONS * * * * * */
    
    public boolean contar() { 

        boolean contar = true;

        if (t0()) {
            t_1();
        } else if (i0()) {
            i_1();
            reset_t();
        } else if (r1()) {
            r_1();
            reset_i();
            reset_t();
        } else {
            contar = false;
        }

        return contar;

    }

    public boolean avanzar(){
        
        boolean avanzar = false;
        
        if (i0()) {
            i_1();
            set_t(get_i());
        } else {
            set_t(get_t() +1);
            avanzar = true;
        }
        
        return avanzar;
    }
    
}