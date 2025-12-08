package edu.unizg.foi.uzdiz.mmarkovin21.validatori;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.ValidiraniPodaci;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorAranzmana extends Validator {
    public ValidiraniPodaci validiraj(String[] atributi) {
        try {
            int oznaka = validirajInt(atributi[0], "Oznaka", true);
            String naziv = validirajString(atributi[1], "Naziv", true);
            String program = validirajString(atributi[2], "Program", true);

            LocalDate pocetniDatum = validirajDatum(atributi[3], "Početni datum", true);
            LocalDate zavrsniDatum = validirajDatum(atributi[4], "Završni datum", true);
            LocalTime vrijemeKretanja = validirajVrijeme(atributi[5], "Vrijeme kretanja", false);
            LocalTime vrijemePovratka = validirajVrijeme(atributi[6], "Vrijeme povratka", false);

            Long cijenaPoOsobi = validirajLong(atributi[7], "Cijena", true);
            int minBrojPutnika = validirajInt(atributi[8], "Min broj putnika", true);
            int maxBrojPutnika = validirajInt(atributi[9], "Maks broj putnika", true);
            int brojNocenja = validirajInt(atributi[10], "Broj noćenja", false);
            Long doplataZaJednokrevetnuSobu = validirajLong(atributi[11], "Doplata", false);
            List<String> prijevoz = parsirajPrijevoz(atributi[12]);
            int brojDorucaka = validirajInt(atributi[13], "Broj doručaka", false);
            int brojRucakova = validirajInt(atributi[14], "Broj ručkova", false);
            int brojVecera = validirajInt(atributi[15], "Broj večera", false);

            return new ValidiraniPodaci(
                    oznaka, naziv, program, pocetniDatum, zavrsniDatum,
                    minBrojPutnika, maxBrojPutnika, cijenaPoOsobi,
                    vrijemeKretanja, vrijemePovratka, brojNocenja,
                    doplataZaJednokrevetnuSobu, prijevoz,
                    brojDorucaka, brojRucakova, brojVecera
            );
        } catch (Exception e) {
            System.err.println("Greška pri validaciji: " + e.getMessage());
            return null;
        }
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
