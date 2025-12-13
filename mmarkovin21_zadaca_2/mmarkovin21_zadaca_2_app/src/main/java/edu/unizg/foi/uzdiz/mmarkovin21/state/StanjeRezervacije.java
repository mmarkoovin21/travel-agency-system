package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public interface StanjeRezervacije {
    void primi(Rezervacija rezervacija);
    void aktiviraj(Rezervacija rezervacija);
    void staviNaCekanje(Rezervacija rezervacija);
    void otkazi(Rezervacija rezervacija);
    void odgodi(Rezervacija rezervacija);
    String dohvatiNazivStanja();
}
