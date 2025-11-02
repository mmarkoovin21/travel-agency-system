package edu.unizg.foi.uzdiz.mmarkovin21.upravitelji;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpraviteljStanjaRezervacija {
    private final TuristickaAgencija agencija;

    public UpraviteljStanjaRezervacija(){
        this.agencija = TuristickaAgencija.dohvatiInstancu();
    }

    public String odrediPocetnoStanje(int oznakaAranzmana, List<Rezervacija> postojeceRezervacije,
                                      String ime, String prezime) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            System.err.println("Aranžman s oznakom " + oznakaAranzmana + " nije pronađen.");
            return "";
        }

        if (imaAktivnuRezervacijuNaDrugomAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            return "na čekanju";
        }

        if (imaRezervacijuNaIstomAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            return "na čekanju";
        }

        long brojAktivnih = brojRezervacijaBezOtkazanih(oznakaAranzmana, postojeceRezervacije) + 1;

        if (brojAktivnih > aranzman.dohvatiMaxBrojPutnika()) {
            return "na čekanju";
        } else if (brojAktivnih >= aranzman.dohvatiMinBrojPutnika()) {
            return "aktivna";
        } else {
            return "primljena";
        }
    }

    public void azurirajStanja(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            return;
        }

        List<Rezervacija> aktivneRezervacije = dohvatiSortiraneAktivneRezervacije(oznakaAranzmana, rezervacije);
        Map<String, List<Rezervacija>> rezervacijePoOsobi = grupirajPoOsobi(aktivneRezervacije);

        int trenutniBrojAktivnih = postaviStanja(aktivneRezervacije, rezervacijePoOsobi,
                aranzman.dohvatiMinBrojPutnika(),
                aranzman.dohvatiMaxBrojPutnika());

        prilagodiStanjaPremaMinimumu(aktivneRezervacije, trenutniBrojAktivnih, aranzman.dohvatiMinBrojPutnika());
    }

    public boolean otkazirezervaciju(String ime, String prezime, int oznakaAranzmana) {
        Rezervacija otkazana = agencija.dohvatiRezervacije().stream().filter(r ->
                r.dohvatiIme().equalsIgnoreCase(ime) &&
                        r.dohvatiPrezime().equalsIgnoreCase(prezime) &&
                        r.dohvatiOznakaAranzmana() == oznakaAranzmana
        ).findFirst().orElse(null);
        if (otkazana != null) {
            otkazana.promijeniStanje("otkazana");
            otkazana.postaviDatumVrijemeOtkazivanja(LocalDateTime.now());
            azurirajStanja(oznakaAranzmana, agencija.dohvatiRezervacije());
            return true;
        } else {
            System.out.println("Nije pronađena rezervacija za " + ime + " " + prezime + " na aranžmanu " + oznakaAranzmana + ".");
            return false;
        }
    }

    public boolean dodajRezervaciju(Rezervacija novaRezervacija) {
        if (novaRezervacija == null) {
            System.err.println("Rezervacija ne može biti null.");
            return false;
        }

        Aranzman aranzman = pronadjiAranzman(novaRezervacija.dohvatiOznakaAranzmana());
        if (aranzman == null) {
            System.err.println("Aranžman s oznakom " + novaRezervacija.dohvatiOznakaAranzmana() + " nije pronađen.");
            return false;
        }

        List<Rezervacija> postojeceRezervacije = agencija.dohvatiRezervacije();

        String pocetnoStanje = odrediPocetnoStanje(
                novaRezervacija.dohvatiOznakaAranzmana(),
                postojeceRezervacije,
                novaRezervacija.dohvatiIme(),
                novaRezervacija.dohvatiPrezime()
        );

        if (pocetnoStanje.isEmpty()) {
            return false;
        }

        novaRezervacija.promijeniStanje(pocetnoStanje);
        agencija.dohvatiRezervacije().add(novaRezervacija);

        azurirajStanja(novaRezervacija.dohvatiOznakaAranzmana(), agencija.dohvatiRezervacije());

        return true;
    }


    private boolean imaAktivnuRezervacijuNaDrugomAranzmanu(int oznakaAranzmana,
                                                           List<Rezervacija> rezervacije,
                                                           String ime, String prezime) {
        return rezervacije.stream()
                .anyMatch(r -> r.dohvatiOznakaAranzmana() != oznakaAranzmana &&
                        r.dohvatiIme().equals(ime) &&
                        r.dohvatiPrezime().equals(prezime) &&
                        "aktivna".equals(r.dohvatiStanje()));
    }

    private boolean imaRezervacijuNaIstomAranzmanu(int oznakaAranzmana,
                                                   List<Rezervacija> rezervacije,
                                                   String ime, String prezime) {
        return rezervacije.stream()
                .anyMatch(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana &&
                        r.dohvatiIme().equals(ime) &&
                        r.dohvatiPrezime().equals(prezime) &&
                        ("aktivna".equals(r.dohvatiStanje()) || "primljena".equals(r.dohvatiStanje())));
    }

    private long brojRezervacijaBezOtkazanih(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .filter(r -> !"otkazana".equals(r.dohvatiStanje()))
                .count();
    }

    private List<Rezervacija> dohvatiSortiraneAktivneRezervacije(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .filter(r -> !"otkazana".equals(r.dohvatiStanje()))
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();
    }

    private Map<String, List<Rezervacija>> grupirajPoOsobi(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .collect(Collectors.groupingBy(r -> r.dohvatiIme() + r.dohvatiPrezime()));
    }

    private int postaviStanja(List<Rezervacija> rezervacije,
                                       Map<String, List<Rezervacija>> rezervacijePoOsobi,
                                       int minPutnika, int maxPutnika) {
        int trenutniBrojAktivnih = 0;

        for (Rezervacija rez : rezervacije) {
            String kljucOsobe = rez.dohvatiIme() + rez.dohvatiPrezime();
            List<Rezervacija> rezervacijeOsobe = rezervacijePoOsobi.get(kljucOsobe);

            if (imaDuplikatPrije(rez, rezervacijeOsobe)) {
                rez.promijeniStanje("na čekanju");
            } else if (trenutniBrojAktivnih < maxPutnika) {
                String stanje = trenutniBrojAktivnih < minPutnika ? "primljena" : "aktivna";
                rez.promijeniStanje(stanje);
                trenutniBrojAktivnih++;
            } else {
                rez.promijeniStanje("na čekanju");
            }
        }

        return trenutniBrojAktivnih;
    }

    private boolean imaDuplikatPrije(Rezervacija rez, List<Rezervacija> rezervacijeOsobe) {
        return rezervacijeOsobe.stream()
                .filter(r -> r.dohvatiDatumVrijemePrijema().isBefore(rez.dohvatiDatumVrijemePrijema()))
                .anyMatch(r -> !"na čekanju".equals(r.dohvatiStanje()));
    }

    private void prilagodiStanjaPremaMinimumu(List<Rezervacija> rezervacije, int trenutniBrojAktivnih, int minPutnika) {
        if (trenutniBrojAktivnih >= minPutnika) {
            rezervacije.stream()
                    .filter(r -> "primljena".equals(r.dohvatiStanje()))
                    .forEach(r -> r.promijeniStanje("aktivna"));
        } else {
            rezervacije.stream()
                    .filter(r -> "aktivna".equals(r.dohvatiStanje()))
                    .forEach(r -> r.promijeniStanje("primljena"));
        }
    }

    private Aranzman pronadjiAranzman(int oznaka) {
        return agencija.dohvatiAranzmane()
                .stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }
}