/*
 * ModelJTableArtRepar.java
 *
 * Created on June 29, 2004, 1:22 AM
 */
package Formulare.grid.GridComplex;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.sql.*;
import java.math.BigDecimal;
import Sursa.*;
import utilitati.*;
/**
 *
 * @author alex
 */
public class ModelJTableArtRepar extends AbstractTableModel {
    public ArrayList listaArt=new ArrayList();
    public Connection conexiune;
    
    /** Creates a new instance of ModelGridStudenti */
    public ModelJTableArtRepar(){
        try{
       this.conexiune=Utilitati.getConexiune();
       this.listaArt=ArtRepar.getObjects(conexiune,null);
        }
       catch(Exception e){
           Utilitati.tratareErori(e);
       }
    }
     public String getColumnName(int column) {
     if (column==0)
         return "Cod Repar";
     else if(column==1)
         return "Cod Chitara";
     else if(column==2)
         return "Cod Compo";
     else if(column==3)
        return "Cantitate";
     else if(column==4)
        return "Grad_defect"; 
     else 
        return "Pret Reparatie";
     
     
     }
    public int getColumnCount() {
        return 6;
    }
    
    public int getRowCount() {
        return this.listaArt.size();
    }
    public Class getColumnClass(int col) {
          if (col==0||col==2||col==3||col==4||col==5)
            return BigDecimal.class;
        else      
             return String.class;
           
        
      }
 public Object getValueAt(int rowIndex, int columnIndex) {
        ArtRepar objLinie=(ArtRepar)this.listaArt.get(rowIndex);
     if (columnIndex==0)
         return objLinie.getCodrepar();
     else if(columnIndex==1)
         return objLinie.getCodchitara();
     else if(columnIndex==2)
         return objLinie.getCodcompo();
     else if(columnIndex==3)
        return objLinie.getCantitate();
     else if(columnIndex==4)
        return objLinie.getGrad_defect();
     else 
        return objLinie.getPretreparatie();
     
    }
 public void setValueAt(Object valNoua, int rowIndex, int columnIndex) {
    ArtRepar objLinie=(ArtRepar)this.listaArt.get(rowIndex);
        
     if (columnIndex==0)
         objLinie.setCodrepar((BigDecimal)valNoua);
     else if(columnIndex==1)
         objLinie.setCodchitara((String)valNoua);
     else if(columnIndex==2)
        objLinie.setCodcompo((BigDecimal)valNoua);
     else if(columnIndex==3)
        objLinie.setCantitate((BigDecimal)valNoua);
     else if(columnIndex==4)
        objLinie.setGrad_defect((BigDecimal)valNoua);
     else if(columnIndex==5)
        objLinie.setPretreparatie((BigDecimal)valNoua);
        
     }
     public boolean isCellEditable(int rowIndex, int columnIndex) {
         if (columnIndex==0)
            return false;
         else
            return true;
    }
     
 // zona tranzactionala specfica     
 public void comiteModificari(){
     ArtRepar objLinie=null;
     for (int i=0;i<this.listaArt.size();i++)
         try{    // incercam salvarea obiectului curent
            objLinie=(ArtRepar)this.listaArt.get(i);
            System.out.println((ArtRepar)this.listaArt.get(i));
            int stare=objLinie.getStare();
            //System.out.println(stare);
            if (stare!=ArtRepar.SINCRONIZAT)
                objLinie.salveaza(this.conexiune);
            // daca nu a dat eroare si era STERS trebuie sa-l elimin din colectie
            if (stare==ArtRepar.STERS)
                this.listaArt.remove(i);
         }
         catch(Exception e){Utilitati.tratareErori(e);} 
     }
  
 public void anulezModificari(){
     ArtRepar objLinie=null;
     for (int i=0;i<this.listaArt.size();i++)
         try{    
            objLinie=(ArtRepar)this.listaArt.get(i);
            int stare=objLinie.getStare();
            if (stare==ArtRepar.NOU)
                this.listaArt.remove(i);
            else if (stare!=ArtRepar.SINCRONIZAT)
                objLinie.refresh(this.conexiune);
             }
         catch(Exception e){Utilitati.tratareErori(e);} 
     }
 public void requeryModel(BigDecimal pcodrepar){
     try{
        this.listaArt=ArtRepar.getObjects(this.conexiune, "where codrepar="+pcodrepar);
    }
    catch(Exception e){Utilitati.tratareErori(e);} 
    
    }
  public void addLinieNoua(BigDecimal codrepar){
        
 ArtRepar obiectNou=new ArtRepar(null,codrepar,null,BigDecimal.valueOf(0),BigDecimal.valueOf(0), BigDecimal.valueOf(0),BigDecimal.valueOf(0));
 this.listaArt.add(obiectNou);
        
        
    }
    public void stergLinie(int rowIndex){
       ArtRepar objLinie=(ArtRepar)this.listaArt.get(rowIndex);
       objLinie.setStare(ArtRepar.STERS);
      }   
}