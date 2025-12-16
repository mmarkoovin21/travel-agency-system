package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterBrojeva;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

/**
 * Konkretna implementacija formatera za detaljni prikaz aranžmana (komanda ITAP).
 * Prikazuje sve dostupne informacije o jednom aranžmanu.
 */
public class FormaterDetaljaAranzmana implements TablicniFormater {

    @Override
    public int[] definirajSirineKolona() {
        return new int[]{5, 15, 30, 12, 12, 8, 8, 8, 8, 8, 8, 8, 10, 8, 8, 8, 8};
    }

    @Override
    public String[] kreirajZaglavlje() {
        return new String[]{
                "Oznaka",
                "Naziv",
                "Program",
                "Početni datum",
                "Završni datum",
                "Vrijeme kretanja",
                "Vrijeme povratka",
                "Cijena",
                "Min broj putnika",
                "Maks broj putnika",
                "Broj noćenja",
                "Doplata za jednokrevetnu sobu",
                "Prijevoz",
                "Broj doručka",
                "Broj ručkova",
                "Broj večera",
                "Status"
        };
    }

    @Override
    public boolean[] definirajBrojcanaPolja() {
        return new boolean[]{true, false, false, false, false, false, false, true, true, true, true, true, false, true, true, true, false};
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Aranzman aranzman)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Aranzman");
        }

        return new String[]{
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiOznaka()),
                aranzman.dohvatiNaziv(),
                aranzman.dohvatiProgram(),
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
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojNocenja()),
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiDoplataZaJednokrevetnuSobu()),
                aranzman.dohvatiPrijevoz() != null && !aranzman.dohvatiPrijevoz().isEmpty()
                    ? String.join(", ", aranzman.dohvatiPrijevoz())
                    : "",
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojDorucaka()),
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojRuckova()),
                FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojVecera()),
                aranzman.dohvatiStatusString()
        };
    }
}
