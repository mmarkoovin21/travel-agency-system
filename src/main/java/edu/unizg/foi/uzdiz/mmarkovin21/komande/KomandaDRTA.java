package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;
import edu.unizg.foi.uzdiz.mmarkovin21.upravitelji.UpraviteljStanjaRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

import java.time.LocalDateTime;

public class KomandaDRTA implements Komanda {
    TuristickaAgencija agencija = TuristickaAgencija.dohvatiInstancu();
    UpraviteljStanjaRezervacija upraviteljStanja = new UpraviteljStanjaRezervacija();
    ValidatorRezervacija validatorRezervacija = new ValidatorRezervacija();

    @Override
    public void izvrsi(String[] parametri) {
        String ime, prezime;
        int oznakaAranzmana;
        LocalDateTime datumVrijeme;
        String[] atributi;

        if (parametri.length == 6) {
            ime = parametri[1];
            prezime = parametri[2];
            oznakaAranzmana = Integer.parseInt(parametri[3]);

            String datumVrijemeString = parametri[4] + " " + parametri[5];
            datumVrijeme = PretvaracDatuma.parsirajDatumVrijeme(datumVrijemeString);
            atributi  = new String[] {ime, prezime, String.valueOf(oznakaAranzmana), datumVrijemeString};

            if (datumVrijeme == null) {
                System.out.println("Greška: Neispravan format datuma/vremena.");
                return;
            }
        } else {
            System.out.println("Greška: Neispravan broj parametara za komandu DRTA. Očekivano DRTA <ime> <prezime> <oznaka> <datum> <vrijeme>.");
            return;
        }
        Rezervacija novaRezervacija = validatorRezervacija.validiraj(atributi);
        if (novaRezervacija == null) {
            System.out.println("Greška: Neispravni podaci za rezervaciju.");
        }
        boolean jeDodana = upraviteljStanja.dodajRezervaciju(novaRezervacija);
        if (jeDodana) {
            System.out.println("Dodana rezervacija" + ime + " " + prezime + " za turistički aranžman s\n" +
                    "oznakom " + oznakaAranzmana + " u " + datumVrijeme + ".");
        } else {
            System.out.println("Greška: Rezervacija nije dodana!");
        }
    }

}
