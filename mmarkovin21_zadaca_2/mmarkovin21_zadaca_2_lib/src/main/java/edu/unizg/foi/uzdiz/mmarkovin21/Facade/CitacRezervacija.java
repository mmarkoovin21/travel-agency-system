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

    protected List<Map<String, Object>> ucitajRezervacije(String nazivDatoteke) {
        List<Map<String, Object>> rezervacije = new ArrayList<>();
        SkupljacGresaka skupljacGresaka = new SkupljacGresaka();

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
                    String opisGreske = "Neispravan broj atributa. Očekivano: " + OCEKIVANI_BROJ_ATRIBUTA + ", pronađeno: " + atributi.length;
                    skupljacGresaka.dodajGresku(brojRetka, opisGreske);
                    continue;
                }

                try {
                    Map<String, Object> validnaRezervacija = validator.validiraj(atributi, skupljacGresaka, brojRetka);

                    if (validnaRezervacija != null) {
                        rezervacije.add(validnaRezervacija);
                    }
                } catch (Exception e) {
                    skupljacGresaka.dodajGresku(brojRetka, e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        
        skupljacGresaka.ispisiGreske();

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
