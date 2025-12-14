package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

public class PopunjenStatusAranzmana implements StanjeAranzmana {
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
        System.out.println("Aranžman je već u stanju POPUNJEN");
    }

    @Override
    public void otkazi(Aranzman aranzman) {
        aranzman.promijeniStatus(new OtkazanStatusAranzmana());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "POPUNJEN";
    }
}
