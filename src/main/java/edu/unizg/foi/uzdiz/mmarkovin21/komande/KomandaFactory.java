package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

public abstract class KomandaFactory {
    public abstract Komanda kreirajKomandu();

    public static KomandaFactory dohvatiFactory(String naziv) {
        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica();
            case "Q" -> new QTvornica();
            default -> null;
        };
    }
}
