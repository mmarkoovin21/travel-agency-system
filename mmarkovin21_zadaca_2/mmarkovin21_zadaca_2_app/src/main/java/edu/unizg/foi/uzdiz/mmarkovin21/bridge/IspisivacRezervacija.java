package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

/**
 * Konkretna apstrakcija za ispis rezervacija.
 * Dio Bridge uzorka - omogućava ispis rezervacija koristeći različite formatere.
 */
public class IspisivacRezervacija extends TablicniIspisivac {

    /**
     * Konstruktor koji prima formater za rezervacije.
     * @param formater konkretni formater (FormaterRezervacija s ili bez datuma otkazivanja)
     */
    public IspisivacRezervacija(TablicniFormater formater) {
        super(formater);
    }
}
