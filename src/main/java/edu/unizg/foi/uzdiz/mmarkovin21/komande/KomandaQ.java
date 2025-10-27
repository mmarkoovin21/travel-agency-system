package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public class KomandaQ implements Komanda {
    private TuristickaAgencija agencija;

    public KomandaQ(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }
    @Override
    public void izvrsi(String[] parametri) {
        System.out.println("Izlaz iz programa.");
        System.exit(0);
    }
}