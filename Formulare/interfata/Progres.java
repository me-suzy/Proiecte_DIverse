/*
 * JScroll.java
 *
 * Created on 02 iulie 2004, 11:37
 */

package Formulare.interfata;
import Sursa.Clienti;

import Sursa.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.text.*;
import javax.swing.text.*;
import java.math.BigDecimal;
import utilitati.*;
import javax.swing.event.*;
import java.awt.*;
/**
 *
 * @author  alex
 */
public class Progres extends javax.swing.JFrame {
    
    /** Creates new form JScroll */
    public Progres() {
        initComponents();
    //   faContinut();
      final int m_min=1,m_max=100;
      JPanel continut=new JPanel();
        
      this.getContentPane().add(continut,java.awt.BorderLayout.CENTER);
      UIManager.put("JProgressBar.selectionBackground", Color.black);
      UIManager.put("JProgressBar.selectionForeground", Color.white);
      UIManager.put("JProgressBar.foreground",new Color(128,32,128));  
        
     final JProgressBar jpb=new JProgressBar();
     jpb.setMinimum(m_min);
     jpb.setMaximum(m_max);
     jpb.setStringPainted(true);
        
     JButton start=new JButton("Start");
    }
    /*protected void faContinut()   {
        final int m_min=1,m_max=100;
        JPanel continut=new JPanel();
        
        this.getContentPane().add(continut,java.awt.BorderLayout.CENTER);
        UIManager.put("JProgressBar.selectionBackground", Color.black);
        UIManager.put("JProgressBar.selectionForeground", Color.white);
        UIManager.put("JProgressBar.foreground",new Color(128,32,128));  
        
        final JProgressBar jpb=new JProgressBar();
        jpb.setMinimum(m_min);
        jpb.setMaximum(m_max);
        jpb.setStringPainted(true);
        
        JButton start=new JButton("Start");
         start.addActionListener(new java.awt.event.ActionListener(){
           public void actionPerformed(java.awt.event.ActionEvent evt){
               startAddActionPerformed(evt);
           }*/
   /*class AscultatorStart implements ActionListener{
       
         public void actionPerformed(javax.swing.event.ActionEvent e){
                Thread runner=new Thread(){
                    int m_counter=m_min;
                    public void run(){
                        while(m_counter<=m_max){
                            Runnable runme= new Runnable(){
                                public void run(){
                                    jpb.setValue(m_counter);
                                }
                            };
                            SwingUtilities.invokeLater(runme);
                            m_counter++;
                            try{
                                Thread.sleep(100);
                            }
                            catch(Exception ex){}
                        }
                    }
                };
                runner.start();
            }
       }
        AscultatorStart Start=new AscultatorStart();
        start.addActionListener(Start);
        //continut.add(jpb,BorderLayout.CENTER);
        //continut.add(start,BorderLayout.WEST); */
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        JPanel = new javax.swing.JPanel();
        JProgressBar = new javax.swing.JProgressBar();
        Start = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        JPanel.add(JProgressBar);

        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        JPanel.add(Start);

        getContentPane().add(JPanel, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_StartActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        new Progres().show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel;
    private javax.swing.JProgressBar JProgressBar;
    private javax.swing.JButton Start;
    // End of variables declaration//GEN-END:variables
        
    
    
}

