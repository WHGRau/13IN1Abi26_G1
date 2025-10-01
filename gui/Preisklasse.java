package gui;


/**
 * Beschreiben Sie hier die Klasse Preisklasse.
 * 
 * @author (Tobias Ahrens) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Preisklasse
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int id;
    private int preis;
    private int zusatzVersicherungsPreis;

    /**
     * Konstruktor für Objekte der Klasse Preisklasse
     */
    public Preisklasse(int pId, int pPreis, int pZVP)
    {
        id = pId;
        preis = pPreis;
        zusatzVersicherungsPreis = pZVP;
    }
    public int getId(){
        return id;
    }
    public int getPreis(){
        return preis;
    }
    public int getZVP(){
        return zusatzVersicherungsPreis;
    }
    
}
