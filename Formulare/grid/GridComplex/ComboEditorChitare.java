/*
 * ComboEditorCompartiment.java
 *
 * Created on June 28, 2004, 7:04 PM
 */

package Formulare.grid.GridComplex;

import javax.swing.*;
import javax.swing.JComboBox.*;
import javax.swing.DefaultCellEditor;
import Sursa.*;
import java.math.BigDecimal;
/**
 *
 * @author alex
 */

public class ComboEditorChitare extends javax.swing.DefaultCellEditor{
    
    private javax.swing.JComboBox cboChitare;
    public ComboEditorChitare(javax.swing.JComboBox cbo) {
        super(cbo);
        Chitare prodNull=new Chitare();
        prodNull.setCodchitara(null);
        prodNull.setFirma("necunoscuta");
        cbo.addItem(prodNull);
        this.cboChitare=cbo;       
        
    }
    public Object getCellEditorValue()
    {
    return((Chitare)this.cboChitare.getSelectedItem()).getCodchitara();
    }
    
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table,Object value,boolean isSelected,int row,int column)
    
    {String codchitaraCellCurenta=(String)value;
     
     if(codchitaraCellCurenta==null)
     {cboChitare.setSelectedIndex(cboChitare.getItemCount()-1);
      return cboChitare;
     }
     for (int i=0;i<cboChitare.getItemCount();i++)
     {Chitare obj=(Chitare)cboChitare.getItemAt(i);
      if(obj.getCodchitara().equals(codchitaraCellCurenta))
      {cboChitare.setSelectedIndex(i);
       break;
      }
     }
     return cboChitare;
    }
}

