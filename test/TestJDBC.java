/*
 * TestJDBC.java
 *
 * Created on April 5, 2004, 11:37 AM
 */

package test;
import java.sql.*;
/**
 *
 * @author  alex
 */
public class TestJDBC {
   static void afiseazacursor(ResultSet cursor) throws SQLException{
         cursor.beforeFirst();
    System.out.println("codpost: "+"loc: "+"jud: ");
    while(cursor.next()){
        System.out.println(cursor.getString("codpost")+" "+cursor.getString("loc")+" "+cursor.getString("jud"));
    
    }
  }  
                /** Creates a new instance of TestJDBC */
     public static void main(String[] args) throws SQLException{
DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
Connection conn=utilitati.Utilitati.getConexiune();
  // 1) Gestiunea conexiunii 
  //2) Gestiunea Tranzactiilor 
  //3) Dialogul cu BD (SQL, Resultset(cursoare)) 
  //4) Gestiunea exceptiilor din BD (sql exception)
conn.setAutoCommit(false); //se comite doar in program    
Statement stm= conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
     // ResultSet.type_forword_only (parcurge cusorul numai inante)
     // Resultset.type_scroll_sensitive(in ambele sensuri)
     ResultSet cursor = stm.executeQuery("SELECT l.* FROM localitati l");
   afiseazacursor(cursor);
  /* cursor.first();
  String nume=cursor.getString("numepren")+" M";
  cursor.updateString("numepren",nume);
  cursor.updateRow();
  afiseazacursor(cursor);
  conn.rollback();
  afiseazacursor(cursor);
    cursor.close();
    stm.close();
    conn.close(); */
  Statement stm2= conn.createStatement();
  stm2.execute("update localitati set loc='Ljjhk' Where codpost=5467");
  afiseazacursor(cursor);
  //conn.rollback();
   //afiseazacursor(cursor);
    cursor.close();
    stm.close();
    conn.close(); 
   
     }
  
}
