package edu.unizg.foi.uzdiz.mmarkovin21.strategy;

import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class ViseDozvoljenihRezervacija extends UpraviteljRezervacijaIAranzmana {
    @Override
    public boolean dodajRezervaciju(Rezervacija novaRez) {
        return false;
    }

    @Override
    public boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        return false;
    }

    @Override
    public void azurirajStanjeAranzmana(Aranzman aranzman) {

    }

    @Override
    public void azurirajStanjaRezervacija(Aranzman aranzman) {

    }

    @Override
    protected void pokusajAktiviratiOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez) {

    }
}
