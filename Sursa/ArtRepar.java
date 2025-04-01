 /*
 * ArtReparatie.java
 *
 * Created on 28 iunie 2004, 16:46
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
/**
 *
 * @author  alex
 */
public class ArtRepar{
    
    /** Creates a new instance of reparatii */
 
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    protected BigDecimal codrepar;
    public BigDecimal cantitate;
    public String codchitara;
    public BigDecimal codcompo;
    public BigDecimal grad_defect;
    public BigDecimal pretreparatie;
        
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    
    public java.math.BigDecimal getCodrepar(){return this.codrepar;}
    public java.lang.String getCodchitara(){return this.codchitara;}
    public java.math.BigDecimal getCodcompo(){return this.codcompo;}
    public java.math.BigDecimal getCantitate(){return this.cantitate;}
    public java.math.BigDecimal getGrad_defect(){return this.grad_defect;}
    public java.math.BigDecimal getPretreparatie(){return this.pretreparatie;}
    
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    
    public void setCodrepar(java.math.BigDecimal valNoua){
        if (valNoua==null ? codrepar!=valNoua : !valNoua.equals(codrepar))
        {
        this.codrepar=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setCantitate(java.math.BigDecimal valNoua){
        if (valNoua==null ? cantitate!=valNoua : !valNoua.equals(cantitate))
        {
        this.cantitate=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setCodchitara(java.lang.String valNoua){
        if (valNoua==null ? codchitara!=valNoua : !valNoua.equals(codchitara))
        {
        this.codchitara=valNoua;
        this.setStare(MODIFICAT);
        }
    }
   public void setCodcompo(java.math.BigDecimal valNoua){
        if (valNoua==null ? codcompo!=valNoua : !valNoua.equals(codcompo))
        {
        this.codcompo=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setGrad_defect(java.math.BigDecimal valNoua){
        if (valNoua==null ? grad_defect!=valNoua : !valNoua.equals(grad_defect))
        {
            this.grad_defect=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setPretreparatie(java.math.BigDecimal valNoua){
        if (valNoua==null ? pretreparatie!=valNoua : !valNoua.equals(pretreparatie))
        {
            this.pretreparatie=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    
    public ArtRepar() {
        this.stare=NOU; 
    }
    public ArtRepar(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodrepar,String pCodchitara,
                   BigDecimal pCodcompo,BigDecimal pCantitate
                    ,BigDecimal pGrad_defect,BigDecimal pPretreparatie)
    {
        this();
        this.cantitate=pCantitate;
        this.codchitara=pCodchitara;
        this.codcompo=pCodcompo;
        this.codrepar=pCodrepar;
        this.grad_defect=pGrad_defect;
        this.pretreparatie=pPretreparatie;
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
 stmt=conn.prepareStatement("insert into artrepar"+"(codrepar,codchitara,codcompo,cantitate,grad_defect,pretreparatie)"+
          "values(?,?,?,?,?,?)");
       stmt.setBigDecimal(1, this.codrepar);stmt.setString(2,this.codchitara);
       stmt.setBigDecimal(3,this.codcompo);stmt.setBigDecimal(4, this.cantitate);
       stmt.setBigDecimal(5,this.grad_defect); stmt.setBigDecimal(6,this.pretreparatie);
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from artrepar where codrepar="+this.codrepar.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update artrepar set "+"codrepar=?,codchitara=?,codcompo=?,cantitate=?,grad_defect=?,pretreparatie=? where rowid=?");
          stmt.setBigDecimal(1, this.codrepar);stmt.setString(2, this.codchitara);
          stmt.setBigDecimal(3, this.codcompo);stmt.setBigDecimal(4, this.cantitate);
          stmt.setBigDecimal(5,this.grad_defect);stmt.setBigDecimal(6,this.pretreparatie);
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(7,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from artrepar where rowid=?");
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
           stmt=conn.prepareStatement("select * from artrepar where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.codrepar=rs.getBigDecimal("codrepar");
               this.codchitara=rs.getString("codchitara");
               this.codrepar=rs.getBigDecimal("codcompo");
               this.cantitate=rs.getBigDecimal("cantitate");
               this.grad_defect=rs.getBigDecimal("grad_defect");
               this.pretreparatie=rs.getBigDecimal("pretreparatie");
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
        String frazaSelect="Select rowid,artrepar.* from artrepar";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          ArtRepar ar;
          while (rs.next())
          {
              ar=new ArtRepar(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("codrepar"),
              rs.getString("codchitara"),
              rs.getBigDecimal("codcompo"),
              rs.getBigDecimal("cantitate"),
              rs.getBigDecimal("grad_defect"), 
              rs.getBigDecimal("pretreparatie")
              );
              ar.stare=SINCRONIZAT;
              listaObiecte.add(ar);
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
