package edu.unizg.foi.uzdiz.mmarkovin21.decorator;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.Komanda;
import edu.unizg.foi.uzdiz.mmarkovin21.komande.KomandaIRO;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

import java.util.List;

public class DecoratorKomandaIROS implements Komanda {
    private final TuristickaAgencija agencija;
    private final Komanda iroKomanda;

    public DecoratorKomandaIROS(TuristickaAgencija agencija) {
        this.agencija = agencija;
        this.iroKomanda = new KomandaIRO(agencija);
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 3) {
            System.out.println("Greška: Neispravan broj parametara. Očekivano: IROS <ime> <prezime>");
            return;
        }

        String ime = parametri[1];
        String prezime = parametri[2];

        if (!ValidatorKomandi.validirajImeIliPrezime(ime) || !ValidatorKomandi.validirajImeIliPrezime(prezime)) {
            System.out.println("Greška: Ime i prezime moraju sadržavati samo slova.");
            return;
        }
        iroKomanda.izvrsi(parametri);

        List<Rezervacija> rezervacije = dohvatiRezervacije(ime, prezime);

        if (!rezervacije.isEmpty()) {
            ispisiStatistiku(rezervacije);
        }
    }

    private List<Rezervacija> dohvatiRezervacije(String ime, String prezime) {
        return agencija.dohvatiPodatke()
                .stream()
                .flatMap(aranzman -> aranzman.dohvatiDjecu().stream())
                .filter(k -> k instanceof Rezervacija)
                .map(k -> (Rezervacija) k)
                .filter(r -> r.dohvatiIme().equalsIgnoreCase(ime) && r.dohvatiPrezime().equalsIgnoreCase(prezime))
                .toList();
    }

    private void ispisiStatistiku(List<Rezervacija> rezervacije) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("                      STATISTIKA");
        System.out.println("=".repeat(60));
        System.out.println();

        int ukupnoRezervacija = rezervacije.size();

        long brojAktivnih = rezervacije.stream()
                .filter(r -> r.dohvatiStanjeString().equalsIgnoreCase("PRIMLJENA") ||
                            r.dohvatiStanjeString().equalsIgnoreCase("AKTIVNA"))
                .count();

        long brojOtkazanih = rezervacije.stream()
                .filter(r -> r.dohvatiStanjeString().equalsIgnoreCase("OTKAZANA"))
                .count();

        long brojNaCekanju = rezervacije.stream()
                .filter(r -> r.dohvatiStanjeString().equalsIgnoreCase("NA ČEKANJU"))
                .count();

        long brojOdgodjenih = rezervacije.stream()
                .filter(r -> r.dohvatiStanjeString().equalsIgnoreCase("ODGOĐENA"))
                .count();

        int ukupnaCijena = izracunajUkupnuCijenu(rezervacije);

        System.out.println("Ukupan broj rezervacija:          " + ukupnoRezervacija);
        System.out.println("Broj aktivnih rezervacija:         " + brojAktivnih);
        System.out.println("Broj otkazanih rezervacija:        " + brojOtkazanih);
        System.out.println("Broj rezervacija na čekanju:       " + brojNaCekanju);
        if (brojOdgodjenih > 0) {
            System.out.println("Broj odgođenih rezervacija:        " + brojOdgodjenih);
        }
        System.out.println("Ukupna cijena (aktivne):           " + ukupnaCijena + " EUR");
        System.out.println();
        System.out.println("=".repeat(60));
    }

    private int izracunajUkupnuCijenu(List<Rezervacija> rezervacije) {
        int ukupnaCijena = 0;

        for (Rezervacija rezervacija : rezervacije) {
            String stanje = rezervacija.dohvatiStanjeString();
            if (stanje.equalsIgnoreCase("PRIMLJENA") || stanje.equalsIgnoreCase("AKTIVNA")) {
                Aranzman aranzman = pronadjiAranzman(rezervacija);
                if (aranzman != null) {
                    ukupnaCijena += aranzman.dohvatiCijenaPoOsobi();
                }
            }
        }

        return ukupnaCijena;
    }

    private Aranzman pronadjiAranzman(Rezervacija rezervacija) {
        return agencija.dohvatiPodatke()
                .stream()
                .filter(aranzman -> aranzman.dohvatiDjecu().contains(rezervacija))
                .findFirst()
                .orElse(null);
    }
}
