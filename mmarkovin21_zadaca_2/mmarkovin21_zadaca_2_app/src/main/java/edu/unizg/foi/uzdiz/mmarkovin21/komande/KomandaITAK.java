package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterListeAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

import java.time.LocalDate;
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

        // Bridge uzorak: kreiranje ispisivača s odgovarajućim formaterom
        IspisivacAranzmana ispisivac = new IspisivacAranzmana(new FormaterListeAranzmana());

        LocalDate finalDatumOd = datumOd;
        LocalDate finalDatumDo = datumDo;

        // Filtriranje aranžmana prema datumu (ako je specificiran)
        List<TuristickaKomponenta> aranzmaniZaPrikaz = agencija.dohvatiPodatke().stream()
                .filter(k -> k instanceof Aranzman)
                .map(k -> (Aranzman) k)
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
            ispisivac.ispisi(aranzmaniZaPrikaz);
        }
    }
}
