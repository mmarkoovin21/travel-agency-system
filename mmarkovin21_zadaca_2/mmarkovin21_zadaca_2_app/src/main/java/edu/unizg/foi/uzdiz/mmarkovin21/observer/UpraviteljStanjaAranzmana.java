package edu.unizg.foi.uzdiz.mmarkovin21.observer;

import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class UpraviteljStanjaAranzmana implements AranzmanObserver {
    private static UpraviteljStanjaAranzmana instanca;
    private List<Aranzman> sviAranzmani;

    private UpraviteljStanjaAranzmana() {
        this.sviAranzmani = new ArrayList<>();
    }

    public static UpraviteljStanjaAranzmana dohvatiInstancu() {
        if (instanca == null) {
            instanca = new UpraviteljStanjaAranzmana();
        }
        return instanca;
    }

    public void postaviAranzmane(List<Aranzman> aranzmani) {
        this.sviAranzmani = aranzmani;
    }

    @Override
    public void obavijesti(Aranzman aranzman) {
        azurirajStanjaAranzmana(aranzman);
    }

    private void azurirajStanjaAranzmana(Aranzman aranzman) {
        int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman);

        if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
            aranzman.pripremi();
            postaviSveRezervacijeUStanje(aranzman, "PRIMLJENA");
        } else if (brojBrojivih >= aranzman.dohvatiMinBrojPutnika()
                && brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
            aranzman.aktiviraj();
            azurirajRezervacijeUAktivne(aranzman);
        } else {
            aranzman.popuni();
            azurirajRezervacijeKadJePopunjeno(aranzman);
        }
    }

    private int izracunajBrojBrojivihRezervacija(Aranzman aranzman) {
        return (int) aranzman.dohvatiDjecu().stream()
                .filter(komponenta -> komponenta instanceof Rezervacija)
                .map(komponenta -> (Rezervacija) komponenta)
                .filter(this::jeBrojiva)
                .count();
    }

    private boolean jeBrojiva(Rezervacija rezervacija) {
        String stanje = rezervacija.dohvatiStanjeString();
        return stanje.equals("PRIMLJENA")
                || stanje.equals("AKTIVNA")
                || stanje.equals("NA ČEKANJU");
    }

    private void postaviSveRezervacijeUStanje(Aranzman aranzman, String stanje) {
        List<Rezervacija> rezervacije = dohvatiRezervacijeAranzmana(aranzman);
        for (Rezervacija rez : rezervacije) {
            if (jeBrojiva(rez)) {
                switch (stanje) {
                    case "PRIMLJENA":
                        rez.primi();
                        break;
                    case "AKTIVNA":
                        rez.aktiviraj();
                        break;
                    case "NA ČEKANJU":
                        rez.staviNaCekanje();
                        break;
                }
            }
        }
    }

    private void azurirajRezervacijeUAktivne(Aranzman aranzman) {
        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        for (Rezervacija rez : brojiveRezervacije) {
            rez.aktiviraj();
        }
    }

    private void azurirajRezervacijeKadJePopunjeno(Aranzman aranzman) {
        int maxPutnika = aranzman.dohvatiMaxBrojPutnika();

        List<Rezervacija> brojiveRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(this::jeBrojiva)
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        for (int i = 0; i < brojiveRezervacije.size(); i++) {
            Rezervacija rez = brojiveRezervacije.get(i);
            if (i < maxPutnika) {
                rez.aktiviraj();
            } else {
                rez.staviNaCekanje();
            }
        }
    }

    public void dodajRezervaciju(Aranzman aranzman, Rezervacija novaRez) {
        if (imaRezervacijuNaIstomAranzmanu(aranzman, novaRez)) {
            novaRez.odgodi();
            aranzman.dodajDijete(novaRez);
            System.out.println("Rezervacija odgođena: korisnik već ima rezervaciju na ovom aranžmanu");
            return;
        }

        if (imaAktivnuNaPreklapajucemAranzmanu(aranzman, novaRez)) {
            novaRez.odgodi();
            aranzman.dodajDijete(novaRez);
            System.out.println("Rezervacija odgođena: korisnik ima aktivnu rezervaciju na preklapajućem aranžmanu");
            return;
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
        aranzman.obavijestObservere();
    }

    public void otkaziRezervaciju(Rezervacija rezervacija) {
        if (rezervacija == null) {
            return;
        }

        String prethodnoStanje = rezervacija.dohvatiStanjeString();
        rezervacija.otkazi();

        Aranzman aranzman = rezervacija.dohvatiAranzman();

        if (prethodnoStanje.equals("ODGOĐENA")) {
            pokusajAktiviratiDruguOdgodjenuRezervaciju(aranzman, rezervacija);
        } else {
            aranzman.obavijestObservere();
        }
    }

    private void pokusajAktiviratiDruguOdgodjenuRezervaciju(Aranzman aranzman, Rezervacija otkazanaRez) {
        List<Rezervacija> odgodeneRezervacije = dohvatiRezervacijeAranzmana(aranzman).stream()
                .filter(rez -> rez.dohvatiStanjeString().equals("ODGOĐENA"))
                .filter(rez -> rez.dohvatiIme().equals(otkazanaRez.dohvatiIme())
                        && rez.dohvatiPrezime().equals(otkazanaRez.dohvatiPrezime()))
                .sorted(Comparator.comparing(Rezervacija::dohvatiDatumVrijemePrijema))
                .toList();

        if (!odgodeneRezervacije.isEmpty()) {
            Rezervacija prvaOdgodena = odgodeneRezervacije.getFirst();

            if (!imaAktivnuNaPreklapajucemAranzmanu(aranzman, prvaOdgodena)) {
                int brojBrojivih = izracunajBrojBrojivihRezervacija(aranzman) + 1;

                if (brojBrojivih < aranzman.dohvatiMinBrojPutnika()) {
                    prvaOdgodena.primi();
                } else if (brojBrojivih <= aranzman.dohvatiMaxBrojPutnika()) {
                    prvaOdgodena.aktiviraj();
                } else {
                    prvaOdgodena.staviNaCekanje();
                }

                aranzman.obavijestObservere();
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

    private boolean aranzmaniSePreklapaju(Aranzman a1, Aranzman a2) {
        LocalDate pocetak1 = a1.dohvatiPocetniDatum();
        LocalDate kraj1 = a1.dohvatiZavrsniDatum();
        LocalDate pocetak2 = a2.dohvatiPocetniDatum();
        LocalDate kraj2 = a2.dohvatiZavrsniDatum();

        return !pocetak1.isAfter(kraj2) && !kraj1.isBefore(pocetak2);
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
}
