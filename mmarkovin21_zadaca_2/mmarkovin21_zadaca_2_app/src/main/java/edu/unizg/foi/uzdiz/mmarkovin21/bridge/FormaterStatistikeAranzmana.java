package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterBrojeva;


public class FormaterStatistikeAranzmana implements TablicniFormater {

    @Override
    public int[] definirajSirineKolona() {
        return new int[]{8, 30, 10, 10, 10, 10, 10, 15};
    }

    @Override
    public String[] kreirajZaglavlje() {
        return new String[]{
                "Oznaka",
                "Naziv",
                "Ukupno rez.",
                "Aktivne",
                "Na čekanju",
                "Odgođene",
                "Otkazane",
                "Ukupan prihod"
        };
    }

    @Override
    public boolean[] definirajBrojcanaPolja() {
        return new boolean[]{true, false, true, true, true, true, true, true};
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Aranzman aranzman)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Aranzman");
        }

        int ukupnoRezervacija = 0;
        int brojAktivnih = 0;
        int brojNaCekanju = 0;
        int brojOdgodjenih = 0;
        int brojOtkazanih = 0;

        for (TuristickaKomponenta dijete : aranzman.dohvatiDjecu()) {
            if (dijete instanceof Rezervacija rezervacija) {
                ukupnoRezervacija++;

                String stanje = rezervacija.dohvatiStanjeString().toUpperCase();
                switch (stanje) {
                    case "AKTIVNA" -> brojAktivnih++;
                    case "NA ČEKANJU" -> brojNaCekanju++;
                    case "ODGOĐENA" -> brojOdgodjenih++;
                    case "OTKAZANA" -> brojOtkazanih++;
                    case "PRIMLJENA" -> {}
                }
            }
        }

        long ukupanPrihod = brojAktivnih * aranzman.dohvatiCijenaPoOsobi();

        return new String[]{
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiOznaka()),
                aranzman.dohvatiNaziv(),
                FormaterBrojeva.formatirajCijeliBroj(ukupnoRezervacija),
                FormaterBrojeva.formatirajCijeliBroj(brojAktivnih),
                FormaterBrojeva.formatirajCijeliBroj(brojNaCekanju),
                FormaterBrojeva.formatirajCijeliBroj(brojOdgodjenih),
                FormaterBrojeva.formatirajCijeliBroj(brojOtkazanih),
                FormaterBrojeva.formatirajCijeliBroj(ukupanPrihod)
        };
    }
}
