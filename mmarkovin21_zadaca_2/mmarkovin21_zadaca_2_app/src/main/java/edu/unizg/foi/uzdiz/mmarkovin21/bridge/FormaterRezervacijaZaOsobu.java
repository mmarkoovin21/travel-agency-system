package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.PretvaracTipovaPodataka;

import java.util.List;


public class FormaterRezervacijaZaOsobu implements TablicniFormater {

    private final boolean prikaziDatumOtkaza;
    public FormaterRezervacijaZaOsobu(boolean prikaziDatumOtkaza) {
        this.prikaziDatumOtkaza = prikaziDatumOtkaza;
    }
    private final List<Aranzman> aranzmani = TuristickaAgencija.dohvatiInstancu()
            .dohvatiPodatke();
    @Override
    public int[] definirajSirineKolona() {
        if (prikaziDatumOtkaza) {
            return new int[]{15, 15, 20, 10, 20};
        } else {
            return new int[]{15, 15, 20, 10};
        }
    }

    @Override
    public String[] kreirajZaglavlje() {
        if (prikaziDatumOtkaza) {
            return new String[]{
                    "Datum i vrijeme",
                    "Oznaka aranžmana",
                    "Naziv aranžmana",
                    "Vrsta",
                    "Datum i vrijeme otkaza"
            };
        } else {
            return new String[]{
                    "Datum i vrijeme",
                    "Oznaka aranžmana",
                    "Naziv aranžmana",
                    "Vrsta"
            };
        }
    }

    @Override
    public String[] formatirajRed(Object podatak) {
        if (!(podatak instanceof Rezervacija rezervacija)) {
            throw new IllegalArgumentException("Očekivan objekt tipa Rezervacija");
        }

        if (prikaziDatumOtkaza) {
            String datumOtkaza = rezervacija.dohvatiDatumVrijemeOtkazivanja() != null
                    ? PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemeOtkazivanja())
                    : "";

            return new String[]{
                    PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemePrijema()),
                    String.valueOf(rezervacija.dohvatiOznakaAranzmana()),
                    aranzmani.stream()
                            .filter(a -> a.dohvatiOznaka() == rezervacija.dohvatiOznakaAranzmana())
                            .map(Aranzman::dohvatiNaziv)
                            .findFirst()
                            .orElse("Nepoznat aranžman"),
                    rezervacija.dohvatiStanjeString(),
                    datumOtkaza
            };
        } else {
            return new String[]{
                    PretvaracTipovaPodataka.formatirajDatumVrijeme(rezervacija.dohvatiDatumVrijemePrijema()),
                    String.valueOf(rezervacija.dohvatiOznakaAranzmana()),
                    aranzmani.stream()
                            .filter(a -> a.dohvatiOznaka() == rezervacija.dohvatiOznakaAranzmana())
                            .map(Aranzman::dohvatiNaziv)
                            .findFirst()
                            .orElse("Nepoznat aranžman"),
                    rezervacija.dohvatiStanjeString()
            };
        }
    }
}
