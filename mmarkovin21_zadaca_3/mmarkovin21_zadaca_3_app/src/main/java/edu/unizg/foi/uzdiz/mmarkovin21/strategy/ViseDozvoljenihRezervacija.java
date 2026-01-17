package edu.unizg.foi.uzdiz.mmarkovin21.strategy;

import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViseDozvoljenihRezervacija extends UpraviteljRezervacijaIAranzmana {
    @Override
    public boolean dodajRezervaciju(Rezervacija novaRez) {
        Aranzman aranzman = pronadjiAranzmanPoOznaci(novaRez.dohvatiOznakaAranzmana());
        if (aranzman == null) {
            System.out.println("Greška: Aranžman ne postoji.");
            return false;
        }
        int brojBrojivihAranzmana = izracunajBrojBrojivihRezervacija(aranzman);
        int brojBrojivihOsoba = brojiveRezervacijeOsobe(novaRez.dohvatiIme(), novaRez.dohvatiPrezime()).size() + 1;
        if (brojBrojivihAranzmana < aranzman.dohvatiMinBrojPutnika()) {
            novaRez.primi();
        } else if (brojBrojivihAranzmana <= aranzman.dohvatiMaxBrojPutnika()) {
            if(brojBrojivihOsoba >= aranzman.dohvatiMaxBrojPutnika()/4) {
                novaRez.staviNaCekanje();
            }
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

    private List<Rezervacija> brojiveRezervacijeOsobe(String ime, String prezime) {
        return sveRezervacije.stream()
                .filter(rez -> rez.dohvatiIme().equalsIgnoreCase(ime)
                        && rez.dohvatiPrezime().equalsIgnoreCase(prezime))
                .filter(this::jeBrojiva)
                .toList();
    }

    @Override
    public void azurirajStanjaRezervacija(Aranzman aranzman) {
        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        int brojBrojivih = brojiveRezervacije.size();
        int minBrojPutnika = aranzman.dohvatiMinBrojPutnika();
        int maxBrojPutnika = aranzman.dohvatiMaxBrojPutnika();
        int maxPoOsobi = maxBrojPutnika / 4;

        Map<String, Integer> rezervacijaPoOsobi = new HashMap<>();

        if (brojBrojivih < minBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                String kljucOsobe = rez.dohvatiIme() + " " + rez.dohvatiPrezime();
                int trenutnoOsoba = rezervacijaPoOsobi.getOrDefault(kljucOsobe, 0);

                if (trenutnoOsoba < maxPoOsobi) {
                    rez.primi();
                    rezervacijaPoOsobi.put(kljucOsobe, trenutnoOsoba + 1);
                } else {
                    rez.odgodi();
                }
            }
        } else if (brojBrojivih <= maxBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                String kljucOsobe = rez.dohvatiIme() + " " + rez.dohvatiPrezime();
                int trenutnoOsoba = rezervacijaPoOsobi.getOrDefault(kljucOsobe, 0);

                if (trenutnoOsoba < maxPoOsobi) {
                    rez.aktiviraj();
                    rezervacijaPoOsobi.put(kljucOsobe, trenutnoOsoba + 1);
                } else {
                    rez.odgodi();
                }
            }
        } else {
            int ukupnoAktivnih = 0;
            for (Rezervacija rez : brojiveRezervacije) {
                String kljucOsobe = rez.dohvatiIme() + " " + rez.dohvatiPrezime();
                int trenutnoOsoba = rezervacijaPoOsobi.getOrDefault(kljucOsobe, 0);

                if (trenutnoOsoba < maxPoOsobi && ukupnoAktivnih < maxBrojPutnika) {
                    rez.aktiviraj();
                    ukupnoAktivnih++;
                    rezervacijaPoOsobi.put(kljucOsobe, trenutnoOsoba + 1);
                } else {
                    rez.staviNaCekanje();
                }
            }
        }
    }

    private Map<String, List<Rezervacija>> grupirajRezervacijePoOsobi(List<Rezervacija> brojiveRezervacije) {
        return brojiveRezervacije.stream()
                .collect(Collectors.groupingBy(rez -> rez.dohvatiIme() + " " + rez.dohvatiPrezime()));
    }

    @Override
    protected void pokusajAktiviratiOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez) {
        List<Rezervacija> odgodeneRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> rez.dohvatiStanjeString().equals("ODGOĐENA"))
                .filter(rez -> rez.dohvatiIme().equals(otkazanaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(otkazanaRez.dohvatiPrezime()))
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        if (!odgodeneRezervacije.isEmpty()) {
            Rezervacija prvaOdgodena = odgodeneRezervacije.getFirst();

                int brojBrojivihAranzman = izracunajBrojBrojivihRezervacija(aranzman) + 1;
                int brojBrojivihOsoba = brojiveRezervacijeOsobe(prvaOdgodena.dohvatiIme(), prvaOdgodena.dohvatiPrezime()).size() + 1;
                if (brojBrojivihAranzman < aranzman.dohvatiMinBrojPutnika()) {
                    prvaOdgodena.primi();
                } else if (brojBrojivihAranzman <= aranzman.dohvatiMaxBrojPutnika()) {
                    if (brojBrojivihOsoba >= aranzman.dohvatiMaxBrojPutnika()/4) {
                        prvaOdgodena.staviNaCekanje();
                    }
                    prvaOdgodena.aktiviraj();
                } else {
                    prvaOdgodena.staviNaCekanje();
                }

                azurirajStanjeAranzmana(aranzman);
                azurirajStanjaRezervacija(aranzman);
        }
    }
}
