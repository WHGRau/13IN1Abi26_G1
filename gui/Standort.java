package gui;

/**
 * Beschreiben Sie hier die Klasse Standort.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Standort
{
    private String ort;
    private int plz;
    private String straße;
    private int hausNr;
    private String id;
    
    /**
     * Konstruktor für Objekte der Klasse Standort
     */
    public Standort(String pOrt, int pPlz, String pStraße, int pHausNr)
    {
        id = "";
        ort = pOrt;
        plz = pPlz;
        straße = pStraße;
        hausNr = pHausNr;
    }
    
    public Standort(String pId, String pOrt, int pPlz, String pStraße, int pHausNr)
    {
        id = pId;
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
    
    public String getId() {
        return id;
    }
}