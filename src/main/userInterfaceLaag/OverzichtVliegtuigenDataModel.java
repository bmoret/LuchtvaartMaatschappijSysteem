package main.userInterfaceLaag;

import javafx.beans.property.SimpleStringProperty;

public class OverzichtVliegtuigenDataModel {
    private SimpleStringProperty vliegtuigNm;
    private SimpleStringProperty fabrikantNm;
    private SimpleStringProperty typeNm;
    private SimpleStringProperty cap;

    public OverzichtVliegtuigenDataModel(String vliegtuigNaam, String fabrikantNaam, String typeNaam, int capaciteit) {
        this.vliegtuigNm = new SimpleStringProperty(vliegtuigNaam);
        this.fabrikantNm = new SimpleStringProperty(fabrikantNaam);
        this.typeNm = new SimpleStringProperty(typeNaam);
        this.cap = new SimpleStringProperty(Integer.toString(capaciteit));
    }

    public String getVliegtuigNm() {
        return vliegtuigNm.get();
    }

    public String getFabrikantNm() {
        return fabrikantNm.get();
    }

    public String getTypeNm() {
        return typeNm.get();
    }

    public String getCap() { return cap.get(); }

    public void setVliegtuigNm(String vliegtuigNm) {
        this.vliegtuigNm.set(vliegtuigNm);
    }

    public void setFabrikantNm(String fabrikantNm) {
        this.fabrikantNm.set(fabrikantNm);
    }

    public void setTypeNm(String typeNm) {
        this.typeNm.set(typeNm);
    }

    public void setCap(int cap) {
        this.cap.set(Integer.toString(cap));
    }
}
