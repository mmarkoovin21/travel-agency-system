package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// CitacRezervacija je odgovoran za učitavanje rezervacija iz datoteke i za proizvodnju lista rezervacija i osoba
public class CitacRezervacija {
    private final ValidatorRezervacija validator = new ValidatorRezervacija();
    public List<Rezervacija> rezervacije = new ArrayList<>();

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
                    validnaRezervacija.promijeniStanje(odrediStanje(validnaRezervacija.dohvatiOznakaAranzmana()));
                    rezervacije.add(validnaRezervacija);
                    azurirajStanjaPrimljenihRezervacija(validnaRezervacija.dohvatiOznakaAranzmana());
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

    private String odrediStanje(int oznaka) {
        Aranzman aranzman = TuristickaAgencija.dohvatiInstancu()
                .dohvatiAranzmane()
                .stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);

        if (aranzman == null) {
            System.err.println("Aranžman s oznakom " + oznaka + " nije pronađen.");
            return "";
        }

        long brojTrenutnihRezervacija = this.rezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznaka)
                .count() + 1;

        if (brojTrenutnihRezervacija > aranzman.dohvatiMaxBrojPutnika()) {
            return "na čekanju";
        } else if (brojTrenutnihRezervacija >= aranzman.dohvatiMinBrojPutnika()) {
            return "aktivna";
        } else {
            return "primljena";
        }
    }


    private void azurirajStanjaPrimljenihRezervacija(int oznakaAranzmana) {
        Aranzman aranzman = TuristickaAgencija.dohvatiInstancu()
                .dohvatiAranzmane()
                .stream()
                .filter(a -> a.dohvatiOznaka() == oznakaAranzmana)
                .findFirst()
                .orElse(null);

        if (aranzman == null) {
            return;
        }

        long brojRezervacija = rezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .count();

        if (brojRezervacija >= aranzman.dohvatiMinBrojPutnika()) {
            rezervacije.stream()
                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                    .filter(r -> "primljena".equals(r.dohvatiStanje()))
                    .forEach(r -> r.promijeniStanje("aktivna"));
        }
    }

}
