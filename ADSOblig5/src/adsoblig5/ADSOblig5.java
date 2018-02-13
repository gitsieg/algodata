
package adsoblig5;

/** 
 * Test av metoder for DoublyLinkedList.java
 * <hr>
 * @author Atle Amundsen, Nikolai Fosså, Kristian Robertsen
 */
public class ADSOblig5 {

    public static void main(String[] args) {
        System.out.println("########LIST1########");
        DoublyLinkedList<String> dlist1 = new DoublyLinkedList<>();
        dlist1.insertBefore("Hei", "på", "deg", "du",  "ditt", null);
        System.out.println(dlist1.size());
        System.out.println(dlist1.toString());
        
        
        System.out.println("########LIST2########");
        DoublyLinkedList<String> dlist2 = new DoublyLinkedList<>();
        dlist2.insertAfter("Hvordan", "kan", "dette", "ha", "seg");
        System.out.println("Listens representert: " + dlist2.toString());
        dlist2.moveToTail();
        System.out.println("Har den previous: " + dlist2.hasPrevious());
        System.out.println("Hva er previous: " + dlist2.retrievePrevious());
        dlist2.retreat();
        System.out.println("Sletter: " + dlist2.remove());
        System.out.println("Listens størrelse: " + dlist2.size());
        System.out.println("Listen representert: " +dlist2.toString());
        System.out.println("Står current på noe reellt: " + dlist2.isValid());
        System.out.println("Hvor current er nå: " + dlist2.retrieve());
        System.out.println("Er neste reell: " + dlist2.hasNext());
        System.out.println("Er forrige reell: " + dlist2.hasPrevious());
        dlist2.moveToHead();
        System.out.println("Er head reell: " + dlist2.isValid());
        dlist2.first();
        dlist2.advance();
        dlist2.advance();
        System.out.println("Hvor står current nå: " + dlist2.retrieve());
        System.out.println("Har current reell neste: " + dlist2.hasNext());
        System.out.println("Hent current sin neste: " + dlist2.retrieveNext());
        System.out.println("Er dlist1 lik dlist2: " + dlist2.equals(dlist1));
        dlist2.moveToTail();
        dlist2.last();
        System.out.println("Sletter: " + dlist2.remove());
        System.out.println("Sletter: " + dlist2.remove());
        System.out.println("Hvordan ser listen ut nå: " + dlist2.toString());
        System.out.println("Og hva er størrelsen: " + dlist2.size());
        dlist2.remove();
        dlist2.insertBefore("Hei", "på", "deg", "du", "ditt", null);
        System.out.println("LIST1" + dlist1.toString());
        System.out.println("LIST2" + dlist2.toString());
        System.out.println("Er listene like nå: " + dlist2.equals(dlist1));
        System.out.println("Er listen tom: " + dlist2.isEmpty());
        dlist2.makeEmpty();
        System.out.println(dlist2.toString());
        System.out.println("Er den tom nå: " + dlist2.isEmpty());
        dlist2.insertAfter("Legger", "inn", "elementer");
        System.out.println(dlist2.toString());
        dlist2.insertAsFirst("i");
        dlist2.insertAsLast("listen");
        System.out.println(dlist2.toString());
        System.out.println("Current har ikke flyttet seg: " + dlist2.retrieve());
        dlist1.makeEmpty();
        dlist2.makeEmpty();
        dlist1.insertBefore("Hallo", "hvordan", "står", "det", "til?");
        dlist2.insertBefore("Hallo", "hvordan", "står", "det", "til?");
        
        System.out.println(dlist1.toString());
        System.out.println(dlist2.toString());
        System.out.println("Like nå : " + dlist1.equals(dlist2));
        
    }
}
