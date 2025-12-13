package edu.unizg.foi.uzdiz.mmarkovin21;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Niste unjeli argumente datoteka za učitavanje aranžmana i rezervacija!");
            System.out.println("Učitajte ih korištenjem komande UP [A|R] <datoteka> prije pokretanja interaktivnog načina rada.");
        }

        String datotekaAranzmani = null;
        String datotekaRezervacije = null;


        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--ta")) {
                datotekaAranzmani = args[i + 1];
            } else if (args[i].equals("--rta")) {
                datotekaRezervacije = args[i + 1];
            } else {
                System.out.println("Neispravan format argumenata!");
            }
        }

        TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
        agencija.inicijaliziraj(datotekaAranzmani, datotekaRezervacije);
        agencija.pokreniInteraktivniNacin();
    }
}