package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.ValidiraniPodaci;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorAranzmana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CitacAranzmana {
    private List<Aranzman> aranzmani = new ArrayList<>();
    private ValidatorAranzmana validator = new ValidatorAranzmana();
    private AranzmanDirektor direktor = new AranzmanDirektor(new AranzmanGraditelj());
    final int OCEKIVANI_BROJ_ATRIBUTA = 16;

    public List<Aranzman> ucitaj(String nazivDatoteke) {

        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                try {
                    String[] atributi = CSVHelper.parsirajRedakCSV(linija);

                    if (prviRedak) {
                        prviRedak = false;
                        boolean imaHeader = jeInformativniRedak(atributi);
                        CSVHelper.ispisiHeaderInfo(nazivDatoteke, imaHeader, OCEKIVANI_BROJ_ATRIBUTA, atributi.length);

                        if (imaHeader) {
                            continue;
                        }
                    }

                    if (atributi.length != OCEKIVANI_BROJ_ATRIBUTA) {
                        CSVHelper.ispisiGreskaBrojaAtributa(brojRetka, nazivDatoteke, OCEKIVANI_BROJ_ATRIBUTA, atributi.length);
                        continue;
                    }

                    ValidiraniPodaci podaci = this.validator.validiraj(atributi);

                    if (podaci != null) {
                        Aranzman aranzman = direktor.konstruiraj(podaci);
                        aranzmani.add(aranzman);
                    }
                } catch (Exception e) {
                    System.err.println("Greška pri obradi retka " + brojRetka + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        return aranzmani;
    }

    private boolean jeInformativniRedak(String[] atributi) {
        if (atributi.length == 0) {
            return false;
        }

        String[] kljucneRijeci = {"oznaka", "naziv", "program"};
        if (CSVHelper.sadrziKljucneRijeci(atributi, kljucneRijeci)) {
            return true;
        }

        String prviAtribut = CSVHelper.ocistiAtribut(atributi[0]);
        if (CSVHelper.jeBroj(prviAtribut)) {
            return false;
        }

        return prviAtribut.trim().isEmpty();
    }

}
