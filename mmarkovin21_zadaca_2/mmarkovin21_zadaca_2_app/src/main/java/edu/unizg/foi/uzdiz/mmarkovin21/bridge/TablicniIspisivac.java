package edu.unizg.foi.uzdiz.mmarkovin21.bridge;

import java.util.ArrayList;
import java.util.List;

public abstract class TablicniIspisivac {

    protected TablicniFormater formater;

    public TablicniIspisivac(TablicniFormater formater) {
        this.formater = formater;
    }

    public void ispisi(List<?> podaci) {
        if (podaci == null || podaci.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
            return;
        }

        int[] sirineKolona = formater.definirajSirineKolona();
        boolean[] brojcanaPolja = formater.definirajBrojcanaPolja();
        List<String[]> redovi = new ArrayList<>();

        redovi.add(formater.kreirajZaglavlje());

        for (Object podatak : podaci) {
            String[] red = formater.formatirajRed(podatak);
            redovi.add(red);
        }

        System.out.println(formatirajTablicu(sirineKolona, brojcanaPolja, redovi));
    }

    private String formatirajTablicu(int[] sirineKolona, boolean[] brojcanaPolja, List<String[]> redovi) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < redovi.size(); i++) {
            String[] red = redovi.get(i);
            if (red.length != sirineKolona.length) {
                throw new IllegalArgumentException(
                        "Broj vrijednosti (" + red.length +
                                ") ne odgovara broju kolona (" + sirineKolona.length + ")"
                );
            }
            sb.append(ispisiLiniju(sirineKolona)).append("\n");
            boolean jeZaglavlje = (i == 0);
            sb.append(ispisiRed(red, sirineKolona, brojcanaPolja, jeZaglavlje)).append("\n");
        }
        sb.append(ispisiLiniju(sirineKolona)).append("\n");

        return sb.toString();
    }

    private String ispisiLiniju(int[] sirineKolona) {
        StringBuilder sb = new StringBuilder("+");
        for (int sirina : sirineKolona) {
            sb.append("-".repeat(sirina + 2)).append("+");
        }
        return sb.toString();
    }

    private String ispisiRed(String[] vrijednosti, int[] sirineKolona, boolean[] brojcanaPolja, boolean jeZaglavlje) {
        StringBuilder sb = new StringBuilder();
        String[][] omotaneVrijednosti = new String[vrijednosti.length][];
        int maxLinija = 1;

        for (int i = 0; i < vrijednosti.length; i++) {
            omotaneVrijednosti[i] = omotajTekst(vrijednosti[i], sirineKolona[i]);
            maxLinija = Math.max(maxLinija, omotaneVrijednosti[i].length);
        }

        for (int linija = 0; linija < maxLinija; linija++) {
            sb.append("|");
            for (int i = 0; i < sirineKolona.length; i++) {
                String tekst = linija < omotaneVrijednosti[i].length
                        ? omotaneVrijednosti[i][linija]
                        : "";

                String formatiraniTekst;
                if (jeZaglavlje || !brojcanaPolja[i]) {
                    formatiraniTekst = String.format("%-" + sirineKolona[i] + "s", tekst);
                } else {
                    formatiraniTekst = String.format("%" + sirineKolona[i] + "s", tekst);
                }

                sb.append(" ").append(formatiraniTekst).append(" |");
            }
            if (linija < maxLinija - 1) sb.append("\n");
        }
        return sb.toString();
    }

    private String[] omotajTekst(String tekst, int maxSirina) {
        if (tekst == null) tekst = "";
        if (tekst.length() <= maxSirina) return new String[]{tekst};

        List<String> linije = new ArrayList<>();
        int pos = 0;

        while (pos < tekst.length()) {
            int kraj = Math.min(pos + maxSirina, tekst.length());
            if (kraj < tekst.length()) {
                int zadnjiRazmak = tekst.lastIndexOf(' ', kraj);
                if (zadnjiRazmak > pos) kraj = zadnjiRazmak;
            }
            linije.add(tekst.substring(pos, kraj).trim());
            pos = kraj + 1;
        }

        return linije.toArray(new String[0]);
    }
}
