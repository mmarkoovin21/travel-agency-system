package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Osoba;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// CitacRezervacija je odgovoran za učitavanje rezervacija iz datoteke i za proizvodnju lista rezervacija i osoba
public class CitacRezervacija {
    public List<Rezervacija> rezervacije = new ArrayList<>();
    private final ValidatorRezervacija validator = new ValidatorRezervacija();

    public List<Rezervacija> ucitajRezervacije(String nazivDatoteke) {

        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (prviRedak) {
                    prviRedak = false;
                    continue;
                }
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                String[] atributi = linija.split(",");

                try {
                    Rezervacija validnaRezervacija = validator.validiraj(atributi);

                        if (validnaRezervacija == null) {
                            System.err.println("Neispravan format rezervacije na retku " + brojRetka);
                            continue;
                        }
                    rezervacije.add(validnaRezervacija);
                } catch (Exception e) {
                    System.err.println("Greška pri obradi retka " + brojRetka + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }
        System.out.println("Rezervacije ucitane iz datoteke: " + rezervacije.size());

        return rezervacije;
    }

//    private List<Osoba> ucitajOsobeIzRezervacija(List<Rezervacija> rezervacije) {
//        List<Osoba> osobe = new ArrayList<>();
//
//        for (Rezervacija rezervacija : rezervacije) {
//            String ime = rezervacija.dohvatiIme();
//            String prezime = rezervacija.dohvatiPrezime();
//            Osoba osoba = pronadiIliKreirajOsobu(osobe, ime, prezime);
//            osoba.dodajRezervaciju(rezervacija);
//        }
//        return osobe;
//    }

//    private String odrediStanje(int oznaka) {
//        Aranzman aranzman = TuristickaAgencija.dohvatiInstancu()
//                .dohvatiAranzmane()
//                .stream()
//                .filter(a -> a.dohvatiOznaka() == oznaka)
//                .findFirst()
//                .orElse(null);
//        Long brojTrenutnihRezervacija = this.rezervacije.stream()
//                .filter(r -> r.dohvatiOznakaAranzmana() == oznaka)
//                .count();
//        if (brojTrenutnihRezervacija >= aranzman.dohvatiMinBrojPutnika()
//                && brojTrenutnihRezervacija <= aranzman.dohvatiMaxBrojPutnika()) {
//            return "aktivna";
//        } else if (brojTrenutnihRezervacija > aranzman.dohvatiMaxBrojPutnika()) {
//            return "na čekanju";
//        } else {
//            return "primljena";
//        }
//    }
}
