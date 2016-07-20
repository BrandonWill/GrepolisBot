/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grepolis.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

import static Grepolis.util.MyLogger.log;
import static Grepolis.util.MyLogger.logError;
import Grepolis.*;
import Grepolis.IO.Saver;

/**
 *
 * @author Brandon
 */
public class QueuePanel extends JPanel {
    private ArrayList<Town> towns;
    private static int currentTownIndex = 0; //Used only for updating the buildings/troops when the tab is clicked
    private static int currentTemplateIndex = 0;
    private static ArrayList<Town> templateTowns;

    public QueuePanel(ArrayList<Town> townList) {
        this.towns = townList;
        jComboBox1 = new JComboBox<>();
        for (Town town : towns) {
            jComboBox1.addItem(town.getName());
        }
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTownIndex = jComboBox1.getSelectedIndex();
                if (towns.size() > 0 && jComboBox1.getSelectedIndex() >= 0) {
                    changeTown(towns.get(jComboBox1.getSelectedIndex()));
                }
            }
        });

        templateComboBox = new JComboBox<>();
        if (templateTowns != null) {
            for (Town town : templateTowns) {
                templateComboBox.addItem(town.getName());
            }
        }
        initComponents();
        if (currentTownIndex != -1) {
            jComboBox1.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
        }else if (towns.size() > 0) {
            changeTown(towns.get(0));
        }
    }

    public void changeTown() {
        jComboBox1.removeAllItems();
        templateComboBox.removeAllItems();
        for (Town town : towns) {
            jComboBox1.addItem(town.getName());
        }
        if (templateTowns != null) {
            for (Town town : templateTowns) {
                templateComboBox.addItem(town.getName());
            }
        }
        if (currentTownIndex != -1) {
            jComboBox1.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
        }else if (towns.size() > 0) {
            changeTown(towns.get(0));
        }
    }

    public void changeTown(Town town) {
        //Update the building levels and building level to build
        try {
            mainLevelToBuild.setValue(town.getBuilding(Building.BuildingType.main).getBuildTo());
            mainPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.main).getLevel()));
            mainPic.setForeground(town.getBuilding(Building.BuildingType.main).getLevel() == town.getBuilding(Building.BuildingType.main).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            mainPic.setText(String.valueOf(0));
            mainPic.setForeground(Color.white);
            mainLevelToBuild.setValue(0);
        }
        try {
            hideLevelToBuild.setValue(town.getBuilding(Building.BuildingType.hide).getBuildTo());
            hidePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.hide).getLevel()));
            hidePic.setForeground(town.getBuilding(Building.BuildingType.hide).getLevel() == town.getBuilding(Building.BuildingType.hide).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            hidePic.setText(String.valueOf(0));
            hidePic.setForeground(Color.white);
            hideLevelToBuild.setValue(0);
        }
        try {
            storageLevelToBuild.setValue(town.getBuilding(Building.BuildingType.storage).getBuildTo());
            storagePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.storage).getLevel()));
            storagePic.setForeground(town.getBuilding(Building.BuildingType.storage).getLevel() == town.getBuilding(Building.BuildingType.storage).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            storagePic.setText(String.valueOf(0));
            storagePic.setForeground(Color.white);
            storageLevelToBuild.setValue(0);
        }
        try {
            farmLevelToBuild.setValue(town.getBuilding(Building.BuildingType.farm).getBuildTo());
            farmPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.farm).getLevel()));
            farmPic.setForeground(town.getBuilding(Building.BuildingType.farm).getLevel() == town.getBuilding(Building.BuildingType.farm).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            farmPic.setText(String.valueOf(0));
            farmPic.setForeground(Color.white);
            farmLevelToBuild.setValue(0);
        }
        try {
            lumberLevelToBuild.setValue(town.getBuilding(Building.BuildingType.lumber).getBuildTo());
            lumberPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.lumber).getLevel()));
            lumberPic.setForeground(town.getBuilding(Building.BuildingType.lumber).getLevel() == town.getBuilding(Building.BuildingType.lumber).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            lumberPic.setText(String.valueOf(0));
            lumberPic.setForeground(Color.white);
            lumberLevelToBuild.setValue(0);
        }
        try {
            stonerLevelToBuild.setValue(town.getBuilding(Building.BuildingType.stoner).getBuildTo());
            stonerPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.stoner).getLevel()));
            stonerPic.setForeground(town.getBuilding(Building.BuildingType.stoner).getLevel() == town.getBuilding(Building.BuildingType.stoner).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            stonerPic.setText(String.valueOf(0));
            stonerPic.setForeground(Color.white);
            stonerLevelToBuild.setValue(0);
        }
        try {
            ironerLevelToBuild.setValue(town.getBuilding(Building.BuildingType.ironer).getBuildTo());
            ironerPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.ironer).getLevel()));
            ironerPic.setForeground(town.getBuilding(Building.BuildingType.ironer).getLevel() == town.getBuilding(Building.BuildingType.ironer).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            ironerPic.setText(String.valueOf(0));
            ironerPic.setForeground(Color.white);
            ironerLevelToBuild.setValue(0);
        }
        try {
            marketLevelToBuild.setValue(town.getBuilding(Building.BuildingType.market).getBuildTo());
            marketPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.market).getLevel()));
            marketPic.setForeground(town.getBuilding(Building.BuildingType.market).getLevel() == town.getBuilding(Building.BuildingType.market).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            marketPic.setText(String.valueOf(0));
            marketPic.setForeground(Color.white);
            marketLevelToBuild.setValue(0);
        }
        try {
            docksLevelToBuild.setValue(town.getBuilding(Building.BuildingType.docks).getBuildTo());
            docksPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.docks).getLevel()));
            docksPic.setForeground(town.getBuilding(Building.BuildingType.docks).getLevel() == town.getBuilding(Building.BuildingType.docks).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            docksPic.setText(String.valueOf(0));
            docksPic.setForeground(Color.white);
            docksLevelToBuild.setValue(0);
        }
        try {
            barracksLevelToBuild.setValue(town.getBuilding(Building.BuildingType.barracks).getBuildTo());
            barracksPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.barracks).getLevel()));
            barracksPic.setForeground(town.getBuilding(Building.BuildingType.barracks).getLevel() == town.getBuilding(Building.BuildingType.barracks).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            barracksPic.setText(String.valueOf(0));
            barracksPic.setForeground(Color.white);
            barracksLevelToBuild.setValue(0);
        }
        try {
            wallLevelToBuild.setValue(town.getBuilding(Building.BuildingType.wall).getBuildTo());
            wallPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.wall).getLevel()));
            wallPic.setForeground(town.getBuilding(Building.BuildingType.wall).getLevel() == town.getBuilding(Building.BuildingType.wall).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            wallPic.setText(String.valueOf(0));
            wallPic.setForeground(Color.white);
            wallLevelToBuild.setValue(0);
        }
        try {
            academyLevelToBuild.setValue(town.getBuilding(Building.BuildingType.academy).getBuildTo());
            academyPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.academy).getLevel()));
            academyPic.setForeground(town.getBuilding(Building.BuildingType.academy).getLevel() == town.getBuilding(Building.BuildingType.academy).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            academyPic.setText(String.valueOf(0));
            academyPic.setForeground(Color.white);
            academyLevelToBuild.setValue(0);
        }
        try {
            templeLevelToBuild.setValue(town.getBuilding(Building.BuildingType.temple).getBuildTo());
            templePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.temple).getLevel()));
            templePic.setForeground(town.getBuilding(Building.BuildingType.temple).getLevel() == town.getBuilding(Building.BuildingType.temple).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            templePic.setText(String.valueOf(0));
            templePic.setForeground(Color.white);
            templeLevelToBuild.setValue(0);
        }
        try {
            theaterLevelToBuild.setValue(town.getBuilding(Building.BuildingType.theater).getBuildTo());
            theaterPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.theater).getLevel()));
            theaterPic.setForeground(town.getBuilding(Building.BuildingType.theater).getLevel() == town.getBuilding(Building.BuildingType.theater).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            theaterPic.setText(String.valueOf(0));
            theaterPic.setForeground(Color.white);
            theaterLevelToBuild.setValue(0);
        }
        try {
            thermalLevelToBuild.setValue(town.getBuilding(Building.BuildingType.thermal).getBuildTo());
            thermalPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.thermal).getLevel()));
            thermalPic.setForeground(town.getBuilding(Building.BuildingType.thermal).getLevel() == town.getBuilding(Building.BuildingType.thermal).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            thermalPic.setText(String.valueOf(0));
            thermalPic.setForeground(Color.white);
            thermalLevelToBuild.setValue(0);
        }
        try {
            libraryLevelToBuild.setValue(town.getBuilding(Building.BuildingType.library).getBuildTo());
            libraryPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.library).getLevel()));
            libraryPic.setForeground(town.getBuilding(Building.BuildingType.library).getLevel() == town.getBuilding(Building.BuildingType.library).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            libraryPic.setText(String.valueOf(0));
            libraryPic.setForeground(Color.white);
            libraryLevelToBuild.setValue(0);
        }
        try {
            lighthouseLevelToBuild.setValue(town.getBuilding(Building.BuildingType.lighthouse).getBuildTo());
            lighthousePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.lighthouse).getLevel()));
            lighthousePic.setForeground(town.getBuilding(Building.BuildingType.lighthouse).getLevel() == town.getBuilding(Building.BuildingType.lighthouse).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            lighthousePic.setText(String.valueOf(0));
            lighthousePic.setForeground(Color.white);
            lighthouseLevelToBuild.setValue(0);
        }
        try {
            towerLevelToBuild.setValue(town.getBuilding(Building.BuildingType.tower).getBuildTo());
            towerPic.setText(String.valueOf(town.getBuilding(Building.BuildingType.tower).getLevel()));
            towerPic.setForeground(town.getBuilding(Building.BuildingType.tower).getLevel() == town.getBuilding(Building.BuildingType.tower).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            towerPic.setText(String.valueOf(0));
            towerPic.setForeground(Color.white);
            towerLevelToBuild.setValue(0);
        }
        try {
            statueLevelToBuild.setValue(town.getBuilding(Building.BuildingType.statue).getBuildTo());
            statuePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.statue).getLevel()));
            statuePic.setForeground(town.getBuilding(Building.BuildingType.statue).getLevel() == town.getBuilding(Building.BuildingType.statue).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            statuePic.setText(String.valueOf(0));
            statuePic.setForeground(Color.white);
            statueLevelToBuild.setValue(0);
        }
        try {
            oracleLevelToBuild.setValue(town.getBuilding(Building.BuildingType.oracle).getBuildTo());
            oraclePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.oracle).getLevel()));
            oraclePic.setForeground(town.getBuilding(Building.BuildingType.oracle).getLevel() == town.getBuilding(Building.BuildingType.oracle).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            oraclePic.setText(String.valueOf(0));
            oraclePic.setForeground(Color.white);
            oracleLevelToBuild.setValue(0);
        }
        try {
            trade_officeLevelToBuild.setValue(town.getBuilding(Building.BuildingType.trade_office).getBuildTo());
            trade_officePic.setText(String.valueOf(town.getBuilding(Building.BuildingType.trade_office).getLevel()));
            trade_officePic.setForeground(town.getBuilding(Building.BuildingType.trade_office).getLevel() == town.getBuilding(Building.BuildingType.trade_office).getBuildTo() ? Color.green : Color.white);
        } catch (Exception ignored) {
            trade_officePic.setText(String.valueOf(0));
            trade_officePic.setForeground(Color.white);
            trade_officeLevelToBuild.setValue(0);
        }
        //update the troop count and the number of them to build
        try {
            swordToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getBuildTo());
            swordPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getTotalTroops()));
            swordPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getInQueue() +" in queue)")));
            swordPic.setForeground(Color.black);
        } catch (Exception ignored) {
            swordPic.setText(String.valueOf(0));
            swordPic.setForeground(Color.black);
            swordPic.setToolTipText(String.valueOf(0));
            swordToBuild.setValue(0);
        }
        try {
            slingerToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getBuildTo());
            slingerPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getTotalTroops()));
            slingerPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getInQueue() +" in queue)")));
            slingerPic.setForeground(Color.black);
        } catch (Exception ignored) {
            slingerPic.setText(String.valueOf(0));
            slingerPic.setForeground(Color.black);
            slingerPic.setToolTipText(String.valueOf(0));
            slingerToBuild.setValue(0);
        }
        try {
            archerToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getBuildTo());
            archerPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getTotalTroops()));
            archerPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getInQueue() +" in queue)")));
            archerPic.setForeground(Color.black);
        } catch (Exception ignored) {
            archerPic.setText(String.valueOf(0));
            archerPic.setForeground(Color.black);
            archerPic.setToolTipText(String.valueOf(0));
            archerToBuild.setValue(0);
        }
        try {
            hopliteToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getBuildTo());
            hoplitePic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getTotalTroops()));
            hoplitePic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getInQueue() +" in queue)")));
            hoplitePic.setForeground(Color.black);
        } catch (Exception ignored) {
            hoplitePic.setText(String.valueOf(0));
            hoplitePic.setForeground(Color.black);
            hoplitePic.setToolTipText(String.valueOf(0));
            hopliteToBuild.setValue(0);
        }
        try {
            riderToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getBuildTo());
            riderPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getTotalTroops()));
            riderPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getInQueue() +" in queue)")));
            riderPic.setForeground(Color.black);
        } catch (Exception ignored) {
            riderPic.setText(String.valueOf(0));
            riderPic.setForeground(Color.black);
            riderPic.setToolTipText(String.valueOf(0));
            riderToBuild.setValue(0);
        }
        try {
            chariotToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getBuildTo());
            chariotPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getTotalTroops()));
            chariotPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getInQueue() +" in queue)")));
            chariotPic.setForeground(Color.black);
        } catch (Exception ignored) {
            chariotPic.setText(String.valueOf(0));
            chariotPic.setForeground(Color.black);
            chariotPic.setToolTipText(String.valueOf(0));
            chariotToBuild.setValue(0);
        }
        try {
            catapultToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getBuildTo());
            catapultPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getTotalTroops()));
            catapultPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getInQueue() +" in queue)")));
            catapultPic.setForeground(Color.black);
        } catch (Exception ignored) {
            catapultPic.setText(String.valueOf(0));
            catapultPic.setForeground(Color.black);
            catapultPic.setToolTipText(String.valueOf(0));
            catapultToBuild.setValue(0);
        }
        try {
            minotaurToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getBuildTo());
            minotaurPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getTotalTroops()));
            minotaurPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getInQueue() +" in queue)")));
            minotaurPic.setForeground(Color.black);
        } catch (Exception ignored) {
            minotaurPic.setText(String.valueOf(0));
            minotaurPic.setForeground(Color.black);
            minotaurPic.setToolTipText(String.valueOf(0));
            minotaurToBuild.setValue(0);
        }
        try {
            manticoreToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getBuildTo());
            manticorePic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getTotalTroops()));
            manticorePic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getInQueue() +" in queue)")));
            manticorePic.setForeground(Color.black);
        } catch (Exception ignored) {
            manticorePic.setText(String.valueOf(0));
            manticorePic.setForeground(Color.black);
            manticorePic.setToolTipText(String.valueOf(0));
            manticoreToBuild.setValue(0);
        }
        try {
            centaurToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getBuildTo());
            centaurPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getTotalTroops()));
            centaurPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getInQueue() +" in queue)")));
            centaurPic.setForeground(Color.black);
        } catch (Exception ignored) {
            centaurPic.setText(String.valueOf(0));
            centaurPic.setForeground(Color.black);
            centaurPic.setToolTipText(String.valueOf(0));
            centaurToBuild.setValue(0);
        }
        try {
            pegasusToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getBuildTo());
            pegasusPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getTotalTroops()));
            pegasusPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getInQueue() +" in queue)")));
            pegasusPic.setForeground(Color.black);
        } catch (Exception ignored) {
            pegasusPic.setText(String.valueOf(0));
            pegasusPic.setForeground(Color.black);
            pegasusPic.setToolTipText(String.valueOf(0));
            pegasusToBuild.setValue(0);
        }
        try {
            harpyToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getBuildTo());
            harpyPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getTotalTroops()));
            harpyPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getInQueue() +" in queue)")));
            harpyPic.setForeground(Color.black);
        } catch (Exception ignored) {
            harpyPic.setText(String.valueOf(0));
            harpyPic.setForeground(Color.black);
            harpyPic.setToolTipText(String.valueOf(0));
            harpyToBuild.setValue(0);
        }
        try {
            medusaToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getBuildTo());
            medusaPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getTotalTroops()));
            medusaPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getInQueue() +" in queue)")));
            medusaPic.setForeground(Color.black);
        } catch (Exception ignored) {
            medusaPic.setText(String.valueOf(0));
            medusaPic.setForeground(Color.black);
            medusaPic.setToolTipText(String.valueOf(0));
            medusaToBuild.setValue(0);
        }
        try {
            zyklopToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getBuildTo());
            zyklopPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getTotalTroops()));
            zyklopPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getInQueue() +" in queue)")));
            zyklopPic.setForeground(Color.black);
        } catch (Exception ignored) {
            zyklopPic.setText(String.valueOf(0));
            zyklopPic.setForeground(Color.black);
            zyklopPic.setToolTipText(String.valueOf(0));
            zyklopToBuild.setValue(0);
        }
        try {
            cerberusToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getBuildTo());
            cerberusPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getTotalTroops()));
            cerberusPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getInQueue() +" in queue)")));
            cerberusPic.setForeground(Color.black);
        } catch (Exception ignored) {
            cerberusPic.setText(String.valueOf(0));
            cerberusPic.setForeground(Color.black);
            cerberusPic.setToolTipText(String.valueOf(0));
            cerberusToBuild.setValue(0);
        }
        try {
            furyToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getBuildTo());
            furyPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getTotalTroops()));
            furyPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getInQueue() +" in queue)")));
            furyPic.setForeground(Color.black);
        } catch (Exception ignored) {
            furyPic.setText(String.valueOf(0));
            furyPic.setForeground(Color.black);
            furyPic.setToolTipText(String.valueOf(0));
            furyToBuild.setValue(0);
        }
        try {
            griffinToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getBuildTo());
            griffinPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getTotalTroops()));
            griffinPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getInQueue() +" in queue)")));
            griffinPic.setForeground(Color.black);
        } catch (Exception ignored) {
            griffinPic.setText(String.valueOf(0));
            griffinPic.setForeground(Color.black);
            griffinPic.setToolTipText(String.valueOf(0));
            griffinToBuild.setValue(0);
        }
        try {
            calydonian_boarToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getBuildTo());
            calydonian_boarPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getTotalTroops()));
            calydonian_boarPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getInQueue() +" in queue)")));
            calydonian_boarPic.setForeground(Color.black);
        } catch (Exception ignored) {
            calydonian_boarPic.setText(String.valueOf(0));
            calydonian_boarPic.setForeground(Color.black);
            calydonian_boarPic.setToolTipText(String.valueOf(0));
            calydonian_boarToBuild.setValue(0);
        }
        try {
            godsentToBuild.setValue(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getBuildTo());
            godsentPic.setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getTotalTroops()));
            godsentPic.setToolTipText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getInQueue() +" in queue)")));
            godsentPic.setForeground(Color.black);
        } catch (Exception ignored) {
            godsentPic.setText(String.valueOf(0));
            godsentPic.setForeground(Color.black);
            godsentPic.setToolTipText(String.valueOf(0));
            godsentToBuild.setValue(0);
        }
        try {
            big_transporterToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getBuildTo());
            big_transporterPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getTotalTroops()));
            big_transporterPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getInQueue() +" in queue)")));
            big_transporterPic.setForeground(Color.black);
        } catch (Exception ignored) {
            big_transporterPic.setText(String.valueOf(0));
            big_transporterPic.setForeground(Color.black);
            big_transporterPic.setToolTipText(String.valueOf(0));
            big_transporterToBuild.setValue(0);
        }
        try {
            biremeToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getBuildTo());
            biremePic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getTotalTroops()));
            biremePic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getInQueue() +" in queue)")));
            biremePic.setForeground(Color.black);
        } catch (Exception ignored) {
            biremePic.setText(String.valueOf(0));
            biremePic.setForeground(Color.black);
            biremePic.setToolTipText(String.valueOf(0));
            biremeToBuild.setValue(0);
        }
        try {
            attack_shipToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getBuildTo());
            attack_shipPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getTotalTroops()));
            attack_shipPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getInQueue() +" in queue)")));
            attack_shipPic.setForeground(Color.black);
        } catch (Exception ignored) {
            attack_shipPic.setText(String.valueOf(0));
            attack_shipPic.setForeground(Color.black);
            attack_shipPic.setToolTipText(String.valueOf(0));
            attack_shipToBuild.setValue(0);
        }
        try {
            demolition_shipToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getBuildTo());
            demolition_shipPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getTotalTroops()));
            demolition_shipPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getInQueue() +" in queue)")));
            demolition_shipPic.setForeground(Color.black);
        } catch (Exception ignored) {
            demolition_shipPic.setText(String.valueOf(0));
            demolition_shipPic.setForeground(Color.black);
            demolition_shipPic.setToolTipText(String.valueOf(0));
            demolition_shipToBuild.setValue(0);
        }
        try {
            small_transporterToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getBuildTo());
            small_transporterPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getTotalTroops()));
            small_transporterPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getInQueue() +" in queue)")));
            small_transporterPic.setForeground(Color.black);
        } catch (Exception ignored) {
            small_transporterPic.setText(String.valueOf(0));
            small_transporterPic.setForeground(Color.black);
            small_transporterPic.setToolTipText(String.valueOf(0));
            small_transporterToBuild.setValue(0);
        }
        try {
            triremeToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getBuildTo());
            triremePic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getTotalTroops()));
            triremePic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getInQueue() +" in queue)")));
            triremePic.setForeground(Color.black);
        } catch (Exception ignored) {
            triremePic.setText(String.valueOf(0));
            triremePic.setForeground(Color.black);
            triremePic.setToolTipText(String.valueOf(0));
            triremeToBuild.setValue(0);
        }
        try {
            colonize_shipToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getBuildTo());
            colonize_shipPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getTotalTroops()));
            colonize_shipPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getInQueue() +" in queue)")));
            colonize_shipPic.setForeground(Color.black);
        } catch (Exception ignored) {
            colonize_shipPic.setText(String.valueOf(0));
            colonize_shipPic.setForeground(Color.black);
            colonize_shipPic.setToolTipText(String.valueOf(0));
            colonize_shipToBuild.setValue(0);
        }
        try {
            sea_monsterToBuild.setValue(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getBuildTo());
            sea_monsterPic.setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getTotalTroops()));
            sea_monsterPic.setToolTipText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getInQueue() +" in queue)")));
            sea_monsterPic.setForeground(Color.black);
        } catch (Exception ignored) {
            sea_monsterPic.setText(String.valueOf(0));
            sea_monsterPic.setForeground(Color.black);
            sea_monsterPic.setToolTipText(String.valueOf(0));
            sea_monsterToBuild.setValue(0);
        }
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nextTownButton = new javax.swing.JButton();
        previousTownButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        templeLevelToBuild = new javax.swing.JSpinner();
        trade_officePic = new javax.swing.JLabel();
        academyLevelToBuild = new javax.swing.JSpinner();
        theaterLevelToBuild = new javax.swing.JSpinner();
        oraclePic = new javax.swing.JLabel();
        statuePic = new javax.swing.JLabel();
        stonerPic = new javax.swing.JLabel();
        oracleLevelToBuild = new javax.swing.JSpinner();
        farmPic = new javax.swing.JLabel();
        lumberPic = new javax.swing.JLabel();
        hidePic = new javax.swing.JLabel();
        storagePic = new javax.swing.JLabel();
        marketLevelToBuild = new javax.swing.JSpinner();
        docksLevelToBuild = new javax.swing.JSpinner();
        trade_officeLevelToBuild = new javax.swing.JSpinner();
        stonerLevelToBuild = new javax.swing.JSpinner();
        ironerLevelToBuild = new javax.swing.JSpinner();
        marketPic = new javax.swing.JLabel();
        ironerPic = new javax.swing.JLabel();
        statueLevelToBuild = new javax.swing.JSpinner();
        towerPic = new javax.swing.JLabel();
        libraryPic = new javax.swing.JLabel();
        lighthousePic = new javax.swing.JLabel();
        libraryLevelToBuild = new javax.swing.JSpinner();
        thermalLevelToBuild = new javax.swing.JSpinner();
        towerLevelToBuild = new javax.swing.JSpinner();
        lighthouseLevelToBuild = new javax.swing.JSpinner();
        theaterPic = new javax.swing.JLabel();
        templePic = new javax.swing.JLabel();
        academyPic = new javax.swing.JLabel();
        thermalPic = new javax.swing.JLabel();
        docksPic = new javax.swing.JLabel();
        barracksLevelToBuild = new javax.swing.JSpinner();
        barracksPic = new javax.swing.JLabel();
        wallLevelToBuild = new javax.swing.JSpinner();
        lumberLevelToBuild = new javax.swing.JSpinner();
        wallPic = new javax.swing.JLabel();
        hideLevelToBuild = new javax.swing.JSpinner();
        mainLevelToBuild = new javax.swing.JSpinner();
        mainPic = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        farmLevelToBuild = new javax.swing.JSpinner();
        storageLevelToBuild = new javax.swing.JSpinner();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        swordPic = new javax.swing.JLabel();
        swordToBuild = new javax.swing.JSpinner();
        slingerToBuild = new javax.swing.JSpinner();
        slingerPic = new javax.swing.JLabel();
        archerToBuild = new javax.swing.JSpinner();
        archerPic = new javax.swing.JLabel();
        hopliteToBuild = new javax.swing.JSpinner();
        hoplitePic = new javax.swing.JLabel();
        riderToBuild = new javax.swing.JSpinner();
        riderPic = new javax.swing.JLabel();
        chariotToBuild = new javax.swing.JSpinner();
        chariotPic = new javax.swing.JLabel();
        catapultToBuild = new javax.swing.JSpinner();
        catapultPic = new javax.swing.JLabel();
        minotaurToBuild = new javax.swing.JSpinner();
        minotaurPic = new javax.swing.JLabel();
        manticoreToBuild = new javax.swing.JSpinner();
        manticorePic = new javax.swing.JLabel();
        centaurToBuild = new javax.swing.JSpinner();
        centaurPic = new javax.swing.JLabel();
        pegasusToBuild = new javax.swing.JSpinner();
        pegasusPic = new javax.swing.JLabel();
        harpyToBuild = new javax.swing.JSpinner();
        harpyPic = new javax.swing.JLabel();
        medusaToBuild = new javax.swing.JSpinner();
        medusaPic = new javax.swing.JLabel();
        zyklopToBuild = new javax.swing.JSpinner();
        zyklopPic = new javax.swing.JLabel();
        cerberusToBuild = new javax.swing.JSpinner();
        cerberusPic = new javax.swing.JLabel();
        furyToBuild = new javax.swing.JSpinner();
        furyPic = new javax.swing.JLabel();
        griffinToBuild = new javax.swing.JSpinner();
        griffinPic = new javax.swing.JLabel();
        calydonian_boarToBuild = new javax.swing.JSpinner();
        calydonian_boarPic = new javax.swing.JLabel();
        godsentToBuild = new javax.swing.JSpinner();
        godsentPic = new javax.swing.JLabel();
        big_transporterToBuild = new javax.swing.JSpinner();
        big_transporterPic = new javax.swing.JLabel();
        biremeToBuild = new javax.swing.JSpinner();
        biremePic = new javax.swing.JLabel();
        attack_shipToBuild = new javax.swing.JSpinner();
        attack_shipPic = new javax.swing.JLabel();
        demolition_shipToBuild = new javax.swing.JSpinner();
        demolition_shipPic = new javax.swing.JLabel();
        small_transporterToBuild = new javax.swing.JSpinner();
        small_transporterPic = new javax.swing.JLabel();
        triremeToBuild = new javax.swing.JSpinner();
        triremePic = new javax.swing.JLabel();
        colonize_shipToBuild = new javax.swing.JSpinner();
        colonize_shipPic = new javax.swing.JLabel();
        sea_monsterToBuild = new javax.swing.JSpinner();
        sea_monsterPic = new javax.swing.JLabel();
        templateSaveButton = new javax.swing.JButton();
        templateNextButton = new javax.swing.JButton();
        templatePreviousButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        templateLoadButton = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Towns");

        nextTownButton.setText("Next");
        nextTownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextTownButtonActionPerformed(evt);
            }
        });

        previousTownButton.setText("Prev");
        previousTownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousTownButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setToolTipText("");

        templeLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 30, 1));

        trade_officePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        trade_officePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        trade_officePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "trade_officex20.png"))); // NOI18N
        trade_officePic.setIconTextGap(-20);
        trade_officePic.setOpaque(true);
        trade_officePic.setLayout(null);
        trade_officePic.setToolTipText("");
        trade_officePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        trade_officePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        academyLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 37, 1));

        theaterLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        oraclePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        oraclePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        oraclePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "oraclex20.png"))); // NOI18N
        oraclePic.setIconTextGap(-20);
        oraclePic.setOpaque(true);
        oraclePic.setLayout(null);
        oraclePic.setToolTipText("");
        oraclePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        oraclePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        statuePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        statuePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statuePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "statuex20.png"))); // NOI18N
        statuePic.setIconTextGap(-20);
        statuePic.setOpaque(true);
        statuePic.setLayout(null);
        statuePic.setToolTipText("");
        statuePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        statuePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);


        stonerPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        stonerPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stonerPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "stonerx20.png"))); // NOI18N
        stonerPic.setIconTextGap(-20);
        stonerPic.setOpaque(true);
        stonerPic.setLayout(null);
        stonerPic.setForeground(Color.yellow);
        stonerPic.setToolTipText("");
        stonerPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        stonerPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        oracleLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        farmPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        farmPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        farmPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "farmx20.png"))); // NOI18N
        farmPic.setIconTextGap(-20);
        farmPic.setOpaque(true);
        farmPic.setLayout(null);
        farmPic.setForeground(Color.yellow);
        farmPic.setToolTipText("");
        farmPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        farmPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lumberPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lumberPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lumberPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "lumberx20.png"))); // NOI18N
        lumberPic.setIconTextGap(-20);
        lumberPic.setOpaque(true);
        lumberPic.setLayout(null);
        lumberPic.setForeground(Color.yellow);
        lumberPic.setToolTipText("");
        lumberPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lumberPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        hidePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        hidePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hidePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "hidex20.png"))); // NOI18N
        hidePic.setIconTextGap(-20);
        hidePic.setOpaque(true);
        hidePic.setLayout(null);
        hidePic.setForeground(Color.yellow);
        hidePic.setToolTipText("");
        hidePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        hidePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        storagePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        storagePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        storagePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "storagex20.png"))); // NOI18N
        storagePic.setIconTextGap(-20);
        storagePic.setOpaque(true);
        storagePic.setLayout(null);
        storagePic.setForeground(Color.yellow);
        storagePic.setToolTipText("");
        storagePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        storagePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        marketLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 30, 1));

        docksLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 30, 1));

        trade_officeLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        stonerLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 40, 1));

        ironerLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 40, 1));

        marketPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        marketPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        marketPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "marketx20.png"))); // NOI18N
        marketPic.setIconTextGap(-20);
        marketPic.setOpaque(true);
        marketPic.setLayout(null);
        marketPic.setForeground(Color.yellow);
        marketPic.setToolTipText("");
        marketPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        marketPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        ironerPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ironerPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ironerPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "ironerx20.png"))); // NOI18N
        ironerPic.setIconTextGap(-20);
        ironerPic.setOpaque(true);
        ironerPic.setLayout(null);
        ironerPic.setForeground(Color.yellow);
        ironerPic.setToolTipText("");
        ironerPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ironerPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        statueLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        towerPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        towerPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        towerPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "towerx20.png"))); // NOI18N
        towerPic.setIconTextGap(-20);
        towerPic.setOpaque(true);
        towerPic.setLayout(null);
        towerPic.setToolTipText("");
        towerPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        towerPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        libraryPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        libraryPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        libraryPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "libraryx20.png"))); // NOI18N
        libraryPic.setIconTextGap(-20);
        libraryPic.setOpaque(true);
        libraryPic.setLayout(null);
        libraryPic.setToolTipText("");
        libraryPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        libraryPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lighthousePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lighthousePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lighthousePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "lighthousex20.png"))); // NOI18N
        lighthousePic.setIconTextGap(-20);
        lighthousePic.setOpaque(true);
        lighthousePic.setLayout(null);
        lighthousePic.setToolTipText("");
        lighthousePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lighthousePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        libraryLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        thermalLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        towerLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        lighthouseLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        theaterPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        theaterPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        theaterPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "theaterx20.png"))); // NOI18N
        theaterPic.setIconTextGap(-20);
        theaterPic.setOpaque(true);
        theaterPic.setLayout(null);
        theaterPic.setForeground(Color.yellow);
        theaterPic.setToolTipText("");
        theaterPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        theaterPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        templePic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        templePic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        templePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "templex20.png"))); // NOI18N
        templePic.setIconTextGap(-20);
        templePic.setOpaque(true);
        templePic.setLayout(null);
        templePic.setForeground(Color.yellow);
        templePic.setToolTipText("");
        templePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        templePic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        academyPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        academyPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        academyPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "academyx20.png"))); // NOI18N
        academyPic.setIconTextGap(-20);
        academyPic.setOpaque(true);
        academyPic.setLayout(null);
        academyPic.setForeground(Color.yellow);
        academyPic.setToolTipText("");
        academyPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        academyPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        thermalPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        thermalPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        thermalPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "thermalx20.png"))); // NOI18N
        thermalPic.setIconTextGap(-20);
        thermalPic.setOpaque(true);
        thermalPic.setLayout(null);
        thermalPic.setForeground(Color.yellow);
        thermalPic.setToolTipText("");
        thermalPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        thermalPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        docksPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        docksPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        docksPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "docksx20.png"))); // NOI18N
        docksPic.setIconTextGap(-20);
        docksPic.setOpaque(true);
        docksPic.setLayout(null);
        docksPic.setForeground(Color.yellow);
        docksPic.setToolTipText("");
        docksPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        docksPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        barracksLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 30, 1));

        barracksPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        barracksPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        barracksPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "barracksx20.png"))); // NOI18N
        barracksPic.setIconTextGap(-20);
        barracksPic.setOpaque(true);
        barracksPic.setLayout(null);
        barracksPic.setForeground(Color.yellow);
        barracksPic.setToolTipText("");
        barracksPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        barracksPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        wallLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 25, 1));

        lumberLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 40, 1));

        wallPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        wallPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        wallPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "wallx20.png"))); // NOI18N
        wallPic.setIconTextGap(-20);
        wallPic.setOpaque(true);
        wallPic.setLayout(null);
        wallPic.setForeground(Color.yellow);
        wallPic.setToolTipText("");
        wallPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        wallPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        hideLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        mainLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 25, 1));

        mainPic.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        mainPic.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "mainx20.png"))); // NOI18N
        mainPic.setIconTextGap(-20);
        mainPic.setOpaque(true);
        mainPic.setLayout(null);
        mainPic.setForeground(Color.yellow);
        mainPic.setToolTipText("");
        mainPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mainPic.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel2.setText("Buildings");

        farmLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 1));

        storageLevelToBuild.setModel(new javax.swing.SpinnerNumberModel(0, 0, 35, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(mainLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(mainPic))
                                                .addGap(0, 0, 0)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(hideLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(hidePic))
                                                .addGap(0, 0, 0)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(storagePic)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(farmPic))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(storageLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(farmLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lumberLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lumberPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(stonerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(stonerPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(ironerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(ironerPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(marketLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(marketPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(docksLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(docksPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(barracksLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(barracksPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(wallLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(wallPic))
                                                .addGap(0, 0, 0)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(academyLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(academyPic))
                                                .addGap(0, 0, 0)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(templePic)
                                                        .addComponent(templeLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, 0)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(theaterLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(theaterPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(thermalLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(thermalPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(libraryLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(libraryPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lighthouseLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lighthousePic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(towerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(towerPic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(statueLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(statuePic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(oracleLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(oraclePic))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(trade_officePic)
                                                        .addComponent(trade_officeLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel2))
                                .addContainerGap(347, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(storagePic)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                        .addComponent(ironerPic)
                                                                                                                        .addComponent(marketPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                                                .addComponent(docksPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                                        .addComponent(farmPic, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(barracksPic)
                                                                                                        .addComponent(wallPic))
                                                                                                .addComponent(lumberPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                        .addComponent(stonerPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(storageLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(farmLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lumberLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(stonerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(ironerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(marketLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(docksLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(barracksLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(mainPic)
                                                                        .addComponent(hidePic))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(mainLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(hideLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addComponent(wallLevelToBuild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(26, 26, 26)
                                                        .addComponent(academyLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(academyPic, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(templePic)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(templeLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(trade_officeLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(oracleLevelToBuild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(statueLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addComponent(libraryPic)
                                                                                                .addComponent(lighthousePic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                        .addComponent(towerPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                                .addComponent(statuePic)
                                                                                .addComponent(oraclePic)
                                                                                .addComponent(theaterPic, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(trade_officePic))
                                                                        .addComponent(thermalPic, javax.swing.GroupLayout.Alignment.LEADING))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(theaterLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(thermalLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(libraryLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lighthouseLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(towerLevelToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1096, 130));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Troops");

        swordPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        swordPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        swordPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "swordx40.png"))); // NOI18N
        swordPic.setText("0");
        swordPic.setToolTipText("1000(+300)");
        swordPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        swordPic.setDoubleBuffered(true);
        swordPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        swordPic.setMaximumSize(new java.awt.Dimension(40, 60));
        swordPic.setMinimumSize(new java.awt.Dimension(40, 60));
        swordPic.setName(""); // NOI18N
        swordPic.setPreferredSize(new java.awt.Dimension(40, 60));
        swordPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        swordToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        slingerToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        slingerPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        slingerPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slingerPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "slingerx40.png"))); // NOI18N
        slingerPic.setText("0");
        slingerPic.setToolTipText("1000(+300)");
        slingerPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        slingerPic.setDoubleBuffered(true);
        slingerPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        slingerPic.setMaximumSize(new java.awt.Dimension(40, 60));
        slingerPic.setMinimumSize(new java.awt.Dimension(40, 60));
        slingerPic.setName(""); // NOI18N
        slingerPic.setPreferredSize(new java.awt.Dimension(40, 60));
        slingerPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        archerToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        archerPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        archerPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        archerPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "archerx40.png"))); // NOI18N
        archerPic.setText("0");
        archerPic.setToolTipText("1000(+300)");
        archerPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        archerPic.setDoubleBuffered(true);
        archerPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        archerPic.setMaximumSize(new java.awt.Dimension(40, 60));
        archerPic.setMinimumSize(new java.awt.Dimension(40, 60));
        archerPic.setName(""); // NOI18N
        archerPic.setPreferredSize(new java.awt.Dimension(40, 60));
        archerPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        hopliteToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        hoplitePic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hoplitePic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hoplitePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "hoplitex40.png"))); // NOI18N
        hoplitePic.setText("0");
        hoplitePic.setToolTipText("1000(+300)");
        hoplitePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        hoplitePic.setDoubleBuffered(true);
        hoplitePic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hoplitePic.setMaximumSize(new java.awt.Dimension(40, 60));
        hoplitePic.setMinimumSize(new java.awt.Dimension(40, 60));
        hoplitePic.setName(""); // NOI18N
        hoplitePic.setPreferredSize(new java.awt.Dimension(40, 60));
        hoplitePic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        riderToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        riderPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        riderPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        riderPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "riderx40.png"))); // NOI18N
        riderPic.setText("0");
        riderPic.setToolTipText("1000(+300)");
        riderPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        riderPic.setDoubleBuffered(true);
        riderPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        riderPic.setMaximumSize(new java.awt.Dimension(40, 60));
        riderPic.setMinimumSize(new java.awt.Dimension(40, 60));
        riderPic.setName(""); // NOI18N
        riderPic.setPreferredSize(new java.awt.Dimension(40, 60));
        riderPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        chariotToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        chariotPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chariotPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chariotPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "chariotx40.png"))); // NOI18N
        chariotPic.setText("0");
        chariotPic.setToolTipText("1000(+300)");
        chariotPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        chariotPic.setDoubleBuffered(true);
        chariotPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chariotPic.setMaximumSize(new java.awt.Dimension(40, 60));
        chariotPic.setMinimumSize(new java.awt.Dimension(40, 60));
        chariotPic.setName(""); // NOI18N
        chariotPic.setPreferredSize(new java.awt.Dimension(40, 60));
        chariotPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        catapultToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        catapultPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        catapultPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catapultPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "catapultx40.png"))); // NOI18N
        catapultPic.setText("0");
        catapultPic.setToolTipText("1000(+300)");
        catapultPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        catapultPic.setDoubleBuffered(true);
        catapultPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        catapultPic.setMaximumSize(new java.awt.Dimension(40, 60));
        catapultPic.setMinimumSize(new java.awt.Dimension(40, 60));
        catapultPic.setName(""); // NOI18N
        catapultPic.setPreferredSize(new java.awt.Dimension(40, 60));
        catapultPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        minotaurToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        minotaurPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        minotaurPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minotaurPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "minotaurx40.png"))); // NOI18N
        minotaurPic.setText("0");
        minotaurPic.setToolTipText("1000(+300)");
        minotaurPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        minotaurPic.setDoubleBuffered(true);
        minotaurPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minotaurPic.setMaximumSize(new java.awt.Dimension(40, 60));
        minotaurPic.setMinimumSize(new java.awt.Dimension(40, 60));
        minotaurPic.setName(""); // NOI18N
        minotaurPic.setPreferredSize(new java.awt.Dimension(40, 60));
        minotaurPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        manticoreToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        manticorePic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        manticorePic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manticorePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "manticorex40.png"))); // NOI18N
        manticorePic.setText("0");
        manticorePic.setToolTipText("1000(+300)");
        manticorePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        manticorePic.setDoubleBuffered(true);
        manticorePic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manticorePic.setMaximumSize(new java.awt.Dimension(40, 60));
        manticorePic.setMinimumSize(new java.awt.Dimension(40, 60));
        manticorePic.setName(""); // NOI18N
        manticorePic.setPreferredSize(new java.awt.Dimension(40, 60));
        manticorePic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        centaurToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        centaurPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        centaurPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        centaurPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "centaurx40.png"))); // NOI18N
        centaurPic.setText("0");
        centaurPic.setToolTipText("1000(+300)");
        centaurPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        centaurPic.setDoubleBuffered(true);
        centaurPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        centaurPic.setMaximumSize(new java.awt.Dimension(40, 60));
        centaurPic.setMinimumSize(new java.awt.Dimension(40, 60));
        centaurPic.setName(""); // NOI18N
        centaurPic.setPreferredSize(new java.awt.Dimension(40, 60));
        centaurPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        pegasusToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        pegasusPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pegasusPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pegasusPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "pegasusx40.png"))); // NOI18N
        pegasusPic.setText("0");
        pegasusPic.setToolTipText("1000(+300)");
        pegasusPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pegasusPic.setDoubleBuffered(true);
        pegasusPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pegasusPic.setMaximumSize(new java.awt.Dimension(40, 60));
        pegasusPic.setMinimumSize(new java.awt.Dimension(40, 60));
        pegasusPic.setName(""); // NOI18N
        pegasusPic.setPreferredSize(new java.awt.Dimension(40, 60));
        pegasusPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        harpyToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        harpyPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        harpyPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        harpyPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "harpyx40.png"))); // NOI18N
        harpyPic.setText("0");
        harpyPic.setToolTipText("1000(+300)");
        harpyPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        harpyPic.setDoubleBuffered(true);
        harpyPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        harpyPic.setMaximumSize(new java.awt.Dimension(40, 60));
        harpyPic.setMinimumSize(new java.awt.Dimension(40, 60));
        harpyPic.setName(""); // NOI18N
        harpyPic.setPreferredSize(new java.awt.Dimension(40, 60));
        harpyPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        medusaToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        medusaPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        medusaPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        medusaPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "medusax40.png"))); // NOI18N
        medusaPic.setText("0");
        medusaPic.setToolTipText("1000(+300)");
        medusaPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        medusaPic.setDoubleBuffered(true);
        medusaPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        medusaPic.setMaximumSize(new java.awt.Dimension(40, 60));
        medusaPic.setMinimumSize(new java.awt.Dimension(40, 60));
        medusaPic.setName(""); // NOI18N
        medusaPic.setPreferredSize(new java.awt.Dimension(40, 60));
        medusaPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        zyklopToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        zyklopPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        zyklopPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zyklopPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "zyklopx40.png"))); // NOI18N
        zyklopPic.setText("0");
        zyklopPic.setToolTipText("1000(+300)");
        zyklopPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        zyklopPic.setDoubleBuffered(true);
        zyklopPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zyklopPic.setMaximumSize(new java.awt.Dimension(40, 60));
        zyklopPic.setMinimumSize(new java.awt.Dimension(40, 60));
        zyklopPic.setName(""); // NOI18N
        zyklopPic.setPreferredSize(new java.awt.Dimension(40, 60));
        zyklopPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        cerberusToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        cerberusPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cerberusPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cerberusPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "cerberusx40.png"))); // NOI18N
        cerberusPic.setText("0");
        cerberusPic.setToolTipText("1000(+300)");
        cerberusPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        cerberusPic.setDoubleBuffered(true);
        cerberusPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cerberusPic.setMaximumSize(new java.awt.Dimension(40, 60));
        cerberusPic.setMinimumSize(new java.awt.Dimension(40, 60));
        cerberusPic.setName(""); // NOI18N
        cerberusPic.setPreferredSize(new java.awt.Dimension(40, 60));
        cerberusPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        furyToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        furyPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        furyPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        furyPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "furyx40.png"))); // NOI18N
        furyPic.setText("0");
        furyPic.setToolTipText("1000(+300)");
        furyPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        furyPic.setDoubleBuffered(true);
        furyPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        furyPic.setMaximumSize(new java.awt.Dimension(40, 60));
        furyPic.setMinimumSize(new java.awt.Dimension(40, 60));
        furyPic.setName(""); // NOI18N
        furyPic.setPreferredSize(new java.awt.Dimension(40, 60));
        furyPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        griffinToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        griffinPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        griffinPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        griffinPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "griffinx40.png"))); // NOI18N
        griffinPic.setText("0");
        griffinPic.setToolTipText("1000(+300)");
        griffinPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        griffinPic.setDoubleBuffered(true);
        griffinPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        griffinPic.setMaximumSize(new java.awt.Dimension(40, 60));
        griffinPic.setMinimumSize(new java.awt.Dimension(40, 60));
        griffinPic.setName(""); // NOI18N
        griffinPic.setPreferredSize(new java.awt.Dimension(40, 60));
        griffinPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        calydonian_boarToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        calydonian_boarPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        calydonian_boarPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        calydonian_boarPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "calydonian_boarx40.png"))); // NOI18N
        calydonian_boarPic.setText("0");
        calydonian_boarPic.setToolTipText("1000(+300)");
        calydonian_boarPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        calydonian_boarPic.setDoubleBuffered(true);
        calydonian_boarPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        calydonian_boarPic.setMaximumSize(new java.awt.Dimension(40, 60));
        calydonian_boarPic.setMinimumSize(new java.awt.Dimension(40, 60));
        calydonian_boarPic.setName(""); // NOI18N
        calydonian_boarPic.setPreferredSize(new java.awt.Dimension(40, 60));
        calydonian_boarPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        godsentToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        godsentPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        godsentPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        godsentPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "godsentx40.png"))); // NOI18N
        godsentPic.setText("0");
        godsentPic.setToolTipText("1000(+300)");
        godsentPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        godsentPic.setDoubleBuffered(true);
        godsentPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        godsentPic.setMaximumSize(new java.awt.Dimension(40, 60));
        godsentPic.setMinimumSize(new java.awt.Dimension(40, 60));
        godsentPic.setName(""); // NOI18N
        godsentPic.setPreferredSize(new java.awt.Dimension(40, 60));
        godsentPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        big_transporterToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        big_transporterPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        big_transporterPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        big_transporterPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "big_transporterx40.png"))); // NOI18N
        big_transporterPic.setText("0");
        big_transporterPic.setToolTipText("1000(+300)");
        big_transporterPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        big_transporterPic.setDoubleBuffered(true);
        big_transporterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        big_transporterPic.setMaximumSize(new java.awt.Dimension(40, 60));
        big_transporterPic.setMinimumSize(new java.awt.Dimension(40, 60));
        big_transporterPic.setName(""); // NOI18N
        big_transporterPic.setPreferredSize(new java.awt.Dimension(40, 60));
        big_transporterPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        biremeToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        biremePic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        biremePic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        biremePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "biremex40.png"))); // NOI18N
        biremePic.setText("0");
        biremePic.setToolTipText("1000(+300)");
        biremePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        biremePic.setDoubleBuffered(true);
        biremePic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        biremePic.setMaximumSize(new java.awt.Dimension(40, 60));
        biremePic.setMinimumSize(new java.awt.Dimension(40, 60));
        biremePic.setName(""); // NOI18N
        biremePic.setPreferredSize(new java.awt.Dimension(40, 60));
        biremePic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        attack_shipToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        attack_shipPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        attack_shipPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        attack_shipPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "attack_shipx40.png"))); // NOI18N
        attack_shipPic.setText("0");
        attack_shipPic.setToolTipText("1000(+300)");
        attack_shipPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        attack_shipPic.setDoubleBuffered(true);
        attack_shipPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        attack_shipPic.setMaximumSize(new java.awt.Dimension(40, 60));
        attack_shipPic.setMinimumSize(new java.awt.Dimension(40, 60));
        attack_shipPic.setName(""); // NOI18N
        attack_shipPic.setPreferredSize(new java.awt.Dimension(40, 60));
        attack_shipPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        demolition_shipToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        demolition_shipPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        demolition_shipPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        demolition_shipPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "demolition_shipx40.png"))); // NOI18N
        demolition_shipPic.setText("0");
        demolition_shipPic.setToolTipText("1000(+300)");
        demolition_shipPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        demolition_shipPic.setDoubleBuffered(true);
        demolition_shipPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        demolition_shipPic.setMaximumSize(new java.awt.Dimension(40, 60));
        demolition_shipPic.setMinimumSize(new java.awt.Dimension(40, 60));
        demolition_shipPic.setName(""); // NOI18N
        demolition_shipPic.setPreferredSize(new java.awt.Dimension(40, 60));
        demolition_shipPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        small_transporterToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        small_transporterPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        small_transporterPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        small_transporterPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "small_transporterx40.png"))); // NOI18N
        small_transporterPic.setText("0");
        small_transporterPic.setToolTipText("1000(+300)");
        small_transporterPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        small_transporterPic.setDoubleBuffered(true);
        small_transporterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        small_transporterPic.setMaximumSize(new java.awt.Dimension(40, 60));
        small_transporterPic.setMinimumSize(new java.awt.Dimension(40, 60));
        small_transporterPic.setName(""); // NOI18N
        small_transporterPic.setPreferredSize(new java.awt.Dimension(40, 60));
        small_transporterPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        triremeToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        triremePic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        triremePic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        triremePic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "triremex40.png"))); // NOI18N
        triremePic.setText("0");
        triremePic.setToolTipText("1000(+300)");
        triremePic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        triremePic.setDoubleBuffered(true);
        triremePic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        triremePic.setMaximumSize(new java.awt.Dimension(40, 60));
        triremePic.setMinimumSize(new java.awt.Dimension(40, 60));
        triremePic.setName(""); // NOI18N
        triremePic.setPreferredSize(new java.awt.Dimension(40, 60));
        triremePic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        colonize_shipToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        colonize_shipPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        colonize_shipPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        colonize_shipPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "colonize_shipx40.png"))); // NOI18N
        colonize_shipPic.setText("0");
        colonize_shipPic.setToolTipText("1000(+300)");
        colonize_shipPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        colonize_shipPic.setDoubleBuffered(true);
        colonize_shipPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        colonize_shipPic.setMaximumSize(new java.awt.Dimension(40, 60));
        colonize_shipPic.setMinimumSize(new java.awt.Dimension(40, 60));
        colonize_shipPic.setName(""); // NOI18N
        colonize_shipPic.setPreferredSize(new java.awt.Dimension(40, 60));
        colonize_shipPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        swordToBuild.setEditor(new JSpinner.NumberEditor(swordToBuild,"#"));
        sea_monsterToBuild.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N

        sea_monsterPic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sea_monsterPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sea_monsterPic.setIcon(new javax.swing.ImageIcon(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "sea_monsterx40.png"))); // NOI18N
        sea_monsterPic.setText("0");
        sea_monsterPic.setToolTipText("1000(+300)");
        sea_monsterPic.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        sea_monsterPic.setDoubleBuffered(true);
        sea_monsterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sea_monsterPic.setMaximumSize(new java.awt.Dimension(40, 60));
        sea_monsterPic.setMinimumSize(new java.awt.Dimension(40, 60));
        sea_monsterPic.setName(""); // NOI18N
        sea_monsterPic.setPreferredSize(new java.awt.Dimension(40, 60));
        sea_monsterPic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        swordPic.setOpaque(true);
        swordPic.setText("1200");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(swordPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(swordToBuild))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(slingerPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slingerToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(archerPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(archerToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(hoplitePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(hopliteToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(riderPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(riderToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(chariotPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chariotToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(catapultPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(catapultToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(minotaurPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(minotaurToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(manticorePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(manticoreToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(centaurPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(centaurToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pegasusPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pegasusToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(harpyPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(harpyToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(medusaPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(medusaToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(zyklopPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(zyklopToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cerberusPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cerberusToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(furyPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(furyToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(griffinPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(griffinToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(calydonian_boarPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calydonian_boarToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(godsentPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(godsentToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(big_transporterPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(big_transporterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(biremePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(biremeToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(attack_shipPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(attack_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(demolition_shipPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(demolition_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(small_transporterPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(small_transporterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(triremePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(triremeToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(colonize_shipPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(colonize_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sea_monsterPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(sea_monsterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(minotaurPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(minotaurToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(swordPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(swordToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(slingerPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(slingerToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(archerPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(archerToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(riderPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(riderToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(hoplitePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(hopliteToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(chariotPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(chariotToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(catapultPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(catapultToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(centaurPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(centaurToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(manticorePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(manticoreToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(pegasusPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(pegasusToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(harpyPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(harpyToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(zyklopPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(zyklopToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(medusaPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(medusaToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(cerberusPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cerberusToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(furyPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(furyToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(griffinPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(griffinToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(godsentPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(godsentToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(calydonian_boarPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(calydonian_boarToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(big_transporterPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(big_transporterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(biremePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(biremeToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(attack_shipPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(attack_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(small_transporterPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(small_transporterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(demolition_shipPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(demolition_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(colonize_shipPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(colonize_shipToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(triremePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(triremeToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(sea_monsterPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(sea_monsterToBuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        templateSaveButton.setText("Save");
        templateSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateSaveButtonActionPerformed(evt);
            }
        });

        templateNextButton.setText("Next");
        templateNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateNextButtonActionPerformed(evt);
            }
        });

        templatePreviousButton.setText("Prev");
        templatePreviousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templatePreviousButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Town Templates");

        templateLoadButton.setText("Load");
        templateLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateLoadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(templateSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(templatePreviousButton)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(templateNextButton))
                                                                                        .addComponent(templateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(templateLoadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                .addGap(196, 196, 196))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel4)
                                                                                .addGap(218, 218, 218)))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel1)
                                                                                .addGap(82, 82, 82))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(previousTownButton)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(nextTownButton))
                                                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(27, 27, 27))))
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(previousTownButton)
                                                        .addComponent(nextTownButton))
                                                .addGap(18, 18, 18)
                                                .addComponent(saveButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(templateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(templatePreviousButton)
                                                        .addComponent(templateNextButton))
                                                .addGap(18, 18, 18)
                                                .addComponent(templateSaveButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(templateLoadButton)
                                .addGap(154, 154, 154))
        );

    }// </editor-fold>

    private void nextTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = jComboBox1.getItemCount();
        int currentIndex = jComboBox1.getSelectedIndex();
        if (currentIndex+1 < size) {
            jComboBox1.setSelectedIndex(currentIndex+1);
            currentTownIndex = currentIndex+1;
            changeTown(towns.get(currentIndex+1));
        }
    }

    private void previousTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = jComboBox1.getSelectedIndex();
        if (currentIndex-1 >= 0) {
            jComboBox1.setSelectedIndex(currentIndex-1);
            currentTownIndex = currentIndex-1;
            changeTown(towns.get(currentIndex-1));
        }
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Town town = towns.get(jComboBox1.getSelectedIndex());
        //buildings
        town.getBuilding(Building.BuildingType.main).setBuildTo((Integer) mainLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.hide).setBuildTo((Integer) hideLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.storage).setBuildTo((Integer) storageLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.farm).setBuildTo((Integer) farmLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.lumber).setBuildTo((Integer) lumberLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.stoner).setBuildTo((Integer) stonerLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.ironer).setBuildTo((Integer) ironerLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.market).setBuildTo((Integer) marketLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.docks).setBuildTo((Integer) docksLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.barracks).setBuildTo((Integer) barracksLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.wall).setBuildTo((Integer) wallLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.academy).setBuildTo((Integer) academyLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.temple).setBuildTo((Integer) templeLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.theater).setBuildTo((Integer) theaterLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.thermal).setBuildTo((Integer) thermalLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.library).setBuildTo((Integer) libraryLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.lighthouse).setBuildTo((Integer) lighthouseLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.tower).setBuildTo((Integer) towerLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.statue).setBuildTo((Integer) statueLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.oracle).setBuildTo((Integer) oracleLevelToBuild.getValue());
        town.getBuilding(Building.BuildingType.trade_office).setBuildTo((Integer) trade_officeLevelToBuild.getValue());
        //barrack troops
        // TODO add your handling code here:
        if (town.getBuilding(Building.BuildingType.barracks).getLevel() > 0 || town.getBarracks().getUnit(BarracksUnit.UnitType.sword) != null) {
            town.getBarracks().getUnit(BarracksUnit.UnitType.sword).setBuildTo((Integer) swordToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).setBuildTo((Integer) slingerToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.archer).setBuildTo((Integer) archerToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).setBuildTo((Integer) hopliteToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.rider).setBuildTo((Integer) riderToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).setBuildTo((Integer) chariotToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).setBuildTo((Integer) catapultToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).setBuildTo((Integer) minotaurToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).setBuildTo((Integer) manticoreToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).setBuildTo((Integer) centaurToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).setBuildTo((Integer) pegasusToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).setBuildTo((Integer) harpyToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).setBuildTo((Integer) medusaToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).setBuildTo((Integer) zyklopToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).setBuildTo((Integer) cerberusToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.fury).setBuildTo((Integer) furyToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).setBuildTo((Integer) griffinToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).setBuildTo((Integer) calydonian_boarToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).setBuildTo((Integer) godsentToBuild.getValue());
        } else {
            town.getBarracks().fillTroops();
            town.getBarracks().getUnit(BarracksUnit.UnitType.sword).setBuildTo((Integer) swordToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).setBuildTo((Integer) slingerToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.archer).setBuildTo((Integer) archerToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).setBuildTo((Integer) hopliteToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.rider).setBuildTo((Integer) riderToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).setBuildTo((Integer) chariotToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).setBuildTo((Integer) catapultToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).setBuildTo((Integer) minotaurToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).setBuildTo((Integer) manticoreToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).setBuildTo((Integer) centaurToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).setBuildTo((Integer) pegasusToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).setBuildTo((Integer) harpyToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).setBuildTo((Integer) medusaToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).setBuildTo((Integer) zyklopToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).setBuildTo((Integer) cerberusToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.fury).setBuildTo((Integer) furyToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).setBuildTo((Integer) griffinToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).setBuildTo((Integer) calydonian_boarToBuild.getValue());
            town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).setBuildTo((Integer) godsentToBuild.getValue());
//            log(Level.WARNING, "Barracks not loaded for the town! Can't save the troops.");
        }
        //docks troops
        if (town.getBuilding(Building.BuildingType.docks).getLevel() > 0 || town.getDocks().getUnit(DocksUnit.UnitType.big_transporter) != null) {
            town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).setBuildTo((Integer) big_transporterToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.bireme).setBuildTo((Integer) biremeToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).setBuildTo((Integer) demolition_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).setBuildTo((Integer) attack_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).setBuildTo((Integer) small_transporterToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).setBuildTo((Integer) colonize_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).setBuildTo((Integer) sea_monsterToBuild.getValue());
        } else {
//            log(Level.WARNING, "Docks not loaded for the town! Can't save the troops.");
            town.getDocks().fillTroops();
            town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).setBuildTo((Integer) big_transporterToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.bireme).setBuildTo((Integer) biremeToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).setBuildTo((Integer) demolition_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).setBuildTo((Integer) attack_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).setBuildTo((Integer) small_transporterToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).setBuildTo((Integer) colonize_shipToBuild.getValue());
            town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).setBuildTo((Integer) sea_monsterToBuild.getValue());
        }
    }

    private void templateSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String fileName = JOptionPane.showInputDialog(this, "This saves your current town as a template you can load for other towns.\n" +
                                                            "Make sure you saved your town before saving it as a template! \n" +
                                                            "Enter in your desired template name:");
        if (fileName != null) {
            System.out.println("Saving file");
            if (currentTownIndex == -1) {
                Saver.saveTemplate(towns.get(0), fileName);
            } else {
                Saver.saveTemplate(towns.get(currentTownIndex), fileName);
            }
        }
    }

    private void templateNextButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = templateComboBox.getItemCount();
        int currentIndex = templateComboBox.getSelectedIndex();
        if (currentIndex+1 < size) {
            templateComboBox.setSelectedIndex(currentIndex+1);
        }
    }

    private void templatePreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = templateComboBox.getSelectedIndex();
        if (currentIndex-1 >= 0) {
            templateComboBox.setSelectedIndex(currentIndex-1);
        }
    }

    private void templateLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentTemplateIndex = templateComboBox.getSelectedIndex();
        int currentTownIndex = jComboBox1.getSelectedIndex();
        Town currentTown = towns.get(currentTownIndex);
        Town templateTown = templateTowns.get(currentTemplateIndex);
        currentTown.setBarracks(templateTown.getBarracks());
        currentTown.setDocks(templateTown.getDocks());
        currentTown.setBuildingList(templateTown.getBuildingList());
        currentTown.setFarming(templateTown.getFarming());
    }

    public static ArrayList<Town> getTemplateTowns() {
        return templateTowns;
    }

    public static void setTemplateTowns(ArrayList<Town> templateTowns) {
        QueuePanel.templateTowns = templateTowns;
    }

    private String getImage(String imageLocation) {
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            logError(e);
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        return jarDir + imageLocation;
    }


    private javax.swing.JSpinner academyLevelToBuild;
    private javax.swing.JLabel academyPic;
    private javax.swing.JLabel archerPic;
    private javax.swing.JSpinner archerToBuild;
    private javax.swing.JLabel attack_shipPic;
    private javax.swing.JSpinner attack_shipToBuild;
    private javax.swing.JSpinner barracksLevelToBuild;
    private javax.swing.JLabel barracksPic;
    private javax.swing.JLabel big_transporterPic;
    private javax.swing.JSpinner big_transporterToBuild;
    private javax.swing.JLabel biremePic;
    private javax.swing.JSpinner biremeToBuild;
    private javax.swing.JLabel calydonian_boarPic;
    private javax.swing.JSpinner calydonian_boarToBuild;
    private javax.swing.JLabel catapultPic;
    private javax.swing.JSpinner catapultToBuild;
    private javax.swing.JLabel centaurPic;
    private javax.swing.JSpinner centaurToBuild;
    private javax.swing.JLabel cerberusPic;
    private javax.swing.JSpinner cerberusToBuild;
    private javax.swing.JLabel chariotPic;
    private javax.swing.JSpinner chariotToBuild;
    private javax.swing.JLabel colonize_shipPic;
    private javax.swing.JSpinner colonize_shipToBuild;
    private javax.swing.JLabel demolition_shipPic;
    private javax.swing.JSpinner demolition_shipToBuild;
    private javax.swing.JSpinner docksLevelToBuild;
    private javax.swing.JLabel docksPic;
    private javax.swing.JSpinner farmLevelToBuild;
    private javax.swing.JLabel farmPic;
    private javax.swing.JLabel furyPic;
    private javax.swing.JSpinner furyToBuild;
    private javax.swing.JLabel godsentPic;
    private javax.swing.JSpinner godsentToBuild;
    private javax.swing.JLabel griffinPic;
    private javax.swing.JSpinner griffinToBuild;
    private javax.swing.JLabel harpyPic;
    private javax.swing.JSpinner harpyToBuild;
    private javax.swing.JSpinner hideLevelToBuild;
    private javax.swing.JLabel hidePic;
    private javax.swing.JLabel hoplitePic;
    private javax.swing.JSpinner hopliteToBuild;
    private javax.swing.JSpinner ironerLevelToBuild;
    private javax.swing.JLabel ironerPic;
    private JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner libraryLevelToBuild;
    private javax.swing.JLabel libraryPic;
    private javax.swing.JSpinner lighthouseLevelToBuild;
    private javax.swing.JLabel lighthousePic;
    private javax.swing.JSpinner lumberLevelToBuild;
    private javax.swing.JLabel lumberPic;
    private javax.swing.JSpinner mainLevelToBuild;
    private javax.swing.JLabel mainPic;
    private javax.swing.JLabel manticorePic;
    private javax.swing.JSpinner manticoreToBuild;
    private javax.swing.JSpinner marketLevelToBuild;
    private javax.swing.JLabel marketPic;
    private javax.swing.JLabel medusaPic;
    private javax.swing.JSpinner medusaToBuild;
    private javax.swing.JLabel minotaurPic;
    private javax.swing.JSpinner minotaurToBuild;
    private javax.swing.JButton nextTownButton;
    private javax.swing.JSpinner oracleLevelToBuild;
    private javax.swing.JLabel oraclePic;
    private javax.swing.JLabel pegasusPic;
    private javax.swing.JSpinner pegasusToBuild;
    private javax.swing.JButton previousTownButton;
    private javax.swing.JLabel riderPic;
    private javax.swing.JSpinner riderToBuild;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel sea_monsterPic;
    private javax.swing.JSpinner sea_monsterToBuild;
    private javax.swing.JLabel slingerPic;
    private javax.swing.JSpinner slingerToBuild;
    private javax.swing.JLabel small_transporterPic;
    private javax.swing.JSpinner small_transporterToBuild;
    private javax.swing.JSpinner statueLevelToBuild;
    private javax.swing.JLabel statuePic;
    private javax.swing.JSpinner stonerLevelToBuild;
    private javax.swing.JLabel stonerPic;
    private javax.swing.JSpinner storageLevelToBuild;
    private javax.swing.JLabel storagePic;
    private javax.swing.JLabel swordPic;
    private javax.swing.JSpinner swordToBuild;
    private JComboBox<String> templateComboBox;
    private javax.swing.JButton templateLoadButton;
    private javax.swing.JButton templateNextButton;
    private javax.swing.JButton templatePreviousButton;
    private javax.swing.JButton templateSaveButton;
    private javax.swing.JSpinner templeLevelToBuild;
    private javax.swing.JLabel templePic;
    private javax.swing.JSpinner theaterLevelToBuild;
    private javax.swing.JLabel theaterPic;
    private javax.swing.JSpinner thermalLevelToBuild;
    private javax.swing.JLabel thermalPic;
    private javax.swing.JLabel triremePic;
    private javax.swing.JSpinner triremeToBuild;
    private javax.swing.JSpinner towerLevelToBuild;
    private javax.swing.JLabel towerPic;
    private javax.swing.JSpinner trade_officeLevelToBuild;
    private javax.swing.JLabel trade_officePic;
    private javax.swing.JSpinner wallLevelToBuild;
    private javax.swing.JLabel wallPic;
    private javax.swing.JLabel zyklopPic;
    private javax.swing.JSpinner zyklopToBuild;
}
