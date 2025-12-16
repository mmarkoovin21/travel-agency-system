package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.mediator.UpraviteljRezervacijaIAranzmana;

public class KomandaBP implements Komanda {
    private final TuristickaAgencija agencija;
    private final UpraviteljRezervacijaIAranzmana mediator;

    public KomandaBP(TuristickaAgencija agencija, UpraviteljRezervacijaIAranzmana mediator) {
        this.agencija = agencija;
        this.mediator = mediator;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 2) {
            System.out.println("Greška: Neispravan broj parametara za komandu BP. Očekivano BP [A|R].");
            return;
        }

        String tipBrisanja = parametri[1].toUpperCase();

        switch (tipBrisanja) {
            case "A" -> obrisiAranzmane();
            case "R" -> obrisiRezervacije();
            default -> System.out.println("Greška: Neispravan parametar. Koristite 'A' za aranžmane ili 'R' za rezervacije.");
        }
    }

    private void obrisiAranzmane() {
        int brojAranzmana = agencija.dohvatiPodatke().size();

        agencija.obrisiSveAranzmane();
        mediator.obrisiSveAranzmane();

        System.out.println("Uspješno obrisano " + brojAranzmana + " aranžmana (i sve njihove rezervacije).");
    }

    private void obrisiRezervacije() {
        int brojRezervacija = 0;
        for (var aranzman : agencija.dohvatiPodatke()) {
            brojRezervacija += aranzman.dohvatiDjecu().size();
        }

        agencija.obrisiSveRezervacije();
        mediator.obrisiSveRezervacije();

        System.out.println("Uspješno obrisano " + brojRezervacija + " rezervacija.");
    }
}
