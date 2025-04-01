
package Formulare.grid.pontaje;
import javax.swing.*;
import java.sql.*;
 class FormattedEditor extends javax.swing.DefaultCellEditor {
                       
    private javax.swing.JFormattedTextField txt;  
    public FormattedEditor(java.text.Format format) {
         super(new JFormattedTextField(format));  // apelez constructorul implicit al clasei DefaultCellEditor
         
	//	this.tx=txt;
        // txtDatasv.setHorizontalAlignment(JFormattedTextField.RIGHT);
         
    }
   public Object getCellEditorValue(){
          
       Object valNoua=null;
        JFormattedTextField.AbstractFormatter formatter = ((JFormattedTextField)this.getComponent()).getFormatter();
          if (formatter != null) {
                 String text = ((JFormattedTextField)this.getComponent()).getText();
                 if (text!=null&&!text.equals(""))
                     try { // incerc un parsing daca text!=null
                         valNoua=formatter.stringToValue(text);
                        // System.out.println(valNoua);   
                           } 
		     catch (java.text.ParseException e) { //textul nu corespunde unei date valide
                         JOptionPane.showMessageDialog(null,"Format invalid");
                         
			 }
              }
      // valNoua este de tip Date din pricina formatterului SimpleDateFormat
      if (formatter instanceof javax.swing.text.DateFormatter)
        return new Timestamp(((java.util.Date)valNoua).getTime());
      else
         return new java.math.BigDecimal(valNoua.toString()); 
      }
   public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, 
              Object value, boolean isSelected, int row, int column) {
      if (isSelected){
           ((JFormattedTextField)this.getComponent()).setBackground(table.getBackground());
           ((JFormattedTextField)this.getComponent()).setForeground(table.getForeground());  
      }
      ((JFormattedTextField)this.getComponent()).setValue(value);
      return this.getComponent();
      
    }
 
}
