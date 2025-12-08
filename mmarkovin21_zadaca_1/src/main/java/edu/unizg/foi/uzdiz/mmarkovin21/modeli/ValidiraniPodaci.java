package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ValidiraniPodaci {
    // Obavezni
    private final int oznaka;
    private final String naziv;
    private final String program;
    private final LocalDate pocetniDatum;
    private final LocalDate zavrsniDatum;
    private final int minBrojPutnika;
    private final int maxBrojPutnika;
    private final Long cijenaPoOsobi;

    // Opcionalni
    private final LocalTime vrijemeKretanja;
    private final LocalTime vrijemePovratka;
    private final int brojNocenja;
    private final Long doplataZaJednokrevetnuSobu;
    private final List<String> prijevoz;
    private final int brojDorucaka;
    private final int brojRucakova;
    private final int brojVecera;

    public ValidiraniPodaci(
            int oznaka,
            String naziv,
            String program,
            LocalDate pocetniDatum,
            LocalDate zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            LocalTime vrijemeKretanja,
            LocalTime vrijemePovratka,
            int brojNocenja,
            Long doplataZaJednokrevetnuSobu,
            List<String> prijevoz,
            int brojDorucaka,
            int brojRucakova,
            int brojVecera
    ) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.program = program;
        this.pocetniDatum = pocetniDatum;
        this.zavrsniDatum = zavrsniDatum;
        this.minBrojPutnika = minBrojPutnika;
        this.maxBrojPutnika = maxBrojPutnika;
        this.cijenaPoOsobi = cijenaPoOsobi;
        this.vrijemeKretanja = vrijemeKretanja;
        this.vrijemePovratka = vrijemePovratka;
        this.brojNocenja = brojNocenja;
        this.doplataZaJednokrevetnuSobu = doplataZaJednokrevetnuSobu;
        this.prijevoz = prijevoz;
        this.brojDorucaka = brojDorucaka;
        this.brojRucakova = brojRucakova;
        this.brojVecera = brojVecera;
    }

    // Getteri
    public int dohvatiOznaka() { return oznaka; }
    public String dohvatiNaziv() { return naziv; }
    public String dohvatiProgram() { return program; }
    public LocalDate dohvatiPocetniDatum() { return pocetniDatum; }
    public LocalDate dohvatiZavrsniDatum() { return zavrsniDatum; }
    public int dohvatiMinBrojPutnika() { return minBrojPutnika; }
    public int dohvatiMaxBrojPutnika() { return maxBrojPutnika; }
    public Long dohvatiCijenaPoOsobi() { return cijenaPoOsobi; }
    public LocalTime dohvatiVrijemeKretanja() { return vrijemeKretanja; }
    public LocalTime dohvatiVrijemePovratka() { return vrijemePovratka; }
    public int dohvatiBrojNocenja() { return brojNocenja; }
    public Long dohvatiDoplataZaJednokrevetnuSobu() { return doplataZaJednokrevetnuSobu; }
    public List<String> dohvatiPrijevoz() { return prijevoz; }
    public int dohvatiBrojDorucaka() { return brojDorucaka; }
    public int dohvatiBrojRucakova() { return brojRucakova; }
    public int dohvatiBrojVecera() { return brojVecera; }
}
