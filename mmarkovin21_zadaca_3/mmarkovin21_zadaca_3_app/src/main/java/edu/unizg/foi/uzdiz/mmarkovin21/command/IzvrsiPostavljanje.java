package edu.unizg.foi.uzdiz.mmarkovin21.command;

import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class IzvrsiPostavljanje implements Command {

    private final Aranzman aranzman;
    private final AranzmanCaretaker caretaker;

    public IzvrsiPostavljanje(Aranzman aranzman, AranzmanCaretaker caretaker) {
        this.aranzman = aranzman;
        this.caretaker = caretaker;
    }
    @Override
    public void izvrsi() {
        AranzmanMemento memento = aranzman.kreirajMemento();
        caretaker.spremi(aranzman.dohvatiOznaka(), memento);
    }
}
