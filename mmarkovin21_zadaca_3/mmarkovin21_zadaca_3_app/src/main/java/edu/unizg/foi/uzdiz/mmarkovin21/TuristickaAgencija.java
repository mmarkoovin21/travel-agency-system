package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaTvornica;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.UcitavacPodataka;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TuristickaAgencija {
    private static TuristickaAgencija instanca;
    private final List<TuristickaKomponenta> podaci = new ArrayList<>();

    private TuristickaAgencija() {}

    public static TuristickaAgencija dohvatiInstancu() {
        if (instanca == null) {
            instanca = new TuristickaAgencija();
        }
        return instanca;
    }

    public void inicijaliziraj(String datotekaAranzmani, String datotekaRezervacije) {
        UcitavacPodataka.ucitajAranzmane(datotekaAranzmani);
        UcitavacPodataka.ucitajRezervacije(datotekaRezervacije);
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

    public List<Aranzman> dohvatiPodatke() {
        return podaci.stream()
                .filter(k -> k instanceof Aranzman)
                .map(k -> (Aranzman) k)
                .toList();
    }

    public void dodajPodatak(TuristickaKomponenta komponenta) {
        podaci.add(komponenta);
    }

    public void obrisiSveAranzmane() {
        podaci.clear();
    }

    public void obrisiSveRezervacije() {
        for (Aranzman aranzman : dohvatiPodatke()) {
            List<TuristickaKomponenta> djeca = new ArrayList<>(aranzman.dohvatiDjecu());
            for (TuristickaKomponenta dijete : djeca) {
                aranzman.ukloniDijete(dijete);
            }
        }
    }
}