package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.upravitelji.UpraviteljStanjaRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

public abstract class KomandaTvornica {
    private static TuristickaAgencija agencija;
    private static UpraviteljStanjaRezervacija upraviteljStanja;
    private static ValidatorRezervacija validator;

    public abstract Komanda kreirajKomandu();

    public static KomandaTvornica dohvatiFactory(String naziv) {
        if (agencija == null) {
            agencija = TuristickaAgencija.dohvatiInstancu();
            upraviteljStanja = new UpraviteljStanjaRezervacija();
            validator = new ValidatorRezervacija();
        }

        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica(agencija);
            case "ITAP" -> new ITAPTvornica(agencija);
            case "IRTA" -> new IRTATvornica(agencija);
            case "IRO" -> new IROTvornica(agencija);
            case "ORTA" -> new ORTATvornica(agencija, upraviteljStanja);
            case "DRTA" -> new DRTATvornica(agencija, upraviteljStanja, validator);
            case "Q" -> new QTvornica();
            default -> null;
        };
    }
}
