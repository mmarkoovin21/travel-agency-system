package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterRezervacijaZaOsobu;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2]);

        List<Rezervacija> rezervacije = agencija.dohvatiPodatke()
                .stream()
                .flatMap(aranzman -> aranzman.dohvatiDjecu().stream())
                .filter(k -> k instanceof Rezervacija)
                .map(k -> (Rezervacija) k)
                .filter(r -> r.dohvatiIme().equalsIgnoreCase(ime) && r.dohvatiPrezime().equalsIgnoreCase(prezime))
                .toList();

        boolean imaOdgodjenih = rezervacije.stream().anyMatch(r -> r.dohvatiStanjeString().equalsIgnoreCase("ODGOĐENA"));
        IspisivacRezervacija ispisivac = new IspisivacRezervacija(new FormaterRezervacijaZaOsobu(imaOdgodjenih));


        if (rezervacije.isEmpty()) {
            System.out.println("Nema rezervacija za korisnika: " + ime + " " + prezime);
        } else {
            Comparator<Rezervacija> comparator = Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema);
            if (KonfiguracijaIspisa.dohvatiInstancu().jeObrnutoSortiranje()) {
                comparator = comparator.reversed();
            }
            rezervacije = rezervacije.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            ispisivac.ispisiSNaslovom(rezervacije, "REZERVACIJE ZA OSOBU");
        }
    }
}
