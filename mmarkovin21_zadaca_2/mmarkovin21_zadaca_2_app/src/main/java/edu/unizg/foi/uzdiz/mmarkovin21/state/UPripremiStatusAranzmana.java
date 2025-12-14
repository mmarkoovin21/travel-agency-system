package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class UPripremiStatusAranzmana implements StanjeAranzmana {

    @Override
    public void pripremi(Aranzman aranzman) {
        System.out.println("Aranžman je već u stanju U PRIPREMI");
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
        aranzman.promijeniStatus(new OtkazanStatusAranzmana());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "U PRIPREMI";
    }
}
