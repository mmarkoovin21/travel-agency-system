package edu.unizg.foi.uzdiz.mmarkovin21;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String datotekaAranzmani = null;
        String datotekaRezervacije = null;
        String dopusteneRezervacije = null;

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
            } else if (args[i].equals("--jdr")) {
                dopusteneRezervacije = args[i];
                i++;
            } else if (args[i].equals("--vdr")) {
                dopusteneRezervacije = args[i];
                i++;
            } else {
                System.out.println("Nepoznata opcija: " + args[i]);
                System.out.println("Dozvoljene opcije: --ta <datoteka_aranzmani> --rta <datoteka_rezervacije>");
                return;
            }
        }

        if (datotekaAranzmani != null && provjeriPostojanjeDatoteke(datotekaAranzmani)) {
            System.out.println("Upozorenje: Datoteka s aranžmanima ne postoji ili nije dostupna: " + datotekaAranzmani);
            datotekaAranzmani = null; 
        }

        if (datotekaRezervacije != null && provjeriPostojanjeDatoteke(datotekaRezervacije)) {
            System.out.println("Upozorenje: Datoteka s rezervacijama ne postoji ili nije dostupna: " + datotekaRezervacije);
            datotekaRezervacije = null; 
        }

        TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
        agencija.inicijaliziraj(dopusteneRezervacije, datotekaAranzmani, datotekaRezervacije);
        agencija.pokreniInteraktivniNacin();
    }

    private static boolean provjeriPostojanjeDatoteke(String putanja) {
        File datoteka = new File(putanja);
        return !datoteka.exists() || !datoteka.isFile();
    }
}