package edu.unizg.foi.uzdiz.mmarkovin21.memento;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AranzmanMemento {
    private final int oznaka;
    private final String naziv;
    private final String program;
    private final LocalDate pocetniDatum;
    private final LocalDate zavrsniDatum;
    private final int minBrojPutnika;
    private final int maxBrojPutnika;
    private final Long cijenaPoOsobi;

    private final LocalTime vrijemeKretanja;
    private final LocalTime vrijemePovratka;
    private final int brojNocenja;
    private final Long doplataZaJednokrevetnuSobu;
    private final List<String> prijevoz;
    private final int brojDorucaka;
    private final int brojRucakova;
    private final int brojVecera;
    private final String status;

    private final List<RezervacijaMemento> djeca = new ArrayList<>();

    public AranzmanMemento(Aranzman aranzman) {
        this.oznaka = aranzman.dohvatiOznaka();
        this.naziv = aranzman.dohvatiNaziv();
        this.program = aranzman.dohvatiProgram();
        this.pocetniDatum = aranzman.dohvatiPocetniDatum();
        this.zavrsniDatum = aranzman.dohvatiZavrsniDatum();
        this.minBrojPutnika = aranzman.dohvatiMinBrojPutnika();
        this.maxBrojPutnika = aranzman.dohvatiMaxBrojPutnika();
        this.cijenaPoOsobi = aranzman.dohvatiCijenaPoOsobi();
        this.vrijemeKretanja = aranzman.dohvatiVrijemeKretanja();
        this.vrijemePovratka = aranzman.dohvatiVrijemePovratka();
        this.brojNocenja = aranzman.dohvatiBrojNocenja();
        this.doplataZaJednokrevetnuSobu = aranzman.dohvatiDoplataZaJednokrevetnuSobu();
        this.prijevoz = aranzman.dohvatiPrijevoz();
        this.brojDorucaka = aranzman.dohvatiBrojDorucaka();
        this.brojRucakova = aranzman.dohvatiBrojRuckova();
        this.brojVecera = aranzman.dohvatiBrojVecera();
        this.status = aranzman.dohvatiStatusString();

        for (var dijete : aranzman.dohvatiDjecu()) {
            if (dijete instanceof edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija) {
                this.djeca.add(new RezervacijaMemento((edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija) dijete));
            }
        }
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
    public LocalTime dohvatiVrijemeKretanja() {
        return vrijemeKretanja;
    }
    public LocalTime dohvatiVrijemePovratka() {
        return vrijemePovratka;
    }
    public int dohvatiBrojNocenja() {
        return brojNocenja;
    }
    public Long dohvatiDoplataZaJednokrevetnuSobu() {
        return doplataZaJednokrevetnuSobu;
    }
    public List<String> dohvatiPrijevoz() {
        return prijevoz;
    }
    public int dohvatiBrojDorucaka() {
        return brojDorucaka;
    }
    public int dohvatiBrojRuckova() {
        return brojRucakova;
    }
    public int dohvatiBrojVecera() {
        return brojVecera;
    }
    public String dohvatiStatusString() {
        return status;
    }
    public List<RezervacijaMemento> dohvatiRezervacije(){
        return djeca;
    }
}
