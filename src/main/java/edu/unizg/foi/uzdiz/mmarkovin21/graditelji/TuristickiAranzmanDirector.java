package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.TuristickiAranzman;

import java.time.LocalDateTime;

public class TuristickiAranzmanDirector {
    private final TuristickiAranzmanGraditelj graditelj;

    public TuristickiAranzmanDirector(TuristickiAranzmanGraditelj graditelj) {
        this.graditelj = graditelj;
    }

    public TuristickiAranzman izgradiOsnovniAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi
    ) {
        return graditelj.izradiAranzman(
                oznaka, naziv, program, pocetniDatum, zavrsniDatum, minBrojPutnika, maxBrojPutnika, cijenaPoOsobi
        ).dohvatiAranzman();
    }

    // Aranžman s organiziranim prijevozom (ima vremena kretanja/povratka)
    public TuristickiAranzman izgradiAranzmanSPrijevozom(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            LocalDateTime vrijemeKretanja,
            LocalDateTime vrijemePovratka,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            String prijevoz
    ) {
        return graditelj.izradiAranzman(
                        oznaka, naziv, program, pocetniDatum, zavrsniDatum, minBrojPutnika, maxBrojPutnika, cijenaPoOsobi
                )
                .postaviVrijemeKretanja(vrijemeKretanja)
                .postaviVrijemePovratka(vrijemePovratka)
                .postaviPrijevoz(prijevoz)
                .dohvatiAranzman();
    }

    // Aranžman s noćenjem (ali bez doplate za jednokrevetnu sobu)
    public TuristickiAranzman izgradiAranzmanSNocenjem(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            LocalDateTime vrijemeKretanja,
            LocalDateTime vrijemePovratka,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            int brojNocenja,
            String prijevoz,
            int brojDorucaka,
            int brojRucakova,
            int brojVecera
    ) {
        return graditelj.izradiAranzman(
                        oznaka, naziv, program, pocetniDatum, zavrsniDatum, minBrojPutnika, maxBrojPutnika, cijenaPoOsobi
                )
                .postaviVrijemeKretanja(vrijemeKretanja)
                .postaviVrijemePovratka(vrijemePovratka)
                .postaviBrojNocenja(brojNocenja)
                .postaviPrijevoz(prijevoz)
                .postaviBrojDorucaka(brojDorucaka)
                .postaviBrojRucakova(brojRucakova)
                .postaviBrojVecera(brojVecera)
                .dohvatiAranzman();
    }

    // Potpuni aranžman s noćenjem i doplatom za jednokrevetnu sobu
    public TuristickiAranzman izgradiPotpuniAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            LocalDateTime vrijemeKretanja,
            LocalDateTime vrijemePovratka,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            int brojNocenja,
            Long doplataZaJednokrevetnuSobu,
            String prijevoz,
            int brojDorucaka,
            int brojRucakova,
            int brojVecera
    ) {
        return graditelj.izradiAranzman(
                        oznaka, naziv, program, pocetniDatum, zavrsniDatum, minBrojPutnika, maxBrojPutnika, cijenaPoOsobi
                )
                .postaviVrijemeKretanja(vrijemeKretanja)
                .postaviVrijemePovratka(vrijemePovratka)
                .postaviBrojNocenja(brojNocenja)
                .postaviDoplatuZaJednokrevetnuSobu(doplataZaJednokrevetnuSobu)
                .postaviPrijevoz(prijevoz)
                .postaviBrojDorucaka(brojDorucaka)
                .postaviBrojRucakova(brojRucakova)
                .postaviBrojVecera(brojVecera)
                .dohvatiAranzman();
    }

    // Aranžman bez noćenja ali s obrocima (npr. jednodnevni izlet)
    public TuristickiAranzman izgradiJednodnevniAranzman(
            int oznaka,
            String naziv,
            String program,
            LocalDateTime pocetniDatum,
            LocalDateTime zavrsniDatum,
            LocalDateTime vrijemeKretanja,
            LocalDateTime vrijemePovratka,
            int minBrojPutnika,
            int maxBrojPutnika,
            Long cijenaPoOsobi,
            String prijevoz,
            int brojDorucaka,
            int brojRucakova,
            int brojVecera
    ) {
        return graditelj.izradiAranzman(
                        oznaka, naziv, program, pocetniDatum, zavrsniDatum, minBrojPutnika, maxBrojPutnika, cijenaPoOsobi
                )
                .postaviVrijemeKretanja(vrijemeKretanja)
                .postaviVrijemePovratka(vrijemePovratka)
                .postaviPrijevoz(prijevoz)
                .postaviBrojDorucaka(brojDorucaka)
                .postaviBrojRucakova(brojRucakova)
                .postaviBrojVecera(brojVecera)
                .dohvatiAranzman();
    }
}
