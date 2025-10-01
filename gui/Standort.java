package gui;

/**
 * Beschreiben Sie hier die Klasse Standort.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Standort
{
    private int ID;
    private String ort;
    private int plz;
    private String straße;
    private int hausNr;
    
    /**
     * Konstruktor für Objekte der Klasse Standort
     */
    public Standort(String pOrt, int pPlz, String pStraße, int pHausNr)
    {
        ort = pOrt;
        plz = pPlz;
        straße = pStraße;
        hausNr = pHausNr;
    }
    
    public String getOrt() {
        return ort;
    }
    
    public int getPlz() {
        return plz;
    }
    
    public String getStraße() {
        return straße;
    }
    
    public int getHausNr() {
        return hausNr;
    }
}