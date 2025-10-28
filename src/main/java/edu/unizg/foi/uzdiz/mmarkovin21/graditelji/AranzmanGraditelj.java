package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

import java.time.LocalDateTime;
import java.util.List;

public class AranzmanGraditelj {
    // nužni
    private final int oznaka;
    private final String naziv;
    private final String program;
    private final LocalDateTime pocetniDatum;
    private final LocalDateTime zavrsniDatum;
    private final int minBrojPutnika;
    private final int maxBrojPutnika;
    private final Long cijenaPoOsobi;

    // opcionalni
    private LocalDateTime vrijemeKretanja;
    private LocalDateTime vrijemePovratka;
    private int brojNocenja;
    private Long doplataZaJednokrevetnuSobu;
    private List<String> prijevoz;
    private int brojDorucaka;
    private int brojRucakova;
    private int brojVecera;

    public AranzmanGraditelj(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi)
    {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.program = program;
        this.pocetniDatum = pocetniDatum;
        this.zavrsniDatum = zavrsniDatum;
        this.minBrojPutnika = minBrojPutnika;
        this.maxBrojPutnika = maxBrojPutnika;
        this.cijenaPoOsobi = cijenaPoOsobi;
    }

    public AranzmanGraditelj postaviVrijemeKretanja(LocalDateTime vrijemeKretanja) {
        this.vrijemeKretanja = vrijemeKretanja;
        return this;
    }

    public AranzmanGraditelj postaviVrijemePovratka(LocalDateTime vrijemePovratka) {
        this.vrijemePovratka = vrijemePovratka;
        return this;
    }

    public AranzmanGraditelj postaviBrojNocenja(int brojNocenja) {
        this.brojNocenja = brojNocenja;
        return this;
    }

    public AranzmanGraditelj postaviDoplatuZaJednokrevetnuSobu(Long doplata) {
        this.doplataZaJednokrevetnuSobu = doplata;
        return this;
    }

    public AranzmanGraditelj postaviPrijevoz(List<String> prijevoz) {
        this.prijevoz = prijevoz;
        return this;
    }

    public AranzmanGraditelj postaviBrojDorucaka(int brojDorucaka) {
        this.brojDorucaka = brojDorucaka;
        return this;
    }

    public AranzmanGraditelj postaviBrojRucakova(int brojRucakova) {
        this.brojRucakova = brojRucakova;
        return this;
    }

    public AranzmanGraditelj postaviBrojVecera(int brojVecera) {
        this.brojVecera = brojVecera;
        return this;
    }

    public Aranzman build() {
        return new Aranzman(this);
    }

    public int getOznaka() {
        return oznaka;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getProgram() {
        return program;
    }

    public LocalDateTime getPocetniDatum() {
        return pocetniDatum;
    }

    public LocalDateTime getZavrsniDatum() {
        return zavrsniDatum;
    }

    public int getMinBrojPutnika() {
        return minBrojPutnika;
    }

    public int getMaxBrojPutnika() {
        return maxBrojPutnika;
    }

    public Long getCijenaPoOsobi() {
        return cijenaPoOsobi;
    }

    public LocalDateTime getVrijemeKretanja() {
        return vrijemeKretanja;
    }

    public LocalDateTime getVrijemePovratka() {
        return vrijemePovratka;
    }

    public int getBrojNocenja() {
        return brojNocenja;
    }

    public Long getDoplataZaJednokrevetnuSobu() {
        return doplataZaJednokrevetnuSobu;
    }

    public List<String> getPrijevoz() {
        return prijevoz;
    }

    public int getBrojDorucaka() {
        return brojDorucaka;
    }

    public int getBrojRucakova() {
        return brojRucakova;
    }

    public int getBrojVecera() {
        return brojVecera;
    }
}
