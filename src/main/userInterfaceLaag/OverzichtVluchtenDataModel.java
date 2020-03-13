package main.userInterfaceLaag;

import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OverzichtVluchtenDataModel {
    private SimpleStringProperty vliegtuigNm;
    private SimpleStringProperty vertrekpunt;
    private SimpleStringProperty bestemming;
    private SimpleStringProperty vertrekTijd;
    private SimpleStringProperty aankomstTijd;

    private Locale currentLocale = new Locale("nl","NL");
    private DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, currentLocale);

    public OverzichtVluchtenDataModel(String vliegtuigNaam, String vertrekpunt, String bestemming, Calendar vertrekTijd, Calendar aankomstTijd) {
        this.vliegtuigNm = new SimpleStringProperty(vliegtuigNaam);
        this.vertrekpunt = new SimpleStringProperty(vertrekpunt);
        this.bestemming = new SimpleStringProperty(bestemming);
        this.vertrekTijd = new SimpleStringProperty(formatter.format(vertrekTijd.getTime()));
        this.aankomstTijd = new SimpleStringProperty(formatter.format(aankomstTijd.getTime()));
    }

    public String getVliegtuigNm() {
        return vliegtuigNm.get();
    }

    public String getVertrekpunt() {
        return vertrekpunt.get();
    }

    public String getBestemming() {
        return bestemming.get();
    }

    public String getVertrekTijd() { return vertrekTijd.get(); }

    public String getAankomstTijd() { return aankomstTijd.get(); }

    public void setVliegtuigNm(String vliegtuigNm) {
        this.vliegtuigNm.set(vliegtuigNm);
    }

    public void setVertrekpunt(String vertrekpunt) {
        this.vertrekpunt.set(vertrekpunt);
    }

    public void setBestemming(String bestemming) {
        this.bestemming.set(bestemming);
    }

    public void setVertrekTijd(Calendar vertrekTijd) {
        this.vertrekTijd.set(formatter.format(vertrekTijd));
    }

    public void setAankomstTijd(Calendar vertrekTijd) { this.vertrekTijd.set(formatter.format(aankomstTijd)); }
}
