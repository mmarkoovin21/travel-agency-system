package edu.unizg.foi.uzdiz.mmarkovin21.visitor;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;


public class PretrazivacVisitor implements Visitor {
    private String rijec;

    public PretrazivacVisitor(String rijec) {
        this.rijec = rijec;
    }
    @Override
    public boolean posjetiAranzman(Aranzman aranzman) {
        return aranzman.dohvatiNaziv().contains(rijec) ||
                aranzman.dohvatiProgram().contains(rijec);
    }

    @Override
    public boolean posjetiRezervaciju(Rezervacija rezervacija) {
        return rezervacija.dohvatiIme().contains(rijec) ||
                rezervacija.dohvatiPrezime().contains(rijec);
    }
}
