package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import edu.unizg.foi.uzdiz.mmarkovin21.Facade.citaci.CitacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.Facade.citaci.CitacRezervacija;

import java.util.List;
import java.util.Map;

public class UcitaniPodaciFacade {
    private final CitacAranzmana citacAranzmana;
    private final CitacRezervacija citacRezervacija;
    private List<Map<String, String>> aranzmani;
    private List<Map<String, String>> rezervacije;

    public UcitaniPodaciFacade() {
        this.citacAranzmana = new CitacAranzmana();
        this.citacRezervacija = new CitacRezervacija();
    }

    public void ucitajAranzmane(String putanjaDatoteke) {
        this.aranzmani = citacAranzmana.ucitaj(putanjaDatoteke);
    }

    public void ucitajRezervacije(String putanjaDatoteke) {
        this.rezervacije = citacRezervacija.ucitajRezervacije(putanjaDatoteke);
    }

    public List<Map<String, String>> dohvatiAranzmane() {
        return aranzmani;
    }

    public List<Map<String, String>> dohvatiRezervacije() {
        return rezervacije;
    }
}

