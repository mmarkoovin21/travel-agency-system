package edu.unizg.foi.uzdiz.mmarkovin21;

//import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
//import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaTvornica;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaTvornica;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.UcitaniPodaciFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TuristickaAgencija {
    private static TuristickaAgencija instanca;
    private List<Aranzman> aranzmani = new ArrayList<>();
    private List<Rezervacija> rezervacije = new ArrayList<>();

    private TuristickaAgencija() {}

    public static TuristickaAgencija dohvatiInstancu() {
        if (instanca == null) {
            instanca = new TuristickaAgencija();
        }
        return instanca;
    }

    public void inicijaliziraj(String datotekaAranzmani, String datotekaRezervacije) {
        UcitaniPodaciFacade podaci = new UcitaniPodaciFacade();
        podaci.ucitajAranzmane(datotekaAranzmani);
        podaci.ucitajRezervacije(datotekaRezervacije);
        System.out.println("Aranzmani: \n" + aranzmani);
        System.out.println("Rezervacije: \n" + rezervacije);
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
            if (factory != null) {
                Komanda komanda = factory.kreirajKomandu();
                komanda.izvrsi(dijelovi);

                if (nazivKomande.equals("Q")) break;
            } else {
                System.out.println("Nepoznata komanda: " + nazivKomande);
            }
        }
        scanner.close();
    }
}