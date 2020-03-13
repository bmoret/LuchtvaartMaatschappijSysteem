package main.domeinLaag;
import java.util.*;

public class Luchthaven 
{
   private String naam = "";
   private String code = "";
   private boolean werkPlaats = false;
   private Land land;
   private static Set<Luchthaven> alleLuchthavens = new HashSet<Luchthaven>();

   /**
	* @return TreeMap met key = naam en value = (link naar) het Luchthaven-object.
	*/
   public static TreeMap<String, Luchthaven> geefAlle() {
	TreeMap<String, Luchthaven> alleLhMap = new TreeMap<String, Luchthaven>();
	for (Luchthaven lh : alleLuchthavens) {
		alleLhMap.put(lh.naam, lh);
	}
	return alleLhMap;
   }
 
	public Luchthaven(String naam, String code, boolean werkplaats, Land land) {
		 this.naam = naam;
		 this.code = code;
		 this.werkPlaats = werkplaats;
		 this.land = land;
		 alleLuchthavens.add(this);
		 land.addLuchthaven(this);
	}
	
	public Luchthaven() 
	{
	}
   
	/**
	Controleer of de naam uniek is binnen het land.
	Zo ja, leg naam vast.
	Zo nee, throw exception.
    */
	public void zetNaam(String naam) throws IllegalArgumentException {
		if (naam == null) {
			this.naam = null;
		} else {
			String nmTrim = naam.trim();
			Luchthaven bestaandeLuchthaven = land.geefLuchthaven(nmTrim);
			if (bestaandeLuchthaven != null)
				throw new IllegalArgumentException("Naam bestaat al!");
			else if (nmTrim.length() == 0)
				throw new IllegalArgumentException("Naam heeft geen geldige waarde!");
			else
				this.naam = nmTrim;
		}
	}
  
	public String geefNaam() {
	 return naam;
	}

	public void zetCode(String code) {
		if (code == null) {
			this.code = null;
		} else {
			String cdTrim = code.trim();
			if (cdTrim.length() == 0)
				throw new IllegalArgumentException("Code heeft geen geldige waarde!");
			this.code =code;
		}
	}
   
	public void zetWerkPlaats(boolean wp)
	{
		werkPlaats = wp;
	}
   
	public void zetLand(Land land)
	{
		this.land = land;
	}
	   
	/**
	Controleer of alle attributen een waarde hebben (alles verplicht).
	Zo ja: roep Land.addLuchthaven().
	Zo nee: IllegalStateException.
	Sluit functie af.
	@throws java.lang.IllegalStateException
    */
	public void bewaar() throws IllegalStateException {
		if(land == null)
			throw new IllegalStateException("Land niet ingevuld!");
		else if (naam == null || naam.isEmpty())
			throw new IllegalStateException("Naam niet ingevuld!");
		else if (code == null || code.isEmpty())
			throw new IllegalStateException("Code niet ingevuld!");
		else
			land.addLuchthaven(this);
		 	alleLuchthavens.add(this);
	}

	public String geefLandNaam() {
		return land.geefNaam();
	}

	public String geefCode() {
		return code;
	}

	public boolean geefWerkplaats() {
		return werkPlaats;
	}

	@Override
	public String toString() {
		return "Luchthaven [naam=" + naam + ", code=" + code + ", werkPlaats="
				+ werkPlaats + ", land=" + land + "]";
	}
}