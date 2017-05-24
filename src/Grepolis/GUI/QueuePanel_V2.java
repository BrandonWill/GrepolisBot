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
import Grepolis.IO.Loader;
import Grepolis.IO.Saver;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;

/**
 * @author Brandon
 */
public class QueuePanel_V2 extends JPanel {
    private ArrayList<Town> towns;
    private static int currentTownIndex; //Used only for updating the buildings/troops when the tab is clicked
    private static ArrayList<Town> templateTowns;

    public QueuePanel_V2(ArrayList<Town> townList) {
        this.towns = townList;
        jComboBox1 = new JComboBox<>();
        for (Town town : towns) {
            jComboBox1.addItem(town.getName());
        }
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (towns.size() > 0 && jComboBox1.getItemCount() == towns.size() && jComboBox1.getSelectedIndex() >= 0) {
                    currentTownIndex = jComboBox1.getSelectedIndex();
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

        if (towns.size() > 0) {
            jComboBox1.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
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
        if (towns.size() > 0) {
            jComboBox1.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
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
            Platform.runLater(() -> {
                troopPanel.getSwordToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getBuildTo()));
                troopPanel.getSwordPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getTotalTroops()));
                troopPanel.getSwordPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.sword).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getSwordPic().setText(String.valueOf(0));
                troopPanel.getSwordPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getSwordToBuild().setText(String.valueOf(0));
            });
        }
        try {
            Platform.runLater(() -> {
                troopPanel.getSlingerToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getBuildTo()));
                troopPanel.getSlingerPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getTotalTroops()));
                troopPanel.getSlingerPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getSlingerPic().setText(String.valueOf(0));
                troopPanel.getSlingerPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getSlingerToBuild().setText(String.valueOf(0));
            });
        }
        try {
            Platform.runLater(() -> {
                troopPanel.getArcherToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getBuildTo()));
                troopPanel.getArcherPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getTotalTroops()));
                troopPanel.getArcherPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.archer).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getArcherPic().setText(String.valueOf(0));
                troopPanel.getArcherPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getArcherToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getHopliteToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getBuildTo()));
                troopPanel.getHoplitePic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getTotalTroops()));
                troopPanel.getHoplitePic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getHoplitePic().setText(String.valueOf(0));
                troopPanel.getHoplitePic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getHopliteToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getRiderToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getBuildTo()));
                troopPanel.getRiderPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getTotalTroops()));
                troopPanel.getRiderPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.rider).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getRiderPic().setText(String.valueOf(0));
                troopPanel.getRiderPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getRiderToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getChariotToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getBuildTo()));
                troopPanel.getChariotPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getTotalTroops()));
                troopPanel.getChariotPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getChariotPic().setText(String.valueOf(0));
                troopPanel.getChariotPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getChariotToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getCatapultToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getBuildTo()));
                troopPanel.getCatapultPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getTotalTroops()));
                troopPanel.getCatapultPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getCatapultPic().setText(String.valueOf(0));
                troopPanel.getCatapultPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getCatapultToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getMinotaurToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getBuildTo()));
                troopPanel.getMinotaurPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getTotalTroops()));
                troopPanel.getMinotaurPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getMinotaurPic().setText(String.valueOf(0));
                troopPanel.getMinotaurPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getMinotaurToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getManticoreToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getBuildTo()));
                troopPanel.getManticorePic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getTotalTroops()));
                troopPanel.getManticorePic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getManticorePic().setText(String.valueOf(0));
                troopPanel.getManticorePic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getManticoreToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getCentaurToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getBuildTo()));
                troopPanel.getCentaurPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getTotalTroops()));
                troopPanel.getCentaurPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getCentaurPic().setText(String.valueOf(0));
                troopPanel.getCentaurPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getCentaurToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getPegasusToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getBuildTo()));
                troopPanel.getPegasusPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getTotalTroops()));
                troopPanel.getPegasusPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getPegasusPic().setText(String.valueOf(0));
                troopPanel.getPegasusPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getPegasusToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getHarpyToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getBuildTo()));
                troopPanel.getHarpyPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getTotalTroops()));
                troopPanel.getHarpyPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getHarpyPic().setText(String.valueOf(0));
                troopPanel.getHarpyPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getHarpyToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getMedusaToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getBuildTo()));
                troopPanel.getMedusaPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getTotalTroops()));
                troopPanel.getMedusaPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getMedusaPic().setText(String.valueOf(0));
                troopPanel.getMedusaPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getMedusaToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getZyklopToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getBuildTo()));
                troopPanel.getZyklopPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getTotalTroops()));
                troopPanel.getZyklopPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getZyklopPic().setText(String.valueOf(0));
                troopPanel.getZyklopPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getZyklopToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getCerberusToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getBuildTo()));
                troopPanel.getCerberusPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getTotalTroops()));
                troopPanel.getCerberusPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getCerberusPic().setText(String.valueOf(0));
                troopPanel.getCerberusPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getCerberusToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getFuryToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getBuildTo()));
                troopPanel.getFuryPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getTotalTroops()));
                troopPanel.getFuryPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.fury).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getFuryPic().setText(String.valueOf(0));
                troopPanel.getFuryPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getFuryToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getGriffinToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getBuildTo()));
                troopPanel.getGriffinPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getTotalTroops()));
                troopPanel.getGriffinPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getGriffinPic().setText(String.valueOf(0));
                troopPanel.getGriffinPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getGriffinToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getCalydonian_boarToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getBuildTo()));
                troopPanel.getCalydonian_boarPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getTotalTroops()));
                troopPanel.getCalydonian_boarPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getCalydonian_boarPic().setText(String.valueOf(0));
                troopPanel.getCalydonian_boarPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getCalydonian_boarToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getGodsentToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getBuildTo()));
                troopPanel.getGodsentPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getTotalTroops()));
                troopPanel.getGodsentPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getTotalTroops() + "(" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getGodsentPic().setText(String.valueOf(0));
                troopPanel.getGodsentPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getGodsentToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getBig_transporterToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getBuildTo()));
                troopPanel.getBig_transporterPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getTotalTroops()));
                troopPanel.getBig_transporterPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getBig_transporterPic().setText(String.valueOf(0));
                troopPanel.getBig_transporterPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getBig_transporterToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getBiremeToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getBuildTo()));
                troopPanel.getBiremePic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getTotalTroops()));
                troopPanel.getBiremePic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.bireme).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getBiremePic().setText(String.valueOf(0));
                troopPanel.getBiremePic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getBiremeToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getAttack_shipToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getBuildTo()));
                troopPanel.getAttack_shipPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getTotalTroops()));
                troopPanel.getAttack_shipPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getAttack_shipPic().setText(String.valueOf(0));
                troopPanel.getAttack_shipPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getAttack_shipToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getDemolition_shipToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getBuildTo()));
                troopPanel.getDemolition_shipPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getTotalTroops()));
                troopPanel.getDemolition_shipPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getDemolition_shipPic().setText(String.valueOf(0));
                troopPanel.getDemolition_shipPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getDemolition_shipToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getSmall_transporterToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getBuildTo()));
                troopPanel.getSmall_transporterPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getTotalTroops()));
                troopPanel.getSmall_transporterPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getSmall_transporterPic().setText(String.valueOf(0));
                troopPanel.getSmall_transporterPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getSmall_transporterToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getTriremeToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getBuildTo()));
                troopPanel.getTriremePic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getTotalTroops()));
                troopPanel.getTriremePic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.trireme).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getTriremePic().setText(String.valueOf(0));
                troopPanel.getTriremePic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getTriremeToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getColonize_shipToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getBuildTo()));
                troopPanel.getColonize_shipPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getTotalTroops()));
                troopPanel.getColonize_shipPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getColonize_shipPic().setText(String.valueOf(0));
                troopPanel.getColonize_shipPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getColonize_shipToBuild().setText(String.valueOf(0));
            });
        }

        try {
            Platform.runLater(() -> {
                troopPanel.getSea_monsterToBuild().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getBuildTo()));
                troopPanel.getSea_monsterPic().setText(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getTotalTroops()));
                troopPanel.getSea_monsterPic().setTooltip(new Tooltip(String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getTotalTroops() + "(" + String.valueOf(town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).getInQueue() + " in queue)"))));
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getSea_monsterPic().setText(String.valueOf(0));
                troopPanel.getSea_monsterPic().setTooltip(new Tooltip(String.valueOf(0)));
                troopPanel.getSea_monsterToBuild().setText(String.valueOf(0));
            });
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
        troopPanel = new TroopPanel();
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

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1100, 130));

        jScrollPane1.setViewportView(troopPanel);

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
        if (currentIndex + 1 < size) {
            jComboBox1.setSelectedIndex(currentIndex + 1);
            currentTownIndex = currentIndex + 1;
            changeTown(towns.get(currentIndex + 1));
        }
    }

    private void previousTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = jComboBox1.getSelectedIndex();
        if (currentIndex - 1 >= 0) {
            jComboBox1.setSelectedIndex(currentIndex - 1);
            currentTownIndex = currentIndex - 1;
            changeTown(towns.get(currentIndex - 1));
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
            town.getBarracks().getUnit(BarracksUnit.UnitType.sword).setBuildTo(Integer.parseInt(troopPanel.getSwordToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).setBuildTo(Integer.parseInt(troopPanel.getSlingerToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.archer).setBuildTo(Integer.parseInt(troopPanel.getArcherToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).setBuildTo(Integer.parseInt(troopPanel.getHopliteToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.rider).setBuildTo(Integer.parseInt(troopPanel.getRiderToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).setBuildTo(Integer.parseInt(troopPanel.getChariotToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).setBuildTo(Integer.parseInt(troopPanel.getCatapultToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).setBuildTo(Integer.parseInt(troopPanel.getMinotaurToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).setBuildTo(Integer.parseInt(troopPanel.getManticoreToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).setBuildTo(Integer.parseInt(troopPanel.getCentaurToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).setBuildTo(Integer.parseInt(troopPanel.getPegasusToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).setBuildTo(Integer.parseInt(troopPanel.getHarpyToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).setBuildTo(Integer.parseInt(troopPanel.getMedusaToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).setBuildTo(Integer.parseInt(troopPanel.getZyklopToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).setBuildTo(Integer.parseInt(troopPanel.getCerberusToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.fury).setBuildTo(Integer.parseInt(troopPanel.getFuryToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).setBuildTo(Integer.parseInt(troopPanel.getGriffinToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).setBuildTo(Integer.parseInt(troopPanel.getCalydonian_boarToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).setBuildTo(Integer.parseInt(troopPanel.getGodsentToBuild().getText()));
        } else {
            town.getBarracks().fillTroops();
            town.getBarracks().getUnit(BarracksUnit.UnitType.sword).setBuildTo(Integer.parseInt(troopPanel.getSwordToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.slinger).setBuildTo(Integer.parseInt(troopPanel.getSlingerToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.archer).setBuildTo(Integer.parseInt(troopPanel.getArcherToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.hoplite).setBuildTo(Integer.parseInt(troopPanel.getHopliteToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.rider).setBuildTo(Integer.parseInt(troopPanel.getRiderToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.chariot).setBuildTo(Integer.parseInt(troopPanel.getChariotToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.catapult).setBuildTo(Integer.parseInt(troopPanel.getCatapultToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.minotaur).setBuildTo(Integer.parseInt(troopPanel.getMinotaurToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.manticore).setBuildTo(Integer.parseInt(troopPanel.getManticoreToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.centaur).setBuildTo(Integer.parseInt(troopPanel.getCentaurToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.pegasus).setBuildTo(Integer.parseInt(troopPanel.getPegasusToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.harpy).setBuildTo(Integer.parseInt(troopPanel.getHarpyToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.medusa).setBuildTo(Integer.parseInt(troopPanel.getMedusaToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.zyklop).setBuildTo(Integer.parseInt(troopPanel.getZyklopToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.cerberus).setBuildTo(Integer.parseInt(troopPanel.getCerberusToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.fury).setBuildTo(Integer.parseInt(troopPanel.getFuryToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.griffin).setBuildTo(Integer.parseInt(troopPanel.getGriffinToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.calydonian_boar).setBuildTo(Integer.parseInt(troopPanel.getCalydonian_boarToBuild().getText()));
            town.getBarracks().getUnit(BarracksUnit.UnitType.godsent).setBuildTo(Integer.parseInt(troopPanel.getGodsentToBuild().getText()));
//            log(Level.WARNING, "Barracks not loaded for the town! Can't save the troops.");
        }
        //docks troops
        if (town.getBuilding(Building.BuildingType.docks).getLevel() > 0 || town.getDocks().getUnit(DocksUnit.UnitType.big_transporter) != null) {
            town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).setBuildTo(Integer.parseInt(troopPanel.getBig_transporterToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.bireme).setBuildTo(Integer.parseInt(troopPanel.getBiremeToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).setBuildTo(Integer.parseInt(troopPanel.getDemolition_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).setBuildTo(Integer.parseInt(troopPanel.getAttack_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).setBuildTo(Integer.parseInt(troopPanel.getSmall_transporterToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.trireme).setBuildTo(Integer.parseInt(troopPanel.getTriremeToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).setBuildTo(Integer.parseInt(troopPanel.getColonize_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).setBuildTo(Integer.parseInt(troopPanel.getSea_monsterToBuild().getText()));
        } else {
//log(Level.WARNING,"Docksnotloadedforthetown!Can'tsavethetroops.");
            town.getDocks().fillTroops();
            town.getDocks().getUnit(DocksUnit.UnitType.big_transporter).setBuildTo(Integer.parseInt(troopPanel.getBig_transporterToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.bireme).setBuildTo(Integer.parseInt(troopPanel.getBiremeToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.demolition_ship).setBuildTo(Integer.parseInt(troopPanel.getDemolition_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.attack_ship).setBuildTo(Integer.parseInt(troopPanel.getAttack_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.small_transporter).setBuildTo(Integer.parseInt(troopPanel.getSmall_transporterToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.trireme).setBuildTo(Integer.parseInt(troopPanel.getTriremeToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.colonize_ship).setBuildTo(Integer.parseInt(troopPanel.getColonize_shipToBuild().getText()));
            town.getDocks().getUnit(DocksUnit.UnitType.sea_monster).setBuildTo(Integer.parseInt(troopPanel.getSea_monsterToBuild().getText()));
        }
    }

    private void templateSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String fileName = JOptionPane.showInputDialog(this, "This saves your current town as a template you can load for other towns.\n" +
                "Make sure you saved your town before saving it as a template! \n" +
                "Enter in your desired template name:");
        if (fileName != null) {
            //System.out.println("Saving file");
            Saver.saveTemplate(towns.get(currentTownIndex), fileName);
        }

        Loader.loadTemplateTowns();
        changeTown();
    }

    private void templateNextButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = templateComboBox.getItemCount();
        int currentIndex = templateComboBox.getSelectedIndex();
        if (currentIndex + 1 < size) {
            templateComboBox.setSelectedIndex(currentIndex + 1);
        }
    }

    private void templatePreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = templateComboBox.getSelectedIndex();
        if (currentIndex - 1 >= 0) {
            templateComboBox.setSelectedIndex(currentIndex - 1);
        }
    }

    private void templateLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentTemplateIndex = templateComboBox.getSelectedIndex();
        int currentTownIndex = jComboBox1.getSelectedIndex();
        Town currentTown = towns.get(currentTownIndex);
        Town templateTown = templateTowns.get(currentTemplateIndex);
        currentTown.setBarracks(templateTown.getBarracks());
        currentTown.getBarracks().setTown(currentTown);
        currentTown.setDocks(templateTown.getDocks());
        currentTown.getDocks().setTown(currentTown);
        currentTown.setBuildingList(templateTown.getBuildingList());
        currentTown.setFarming(templateTown.getFarming());
        currentTown.getFarming().setTown(currentTown);
        changeTown(currentTown);
    }

    public static ArrayList<Town> getTemplateTowns() {
        return templateTowns;
    }

    public static void setTemplateTowns(ArrayList<Town> templateTowns) {
        QueuePanel_V2.templateTowns = templateTowns;
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
    private javax.swing.JSpinner barracksLevelToBuild;
    private javax.swing.JLabel barracksPic;
    private javax.swing.JSpinner docksLevelToBuild;
    private javax.swing.JLabel docksPic;
    private javax.swing.JSpinner farmLevelToBuild;
    private javax.swing.JLabel farmPic;
    private javax.swing.JSpinner hideLevelToBuild;
    private javax.swing.JLabel hidePic;
    private javax.swing.JSpinner ironerLevelToBuild;
    private javax.swing.JLabel ironerPic;
    private JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private TroopPanel troopPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner libraryLevelToBuild;
    private javax.swing.JLabel libraryPic;
    private javax.swing.JSpinner lighthouseLevelToBuild;
    private javax.swing.JLabel lighthousePic;
    private javax.swing.JSpinner lumberLevelToBuild;
    private javax.swing.JLabel lumberPic;
    private javax.swing.JSpinner mainLevelToBuild;
    private javax.swing.JLabel mainPic;
    private javax.swing.JSpinner marketLevelToBuild;
    private javax.swing.JLabel marketPic;
    private javax.swing.JButton nextTownButton;
    private javax.swing.JSpinner oracleLevelToBuild;
    private javax.swing.JLabel oraclePic;
    private javax.swing.JButton previousTownButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JSpinner statueLevelToBuild;
    private javax.swing.JLabel statuePic;
    private javax.swing.JSpinner stonerLevelToBuild;
    private javax.swing.JLabel stonerPic;
    private javax.swing.JSpinner storageLevelToBuild;
    private javax.swing.JLabel storagePic;
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
    private javax.swing.JSpinner towerLevelToBuild;
    private javax.swing.JLabel towerPic;
    private javax.swing.JSpinner trade_officeLevelToBuild;
    private javax.swing.JLabel trade_officePic;
    private javax.swing.JSpinner wallLevelToBuild;
    private javax.swing.JLabel wallPic;
}
