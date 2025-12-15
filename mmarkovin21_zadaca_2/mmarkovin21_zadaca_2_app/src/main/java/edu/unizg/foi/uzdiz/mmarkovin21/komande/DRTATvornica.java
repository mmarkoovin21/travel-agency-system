package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.MediatorRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public class DRTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final MediatorRezervacija mediator;
    private final ValidatorNovihRezervacija validator;

    public DRTATvornica(TuristickaAgencija agencija, MediatorRezervacija mediator,
                        ValidatorNovihRezervacija validator) {
        this.agencija = agencija;
        this.mediator = mediator;
        this.validator = validator;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaDRTA(agencija, mediator, validator);
    }
}
