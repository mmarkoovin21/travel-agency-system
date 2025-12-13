package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;

import java.time.LocalDateTime;

public class Rezervacija extends TuristickaKomponenta {
    private String ime;
    private String prezime;
    private int oznakaAranzmana;
    private LocalDateTime datumVrijemePrijema;
    private StanjeRezervacije stanje;
    private LocalDateTime datumVrijemeOtkazivanja;

    private Aranzman aranzman;

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

    @Override
    public void ispisi(int razina) {
    // ovo će se mijenjati kasnije
        String uvlaka = "  ".repeat(razina);
        System.out.println(uvlaka + "└─ Rezervacija: " + ime + " " + prezime +
                " (stanje: " + dohvatiStanjeString() + ")");
    }

    @Override
    public double izracunajUkupnuCijenu() {
        // Cijena je određena aranžmanom na koji se odnosi
        if (aranzman != null) {
            return aranzman.dohvatiCijenaPoOsobi();
        }
        return 0;
    }

    public void postaviAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }

    public Aranzman dohvatiAranzman() {
        return aranzman;
    }
}
