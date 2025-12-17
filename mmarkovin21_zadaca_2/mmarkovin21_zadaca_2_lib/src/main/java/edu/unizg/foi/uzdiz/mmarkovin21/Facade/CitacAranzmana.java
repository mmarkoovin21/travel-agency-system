package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitacAranzmana {
    private List<Map<String, Object>> aranzmani = new ArrayList<>();
    private ValidatorAranzmana validator = new ValidatorAranzmana();
    final int OCEKIVANI_BROJ_ATRIBUTA = 16;

    protected List<Map<String, Object>> ucitaj(String nazivDatoteke) {
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
                        String opisGreske = "Neispravan broj atributa. Očekivano: " + OCEKIVANI_BROJ_ATRIBUTA + ", pronađeno: " + atributi.length;
                        skupljacGresaka.dodajGresku(brojRetka, opisGreske);
                        continue;
                    }

                    Map<String, Object>  podaci = this.validator.validiraj(atributi, skupljacGresaka, brojRetka);
                    if (podaci != null) {
                        aranzmani.add(podaci);
                    }
                } catch (Exception e) {
                    skupljacGresaka.dodajGresku(brojRetka, e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        skupljacGresaka.ispisiGreske();

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
