package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.command.Command;
import edu.unizg.foi.uzdiz.mmarkovin21.command.IzvrsiPostavljanje;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class KomandaPSTAR implements Komanda {
    TuristickaAgencija agencija;
    private AranzmanCaretaker caretaker;

    public KomandaPSTAR (TuristickaAgencija agencija, AranzmanCaretaker caretaker) {;
        this.caretaker = caretaker;
        this.agencija = agencija;
    }
    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length < 2) {
            System.out.println("Pogrešna sintaksa. Koristite: PSTAR oznaka");
            return;
        }

        try {
            int oznaka = Integer.parseInt(parametri[1]);
            Aranzman aranzman = agencija.dohvatiPodatke().stream()
                    .filter(a -> a.dohvatiOznaka() == oznaka)
                    .findFirst()
                    .orElse(null);

            if (aranzman == null) {
                System.out.println("Aranžman s oznakom " + oznaka + " nije pronađen.");
                return;
            }

            Command command = new IzvrsiPostavljanje(aranzman, caretaker);
            command.izvrsi();

        } catch (NumberFormatException e) {
            System.out.println("Oznaka mora biti broj.");
        }
    }
}
