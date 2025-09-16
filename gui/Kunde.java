package gui;


/**
 * Beschreiben Sie hier die Klasse Kunde.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Kunde extends User {
    private String adresse; 
    
    /**
     * Konstruktor für Objekte der Klasse Kunde
     */
    public Kunde(int pID, String pBenutzername, String pName, String pVorname, String pGeburtsdatum, String pAdresse, boolean pMitarbeiter, boolean pVerifiziert) {
        super(pID, pBenutzername, pName, pVorname, pGeburtsdatum, pMitarbeiter, pVerifiziert);
        adresse = pAdresse;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String pAdresse) {
        adresse = pAdresse;
    }
}
