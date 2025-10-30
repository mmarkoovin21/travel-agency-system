package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDateTime;

public class Rezervacija {
    private String ime;
    private String prezime;
    private int oznakaAranzmana;
    private LocalDateTime datumVrijemePrijema;
    private String vrsta;

    public Rezervacija(String ime, String prezime, int oznakaAranzmana,
                       LocalDateTime datumVrijemePrijema, String vrsta) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaAranzmana = oznakaAranzmana;
        this.datumVrijemePrijema = datumVrijemePrijema;
        this.vrsta = vrsta;
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

    public String dohvatiVrsta() {
        return vrsta;
    }

    public void promijeniVrstu(String novaVrsta) {
        this.vrsta = novaVrsta;
    }


}
