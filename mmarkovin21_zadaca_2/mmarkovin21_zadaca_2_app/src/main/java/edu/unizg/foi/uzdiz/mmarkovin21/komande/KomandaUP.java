package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.UcitaniPodaciFacade;
import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.UcitavacPodataka;

import java.io.File;
import java.util.List;
import java.util.Map;

public class KomandaUP implements Komanda {
    AranzmanDirektor aranzmanDirektor = new AranzmanDirektor(new AranzmanGraditelj());
    TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();

    public KomandaUP(TuristickaAgencija agencija) {
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

        switch (argument) {
            case "A":
                UcitavacPodataka.ucitajAranzmane(datoteka);
                System.out.println("Aranžmani su uspješno učitani iz datoteke '" + datoteka + "'.");
                break;
            case "R":
                UcitaniPodaciFacade.ucitajRezervacije(datoteka);
                List<Map<String, Object>> rezervacije = UcitaniPodaciFacade.dohvatiRezervacije();
                UcitavacPodataka.ucitajRezervacije(datoteka);
                System.out.println("Rezervacije su uspješno učitane iz datoteke '" + datoteka + "'.");
                break;
        }
    }
}