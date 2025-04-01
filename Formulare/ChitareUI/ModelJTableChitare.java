/*
 * ModelJTableChitare.java
 *
 * Created on 01 iulie 2004, 05:34
 */

package Formulare.ChitareUI;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import Sursa.*;
import java.sql.*;
import java.math.BigDecimal;
import utilitati.*;
/**
 *
 * @author alex
 */
public class ModelJTableChitare extends AbstractTableModel {
 public java.util.HashMap listaObjModif=new java.util.HashMap();
 public ArrayList listaChitare=new ArrayList();
    Connection conexiune;
    
    /** Creates a new instance of ModelGridStudenti */
    public ModelJTableChitare(){
        try{
       this.conexiune=Utilitati.getConexiune();
       this.listaChitare=Chitare.getObjects(conexiune,"order by firma");
        }
       catch(Exception e){
           Utilitati.tratareErori(e);
       }
   }
     public String getColumnName(int column) {
     if (column==0)
         return "Cod chitara";
     else if(column==1)
         return "Firma";
     else if(column==2)
         return "Tip Chitara";
     else if(column==3)
        return "Tip Poz";
     else if(column==4)
        return "Rezonanta";   
     else if(column==5)
        return "Nr Doze";
      else if(column==6)
        return "Model Doza";   
      else if(column==7)
        return "Nr taste";   
      else if(column==8)
        return "Tip Taste";   
      else 
        return "Foto";   
     }
    public int getColumnCount() {
        return 10;
    }
    
    public int getRowCount() {
        return this.listaChitare.size();
    }
    public Class getColumnClass(int col) {
        if (col==0||col==1||col==2||col==3||col==4||col==6||col==8)
            return String.class;
        else      
            if(col==5||col==7)
            return BigDecimal.class;
            else
              return oracle.sql.BLOB.class;
      }
    
 public Object getValueAt(int rowIndex, int columnIndex) {
        Chitare objChitare=(Chitare)this.listaChitare.get(rowIndex);
      
     if (columnIndex==0)
         return objChitare.getCodchitara();
     else if(columnIndex==1)
         return objChitare.getFirma();
     else if(columnIndex==2)
         return objChitare.getTip_chitara();
     else if(columnIndex==3)
        return objChitare.getTip_poz();
     else if(columnIndex==4)
        return objChitare.getRezonanta();
     else if(columnIndex==5)
        return objChitare.getNr_doze(); 
     else if(columnIndex==6)
        return objChitare.getModel_doza();
     else if(columnIndex==7)
        return objChitare.getNr_taste();
     else if(columnIndex==8)
        return objChitare.getTip_taste();
     else 
        return objChitare.getFoto(); 
    }
 public void setValueAt(Object valNoua, int rowIndex, int columnIndex) {
    Chitare objChitare=(Chitare)this.listaChitare.get(rowIndex);
      if(!this.listaObjModif.containsKey(objChitare.getIdLinieBD()))
          this.listaObjModif.put(objChitare.getIdLinieBD(), objChitare);
     
    if (columnIndex==0)
         objChitare.setCodchitara((String)valNoua);
     else if(columnIndex==1)
         objChitare.setFirma((String)valNoua);
     else if(columnIndex==2)
        objChitare.setTip_chitara((String)valNoua);
     else if(columnIndex==3)
        objChitare.setTip_poz((String)valNoua);
     else if(columnIndex==4)
        objChitare.setRezonanta((String)valNoua);
     else if(columnIndex==5)
        objChitare.setNr_doze((BigDecimal)valNoua); 
     else if(columnIndex==6)
        objChitare.setModel_doza((String)valNoua);         
    else if(columnIndex==7)
        objChitare.setNr_taste((BigDecimal)valNoua); 
    else if(columnIndex==8)
        objChitare.setTip_taste((String)valNoua); 
  //  else 
  //      objChitare.setTip_taste((String)valNoua); 
     }
     public boolean isCellEditable(int rowIndex, int columnIndex) {
          return true;
    }
     
 // zona tranzactionala specfica     
 public void comiteModificari(){
     java.util.Set setChei=this.listaObjModif.keySet();
     java.util.Iterator i=setChei.iterator();
     while(i.hasNext())
       try{
           Object cheia=i.next();
           ((Chitare)this.listaObjModif.get(cheia)).salveaza(this.conexiune);
           this.listaObjModif.remove(cheia);
       }
       catch(Exception e){
           Utilitati.tratareErori(e);
       }
     
     /*Chitare objChitare=null;
     for (int i=0;i<this.listaChitare.size();i++)
         try{    // incercam salvarea obiectului curent
            objChitare=(Chitare)this.listaChitare.get(i);
            int stare=objChitare.getStare();
            System.out.println(stare);
            if (stare!=Chitare.SINCRONIZAT)
                objChitare.salveaza(this.conexiune);
            // daca nu a dat eroare si era STERS tre' sa-l elimin din colectie
            if (stare==Chitare.STERS)
                this.listaChitare.remove(i);
         }
         catch(Exception e){Utilitati.tratareErori(e);} */
     }
     
 public void anulezModificariBuffer(){
  java.util.Set setChei=this.listaObjModif.keySet();
  java.util.Iterator i=setChei.iterator();
     while(i.hasNext())
     {Object cheia=i.next();
      try{
           ((Chitare)this.listaObjModif.get(cheia)).refresh(this.conexiune);
      }
      catch(Exception e){
           Utilitati.tratareErori(e);
      }
     this.listaObjModif.remove(cheia);
     }   
}
/*     Chitare objChitare=null;
     for (int i=0;i<this.listaChitare.size();i++)
         try{    
            objChitare=(Chitare)this.listaChitare.get(i);
            int stare=objChitare.getStare();
            if (stare==Chitare.NOU)
                this.listaChitare.remove(i);
            else if (stare!=Chitare.SINCRONIZAT)
                objChitare.refresh(this.conexiune);
             }
         catch(Exception e){Utilitati.tratareErori(e);} 
     }*/
/* public void requeryModel(String pCodchitara){
     try{
        this.listaChitare=Chitare.getObjects(this.conexiune, "where codchitara="+pCodchitara);
    }
    catch(Exception e){Utilitati.tratareErori(e);} 
    
    }
  public void addLinieNoua(String codchitara){
        
 Chitare obiectNou=new Chitare(null,codchitara,String.valueOf("a"),String.valueOf("a")
 ,String.valueOf("a"),String.valueOf("a"),BigDecimal.valueOf(0),
 null,BigDecimal.valueOf(0),null,null);
        this.listaChitare.add(obiectNou);
        
        
    }
    public void stergLinie(int rowIndex){
       Chitare objChitare=(Chitare)this.listaChitare.get(rowIndex);
       objChitare.setStare(Chitare.STERS);
      }   */
}
