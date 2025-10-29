package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanDirektor;
import edu.unizg.foi.uzdiz.mmarkovin21.graditelji.AranzmanGraditelj;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.ValidiraniPodaci;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorAranzmana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CitacAranzmana {
    private List<Aranzman> aranzmani = new ArrayList<>();
    private ValidatorAranzmana validator = new ValidatorAranzmana();
    private AranzmanDirektor direktor = new AranzmanDirektor(new AranzmanGraditelj());

    public List<Aranzman> ucitaj(String nazivDatoteke) {

        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (prviRedak) {
                    prviRedak = false;
                    continue;
                }
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                try {
                    String[] atributi = parsirajRedak(linija);
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
