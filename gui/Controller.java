package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import java.util.Date;
import java.time.LocalDateTime;

//import java.time.LocalDateTime;
import java.time.LocalDate;

public class Controller {
    // Für Anmeldung:
    
    @FXML
    private Button passwortVergessen1;
    
    @FXML
    private Button zuHauptseiteAnmeldung;
    
    @FXML
    private Button anmelden1;

    @FXML
    private TextField benutzername1;

    @FXML
    private TextField passwort1;
    
    @FXML
    private Button registrieren1;
    
    @FXML
    private Label text2;
    
    // Für Regisrierung:
    @FXML
    private Button zuHauptseiteRegistrierung;
    
    @FXML
    private TextField benutzername2;

    @FXML
    private DatePicker geburtsdatum1;

    @FXML
    private TextField hausnummer1;

    @FXML
    private TextField name1;

    @FXML
    private TextField ort1;

    @FXML
    private TextField passwort2;

    @FXML
    private Button registrieren2;

    @FXML
    private TextField straße1;

    @FXML
    private TextField vorname1;

    @FXML
    private Button zuAnmeldung1;
    
    @FXML
    private Label text1;
    
    @FXML
    private TextField plz1;
    
    // Um Autos hinzuzufügen:
    
    @FXML
    private Button zuHauptseiteAutoHinzufügen;
    
    @FXML
    private Button autoHinzufügen1;

    @FXML
    private TextField kategorie1;

    @FXML
    private TextField kennzeichen1;

    @FXML
    private TextField leistung1;

    @FXML
    private TextField marke1;

    @FXML
    private TextField modell1;

    @FXML
    private TextField preisklasse1;

    @FXML
    private Label text3;

   
    // Für die Hauptseite:
    
    @FXML
    private ImageView bild1;

    @FXML
    private ImageView bild2;
    
    @FXML
    private TextField benutzer11;

    @FXML
    private Button miethistorie2;
    
    @FXML
    private Button hsBtnNutzerverwaltung;
    
    @FXML
    private Button abmelden1;
    
    @FXML
    private Label kontoLöschen2;
    
    @FXML
    private Label preisAnzeige1;
    
    @FXML
    private Label LeistungAnzeige;

    @FXML
    private Button anmelden3;

    @FXML
    private Button ausleihen1;

    @FXML
    private TextField kategorie2;

    @FXML
    private Label kategorieAnzeige;

    @FXML
    private Button kontoLöschen1;

    @FXML
    private TextField marke2;

    @FXML
    private Label markeAnzeige;

    @FXML
    private TextField modell2;

    @FXML
    private Label modellAnzeige;

    @FXML
    private Slider ps1;
    
    @FXML
    private TableView<Auto> autoListe1;
    
    @FXML
    private TableColumn<Auto, String> markeListe1; 
    
    @FXML
    private TableColumn<Auto, String> modellListe1;
    
    @FXML
    private TableColumn<Auto, String> kategorieListe1; 
    
    @FXML
    private TableColumn<Auto, String> psListe1; 
    
    @FXML
    private TableColumn<Auto, String> preisListe1; 
    
    @FXML
    private TableColumn<Auto, Integer> idListe1; 
    
    @FXML
    private Button suchen1;
    
    @FXML
    private Button autoHinzuügen1;

    @FXML
    private Button autoAuswählen1;

    @FXML
    private Label preis4;
    
    @FXML
    private DatePicker rückgabe1;
    
    
    //Für die Miethistorie:
    @FXML
    private TableColumn<Auto, Integer> id100;

    @FXML
    private TableColumn<Auto, String> kategorie100;

    @FXML
    private TableColumn<Auto, String> leistung100;

    @FXML
    private TableColumn<Auto, String> marke100;

    @FXML
    private TableView<Auto> miethistorie1;

    @FXML
    private TableColumn<Auto, String> modell100;
    
    @FXML
    private TableColumn<Auto, String> mieterBenutzername100;
    
    @FXML
    private TableColumn<Auto, String> mieterName100;
    
    @FXML
    private TableColumn<Auto, String> mieterVorname100;
    
    @FXML
    private TableColumn<Auto, String> ausgeliehen100;
    
    @FXML
    private TableColumn<Auto, String> rückgabe100;
    
    @FXML
    private TableColumn<Auto, String> mietId100;
    
    @FXML
    private Text benutzer10;

    @FXML
    private TextField benutzerEingabe10;

    @FXML
    private Button suchen10;
    
    @FXML
    private Button zurück10;
    
    @FXML
    private Button autoZurückgeben1;
    
    @FXML
    private CheckBox tick1;
    
    @FXML
    private Label fehler10;
    
    //Für Passwort ändern:
    
    @FXML
    private TextField benutzer111;

    @FXML
    private Label fehler11;

    @FXML
    private TextField frage1;

    @FXML
    private TextField passwort111;

    @FXML
    private Button passwortÄndern1;
    
    @FXML
    private Button zurück111;
    
    @FXML
    private Label benutzerInfoSpan;
    
    // Für Nutzerverwaltung:
    
    @FXML
    private TableView<User> nvTabelle;
    
    @FXML
    private TableColumn<User, Integer> nvNutzerId100;

    @FXML
    private TableColumn<User, String> nvBenutzername100;

    @FXML
    private TableColumn<User, String> nvVorname100;

    @FXML
    private TableColumn<User, String> nvName100;

    @FXML
    private TableColumn<User, String> nvGBDatum100;
    
    @FXML
    private TableColumn<User, Boolean> nvVerif100;
    
    @FXML
    private TableColumn<User, Boolean> nvMitarb100;
    
    @FXML
    private TableColumn<User, String> nvOrt100;
    
    @FXML
    private TableColumn<User, Integer> nvPlz100;
    
    @FXML
    private TableColumn<User, String> nvStraße100;
    
    @FXML
    private TableColumn<User, Integer> nvHausnr100;

    @FXML
    private Button nvBtnSuchen;
    
    @FXML
    private Button nvBtnVerifizieren;
    
    @FXML
    private Button nvBtnLöschen;
    
    @FXML
    private Label nvSpan;
    
    
    // Die Verwalter Klasse ist in diesem Fall unser Model
    private Verwalter model ;
    
    Auto ausgewähltesAuto;
    
    public Controller() {
        model = ModelLoader.getModel();
    }
    
    public void initialize() {
        
    }    
    
    @FXML 
    void anmelden(ActionEvent event) throws IOException {
        String benutzername = benutzername1.getText();
        if (benutzername.equals("")) {
            text2.setText("Kein Benutzername angegeben!");
            return;
        }
        String error = Helper.isInputValid(benutzername, Constants.benutzernameMaxLength);
        if (error != null) {
            text2.setText("Ungültiger Benutzername: " + error);
            return;
        }
        
        String passwort = passwort1.getText();
        if (passwort.equals("")) {
            text2.setText("Kein Passwort angegeben!");
            return;
        }
        error = Helper.isInputValid(passwort, Constants.passwortMaxLength);
        if (error != null) {
            text2.setText("Ungültiges Passwort: " + error);
            return;
        }
        
        String rückgabe = model.anmelden(benutzername, passwort);
        if (rückgabe.equals("Erfolgreich als Kunde angemeldet!")|| rückgabe.equals("Erfolgreich als Mitarbeiter angemeldet!")) {
            switchToHauptseite(event);
        } else {
            text2.setText(rückgabe);   
        }
    }
    
    @FXML 
    void abmelden(ActionEvent event) throws IOException {
        model.abmelden();
        switchToHauptseite(event); //Hauptseite neuladen
        benutzerInfoSpan.setText("Nicht angemeldet");
    }
    
    @FXML
    void registrieren(ActionEvent event) throws IOException {
        //Überprüfung dass die Eingabefelder alle gefüllt sind
        String benutzername = benutzername2.getText();
        if(benutzername.equals("")) {
            text1.setText("Kein Benutzername angegeben!");
            return;
        }
        String error = Helper.isInputValid(benutzername, Constants.benutzernameMaxLength);
        if (error != null) {
            text1.setText("Ungültiger Benutzername: " + error);
            return;
        }
        
        String passwort = passwort2.getText();
        if(passwort.equals("")) {
            text1.setText("Kein Passwort angegeben!");
            return;
        }
        error = Helper.isInputValid(passwort, Constants.passwortMaxLength);
        if (error != null) {
            text1.setText("Ungültiges Passwort: " + error);
            return;
        }
        
        String name = name1.getText();
        if(name.equals("")) {
            text1.setText("Kein Name angegeben!");
            return;
        }
        error = Helper.isInputValid(name, Constants.nameMaxLength);
        if (error != null) {
            text1.setText("Ungültiger Name: " + error);
            return;
        }
        
        String vorname = vorname1.getText();
        if(vorname.equals("")) {
            text1.setText("Kein Vorname angegeben!");
            return;
        }
        error = Helper.isInputValid(vorname, Constants.vornameMaxLength);
        if (error != null) {
            text1.setText("Ungültiger Vorname: " + error);
            return;
        }
        
        String plz = plz1.getText();
        if(plz.equals("")) {
            text1.setText("Keine Postleitzahl angegeben!");
            return;
        }
        int plzParsed = Helper.tryParseInt(plz);
        if(plzParsed <= 0) {
            text1.setText("Postleitzahl ist keine gültige Zahl!");
            return;
        }
        
        String ort = ort1.getText();
        if(ort.equals("")) {
            text1.setText("Kein Ort angegeben!");
            return;
        }
        error = Helper.isInputValid(ort, Constants.ortMaxLength);
        if (error != null) {
            text1.setText("Ungültiger Ort: " + error);
            return;
        }
        
        String straße = straße1.getText();
        if(straße.equals("")) {
            text1.setText("Keine Straße angegeben!");
            return;
        }
        error = Helper.isInputValid(straße, Constants.straßeMaxLength);
        if (error != null) {
            text1.setText("Ungültige Straße: " + error);
            return;
        }
        
        String hausnummer = hausnummer1.getText();
        if(hausnummer.equals("")) {
            text1.setText("Keine Hausnummer angegeben!");
            return;
        }
        int hausNrParsed = Helper.tryParseInt(hausnummer);
        if(hausNrParsed <= 0) {
            text1.setText("Hausnummer ist keine gültige Zahl!");
            return;
        }
        
        String geburtsdatum = geburtsdatum1.getValue().toString();
        if(geburtsdatum == null || geburtsdatum.equals("")) {
            text1.setText("Geburtsdatum muss natürlich ausgefüllt sein!");
            return;
        }
        error = Helper.isInputValid(geburtsdatum, Constants.fullDateTimeMaxLength);
        if (error != null) {
            text1.setText("Ungültiges Geburtsdatum: " + error);
            return;
        }

        // Ab hier ist die Anmeldung erfolgreich ausgelesen und validiert!
        String rückgabe = model.registrieren(benutzername, passwort, name, vorname, geburtsdatum, new Standort("0", ort, plzParsed, straße, hausNrParsed), 0, 0);
        if (rückgabe.equals("Konto angelegt!")) {
            switchToHauptseite(event);
        } else {
            text1.setText(rückgabe);   
        }
    }
    
    void setBenutzerInfoSpan(User user) {
        // UserInfo label setzen
        if (user.getIstMitarbeiter()) {
            benutzerInfoSpan.setText("Als " + user.getBenutzername() + " (Mitarbeiter) angemeldet");
        }
        else if (user.getIstVerifiziert()) {
            benutzerInfoSpan.setText("Als " + user.getBenutzername() + " (verifizierter Kunde) angemeldet");
        }
        else {
            benutzerInfoSpan.setText("Als " + user.getBenutzername() + " (Kunde) angemeldet");
        }
    }
    
    /**
     * Methode gibt alle aktuellen und zurückliegenden Mieten des ausgelesen Benutzers
     * in der Tabelle zurück.
     */
    @FXML
    void mieteSuchen(ActionEvent event) throws IOException {
        String benutzername = benutzerEingabe10.getText();
        ObservableList<Auto> daten;
        
        if (model.getUser().getIstMitarbeiter()) {
            if (benutzername.equals("")) {
                fehler10.setText("Lade alle Miethistorien");
                model.getGemieteteAutosVonAllen(tick1.isSelected());
            }
            else {
                String error = Helper.isInputValid(benutzername, Constants.benutzernameMaxLength);
                if (error != null) {
                    fehler10.setText("Ungültiger Benutzername: " + error);
                    return;
                }
                int benutzerID = model.getBenutzerID(benutzername);
                if (benutzerID <= 0) {
                    fehler10.setText("Benutzer '" + benutzername + "' existiert nicht. Vielleicht Tippfehler?");
                    return;
                }
                
                fehler10.setText("Lade Miethistorie von '" + benutzername + "'");
                model.getGemieteteAutosVon(benutzerID, tick1.isSelected());
            }
        }
        else {
            fehler10.setText("Lade eigene Miethistorie");
            int benutzerID = model.getUser().getID();
            model.getGemieteteAutos(tick1.isSelected());
        }
        if(tick1.isSelected()){
            autoZurückgeben1.setVisible(true);
        } else {
            autoZurückgeben1.setVisible(false);
        }
        
        id100.setCellValueFactory(new PropertyValueFactory<>("iD"));
        marke100.setCellValueFactory(new PropertyValueFactory<>("marke"));
        modell100.setCellValueFactory(new PropertyValueFactory<>("modell"));
        kategorie100.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
        leistung100.setCellValueFactory(new PropertyValueFactory<>("leistung"));
        mieterBenutzername100.setCellValueFactory(new PropertyValueFactory<>("mieterBenutzername"));
        mieterName100.setCellValueFactory(new PropertyValueFactory<>("mieterName"));
        mieterVorname100.setCellValueFactory(new PropertyValueFactory<>("mieterVorname"));
        ausgeliehen100.setCellValueFactory(new PropertyValueFactory<>("ausleihDatum"));
        rückgabe100.setCellValueFactory(new PropertyValueFactory<>("rückgabeDatum"));
        mietId100.setCellValueFactory(new PropertyValueFactory<>("mietId"));
        
        daten = FXCollections.observableArrayList(model.getAutos());
        miethistorie1.setItems(daten);
    }
    
    @FXML
    void autoZurückgeben(ActionEvent event) {
        int autoID = miethistorie1.getSelectionModel().getSelectedItem().getID();
        if (model.getUser().getIstMitarbeiter()){
            fehler10.setText(model.autoZwangsRücknahme(autoID));    
        } else {
            fehler10.setText(model.autoRückgabe(autoID));   
        }
    }
    
    @FXML
    void kontoLöschen(ActionEvent event) throws IOException{
        String message = model.kontoLoeschen();
        
        // Kontolöschung erfolgreich: Abmelden, Hauptseite neuladen, erst dann Nachricht zeigen, 
        // sonst Fehlermeldung zeigen
        if (message == null) {
            // Abmelden
            model.abmelden();
            switchToKontoGelöscht(event);
        }
        else {
            kontoLöschen2.setText(message);
        }
    }
    
    /**
     * Sucht das Auto mit den eingegeben Merkmalen und zeigt alle Treffer in der Tabelle an.
     */
    @FXML
    void autoSuchen(ActionEvent event){
        idListe1.setCellValueFactory(new PropertyValueFactory<>("iD"));
        markeListe1.setCellValueFactory(new PropertyValueFactory<>("marke"));
        modellListe1.setCellValueFactory(new PropertyValueFactory<>("modell"));
        kategorieListe1.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
        psListe1.setCellValueFactory(new PropertyValueFactory<>("leistung"));
        preisListe1.setCellValueFactory(new PropertyValueFactory<>("preis"));
        
        // Eingaben validieren
        String marke = marke2.getText();
        String error = Helper.isInputValid(marke, Constants.markeMaxLength);
        if (error != null) {
            kontoLöschen2.setText("Ungültige Marke: " + error);
            return;
        }
        String modell = modell2.getText();
        error = Helper.isInputValid(modell, Constants.modellMaxLength);
        if (error != null) {
            kontoLöschen2.setText("Ungültiges Modell: " + error);
            return;
        }
        String kategorie = kategorie2.getText();
        error = Helper.isInputValid(kategorie, Constants.kategorieMaxLength);
        if (error != null) {
            kontoLöschen2.setText("Ungültige Kategorie: " + error);
            return;
        }
        
        int leistung = (int)ps1.getValue();
        String meldung = model.autoSuchen(marke, modell, kategorie, leistung);
        if (meldung != null) {
            System.out.println(meldung);
            return;
        }
        
        ObservableList<Auto> daten = FXCollections.observableArrayList(model.getAutos());
        autoListe1.setItems(daten);
    }
    
    /**
     * Wechselt zur Registrierungs-Seite.
     */
    @FXML
    void switchToRegistrierung(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/registrierung.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    } 
    
    /**
     * Wechselt zur Passwort Änderungs Seite.
     */
    @FXML
    void switchToPasswortÄndern(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/passwortÄndern.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();        
    }
    
    /**
     * Ruft die Hauptseite auf oder lädt sie neu.
     * Überprüft dabei ob ein Nutzer angemeldet ist usw. um aufgrunddessen
     * Elemente anzuzeigen oder zu verbergen.
     */
    @FXML
    void switchToHauptseite(ActionEvent event)throws IOException{
        // Verbesserter Code von ChatGPT
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/hauptseite.fxml"));
        Parent root = loader.load();
        
        // Zugriff auf den Controller
        Controller controller = loader.getController(); 
                
        // Code aus Vorlage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
        
        // Sorgt dafür dass die Methoden erst nach dem vollständigem Laden des
        // Fensters ausgeführt werden
        Platform.runLater(() -> {
            if(model.getUser() != null){
                controller.abmelden1.setVisible(true);
                controller.anmelden3.setVisible(false);
                controller.kontoLöschen1.setVisible(true); 
                controller.miethistorie2.setVisible(true);
                
                if(model.getUser().getIstMitarbeiter()) {
                    controller.autoHinzuügen1.setVisible(true);
                    controller.hsBtnNutzerverwaltung.setVisible(true);
                }
                
                controller.setBenutzerInfoSpan(model.getUser());
            }
        });
    }
    
    /**
     * Ruft die Konto-Gelöscht-Seite auf.
     */
    @FXML
    void switchToKontoGelöscht(ActionEvent event)throws IOException{
        // Verbesserter Code von ChatGPT
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/kontoGelöscht.fxml"));
        Parent root = loader.load();
        
        // Zugriff auf den Controller
        Controller controller = loader.getController(); 
                
        // Code aus Vorlage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
        
        // Sorgt dafür dass die Methoden erst nach dem vollständigem Laden des
        // Fensters ausgeführt werden
        Platform.runLater(() -> {
            // Keine interaktiven Inhalte außer Zurück-Knopf auf dieser Seite
        });
    }
    
    @FXML 
    void nvVerifizieren(ActionEvent event)throws IOException{
        User user = nvTabelle.getSelectionModel().getSelectedItem();
        if (user == null) {
            nvSpan.setText("Kein Kunde zum Verifizieren ausgewählt!");
            return;
        }
        
        if (user.getIstVerifiziert()){
            String message = model.kundeEntverifizieren(user);
            if (message == null) message = "Kunde wurde entverifiziert!";
            
            nvSpan.setText(message);    
        } else {
            String message = model.kundeVerifizieren(user);
            if (message == null) message = "Kunde wurde verifiziert!";
            
            nvSpan.setText(message);   
        }
    }
    
    @FXML 
    void nvLöschen(ActionEvent event)throws IOException{
        User user = nvTabelle.getSelectionModel().getSelectedItem();
        if (user == null) {
            nvSpan.setText("Kein Kunde zum Löschen ausgewählt!");
            return;
        }
        
        String message = model.kundeLöschen(user);
        if (message == null) message = "Kunde wurde gelöscht!";
        
        nvSpan.setText(message);    
    }
    
    @FXML 
    void nvSuchen(ActionEvent event)throws IOException{
        //nvTabelleInit();
        
        nvNutzerId100.setCellValueFactory(new PropertyValueFactory<>("iD"));
        nvBenutzername100.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        nvName100.setCellValueFactory(new PropertyValueFactory<>("name"));
        nvVorname100.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nvGBDatum100.setCellValueFactory(new PropertyValueFactory<>("geburtsdatum"));
        nvMitarb100.setCellValueFactory(new PropertyValueFactory<>("istMitarbeiterDE"));
        nvVerif100.setCellValueFactory(new PropertyValueFactory<>("istVerifiziertDE"));
        nvOrt100.setCellValueFactory(new PropertyValueFactory<>("ort"));
        nvPlz100.setCellValueFactory(new PropertyValueFactory<>("plz"));
        nvStraße100.setCellValueFactory(new PropertyValueFactory<>("straße"));
        nvHausnr100.setCellValueFactory(new PropertyValueFactory<>("hausnr"));
        
        String message = model.kundenSuchen();
        if (message != null) {
            nvSpan.setText(message);
            return;
        }
        
        ObservableList<User> daten = FXCollections.observableArrayList(model.getKunden());
        nvTabelle.setItems(daten);
    }
    
    void nvTabelleInit() {
        nvNutzerId100.setCellValueFactory(new PropertyValueFactory<>("iD"));
        nvBenutzername100.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        nvName100.setCellValueFactory(new PropertyValueFactory<>("name"));
        nvVorname100.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nvGBDatum100.setCellValueFactory(new PropertyValueFactory<>("geburtsdatum"));
        nvMitarb100.setCellValueFactory(new PropertyValueFactory<>("istMitarbeiter"));
        nvVerif100.setCellValueFactory(new PropertyValueFactory<>("istVerifiziert"));
        nvOrt100.setCellValueFactory(new PropertyValueFactory<>("ort"));
        nvPlz100.setCellValueFactory(new PropertyValueFactory<>("plz"));
        nvStraße100.setCellValueFactory(new PropertyValueFactory<>("straße"));
        nvHausnr100.setCellValueFactory(new PropertyValueFactory<>("hausnr"));
        
        ObservableList<User> daten = FXCollections.observableArrayList(model.getKunden());
        nvTabelle.setItems(daten);
    }
    
    /**
     * Ruft die Nutzerverwaltungs-Seite auf.
     * Schlägt fehl wenn man nicht angemeldet oder kein Mitarbeiter ist.
     */
    
    @FXML 
    void switchToNutzerverwaltung(ActionEvent event)throws IOException{
        if (model.getUser() == null || !model.getUser().getIstMitarbeiter()) return;
        
        // Verbesserter Code von ChatGPT
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/nutzerverwaltung.fxml"));
        Parent root = loader.load();
        
        // Zugriff auf den Controller
        Controller controller = loader.getController(); 
                
        // Code aus Vorlage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
        
        // Sorgt dafür dass die Methoden erst nach dem vollständigem Laden des
        // Fensters ausgeführt werden
        Platform.runLater(() -> {
            if(model.getUser().getIstMitarbeiter()) {
                //nvTabelleInit();
                controller.nvBtnSuchen.setVisible(true);
                controller.nvBtnVerifizieren.setVisible(true);
                controller.nvBtnLöschen.setVisible(true);
            }
            else {
                controller.nvSpan.setText("Nur Mitarbeiter dürfen Kunden verwalten!");
            }
        });    
    }

    /**
     * Ruft die Miethistorien-Seite auf.
     * Überprüft dabei ob ein Nutzer Mitarbeiter ist usw. um aufgrunddessen
     * Elemente anzuzeigen oder zu verbergeben.
     */
    
    @FXML 
    void switchToMiethistorie(ActionEvent event)throws IOException{
        // Verbesserter Code von ChatGPT
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/miethistorie.fxml"));
        Parent root = loader.load();
        
        // Zugriff auf den Controller
        Controller controller = loader.getController(); 
                
        // Code aus Vorlage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
        
        // Sorgt dafür dass die Methoden erst nach dem vollständigem Laden des
        // Fensters ausgeführt werden
        Platform.runLater(() -> {
            controller.suchen10.setVisible(true);
            
            if(model.getUser().getIstMitarbeiter()) {
                controller.benutzerEingabe10.setVisible(true);
            } else {
                //controller.benutzer10.setText(model.getUser().getBenutzername());
            }
        });    
    }
    
    /**
     * Ruft die Anmeldungs-Seite auf.
     */
    @FXML
    void switchToAnmeldung(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/anmeldung.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    /**
     * Ruft die autoHinzufügen-Seite auf.
     */
    @FXML
    void switchToHinzufügen(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/autoHinzufügen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    /**
     * Fügt Autos mit den in den dazugehörigen Feldern eingegebenen Daten in die Datenbank ein.
     */
    @FXML
    void autoHinzufügen(ActionEvent event)throws IOException {
        //Prüft dass alle Felder ausgefüllt sind
        String marke = marke1.getText();
        if(marke1.getText().equals("")) {
            text3.setText("Marke nicht angegeben!");
            return;
        }
        String error = Helper.isInputValid(marke, Constants.markeMaxLength);
        if (error != null) {
            text3.setText("Ungültige Marke: " + error);
            return;
        }
        
        String modell = modell1.getText();
        if(modell1.getText().equals("")) {
            text3.setText("Modell nicht angegeben!");
            return;
        }
        error = Helper.isInputValid(modell, Constants.modellMaxLength);
        if (error != null) {
            text3.setText("Ungültiges Modell: " + error);
            return;
        }
        
        String kennzeichen = kennzeichen1.getText();
        if(kennzeichen1.getText().equals("")) {
            text3.setText("Kennzeichen nicht angegeben!");
            return;
        }
        error = Helper.isInputValid(kennzeichen, Constants.kennzeichenMaxLength);
        if (error != null) {
            text3.setText("Ungültiges Kennzeichen: " + error);
            return;
        }
        
        String leistung = leistung1.getText(); 
        if(leistung1.getText().equals("")) {
            text3.setText("Leistung nicht angegeben!");
            return;
        }
        int leistungParsed = Helper.tryParseInt(leistung1.getText());
        if(leistungParsed <= 0) {
            text3.setText("Leistung ist keine gültige Zahl!");
            return;
        }
        
        String kategorie = kategorie1.getText();
        if(kategorie1.getText().equals("")) {
            text3.setText("Kategorie nicht angegeben!");
            return;
        }
        error = Helper.isInputValid(kategorie, Constants.kategorieMaxLength);
        if (error != null) {
            text1.setText("Ungültige Kategorie: " + error);
            return;
        }
        
        String preisklasse = preisklasse1.getText();
        if(preisklasse1.getText().equals("")) {
            text3.setText("Preisklasse nicht angegeben!");
            return;
        }
        int pkParsed = Helper.tryParseInt(preisklasse1.getText());
        if(pkParsed <= 0) {
            text3.setText("Preisklasse ist kein gültiger Betrag!");
            return;
        }
        
        text3.setText(model.autoHinzufügen(marke, modell, kategorie, leistungParsed, kennzeichen, pkParsed));
    }

    /**
     * Ein in der Tabelle ausgewähltes Auto wird durch Drücken des dazu-
     * gehörigen Buttons in die Anzeige daneben gebracht.
     */
    @FXML
    void autoÜbertragen(ActionEvent event)throws IOException {
        User user = model.getUser();
        if(user != null && (user.getIstMitarbeiter() || user.getIstVerifiziert())){
            ausleihen1.setVisible(true);
            rückgabe1.setVisible(true);
        }
        
        bild1.setVisible(true);
        bild2.setVisible(true);
        // TODO: Auto automatisch durch Doppelklick auf Tabellenzeile auswählen
        ausgewähltesAuto = autoListe1.getSelectionModel().getSelectedItem();
        
        if (ausgewähltesAuto != null) {
            String marke = ausgewähltesAuto.getMarke();
            String modell = ausgewähltesAuto.getModell();
            String kategorie = ausgewähltesAuto.getKategorie();
            int leistung = ausgewähltesAuto.getLeistungNum();
            Preisklasse pk = ausgewähltesAuto.getPreisklasse();
            int preis = pk.getPreis();
            
            markeAnzeige.setText(marke);
            modellAnzeige.setText(modell);
            kategorieAnzeige.setText(kategorie);
            LeistungAnzeige.setText(""+leistung+" PS");
            preisAnzeige1.setText(""+preis+" € / Tag");
        }
        
        if(user != null && user.getIstMitarbeiter()){
            benutzer11.setVisible (true);
        }
    }
    
    /**
     * Ein Auto mit den auf der Hauptseite gewählten Präferenzen wird gemietet.
     * Ein Mitarbeiter kann ein Auto an jeden, ein Verifizierter Kunde jedoch nur
     * an sich selbst vermieten.
     */
    @FXML
    void autoMieten(ActionEvent event)throws IOException {
        User user = model.getUser();
        if (user == null) {
            kontoLöschen2.setText("Nicht angemeldet!");
            return;
        }
        
        // Wichtig, weil wenn verifizierter Nutzer ein Auto selbst ausleihen will geht dies nur auf seinen Namen!!
        String benutzername;
        if(user.getIstVerifiziert() && !user.getIstMitarbeiter()){
            benutzername = user.getBenutzername();
        } else {
            benutzername = benutzer11.getText();    
        }
        
        if (benutzername.equals("")) {
            kontoLöschen2.setText("Kein Benutzername angegeben!");
            return;
        }
        String error = Helper.isInputValid(benutzername, Constants.benutzernameMaxLength);
        if (error != null) {
            kontoLöschen2.setText("Ungültiger Benutzername: " + error);
            return;
        }
        
        LocalDate rückgabeDatum = rückgabe1.getValue();
        
        if (rückgabeDatum == null) {
            kontoLöschen2.setText("Kein Rückgabedatum angegeben!");
            return;
        }
        String rückgabeDatumParsed = rückgabe1.getValue().toString();
        // TODO: Nachschauen ob diese Extraprüfung überhaupt notwendig ist
        if (rückgabeDatumParsed == null || rückgabeDatumParsed.equals("")) {
            kontoLöschen2.setText("Kein Rückgabedatum angegeben!");
            return;
        }
        error = Helper.isInputValid(rückgabeDatumParsed, Constants.fullDateTimeMaxLength);
        if (error != null) {
            kontoLöschen2.setText("Ungültiges Rückgabedatum: " + error);
            return;
        }
        else if (LocalDate.now().isAfter(rückgabeDatum)) {
            kontoLöschen2.setText("Rückgabedatum darf nicht in der Vergangenheit sein!");
            return;
        }
        
        int autoId = ausgewähltesAuto.getID();
        int userId = -1;
        if(user.getIstMitarbeiter()){
            userId = model.getBenutzerID(benutzername);       
        } else if(user.getIstVerifiziert()) {
            userId = user.getID();    
        }
        else {
            kontoLöschen2.setText("Keine Berechtigung!");
            return;
        }
        
        if (userId <= 0) {
            kontoLöschen2.setText("Kein Kunde unter dem Namen vorhanden!");
            return;
        }
        kontoLöschen2.setText(model.autoVermieten(autoId, userId, rückgabeDatumParsed)); 
    }
    
    @FXML
    void passwortÄndern(ActionEvent event)throws IOException {
        String passwort = passwort111.getText();
        String benutzername = benutzer111.getText();
        if(benutzername.equals("")){
            fehler11.setText("Kein Benutzername angegeben!");
            return;
        }
        String error = Helper.isInputValid(benutzername, Constants.benutzernameMaxLength);
        if(error != null){
            fehler11.setText("Benutzername: " + error);
            return;    
        }
        
        if(passwort.equals("")){
            fehler11.setText("Kein Benutzername angegeben!");
            return;
        }
        error = Helper.isInputValid(passwort, Constants.passwortMaxLength);
        if(error != null){
            fehler11.setText("Passwort: " + error);
            return;    
        }
        
        if(frage1.getText().equalsIgnoreCase("Ja")) {
            fehler11.setText(model.passwortÄndern(benutzername, passwort)); 
            switchToAnmeldung(event);
        } else {
            fehler11.setText("Sicherheitsfrage falsch!!");
        }
    }
}