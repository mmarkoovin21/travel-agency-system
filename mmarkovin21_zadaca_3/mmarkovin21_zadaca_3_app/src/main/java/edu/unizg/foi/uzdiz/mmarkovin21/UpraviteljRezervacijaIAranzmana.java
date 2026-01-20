package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.util.ArrayList;
import java.util.Comparator;
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

    public boolean dodajRezervaciju(Rezervacija novaRez) {
        Aranzman aranzman = pronadjiAranzmanPoOznaci(novaRez.dohvatiOznakaAranzmana());
        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + novaRez.dohvatiOznakaAranzmana() + " ne postoji.");
            return false;
        }

        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman) + 1;

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            novaRez.primi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            novaRez.aktiviraj();
        } else {
            novaRez.staviNaCekanje();
        }

        aranzman.dodajDijete(novaRez);
        sveRezervacije.add(novaRez);

        azurirajStanjeAranzmana(aranzman);
        azurirajStanjaRezervacija(aranzman);

        return true;
    }

    public void azurirajStanjaRezervacija(Aranzman aranzman) {
        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        int brojBrojivih = brojiveRezervacije.size();
        int minBrojPutnika = aranzman.dohvatiMinBrojPutnika();
        int maxBrojPutnika = aranzman.dohvatiMaxBrojPutnika();

        if (brojBrojivih < minBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                rez.primi();
            }
        } else if (brojBrojivih <= maxBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                rez.aktiviraj();
            }
        } else {
            for (int i = 0; i < brojiveRezervacije.size(); i++) {
                Rezervacija rez = brojiveRezervacije.get(i);
                if (i < maxBrojPutnika) {
                    rez.aktiviraj();
                } else {
                    rez.staviNaCekanje();
                }
            }
        }
    }

    protected void pokusajAktiviratiOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez) {
    }


    public void azurirajStanjeAranzmana(Aranzman aranzman) {
        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman);

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            aranzman.pripremi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            aranzman.aktiviraj();
        } else {
            aranzman.popuni();
        }
    }

    public boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        Rezervacija rezervacija = pronadjiRezervaciju(ime, prezime, oznakaAranzmana);
        if (rezervacija == null) {
            return false;
        }

        String prethodnoStanje = rezervacija.dohvatiStanjeString();
        rezervacija.otkazi();

        Aranzman aranzman = rezervacija.dohvatiAranzman();
        if (aranzman == null) {
            return true;
        }

        azurirajStanjeAranzmana(aranzman);
        azurirajStanjaRezervacija(aranzman);

        if (prethodnoStanje.equals("ODGOĐENA")) {
            pokusajAktiviratiOdgodjenuRezervaciju(aranzman, rezervacija);
        }

        return true;
    }

    protected Rezervacija pronadjiPostojecuRezervaciju(Aranzman aranzman, Rezervacija novaRez) {
        return dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .filter(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()))
                .findFirst()
                .orElse(null);
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
                .min(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema).reversed())
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
