package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CitacAranzmana {
//    private final ValidatorAranzmana validatorAranzmana = new ValidatorAranzmana();
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.mm.yyyy.");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.mm.yyyy. hh:mm:ss");

    public List<Aranzman> ucitaj(String nazivDatoteke) {
        List<Aranzman> aranzmani = new ArrayList<>();
        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            // provjera prvog retka (zaglavlje)
            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (prviRedak) {
                    prviRedak = false;
                    continue;
                }
                // provjera komentara i praznih linija
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }
                String[] atributi = parsirajRedak(linija);
                Arrays.stream(atributi)
                        .forEach(atribut -> System.out.println("  - " + atribut));
                List<String> prijevozLista = parsirajPrijevoz(atributi[12]);
                System.out.println("Prijevoz lista: " + prijevozLista);
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
            e.printStackTrace();
        }
        return aranzmani;
    }

    public String[] parsirajRedak(String redak) {
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

    private String ocistiAtribut(String atribut) {
        String ociscen = atribut.trim();

        if (ociscen.startsWith("\"") && ociscen.endsWith("\"")) {
            ociscen = ociscen.substring(1, ociscen.length() - 1);
        }

        return ociscen;
    }

    private List<String> parsirajPrijevoz(String prijevozString) {
        if (prijevozString == null || prijevozString.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(prijevozString.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
