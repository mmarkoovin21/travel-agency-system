package edu.unizg.foi.uzdiz.mmarkovin21.modeli;


public enum StanjeRezervacije {
    PRIMLJENA("primljena"),
    AKTIVNA("aktivna"),
    NA_CEKANJU("na čekanju"),
    OTKAZANA("otkazana");

    private final String vrijednost;

    StanjeRezervacije(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }

    public static StanjeRezervacije fromString(String vrijednost) {
        if (vrijednost == null) {
            return null;
        }
        for (StanjeRezervacije stanje : StanjeRezervacije.values()) {
            if (stanje.vrijednost.equalsIgnoreCase(vrijednost)) {
                return stanje;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return vrijednost;
    }
}
