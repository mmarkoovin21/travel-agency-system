package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class IROTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public IROTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaIRO(agencija);
    }
}
