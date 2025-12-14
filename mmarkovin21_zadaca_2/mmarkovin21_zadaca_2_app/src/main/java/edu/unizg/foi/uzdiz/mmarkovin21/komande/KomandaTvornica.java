package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.observer.UpraviteljStanjaAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public abstract class KomandaTvornica {
    private static TuristickaAgencija agencija;
    private static UpraviteljStanjaAranzmana upraviteljStanja;
    private static ValidatorNovihRezervacija validator;

    public abstract Komanda kreirajKomandu();

    public static KomandaTvornica dohvatiFactory(String naziv) {
        if (agencija == null) {
            agencija = TuristickaAgencija.dohvatiInstancu();
            upraviteljStanja = UpraviteljStanjaAranzmana.dohvatiInstancu();
            validator = new ValidatorNovihRezervacija();
        }

        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica(agencija);
            case "ITAP" -> new ITAPTvornica(agencija);
            case "IRTA" -> new IRTATvornica(agencija);
            case "IRO" -> new IROTvornica(agencija);
            case "ORTA" -> new ORTATvornica(agencija, upraviteljStanja);
            case "DRTA" -> new DRTATvornica(agencija, upraviteljStanja, validator);
            case "UP" -> new UPTvornica(agencija);
            case "Q" -> new QTvornica();
            default -> new NKTvornica(naziv);
        };
    }
}
