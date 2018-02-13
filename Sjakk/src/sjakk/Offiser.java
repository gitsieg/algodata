/**
 * Superklasse alle offiserbrikker, altså brikker som ikke er bønder.
 */
package sjakk;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */

public abstract class Offiser extends Brikke {
    
    public Offiser(String rutenavn, Color farge, Brett brett, Image token) {
        super(rutenavn, farge, brett, token);
    }
    

    
}
