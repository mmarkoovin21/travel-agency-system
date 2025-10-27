package edu.unizg.foi.uzdiz.mmarkovin21;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Nedostaju argumenti! Potrebno: --ta <datoteka> --rta <datoteka>");
            return;
        }

        String datotekaAranzmani = null;
        String datotekaRezervacije = null;


        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--ta")) {
                datotekaAranzmani = args[i + 1];
            } else if (args[i].equals("--rta")) {
                datotekaRezervacije = args[i + 1];
            }
        }

        if (datotekaAranzmani == null || datotekaRezervacije == null) {
            System.out.println("Nedostaju obavezni argumenti --ta i --rta!");
            return;
        }

        TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
        agencija.inicijaliziraj(datotekaAranzmani, datotekaRezervacije);
        agencija.pokreniInteraktivniNacin();

        System.out.println("Učitavam: " + datotekaAranzmani);
        System.out.println("Učitavam: " + datotekaRezervacije);
    }
}