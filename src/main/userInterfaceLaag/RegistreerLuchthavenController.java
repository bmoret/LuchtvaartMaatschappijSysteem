package main.userInterfaceLaag;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.domeinLaag.Land;
import main.domeinLaag.Luchthaven;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class RegistreerLuchthavenController implements Initializable {
    @FXML
    ComboBox landComboBox;
    @FXML
    TextField landCodeTextField;
    @FXML
    TextField naamTextField;
    @FXML
    TextField codeTextField;
    @FXML
    RadioButton jaRadioButton;
    @FXML
    RadioButton neeRadioButton;
    @FXML
    Button buttonOK;
    @FXML
    Button buttonCancel;
    @FXML
    ToggleGroup werkplaats;

    private Land land;
    private Luchthaven nieuweLuchthaven;
    private TreeMap<String, Land> alleLanden;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
/**     Activeer na het maken van het scherm
        alleLanden = Land.geefAlleLanden();
        nieuweLuchthaven = new Luchthaven();

        // Update de UI: Toon de landen in de LandenComboBox
        // Toon eerst een leeg veld, zodat de gebruiker een waarde moet selecteren, wat de event triggered.
        String leeg = "";
        landComboBox.getItems().add(leeg);
        // Creëer een gesorteerde Set namen en voeg ze toe aan de combo box.
        Set<String> landNamen = alleLanden.keySet();
        for (Iterator<String> i = landNamen.iterator(); i.hasNext();) {
            landComboBox.getItems().add(i.next());
        }
        // Voeg event listener toe aan naam- en codeTextField.
        naamTextField.focusedProperty().addListener((obs, oldText, newText) -> {
            if(!newText) naam();}); // if (focus lost)
        codeTextField.focusedProperty().addListener((obs, oldText, newText) -> {
            if(!newText) code();});
 */
        alleLanden = Land.geefAlleLanden();
        nieuweLuchthaven = new Luchthaven();
        String leeg = "";
        landComboBox.getItems().add(leeg);
        Set<String> landNamen = alleLanden.keySet();
        for (Iterator<String> i = landNamen.iterator(); i.hasNext();) {
            landComboBox.getItems().add(i.next());
        }
    }

    public void landNaam() {
        String landNaam = (String) landComboBox.getValue();
        if (landNaam != null) {
            //Zoek het land-object op basis van de naam en onthoud het geselecteerde land
            this.land = alleLanden.get(landNaam);
            if (land != null) {
                nieuweLuchthaven.zetLand(land);
                String landCode = Integer.toString(land.geefCode());
                // Update de UI: Toon de code van het land
                landCodeTextField.setText(landCode);
            } else {
                nieuweLuchthaven.zetLand(null);
                landCodeTextField.setText("");
            }
        }

    }

    public void naam() {
        String naam = naamTextField.getText();
        if (naam != null && !naam.isEmpty()) {
            try {
                nieuweLuchthaven.zetNaam(naam);
            } catch (IllegalArgumentException ei) {
                toonMelding(ei.getMessage());
            }
        } else {
            nieuweLuchthaven.zetNaam(null);
            naamTextField.setText("");
        }
    }

    public void code() {
        String code = codeTextField.getText();
        if (code != null && !code.isEmpty()) {
            try {
                nieuweLuchthaven.zetCode(code);
            } catch (IllegalArgumentException ei) {
                toonMelding(ei.getMessage());
            }
        } else {
            nieuweLuchthaven.zetCode(null);
            codeTextField.setText("");
        }
    }

    public void werkplaats() {
        boolean werkPlaatsAanwezig = false;
        RadioButton selectedButton = (RadioButton) werkplaats.getSelectedToggle();
        String choice = selectedButton.getText();
        if (choice.equals("Ja")) {
            werkPlaatsAanwezig = true;
        }
        nieuweLuchthaven.zetWerkPlaats(werkPlaatsAanwezig);
    }

    public void ok() {
        try {
            nieuweLuchthaven.bewaar();
            Stage stage = (Stage) buttonOK.getScene().getWindow();
            stage.close();
        } catch (IllegalStateException ei) {
            toonMelding(ei.getMessage());
        }
    }

    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void toonMelding(String tekstMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waarschuwing!");
        alert.setContentText(tekstMessage);
        alert.showAndWait();
    }
}
