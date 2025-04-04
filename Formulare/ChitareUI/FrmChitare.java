/*
 * FrmChitare.java
 *
 * Created on 01 iulie 2004, 00:34
 */

package Formulare.ChitareUI;

import Sursa.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.text.*;
import javax.swing.text.*;
import java.math.BigDecimal;
import utilitati.*;
import Formulare.interfata.*;
/**
 *
 * @author  alex
 */
public class FrmChitare extends javax.swing.JFrame {
      Connection conn;
 private String[] tip_chitare = {"Chitare","Bass"};
    /** Creates new form FrmChitare */
    public FrmChitare() {
        initComponents();
        ModelJTableChitare modelDate=new ModelJTableChitare();
        this.GrdChitare.setModel(modelDate);
        this.conn=modelDate.conexiune;
     
        ListSelectionModel modelSelectieGrid=this.GrdChitare.getSelectionModel();
        modelSelectieGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelSelectieGrid.addListSelectionListener(new ListenerSelectiiGrid());
        this.setLocation(280,280);
    }
     
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        GrdChitare = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnSterg = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnAbandon = new javax.swing.JButton();
        btnRefreshAll = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(228, 232, 240));
        GrdChitare.setBackground(new java.awt.Color(239, 240, 249));
        GrdChitare.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(GrdChitare);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 540, 130));

        btnAdd.setBackground(new java.awt.Color(153, 204, 255));
        btnAdd.setText("Adauga");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        btnSterg.setBackground(new java.awt.Color(153, 204, 255));
        btnSterg.setText("Sterge");
        btnSterg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStergActionPerformed(evt);
            }
        });

        getContentPane().add(btnSterg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 100, -1));

        btnOk.setBackground(new java.awt.Color(153, 204, 255));
        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        getContentPane().add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 70, -1));

        btnAbandon.setBackground(new java.awt.Color(153, 204, 255));
        btnAbandon.setText("Abandon\n");
        btnAbandon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbandonActionPerformed(evt);
            }
        });

        getContentPane().add(btnAbandon, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, -1));

        btnRefreshAll.setBackground(new java.awt.Color(0, 153, 204));
        btnRefreshAll.setText("Refresh");
        btnRefreshAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshAllActionPerformed(evt);
            }
        });

        getContentPane().add(btnRefreshAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jToggleButton1.setBackground(new java.awt.Color(255, 153, 0));
        jToggleButton1.setText("Home ");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poze/chitare/ibanez_steve.jpg")));
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 140, 100));

        pack();
    }//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
       new FrmPrincipal().show();
       this.hide();
    }//GEN-LAST:event_jToggleButton1ActionPerformed
  public Chitare getObiectLinieCurenta(){
        int linieCurenta=this.GrdChitare.getSelectedRow();
        ModelJTableChitare modelDate=(ModelJTableChitare)GrdChitare.getModel();
        return(Chitare)modelDate.listaChitare.get(linieCurenta);
    }
  public void anuleazaModificari(Chitare objChitare){
        //daca e nou il sterg
        if(objChitare.getStare()==Chitare.NOU)
        {((ModelJTableChitare)GrdChitare.getModel()).listaChitare.remove(objChitare);}
        else//daca e modificat reinteroghez sursa cu refresh
            try{
                objChitare.refresh(FrmChitare.this.conn);
            }
            catch(Exception e){
                Utilitati.tratareErori(e);
            }
        GrdChitare.revalidate();
        GrdChitare.repaint();
    }
    private void btnRefreshAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshAllActionPerformed
        // TODO add your handling code here:
        if(GrdChitare.isEditing())
            GrdChitare.getCellEditor(
            GrdChitare.getEditingRow(),GrdChitare.getEditingColumn()).stopCellEditing();
        try{
            java.util.ArrayList listaNew=Chitare.getObjects(this.conn,"order by codchitara");
            ((ModelJTableChitare)GrdChitare.getModel()).listaChitare=listaNew;
        }
        catch(Exception e){
            Utilitati.tratareErori(e);
        }
        this.GrdChitare.revalidate();
        this.GrdChitare.repaint();
    }//GEN-LAST:event_btnRefreshAllActionPerformed

    private void btnStergActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStergActionPerformed
        // TODO add your handling code here:
        if(GrdChitare.isEditing())
            GrdChitare.getCellEditor(
            GrdChitare.getEditingRow(),GrdChitare.getEditingColumn()).stopCellEditing();
         int linieCurenta=GrdChitare.getSelectedRow();
         Chitare objChitare=getObiectLinieCurenta();
         objChitare.setStare(Chitare.STERS);
         try{
             objChitare.salveaza(this.conn);
             //il scot si din arraylist
             ((ModelJTableChitare)GrdChitare.getModel()).listaChitare.remove(objChitare);
             GrdChitare.revalidate();
             GrdChitare.repaint();
            // selectam linia anterioara celei sterse
             GrdChitare.setRowSelectionInterval(linieCurenta-1,linieCurenta-1);
             //barele de scroll
             GrdChitare.scrollRectToVisible(GrdChitare.getCellRect(linieCurenta-1,0,true));
             
         }
         catch(Exception e){
             Utilitati.tratareErori(e);
         }
             
         
        
    }//GEN-LAST:event_btnStergActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        if(GrdChitare.isEditing())
            GrdChitare.getCellEditor(
            GrdChitare.getEditingRow(),GrdChitare.getEditingColumn()).stopCellEditing();
        try{
           Chitare objChitare=getObiectLinieCurenta();
           objChitare.salveaza(this.conn);
        }
        catch(Exception e){
            Utilitati.tratareErori(e);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnAbandonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbandonActionPerformed
        // TODO add your handling code here:
        if(GrdChitare.isEditing())
            GrdChitare.getCellEditor(
            GrdChitare.getEditingRow(),GrdChitare.getEditingColumn()).stopCellEditing();
        
         int raspuns=JOptionPane.showConfirmDialog(FrmChitare.this, 
          "Salvati modificarile efectuate pe linia curenta",
          "Confirma Actualizari",JOptionPane.YES_NO_OPTION);
          if(raspuns==JOptionPane.YES_OPTION)
          {Chitare objChitare=this.getObiectLinieCurenta();
           anuleazaModificari(objChitare);}
    }//GEN-LAST:event_btnAbandonActionPerformed

   
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if(GrdChitare.isEditing())
            GrdChitare.getCellEditor(
            GrdChitare.getEditingRow(),GrdChitare.getEditingColumn()).stopCellEditing();
      
        Chitare objChitare=new Chitare();
             ((ModelJTableChitare)GrdChitare.getModel()).listaChitare.add(objChitare);
        GrdChitare.revalidate();
        GrdChitare.repaint();
         int ultimaLinie=GrdChitare.getModel().getRowCount()-1;
             GrdChitare.setRowSelectionInterval(ultimaLinie,ultimaLinie);
      //barele de scroll
             GrdChitare.scrollRectToVisible(GrdChitare.getCellRect(ultimaLinie,0,true));
    }//GEN-LAST:event_btnAddActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
       try{
           conn.close();}
       catch(Exception e){
           System.exit(0);}
       System.exit(0);
       
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new FrmChitare().show();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GrdChitare;
    private javax.swing.JButton btnAbandon;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnRefreshAll;
    private javax.swing.JButton btnSterg;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
   
 class ListenerSelectiiGrid implements javax.swing.event.ListSelectionListener{
   public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
         if(evt.getValueIsAdjusting()==false)//nu este in miscare
         {//salvez modificarile liniei selectate anterior
            //trebuie sa aflu linia anterioara selectiei
             
      int linieCurenta=GrdChitare.getSelectedRow();
      int linieAnterioara=0;
      if(evt.getLastIndex()==linieCurenta)
          linieAnterioara=evt.getFirstIndex();
      else 
          linieAnterioara=evt.getLastIndex();
    //extrag obiectul corespunzator liniei ce trebuie salvate in bd
      ModelJTableChitare modelDate=(ModelJTableChitare)GrdChitare.getModel();
      Chitare objChitare=(Chitare)modelDate.listaChitare.get(linieAnterioara);
      if (objChitare.getStare()!=Chitare.SINCRONIZAT)
      {
          int raspuns=JOptionPane.showConfirmDialog(FrmChitare.this, 
          "Salvati modificarile efectuate pe linia curenta",
          "Confirma Actualizari",JOptionPane.YES_NO_OPTION);
          if(raspuns==JOptionPane.YES_OPTION)
              try{
                  objChitare.salveaza(FrmChitare.this.conn);
             }
              catch(Exception e){//erori la salvare
                   Utilitati.tratareErori(e);
                   GrdChitare.setRowSelectionInterval(linieAnterioara,linieAnterioara);
             }
          else //nu doreste sa salveze
          try{
              anuleazaModificari(objChitare);
          }
          catch(Exception e){
              Utilitati.tratareErori(e);
          }
              
      }
         }
       
   }
 }       

}