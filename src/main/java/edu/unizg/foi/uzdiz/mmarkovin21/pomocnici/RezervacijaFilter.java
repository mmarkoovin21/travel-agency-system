package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.StanjeRezervacije;

import java.util.function.Predicate;

public class RezervacijaFilter {

    public static Predicate<Rezervacija> nijeOtkazana() {
        return r -> r.dohvatiStanje() != StanjeRezervacije.OTKAZANA;
    }

    public static Predicate<Rezervacija> zaAranzman(int oznakaAranzmana) {
        return r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana;
    }

    public static Predicate<Rezervacija> zaOsobu(String ime, String prezime) {
        return r -> r.dohvatiIme().equalsIgnoreCase(ime) &&
                    r.dohvatiPrezime().equalsIgnoreCase(prezime);
    }

    public static Predicate<Rezervacija> uStanju(StanjeRezervacije stanje) {
        return r -> r.dohvatiStanje() == stanje;
    }

    public static Predicate<Rezervacija> primljenIliAktivna() {
        return r -> r.dohvatiStanje() == StanjeRezervacije.PRIMLJENA ||
                    r.dohvatiStanje() == StanjeRezervacije.AKTIVNA;
    }

    public static Predicate<Rezervacija> naCekanju() {
        return r -> r.dohvatiStanje() == StanjeRezervacije.NA_CEKANJU;
    }

    public static Predicate<Rezervacija> otkazana() {
        return r -> r.dohvatiStanje() == StanjeRezervacije.OTKAZANA;
    }

    public static Predicate<Rezervacija> aktivneZaAranzman(int oznakaAranzmana) {
        return zaAranzman(oznakaAranzmana).and(nijeOtkazana());
    }

    public static Predicate<Rezervacija> drugiAranzmani(int oznakaAranzmana) {
        return r -> r.dohvatiOznakaAranzmana() != oznakaAranzmana;
    }
}
