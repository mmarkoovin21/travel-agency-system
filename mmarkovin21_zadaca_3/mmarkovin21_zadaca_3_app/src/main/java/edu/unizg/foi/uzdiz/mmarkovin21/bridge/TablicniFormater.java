package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

public interface TablicniFormater {

    int[] definirajSirineKolona();

    String[] kreirajZaglavlje();

    boolean[] definirajBrojcanaPolja();

    String[] formatirajRed(Object podatak);
}
    