package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class NaCekanjuStanjeRezervacije implements StanjeRezervacije{
    @Override
    public void primi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanjeRezervacije());
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new AktivnoStanjeRezervacije());
    }

    @Override
    public void staviNaCekanje(Rezervacija rezervacija) {
        System.out.println("Rezervacija je već u stanju NA ČEKANJU");
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
        return "NA ČEKANJU";
    }
}
