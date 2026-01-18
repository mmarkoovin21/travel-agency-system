package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class PPTARTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public PPTARTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaPPTAR(agencija);
    }
}
