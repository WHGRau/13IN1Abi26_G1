package gui;


/**
 * Beschreiben Sie hier die Klasse Kunde.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Kunde extends User {
    
    /**
     * Konstruktor für Objekte der Klasse Kunde
     */
    public Kunde(int pID, String pBenutzername, String pName, String pVorname, String pGeburtsdatum, Standort pAdresse, boolean pMitarbeiter, boolean pVerifiziert) {
        super(pID, pBenutzername, pName, pVorname, pGeburtsdatum, pMitarbeiter, pVerifiziert, pAdresse);
    }
}
