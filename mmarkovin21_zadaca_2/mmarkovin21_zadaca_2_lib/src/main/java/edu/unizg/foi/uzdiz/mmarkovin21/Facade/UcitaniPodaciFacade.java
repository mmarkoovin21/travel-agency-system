package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

import java.util.List;
import java.util.Map;

public class UcitaniPodaciFacade {
    private static final CitacAranzmana citacAranzmana = new CitacAranzmana();
    private static final CitacRezervacija citacRezervacija = new CitacRezervacija();
    private static List<Map<String, Object>> aranzmani;
    private static List<Map<String, Object>> rezervacije;

    public static void ucitajAranzmane(String putanjaDatoteke) {
        aranzmani = citacAranzmana.ucitaj(putanjaDatoteke);
    }

    public static void ucitajRezervacije(String putanjaDatoteke) {
        rezervacije = citacRezervacija.ucitajRezervacije(putanjaDatoteke);
    }

    public static List<Map<String, Object>> dohvatiAranzmane() {
        return aranzmani;
    }

    public static List<Map<String, Object>> dohvatiRezervacije() {
        return rezervacije;
    }
}

