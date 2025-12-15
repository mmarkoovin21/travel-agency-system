package edu.unizg.foi.uzdiz.mmarkovin21.mediator;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public interface UpraviteljMediator {
    boolean dodajRezervaciju(Rezervacija novaRez);
    boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana);
    void azurirajStanjeAranzmana(Aranzman aranzman);
    void azurirajStanjaRezervacija(Aranzman aranzman);
}
