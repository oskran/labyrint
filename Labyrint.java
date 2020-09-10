import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Labyrint {
    Rute[][] ruter;
    int antallRader;
    int antallKolonner;
    Lenkeliste<String> veiListe = new Lenkeliste<>();

    private Labyrint(Rute[][] ruterInn, int antallRader, int antallKolonner) {
        ruter = ruterInn;
        this.antallRader = antallRader;
        this.antallKolonner = antallKolonner;
    }

    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
        Scanner scan = new Scanner(fil);

        String[] linje1 = scan.nextLine().split(" ");
        int raderInn = Integer.parseInt(linje1[0]);
        int kolonnerInn = Integer.parseInt(linje1[1]);
        Rute[][] ruterInn = new Rute[raderInn][kolonnerInn];

        Labyrint lab = new Labyrint(ruterInn, raderInn, kolonnerInn);

        // Legg inn ruter i labyrinter
        for (int i = 0; i < raderInn; i++) {
            String[] linje = scan.nextLine().split("");
            for (int j = 0; j < kolonnerInn; j++) {
                if (linje[j].equals(".")) {
                    if (erAapning(raderInn, kolonnerInn, i, j)) {

                        ruterInn[i][j] = new Aapning(lab, i, j);
                    } else {
                        ruterInn[i][j] = new HvitRute(lab, i, j);

                    }
                } else if (linje[j].equals("#")) {
                    ruterInn[i][j] = new SortRute(lab, i, j);
                }
            }
        } 

        scan.close();

        // Legg til naboer
        for (int rad = 0; rad < raderInn; rad++) {
            for (int kol = 0; kol < kolonnerInn; kol++) {
                if (0 < rad) {
                    ruterInn[rad][kol].settNord(ruterInn[rad - 1][kol]);
                }
                if (raderInn > rad + 1) {
                    ruterInn[rad][kol].settSor(ruterInn[rad + 1][kol]);

                }
                if (kolonnerInn > kol + 1) {
                    ruterInn[rad][kol].settOst(ruterInn[rad][kol + 1]);

                }
                if (0 < kol) {
                    ruterInn[rad][kol].settVest(ruterInn[rad][kol - 1]);
                }
            }
        }

        return lab;
    }



    private static boolean erAapning(int raderInn, int kolonnerInn, int rad, int kolonne) {
        return (rad + 1 == raderInn || rad == 0 || kolonne + 1 == kolonnerInn || kolonne == 0);
    }

    Lenkeliste<String> finnUtveiFra(int kol, int rad) {
        System.out.println("start = " + kol + " " + rad);
        ruter[rad][kol].finnUtvei();
        return veiListe;
    }

    void leggTilVei(String vei) {
        veiListe.leggTil(vei);
    }

    public int hentKolonner(){
        return this.antallKolonner;
    }

    public int hentRader(){
        return this.antallRader;
    }

    @Override
    public String toString() {

        String s = "";

        for (int rad = 0; rad < antallRader; rad++) {
            for (int kol = 0; kol < antallKolonner; kol++) {
                s = s + ruter[rad][kol].tilTegn();
            }
            s = s + "\n";
        }

        return s;
    }
}
