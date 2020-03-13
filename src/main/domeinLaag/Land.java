package main.domeinLaag;
import java.util.HashSet;
import java.util.TreeMap;

public class Land 
{
   private String naam;
   private int code;
   /** Alle luchthavens binnen het land */
   private HashSet<Luchthaven> luchthavens;
   private static HashSet<Land> alleLanden = new HashSet<Land>();
   
   public Land(String nm, int cd) 
   {
    naam = nm;
    code = cd;
    luchthavens = new HashSet<Luchthaven>();
    alleLanden.add(this);
   }
  
   public String geefNaam()
   {
	return naam;
   }
   	
   public int geefCode() 
   {
    return code;
   }
   
   /** Returns een TreeMap met naam als key en de link naar het Land als value. */
   public static TreeMap<String, Land> geefAlleLanden() {
	TreeMap<String, Land> landMap = new TreeMap<String, Land>();
	for (Land land : alleLanden) {
		String naam = land.geefNaam();
		landMap.put(naam,land); 
	}
	return landMap; 
   }
   
   /** Voegt een Luchthaven toe */
   public void addLuchthaven(Luchthaven lhv) {
   	luchthavens.add(lhv);
   }
   
   /** Returns een TreeMap met naam als key en de link naar de Luchthaven als value. */
   public TreeMap<String, Luchthaven> geefLuchthavens() {
	TreeMap<String, Luchthaven> luchthavenMap = new TreeMap<String, Luchthaven>();
	for (Luchthaven luchthaven: luchthavens) {
		String naam = luchthaven.geefNaam();
		luchthavenMap.put(naam,luchthaven); 
	}
	return luchthavenMap; 
   }

   /** Returns een Luchthaven als die bestaat met de gegeven naam; anders null. */
	public Luchthaven geefLuchthaven(String naam) {			
		Luchthaven returnvalue = null;
		for(Luchthaven luchthaven : luchthavens) {
			if (luchthaven.geefNaam().equals(naam))
				returnvalue = luchthaven; 
		}
		return returnvalue;
	}

	@Override
	public String toString() {
		return "Land [naam=" + naam + ", code=" + code + "]";
	}
}

