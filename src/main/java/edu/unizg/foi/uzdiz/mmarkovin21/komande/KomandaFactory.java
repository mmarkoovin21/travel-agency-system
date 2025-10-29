package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public abstract class KomandaFactory {
    public abstract Komanda kreirajKomandu();

    public static KomandaFactory dohvatiFactory(String naziv) {
        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica();
            case "ITAP" -> new ITAPTvornica();
            case "Q" -> new QTvornica();
            default -> null;
        };
    }
}
