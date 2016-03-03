package Grepolis.GUI;

/**
 * @Author Brandon
 * Created by Brandon on 3/3/2016.
 * Time: 12:33 AM
 */

import Grepolis.*;

import java.util.ArrayList;

import javax.swing.*;

/**
 * @Author Brandon
 * Created by Brandon on 10/24/2015.
 * Time: 3:36 PM
 */
public class FarmingPanel extends JPanel {
    private ArrayList<Town> towns;

    public FarmingPanel(ArrayList<Town> townList) {
        this.towns = townList;
        initComponents();
        if (towns != null && towns.size() > 0) {
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
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();

        jCheckBox1.setText("Enabled");

        jLabel2.setText("Interval");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5 minutes", "20 minutes", "1 hour 30 minutes", "4 hours" }));

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Min mood");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(80, 0, 100, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(518, 518, 518)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(501, 501, 501)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(465, 465, 465)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jCheckBox1)
                                                        .addComponent(jLabel3))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(36, 36, 36)
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(63, 63, 63)))))
                                .addContainerGap(512, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBox1)
                                        .addComponent(jButton1))
                                .addContainerGap(172, Short.MAX_VALUE))
        );
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


