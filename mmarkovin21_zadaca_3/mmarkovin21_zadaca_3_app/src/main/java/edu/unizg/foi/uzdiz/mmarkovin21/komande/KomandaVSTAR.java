package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.command.Command;
import edu.unizg.foi.uzdiz.mmarkovin21.command.IzvrsiDohvacanje;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class KomandaVSTAR implements  Komanda {
    private final TuristickaAgencija agencija;
    private final AranzmanCaretaker caretaker;

    public KomandaVSTAR(TuristickaAgencija agencija, AranzmanCaretaker caretaker) {
        this.caretaker = caretaker;
        this.agencija = agencija;
    }
    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length < 2) {
            System.out.println("Pogrešna sintaksa. Koristite: VSTAR oznaka");
            return;
        }

        try {
            int oznaka = Integer.parseInt(parametri[1]);

            Command command = new IzvrsiDohvacanje(caretaker, oznaka, agencija);
            command.izvrsi();

        } catch (NumberFormatException e) {
            System.out.println("Oznaka mora biti broj.");
        }
    }
}
