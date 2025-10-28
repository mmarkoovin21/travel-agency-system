package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDateTime;

public class TuristickiAranzman {
    private  int oznaka;
    private String naziv;
    private String program;
    private LocalDateTime pocetniDatum;
    private LocalDateTime zavrsniDatum;
    private Long cijenaPoOsobi;
    private LocalDateTime vrijemeKretanja;
    private LocalDateTime vrijemePovratka;
    private int minBrojPutnika;
    private int maxBrojPutnika;
    private int brojNocenja;
    private Long doplataZaJednokrevetnuSobu;
    private String prijevoz;
    private int brojDorucaka;
    private int brojRucakova;
    private int brojVecera;

    public TuristickiAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi
    ) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.program = program;
        this.pocetniDatum = pocetniDatum;
        this.zavrsniDatum = zavrsniDatum;
        this.cijenaPoOsobi = cijenaPoOsobi;
        this.minBrojPutnika = minBrojPutnika;
        this.maxBrojPutnika = maxBrojPutnika;
    }

    public void postaviVrijemeKretanja(LocalDateTime vrijemeKretanja) {
        this.vrijemeKretanja = vrijemeKretanja;
    }
    public LocalDateTime dohvatiVrijemeKretanja() {
        return this.vrijemeKretanja;
    }
    public void postaviVrijemePovratka(LocalDateTime vrijemePovratka) {
        this.vrijemePovratka = vrijemePovratka;
    }
    public LocalDateTime dohvatiVrijemePovratka() {
        return this.vrijemePovratka;
    }

    public void postaviBrojNocenja(int brojNocenja) {
        this.brojNocenja = brojNocenja;
    }

    public int dohvatiBrojNocenja() {
        return this.brojNocenja;
    }

    public void postaviDoplataZaJednokrevetnuSobu(Long doplataZaJednokrevetnuSobu) {
        this.doplataZaJednokrevetnuSobu = doplataZaJednokrevetnuSobu;
    }

    public Long dohvatiDoplataZaJednokrevetnuSobu() {
        return this.doplataZaJednokrevetnuSobu;
    }

    public void postaviPrijevoz(String prijevoz) {
        this.prijevoz = prijevoz;
    }

    public String dohvatiPrijevoz() {
        return this.prijevoz;
    }

    public void postaviBrojDorucaka(Integer brojDorucaka) {
        this.brojDorucaka = brojDorucaka;
    }

    public int dohvatiBrojDorucaka() {
        return this.brojDorucaka;
    }

    public void postaviBrojRucakova(Integer brojRucakova) {
        this.brojRucakova = brojRucakova;
    }

    public Integer dohvatiBrojRucakova() {
        return this.brojRucakova;
    }

    public void postaviBrojVecera(Integer brojVecera) {
        this.brojVecera = brojVecera;
    }

    public int dohvatiBrojVecera() {
        return this.brojVecera;
    }
}
