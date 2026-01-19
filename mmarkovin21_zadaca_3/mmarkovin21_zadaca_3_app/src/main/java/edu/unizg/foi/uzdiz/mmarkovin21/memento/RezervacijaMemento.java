package edu.unizg.foi.uzdiz.mmarkovin21.memento;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDateTime;

public class RezervacijaMemento {
    private final String ime;
    private final String prezime;
    private final int oznakaAranzmana;
    private final LocalDateTime datumVrijemePrijema;
    private final String stanje;
    private final LocalDateTime datumVrijemeOtkazivanja;

    private AranzmanMemento aranzmanMemento;

    public RezervacijaMemento(Rezervacija rez) {
        this.ime = rez.dohvatiIme();
        this.prezime = rez.dohvatiPrezime();
        this.oznakaAranzmana = rez.dohvatiOznakaAranzmana();
        this.datumVrijemePrijema = rez.dohvatiDatumVrijemePrijema();
        this.stanje = rez.dohvatiStanjeString();
        this.datumVrijemeOtkazivanja = rez.dohvatiDatumVrijemeOtkazivanja();
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

    public String dohvatiStanjeString() {
        return stanje;
    }

    public AranzmanMemento dohvatiAranzmanMemento() {
        return aranzmanMemento;
    }

    public LocalDateTime dohvatiDatumVrijemeOtkazivanja() {
        return datumVrijemeOtkazivanja;
    }
}
