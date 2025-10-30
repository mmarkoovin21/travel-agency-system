package edu.unizg.foi.uzdiz.mmarkovin21.upravitelji;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.util.List;

public class UpraviteljStanjaRezervacija {
    private final TuristickaAgencija agencija;

    public UpraviteljStanjaRezervacija(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    public String odrediPocetnoStanje(int oznakaAranzmana, List<Rezervacija> postojeceRezervacije) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            System.err.println("Aranžman s oznakom " + oznakaAranzmana + " nije pronađen.");
            return "";
        }

        long brojRezervacija = postojeceRezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .count() + 1;

        if (brojRezervacija > aranzman.dohvatiMaxBrojPutnika()) {
            return "na čekanju";
        } else if (brojRezervacija >= aranzman.dohvatiMinBrojPutnika()) {
            return "aktivna";
        } else {
            return "primljena";
        }
    }

    public void azurirajStanja(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            return;
        }

        long brojRezervacija = rezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .count();

        if (brojRezervacija >= aranzman.dohvatiMinBrojPutnika()) {
            rezervacije.stream()
                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                    .filter(r -> "primljena".equals(r.dohvatiStanje()))
                    .forEach(r -> r.promijeniStanje("aktivna"));
        }
    }

    private Aranzman pronadjiAranzman(int oznaka) {
        return agencija.dohvatiAranzmane()
                .stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }
}