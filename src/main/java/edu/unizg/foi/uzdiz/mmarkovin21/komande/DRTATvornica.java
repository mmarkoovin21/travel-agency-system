package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class DRTATvornica extends KomandaTvornica {
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaDRTA();
    }
}
