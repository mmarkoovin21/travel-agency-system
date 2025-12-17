package edu.unizg.foi.uzdiz.mmarkovin21.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class TuristickaKomponenta {
    
    public abstract double izracunajUkupnuCijenu();

    
    public void dodajDijete(TuristickaKomponenta komponenta) {
        throw new UnsupportedOperationException(
                "Operacija nije podržana za ovaj tip komponente.");
    }

    public void ukloniDijete(TuristickaKomponenta komponenta) {
        throw new UnsupportedOperationException(
                "Operacija nije podržana za ovaj tip komponente.");
    }

    public List<TuristickaKomponenta> dohvatiDjecu() {
        return new ArrayList<>();
    }

    public boolean jeLiComposite() {
        return false;
    }
}
