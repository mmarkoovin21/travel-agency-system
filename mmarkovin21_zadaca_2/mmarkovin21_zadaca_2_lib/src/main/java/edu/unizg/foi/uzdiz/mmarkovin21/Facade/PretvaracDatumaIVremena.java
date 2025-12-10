package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PretvaracDatumaIVremena {
    private static final DateTimeFormatter[] DATUM_FORMATI = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd.MM.yyyy."),
            DateTimeFormatter.ofPattern("d.M.yyyy.")
    };

    private static final DateTimeFormatter[] VRIJEME_FORMATI = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("HH:mm:ss"),
            DateTimeFormatter.ofPattern("H:mm:ss"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("H:mm")
    };

    private static final DateTimeFormatter[] DATUM_VRIJEME_FORMATI = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy. H:mm:ss"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy. H:mm"),
            DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"),
            DateTimeFormatter.ofPattern("d.M.yyyy H:mm:ss"),
            DateTimeFormatter.ofPattern("d.M.yyyy H:mm"),
            DateTimeFormatter.ofPattern("d.M.yyyy. HH:mm:ss"),
            DateTimeFormatter.ofPattern("d.M.yyyy. HH:mm"),
            DateTimeFormatter.ofPattern("d.M.yyyy. H:mm:ss"),
            DateTimeFormatter.ofPattern("d.M.yyyy. H:mm")
    };

    public static LocalDate parsirajDatum(String datumString) {
        if (datumString == null || datumString.trim().isEmpty()) {
            return null;
        }
        String s = datumString.trim();
        for (DateTimeFormatter fmt : DATUM_FORMATI) {
            try {
                return LocalDate.parse(s, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    protected static LocalTime parsirajVrijeme(String vrijemeString) {
        if (vrijemeString == null || vrijemeString.trim().isEmpty()) {
            return null;
        }
        String s = vrijemeString.trim();
        for (DateTimeFormatter fmt : VRIJEME_FORMATI) {
            try {
                return LocalTime.parse(s, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    protected static LocalDateTime parsirajDatumVrijeme(String datumVrijemeString) {
        if (datumVrijemeString == null || datumVrijemeString.trim().isEmpty()) {
            return null;
        }
        String s = datumVrijemeString.trim();

        for (DateTimeFormatter fmt : DATUM_VRIJEME_FORMATI) {
            try {
                return LocalDateTime.parse(s, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }
}
