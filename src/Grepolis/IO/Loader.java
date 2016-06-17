package Grepolis.IO;

import Grepolis.*;
import Grepolis.GUI.SettingsPanel;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.logging.Level;

import static Grepolis.util.MyLogger.log;
/**
 * @Author Brandon
 * Created by Brandon on 10/9/2015.
 * Time: 4:05 AM
 */
public class Loader {

    public static void load() {
        if (loadAccount()) {
            loadBuildings();
            loadBarrackTroops();
            loadDocksTroops();
            loadFarmers();
        }
    }

    protected static boolean loadAccount() {
        BufferedReader reader = null;
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        String fileName = jarDir + File.separator + "Saves" + File.separator + "AccountSave.txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("initializing variables");
            System.out.println("Account save file not found!");
            if (!new File(jarDir + File.separator + "Saves").exists()) {
                if (!(new File(jarDir + File.separator + "Saves").mkdir())) {
                    log(Level.SEVERE, "Unable to create a save directory!");
                    return false;
                }
            }
        }

        String line;

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Username:") && line.split(":").length > 1) {
                        final String finalLine = line;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SettingsPanel.setUsernameField(new JTextField(finalLine.split(":")[1]));
                            }
                        });
                    } else if (line.startsWith("Password:") && line.split(":").length > 1) {
                        final String finalLine1 = line;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SettingsPanel.setPasswordField(new JPasswordField(finalLine1.split(":")[1]));
                            }
                        });

                    } else if (line.startsWith("Server:") && line.split(":").length > 1) {
                        final String finalLine2 = line;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SettingsPanel.setWorldField(new JTextField(finalLine2.split(":")[1]));
                            }
                        });
                    } else if (line.startsWith("RefreshTime-") && line.split(":").length > 1) {
                        final String finalLine2 = line;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SettingsPanel.setUpdateTimeField(new JTextField((finalLine2.split(":")[1].replaceAll("-", ":"))));
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void loadBuildings() {
        ArrayList<Town> towns = new ArrayList<>();
        BufferedReader reader = null;
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        String fileName = jarDir + File.separator + "Saves" + File.separator + "buildingSave.txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception ignored) { /*Error checked inside of loadAccount with creating an account.*/}
        String line;

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    String text[] = line.split(",");
                    Town town = new Town();
                    for (String string : text) {
                        if (string.startsWith("townID:")) {
                            town.setId(Integer.parseInt(string.split(":")[1]));
                        } else if (string.startsWith("name:")) {
                            town.setName(string.split("name:")[1]);
                        } else if (string.startsWith("main:")) {
                            Building main = new Building();
                            main.setBuildingType(Building.BuildingType.main);
                            main.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(main);
                        } else if (string.startsWith("hide:")) {
                            Building hide = new Building();
                            hide.setBuildingType(Building.BuildingType.hide);
                            hide.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(hide);
                        } else if (string.startsWith("storage:")) {
                            Building storage = new Building();
                            storage.setBuildingType(Building.BuildingType.storage);
                            storage.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(storage);
                        } else if (string.startsWith("farm:")) {
                            Building farm = new Building();
                            farm.setBuildingType(Building.BuildingType.farm);
                            farm.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(farm);
                        } else if (string.startsWith("lumber:")) {
                            Building lumber = new Building();
                            lumber.setBuildingType(Building.BuildingType.lumber);
                            lumber.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(lumber);
                        } else if (string.startsWith("stoner:")) {
                            Building stoner = new Building();
                            stoner.setBuildingType(Building.BuildingType.stoner);
                            stoner.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(stoner);
                        } else if (string.startsWith("ironer:")) {
                            Building ironer = new Building();
                            ironer.setBuildingType(Building.BuildingType.ironer);
                            ironer.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(ironer);
                        } else if (string.startsWith("market:")) {
                            Building market = new Building();
                            market.setBuildingType(Building.BuildingType.market);
                            market.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(market);
                        } else if (string.startsWith("docks:")) {
                            Building docks = new Building();
                            docks.setBuildingType(Building.BuildingType.docks);
                            docks.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(docks);
                        } else if (string.startsWith("barracks:")) {
                            Building barracks = new Building();
                            barracks.setBuildingType(Building.BuildingType.barracks);
                            barracks.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(barracks);
                        } else if (string.startsWith("wall:")) {
                            Building wall = new Building();
                            wall.setBuildingType(Building.BuildingType.wall);
                            wall.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(wall);
                        } else if (string.startsWith("academy:")) {
                            Building academy = new Building();
                            academy.setBuildingType(Building.BuildingType.academy);
                            academy.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(academy);
                        } else if (string.startsWith("temple:")) {
                            Building temple = new Building();
                            temple.setBuildingType(Building.BuildingType.temple);
                            temple.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(temple);
                        } else if (string.startsWith("theater:")) {
                            Building theater = new Building();
                            theater.setBuildingType(Building.BuildingType.theater);
                            theater.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(theater);
                        } else if (string.startsWith("thermal:")) {
                            Building thermal = new Building();
                            thermal.setBuildingType(Building.BuildingType.thermal);
                            thermal.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(thermal);
                        } else if (string.startsWith("library:")) {
                            Building library = new Building();
                            library.setBuildingType(Building.BuildingType.library);
                            library.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(library);
                        } else if (string.startsWith("lighthouse:")) {
                            Building lighthouse = new Building();
                            lighthouse.setBuildingType(Building.BuildingType.lighthouse);
                            lighthouse.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(lighthouse);
                        } else if (string.startsWith("tower:")) {
                            Building tower = new Building();
                            tower.setBuildingType(Building.BuildingType.tower);
                            tower.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(tower);
                        } else if (string.startsWith("statue:")) {
                            Building statue = new Building();
                            statue.setBuildingType(Building.BuildingType.statue);
                            statue.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(statue);
                        } else if (string.startsWith("oracle:")) {
                            Building oracle = new Building();
                            oracle.setBuildingType(Building.BuildingType.oracle);
                            oracle.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(oracle);
                        } else if (string.startsWith("trade_office:")) {
                            Building trade_office = new Building();
                            trade_office.setBuildingType(Building.BuildingType.trade_office);
                            trade_office.setBuildTo(Integer.parseInt(string.split(":")[1]));
                            town.getBuildingList().add(trade_office);
                        }
                    }
                    towns.add(town);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Grepolis.GrepolisBot.setTowns(towns);
    }

    public static void loadBarrackTroops() {
        BufferedReader reader = null;
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        String fileName = jarDir + File.separator + "Saves" + File.separator + "TroopSave.txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception ignored) {/*Error checked inside of loadAccount with creating an account.*/}
        String line;

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    String text[] = line.split(",");
                    ArrayList<Town> towns = Grepolis.GrepolisBot.getTowns();
                    Town town;
                    Barracks barracks = null;
                    for (String string : text) {
                        if (string.startsWith("townID:")) {
                            int townID = Integer.parseInt(string.split(":")[1]);
                            for (Town townSearcher : towns) {
                                if (townSearcher.getId() == townID) {
                                    town = townSearcher;
                                    barracks = town.getBarracks();
                                }
                            }
                        } else {
                            if (barracks != null) {
                                String unitData[] = string.split(":");
                                BarracksUnit barracksUnit = new BarracksUnit();
                                barracksUnit.setUnitType(BarracksUnit.UnitType.valueOf(unitData[0]));
                                barracksUnit.setBuildTo(Integer.parseInt(unitData[1]));
                                barracks.getBarracksUnits().add(barracksUnit);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDocksTroops() {
        BufferedReader reader = null;

        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        String fileName = jarDir + File.separator + "Saves" + File.separator + "DocksSave.txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception ignored) {/*Error checked inside of loadAccount with creating an account.*/}
        String line;

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    String text[] = line.split(",");
                    ArrayList<Town> towns = Grepolis.GrepolisBot.getTowns();
                    Town town;
                    Docks docks = null;
                    for (String string : text) {
                        if (string.startsWith("townID:")) {
                            int townID = Integer.parseInt(string.split(":")[1]);
                            for (Town townSearcher : towns) {
                                if (townSearcher.getId() == townID) {
                                    town = townSearcher;
                                    docks = town.getDocks();
                                }
                            }
                        } else {
                            if (docks != null) {
                                String unitData[] = string.split(":");
                                DocksUnit docksUnit = new DocksUnit();
                                docksUnit.setUnitType(DocksUnit.UnitType.valueOf(unitData[0]));
                                docksUnit.setBuildTo(Integer.parseInt(unitData[1]));
                                docks.getDocksUnits().add(docksUnit);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFarmers() {
        BufferedReader reader = null;

        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        String fileName = jarDir + File.separator + "Saves" + File.separator + "FarmersSave.txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception ignored) {/*Error checked inside of loadAccount with creating an account.*/}
        String line;

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Farmers enabled:") && line.split(":").length > 1) {
                        System.out.println("Old save file detected. Rewriting farmers correctly...");
                        Farming.setAllEnabled(Boolean.parseBoolean(line.split(":")[1]));
                    } else if (line.startsWith("MoodToLoot:") && line.split(":").length > 1) {
                        Farming.setAllMoodToLootTo(Integer.parseInt(line.split(":")[1]));
                    } else if (line.startsWith("Interval:") && line.split(":").length > 1) {
                        Farming.setAllIntervalToFarm(Farming.IntervalToFarm.valueOf(line.split(":")[1]));
                    } else if (line.startsWith("All Farmers enabled:") && line.split(":").length > 1) {
                        Farming.setAllEnabled(Boolean.parseBoolean(line.split(":")[1]));
                    } else if (line.startsWith("All MoodToLoot:") && line.split(":").length > 1) {
                        Farming.setAllMoodToLootTo(Integer.parseInt(line.split(":")[1]));
                    } else if (line.startsWith("All Interval:") && line.split(":").length > 1) {
                        Farming.setAllIntervalToFarm(Farming.IntervalToFarm.valueOf(line.split(":")[1]));
                    } else if (line.startsWith("townID:")) {
                        Town town = null;
                        ArrayList<Town> towns = Grepolis.GrepolisBot.getTowns();
                        String text[] = line.split(",");
                        for (String string : text) {
                            if (string.startsWith("townID:")) {
                                int townID = Integer.parseInt(string.split(":")[1]);
                                for (Town townSearcher : towns) {
                                    if (townSearcher.getId() == townID) {
                                        town = townSearcher;
                                    }
                                }
                            }
                            if (string.startsWith("enabled:")) {
                                if (town != null) {
                                    town.getFarming().setEnabled(Boolean.parseBoolean(string.split(":")[1]));
                                }
                            }
                            if (string.startsWith("MoodToLoot:")) {
                                if (town != null) {
                                    town.getFarming().setMoodToLootTo(Integer.parseInt(string.split(":")[1]));
                                }
                            }
                            if (string.startsWith("Interval:")) {
                                if (town != null) {
                                    town.getFarming().setIntervalToFarm(Farming.IntervalToFarm.valueOf(string.split(":")[1]));
                                }
                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
