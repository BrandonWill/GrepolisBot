package Grepolis;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @Author Brandon
 * Created by Brandon on 10/24/2015.
 * Time: 3:36 PM
 */
public class FarmersGUI extends javax.swing.JFrame {
    private ArrayList<Town> towns;

    public FarmersGUI(ArrayList<Town> townList) {
        this.towns = townList;
        if (towns != null && towns.size() > 0) {
            initComponents();
            Town town = towns.get(0);
            jSpinner1.setValue(town.getFarming().getMoodToLootTo());
            jCheckBox1.setSelected(GrepolisBot.isFarmersEnabled());

            int index;
            switch (Farming.getTimeToFarm()) {
                case MINUTES_FIVE:
                    index = 0;
                    break;

                case MINUTES_TWENTY:
                    index = 1;
                    break;

                case MINUTES_NINETY:
                    index = 2;
                    break;

                case MINUTES_TWO_HUNDRED_FORTY:
                    index = 3;
                    break;

                default:
                    index = 0;
                    break;
            }
            jComboBox1.setSelectedIndex(index);
        } else {
            this.dispose();
            //TODO write an error log to wait until towns are loaded
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jCheckBox1.setText("Enabled");

        jLabel1.setText("Farming");

        jLabel2.setText("Interval");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"5 minutes", "20 minutes", "1 hour 30 minutes", "4 hours"}));

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Min mood");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(80, 0, 100, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(161, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(61, 61, 61))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(201, 201, 201))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jSpinner1))
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel2)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCheckBox1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        GrepolisBot.setFarmersEnabled(jCheckBox1.isSelected());
        for (Town town : towns) {
            town.getFarming().setMoodToLootTo(Integer.parseInt(jSpinner1.getValue().toString()));
            switch (jComboBox1.getSelectedIndex()) {
                case 0:
                    town.getFarming().setTimeToFarm(Farming.TimeToFarm.MINUTES_FIVE);
                    break;

                case 1:
                    town.getFarming().setTimeToFarm(Farming.TimeToFarm.MINUTES_TWENTY);
                    break;

                case 2:
                    town.getFarming().setTimeToFarm(Farming.TimeToFarm.MINUTES_NINETY);
                    break;

                case 3:
                    town.getFarming().setTimeToFarm(Farming.TimeToFarm.MINUTES_TWO_HUNDRED_FORTY);
                    break;
            }
        }

    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration
}

