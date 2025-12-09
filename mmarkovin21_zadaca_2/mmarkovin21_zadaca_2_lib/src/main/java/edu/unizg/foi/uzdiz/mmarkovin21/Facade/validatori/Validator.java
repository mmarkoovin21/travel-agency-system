package edu.unizg.foi.uzdiz.mmarkovin21.Facade.validatori;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.PretvaracDatumaIVremena;

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
        LocalDate datum = PretvaracDatumaIVremena.parsirajDatum(vrijednost);
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
        LocalTime vrijeme = PretvaracDatumaIVremena.parsirajVrijeme(vrijednost);
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
        LocalDateTime datumVrijeme = PretvaracDatumaIVremena.parsirajDatumVrijeme(vrijednost);
        if (datumVrijeme == null && obavezan) {
            throw new IllegalArgumentException("Nevažeći format datuma i vremena za " + imePolja + ": " + vrijednost);
        }
        return datumVrijeme;
    }

    public static Integer parsirajOznakuAranzmana(String parametar) {
        if (parametar == null || parametar.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(parametar.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean validirajImeIliPrezime(String vrijednost) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            return false;
        }
        return vrijednost.matches("[a-zA-ZčćžšđČĆŽŠĐ]+");
    }

    public static Integer parsirajIValidirajOznaku(String parametar, String nazivKomande) {
        Integer oznaka = parsirajOznakuAranzmana(parametar);
        if (oznaka == null) {
            System.out.println("Greška: Neispravan format oznake aranžmana. Oznaka mora biti cijeli broj.");
        }
        return oznaka;
    }

}
