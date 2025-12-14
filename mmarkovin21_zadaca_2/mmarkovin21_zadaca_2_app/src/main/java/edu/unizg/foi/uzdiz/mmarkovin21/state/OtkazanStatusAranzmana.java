package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class OtkazanStatusAranzmana implements StanjeAranzmana {
    @Override
    public void pripremi(Aranzman aranzman) {
        aranzman.promijeniStatus(new UPripremiStatusAranzmana());
    }

    @Override
    public void aktiviraj(Aranzman aranzman) {
        aranzman.promijeniStatus(new AktivanStatusAranzmana());
    }

    @Override
    public void popuni(Aranzman aranzman) {
        aranzman.promijeniStatus(new PopunjenStatusAranzmana());
    }

    @Override
    public void otkazi(Aranzman aranzman) {
        System.out.println("Aranzman je vec u stanju OTKAZAN");
    }

    @Override
    public String dohvatiNazivStanja() {
        return "OTKAZAN";
    }
}
