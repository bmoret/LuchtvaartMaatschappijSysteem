package test;

import main.domeinLaag.Fabrikant;
import main.domeinLaag.LuchtvaartMaatschappij;
import main.domeinLaag.Vliegtuig;
import main.domeinLaag.VliegtuigType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Calendar;

public class VliegtuigTest {

	LuchtvaartMaatschappij lvm ;
	Fabrikant f1;
	VliegtuigType vtt1;
	Vliegtuig vt1;

	@BeforeEach
	public void initialize() {
		try {
			lvm = new LuchtvaartMaatschappij("NLM");
			f1 = new Fabrikant("Airbus","G. Dejenelle");
			vtt1 = f1.creeervliegtuigtype("A-200", 140);
			Calendar datum = Calendar.getInstance();
			datum.set(2020, 01, 01);
			vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
		} catch (Exception e){
			String errorMessage =  "Exception: " + e.getMessage();
			System.out.println(errorMessage); 
		}
	}

	/**
	 * Business Rule
	 * De naam van een vliegtuigmoet uniek zijn binnen de luchtvaartmaatschappij.
	 */
	@Test
	public void testNaamMoetUniekZijn_False() {
		String argumentNaam = vt1.geefNaam();
		Vliegtuig vliegtuig = null;
		try {
			vliegtuig = new Vliegtuig(lvm);
			vliegtuig.zetNaam(argumentNaam);
			String vliegtuigNaam = vliegtuig.geefNaam();
			assertFalse(vliegtuigNaam.equals(argumentNaam));
		}
		catch(IllegalArgumentException e) {
			if (vliegtuig != null) {
				String vliegtuigNaam = vliegtuig.geefNaam();
				assertFalse(vliegtuigNaam.equals(argumentNaam));
			}
		}
	}

	@Test
	public void testNaamMoetUniekZijn_True() {
		String argumentNaam = "Luchtbus 101";
		Vliegtuig vliegtuig = null;
		try {
			vliegtuig = new Vliegtuig(lvm);
			vliegtuig.zetNaam(argumentNaam);
			String vliegtuigNaam = vliegtuig.geefNaam();
			assertTrue(vliegtuigNaam.equals(argumentNaam));
		}
		catch(IllegalArgumentException e) {
			if (vliegtuig != null) {
				String vliegtuigNaam = vliegtuig.geefNaam();
				assertTrue(vliegtuigNaam.equals(argumentNaam));
			}
		}
	}

	/**
	 * Business Rule
	 * De datumInGebruik van een vliegtuig moet geldig zijn EN bij registratie in de toekomst liggen.
	 */
	
	@Test
	public void testDatumMoetGeldigZIjn_False_InDeToekomstLiggen_False() {
		Calendar datumFout = Calendar.getInstance();
		datumFout.set(2019, 12, 32);
		Vliegtuig vliegtuig = new Vliegtuig(lvm);
		Calendar datumInGebruik = null;
		try {
			vliegtuig.zetInGebruik(datumFout); // Moet exception opleveren
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertFalse(datumInGebruik.equals(datumFout));
		}
		catch(IllegalArgumentException e){
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertTrue(datumInGebruik == null);
		}
	}

	@Test
	public void testDatumMoetGeldigZijn_False_InDeToekomstLiggen_True() {
		Calendar datumFout = Calendar.getInstance();
		datumFout.set(2030, 12, 32);
		Vliegtuig vliegtuig = new Vliegtuig(lvm);
		Calendar datumInGebruik = null;
		try {
			vliegtuig.zetInGebruik(datumFout); // Moet exception opleveren
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertFalse(datumInGebruik.equals(datumFout));
		}
		catch(IllegalArgumentException e) {
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertTrue(datumInGebruik == null);
		}
	}

	@Test
	public void testDatumMoetGeldigZijn_True_InDeToekomstLiggen_False() {
		Calendar datumOud = Calendar.getInstance();
		Vliegtuig vliegtuig = new Vliegtuig(lvm);
		Calendar datumInGebruik;
		try {
			vliegtuig.zetInGebruik(datumOud);
			datumInGebruik = vliegtuig.geefDatumInGebruik(); // Moet exception opleveren
			assertFalse(datumInGebruik.equals(datumOud));
		}
		catch(IllegalArgumentException e){
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertTrue(datumInGebruik == null);
		}
	}

	@Test
	public void testDatumMoetGeldigZijn_True_InDeToekomstLiggen_True() {
		Calendar datumToekomst = Calendar.getInstance();
		datumToekomst.add(Calendar.DATE, +1);
		Vliegtuig vliegtuig = new Vliegtuig(lvm);
		Calendar datumInGebruik;
		try {
			vliegtuig.zetInGebruik(datumToekomst);
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertTrue(datumInGebruik.equals(datumToekomst));
		}
		catch(IllegalArgumentException e){
			datumInGebruik = vliegtuig.geefDatumInGebruik();
			assertFalse(datumInGebruik == null);
		}
	}

}
