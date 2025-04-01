/*
 * Componente.java
 *
 * Created on 28 iunie 2004, 17:09
 */

package Sursa;

/**
 *
 * @author  alex
 */

import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import utilitati.Utilitati;

public class Componente {
    
    /** Creates a new instance of personal */
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    protected BigDecimal codcompo;
    public String numecompo;
    public String tip_compo;
    public BigDecimal pretcompo;
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.math.BigDecimal getCodcompo(){return this.codcompo;}
    public java.lang.String getNumecompo(){return this.numecompo;}
    public java.lang.String getTip_compo(){return this.tip_compo;}
    public java.math.BigDecimal getpretcompo(){return this.pretcompo;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodcompo(java.math.BigDecimal valNoua){
       if (valNoua==null ? codcompo!=valNoua : !valNoua.equals(codcompo))
       { this.codcompo=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setNumecompo(java.lang.String valNoua){
        if (valNoua==null ? numecompo!=valNoua : !valNoua.equals(numecompo))
        {
        this.numecompo=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setTip_compo(java.lang.String valNoua){
        if (valNoua==null ? tip_compo!=valNoua : !valNoua.equals(tip_compo))
        {
            this.tip_compo=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setPretcompo(java.math.BigDecimal valNoua){
        if (valNoua==null ? pretcompo!=valNoua : !valNoua.equals(pretcompo))
        {
            this.pretcompo=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Componente() {
        this.stare=NOU; 
    }
    public Componente(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodcompo,String pNumecompo,
                    String pTip_compo,BigDecimal pPretcompo)
    {
        this.idLinieBD=pIdlinieBD;
        this.codcompo=pCodcompo;
        this.numecompo=pNumecompo;
        this.tip_compo=pTip_compo;
        this.pretcompo=pPretcompo;
        
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
          stmt=conn.prepareStatement("insert into componente"+"(codcompo,numecompo,tip_compo,pretcompo)"+
          "values(?,?,?,?)");
       stmt.setBigDecimal(1, this.codcompo); stmt.setString(2, this.numecompo);
       stmt.setString(3,this.tip_compo);stmt.setBigDecimal(4, this.pretcompo); 
       
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from componente where codcompo="+this.codcompo.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update componente set "+"codcompo=?,numecompo=?,tip_compo=?,pretcompo=? where rowid=?");
          stmt.setBigDecimal(1, this.codcompo); stmt.setString(2, this.numecompo);
          stmt.setString(3,this.tip_compo);stmt.setBigDecimal(4, this.pretcompo);
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from componente where rowid=?");
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
           stmt=conn.prepareStatement("select * from componente where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codcompo=rs.getBigDecimal("codcompo");
               this.numecompo=rs.getString("numecompo");
               this.tip_compo=rs.getString("tip_compo");
               this.pretcompo=rs.getBigDecimal("pretcompo");
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
        String frazaSelect="Select rowid,componente.* from componente";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Componente co;
          while (rs.next())
          {
              co=new Componente(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("codcompo"),
              rs.getString("numecompo"),
              rs.getString("tip_compo"), 
              rs.getBigDecimal("pretcompo")
              );
              co.stare=SINCRONIZAT;
              listaObiecte.add(co);
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
    return this.getNumecompo();
}
}