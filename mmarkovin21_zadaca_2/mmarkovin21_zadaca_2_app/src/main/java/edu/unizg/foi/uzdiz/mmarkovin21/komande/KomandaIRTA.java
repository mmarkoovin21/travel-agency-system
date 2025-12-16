package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterListeRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.RezervacijaFilter;
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

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2]);

        String stanjeRezervacije = parametri[2];
        List<Rezervacija> filtriraneRezervacije = vratiRezervacijeSaStanjem(stanjeRezervacije, oznaka);
        boolean jesuOdgodjene = filtriraneRezervacije.stream().anyMatch(r -> r.dohvatiDatumVrijemeOtkazivanja() != null);

        IspisivacRezervacija ispisivac = new IspisivacRezervacija(new FormaterListeRezervacija(jesuOdgodjene));
        if (filtriraneRezervacije.isEmpty()) {
            System.out.println("Nema rezervacija za zadane kriterije.");
        } else {
            ispisivac.ispisiSNaslovom(filtriraneRezervacije, "REZERVACIJE ZA TURISTIČKI ARANŽMAN");
        }

    }

    private List<Rezervacija> vratiRezervacijeSaStanjem(String stanje, int oznakaAranzmana) {
        List<Rezervacija> rezervacije = agencija.dohvatiPodatke()
                .stream()
                .flatMap(aranzman -> aranzman.dohvatiDjecu().stream())
                .filter(k -> k instanceof Rezervacija)
                .map(k -> (Rezervacija) k)
                .toList();

        Comparator<Rezervacija> usporedjivac = Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema);
        if (KonfiguracijaIspisa.dohvatiInstancu().jeObrnutoSortiranje()) {
            usporedjivac = usporedjivac.reversed();
        }

        return switch (stanje) {
            case "PA" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.primljenIliAktivna())
                    .sorted(usporedjivac)
                    .toList();
            case "Č" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.naCekanju())
                    .sorted(usporedjivac)
                    .toList();
            case "O" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.otkazana())
                    .sorted(usporedjivac)
                    .toList();
            case "PAČO" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .sorted(usporedjivac)
                    .toList();
            case "ODO" -> rezervacije.stream()
                    .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                    .filter(RezervacijaFilter.odgodjena())
                    .sorted(usporedjivac)
                    .toList();
            default -> List.of();
        };
    }
}
