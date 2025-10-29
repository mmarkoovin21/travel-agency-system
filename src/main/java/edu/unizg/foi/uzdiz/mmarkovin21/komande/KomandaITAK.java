package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterTablice;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.time.LocalDate;

public class KomandaITAK implements Komanda {
    private TuristickaAgencija agencija;

    public KomandaITAK(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (parametri.length == 3) {
            datumOd = PretvaracDatuma.parsirajDatum(parametri[1]);
            datumDo = PretvaracDatuma.parsirajDatum(parametri[2]);

            if (datumOd == null || datumDo == null) {
                System.err.println("Greška: Neispravan format datuma. Koristite format: d.M.yyyy. ili dd.MM.yyyy.");
                return;
            }
        }

        FormaterTablice tablica = new FormaterTablice(new int[]{5, 30, 12, 12, 10, 10, 8, 8, 8});

        tablica.dodajRed("Oznaka", "Naziv", "Početni datum", "Završni datum", "Vrijeme kretanja", "Vrijeme povratka", "Cijena", "Min broj putnika", "Maks broj putnika");

        LocalDate finalDatumOd = datumOd;
        LocalDate finalDatumDo = datumDo;

        for (var aranzman : agencija.dohvatiAranzmane()) {
            if (finalDatumOd != null && finalDatumDo != null) {
                if (aranzman.dohvatiPocetniDatum().isBefore(finalDatumOd) ||
                        aranzman.dohvatiPocetniDatum().isAfter(finalDatumDo)) {
                    continue;
                }
            }

            tablica.dodajRed(
                    String.valueOf(aranzman.dohvatiOznaka()),
                    aranzman.dohvatiNaziv(),
                    PretvaracDatuma.formatirajDatum(aranzman.dohvatiPocetniDatum()),
                    PretvaracDatuma.formatirajDatum(aranzman.dohvatiZavrsniDatum()),
                    aranzman.dohvatiVrijemeKretanja() != null ? PretvaracDatuma.formatirajVrijeme(aranzman.dohvatiVrijemeKretanja()) : "",
                    aranzman.dohvatiVrijemePovratka() != null ? PretvaracDatuma.formatirajVrijeme(aranzman.dohvatiVrijemePovratka()) : "",
                    String.valueOf(aranzman.dohvatiCijenaPoOsobi()),
                    String.valueOf(aranzman.dohvatiMinBrojPutnika()),
                    String.valueOf(aranzman.dohvatiMaxBrojPutnika())
            );
        }

        System.out.println(tablica.formatiraj());
    }
}
