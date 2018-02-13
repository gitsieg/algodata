/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Rockass
 */
public class Springer extends Brikke {

    public Springer(String rutenavn, Color farge, Brett brett, Image token) {
        super(rutenavn, farge, brett, token);
    }

    @Override
    public boolean erLovligTrekk(String tilRute) {
        char rfra = rutenavn.charAt(1);
        char kfra = rutenavn.charAt(0);
        char rtil = tilRute.charAt(1);
        char ktil = tilRute.charAt(0);

        if (tilRute == rutenavn) {
            return false;
        }
        if (brett.getBrikke(tilRute) != null) {
            if (farge == brett.getBrikke(tilRute).getFarge()) {
                return false;
            }
        }
        if ((rfra == rtil + 2) && (kfra == ktil + 1)) {
            return true;
        } else if ((rfra == rtil + 1) && (kfra == ktil + 2)) {
            return true;
        } else if ((rfra == rtil - 2) && (kfra == ktil + 1)) {
            return true;
        } else if ((rfra == rtil - 1) && (kfra == ktil + 2)) {
            return true;
        } else if ((rfra == rtil + 2) && (kfra == ktil - 1)) {
            return true;
        } else if ((rfra == rtil + 1) && (kfra == ktil - 2)) {
            return true;
        } else if ((rfra == rtil - 1) && (kfra == ktil - 2)) {
            return true;
        } else if ((rfra == rtil - 2) && (kfra == ktil - 1)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String brikkenavn() {
        return "S";
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

    public Brett getBrett() {
        return brett;
    }

    public void setBrett(Brett brett) {
        this.brett = brett;
    }

    public Image getToken() {
        return token;
    }

    public void setToken(Image token) {
        this.token = token;
    }

}
