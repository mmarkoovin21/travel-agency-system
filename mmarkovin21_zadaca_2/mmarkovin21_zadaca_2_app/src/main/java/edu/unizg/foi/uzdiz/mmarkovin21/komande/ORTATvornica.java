package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.MediatorRezervacija;

public class ORTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final MediatorRezervacija mediator;

    public ORTATvornica(TuristickaAgencija agencija, MediatorRezervacija mediator) {
        this.agencija = agencija;
        this.mediator = mediator;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaORTA(agencija, mediator);
    }
}
