/**
 * Abstrakt superklasse for alle sjakkbrikker.
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */

public abstract class Brikke {

    protected String rutenavn;
    protected Color farge;
    protected Brett brett;
    protected Image token;

    public Brikke(String rutenavn, Color farge, Brett brett, Image token) {
        this.rutenavn = rutenavn;
        this.farge = farge;
        this.brett = brett;
        this.token = token;
    }

    /**
     * Denne metoden sjekker om et trekk til en rute er lovlig for brikken Tårn.
     *
     * @param rutenavn
     * @return boolean
     */
    public abstract boolean erLovligTrekk(String rutenavn);
    
    /**
     * Denne metoden returnerer en brikkes brikkenavn
     * 
     * @return 
     */
    public abstract String brikkenavn();
    
    /**
     * Denne metoden endrer brikkens rutenavn til String rutenavn,
     * dersom trekket erLovligTrekk() gir true.
     * @param rutenavn
     * @return boolean
     */
    public abstract boolean flyttTil(String rutenavn);

    // Getters-Setters
    public String getRutenavn() {
        return rutenavn;
    }

    public void setRutenavn(String rutenavn) {
        this.rutenavn = rutenavn;
    }

    public Color getFarge() {
        return farge;
    }

    public void setFarge(Color farge) {
        this.farge = farge;
    }
    
    public void setToken() {
    }
    
    public Image getToken() {
        return token;
    }

}
