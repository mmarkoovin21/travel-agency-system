package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.StanjeRezervacije;
import edu.unizg.foi.uzdiz.mmarkovin21.pomocnici.RezervacijaFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UpraviteljStanjaRezervacija {
    private final TuristickaAgencija agencija;

    public UpraviteljStanjaRezervacija(){
        this.agencija = TuristickaAgencija.dohvatiInstancu();
    }

    public StanjeRezervacije odrediPocetnoStanje(int oznakaAranzmana, List<Rezervacija> postojeceRezervacije,
                                                  String ime, String prezime) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            System.err.println("Aranžman s oznakom " + oznakaAranzmana + " nije pronađen.");
            return null;
        }

        if (imaAktivnuRezervacijuNaPreklapajucemAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            System.err.println("Korisnik " + ime + " " + prezime + " već ima aktivnu rezervaciju na preklapajućem aranžmanu.");
            return null;
        }

        if (imaRezervacijuNaIstomAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            System.err.println("Korisnik " + ime + " " + prezime + " već ima rezervaciju na ovom aranžmanu.");
            return null;
        }

        long brojAktivnih = brojRezervacijaBezOtkazanih(oznakaAranzmana, postojeceRezervacije) + 1;

        if (brojAktivnih > aranzman.dohvatiMaxBrojPutnika()) {
            return StanjeRezervacije.NA_CEKANJU;
        } else if (brojAktivnih >= aranzman.dohvatiMinBrojPutnika()) {
            return StanjeRezervacije.AKTIVNA;
        } else {
            return StanjeRezervacije.PRIMLJENA;
        }
    }

    public void azurirajStanja(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        Aranzman aranzman = pronadjiAranzman(oznakaAranzmana);
        if (aranzman == null) {
            return;
        }

        List<Rezervacija> aktivneRezervacije = dohvatiSortiraneAktivneRezervacije(oznakaAranzmana, rezervacije);
        List<Rezervacija> neispravneRezervacije = new ArrayList<>();

        int trenutniBrojAktivnih = 0;
        Set<String> obradjeneOsobe = new HashSet<>();

        for (Rezervacija rez : aktivneRezervacije) {
            String kljucOsobe = rez.dohvatiIme() + "|" + rez.dohvatiPrezime();

            if (obradjeneOsobe.contains(kljucOsobe)) {
                System.err.println("Neispravna rezervacija (duplikat na istom aranžmanu): " + rez.dohvatiIme() + " " +
                        rez.dohvatiPrezime() + " za aranžman " + oznakaAranzmana);
                neispravneRezervacije.add(rez);
                continue;
            }

            if (imaAktivnuRezervacijuNaPreklapajucemAranzmanu(oznakaAranzmana, rezervacije,
                    rez.dohvatiIme(), rez.dohvatiPrezime())) {
                System.err.println("Neispravna rezervacija (postoji aktivna na preklapajućem aranžmanu): " +
                        rez.dohvatiIme() + " " + rez.dohvatiPrezime() +
                        " za aranžman " + oznakaAranzmana);
                neispravneRezervacije.add(rez);
                continue;
            }

            if (trenutniBrojAktivnih < aranzman.dohvatiMaxBrojPutnika()) {
                StanjeRezervacije stanje = trenutniBrojAktivnih < aranzman.dohvatiMinBrojPutnika()
                    ? StanjeRezervacije.PRIMLJENA
                    : StanjeRezervacije.AKTIVNA;
                rez.promijeniStanje(stanje);
                trenutniBrojAktivnih++;
                obradjeneOsobe.add(kljucOsobe);
            } else {
                rez.promijeniStanje(StanjeRezervacije.NA_CEKANJU);
            }
        }

        prilagodiStanjaPremaMinimumu(aktivneRezervacije, trenutniBrojAktivnih, aranzman.dohvatiMinBrojPutnika());

        rezervacije.removeAll(neispravneRezervacije);
        agencija.dohvatiRezervacije().removeAll(neispravneRezervacije);
    }

    public boolean otkazirezervaciju(String ime, String prezime, int oznakaAranzmana) {
        Rezervacija otkazana = agencija.dohvatiRezervacije().stream()
                .filter(RezervacijaFilter.zaOsobu(ime, prezime))
                .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                .filter(RezervacijaFilter.nijeOtkazana())
                .findFirst()
                .orElse(null);

        if (otkazana != null) {
            StanjeRezervacije prethodnoStanje = otkazana.dohvatiStanje();
            otkazana.promijeniStanje(StanjeRezervacije.OTKAZANA);
            otkazana.postaviDatumVrijemeOtkazivanja(LocalDateTime.now());

            if (prethodnoStanje == StanjeRezervacije.AKTIVNA) {
                aktivirajPrvuIspravnuSaCekanja(oznakaAranzmana);
            }

            azurirajStanja(oznakaAranzmana, agencija.dohvatiRezervacije());
            return true;
        } else {
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

        StanjeRezervacije pocetnoStanje = odrediPocetnoStanje(
                novaRezervacija.dohvatiOznakaAranzmana(),
                postojeceRezervacije,
                novaRezervacija.dohvatiIme(),
                novaRezervacija.dohvatiPrezime()
        );

        if (pocetnoStanje == null) {
            System.err.println("Neispravna rezervacija - nije dodana.");
            return false;
        }

        novaRezervacija.promijeniStanje(pocetnoStanje);
        agencija.dohvatiRezervacije().add(novaRezervacija);

        azurirajStanja(novaRezervacija.dohvatiOznakaAranzmana(), agencija.dohvatiRezervacije());

        return true;
    }

    private void aktivirajPrvuIspravnuSaCekanja(int oznakaAranzmana) {
        List<Rezervacija> naCekanju = agencija.dohvatiRezervacije().stream()
                .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                .filter(RezervacijaFilter.naCekanju())
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        for (Rezervacija rez : naCekanju) {
            if (!imaAktivnuRezervacijuNaPreklapajucemAranzmanu(oznakaAranzmana, agencija.dohvatiRezervacije(),
                    rez.dohvatiIme(), rez.dohvatiPrezime())) {
                return;
            } else {
                System.err.println("Neispravna rezervacija sa čekanja (ima aktivnu na preklapajućem aranžmanu): " +
                        rez.dohvatiIme() + " " + rez.dohvatiPrezime());
            }
        }
    }

    private boolean imaAktivnuRezervacijuNaPreklapajucemAranzmanu(int oznakaAranzmana,
                                                                  List<Rezervacija> rezervacije,
                                                                  String ime, String prezime) {
        Aranzman trenutniAranzman = pronadjiAranzman(oznakaAranzmana);
        if (trenutniAranzman == null) {
            return false;
        }

        return rezervacije.stream()
                .filter(RezervacijaFilter.drugiAranzmani(oznakaAranzmana))
                .filter(RezervacijaFilter.zaOsobu(ime, prezime))
                .filter(RezervacijaFilter.nijeOtkazana())
                .anyMatch(r -> {
                    Aranzman drugiAranzman = pronadjiAranzman(r.dohvatiOznakaAranzmana());
                    return drugiAranzman != null && aranzmaniSePreklapaju(trenutniAranzman, drugiAranzman);
                });
    }

    private boolean aranzmaniSePreklapaju(Aranzman a1, Aranzman a2) {
        LocalDate pocetak1 = a1.dohvatiPocetniDatum();
        LocalDate kraj1 = a1.dohvatiZavrsniDatum();
        LocalDate pocetak2 = a2.dohvatiPocetniDatum();
        LocalDate kraj2 = a2.dohvatiZavrsniDatum();

        return !pocetak1.isAfter(kraj2) && !kraj1.isBefore(pocetak2);
    }

    private boolean imaRezervacijuNaIstomAranzmanu(int oznakaAranzmana,
                                                   List<Rezervacija> rezervacije,
                                                   String ime, String prezime) {
        return rezervacije.stream()
                .filter(RezervacijaFilter.zaAranzman(oznakaAranzmana))
                .filter(RezervacijaFilter.zaOsobu(ime, prezime))
                .anyMatch(RezervacijaFilter.nijeOtkazana());
    }

    private long brojRezervacijaBezOtkazanih(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .filter(RezervacijaFilter.aktivneZaAranzman(oznakaAranzmana))
                .count();
    }

    private List<Rezervacija> dohvatiSortiraneAktivneRezervacije(int oznakaAranzmana, List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .filter(RezervacijaFilter.aktivneZaAranzman(oznakaAranzmana))
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .collect(Collectors.toList());
    }

    private void prilagodiStanjaPremaMinimumu(List<Rezervacija> rezervacije, int trenutniBrojAktivnih, int minPutnika) {
        if (trenutniBrojAktivnih >= minPutnika) {
            rezervacije.stream()
                    .filter(RezervacijaFilter.uStanju(StanjeRezervacije.PRIMLJENA))
                    .forEach(r -> r.promijeniStanje(StanjeRezervacije.AKTIVNA));
        } else {
            rezervacije.stream()
                    .filter(RezervacijaFilter.uStanju(StanjeRezervacije.AKTIVNA))
                    .forEach(r -> r.promijeniStanje(StanjeRezervacije.PRIMLJENA));
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