package gui;

/**
 * Beschreiben Sie hier die Klasse MietInfo.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MietInfo
{
    private int id;
    private String ausleihDatum;
    private String rückgabeDatum;
    private User mieter;
    
    public MietInfo(int pMietID, String pAusleihDatum, String pRückgabeDatum, User pMieter)
    {
        id = pMietID;
        ausleihDatum = pAusleihDatum;
        rückgabeDatum = pRückgabeDatum;
        mieter = pMieter;
    }
    
    public int getId() {
        return id;
    }

    public String getAusleihDatum() {
        return ausleihDatum;
    }
    
    public String getRückgabeDatum() {
        return rückgabeDatum;
    }
    
    public User getMieter() {
        return mieter;
    }
}