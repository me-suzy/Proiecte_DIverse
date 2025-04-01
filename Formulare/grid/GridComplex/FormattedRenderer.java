/*
 * ComboRendererChitare.java
 *
 * Created on June 30, 2004, 11:04 PM
 */

package Formulare.grid.GridComplex;
/**
 *
 * @author alex
 */
public class FormattedRenderer extends javax.swing.JFormattedTextField implements javax.swing.table.TableCellRenderer{
    public FormattedRenderer(java.text.Format format){
        super(format);
        this.setHorizontalAlignment(javax.swing.JFormattedTextField.RIGHT);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         }
 //optional   
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, 
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (isSelected){
           this.setBackground(table.getSelectionBackground());
           this.setForeground(table.getSelectionForeground());  
      }else{
           this.setBackground(table.getBackground());
           this.setForeground(table.getForeground());  
      }
      if (hasFocus){
          this.setBackground(table.getBackground());
          this.setForeground(table.getForeground());  
      }
  //pana aici   
       this.setValue(value);  
       return this;
    }    
}
