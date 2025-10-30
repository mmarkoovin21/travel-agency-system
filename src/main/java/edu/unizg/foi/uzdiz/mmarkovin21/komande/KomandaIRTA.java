package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterTablice;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.util.List;

public class KomandaIRTA implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaIRTA(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        int oznaka = 0;
        String stanjeRezervacije = "";

        if (parametri.length == 3) {
            try {
                oznaka = Integer.parseInt(parametri[1]);
                stanjeRezervacije = parametri[2];
            } catch (NumberFormatException e) {
                System.out.println("Greška: Neispravan format oznake aranžmana. Oznaka mora biti cijeli broj.");
                return;
            }
        } else {
            System.out.println("Greška: Neispravan broj parametara za komandu IRTA. Očekivano 2 parametra.");
            return;
        }

        List<Rezervacija> rezervacije = vratiRezervacijeSaStanjem(stanjeRezervacije, oznaka);

        if (rezervacije.isEmpty()) {
            System.out.println("Nema rezervacija za zadane kriterije.");
            return;
        }

        boolean prikaziDatumOtkaza = stanjeRezervacije.equals("O") || stanjeRezervacije.equals("PAČO");
        int[] sirine = prikaziDatumOtkaza ?
                new int[]{15, 15, 20, 10, 20} :
                new int[]{15, 15, 20, 10};

        FormaterTablice tablica = new FormaterTablice(sirine);

        if (prikaziDatumOtkaza) {
            tablica.dodajRed("Ime", "Prezime", "Datum i vrijeme", "Vrsta", "Datum i vrijeme otkaza");
        } else {
            tablica.dodajRed("Ime", "Prezime", "Datum i vrijeme", "Vrsta");
        }

        for (Rezervacija rez : rezervacije) {
//            if (prikaziDatumOtkaza && rez.dohvatiStanje().equals("otkazana")) {
//                tablica.dodajRed(
//                        rez.dohvatiIme(),
//                        rez.dohvatiPrezime(),
//                        PretvaracDatuma.formatirajDatumVrijeme(rez.dohvatiDatumVrijemePrijema()),
//                        rez.dohvatiStanje(),
//                        rez.dohvatiDatumOtkaza()
//                );
//            } else {
                tablica.dodajRed(
                        rez.dohvatiIme(),
                        rez.dohvatiPrezime(),
                        PretvaracDatuma.formatirajDatumVrijeme(rez.dohvatiDatumVrijemePrijema()),
                        rez.dohvatiStanje()
                );
//            }
        }

        System.out.println(tablica.formatiraj());

    }

    private List<Rezervacija> vratiRezervacijeSaStanjem(String stanje, int oznakaAranzmana) {
        List<Rezervacija> rezervacije = agencija.dohvatiRezervacije();

        return switch (stanje) {
            case "PA" -> rezervacije.stream()
                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                    .filter(r -> r.dohvatiStanje().equals("primljena") || r.dohvatiStanje().equals("aktivna"))
                    .toList();
            case "Č" -> rezervacije.stream()
                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                    .filter(r -> r.dohvatiStanje().equals("na čekanju"))
                    .toList();
//            case "O" -> rezervacije.stream()
//                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
//                    .filter(r -> r.dohvatiStanje().equals("otkazana"))
//                    .toList();
//            case "PAČO" -> rezervacije.stream()
//                    .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
//                    .toList();
            default -> List.of();
        };
    }

}
