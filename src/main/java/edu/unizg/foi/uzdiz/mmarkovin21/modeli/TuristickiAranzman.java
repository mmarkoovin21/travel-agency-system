package edu.unizg.foi.uzdiz.mmarkovin21.modeli;

import java.time.LocalDateTime;

public class TuristickiAranzman {
    private String oznaka;
    private String naziv;
    // ... ostali atributi

    private TuristickiAranzman() {}

    public static class Builder {
        private TuristickiAranzman aranzman = new TuristickiAranzman();

        public Builder setOznaka(String oznaka) {
            aranzman.oznaka = oznaka;
            return this;
        }

        public Builder setNaziv(String naziv) {
            aranzman.naziv = naziv;
            return this;
        }

        public TuristickiAranzman build() {
            return aranzman;
        }
    }
}
