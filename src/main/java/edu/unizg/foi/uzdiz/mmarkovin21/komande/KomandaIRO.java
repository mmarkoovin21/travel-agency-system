package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterTablice;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;

import java.util.List;

public class KomandaIRO implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaIRO(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        String ime;
        String prezime;

        if (parametri.length == 3) {
            ime = parametri[1];
            prezime = parametri[2];

            if (!ime.matches("[a-zA-ZčćžšđČĆŽŠĐ]+") || !prezime.matches("[a-zA-ZčćžšđČĆŽŠĐ]+")) {
                System.out.println("Greška: Ime i prezime moraju sadržavati samo slova.");
                return;
            }
        } else {
            prezime = "";
            ime = "";
            System.out.println("Greška: Neispravan broj parametara. Očekivano: IRO <ime> <prezime>");
            return;
        }

        FormaterTablice tablica = new FormaterTablice(new int[]{15, 15, 15, 15});

        tablica.dodajRed("Datum i vrijeme",
                "Oznaka aranžmana", "Naziv aranžmana", "Vrsta");

        List<Rezervacija> rezervacijeKlijenta = agencija.dohvatiRezervacije().stream()
                .filter(r -> r.dohvatiIme().equalsIgnoreCase(ime)
                        && r.dohvatiPrezime().equalsIgnoreCase(prezime))
                .toList();

        if (rezervacijeKlijenta.isEmpty()) {
            System.out.println("Nema rezervacija za klijenta " + ime + " " + prezime);
            return;
        }

        for (var rezervacija : rezervacijeKlijenta) {
            Aranzman aranzman = agencija.dohvatiAranzmane().stream()
                    .filter(a -> a.dohvatiOznaka() == rezervacija.dohvatiOznakaAranzmana())
                    .findFirst()
                    .orElse(null);

            if (aranzman != null) {
                tablica.dodajRed(
                        PretvaracDatuma.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemePrijema()),
                        String.valueOf(rezervacija.dohvatiOznakaAranzmana()),
                        aranzman.dohvatiNaziv(),
                        rezervacija.dohvatiStanje()
                );
            }
        }
        System.out.println(tablica.formatiraj());
    }
}
