package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

public class KomandaORTA implements Komanda {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana mediator;

    public KomandaORTA(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana mediator) {
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

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2] + " " + parametri[3]);

        boolean jeOtkazana = mediator.otkaziRezervaciju(ime, prezime, oznakaAranzmana);

        if (jeOtkazana) {
            System.out.println("Otkazana rezervacija " + ime + " " + prezime + " za turistički aranžman " + oznakaAranzmana + ".");
        } else {
            System.out.println("Nije pronađena rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + ".");
        }
    }
}
