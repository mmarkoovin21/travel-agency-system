package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.upravitelji.UpraviteljStanjaRezervacija;

public class KomandaORTA implements Komanda {
    TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
    UpraviteljStanjaRezervacija upraviteljStanja = new UpraviteljStanjaRezervacija();

    @Override
    public void izvrsi(String[] parametri) {
        String ime;
        String prezime;
        int oznakaAranzmana;

        if (parametri.length == 4) {
            try {
                ime = parametri[1];
                prezime = parametri[2];
                oznakaAranzmana = Integer.parseInt(parametri[3]);
            } catch (NumberFormatException e) {
                ime = "";
                prezime = "";
                oznakaAranzmana = 0;
                System.out.println("Greška: Neispravan format oznake. Oznaka mora biti cijeli broj.");
                return;
            }
        } else {
            System.out.println("Greška: Neispravan broj parametara za komandu ORTA. Očekivano ORTA <ime> <prezime> <oznaka>.");
            return;
        }

        boolean jeOtkazana = upraviteljStanja.otkazirezervaciju(ime, prezime, oznakaAranzmana);

        if (jeOtkazana) {
            System.out.println("Rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + " je uspješno otkazana.");
        } else {
            System.out.println("Nije pronađena rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + ".");
        }
    }
}
