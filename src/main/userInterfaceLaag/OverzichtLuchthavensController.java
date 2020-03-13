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
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class OverzichtLuchthavensController implements Initializable {
    @FXML
    public TableView<OverzichtLuchthavensDataModel> tableView;
    @FXML
    TableColumn<OverzichtLuchthavensDataModel, String> luchthavenCol;
    @FXML
    TableColumn<OverzichtLuchthavensDataModel, String> codeCol;
    @FXML
    TableColumn<OverzichtLuchthavensDataModel, String> werkplaatsCol;
    @FXML
    TableColumn<OverzichtLuchthavensDataModel, String> landCol;

    @FXML
    private Button buttonCancel;

    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
    private ObservableList<OverzichtLuchthavensDataModel> dataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillDataList();
        luchthavenCol.setCellValueFactory(new PropertyValueFactory<OverzichtLuchthavensDataModel, String>("luchthavenNm"));
        codeCol.setCellValueFactory(new PropertyValueFactory<OverzichtLuchthavensDataModel, String>("code"));
        werkplaatsCol.setCellValueFactory(new PropertyValueFactory<OverzichtLuchthavensDataModel, String>("werkplaats"));
        landCol.setCellValueFactory(new PropertyValueFactory<OverzichtLuchthavensDataModel, String>("land"));
        tableView.getItems().setAll(dataList);
    }

    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void fillDataList() {
        dataList = FXCollections.observableArrayList();
        TreeMap<String, Luchthaven> luchthavens = Luchthaven.geefAlle();
        Set<String> lSet = luchthavens.keySet();
        for (String lNaam : lSet) {
            OverzichtLuchthavensDataModel dataModel;
            String werkplaats = "";
            Luchthaven luchthaven = (Luchthaven) luchthavens.get(lNaam);
            String luchthavenNaam = luchthaven.geefNaam();
            String code = luchthaven.geefCode();
            if (luchthaven.geefWerkplaats()) {
                werkplaats = "Ja";
            } else if (!luchthaven.geefWerkplaats()) {
                werkplaats = "Nee";
            }
            String landNaam = luchthaven.geefLandNaam();
            dataModel = new OverzichtLuchthavensDataModel(luchthavenNaam, code, werkplaats, landNaam);
            dataList.add(dataModel);
        }
    }
}
