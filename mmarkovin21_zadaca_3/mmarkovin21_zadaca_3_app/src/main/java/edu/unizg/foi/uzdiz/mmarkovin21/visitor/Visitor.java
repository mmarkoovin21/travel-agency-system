package edu.unizg.foi.uzdiz.mmarkovin21.visitor;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public interface Visitor {
    boolean posjetiAranzman(Aranzman aranzman);
    boolean posjetiRezervaciju(Rezervacija rezervacija);
}
