package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.MediatorRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

public class KomandaORTA implements Komanda {
    private final TuristickaAgencija agencija;
    private final MediatorRezervacija mediator;

    public KomandaORTA(TuristickaAgencija agencija, MediatorRezervacija mediator) {
        this.agencija = agencija;
        this.mediator = mediator;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 4) {
            System.out.println("Greška: Neispravan broj parametara za komandu ORTA. Očekivano ORTA <ime> <prezime> <oznaka>.");
            return;
        }

        String ime = parametri[1];
        String prezime = parametri[2];

        Integer oznakaAranzmana = ValidatorKomandi.parsirajIValidirajOznakuAranzmana(parametri[3]);
        if (oznakaAranzmana == null) {
            return;
        }

        boolean jeOtkazana = mediator.otkaziRezervaciju(ime, prezime, oznakaAranzmana);

        if (jeOtkazana) {
            System.out.println("Otkazana rezervacija " + ime + " " + prezime + " za turistički aranžman " + oznakaAranzmana + ".");
        } else {
            System.out.println("Nije pronađena rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + ".");
        }
    }
}
