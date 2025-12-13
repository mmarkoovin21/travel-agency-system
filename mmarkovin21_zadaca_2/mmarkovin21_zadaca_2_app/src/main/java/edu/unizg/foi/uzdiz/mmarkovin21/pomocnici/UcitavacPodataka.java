package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.UcitaniPodaciFacade;
import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// privremena klasa za ucitavanje podataka u agenciju i za komandu UP
public class UcitavacPodataka {
    private static final TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
    private static final AranzmanDirektor aranzmanDirektor = new AranzmanDirektor(new AranzmanGraditelj());
    public static void ucitajAranzmane(String datotekaAranzmani) {
        if (datotekaAranzmani == null || datotekaAranzmani.isEmpty()) {
            return;
        }

        UcitaniPodaciFacade.ucitajAranzmane(datotekaAranzmani);
        List<Map<String, Object>> aranzmani = UcitaniPodaciFacade.dohvatiAranzmane();

        if (aranzmani == null || aranzmani.isEmpty()) {
            System.out.println("Datoteka aranžmana je prazna ili nema aranžmana za učitavanje.");
            return;
        }

        for (Map<String, Object> aranzman : aranzmani) {
            try {
                Aranzman noviAranzman = aranzmanDirektor.konstruiraj(aranzman);
                agencija.dodajPodatak(noviAranzman);
            } catch (Exception e) {
                System.err.println("Greška pri kreiranju aranžmana: " + e.getMessage());
            }
        }
    }

    public static void ucitajRezervacije(String datotekaRezervacije) {
        if (datotekaRezervacije == null || datotekaRezervacije.isEmpty()) {
            return;
        }

        UcitaniPodaciFacade.ucitajRezervacije(datotekaRezervacije);
        List<Map<String, Object>> rezervacije = UcitaniPodaciFacade.dohvatiRezervacije();

        if (rezervacije == null || rezervacije.isEmpty()) {
            System.out.println("Datoteka rezervacija je prazna ili nema rezervacija za učitavanje.");
            return;
        }

        for (Map<String, Object> rezervacija : rezervacije) {
            try {
                Rezervacija rez = new Rezervacija(
                        (String) rezervacija.get("ime"),
                        (String) rezervacija.get("prezime"),
                        (Integer) rezervacija.get("oznaka"),
                        (LocalDateTime) rezervacija.get("datumIVrijemePrijema"),
                        (String) rezervacija.get("staneje")
                );

                Aranzman nadredeniAranzman = pronadjiAranzmanPoOznaci((Integer) rezervacija.get("oznaka"));

                if (nadredeniAranzman != null) {
                    agencija.dodajPodatak(rez);
                    nadredeniAranzman.dodajDijete(rez);
                } else {
                    System.err.println("Greška: Aranžman s oznakom " + rezervacija.get("oznaka")
                            + " nije pronađen za rezervaciju " + rez.dohvatiIme() + " " + rez.dohvatiPrezime());
                }
            } catch (Exception e) {
                System.err.println("Greška pri kreiranju rezervacije: " + e.getMessage());
            }
        }
    }

    private static Aranzman pronadjiAranzmanPoOznaci(Integer oznaka) {
        List<TuristickaKomponenta> podaci = agencija.dohvatiPodatke();
        return podaci.stream()
                .filter(a -> a instanceof Aranzman)
                .map(a -> (Aranzman) a)
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }
}
