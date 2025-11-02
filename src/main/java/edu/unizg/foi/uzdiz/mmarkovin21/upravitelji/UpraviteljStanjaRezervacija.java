package edu.unizg.foi.uzdiz.mmarkovin21.upravitelji;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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

        if (imaAktivnuRezervacijuNaPreklapajucemAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            System.err.println("Korisnik " + ime + " " + prezime + " već ima aktivnu rezervaciju na preklapajućem aranžmanu.");
            return "";
        }

        if (imaRezervacijuNaIstomAranzmanu(oznakaAranzmana, postojeceRezervacije, ime, prezime)) {
            System.err.println("Korisnik " + ime + " " + prezime + " već ima rezervaciju na ovom aranžmanu.");
            return "";
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
                String stanje = trenutniBrojAktivnih < aranzman.dohvatiMinBrojPutnika() ? "primljena" : "aktivna";
                rez.promijeniStanje(stanje);
                trenutniBrojAktivnih++;
                obradjeneOsobe.add(kljucOsobe);
            } else {
                rez.promijeniStanje("na čekanju");
            }
        }

        prilagodiStanjaPremaMinimumu(aktivneRezervacije, trenutniBrojAktivnih, aranzman.dohvatiMinBrojPutnika());

        rezervacije.removeAll(neispravneRezervacije);
        agencija.dohvatiRezervacije().removeAll(neispravneRezervacije);
    }

    public boolean otkazirezervaciju(String ime, String prezime, int oznakaAranzmana) {
        Rezervacija otkazana = agencija.dohvatiRezervacije().stream()
                .filter(r -> r.dohvatiIme().equalsIgnoreCase(ime) &&
                        r.dohvatiPrezime().equalsIgnoreCase(prezime) &&
                        r.dohvatiOznakaAranzmana() == oznakaAranzmana &&
                        !"otkazana".equals(r.dohvatiStanje()))
                .findFirst()
                .orElse(null);

        if (otkazana != null) {
            String prethodnoStanje = otkazana.dohvatiStanje();
            otkazana.promijeniStanje("otkazana");
            otkazana.postaviDatumVrijemeOtkazivanja(LocalDateTime.now());

            if ("aktivna".equals(prethodnoStanje)) {
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

        String pocetnoStanje = odrediPocetnoStanje(
                novaRezervacija.dohvatiOznakaAranzmana(),
                postojeceRezervacije,
                novaRezervacija.dohvatiIme(),
                novaRezervacija.dohvatiPrezime()
        );

        if (pocetnoStanje.isEmpty()) {
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
                .filter(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana)
                .filter(r -> "na čekanju".equals(r.dohvatiStanje()))
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
                .filter(r -> r.dohvatiOznakaAranzmana() != oznakaAranzmana)
                .filter(r -> r.dohvatiIme().equalsIgnoreCase(ime))
                .filter(r -> r.dohvatiPrezime().equalsIgnoreCase(prezime))
                .filter(r -> !"otkazana".equals(r.dohvatiStanje()))
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
                .anyMatch(r -> r.dohvatiOznakaAranzmana() == oznakaAranzmana &&
                        r.dohvatiIme().equalsIgnoreCase(ime) &&
                        r.dohvatiPrezime().equalsIgnoreCase(prezime) &&
                        !"otkazana".equals(r.dohvatiStanje()));
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
                .collect(Collectors.toList());
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