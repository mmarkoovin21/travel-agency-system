package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

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

        Integer oznaka = ValidatorKomandi.parsirajIValidirajOznakuAranzmana(parametri[1]);
        if (oznaka == null) {
            return;
        }

        String stanjeRezervacije = parametri[2];
        List<Rezervacija> filtriraneRezervacije = vratiRezervacijeSaStanjem(stanjeRezervacije, oznaka);
        boolean jesuOdgodjene = filtriraneRezervacije.stream().anyMatch(r -> r.dohvatiDatumVrijemeOtkazivanja() != null);

        IspisivacRezervacija ispisivac = new IspisivacRezervacija(new FormaterRezervacija(jesuOdgodjene));
        if (filtriraneRezervacije.isEmpty()) {
            System.out.println("Nema rezervacija za zadane kriterije.");
        } else {
            ispisivac.ispisi(filtriraneRezervacije);
        }

    }

    private List<Rezervacija> vratiRezervacijeSaStanjem(String stanje, int oznakaAranzmana) {
        List<Rezervacija> rezervacije = agencija.dohvatiPodatke()
                .stream()
                .filter(k -> k instanceof Rezervacija)
                .map(k -> (Rezervacija) k)
                .toList();

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
            case "O" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.otkazana())
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            case "PAČO" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            case "ODO" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.odgodjena())
                    .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                    .toList();
            default -> List.of();
        };
    }
}
