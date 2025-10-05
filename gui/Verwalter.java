package gui;
import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

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
        
        anmelden("Klogang420", "stuhlgang69");
    }
    
    /**
     * Konstruktor für Objekte der Klasse Verwalter
     */
    public Verwalter(ArrayList<Auto> pAutos, ArrayList<User> pKunden) {
        autos = pAutos;
        kunden = pKunden;
        datenbankVerbinden();
        
        anmelden("Klogang420", "stuhlgang69");
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
            if (ich.getIstMitarbeiter()) {
                return ("Erfolgreich als Mitarbeiter angemeldet!");
            }
            else {
                return ("Erfolgreich als Kunde angemeldet!");
            }
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
        dbConnector = new DatabaseConnector("localhost", 3306, "mietwagenverleih_ronkel", "root", "amogus");
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
    public ArrayList autoSuchen(String pMarke, String pModell, String pKategorie, int pLeistung){
        QueryResult auto = null;
        autos.clear();
        int leistung = (int)pLeistung;
        String query = getAutoSql;
        
        if (!pMarke.isEmpty() && !pModell.isEmpty() && !pKategorie.isEmpty()){
            query += "AND Marke = '"+pMarke+"' AND Modell = '"+pModell+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'";  
        } else if (pMarke.isEmpty() && !pModell.isEmpty() && !pKategorie.isEmpty()){
            query += "AND Modell = '"+pModell+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'";
        } else if (pModell.isEmpty() && !pMarke.isEmpty() && !pKategorie.isEmpty()){
            query += "AND Marke = '"+pMarke+"' AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'";
        } else if (pKategorie.isEmpty() && !pModell.isEmpty() && !pMarke.isEmpty()){
            query += "AND Marke = '"+pMarke+"' AND Modell = '"+pModell+"' AND Leistung > '"+leistung+"'";
        } else if (pMarke.isEmpty() && pModell.isEmpty() && !pKategorie.isEmpty()) {
            query += "AND Kategorie ='"+pKategorie+"' AND Leistung > '"+leistung+"'"; 
        } else if (pMarke.isEmpty() && pKategorie.isEmpty() && !pModell.isEmpty()){
            query += "AND Modell = '"+pModell+"' AND Leistung > '"+leistung+"'"; 
        } else if ( pModell.isEmpty() && pKategorie.isEmpty() && !pMarke.isEmpty()){
            query += "AND Marke = '"+pMarke+"' AND Leistung > '"+leistung+"'";  
        } else {
            query += "AND Leistung > '"+leistung+"'";    
        }
        
        dbConnector.executeStatement(query);
        auto = dbConnector.getCurrentQueryResult();
        System.out.println("auto query: " + query);
        
        ArrayList list = parseAutos(auto, false);
        autos = list;
        return list;
    }
    
    public ArrayList getGemieteteAutos(boolean früher){
        if (ich == null) throw new IllegalCallerException("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() == true) throw new IllegalCallerException("Mitarbeiter dürfen keine Autos mieten!");
        
        return this.getGemieteteAutosInternal(ich.getID(), früher);
    }
    
    public ArrayList getGemieteteAutosVon(int userID, boolean früher){
        if (ich == null) throw new IllegalCallerException("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) throw new IllegalCallerException("Nur Mitarbeiter dürfen gemietete Autos von anderen sehen!");
        
        return this.getGemieteteAutosInternal(userID, früher);
    }
    
    private ArrayList getGemieteteAutosInternal(int userID, boolean früher) {
        String timestamp = getNowDateTime();
        String query = getGemieteteAutosSql + " AND mietet.UserID = " + userID;
        // Nur früher gemietete Autos, sonst momentan Gemietete
        if (früher) {
            query += " AND mietet.RückgabeAm < " + timestamp;
        }
        else {
            query += " AND mietet.RückgabeAm >= " + timestamp;
        }
        
        dbConnector.executeStatement(query);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return new ArrayList();
        }
        
        ArrayList list = parseAutos(r, true);
        autos = list;
        return list;
    }
    
    public String autoMieten(int autoID, int userID, String rückgabeAm) {
        if (ich == null) return "Nicht angemeldet!";
        if (ich.getIstMitarbeiter() != true) return "Nur Mitarbeiter dürfen Autos vermieten!";
        
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        
        // Prüfen ob der Nutzer existiert und Kunde ist
        dbConnector.executeStatement("SELECT benutzer.ID, benutzer.IstMitarbeiter FROM benutzer WHERE benutzer.ID = " + userID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Auto wird gerade bereits vermietet!";
        }
        else if (r.getData()[0][1] != "0") {
            return "Nur Kunden darf man Autos vermieten!";
        }
        
        String timestamp = getNowDateTime();
        
        // Prüfen ob niemand das Auto gerade am Mieten ist
        dbConnector.executeStatement("SELECT mietet.AutoID, mietet.RückgabeAm, UserID FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm >= '" + timestamp + "'");
        r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() > 0) {
            return "Auto wird gerade bereits vermietet!";
        }
        
        // Relation endlich hinzufügen
        dbConnector.executeStatement("INSERT INTO mietet (UserID, AutoID, AusgeliehenAm, RückgabeAm) VALUES (" + userID + ", " + autoID + ", '" + timestamp + "', '" + rückgabeAm + "')");
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        /*
        r = dbConnector.getCurrentQueryResult();
        
        // Prüfen ob Relation erstellt wurde
        dbConnector.executeStatement("SELECT COUNT(*) FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + userID);
        r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Konnte das Auto aus unbekanntem Grund nicht vermieten!!";
        }
        */
        
        return "Auto erfolgreich vermietet!";
    }
    
    public String autoRückgabe(int autoID) {
        if (ich == null) throw new IllegalCallerException("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() == true) throw new IllegalCallerException("Mitarbeiter dürfen keine selbst ausgeliehenen Autos zurückgeben!");
        
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        
        String timestamp = getNowDateTime();
        int ownID = ich.getID();
        
        // Prüfen ob wir das Auto überhaupt gemietet haben
        dbConnector.executeStatement("SELECT COUNT(*) FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Du mietest dieses Auto nicht mal, hau ab!";
        }
        
        // Relation endlich entfernen
        dbConnector.executeStatement("DELETE FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        // Prüfen ob wir immer noch das Auto mieten
        dbConnector.executeStatement("SELECT COUNT(*) FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() > 0) {
            return "Fehlschlag: Nutzer mietet das Auto immer noch!";
        }
        
        return "Nun mietet keiner das Auto mehr";   
    }
    
    public String autoZwangsRückgabe(int autoID) {
        if (ich == null) return "Nicht angemeldet!";
        if (ich.getIstMitarbeiter() != true) return "Nur Mitarbeiter können Autos zurück ins Lager erzwingen!";
        
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        
        String timestamp = getNowDateTime();
        dbConnector.executeStatement("UPDATE mietet SET RückgabeAm = " + timestamp + " WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "'");
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        // Prüfen ob nun immer noch wer das Auto mietet
        dbConnector.executeStatement("SELECT COUNT(*) FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() > 0) {
            return "Fehlschlag: " + r.getData()[0][0] + " Nutzer mieten das Auto immer noch!";
        }
        
        return "Nun mietet keiner das Auto mehr";        
    }
    
    private boolean existiertAuto(int autoID) {
        dbConnector.executeStatement("SELECT ID FROM auto WHERE auto.ID = " + autoID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        return r.getRowCount() > 0;
    }
    
    public MietInfo getMietRelation(int autoID) {
        if (ich == null) throw new IllegalCallerException("Nicht angemeldet!");
        
        dbConnector.executeStatement("SELECT mietet.AusgeliehenAm, mietet.RückgabeAm, mietet.UserID FROM mietet WHERE mietet.AutoID = " + autoID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return null;
        }
        String[] row = r.getData()[0];
        
        int muID = Helper.tryParseInt(row[2]);
        MietInfo mietInfo = new MietInfo(row[0], row[1], muID);
        return mietInfo;
    }
    
    private String autoSelectSql = "SELECT "+
        "auto.ID, auto.Marke, auto.Modell, auto.Kategorie, auto.Leistung, auto.Kennzeichen, "+
        "preisklassen.ID, preisklassen.Preis, preisklassen.ZusatzversicherungsPreis";
    
    private String getAutoSql = autoSelectSql + " FROM auto, preisklassen WHERE auto.PreisklasseID = preisklassen.ID ";
    
    private String getGemieteteAutosSql = autoSelectSql + ", mietet.AusgeliehenAm, mietet.RückgabeAm, mietet.UserID "+
        "FROM auto, preisklassen, mietet WHERE auto.PreisklasseID = preisklassen.ID AND auto.ID = mietet.AutoID ";
        
    private ArrayList parseAutos(QueryResult result, boolean hatMietInfo) {
        ArrayList list = new ArrayList();
        String[][] autoArray = result.getData();
        
        for(int i = 0; i < autoArray.length; i++){
            String[] row = autoArray[i];
            
            // Preisklasse
            int pkId = Helper.tryParseInt(row[6]);
            int pkPreis = Helper.tryParseInt(row[7]);
            int pkZVPreis = Helper.tryParseInt(row[8]);
            Preisklasse pk = new Preisklasse(pkId, pkPreis, pkZVPreis);
            
            // Auto
            int aId = Helper.tryParseInt(row[0]);
            int aLeistung = Helper.tryParseInt(row[4]);
            Auto auto = new Auto(aId, row[1], row[2], row[3], aLeistung, row[5], pk);
            
            if (hatMietInfo) {
                int muID = Helper.tryParseInt(row[11]);
                MietInfo mietInfo = new MietInfo(row[9], row[10], muID);
            }
            
            list.add(auto);    
        }
        
        return list;
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
    
    // source: https://stackoverflow.com/questions/8345023/need-to-get-current-timestamp-in-java
    public String getNowDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    
    public String getNowDateTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // HH -> 24h; hh -> 12h am/pm
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
