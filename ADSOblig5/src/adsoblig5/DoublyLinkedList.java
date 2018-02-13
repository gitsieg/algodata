package adsoblig5;


import java.util.NoSuchElementException;

/**
 * <h3>Klasse for å representere en DoublyLinkedList</h3>
 * <hr>
 * <b>Klassens metoder: </b>
 * <ul>
 * <li>DoublyLinkedList - Oppretter en ny DoublyLinkedList som er tom</li>
 * <li>moveToHead - Flytter current til head</li>
 * <li>moveToTail - Flytter current til tail</li>
 * <li>first - Flytter current til den første "reelle" noden i listen</li>
 * <li>last - Flytter current til den siste "reelle" noden i listen</li>
 * <li>advance - Flytter current til current sin neste</li>
 * <li>retreat - Flytter current til current sin forrige</li>
 * <li>hasNext - Sjekker om current har en reell node som neste</li>
 * <li>hasPrevious - Sjekker om current har en reell node som forrige</li>
 * <li>retrieveNext - Returnerer current sin neste sitt element</li>
 * <li>retrievePrevious - Returnerer current sitt forrige element</li>
 * <li>retrieve - Returnerer current sitt element</li>
 * <li>insertFirsElement - Setter inn det første elementet i listen</li>
 * <li>insertBefore - Setter inn en ny node med element som current sin
 * forrige</li>
 * <li>insertAfter - Setter inn en ny node med element som current sin
 * neste</li>
 * <li>isValid - Sjekker om current står på et reellt objekt</li>
 * <li>equals - Sjekker likhet mellom to DoublyLinkedList</li>
 * <li>size - Returnerer antall reelle objekter i listen</li>
 * <li>toString - Returnerer en String- representasjon av listen</li>
 * <li>makeEmpty - Gjør listen tom</li>
 * <li>isEmpty</li>
 * <li>insertAsFirts</li>
 * <li>insertAsLast</li>
 * </ul>
 *
 * @author Atle Amundsen, Nikolai Fosså, Kristian Robertsen
 * @param <AnyType> Hvilken klasse objektene som legges inn er av
 */
public class DoublyLinkedList<AnyType> {

    private Node head, tail, current;
    private int size;

    /**
     * Klassekonstruktør som oppretter en ny DoublyLinkedList
     * <br>
     * <h3>Initialisert til:</h3>
     * <ul>
     * <li>size = 0</li>
     * <li>head og tail peker på to forskjellige noder som er lenket med
     * previous og next</li>
     * </ul>
     */
    public DoublyLinkedList() {
        makeEmpty();
    }

    /**
     * Flytter current-pekeren til den noden som head peker på
     */
    public void moveToHead() {
        current = head;
    }

    /**
     * Flytter current-pekeren til den noden som tail peker på
     */
    public void moveToTail() {
        current = tail;
    }

    /**
     * Flytter current-pekeren til head-nodens neste peker
     */
    public void first() {
        if (size == 0) {
            throw new NoSuchElementException("No elements in the list. Cannot move to first");
        }
        current = head.next;
    }

    /**
     * Flytter current-pekeren til tail-nodens forrige peker
     */
    public void last() {
        if (size == 0) {
            throw new NoSuchElementException("No elements in the list. Cannot move to last");
        }
        current = tail.previous;
    }

    /**
     * Flytter current pekeren til current sin forrige node. Kaster unntak når:
     * - listen er tom - current peker på head
     */
    public void retreat() {
        if (current == head) {
            throw new UnsupportedOperationException("Cannot retreat before head");
        }
        current = current.previous;
    }

    /**
     * Flytter current pekeren til current sin neste node. Kaster unntak når:
     * -listen er tom - current peker på tail
     */
    public void advance() {
        if (current == tail) {
            throw new UnsupportedOperationException("Cannot advance past tail");
        }
        current = current.next;
    }

    /**
     * Sjekker om current sin neste-peker er et reelt objekt.
     *
     * @return true dersom currents neste node ikke er tail.
     */
    public boolean hasNext() {
        if (current.next == tail) {
            return false;
        }
        return current.next != null;
    }

    /**
     * Sjekker om current sin forrige-peker er et reelt objekt.
     *
     * @return true dersom currents neste node ikke er head
     */
    public boolean hasPrevious() {
        if (current.previous == head) {
            return false;
        }
        return current.previous != null;
    }

    /**
     * @return currents neste node sitt element dersom det ikke er tail
     */
    public AnyType retrieveNext() {
        if (current.next == tail) {
            throw new NoSuchElementException("No such element in tail");
        }
        return current.next.element;
    }

    /**
     * @return currents forrige node sitt element dersom det ikke er head
     */
    public AnyType retrievePrevious() {
        if (current.previous == head) {
            throw new NoSuchElementException("No such element in head");
        }
        return current.previous.element;
    }

    /**
     * Fjerne alle pekere til noder i listen.
     */
    public void makeEmpty() {
        current = head = new Node();
        tail = new Node(head, null);
        head.next = tail;
         size = 0;
    }

    /**
     * Sletter og returnerer elementet til noden som current peker på. Kaster
     * unntak når: - listen er tom - current peker på head - current peker på
     * tail
     *
     * @return elementet til noden som current peker på
     */
    public AnyType remove() {
        AnyType element = current.element;
        if (!isValid()) {
            throw new UnsupportedOperationException("Current is not valid");
        }
        current.previous.next = current.next;
        current.previous.next.previous = current.previous;
        current = current.previous;
        size--;
        return element;
    }

    /**
     * Henter elementet til noden som current peker på. Kaster unntak når: -
     * listen er tom - current peker på head - current peker på tail
     *
     * @return elementet til det noden som current peker på.
     */
    public AnyType retrieve() throws NoSuchElementException {
        if (current == head || current == tail) {
            throw new NoSuchElementException("Cannot retrieve from element from head or tail");
        }
        return current.element;
    }
    
    /**
     * Tar et element og setter det inn som første element i listen.
     * @param element elementet som skal settes inn i listen
     */
    public void insertAsFirst(AnyType element) {
        Node tmp = current;
        current = head;
        insertAfter(element);
        current = tmp;
    }
    
    /**
     * Tar et element og setter det inn som siste element i listen. 
     * @param element elementet som skal settes inn i listen
     */
    public void insertAsLast(AnyType element) {
        Node tmp = current;
        current = tail;
        insertBefore(element);
        current = tmp;
    }
    
    /**
     * Hjelpemetode som oppretter en node og tilordner pekere til det første
     * elementet som legges inn i listen Gir illusjon av at når listen er tom,
     * så peker current mellom head og tail
     *
     * @param element
     */
    private void insertFirstElement(AnyType element) {
        current = new Node(element, head, tail);
        tail.previous = current;
        head.next = current;
    }

    /**
     * Setter inn et eller flere elementer i listen etter current pekeren.
     * Dersom listen er tom, kalles insertFirstElement.
     * <br>
     * <b>Kaster unntak dersom:</b>
     * <br>
     * <ul>
     * <li>current peker på tail</li>
     * </ul>
     *
     * @param elements en eller flere elementer som skal legges inn i listen
     * etter current
     */
    public void insertAfter(AnyType... elements) {
        for (AnyType x : elements) {
            if (size == 0) {
                insertFirstElement(x);
            } else if (current == tail) {
                throw new UnsupportedOperationException("Cannot insert element after tail.");
            } else {
                current.next.previous = current.next = new Node(x, current, current.next);
            }
            size++;
        }
    }

    /**
     * Setter inn et eller flere elementer i listen før current pekeren. Dersom
     * listen er tom, kalles insertFirstElement.
     * <br>
     * <b>Kaster unntak dersom:</b>
     * <br>
     * <ul>
     * <li>current peker på head</li>
     * </ul>
     *
     * @param elements en eller flere elementer som skal legges inn i listen før
     * current
     */
    public void insertBefore(AnyType... elements) {
        for (AnyType x : elements) {
            if (size == 0) {
                insertFirstElement(x);
            } else if (current == head) {
                throw new UnsupportedOperationException("Cannot insert element at before head.");
            } else {
                current.previous.next = current.previous = new Node(x, current.previous, current);
            }
            size++;
        }
    }

    /**
     * @return true dersom current peker på et et element som ikke er head eller
     * tail
     */
    public boolean isValid() {
        return current != head && current != tail;
    }

    /**
     * @return true dersom listen er tom, false ellers
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o listen som det skal vurdere likhet mot
     * @return true dersom listene er like
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        DoublyLinkedList<AnyType> otherList = (DoublyLinkedList<AnyType>) o;

        if (size != otherList.size()) {
            return false;
        }
        Node thisPeker = head;
        Node otherPeker = otherList.head;

        while (thisPeker.next != tail) {
            if ((thisPeker.next.element == null && otherPeker.next.element != null) // Tar hånd om nullpekere
                    || (otherPeker.next.element != null && otherPeker.next.element == null)) {
                return false;
            }
            if (thisPeker.next.element != null && otherPeker.next.element != null) {
                if (!thisPeker.next.element.equals(otherPeker.next.element)) {
                    return false;
                }
            }
            thisPeker = thisPeker.next;
            otherPeker = otherPeker.next;
        }
        return true;
    }

    /**
     *
     * @return en string som representerer innholdet i listen.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        if (size == 0) {
            return sb.append(']').toString();
        }

        Node p = head;
        while (p.next != tail) {
            if (p.next.element == null) {
                sb.append("null");
            } else {
                sb.append(p.next.element.toString());
            }
            if (p.next.next != tail) {
                sb.append(", ");
            }
            p = p.next;
        }
        return sb.append(']').toString();
    }

    /**
     *
     * @return antall elementer ligger i listen
     */
    public int size() {
        return size;
    }

    /**
     * Klasse for å representere en node i en DoublyLinkedList
     *
     * @param <AnyType>
     */
    private class Node {

        Node next, previous;
        AnyType element;

        /**
         * Klassekonstruktør som oppretter en tom node
         */
        public Node() {
            next = previous = null;
            element = null;
        }

        public Node(Node previous, Node next) {
            this.previous = previous;
            this.next = next;
        }

        /**
         * Klassekonstruktør som oppretter en node med innhold, og pekere til
         * forrige og neste node i listen.
         *
         * @param element
         * @param previous
         * @param next
         */
        public Node(AnyType element, Node previous, Node next) {
            this.element = element;
            this.previous = previous;
            this.next = next;
        }
    }
}
