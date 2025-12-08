package edu.unizg.foi.uzdiz.mmarkovin21.graditelji;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.ValidiraniPodaci;

public class AranzmanDirektor {
    private AranzmanGraditelj graditelj;

    public AranzmanDirektor(AranzmanGraditelj graditelj) {
        this.graditelj = graditelj;
    }

    public Aranzman konstruiraj(ValidiraniPodaci podaci) {
        graditelj.izgradiAranzman(
                podaci.dohvatiOznaka(),
                podaci.dohvatiNaziv(),
                podaci.dohvatiProgram(),
                podaci.dohvatiPocetniDatum(),
                podaci.dohvatiZavrsniDatum(),
                podaci.dohvatiMinBrojPutnika(),
                podaci.dohvatiMaxBrojPutnika(),
                podaci.dohvatiCijenaPoOsobi()
        );

        // Dodajemo opcionalne parametre ako postoje
        if (podaci.dohvatiVrijemeKretanja() != null) {
            graditelj.postaviVrijemeKretanja(podaci.dohvatiVrijemeKretanja());
        }

        if (podaci.dohvatiVrijemePovratka() != null) {
            graditelj.postaviVrijemePovratka(podaci.dohvatiVrijemePovratka());
        }

        if (podaci.dohvatiBrojNocenja() > 0) {
            graditelj.postaviBrojNocenja(podaci.dohvatiBrojNocenja());
        }

        if (podaci.dohvatiDoplataZaJednokrevetnuSobu() != null) {
            graditelj.postaviDoplatuZaJednokrevetnuSobu(podaci.dohvatiDoplataZaJednokrevetnuSobu());
        }

        if (podaci.dohvatiPrijevoz() != null && !podaci.dohvatiPrijevoz().isEmpty()) {
            graditelj.postaviPrijevoz(podaci.dohvatiPrijevoz());
        }

        if (podaci.dohvatiBrojDorucaka() > 0) {
            graditelj.postaviBrojDorucaka(podaci.dohvatiBrojDorucaka());
        }

        if (podaci.dohvatiBrojRucakova() > 0) {
            graditelj.postaviBrojRucakova(podaci.dohvatiBrojRucakova());
        }

        if (podaci.dohvatiBrojVecera() > 0) {
            graditelj.postaviBrojVecera(podaci.dohvatiBrojVecera());
        }

        return graditelj.getAranzman();
    }
}