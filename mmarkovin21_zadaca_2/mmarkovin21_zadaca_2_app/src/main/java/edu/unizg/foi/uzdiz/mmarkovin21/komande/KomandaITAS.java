package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterStatistikeAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KomandaITAS implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaITAS(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (parametri.length == 3) {
            datumOd = PretvaracTipovaPodataka.parsirajDatum(parametri[1]);
            datumDo = PretvaracTipovaPodataka.parsirajDatum(parametri[2]);

            if (datumOd == null || datumDo == null) {
                System.out.println("Greška: Neispravan format datuma. Koristite format: d.M.yyyy. ili dd.MM.yyyy.");
                return;
            }
            if (datumOd.isAfter(datumDo)) {
                System.out.println("Greška: Početni datum ne može biti nakon završnog datuma.");
                return;
            }
        } else if (parametri.length != 1) {
            System.out.println("Greška: Neispravan broj parametara. Očekivano: ITAS ili ITAS <od> <do>");
            return;
        }

        IspisivacAranzmana ispisivac = new IspisivacAranzmana(new FormaterStatistikeAranzmana());

        LocalDate finalDatumOd = datumOd;
        LocalDate finalDatumDo = datumDo;

        List<Aranzman> aranzmani = agencija.dohvatiPodatke()
                .stream()
                .filter(aranzman -> {
                    if (finalDatumOd != null && finalDatumDo != null) {
                        return !aranzman.dohvatiPocetniDatum().isAfter(finalDatumDo) &&
                               !aranzman.dohvatiZavrsniDatum().isBefore(finalDatumOd);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        if (aranzmani.isEmpty()) {
            if (datumOd != null) {
                System.out.println("Nema aranžmana u zadanom razdoblju.");
            } else {
                System.out.println("Nema aranžmana za prikaz.");
            }
            return;
        }

        Comparator<Aranzman> comparator = Comparator.comparing(Aranzman::dohvatiPocetniDatum);
        if (KonfiguracijaIspisa.dohvatiInstancu().jeObrnutoSortiranje()) {
            comparator = comparator.reversed();
        }
        aranzmani = aranzmani.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (datumOd != null) {
            System.out.println("\n STATISTIKA ARANŽMANA ZA RAZDOBLJE " +
                    PretvaracTipovaPodataka.formatirajDatum(datumOd) + " - " +
                    PretvaracTipovaPodataka.formatirajDatum(datumDo) + " ");
        } else {
            System.out.println("\n STATISTIKA SVIH ARANŽMANA ");
        }

        ispisivac.ispisi(aranzmani);
    }
}
