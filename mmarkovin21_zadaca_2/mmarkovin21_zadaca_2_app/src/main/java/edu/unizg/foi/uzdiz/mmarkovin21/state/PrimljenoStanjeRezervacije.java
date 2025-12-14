package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class PrimljenoStanjeRezervacije implements StanjeRezervacije{
    @Override
    public void primi(Rezervacija rezervacija) {
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new AktivnoStanjeRezervacije());
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
        return "PRIMLJENA";
    }
}
