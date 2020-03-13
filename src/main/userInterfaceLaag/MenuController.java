package main.userInterfaceLaag;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.domeinLaag.LuchtvaartMaatschappij;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private Button buttonOK;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setText("		1	Registreer vliegtuig\n");
        textArea.appendText("		2	Overzicht vliegtuigen\n");
        textArea.appendText("		3	Registreer luchthaven\n");
        textArea.appendText("		4	Overzicht luchthavens\n");
        textArea.appendText("		5	Registreer vlucht\n");
        textArea.appendText("		6	Overzicht vluchten per vliegtuig\n");
        textArea.appendText("		7	Boek vlucht\n");
        textArea.appendText("		8	Overzicht boekingen per vlucht\n");
    }

    public void ok() {
        LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
        String keuze = textField.getText();
        int keuzeNr = 0;
        String useCaseTitle = "";
        String fxmlFileNaam = "";

        try {
            keuzeNr = Integer.parseInt(keuze);
        } catch (NumberFormatException e) {
            // Do nothing
        }
        if (keuzeNr == 1) {
            fxmlFileNaam = "RegistreerVliegtuig";
            useCaseTitle = "Registreer Vliegtuig";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 2) {
            fxmlFileNaam = "OverzichtVliegtuigen";
            useCaseTitle = "Overzicht Vliegtuigen";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 3) {
            fxmlFileNaam = "RegistreerLuchthaven";
            useCaseTitle = "Registreer Luchthaven";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 4) {
            fxmlFileNaam = "OverzichtLuchthavens";
            useCaseTitle = "Overzicht Luchthavens";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 5) {
            fxmlFileNaam = "RegistreerVlucht";
            useCaseTitle = "Registreer Vlucht";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 6) {
            fxmlFileNaam = "OverzichtVluchten";
            useCaseTitle = "Overzicht Vluchten";
            startUseCase(fxmlFileNaam, useCaseTitle);
        } else if (keuzeNr == 7) {
            fxmlFileNaam = "BoekVlucht";
            useCaseTitle = "Boek Vlucht";
            startUseCase(fxmlFileNaam,useCaseTitle);
        }
        textField.setText("");
        textField.requestFocus();

    }

   private void startUseCase(String fxmlName, String title) {
        if (!fxmlName.isEmpty()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(fxmlName + ".fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 470, 310);
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }

}

