package de.gaalop.visualizer.gui;

import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Christian Steinmetz
 */
public class DrawSettings extends javax.swing.JFrame {

    /**
     * Creates new form DrawSettings
     */
    public DrawSettings() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane_Settings = new javax.swing.JScrollPane();
        jPanel_Settings = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane_Inputs = new javax.swing.JScrollPane();
        jPanel_Inputs = new javax.swing.JPanel();
        jButton_Repaint = new javax.swing.JButton();
        jLabel_Info = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel_Visible = new javax.swing.JPanel();
        jButton_LoadPointCloud = new javax.swing.JButton();
        jButton_SavePointCloud = new javax.swing.JButton();
        jComboBox_ZerofindingMethod = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton_DisplayEquations = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gaalop Visualizer Settings");
        setMinimumSize(new java.awt.Dimension(500, 690));
        getContentPane().setLayout(null);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Settings", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jScrollPane_Settings.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_Settings.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout jPanel_SettingsLayout = new javax.swing.GroupLayout(jPanel_Settings);
        jPanel_Settings.setLayout(jPanel_SettingsLayout);
        jPanel_SettingsLayout.setHorizontalGroup(
            jPanel_SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 209, Short.MAX_VALUE)
        );
        jPanel_SettingsLayout.setVerticalGroup(
            jPanel_SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );

        jScrollPane_Settings.setViewportView(jPanel_Settings);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane_Settings)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane_Settings)
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(240, 10, 240, 340);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inputs", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel_InputsLayout = new javax.swing.GroupLayout(jPanel_Inputs);
        jPanel_Inputs.setLayout(jPanel_InputsLayout);
        jPanel_InputsLayout.setHorizontalGroup(
            jPanel_InputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );
        jPanel_InputsLayout.setVerticalGroup(
            jPanel_InputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        jScrollPane_Inputs.setViewportView(jPanel_Inputs);

        jPanel2.add(jScrollPane_Inputs);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 11, 220, 530);

        jButton_Repaint.setText("Repaint");
        getContentPane().add(jButton_Repaint);
        jButton_Repaint.setBounds(360, 547, 90, 23);

        jLabel_Info.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel_Info);
        jLabel_Info.setBounds(30, 580, 430, 20);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visible Objects", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel_VisibleLayout = new javax.swing.GroupLayout(jPanel_Visible);
        jPanel_Visible.setLayout(jPanel_VisibleLayout);
        jPanel_VisibleLayout.setHorizontalGroup(
            jPanel_VisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 209, Short.MAX_VALUE)
        );
        jPanel_VisibleLayout.setVerticalGroup(
            jPanel_VisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel_Visible);

        jPanel3.add(jScrollPane1);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(240, 360, 240, 180);

        jButton_LoadPointCloud.setText("Load Pointcloud");
        jButton_LoadPointCloud.setMaximumSize(new java.awt.Dimension(109, 23));
        jButton_LoadPointCloud.setMinimumSize(new java.awt.Dimension(1079, 23));
        getContentPane().add(jButton_LoadPointCloud);
        jButton_LoadPointCloud.setBounds(30, 610, 120, 23);

        jButton_SavePointCloud.setText("Save Pointcloud");
        getContentPane().add(jButton_SavePointCloud);
        jButton_SavePointCloud.setBounds(180, 610, 120, 23);

        getContentPane().add(jComboBox_ZerofindingMethod);
        jComboBox_ZerofindingMethod.setBounds(140, 550, 200, 20);

        jLabel1.setText("Zerofinding method:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 550, 120, 20);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("F3: Starts recording, F4: Stops recording, ESC: Quit");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 634, 490, 20);

        jButton_DisplayEquations.setText("Display equations");
        jButton_DisplayEquations.setMaximumSize(new java.awt.Dimension(109, 23));
        jButton_DisplayEquations.setMinimumSize(new java.awt.Dimension(109, 23));
        jButton_DisplayEquations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DisplayEquationsActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_DisplayEquations);
        jButton_DisplayEquations.setBounds(330, 610, 130, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_DisplayEquationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DisplayEquationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_DisplayEquationsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DrawSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DrawSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DrawSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DrawSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new DrawSettings().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_DisplayEquations;
    public javax.swing.JButton jButton_LoadPointCloud;
    protected javax.swing.JButton jButton_Repaint;
    public javax.swing.JButton jButton_SavePointCloud;
    protected javax.swing.JComboBox jComboBox_ZerofindingMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel_Info;
    protected javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel_Inputs;
    protected javax.swing.JPanel jPanel_Settings;
    protected javax.swing.JPanel jPanel_Visible;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane_Inputs;
    protected javax.swing.JScrollPane jScrollPane_Settings;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Initializes the zerofinding combobox
     * @param zerofinderList The items of the combobox
     * @param defaultZeroFinder The default selected zero finder
     */
    protected void setZerofinderMethods(LinkedList<ZeroFinder> zerofinderList, ZeroFinder defaultZeroFinder) {
        jComboBox_ZerofindingMethod.setModel(new DefaultComboBoxModel(zerofinderList.toArray(new ZeroFinder[0])));
        jComboBox_ZerofindingMethod.setSelectedItem(defaultZeroFinder);
    }
    
    /**
     * Retruns the selected zerofinder in the combobox
     * @return The selected zerofinder
     */
    protected ZeroFinder getSelectedZeroFinder() {
        return (ZeroFinder) jComboBox_ZerofindingMethod.getSelectedItem();
    }
    
    
}
