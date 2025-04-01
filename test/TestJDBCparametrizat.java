/*
 * TestJDBCparametrizat.java
 *
 * Created on April 26, 2004, 10:30 AM
 */

package test;
import java.sql.*;
/**
 *
 * @author  el010212
 */
public class TestJDBCparametrizat {
    
    /** Creates a new instance of TestJDBCparametrizat */
    public TestJDBCparametrizat() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        Connection conn=utilitati.Utilitati.getConexiune();
       conn.setAutoCommit(false);
       PreparedStatement pstm1= conn.prepareStatement("UPDATE CLIENTI SET NUMEPREN=? WHERE CODCL=?");
       Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet cursor=stm.executeQuery("SELECT * FROM CLIENTI");
 while(cursor.next())
        System.out.println("CODCL "+cursor.getString("CODCL")+"NUMEPREN "+cursor.getString("numepren"));
pstm1.setString(1,"CLIENT 10002 MODF3");
pstm1.setInt(2,10002);
pstm1.execute();
cursor=stm.executeQuery("select * from clienti");
 while(cursor.next())
        System.out.println("CODCL "+" "+cursor.getString("c" +
        "odcl")+" "+"NUMEPREN "+cursor.getString("numepren"));
conn.rollback();
 while(cursor.next())
         System.out.println("CODCL "+" "+cursor.getString("c" +
         "odcl")+" "+"NUMEPREN "+cursor.getString("numepren"));
conn.close();

    }

}
