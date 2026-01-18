package edu.unizg.foi.uzdiz.mmarkovin21.komande;

import edu.unizg.foi.uzdiz.mmarkovin21.TuristickaAgencija;
import edu.unizg.foi.uzdiz.mmarkovin21.composite.TuristickaKomponenta;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Aranzman;
import edu.unizg.foi.uzdiz.mmarkovin21.modeli.Rezervacija;
import edu.unizg.foi.uzdiz.mmarkovin21.visitor.PretrazivacVisitor;

import java.util.List;

public class KomandaPPTAR implements Komanda {
    private TuristickaAgencija agencija;
    int brojPronadjenih = 0;

    public KomandaPPTAR(TuristickaAgencija agencija) {
        this.agencija = agencija;
    }

    @Override
    public void izvrsi(String[] parametri) {
        if (parametri.length != 3) {
            System.out.println("Greška: Neispravan broj parametara za komandu PPTAR. Očekivano PPTAR [A|R] <riječ>.");
            return;
        }

        String komponentaPretrazivanja= parametri[1].toUpperCase();
        String rijec = parametri[2];

        System.out.println("Naziv komande: " + parametri[0] + " " + parametri[1] + " " + parametri[2] + "\n");

        switch (komponentaPretrazivanja) {
            case "A" -> pronadjiRjecUAranzmanima(rijec);
            case "R" -> pronadjiRjecURezervacijama(rijec);
            default -> System.out.println("Greška: Neispravan parametar. Koristite 'A' za aranžmane ili 'R' za rezervacije.");
        }

        if (brojPronadjenih > 0) {
            System.out.println("\nUkupno pronađeno rezervacija: " + brojPronadjenih);
        } else if (brojPronadjenih == 0) {
            System.out.println("Nema pronađenih rezervacija niti aranžmana koje sadrže riječ: " + rijec);
        }
    }

    private void pronadjiRjecUAranzmanima(String rijec) {
        List<Aranzman> aranzmani = agencija.dohvatiPodatke();
        PretrazivacVisitor visitor = new PretrazivacVisitor(rijec);

        for (Aranzman aranzman : aranzmani) {
            if (aranzman.prihvatiVisitora(visitor)) {
                brojPronadjenih++;
                System.out.println("Aranžman: " + aranzman.dohvatiNaziv() + " - " + aranzman.dohvatiProgram());
            }
        }
    }

    private void pronadjiRjecURezervacijama(String rijec) {
        List<Aranzman> aranzmani = agencija.dohvatiPodatke();
        PretrazivacVisitor visitor = new PretrazivacVisitor(rijec);

        for (Aranzman aranzman : aranzmani) {
            for (TuristickaKomponenta komponenta : aranzman.dohvatiDjecu()) {
                if (komponenta instanceof Rezervacija) {
                    Rezervacija rezervacija = (Rezervacija) komponenta;
                    if (rezervacija.prihvatiVisitora(visitor)) {
                        brojPronadjenih++;
                        System.out.println("Rezervacija: " + rezervacija.dohvatiIme() + " " +
                                rezervacija.dohvatiPrezime() + " " + aranzman.dohvatiOznaka());
                    }
                }
            }
        }
    }
}
