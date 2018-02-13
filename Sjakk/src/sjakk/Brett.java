/**
 * Klassen Brett oppretter et spillebrett som foretar handlinger på de individuelle brikkene i sjakkbrettet
 * Brikke[][] brikkene holder på posisjonene sjakkbrikkene har i brettet.
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */
class Brett {

    public static final int BRETTSTORRELSE = 8;
    private int spillNr;
    private Brikke[][] brikkene;
    private Image[] tokens;

    /**
     * Konstruktør for å opprette et spillebrett "klar" for spilling.
     *
     * @param spillNr
     */
    public Brett(int spillNr) {
        // Oppretter tabell tokens og kalle på metode for initialisering 
        tokens = new Image[12];
        createTokens();

        // Initierer brettet for et nytt spill.
        brikkene = new Brikke[BRETTSTORRELSE][BRETTSTORRELSE];

        // Oppretter bønder
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            String hvit = (char) (i + 'a') + "2";
            String sort = (char) (i + 'a') + "7";
            brikkene[6][i] = new Bonde(sort, Color.BLACK, this, tokens[6]);
            brikkene[1][i] = new Bonde(hvit, Color.WHITE, this, tokens[0]);
        }

        // Oppretter tårn
        brikkene[0][0] = new Taarn("a1", Color.WHITE, this, tokens[1]);
        brikkene[0][7] = new Taarn("h1", Color.WHITE, this, tokens[1]);
        brikkene[7][0] = new Taarn("a8", Color.BLACK, this, tokens[7]);
        brikkene[7][7] = new Taarn("h8", Color.BLACK, this, tokens[7]);

        // Oppretter springere
        brikkene[0][1] = new Springer("b1", Color.WHITE, this, tokens[2]);
        brikkene[0][6] = new Springer("g1", Color.WHITE, this, tokens[2]);
        brikkene[7][1] = new Springer("b7", Color.BLACK, this, tokens[8]);
        brikkene[7][6] = new Springer("g7", Color.BLACK, this, tokens[8]);
        
        
        // Oppretter løpere
        brikkene[0][2] = new Loper("a1", Color.WHITE, this, tokens[3]);
        brikkene[0][5] = new Loper("h1", Color.WHITE, this, tokens[3]);
        brikkene[7][2] = new Loper("a8", Color.BLACK, this, tokens[9]);
        brikkene[7][5] = new Loper("h8", Color.BLACK, this, tokens[9]);
        
        // Oppretter konger og dronninger
        brikkene[0][3] = new Dronning("a1", Color.WHITE, this, tokens[4]);
        brikkene[0][4] = new Konge("h1", Color.WHITE, this, tokens[5]);
        brikkene[7][3] = new Dronning("a8", Color.BLACK, this, tokens[10]);
        brikkene[7][4] = new Konge("h8", Color.BLACK, this, tokens[11]);
        
    }

    /**
     * Denne metoden sjekker om et gitt rutenavn er lovlig, altså innenfor de
     * kolonne- og rad-indeksene et sjakkbrett har.
     *
     * @param rutenavn
     * @return
     */
    public static boolean erLovligRutenavn(String rutenavn) {
        // Sjekk om rutenavnet er lovlig.
        /*A-H og 1-8*/
        char kol = rutenavn.charAt(0);
        int rad = rutenavn.charAt(1) - '0';
        return kol >= 'a' && kol <= 'h' && rad >= 1 && rad <= BRETTSTORRELSE;
    }

    /**
     * Denne metoden initierer tabellen tokens med bilder av sjakkbrikke-tokens
     */
    private void createTokens() {
        // Hvite Brikker \\
        // Hvit bonde
        tokens[0] = new Image("/img/hpawn.png");
        // Hvit tårn
        tokens[1] = new Image("/img/hrook.png");
        // Hvit springer
        tokens[2] = new Image("/img/hknight.png");
        // Hivt løper
        tokens[3] = new Image("/img/hbishop.png");
        // Hvit dronning
        tokens[4] = new Image("/img/hqueen.png");
        // Hvit konge
        tokens[5] = new Image("/img/hking.png");

        // Sorte brikker \\
        // Sort bonde
        tokens[6] = new Image("/img/bpawn.png");
        // Sort tårn
        tokens[7] = new Image("/img/brook.png");
        // Sort springer
        tokens[8] = new Image("/img/bknight.png");
        // Sort løper
        tokens[9] = new Image("/img/bbishop.png");
        // Sort dronning
        tokens[10] = new Image("/img/bqueen.png");
        // Sort Konge
        tokens[11] = new Image("/img/bking.png");
    }

    /**
     * Denne metoden henter en brikke fra fra brettet, posisjon angitt med
     * rutenavn som er sjakk-koordinater
     *
     * @param rutenavn
     * @return Brikke
     */
    public Brikke getBrikke(String rutenavn) {
        int kolonneindex = (int) rutenavn.charAt(0) - 'a';
        int radindex = (int) rutenavn.charAt(1) - '1';
        if (brikkene[radindex][kolonneindex] != null) {
            return brikkene[radindex][kolonneindex];
        } else {
            return null;
        }
    }

    /**
     * Denne metoden flytter en brikke fra en rute til en annen rute dersom
     * rutenavnene er lovlige, og fraRuten inneholder en brikke.
     *
     * @param fraRute
     * @param tilRute
     * @return boolean
     */
    public boolean flyttBrikke(String fraRute, String tilRute) {
        // Flytter brikke hvis lovlig/mulig.
        boolean oktrekk = false;
        if (erLovligRutenavn(fraRute) && erLovligRutenavn(tilRute) && getBrikke(fraRute) != null) {

            // Henter posisjoner til de aktuelle rutene. Lovlighetssjekk på rute utført, altså innenfor brettet
            int kfraIndex = (int) fraRute.charAt(0) - 'a';
            int rfraIndex = (int) fraRute.charAt(1) - '1';
            int ktilIndex = (int) tilRute.charAt(0) - 'a';
            int rtilIndex = (int) tilRute.charAt(1) - '1';

            if (brikkene[rfraIndex][kfraIndex].flyttTil(tilRute)) {
                // Flytter brikken, setter gammel posisjon til null
                brikkene[rtilIndex][ktilIndex] = brikkene[rfraIndex][kfraIndex];
                brikkene[rfraIndex][kfraIndex] = null;
                oktrekk = true;
            }
        }
        return oktrekk;
    }

    public int getSpillNr() {
        return spillNr;
    }

    public void setSpillNr(int spillNr) {
        this.spillNr = spillNr;
    }

    public Brikke[][] getBrikkene() {
        return brikkene;
    }

    public void setBrikkene(Brikke[][] brikkene) {
        this.brikkene = brikkene;
    }
}
