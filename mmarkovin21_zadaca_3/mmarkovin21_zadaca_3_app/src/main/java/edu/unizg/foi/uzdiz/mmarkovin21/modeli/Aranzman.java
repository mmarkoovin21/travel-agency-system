package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.AranzmanMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.memento.RezervacijaMemento;
import edu.unizg.foi.uzdiz.mmarkovin21.state.*;
import edu.unizg.foi.uzdiz.mmarkovin21.visitor.Visitor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aranzman extends TuristickaKomponenta {
    private int oznaka;
    private String naziv;
    private String program;
    private LocalDate pocetniDatum;
    private LocalDate zavrsniDatum;
    private int minBrojPutnika;
    private int maxBrojPutnika;
    private Long cijenaPoOsobi;

    private LocalTime vrijemeKretanja;
    private LocalTime vrijemePovratka;
    private int brojNocenja;
    private Long doplataZaJednokrevetnuSobu;
    private List<String> prijevoz;
    private int brojDorucaka;
    private int brojRucakova;
    private int brojVecera;
    private StanjeAranzmana status;


    private final List<TuristickaKomponenta> djeca = new ArrayList<>();

    public Aranzman() {
    }
    public Aranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDate pocetniDatum,
            LocalDate zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            String status
    ) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.program = program;
        this.pocetniDatum = pocetniDatum;
        this.zavrsniDatum = zavrsniDatum;
        this.minBrojPutnika = minBrojPutnika;
        this.maxBrojPutnika = maxBrojPutnika;
        this.cijenaPoOsobi = cijenaPoOsobi;
        this.status = odrediPocetniStatusPremaNazivu(status);
    }

    public int dohvatiOznaka() {
        return oznaka;
    }

    public String dohvatiNaziv() {
        return naziv;
    }

    public String dohvatiProgram() {
        return program;
    }

    public LocalDate dohvatiPocetniDatum() {
        return pocetniDatum;
    }

    public LocalDate dohvatiZavrsniDatum() {
        return zavrsniDatum;
    }

    public int dohvatiMinBrojPutnika() {
        return minBrojPutnika;
    }

    public int dohvatiMaxBrojPutnika() {
        return maxBrojPutnika;
    }

    public Long dohvatiCijenaPoOsobi() {
        return cijenaPoOsobi;
    }

    public String dohvatiStatusString(){ return status.dohvatiNazivStanja(); }

    public StanjeAranzmana dohvatiStatus() {
        return status;
    }

    public void promijeniStatus(StanjeAranzmana novoStanje) {
        this.status = novoStanje;
    }
    
    public LocalTime dohvatiVrijemeKretanja() {
        return vrijemeKretanja;
    }
    
    public void postaviVrijemeKretanja(LocalTime vrijemeKretanja) {
        this.vrijemeKretanja = vrijemeKretanja;
    }

    public LocalTime dohvatiVrijemePovratka() {
        return vrijemePovratka;
    }
    
    public void postaviVrijemePovratka(LocalTime vrijemePovratka) {
        this.vrijemePovratka = vrijemePovratka;
    }

    public int dohvatiBrojNocenja() {
        return brojNocenja;
    }
    
    public void postaviBrojNocenja(int brojNocenja) {
        this.brojNocenja = brojNocenja;
    }

    public Long dohvatiDoplataZaJednokrevetnuSobu() {
        return doplataZaJednokrevetnuSobu;
    }
    
    public void postaviDoplataZaJednokrevetnuSobu(Long doplataZaJednokrevetnuSobu) {
        this.doplataZaJednokrevetnuSobu = doplataZaJednokrevetnuSobu;
    }

    public List<String> dohvatiPrijevoz() {
        return prijevoz != null ? prijevoz : Collections.emptyList();
    }
    
    public void postaviPrijevoz(List<String> prijevoz) {
        this.prijevoz = prijevoz;
    }

    public int dohvatiBrojDorucaka() {
        return brojDorucaka;
    }
    
    public void postaviBrojDorucaka(int brojDorucaka) {
        this.brojDorucaka = brojDorucaka;
    }

    public int dohvatiBrojRuckova() {
        return brojRucakova;
    }
    
    public void postaviBrojRucakova(int brojRucakova) {
        this.brojRucakova = brojRucakova;
    }

    public int dohvatiBrojVecera() {
        return brojVecera;
    }
    
    public void postaviBrojVecera(int brojVecera) {
        this.brojVecera = brojVecera;
    }

    @Override
    public double izracunajUkupnuCijenu() {
        double ukupno = 0;

        for (TuristickaKomponenta dijete : djeca) {
            ukupno += dijete.izracunajUkupnuCijenu();
        }
        return ukupno;
    }

    @Override
    public void dodajDijete(TuristickaKomponenta koponenta) {
        djeca.add(koponenta);

        if (koponenta instanceof Rezervacija) {
            ((Rezervacija) koponenta).postaviAranzman(this);
        }
    }
    @Override
    public void ukloniDijete(TuristickaKomponenta koponenta) {
        djeca.remove(koponenta);
    }

    public List<TuristickaKomponenta> dohvatiDjecu() {
        return djeca;
    }

    private StanjeAranzmana odrediPocetniStatusPremaNazivu(String nazivStatusa) {
        switch (nazivStatusa.toUpperCase()) {
            case "U PRIPREMI":
                return new UPripremiStatusAranzmana();
            case "AKTIVAN":
                return new AktivanStatusAranzmana();
            case "POPUNJEN":
                return new PopunjenStatusAranzmana();
            case "OTKAZAN":
                return new OtkazanStatusAranzmana();
            default:
                throw new IllegalArgumentException("Nepoznat naziv statusa aranžmana: " + nazivStatusa);
        }
    }

    public void pripremi() {
        status.pripremi(this);
    }

    public void aktiviraj() {
        status.aktiviraj(this);
    }

    public void popuni() {
        status.popuni(this);
    }

    public void otkazi() {
        status.otkazi(this);
    }

    public boolean prihvatiVisitora(Visitor visitor) {
        return visitor.posjetiAranzman(this);
    }

    public AranzmanMemento kreirajMemento() {
        return new AranzmanMemento(this);
    }

    public void vratiIzMementa(AranzmanMemento memento) {
        this.oznaka = memento.dohvatiOznaka();
        this.naziv = memento.dohvatiNaziv();
        this.program = memento.dohvatiProgram();
        this.pocetniDatum = memento.dohvatiPocetniDatum();
        this.zavrsniDatum = memento.dohvatiZavrsniDatum();
        this.minBrojPutnika = memento.dohvatiMinBrojPutnika();
        this.maxBrojPutnika = memento.dohvatiMaxBrojPutnika();
        this.cijenaPoOsobi = memento.dohvatiCijenaPoOsobi();
        this.vrijemeKretanja = memento.dohvatiVrijemeKretanja();
        this.vrijemePovratka = memento.dohvatiVrijemePovratka();
        this.brojNocenja = memento.dohvatiBrojNocenja();
        this.doplataZaJednokrevetnuSobu = memento.dohvatiDoplataZaJednokrevetnuSobu();
        this.prijevoz = new ArrayList<>(memento.dohvatiPrijevoz());
        this.brojDorucaka = memento.dohvatiBrojDorucaka();
        this.brojRucakova = memento.dohvatiBrojRuckova();
        this.brojVecera = memento.dohvatiBrojVecera();
        this.status = odrediPocetniStatusPremaNazivu(memento.dohvatiStatusString());

        this.djeca.clear();
        for (RezervacijaMemento rezMemento : memento.dohvatiRezervacije()) {
            Rezervacija rez = Rezervacija.izMementa(rezMemento);
            this.dodajDijete(rez);
        }
    }
}
