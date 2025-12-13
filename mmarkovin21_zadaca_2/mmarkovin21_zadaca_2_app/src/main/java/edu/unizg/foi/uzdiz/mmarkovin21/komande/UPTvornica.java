package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class UPTvornica extends KomandaTvornica{
    private final TuristickaAgencija agencija;

    public UPTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaUP(agencija);
    }
}
