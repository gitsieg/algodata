/**
 * Denne klassen er en modellklasse for en sjakkbrikke av typen Tårn.
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */
public class Taarn extends Offiser {

    public Taarn(String rutenavn, Color farge, Brett brett, Image token) {
        super(rutenavn, farge, brett, token);
    }
    

    @Override
    public boolean erLovligTrekk(String tilRute) {
        char rfra = rutenavn.charAt(1);
        char kfra = rutenavn.charAt(0);
        char rtil = tilRute.charAt(1);
        char ktil = tilRute.charAt(0);
        
        // Returnerer false for ulovlige trekk.
        // Sjekk på samme rad eller samme kolonne, dersom ingen er sanne, ugyldig trekk
        if (tilRute == rutenavn) {
            return false;
        }
        if (kfra != ktil && rfra != rtil) {
            return false;
        }
        if (brett.getBrikke(tilRute) != null) {
            if (farge == brett.getBrikke(tilRute).getFarge()) {
                return false;
            }
        }
        if (rfra < rtil && (rtil - rfra) > 1) // Nedover, over en rute differanse.
        {
            for (int i = rfra + 1; i < rtil; i++) {
                String brikkeSjekk = kfra + "" + (char) i;
                if (brett.getBrikke(brikkeSjekk) != null) {
                    return false;
                }
            }
        }
        if (rfra > rtil && (rfra - rtil) > 1) // Oppover, over en rute differanse.
        {
            for (int i = rfra - 1; i > rtil; i--) {
                String brikkeSjekk = kfra + "" + (char) i;
                if (brett.getBrikke(brikkeSjekk) != null) {
                    return false;
                }
            }
        }
        if (kfra < ktil && (ktil - kfra) > 1) // Høyre, over en rute differanse
        {
            for (int i = kfra + 1; i < ktil; i++) {
                String brikkeSjekk = (char) i + "" + rfra;
                if (brett.getBrikke(brikkeSjekk) != null) {
                    return false;
                }
            }
        }
        if (kfra > ktil && (kfra - ktil) > 1) // Venstre, over en rute differanse
        {
            for (int i = kfra - 1; i > ktil; i--) {
                String brikkeSjekk = (char) i + "" + rfra;
                if (brett.getBrikke(brikkeSjekk) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String brikkenavn() {
        return "T";
    }

    @Override
    public boolean flyttTil(String rutenavn) {
        if (erLovligTrekk(rutenavn)) {
            this.rutenavn = rutenavn;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getRutenavn() {
        return rutenavn;
    }

    @Override
    public void setRutenavn(String rutenavn) {
        this.rutenavn = rutenavn;
    }

    @Override
    public Color getFarge() {
        return farge;
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    public Brett getBrett() {
        return brett;
    }

    public void setBrett(Brett brett) {
        this.brett = brett;
    }

}
