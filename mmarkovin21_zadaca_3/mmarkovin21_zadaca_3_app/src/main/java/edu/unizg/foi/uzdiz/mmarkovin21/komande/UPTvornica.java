package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class UPTvornica extends KomandaTvornica{

    public UPTvornica() {
    }

    @Override
    public Komanda kreirajKomandu() {
        return new KomandaUP();
    }
}
