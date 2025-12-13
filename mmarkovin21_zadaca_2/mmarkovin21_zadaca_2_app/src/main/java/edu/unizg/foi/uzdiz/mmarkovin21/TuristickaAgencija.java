package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaTvornica;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.UcitaniPodaciFacade;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.RezervacijaFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TuristickaAgencija {
    private static TuristickaAgencija instanca;
    private final List<TuristickaKomponenta> podaci = new ArrayList<>();
    private final AranzmanDirektor aranzmanDirektor = new AranzmanDirektor(new AranzmanGraditelj());

    private TuristickaAgencija() {}

    public static TuristickaAgencija dohvatiInstancu() {
        if (instanca == null) {
            instanca = new TuristickaAgencija();
        }
        return instanca;
    }

    public void inicijaliziraj(String datotekaAranzmani, String datotekaRezervacije) {
        if (datotekaAranzmani != null && !datotekaAranzmani.isEmpty()) {
            UcitaniPodaciFacade.ucitajAranzmane(datotekaAranzmani);
            List<Map<String, Object>> aranzmani = UcitaniPodaciFacade.dohvatiAranzmane();
            for (Map<String, Object> aranzman : aranzmani) {
                instanca.dodajPodatak(aranzmanDirektor.konstruiraj(aranzman));
            }
        }
        if (datotekaRezervacije != null && !datotekaRezervacije.isEmpty()) {
            UcitaniPodaciFacade.ucitajRezervacije(datotekaRezervacije);
            List<Map<String, Object>> rezervacije = UcitaniPodaciFacade.dohvatiRezervacije();
            for (Map<String, Object> rezervacija : rezervacije) {
                Rezervacija rez = new Rezervacija(
                        (String) rezervacija.get("ime"),
                        (String) rezervacija.get("prezime"),
                        (Integer) rezervacija.get("oznaka"),
                        (LocalDateTime) rezervacija.get("datumIVrijemePrijema"),
                        (String) rezervacija.get("staneje")
                );

                Aranzman nadredeniAranzman = podaci.stream()
                        .filter(a -> a instanceof Aranzman)
                        .map(a -> (Aranzman) a)
                        .filter(a -> a.dohvatiOznaka() == (Integer) rezervacija.get("oznaka"))
                        .findFirst()
                        .orElse(null);

                if (nadredeniAranzman != null) {
                    instanca.dodajPodatak(rez);
                    nadredeniAranzman.dodajDijete(rez);
                } else {
                    System.out.println("Greška: Aranžman s oznakom " + rezervacija.get("oznaka") + " nije pronađen za rezervaciju " + rez.dohvatiIme() + " " + rez.dohvatiPrezime());
                }
            }
        }
    }

    public void pokreniInteraktivniNacin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.print(">>> Unesite komandu: ");
            String unos = scanner.nextLine().trim();

            String[] dijelovi = unos.split("\\s+");
            String nazivKomande = dijelovi[0];

            KomandaTvornica factory = KomandaTvornica.dohvatiFactory(nazivKomande);
            Komanda komanda = factory.kreirajKomandu();
            komanda.izvrsi(dijelovi);

            if (nazivKomande.equals("Q")) break;
        }
        scanner.close();
    }
    public List<TuristickaKomponenta> dohvatiPodatke() {
        return podaci;
    }

    public void dodajPodatak(TuristickaKomponenta komponenta) {
        podaci.add(komponenta);
    }
}