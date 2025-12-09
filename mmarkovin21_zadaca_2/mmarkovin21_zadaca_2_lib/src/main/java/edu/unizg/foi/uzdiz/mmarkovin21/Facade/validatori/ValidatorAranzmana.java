package edu.unizg.foi.uzdiz.mmarkovin21.Facade.validatori;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ValidatorAranzmana extends Validator {
    public Map<String, String> validiraj(String[] atributi) {
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
            String prijevoz =atributi[12];
            int brojDorucaka = validirajInt(atributi[13], "Broj doručaka", false);
            int brojRucakova = validirajInt(atributi[14], "Broj ručkova", false);
            int brojVecera = validirajInt(atributi[15], "Broj večera", false);

            Map<String, String> aranzmani = new HashMap<>();
            aranzmani.put("oznaka", String.valueOf(oznaka));
            aranzmani.put("naziv", naziv);
            aranzmani.put("program", program);
            aranzmani.put("pocetniDatum", pocetniDatum.toString());
            aranzmani.put("zavrsniDatum", zavrsniDatum.toString());
            aranzmani.put("vrijemeKretanja", vrijemeKretanja != null ? vrijemeKretanja.toString() : "");
            aranzmani.put("vrijemePovratka", vrijemePovratka != null ? vrijemePovratka.toString() : "");
            aranzmani.put("cijenaPoOsobi", String.valueOf(cijenaPoOsobi));
            aranzmani.put("minBrojPutnika", String.valueOf(minBrojPutnika));
            aranzmani.put("maxBrojPutnika", String.valueOf(maxBrojPutnika));
            aranzmani.put("brojNocenja", String.valueOf(brojNocenja));
            aranzmani.put("doplataZaJednokrevetnuSobu", String.valueOf(doplataZaJednokrevetnuSobu));
            aranzmani.put("prijevoz", prijevoz);
            aranzmani.put("brojDorucaka", String.valueOf(brojDorucaka));
            aranzmani.put("brojRucakova", String.valueOf(brojRucakova));
            aranzmani.put("brojVecera", String.valueOf(brojVecera));

            return aranzmani;
        } catch (Exception e) {
            System.err.println("Greška pri validaciji: " + e.getMessage());
            return null;
        }
    }
//    private String parsirajPrijevoz(String prijevozString) {
//        if (prijevozString == null || prijevozString.trim().isEmpty()) {
//            return "";
//        }
//        return Arrays.stream(prijevozString.split(";"))
//                .map(String::trim)
//                .filter(s -> !s.isEmpty())
//                .collect(Collectors.joining(";"));
//    }
}
