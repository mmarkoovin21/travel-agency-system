package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class OtkazanoStanjeRezervacije implements StanjeRezervacije{
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
        rezervacija.promijeniStanje(new NaCekanjuStanjeRezervacije());
    }

    @Override
    public void otkazi(Rezervacija rezervacija) {
    }

    @Override
    public void odgodi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new OdgodenoStanjeRezervacije());
    }

    @Override
    public String dohvatiNazivStanja() {
        return "OTKAZANA";
    }
}
