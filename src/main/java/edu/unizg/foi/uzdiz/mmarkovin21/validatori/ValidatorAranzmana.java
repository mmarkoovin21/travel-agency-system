package edu.unizg.foi.uzdiz.mmarkovin21.validatori;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.ValidiraniPodaci;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorAranzmana {
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
            System.err.println("Greška pri validaciji retka : " + e.getMessage());
            return null;
        }
    }

    private String validirajString(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return null;
        }
        return vrijednost.trim();
    }

    private int validirajInt(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return 0;
        }
        try {
            return Integer.parseInt(vrijednost.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nevažeća vrijednost za " + imePolja + ": " + vrijednost);
        }
    }

    private Long validirajLong(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return null;
        }
        try {
            return Long.parseLong(vrijednost.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nevažeća vrijednost za " + imePolja + ": " + vrijednost);
        }
    }

    private LocalDate validirajDatum(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return null;
        }
        LocalDate datum = PretvaracDatuma.parsirajDatum(vrijednost);
        if (datum == null && obavezan) {
            throw new IllegalArgumentException("Nevažeći format datuma za " + imePolja + "!: " + vrijednost);
        }
        return datum;
    }

    private LocalTime validirajVrijeme(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return null;
        }
        LocalTime vrijeme = PretvaracDatuma.parsirajVrijeme(vrijednost);
        if (vrijeme == null && obavezan) {
            throw new IllegalArgumentException("Nevažeći format vremena za " + imePolja + ": " + vrijednost);
        }
        return vrijeme;
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
