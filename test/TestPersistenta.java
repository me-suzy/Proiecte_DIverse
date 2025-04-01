/*
 * TestPersistenta.java
 *
 * Created on May 3, 2004, 10:20 AM
 */

package test;
import utilitati.Utilitati;
import java.sql.*;
import java.util.*;
/**
 *
 * @author  el010212
 */
public class TestPersistenta {
    
    
    /** Creates a new instance of TestPersistenta */
    public TestPersistenta() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception{
        // TODO code application logic here
       Connection conn=Utilitati.getConexiune();
       //instantierea unui salariat
       conn.setAutoCommit(false);
       Sursa.Clienti client= new Sursa.Clienti();
       client.setCodcl(new java.math.BigDecimal(5555));
       client.setNumepren("CLIENT");
       //java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("dd/MM/yyyy");
       //java.util.Date data=format.parse("03/05/2004");
       client.setAdresa("Sfhgfdhgfd");
       client.setCodpost("5467");
       client.setTelefon(new java.math.BigDecimal(464654));
       client.setEmail("hjhgfjg@gfdgf");
       client.setSold(new java.math.BigDecimal(0));
       client.salveaza(conn);
       List clienti=Sursa.Clienti.getObjects(conn,null);
       Iterator itr=clienti.iterator();
       while (itr.hasNext())
           System.out.println(itr.next());
       conn.close();
       
    }
    
    
    
}
