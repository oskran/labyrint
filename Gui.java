import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.event.*;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Gui extends Application {
    Knapp brett[];
    Labyrint l;
    int rader;
    int kolonner;
    boolean[][] utveiMatrise;
    GridPane rutenett;

    class Knapp extends Button {
        char merke = ' ';
        int rad;
        int kolonne;

        Knapp() {
            super(" ");
            setFont(new Font(10));
            setPrefSize(25, 25);
        }

        void settMerke(char c) {
            setText("" + c);
            merke = c;
        }

        void settKoordinat(int rad, int kolonne) {
            this.rad = rad;
            this.kolonne = kolonne;
        }
    }

    @Override
    public void start(Stage teater) {

        File fil = new FileChooser().showOpenDialog(teater);

        try {
            l = Labyrint.lesFraFil(fil);
        } catch (Exception e) {
        }

        rutenett = new GridPane();
        rutenett.setGridLinesVisible(true);

        this.rader = l.antallRader;
        this.kolonner = l.antallKolonner;

        brett = new Knapp[kolonner * rader];
        Klikkbehandler klikk = new Klikkbehandler();

        for (int i = 0; i < kolonner * rader; i++) {

            int rad = i / kolonner;
            int kolonne = i % kolonner;

            brett[i] = new Knapp();
            brett[i].setOnAction(klikk);
            brett[i].setText(String.valueOf(l.ruter[rad][kolonne].tilTegn()));
            brett[i].settKoordinat(rad, kolonne);

            rutenett.add(brett[i], rad, kolonne);

        }

        Pane kulisser = new Pane();
        kulisser.getChildren().add(rutenett);

        Scene scene = new Scene(kulisser);

        teater.setTitle("Labyrint");
        teater.setScene(scene);
        teater.show();
    }

    class Klikkbehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            finnUtvei((Knapp) e.getSource());
        }
    }

    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s", ""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }

    void finnUtvei(Knapp r) {
        try {
            int startKol = r.kolonne;
            int startRad = r.rad;

            /// Finn utveier som starter på ruten som ble klikket på
            Liste<String> utveier = l.finnUtveiFra(startKol, startRad);

            // Hent ut den forste utveien.
            String losning = utveier.hent(0);

            if (utveier.stoerrelse() != 0) {

                utveiMatrise = losningStringTilTabell(losning, kolonner, rader);

                for (int i = 0; i < kolonner * rader; i++) {

                    int rad = i / kolonner;
                    int kolonne = i % kolonner;

                    if (utveiMatrise[rad][kolonne]) {
                        brett[i].setText("O");
                    }
                }
            }

            else {
                System.out.println("Ingen utveier.");
            }
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println("Ugyldig input!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}