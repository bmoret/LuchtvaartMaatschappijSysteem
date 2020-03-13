package main.userInterfaceLaag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.domeinLaag.*;

import java.net.URL;
import java.util.*;

public class OverzichtVluchtenController implements Initializable {
    @FXML
    public TableView<OverzichtVluchtenDataModel> tableView;
    @FXML
    TableColumn<OverzichtVluchtenDataModel, String> vliegtuigCol;
    @FXML
    TableColumn<OverzichtVluchtenDataModel, String> vertrekpuntCol;
    @FXML
    TableColumn<OverzichtVluchtenDataModel, String> bestemmingCol;
    @FXML
    TableColumn<OverzichtVluchtenDataModel, String> vertrekTijdCol;
    @FXML
    TableColumn<OverzichtVluchtenDataModel, String> aankomstTijdCol;

    @FXML
    private Button buttonCancel;

    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
    private ObservableList<OverzichtVluchtenDataModel> dataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillDataList();
        vliegtuigCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vliegtuigNm"));
        vertrekpuntCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vertrekpunt"));
        bestemmingCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("bestemming"));
        vertrekTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vertrekTijd"));
        aankomstTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("aankomstTijd"));
        tableView.getItems().setAll(dataList);
    }

    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void fillDataList() {
        dataList = FXCollections.observableArrayList();
        TreeMap<Integer, Vlucht> vluchten = Vlucht.geefAlle();
        Set<Integer> vluchtenSet = vluchten.keySet();
        for (Integer vluchtNr : vluchtenSet) {
            OverzichtVluchtenDataModel dataModel;
            Vlucht vlucht = vluchten.get(vluchtNr);
            String vliegtuig = vlucht.getVliegtuig().geefNaam();
            String vertrekp = vlucht.getVertrekPunt().geefNaam();
            String bestemming = vlucht.getBestemming().geefNaam();
            Calendar vertrekTijd= vlucht.getVertrekTijd();
            Calendar aankomstTijd= vlucht.getAankomstTijd();
            dataModel = new OverzichtVluchtenDataModel(vliegtuig, vertrekp, bestemming, vertrekTijd, aankomstTijd);
            dataList.add(dataModel);
        }
    }
}
