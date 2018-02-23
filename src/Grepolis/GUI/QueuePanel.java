/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grepolis.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

import static Grepolis.util.Image.getImage;
import static Grepolis.util.MyLogger.log;
import static Grepolis.util.MyLogger.logError;

import Grepolis.*;
import Grepolis.IO.Loader;
import Grepolis.IO.Saver;
import Grepolis.util.Image;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * @author Brandon
 */
public class QueuePanel extends JPanel {
    private static ArrayList<Town> towns;
    private static int currentTownIndex; //Used only for updating the buildings/troops when the tab is clicked
    private static ArrayList<Town> templateTowns;

    public QueuePanel(ArrayList<Town> townList) {
        this.towns = townList;
        townComboBox = new JComboBox<>();
        for (Town town : towns) {
            townComboBox.addItem(town.getName());
        }
        townComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (towns.size() > 0 && townComboBox.getItemCount() == towns.size() && townComboBox.getSelectedIndex() >= 0) {
                    currentTownIndex = townComboBox.getSelectedIndex();
                    changeTown(towns.get(townComboBox.getSelectedIndex()));
                }
            }
        });

        townTemplateComboBox = new JComboBox<>();
        if (templateTowns != null) {
            for (Town town : templateTowns) {
                townTemplateComboBox.addItem(town.getName());
            }
        }
        initComponents();

        if (towns.size() > 0) {
            townComboBox.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
        }
    }

    public void changeTown() {
        townComboBox.removeAllItems();
        townTemplateComboBox.removeAllItems();
        for (Town town : towns) {
            townComboBox.addItem(town.getName());
        }
        if (templateTowns != null) {
            for (Town town : templateTowns) {
                townTemplateComboBox.addItem(town.getName());
            }
        }
        if (towns.size() > 0) {
            townComboBox.setSelectedIndex(currentTownIndex);
            changeTown(towns.get(currentTownIndex));
        }
    }

    public void changeTown(Town town) {
        //Update the building levels and building level to build
        try {
            Platform.runLater(() -> {
                buildingPanel.getAcademyLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.academy).getBuildTo());
                buildingPanel.getAcademyPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.academy).getLevel()));
                buildingPanel.getAcademyPic().setTextFill(town.getBuilding(Building.BuildingType.academy).getLevel() == town.getBuilding(Building.BuildingType.academy).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getAcademyLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getAcademyPic().setText(String.valueOf(0));
                buildingPanel.getAcademyPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getBarracksLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.barracks).getBuildTo());
                buildingPanel.getBarracksPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.barracks).getLevel()));
                buildingPanel.getBarracksPic().setTextFill(town.getBuilding(Building.BuildingType.barracks).getLevel() == town.getBuilding(Building.BuildingType.barracks).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getBarracksLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getBarracksPic().setText(String.valueOf(0));
                buildingPanel.getBarracksPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getDocksLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.docks).getBuildTo());
                buildingPanel.getDocksPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.docks).getLevel()));
                buildingPanel.getDocksPic().setTextFill(town.getBuilding(Building.BuildingType.docks).getLevel() == town.getBuilding(Building.BuildingType.docks).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getDocksLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getDocksPic().setText(String.valueOf(0));
                buildingPanel.getDocksPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getFarmLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.farm).getBuildTo());
                buildingPanel.getFarmPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.farm).getLevel()));
                buildingPanel.getFarmPic().setTextFill(town.getBuilding(Building.BuildingType.farm).getLevel() == town.getBuilding(Building.BuildingType.farm).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getFarmLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getFarmPic().setText(String.valueOf(0));
                buildingPanel.getFarmPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getHideLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.hide).getBuildTo());
                buildingPanel.getHidePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.hide).getLevel()));
                buildingPanel.getHidePic().setTextFill(town.getBuilding(Building.BuildingType.hide).getLevel() == town.getBuilding(Building.BuildingType.hide).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getHideLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getHidePic().setText(String.valueOf(0));
                buildingPanel.getHidePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getIronerLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.ironer).getBuildTo());
                buildingPanel.getIronerPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.ironer).getLevel()));
                buildingPanel.getIronerPic().setTextFill(town.getBuilding(Building.BuildingType.ironer).getLevel() == town.getBuilding(Building.BuildingType.ironer).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getIronerLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getIronerPic().setText(String.valueOf(0));
                buildingPanel.getIronerPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getLibraryLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.library).getBuildTo());
                buildingPanel.getLibraryPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.library).getLevel()));
                buildingPanel.getLibraryPic().setTextFill(town.getBuilding(Building.BuildingType.library).getLevel() == town.getBuilding(Building.BuildingType.library).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getLibraryLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getLibraryPic().setText(String.valueOf(0));
                buildingPanel.getLibraryPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getLighthouseLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.lighthouse).getBuildTo());
                buildingPanel.getLighthousePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.lighthouse).getLevel()));
                buildingPanel.getLighthousePic().setTextFill(town.getBuilding(Building.BuildingType.lighthouse).getLevel() == town.getBuilding(Building.BuildingType.lighthouse).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getLighthouseLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getLighthousePic().setText(String.valueOf(0));
                buildingPanel.getLighthousePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getLumberLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.lumber).getBuildTo());
                buildingPanel.getLumberPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.lumber).getLevel()));
                buildingPanel.getLumberPic().setTextFill(town.getBuilding(Building.BuildingType.lumber).getLevel() == town.getBuilding(Building.BuildingType.lumber).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getLumberLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getLumberPic().setText(String.valueOf(0));
                buildingPanel.getLumberPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getMainLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.main).getBuildTo());
                buildingPanel.getMainPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.main).getLevel()));
                buildingPanel.getMainPic().setTextFill(town.getBuilding(Building.BuildingType.main).getLevel() == town.getBuilding(Building.BuildingType.main).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getMainLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getMainPic().setText(String.valueOf(0));
                buildingPanel.getMainPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getMarketLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.market).getBuildTo());
                buildingPanel.getMarketPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.market).getLevel()));
                buildingPanel.getMarketPic().setTextFill(town.getBuilding(Building.BuildingType.market).getLevel() == town.getBuilding(Building.BuildingType.market).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getMarketLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getMarketPic().setText(String.valueOf(0));
                buildingPanel.getMarketPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getOracleLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.oracle).getBuildTo());
                buildingPanel.getOraclePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.oracle).getLevel()));
                buildingPanel.getOraclePic().setTextFill(town.getBuilding(Building.BuildingType.oracle).getLevel() == town.getBuilding(Building.BuildingType.oracle).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getOracleLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getOraclePic().setText(String.valueOf(0));
                buildingPanel.getOraclePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getStatueLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.statue).getBuildTo());
                buildingPanel.getStatuePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.statue).getLevel()));
                buildingPanel.getStatuePic().setTextFill(town.getBuilding(Building.BuildingType.statue).getLevel() == town.getBuilding(Building.BuildingType.statue).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getStatueLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getStatuePic().setText(String.valueOf(0));
                buildingPanel.getStatuePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getStonerLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.stoner).getBuildTo());
                buildingPanel.getStonerPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.stoner).getLevel()));
                buildingPanel.getStonerPic().setTextFill(town.getBuilding(Building.BuildingType.stoner).getLevel() == town.getBuilding(Building.BuildingType.stoner).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getStonerLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getStonerPic().setText(String.valueOf(0));
                buildingPanel.getStonerPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getStorageLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.storage).getBuildTo());
                buildingPanel.getStoragePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.storage).getLevel()));
                buildingPanel.getStoragePic().setTextFill(town.getBuilding(Building.BuildingType.storage).getLevel() == town.getBuilding(Building.BuildingType.storage).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getStorageLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getStoragePic().setText(String.valueOf(0));
                buildingPanel.getStoragePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getTempleLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.temple).getBuildTo());
                buildingPanel.getTemplePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.temple).getLevel()));
                buildingPanel.getTemplePic().setTextFill(town.getBuilding(Building.BuildingType.temple).getLevel() == town.getBuilding(Building.BuildingType.temple).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getTempleLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getTemplePic().setText(String.valueOf(0));
                buildingPanel.getTemplePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getTheaterLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.theater).getBuildTo());
                buildingPanel.getTheaterPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.theater).getLevel()));
                buildingPanel.getTheaterPic().setTextFill(town.getBuilding(Building.BuildingType.theater).getLevel() == town.getBuilding(Building.BuildingType.theater).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getTheaterLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getTheaterPic().setText(String.valueOf(0));
                buildingPanel.getTheaterPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getThermalLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.thermal).getBuildTo());
                buildingPanel.getThermalPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.thermal).getLevel()));
                buildingPanel.getThermalPic().setTextFill(town.getBuilding(Building.BuildingType.thermal).getLevel() == town.getBuilding(Building.BuildingType.thermal).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getThermalLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getThermalPic().setText(String.valueOf(0));
                buildingPanel.getThermalPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getTowerLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.tower).getBuildTo());
                buildingPanel.getTowerPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.tower).getLevel()));
                buildingPanel.getTowerPic().setTextFill(town.getBuilding(Building.BuildingType.tower).getLevel() == town.getBuilding(Building.BuildingType.tower).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getTowerLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getTowerPic().setText(String.valueOf(0));
                buildingPanel.getTowerPic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getTrade_officeLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.trade_office).getBuildTo());
                buildingPanel.getTrade_officePic().setText(String.valueOf(town.getBuilding(Building.BuildingType.trade_office).getLevel()));
                buildingPanel.getTrade_officePic().setTextFill(town.getBuilding(Building.BuildingType.trade_office).getLevel() == town.getBuilding(Building.BuildingType.trade_office).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getTrade_officeLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getTrade_officePic().setText(String.valueOf(0));
                buildingPanel.getTrade_officePic().setTextFill(Color.BLACK);
            });
        }

        try {
            Platform.runLater(() -> {
                buildingPanel.getWallLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType.wall).getBuildTo());
                buildingPanel.getWallPic().setText(String.valueOf(town.getBuilding(Building.BuildingType.wall).getLevel()));
                buildingPanel.getWallPic().setTextFill(town.getBuilding(Building.BuildingType.wall).getLevel() == town.getBuilding(Building.BuildingType.wall).getBuildTo() ? Color.GREEN : Color.RED);
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                buildingPanel.getWallLevelToBuild().getValueFactory().setValue(0);
                buildingPanel.getWallPic().setText(String.valueOf(0));
                buildingPanel.getWallPic().setTextFill(Color.BLACK);
            });
        }

        //update the troop count and the number of them to build
        try {
            Platform.runLater(() -> {
                troopPanel.getTroopsLabel().setText("Troops (available population " + town.getAvailablePopulation() +")");
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> {
                troopPanel.getTroopsLabel().setText("Troops (available population 0)");
            });
        }
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

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.slinger).shouldResearch()) {
                   academyPanel.getSlingerResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png")));
                } else {
                    ImageView tempSlingerResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png"));
                    tempSlingerResearchPic = Image.imageToGrayscale(tempSlingerResearchPic);
                    academyPanel.getSlingerResearchPic().setGraphic(tempSlingerResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.slinger)) {
                    academyPanel.getSlingerResearchPic().setText("Researched");
                    academyPanel.getSlingerResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getSlingerResearchPic().setText("Researched");
                    academyPanel.getSlingerResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempSlingerResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png"));
            tempSlingerResearchPic = Image.imageToGrayscale(tempSlingerResearchPic);
            academyPanel.getSlingerResearchPic().setGraphic(tempSlingerResearchPic);
            academyPanel.getSlingerResearchPic().setText("Researched");
            academyPanel.getSlingerResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.hoplite).shouldResearch()) {
                    academyPanel.getHopliteResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png")));
                } else {
                    ImageView tempHopliteResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png"));
                    tempHopliteResearchPic = Image.imageToGrayscale(tempHopliteResearchPic);
                    academyPanel.getHopliteResearchPic().setGraphic(tempHopliteResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.hoplite)) {
                    academyPanel.getHopliteResearchPic().setText("Researched");
                    academyPanel.getHopliteResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getHopliteResearchPic().setText("Researched");
                    academyPanel.getHopliteResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempHopliteResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png"));
            tempHopliteResearchPic = Image.imageToGrayscale(tempHopliteResearchPic);
            academyPanel.getHopliteResearchPic().setGraphic(tempHopliteResearchPic);
            academyPanel.getHopliteResearchPic().setText("Researched");
            academyPanel.getHopliteResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.espionage).shouldResearch()) {
                    academyPanel.getEspionageResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png")));
                } else {
                    ImageView tempEspionageResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png"));
                    tempEspionageResearchPic = Image.imageToGrayscale(tempEspionageResearchPic);
                    academyPanel.getEspionageResearchPic().setGraphic(tempEspionageResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.espionage)) {
                    academyPanel.getEspionageResearchPic().setText("Researched");
                    academyPanel.getEspionageResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getEspionageResearchPic().setText("Researched");
                    academyPanel.getEspionageResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempEspionageResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png"));
            tempEspionageResearchPic = Image.imageToGrayscale(tempEspionageResearchPic);
            academyPanel.getEspionageResearchPic().setGraphic(tempEspionageResearchPic);
            academyPanel.getEspionageResearchPic().setText("Researched");
            academyPanel.getEspionageResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.rider).shouldResearch()) {
                    academyPanel.getRiderResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png")));
                } else {
                    ImageView tempRiderResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png"));
                    tempRiderResearchPic = Image.imageToGrayscale(tempRiderResearchPic);
                    academyPanel.getRiderResearchPic().setGraphic(tempRiderResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.rider)) {
                    academyPanel.getRiderResearchPic().setText("Researched");
                    academyPanel.getRiderResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getRiderResearchPic().setText("Researched");
                    academyPanel.getRiderResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempRiderResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png"));
            tempRiderResearchPic = Image.imageToGrayscale(tempRiderResearchPic);
            academyPanel.getRiderResearchPic().setGraphic(tempRiderResearchPic);
            academyPanel.getRiderResearchPic().setText("Researched");
            academyPanel.getRiderResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.bireme).shouldResearch()) {
                    academyPanel.getBiremeResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png")));
                } else {
                    ImageView tempBiremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png"));
                    tempBiremeResearchPic = Image.imageToGrayscale(tempBiremeResearchPic);
                    academyPanel.getBiremeResearchPic().setGraphic(tempBiremeResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.bireme)) {
                    academyPanel.getBiremeResearchPic().setText("Researched");
                    academyPanel.getBiremeResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getBiremeResearchPic().setText("Researched");
                    academyPanel.getBiremeResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempBiremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png"));
            tempBiremeResearchPic = Image.imageToGrayscale(tempBiremeResearchPic);
            academyPanel.getBiremeResearchPic().setGraphic(tempBiremeResearchPic);
            academyPanel.getBiremeResearchPic().setText("Researched");
            academyPanel.getBiremeResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.chariot).shouldResearch()) {
                    academyPanel.getChariotResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png")));
                } else {
                    ImageView tempChariotResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png"));
                    tempChariotResearchPic = Image.imageToGrayscale(tempChariotResearchPic);
                    academyPanel.getChariotResearchPic().setGraphic(tempChariotResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.chariot)) {
                    academyPanel.getChariotResearchPic().setText("Researched");
                    academyPanel.getChariotResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getChariotResearchPic().setText("Researched");
                    academyPanel.getChariotResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempChariotResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png"));
            tempChariotResearchPic = Image.imageToGrayscale(tempChariotResearchPic);
            academyPanel.getChariotResearchPic().setGraphic(tempChariotResearchPic);
            academyPanel.getChariotResearchPic().setText("Researched");
            academyPanel.getChariotResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.demolition_ship).shouldResearch()) {
                    academyPanel.getDemolition_ShipResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png")));
                } else {
                    ImageView tempDemolition_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png"));
                    tempDemolition_ShipResearchPic = Image.imageToGrayscale(tempDemolition_ShipResearchPic);
                    academyPanel.getDemolition_ShipResearchPic().setGraphic(tempDemolition_ShipResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.demolition_ship)) {
                    academyPanel.getDemolition_ShipResearchPic().setText("Researched");
                    academyPanel.getDemolition_ShipResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getDemolition_ShipResearchPic().setText("Researched");
                    academyPanel.getDemolition_ShipResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempDemolition_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png"));
            tempDemolition_ShipResearchPic = Image.imageToGrayscale(tempDemolition_ShipResearchPic);
            academyPanel.getDemolition_ShipResearchPic().setGraphic(tempDemolition_ShipResearchPic);
            academyPanel.getDemolition_ShipResearchPic().setText("Researched");
            academyPanel.getDemolition_ShipResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.small_transporter).shouldResearch()) {
                    academyPanel.getSmall_TransporterResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png")));
                } else {
                    ImageView tempSmall_TransporterResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png"));
                    tempSmall_TransporterResearchPic = Image.imageToGrayscale(tempSmall_TransporterResearchPic);
                    academyPanel.getSmall_TransporterResearchPic().setGraphic(tempSmall_TransporterResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.small_transporter)) {
                    academyPanel.getSmall_TransporterResearchPic().setText("Researched");
                    academyPanel.getSmall_TransporterResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getSmall_TransporterResearchPic().setText("Researched");
                    academyPanel.getSmall_TransporterResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempSmall_TransporterResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png"));
            tempSmall_TransporterResearchPic = Image.imageToGrayscale(tempSmall_TransporterResearchPic);
            academyPanel.getSmall_TransporterResearchPic().setGraphic(tempSmall_TransporterResearchPic);
            academyPanel.getSmall_TransporterResearchPic().setText("Researched");
            academyPanel.getSmall_TransporterResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.trireme).shouldResearch()) {
                    academyPanel.getTriremeResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png")));
                } else {
                    ImageView tempTriremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png"));
                    tempTriremeResearchPic = Image.imageToGrayscale(tempTriremeResearchPic);
                    academyPanel.getTriremeResearchPic().setGraphic(tempTriremeResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.trireme)) {
                    academyPanel.getTriremeResearchPic().setText("Researched");
                    academyPanel.getTriremeResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getTriremeResearchPic().setText("Researched");
                    academyPanel.getTriremeResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempTriremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png"));
            tempTriremeResearchPic = Image.imageToGrayscale(tempTriremeResearchPic);
            academyPanel.getTriremeResearchPic().setGraphic(tempTriremeResearchPic);
            academyPanel.getTriremeResearchPic().setText("Researched");
            academyPanel.getTriremeResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.ram).shouldResearch()) {
                    academyPanel.getRamResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png")));
                } else {
                    ImageView tempRamResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png"));
                    tempRamResearchPic = Image.imageToGrayscale(tempRamResearchPic);
                    academyPanel.getRamResearchPic().setGraphic(tempRamResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.ram)) {
                    academyPanel.getRamResearchPic().setText("Researched");
                    academyPanel.getRamResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getRamResearchPic().setText("Researched");
                    academyPanel.getRamResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempRamResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png"));
            tempRamResearchPic = Image.imageToGrayscale(tempRamResearchPic);
            academyPanel.getRamResearchPic().setGraphic(tempRamResearchPic);
            academyPanel.getRamResearchPic().setText("Researched");
            academyPanel.getRamResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.stone_storm).shouldResearch()) {
                    academyPanel.getStone_StormResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_storm.png")));
                } else {
                    ImageView tempStone_StormResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_storm.png"));
                    tempStone_StormResearchPic = Image.imageToGrayscale(tempStone_StormResearchPic);
                    academyPanel.getStone_StormResearchPic().setGraphic(tempStone_StormResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.stone_storm)) {
                    academyPanel.getStone_StormResearchPic().setText("Researched");
                    academyPanel.getStone_StormResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getStone_StormResearchPic().setText("Researched");
                    academyPanel.getStone_StormResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempStone_StormResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_storm.png"));
            tempStone_StormResearchPic = Image.imageToGrayscale(tempStone_StormResearchPic);
            academyPanel.getStone_StormResearchPic().setGraphic(tempStone_StormResearchPic);
            academyPanel.getStone_StormResearchPic().setText("Researched");
            academyPanel.getStone_StormResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.combat_experience).shouldResearch()) {
                    academyPanel.getCombat_ExperienceResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png")));
                } else {
                    ImageView tempCombat_ExperienceResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png"));
                    tempCombat_ExperienceResearchPic = Image.imageToGrayscale(tempCombat_ExperienceResearchPic);
                    academyPanel.getCombat_ExperienceResearchPic().setGraphic(tempCombat_ExperienceResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.combat_experience)) {
                    academyPanel.getCombat_ExperienceResearchPic().setText("Researched");
                    academyPanel.getCombat_ExperienceResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getCombat_ExperienceResearchPic().setText("Researched");
                    academyPanel.getCombat_ExperienceResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempCombat_ExperienceResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png"));
            tempCombat_ExperienceResearchPic = Image.imageToGrayscale(tempCombat_ExperienceResearchPic);
            academyPanel.getCombat_ExperienceResearchPic().setGraphic(tempCombat_ExperienceResearchPic);
            academyPanel.getCombat_ExperienceResearchPic().setText("Researched");
            academyPanel.getCombat_ExperienceResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.archer).shouldResearch()) {
                    academyPanel.getArcherResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png")));
                } else {
                    ImageView tempArcherResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png"));
                    tempArcherResearchPic = Image.imageToGrayscale(tempArcherResearchPic);
                    academyPanel.getArcherResearchPic().setGraphic(tempArcherResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.archer)) {
                    academyPanel.getArcherResearchPic().setText("Researched");
                    academyPanel.getArcherResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getArcherResearchPic().setText("Researched");
                    academyPanel.getArcherResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempArcherResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png"));
            tempArcherResearchPic = Image.imageToGrayscale(tempArcherResearchPic);
            academyPanel.getArcherResearchPic().setGraphic(tempArcherResearchPic);
            academyPanel.getArcherResearchPic().setText("Researched");
            academyPanel.getArcherResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.meteorology).shouldResearch()) {
                    academyPanel.getMeteorologyResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png")));
                } else {
                    ImageView tempMeteorologyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png"));
                    tempMeteorologyResearchPic = Image.imageToGrayscale(tempMeteorologyResearchPic);
                    academyPanel.getMeteorologyResearchPic().setGraphic(tempMeteorologyResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.meteorology)) {
                    academyPanel.getMeteorologyResearchPic().setText("Researched");
                    academyPanel.getMeteorologyResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getMeteorologyResearchPic().setText("Researched");
                    academyPanel.getMeteorologyResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempMeteorologyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png"));
            tempMeteorologyResearchPic = Image.imageToGrayscale(tempMeteorologyResearchPic);
            academyPanel.getMeteorologyResearchPic().setGraphic(tempMeteorologyResearchPic);
            academyPanel.getMeteorologyResearchPic().setText("Researched");
            academyPanel.getMeteorologyResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.booty).shouldResearch()) {
                    academyPanel.getBootyResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png")));
                } else {
                    ImageView tempBootyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png"));
                    tempBootyResearchPic = Image.imageToGrayscale(tempBootyResearchPic);
                    academyPanel.getBootyResearchPic().setGraphic(tempBootyResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.booty)) {
                    academyPanel.getBootyResearchPic().setText("Researched");
                    academyPanel.getBootyResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getBootyResearchPic().setText("Researched");
                    academyPanel.getBootyResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempBootyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png"));
            tempBootyResearchPic = Image.imageToGrayscale(tempBootyResearchPic);
            academyPanel.getBootyResearchPic().setGraphic(tempBootyResearchPic);
            academyPanel.getBootyResearchPic().setText("Researched");
            academyPanel.getBootyResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.architecture).shouldResearch()) {
                    academyPanel.getArchitectureResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png")));
                } else {
                    ImageView tempArchitectureResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png"));
                    tempArchitectureResearchPic = Image.imageToGrayscale(tempArchitectureResearchPic);
                    academyPanel.getArchitectureResearchPic().setGraphic(tempArchitectureResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.architecture)) {
                    academyPanel.getArchitectureResearchPic().setText("Researched");
                    academyPanel.getArchitectureResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getArchitectureResearchPic().setText("Researched");
                    academyPanel.getArchitectureResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempArchitectureResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png"));
            tempArchitectureResearchPic = Image.imageToGrayscale(tempArchitectureResearchPic);
            academyPanel.getArchitectureResearchPic().setGraphic(tempArchitectureResearchPic);
            academyPanel.getArchitectureResearchPic().setText("Researched");
            academyPanel.getArchitectureResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.building_crane).shouldResearch()) {
                    academyPanel.getBuilding_CraneResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png")));
                } else {
                    ImageView tempBuilding_CraneResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png"));
                    tempBuilding_CraneResearchPic = Image.imageToGrayscale(tempBuilding_CraneResearchPic);
                    academyPanel.getBuilding_CraneResearchPic().setGraphic(tempBuilding_CraneResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.building_crane)) {
                    academyPanel.getBuilding_CraneResearchPic().setText("Researched");
                    academyPanel.getBuilding_CraneResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getBuilding_CraneResearchPic().setText("Researched");
                    academyPanel.getBuilding_CraneResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempBuilding_CraneResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png"));
            tempBuilding_CraneResearchPic = Image.imageToGrayscale(tempBuilding_CraneResearchPic);
            academyPanel.getBuilding_CraneResearchPic().setGraphic(tempBuilding_CraneResearchPic);
            academyPanel.getBuilding_CraneResearchPic().setText("Researched");
            academyPanel.getBuilding_CraneResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.attack_ship).shouldResearch()) {
                    academyPanel.getAttack_ShipResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png")));
                } else {
                    ImageView tempAttack_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png"));
                    tempAttack_ShipResearchPic = Image.imageToGrayscale(tempAttack_ShipResearchPic);
                    academyPanel.getAttack_ShipResearchPic().setGraphic(tempAttack_ShipResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.attack_ship)) {
                    academyPanel.getAttack_ShipResearchPic().setText("Researched");
                    academyPanel.getAttack_ShipResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getAttack_ShipResearchPic().setText("Researched");
                    academyPanel.getAttack_ShipResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempAttack_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png"));
            tempAttack_ShipResearchPic = Image.imageToGrayscale(tempAttack_ShipResearchPic);
            academyPanel.getAttack_ShipResearchPic().setGraphic(tempAttack_ShipResearchPic);
            academyPanel.getAttack_ShipResearchPic().setText("Researched");
            academyPanel.getAttack_ShipResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.catapult).shouldResearch()) {
                    academyPanel.getCatapultResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png")));
                } else {
                    ImageView tempCatapultResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png"));
                    tempCatapultResearchPic = Image.imageToGrayscale(tempCatapultResearchPic);
                    academyPanel.getCatapultResearchPic().setGraphic(tempCatapultResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.catapult)) {
                    academyPanel.getCatapultResearchPic().setText("Researched");
                    academyPanel.getCatapultResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getCatapultResearchPic().setText("Researched");
                    academyPanel.getCatapultResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempCatapultResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png"));
            tempCatapultResearchPic = Image.imageToGrayscale(tempCatapultResearchPic);
            academyPanel.getCatapultResearchPic().setGraphic(tempCatapultResearchPic);
            academyPanel.getCatapultResearchPic().setText("Researched");
            academyPanel.getCatapultResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.plow).shouldResearch()) {
                    academyPanel.getPlowResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png")));
                } else {
                    ImageView tempPlowResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png"));
                    tempPlowResearchPic = Image.imageToGrayscale(tempPlowResearchPic);
                    academyPanel.getPlowResearchPic().setGraphic(tempPlowResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.plow)) {
                    academyPanel.getPlowResearchPic().setText("Researched");
                    academyPanel.getPlowResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getPlowResearchPic().setText("Researched");
                    academyPanel.getPlowResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempPlowResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png"));
            tempPlowResearchPic = Image.imageToGrayscale(tempPlowResearchPic);
            academyPanel.getPlowResearchPic().setGraphic(tempPlowResearchPic);
            academyPanel.getPlowResearchPic().setText("Researched");
            academyPanel.getPlowResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.phalanx).shouldResearch()) {
                    academyPanel.getPhalanxResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png")));
                } else {
                    ImageView tempPhalanxResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png"));
                    tempPhalanxResearchPic = Image.imageToGrayscale(tempPhalanxResearchPic);
                    academyPanel.getPhalanxResearchPic().setGraphic(tempPhalanxResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.phalanx)) {
                    academyPanel.getPhalanxResearchPic().setText("Researched");
                    academyPanel.getPhalanxResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getPhalanxResearchPic().setText("Researched");
                    academyPanel.getPhalanxResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempPhalanxResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png"));
            tempPhalanxResearchPic = Image.imageToGrayscale(tempPhalanxResearchPic);
            academyPanel.getPhalanxResearchPic().setGraphic(tempPhalanxResearchPic);
            academyPanel.getPhalanxResearchPic().setText("Researched");
            academyPanel.getPhalanxResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.cartography).shouldResearch()) {
                    academyPanel.getCartographyResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png")));
                } else {
                    ImageView tempCartographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png"));
                    tempCartographyResearchPic = Image.imageToGrayscale(tempCartographyResearchPic);
                    academyPanel.getCartographyResearchPic().setGraphic(tempCartographyResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.cartography)) {
                    academyPanel.getCartographyResearchPic().setText("Researched");
                    academyPanel.getCartographyResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getCartographyResearchPic().setText("Researched");
                    academyPanel.getCartographyResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempCartographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png"));
            tempCartographyResearchPic = Image.imageToGrayscale(tempCartographyResearchPic);
            academyPanel.getCartographyResearchPic().setGraphic(tempCartographyResearchPic);
            academyPanel.getCartographyResearchPic().setText("Researched");
            academyPanel.getCartographyResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.temple_looting).shouldResearch()) {
                    academyPanel.getTemple_LootingResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png")));
                } else {
                    ImageView tempTemple_LootingResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png"));
                    tempTemple_LootingResearchPic = Image.imageToGrayscale(tempTemple_LootingResearchPic);
                    academyPanel.getTemple_LootingResearchPic().setGraphic(tempTemple_LootingResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.temple_looting)) {
                    academyPanel.getTemple_LootingResearchPic().setText("Researched");
                    academyPanel.getTemple_LootingResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getTemple_LootingResearchPic().setText("Researched");
                    academyPanel.getTemple_LootingResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempTemple_LootingResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png"));
            tempTemple_LootingResearchPic = Image.imageToGrayscale(tempTemple_LootingResearchPic);
            academyPanel.getTemple_LootingResearchPic().setGraphic(tempTemple_LootingResearchPic);
            academyPanel.getTemple_LootingResearchPic().setText("Researched");
            academyPanel.getTemple_LootingResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.strong_wine).shouldResearch()) {
                    academyPanel.getStrong_WineResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png")));
                } else {
                    ImageView tempStrong_WineResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png"));
                    tempStrong_WineResearchPic = Image.imageToGrayscale(tempStrong_WineResearchPic);
                    academyPanel.getStrong_WineResearchPic().setGraphic(tempStrong_WineResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.strong_wine)) {
                    academyPanel.getStrong_WineResearchPic().setText("Researched");
                    academyPanel.getStrong_WineResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getStrong_WineResearchPic().setText("Researched");
                    academyPanel.getStrong_WineResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempStrong_WineResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png"));
            tempStrong_WineResearchPic = Image.imageToGrayscale(tempStrong_WineResearchPic);
            academyPanel.getStrong_WineResearchPic().setGraphic(tempStrong_WineResearchPic);
            academyPanel.getStrong_WineResearchPic().setText("Researched");
            academyPanel.getStrong_WineResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.town_guard).shouldResearch()) {
                    academyPanel.getTown_GuardResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png")));
                } else {
                    ImageView tempTown_GuardResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png"));
                    tempTown_GuardResearchPic = Image.imageToGrayscale(tempTown_GuardResearchPic);
                    academyPanel.getTown_GuardResearchPic().setGraphic(tempTown_GuardResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.town_guard)) {
                    academyPanel.getTown_GuardResearchPic().setText("Researched");
                    academyPanel.getTown_GuardResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getTown_GuardResearchPic().setText("Researched");
                    academyPanel.getTown_GuardResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempTown_GuardResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png"));
            tempTown_GuardResearchPic = Image.imageToGrayscale(tempTown_GuardResearchPic);
            academyPanel.getTown_GuardResearchPic().setGraphic(tempTown_GuardResearchPic);
            academyPanel.getTown_GuardResearchPic().setText("Researched");
            academyPanel.getTown_GuardResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.pottery).shouldResearch()) {
                    academyPanel.getPotteryResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png")));
                } else {
                    ImageView tempPotteryResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png"));
                    tempPotteryResearchPic = Image.imageToGrayscale(tempPotteryResearchPic);
                    academyPanel.getPotteryResearchPic().setGraphic(tempPotteryResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.pottery)) {
                    academyPanel.getPotteryResearchPic().setText("Researched");
                    academyPanel.getPotteryResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getPotteryResearchPic().setText("Researched");
                    academyPanel.getPotteryResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempPotteryResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png"));
            tempPotteryResearchPic = Image.imageToGrayscale(tempPotteryResearchPic);
            academyPanel.getPotteryResearchPic().setGraphic(tempPotteryResearchPic);
            academyPanel.getPotteryResearchPic().setText("Researched");
            academyPanel.getPotteryResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.instructor).shouldResearch()) {
                    academyPanel.getInstructorResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png")));
                } else {
                    ImageView tempInstructorResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png"));
                    tempInstructorResearchPic = Image.imageToGrayscale(tempInstructorResearchPic);
                    academyPanel.getInstructorResearchPic().setGraphic(tempInstructorResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.instructor)) {
                    academyPanel.getInstructorResearchPic().setText("Researched");
                    academyPanel.getInstructorResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getInstructorResearchPic().setText("Researched");
                    academyPanel.getInstructorResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempInstructorResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png"));
            tempInstructorResearchPic = Image.imageToGrayscale(tempInstructorResearchPic);
            academyPanel.getInstructorResearchPic().setGraphic(tempInstructorResearchPic);
            academyPanel.getInstructorResearchPic().setText("Researched");
            academyPanel.getInstructorResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.shipwright).shouldResearch()) {
                    academyPanel.getShipwrightResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png")));
                } else {
                    ImageView tempShipwrightResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png"));
                    tempShipwrightResearchPic = Image.imageToGrayscale(tempShipwrightResearchPic);
                    academyPanel.getShipwrightResearchPic().setGraphic(tempShipwrightResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.shipwright)) {
                    academyPanel.getShipwrightResearchPic().setText("Researched");
                    academyPanel.getShipwrightResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getShipwrightResearchPic().setText("Researched");
                    academyPanel.getShipwrightResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempShipwrightResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png"));
            tempShipwrightResearchPic = Image.imageToGrayscale(tempShipwrightResearchPic);
            academyPanel.getShipwrightResearchPic().setGraphic(tempShipwrightResearchPic);
            academyPanel.getShipwrightResearchPic().setText("Researched");
            academyPanel.getShipwrightResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.conscription).shouldResearch()) {
                    academyPanel.getConscriptionResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png")));
                } else {
                    ImageView tempConscriptionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png"));
                    tempConscriptionResearchPic = Image.imageToGrayscale(tempConscriptionResearchPic);
                    academyPanel.getConscriptionResearchPic().setGraphic(tempConscriptionResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.conscription)) {
                    academyPanel.getConscriptionResearchPic().setText("Researched");
                    academyPanel.getConscriptionResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getConscriptionResearchPic().setText("Researched");
                    academyPanel.getConscriptionResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempConscriptionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png"));
            tempConscriptionResearchPic = Image.imageToGrayscale(tempConscriptionResearchPic);
            academyPanel.getConscriptionResearchPic().setGraphic(tempConscriptionResearchPic);
            academyPanel.getConscriptionResearchPic().setText("Researched");
            academyPanel.getConscriptionResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.cryptography).shouldResearch()) {
                    academyPanel.getCryptographyResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png")));
                } else {
                    ImageView tempCryptographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png"));
                    tempCryptographyResearchPic = Image.imageToGrayscale(tempCryptographyResearchPic);
                    academyPanel.getCryptographyResearchPic().setGraphic(tempCryptographyResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.cryptography)) {
                    academyPanel.getCryptographyResearchPic().setText("Researched");
                    academyPanel.getCryptographyResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getCryptographyResearchPic().setText("Researched");
                    academyPanel.getCryptographyResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempCryptographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png"));
            tempCryptographyResearchPic = Image.imageToGrayscale(tempCryptographyResearchPic);
            academyPanel.getCryptographyResearchPic().setGraphic(tempCryptographyResearchPic);
            academyPanel.getCryptographyResearchPic().setText("Researched");
            academyPanel.getCryptographyResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.berth).shouldResearch()) {
                    academyPanel.getBerthResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png")));
                } else {
                    ImageView tempBerthResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png"));
                    tempBerthResearchPic = Image.imageToGrayscale(tempBerthResearchPic);
                    academyPanel.getBerthResearchPic().setGraphic(tempBerthResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.berth)) {
                    academyPanel.getBerthResearchPic().setText("Researched");
                    academyPanel.getBerthResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getBerthResearchPic().setText("Researched");
                    academyPanel.getBerthResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempBerthResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png"));
            tempBerthResearchPic = Image.imageToGrayscale(tempBerthResearchPic);
            academyPanel.getBerthResearchPic().setGraphic(tempBerthResearchPic);
            academyPanel.getBerthResearchPic().setText("Researched");
            academyPanel.getBerthResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.breach).shouldResearch()) {
                    academyPanel.getBreachResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png")));
                } else {
                    ImageView tempBreachResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png"));
                    tempBreachResearchPic = Image.imageToGrayscale(tempBreachResearchPic);
                    academyPanel.getBreachResearchPic().setGraphic(tempBreachResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.breach)) {
                    academyPanel.getBreachResearchPic().setText("Researched");
                    academyPanel.getBreachResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getBreachResearchPic().setText("Researched");
                    academyPanel.getBreachResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempBreachResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png"));
            tempBreachResearchPic = Image.imageToGrayscale(tempBreachResearchPic);
            academyPanel.getBreachResearchPic().setGraphic(tempBreachResearchPic);
            academyPanel.getBreachResearchPic().setText("Researched");
            academyPanel.getBreachResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.take_over).shouldResearch()) {
                    academyPanel.getTake_OverResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png")));
                } else {
                    ImageView tempTake_OverResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png"));
                    tempTake_OverResearchPic = Image.imageToGrayscale(tempTake_OverResearchPic);
                    academyPanel.getTake_OverResearchPic().setGraphic(tempTake_OverResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.take_over)) {
                    academyPanel.getTake_OverResearchPic().setText("Researched");
                    academyPanel.getTake_OverResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getTake_OverResearchPic().setText("Researched");
                    academyPanel.getTake_OverResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempTake_OverResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png"));
            tempTake_OverResearchPic = Image.imageToGrayscale(tempTake_OverResearchPic);
            academyPanel.getTake_OverResearchPic().setGraphic(tempTake_OverResearchPic);
            academyPanel.getTake_OverResearchPic().setText("Researched");
            academyPanel.getTake_OverResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.divine_selection).shouldResearch()) {
                    academyPanel.getDivine_SelectionResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png")));
                } else {
                    ImageView tempDivine_SelectionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png"));
                    tempDivine_SelectionResearchPic = Image.imageToGrayscale(tempDivine_SelectionResearchPic);
                    academyPanel.getDivine_SelectionResearchPic().setGraphic(tempDivine_SelectionResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.divine_selection)) {
                    academyPanel.getDivine_SelectionResearchPic().setText("Researched");
                    academyPanel.getDivine_SelectionResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getDivine_SelectionResearchPic().setText("Researched");
                    academyPanel.getDivine_SelectionResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempDivine_SelectionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png"));
            tempDivine_SelectionResearchPic = Image.imageToGrayscale(tempDivine_SelectionResearchPic);
            academyPanel.getDivine_SelectionResearchPic().setGraphic(tempDivine_SelectionResearchPic);
            academyPanel.getDivine_SelectionResearchPic().setText("Researched");
            academyPanel.getDivine_SelectionResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.set_sail).shouldResearch()) {
                    academyPanel.getSet_SailResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png")));
                } else {
                    ImageView tempSet_SailResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png"));
                    tempSet_SailResearchPic = Image.imageToGrayscale(tempSet_SailResearchPic);
                    academyPanel.getSet_SailResearchPic().setGraphic(tempSet_SailResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.set_sail)) {
                    academyPanel.getSet_SailResearchPic().setText("Researched");
                    academyPanel.getSet_SailResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getSet_SailResearchPic().setText("Researched");
                    academyPanel.getSet_SailResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempSet_SailResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png"));
            tempSet_SailResearchPic = Image.imageToGrayscale(tempSet_SailResearchPic);
            academyPanel.getSet_SailResearchPic().setGraphic(tempSet_SailResearchPic);
            academyPanel.getSet_SailResearchPic().setText("Researched");
            academyPanel.getSet_SailResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.colonize_ship).shouldResearch()) {
                    academyPanel.getColonize_ShipResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png")));
                } else {
                    ImageView tempColonize_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png"));
                    tempColonize_ShipResearchPic = Image.imageToGrayscale(tempColonize_ShipResearchPic);
                    academyPanel.getColonize_ShipResearchPic().setGraphic(tempColonize_ShipResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.colonize_ship)) {
                    academyPanel.getColonize_ShipResearchPic().setText("Researched");
                    academyPanel.getColonize_ShipResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getColonize_ShipResearchPic().setText("Researched");
                    academyPanel.getColonize_ShipResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempColonize_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png"));
            tempColonize_ShipResearchPic = Image.imageToGrayscale(tempColonize_ShipResearchPic);
            academyPanel.getColonize_ShipResearchPic().setGraphic(tempColonize_ShipResearchPic);
            academyPanel.getColonize_ShipResearchPic().setText("Researched");
            academyPanel.getColonize_ShipResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.democracy).shouldResearch()) {
                    academyPanel.getDemocracyResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png")));
                } else {
                    ImageView tempDemocracyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png"));
                    tempDemocracyResearchPic = Image.imageToGrayscale(tempDemocracyResearchPic);
                    academyPanel.getDemocracyResearchPic().setGraphic(tempDemocracyResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.democracy)) {
                    academyPanel.getDemocracyResearchPic().setText("Researched");
                    academyPanel.getDemocracyResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getDemocracyResearchPic().setText("Researched");
                    academyPanel.getDemocracyResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempDemocracyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png"));
            tempDemocracyResearchPic = Image.imageToGrayscale(tempDemocracyResearchPic);
            academyPanel.getDemocracyResearchPic().setGraphic(tempDemocracyResearchPic);
            academyPanel.getDemocracyResearchPic().setText("Researched");
            academyPanel.getDemocracyResearchPic().setTextFill(Color.RED);
        }

        try {
            Platform.runLater(() -> {
                if (town.getAcademy().getResearch(AcademyResearch.ResearchType.mathematics).shouldResearch()) {
                    academyPanel.getMathematicsResearchPic().setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png")));
                } else {
                    ImageView tempMathematicsResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png"));
                    tempMathematicsResearchPic = Image.imageToGrayscale(tempMathematicsResearchPic);
                    academyPanel.getMathematicsResearchPic().setGraphic(tempMathematicsResearchPic);
                }
                if (town.getAcademy().isResearched(AcademyResearch.ResearchType.mathematics)) {
                    academyPanel.getMathematicsResearchPic().setText("Researched");
                    academyPanel.getMathematicsResearchPic().setTextFill(Color.GREEN);
                } else {
                    academyPanel.getMathematicsResearchPic().setText("Researched");
                    academyPanel.getMathematicsResearchPic().setTextFill(Color.RED);
                }
            });

        } catch (Exception ignored) {
            ImageView tempMathematicsResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png"));
            tempMathematicsResearchPic = Image.imageToGrayscale(tempMathematicsResearchPic);
            academyPanel.getMathematicsResearchPic().setGraphic(tempMathematicsResearchPic);
            academyPanel.getMathematicsResearchPic().setText("Researched");
            academyPanel.getMathematicsResearchPic().setTextFill(Color.RED);
        }
    }

    private void initComponents() {

        townLabel = new javax.swing.JLabel();
        nextTownButton = new javax.swing.JButton();
        previousTownButton = new javax.swing.JButton();
        buildingPanel = new BuildingPanel();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        troopPanel = new TroopPanel();
        academyPanel = new AcademyPanel();
        templateSaveButton = new javax.swing.JButton();
        templateNextButton = new javax.swing.JButton();
        templatePreviousButton = new javax.swing.JButton();
        townTemplateLabel = new javax.swing.JLabel();
        templateLoadButton = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        townLabel.setText("Towns");

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

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

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

        townTemplateLabel.setText("Town Templates");

        templateLoadButton.setText("Load");
        templateLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateLoadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout troopPanelLayout = new javax.swing.GroupLayout(troopPanel);
        troopPanel.setLayout(troopPanelLayout);
        troopPanelLayout.setHorizontalGroup(
                troopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        troopPanelLayout.setVerticalGroup(
                troopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout buildingPanelLayout = new javax.swing.GroupLayout(buildingPanel);
        buildingPanel.setLayout(buildingPanelLayout);
        buildingPanelLayout.setHorizontalGroup(
                buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        buildingPanelLayout.setVerticalGroup(
                buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout academyPanelLayout = new javax.swing.GroupLayout(academyPanel);
        academyPanel.setLayout(academyPanelLayout);
        academyPanelLayout.setHorizontalGroup(
                academyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 697, Short.MAX_VALUE)
        );
        academyPanelLayout.setVerticalGroup(
                academyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buildingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(troopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(academyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(townTemplateComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(templateSaveButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(templatePreviousButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(templateNextButton))
                                        .addComponent(templateLoadButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(townTemplateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(107, 107, 107)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(previousTownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(saveButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                                .addComponent(nextTownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(townLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(townComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(troopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buildingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(academyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(townTemplateLabel)
                                                        .addComponent(townLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(townTemplateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(townComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(templatePreviousButton)
                                                        .addComponent(templateNextButton)
                                                        .addComponent(previousTownButton)
                                                        .addComponent(nextTownButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(templateSaveButton)
                                                        .addComponent(saveButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(templateLoadButton)
                                                .addGap(0, 187, Short.MAX_VALUE))))
        );

    }// </editor-fold>

    private void nextTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = townComboBox.getItemCount();
        int currentIndex = townComboBox.getSelectedIndex();
        if (currentIndex + 1 < size) {
            townComboBox.setSelectedIndex(currentIndex + 1);
            currentTownIndex = currentIndex + 1;
            changeTown(towns.get(currentIndex + 1));
        }
    }

    private void previousTownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = townComboBox.getSelectedIndex();
        if (currentIndex - 1 >= 0) {
            townComboBox.setSelectedIndex(currentIndex - 1);
            currentTownIndex = currentIndex - 1;
            changeTown(towns.get(currentIndex - 1));
        }
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Town town = towns.get(townComboBox.getSelectedIndex());
        //buildings
        town.getBuilding(Building.BuildingType.main).setBuildTo(buildingPanel.getMainLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.hide).setBuildTo(buildingPanel.getHideLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.storage).setBuildTo(buildingPanel.getStorageLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.farm).setBuildTo(buildingPanel.getFarmLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.lumber).setBuildTo(buildingPanel.getLumberLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.stoner).setBuildTo(buildingPanel.getStonerLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.ironer).setBuildTo(buildingPanel.getIronerLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.market).setBuildTo(buildingPanel.getMarketLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.docks).setBuildTo(buildingPanel.getDocksLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.barracks).setBuildTo(buildingPanel.getBarracksLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.wall).setBuildTo(buildingPanel.getWallLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.academy).setBuildTo(buildingPanel.getAcademyLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.temple).setBuildTo(buildingPanel.getTempleLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.theater).setBuildTo(buildingPanel.getTheaterLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.thermal).setBuildTo(buildingPanel.getThermalLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.library).setBuildTo(buildingPanel.getLibraryLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.lighthouse).setBuildTo(buildingPanel.getLighthouseLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.tower).setBuildTo(buildingPanel.getTowerLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.statue).setBuildTo(buildingPanel.getStatueLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.oracle).setBuildTo(buildingPanel.getOracleLevelToBuild().getValue());
        town.getBuilding(Building.BuildingType.trade_office).setBuildTo(buildingPanel.getTrade_officeLevelToBuild().getValue());
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
        //academy
        if (town.getBuilding(Building.BuildingType.academy) == null || town.getBuilding(Building.BuildingType.academy).getLevel() == 0) {
            town.getAcademy().fillResearches();
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
        int size = townTemplateComboBox.getItemCount();
        int currentIndex = townTemplateComboBox.getSelectedIndex();
        if (currentIndex + 1 < size) {
            townTemplateComboBox.setSelectedIndex(currentIndex + 1);
        }
    }

    private void templatePreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentIndex = townTemplateComboBox.getSelectedIndex();
        if (currentIndex - 1 >= 0) {
            townTemplateComboBox.setSelectedIndex(currentIndex - 1);
        }
    }

    private void templateLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int currentTemplateIndex = townTemplateComboBox.getSelectedIndex();
        int currentTownIndex = townComboBox.getSelectedIndex();
        Town currentTown = towns.get(currentTownIndex);
        Town templateTown = templateTowns.get(currentTemplateIndex);
        currentTown.setBarracks(templateTown.getBarracks());
        currentTown.getBarracks().setTown(currentTown);
        currentTown.setDocks(templateTown.getDocks());
        currentTown.getDocks().setTown(currentTown);
        currentTown.setBuildingList(templateTown.getBuildingList());
        currentTown.setFarming(templateTown.getFarming());
        currentTown.getFarming().setTown(currentTown);
        currentTown.setAcademy(templateTown.getAcademy());
        System.out.println("Research size: " +templateTown.getAcademy().getResearches().size());
        System.out.println("Researches: " +templateTown.getAcademy().getResearches().toString());
//        currentTown.getAcademy().setResearches(templateTown.getAcademy().getResearches());
        currentTown.getAcademy().setTown(currentTown);
        changeTown(currentTown);
    }

    public static ArrayList<Town> getTemplateTowns() {
        return templateTowns;
    }

    public static void setTemplateTowns(ArrayList<Town> templateTowns) {
        QueuePanel.templateTowns = templateTowns;
    }

    public static Town getCurrentSelectedTown() {
        return towns.get(currentTownIndex);
    }

    private JComboBox<String> townTemplateComboBox;
    private javax.swing.JLabel townTemplateLabel;

    private JComboBox<String> townComboBox;
    private javax.swing.JLabel townLabel;

    private BuildingPanel buildingPanel;
    private AcademyPanel academyPanel;
    private TroopPanel troopPanel;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JButton templateLoadButton;
    private javax.swing.JButton templateNextButton;
    private javax.swing.JButton templatePreviousButton;
    private javax.swing.JButton templateSaveButton;

    private javax.swing.JButton saveButton;
    private javax.swing.JButton nextTownButton;
    private javax.swing.JButton previousTownButton;

}
