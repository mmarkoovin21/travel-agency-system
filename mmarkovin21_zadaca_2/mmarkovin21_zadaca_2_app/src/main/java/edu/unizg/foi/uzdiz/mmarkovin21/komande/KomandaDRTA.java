package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.observer.UpraviteljStanjaAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorNovihRezervacija;
//import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

import java.time.LocalDateTime;

public class KomandaDRTA implements Komanda {
    private final TuristickaAgencija agencija;
    private final UpraviteljStanjaAranzmana upraviteljStanja;
    private final ValidatorNovihRezervacija validator;

    public KomandaDRTA(TuristickaAgencija agencija, UpraviteljStanjaAranzmana upraviteljStanja,
                       ValidatorNovihRezervacija validator) {
        this.agencija = agencija;
        this.upraviteljStanja = upraviteljStanja;
        this.validator = validator;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 6) {
            System.out.println("Greška: Neispravan broj parametara za komandu DRTA. Očekivano DRTA <ime> <prezime> <oznaka> <datum> <vrijeme>.");
            return;
        }

        String ime = parametri[1];
        String prezime = parametri[2];

        if (!ValidatorKomandi.validirajImeIliPrezime(ime) || !ValidatorKomandi.validirajImeIliPrezime(prezime)) {
            System.out.println("Greška: Ime i prezime moraju sadržavati samo slova.");
            return;
        }

        Integer oznakaAranzmana = ValidatorKomandi.parsirajIValidirajOznakuAranzmana(parametri[3]);
        if (oznakaAranzmana == null) {
            return;
        }

        String datumVrijemeString = parametri[4] + " " + parametri[5];
        LocalDateTime datumVrijeme = PretvaracTipovaPodataka.parsirajDatumVrijeme(datumVrijemeString);

        if (datumVrijeme == null) {
            System.out.println("Greška: Neispravan format datuma/vremena. Očekivani format: DD.MM.YYYY HH:MM:SS (npr. 01.01.2010 01:00:01)");
            return;
        }

        String[] atributi = new String[] {ime, prezime, String.valueOf(oznakaAranzmana), datumVrijemeString};
        Rezervacija novaRezervacija = validator.validiraj(atributi);

        if (novaRezervacija == null) {
            System.out.println("Greška: Neispravni podaci za rezervaciju.");
            return;
        }

//        boolean jeDodana = upraviteljStanja.dodajRezervaciju(novaRezervacija);
//        if (jeDodana) {
//            System.out.println("Dodana rezervacija " + ime + " " + prezime + " za turistički aranžman s oznakom " +
//                    oznakaAranzmana + " u " + PretvaracDatuma.formatirajDatumVrijeme(datumVrijeme) + ".");
//        } else {
//            System.out.println("Greška: Rezervacija nije dodana!");
//        }
    }
}
