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
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class Controller {
    // Für Anmeldung:
    
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
    private TableView<String> autoListe1;
    
    @FXML
    private TableColumn<String, String> markeListe1; 
    
    @FXML
    private TableColumn<String, String> modellListe1;
    
    @FXML
    private TableColumn<String, String> kategorieListe1; 
    
    @FXML
    private TableColumn<String, Integer> psListe1; 
    
    @FXML
    private TableColumn<String, Integer> preisListe1; 
    
    // Die Verwalter Klasse ist in diesem Fall unser Model
    private Verwalter model ;
    
    public Controller() {
        model = ModelLoader.getModel();
    }
    
    public void initialize() {
        
    }    
    
    @FXML 
    void anmelden(ActionEvent event) {
        String benutzername = benutzername1.getText();
        if (benutzername == "") {
            text2.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String passwort = passwort1.getText();
        if (benutzername == "") {
            text2.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        text2.setText(model.anmelden(benutzername, passwort));
    }
    
    @FXML
    void registrieren(ActionEvent event) {
        if(benutzername2.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String benutzername = benutzername2.getText();
        
        if(passwort2.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String passwort = passwort2.getText();
        
        if(name1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String name = name1.getText();
        
        if(vorname1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String vorname = vorname1.getText();
        
        if(plz1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String plz = plz1.getText();
        
        if(ort1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String ort = ort1.getText();
        
        if(straße1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String straße = straße1.getText();
        
        if(hausnummer1.getText() == "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String hausnummer = hausnummer1.getText();
        
        if(geburtsdatum1.getValue() == null) {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String geburtsdatum = geburtsdatum1.getValue().toString();
        
        // Wichtig!! Hier wurde für die eigentliche Adresse nur der Ort als 
        // Platzhalter verwendet. Muss nach fertiger Implementation der 
        // Adresse noch ausgetauscht werden!!!!
        text1.setText(model.registrieren(benutzername, passwort, name, vorname, geburtsdatum, ort, 0, 0));  
    }
    
    @FXML
    void kontoLöschen(ActionEvent event){
        kontoLöschen2.setText(model.kontoLoeschen());
    }
    
    @FXML
    void autoSuchen(ActionEvent event){
        QueryResult autos = model.autoSuchen(marke2.getText(), modell2.getText(), kategorie2.getText(), ps1.getValue());    
        markeListe1 = new TableColumn<String ,String>("Marke");
        modellListe1 = new TableColumn<String ,String>("Modell");
        kategorieListe1 = new TableColumn<String ,String>("Kategorie");
        psListe1 = new TableColumn<String ,Integer>("Leistung");
        preisListe1 = new TableColumn<String ,Integer>("Preis");
    }
    
    @FXML
    void switchToRegistrierung(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/registrierung.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    } 
    
    @FXML
    void switchToHauptseite(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/hauptseite.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    } 
    
    @FXML
    void switchToAnmeldung(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/anmeldung.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    
    @FXML
    void switchToHinzufügen(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/autoHinzufügen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    /**
     * Noch nicht funktionsfähig, da keine Anbindung an andere Szene
     */
    @FXML
    void autoHinzufügen(ActionEvent event)throws IOException {
        
        if(marke1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String marke = marke1.getText();
        
        if(modell1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String modell = modell1.getText();
        
        if(kennzeichen1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String kennzeichen = kennzeichen1.getText();
        
        if(leistung1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        int leistung = Integer.parseInt(leistung1.getText()); 
        
        if(kategorie1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String kategorie = kategorie1.getText();
        
        if(preisklasse1.getText() == "") {
            text3.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        int preisklasse = Integer.parseInt(preisklasse1.getText());
        
        text3.setText(model.autoHinzufügen(marke, modell, kategorie, leistung, kennzeichen, preisklasse));
    }

}