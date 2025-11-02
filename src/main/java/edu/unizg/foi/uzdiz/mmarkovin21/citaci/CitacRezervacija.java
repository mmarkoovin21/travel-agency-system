package edu.unizg.foi.uzdiz.mmarkovin21.citaci;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.upravitelji.UpraviteljStanjaRezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.validatori.ValidatorRezervacija;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CitacRezervacija {
    private final ValidatorRezervacija validator;
    private final UpraviteljStanjaRezervacija upraviteljStanja;

    public CitacRezervacija() {
        this.upraviteljStanja = new UpraviteljStanjaRezervacija();
        this.validator = new ValidatorRezervacija();
    }

    public List<Rezervacija> ucitajRezervacije(String nazivDatoteke) {
        List<Rezervacija> rezervacije = new ArrayList<>();
        try (BufferedReader citac = new BufferedReader(new FileReader(nazivDatoteke))) {
            String linija;
            boolean prviRedak = true;
            int brojRetka = 0;

            while ((linija = citac.readLine()) != null) {
                brojRetka++;
                if (prviRedak) {
                    prviRedak = false;
                    continue;
                }
                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                String[] atributi = linija.split(",");
                int OCEKIVANI_BROJ_ATRIBUTA = 4;
                if (atributi.length != OCEKIVANI_BROJ_ATRIBUTA) {
                    System.err.println("Greška pri obradi retka " + brojRetka +
                            " u datoteci" + nazivDatoteke + ": Očekivano " + OCEKIVANI_BROJ_ATRIBUTA +
                            " atributa, pronađeno " + atributi.length);
                    continue;
                }

                try {
                    Rezervacija validnaRezervacija = validator.validiraj(atributi);

                    if (validnaRezervacija == null) {
                        System.err.println("Neispravan format rezervacije na retku " + brojRetka);
                        continue;
                    }
                    if (!provjeriPostojanjeAranzmana(validnaRezervacija.dohvatiOznakaAranzmana())) {
                        System.err.println("Aranžman " + validnaRezervacija.dohvatiOznakaAranzmana() +
                                " ne postoji (redak " + brojRetka + " u datoteci" + nazivDatoteke + ")");
                        continue;
                    }

                    String pocetnoStanje = upraviteljStanja.odrediPocetnoStanje(
                            validnaRezervacija.dohvatiOznakaAranzmana(),
                            rezervacije, validnaRezervacija.dohvatiIme(), validnaRezervacija.dohvatiPrezime()
                    );

                    // Ne dodaj rezervaciju ako nije validna (pocetnoStanje je prazan string)
                    if (pocetnoStanje.isEmpty()) {
                        continue;
                    }

                    validnaRezervacija.promijeniStanje(pocetnoStanje);
                    rezervacije.add(validnaRezervacija);
                } catch (Exception e) {
                    System.err.println("Greška pri obradi retka " + brojRetka + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        List<Integer> oznakaAranzmana = rezervacije.stream()
                .map(Rezervacija::dohvatiOznakaAranzmana)
                .distinct()
                .toList();

        for (Integer oznaka : oznakaAranzmana) {
            upraviteljStanja.azurirajStanja(oznaka, rezervacije);
        }

        return rezervacije;
    }

    private boolean provjeriPostojanjeAranzmana(int oznakaAranzmana) {
        return TuristickaAgencija.dohvatiInstancu()
                .dohvatiAranzmane()
                .stream()
                .anyMatch(aranzman -> aranzman.dohvatiOznaka() == oznakaAranzmana);
    }

}
