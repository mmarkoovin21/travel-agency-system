package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDateTime;

public class ValidatorNovihRezervacija {
    public Rezervacija validiraj(String[] atributi) {
        try {
            String ime = validirajString(atributi[0], "Ime", true);
            String prezime = validirajString(atributi[1], "Prezime", true);
            int oznaka = validirajInt(atributi[2], "Oznaka aranžmana", true);
            LocalDateTime datumIVrijemePrijema = validirajDatumVrijeme(atributi[3], "Datum i vrijeme prijema", true);

            return new Rezervacija(
                ime,
                prezime,
                oznaka,
                datumIVrijemePrijema,
                "NOVA"
            );

        } catch (IllegalArgumentException e) {
            System.err.println("Greška: " + e.getMessage());
            return null;
        }
    }

    String validirajString(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezan!");
            }
            return null;
        }
        return vrijednost.trim();
    }

    int validirajInt(String vrijednost, String imePolja, boolean obavezan) {
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
    LocalDateTime validirajDatumVrijeme(String vrijednost, String imePolja, boolean obavezan) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            if (obavezan) {
                throw new IllegalArgumentException(imePolja + " je obavezno polje!");
            }
        }
        LocalDateTime datumVrijeme = PretvaracDatuma.parsirajDatumVrijeme(vrijednost);
        if (datumVrijeme == null && obavezan) {
            throw new IllegalArgumentException("Nevažeći format datuma i vremena za " + imePolja + ": " + vrijednost);
        }
        return datumVrijeme;
    }
}
