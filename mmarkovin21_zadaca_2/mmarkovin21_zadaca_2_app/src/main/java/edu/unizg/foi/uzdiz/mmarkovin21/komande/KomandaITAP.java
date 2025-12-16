package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.FormaterDetaljaAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.bridge.IspisivacAranzmana;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.ValidatorKomandi;

import java.util.Collections;
import java.util.List;

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

        Integer oznaka = ValidatorKomandi.parsirajIValidirajOznakuAranzmana(parametri[1]);
        if (oznaka == null) {
            return;
        }

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1]);

        boolean pronadjen = false;
        List<Aranzman> aranzmani = agencija.dohvatiPodatke();
        IspisivacAranzmana ispisivac = new IspisivacAranzmana(new FormaterDetaljaAranzmana());
        for (var aranzman : aranzmani) {
            if (aranzman.dohvatiOznaka() == oznaka) {
                    pronadjen = true;
                    var redoviDetalja = FormaterDetaljaAranzmana.pretvoriAranzmanURedove(aranzman);
                    ispisivac.ispisiSNaslovom(redoviDetalja, "DETALJI TURISTIČKOG ARANŽMANA");
                    break;
            }
        }

        if (!pronadjen) {
            System.err.println("Greška: Aranžman s oznakom " + oznaka + " nije pronađen.");
        }
    }
}
