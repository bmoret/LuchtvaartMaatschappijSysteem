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
import main.domeinLaag.Fabrikant;
import main.domeinLaag.LuchtvaartMaatschappij;
import main.domeinLaag.Vliegtuig;
import main.domeinLaag.VliegtuigType;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class OverzichtVliegtuigenController implements Initializable {
    @FXML
    public TableView<OverzichtVliegtuigenDataModel> tableView;
    @FXML
    TableColumn<OverzichtVliegtuigenDataModel, String> vliegtuigCol;
    @FXML
    TableColumn<OverzichtVliegtuigenDataModel, String> fabrikantCol;
    @FXML
    TableColumn<OverzichtVliegtuigenDataModel, String> typeCol;
    @FXML
    TableColumn<OverzichtVliegtuigenDataModel, String> capaciteitCol;

    @FXML
    private Button buttonCancel;

    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
    private ObservableList<OverzichtVliegtuigenDataModel> dataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillDataList();
        vliegtuigCol.setCellValueFactory(new PropertyValueFactory<OverzichtVliegtuigenDataModel, String>("vliegtuigNm"));
        fabrikantCol.setCellValueFactory(new PropertyValueFactory<OverzichtVliegtuigenDataModel, String>("fabrikantNm"));
        typeCol.setCellValueFactory(new PropertyValueFactory<OverzichtVliegtuigenDataModel, String>("typeNm"));
        capaciteitCol.setCellValueFactory(new PropertyValueFactory<OverzichtVliegtuigenDataModel, String>("cap"));
        tableView.getItems().setAll(dataList);
    }

    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    private void fillDataList() {
        dataList = FXCollections.observableArrayList();
        TreeMap<String, Vliegtuig> vliegtuigen = lvm.geefVliegtuigen();
        Set<String> vSet = vliegtuigen.keySet();
        for (String vNaam : vSet) {
            OverzichtVliegtuigenDataModel dataModel;
            Vliegtuig vliegtuig = (Vliegtuig) vliegtuigen.get(vNaam);
            VliegtuigType vtt = vliegtuig.geefVliegtuigType();
            String vttCode = vtt.geefCode();
            Fabrikant fb = vtt.geefFabrikant();
            String fbNaam = fb.geefNaam();
            int cp = vliegtuig.geefCapaciteit();
            dataModel = new OverzichtVliegtuigenDataModel(vNaam, fbNaam, vttCode, cp);
            dataList.add(dataModel);
        }
    }
}
