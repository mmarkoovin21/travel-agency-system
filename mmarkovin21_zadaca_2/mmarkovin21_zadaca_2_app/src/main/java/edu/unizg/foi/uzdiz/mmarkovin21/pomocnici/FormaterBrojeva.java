package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormaterBrojeva {
    private static final DecimalFormat FORMAT_CIJELI;
    private static final DecimalFormat FORMAT_DECIMALNI;

    static {
        DecimalFormatSymbols simboli = new DecimalFormatSymbols(Locale.getDefault());
        simboli.setGroupingSeparator('.');
        simboli.setDecimalSeparator(',');

        FORMAT_CIJELI = new DecimalFormat("#,##0", simboli);
        FORMAT_DECIMALNI = new DecimalFormat("#,##0.00", simboli);
    }

    public static String formatirajCijeliBroj(long broj) {
        return FORMAT_CIJELI.format(broj);
    }
    public static String formatirajCijeliBroj(int broj) {
        return FORMAT_CIJELI.format(broj);
    }
}
