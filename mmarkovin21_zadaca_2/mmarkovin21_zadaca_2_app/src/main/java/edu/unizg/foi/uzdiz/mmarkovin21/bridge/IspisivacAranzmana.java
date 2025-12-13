package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

/**
 * Konkretna apstrakcija za ispis aranžmana.
 * Dio Bridge uzorka - omogućava ispis aranžmana koristeći različite formatere.
 */
public class IspisivacAranzmana extends TablicniIspisivac {

    /**
     * Konstruktor koji prima formater za aranžmane.
     * @param formater konkretni formater (FormaterListeAranzmana ili FormaterDetaljaAranzmana)
     */
    public IspisivacAranzmana(TablicniFormater formater) {
        super(formater);
    }
}
