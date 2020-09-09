/**
 * Hvite ruter er ruter man kan gaa gjennom
 */
class HvitRute extends Rute {
    HvitRute(Labyrint lab, int r, int kol) {
        super(lab, r, kol);
    }

    @Override
    char tilTegn() {
        return '.';
    }
}