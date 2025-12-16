package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

public class DetaljiRed {
    private final String atribut;
    private final String vrijednost;

    public DetaljiRed(String atribut, String vrijednost) {
        this.atribut = atribut;
        this.vrijednost = vrijednost != null ? vrijednost : "";
    }

    public String dohvatiAtribut() {
        return atribut;
    }

    public String dohvatiVrijednost() {
        return vrijednost;
    }
}
