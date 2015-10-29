package Grepolis;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Author Brandon
 * Created by Brandon on 9/21/2015.
 * Time: 5:55 PM
 */
public class Town {
    private String name = null;
    private int id = 0;
    private ArrayList<Building> buildingList = new ArrayList<>();
    private Barracks barracks = new Barracks(this);
    private Docks docks = new Docks(this);
    private Culture culture = new Culture(this);
    private Farming farming = new Farming(this);
    private boolean isBuildingQueueFull;
    private boolean fullStorage = false;
    private Long timeToFarm = (long) 0;
    private FarmAmount farmAmount = FarmAmount.MINUTES_FIVE;
    private String server;
    private String csrftoken;


    public boolean parseHTML(String html) {

        if (html.contains("BuildingMain.full_queue")) {
            isBuildingQueueFull = !html.contains("BuildingMain.full_queue = false");
        }
//        System.out.println("Parsing html: ");

        if (html.contains("\"id")) {
//            buildingList.clear();
//            System.out.println("HTML contains id: ");
            String[] buildings = html.split("\"id");
            for (int i =0; i < buildings.length; i++) {
                if (buildings[i].contains("description")) {
//                    System.out.println("Adding building" + i + ": " + buildings[i] + "\"id:\"");
//                    String buildingID = buildings[i].substring(0, buildings[i].charAt('}')+1);
//                    if (buildingsFixed.size() > 1) {
//                        buildingsFixed.set(buildingsFixed.size() - 1, buildingsFixed.get(buildingsFixed.size() -1)+buildingID);
//                    }
//                    buildings[i] = buildings[i].substring(buildings[i].charAt('}')+1);
                    for (Building.BuildingType buildingType : Building.BuildingType.values()) {
                        if (buildings[i].contains(buildingType.description)) {
                            //Create the building here if it isn't loaded already
                            if (!hasBuilding(buildingType)) {
                                Building building = new Building();
                                building.setBuildingType(buildingType);
                                building.setName(buildingType.name());
                                building.setDescription(buildingType.description);
//                            System.out.println("Found " + buildingType.name() + ": " + buildings[i].substring(0, buildings[i].indexOf("resourcesForLevelReduceFactor")).replaceAll("\"", ""));
//                            System.out.println("Building name: " +building.getName());
//                            String buildingData[] = buildings[i].substring(0, buildings[i].indexOf("controller")).replaceAll("\\\\\"", "").split(",");

                                String buildingData[] = buildings[i].replaceAll("\"", "").replaceAll("\\\\", "").split(",");
                                //Example data: :trade_office}}});\n\tBuildingMain.buildings =
                                // {main:{name:Senate,description:In the Senate you can have new buildings erected or existing buildings upgraded.
                                // The higher the Senate level, the more quickly the construction work is done.,level:17,current_level:17,next_level:18,get_dependencies:[],
                                // can_upgrade:true,can_upgrade_reduced:true,enough_population:true,max_level:false,can_tear_down:true,needed_resources:{wood:2999,stone:2998,
                                // iron:1542},needed_resources_reduced:{wood:2250,stone:2249,iron:1157},enough_resources:true,enough_storage:true,pop:6,pop_tear_down:6,
                                // build_time:2:28:46,tear_down_time:2:14:13,
                                for (String data : buildingData) {
//                                System.out.println("Data: " +data);
                                    if (data.startsWith("level:")) {
                                        if (!data.contains("null")) {
                                            building.setLevel(Integer.parseInt(data.split(":")[1]));
//                                        System.out.println("level: " + building.getLevel());
                                        }
                                    }
                                    if (data.startsWith("current_level")) {
                                        building.setCurrentLevel(Integer.parseInt(data.split(":")[1]));
//                                    System.out.println("Current level: " +building.getCurrentLevel());
                                    }
                                    if (data.startsWith("next_level")) {
                                        building.setNextLevel(Integer.parseInt(data.split(":")[1]));
//                                    System.out.println("Next level: " +building.getNextLevel());
                                    }
                                    if (data.startsWith("can_upgrade:")) {
                                        building.setCan_upgrade(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Can upgrade: " +building.canUpgrade());
                                    }
                                    if (data.startsWith("enough_population")) {
                                        building.setEnough_population(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough population: " +building.hasEnoughPopulation());
                                    }
                                    if (data.startsWith("max_level")) {
                                        building.setMax_level(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Max level: " +building.isMaxLevel());
                                    }
                                    if (data.startsWith("can_tear_down")) {
                                        building.setCan_tear_down(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Can tear down: " +building.canTearDown());
                                    }
                                    if (data.startsWith("enough_resources")) {
                                        building.setEnough_resources(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough resources: " +building.hasEnoughResources());
                                    }
                                    if (data.startsWith("enough_storage")) {
                                        building.setEnough_storage(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough storage: " +building.hasEnoughStorage());
                                    }
                                }
                                buildingList.add(building);
                            } else {
                                //update a current building
                                Building building = getBuilding(buildingType);
                                building.setName(buildingType.name());
                                building.setDescription(buildingType.description);
//                            System.out.println("Found " + buildingType.name() + ": " + buildings[i].substring(0, buildings[i].indexOf("resourcesForLevelReduceFactor")).replaceAll("\"", ""));
//                            System.out.println("Building name: " +building.getName());
//                            String buildingData[] = buildings[i].substring(0, buildings[i].indexOf("controller")).replaceAll("\\\\\"", "").split(",");

                                String buildingData[] = buildings[i].replaceAll("\"", "").replaceAll("\\\\", "").split(",");
                                //Example data: :trade_office}}});\n\tBuildingMain.buildings =
                                // {main:{name:Senate,description:In the Senate you can have new buildings erected or existing buildings upgraded.
                                // The higher the Senate level, the more quickly the construction work is done.,level:17,current_level:17,next_level:18,get_dependencies:[],
                                // can_upgrade:true,can_upgrade_reduced:true,enough_population:true,max_level:false,can_tear_down:true,needed_resources:{wood:2999,stone:2998,
                                // iron:1542},needed_resources_reduced:{wood:2250,stone:2249,iron:1157},enough_resources:true,enough_storage:true,pop:6,pop_tear_down:6,
                                // build_time:2:28:46,tear_down_time:2:14:13,
                                for (String data : buildingData) {
//                                System.out.println("Data: " +data);
                                    if (data.startsWith("level:")) {
                                        if (!data.contains("null")) {
                                            building.setLevel(Integer.parseInt(data.split(":")[1]));
//                                        System.out.println("level: " + building.getLevel());
                                        }
                                    }
                                    if (data.startsWith("current_level")) {
                                        building.setCurrentLevel(Integer.parseInt(data.split(":")[1]));
//                                    System.out.println("Current level: " +building.getCurrentLevel());
                                    }
                                    if (data.startsWith("next_level")) {
                                        building.setNextLevel(Integer.parseInt(data.split(":")[1]));
//                                    System.out.println("Next level: " +building.getNextLevel());
                                    }
                                    if (data.startsWith("can_upgrade:")) {
                                        building.setCan_upgrade(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Can upgrade: " +building.canUpgrade());
                                    }
                                    if (data.startsWith("enough_population")) {
                                        building.setEnough_population(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough population: " +building.hasEnoughPopulation());
                                    }
                                    if (data.startsWith("max_level")) {
                                        building.setMax_level(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Max level: " +building.isMaxLevel());
                                    }
                                    if (data.startsWith("can_tear_down")) {
                                        building.setCan_tear_down(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Can tear down: " +building.canTearDown());
                                    }
                                    if (data.startsWith("enough_resources")) {
                                        building.setEnough_resources(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough resources: " +building.hasEnoughResources());
                                    }
                                    if (data.startsWith("enough_storage")) {
                                        building.setEnough_storage(Boolean.parseBoolean(data.split(":")[1]));
//                                    System.out.println("Enough storage: " +building.hasEnoughStorage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            for (Building building : buildingList) {
//                System.out.println("Found " +building.getName());
//            }
//            if (buildingList.size() == 22) {
//                System.out.println("All buildings found");
//            }
//            for (String buildingParsed : buildingsFixed) {
//                System.out.println("Parsed: " +buildingParsed);
//            }
        }
        return true;
    }

    public boolean hasBuilding(Building.BuildingType buildingType) {
        for (Building building : buildingList) {
            if (building.getBuildingType().equals(buildingType)) {
                return true;
            }
        }
        return false;
    }

    public Building getBuilding(Building.BuildingType buildingType) {
        for (Building building : buildingList) {
            if (building.getBuildingType().equals(buildingType)) {
                return building;
            }
        }
        return null;
    }

    public void setFullStorage(boolean value) {
        this.fullStorage = value;
    }

    public void setFullStorage(String townData) {
//                                System.out.println("Town ID: " +getId() + "... Resource[0] string: " +resources[0]);
//                        System.out.println("town id found: " +resources[0].substring(resources[0].indexOf(getId() + "")));
        System.out.println("Current town: " + getId());
        System.out.println("Data: " +townData);
        String resourceString = townData.substring(townData.indexOf(getId() + ""));
//                        System.out.println("resource string original: " + resourceString);
        String storageString = resourceString.substring(resourceString.indexOf("\"storage_volume\":"));
        resourceString = resourceString.substring(resourceString.indexOf("resources_last_update"), resourceString.indexOf("island_id"));
        resourceString = resourceString.replaceAll("\"", "");
        //.substring(resources[0].indexOf("resources_last_update"), resources[0].indexOf("island_id"))
//                        System.out.println("Resource string: " +resourceString);
        int wood = 0;
        int stone = 0;
        int iron = 0;
        for (String resource : resourceString.split(",")) {

            if (resource.contains("wood")) {
                wood = Integer.parseInt(resource.split(":")[1]);
//                                System.out.println("Wood: " + wood);
            }
            if (resource.contains("stone")) {
                stone = Integer.parseInt(resource.split(":")[1]);
//                                System.out.println("Stone: " + stone);
            }
            if (resource.contains("iron")) {
                iron = Integer.parseInt(resource.split(":")[1]);
//                                System.out.println("Iron: " + iron);
            }
        }
//                        System.out.println("Storage string: " +storageString);
        storageString = storageString.substring(0, storageString.indexOf(","));
        int storage = Integer.parseInt(storageString.split(":")[1]);
        fullStorage = (wood == storage) && (stone == storage) && (iron == storage);
    }

    public boolean hasFullStorage() {
        return fullStorage;
    }

    public boolean isBuildingQueueFull() {
        return isBuildingQueueFull;
    }

    public Long getTimeToFarm() {
        return timeToFarm;
    }

    public void setTimeToFarm(Long timeToFarm) {
        this.timeToFarm = timeToFarm;
    }

    public FarmAmount getFarmAmount() {
        return farmAmount;
    }

    public void setFarmAmount(FarmAmount farmAmount) {
        this.farmAmount = farmAmount;
    }

    public boolean buildABuilding() {
        if (canBuild(Building.BuildingType.theater)) {
            return build(Building.BuildingType.theater, getBuilding(Building.BuildingType.theater).getBuildTo());
        }else if (canBuild(Building.BuildingType.thermal)) {
            return build(Building.BuildingType.thermal, getBuilding(Building.BuildingType.thermal).getBuildTo());
        }else if (canBuild(Building.BuildingType.library)) {
            return build(Building.BuildingType.library, getBuilding(Building.BuildingType.library).getBuildTo());
        }else if (canBuild(Building.BuildingType.lighthouse)) {
            return build(Building.BuildingType.lighthouse, getBuilding(Building.BuildingType.lighthouse).getBuildTo());
        }else if (canBuild(Building.BuildingType.tower)) {
            return build(Building.BuildingType.tower, getBuilding(Building.BuildingType.tower).getBuildTo());
        }else if (canBuild(Building.BuildingType.statue)) {
            return build(Building.BuildingType.statue, getBuilding(Building.BuildingType.statue).getBuildTo());
        }else if (canBuild(Building.BuildingType.oracle)) {
            return build(Building.BuildingType.oracle, getBuilding(Building.BuildingType.oracle).getBuildTo());
        }else if (canBuild(Building.BuildingType.trade_office)) {
            return build(Building.BuildingType.trade_office, getBuilding(Building.BuildingType.trade_office).getBuildTo());
        }else if (canBuild(Building.BuildingType.main)) {
            return build(Building.BuildingType.main, getBuilding(Building.BuildingType.main).getBuildTo());
        }else if (canBuild(Building.BuildingType.academy)) {
            return build(Building.BuildingType.academy, getBuilding(Building.BuildingType.academy).getBuildTo());
        }else if (canBuild(Building.BuildingType.wall)) {
            return build(Building.BuildingType.wall, getBuilding(Building.BuildingType.wall).getBuildTo());
        }else if (canBuild(Building.BuildingType.temple)) {
            return build(Building.BuildingType.temple, getBuilding(Building.BuildingType.temple).getBuildTo());
        }else if (canBuild(Building.BuildingType.market)) {
            return build(Building.BuildingType.market, getBuilding(Building.BuildingType.market).getBuildTo());
        }else if (canBuild(Building.BuildingType.barracks)) {
            return build(Building.BuildingType.barracks, getBuilding(Building.BuildingType.barracks).getBuildTo());
        }else if (canBuild(Building.BuildingType.docks)) {
            return build(Building.BuildingType.docks, getBuilding(Building.BuildingType.docks).getBuildTo());
        }else if (canBuild(Building.BuildingType.farm)) {
            return build(Building.BuildingType.farm, getBuilding(Building.BuildingType.farm).getBuildTo());
        }else if (canBuild(Building.BuildingType.storage)) {
            return build(Building.BuildingType.storage, getBuilding(Building.BuildingType.storage).getBuildTo());
        }else if (canBuild(Building.BuildingType.hide)) {
            return build(Building.BuildingType.hide, getBuilding(Building.BuildingType.hide).getBuildTo());
        }else if (canBuild(Building.BuildingType.lumber)) {
            return build(Building.BuildingType.lumber, getBuilding(Building.BuildingType.lumber).getBuildTo());
        }else if (canBuild(Building.BuildingType.stoner)) {
            return build(Building.BuildingType.stoner, getBuilding(Building.BuildingType.stoner).getBuildTo());
        }else if (canBuild(Building.BuildingType.ironer)) {
            return build(Building.BuildingType.ironer, getBuilding(Building.BuildingType.ironer).getBuildTo());
        }

        return false;
    }

    private boolean canBuild(Building.BuildingType building) {
        Building actualBuilding = getBuilding(building);
        return (!isBuildingQueueFull && actualBuilding.canUpgrade() && actualBuilding.getLevel() < actualBuilding.getBuildTo());
    }

    public boolean build(Building.BuildingType buildingType, final int buildingLevel) {
        final Building building = getBuilding(buildingType);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        console.log(xhr.responseText);\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + server + ".grepolis.com/game/frontend_bridge?town_id=" + getId() + "&action=execute&h=" +csrftoken + "&json=%7B%22model_url%22%3A%22BuildingOrder%22%2C%22action_name%22%3A%22buildUp%22%2C%22arguments%22%3A%7B%22building_id%22%3A%22" + building.getName() + "%22%7D%2C%22town_id%22%3A" + getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send({\"model_url\":\"BuildingOrder\",\"action_name\":\"buildUp\",\"arguments\":{\"building_id\":\"" + building.getName() + "\"},\"town_id\":" +getId()+ ",\"nl_init\":true});");
//                GrepolisBot.webView.getEngine().executeScript("BuildingMain.buildBuilding('" + building.getName() + "'," + buildingLevel + ")");
                System.out.println(getTimeOnly(LocalDateTime.now().toString()) + " " + name + " " +building.getBuildingType().inGameName + " has been added to the building queue!");
            }
        });
        return true;
    }

    public Culture getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeOnly(String time) {
        return time.split("T")[1];
    }

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public String getCsrftoken() {
        return csrftoken;
    }

    public void setCsrftoken(String csrftoken) {
        this.csrftoken = csrftoken;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Barracks getBarracks() {
        return barracks;
    }

    public void setBarracks(Barracks barracks) {
        this.barracks = barracks;
    }

    public Docks getDocks() {
        return docks;
    }

    public void setDocks(Docks docks) {
        this.docks = docks;
    }

    public Farming getFarming() {
        return farming;
    }

    public void setFarming(Farming farming) {
        this.farming = farming;
    }

    public enum FarmAmount {
        MINUTES_FIVE("fto_time_checkbox fto_300"),
        MINUTES_TEN("fto_time_checkbox fto_600"),
        MINUTES_TWENTY("fto_time_checkbox fto_1200"),
        MINUTES_FORTY("fto_time_checkbox fto_2400"),
        MINUTES_NINETY("fto_time_checkbox fto_5400"),
        MINUTES_ONE_HUNDRED_EIGHTY("fto_time_checkbox fto_10800"),
        MINUTES_TWO_HUNDRED_FORTY("fto_time_checkbox fto_14400"),
        MINUTES_FOUR_HUNDRED_EIGHTY("fto_time_checkbox fto_28800");

        String javascriptVariable;
        FarmAmount(String javascriptVariable) {
            this.javascriptVariable = javascriptVariable;
        }
    }
}
