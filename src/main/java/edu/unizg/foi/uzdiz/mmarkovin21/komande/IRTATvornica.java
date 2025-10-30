package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class IRTATvornica extends KomandaTvornica{
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaIRTA(TuristickaAgencija.dohvatiInstancu());
    }
}
