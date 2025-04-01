/*
 * Clienti.java
 *
 * Created on 27 iunie 2004, 22:21
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

public class Clienti {
    
    /** Creates a new instance of personal */
     public static Connection conn;
     
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public BigDecimal codcl;
    public String numepren;
    public String adresa;
    public String codpost;
    public BigDecimal telefon;
    public String email;
   
    
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.math.BigDecimal getCodcl(){return this.codcl;}
    public java.lang.String getNumepren(){return this.numepren;}
    public java.lang.String getAdresa(){return this.adresa;}
    public java.lang.String getCodpost(){return this.codpost;}
    public java.math.BigDecimal getTelefon(){return this.telefon;}
    public java.lang.String getEmail(){return this.email;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodcl(java.math.BigDecimal valNoua){
       if (valNoua==null ? codcl!=valNoua : !valNoua.equals(codcl))
       { this.codcl=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setNumepren(java.lang.String valNoua){
        if (valNoua==null ? numepren!=valNoua : !valNoua.equals(numepren))
        {
        this.numepren=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setAdresa(java.lang.String valNoua){
        if (valNoua==null ? adresa!=valNoua : !valNoua.equals(adresa))
        {
            this.adresa=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setCodpost(java.lang.String valNoua){
        if (valNoua==null ? codpost!=valNoua : !valNoua.equals(codpost))
        {
            this.codpost=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setTelefon(java.math.BigDecimal valNoua){
        if (valNoua==null ? telefon!=valNoua : !valNoua.equals(telefon))
        {
            this.telefon=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setEmail(java.lang.String valNoua){
        if (valNoua==null ? email!=valNoua : !valNoua.equals(email))
        {
            this.email=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Clienti() {
        this.stare=NOU; 
    }
    public Clienti(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodcl,String pNumepren,
                    String pAdresa,String pCodpost,BigDecimal pTelefon,String pEmail)
    {
        this.idLinieBD=pIdlinieBD;
        this.codcl=pCodcl;
        this.numepren=pNumepren;
        this.adresa=pAdresa;
        this.codpost=pCodpost;
        this.telefon=pTelefon;
        this.email=pEmail;
    
    }
    public int  getStare(){return this.stare;}
    
    public void setStare(int stareNoua){
       if (this.stare==NOU && stareNoua==MODIFICAT)
           return;
       else
           this.stare=stareNoua;
    }
    public void salveaza(Connection conn) throws Exception{
      if (this.stare==SINCRONIZAT)
         return;
       
      boolean conexiuneNula=false;
      if (conn==null)
         {conn=utilitati.Utilitati.getConexiune();
          conexiuneNula=true;
         }  
      PreparedStatement stmt1=null;
      Statement stmtRowid1=null;
    try{  
      if (this.stare==NOU){
            stmt1=conn.prepareStatement("insert into clienti" +
                            "(codcl,numepren,adresa,codpost,telefon,email) "+
                            " values (?,?,?,?,?,?)");
            stmt1.setBigDecimal(1,this.codcl);stmt1.setString(2,this.numepren);
            stmt1.setString(4, this.codpost);stmt1.setString(3, this.adresa);
            stmt1.setBigDecimal(5,this.telefon);stmt1.setString(6, this.email);
            stmt1.execute();
            // trebuie sa obtinem rowid-ul noii inregistrari
             stmtRowid1=conn.createStatement();
            oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
               stmtRowid1.executeQuery("select rowid from clienti where codcl="+
                                                this.codcl.toString());
            rs.next();
            this.idLinieBD=rs.getROWID(1);
            rs.close();
            this.stare=SINCRONIZAT;
      }
       else if (this.stare==MODIFICAT){
            stmt1=conn.prepareStatement("update clienti set "+
                  "codcl=?,numepren=?,codpost=?,adresa=?,telefon=?,email=? where rowid=?");
                                                    
            stmt1.setBigDecimal(1,this.codcl);stmt1.setString(2,this.numepren);
            stmt1.setString(3,this.codpost);stmt1.setString(4, this.adresa);
            stmt1.setBigDecimal(4,this.telefon);stmt1.setString(6, this.email);
                       
           // pentru setROWID trebuie downcasting 
            ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
            stmt1.execute();
             if (stmt1.getUpdateCount()==0)
               throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
            this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS){
            stmt1=conn.prepareStatement("delete from clienti where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(1,this.idLinieBD);
            stmt1.execute();
            this.stare=SINCRONIZAT;
           
      }    
      stmt1.close();
      
     }
      catch(Exception e){if (stmt1!=null)
                            stmt1.close();
                         if (conexiuneNula) // daca nu am primit conexiunea o inchid
                            conn.close();
                         if (stmtRowid1 !=null)
                             stmt1.close();
                          throw e;
                       }  
     if (conexiuneNula) // daca nu am primit conexiunea o inchid
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
           stmt=conn.prepareStatement("select * from clienti where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codcl=rs.getBigDecimal("codcl");
               this.numepren=rs.getString("numepren");
               this.adresa=rs.getString("adresa");
               this.codpost=rs.getString("codpost");
               this.telefon=rs.getBigDecimal("telefon");
               this.email=rs.getString("email");
          
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
        String frazaSelect="Select rowid,clienti.* from clienti";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Clienti cl;
          while (rs.next())
          {
              cl=new Clienti(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("codcl"),
              rs.getString("numepren"),
              rs.getString("adresa"), 
              rs.getString("codpost"),
              rs.getBigDecimal("telefon"),
              rs.getString("email")
              );
              cl.stare=SINCRONIZAT;
              listaObiecte.add(cl);
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
    return this.getNumepren();
}
}