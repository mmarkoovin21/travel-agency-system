package edu.unizg.foi.uzdiz.mmarkovin21.observer;

public interface Subject {
    void registrirajObservera(Observer observer);
    void ukloniObservera(Observer observer);
    void ukloniSveObservere();
    void obavijestiObservere(String poruka);
}
