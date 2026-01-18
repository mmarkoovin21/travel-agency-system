package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljRezervacijaIAranzmana;

public class BPTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana upravitelj;

    public BPTvornica(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana upravitelj) {
        this.agencija = agencija;
        this.upravitelj = upravitelj;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaBP(agencija, upravitelj);
    }
}
