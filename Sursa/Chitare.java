/*
 * Chitare.java
 *
 * Created on 28 iunie 2004, 16:45
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
import java.math.BigDecimal;
/**
 *
 * @author  alex
 */
public class Chitare{
    
    /** Creates a new instance of reparatii */
 
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
   public String codchitara;
    public String firma;
    public String tip_chitara;
    public String tip_poz;
    public String rezonanta;
    public BigDecimal nr_doze;
    public String model_doza;
    public BigDecimal nr_taste;
    public String tip_taste;
    public oracle.sql.BLOB foto;
    
    protected oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.lang.String getCodchitara(){return this.codchitara;}
    public java.lang.String getFirma(){return this.firma;}
    public java.lang.String getTip_chitara(){return this.tip_chitara;}
    public java.lang.String getTip_poz(){return this.tip_poz;}
    public java.lang.String getRezonanta(){return this.rezonanta;}
    public java.math.BigDecimal getNr_doze(){return this.nr_doze;}
    public java.lang.String getModel_doza(){return this.model_doza;}
    public java.math.BigDecimal getNr_taste(){return this.nr_taste;}
    public java.lang.String getTip_taste(){return this.tip_taste;}
    public oracle.sql.BLOB getFoto(){return this.foto;}
    
    
    
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodchitara(java.lang.String valNoua){
       if (valNoua==null ? codchitara!=valNoua : !valNoua.equals(codchitara))
       { this.codchitara=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setFirma(java.lang.String valNoua){
        if (valNoua==null ? firma!=valNoua : !valNoua.equals(firma))
        {
        this.firma=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setTip_chitara(java.lang.String valNoua){
        if (valNoua==null ? tip_chitara!=valNoua : !valNoua.equals(tip_chitara))
        {
            this.tip_chitara=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setTip_poz(java.lang.String valNoua){
        if (valNoua==null ? tip_poz!=valNoua : !valNoua.equals(tip_poz))
        {
            this.tip_poz=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setRezonanta(java.lang.String valNoua){
        if (valNoua==null ? rezonanta!=valNoua : !valNoua.equals(rezonanta))
        {
            this.rezonanta=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setNr_doze(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_doze!=valNoua : !valNoua.equals(nr_doze))
        {
            this.nr_doze=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setModel_doza(java.lang.String valNoua){
        if (valNoua==null ? model_doza!=valNoua : !valNoua.equals(model_doza))
        {
            this.model_doza=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setNr_taste(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_taste!=valNoua : !valNoua.equals(nr_taste))
        {
            this.nr_taste=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setTip_taste(java.lang.String valNoua){
        if (valNoua==null ? tip_taste!=valNoua : !valNoua.equals(tip_taste))
        {
            this.tip_taste=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setFoto(oracle.sql.BLOB valNoua){
        if (valNoua==null ? foto!=valNoua : !valNoua.equals(foto))
        {
            this.foto=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public Chitare() {
        this.stare=NOU;
        this.tip_chitara="Chitara";
        this.tip_poz="Stanga";
        this.tip_taste="Late";
        this.rezonanta="Electrica";
    }
    public Chitare(oracle.sql.ROWID pIdlinieBD,String pCodchitara,String pFirma,
                    String pTip_chitara,String pTip_poz,String pRezonanta,BigDecimal pNr_doze,String pModel_doza,
                    BigDecimal pNr_taste,String pTip_taste,oracle.sql.BLOB pFoto)
    {
        this.idLinieBD=pIdlinieBD;
        this.codchitara=pCodchitara;
        this.firma=pFirma;
        this.tip_chitara=pTip_chitara;
        this.tip_poz=pTip_poz;
        this.rezonanta=pRezonanta;
        this.nr_doze=pNr_doze;
        this.model_doza=pModel_doza;
        this.nr_taste=pNr_taste;
        this.tip_taste=pTip_taste;
        this.foto=pFoto;
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
          stmt=conn.prepareStatement("insert into chitare"+"(codchitara,firma,tip_chitara,tip_poz," +
          "rezonanta,nr_doze,model_doza,nr_taste,tip_taste)"+
          "values(?,?,?,?,?,?,?,?,?)");
       stmt.setString(1, this.codchitara); stmt.setString(2, this.firma);
       stmt.setString(3,this.tip_chitara); stmt.setString(4,this.tip_poz);
       stmt.setString(5,this.rezonanta); stmt.setBigDecimal(6,this.nr_doze);
       stmt.setString(7,this.model_doza); stmt.setBigDecimal(8,this.nr_taste);
       stmt.setString(9,this.tip_taste); 
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from chitare where codchitara="+this.codchitara);//.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update chitare set "+"codchitara=?,firma=?,tip_chitara=?,tip_poz=?," +
          "rezonanta=?,nr_doze=?,model_doza=?,nr_taste=?,tip_taste=? where rowid=?");
          stmt.setString(1, this.codchitara); stmt.setString(2, this.firma);
          stmt.setString(3,this.tip_chitara); stmt.setString(4,this.tip_poz);
          stmt.setString(5,this.rezonanta); stmt.setBigDecimal(6,this.nr_doze);
          stmt.setString(7,this.model_doza); stmt.setBigDecimal(8,this.nr_taste);
          stmt.setString(9,this.tip_taste); 
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from chitare where rowid=?");
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
           stmt=conn.prepareStatement("select * from chitare where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codchitara=rs.getString("codchitara");
               this.firma=rs.getString("firma");
               this.tip_chitara=rs.getString("tip_chitara");
               this.tip_poz=rs.getString("tip_poz");
               this.rezonanta=rs.getString("rezonanta");
                this.nr_doze=rs.getBigDecimal("nr_doze");
                this.model_doza=rs.getString("model_doza");
                this.nr_taste=rs.getBigDecimal("nr_taste");
                this.tip_taste=rs.getString("tip_taste");
                this.foto=((oracle.jdbc.OracleResultSet)rs).getBLOB("foto");
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
        String frazaSelect="Select rowid,chitare.* from chitare";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Chitare ch;
          while (rs.next())
          {
              ch=new Chitare(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getString("codchitara"),
              rs.getString("firma"),
              rs.getString("tip_chitara"), 
              rs.getString("tip_poz"),
              rs.getString("rezonanta"),
              rs.getBigDecimal("nr_doze"),
              rs.getString("model_doza"), 
              rs.getBigDecimal("nr_taste"),
              rs.getString("tip_taste"), 
              ((oracle.jdbc.OracleResultSet)rs).getBLOB("foto")
              );
              ch.stare=SINCRONIZAT;
              listaObiecte.add(ch);
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
    return this.getCodchitara();
}
}
