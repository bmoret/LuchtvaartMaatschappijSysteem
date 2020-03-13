package main.userInterfaceLaag;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.domeinLaag.*;

import java.net.URL;
import java.util.*;

public class RegistreerVluchtController implements Initializable {
    @FXML
    private ComboBox vliegtuigComboBox;
    @FXML
    private TextField capaciteitTextField;
    @FXML
    private ComboBox vertrekPuntComboBox;
    @FXML
    private ComboBox bestemmingComboBox;
    @FXML
    private TextField vertrekDagTextField;
    @FXML
    private TextField vertrekTijdTextField;
    @FXML
    private TextField aankomstDagTextField;
    @FXML
    private TextField aankomstTijdTextField;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;

    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
    private Vliegtuig vliegtuig;
    private Vlucht nieuweVlucht;
    private TreeMap<String, Vliegtuig> vliegtuigenMap;
    private TreeMap<String, Luchthaven> luchthavensMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vliegtuigenMap = lvm.geefVliegtuigen();
        luchthavensMap = Luchthaven.geefAlle();
        nieuweVlucht = new Vlucht();
        // Update de UI: Toon de vliegtuigen en luchthavens in de ComboBoxen.
        Set<String> vliegtuigenSet = vliegtuigenMap.keySet();
        vulComboBox(vliegtuigenSet, vliegtuigComboBox);
        Set<String> luchthavenSet = luchthavensMap.keySet();
        vulComboBox(luchthavenSet, vertrekPuntComboBox);
        vulComboBox(luchthavenSet, bestemmingComboBox);
        // Voeg event listener toe aan tijd-TextFields.
        vertrekTijdTextField.focusedProperty().addListener((obs, oldText, newText) -> {
            if(!newText) vertrekTijd();}); // if (focus lost)
        aankomstTijdTextField.focusedProperty().addListener((obs, oldText, newText) -> {
            if(!newText) aankomstTijd();});
    }

    public void vliegtuig() {
        String vliegtuigNaam = (String) vliegtuigComboBox.getValue();
        if (vliegtuigNaam != null) {
            //Zoek het vliegtuig-object op basis van de naam en onthoud het geselecteerde vliegtuig
            if (vliegtuigenMap.get(vliegtuigNaam) != null) {
                vliegtuig = vliegtuigenMap.get(vliegtuigNaam);
                nieuweVlucht.zetVliegtuig(vliegtuig);
                // Update de UI: Toon de capaciteit
                int cap = vliegtuig.geefVliegtuigType().geefCapaciteit();
                capaciteitTextField.setText(String.valueOf(cap));
            } else {
                nieuweVlucht.zetVliegtuig(null);
                capaciteitTextField.setText("");
            }
        }
    }

    public void vertrekpunt() {
        String naam = (String) vertrekPuntComboBox.getValue();
        Luchthaven vertrekpunt = (Luchthaven) luchthavensMap.get(naam);
        nieuweVlucht.zetVertrekpunt(vertrekpunt);
    }

    public void bestemming() {
        String naam = (String) bestemmingComboBox.getValue();
        Luchthaven bestemming = (Luchthaven)luchthavensMap.get(naam);
        nieuweVlucht.zetBestemming(bestemming);
    }

    public void vertrekTijd() {
        String dagMaandJaar = vertrekDagTextField.getText();
        String tijd = vertrekTijdTextField.getText();
        try {
            StringTokenizer st = new StringTokenizer(dagMaandJaar, "-. ");
            int dag = Integer.parseInt(st.nextToken());
            int maand = Integer.parseInt(st.nextToken()) - 1;
            int jaar = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(tijd, ":h. ");
            int uur = Integer.parseInt(st.nextToken());
            int min = Integer.parseInt(st.nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.set(jaar, maand, dag, uur, min);
            nieuweVlucht.zetVertrekTijd(calendar);
        } catch (VluchtException ei) { // Thrown by zetAankomstTijd()
            toonMelding(ei.getMessage());
        } catch (NoSuchElementException ei) { // Thrown by st.nextToken()
            toonMelding("Datum en/of Tijd niet compleet");
            try {
                nieuweVlucht.zetVertrekTijd(null);
            } catch (VluchtException ei2) { // Thrown by zetAankomstTijd()
                toonMelding(ei2.getMessage());
            }
        }
    }

    public void aankomstTijd() {
        String dagMaandJaar = aankomstDagTextField.getText();
        String tijd = aankomstTijdTextField.getText();
        try {
            StringTokenizer st = new StringTokenizer(dagMaandJaar, "-. ");
            int dag = Integer.parseInt(st.nextToken());
            int maand = Integer.parseInt(st.nextToken()) - 1;
            int jaar = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(tijd, ":h. ");
            int uur = Integer.parseInt(st.nextToken());
            int min = Integer.parseInt(st.nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.set(jaar, maand, dag, uur, min);
            nieuweVlucht.zetAankomstTijd(calendar);
        } catch (VluchtException ei) { // Thrown by zetAankomstTijd()
            toonMelding(ei.getMessage());
        } catch (NoSuchElementException ei) { // Thrown by st.nextToken()
            toonMelding("Datum en/of Tijd niet compleet");
        }
    }

    public void ok() {
        try {
            nieuweVlucht.bewaar();
            Stage stage = (Stage) buttonOK.getScene().getWindow();
            stage.close();
        } catch (VluchtException ei) {
            toonMelding(ei.getMessage());
        }
    }

    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void vulComboBox(Set<String> set, ComboBox comboBox) {
        // Toon eerst een leeg veld, zodat de gebruiker een waarde moet selecteren, wat de event triggered.
        String leeg = "";
        comboBox.getItems().add(leeg);
        // Creëer een gesorteerde Set namen en voeg ze toe aan vliegtuigComboBox.
        for (Iterator<String> i = set.iterator(); i.hasNext();) {
            comboBox.getItems().add(i.next());
        }
    }

    private void toonMelding(String tekstMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waarschuwing!");
        alert.setContentText(tekstMessage);
        alert.showAndWait();
    }
}
