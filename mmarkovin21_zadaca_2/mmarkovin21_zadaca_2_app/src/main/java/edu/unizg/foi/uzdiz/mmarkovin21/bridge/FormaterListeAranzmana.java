package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterBrojeva;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

/**
 * Konkretna implementacija formatera za listu aranžmana (komanda ITAK).
 * Prikazuje osnovne informacije o aranžmanima u sumarnom obliku.
 */
public class FormaterListeAranzmana implements TablicniFormater {

    @Override
    public int[] definirajSirineKolona() {
        return new int[]{5, 30, 12, 12, 10, 10, 8, 8, 8, 8};
    }

    @Override
    public String[] kreirajZaglavlje() {
        return new String[]{
                "Oznaka",
                "Naziv",
                "Početni datum",
                "Završni datum",
                "Vrijeme kretanja",
                "Vrijeme povratka",
                "Cijena",
                "Min broj putnika",
                "Maks broj putnika",
                "Status"
        };
    }

    @Override
    public boolean[] definirajBrojcanaPolja() {
        return new boolean[]{true, false, false, false, false, false, true, true, true, false};
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Aranzman aranzman)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Aranzman");
        }

        return new String[]{
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiOznaka()),
                aranzman.dohvatiNaziv(),
                PretvaracTipovaPodataka.formatirajDatum(aranzman.dohvatiPocetniDatum()),
                PretvaracTipovaPodataka.formatirajDatum(aranzman.dohvatiZavrsniDatum()),
                aranzman.dohvatiVrijemeKretanja() != null
                    ? PretvaracTipovaPodataka.formatirajVrijeme(aranzman.dohvatiVrijemeKretanja())
                    : "",
                aranzman.dohvatiVrijemePovratka() != null
                    ? PretvaracTipovaPodataka.formatirajVrijeme(aranzman.dohvatiVrijemePovratka())
                    : "",
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiCijenaPoOsobi()),
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiMinBrojPutnika()),
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiMaxBrojPutnika()),
                aranzman.dohvatiStatusString()
        };
    }
}
