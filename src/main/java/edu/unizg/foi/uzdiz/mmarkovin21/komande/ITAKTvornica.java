package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class ITAKTvornica extends KomandaFactory{
    @Override
    public Komanda kreirajKomandu(TuristickaAgencija agencija) {
        return new KomandaITAK(agencija);
    }
}
