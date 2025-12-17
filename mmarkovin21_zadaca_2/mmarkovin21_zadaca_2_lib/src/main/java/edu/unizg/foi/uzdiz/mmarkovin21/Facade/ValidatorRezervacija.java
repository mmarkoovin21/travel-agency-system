package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRezervacija extends Validator {

    public Map<String, Object> validiraj(String[] atributi, SkupljacGresaka skupljacGresaka, int brojRetka) {
        try {
            String ime = validirajString(atributi[0], "Ime", true);
            String prezime = validirajString(atributi[1], "Prezime", true);
            int oznaka = validirajInt(atributi[2], "Oznaka aranžmana", true);
            LocalDateTime datumIVrijemePrijema = validirajDatumVrijeme(atributi[3], "Datum i vrijeme prijema", true);

            Map<String, Object> rezervacija = new HashMap<>();
            rezervacija.put("ime", ime);
            rezervacija.put("prezime", prezime);
            rezervacija.put("oznaka", oznaka);
            rezervacija.put("datumIVrijemePrijema", datumIVrijemePrijema);
            rezervacija.put("stanje", "NOVA");
            return rezervacija;

        } catch (IllegalArgumentException e) {
            skupljacGresaka.dodajGresku(brojRetka, e.getMessage());
            return null;
        }
    }

}
