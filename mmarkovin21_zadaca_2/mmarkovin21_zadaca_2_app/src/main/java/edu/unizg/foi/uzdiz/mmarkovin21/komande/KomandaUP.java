package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.UcitaniPodaciFacade;
import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;

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
                UcitaniPodaciFacade.ucitajAranzmane(datoteka);
                List<Map<String, Object>> aranzmani = UcitaniPodaciFacade.dohvatiAranzmane();
                for (Map<String, Object> aranzman : aranzmani) {
                    agencija.dodajPodatak(aranzmanDirektor.konstruiraj(aranzman));
                }
                System.out.println("Aranžmani uspješno učitani!");
                break;
            case "R":
                UcitaniPodaciFacade.ucitajRezervacije(datoteka);
                List<Map<String, Object>> rezervacije = UcitaniPodaciFacade.dohvatiRezervacije();
                // Dodaj logiku za rezervacije
                System.out.println("Rezervacije uspješno učitane!");
                break;
        }
    }
}