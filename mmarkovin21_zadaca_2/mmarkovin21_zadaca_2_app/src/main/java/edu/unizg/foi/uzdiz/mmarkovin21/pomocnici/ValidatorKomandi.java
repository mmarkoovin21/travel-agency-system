package edu.unizg.foi.uzdiz.mmarkovin21.pomocnici;

public class ValidatorKomandi {
    public static Integer parsirajOznakuAranzmana(String parametar) {
        if (parametar == null || parametar.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(parametar.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean validirajImeIliPrezime(String vrijednost) {
        if (vrijednost == null || vrijednost.trim().isEmpty()) {
            return false;
        }
        return vrijednost.matches("[a-zA-ZčćžšđČĆŽŠĐ]+");
    }

    public static Integer parsirajIValidirajOznaku(String parametar, String nazivKomande) {
        Integer oznaka = parsirajOznakuAranzmana(parametar);
        if (oznaka == null) {
            System.out.println("Greška: Neispravan format oznake aranžmana. Oznaka mora biti cijeli broj.");
        }
        return oznaka;
    }
}
