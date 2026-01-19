package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;

public class VSTARTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final AranzmanCaretaker caretaker;
    public VSTARTvornica(TuristickaAgencija agencija, AranzmanCaretaker caretaker) {
        this.agencija = agencija;
        this.caretaker = caretaker;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaVSTAR(agencija, caretaker);
    }
}
