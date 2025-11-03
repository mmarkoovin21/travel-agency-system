package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class ITAKTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public ITAKTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaITAK(agencija);
    }
}
