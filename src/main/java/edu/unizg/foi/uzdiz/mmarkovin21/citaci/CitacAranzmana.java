package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.TuristickiAranzman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitacAranzmana {
    public List<TuristickiAranzman> ucitaj(String nazivDatoteke) {
        List<TuristickiAranzman> aranzmani = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;

            while ((linija = br.readLine()) != null) {
                if (prviRedak) {
                    prviRedak = false;
                    continue;
                }

                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                // Parsiranje CSV-a
                String[] dijelovi = linija.split(",");
                TuristickiAranzman aranzman = new TuristickiAranzman.Builder()
                        .setOznaka(dijelovi[0])
                        .setNaziv(dijelovi[1])
                        // ... ostali parametri
                        .build();

                aranzmani.add(aranzman);
            }
        } catch (Exception e) {
            System.err.println("Greška: " + e.getMessage());
        }
        return aranzmani;
    }
}
