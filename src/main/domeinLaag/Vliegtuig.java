package main.domeinLaag;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

public class Vliegtuig 
{
   private String naam = "";
   private Calendar datumInGebruik = null;
   private VliegtuigType vtt;
   private LuchtvaartMaatschappij lvm;
   
   public Vliegtuig(LuchtvaartMaatschappij lvm) {
		this.lvm = lvm;
	}

	// Alleen voor initialisatie van testobjecten
   public Vliegtuig(LuchtvaartMaatschappij lvm,VliegtuigType VtT, String nm, Calendar inGebruik)
		   throws IllegalArgumentException {
	   this.lvm = lvm;
	   vtt = VtT;
	   datumInGebruik = inGebruik;
	   naam = nm;
	   lvm.addVliegtuig(this);
   }
   
   public void zetVliegtuigType(VliegtuigType vtt) {
	   this.vtt = vtt;
   }
   
   public VliegtuigType geefVliegtuigType() {
	return vtt;
   }
 
    /**
   	Controleert of de naam niet leeg is en uniek is voor de LvM:
   	- Vraag een lijst van alle vliegtuigen op.
   	- Controleer of er al een vliegtuig met die naam is.
		- Zo ja, throw IllegalArgumentException
		- Zo nee, zet de naam.
   	@param naam
	*/
   public void zetNaam(String naam) throws IllegalArgumentException {
		if (naam == null) {
			this.naam = null;
		} else {
			String naamTrim = naam.trim();
			TreeMap<String, Vliegtuig> alleVt = lvm.geefVliegtuigen();
			Vliegtuig bestaandVliegtuig = alleVt.get(naamTrim);
			if (bestaandVliegtuig != null)
				throw new IllegalArgumentException("Naam bestaat al!");
			else if (naamTrim.length() == 0)
				throw new IllegalArgumentException("Naam heeft geen geldige waarde!");
			else
				this.naam = naamTrim;
		}
   }
   
   public String geefNaam(){
	   return naam;
   }

   public void zetInGebruik(Calendar inGebruik) throws IllegalArgumentException {
	   Calendar datumIG = inGebruik;
	   datumIG.setLenient(false);
	   try {
		   // Controleer of het een geldige datum is en of de datum in de toekomst ligt.
		   // Calendar doet dit niet bij het zetten, maar bij getTime() en dergelijke.
		   @SuppressWarnings("unused")
		   Date datum = datumIG.getTime();
	   } catch (IllegalArgumentException e) {
		   throw new IllegalArgumentException("Geen geldige datum!");
	   }
	   // Controleer of de datum in de toekomst ligt.
	   Calendar nu = Calendar.getInstance();
	   if (datumIG.after(nu)) {
		   datumInGebruik = (Calendar) inGebruik.clone();
	   } else {
		   throw new IllegalArgumentException("Datum moet in toekomst liggen!");
	   }
   }
   
   public Calendar geefDatumInGebruik() {
	   return datumInGebruik;
   }
   /**
    * Controleert of lvm, vtt en naam ingevuld zijn.
    * 	Zo niet: @throws IllegalStateException.
    * 	Zo wel: roept lvm.addVliegtuig().
    */
   public void bewaar() throws IllegalStateException {
	   	if (vtt == null)
	   		throw new IllegalStateException("Vliegtuigtype niet geselecteerd!");
	   	else if (naam == null || naam.isEmpty())
			throw new IllegalStateException("Naam niet goed ingevuld!");
		else if (datumInGebruik == null)
			throw new IllegalStateException("Datum-in-gebruik niet goed ingevuld!");
	   	else
	   		lvm.addVliegtuig(this);
   }
   
   public int geefCapaciteit() {
   		return vtt.geefCapaciteit();
   }

@Override
public String toString() {
	return "Vliegtuig [naam=" + naam + ", datumInGebruik=" + datumInGebruik
			+ ", VliegtuigType=" + vtt + ", LuchtvaarMy=" + lvm + "]";
}
   
	  
}
