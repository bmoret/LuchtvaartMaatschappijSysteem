package main.domeinLaag;
import java.util.TreeMap;
import java.util.HashSet;

public class LuchtvaartMaatschappij 
{
   private String naam;
   private HashSet<Vliegtuig> vliegtuigen= new HashSet<Vliegtuig>();

	public static LuchtvaartMaatschappij getCurrentLuchtvaartMaatschappij() {
		return currentLuchtvaartMaatschappij;
	}

	public static void setCurrentLuchtvaartMaatschappij(LuchtvaartMaatschappij currentLuchtvaartMaatschappij) {
		LuchtvaartMaatschappij.currentLuchtvaartMaatschappij = currentLuchtvaartMaatschappij;
	}

	private static LuchtvaartMaatschappij currentLuchtvaartMaatschappij;
   
   public LuchtvaartMaatschappij(String naam) {
	   this.naam = naam;    
   }
   
   public void addVliegtuig(Vliegtuig vt) {
	   vliegtuigen.add(vt);
   }
   
   /**
   @return TreeMap met key = naam en value = (link naar) het Vliegtuig-object
    */
   public TreeMap<String, Vliegtuig> geefVliegtuigen() {
		TreeMap<String, Vliegtuig> alleVt = new TreeMap<String, Vliegtuig>();
		for (Vliegtuig vliegtuig : vliegtuigen) {
			String naam = vliegtuig.geefNaam();
			alleVt.put(naam, vliegtuig);
		}
		return alleVt;
   }

	@Override
	public String toString() {
		return "LuchtvaartMaatschappij [naam=" + naam+ "]";
	}
}

