/*
 * ModelGridStudenti.java
 *
 * Created on 11 mai 2003, 14:13
 */

package Formulare.grid.Reparatii;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import utilitati.Utilitati;
import Sursa.*;
import java.sql.*;
import java.math.BigDecimal;
public class ModelJTableReparatii extends AbstractTableModel {
    public ArrayList listaReparatii=new ArrayList();
    public Connection conexiune;
    
    /** Creates a new instance of ModelGridStudenti */
    public ModelJTableReparatii(Connection conn){
       this.conexiune=conn;
    }
     public String getColumnName(int column) {
     if (column==0)
         return "Codrepar";
     else if(column==1)
         return "Data Repar";
     else if(column==2)
         return "Cod Client";
     else 
        return "Valoare Totala ";
            
     }
     
    public int getColumnCount() {
        return 4;
    }
    
    public int getRowCount() {
        return this.listaReparatii.size();
    }
    public Class getColumnClass(int col) {
        if (col==1)
            return Timestamp.class;
        else      
            return BigDecimal.class;
      }
 public Object getValueAt(int rowIndex, int columnIndex) {
        Reparatii objRepar=(Reparatii)this.listaReparatii.get(rowIndex);
     if (columnIndex==0)
         return objRepar.getCodrepar();
     else if(columnIndex==1)
         return objRepar.getDataRepar();
     else if(columnIndex==2)
         return objRepar.getCodcl();
     else 
        return objRepar.getValtotalar();
         
    }
 public void setValueAt(Object valNoua, int rowIndex, int columnIndex) {
    Reparatii objRepar=(Reparatii)this.listaReparatii.get(rowIndex);
        
     if (columnIndex==0)
         objRepar.setCodrepar((BigDecimal)valNoua);
     else if(columnIndex==1)
         objRepar.setDatarepar((Timestamp)valNoua);
     else if(columnIndex==2)
        objRepar.setCodcl((BigDecimal)valNoua);
     else if(columnIndex==3)
        objRepar.setValtotalar((BigDecimal)valNoua);
     }
     public boolean isCellEditable(int rowIndex, int columnIndex) {
         if (columnIndex==0)
            return false;
         else
            return true;
    }
     
 // zona tranzactionala specfica     
 public void comiteModificari(){
     Reparatii objRepar=null;
     for (int i=0;i<this.listaReparatii.size();i++)
         try{    // incercam salvarea obiectului curent
            objRepar=(Reparatii)this.listaReparatii.get(i);
            int stare=objRepar.getStare();
            System.out.println(stare);
            if (stare!=Reparatii.SINCRONIZAT)
                objRepar.salveaza(this.conexiune);
            // daca nu a dat eroare si era STERS tre' sa-l elimin din colectie
            if (stare==Reparatii.STERS)
                this.listaReparatii.remove(i);
         }
         catch(Exception e){utilitati.Utilitati.tratareErori(e);}
     }
     
 public void anulezModificari(){
     Reparatii objRepar=null;
     for (int i=0;i<this.listaReparatii.size();i++)
         try{    
            objRepar=(Reparatii)this.listaReparatii.get(i);
            int stare=objRepar.getStare();
            if (stare==Reparatii.NOU)
                this.listaReparatii.remove(i);
            else if (stare!=Reparatii.SINCRONIZAT)
                objRepar.refresh(this.conexiune);
             }
         catch(Exception e){Utilitati.tratareErori(e);}
     }
 public void requeryModel(BigDecimal pCodrepar, BigDecimal an, BigDecimal luna){
     try{
        this.listaReparatii=Reparatii.getObjects(this.conexiune, "where codrepar="+pCodrepar
                                           +"  and Extract(MONTH from datarepar)="+luna
                                           +"  and Extract(YEAR from datarepar)="+an);
    }
    catch(Exception e){utilitati.Utilitati.tratareErori(e);}
    
    }
  public void addLinieNoua(BigDecimal codrepar){
        
        Reparatii obiectNou=new Reparatii(null,BigDecimal.valueOf(0),codrepar,new Timestamp(1), BigDecimal.valueOf(0));
        this.listaReparatii.add(obiectNou);
        
        
    }
    public void stergLinie(int rowIndex){
       Reparatii objRepar=(Reparatii)this.listaReparatii.get(rowIndex);
       objRepar.setStare(Reparatii.STERS);
      }   
}
