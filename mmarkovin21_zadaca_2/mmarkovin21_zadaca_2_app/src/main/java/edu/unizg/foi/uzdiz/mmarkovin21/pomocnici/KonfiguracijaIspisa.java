package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

/**
 * Singleton klasa koja čuva globalnu konfiguraciju ispisa tablica.
 * Koristi se za spremanje načina sortiranja podataka (kronološki ili obrnuto).
 */
public class KonfiguracijaIspisa {
    private static KonfiguracijaIspisa instanca;
    private String nacinSortiranja = "N";

    private KonfiguracijaIspisa() {}

    public static KonfiguracijaIspisa dohvatiInstancu() {
        if (instanca == null) {
            instanca = new KonfiguracijaIspisa();
        }
        return instanca;
    }

    public void postaviNacinSortiranja(String nacin) {
        this.nacinSortiranja = nacin;
    }


    public boolean jeObrnutoSortiranje() {
        return "S".equalsIgnoreCase(nacinSortiranja);
    }
}
