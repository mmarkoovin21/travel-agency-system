package edu.unizg.foi.uzdiz.mmarkovin21.validatori;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValidatorRezervacija extends Validator {

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
                    ""
            );
        } catch (IllegalArgumentException e) {
            System.err.println("Greška: " + e.getMessage());
            return null;
        }
    }

}
