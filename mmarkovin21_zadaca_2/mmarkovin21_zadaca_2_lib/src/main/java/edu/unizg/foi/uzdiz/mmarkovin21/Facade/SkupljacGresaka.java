package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.util.ArrayList;
import java.util.List;

public class SkupljacGresaka {
    private int redniBrojGreske = 0;
    private final List<Greska> greske = new ArrayList<>();

    public SkupljacGresaka() {
    }

    public void dodajGresku(int brojRetka, String opisGreske) {
        redniBrojGreske++;
        greske.add(new Greska(redniBrojGreske, brojRetka, opisGreske));
    }

    public void ispisiGreske() {
        if (greske.isEmpty()) {
            return;
        }

        for (Greska greska : greske) {
            greska.ispisi();
        }
    }

}
