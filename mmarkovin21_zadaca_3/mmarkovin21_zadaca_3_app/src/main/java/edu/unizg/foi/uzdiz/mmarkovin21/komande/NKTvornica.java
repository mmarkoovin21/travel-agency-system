
package edu.unizg.foi.uzdiz.mmarkovin21.komande;

public class NKTvornica extends KomandaTvornica {
    private final String nazivKomande;

    public NKTvornica(String nazivKomande) {
        this.nazivKomande = nazivKomande;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new NepoznataKomanda(nazivKomande);
    }
}