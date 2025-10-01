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
    private ArrayList<User> kunden;
    private DatabaseConnector dbConnector;
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter() {
        autos = new ArrayList<Auto>();
        kunden = new ArrayList<User>();
        datenbankVerbinden();
    }
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter(ArrayList<Auto> pAutos, ArrayList<User> pKunden) {
        autos = pAutos;
        kunden = pKunden;
        datenbankVerbinden();
    }
    
    public String registrieren (String pBenutzername, String pPasswort, String pName, String pVorname, String pGeburtsdatum, Standort pAdresse, int pMitarbeiter, int pVerifiziert){
        // Standort erstellen
        Standort adresse = getStandortFromDb(pAdresse.getOrt(), pAdresse.getPlz(), pAdresse.getStraße(), pAdresse.getHausNr());
        if (adresse == null) return "Konnte Adresse des Nutzers nicht speichern";
        
        // Nutzer erstellen
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO benutzer(Benutzername, Passwort, Vorname, Name, Geburtsdatum, AdresseID, istMitarbeiter, istVerifiziert) "
                +"VALUES('"+pBenutzername+"', '"+pPasswort+"', '"+pVorname+"', '"+pName+"', '"+pGeburtsdatum+"', '"+adresse.getId()+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')");
            
            // Prüfen ob Nutzer auch wirklich erstellt wurde
            dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
            r = dbConnector.getCurrentQueryResult();
            if (r.getRowCount() == 0) {
                return "Nutzer konnte nach Erstellen nicht gefunden werden!";
            } else {
                anmelden(pBenutzername, pPasswort);
            }
        }
        else {
            return "Konto existiert bereits!";
        }
        
        return ("Konto angelegt!");
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
        
        if (ich != null) {
            return ("Anmeldung erfolgreich!");
        } else {
            return ("Anmeldung fehlgeschlagen!");  
        }
    }
    
    // Erstellt neuen Standort Datensatz falls nicht vorhanden
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
        int plzParsed = Helper.tryParseInt(row[2]);
        int hausNrParsed = Helper.tryParseInt(row[4]);
        
        if (plzParsed < 1 || hausNrParsed < 1) {
            // Ungültige ID/Zahleneingaben
            return null;
        }
        
        return new Standort(row[0], row[1], plzParsed, row[3], hausNrParsed);
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
    
    public void setKunden (ArrayList<User> pKunden) {
        kunden = pKunden;   
    }
}
