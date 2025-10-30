package edu.unizg.foi.uzdiz.mmarkovin21.validatori;

import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Validator {
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

    Long validirajLong(String vrijednost, String imePolja, boolean obavezan) {
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

    LocalDate validirajDatum(String vrijednost, String imePolja, boolean obavezan) {
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

    LocalTime validirajVrijeme(String vrijednost, String imePolja, boolean obavezan) {
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
