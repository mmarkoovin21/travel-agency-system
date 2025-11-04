package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.StanjeRezervacije;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterTablice;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.RezervacijaFilter;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.Validator;

import java.util.Comparator;
import java.util.List;

public class KomandaIRTA implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaIRTA(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 3) {
            System.out.println("Greška: Neispravan broj parametara za komandu IRTA. Očekivano IRTA <oznaka> [PA|Č|O|PAČO].");
            return;
        }

        Integer oznaka = Validator.parsirajIValidirajOznaku(parametri[1], "IRTA");
        if (oznaka == null) {
            return;
        }

        String stanjeRezervacije = parametri[2];

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
            if (prikaziDatumOtkaza) {
                String datumOtkaza = rez.dohvatiStanje() == StanjeRezervacije.OTKAZANA
                        ? PretvaracDatuma.formatirajDatumVrijeme(rez.dohvatiDatumVrijemeOtkazivanja())
                        : "";
                tablica.dodajRed(
                        rez.dohvatiIme(),
                        rez.dohvatiPrezime(),
                        PretvaracDatuma.formatirajDatumVrijeme(rez.dohvatiDatumVrijemePrijema()),
                        rez.dohvatiStanjeString(),
                        datumOtkaza
                );
            } else {
                tablica.dodajRed(
                        rez.dohvatiIme(),
                        rez.dohvatiPrezime(),
                        PretvaracDatuma.formatirajDatumVrijeme(rez.dohvatiDatumVrijemePrijema()),
                        rez.dohvatiStanjeString()
                );
            }
        }

        System.out.println(tablica.formatiraj());

    }

    private List<Rezervacija> vratiRezervacijeSaStanjem(String stanje, int oznakaAranzmana) {
        List<Rezervacija> rezervacije = agencija.dohvatiRezervacije();

        return switch (stanje) {
            case "PA" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.primljenIliAktivna())
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            case "Č" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.naCekanju())
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            case "PAČO" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            default -> List.of();
        };
    }

}
