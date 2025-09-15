package gui;


/**
 * Beschreiben Sie hier die Klasse Bewertung.
 * 
 * @author (Tobias Ahrens) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Bewertung
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int id;
    private int bewertung;
    private String text;

    /**
     * Konstruktor für Objekte der Klasse Preisklasse
     */
    public Bewertung(int pId, int pBewertung, String pText)
    {
        id = pId;
        bewertung = pBewertung;
        text = pText;
    }
    public int getId(){
        return id;
    }
    public int getBewertung(){
        return bewertung;
    }
    public String getText(){
        return text;
    }
}
