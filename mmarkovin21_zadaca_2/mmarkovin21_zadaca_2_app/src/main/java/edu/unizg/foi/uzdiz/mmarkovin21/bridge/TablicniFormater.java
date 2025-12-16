package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

/**
 * Implementacijska strana Bridge uzorka.
 * Definira interfejs za formatiranje podataka u tablični oblik.
 */
public interface TablicniFormater {

    /**
     * Definira širine kolona tablice.
     * @return polje širina kolona
     */
    int[] definirajSirineKolona();

    /**
     * Kreira zaglavlje tablice.
     * @return polje naziva kolona
     */
    String[] kreirajZaglavlje();

    /**
     * Definira koja su polja brojčana (desno poravnanje).
     * @return polje boolean vrijednosti - true za brojčano polje, false za tekstualno
     */
    boolean[] definirajBrojcanaPolja();

    /**
     * Formatira jedan red podataka.
     * @param podatak objekt koji se formatira u red tablice
     * @return polje stringova koji predstavljaju jednu stavku u tablici
     */
    String[] formatirajRed(Object podatak);
}
