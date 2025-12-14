package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class AktivnoStanjeRezervacije implements StanjeRezervacije{
    @Override
    public void primi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanjeRezervacije());
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        System.out.println("Rezervacija je već u stanju AKTIVNA!");
    }

    @Override
    public void staviNaCekanje(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new NaCekanjuStanjeRezervacije());
    }

    @Override
    public void otkazi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new OtkazanoStanjeRezervacije());
    }

    @Override
    public void odgodi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new OdgodenoStanjeRezervacije());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "AKTIVNA";
    }
}
