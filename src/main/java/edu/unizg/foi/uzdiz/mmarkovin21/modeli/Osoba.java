package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.util.ArrayList;
import java.util.List;

public class Osoba {
    private String ime;
    private String prezime;
    private List <Rezervacija> rezervacije;

    public Osoba(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
        this.rezervacije = new ArrayList<>();
    }

    // Getteri i setteri
}