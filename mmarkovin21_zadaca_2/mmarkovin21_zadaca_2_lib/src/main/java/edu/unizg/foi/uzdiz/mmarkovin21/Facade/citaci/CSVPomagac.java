package edu.unizg.foi.uzdiz.mmarkovin21.Facade.citaci;

import java.util.ArrayList;
import java.util.List;

public class CSVPomagac {

    public static String ocistiAtribut(String atribut) {
        if (atribut == null) {
            return "";
        }
        String ociscen = atribut.replace("\uFEFF", "").trim();

        if (ociscen.startsWith("\"") && ociscen.endsWith("\"") && ociscen.length() > 1) {
            ociscen = ociscen.substring(1, ociscen.length() - 1);
        }

        return ociscen.trim();
    }

    public static String[] parsirajRedakCSV(String redak) {
        List<String> rezultat = new ArrayList<>();
        StringBuilder trenutniAtribut = new StringBuilder();
        boolean unutarNavodnika = false;

        for (int i = 0; i < redak.length(); i++) {
            char trenutniZnak = redak.charAt(i);

            if (trenutniZnak == '"') {
                unutarNavodnika = !unutarNavodnika;
            } else if (trenutniZnak == ',' && !unutarNavodnika) {
                rezultat.add(ocistiAtribut(trenutniAtribut.toString()));
                trenutniAtribut.setLength(0);
            } else {
                trenutniAtribut.append(trenutniZnak);
            }
        }

        rezultat.add(ocistiAtribut(trenutniAtribut.toString()));

        return rezultat.toArray(new String[0]);
    }

    public static boolean sadrziKljucneRijeci(String[] atributi, String[] kljucneRijeci) {
        if (atributi == null || atributi.length == 0 || kljucneRijeci == null) {
            return false;
        }

        for (String atribut : atributi) {
            if (atribut == null) continue;
            String ociscenAtribut = ocistiAtribut(atribut).toLowerCase();

            for (String kljucnaRijec : kljucneRijeci) {
                if (kljucnaRijec != null && ociscenAtribut.contains(kljucnaRijec.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean jeBroj(String vrijednost) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(vrijednost.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void ispisiHeaderInfo(String nazivDatoteke, boolean imaHeader,
                                        int ocekivaniBrojAtributa, int pronadjeniBrojAtributa) {
        if (imaHeader) {
            if (pronadjeniBrojAtributa != ocekivaniBrojAtributa) {
                System.err.println("UPOZORENJE: Header redak u datoteci " + nazivDatoteke +
                        " ima neispravan broj atributa. Očekivano " + ocekivaniBrojAtributa +
                        ", pronađeno " + pronadjeniBrojAtributa);
            }
        } else {
            System.out.println("INFO: Datoteka " + nazivDatoteke +
                    " nema informativan redak (header). Započinjem s obradom podataka od prvog retka.");
        }
    }

    public static void ispisiGreskaBrojaAtributa(int brojRetka, String nazivDatoteke,
                                                  int ocekivaniBrojAtributa, int pronadjeniBrojAtributa) {
        System.err.println("Greška pri obradi retka " + brojRetka +
                " u datoteci " + nazivDatoteke + ": Očekivano " + ocekivaniBrojAtributa +
                " atributa, pronađeno " + pronadjeniBrojAtributa);
    }
}
