/**
 * En aapningsrute er en hvit rute som ligger langs kanten til labyrinten 
 */

class Aapning extends HvitRute {
    Aapning(Labyrint lab, int r, int kol) {
        super(lab, r, kol);
    }

    @Override
    boolean erAapning() {
        return true;
    }
}
