package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aranzman extends TuristickaKomponenta {
    // nužni
    private int oznaka;
    private String naziv;
    private String program;
    private LocalDate pocetniDatum;
    private LocalDate zavrsniDatum;
    private int minBrojPutnika;
    private int maxBrojPutnika;
    private Long cijenaPoOsobi;

    // opcionalni
    private LocalTime vrijemeKretanja;
    private LocalTime vrijemePovratka;
    private int brojNocenja;
    private Long doplataZaJednokrevetnuSobu;
    private List<String> prijevoz;
    private int brojDorucaka;
    private int brojRucakova;
    private int brojVecera;

    private List<TuristickaKomponenta> djeca = new ArrayList<>();

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
            Long cijenaPoOsobi
    ) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.program = program;
        this.pocetniDatum = pocetniDatum;
        this.zavrsniDatum = zavrsniDatum;
        this.minBrojPutnika = minBrojPutnika;
        this.maxBrojPutnika = maxBrojPutnika;
        this.cijenaPoOsobi = cijenaPoOsobi;
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

    public void postaviMinBrojPutnika(int minBrojPutnika) {
        this.minBrojPutnika = minBrojPutnika;
    }

    public int dohvatiMaxBrojPutnika() {
        return maxBrojPutnika;
    }

    public void postaviMaxBrojPutnika(int maxBrojPutnika) {
        this.maxBrojPutnika = maxBrojPutnika;
    }

    public Long dohvatiCijenaPoOsobi() {
        return cijenaPoOsobi;
    }
    
    // neobavezni getteri i setteri
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

    // composite metode
    @Override
    public void ispisi(int razina) {
        // ovdje treba neki formater tablice
        String uvlaka = "  ".repeat(razina);
        System.out.println(uvlaka + "Aranžman: " + naziv +
                " (oznaka: " + oznaka + ", cijena: " + cijenaPoOsobi + ")");

        // Rekurzivno ispisuj djecu, ne znam bas oce li mi ovo trebati
//        for (TuristickaKomponenta dijete : djeca) {
//            dijete.ispisi(razina + 1);
//        }
    }

    @Override
    public double izracunajUkupnuCijenu() {
        // Osnovna cijena aranžmana + zbroj cijena sve djece
        double ukupno = 0;

        for (TuristickaKomponenta dijete : djeca) {
            ukupno += dijete.izracunajUkupnuCijenu();
        }
        return ukupno;
    }

    @Override
    public void dodajDijete(TuristickaKomponenta koponenta) {
        djeca.add(koponenta);

        // Ako je dijete Rezervacija, postavi referencu
        if (koponenta instanceof Rezervacija) {
            ((Rezervacija) koponenta).postaviAranzman(this);
        }
    }
    @Override
    public void ukloniDijete(TuristickaKomponenta koponenta) {
        djeca.remove(koponenta);
    }
}
