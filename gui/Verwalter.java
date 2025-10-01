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
        
        dbConnector.executeStatement("SELECT * FROM benutzer WHERE Benutzername = '"+benutzername+"' AND Passwort = '"+passwort+"'");
        QueryResult nutzer = dbConnector.getCurrentQueryResult();
        
        if (nutzer.getRowCount() == 0) {
            return("Passwort oder Benutzername falsch!");
        }
        int id = Integer.parseInt(nutzer.getData() [0][0]);
        String name = nutzer.getData() [0][4];
        String vorname = nutzer.getData() [0][3];
        String geburtsdatum = nutzer.getData() [0][5];
        Standort adresse = new Standort("ort", 2, "stras", 4); //nutzer.getData() [0][6];
        int mitarbeiter = Integer.parseInt(nutzer.getData() [0][7]);
        int verifiziert = Integer.parseInt(nutzer.getData() [0][8]);
        
        boolean verifiziertBool;
        boolean mitarbeiterBool;
        if (verifiziert == 1) {
            verifiziertBool = true;
        } else {
            verifiziertBool = false;
        }
        
        if (mitarbeiter == 1) {
            mitarbeiterBool = true;
        } else {
            mitarbeiterBool = false;
        }
        
        ich = new User(id, benutzername, name, vorname, geburtsdatum, mitarbeiterBool, verifiziertBool, adresse); 
        
        /*
        if (mitarbeiter == 1) {
            ich = new User(id, benutzername, name, vorname, geburtsdatum, true, verifiziertBoolean);    
        } else {
            ich = new Kunde(id, benutzername, name, vorname, geburtsdatum, adresse, false, verifiziertBoolean);        
        }
        */
        
        if (ich != null) {
            return ("Anmeldung erfolgreich!");
        } else {
            return ("Anmeldung fehlgeschlagen!");  
        }
    }
    
    Standort getStandortFromDb(String pOrt, int pPlz, String pStraße, int pHausNr) {
        dbConnector.executeStatement("SELECT ID, Ort, Postleitzahl, Straße, Hausnummer FROM standort WHERE Ort = '"+pOrt+"' AND Postleitzahl = '"+pPlz+"' AND Straße = '"+pStraße+"' AND Hausnummer = '"+pHausNr+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO standort(Ort, Postleitzahl, Straße, Hausnummer) VALUES('"+pOrt+"', '"+pPlz+"', '"+pStraße+"', '"+pHausNr+"')");
            dbConnector.executeStatement("SELECT ID, Ort, Postleitzahl, Straße, Hausnummer FROM standort WHERE Ort = '"+pOrt+"' AND Postleitzahl = '"+pPlz+"' AND Straße = '"+pStraße+"' AND Hausnummer = '"+pHausNr+"'");
            r = dbConnector.getCurrentQueryResult();
            
            if (r.getRowCount() == 0) {
                // Konnte Standort nicht erstellen
                return null;
            }
        }
        
        // Standort wurde zurückgegeben
        String[] row = r.getData()[0];
        int idParsed = Helper.tryParseInt(row[0]);
        int plzParsed = Helper.tryParseInt(row[2]);
        int hausNrParsed = Helper.tryParseInt(row[4]);
        
        if (idParsed < 1 || plzParsed < 1 || hausNrParsed < 1)
        
        return new Standort();
    }
    
    Standort getStandortFromQuery() {
        
    }
    
    
    public String registrieren (String pBenutzername, String pPasswort, String pName, String pVorname, String pGeburtsdatum, Standort pAdresse, int pMitarbeiter, int pVerifiziert){
        // Standort erstellen
        dbConnector.executeStatement("SELECT ID FROM standort WHERE Ort = '"+pAdresse.getOrt()+"' AND Postleitzahl = '"+pAdresse.getPlz()+"' AND Straße = '"+pAdresse.getStraße()+"' AND Hausnummer = '"+pAdresse.getHausNr()+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO standort(Ort, Postleitzahl, Straße, Hausnummer) VALUES('"+pAdresse.getOrt()+"', '"+pPasswort+"', '"+pName+"', '"+pVorname+"', '"+pGeburtsdatum+"', '"+pAdresse+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')");
            anmelden(pBenutzername, pPasswort);
        } else {
            return ("Benutzername bereits vergeben!");
        }
        
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO benutzer(Benutzername, Passwort, Vorname, Name, Geburtsdatum, AdresseID, istMitarbeiter, istVerifiziert) VALUES('"+pBenutzername+"', '"+pPasswort+"', '"+pName+"', '"+pVorname+"', '"+pGeburtsdatum+"', '"+pAdresse+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')");
            anmelden(pBenutzername, pPasswort);
        } else {
            return ("Benutzername bereits vergeben!");
        }
        return ("Konto angelegt!");
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
        dbConnector = new DatabaseConnector("localhost", 3306, "mietwagenverleih_ronkel", "root", "amogus");
        String fehler = dbConnector.getErrorMessage();
        if (fehler == null) {
          System.out.println("Datenbank wurde erfolgreich verbunden!");
        } else {
          System.out.println("Fehlermeldung: " + fehler);
        }
    }
    
    private String getStandortID(String pOrt, int pPostleitzahl, String pStraße, int pHausnummer)
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
    
    private User getExtendedUserFromDb(String pBenutzername) {
        dbConnector.executeStatement("""
                SELECT benutzer.ID, benutzer.Benutzername, benutzer.Vorname, benutzer.Name, benutzer.IstMitarbeiter, benutzer.IstVerifiziert, 
                       standort.Ort, standort.Postleitzahl, standort.Straße, standort.Hausnummer
                FROM benutzer 
                JOIN standort ON benutzer.AdresseID = adresse.ID
                WHERE Benutzername = '
            """+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            System.out.println("Eigener Nutzer nicht gefunden!!!");
            return null;
        }
        
        String[] row = r.getData()[0];
        int id = Helper.tryParseInt(row[0]);
        if (id <= 0) {
            System.out.println("Nutzer ID ist ungültig (" + row[0] + ")!");
            return null;
        }
        
        boolean istMitarbeiter = Helper.tryParseBool(row[5]);
        boolean istVerifiziert = Helper.tryParseBool(row[6]);
        Standort adresse = new Standort(row[7], Helper.tryParseInt(row[8]), row[9], Helper.tryParseInt(row[8]));
                
        User user = new User(id, row[1], row[2], row[3], row[4], istMitarbeiter, istVerifiziert, adresse);
        return user;
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
