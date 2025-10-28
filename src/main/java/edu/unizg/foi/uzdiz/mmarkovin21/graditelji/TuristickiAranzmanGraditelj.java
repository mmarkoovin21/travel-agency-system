package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.TuristickiAranzman;

import java.time.LocalDateTime;

public class TuristickiAranzmanGraditelj {
    protected TuristickiAranzman aranzman;
    public TuristickiAranzmanGraditelj izradiAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi
    ) {
        this.aranzman = new TuristickiAranzman(
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
    public TuristickiAranzmanGraditelj postaviVrijemeKretanja(LocalDateTime vrijemeKretanja) {
        this.aranzman.postaviVrijemeKretanja(vrijemeKretanja);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviVrijemePovratka(LocalDateTime vrijemePovratka) {
        this.aranzman.postaviVrijemePovratka(vrijemePovratka);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviBrojNocenja(int brojNocenja) {
        this.aranzman.postaviBrojNocenja(brojNocenja);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviDoplatuZaJednokrevetnuSobu(Long doplata) {
        this.aranzman.postaviDoplataZaJednokrevetnuSobu(doplata);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviPrijevoz(String prijevoz) {
        this.aranzman.postaviPrijevoz(prijevoz);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviBrojDorucaka(int brojDorucaka) {
        this.aranzman.postaviBrojDorucaka(brojDorucaka);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviBrojRucakova(int brojRucakova) {
        this.aranzman.postaviBrojRucakova(brojRucakova);
        return this;
    }

    public TuristickiAranzmanGraditelj postaviBrojVecera(int brojVecera) {
        this.aranzman.postaviBrojVecera(brojVecera);
        return this;
    }

    public TuristickiAranzman dohvatiAranzman() {
        return this.aranzman;
    }
}
