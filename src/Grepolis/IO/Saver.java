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
        saveBuildings(towns);
        saveTroops(towns);
        saveDocks(towns);
        saveFarmers(towns);
    }

    public static void saveAccount() {
        PrintWriter writer = null;
        try {
            CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String jarDir = jarFile.getParentFile().getPath();

            writer = new PrintWriter(jarDir + "\\Saves\\AccountSave.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Account file couldn't be saved!");
        }
        if (writer != null) {
            writer.println("Username:" + SettingsPanel.getUsernameField().getText());
            writer.println("Password:" + SettingsPanel.getPasswordField().getText());
            writer.println("Server:" + SettingsPanel.getWorldField().getText());
            writer.println("RefreshTime:" + SettingsPanel.getUpdateTimeField().getText().replaceAll(":", "-"));
            writer.close();
        }
    }

    public static void saveBuildings(ArrayList<Town> towns) {
        PrintWriter writer = null;
        try {
            CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String jarDir = jarFile.getParentFile().getPath();

            writer = new PrintWriter(jarDir + "\\Saves\\buildingSave.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Building file couldn't be saved!");
        }
        if (writer != null && towns != null && towns.size() > 0) {
            for (Town town : towns) {
                writer.print("townID:" + town.getId());
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

                writer.println();
            }
            writer.close();
        }
    }

    public static void saveTroops(ArrayList<Town> towns) {
        PrintWriter writer = null;
        try {
            CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String jarDir = jarFile.getParentFile().getPath();

            writer = new PrintWriter(jarDir + "\\Saves\\TroopSave.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Troop file couldn't be saved!");
        }
        if (writer != null) {
            for (Town town : towns) {
                writer.print("townID:" + town.getId());
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

    public static void saveDocks(ArrayList<Town> towns) {
        PrintWriter writer = null;
        try {
            CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String jarDir = jarFile.getParentFile().getPath();

            writer = new PrintWriter(jarDir + "\\Saves\\DocksSave.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Docks file couldn't be saved!");
        }
        if (writer != null) {
            for (Town town : towns) {
                writer.print("townID:" + town.getId());
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

    public static void saveFarmers(ArrayList<Town> towns) {
        //Will update when the time comes for changing the settings across multiple towns
        PrintWriter writer = null;
        try {
            CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String jarDir = jarFile.getParentFile().getPath();

            writer = new PrintWriter(jarDir + "\\Saves\\FarmersSave.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Farmers file couldn't be saved!");
        }
        if (writer != null) {
            if (towns.size() > 0) {
                writer.println("Farmers enabled:" + GrepolisBot.isFarmersEnabled());
                writer.println("MoodToLoot:" +towns.get(0).getFarming().getMoodToLootTo());
                writer.println("Interval:" +towns.get(0).getFarming().getTimeToFarm());
            }
            writer.close();
        }
    }

}
