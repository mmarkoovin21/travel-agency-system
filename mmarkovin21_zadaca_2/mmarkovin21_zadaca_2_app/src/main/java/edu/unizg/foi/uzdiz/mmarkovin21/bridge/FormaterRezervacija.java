package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

/**
 * Konkretna implementacija formatera za rezervacije (komanda IRTA).
 * Omogućava prikaz rezervacija s opcionalnom kolonom za datum otkazivanja.
 */
public class FormaterRezervacija implements TablicniFormater {

    private final boolean prikaziDatumOtkaza;

    /**
     * Konstruktor formatera za rezervacije.
     * @param prikaziDatumOtkaza true ako treba prikazati kolonu s datumom otkazivanja
     */
    public FormaterRezervacija(boolean prikaziDatumOtkaza) {
        this.prikaziDatumOtkaza = prikaziDatumOtkaza;
    }

    @Override
    public int[] definirajSirineKolona() {
        if (prikaziDatumOtkaza) {
            return new int[]{15, 15, 20, 10, 20};
        } else {
            return new int[]{15, 15, 20, 10};
        }
    }

    @Override
    public String[] kreirajZaglavlje() {
        if (prikaziDatumOtkaza) {
            return new String[]{
                    "Ime",
                    "Prezime",
                    "Datum i vrijeme",
                    "Vrsta",
                    "Datum i vrijeme otkaza"
            };
        } else {
            return new String[]{
                    "Ime",
                    "Prezime",
                    "Datum i vrijeme",
                    "Vrsta"
            };
        }
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Rezervacija)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Rezervacija");
        }

        Rezervacija rezervacija = (Rezervacija) podatak;

        if (prikaziDatumOtkaza) {
            String datumOtkaza = rezervacija.dohvatiDatumVrijemeOtkazivanja() != null
                    ? PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemeOtkazivanja())
                    : "";

            return new String[]{
                    rezervacija.dohvatiIme(),
                    rezervacija.dohvatiPrezime(),
                    PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemePrijema()),
                    rezervacija.dohvatiStanjeString(),
                    datumOtkaza
            };
        } else {
            return new String[]{
                    rezervacija.dohvatiIme(),
                    rezervacija.dohvatiPrezime(),
                    PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemePrijema()),
                    rezervacija.dohvatiStanjeString()
            };
        }
    }
}
