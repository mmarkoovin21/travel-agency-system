package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class ITAKTvornica extends KomandaTvornica {
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaITAK();
    }
}
