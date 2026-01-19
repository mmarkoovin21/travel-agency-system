package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.observer.Observer;
import edu.unizg.foi.uzdiz.mmarkovin21.observer.Pretplatnik;

import java.util.List;

public class KomandaPTAR implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaPTAR(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length == 2) {
            preplatiSveOsobeNaAranzmanu(parametri[1]);
        } else if (parametri.length == 4) {
            preplatiJednuOsobu(parametri[1], parametri[2], parametri[3]);
        } else {
            System.out.println("Greška: PTAR zahtijeva 1 ili 3 parametra");
            System.out.println("Primjeri:");
            System.out.println("  PTAR 3           - pretplaćuje sve osobe s rezervacijama na aranžmanu 3");
            System.out.println("  PTAR Lea Novak 3 - pretplaćuje Leu Novak na aranžman 3");
        }
    }

    private void preplatiJednuOsobu(String ime, String prezime, String oznakaStr) {
        int oznakaAranzmana;

        try {
            oznakaAranzmana = Integer.parseInt(oznakaStr);
        } catch (NumberFormatException e) {
            System.out.println("Greška: Oznaka aranžmana mora biti broj");
            return;
        }

        Aranzman aranzman = pronadiAranzmanPoOznaci(oznakaAranzmana);

        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + oznakaAranzmana + " ne postoji");
            return;
        }

        if (jeVecPretplacen(aranzman, ime, prezime)) {
            System.out.println("Osoba " + ime + " " + prezime + " je već pretplaćena na aranžman " + oznakaAranzmana);
            return;
        }

        Pretplatnik pretplatnik = new Pretplatnik(ime, prezime, oznakaAranzmana);
        aranzman.registrirajObservera(pretplatnik);

        System.out.println("Osoba " + ime + " " + prezime + " uspješno pretplaćena na aranžman " + oznakaAranzmana);
    }

    private void preplatiSveOsobeNaAranzmanu(String oznakaStr) {
        int oznakaAranzmana;

        try {
            oznakaAranzmana = Integer.parseInt(oznakaStr);
        } catch (NumberFormatException e) {
            System.out.println("Greška: Oznaka aranžmana mora biti broj");
            return;
        }

        Aranzman aranzman = pronadiAranzmanPoOznaci(oznakaAranzmana);

        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + oznakaAranzmana + " ne postoji");
            return;
        }

        int brojDodanih = 0;

        for (TuristickaKomponenta dijete : aranzman.dohvatiDjecu()) {
            if (dijete instanceof Rezervacija rez) {
                String ime = rez.dohvatiIme();
                String prezime = rez.dohvatiPrezime();

                if (!jeVecPretplacen(aranzman, ime, prezime)) {
                    Pretplatnik pretplatnik = new Pretplatnik(ime, prezime, oznakaAranzmana);
                    aranzman.registrirajObservera(pretplatnik);
                    brojDodanih++;
                }
            }
        }

        if (brojDodanih == 0) {
            System.out.println("Nema novih osoba za pretplatiti na aranžman " + oznakaAranzmana);
        } else {
            System.out.println("Pretplaćeno " + brojDodanih + " osoba na aranžman " + oznakaAranzmana);
        }
    }

    private boolean jeVecPretplacen(Aranzman aranzman, String ime, String prezime) {
        for (Observer o : aranzman.dohvatiObservere()) {
            if (o.dohvatiIme().equals(ime) && o.dohvatiPrezime().equals(prezime)) {
                return true;
            }
        }
        return false;
    }

    private Aranzman pronadiAranzmanPoOznaci(int oznaka) {
        List<Aranzman> aranzmani = agencija.dohvatiPodatke();
        return aranzmani.stream().filter(a -> a.dohvatiOznaka() == oznaka).findFirst().orElse(null);
    }
}
