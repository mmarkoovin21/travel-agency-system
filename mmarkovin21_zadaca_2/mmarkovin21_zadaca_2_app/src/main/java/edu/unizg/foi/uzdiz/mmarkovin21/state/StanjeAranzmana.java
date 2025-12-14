package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public interface StanjeAranzmana {
    void pripremi(Aranzman aranzman);
    void aktiviraj(Aranzman aranzman);
    void popuni(Aranzman aranzman);
    void otkazi(Aranzman aranzman);
    String dohvatiNazivStanja();
}
