import java.util.Iterator;

/**
 * Representerer en lenkeliste.
 *
 * @author Oskar Randen
 */
class Lenkeliste<T> implements Liste<T> {
    /**
     * Representerer en Node i lenkelisten
     */
    class Node {
        Node neste;
        T data;

        Node(T x) {
            data = x;
        }
    }

    protected Node start;

    class LenkelisteIterator implements Iterator<T> {

        private Lenkeliste<T> liste;
        private int posIter;

        public LenkelisteIterator(Lenkeliste<T> nyLL) {
            liste = nyLL;
            posIter = 0;
        }

        public boolean hasNext() {
            return (posIter < liste.stoerrelse());
        }

        public T next() {
            return liste.hent(posIter++);
        }

        public void remove() {
        }
    }

    public Iterator iterator() {
        return new LenkelisteIterator(this);
    }

    /**
     * Returnerer antall Noder i lenkelisten.
     *
     * @return En int som representerer storelsen til listen.
     */
    public int stoerrelse() {
        Node n = start;
        int teller = 0;
        while (n != null) {
            teller++;
            n = n.neste;
        }
        return teller;
    }

    /**
     * Legger til en Node bakerst i lenkelisten med data x.
     *
     * @param x Et typeparameter T som representerer dataen til noden.
     */
    public void leggTil(T x) {

        if (start == null) {
            Node n = new Node(x);
            start = n;
        } else {
            Node n = start;
            while (n.neste != null) {
                n = n.neste;
            }
            n.neste = new Node(x);
        }
    }

    /**
     * Legger til en Node på posisjon pos i lenkelisten.
     *
     * @param pos En int med posisjonen i lenkelisten hvor Noden skal legges inn.
     * @param x   Et typeparameter T som representerer dataen til noden.
     */
    public void leggTil(int pos, T x) {

        // Hvis posisjonen refererer til en Node som ikke finnes, kast unntak.
        if (pos < 0 || pos > this.stoerrelse()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node n = start;
        int teller = 0;

        // Hvis posisjonen er 0, lag en ny head-Node.
        if (pos == 0) {
            Node startForrige = start;
            start = new Node(x);
            start.neste = startForrige;
        }

        else {
            // Teller seg frem til Noden forran posisjonen til Noden som skal legges inn.
            while (teller != pos - 1) {
                n = n.neste;
                teller++;
            }

            /**
             * Mellomlagrer den neste Noden, bytter den ut med den nye Noden, og gjor den
             * mellomlagrede noden til den nye nodens neste-Node.
             */

            Node forrigeNeste = n.neste;
            Node ny = new Node(x);

            n.neste = ny;
            n.neste.neste = forrigeNeste;
        }
    }

    /**
     * Endrer data-verdien til en Node på posisjon pos i lenkelisten.
     *
     * @param pos En int med posisjonen i lenkelisten til Noden som skal endres.
     * @param x   Et typeparameter T som representerer den nye dataen til noden.
     */
    public void sett(int pos, T x) {

        if (pos < 0 || pos >= this.stoerrelse()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node n = start;
        int teller = 0;

        while (teller != pos) {
            n = n.neste;
            teller++;
        }
        n.data = x;
    }

    /**
     * Returnerer data-verdien til Noden på posisjon pos.
     *
     * @param pos Int med posisjonen til Nod.en som dataen skal hentes fra.
     * @return Dataen til Noden,
     */
    public T hent(int pos) {

        if (pos < 0 || pos >= this.stoerrelse()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node n = start;
        int teller = 0;

        while (teller != pos) {
            n = n.neste;
            teller++;
        }

        return n.data;
    }

    /**
     * Fjerner Noden på posisjon pos fra lenkelisten og returnerer data-verdien til
     * den fjernede Noden.
     *
     * @param pos Int med posisjonen til noden som skal fjernes.
     * @return Dataen til noden som ble fjernet.
     */
    public T fjern(int pos) {

        if (pos < 0 || pos >= this.stoerrelse()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node n = start;
        int teller = 0;

        if (pos == 0) {
            T dataTemp = start.data;
            start = start.neste;
            return dataTemp;
        }

        else {
            while (teller != pos - 1) {
                n = n.neste;
                teller++;
            }

            /** Gjor neste-Noden til Noden som skal fjernes til Noden som ligger bak sin
            neste-Node.*/
            Node skalFjernes = n.neste;
            n.neste = skalFjernes.neste;
            return skalFjernes.data;
        }
    }

    /**
     * Fjerner den første Noden fra lenkelisten.
     *
     * @return Dataen til den første noden.
     */
    public T fjern() {

        if (start == null) {
            throw new UgyldigListeIndeks(0);
        }

        T dataTemp = start.data;
        start = start.neste;
        return dataTemp;
    }
}
