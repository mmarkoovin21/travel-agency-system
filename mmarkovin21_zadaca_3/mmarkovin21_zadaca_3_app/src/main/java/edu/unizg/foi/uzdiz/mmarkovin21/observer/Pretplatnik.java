package edu.unizg.foi.uzdiz.mmarkovin21.observer;

public class Pretplatnik implements Observer {

    private final String ime;
    private final String prezime;
    private final int oznakaAranzmana;

    public Pretplatnik(String ime, String prezime, int oznakaAranzmana) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaAranzmana = oznakaAranzmana;
    }

    @Override
    public void azuriraj(String poruka) {
        System.out.println("=== OBAVIJEST ===");
        System.out.println("Pretplatnik: " + ime + " " + prezime);
        System.out.println("Aranžman: " + oznakaAranzmana);
        System.out.println("Promjena: " + poruka);
        System.out.println("=================");
    }

    @Override
    public String dohvatiIme() {
        return ime;
    }

    @Override
    public String dohvatiPrezime() {
        return prezime;
    }

    @Override
    public int dohvatiOznakaAranzmana() {
        return oznakaAranzmana;
    }
}
