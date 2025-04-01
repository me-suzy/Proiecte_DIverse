/*
 * Grad_defectiune.java
 *
 * Created on 28 iunie 2004, 17:10
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

public class Grad_defectiune {
    
    /** Creates a new instance of personal */
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    protected BigDecimal grad_defect;
    public String numegrad;
    public String decizie ;
    public BigDecimal procent;
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.math.BigDecimal getGrad_defect(){return this.grad_defect;}
    public java.lang.String getNumegrad(){return this.numegrad;}
    public java.lang.String getDecizie(){return this.decizie;}
    public java.math.BigDecimal getProcent(){return this.procent;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setGrad_defect(java.math.BigDecimal valNoua){
       if (valNoua==null ? grad_defect!=valNoua : !valNoua.equals(grad_defect))
       { this.grad_defect=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setNumegrad(java.lang.String valNoua){
        if (valNoua==null ? numegrad!=valNoua : !valNoua.equals(numegrad))
        {
        this.numegrad=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setDecizie(java.lang.String valNoua){
        if (valNoua==null ? decizie!=valNoua : !valNoua.equals(decizie))
        {
            this.decizie=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setProcent(java.math.BigDecimal valNoua){
        if (valNoua==null ? procent!=valNoua : !valNoua.equals(procent))
        {
            this.procent=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Grad_defectiune() {
        this.stare=NOU; 
    }
    public Grad_defectiune(oracle.sql.ROWID pIdlinieBD,BigDecimal pGrad_defect,String pNumegrad,
                    String pDecizie,BigDecimal pProcent)
    {
        this.idLinieBD=pIdlinieBD;
        this.grad_defect=pGrad_defect;
        this.numegrad=pNumegrad;
        this.decizie=pDecizie;
        this.procent=pProcent;
        
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
          stmt=conn.prepareStatement("insert into grad_defectiune"+"(grad_defect,numegrad,decizie,procent)"+
          "values(?,?,?,?)");
       stmt.setBigDecimal(1, this.grad_defect); stmt.setString(2, this.numegrad);
       stmt.setString(3,this.decizie);stmt.setBigDecimal(4, this.procent); 
       
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from grad_defectiune where grad_defect="+this.grad_defect.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update grad_defectiune set "+"grad_defect=?,numegrad=?,decizie=?,procent=? where rowid=?");
          stmt.setBigDecimal(1, this.grad_defect); stmt.setString(2, this.numegrad);
          stmt.setString(3,this.decizie);stmt.setBigDecimal(4, this.procent);
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from grad_defectiune where rowid=?");
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
           stmt=conn.prepareStatement("select * from grad_defectiune where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.grad_defect=rs.getBigDecimal("grad_defect");
               this.numegrad=rs.getString("numegrad");
               this.decizie=rs.getString("decizie");
               this.procent=rs.getBigDecimal("procent");
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
        String frazaSelect="Select rowid,grad_defectiune.* from grad_defectiune";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Grad_defectiune gd;
          while (rs.next())
          {
              gd=new Grad_defectiune(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("grad_defect"),
              rs.getString("numegrad"),
              rs.getString("decizie"), 
              rs.getBigDecimal("procent")
              );
              gd.stare=SINCRONIZAT;
              listaObiecte.add(gd);
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
    return this.getNumegrad();
}
}