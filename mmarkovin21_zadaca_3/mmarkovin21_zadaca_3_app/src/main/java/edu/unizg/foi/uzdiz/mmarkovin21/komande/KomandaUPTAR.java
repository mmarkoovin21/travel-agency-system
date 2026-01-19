package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.observer.Observer;

import java.util.List;

public class KomandaUPTAR implements Komanda {
    TuristickaAgencija agencija;
    public KomandaUPTAR(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length == 2) {
            ukloniSvePreplateNaAranzmanu(parametri[1]);
        } else if (parametri.length == 4) {
            ukloniPretplatuJedneOsobe(parametri[1], parametri[2], parametri[3]);
        } else {
            System.out.println("Greška: PTAR zahtijeva 1 ili 3 parametra");
            System.out.println("Primjeri:");
            System.out.println("  PTAR 3           - pretplaćuje sve osobe s rezervacijama na aranžmanu 3");
            System.out.println("  PTAR Lea Novak 3 - pretplaćuje Leu Novak na aranžman 3");
        }
    }

    private void ukloniPretplatuJedneOsobe(String ime, String prezime, String oznakaStr) {
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

        Observer zaBrisanje = null;

        for (Observer o : aranzman.dohvatiObservere()) {
            if (o.dohvatiIme().equals(ime) && o.dohvatiPrezime().equals(prezime)) {
                zaBrisanje = o;
                break;
            }
        }

        if (zaBrisanje == null) {
            System.out.println("Osoba " + ime + " " + prezime + " nije pretplaćena na aranžman " + oznakaAranzmana);
            return;
        }

        aranzman.ukloniObservera(zaBrisanje);
        System.out.println("Pretplata ukinuta za: " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana);

    }

    private void ukloniSvePreplateNaAranzmanu(String oznakaStr) {
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

        int brojPretplatnika = aranzman.dohvatiObservere().size();

        if (brojPretplatnika == 0) {
            System.out.println("Aranžman " + oznakaAranzmana + " nema pretplatnika");
            return;
        }

        aranzman.ukloniSveObservere();
        System.out.println("Uklonjeno " + brojPretplatnika + " pretplatnika s aranžmana " + oznakaAranzmana);

    }

    private Aranzman pronadiAranzmanPoOznaci(int oznakaAranzmana) {
        List<Aranzman> aranzmani = agencija.dohvatiPodatke();
        return aranzmani.stream()
                .filter(a -> a.dohvatiOznaka() == oznakaAranzmana)
                .findFirst()
                .orElse(null);
    }
}
