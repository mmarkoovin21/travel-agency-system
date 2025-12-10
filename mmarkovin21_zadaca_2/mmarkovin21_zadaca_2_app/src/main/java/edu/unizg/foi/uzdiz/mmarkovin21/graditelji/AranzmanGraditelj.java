package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AranzmanGraditelj {
    protected Aranzman aranzman;
    public AranzmanGraditelj() {}
    public AranzmanGraditelj izgradiAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDate pocetniDatum,
            LocalDate zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi
    ) {
        this.aranzman = new Aranzman(
                oznaka,
                naziv,
                program,
                pocetniDatum,
                zavrsniDatum,
                minBrojPutnika,
                maxBrojPutnika,
                cijenaPoOsobi
        );
        return this;
    }
    public AranzmanGraditelj postaviVrijemeKretanja(LocalTime vrijemeKretanja) {
        this.aranzman.postaviVrijemeKretanja(vrijemeKretanja);
        return this;
    }

    public AranzmanGraditelj postaviVrijemePovratka(LocalTime vrijemePovratka) {
        this.aranzman.postaviVrijemePovratka(vrijemePovratka);
        return this;
    }

    public AranzmanGraditelj postaviBrojNocenja(int brojNocenja) {
        this.aranzman.postaviBrojNocenja(brojNocenja);
        return this;
    }

    public AranzmanGraditelj postaviDoplatuZaJednokrevetnuSobu(Long doplata) {
        this.aranzman.postaviDoplataZaJednokrevetnuSobu(doplata);
        return this;
    }

    public AranzmanGraditelj postaviPrijevoz(List<String> prijevoz) {
        this.aranzman.postaviPrijevoz(prijevoz);
        return this;
    }

    public AranzmanGraditelj postaviBrojDorucaka(int brojDorucaka) {
        this.aranzman.postaviBrojDorucaka(brojDorucaka);
        return this;
    }

    public AranzmanGraditelj postaviBrojRucakova(int brojRucakova) {
        this.aranzman.postaviBrojRucakova(brojRucakova);
        return this;
    }

    public AranzmanGraditelj postaviBrojVecera(int brojVecera) {
        this.aranzman.postaviBrojVecera(brojVecera);
        return this;
    }

    public Aranzman getAranzman() {
        return this.aranzman;
    }
}
