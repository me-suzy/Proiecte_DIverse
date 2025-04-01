/*
 * FormClienti.java
 *
 * Created on 10 May 2004, 17:52
 */

package Formulare.grid.Reparatii;
import Sursa.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.math.BigDecimal;
import java.sql.*;
import utilitati.*;

/**
 *
 * @author  Cretzanu
 */
public class FormClienti extends javax.swing.JFrame {
    Connection conn;
    /** Creates new form FormClienti */
    public FormClienti() {
        initComponents();
        formatGUIElements();
        try{
           conn=Utilitati.getConexiune();
            // modelul de date al grid-ului Reparatii:
           
           java.util.ArrayList clienti=Clienti.getObjects(Clienti.class, conn, " where codpost<>'5467' order by numepren ");
       for(int i=0;i<clienti.size();i++)
           this.cboClienti.addItem(clienti.get(i));
       java.util.ArrayList codposts=Localitati.getObjects(Localitati.class, conn, "order by loc");
       for(int i=0; i<codposts.size(); i++)
           this.cboCodpost.addItem(codposts.get(i));
        
        this.cboAn.addItem(BigDecimal.valueOf(2000));
        this.cboAn.addItem(BigDecimal.valueOf(2001));
        this.cboAn.addItem(BigDecimal.valueOf(2002));
        this.cboAn.addItem(BigDecimal.valueOf(2003));
        for (int i=1; i<=12;i++)
             this.cboLuna.addItem(BigDecimal.valueOf(i));
        
       
          }
        
        
        catch(Exception e){utilitati.Utilitati.tratareErori(e);}
    
    }
    
    private void formatGUIElements(){
       // date calendaristice:
       java.text.SimpleDateFormat formatData=new java.text.SimpleDateFormat("dd/MM/yyyy");
       formatData.setLenient(false);
       //this.txtDatasv.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(formatData)));
       // campuri numerice:
       java.text.DecimalFormat formatInt=new java.text.DecimalFormat("#");
       formatInt.setMinimumFractionDigits(0);
       formatInt.setMaximumIntegerDigits(5);
       this.txtCodcl.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatInt)));
       java.text.DecimalFormat formatNumeric=new java.text.DecimalFormat("#.#");
       formatNumeric.setMinimumFractionDigits(2);
       formatNumeric.setMaximumIntegerDigits(16);
       this.txtTelefon.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatNumeric)));
      // this.txtSalorarco.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(formatNumeric)));
       ((NumberFormatter)this.txtTelefon.getFormatter()).setAllowsInvalid(false);
       //((NumberFormatter)this.txtSalorarco.getFormatter()).setAllowsInvalid(false);
       
       // modelul gridului
       this.tblReparatii.setModel(new ModelJTableReparatii(conn));
       // rendere si editoare pentru coloanele gridului
       //coloana data:
       java.text.SimpleDateFormat formatDataRepar=new java.text.SimpleDateFormat("dd/MM/yyyy");
       formatData.setLenient(false);
       javax.swing.table.TableColumn tc=this.tblReparatii.getColumnModel().getColumn(1);
       tc.setCellEditor(new FormattedEditor(formatDataRepar));
       tc.setCellRenderer(new FormattedRenderer(formatDataRepar));
      
       // coloanele numerice:
       java.text.DecimalFormat formatOre=new java.text.DecimalFormat("#");
       formatNumeric.setMinimumFractionDigits(0);
       formatNumeric.setMaximumIntegerDigits(2);
       
       this.tblReparatii.setDefaultRenderer(BigDecimal.class, new FormattedRenderer(formatOre));
       this.tblReparatii.setDefaultEditor(BigDecimal.class, new FormattedEditor(formatOre));
       
      
    }
    private void refreshForm(Clienti objClient){
        this.txtCodcl.setValue(objClient.getCodcl());
        this.txtNumepren.setValue(objClient.getNumepren());
        if (objClient.getAdresa()!=null)
            this.txtAdresa.setValue(objClient.getAdresa());
        
        else
            this.txtAdresa.setValue(null);
        this.txtTelefon.setValue(objClient.getTelefon());
        
        
        /*boolean colab=objClient.getColaborator().equals("D") ? true : false;
        this.chkColaborator.setSelected(colab);
        for (int i=0;i<this.cboCompart.getItemCount();i++)
            if(((Compartimente) this.cboCompart.getItemAt(i)).getCompid().equals(objClient.getCompart()))
                this.cboCompart.setSelectedIndex(i);
        */
       // refresh penttu gridul de Reparatii:
        
       
        
          
    }
    private void refreshReparatii(){
     Clienti objClient=(Clienti)this.cboClienti.getSelectedItem();   
     BigDecimal an=(BigDecimal)this.cboAn.getSelectedItem();
     BigDecimal luna=(BigDecimal)this.cboLuna.getSelectedItem();
     ((ModelJTableReparatii)this.tblReparatii.getModel()).requeryModel(objClient.getCodcl(), an, luna);
     this.tblReparatii.revalidate();
     System.out.println(an+"/"+luna+"/"+objClient.getCodcl());
     this.tblReparatii.repaint();
                                                                
    
    }
    private void updateBean(Clienti objClient){
    
        objClient.setCodcl(new java.math.BigDecimal(txtMarca.getValue().toString()));
        objClient.setNumepren(txtNumepren.getValue().toString());
        objClient.setAdresa(txtAdresa.getValue().toString());
        objClient.setTelefon(new java.math.BigDecimal(txtTelefon.getValue().toString()));
        objClient.setSalorarco(new java.math.BigDecimal(txtSalorarco.getValue().toString()));
        objClient.setCodpost((Localitati)this.cboCodpost.getSelectedItem().getCodpost());
        objClient.setEmail(txtEmail.getValue().toString()) ;
    
    }
    
     private void abandon(Clienti objClient){
         if (objClient.getStare()==Clienti.NOU)
       {  this.cboClienti.removeItem(objClient);
          this.refreshForm(((Clienti)this.cboClienti.getSelectedItem())); 
          
       }
     else
       try{
              objClient.refresh(this.conn);
              this.refreshForm(objClient);
               }
            catch(Exception e){
                tools.utilitati.Utilitati.tratareErori(e);
                
            }
}
     private void salveaza(Clienti objClient){
try{           
                int stare=objClient.getStare();
                objClient.salveaza(this.conn);
                if (stare==Clienti.STERS)
                {   this.cboClienti.removeItemAt(this.cboClienti.getSelectedIndex()); 
                     this.refreshForm((Clienti)this.cboClienti.getSelectedItem());
                }
               }
            catch(Exception e){
                tools.utilitati.Utilitati.tratareErori(e);
            }
}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panelNorth = new javax.swing.JPanel();
        cboClienti = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodcl = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNumepren = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSalorar = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTelefon = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        cboCompart = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JFormattedTextField();
        panelSouth = new javax.swing.JPanel();
        panelTranzactii1 = new javax.swing.JPanel();
        btnAdauga = new javax.swing.JButton();
        btnSterge = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelPontaj = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReparatii = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAddReparatie = new javax.swing.JButton();
        btnStergReparatie = new javax.swing.JButton();
        btnSaveReparatie = new javax.swing.JButton();
        btnCancelReparatie = new javax.swing.JButton();
        cboAn = new javax.swing.JComboBox();
        cboLuna = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        cboClienti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClientiActionPerformed(evt);
            }
        });
        cboClienti.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboClientiItemStateChanged(evt);
            }
        });

        panelNorth.add(cboClienti);

        jButton1.setText("<<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        panelNorth.add(jButton1);

        jButton2.setText("<");
        panelNorth.add(jButton2);

        jButton3.setText(">");
        panelNorth.add(jButton3);

        jButton4.setText(">>");
        panelNorth.add(jButton4);

        getContentPane().add(panelNorth, java.awt.BorderLayout.NORTH);

        panelCenter.setLayout(new java.awt.GridLayout(7, 2));

        jLabel1.setText("Cod Client");
        panelCenter.add(jLabel1);

        txtCodcl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodclFocusGained(evt);
            }
        });

        panelCenter.add(txtCodcl);

        jLabel2.setText("Nume Prenume");
        panelCenter.add(jLabel2);

        panelCenter.add(txtNumepren);

        jLabel4.setText("Adresa");
        panelCenter.add(jLabel4);

        panelCenter.add(txtSalorar);

        jLabel5.setText("Telefon");
        panelCenter.add(jLabel5);

        panelCenter.add(txtTelefon);

        jLabel6.setText("compartiment");
        panelCenter.add(jLabel6);

        panelCenter.add(cboCompart);

        jLabel7.setText("Email");
        panelCenter.add(jLabel7);

        panelCenter.add(txtEmail);

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        panelSouth.setLayout(new java.awt.BorderLayout());

        btnAdauga.setText("Adauga");
        btnAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdaugaActionPerformed(evt);
            }
        });

        panelTranzactii1.add(btnAdauga);

        btnSterge.setText("Sterge");
        btnSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStergeActionPerformed(evt);
            }
        });

        panelTranzactii1.add(btnSterge);

        btnOk.setText("Save");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        panelTranzactii1.add(btnOk);

        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        panelTranzactii1.add(btnCancel);

        panelSouth.add(panelTranzactii1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 250));
        tblReparatii.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblReparatii);

        panelPontaj.add(jScrollPane1);

        panelSouth.add(panelPontaj, java.awt.BorderLayout.CENTER);

        btnAddReparatie.setText("Add Reparatie");
        btnAddReparatie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddReparatieActionPerformed(evt);
            }
        });

        jPanel2.add(btnAddReparatie);

        btnStergReparatie.setText("Sterge linia ");
        btnStergReparatie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStergReparatieActionPerformed(evt);
            }
        });

        jPanel2.add(btnStergReparatie);

        btnSaveReparatie.setText("Save");
        btnSaveReparatie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveReparatieActionPerformed(evt);
            }
        });

        jPanel2.add(btnSaveReparatie);

        btnCancelReparatie.setText("CANCEL");
        btnCancelReparatie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelReparatieActionPerformed(evt);
            }
        });

        jPanel2.add(btnCancelReparatie);

        cboAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAnActionPerformed(evt);
            }
        });

        jPanel2.add(cboAn);

        cboLuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLunaActionPerformed(evt);
            }
        });

        jPanel2.add(cboLuna);

        panelSouth.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        panelSouth.add(jPanel1, java.awt.BorderLayout.EAST);

        getContentPane().add(panelSouth, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void cboLunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLunaActionPerformed
       this.refreshReparatii();
    }//GEN-LAST:event_cboLunaActionPerformed

    private void cboAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAnActionPerformed
      this.refreshReparatii();
    }//GEN-LAST:event_cboAnActionPerformed

    private void cboClientiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClientiActionPerformed
       this.refreshReparatii();
    }//GEN-LAST:event_cboClientiActionPerformed

    private void btnCancelReparatieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelReparatieActionPerformed
       ((ModelJTableReparatii)this.tblReparatii.getModel()).anulezModificari();
       this.tblReparatii.revalidate();
       this.tblReparatii.repaint();
    }//GEN-LAST:event_btnCancelReparatieActionPerformed

    private void btnSaveReparatieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReparatieActionPerformed
      ((ModelJTableReparatii)this.tblReparatii.getModel()).comiteModificari();
       this.tblReparatii.revalidate();
       this.tblReparatii.repaint();
    }//GEN-LAST:event_btnSaveReparatieActionPerformed

    private void btnStergReparatieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStergReparatieActionPerformed
      if (this.tblReparatii.getSelectedRow()==-1)
          JOptionPane.showMessageDialog(this,"Selectati mai intai o linie!!!");
      else
        ((ModelJTableReparatii)this.tblReparatii.getModel()).stergLinie(this.tblReparatii.getSelectedRow());
    }//GEN-LAST:event_btnStergReparatieActionPerformed

    private void btnAddReparatieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddReparatieActionPerformed
        Clienti objClient=(Clienti)this.cboClienti.getSelectedItem();
        ((ModelJTableReparatii)this.tblReparatii.getModel()).addLinieNoua(objClient.getCodcl());
          this.tblReparatii.revalidate();
          this.tblReparatii.repaint();
    }//GEN-LAST:event_btnAddReparatieActionPerformed

    private void txtCodclFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodclFocusGained
      //((JFormattedTextField)evt.getSource()).setSelectionStart(0);
      //((JFormattedTextField)evt.getSource()).setSelectionEnd(4);
      //((JFormattedTextField)evt.getSource()).select(0, 2);
     // ((JFormattedTextField)evt.getSource()).setCaretPosition(0);
     // ((JFormattedTextField)evt.getSource()).moveCaretPosition(3);
        ((JFormattedTextField)evt.getSource()).setCaretPosition(1);
        ((JFormattedTextField)evt.getSource()).moveCaretPosition(3);
        ((JFormattedTextField)evt.getSource()).grabFocus();
      //((JFormattedTextField)evt.getSource()).repaint();
    }//GEN-LAST:event_txtCodclFocusGained

    private void btnStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStergeActionPerformed
 ((Clienti)this.cboClienti.getSelectedItem()).setStare(Clienti.STERS);
 
    }//GEN-LAST:event_btnStergeActionPerformed

    private void btnAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdaugaActionPerformed
      Clienti objNou=new Clienti();
    
        this.cboClienti.addItem(objNou);
        this.cboClienti.setSelectedItem(objNou);
        this.refreshForm(objNou);
    }//GEN-LAST:event_btnAdaugaActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
      Clienti objClient=((Clienti)this.cboClienti.getSelectedItem());
      this.abandon(objClient);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        this.updateBean((Clienti)this.cboClienti.getSelectedItem());
        //JOptionPane.showMessageDialog(null,new Integer(((Clienti)this.cboClienti.getSelectedItem()).getStare()));
        this.salveaza((Clienti)this.cboClienti.getSelectedItem());
      
    }//GEN-LAST:event_btnOkActionPerformed

    private void cboClientiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboClientiItemStateChanged
        // TODO add your handling code here:
        Clienti objClient=(Clienti)evt.getItem();
        if (evt.getStateChange()==java.awt.event.ItemEvent.SELECTED)
          {this.refreshForm(objClient);
           }
        
                  
    }//GEN-LAST:event_cboClientiItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new FormClienti().show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdauga;
    private javax.swing.JButton btnAddReparatie;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancelReparatie;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnSaveReparatie;
    private javax.swing.JButton btnStergReparatie;
    private javax.swing.JButton btnSterge;
    private javax.swing.JComboBox cboAn;
    private javax.swing.JComboBox cboClienti;
    private javax.swing.JComboBox cboCompart;
    private javax.swing.JComboBox cboLuna;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelNorth;
    private javax.swing.JPanel panelPontaj;
    private javax.swing.JPanel panelSouth;
    private javax.swing.JPanel panelTranzactii1;
    private javax.swing.JTable tblReparatii;
    private javax.swing.JFormattedTextField txtCodcl;
    private javax.swing.JFormattedTextField txtEmail;
    private javax.swing.JFormattedTextField txtNumepren;
    private javax.swing.JFormattedTextField txtSalorar;
    private javax.swing.JFormattedTextField txtTelefon;
    // End of variables declaration//GEN-END:variables
    
}
