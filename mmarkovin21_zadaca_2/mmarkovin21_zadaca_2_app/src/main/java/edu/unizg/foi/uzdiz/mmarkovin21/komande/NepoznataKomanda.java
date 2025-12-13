// Nova klasa - NepoznataKomanda.java
package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class NepoznataKomanda implements Komanda {
    private final String nazivKomande;

    public NepoznataKomanda(String nazivKomande) {
        this.nazivKomande = nazivKomande;
    }

    @Override
    public void izvrsi(String[] parametri) {
        System.out.println("Greška: Nepoznata komanda " + nazivKomande);
    }
}