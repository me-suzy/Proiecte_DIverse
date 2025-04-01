/*
 * ComboRendererChitare.java
 *
 * Created on June 30, 2004, 11:04 PM
 */

package Formulare.grid.GridComplex;

import Sursa.*;
import java.lang.Object;
import javax.swing.*;
import javax.swing.JComboBox;
import java.math.BigDecimal;
/**
 *
 * @author alex
 */
public class ComboRendererChitare extends javax.swing.JComboBox implements javax.swing.table.TableCellRenderer

{
      //private javax.swing.JComboBox listaret;   
          
    /** Creates a new instance of ComboRendererChitare */
    public ComboRendererChitare(Object[] listaChitara) {
        super(listaChitara);
        Chitare retNull=new Chitare();
        retNull.setCodchitara(null);
        retNull.setFirma("necunoscut");
        this.addItem(retNull);
        this.setBorder(BorderFactory.createEmptyBorder());
        
    }
    
      public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
      {if(isSelected)
       {this.setBackground(table.getSelectionBackground());
        this.setForeground(table.getSelectionForeground());
        }
       else
       {this.setBackground(table.getBackground());
        this.setForeground(table.getForeground());
       }
       if(hasFocus)
       {this.setBackground(table.getBackground());
        this.setForeground(table.getForeground());
       }
       
       String codchitaraCellCurenta=(String)value;
       
       if(codchitaraCellCurenta==null)
       {this.setSelectedIndex(this.getItemCount()-1);
        
       return this;
       }
	this.setSelectedIndex(this.getItemCount()-1);
       
        for(int i=0;i<this.getItemCount();i++)
        {Chitare obj=(Chitare)this.getItemAt(i);
         if(codchitaraCellCurenta.equals(obj.getCodchitara()))
         {this.setSelectedIndex(i);
          break;}
         }
         return this;
        }

      }

