package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterBrojeva;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

import java.util.ArrayList;
import java.util.List;

public class FormaterDetaljaAranzmana implements TablicniFormater {

    @Override
    public int[] definirajSirineKolona() {
        return new int[]{35, 60};
    }

    @Override
    public String[] kreirajZaglavlje() {
        return new String[]{"Atribut", "Vrijednost"};
    }

    @Override
    public boolean[] definirajBrojcanaPolja() {
        return new boolean[]{false, false};
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof DetaljiRed red)) {
            throw new IllegalArgumentException("Očekivan objekt tipa DetaljiRed");
        }

        return new String[]{red.dohvatiAtribut(), red.dohvatiVrijednost()};
    }


    public static List<DetaljiRed> pretvoriAranzmanURedove(Aranzman aranzman) {
        List<DetaljiRed> redovi = new ArrayList<>();

        redovi.add(new DetaljiRed("Oznaka", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiOznaka())));
        redovi.add(new DetaljiRed("Naziv", aranzman.dohvatiNaziv()));
        redovi.add(new DetaljiRed("Program", aranzman.dohvatiProgram()));
        redovi.add(new DetaljiRed("Početni datum", PretvaracTipovaPodataka.formatirajDatum(aranzman.dohvatiPocetniDatum())));
        redovi.add(new DetaljiRed("Završni datum", PretvaracTipovaPodataka.formatirajDatum(aranzman.dohvatiZavrsniDatum())));

        if (aranzman.dohvatiVrijemeKretanja() != null) {
            redovi.add(new DetaljiRed("Vrijeme kretanja", PretvaracTipovaPodataka.formatirajVrijeme(aranzman.dohvatiVrijemeKretanja())));
        }

        if (aranzman.dohvatiVrijemePovratka() != null) {
            redovi.add(new DetaljiRed("Vrijeme povratka", PretvaracTipovaPodataka.formatirajVrijeme(aranzman.dohvatiVrijemePovratka())));
        }

        redovi.add(new DetaljiRed("Cijena po osobi", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiCijenaPoOsobi())));
        redovi.add(new DetaljiRed("Min broj putnika", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiMinBrojPutnika())));
        redovi.add(new DetaljiRed("Maks broj putnika", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiMaxBrojPutnika())));
        redovi.add(new DetaljiRed("Broj noćenja", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojNocenja())));
        redovi.add(new DetaljiRed("Doplata za jednokrevetnu sobu", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiDoplataZaJednokrevetnuSobu())));

        if (aranzman.dohvatiPrijevoz() != null && !aranzman.dohvatiPrijevoz().isEmpty()) {
            redovi.add(new DetaljiRed("Prijevoz", String.join(", ", aranzman.dohvatiPrijevoz())));
        }

        redovi.add(new DetaljiRed("Broj doručaka", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojDorucaka())));
        redovi.add(new DetaljiRed("Broj ručkova", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojRuckova())));
        redovi.add(new DetaljiRed("Broj večera", FormaterBrojeva.formatirajCijeliBroj(aranzman.dohvatiBrojVecera())));
        redovi.add(new DetaljiRed("Status", aranzman.dohvatiStatusString()));

        return redovi;
    }
}
