/**
 * Representerer en rute i labyrinten, med tilhorende rad og kolonnenummer.
 * 
 * Rutene kan ha naboruter i alle fire himmelretninger og har setter/getter-metoder
 * for naboruter. En rute har ogsaa en finnUtvei metode for aa finne veien til
 *  naermeste aapning i labyrinten. finnUtvei kaller paa gaa-metoden som rekursivt
 *  sjekker om naborutene er aapningsruter.
 * 
 * @author Oskar Randen
 */

abstract class Rute {
    private int kolonne;
    private int rad;
    private Labyrint labyrint;
    private Rute nord;
    private Rute sor;
    private Rute ost;
    private Rute vest;
    private boolean besokt = false;

    public Rute(Labyrint lab, int r, int kol) {
        kolonne = kol;
        rad = r;
        labyrint = lab;
    }

    void settNord(Rute rute) {
        nord = rute;
    }

    void settSor(Rute rute) {
        sor = rute;
    }

    void settOst(Rute rute) {
        ost = rute;
    }

    void settVest(Rute rute) {
        vest = rute;
    }

    Rute hentNord() {
        return nord;
    }

    Rute hentSor() {
        return sor;
    }

    Rute hentOst() {
        return ost;
    }

    Rute hentVest() {
        return vest;
    }

    int hentRad() {
        return rad;
    }

    int hentKolonne() {
        return kolonne;
    }

    boolean hentBesokt(){
        return besokt;
    }

    void besok(){
        besokt = true;
    }

    /**
     * Bygger opp en string med utveien til labyrinten ved aa rekursivt sjekke
     * noen av naborutene er utveier. 
     * 
     * Sjekker ikke den forrige ruten i utveien. Hvis ruten er sort sjekkes ikke
     * naborutene. 
     * 
     * @param rute den naaverende ruten som sjekkes.
     * @param string stringen med retningen til den forrige ruten i utveien. 
     * @param string stringen med ruter ut av labyrinten som er bygget opp hittil
     */

    void gaa(Rute r, String retning, String vei) {

        r.besok();

        String forelopigVei = vei;

        if (r.erAapning() == false) {
            forelopigVei = vei + "(" + r.hentKolonne() + ", " + r.hentRad() + ")" + " --> ";
        }

        if (r.tilTegn() == '#') {
            return;
        }

        if (r.erAapning()) {
            forelopigVei = vei + "(" + r.hentKolonne() + ", " + r.hentRad() + ")";
            labyrint.leggTilVei(forelopigVei);
            return;
        }

        if (r.tilTegn() != '#') {
            System.out.println(r.hentRad() + " " + r.hentKolonne());
            if (!r.hentNord().hentBesokt()) {
                gaa(r.hentNord(), "nord", forelopigVei);
            }
            if (!r.hentSor().hentBesokt()) {
                gaa(r.hentSor(), "sor", forelopigVei);
            }
            if (!r.hentOst().hentBesokt()) {
                gaa(r.hentOst(), "ost", forelopigVei);
            }
            if (!r.hentVest().hentBesokt()) {
                gaa(r.hentVest(), "vest", forelopigVei);
            }
        }

        return;
    }

    void finnUtvei() {
        String vei = "";
        gaa(this, " ", vei);
    }

    boolean erAapning() {
        return false;
    }

    abstract char tilTegn();
}