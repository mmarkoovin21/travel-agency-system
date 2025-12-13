package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AranzmanDirektor {
    private AranzmanGraditelj graditelj;

    public AranzmanDirektor(AranzmanGraditelj graditelj) {
        this.graditelj = graditelj;
    }

    public Aranzman konstruiraj(Map<String, Object> podaci) {
        graditelj.izgradiAranzman(
                (Integer) podaci.get("oznaka"),
                (String) podaci.get("naziv"),
                (String) podaci.get("program"),
                (LocalDate) podaci.get("pocetniDatum"),
                (LocalDate) podaci.get("zavrsniDatum"),
                (Integer) podaci.get("minBrojPutnika"),
                (Integer) podaci.get("maxBrojPutnika"),
                (Long) podaci.get("cijenaPoOsobi")
        );

        // Dodajemo opcionalne parametre ako postoje
        LocalTime vrijemeKretanja = (LocalTime) podaci.get("vrijemeKretanja");
        if (vrijemeKretanja != null) {
            graditelj.postaviVrijemeKretanja(vrijemeKretanja);
        }

        LocalTime vrijemePovratka = (LocalTime) podaci.get("vrijemePovratka");
        if (vrijemePovratka != null) {
            graditelj.postaviVrijemePovratka(vrijemePovratka);
        }

        Integer brojNocenja = (Integer) podaci.get("brojNocenja");
        if (brojNocenja != null && brojNocenja > 0) {
            graditelj.postaviBrojNocenja(brojNocenja);
        }

        Long doplata = (Long) podaci.get("doplataZaJednokrevetnuSobu");
        if (doplata != null && doplata > 0L) {
            graditelj.postaviDoplatuZaJednokrevetnuSobu(doplata);
        }

        String prijevoz = (String) podaci.get("prijevoz");
        if (prijevoz != null && !prijevoz.isEmpty()) {
            graditelj.postaviPrijevoz(parsirajPrijevoz(prijevoz));
        }

        Integer brojDorucaka = (Integer) podaci.get("brojDorucaka");
        if (brojDorucaka != null && brojDorucaka > 0) {
            graditelj.postaviBrojDorucaka(brojDorucaka);
        }

        Integer brojRucakova = (Integer) podaci.get("brojRucakova");
        if (brojRucakova != null && brojRucakova > 0) {
            graditelj.postaviBrojRucakova(brojRucakova);
        }

        Integer brojVecera = (Integer) podaci.get("brojVecera");
        if (brojVecera != null && brojVecera > 0) {
            graditelj.postaviBrojVecera(brojVecera);
        }

        return graditelj.getAranzman();
    }

    private List<String> parsirajPrijevoz(String prijevozString) {
        if (prijevozString == null || prijevozString.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(prijevozString.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}