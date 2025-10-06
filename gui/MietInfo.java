package gui;

/**
 * Beschreiben Sie hier die Klasse MietInfo.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MietInfo
{
    private String ausleihDatum;
    private String rückgabeDatum;
    private int leiherUserId;

    public MietInfo(String pAusleihDatum, String pRückgabeDatum, int pLeiherUserId)
    {
        ausleihDatum = pAusleihDatum;
        rückgabeDatum = pRückgabeDatum;
        leiherUserId = pLeiherUserId;
    }

    public String getAusleihDatum() {
        return ausleihDatum;
    }
    
    public String getRückgabeDatum() {
        return rückgabeDatum;
    }
    
    public int getLeiherUserId() {
        return leiherUserId;
    }
}