package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import java.util.ArrayList;
import java.util.List;

public class FormaterTablice {
    private int[] sirineKolona;
    private List<String[]> redovi;

    public FormaterTablice(int[] sirineKolona) {
        this.sirineKolona = sirineKolona;
        this.redovi = new ArrayList<>();
    }

    public void dodajRed(String... vrijednosti) {
        redovi.add(vrijednosti);
    }

    public String formatiraj() {
        StringBuilder sb = new StringBuilder();

        for (String[] red : redovi) {
            sb.append(ispisiLiniju()).append("\n");
            sb.append(ispisiRed(red)).append("\n");
        }
        sb.append(ispisiLiniju()).append("\n");

        return sb.toString();
    }

    private String ispisiLiniju() {
        StringBuilder sb = new StringBuilder("+");
        for (int sirina : sirineKolona) {
            sb.append("-".repeat(sirina + 2)).append("+");
        }
        return sb.toString();
    }

    private String ispisiRed(String[] vrijednosti) {
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
                sb.append(" ").append(String.format("%-" + sirineKolona[i] + "s", tekst)).append(" |");
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