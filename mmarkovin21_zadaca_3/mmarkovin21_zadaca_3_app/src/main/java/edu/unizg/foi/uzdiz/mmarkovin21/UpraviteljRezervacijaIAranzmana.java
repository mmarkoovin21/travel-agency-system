package edu.unizg.foi.uzdiz.mmarkovin21;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UpraviteljRezervacijaIAranzmana {
    private static UpraviteljRezervacijaIAranzmana instanca;
    private List<Aranzman> sviAranzmani;
    private final List<Rezervacija> sveRezervacije;

    private UpraviteljRezervacijaIAranzmana() {
        this.sviAranzmani = new ArrayList<>();
        this.sveRezervacije = new ArrayList<>();
    }

    public static UpraviteljRezervacijaIAranzmana dohvatiInstancu() {
        if (instanca == null) {
            instanca = new UpraviteljRezervacijaIAranzmana();
        }
        return instanca;
    }

    public void postaviAranzmane(List<Aranzman> aranzmani) {
        this.sviAranzmani = aranzmani;
    }

    public boolean dodajRezervaciju(Rezervacija novaRez) {

        Aranzman aranzman = pronadjiAranzmanPoOznaci(novaRez.dohvatiOznakaAranzmana());
        if (aranzman == null) {
            System.out.println("Greška: Aranžman s oznakom " + novaRez.dohvatiOznakaAranzmana() + " ne postoji.");
            return false;
        }

        Rezervacija postojecaRez = pronadjiPostojecuRezervaciju(aranzman, novaRez);
        if (postojecaRez != null) {
            if (novaRez.dohvatiDatumVrijemePrijema().isAfter(postojecaRez.dohvatiDatumVrijemePrijema())) {
                novaRez.odgodi();
                aranzman.dodajDijete(novaRez);
                sveRezervacije.add(novaRez);
                System.out.println("Rezervacija odgođena: korisnik " + novaRez.dohvatiIme() + " " + novaRez.dohvatiPrezime() + " već ima raniju rezervaciju na ovom aranžmanu.");
                return true;
            } else {
                postojecaRez.odgodi();
                System.out.println("Postojeća rezervacija odgođena: korisnik " + postojecaRez.dohvatiIme() + " " + postojecaRez.dohvatiPrezime() + " ima raniju rezervaciju.");
                azurirajStanjeAranzmana(aranzman);
                azurirajStanjaRezervacija(aranzman);
            }
        }

        Rezervacija rezNaPreklapajucem = pronadjiRezervacijuNaPreklapajucemAranzmanu(aranzman, novaRez);
        if (rezNaPreklapajucem != null) {
            if (novaRez.dohvatiDatumVrijemePrijema().isAfter(rezNaPreklapajucem.dohvatiDatumVrijemePrijema())) {
                novaRez.odgodi();
                aranzman.dodajDijete(novaRez);
                sveRezervacije.add(novaRez);
                System.out.println("Rezervacija odgođena: korisnik " + novaRez.dohvatiIme() + " " + novaRez.dohvatiPrezime() + " ima raniju rezervaciju na preklapajućem aranžmanu.");
                return true;
            } else {
                rezNaPreklapajucem.odgodi();
                System.out.println("Postojeća rezervacija na preklapajućem aranžmanu odgođena: korisnik " + rezNaPreklapajucem.dohvatiIme() + " " + rezNaPreklapajucem.dohvatiPrezime() + " ima raniju rezervaciju.");
                Aranzman aranzmanPreklapajuce = rezNaPreklapajucem.dohvatiAranzman();
                if (aranzmanPreklapajuce != null) {
                    azurirajStanjeAranzmana(aranzmanPreklapajuce);
                    azurirajStanjaRezervacija(aranzmanPreklapajuce);
                }
            }
        }

        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman) + 1;

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            novaRez.primi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            novaRez.aktiviraj();
        } else {
            novaRez.staviNaCekanje();
        }

        aranzman.dodajDijete(novaRez);
        sveRezervacije.add(novaRez);

        azurirajStanjeAranzmana(aranzman);
        azurirajStanjaRezervacija(aranzman);

        return true;
    }

    public boolean otkaziRezervaciju(String ime, String prezime, int oznakaAranzmana) {
        Rezervacija rezervacija = pronadjiRezervaciju(ime, prezime, oznakaAranzmana);
        if (rezervacija == null) {
            return false;
        }

        String prethodnoStanje = rezervacija.dohvatiStanjeString();
        rezervacija.otkazi();

        Aranzman aranzman = rezervacija.dohvatiAranzman();
        if (aranzman == null) {
            return true;
        }

        azurirajStanjeAranzmana(aranzman);
        azurirajStanjaRezervacija(aranzman);

        if (prethodnoStanje.equals("ODGOĐENA")) {
            pokusajAktiviratiOdgodjenuRezervaciju(aranzman, rezervacija);
        }

        return true;
    }

    public void azurirajStanjeAranzmana(Aranzman aranzman) {
        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman);

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            aranzman.pripremi();
        } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            aranzman.aktiviraj();
        } else {
            aranzman.popuni();
        }
    }

    public void azurirajStanjaRezervacija(Aranzman aranzman) {
        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        int brojBrojivih = brojiveRezervacije.size();
        int minBrojPutnika = aranzman.dohvatiMinBrojPutnika();
        int maxBrojPutnika = aranzman.dohvatiMaxBrojPutnika();

        if (brojBrojivih < minBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                rez.primi();
            }
        } else if (brojBrojivih <= maxBrojPutnika) {
            for (Rezervacija rez : brojiveRezervacije) {
                rez.aktiviraj();
            }
        } else {
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

    private Rezervacija pronadjiPostojecuRezervaciju(Aranzman aranzman, Rezervacija novaRez) {
        return dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> !rez.dohvatiStanjeString().equals("OTKAZANA"))
                .filter(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()))
                .findFirst()
                .orElse(null);
    }

    private boolean imaAktivnuNaPreklapajucemAranzmanu(Aranzman trenutniAranzman, Rezervacija novaRez) {
        for (Aranzman aranzman : sviAranzmani) {
            if (aranzman.dohvatiOznaka() == trenutniAranzman.dohvatiOznaka()) {
                continue;
            }

            if (!aranzmaniSePreklapaju(trenutniAranzman, aranzman)) {
                continue;
            }

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

    private Rezervacija pronadjiRezervacijuNaPreklapajucemAranzmanu(Aranzman trenutniAranzman, Rezervacija novaRez) {
        for (Aranzman aranzman : sviAranzmani) {
            if (aranzman.dohvatiOznaka() == trenutniAranzman.dohvatiOznaka()) {
                continue;
            }

            if (!aranzmaniSePreklapaju(trenutniAranzman, aranzman)) {
                continue;
            }

            Rezervacija postojeca = dohvatiRezervacijeAranzmana(aranzman).stream()
                    .filter(rez -> rez.dohvatiIme().equals(novaRez.dohvatiIme())
                            && rez.dohvatiPrezime().equals(novaRez.dohvatiPrezime()))
                    .filter(rez -> !rez.dohvatiStanjeString().equals("OTKAZANA")
                            && !rez.dohvatiStanjeString().equals("ODGOĐENA"))
                    .findFirst()
                    .orElse(null);

            if (postojeca != null) {
                return postojeca;
            }
        }
        return null;
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

    public void obrisiSveAranzmane() {
        sviAranzmani = new ArrayList<>();
        sveRezervacije.clear();
    }

    public void obrisiSveRezervacije() {
        sveRezervacije.clear();
    }
}
