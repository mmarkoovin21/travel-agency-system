package edu.unizg.foi.uzdiz.mmarkovin21.Facade.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.validatori.ValidatorAranzmana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitacAranzmana {
    private List<Map<String, String>> aranzmani = new ArrayList<>();
    private ValidatorAranzmana validator = new ValidatorAranzmana();
    final int OCEKIVANI_BROJ_ATRIBUTA = 16;

    public List<Map<String, String>> ucitaj(String nazivDatoteke) {

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
                    String[] atributi = CSVPomagac.parsirajRedakCSV(linija);

                    if (prviRedak) {
                        prviRedak = false;
                        boolean imaHeader = jeInformativniRedak(atributi);
                        CSVPomagac.ispisiHeaderInfo(nazivDatoteke, imaHeader, OCEKIVANI_BROJ_ATRIBUTA, atributi.length);

                        if (imaHeader) {
                            continue;
                        }
                    }

                    if (atributi.length != OCEKIVANI_BROJ_ATRIBUTA) {
                        CSVPomagac.ispisiGreskaBrojaAtributa(brojRetka, nazivDatoteke, OCEKIVANI_BROJ_ATRIBUTA, atributi.length);
                        continue;
                    }

                    Map<String, String> podaci = this.validator.validiraj(atributi);
                    aranzmani.add(podaci);
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
        if (CSVPomagac.sadrziKljucneRijeci(atributi, kljucneRijeci)) {
            return true;
        }

        String prviAtribut = CSVPomagac.ocistiAtribut(atributi[0]);
        if (CSVPomagac.jeBroj(prviAtribut)) {
            return false;
        }

        return prviAtribut.trim().isEmpty();
    }

}
