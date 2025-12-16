package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.UpraviteljRezervacijaIAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

import java.util.List;

public class KomandaOTA implements Komanda {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana mediator;

    public KomandaOTA(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana mediator) {
        this.agencija = agencija;
        this.mediator = mediator;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 2) {
            System.out.println("Greška: Neispravan broj parametara za komandu OTA. Očekivano OTA <oznaka>.");
            return;
        }

        Integer oznakaAranzmana = ValidatorKomandi.parsirajIValidirajOznakuAranzmana(parametri[1]);
        if (oznakaAranzmana == null) {
            return;
        }

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1]);

        // Pronađi aranžman po oznaci
        Aranzman aranzman = pronadjiAranzmanPoOznaci(oznakaAranzmana);
        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + oznakaAranzmana + " nije pronađen.");
            return;
        }

        // Provjerim je li aranžman već otkazan
        if (aranzman.dohvatiStatusString().equalsIgnoreCase("OTKAZAN")) {
            System.out.println("Greška: Aranžman s oznakom " + oznakaAranzmana + " je već otkazan.");
            return;
        }

        // Dohvati sve rezervacije aranžmana (Composite uzorak - djeca)
        List<TuristickaKomponenta> djeca = aranzman.dohvatiDjecu();
        int brojOtkazanih = 0;

        // Otkaži sve neotkazane rezervacije
        for (TuristickaKomponenta dijete : djeca) {
            if (dijete instanceof Rezervacija) {
                Rezervacija rezervacija = (Rezervacija) dijete;
                if (!rezervacija.dohvatiStanjeString().equalsIgnoreCase("OTKAZANA")) {
                    // Otkaži rezervaciju koristeći mediator
                    boolean otkazana = mediator.otkaziRezervaciju(
                            rezervacija.dohvatiIme(),
                            rezervacija.dohvatiPrezime(),
                            oznakaAranzmana
                    );
                    if (otkazana) {
                        brojOtkazanih++;
                    }
                }
            }
        }

        // Otkaži aranžman
        aranzman.otkazi();

        System.out.println("Uspješno otkazan turistički aranžman s oznakom " + oznakaAranzmana + ".");
        System.out.println("Otkazano " + brojOtkazanih + " rezervacija.");
    }

    private Aranzman pronadjiAranzmanPoOznaci(int oznaka) {
        return agencija.dohvatiPodatke().stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }
}
