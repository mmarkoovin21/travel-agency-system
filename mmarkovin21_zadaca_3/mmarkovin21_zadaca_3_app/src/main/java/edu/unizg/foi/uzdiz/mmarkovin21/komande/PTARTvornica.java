package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanCaretaker;

public class PTARTvornica extends KomandaTvornica {
    TuristickaAgencija agencija;
    public PTARTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaPTAR(agencija);
    }
}
