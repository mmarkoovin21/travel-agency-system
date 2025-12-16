package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.UpraviteljRezervacijaIAranzmana;

public class OTATvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana mediator;

    public OTATvornica(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana mediator) {
        this.agencija = agencija;
        this.mediator = mediator;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaOTA(agencija, mediator);
    }
}
