package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.UcitavacPodataka;

import java.io.File;

public class KomandaUP implements Komanda {

    public KomandaUP() {
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 3) {
            System.out.println("Greška: Pogrešna sintaksa komande UP. Ispravan format: UP <A|R> <datoteka>.");
            return;
        }

        String argument = parametri[1];
        String datoteka = parametri[2];

        if (!argument.equals("A") && !argument.equals("R")) {
            System.out.println("Greška: Neispravan format argumenta. Koristite 'A' za aranžmane ili 'R' za rezervacije.");
            return;
        }

        File file = new File(datoteka);
        if (!file.exists()) {
            System.out.println("Greška: Datoteka '" + datoteka + "' ne postoji.");
            return;
        }

        if (!datoteka.toLowerCase().endsWith(".csv")) {
            System.out.println("Greška: Datoteka mora biti u .csv formatu.");
            return;
        }

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2]);

        switch (argument) {
            case "A":
                UcitavacPodataka.ucitajAranzmane(datoteka);
                System.out.println("Aranžmani su uspješno učitani iz datoteke '" + datoteka + "'.");
                break;
            case "R":
                UcitavacPodataka.ucitajRezervacije(datoteka);
                System.out.println("Rezervacije su uspješno učitane iz datoteke '" + datoteka + "'.");
                break;
        }
    }
}