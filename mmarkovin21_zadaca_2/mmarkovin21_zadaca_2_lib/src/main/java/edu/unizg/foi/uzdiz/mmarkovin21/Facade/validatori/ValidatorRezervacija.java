package edu.unizg.foi.uzdiz.mmarkovin21.Facade.validatori;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRezervacija extends Validator {

    public Map<String, String> validiraj(String[] atributi) {
        try {
            String ime = validirajString(atributi[0], "Ime", true);
            String prezime = validirajString(atributi[1], "Prezime", true);
            int oznaka = validirajInt(atributi[2], "Oznaka aranžmana", true);
            LocalDateTime datumIVrijemePrijema = validirajDatumVrijeme(atributi[3], "Datum i vrijeme prijema", true);

            Map<String, String> rezervacija = new HashMap<>();
            rezervacija.put("ime", ime);
            rezervacija.put("prezime", prezime);
            rezervacija.put("oznaka", String.valueOf(oznaka));
            rezervacija.put("datumIVrijemePrijema", datumIVrijemePrijema.toString());
            rezervacija.put("stanje", "NOVA");
            return rezervacija;

        } catch (IllegalArgumentException e) {
            System.err.println("Greška: " + e.getMessage());
            return null;
        }
    }

}
