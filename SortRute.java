class SortRute extends Rute {
    SortRute(Labyrint lab, int r, int kol) {
        super(lab, r, kol);
    }

    @Override
    char tilTegn() {
        return '#';
    }
}