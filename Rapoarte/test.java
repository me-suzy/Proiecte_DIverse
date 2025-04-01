/*
 * test.java
 *
 * Created on 02 iulie 2004, 20:01
 */

package Rapoarte;

/**
 *
 * @author  alex
 */
public class test {
    
    /** Creates a new instance of test */
    public test() {
     

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          try {
    /*Citesc in mediul Java fiºierul .jasper care conþine raportul compilat*/
    java.io.InputStream report = new java.io.FileInputStream(
                "D:\\Working\\Professional\\POO_2004\\RapoarteJava\\R1.jasper");
    /*Completez raportul compilat cu datele din sursa JDBC ºi colecþia de parametri*/
    dori.jasper.engine.JasperPrint printReport = 
    dori.jasper.engine.JasperFillManager.fillReport(report, null,conn);
    /*Previzualizare raport*/
    dori.jasper.view.JasperViewer.viewReport(printReport, false);
    }catch(Exception e){e.printStackTrace();}






    }
    
}
