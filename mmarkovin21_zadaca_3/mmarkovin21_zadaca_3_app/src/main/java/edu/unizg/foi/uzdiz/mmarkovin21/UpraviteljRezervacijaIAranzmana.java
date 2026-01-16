package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class UpraviteljRezervacijaIAranzmana {
    protected List<Aranzman> sviAranzmani;
    protected final List<Rezervacija> sveRezervacije;
    public UpraviteljRezervacijaIAranzmana() {
        this.sviAranzmani = new ArrayList<>();
        this.sveRezervacije = new ArrayList<>();
    }

    public void postaviAranzmane(List<Aranzman> aranzmani) {
        this.sviAranzmani = aranzmani;
    }

    public abstract boolean dodajRezervaciju(Rezervacija novaRez);
    public abstract boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana);
    public abstract void azurirajStanjeAranzmana(Aranzman aranzman);
    public abstract void azurirajStanjaRezervacija(Aranzman aranzman);
    protected abstract void pokusajAktiviratiOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez);

    protected Rezervacija pronadjiPostojecuRezervaciju(Aranzman aranzman, Rezervacija novaRez) {
        return dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .filter(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()))
                .findFirst()
                .orElse(null);
    }

    protected boolean aranzmaniSePreklapaju(Aranzman a1, Aranzman a2) {
        LocalDate pocetak1 = a1.dohvatiPocetniDatum();
        LocalDate kraj1 = a1.dohvatiZavrsniDatum();
        LocalDate pocetak2 = a2.dohvatiPocetniDatum();
        LocalDate kraj2 = a2.dohvatiZavrsniDatum();

        return pocetak1.isAfter(kraj2) || kraj1.isBefore(pocetak2);
    }

    protected int izracunajBrojBrojivihRezervacija(Aranzman aranzman) {
        return (int) dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .count();
    }

    protected boolean jeBrojiva(Rezervacija rezervacija) {
        String stanje = rezervacija.dohvatiStanjeString();
        return stanje.equals("PRIMLJENA")
                || stanje.equals("AKTIVNA")
                || stanje.equals("NA ČEKANJU");
    }

    protected List<Rezervacija> dohvatiRezervacijeAranzmana(Aranzman aranzman) {
        List<Rezervacija> rezervacije = new ArrayList<>();
        for (TuristickaKomponenta komponenta : aranzman.dohvatiDjecu()) {
            if (komponenta instanceof Rezervacija) {
                rezervacije.add((Rezervacija) komponenta);
            }
        }
        return rezervacije;
    }

    protected Aranzman pronadjiAranzmanPoOznaci(int oznaka) {
        return sviAranzmani.stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }

    protected Rezervacija pronadjiRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        return sveRezervacije.stream()
                .filter(rez -> rez.dohvatiIme().equals(ime)
                        && rez.dohvatiPrezime().equals(prezime)
                        && rez.dohvatiOznakaAranzmana() == oznakaAranzmana
                        && !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .findFirst()
                .orElse(null);
    }

    public void obrisiSveAranzmane() {
        sviAranzmani = new ArrayList<>();
        sveRezervacije.clear();
    }

    public void obrisiSveRezervacije() {
        sveRezervacije.clear();
    }
}
