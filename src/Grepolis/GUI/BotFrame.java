package Grepolis.GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Grepolis.GrepolisBot;
import Grepolis.IO.Loader;
import Grepolis.IO.Saver;
import Grepolis.Town;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * @author Brandon
 */
public class BotFrame extends javax.swing.JFrame {
    private ArrayList<Town> towns;
    private QueuePanel queueTab;
    private FarmingPanel farmingTab;

    public BotFrame(ArrayList<Town> townList) {
        this.towns = townList;
        initComponents();
        addJTabbedPaneListener();
    }

    private void addJTabbedPaneListener() {
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if (index == 1) { //queue tab
                    Loader.loadTemplateTowns();
                    queueTab.changeTown();
                } else if (index == 2) { //farming tab
                    farmingTab.setTowns(towns);
                }

            }
        };
        jTabbedPane1.addChangeListener(changeListener);
    }

    private void initComponents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GrepolisBot.setBotIsRunning(false);
                Saver.save(towns);
            }
        });
        jTabbedPane1 = new javax.swing.JTabbedPane();
        settingsPanel = new javax.swing.JPanel();
        queuePanel = new javax.swing.JPanel();
        farmingPanel = new javax.swing.JPanel();
        logPane = new javax.swing.JPanel();
        browserPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
                settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1216, Short.MAX_VALUE)
        );
        settingsPanelLayout.setVerticalGroup(
                settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 586, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Settings", new SettingsPanel());

        javax.swing.GroupLayout queuePanelLayout = new javax.swing.GroupLayout(queuePanel);
        queuePanel.setLayout(queuePanelLayout);
        queuePanelLayout.setHorizontalGroup(
                queuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1216, Short.MAX_VALUE)
        );
        queuePanelLayout.setVerticalGroup(
                queuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 586, Short.MAX_VALUE)
        );


        queueTab = new QueuePanel(towns);
        jTabbedPane1.addTab("Queue", new JScrollPane(queueTab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        javax.swing.GroupLayout farmingPanelLayout = new javax.swing.GroupLayout(farmingPanel);
        farmingPanel.setLayout(farmingPanelLayout);
        farmingPanelLayout.setHorizontalGroup(
                farmingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1216, Short.MAX_VALUE)
        );
        farmingPanelLayout.setVerticalGroup(
                farmingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 586, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Farming", farmingTab = new FarmingPanel(towns));

        javax.swing.GroupLayout logPaneLayout = new javax.swing.GroupLayout(logPane);
        logPane.setLayout(logPaneLayout);
        logPaneLayout.setHorizontalGroup(
                logPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1216, Short.MAX_VALUE)
        );
        logPaneLayout.setVerticalGroup(
                logPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 586, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Log", new LogPanel());

        javax.swing.GroupLayout browserPanelLayout = new javax.swing.GroupLayout(browserPanel);
        browserPanel.setLayout(browserPanelLayout);
        browserPanelLayout.setHorizontalGroup(
                browserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1216, Short.MAX_VALUE)
        );
        browserPanelLayout.setVerticalGroup(
                browserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 586, Short.MAX_VALUE)
        );

        new GrepolisBot();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>

    private javax.swing.JPanel browserPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel logPane;
    private javax.swing.JPanel queuePanel;
    private javax.swing.JPanel farmingPanel;
    private javax.swing.JPanel settingsPanel;

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public void setTowns(ArrayList<Town> towns) {
        this.towns = towns;
    }


}

