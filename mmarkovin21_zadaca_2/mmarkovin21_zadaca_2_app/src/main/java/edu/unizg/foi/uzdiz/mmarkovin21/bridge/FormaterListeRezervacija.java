package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

public class FormaterListeRezervacija implements TablicniFormater{
    private final boolean prikaziDatumOtkaza;
    public FormaterListeRezervacija(boolean prikaziDatumOtkaza) {
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
        if (!(podatak instanceof Rezervacija rezervacija)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Rezervacija");
        }

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
