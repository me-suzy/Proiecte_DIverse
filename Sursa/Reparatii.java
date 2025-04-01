/*
 * Reparatii.java
 *
 * Created on 28 iunie 2004, 16:19
 */

package Sursa;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import utilitati.Utilitati;
/**
 *
 * @author  alex
 */
public class Reparatii {
    
    /** Creates a new instance of reparatii */
 
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    protected BigDecimal codrepar;
    public BigDecimal codcl;
    protected BigDecimal valtotalar;
    public Timestamp datarepar;
    private java.util.Date newdate;
    
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
  
    public java.math.BigDecimal getCodrepar(){return this.codrepar;}
    public java.math.BigDecimal getCodcl(){return this.codcl;}
    public Timestamp getDatarepar(){return this.datarepar;}
    public java.math.BigDecimal getValtotalar(){return this.valtotalar;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodcl(java.math.BigDecimal valNoua){
       if (valNoua==null ? codcl!=valNoua : !valNoua.equals(codcl))
       { this.codcl=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setCodrepar(java.math.BigDecimal valNoua){
        if (valNoua==null ? codrepar!=valNoua : !valNoua.equals(codrepar))
        {
        this.codrepar=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setValtotalar(java.math.BigDecimal valNoua){
        if (valNoua==null ? valtotalar!=valNoua : !valNoua.equals(valtotalar))
        {
            this.valtotalar=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setDatarepar(Timestamp valNoua){
        if (valNoua==null ? datarepar!=valNoua : !valNoua.equals(datarepar))
        {
            this.datarepar=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Reparatii() {
        this.stare=NOU; 
    }
    public Reparatii(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodrepar,BigDecimal pCodcl,
                    Timestamp pDatarepar,BigDecimal pValtotalar)
    {
        this.idLinieBD=pIdlinieBD;
        this.codrepar=pCodrepar;
        this.codcl=pCodcl;
        this.datarepar=pDatarepar;
        this.valtotalar=pValtotalar;
    }
    
    public int  getStare(){return this.stare;}
    
    public void setStare(int stareNoua){
       if (this.stare==NOU && stareNoua==MODIFICAT)
           return;
       else
           this.stare=stareNoua;
    }
    public void salveaza(Connection conn) throws Exception{
        if(this.stare==SINCRONIZAT)
            return;
        boolean conexiuneNula=false;
        if(conn==null)
        {
         conn=Utilitati.getConexiune();
         conexiuneNula=true;
        }
        PreparedStatement stmt=null;
        Statement stmtRowid=null;
    try{
      if(this.stare==NOU){
          stmt=conn.prepareStatement("insert into reparatii"+"(codrepar,codcl,datarepar,valtotalar)"+
          "values(?,?,?,?)");
        stmt.setBigDecimal(1, this.codrepar);stmt.setBigDecimal(2, this.codcl);
        stmt.setTimestamp(3,this.datarepar);stmt.setBigDecimal(4,this.valtotalar);
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from reparatii where codrepar="+this.codrepar.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update reparatii set "+"codrepar=?,codcl=?,datarepar=?,valtotalar=? where rowid=?");
          stmt.setBigDecimal(1, this.codrepar);stmt.setBigDecimal(2, this.codcl); 
          stmt.setTimestamp(3,this.datarepar);stmt.setBigDecimal(4,this.valtotalar); 
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from reparatii where rowid=?");
       ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1,this.idLinieBD);
       stmt.execute();
       this.stare=SINCRONIZAT;
      }
      stmt.close();
      }
    catch(Exception e){if (stmt!=null)
        //e.printStackTrace();
                        stmt.close();
                       if (conexiuneNula) // daca nu am primit conexiune o inchid}
                           conn.close();
                         if(stmtRowid !=null) 
                             stmt.close();
                       throw e;
    }                 
    if (conexiuneNula) //daca nu am primit conexiune o inchid
        conn.close();
    }
    public void refresh(Connection conn) throws Exception{
        if(this.idLinieBD==null)
            return;
        boolean conexiuneNula=false;
        if(conn==null)
        {conn=Utilitati.getConexiune();
         conexiuneNula=true;
        }
        PreparedStatement stmt=null;
       try{
           stmt=conn.prepareStatement("select * from reparatii where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codrepar=rs.getBigDecimal("Codrepar");
               this.codcl=rs.getBigDecimal("codcl");
               this.datarepar=rs.getTimestamp("datarepar");
               this.valtotalar=rs.getBigDecimal("valtotalar");
               
           }
           else //inregistrarea nu mai este in bd
               throw new Exception ("inregistrarea nu este in bd");
           this.stare=SINCRONIZAT;
           stmt.close();
           if(conexiuneNula) //daca nu am primit o conexiune o inchid
               conn.close();
       }
       catch(Exception e){
           if (stmt!=null) 
               stmt.close();
           if (conexiuneNula) // 
               conn.close();
           throw e;//trimitte erroarea blocului apelant
       }
    }
    
    public static ArrayList getObjects(Connection conn,String whereFiltru) throws Exception{
        boolean conexiuneNula=false;
       if(conn==null) 
       {
           conn=Utilitati.getConexiune();
           conexiuneNula=true;
       }
        String frazaSelect="Select rowid,reparatii.* from reparatii";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Reparatii rp;
          while (rs.next())
          {
              rp=new Reparatii(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("codrepar"),
              rs.getBigDecimal("codcl"),
              rs.getTimestamp("datarepar"), 
              rs.getBigDecimal("valtotalar")
              );
              rp.stare=SINCRONIZAT;
              listaObiecte.add(rp);
          }
          stmt.close();
      }
      catch(Exception e) {
          if (stmt!=null) 
                    stmt.close();
                    if (conexiuneNula)//daca nu am primt conexiune o inchid
                        conn.close();
      throw e;
       }
      if (conexiuneNula) // daca nu am primit conexiune o inhid
          conn.close();
          return listaObiecte;
}
public String toString(){
    return String.valueOf(this.getCodrepar());
}
}