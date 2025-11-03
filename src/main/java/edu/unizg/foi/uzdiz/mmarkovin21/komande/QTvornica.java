package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class QTvornica extends KomandaTvornica {
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaQ();
    }
}
