/*
 * Incasare.java
 *
 * Created on 28 iunie 2004, 14:45
 */

package UML;
import java.text.*;
/**
 *
 * @author  alex
 */
public class Incasare {
    public Reparatii reparatii;
    public int codinc;
    public java.sql.Date datainc;
    private java.util.Date newdate;
    
    /** Creates a new instance of Incasare */
    public Incasare() {
       reparatii=new Reparatii();
       codinc=0;
      SimpleDateFormat frm=new SimpleDateFormat("dd-MM-yyyy");
      frm.setLenient(false);
        try {
                newdate=frm.parse("01-06-2004");
        } 
        catch (Exception e) {;}; 
        datainc=(java.sql.Date)newdate;
    }
    public String toString(){
        return "Reparatia"+reparatii.nrreparatie+" este incasata ";
    }
    public Incasare(int pCodinc,Reparatii pReparatii,java.sql.Date pDatainc){
     reparatii=new Reparatii(pReparatii.nrreparatie,pReparatii.datarepar);
     codinc=pCodinc;
     datainc=pDatainc;   
    }
    
}

