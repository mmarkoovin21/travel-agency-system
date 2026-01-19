package edu.unizg.foi.uzdiz.mmarkovin21.command;


import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class IzvrsiDohvacanje implements Command {
    private Aranzman aranzman;
    private final AranzmanCaretaker caretaker;

    public IzvrsiDohvacanje(AranzmanCaretaker caretaker, Aranzman aranzman) {
        this.caretaker = caretaker;
        this.aranzman = aranzman;
    }

    @Override
    public void izvrsi() {
        int oznaka = aranzman.dohvatiOznaka();

        if (!caretaker.postoji(oznaka)) {
            System.out.println("Nema spremljenog stanja za aranžman s oznakom: " + oznaka);
            return;
        }

        AranzmanMemento memento = caretaker.dohvati(oznaka);
        aranzman.vratiIzMementa(memento);
    }
}
