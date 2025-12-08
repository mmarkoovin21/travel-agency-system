package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDateTime;

public class Rezervacija {
    private String ime;
    private String prezime;
    private int oznakaAranzmana;
    private LocalDateTime datumVrijemePrijema;
    private StanjeRezervacije stanje;
    private LocalDateTime datumVrijemeOtkazivanja;

    public Rezervacija(String ime, String prezime, int oznakaAranzmana,
                       LocalDateTime datumVrijemePrijema, String vrsta) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaAranzmana = oznakaAranzmana;
        this.datumVrijemePrijema = datumVrijemePrijema;
        this.stanje = StanjeRezervacije.fromString(vrsta);
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

    public StanjeRezervacije dohvatiStanje() {
        return stanje;
    }

    public String dohvatiStanjeString() {
        return stanje != null ? stanje.getVrijednost() : "";
    }

    public void promijeniStanje(String novoStanje) {
        this.stanje = StanjeRezervacije.fromString(novoStanje);
    }

    public void promijeniStanje(StanjeRezervacije novoStanje) {
        this.stanje = novoStanje;
    }

    public LocalDateTime dohvatiDatumVrijemeOtkazivanja() {
        return datumVrijemeOtkazivanja;
    }

    public void postaviDatumVrijemeOtkazivanja(LocalDateTime datumVrijemeOtkazivanja) {
        this.datumVrijemeOtkazivanja = datumVrijemeOtkazivanja;
    }

}
