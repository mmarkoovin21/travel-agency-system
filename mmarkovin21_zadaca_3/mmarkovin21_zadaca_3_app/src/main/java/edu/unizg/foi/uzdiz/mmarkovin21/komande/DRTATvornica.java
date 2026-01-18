package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public class DRTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana upravitelj;
    private final ValidatorNovihRezervacija validator;

    public DRTATvornica(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana upravitelj,
                        ValidatorNovihRezervacija validator) {
        this.agencija = agencija;
        this.upravitelj = upravitelj;
        this.validator = validator;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaDRTA(agencija, upravitelj, validator);
    }
}
