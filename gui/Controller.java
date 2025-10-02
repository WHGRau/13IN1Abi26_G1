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
    private TableView<Auto> autoListe1;
    
    @FXML
    private TableColumn<Auto, String> markeListe1; 
    
    @FXML
    private TableColumn<Auto, String> modellListe1;
    
    @FXML
    private TableColumn<Auto, String> kategorieListe1; 
    
    @FXML
    private TableColumn<Auto, Integer> psListe1; 
    
    @FXML
    private TableColumn<Auto, Integer> preisListe1; 
    
    @FXML
    private Button suchen1;
    
    @FXML
    private Button autoAuswählen1;
    
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
            text2.setText("Benutzername muss angegeben sein!");
            return;
        }
        String passwort = passwort1.getText();
        if (passwort == "") {
            text2.setText("Passwort muss angegeben sein!");
            return;
        }
        text2.setText(model.anmelden(benutzername, passwort));
    }
    
    @FXML
    void registrieren(ActionEvent event) {
        if(benutzername2.getText() == "") {
            text1.setText("Benutzername muss angegeben sein!");
            return;
        }
        String benutzername = benutzername2.getText();
        
        if(passwort2.getText() == "") {
            text1.setText("Passwort muss angegeben sein!");
            return;
        }
        String passwort = passwort2.getText();
        
        if(name1.getText() == "") {
            text1.setText("Name muss angegeben sein");
            return;
        }
        String name = name1.getText();
        
        if(vorname1.getText() == "") {
            text1.setText("Vorname muss angegeben sein");
            return;
        }
        String vorname = vorname1.getText();
        
        if(plz1.getText() == "") {
            text1.setText("Postleitzahl muss angegeben sein!");
            return;
        }
        String plz = plz1.getText();
        int plzParsed = Helper.tryParseInt(plz);
        if(plzParsed <= 0) {
            text1.setText("Ungültige Postleitzahl angegeben!");
            return;
        }
        
        if(ort1.getText() == "") {
            text1.setText("Ort muss angegeben sein!");
            return;
        }
        String ort = ort1.getText();
        
        if(straße1.getText() == "") {
            text1.setText("Straße muss angegeben sein!");
            return;
        }
        String straße = straße1.getText();
        
        if(hausnummer1.getText() == "") {
            text1.setText("Hausnummer muss angegeben sein!");
            return;
        }
        String hausnummer = hausnummer1.getText();
        int hausNrParsed = Helper.tryParseInt(hausnummer);
        if(hausNrParsed <= 0) {
            text1.setText("Ungültige Hausnummer angegeben!");
            return;
        }
        
        if(geburtsdatum1.getValue() == null) {
            text1.setText("Geburtsdatum muss natürlich ausgefüllt sein!");
            return;
        }
        String geburtsdatum = geburtsdatum1.getValue().toString();

        // Ab hier ist die Anmeldung erfolgreich ausgelesen und validiert!
        text1.setText(model.registrieren(benutzername, passwort, name, vorname, geburtsdatum, new Standort(ort, plzParsed, straße, hausNrParsed), 0, 0));  
    }
    
    @FXML
    void kontoLöschen(ActionEvent event){
        kontoLöschen2.setText(model.kontoLoeschen());
    }
    
    @FXML
    void autoSuchen(ActionEvent event){
        markeListe1.setCellValueFactory(new PropertyValueFactory<>("marke"));
        modellListe1.setCellValueFactory(new PropertyValueFactory<>("modell"));
        kategorieListe1.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
        psListe1.setCellValueFactory(new PropertyValueFactory<>("leistung"));
        preisListe1.setCellValueFactory(new PropertyValueFactory<>("preis"));
        
        int leistung = (int)ps1.getValue();
        ObservableList<Auto> daten = FXCollections.observableArrayList(model.autoSuchen(marke2.getText(), modell2.getText(), kategorie2.getText(), leistung));

        autoListe1.setItems(daten);
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

    @FXML
    void autoÜbertragen(ActionEvent event)throws IOException {
        ausleihen1.setVisible(true);
        Auto ausgewähltesAuto = autoListe1.getSelectionModel().getSelectedItem();
        if (ausgewähltesAuto != null) {
            String marke = ausgewähltesAuto.getMarke();
            String modell = ausgewähltesAuto.getModell();
            String kategorie = ausgewähltesAuto.getKategorie();
            int leistung = ausgewähltesAuto.getLeistung();
            Preisklasse pk = ausgewähltesAuto.getPreis();
            int preis = pk.getPreis();
            
            markeAnzeige.setText(marke);
            modellAnzeige.setText(modell);
            kategorieAnzeige.setText(kategorie);
            LeistungAnzeige.setText(""+leistung+" PS");
            preisAnzeige1.setText(""+preis+" € / Tag");
        }
    }
}