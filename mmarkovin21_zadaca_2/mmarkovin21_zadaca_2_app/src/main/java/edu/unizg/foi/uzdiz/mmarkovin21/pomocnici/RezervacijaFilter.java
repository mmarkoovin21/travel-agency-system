package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.util.List;
import java.util.function.Predicate;

public class RezervacijaFilter {

    public static Predicate<? super Rezervacija> zaAranzman(int oznakaAranzmana) {
        return rezervacija -> rezervacija.dohvatiOznakaAranzmana() == oznakaAranzmana;
    }

    public static Predicate<? super Rezervacija> primljenIliAktivna() {
        return rezervacija -> rezervacija.dohvatiStanjeString().equals("PRIMLJENA") || rezervacija.dohvatiStanjeString().equals("AKTIVNA");
    }

    public static Predicate<? super Rezervacija> naCekanju() {
        return rezervacija -> rezervacija.dohvatiStanjeString().equals("NA ČEKANJU");
    }

    public static Predicate<? super Rezervacija> otkazana() {
        return rezervacija -> rezervacija.dohvatiStanjeString().equals("OTKAZANA");
    }

    public static Predicate<? super Rezervacija> odgodjena() {
        return rezervacija -> rezervacija.dohvatiStanjeString().equals("ODGOĐENA");
    }
}
