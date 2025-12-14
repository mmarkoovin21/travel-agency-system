package edu.unizg.foi.uzdiz.mmarkovin21;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String datotekaAranzmani = null;
        String datotekaRezervacije = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--ta")) {
                if (i + 1 < args.length) {
                    datotekaAranzmani = args[i + 1];
                    i++;
                } else {
                    System.out.println("Nedostaje putanja datoteke nakon --ta");
                    return;
                }
            } else if (args[i].equals("--rta")) {
                if (i + 1 < args.length) {
                    datotekaRezervacije = args[i + 1];
                    i++;
                } else {
                    System.out.println("Nedostaje putanja datoteke nakon --rta");
                    return;
                }
            } else {
                System.out.println("Nepoznata opcija: " + args[i]);
                System.out.println("Dozvoljene opcije: --ta <datoteka_aranzmani> --rta <datoteka_rezervacije>");
                return;
            }
        }

        if (datotekaAranzmani != null && provjeriPostojanjeDatoteke(datotekaAranzmani)) {
            System.out.println("Datoteka s aranžmanima ne postoji: " + datotekaAranzmani);
            return;
        }

        if (datotekaRezervacije != null && provjeriPostojanjeDatoteke(datotekaRezervacije)) {
            System.out.println("Datoteka s rezervacijama ne postoji: " + datotekaRezervacije);
            return;
        }

        TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
        agencija.inicijaliziraj(datotekaAranzmani, datotekaRezervacije);
        agencija.pokreniInteraktivniNacin();
    }

    private static boolean provjeriPostojanjeDatoteke(String putanja) {
        File datoteka = new File(putanja);
        return !datoteka.exists() || !datoteka.isFile();
    }
}