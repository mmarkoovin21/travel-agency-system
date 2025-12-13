package edu.unizg.foi.uzdiz.mmarkovin21.state;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import javax.print.attribute.standard.PrinterLocation;

public class OdgodenoStanje implements StanjeRezervacije{
    @Override
    public void primi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void aktiviraj(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void staviNaCekanje(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void otkazi(Rezervacija rezervacija) {
        rezervacija.promijeniStanje(new PrimljenoStanje());
    }

    @Override
    public void odgodi(Rezervacija rezervacija) {
        System.out.println("Rezervacija je već u stanju ODGOĐENA");
    }

    @Override
    public String dohvatiNazivStanja() {
        return "ODGOĐENA";
    }
}
