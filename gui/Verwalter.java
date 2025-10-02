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
    private User ich = null;
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
        String passwortHash = Helper.toSha256(pPasswort);
        
        // Standort erstellen
        Standort adresse = getStandortFromDb(pAdresse.getOrt(), pAdresse.getPlz(), pAdresse.getStraße(), pAdresse.getHausNr());
        if (adresse == null) return "Konnte Adresse des Nutzers nicht speichern";
        
        // Nutzer erstellen
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            dbConnector.executeStatement("INSERT INTO benutzer(Benutzername, Passwort, Vorname, Name, Geburtsdatum, AdresseID, istMitarbeiter, istVerifiziert) "
                +"VALUES('"+pBenutzername+"', '"+passwortHash+"', '"+pVorname+"', '"+pName+"', '"+pGeburtsdatum+"', '"+adresse.getId()+"', '"+pMitarbeiter+"', '"+pVerifiziert+"')");
            
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
        String passwortHash = Helper.toSha256(passwort);
        
        dbConnector.executeStatement("SELECT benutzer.ID, benutzer.Benutzername, benutzer.Passwort, benutzer.Vorname, benutzer.Name, benutzer.Geburtsdatum, benutzer.istMitarbeiter, benutzer.istVerifiziert, "
            +"standort.Ort, standort.Postleitzahl, standort.Straße, standort.Hausnummer "
            +"FROM benutzer "
            +"JOIN standort ON standort.ID = benutzer.AdresseID "
            +"WHERE Benutzername = '"+benutzername+"' AND Passwort = '"+passwortHash+"' ");
            // Das WHERE muss nach dem FROM und dem JOIN, sonst ist MySQL böse
        QueryResult nutzer = dbConnector.getCurrentQueryResult();
        
        if (nutzer.getRowCount() == 0) {
            return("Passwort oder Benutzername falsch!");
        }
        
        String[] row = nutzer.getData()[0];
        int id = Integer.parseInt(row[0]);
        String name = row[4];
        String vorname = row[3];
        String geburtsdatum = row[5];
        int mitarbeiter = Integer.parseInt(row[6]);
        int verifiziert = Integer.parseInt(row[7]);
        
        int plzParsed = Helper.tryParseInt(row[9]);
        int hausNrParsed = Helper.tryParseInt(row[11]);
        Standort adresse = new Standort(row[8], plzParsed, row[10], hausNrParsed);
        
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
    
    public String abmelden(){
        ich = null;
        return("Abmeldung erfolgreich");
    }
    
    public String kontoLoeschen(){
        /*
         * AUSSCHLIESSLICH AUSFÜHREN WENN DER BENUTZER NICHTS MEHR MIETET!!!!!!!!!
         */
        if(ich == null) return("Nicht angemeldet!");
        String benutzername = ich.getBenutzername();
        
        dbConnector.executeStatement("SELECT id FROM benutzer WHERE benutzername ='" + benutzername + "'");
        QueryResult x = dbConnector.getCurrentQueryResult();
        int id = Integer.parseInt(x.getData() [0][0]);
        if (x.getRowCount() == 0) {
            return("Konnte zu löschenden Nutzer nicht finden!");
        }
        
        dbConnector.executeStatement("SELECT AdresseID FROM benutzer WHERE benutzername ='" + benutzername + "'");
        x = dbConnector.getCurrentQueryResult();
        int adresseId = Integer.parseInt(x.getData() [0][0]);
        if (x.getRowCount() == 0) {
            return("Konnte zu löschenden Standort nicht finden!");
        }
        
        dbConnector.executeStatement("DELETE FROM benutzer WHERE benutzername ='" + benutzername + "'");
        dbConnector.executeStatement("DELETE FROM standort WHERE id = '" + adresseId + "'");
        dbConnector.executeStatement("DELETE FROM bewertungen WHERE benutzerID = '" + id + "'");
        dbConnector.executeStatement("DELETE FROM wunschliste WHERE benutzerID = '" + id + "'");
        return("Konto erfolgreich gelöscht.");
    }
    
    // Erstellt neuen Standort Datensatz falls nicht vorhanden
    Standort getStandortFromDb(String pOrt, int pPlz, String pStraße, int pHausNr) {
        if (pPlz < 1 || pHausNr < 1) {
            // Ungültige ID/Zahleneingaben
            return null;
        }
        
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
        dbConnector = new DatabaseConnector("localhost", 3306, "mietwagenverleih_ronkel", "root", "");
        String fehler = dbConnector.getErrorMessage();
        if (fehler == null) {
          System.out.println("Datenbank wurde erfolgreich verbunden!");
        } else {
          System.out.println("Fehlermeldung: " + fehler);
          // TODO: Irgendwann passenderen Exceptiontyp suchen
          throw new NullPointerException("Datenbankverbindung Fehlermeldung: " + fehler);
        }
    }
    
    
    /**
     * Sucht nach Autos mit den angebeben Parametern, es können auch alle leer gelassen werden.
     */
    public ArrayList autoSuchen(String pMarke, String pModell, String pKategorie, double pLeistung){
        QueryResult auto = null;
        autos.clear();
        int leistung = (int)pLeistung;
        if (!pMarke.isEmpty() && !pModell.isEmpty() && !pKategorie.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Marke = '"+pMarke+"' AND Modell = '"+pModell+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();
        } else if (pMarke.isEmpty() && !pModell.isEmpty() && !pKategorie.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Modell = '"+pModell+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();    
        } else if (pModell.isEmpty() && !pMarke.isEmpty() && !pKategorie.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Marke = '"+pMarke+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();    
        } else if (pKategorie.isEmpty() && !pModell.isEmpty() && !pMarke.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Marke = '"+pMarke+"' AND Modell = '"+pModell+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();
        } else if (pMarke.isEmpty() && pModell.isEmpty() && !pKategorie.isEmpty()) {
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();
        } else if (pMarke.isEmpty() && pKategorie.isEmpty() && !pModell.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Modell = '"+pModell+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();
        } else if ( pModell.isEmpty() && pKategorie.isEmpty() && !pMarke.isEmpty()){
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Marke = '"+pMarke+"' AND Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();
        } else {
            dbConnector.executeStatement("SELECT * FROM auto, preisklassen WHERE Leistung > '"+leistung+"'AND auto.PreisklasseID = preisklassen.ID");  
            auto = dbConnector.getCurrentQueryResult();    
        }
        String[][] autoArray = auto.getData();
        for(int i = 0; i<autoArray.length; i++){
            autos.add(new Auto(Integer.parseInt(autoArray[i][0]),autoArray[i][1],autoArray[i][2],autoArray[i][3],Integer.parseInt(autoArray[i][4]), autoArray[i][5],Integer.parseInt(autoArray[i][8]), null));    
        }
        
        return autos;
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
