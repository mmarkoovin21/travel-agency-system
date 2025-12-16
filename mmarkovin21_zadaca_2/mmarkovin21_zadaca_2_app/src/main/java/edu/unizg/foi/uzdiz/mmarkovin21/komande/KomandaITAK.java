package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterListeAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KomandaITAK implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaITAK(TuristickaAgencija agencija) {
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
            else if (datumOd.isAfter(datumDo)) {
                System.out.println("Greška: Početni datum ne može biti nakon završnog datuma.");
                return;
            }
        } else if (parametri.length > 1) {
            System.out.println("Greška: Komanda ITAK zahtijeva oba datuma (od i do) ili nijedan.");
            return;
        }

        if (parametri.length == 3) {
            System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2]);
        } else {
            System.out.println("Naziv komande: " + parametri[0]);
        }

        IspisivacAranzmana ispisivac = new IspisivacAranzmana(new FormaterListeAranzmana());

        LocalDate finalDatumOd = datumOd;
        LocalDate finalDatumDo = datumDo;

        List<Aranzman> aranzmaniZaPrikaz = agencija.dohvatiPodatke()
                .stream()
                .filter(aranzman -> {
                    if (finalDatumOd != null && finalDatumDo != null) {
                        return !aranzman.dohvatiPocetniDatum().isBefore(finalDatumOd) &&
                               !aranzman.dohvatiZavrsniDatum().isAfter(finalDatumDo);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        if (aranzmaniZaPrikaz.isEmpty()) {
            System.out.println("Nema pronađenih aranžmana za ovo razdoblje.");
        } else {
            Comparator<Aranzman> usporedjivac = Comparator.comparing(Aranzman::dohvatiPocetniDatum);
            if (KonfiguracijaIspisa.dohvatiInstancu().jeObrnutoSortiranje()) {
                usporedjivac = usporedjivac.reversed();
            }
            aranzmaniZaPrikaz = aranzmaniZaPrikaz.stream()
                    .sorted(usporedjivac)
                    .collect(Collectors.toList());

            ispisivac.ispisiSNaslovom(aranzmaniZaPrikaz, "TURISTIČKI ARANŽMANI");
        }
    }
}
