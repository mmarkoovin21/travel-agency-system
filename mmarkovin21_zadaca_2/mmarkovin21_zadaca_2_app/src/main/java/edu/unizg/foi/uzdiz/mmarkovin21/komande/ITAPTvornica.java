package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class ITAPTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public ITAPTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaITAP(agencija);
    }
}
