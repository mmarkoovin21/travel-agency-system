package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class IRTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public IRTATvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaIRTA(agencija);
    }
}
