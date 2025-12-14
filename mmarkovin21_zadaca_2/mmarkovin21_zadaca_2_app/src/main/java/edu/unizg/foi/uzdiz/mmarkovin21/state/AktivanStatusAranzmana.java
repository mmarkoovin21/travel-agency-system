package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class AktivanStatusAranzmana implements StanjeAranzmana {

    @Override
    public void pripremi(Aranzman aranzman) {
        aranzman.promijeniStatus(new UPripremiStatusAranzmana());
    }

    @Override
    public void aktiviraj(Aranzman aranzman) {
    }

    @Override
    public void popuni(Aranzman aranzman) {
        aranzman.promijeniStatus(new PopunjenStatusAranzmana());
    }

    @Override
    public void otkazi(Aranzman aranzman) {
        aranzman.promijeniStatus(new OtkazanStatusAranzmana());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "AKTIVAN";
    }
}
