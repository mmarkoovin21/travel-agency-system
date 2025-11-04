package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.upravitelji.UpraviteljStanjaRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.Validator;

public class KomandaORTA implements Komanda {
    private final TuristickaAgencija agencija;
    private final UpraviteljStanjaRezervacija upraviteljStanja;

    public KomandaORTA(TuristickaAgencija agencija, UpraviteljStanjaRezervacija upraviteljStanja) {
        this.agencija = agencija;
        this.upraviteljStanja = upraviteljStanja;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 4) {
            System.out.println("Greška: Neispravan broj parametara za komandu ORTA. Očekivano ORTA <ime> <prezime> <oznaka>.");
            return;
        }

        String ime = parametri[1];
        String prezime = parametri[2];

        Integer oznakaAranzmana = Validator.parsirajIValidirajOznaku(parametri[3], "ORTA");
        if (oznakaAranzmana == null) {
            return;
        }

        boolean jeOtkazana = upraviteljStanja.otkazirezervaciju(ime, prezime, oznakaAranzmana);

        if (jeOtkazana) {
            System.out.println("Otkazana rezervacija " + ime + " " + prezime + " za turistički aranžman " + oznakaAranzmana + ".");
        } else {
            System.out.println("Nije pronađena rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + ".");
        }
    }
}
