package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDateTime;

public class Rezervacija {
    private String ime;
    private String prezime;
    private int oznakaAranzmana;
    private LocalDateTime datumVrijemePrijema;
    private String stanje;

    public Rezervacija(String ime, String prezime, int oznakaAranzmana,
                       LocalDateTime datumVrijemePrijema, String vrsta) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaAranzmana = oznakaAranzmana;
        this.datumVrijemePrijema = datumVrijemePrijema;
        this.stanje = vrsta;
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

    public String dohvatiStanje() {
        return stanje;
    }

    public void promijeniStanje(String novoStanje) {
        this.stanje = novoStanje;
    }

}
