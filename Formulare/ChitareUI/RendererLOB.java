/*
 * RendererLOB.java
 *
 * Created on 01 iulie 2004, 22:16
 */

package Formulare.ChitareUI;
import Sursa.*;
import java.lang.Object;
import javax.swing.*;
import javax.swing.JComboBox;
import java.math.BigDecimal;

/**
 *
 * @author  alex
 */
public class RendererLOB extends javax.swing.JEditorPane 
                         implements javax.swing.table.TableCellRenderer,HyperlinkListener{
  public javax.swing.JFileChooser fc=new javax.swing.JFileChooser("D:/proiect java 2004/Proiect Alex/poze/chitare");
  private java.awt.Cursor cursorNormal;
  private oracle.sql.BLOB adresaLOBCurenta;
    
    /** Creates a new instance of RendererLOB */
    public RendererLOB() {
       super("text/html","a href=view>View</a>/ <a href=edit>Edit</a>");
       this.setBorder(BorderFactory.createEmptyBorder());
       this.addHyperlinkListener(this);
       this.setEditable(false);
   }
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if(isSelected){
        this.setBackground(table.getSelectionBackground());
        this.setForeground(table.getSelectionForeground());
    }
    else 
    {
        this.setBackground(table.getBackground());
        this.setForeground(table.getForeground());
    }
    if(hasFocus){
        this.setBackground(table.getBackground());
        this.setForeground(table.getForeground());
    }
    return this;
    
    }
    public void hyperlinkUpdate(HyperlinkEvent evt){
        if(evt.getEventType()==HyperlinkEvent.EventType.ENTERED)
        {
            this.cursorNormal=this.getCursor();
            this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        else if(evt.getEventType()==HyperlinkEvent.EventType.EXITED)
            this.setCursor(this.cursorNormal);
        else if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
        {
            String Link=evt.getDescription();
            if(Link.equals("view"))
                this.viewBLOB();
            else 
                this.loadBLOB();
        }
    }
    public void viewBLOB(){
        oracle.sql.BLOB lobLocator=this.adresaLOBCurenta;
        if(lobLocator==null)
        {JOptionPane.showMessageDialog(null,"Adresa invalida");
         return;
    }
   ///creez fisier de output local
        try{
            java.io.File=new java.io.File("d:/temp/lob.tmp");
            java.io.FileOutputStream fisierOut=new java.io.FileOutputStream(f);
            //extrag input streamul corespunzator fisierului blob si-l scriu  in temporrar
            java.io.InputStream lobStream=lobLocator.getBinaryStream();
            int bufferSize=lobLocator.getBufferSize();
            byte[] buffer=new byte[bufferSize];
            int nrBytesReaded=-1;
            while((nrBytesReaded)=lobStream.read(buffer))!=-1)
            fisierOut.write(buffer,0,nrBytesReaded);
            fisierOut.close();
            lobStream.close();
            Runtime rt=Runtime.getRuntime();
            if(f.length()==0)
                JOptionPane.showMessageDialog(null,"Fisier Inexistent");
            else
                // deschi cu internet explorer
                rt.exec("c:/Program Files/Internet Explorer/iexplore.exe d:/temp/lob.tmp";
            }
        
}
