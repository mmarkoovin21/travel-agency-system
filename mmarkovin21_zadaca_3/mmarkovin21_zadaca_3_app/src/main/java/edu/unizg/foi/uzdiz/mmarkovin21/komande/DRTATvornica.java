package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public class DRTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana mediator;
    private final ValidatorNovihRezervacija validator;

    public DRTATvornica(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana mediator,
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
