package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;

public class PSTARTvornica extends KomandaTvornica {
    private TuristickaAgencija agencija;
    private AranzmanCaretaker caretaker;

    public PSTARTvornica(TuristickaAgencija agencija, AranzmanCaretaker caretaker) {
        this.agencija = agencija;
        this.caretaker = caretaker;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaPSTAR(agencija, caretaker);
    }
}
