package gui;
import java.util.ArrayList;
import java.sql.*;


/**
 * Beschreiben Sie hier die Klasse Verwalter.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Verwalter {
    private User ich;
    private ArrayList autos;
    private ArrayList kunden;
    private DatabaseConnector dbConnector;
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter(ArrayList pAutos, ArrayList pKunden) {
        autos = pAutos;
        kunden = pKunden;
        datenbankVerbinden();
    }
    
    public String anmelden(String pBenutzername, String pPasswort) {
        String benutzername = pBenutzername;
        String passwort = pPasswort;
        boolean verifiziertBoolean;
        
        dbConnector.executeStatement("SELECT * FROM benutzer WHERE Benutzername = '"+benutzername+"' AND Passwort = '"+passwort+"'");
        QueryResult nutzer = dbConnector.getCurrentQueryResult();
        
        if (nutzer.getRowCount() == 0) {
            return("Passwort oder Benutzername falsch!");
        }
        int id = Integer.parseInt(nutzer.getData() [0][0]);
        String name = nutzer.getData() [0][4];
        String vorname = nutzer.getData() [0][3];
        String geburtsdatum = nutzer.getData() [0][5];
        String adresse = nutzer.getData() [0][6];
        int mitarbeiter = Integer.parseInt(nutzer.getData() [0][7]);
        int verifiziert = Integer.parseInt(nutzer.getData() [0][8]);
        if (verifiziert == 1) {
            verifiziertBoolean = true;
        } else verifiziertBoolean = false;    
        
        
        if (mitarbeiter == 1) {
            ich = new Mitarbeiter(id, benutzername, name, vorname, geburtsdatum, true, verifiziertBoolean);    
        } else {
            ich = new Kunde(id, benutzername, name, vorname, geburtsdatum, adresse, false, verifiziertBoolean);        
        }
        
        
        if (ich != null) {
            return ("Anmeldung erfolgreich!");
        } else {
            return ("Anmeldung fehlgeschlagen!");    
        }
    }
    
    
    public String registrieren (String pBenutzername, String pPasswort, String pName, String pVorname, String pGeburtsdatum, String pAdresse, int pMitarbeiter, int pVerifiziert){
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO benutzer(Benutzername, Passwort, Vorname, Name, Geburtsdatum, Adresse, istMitarbeiter, istVerifiziert) VALUES('"+pBenutzername+"', '"+pPasswort+"', '"+pName+"', '"+pVorname+"', '"+pGeburtsdatum+"', '"+pAdresse+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')");
            anmelden(pBenutzername, pPasswort);
        } else {
            return ("Benutzername bereits vergeben!");
        }
        return ("Konto angelegt!");
    }
    
    public void datenbankVerbinden () {
        dbConnector = new DatabaseConnector("localhost", 3306, "mietwagenverleih_ronkel", "root", "");
        String fehler = dbConnector.getErrorMessage();
        if (fehler == null) {
          System.out.println("Datenbank wurde erfolgreich verbunden!");
        } else {
          System.out.println("Fehlermeldung: " + fehler);
        }
    }
    
    
    
    
    
    
    public User getUser () {
        return ich;        
    }
    
    public ArrayList getAutos () {
        return autos;        
    }
    
    public ArrayList getKunden () {
        return kunden;        
    }
    
    public void setUser (User pIch) {
        ich = pIch;   
    }
    
    public void setAutos (ArrayList pAutos) {
        autos = pAutos;   
    }
    
    public void setKunden (ArrayList pKunden) {
        kunden = pKunden;   
    }
}
