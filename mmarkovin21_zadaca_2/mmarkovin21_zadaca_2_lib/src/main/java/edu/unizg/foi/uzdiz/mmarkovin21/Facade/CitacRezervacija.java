package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitacRezervacija {
    private final ValidatorRezervacija validator;

    protected CitacRezervacija() {
        this.validator = new ValidatorRezervacija();
    }

    protected List<Map<String, String>> ucitajRezervacije(String nazivDatoteke) {
        List<Map<String, String>> rezervacije = new ArrayList<>();
        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                String[] atributi = CSVPomagac.parsirajRedakCSV(linija);
                int OCEKIVANI_BROJ_ATRIBUTA = 4;

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

                try {
                    Map<String, String> validnaRezervacija = validator.validiraj(atributi);

                    if (validnaRezervacija == null) {
                        System.err.println("Neispravan format rezervacije na retku " + brojRetka);
                        continue;
                    }
                    rezervacije.add(validnaRezervacija);
                } catch (Exception e) {
                    System.err.println("Greška pri obradi retka " + brojRetka + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        return rezervacije;
    }

    private boolean jeInformativniRedak(String[] atributi) {
        if (atributi.length == 0) {
            return false;
        }

        String[] kljucneRijeci = {"ime", "prezime", "oznaka", "datum"};
        return CSVPomagac.sadrziKljucneRijeci(atributi, kljucneRijeci);
    }

}
