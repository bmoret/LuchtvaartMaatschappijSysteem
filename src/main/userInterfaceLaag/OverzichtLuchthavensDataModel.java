package main.userInterfaceLaag;

import javafx.beans.property.SimpleStringProperty;

public class OverzichtLuchthavensDataModel {
    private SimpleStringProperty luchthavenNm;
    private SimpleStringProperty code;
    private SimpleStringProperty werkplaats;
    private SimpleStringProperty land;

    public OverzichtLuchthavensDataModel(String luchthavenNaam, String code, String werkplaats, String landNaam) {
        this.luchthavenNm = new SimpleStringProperty(luchthavenNaam);
        this.code = new SimpleStringProperty(code);
        this.werkplaats = new SimpleStringProperty(werkplaats);
        this.land = new SimpleStringProperty(landNaam);
    }

    public String getLuchthavenNm() {
        return luchthavenNm.get();
    }

    public String getCode() {
        return code.get();
    }

    public String getWerkplaats() {
        return werkplaats.get();
    }

    public String getLand() { return land.get(); }

    public void setLuchthavenNm(String luchthavenNm) {
        this.luchthavenNm.set(luchthavenNm);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setWerkplaats(String werkplaats) {
        this.werkplaats.set(werkplaats);
    }

    public void setLand(String land) { this.land.set(land); }
}
