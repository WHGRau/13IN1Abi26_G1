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
        
        // Prüfen ob so ein Nutzer bereits existiert
        dbConnector.executeStatement("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            // Nutzer erstellen
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
        
        // Suche Nutzer, bei dem Benutzername und Passworthash übereinstimmen. Falls so einer gefunden wird: Alle Informationen nötig
        dbConnector.executeStatement("SELECT benutzer.ID, benutzer.Benutzername, benutzer.Passwort, benutzer.Vorname, benutzer.Name, benutzer.Geburtsdatum, benutzer.istMitarbeiter, benutzer.istVerifiziert, "
            +"standort.Ort, standort.Postleitzahl, standort.Straße, standort.Hausnummer, standort.ID "
            +"FROM benutzer "
            +"JOIN standort ON standort.ID = benutzer.AdresseID "
            +"WHERE Benutzername = '"+benutzername+"' AND Passwort = '"+passwortHash+"' ");
            
        QueryResult nutzer = dbConnector.getCurrentQueryResult();
        if (nutzer.getRowCount() == 0) {
            return("Passwort oder Benutzername falsch!");
        }
        
        // Informationen auswerten und als User "ich" speichern
        String[] row = nutzer.getData()[0];
        int id = Integer.parseInt(row[0]);
        String name = row[4];
        String vorname = row[3];
        String geburtsdatum = row[5];
        int mitarbeiter = Integer.parseInt(row[6]);
        int verifiziert = Integer.parseInt(row[7]);
        
        int plzParsed = Helper.tryParseInt(row[9]);
        int hausNrParsed = Helper.tryParseInt(row[11]);
        Standort adresse = new Standort(row[12], row[8], plzParsed, row[10], hausNrParsed);
        
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
    
    /**
     * Das Konto und alle dazugehörigen Daten werden gelöscht. 
     * Funktioniert nur wenn im Moment nichts mehr gemietet wird.
     */
    
    public String kontoLoeschen(){
        if(ich == null) {
            return("Nicht angemeldet!");
        }
        String benutzername = ich.getBenutzername();
        int id = ich.getID();
        
        //Überprüfen ob der Nutzer noch etwas ausleiht
        String datum = Helper.getNowDateTime();
        dbConnector.executeStatement("SELECT * FROM mietet WHERE UserId = '"+id+"' AND RückgabeAm > '"+datum+"'");
        QueryResult x = dbConnector.getCurrentQueryResult();
        if (x.getRowCount() != 0) {
            return("Es wird noch etwas ausgeliehen!");
        }
        
        // Standort
        String adresseId = ich.getAdresse().getId();
        
        dbConnector.executeStatement("DELETE FROM benutzer WHERE benutzername ='" + benutzername + "'");
        // FIXME: Standort nicht löschen, falls diese noch von einem anderen Nutzer (in Zukunft: anderer Entität mit Standort)
        // über Fremdschlüssel genutzt wird.
        dbConnector.executeStatement("DELETE FROM standort WHERE id = '" + adresseId + "'");
        dbConnector.executeStatement("DELETE FROM bewertungen WHERE benutzerID = '" + id + "'");
        dbConnector.executeStatement("DELETE FROM wunschliste WHERE benutzerID = '" + id + "'");
        dbConnector.executeStatement("DELETE FROM mietet WHERE UserID = '" + id + "'");
        
        return null;
    }
    
    /**
     * Erstellt neuen Standortdatensatz falls nicht vorhanden. PLZ oder Hausnummer unter 1 sind ungültig.
     */ 
    public Standort getStandortFromDb(String pOrt, int pPlz, String pStraße, int pHausNr) {
        if (pPlz < 1 || pHausNr < 1) {
            // Ungültige ID/Zahleneingaben
            return null;
        }
        
        // Standort mit passenden Werten suchen
        dbConnector.executeStatement("SELECT ID, Ort, Postleitzahl, Straße, Hausnummer FROM standort WHERE Ort = '"+pOrt+"' AND Postleitzahl = '"+pPlz+"' AND Straße = '"+pStraße+"' AND Hausnummer = '"+pHausNr+"'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        
        if (r.getRowCount() == 0) {
            // Falls Standort nicht existiert: neuen Standort erstellen
            dbConnector.executeStatement("INSERT INTO standort(Ort, Postleitzahl, Straße, Hausnummer) VALUES('"+pOrt+"', '"+pPlz+"', '"+pStraße+"', '"+pHausNr+"')");
            dbConnector.executeStatement("SELECT ID, Ort, Postleitzahl, Straße, Hausnummer FROM standort WHERE Ort = '"+pOrt+"' AND Postleitzahl = '"+pPlz+"' AND Straße = '"+pStraße+"' AND Hausnummer = '"+pHausNr+"'");
            r = dbConnector.getCurrentQueryResult();
            
            if (r.getRowCount() == 0) {
                // Konnte Standort nicht erstellen
                return null;
            }
        }
        
        // Standort als Standortobjekt zurückgeben
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
        // Nur ausführen, falls man als Mitarbeiter angemeldet ist
        if(ich != null && ich.getIstMitarbeiter()){
            String marke = pMarke;
            String modell = pModell;
            String kategorie = pKategorie;
            int leistung = pLeistung;
            String kennzeichen = pKennzeichen.toUpperCase(); // Kennzeichen in Großbuchstaben
            int preisklasse = pPreisklasse;
            
            // Ist das gegebene Kennzeichen bereits vergeben?
            dbConnector.executeStatement("SELECT kennzeichen FROM auto WHERE Kennzeichen = '"+kennzeichen+"'");
            QueryResult r = dbConnector.getCurrentQueryResult();
            
            // Falls noch kein Auto das gegebene Kennzeichen hat: Neues Auto erstellen, sonst abbrechen
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
    
    /**
     * Nutzer werden in dem Nutzer ArrayList vom Verwalter gespeichert, Rückgabe dieser Methode ist eine Fehlermeldung. null = Erfolg
     */
    public String kundenSuchen(){
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen Kundeninformationen sehen!");
        
        // Benutzer mit Adresse zurückgeben, die keine Mitarbeiter sind
        String query = "SELECT "+
            "benutzer.ID, benutzer.Benutzername, benutzer.Vorname, benutzer.Name, benutzer.Geburtsdatum, benutzer.IstVerifiziert, benutzer.IstMitarbeiter, "+
            "standort.Ort, standort.Postleitzahl, standort.Straße, standort.Hausnummer, standort.ID "+
            "FROM benutzer, standort WHERE benutzer.AdresseID = standort.ID AND benutzer.IstMitarbeiter != 1";
        
        dbConnector.executeStatement(query);
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            kunden = new ArrayList(); // leere Liste
            return null;
        }
        
        ArrayList list = parseUsers(r);
        kunden = list;
        return null;
    }
    
    public String kundeVerifizieren(User user) {
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen Kunden verifizieren!");
        
        // Prüfen ob Nutzer existiert
        String query = "SELECT * FROM benutzer WHERE benutzer.ID = "+ user.getID();
        
        dbConnector.executeStatement(query);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Zu verifizierender Kunde existiert nicht!";
        }
        
        // Verifizieren
        query = "UPDATE benutzer SET IstVerifiziert = 1 WHERE benutzer.ID = "+ user.getID();
        
        dbConnector.executeStatement(query);
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        return null;
    }
    
    public String kundeEntverifizieren(User user) {
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen Kunden entverifizieren!");
        
        // Prüfen ob Nutzer existiert
        String query = "SELECT * FROM benutzer WHERE benutzer.ID = "+ user.getID();
        
        dbConnector.executeStatement(query);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Zu entverifizierender Kunde existiert nicht!";
        }
        
        // Entverifizieren
        query = "UPDATE benutzer SET IstVerifiziert = 0 WHERE benutzer.ID = "+ user.getID();
        
        dbConnector.executeStatement(query);
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        return null;
    }
    
    public String kundeLöschen(User user) {
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen Kunden entverifizieren!");
        
        // Prüfen ob Nutzer existiert
        String query = "SELECT benutzer.IstMitarbeiter FROM benutzer WHERE benutzer.ID = "+ user.getID();
        
        dbConnector.executeStatement(query);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Zu entverifizierender Kunde existiert nicht!";
        }
        
        // Andere Mitarbeiter dürfen nicht gelöscht werden
        if (r.getData()[0][0] == "1") {
            return "Kann anderen Mitarbeiter nicht löschen!";
        }
        
        // Löschen
        int id = user.getID();
        dbConnector.executeStatement("DELETE FROM benutzer WHERE ID ='" + id + "'");
        dbConnector.executeStatement("DELETE FROM standort WHERE id = '" + user.getAdresse().getId() + "'");
        dbConnector.executeStatement("DELETE FROM bewertungen WHERE benutzerID = '" + id + "'");
        dbConnector.executeStatement("DELETE FROM wunschliste WHERE benutzerID = '" + id + "'");
        dbConnector.executeStatement("DELETE FROM mietet WHERE UserID = '" + id + "'");
        
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        return null;
    }
    
    /**
     * Sucht nach Autos mit den angebeben Parametern, es können auch alle leer gelassen werden.
     * Autos werden in dem Auto ArrayList vom Verwalter gespeichert, Rückgabe dieser Methode ist eine Fehlermeldung. null = Erfolg
     * Es werden nur Autos angefragt die noch nicht vermietet sind!!
     */
    public String autoSuchen(String pMarke, String pModell, String pKategorie, int pLeistung){
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
        
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        dbConnector.executeStatement(query);
        auto = dbConnector.getCurrentQueryResult();
        
        ArrayList list = parseAutos(auto, false);
        autos = list;
        return null;
    }
    
    // Alle Attribute, die standartmäßig für Autotabellenanfragen gewollt sind
    private String autoSelectSql = "SELECT "+
        "auto.ID, auto.Marke, auto.Modell, auto.Kategorie, auto.Leistung, auto.Kennzeichen, "+
        "preisklassen.ID, preisklassen.Preis, preisklassen.ZusatzversicherungsPreis ";
    
    // Führt die obere SQL weiter, indem Preisklassen mitangefordert werden und dazu nur Autos zurückgegeben werden,
    // die momentan nicht von irgendwem gemietet werden.
    private String getAutoSql = autoSelectSql + " FROM auto JOIN preisklassen ON auto.PreisklasseID = preisklassen.ID "+
        "LEFT JOIN (SELECT AutoID, MAX(rückgabeAm) AS letzteRueckgabe FROM mietet GROUP BY AutoID) mietet1 ON auto.ID = mietet1.AutoID "+
        "WHERE mietet1.letzteRueckgabe < '" + Helper.getNowDateTime() + "' OR mietet1.letzteRueckgabe IS NULL ";
    
    // Führt die oberste SQL weiter, indem hier nur Autos, die jemals gemietet wurden, 
    // mit allen Mietinfos und Mieterinfos zurückgegeben werden.
    private String getGemieteteAutosSql = autoSelectSql + ", mietet.AusgeliehenAm, mietet.RückgabeAm, mietet.UserID, mietet.ID, "+
        "benutzer.Benutzername, benutzer.Name, benutzer.Vorname "+
        "FROM auto, preisklassen, mietet, benutzer WHERE auto.PreisklasseID = preisklassen.ID AND auto.ID = mietet.AutoID AND mietet.UserID = benutzer.ID ";
    
    /**
     * Selbst ausgeliehene Autos werden in dem Auto ArrayList vom Verwalter gespeichert.
     * Rückgabe dieser Methode ist eine Fehlermeldung. null = Erfolg
     */
    public String getGemieteteAutos(boolean aktuell){
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() == true) return ("Mitarbeiter dürfen keine Autos mieten!");
        
        autos = this.getGemieteteAutosInternal(ich.getID(), aktuell);
        return null; 
    }
    
    /**
     * Autos, die von einem bestimmten Nutzer geliehen wurden, werden in dem Auto ArrayList vom Verwalter gespeichert.
     * Rückgabe dieser Methode ist eine Fehlermeldung. null = Erfolg.
     */
    public String getGemieteteAutosVon(int userID, boolean aktuell){
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen gemietete Autos von anderen sehen!");
        
        autos = this.getGemieteteAutosInternal(userID, aktuell);
        return null; 
    }
    
    /**
     * Alle gemietete Autos werden in dem Auto ArrayList vom Verwalter gespeichert.
     * Rückgabe dieser Methode ist eine Fehlermeldung. null = Erfolg
     */
    public String getGemieteteAutosVonAllen(boolean aktuell){
        if (ich == null) return ("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() != true) return ("Nur Mitarbeiter dürfen gemietete Autos von anderen sehen!");
        
        autos = this.getGemieteteAutosInternal(0, aktuell);
        return null; 
    }
    
    /**
     * Falls userID = 0 ist, werden die gemieteten Autos von allen zurückgegeben.
     */
    private ArrayList getGemieteteAutosInternal(int userID, boolean aktuell) {
        String timestamp = Helper.getNowDateTime();
        String query = getGemieteteAutosSql;
        
        // Nach Nutzer suchen falls erfordert
        if (userID != 0) {
            query += " AND mietet.UserID = " + userID;
        }
        
        // Nur früher gemietete Autos, sonst momentan Gemietete
        if (!aktuell) {
            query += " AND mietet.RückgabeAm < '" + timestamp + "'";
        }
        else {
            query += " AND mietet.RückgabeAm >= '" + timestamp + "'";
        }
        
        dbConnector.executeStatement(query);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return new ArrayList(); // leere Liste
        }
        
        ArrayList list = parseAutos(r, true);
        return list;
    }
    
    // TODO: null zurückgeben bei Erfolg
    /**
     * Vermietet ein Auto an einen Nutzer. Controller.autoMieten() stellt sicher, dass verifizierte Kunden nur sich selbst,
     * und nicht anderen Benutzern Autos verleihen dürfen.
     */
    public String autoVermieten(int autoID, int userID, String rückgabeAm) {
        if (ich == null) {
            return "Nicht angemeldet!";
        }
        if (ich.getIstMitarbeiter() != true && ich.getIstVerifiziert() != true ) {
            return "Nur Berechtigte dürfen Autos vermieten!";    
        }
        
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        
        // Prüfen ob der Nutzer existiert und Kunde ist
        dbConnector.executeStatement("SELECT benutzer.ID, benutzer.IstMitarbeiter FROM benutzer WHERE benutzer.ID = " + userID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Kunde existiert nicht in der Datenbank!";
        }
        // Stellt sicher, dass ein Mitarbeiter nicht sich selbst oder anderen Mitarbeitern Autos verleihen darf.
        else if (!r.getData()[0][1].equals("0")) {
            return "Nur Kunden darf man Autos vermieten!";
        }
        
        String timestamp = Helper.getNowDateTime();
        
        // Prüfen ob niemand das Auto gerade am Mieten ist
        dbConnector.executeStatement("SELECT mietet.AutoID, mietet.RückgabeAm, mietet.UserID, benutzer.Benutzername FROM mietet, benutzer WHERE mietet.UserID = benutzer.ID AND mietet.AutoID = " + autoID + " AND mietet.RückgabeAm >= '" + timestamp + "'");
        r = dbConnector.getCurrentQueryResult();        
        if (r.getRowCount() > 0) {
            String[] row = r.getData()[0];
            return "Auto wird gerade bereits von Nutzer " + row[3] + " (ID " + row[2] + ") gemietet!";
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
    
    // TODO: null zurückgeben bei Erfolg, Exceptions durch zurückgegebene Fehlermeldungen ersetzen
    /**
     * Hiermit kann man ein selbst ausgeliehenes Auto frühzeitig wieder abgeben.
     */
    public String autoRückgabe(int autoID) {
        if (ich == null) throw new IllegalCallerException("Nicht angemeldet!");
        if (ich.getIstMitarbeiter() == true) throw new IllegalCallerException("Mitarbeiter dürfen keine selbst ausgeliehenen Autos zurückgeben!");
        
        /*
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        */
        
        String timestamp = Helper.getNowDateTime();
        int ownID = ich.getID();
        
        // Prüfen ob wir das Auto überhaupt gemietet haben
        dbConnector.executeStatement("SELECT * FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() == 0) {
            return "Du mietest dieses Auto nicht mal, hau ab!";
        }
        
        // Relation endlich aktualisieren (Rückgabedatum auf jetzt verkürzen)
        dbConnector.executeStatement("UPDATE mietet SET RückgabeAm = '" + timestamp + "' WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        // Prüfen ob wir immer noch das Auto mieten
        dbConnector.executeStatement("SELECT * FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "' AND mietet.UserID = " + ownID);
        r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() > 0) {
            return "Fehlschlag: Nutzer mietet das Auto immer noch!";
        }
        
        return "Nun mietet keiner das Auto mehr";   
    }
    
    /**
     * Setzt alle Mietrelationen eines Autos, deren Rückgabedatum in der Zukunft liegt, auf jetzt.
     */
    public String autoZwangsRücknahme(int autoID) {
        if (ich == null) return "Nicht angemeldet!";
        if (ich.getIstMitarbeiter() != true) return "Nur Mitarbeiter können Autos zurück ins Lager erzwingen!";
        
        // Prüfen ob Auto existiert
        if (!existiertAuto(autoID)) {
            return "Auto existiert nicht in der Datenbank!";
        }
        
        // Daten zurücksetzen
        String timestamp = Helper.getNowDateTime();
        dbConnector.executeStatement("UPDATE mietet SET RückgabeAm = '" + timestamp + "' WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "'");
        if (dbConnector.getErrorMessage() != null) {
            return "SQL Fehler: " + dbConnector.getErrorMessage();
        }
        
        // Prüfen ob nun immer noch wer das Auto mietet
        dbConnector.executeStatement("SELECT * FROM mietet WHERE mietet.AutoID = " + autoID + " AND mietet.RückgabeAm > '" + timestamp + "'");
        QueryResult r = dbConnector.getCurrentQueryResult();
        if (r.getRowCount() > 0) {
            return "Fehlschlag: " + r.getData().length + " Nutzer mieten das Auto immer noch!";
        }
        
        return "Nun mietet keiner das Auto mehr";        
    }
    
    private boolean existiertAuto(int autoID) {
        dbConnector.executeStatement("SELECT ID FROM auto WHERE auto.ID = " + autoID);
        QueryResult r = dbConnector.getCurrentQueryResult();
        return r.getRowCount() > 0;
    }    
    
    /**
     * Erstellt eine ArrayList aus Autos anhand der rohen (String[][]) SQL-Rückgabe.
     */
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
            Auto auto; 
            
            // Falls auch die über SQL erhaltene Mietinfo + Mieterinfo der Autos lokal mitgespeichert werden müssen,
            // werden auch diese ausgelesen und hinzugefügt
            if (hatMietInfo) {
                int mieterId = Helper.tryParseInt(row[11]);
                int mietId = Helper.tryParseInt(row[12]);
                User mieter = new User(mieterId, row[13], row[14], row[15]);
                MietInfo mietInfo = new MietInfo(mietId, row[9], row[10], mieter);
                auto = new Auto(aId, row[1], row[2], row[3], aLeistung, row[5], pk, mietInfo);
            }
            else {
                auto = new Auto(aId, row[1], row[2], row[3], aLeistung, row[5], pk);
            }
            
            list.add(auto);    
        }
        
        return list;
    }
    
    /**
     * Erstellt eine ArrayList aus Benutzern anhand der rohen (String[][]) SQL-Rückgabe.
     */
    private ArrayList parseUsers(QueryResult result) {
        ArrayList list = new ArrayList();
        String[][] userArray = result.getData();
        
        for(int i = 0; i < userArray.length; i++){
            String[] row = userArray[i];
            
            int uId = Helper.tryParseInt(row[0]);
            boolean istVerifiziert = Helper.tryParseBool(row[5]);
            boolean istMitarbeiter = Helper.tryParseBool(row[6]);
            int plz = Helper.tryParseInt(row[8]);
            int hausNr = Helper.tryParseInt(row[10]);
            
            Standort adresse = new Standort(row[11], row[7], plz, row[9], hausNr);
            User user = new User(uId, row[1], row[3], row[2], row[4], istMitarbeiter, istVerifiziert, adresse);
            list.add(user);    
        }
        
        return list;
    }
    
    public int getBenutzerID(String pBenutzername) {
        if (!pBenutzername.isEmpty()){
            dbConnector.executeStatement("SELECT ID FROM benutzer WHERE Benutzername = '"+pBenutzername+"'");
            QueryResult r = dbConnector.getCurrentQueryResult();
            
            // Nutzer existiert nicht
            if (r.getRowCount() == 0) {
                return -1;
            }
            
            return Integer.parseInt(r.getData()[0][0]);
        } else {
            // Parameter ungültig
            return -1;
        }
    }
    
    public String passwortÄndern(String pBenutzername, String pPasswort)  {
        int id = getBenutzerID(pBenutzername);
        String passwortHash = Helper.toSha256(pPasswort);
        if(id == -1) {
            return("Nutzer nicht gefunden!");
        }
        dbConnector.executeStatement("UPDATE benutzer SET passwort = '"+passwortHash+"'WHERE id= '"+id+"'");
        return("Passwort geändert");
    }
    
    /**
     * Gibt den Nutzer, mit dem man angemeldet ist, zurück. Null = nicht angemeldet.
     */
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
}
