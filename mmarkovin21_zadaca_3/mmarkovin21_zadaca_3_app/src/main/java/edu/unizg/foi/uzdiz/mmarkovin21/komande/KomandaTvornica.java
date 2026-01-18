package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;

public abstract class KomandaTvornica {
    private static TuristickaAgencija agencija;
    private static ValidatorNovihRezervacija validator;

    public abstract Komanda kreirajKomandu();

    public static KomandaTvornica dohvatiFactory(String naziv, UpraviteljRezervacijaIAranzmana upravitelj) {
        if (agencija == null) {
            agencija = TuristickaAgencija.dohvatiInstancu();
            validator = new ValidatorNovihRezervacija();
        }

        return switch (naziv) {
            case "ITAK" -> new ITAKTvornica(agencija);
            case "ITAP" -> new ITAPTvornica(agencija);
            case "ITAS" -> new ITASTvornica(agencija);
            case "IRTA" -> new IRTATvornica(agencija);
            case "IRO" -> new IROTvornica(agencija);
            case "IROS" -> new IROSTvornica(agencija);
            case "ORTA" -> new ORTATvornica(agencija, upravitelj);
            case "DRTA" -> new DRTATvornica(agencija, upravitelj, validator);
            case "OTA" -> new OTATvornica(agencija, upravitelj);
            case "BP" -> new BPTvornica(agencija, upravitelj);
            case "IP" -> new IPTvornica();
            case "UP" -> new UPTvornica();
            case "PPTAR" -> new PPTARTvornica(agencija);
            case "PSTAR" -> new PSTARTvornica(agencija);
            case "Q" -> new QTvornica();
            default -> new NKTvornica(naziv);
        };
    }
}
