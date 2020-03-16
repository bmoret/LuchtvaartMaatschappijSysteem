package test;

import main.domeinLaag.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.plaf.SeparatorUI;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class VluchtTest {

	static LuchtvaartMaatschappij lvm;
	static Fabrikant f1;
	static VliegtuigType vtt1;
	static Vliegtuig vt1;
	static Luchthaven lh1, lh2;
	static Vlucht vl1, vl2;
	static Calendar testDatumVertrekDag, testUrenAankomst, testDatumDatumplus1, dagTijd;

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
	public void testBestemmingMagNietGelijkZijnAanVertrek_False() {
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
	public void testBestemmingMagNietGelijkZijnAanVertrek_True() {
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
	public void testDagDatumMagNietHogerZijnDanErInDeMaandZitten_True() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetVertrekTijd(testDatumVertrekDag);

		} catch (IllegalArgumentException | VluchtException e) {
			System.out.println(e);
		}
	}

	/* test4*/
	@Test
	public void testTijdMagNietHogerZijnDanTijdInEenDag_True() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetAankomstTijd(testUrenAankomst);
		} catch (IllegalArgumentException | VluchtException e) {
			System.out.println(e);
		}
	}


	/* test5*/
	@Test
	public void testVertrekDatumVoorVertrekDatumplus1vast_True() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetVertrekTijd(testDatumDatumplus1);
			testDatumDatumplus1.set(2025, Calendar.SEPTEMBER, 30, 12,1);
			vlucht.zetAankomstTijd(testDatumDatumplus1);
			assertTrue(vlucht.getVertrekTijd().getTime().before(vlucht.getAankomstTijd().getTime()));
		} catch (IllegalArgumentException | VluchtException e) {
			System.out.println(e);
			assertTrue(vlucht.getVertrekTijd().getTime().before(vlucht.getAankomstTijd().getTime()));

		}
	}
	/* test9*/
	@Test
	public void testAankomstVoorVertrek_False() {
		Vlucht vlucht = vl1;
		Calendar vertrek = null;
		Calendar aankomst = null;
		Calendar test = null;
		try {
			vlucht.zetAankomstTijd(null);
			vertrek = vlucht.getVertrekTijd();
			aankomst = vlucht.getVertrekTijd();
			aankomst.add(Calendar.MINUTE,-1);
			vl1.zetVertrekTijd(vertrek);
			vl1.zetAankomstTijd(aankomst);
		} catch (VluchtException e) {
			assertEquals("Aankomsttijd voor vertrektijd",e.getMessage());
		}
	}
	/* test10*/
	@Test
	public void testAankomstVoorVertrek_True() {
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
		}
	}
	/* test11*/
	@Test
	public void testOverlappendeVlucht_Vertrek() {
		Calendar vertr = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 14, 15, 0);
		Calendar aank = Calendar.getInstance();
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);

		} catch (VluchtException e) {
			assertEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}
	/* test11, Om deze test uittevoeren moet code in Vlucht worden aan gepast, op dit moment is de methode isBezet
	* alleen gebruikt door de methode zetVertrekTijd en niet door de methode zetAankomstTijd*/
	@Test
	public void testOverlappendeVlucht_Aankomst() {
		Calendar vertr = Calendar.getInstance();
		vertr.set(2020, Calendar.MARCH, 30, 14, 15, 0);
		Calendar aank = Calendar.getInstance();
		aank.set(2020, Calendar.MARCH, 30, 15, 15, 0);
		Vlucht vlucht1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
		vertr.set(2020, Calendar.MARCH, 30, 8, 15, 0);
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekTijd(vertr);
			vlucht2.zetAankomstTijd(aank);

		} catch (VluchtException e) {
			assertEquals("Vliegtuig reeds bezet op Mon Mar 30 14:15:00 CEST 2020",e.getMessage());
		}
	}
}
