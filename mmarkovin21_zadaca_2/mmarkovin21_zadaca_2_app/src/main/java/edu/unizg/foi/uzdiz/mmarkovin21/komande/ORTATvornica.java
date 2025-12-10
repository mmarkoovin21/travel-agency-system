package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljStanjaRezervacija;

public class ORTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljStanjaRezervacija upraviteljStanja;

    public ORTATvornica(TuristickaAgencija agencija, UpraviteljStanjaRezervacija upraviteljStanja) {
        this.agencija = agencija;
        this.upraviteljStanja = upraviteljStanja;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaORTA(agencija, upraviteljStanja);
    }
}
