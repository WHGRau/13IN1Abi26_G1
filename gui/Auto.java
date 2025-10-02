package gui;
import java.util.ArrayList;


/**
 * Beschreiben Sie hier die Klasse Auto.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Auto {
    private int id;
    private String marke;
    private String modell;
    private String kategorie;
    private int leistung;
    private String kennzeichen;
    private int preis;
    private ArrayList bewertungen;
    public Auto(int pId, String pMarke, String pModell, String pKategorie, int pLeistung, String pKennzeichen, int pPreis, ArrayList pBewertungen){
        id = pId;
        marke = pMarke;
        modell = pModell;
        kategorie = pKategorie;
        leistung = pLeistung;
        kennzeichen = pKennzeichen; 
        preis = pPreis;
        bewertungen = pBewertungen;
    }
    
    public int getID() {
        return id;
    }
    
    public String getMarke() {
        return marke;
    }
    
    public String getModell() {
        return modell;
    }
    
    public String getKategorie() {
        return kategorie;
    }
    
    public int getLeistung() {
        return leistung;
    }
    
    public String getKennzeichen() {
        return kennzeichen;
    }
    
    public ArrayList getBewertungen() {
        return bewertungen;    
    }
    
    public int getPreis(){
        return preis;
    }
    
    /**
    public void addBewertung(Bewertung pBewertung) {
        bewertungen.add(pBewertung);    
    }
    
    public void removeBewertung(Bewertung pBewertung) {
        bewertungen.remove(pBewertung);    
    }
    **/
}
