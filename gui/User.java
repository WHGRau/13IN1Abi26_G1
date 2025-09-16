package gui;


/**
 * Beschreiben Sie hier die Klasse Benutzer.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class User
{
    int id;
    String benutzername;
    String name;
    String vorname;
    String geburtsdatum;
    boolean istMitarbeiter;
    boolean istVerifiziert;
    
    /**
     * Konstruktor für Objekte der Klasse Benutzer
     */
    public User(int pID, String pBenutzername, String pName, String pVorname, String pGeburtsdatum, boolean pMitarbeiter, boolean pVerifiziert)
    {
        benutzername = pBenutzername;
        name = pName;
        vorname = pVorname;
        geburtsdatum = pGeburtsdatum;
        istMitarbeiter = pMitarbeiter;
        istVerifiziert = pVerifiziert;
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
}
