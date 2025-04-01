
/*
 * Utilitati.java
 *
 * Created on April 26, 2004, 10:11 AM
 */

package utilitati;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author alex
 */
public class Utilitati {
    public static String user="alex";
    public static String password=JOptionPane.showInputDialog("Parola:");
    public static String service="127.0.0.1:1521:Oracle";
    private static Connection conexiune;
    /** Creates a new instance of Utilitati */
    public Utilitati() {
        
    }
    public static java.util.Date getDataFromDMY(int zi,int luna,int an){
    // obtin un ob. Calendar cu data curenta    
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.setLenient(false);  // obligatoriu pentru validarea datei 
        java.util.Date dataValidata=null;
        try{
            cal.set( java.util.Calendar.DATE,zi);
            cal.set( java.util.Calendar.MONTH,luna-1); // numerotarea lunilor incepe cu 0(zero)
            cal.set( java.util.Calendar.YEAR, an);
            dataValidata=cal.getTime();  
           }
           // va genera eroare IllegalArg... daca data nu se incadreaza in calendar
        catch(IllegalArgumentException e){
             javax.swing.JOptionPane.showMessageDialog(null,"nu pot construi data din:"+
                                                            zi+"/"+luna+"/"+an,
                                                            "Data invalida !",
                                                            javax.swing.JOptionPane.ERROR_MESSAGE);
             }
        return dataValidata;
    
    }
    public static java.util.Date getDataFromDMY(int zi,int luna,int an,int ora, int min, int sec, String am_pm){
    // obtin un ob. Calendar cu data curenta    
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.setLenient(false);  // obligatoriu pentru validarea datei 
        java.util.Date dataValidata=null;
        try{
            cal.set( java.util.Calendar.DATE,zi);
            cal.set( java.util.Calendar.MONTH,luna-1); // numerotarea lunilor incepe cu 0(zero)
            cal.set( java.util.Calendar.YEAR, an);
            cal.set( java.util.Calendar.HOUR, ora);
            cal.set( java.util.Calendar.MINUTE,min);
            cal.set( java.util.Calendar.SECOND,sec);
            if (am_pm.equalsIgnoreCase("AM"))
                cal.set( java.util.Calendar.AM_PM,java.util.Calendar.AM);
            else
                cal.set( java.util.Calendar.AM_PM,java.util.Calendar.PM);
            dataValidata=cal.getTime();  
           }
           // va genera eroare IllegalArg... daca data nu se incadreaza in calendar
        catch(IllegalArgumentException e){
             javax.swing.JOptionPane.showMessageDialog(null,"nu pot construi data din:"+
                                                            zi+"/"+luna+"/"+an+
                                                            ":"+ora+":"+min+":"+sec+":"+am_pm,
                                                            "Data invalida !",
                                                            javax.swing.JOptionPane.ERROR_MESSAGE);
             }
        return dataValidata;
    
    }
    public static Connection getConexiune() throws SQLException{
    if (conexiune==null) {
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     conexiune=DriverManager.getConnection("jdbc:oracle:thin:@"+service,user,password);
    }
    return conexiune;
    }
     public static void tratareErori(Exception err){
         if (err instanceof SQLException)
           {   SQLException errSQL=(SQLException)err;
                javax.swing.JOptionPane.showMessageDialog(null,
                        errSQL.getErrorCode()+":"+err.getMessage(),
                         "Eroare SQL!",javax.swing.JOptionPane.ERROR_MESSAGE);
            }
         else if (err instanceof NumberFormatException)
         { javax.swing.JOptionPane.showMessageDialog(null,
                        "Sirul de char. introdus nu corespunde unui numar zecimal !",
                         "Acest camp accepta doar valori numerice.",javax.swing.JOptionPane.ERROR_MESSAGE);
         }
         else if (err instanceof java.text.ParseException)
         { javax.swing.JOptionPane.showMessageDialog(null,
                        "Sirul de char. introdus nu corespunde unei date calend. valide !",
                         "Acest camp accepta doar valori in format dd/mm/yyyy.",javax.swing.JOptionPane.ERROR_MESSAGE);
         }
         else
            javax.swing.JOptionPane.showMessageDialog(null,err.getMessage(),
                         "Eroare Non-SQL!",javax.swing.JOptionPane.ERROR_MESSAGE);
           
    err.printStackTrace();
    }

}

