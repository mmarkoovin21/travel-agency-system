package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ValidatorAranzmana extends Validator {
    public Map<String, Object> validiraj(String[] atributi, SkupljacGresaka skupljacGresaka, int brojRetka) {
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

            Map<String, Object> aranzmani = new HashMap<>();
            aranzmani.put("oznaka", oznaka);
            aranzmani.put("naziv", naziv);
            aranzmani.put("program", program);
            aranzmani.put("pocetniDatum", pocetniDatum);
            aranzmani.put("zavrsniDatum", zavrsniDatum);
            aranzmani.put("vrijemeKretanja", vrijemeKretanja);
            aranzmani.put("vrijemePovratka", vrijemePovratka);
            aranzmani.put("cijenaPoOsobi", cijenaPoOsobi);
            aranzmani.put("minBrojPutnika", minBrojPutnika);
            aranzmani.put("maxBrojPutnika", maxBrojPutnika);
            aranzmani.put("brojNocenja", brojNocenja);
            aranzmani.put("doplataZaJednokrevetnuSobu", doplataZaJednokrevetnuSobu);
            aranzmani.put("prijevoz", prijevoz);
            aranzmani.put("brojDorucaka", brojDorucaka);
            aranzmani.put("brojRucakova", brojRucakova);
            aranzmani.put("brojVecera", brojVecera);

            return aranzmani;
        } catch (Exception e) {
            skupljacGresaka.dodajGresku(brojRetka, e.getMessage());
            return null;
        }
    }
}
