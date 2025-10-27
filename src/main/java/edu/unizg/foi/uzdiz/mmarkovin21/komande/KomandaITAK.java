package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class KomandaITAK implements Komanda {
    private TuristickaAgencija agencija;

    public KomandaITAK(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        System.out.println("Lista aranžmana:");
        // TODO
    }
}
