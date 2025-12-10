package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljStanjaRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public class DRTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljStanjaRezervacija upraviteljStanja;
    private final ValidatorNovihRezervacija validator;

    public DRTATvornica(TuristickaAgencija agencija, UpraviteljStanjaRezervacija upraviteljStanja,
                        ValidatorNovihRezervacija validator) {
        this.agencija = agencija;
        this.upraviteljStanja = upraviteljStanja;
        this.validator = validator;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaDRTA(agencija, upraviteljStanja, validator);
    }
}
