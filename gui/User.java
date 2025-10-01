package gui;


/**
 * Beschreiben Sie hier die Klasse Benutzer.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class User
{
    private int id;
    private String benutzername;
    private String name;
    private String vorname;
    private String geburtsdatum;
    private boolean istMitarbeiter;
    private boolean istVerifiziert;
    private Standort adresse;
    
    /**
     * Konstruktor für Objekte der Klasse Benutzer
     */
    public User(int pID, String pBenutzername, String pName, String pVorname, String pGeburtsdatum, boolean pMitarbeiter, boolean pVerifiziert, Standort pAdresse)
    {
        id = pID;
        benutzername = pBenutzername;
        name = pName;
        vorname = pVorname;
        geburtsdatum = pGeburtsdatum;
        istMitarbeiter = pMitarbeiter;
        istVerifiziert = pVerifiziert;
        adresse = pAdresse;
    }

    public int getID(){
        return id;
    }
    
    public String getBenutzername(){
        return benutzername;
    }
    
    public void setBenutzername(String pBenutzername){
        benutzername = pBenutzername;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String pName){
        name = pName;
    }
    
    public String getVorname(){
        return vorname;
    }
    
    public void setVorname(String pVorname){
        vorname = pVorname;
    }
    
    public String getGeburtsdatum(){
        return geburtsdatum;
    }
    
    public boolean getIstMitarbeiter(){
        return istMitarbeiter;
    }
    
    public void setIstMitarbeiter(boolean pMitarbeiter){
        istMitarbeiter = pMitarbeiter;
    }
    
    public boolean getIstVerifiziert(){
        return istVerifiziert;
    }
    
    public void setistVerifiziert(boolean pIstVerifiziert){
        istVerifiziert = pIstVerifiziert;
    }
    
    public Standort getAdresse() {
        return adresse;
    }
    
    public void setAdresse(Standort pAdresse) {
        adresse = pAdresse;
    }
}
