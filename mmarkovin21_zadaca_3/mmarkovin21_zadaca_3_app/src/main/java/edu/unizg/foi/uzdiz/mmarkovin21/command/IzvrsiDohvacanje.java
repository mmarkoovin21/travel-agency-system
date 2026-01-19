package edu.unizg.foi.uzdiz.mmarkovin21.command;


import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class IzvrsiDohvacanje implements Command {
    private final int oznaka;
    private final AranzmanCaretaker caretaker;
    private final TuristickaAgencija agencija;

    public IzvrsiDohvacanje(AranzmanCaretaker caretaker, int oznaka, TuristickaAgencija agencija) {
        this.caretaker = caretaker;
        this.oznaka = oznaka;
        this.agencija = agencija;
    }

    @Override
    public void izvrsi() {
        if (!caretaker.postoji(oznaka)) {
            System.out.println("Nema spremljenog stanja za aranžman s oznakom: " + oznaka);
            return;
        }

        AranzmanMemento memento = caretaker.dohvati(oznaka);

        Aranzman aranzman = agencija.dohvatiPodatke().stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);

        if (aranzman != null) {
            aranzman.vratiIzMementa(memento);
            System.out.println("Vraćeno stanje aranžmana s oznakom: " + oznaka);
        } else {
            Aranzman noviAranzman = Aranzman.izMementa(memento);
            agencija.dodajPodatak(noviAranzman);
            System.out.println("Aranžman s oznakom " + oznaka + " kreiran iz spremljenog stanja i dodan u listu.");
        }
    }
}
