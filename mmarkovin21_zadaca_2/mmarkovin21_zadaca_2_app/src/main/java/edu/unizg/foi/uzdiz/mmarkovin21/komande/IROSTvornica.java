package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.decorator.DecoratorKomandaIROS;

public class IROSTvornica extends KomandaTvornica {
    private final TuristickaAgencija agencija;

    public IROSTvornica(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public Komanda kreirajKomandu() {
        return new DecoratorKomandaIROS(agencija);
    }
}
