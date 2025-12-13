package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class NaCekanjuStanje implements StanjeRezervacije{
    @Override
    public void primi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new AktivnoStanje());
    }

    @Override
    public void staviNaCekanje(Rezervacija rezervacija) {
        System.out.println("Rezervacija je već u stanju NA ČEKANJU");
    }

    @Override
    public void otkazi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new OtkazanoStanje());
    }

    @Override
    public void odgodi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new OdgodenoStanje());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "NA ČEKANJU";
    }
}
