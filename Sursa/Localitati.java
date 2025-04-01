/*
 * Localitati.java
 *
 * Created on 30 iunie 2004, 01:49
 */

package Sursa;

/**
 *
 * @author  alex
 */
import java.sql.*;
import java.util.ArrayList;
import java.lang.String;
import utilitati.Utilitati;

public class Localitati {
    
    /** Creates a new instance of personal */
     public static Connection conn;
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    private String codpost;
    private String loc;
    private String jud;
    
    
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    
    public java.lang.String getCodpost(){return this.codpost;}
    public java.lang.String getLoc(){return this.loc;}
    public java.lang.String getJud(){return this.jud;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
   
    public void setCodpost(java.lang.String valNoua){
        if (valNoua==null ? codpost!=valNoua : !valNoua.equals(codpost))
        {
        this.codpost=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setLoc(java.lang.String valNoua){
        if (valNoua==null ? loc!=valNoua : !valNoua.equals(loc))
        {
            this.loc=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setJud(java.lang.String valNoua){
        if (valNoua==null ? jud!=valNoua : !valNoua.equals(jud))
        {
            this.jud=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Localitati() {
        this.stare=NOU; 
    }
    public Localitati(oracle.sql.ROWID pIdlinieBD,String pCodpost,
                    String pLoc,String pJud)
    {
        this.idLinieBD=pIdlinieBD;
        this.codpost=pCodpost;
        this.loc=pLoc;
        this.jud=pJud;
    
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
          stmt=conn.prepareStatement("insert into localitati"+"(codpost,loc,jud)"+
          "values(?,?,?)");
       stmt.setString(1, this.codpost);
       stmt.setString(2,this.loc);stmt.setString(3, this.jud);
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from localitati where codpost="+this.codpost.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       stmtRowid.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update localitati set "+"codpost=?,loc=?,jud=? where rowid=?");
          stmt.setString(1, this.codpost);stmt.setString(2,this.loc);
          stmt.setString(3, this.jud);
          
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from localitati where rowid=?");
       ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1,this.idLinieBD);
       stmt.execute();
       this.stare=SINCRONIZAT;
      }
    //  stmt.close();
      }
    /*catch(Exception e){if (stmt!=null)
        //e.printStackTrace();
                        stmt.close();
                       if (conexiuneNula) // daca nu am primit conexiune o inchid}
                           conn.close();
                         if(stmtRowid !=null) 
                             stmt.close();
                       throw e;
    } */
        
        catch(Exception e){e.printStackTrace(); throw e;}
      finally{ 
            if (stmt!=null) stmt.close();
            if (stmtRowid!=null) stmtRowid.close();
            if (conexiuneNula) // daca nu am primit conexiunea o inchid
                 conn.close();
     }   
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
           stmt=conn.prepareStatement("select * from localitati where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codpost=rs.getString("codpost");
               this.loc=rs.getString("loc");
               this.jud=rs.getString("jud");
          
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
        String frazaSelect="Select rowid,localitati.* from localitati";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Localitati l;
          while (rs.next())
          {
              l=new Localitati(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getString("codpost"),
              rs.getString("loc"), 
              rs.getString("jud")
              );
              l.stare=SINCRONIZAT;
              listaObiecte.add(l);
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
     public static void initConnection(Connection c) throws Exception{
       
           // if c este null initializam o conexiune 
           if (c==null){
              conn=utilitati.Utilitati.getConexiune();
                      }
           else
               conn=c;
    }
public String toString(){
    return this.loc;
}
}