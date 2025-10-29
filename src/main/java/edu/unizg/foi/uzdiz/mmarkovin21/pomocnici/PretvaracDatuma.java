package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PretvaracDatuma {
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

    private static final DateTimeFormatter HRVATSKI_DATUM_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private static final DateTimeFormatter HRVATSKI_VRIJEME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

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

    public static LocalTime parsirajVrijeme(String vrijemeString) {
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

    public static String formatirajDatum(LocalDate datum) {
        if (datum == null) {
            return null;
        }
        return datum.format(HRVATSKI_DATUM_FORMAT);
    }

    public static String formatirajVrijeme(LocalTime vrijeme) {
        if (vrijeme == null) {
            return null;
        }
        return vrijeme.format(HRVATSKI_VRIJEME_FORMAT);
    }
}