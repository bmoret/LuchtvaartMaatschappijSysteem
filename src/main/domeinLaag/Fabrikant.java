package main.domeinLaag;
import java.util.TreeMap;
import java.util.HashSet;

public class Fabrikant 
{
   private String naam;
   private String contactPersoon;
   private static HashSet<Fabrikant> alleFabrikanten = new HashSet<Fabrikant>();
   private HashSet<VliegtuigType> vliegtuigTypen;
   
   public Fabrikant(String naam, String cp) 
   {
	this.naam = naam;
	this.contactPersoon = cp;
	Fabrikant.alleFabrikanten.add(this);
	vliegtuigTypen = new HashSet<VliegtuigType>();
   }
   
   /**
   @return TreeMap met key = naam en value = (link naar) het Fabrikant-object. 
    */
   public static TreeMap<String, Fabrikant> geefAlle() {
	TreeMap<String, Fabrikant> alle = new TreeMap<String, Fabrikant>();
	for (Fabrikant fabrikant : alleFabrikanten) {
		String naam = fabrikant.naam;
		alle.put(naam, fabrikant);
	}
    return alle;
   }
   
   public String geefNaam() {
    return naam;
   }
   
   public String geefContactpersoon() {
    return contactPersoon;
   }
   
   /**
   @return TreeMap met key = code en value = (link naar) het VliegtuigType-object.
    */
   public TreeMap<String, VliegtuigType> geefVliegtuigTypen() {
	TreeMap<String, VliegtuigType> alleVtt = new TreeMap<String, VliegtuigType>();
	for (VliegtuigType vtt : vliegtuigTypen) {
		String code = vtt.geefCode();
		alleVtt.put(code, vtt);
	}
	return alleVtt;
   }
   
   public VliegtuigType creeervliegtuigtype(String code, int cap) {
	VliegtuigType vtt = new VliegtuigType(this, code, cap);
	vliegtuigTypen.add(vtt);
    return vtt;
   }

   @Override
	public String toString() {
		return "Fabrikant [naam=" + naam + ", contactPersoon=" + contactPersoon + "]";
	}

}
