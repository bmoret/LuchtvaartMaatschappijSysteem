package test;

import main.domeinLaag.*;

import main.userInterfaceLaag.OverzichtVliegtuigenDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import javax.swing.*;
import javax.swing.plaf.SeparatorUI;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class VluchtTest {

	static LuchtvaartMaatschappij lvm, lh;
	static Fabrikant f1;
	static VliegtuigType vtt1;
	static Vliegtuig vt1, vgt;
	static Luchthaven lh1, lh2;
	static Vlucht vl1, vl2;
	static Calendar testDatumVertrekDag, testUrenAankomst, testDatumDatumplus1, dagTijd, vtr, aan, datum;


	@BeforeEach
	public void initialize() {
		try {
			lvm = new LuchtvaartMaatschappij("NLM");
			f1 = new Fabrikant("Airbus", "G. Dejenelle");
			vtt1 = f1.creeervliegtuigtype("A-200", 140);
			Calendar datum = Calendar.getInstance();
			datum.set(2000, 01, 01);
			vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
			Land l1 = new Land("Nederland", 31);
			Land l2 = new Land("België", 32);
			lh1 = new Luchthaven("Schiphol", "ASD", true, l1);
			lh2 = new Luchthaven("Tegel", "TEG", true, l2);
			Calendar vertr = Calendar.getInstance();
			vertr.set(2020, Calendar.MARCH, 30, 14, 15, 0);
			Calendar aank = Calendar.getInstance();
			aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
			vl1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
			vertr.set(2020, Calendar.APRIL, 1, 8, 15, 0);
			aank.set(2020, Calendar.APRIL, 1, 9, 15, 0);
			vl2 = new Vlucht(vt1, lh1, lh2, vertr, aank);

			testDatumVertrekDag = Calendar.getInstance();
			testDatumVertrekDag.setLenient(false);
			testDatumVertrekDag.set(2025, Calendar.SEPTEMBER, 31, 24, 0);

			testUrenAankomst = Calendar.getInstance();
			testUrenAankomst.setLenient(false);
			testUrenAankomst.set(2025, Calendar.SEPTEMBER, 30, 24,1);

			testDatumDatumplus1 = Calendar.getInstance();
			testDatumDatumplus1.setLenient(false);
			testDatumDatumplus1.set(2025, Calendar.SEPTEMBER, 30, 12,0);

			dagTijd = Calendar.getInstance();
			dagTijd.set(2025, Calendar.SEPTEMBER,1,24,0);

			vtr = Calendar.getInstance();
			aan = Calendar.getInstance();
			vtr.set(2020, Calendar.APRIL, 1, 8, 0 );
			aan.set(2020, Calendar.APRIL, 1, 9, 30);
			lh = new LuchtvaartMaatschappij("KLM");
			vgt = new Vliegtuig(lh);





		} catch (Exception e) {
			String errorMessage = "Exception: " + e.getMessage();
			System.out.println(errorMessage);
		}
	}

	/**
	 * Business rule:
	 * De bestemming moet verschillen van het vertrekpunt van de vlucht.
	 */

	@Test
	public void test1_BestemmingMagNietGelijkZijnAanVertrek_False() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			Luchthaven bestemming = vlucht.getBestemming();
			assertTrue(bestemming == null);
			vlucht.zetBestemming(lh1);
			// De test zou niet verder mogen komen: er moet al een exception gethrowd zijn.
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming.equals(lh1));
		} catch (IllegalArgumentException e) {
			Luchthaven bestemming = vlucht.getBestemming();
			assertFalse(bestemming.equals(lh1));
		}
	}

	@Test
	public void test2_BestemmingMagNietGelijkZijnAanVertrek_True() {
		Vlucht vlucht = new Vlucht();
		Luchthaven bestemming;
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh2);
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming == null);
			vlucht.zetBestemming(lh1);
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming.equals(lh1));
		} catch (IllegalArgumentException e) {
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming.equals(lh1));
		}
	}


	/* test3*/
	@Test
	public void test3_DagDatumMagNietHogerZijnDanErInDeMaandZitten_True() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetVertrekTijd(testDatumVertrekDag);
			assertFalse(vlucht.getVertrekTijd().equals(testDatumVertrekDag));

		} catch (VluchtException e) {
			assertEquals("Geen geldig tijdstip!", e.getMessage());
		}
	}

	/* test4*/
	@Test
	public void test4_TijdMagNietHogerZijnDanTijdInEenDag_True() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetAankomstTijd(testUrenAankomst);
			Calendar test = vlucht.getAankomstTijd();
			assertTrue(test.equals(null));
		} catch (VluchtException e) {
			assertEquals("Geen geldig tijdstip!", e.getMessage());
		}
	}


	/* test5*/
	@Test
	public void test5_VertrekDatumVoorVertrekDatumplus1vast_True() {
		Vlucht vlucht1 = new Vlucht();
		try {
			vlucht1.zetVertrekTijd(testDatumDatumplus1);
			testDatumDatumplus1.set(2024, Calendar.SEPTEMBER, 30, 12,1);
			vlucht1.zetAankomstTijd(testDatumDatumplus1);
			assertTrue(vlucht1.getVertrekTijd().getTime().before(vlucht1.getAankomstTijd().getTime()));
		} catch (IllegalArgumentException | VluchtException e) {
			System.out.println(e);
		}
	}


	/* test6, In Class Vlucht zit geen code die terug geeft dat opgegeven tijd voor Nu is*/
	@Test
	public void test6_EenMinuutGeledenVertrek(){
		Vlucht vlucht = vl1;
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar vertrek = Calendar.getInstance();
		vertrek.setTimeZone(tz);

		try {
			vertrek.add(Calendar.MINUTE,-1);
			vl1.zetVertrekTijd(vertrek);//moet al exception opgeven dat het in het verleden ligt
			assertNotEquals(vl1.getVertrekTijd(),vertrek);

		} catch (IllegalArgumentException | VluchtException e) {
			assertEquals("Tijd in het verleden",e.getMessage());
			System.out.println(e);
		}
	}

	/* test7* zou dubbele error moeten geven?*/
	@Test
	public void test7_test2MinuutGeledenVertrek1MinuutGeledenAangekomen(){
		Vlucht vlucht = vl1;
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar vertrek = Calendar.getInstance();
		vertrek.setTimeZone(tz);

		try {
			vertrek.add(Calendar.MINUTE,-2);
			vl1.zetVertrekTijd(vertrek);//moet al exception opgeven dat het in het verleden ligt
			vertrek.add(Calendar.MINUTE,1);
			vl1.zetAankomstTijd(vertrek);
			assertTrue(vl1.getVertrekTijd().equals(null));
			assertTrue(vl1.getAankomstTijd().equals(null));
			throw new IllegalArgumentException("Tijd in het verleden");//throwt new exception, omdat datum niet in verleden mag liggen

		} catch (IllegalArgumentException | VluchtException e) {
			assertEquals("Tijd in het verleden",e.getMessage());
			System.out.println(e);
		}
	}
	/* test8*/
	@Test
	public void test8_1MinuutLaterAankomen(){
		Vlucht vlucht = vl1;
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar vertrek = Calendar.getInstance();
		vertrek.setTimeZone(tz);

		try {
			vl1.zetVertrekTijd(vertrek);//moet al exception opgeven dat het in het verleden ligt
			assertEquals(vertrek, vl1.getVertrekTijd());
			vertrek.add(Calendar.MINUTE,1);
			vl1.zetAankomstTijd(vertrek);
			assertEquals(vertrek, vl1.getAankomstTijd());
		} catch (IllegalArgumentException | VluchtException e) {
			assertEquals("Tijd in het verleden",e.getMessage());
			System.out.println(e);
		}
	}

	/* test9*/
	@Test
	public void test9_AankomstVoorVertrek_False() {
		Vlucht vlucht = vl1;
		Calendar vertrek = null;
		Calendar aankomst = null;
		try {
			vlucht.zetAankomstTijd(null);
			vertrek = vlucht.getVertrekTijd();
			aankomst = vlucht.getVertrekTijd();
			aankomst.add(Calendar.MINUTE,-1);
			vl1.zetVertrekTijd(vertrek);
			vl1.zetAankomstTijd(aankomst);
		} catch (VluchtException e) {
			assertEquals("Aankomsttijd voor vertrektijd",e.getMessage());
			System.out.println(e);
		}
	}

	/* test10*/
	@Test
	public void test10_AankomstVoorVertrek_True() {
		Vlucht vlucht = vl1;
		Calendar vertrek = null;
		Calendar aankomst = null;
		Calendar test = null;
		try {
			vlucht.zetAankomstTijd(null);
			vertrek = vlucht.getVertrekTijd();
			aankomst = vlucht.getVertrekTijd();
			aankomst.add(Calendar.MINUTE,1);
			vlucht.zetVertrekTijd(vertrek);
			vlucht.zetAankomstTijd(aankomst);
			test = vlucht.getAankomstTijd();
			assertFalse(test.equals(null));
		} catch (VluchtException e) {
			assertEquals("Aankomsttijd voor vertrektijd",e.getMessage());
			System.out.println(e);
		}
	}

	/* test11
	* test werkt wanneer je hem los runt, maar geeft nullpointerException wanneer je degehele test runt*/
	@Test
	public void test11_OverlappendeVlucht_Vertrek() {
		Calendar vertr = Calendar.getInstance();
		Calendar aank = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 12, 15, 0);
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		vertr.add(Calendar.HOUR,1); //vertrek vlucht 2 tussen vertek en aankomst vlucht 1
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht1.bewaar();
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);
			Calendar testVertek = vlucht2.getVertrekTijd();
			assertNotNull(testVertek);

		} catch (VluchtException e) {
			assertEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}

	/* test12, Om deze test uittevoeren moet code in Vlucht worden aan gepast, op dit moment is de methode isBezet
	* alleen gebruikt door de methode zetVertrekTijd en niet door de methode zetAankomstTijd*/
	@Test
	public void test12_OverlappendeVlucht_Aankomst() {
		Calendar vertr = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 12, 15, 0);
		Calendar aank = Calendar.getInstance();
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		vertr.add(Calendar.HOUR,-1); //vertrek vlucht 2 voor vertek vlucht 1
		aank.add(Calendar.HOUR,-1); //aankomst vlucht 2 tussen vertrek en aankomst vlucht 1
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht1.bewaar();
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);
			vlucht2.zetAankomstTijd(aank);
			// Moet hier al foutmelding ontvangen maar is niet het geval
			Calendar test = vlucht2.getAankomstTijd();
			assertNull(test);
		} catch (VluchtException e) {
			assertEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}
	/* test13*/
	@Test
	public void test13_OverlappendeVlucht_AankomstEnVertrek() {
		Calendar vertr = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 12, 15, 0);
		Calendar aank = Calendar.getInstance();
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		vertr.add(Calendar.HOUR,-1); //vertrek vlucht 2 voor vertek vlucht 1
		aank.add(Calendar.HOUR,1); //aankomst vlucht 2 na aankomst vlucht 1
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht1.bewaar();
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);
			vlucht2.zetAankomstTijd(aank);
			// Moet hier al foutmelding ontvangen maar is niet het geval
			Calendar testVertek = vlucht2.getVertrekTijd();
			assertNull(testVertek);
			Calendar testAankomst = vlucht2.getAankomstTijd();
			assertNull(testAankomst);
		} catch (VluchtException e) {
			assertEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}
	/* test14*/
	@Test
	public void test14_OverlappendeVlucht_AankomstEnVertrek_True() {
		Calendar vertr = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 12, 15, 0);
		Calendar aank = Calendar.getInstance();
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		vertr.add(Calendar.HOUR,4); //vertrek vlucht 2 voor vertek vlucht 1
		aank.add(Calendar.HOUR,4); //aankomst vlucht 2 na aankomst vlucht 1
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht1.bewaar();
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);
			vlucht2.zetAankomstTijd(aank);
			Calendar testVertek = vlucht2.getVertrekTijd();
			assertNotNull(testVertek);
			Calendar testAankomst = vlucht2.getAankomstTijd();
			assertNotNull(testAankomst);
		} catch (VluchtException e) {
			assertNotEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}

	/*test15, jona*/
	@Test
	public void test15_VluchtNietZonderVliegtuig(){
		try{
			Vlucht vlucht = new Vlucht(null, lh1, lh2, vtr, aan);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotEquals(null, vlucht.getVliegtuig());
		} catch (VluchtException e){
			assertEquals("Geen geldige vliegtuig.",e.getMessage());
		}
	}

	/*test16, jona*/
	@Test
	public void test16_VluchtNietZonderVertrekLuchthaven(){
		try{
			Vlucht vlucht = new Vlucht(vgt, null, lh2, vtr, aan);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotEquals(null,vlucht.getVertrekPunt(), "Vertrekpunt ongeldig");
		} catch (VluchtException e){
			assertEquals("Geen geldig vertekpunt.",e.getMessage());
		}
	}
	/*test17, jona*/
	@Test
	public void test17_VluchtNietZonderAankomstLuchthaven(){
		try{
			Vlucht vlucht = new Vlucht(vgt, lh1, null, vtr, aan);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotEquals(null,vlucht.getBestemming(), "Bestemming ongeldig");
		} catch (VluchtException e){
			assertEquals("Geen geldige bestemming.",e.getMessage());
		}
	}
	/*test18, jona*/
	@Test
	public void test18_VluchtNietZonderCorrecteVertrekTijd(){
		try{
			Vlucht vlucht = new Vlucht(vgt, lh1, lh2, vtr, aan);
			vlucht.zetVertrekTijd(null);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotNull(vlucht.getVertrekTijd(),"Vertrektijd ongeldig");
		} catch (VluchtException e){
			assertEquals("Geen geldige vertrektijd.",e.getMessage());
		}
	}
	/*test19, jona*/
	@Test
	public void test19_VluchtNietZonderCorrecteAankomstTijd(){
		try{
			Vlucht vlucht = new Vlucht(vgt, lh1, lh2, vtr, aan);
			vlucht.zetAankomstTijd(null);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotNull(vlucht.getAankomstTijd(),"Aankomsttijd ongeldig");
		} catch (VluchtException e){
			System.out.println(e.getMessage());
		}
	}
	/*test20, jona*/
	@Test
	public void testVluchtCorrect(){
		try{
			Vlucht vlucht = new Vlucht(vgt, lh1, lh2, vtr, aan);// Zou een exception moeten geven voor geen Vliegtuig
			vlucht.bewaar();
			assertNotEquals(vlucht.getVertrekPunt(), null, "Vliegtuig ongeldig");
			assertNotEquals(vlucht.getVertrekPunt(), null, "Vertrekpunt ongeldig");
			assertNotEquals(vlucht.getBestemming(), null, "Bestemming ongeldig");
			assertNotEquals(vlucht.getVertrekTijd(), null, "Vertrektijd ongeldig");
			assertNotEquals(vlucht.getAankomstTijd(), null, "Aankomsttijd ongeldig");
		} catch (VluchtException e){
			System.out.println(e.getMessage());
		}
	}



}
