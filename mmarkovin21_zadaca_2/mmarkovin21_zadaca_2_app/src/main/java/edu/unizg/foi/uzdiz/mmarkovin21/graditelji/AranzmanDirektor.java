package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

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

    public Aranzman konstruiraj(Map<String, String> podaci) {
        graditelj.izgradiAranzman(
                // Ovjde ce se mozda rusiti apklikacija ako su podaci losi
                Integer.parseInt(podaci.get("oznaka")),
                podaci.get("naziv"),
                podaci.get("program"),
                PretvaracDatuma.parsirajDatum(podaci.get("pocetniDatum")),
                PretvaracDatuma.parsirajDatum(podaci.get("zavrsniDatum")),
                Integer.parseInt(podaci.get("minBrojPutnika")),
                Integer.parseInt(podaci.get("maxBrojPutnika")),
                Long.parseLong(podaci.get("cijenaPoOsobi"))
        );

        // Dodajemo opcionalne parametre ako postoje
        if (podaci.get("vrijemeKretanja") != null) {
            graditelj.postaviVrijemeKretanja(PretvaracDatuma.parsirajVrijeme(podaci.get("vrijemeKretanja")));
        }

        if (podaci.get("vrijemePovratka") != null) {
            graditelj.postaviVrijemePovratka(PretvaracDatuma.parsirajVrijeme(podaci.get("vrijemePovratka")));
        }
        // Ovdje bi isto moglo biti problema sa parsiranjem i rušenjem aplikacije
        if (Integer.parseInt(podaci.get("brojNocenja")) > 0) {
            graditelj.postaviBrojNocenja(Integer.parseInt(podaci.get("brojNocenja")));
        }

        if (podaci.get("doplataZaJednokrevetnuSobu") != null) {
            graditelj.postaviDoplatuZaJednokrevetnuSobu(Long.parseLong(podaci.get("doplataZaJednokrevetnuSobu")));
        }

        if (podaci.get("prijevoz") != null && !podaci.get("prijevoz").isEmpty()) {
            graditelj.postaviPrijevoz(parsirajPrijevoz(podaci.get("prijevoz")));
        }

        if (Integer.parseInt(podaci.get("brojDorucaka"))> 0) {
            graditelj.postaviBrojDorucaka(Integer.parseInt(podaci.get("brojDorucaka")));
        }

        if (Integer.parseInt(podaci.get("brojRucakova")) > 0) {
            graditelj.postaviBrojRucakova(Integer.parseInt(podaci.get("brojRucakova")));
        }

        if (Integer.parseInt(podaci.get("brojVecera")) > 0) {
            graditelj.postaviBrojVecera(Integer.parseInt(podaci.get("brojVecera")));
        }

        return graditelj.getAranzman();
    }
    // ovo ćemo za sada staviti tu pa poslje možda i premjestimo
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