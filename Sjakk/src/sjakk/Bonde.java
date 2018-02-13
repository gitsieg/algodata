/**
 * Brikkemodell for sjakkbrikke.
 * Denne klassen har kun de enkelste lovlighetssjekkene på trekk.
 * Sjekker kun:
 * - På at tilrute ikke kan vær lik brikkens egen plassering
 * - Dersom det er en brikke der den skal flyttes til, kan den ikke være av samme farge.
 *
 * Gjenstår:
 * - Legge inn antall trekk bonden har utført
 * -- Sjekke på at det første trekket brikken gjør, kan gå 2 steg.
 * -- Sjekke for En Passant, kreves nok en dypere logikk en grunnleggende bevegele
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */
public class Bonde extends Brikke {

    public Bonde(String rutenavn, Color farge, Brett brett, Image token) {
        super(rutenavn, farge, brett, token);
    }

    //Sjekker lovligheten av et gitt trekk til tilRute, returnerer true dersom trekket er lovlig.
    @Override
    public boolean erLovligTrekk(String tilRute) {
        int rfra = rutenavn.charAt(1) - '1';
        int kfra = rutenavn.charAt(0) - 'a';
        int rtil = tilRute.charAt(1) - '1';
        int ktil = tilRute.charAt(0) - 'a';

        if (tilRute == rutenavn) {
            return false;
        }
        if (brett.getBrikke(tilRute) != null) {
            if (farge == brett.getBrikke(tilRute).getFarge()) {
                return false;
            }
        }
        return true;
    }

    // Returnerer brikkenavn som String.
    @Override
    public String brikkenavn() {
        return "B";
    }

    // Benytter seg av erLovligTrekk- metoder for å flytte brikken.
    // Ved oppfylte kriterier, blir brikkens rutenavn endret. Returnerer så true, og må behandles for 
    // å "fysisk" endre brikkeplassering i brikketabellen
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
    public Image getToken() {
        return token;
    }

}
