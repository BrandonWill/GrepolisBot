package Grepolis.GUI;

/**
 * @Author Brandon
 * Created by Brandon on 3/3/2016.
 * Time: 12:33 AM
 */

import Grepolis.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        jComboBox3 = new JComboBox<>();
        for (Town town : towns) {
            jComboBox3.addItem(town.getName());
        }
        jComboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTown(towns.get(jComboBox3.getSelectedIndex()));
            }
        });
        initComponents();
        if (towns != null && towns.size() > 0) {

            if (Farming.isAllEnabled()) {
                jSpinner1.setValue(Farming.getAllMoodToLootTo());
                jCheckBox1.setSelected(Farming.isAllEnabled());

                int index;
                switch (Farming.getAllIntervalToFarm()) {
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


                changeTown(towns.get(0));
        }
    }

    private void changeTown(Town town) {
        int index;
        switch (town.getFarming().getIntervalToFarm()) {
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
        jComboBox2.setSelectedIndex(index);

        jSpinner2.setValue(town.getFarming().getMoodToLootTo());
        jCheckBox2.setSelected(town.getFarming().isEnabled());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton2 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<String>();
        jLabel6 = new javax.swing.JLabel();
        previousTownButton = new javax.swing.JButton();
        nextTownButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        updateIntervalButton = new javax.swing.JButton();
        updateEnabledButton = new javax.swing.JButton();
        updateMoodButton = new javax.swing.JButton();

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

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("All towns");

        jTextPane1.setEditable(false);
        jTextPane1.setText("All current and new towns will have these settings. They override (not overwrite) your current town settings. To get your old settings back, simply uncheck the box!");
        jTextPane1.setToolTipText("");
        jScrollPane1.setViewportView(jTextPane1);

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Enabled");

        jTextPane2.setEditable(false);
        jTextPane2.setText("If using this option, every single town must be individually edited. New towns aren't automatically enabled.");
        jTextPane2.setToolTipText("");
        jScrollPane2.setViewportView(jTextPane2);

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(80, 0, 100, 1));

        jLabel4.setText("Min mood");

        jLabel5.setText("Interval");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5 minutes", "20 minutes", "1 hour 30 minutes", "4 hours" }));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Current Town");

        previousTownButton.setText("Prev");
        previousTownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousTownButtonActionPerformed(evt);
            }
        });

        nextTownButton.setText("Next");
        nextTownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextTownButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("Towns");

        updateIntervalButton.setText("Update All Towns");
        updateIntervalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateIntervalButtonActionPerformed(evt);
            }
        });

        updateEnabledButton.setText("Update All Towns");
        updateEnabledButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEnabledButtonActionPerformed(evt);
            }
        });

        updateMoodButton.setText("Update All Towns");
        updateMoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateMoodButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(62, 62, 62)
                                                        .addComponent(jLabel1)
                                                        .addGap(11, 11, 11))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jCheckBox1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jCheckBox2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(updateEnabledButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(44, 44, 44)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel7)
                                                                                .addGap(55, 55, 55))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(previousTownButton)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(nextTownButton))
                                                                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jLabel4))
                                                                        .addGap(164, 164, 164))
                                                                .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(52, 52, 52)
                                                                        .addComponent(jLabel6)
                                                                        .addGap(11, 11, 11))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addGap(27, 27, 27)
                                                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(62, 62, 62)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(updateIntervalButton)
                                                        .addComponent(updateMoodButton))))
                                .addContainerGap(542, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBox1)
                                        .addComponent(jButton1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel6)
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(updateIntervalButton))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(updateMoodButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBox2)
                                        .addComponent(updateEnabledButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(previousTownButton)
                                        .addComponent(nextTownButton))
                                .addGap(12, 12, 12)
                                .addComponent(jButton2)
                                .addContainerGap())
        );
    }// </editor-fold>

    //Saves to all the towns!
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Farming.setAllEnabled(jCheckBox1.isSelected());
        Farming.setAllMoodToLootTo(Integer.parseInt(jSpinner1.getValue().toString()));
        switch (jComboBox1.getSelectedIndex()) {
            case 0:
                Farming.setAllIntervalToFarm(Farming.IntervalToFarm.MINUTES_FIVE);
                break;

            case 1:
                Farming.setAllIntervalToFarm(Farming.IntervalToFarm.MINUTES_TWENTY);
                break;

            case 2:
                Farming.setAllIntervalToFarm(Farming.IntervalToFarm.MINUTES_NINETY);
                break;

            case 3:
                Farming.setAllIntervalToFarm(Farming.IntervalToFarm.MINUTES_TWO_HUNDRED_FORTY);
                break;
        }
    }

    //Individual towns
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        Town town = towns.get(jComboBox3.getSelectedIndex());
        Farming townFarm = town.getFarming();

        townFarm.setEnabled(jCheckBox2.isSelected());
        townFarm.setMoodToLootTo(Integer.parseInt(jSpinner2.getValue().toString()));
        switch (jComboBox2.getSelectedIndex()) {
            case 0:
                townFarm.setIntervalToFarm(Farming.IntervalToFarm.MINUTES_FIVE);
                break;

            case 1:
                townFarm.setIntervalToFarm(Farming.IntervalToFarm.MINUTES_TWENTY);
                break;

            case 2:
                townFarm.setIntervalToFarm(Farming.IntervalToFarm.MINUTES_NINETY);
                break;

            case 3:
                townFarm.setIntervalToFarm(Farming.IntervalToFarm.MINUTES_TWO_HUNDRED_FORTY);
                break;
        }
    }

    private void previousTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = jComboBox3.getSelectedIndex();
        if (currentIndex-1 >= 0) {
            jComboBox3.setSelectedIndex(currentIndex-1);
        }
    }

    private void nextTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = jComboBox3.getItemCount();
        int currentIndex = jComboBox3.getSelectedIndex();
        if (currentIndex+1 < size) {
            jComboBox3.setSelectedIndex(currentIndex+1);
        }
    }

    private void updateIntervalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Farming.IntervalToFarm intervalToFarm = null;
        switch (jComboBox2.getSelectedIndex()) {
            case 0:
                intervalToFarm = Farming.IntervalToFarm.MINUTES_FIVE;
                break;

            case 1:
                intervalToFarm = Farming.IntervalToFarm.MINUTES_TWENTY;
                break;

            case 2:
                intervalToFarm = Farming.IntervalToFarm.MINUTES_NINETY;
                break;

            case 3:
                intervalToFarm = Farming.IntervalToFarm.MINUTES_TWO_HUNDRED_FORTY;
                break;
        }
        for (Town town : towns) {
            town.getFarming().setIntervalToFarm(intervalToFarm);
        }
    }

    private void updateMoodButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int mood = Integer.parseInt(jSpinner2.getValue().toString());
        for (Town town : towns) {
            town.getFarming().setMoodToLootTo(mood);
        }
    }

    private void updateEnabledButtonActionPerformed(java.awt.event.ActionEvent evt) {
        boolean enabled = jCheckBox2.isSelected();
        for (Town town : towns) {
            town.getFarming().setEnabled(enabled);
        }
    }

    public void setTowns(ArrayList<Town> towns) {
        this.towns = towns;
        jComboBox3.removeAllItems();
        for (Town town : towns) {
            jComboBox3.addItem(town.getName());
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JButton nextTownButton;
    private javax.swing.JButton previousTownButton;
    private javax.swing.JButton updateEnabledButton;
    private javax.swing.JButton updateIntervalButton;
    private javax.swing.JButton updateMoodButton;
    // End of variables declaration
}


