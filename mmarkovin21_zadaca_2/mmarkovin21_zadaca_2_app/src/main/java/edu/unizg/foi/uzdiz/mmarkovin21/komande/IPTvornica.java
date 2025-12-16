package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class IPTvornica extends KomandaTvornica{

    public IPTvornica( ) {
    }
    @Override
    public Komanda kreirajKomandu() {
        return new KomandaIP();
    }
}
