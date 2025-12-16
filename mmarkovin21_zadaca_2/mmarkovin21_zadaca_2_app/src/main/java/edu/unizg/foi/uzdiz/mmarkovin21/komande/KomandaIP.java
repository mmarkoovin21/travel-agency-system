package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.KonfiguracijaIspisa;

/**
 * Komanda za postavljanje načina sređivanja podataka kod ispisa u obliku tablica.
 * IP N - kronološki redoslijed (prvo stari, zatim novi)
 * IP S - obrnuti kronološki redoslijed (prvo novi, zatim stari)
 */
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
