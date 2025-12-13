package edu.unizg.foi.uzdiz.mmarkovin21.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class TuristickaKomponenta {
    // Zajedničke operacije za sve komponente
    public abstract void ispisi(int razina);
    public abstract double izracunajUkupnuCijenu();

    // Composite operacije - default implementacija za Leaf
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
        return false;  // Override u Composite klasama
    }
}
