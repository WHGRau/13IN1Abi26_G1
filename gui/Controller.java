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

public class Controller {
    // Für Anmeldung:
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
        text1.setText("hasllo");
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
        if(ort1.getText() + straße1.getText()+hausnummer1.getText()== "") {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String adresse = ort1.getText() + " "+ straße1.getText()+" " +hausnummer1.getText();
        if(geburtsdatum1.getValue() == null) {
            text1.setText("Alle Felder müssen ausgefüllt sein!");
            return;
        }
        String geburtsdatum = geburtsdatum1.getValue().toString();
        text1.setText(model.registrieren(benutzername, passwort, name, vorname, geburtsdatum, adresse, 0, 0));  
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
    void switchToRegistrierung(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/registrierung.fxml"));
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
}