package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

public class DetaljiRed {
    private final String atribut;
    private final String vrijednost;
    private final boolean vrijednostJeBrojcana;

    public DetaljiRed(String atribut, String vrijednost, boolean vrijednostJeBrojcana) {
        this.atribut = atribut;
        this.vrijednost = vrijednost != null ? vrijednost : "";
        this.vrijednostJeBrojcana = vrijednostJeBrojcana;
    }

    public String dohvatiAtribut() {
        return atribut;
    }

    public String dohvatiVrijednost() {
        return vrijednost;
    }

    public boolean jeVrijednostBrojcana() {
        return vrijednostJeBrojcana;
    }
}
