package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class QTvornica extends KomandaFactory{
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaQ(TuristickaAgencija.dohvatiInstancu());
    }
}
