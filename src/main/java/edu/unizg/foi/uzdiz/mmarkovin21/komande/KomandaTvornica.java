package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public abstract class KomandaTvornica {
    public abstract Komanda kreirajKomandu();

    public static KomandaTvornica dohvatiFactory(String naziv) {
        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica();
            case "ITAP" -> new ITAPTvornica();
            case "IRTA" -> new IRTATvornica();
            case "IRO" -> new IROTvornica();
            case "Q" -> new QTvornica();
            default -> null;
        };
    }
}
