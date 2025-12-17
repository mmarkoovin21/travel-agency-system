package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;


public class KomandaIP implements Komanda {
    public KomandaIP() {
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 2) {
            System.out.println("Greška: Neispravan broj parametara. Očekivano: IP [N|S]");
            return;
        }

        String nacinSortiranja = parametri[1].toUpperCase();

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1]);

        switch (nacinSortiranja) {
            case "N" -> {
                KonfiguracijaIspisa.dohvatiInstancu().postaviNacinSortiranja("N");
                System.out.println("Postavljeno sortiranje: kronološki redoslijed (prvo stari, zatim novi).");
            }
            case "S" -> {
                KonfiguracijaIspisa.dohvatiInstancu().postaviNacinSortiranja("S");
                System.out.println("Postavljeno sortiranje: obrnuti kronološki redoslijed (prvo novi, zatim stari).");
            }
            default -> System.out.println("Greška: Neispravan parametar. Očekivano: IP [N|S]");
        }
    }
}
