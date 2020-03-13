package main.domeinLaag;


public class VliegtuigType 
{
   private Fabrikant fb;
   private String code;   
   private int capaciteit;
   
   public VliegtuigType(Fabrikant fb, String code, int cap) {
	   	this.fb = fb;
	    this.code = code;
	    this.capaciteit = cap;
   }
   
   public String geefCode() {
	   return code;
   }
   
   public Fabrikant geefFabrikant() {
	   return fb;   	
   }
   
   public int geefCapaciteit() {
	    return capaciteit;
   }

	@Override
	public String toString() {
		return "VliegtuigType [fb=" + fb + ", code=" + code + ", capaciteit="
				+ capaciteit + "]";
	}
   
}
