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
    private Preisklasse pk;
    
    // Mietinfo
    private MietInfo mietInfo;
    
    public Auto(int pId, String pMarke, String pModell, String pKategorie, int pLeistung, String pKennzeichen, Preisklasse pPreis){
        id = pId;
        marke = pMarke;
        modell = pModell;
        kategorie = pKategorie;
        leistung = pLeistung;
        kennzeichen = pKennzeichen; 
        pk = pPreis;
    }
    
    public Auto(int pId, String pMarke, String pModell, String pKategorie, int pLeistung, String pKennzeichen, Preisklasse pPreis, MietInfo pMietInfo){
        id = pId;
        marke = pMarke;
        modell = pModell;
        kategorie = pKategorie;
        leistung = pLeistung;
        kennzeichen = pKennzeichen; 
        pk = pPreis;
        mietInfo = pMietInfo;
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
    
    public int getLeistungNum() {
        return leistung;
    }
    
    public String getLeistung() {
        return leistung + " PS";
    }
    
    public String getKennzeichen() {
        return kennzeichen;
    }
    
    public Preisklasse getPreisklasse(){
        return pk;
    }
    
    public int getPreisNum() {
        return pk.getPreis();
    }
    
    public String getPreis() {
        return pk.getPreis() + "€";
    }
    
    public MietInfo getMietInfo(){
        return mietInfo;
    }
}
