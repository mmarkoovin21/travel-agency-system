package edu.unizg.foi.uzdiz.mmarkovin21.mediator;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MediatorRezervacija {
    private static MediatorRezervacija instanca;
    private List<Aranzman> sviAranzmani;
    private final List<Rezervacija> sveRezervacije;

    private MediatorRezervacija() {
        this.sviAranzmani = new ArrayList<>();
        this.sveRezervacije = new ArrayList<>();
    }

    public static MediatorRezervacija dohvatiInstancu() {
        if (instanca == null) {
            instanca = new MediatorRezervacija();
        }
        return instanca;
    }

    public void postaviAranzmane(List<Aranzman> aranzmani) {
        this.sviAranzmani = aranzmani;
    }

    public boolean dodajRezervaciju(Rezervacija novaRez) {
        // 1. Pronađi aranžman po oznaci
        Aranzman aranzman = pronadjiAranzmanPoOznaci(novaRez.dohvatiOznakaAranzmana());
        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + novaRez.dohvatiOznakaAranzmana() + " ne postoji.");
            return false;
        }

        // 2. Provjeri: Ima li osoba već rezervaciju na istom aranžmanu?
        if (imaRezervacijuNaIstomAranzmanu(aranzman, novaRez)) {
            novaRez.odgodi();
            aranzman.dodajDijete(novaRez);
            sveRezervacije.add(novaRez);
            System.out.println("Rezervacija odgođena: korisnik već ima rezervaciju na ovom aranžmanu.");
            return true;
        }

        // 3. Provjeri: Ima li osoba AKTIVNU rezervaciju na preklapajućem aranžmanu?
        if (imaAktivnuNaPreklapajucemAranzmanu(aranzman, novaRez)) {
            novaRez.odgodi();
            aranzman.dodajDijete(novaRez);
            sveRezervacije.add(novaRez);
            System.out.println("Rezervacija odgođena: korisnik ima aktivnu rezervaciju na preklapajućem aranžmanu.");
            return true;
        }

        // 4. Izračunaj broj brojivih rezervacija + 1 (nova rezervacija)
        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman) + 1;

        // 5. Odredi inicijalno stanje nove rezervacije
        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            novaRez.primi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            novaRez.aktiviraj();
        } else {
            novaRez.staviNaCekanje();
        }

        // 6. Dodaj rezervaciju
        aranzman.dodajDijete(novaRez);
        sveRezervacije.add(novaRez);

        // 7. Ažuriraj stanje aranžmana
        azurirajStanjeAranzmana(aranzman);

        // 8. Ažuriraj stanja SVIH rezervacija na aranžmanu
        azurirajStanjaRezervacija(aranzman);

        return true;
    }

    public boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        // 1. Pronađi rezervaciju
        Rezervacija rezervacija = pronadjiRezervaciju(ime, prezime, oznakaAranzmana);
        if (rezervacija == null) {
            return false;
        }

        // 2. Otkaži rezervaciju
        String prethodnoStanje = rezervacija.dohvatiStanjeString();
        rezervacija.otkazi();

        // 3. Pronađi nadređeni aranžman
        Aranzman aranzman = rezervacija.dohvatiAranzman();
        if (aranzman == null) {
            return true; // Rezervacija je otkazana, ali nema aranžmana za ažuriranje
        }

        // 4. Ažuriraj stanje aranžmana
        azurirajStanjeAranzmana(aranzman);

        // 5. Ažuriraj stanja rezervacija
        azurirajStanjaRezervacija(aranzman);

        // 6. Pokušaj aktivirati odgođene rezervacije iste osobe
        if (prethodnoStanje.equals("ODGOĐENA")) {
            pokusajAktiviratiOdgodjenuRezervaciju(aranzman, rezervacija);
        }

        return true;
    }


    private void azurirajStanjeAranzmana(Aranzman aranzman) {
        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman);

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            aranzman.pripremi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            aranzman.aktiviraj();
        } else {
            aranzman.popuni();
        }
    }

    private void azurirajStanjaRezervacija(Aranzman aranzman) {
        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        int brojBrojivih = brojiveRezervacije.size();
        int minBrojPutnika = aranzman.dohvatiMinBrojPutnika();
        int maxBrojPutnika = aranzman.dohvatiMaxBrojPutnika();

        if (brojBrojivih < minBrojPutnika) {
            // SVE rezervacije → PRIMLJENA
            for (Rezervacija rez : brojiveRezervacije) {
                rez.primi();
            }
        } else if (brojBrojivih <= maxBrojPutnika) {
            // SVE rezervacije → AKTIVNA
            for (Rezervacija rez : brojiveRezervacije) {
                rez.aktiviraj();
            }
        } else {
            // Prvih maxBrojPutnika → AKTIVNA, ostale → NA ČEKANJU
            for (int i = 0; i < brojiveRezervacije.size(); i++) {
                Rezervacija rez = brojiveRezervacije.get(i);
                if (i < maxBrojPutnika) {
                    rez.aktiviraj();
                } else {
                    rez.staviNaCekanje();
                }
            }
        }
    }

    private boolean imaRezervacijuNaIstomAranzmanu(Aranzman aranzman, Rezervacija novaRez) {
        return dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .anyMatch(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()));
    }

    private boolean imaAktivnuNaPreklapajucemAranzmanu(Aranzman trenutniAranzman, Rezervacija novaRez) {
        for (Aranzman aranzman : sviAranzmani) {
            // Preskoči trenutni aranžman
            if (aranzman.dohvatiOznaka() == trenutniAranzman.dohvatiOznaka()) {
                continue;
            }

            // Provjeri da li se aranžmani preklapaju po datumu
            if (!aranzmaniSePreklapaju(trenutniAranzman, aranzman)) {
                continue;
            }

            // Provjeri ima li ista osoba aktivnu rezervaciju na ovom aranžmanu
            boolean imaAktivnu = dohvatiRezervacijeAranzmana(aranzman).stream()
                    .filter(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                            && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()))
                    .anyMatch(rez -> rez.dohvatiStanjeString().equals("AKTIVNA"));

            if (imaAktivnu) {
                return true;
            }
        }
        return false;
    }

    private boolean aranzmaniSePreklapaju(Aranzman a1, Aranzman a2) {
        LocalDate pocetak1 = a1.dohvatiPocetniDatum();
        LocalDate kraj1 = a1.dohvatiZavrsniDatum();
        LocalDate pocetak2 = a2.dohvatiPocetniDatum();
        LocalDate kraj2 = a2.dohvatiZavrsniDatum();

        return !pocetak1.isAfter(kraj2) && !kraj1.isBefore(pocetak2);
    }

    private int izracunajBrojBrojivihRezervacija(Aranzman aranzman) {
        return (int) dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .count();
    }

    private boolean jeBrojiva(Rezervacija rezervacija) {
        String stanje = rezervacija.dohvatiStanjeString();
        return stanje.equals("PRIMLJENA")
                || stanje.equals("AKTIVNA")
                || stanje.equals("NA ČEKANJU");
    }

    private List<Rezervacija> dohvatiRezervacijeAranzmana(Aranzman aranzman) {
        List<Rezervacija> rezervacije = new ArrayList<>();
        for (TuristickaKomponenta komponenta : aranzman.dohvatiDjecu()) {
            if (komponenta instanceof Rezervacija) {
                rezervacije.add((Rezervacija) komponenta);
            }
        }
        return rezervacije;
    }

    private Aranzman pronadjiAranzmanPoOznaci(int oznaka) {
        return sviAranzmani.stream()
                .filter(a -> a.dohvatiOznaka() == oznaka)
                .findFirst()
                .orElse(null);
    }

    private Rezervacija pronadjiRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        return sveRezervacije.stream()
                .filter(rez -> rez.dohvatiIme().equals(ime)
                        && rez.dohvatiPrezime().equals(prezime)
                        && rez.dohvatiOznakaAranzmana() == oznakaAranzmana
                        && !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .findFirst()
                .orElse(null);
    }

    private void pokusajAktiviratiOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez) {
        List<Rezervacija> odgodeneRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> rez.dohvatiStanjeString().equals("ODGOĐENA"))
                .filter(rez -> rez.dohvatiIme().equals(otkazanaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(otkazanaRez.dohvatiPrezime()))
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        if (!odgodeneRezervacije.isEmpty()) {
            Rezervacija prvaOdgodena = odgodeneRezervacije.get(0);

            // Provjeri nema li aktivnu na preklapajućem aranžmanu
            if (!imaAktivnuNaPreklapajucemAranzmanu(aranzman, prvaOdgodena)) {
                int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman) + 1;

                if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
                    prvaOdgodena.primi();
                } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
                    prvaOdgodena.aktiviraj();
                } else {
                    prvaOdgodena.staviNaCekanje();
                }

                azurirajStanjeAranzmana(aranzman);
                azurirajStanjaRezervacija(aranzman);
            }
        }
    }
}
