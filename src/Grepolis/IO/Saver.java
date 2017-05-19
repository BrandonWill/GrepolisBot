package Grepolis.IO;

import Grepolis.*;
import Grepolis.GUI.SettingsPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

/**
 * @Author Brandon
 * Created by Brandon on 10/9/2015.
 * Time: 4:06 AM
 */
public class Saver {

    public static void save(ArrayList<Town> towns) {
        saveAccount();
        saveBuildings(towns, "Saves");
        saveTroops(towns, "Saves");
        saveDocks(towns, "Saves");
        saveFarmers(towns, "Saves");
    }

    public static void saveAccount() {
        PrintWriter writer = getPrintWriter("Saves", "AccountSave.txt");
        if (writer != null) {
            writer.println("Username:" + SettingsPanel.getUsernameField().getText());
            writer.println("Password:" + SettingsPanel.getPasswordField().getText());
            writer.println("Server:" + SettingsPanel.getWorldField().getText());
            writer.println("RefreshTime:" + SettingsPanel.getBotUpdateTimeField().getText().replaceAll(":", "-"));
            writer.println("TroopRefreshTime:" + SettingsPanel.getTroopUpdateTimeField().getText().replaceAll(":", "-"));
            writer.println("Automatic City Festivals:" + SettingsPanel.getAutomaticFestivals().isSelected());
            writer.close();
        }
    }

    public static void saveBuildings(ArrayList<Town> towns, String directory) {
        PrintWriter writer = getPrintWriter(directory, "buildingSave.txt");
        if (writer != null) {
            for (Town town : towns) {
                writer.print("townID:");
                writer.print(town.getId());
                writer.print(",");
                writer.print("name:");
                writer.print(town.getName());
                writer.print(",");
                writer.print("main:");
                writer.print(town.getBuilding(Building.BuildingType.main).getBuildTo());
                writer.print(",");
                writer.print("hide:");
                writer.print(town.getBuilding(Building.BuildingType.hide).getBuildTo());
                writer.print(",");
                writer.print("storage:");
                writer.print(town.getBuilding(Building.BuildingType.storage).getBuildTo());
                writer.print(",");
                writer.print("farm:");
                writer.print(town.getBuilding(Building.BuildingType.farm).getBuildTo());
                writer.print(",");
                writer.print("lumber:");
                writer.print(town.getBuilding(Building.BuildingType.lumber).getBuildTo());
                writer.print(",");
                writer.print("stoner:");
                writer.print(town.getBuilding(Building.BuildingType.stoner).getBuildTo());
                writer.print(",");
                writer.print("ironer:");
                writer.print(town.getBuilding(Building.BuildingType.ironer).getBuildTo());
                writer.print(",");
                writer.print("market:");
                writer.print(town.getBuilding(Building.BuildingType.market).getBuildTo());
                writer.print(",");
                writer.print("docks:");
                writer.print(town.getBuilding(Building.BuildingType.docks).getBuildTo());
                writer.print(",");
                writer.print("barracks:");
                writer.print(town.getBuilding(Building.BuildingType.barracks).getBuildTo());
                writer.print(",");
                writer.print("wall:");
                writer.print(town.getBuilding(Building.BuildingType.wall).getBuildTo());
                writer.print(",");
                writer.print("academy:");
                writer.print(town.getBuilding(Building.BuildingType.academy).getBuildTo());
                writer.print(",");
                writer.print("temple:");
                writer.print(town.getBuilding(Building.BuildingType.temple).getBuildTo());
                writer.print(",");
                writer.print("theater:");
                writer.print(town.getBuilding(Building.BuildingType.theater).getBuildTo());
                writer.print(",");
                writer.print("thermal:");
                writer.print(town.getBuilding(Building.BuildingType.thermal).getBuildTo());
                writer.print(",");
                writer.print("library:");
                writer.print(town.getBuilding(Building.BuildingType.library).getBuildTo());
                writer.print(",");
                writer.print("lighthouse:");
                writer.print(town.getBuilding(Building.BuildingType.lighthouse).getBuildTo());
                writer.print(",");
                writer.print("tower:");
                writer.print(town.getBuilding(Building.BuildingType.tower).getBuildTo());
                writer.print(",");
                writer.print("statue:");
                writer.print(town.getBuilding(Building.BuildingType.statue).getBuildTo());
                writer.print(",");
                writer.print("oracle:");
                writer.print(town.getBuilding(Building.BuildingType.oracle).getBuildTo());
                writer.print(",");
                writer.print("trade_office:");
                writer.print(town.getBuilding(Building.BuildingType.trade_office).getBuildTo());

//                for (Building building : town.getBuildingList()) {
//                    System.out.println(building.getBuildingType().name() + ":" + building.getBuildTo() + ",");
//                    writer.print(building.getBuildingType().name());
//                    writer.print(":");
//                    writer.print(building.getBuildTo());
//                    writer.print(",");
//                }
                writer.println();
            }
            writer.close();
        }
    }

    public static void saveTroops(ArrayList<Town> towns, String directory) {
        PrintWriter writer = getPrintWriter(directory, "TroopSave.txt");
        if (writer != null) {
            for (Town town : towns) {
                writer.print("townID:");
                writer.print(town.getId());
                writer.print(",");
                for (BarracksUnit barracksUnit : town.getBarracks().getBarracksUnits()) {
                    writer.print(barracksUnit.getUnitType().name());
                    writer.print(":");
                    writer.print(barracksUnit.getBuildTo());
                    writer.print(",");
                }
                writer.println();
            }
            writer.close();
        }
    }

    public static void saveDocks(ArrayList<Town> towns, String directory) {
        PrintWriter writer = getPrintWriter(directory, "DocksSave.txt");
        if (writer != null) {
            for (Town town : towns) {
                writer.print("townID:");
                writer.print(town.getId());
                writer.print(",");
                for (DocksUnit docksUnit : town.getDocks().getDocksUnits()) {
                    writer.print(docksUnit.getUnitType().name());
                    writer.print(":");
                    writer.print(docksUnit.getBuildTo());
                    writer.print(",");
                }
                writer.println();
            }
            writer.close();
        }
    }

    public static void saveFarmers(ArrayList<Town> towns, String directory) {
        PrintWriter writer = getPrintWriter(directory, "FarmersSave.txt");
        if (writer != null) {
            if (towns.size() > 0) {
                writer.println("All Farmers enabled:" + Farming.isAllEnabled());
                writer.println("All MoodToLoot:" + Farming.getAllMoodToLootTo());
                writer.println("All Interval:" + Farming.getAllIntervalToFarm());
                if (!Farming.isAllEnabled()) {
                    for (Town town : towns) {
                        writer.print("townID:" + town.getId());
                        writer.print(",");
                        writer.print("enabled:" + town.getFarming().isEnabled());
                        writer.print(",");
                        writer.print("MoodToLoot:" + town.getFarming().getMoodToLootTo());
                        writer.print(",");
                        writer.print("Interval:" + town.getFarming().getIntervalToFarm());
                        writer.println();
                    }
                }
            }
            writer.close();
        }
    }

    public static void saveTemplate(Town town, String fileName) {
        ArrayList<Town> towns = new ArrayList<>();
        towns.add(town);
        String nameHolder = towns.get(0).getName();
        towns.get(0).setName(fileName);
        String directory = "Saves" + File.separator + "Templates" + File.separator + fileName;
        saveBuildings(towns, directory);
        saveTroops(towns, directory);
        saveDocks(towns, directory);
        saveFarmers(towns, directory);

        towns.get(0).setName(nameHolder);
    }

    private static PrintWriter getPrintWriter(String fileName) {
        return getPrintWriter("Saves", fileName);
    }

    private static PrintWriter getPrintWriter(String saveLocation, String fileName) {
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
            File directory = new File(jarDir + File.separator + saveLocation + File.separator);
            //System.out.println("Directory: " + jarDir + File.separator + saveLocation + File.separator);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                return new PrintWriter(jarDir + File.separator + saveLocation + File.separator + fileName);
            } catch (FileNotFoundException e) {
                System.out.println("Error saving " + fileName);
                return null;
            }
        }
        return null;
    }

}
