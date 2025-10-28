package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Aranzman {
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
    private final LocalDateTime vrijemeKretanja;
    private final LocalDateTime vrijemePovratka;
    private final int brojNocenja;
    private final Long doplataZaJednokrevetnuSobu;
    private final List<String> prijevoz;
    private final int brojDorucaka;
    private final int brojRucakova;
    private final int brojVecera;

    public Aranzman(AranzmanGraditelj graditelj) {
        this.oznaka = graditelj.getOznaka();
        this.naziv = graditelj.getNaziv();
        this.program = graditelj.getProgram();
        this.pocetniDatum = graditelj.getPocetniDatum();
        this.zavrsniDatum = graditelj.getZavrsniDatum();
        this.minBrojPutnika = graditelj.getMinBrojPutnika();
        this.maxBrojPutnika = graditelj.getMaxBrojPutnika();
        this.cijenaPoOsobi = graditelj.getCijenaPoOsobi();
        this.vrijemeKretanja = graditelj.getVrijemeKretanja();
        this.vrijemePovratka = graditelj.getVrijemePovratka();
        this.brojNocenja = graditelj.getBrojNocenja();
        this.doplataZaJednokrevetnuSobu = graditelj.getDoplataZaJednokrevetnuSobu();
        this.prijevoz = graditelj.getPrijevoz();
        this.brojDorucaka = graditelj.getBrojDorucaka();
        this.brojRucakova = graditelj.getBrojRucakova();
        this.brojVecera = graditelj.getBrojVecera();
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

    public Long getCijenaPoOsobi() {
        return cijenaPoOsobi;
    }

    public LocalDateTime getVrijemeKretanja() {
        return vrijemeKretanja;
    }

    public LocalDateTime getVrijemePovratka() {
        return vrijemePovratka;
    }

    public int getMinBrojPutnika() {
        return minBrojPutnika;
    }

    public int getMaxBrojPutnika() {
        return maxBrojPutnika;
    }

    public int getBrojNocenja() {
        return brojNocenja;
    }

    public Long getDoplataZaJednokrevetnuSobu() {
        return doplataZaJednokrevetnuSobu;
    }

    public List<String> getPrijevoz() {
        return prijevoz != null ? prijevoz : Collections.emptyList();
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
