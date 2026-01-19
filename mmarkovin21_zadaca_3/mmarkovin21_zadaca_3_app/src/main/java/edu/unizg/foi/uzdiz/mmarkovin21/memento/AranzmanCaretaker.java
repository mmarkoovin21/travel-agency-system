package edu.unizg.foi.uzdiz.mmarkovin21.memento;

import java.util.HashMap;
import java.util.Map;

public class AranzmanCaretaker {
    private Map<Integer, AranzmanMemento> spremljeniAranzmani = new HashMap<>();

    public void spremi(int oznaka, AranzmanMemento memento) {
        spremljeniAranzmani.put(oznaka, memento);
        System.out.println("Spremljen aranžman s oznakom: " + oznaka);
    }

    public AranzmanMemento dohvati(int oznaka) {
        return spremljeniAranzmani.get(oznaka);
    }

    public boolean postoji(int oznaka) {
        return spremljeniAranzmani.containsKey(oznaka);
    }
}
