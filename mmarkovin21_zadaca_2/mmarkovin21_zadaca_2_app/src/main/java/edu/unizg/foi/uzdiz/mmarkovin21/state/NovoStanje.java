package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

public class NovoStanje implements StanjeRezervacije{
    public void primi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new AktivnoStanje());
    }

    @Override
    public void staviNaCekanje(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new NaCekanjuStanje());
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
        return "NOVA";
    }
}
