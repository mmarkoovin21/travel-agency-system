package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.citaci.CitacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.citaci.CitacRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaFactory;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

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
        CitacAranzmana citacA = new CitacAranzmana();
        CitacRezervacija citacR = new CitacRezervacija();

        this.aranzmani = citacA.ucitaj(datotekaAranzmani);
//        this.rezervacije = citacR.ucitaj(datotekaRezervacije);
    }

    public void pokreniInteraktivniNacin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Unesite komandu: ");
            String unos = scanner.nextLine().trim();

            String[] dijelovi = unos.split("\\s+");
            String nazivKomande = dijelovi[0].toUpperCase();

            KomandaFactory factory = KomandaFactory.dohvatiFactory(nazivKomande);
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


    public List<Aranzman> dohvatiAranzmane() { return aranzmani; }
    public List<Rezervacija> dohvatiRezervacije() { return rezervacije; }
}