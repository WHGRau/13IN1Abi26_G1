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
    private ArrayList<Auto> autos;
    private ArrayList<Kunde> kunden;
    private DatabaseConnector dbConnector;
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter() {
        autos = new ArrayList<Auto>();
        kunden = new ArrayList<Kunde>();
        datenbankVerbinden();
    }
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter(ArrayList<Auto> pAutos, ArrayList<Kunde> pKunden) {
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
    
    
    public String registrieren (String pBenutzername, String pPasswort, String pName, String pVorname, String pGeburtsdatum, String pOrt, int pPostleitzahl, String pStraße, int pHausnummer, int pMitarbeiter, int pVerifiziert){
        String passwortHash = Helper.toSha256(pPasswort);
        String standortID = this.getStandortID(dbConnector, pOrt, pPostleitzahl, pStraße, pHausnummer);
        
        if (standortID.isEmpty()) {
            dbConnector.executeStatement("INSERT INTO standort(Ort, Postleitzahl, Straße, Hausnummer) VALUES('"+pOrt+"', '"+pPostleitzahl+"', '"+pStraße+"', '"+pHausnummer+"')");
            standortID = this.getStandortID(dbConnector, pOrt, pPostleitzahl, pStraße, pHausnummer);
        }
        if (standortID.isEmpty() || Helper.tryParse(standortID) < 1) {
            return "Konnte den Standort nicht verarbeiten!";
        }
        
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            String comm = "INSERT INTO benutzer(Benutzername, Passwort, Vorname, Name, Geburtsdatum, AdresseID, istMitarbeiter, istVerifiziert) VALUES('"+pBenutzername+"', '"+passwortHash+"', '"+pName+"', '"+pVorname+"', '"+pGeburtsdatum+"', '"+standortID+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')";
            dbConnector.executeStatement(comm);
            anmelden(pBenutzername, passwortHash);
        } else {
            return ("Benutzername bereits vergeben!");
        }
        return ("Konto angelegt!");
    }

    private String getStandortID(DatabaseConnector dbConnector, String pOrt, int pPostleitzahl, String pStraße, int pHausnummer)
    {
        dbConnector.executeStatement("SELECT ID FROM standort WHERE Ort = '"+pOrt+"' AND Postleitzahl = '"+pPostleitzahl+"' AND Straße = '"+pStraße+"' AND Hausnummer = '"+pHausnummer+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "";
        }
        else {
            return r.getData()[0][0];
        }
    }
    
    /**
     * Um ein Auto hinzufügen zu können muss man als Mitarbeiter 
     * angemeldet sein.
     */
    public String autoHinzufügen(String pMarke, String pModell, String pKategorie, int pLeistung, String pKennzeichen, int pPreisklasse) {
        if(ich != null && ich.getIstMitarbeiter()){
            String marke = pMarke;
            String modell = pModell;
            String kategorie = pKategorie;
            int leistung = pLeistung;
            String kennzeichen = pKennzeichen.toUpperCase(); 
            int preisklasse = pPreisklasse;
            dbConnector.executeStatement("SELECT kennzeichen FROM auto WHERE Kennzeichen = '"+kennzeichen+"'");
            QueryResult r = dbConnector.getCurrentQueryResult();
            if (r.getRowCount() == 0) {
                dbConnector.executeStatement("INSERT INTO auto(Marke, Modell, Kategorie, Leistung, Kennzeichen, PreisklasseID) VALUES('"+marke+"', '"+modell+"', '"+kategorie+"', '"+leistung+"', '"+kennzeichen+"', '"+preisklasse+"')");
                return("Auto hinzugefügt!");
            } else {
                return ("Kennzeichen bereits vergeben!");
            }
        } else {
            return ("Keine Berechtigung!");
        }
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
    
    public void setAutos (ArrayList<Auto> pAutos) {
        autos = pAutos;   
    }
    
    public void setKunden (ArrayList<Kunde> pKunden) {
        kunden = pKunden;   
    }
}
