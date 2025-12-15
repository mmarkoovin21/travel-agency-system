package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

public class KomandaIRO implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaIRO(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        String ime;
        String prezime;

        if (parametri.length != 3) {
            System.out.println("Greška: Neispravan broj parametara. Očekivano: IRO <ime> <prezime>");
            return;
        }

        ime = parametri[1];
        prezime = parametri[2];

        if (!ValidatorKomandi.validirajImeIliPrezime(ime) || !ValidatorKomandi.validirajImeIliPrezime(prezime)) {
            System.out.println("Greška: Ime i prezime moraju sadržavati samo slova.");
            return;
        }


    }
}
