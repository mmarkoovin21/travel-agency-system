package edu.unizg.foi.uzdiz.mmarkovin21.Facade;

public class Greska {
    private final int redniBroj;
    private final int brojRetka;
    private final String opisGreske;

    public Greska(int redniBroj, int brojRetka, String opisGreske) {
        this.redniBroj = redniBroj;
        this.brojRetka = brojRetka;
        this.opisGreske = opisGreske;
    }

    public void ispisi() {
        System.out.println(" Greška broj " + redniBroj + ": " + opisGreske + " ");
        System.out.println("Redak u datoteci: " + brojRetka);
    }
}
