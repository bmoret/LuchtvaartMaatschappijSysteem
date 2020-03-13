package main.userInterfaceLaag;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.domeinLaag.Fabrikant;
import main.domeinLaag.LuchtvaartMaatschappij;
import main.domeinLaag.Vliegtuig;
import main.domeinLaag.VliegtuigType;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class RegistreerVliegtuigController implements Initializable {
    @FXML
    private ComboBox fabrikantComboBox;
    @FXML
    private ComboBox typeCombobox;
    @FXML
    private TextField contactTextfield;
    @FXML
    private TextField capaciteitTextField;
    @FXML
    private TextField naamTextField;
    @FXML
    private DatePicker InGebruikDatePicker;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;

    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
    private VliegtuigType vliegtuigType;
    private Vliegtuig vliegtuig;
    private Fabrikant fabrikant;
    private TreeMap<String, Fabrikant> fabrikantenMap;
    private TreeMap<String, VliegtuigType> vliegtuigTypenMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fabrikantenMap = Fabrikant.geefAlle();
        vliegtuig = new Vliegtuig(lvm);

        /** Update de UI: Toon de fabrikanten in de fabrikantComboBox
        * Toon eerst een leeg, zodat de gebruiker een waarde moet
        * selecteren, wat de event triggered. */
        String leeg = "";
        fabrikantComboBox.getItems().add(leeg);
        // Creëer een gesorteerde Set namen en voeg ze toe aan fabrikantComboBox.
        Set<String> fabrikantenSet = fabrikantenMap.keySet();
        for (Iterator<String> i = fabrikantenSet.iterator(); i.hasNext();) {
            fabrikantComboBox.getItems().add(i.next());
        }
        // Voeg event listener toe aan naamTextField. Roep naam() als focus-lost event optreedt.
        naamTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> focusProperty, Boolean oldValue, Boolean newValue) {
                if(!newValue) // if (focus lost)
                    naam();
            }
        });
        /*
        Een nieuwe, kortere manier om bovenstaande te doen is met een lambda:
        naamTextField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if(!newValue) naam();});
        */

    }

    public void fabrikant() {
        String fabrikantNaam = (String) fabrikantComboBox.getValue();
        if (fabrikantNaam != null) {
            //Zoek het fabrikant-object op basis van de naam en onthoud het geselecteerde fabrikant
            this.fabrikant = fabrikantenMap.get(fabrikantNaam);
            if (fabrikant != null) {
                vliegtuigTypenMap = fabrikant.geefVliegtuigTypen();
                // Update de UI
                // Toon de vliegtuigtypes van deze fabrikant in de combo box; begin met een leeg veld
                String leeg = "";
                typeCombobox.getItems().clear();
                typeCombobox.getItems().add(leeg);
                Set<String> vliegtuigTypenSet = vliegtuigTypenMap.keySet();
                for (String vliegtuigtypeNaam : vliegtuigTypenSet) {
                    typeCombobox.getItems().add(vliegtuigtypeNaam);
                }
                // Toon de contactpersoon van de fabrikant
                String cp = this.fabrikant.geefContactpersoon();
                contactTextfield.setText(cp);
            } else {
                contactTextfield.setText("");
                typeCombobox.getItems().clear();
                capaciteitTextField.setText("");
                vliegtuig.zetVliegtuigType(null);
            }
        }
    }

    public void type() {
        String vttCode = (String) typeCombobox.getValue();
        //Zoek het vliegtuigtype-object, op basis van de code.
        if (vttCode != null) {
            this.vliegtuigType = vliegtuigTypenMap.get(vttCode);
            if (vliegtuigType != null) {
                vliegtuig.zetVliegtuigType(vliegtuigType);
                // Update de UI: Toon de capaciteit
                int cap = this.vliegtuigType.geefCapaciteit();
                capaciteitTextField.setText(String.valueOf(cap));
            } else {
                vliegtuig.zetVliegtuigType(null);
                capaciteitTextField.setText("");
            }
        }
    }

    public void naam() {
        naamTextField.setStyle("-fx-text-fill: black;");
        String naam = naamTextField.getText();
        if (naam != null && !naam.isEmpty()) {
            try {
                vliegtuig.zetNaam(naam);
            } catch (IllegalArgumentException ei) {
                toonMelding(ei.getMessage());
                naamTextField.setStyle("-fx-text-fill: red;");
            }
        } else {
            vliegtuig.zetNaam(null);
            naamTextField.setText("");
        }
    }

    public void inGebruik() {
        LocalDate inGebruik = InGebruikDatePicker.getValue();
        if (inGebruik != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(inGebruik.getYear(), inGebruik.getMonthValue() - 1, inGebruik.getDayOfMonth());
            try {
                vliegtuig.zetInGebruik(calendar);
            } catch (IllegalArgumentException ei) {
                toonMelding(ei.getMessage());
            }
        }
    }

    public void ok() {
        try {
            vliegtuig.bewaar();
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
