package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;

/**
 * Simple Factory pattern za kreiranje komandi.
 * Eliminira potrebu za konkretnim tvorničkim klasama.
 */
public class KomandaTvornica {

    private KomandaTvornica() {
        // Privatni konstruktor - sprječava instanciranje
    }

    /**
     * Kreira odgovarajuću komandu na temelju naziva.
     *
     * @param naziv naziv komande (ITAK, ITAP, IRTA, IRO, Q)
     * @return instanca komande ili null ako komanda nije prepoznata
     */
    public static Komanda kreirajKomandu(String naziv) {
        TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();

        return switch (naziv) {
            case "ITAK" -> new KomandaITAK(agencija);
            case "ITAP" -> new KomandaITAP(agencija);
            case "IRTA" -> new KomandaIRTA(agencija);
            case "IRO" -> new KomandaIRO(agencija);
            case "Q" -> new KomandaQ(agencija);
            default -> null;
        };
    }
}
