package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.FormaterTablice;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracDatuma;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.Validator;

public class KomandaITAP implements Komanda {
    private final TuristickaAgencija agencija;

    public KomandaITAP(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }
    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 2) {
            System.out.println("Greška: Neispravan broj parametara. Očekivano: ITAP <oznaka>");
            return;
        }

        Integer oznaka = Validator.parsirajIValidirajOznaku(parametri[1], "ITAP");
        if (oznaka == null) {
            return;
        }

        FormaterTablice tablica = new FormaterTablice(new int[]{5, 20, 40, 12, 12, 8, 8, 8, 8, 8, 8, 8, 10, 8, 8, 8});

        tablica.dodajRed("Oznaka", "Naziv", "Program", "Početni datum", "Završni datum", "Vrijeme kretanja", "Vrijeme povratka", "Cijena", "Min broj putnika", "Maks broj putnika", "Broj noćenja", "Doplata za jednokrevetnu sobu", "Prijevoz", "Broj doručka", "Broj ručkova", "Broj večera");

        boolean pronadjen = false;

        for (var aranzman : agencija.dohvatiAranzmane()) {
            if (aranzman.dohvatiOznaka() == oznaka) {
                    tablica.dodajRed(
                            String.valueOf(aranzman.dohvatiOznaka()),
                            aranzman.dohvatiNaziv(),
                            aranzman.dohvatiProgram(),
                            PretvaracDatuma.formatirajDatum(aranzman.dohvatiPocetniDatum()),
                            PretvaracDatuma.formatirajDatum(aranzman.dohvatiZavrsniDatum()),
                            aranzman.dohvatiVrijemeKretanja() != null ? PretvaracDatuma.formatirajVrijeme(aranzman.dohvatiVrijemeKretanja()) : "",
                            aranzman.dohvatiVrijemePovratka() != null ? PretvaracDatuma.formatirajVrijeme(aranzman.dohvatiVrijemePovratka()) : "",
                            String.valueOf(aranzman.dohvatiCijenaPoOsobi()),
                            String.valueOf(aranzman.dohvatiMinBrojPutnika()),
                            String.valueOf(aranzman.dohvatiMaxBrojPutnika()),
                            String.valueOf(aranzman.dohvatiBrojNocenja()),
                            String.valueOf(aranzman.dohvatiDoplataZaJednokrevetnuSobu()),
                            String.valueOf(aranzman.dohvatiPrijevoz() != null ? aranzman.dohvatiPrijevoz() : ""),
                            String.valueOf(aranzman.dohvatiBrojDorucaka()),
                            String.valueOf(aranzman.dohvatiBrojRuckova()),
                            String.valueOf(aranzman.dohvatiBrojVecera())
                    );
                    pronadjen = true;
                    System.out.println(tablica.formatiraj());
                    break;
            }
        }

        if (!pronadjen) {
            System.err.println("Greška: Aranžman s oznakom " + oznaka + " nije pronađen.");
        }
    }
}
