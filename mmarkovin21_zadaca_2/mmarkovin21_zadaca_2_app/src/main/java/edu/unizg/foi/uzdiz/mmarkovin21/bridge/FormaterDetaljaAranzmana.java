package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

/**
 * Konkretna implementacija formatera za detaljni prikaz aranžmana (komanda ITAP).
 * Prikazuje sve dostupne informacije o jednom aranžmanu.
 */
public class FormaterDetaljaAranzmana implements TablicniFormater {

    @Override
    public int[] definirajSirineKolona() {
        return new int[]{5, 20, 40, 12, 12, 8, 8, 8, 8, 8, 8, 8, 10, 8, 8, 8};
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
                "Broj večera"
        };
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Aranzman)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Aranzman");
        }

        Aranzman aranzman = (Aranzman) podatak;

        return new String[]{
                String.valueOf(aranzman.dohvatiOznaka()),
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
                String.valueOf(aranzman.dohvatiCijenaPoOsobi()),
                String.valueOf(aranzman.dohvatiMinBrojPutnika()),
                String.valueOf(aranzman.dohvatiMaxBrojPutnika()),
                String.valueOf(aranzman.dohvatiBrojNocenja()),
                String.valueOf(aranzman.dohvatiDoplataZaJednokrevetnuSobu()),
                aranzman.dohvatiPrijevoz() != null && !aranzman.dohvatiPrijevoz().isEmpty()
                    ? String.join(", ", aranzman.dohvatiPrijevoz())
                    : "",
                String.valueOf(aranzman.dohvatiBrojDorucaka()),
                String.valueOf(aranzman.dohvatiBrojRuckova()),
                String.valueOf(aranzman.dohvatiBrojVecera())
        };
    }
}
