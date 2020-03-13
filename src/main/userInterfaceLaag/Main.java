package main.userInterfaceLaag;
import java.awt.*;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.domeinLaag.*;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		initializeDomainObjects();
		Parent root = FXMLLoader.load(getClass().getResource("/main/userInterfaceLaag/Menu.fxml"));
		primaryStage.setTitle("Hoofdmenu Luchtvaartmaatschappij NLM");
		Scene scene = new Scene(root, 470, 310);
		primaryStage.setScene(scene);
		primaryStage.show();
		// Request focus on the selection field "textfield"
		TextField textfield = (TextField) scene.lookup("#textField");
		if(textfield != null) {
			textfield.requestFocus();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void initializeDomainObjects() {
    LuchtvaartMaatschappij lvm = new LuchtvaartMaatschappij("NLM");
    LuchtvaartMaatschappij.setCurrentLuchtvaartMaatschappij(lvm);
	Fabrikant f1 = new Fabrikant("Airbus","G. Dejenelle");
    Fabrikant f2 = new Fabrikant("Fokker","A.J. Berger");

	VliegtuigType vtt1 = f1.creeervliegtuigtype("A-200", 140);
	VliegtuigType vtt2 = f1.creeervliegtuigtype("A-201", 165);
	VliegtuigType vtt3 = f2.creeervliegtuigtype("F-100", 107);
	VliegtuigType vtt4 = f2.creeervliegtuigtype("F-101", 145);

	Calendar datum = Calendar.getInstance();
	datum.set(2020, 01, 01);
	Vliegtuig vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
	datum.set(1956, 06, 01);
	Vliegtuig vt2 = new Vliegtuig(lvm, vtt3, "Karel Doorman", datum);
	datum.set(2019, 11, 01);
	Vliegtuig vt3 = new Vliegtuig(lvm, vtt4, "Fokke", datum);
	
	Land l1 = new Land("Nederland", 31);
	Land l2 = new Land("Belgie", 32);
	Land l3 = new Land("Frankrijk", 11);
	Land l4 = new Land("Duitsland", 13);
	Land l5 = new Land("Spanje", 40);
	
	Luchthaven lh1 = new Luchthaven("Schiphol", "NETH01", true, l1);
	Luchthaven lh2 = new Luchthaven("Charles de Gaule", "FRAN01", true, l3);
	Luchthaven lh3 = new Luchthaven("Bonn", "GERM01", true, l4);
	Luchthaven lh4 = new Luchthaven("Eindhoven", "NETH02", true, l1);
	Luchthaven lh5 = new Luchthaven("Brussel", "BELG01", false, l2);
	Luchthaven lh6 = new Luchthaven("Madrid", "SPAN01", true, l5);
	Luchthaven lh7 = new Luchthaven("Keulen", "GERM02", true, l4);
	
	Calendar vertrek = Calendar.getInstance();
	vertrek.set(2025, 6, 1, 12, 43);
	Calendar aankomst = Calendar.getInstance();
	aankomst.set(2025, 6, 1, 15, 36);
	Calendar vertrek2 = Calendar.getInstance();
	vertrek2.set(2025, 9, 3, 13, 17);
	Calendar aankomst2 = Calendar.getInstance();
	aankomst2.set(2025, 9, 3, 16, 29);
	Calendar vertrek3 = Calendar.getInstance();
	vertrek3.set(2025, 6, 9, 13, 10);
	Calendar aankomst3 = Calendar.getInstance();
	aankomst3.set(2025, 6, 9, 22, 15);
	Vlucht v1 = new Vlucht(vt3, lh1, lh2, vertrek, aankomst);
	Vlucht v2 = new Vlucht(vt2, lh2, lh3, vertrek2, aankomst2);
	Vlucht v3 = new Vlucht(vt1, lh1, lh2, vertrek3, aankomst3);
   }
}
