package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.RezervacijaMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.state.*;
import edu.unizg.foi.uzdiz.mmarkovin21.visitor.Visitor;

import java.time.LocalDateTime;

public class Rezervacija extends TuristickaKomponenta{
    private String ime;
    private String prezime;
    private int oznakaAranzmana;
    private LocalDateTime datumVrijemePrijema;
    private StanjeRezervacije stanje;
    private LocalDateTime datumVrijemeOtkazivanja;

    private Aranzman aranzman;

    public Rezervacija(String ime, String prezime, int oznakaAranzmana,
                       LocalDateTime datumVrijemePrijema, String vrsta) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaAranzmana = oznakaAranzmana;
        this.datumVrijemePrijema = datumVrijemePrijema;
        this.stanje = odredPOcetnoStanjePremaNazivu(vrsta);
    }

    public String dohvatiIme() {
        return ime;
    }

    public String dohvatiPrezime() {
        return prezime;
    }

    public LocalDateTime dohvatiDatumVrijemePrijema() {
        return datumVrijemePrijema;
    }

    public int dohvatiOznakaAranzmana() {
        return oznakaAranzmana;
    }

    public String dohvatiStanjeString() {
        return stanje.dohvatiNazivStanja();
    }

    public void promijeniStanje(StanjeRezervacije novoStanje) {
        this.stanje = novoStanje;
    }

    public LocalDateTime dohvatiDatumVrijemeOtkazivanja() {
        return datumVrijemeOtkazivanja;
    }

    @Override
    public double izracunajUkupnuCijenu() {
        
        if (aranzman != null) {
            return aranzman.dohvatiCijenaPoOsobi();
        }
        return 0;
    }

    public void postaviAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }

    public Aranzman dohvatiAranzman() {
        return aranzman;
    }

    private StanjeRezervacije odredPOcetnoStanjePremaNazivu(String naziv) {
        switch (naziv.toLowerCase()) {
            case "primljena": return new PrimljenoStanjeRezervacije();
            case "aktivna": return new AktivnoStanjeRezervacije();
            case "na čekanju": return new NaCekanjuStanjeRezervacije();
            case "otkazana": return new OtkazanoStanjeRezervacije();
            default: return new NovoStanjeRezervacije();
        }
    }

    public void primi() {
        stanje.primi(this);
    }

    public void aktiviraj() {
        stanje.aktiviraj(this);
    }

    public void staviNaCekanje() {
        stanje.staviNaCekanje(this);
    }

    public void otkazi() {
        stanje.otkazi(this);
        this.datumVrijemeOtkazivanja = LocalDateTime.now();
        if (aranzman != null) {
            aranzman.obavijestiObservere("Rezervacija otkazana za: " + dohvatiInicijale());
        }
    }

    private String dohvatiInicijale() {
        String inicijalImena = ime != null && !ime.isEmpty() ? ime.substring(0, 1).toUpperCase() + "." : "";
        String inicijalPrezimena = prezime != null && !prezime.isEmpty() ? prezime.substring(0, 1).toUpperCase() + "." : "";
        return inicijalImena + " " + inicijalPrezimena;
    }

    public void odgodi() {
        stanje.odgodi(this);
    }

    public boolean prihvatiVisitora(Visitor visitor) {
        return visitor.posjetiRezervaciju(this);
    }

    public static Rezervacija izMementa(RezervacijaMemento memento) {
        return new Rezervacija(
                memento.dohvatiIme(),
                memento.dohvatiPrezime(),
                memento.dohvatiOznakaAranzmana(),
                memento.dohvatiDatumVrijemePrijema(),
                memento.dohvatiStanjeString()
        );
    }
}
