package Grepolis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Grepolis.util.MyLogger.log;

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
    private Academy academy = new Academy(this);
    private boolean isBuildingQueueFull;
    private boolean fullStorage = false;
    private Long timeToFarm = (long) 0;
    private String server;
    private String csrftoken;
    private boolean hasConqueror;

    private int island_x;
    private int island_y;
    private int last_wood;
    private int last_stone;
    private int last_iron;
    private int storage;
    private int availablePopulation;
    private HashMap<Integer, Long> buildingFinishingTimes = new HashMap<>();
    private ArrayList<BuildingInQueue> buildingInQueues = new ArrayList<>();


    public boolean parseHTML(String html) {

        if (html.contains("BuildingMain.full_queue")) {
            isBuildingQueueFull = !html.contains("BuildingMain.full_queue = false");
        }
//        log("Parsing html: ");

        if (html.contains("\"id")) {
//            buildingList.clear();
//            log("HTML contains id: ");
            String[] buildings = html.split("\"id");
            for (int i =0; i < buildings.length; i++) {
                if (buildings[i].contains("description")) {
//                    log("Adding building" + i + ": " + buildings[i] + "\"id:\"");
//                    String buildingID = buildings[i].substring(0, buildings[i].charAt('}')+1);
//                    if (buildingsFixed.size() > 1) {
//                        buildingsFixed.set(buildingsFixed.size() - 1, buildingsFixed.get(buildingsFixed.size() -1)+buildingID);
//                    }
//                    buildings[i] = buildings[i].substring(buildings[i].charAt('}')+1);
                    for (Building.BuildingType buildingType : Building.BuildingType.values()) {
                        if (buildings[i].contains("building_" +buildingType.name())) {
                            //Create the building here if it isn't loaded already
                            if (!hasBuilding(buildingType)) {
                                Building building = new Building();
                                building.setBuildingType(buildingType);
                                building.setName(buildingType.name());
                                building.setDescription(buildingType.description);
//                            log("Found " + buildingType.name() + ": " + buildings[i].substring(0, buildings[i].indexOf("resourcesForLevelReduceFactor")).replaceAll("\"", ""));
//                            log("Building name: " +building.getName());
//                            String buildingData[] = buildings[i].substring(0, buildings[i].indexOf("controller")).replaceAll("\\\\\"", "").split(",");

                                String buildingData[] = buildings[i].replaceAll("\"", "").replaceAll("\\\\", "").split(",");
//                                System.out.println(Arrays.toString(buildingData));
                                //Example data: :trade_office}}});\n\tBuildingMain.buildings =
                                // {main:{name:Senate,description:In the Senate you can have new buildings erected or existing buildings upgraded.
                                // The higher the Senate level, the more quickly the construction work is done.,level:17,current_level:17,next_level:18,get_dependencies:[],
                                // can_upgrade:true,can_upgrade_reduced:true,enough_population:true,max_level:false,can_tear_down:true,needed_resources:{wood:2999,stone:2998,
                                // iron:1542},needed_resources_reduced:{wood:2250,stone:2249,iron:1157},enough_resources:true,enough_storage:true,pop:6,pop_tear_down:6,
                                // build_time:2:28:46,tear_down_time:2:14:13,
                                for (String data : buildingData) {
//                                log("Data: " +data);
                                    if (data.startsWith("level:")) {
                                        if (!data.contains("null")) {
                                            building.setLevel(Integer.parseInt(data.split(":")[1]));
//                                        log("level: " + building.getLevel());
                                        }
                                    }
                                    if (data.startsWith("current_level")) {
                                        building.setCurrentLevel(Integer.parseInt(data.split(":")[1]));
//                                    log("Current level: " +building.getCurrentLevel());
                                    }
                                    if (data.startsWith("next_level")) {
                                        building.setNextLevel(Integer.parseInt(data.split(":")[1]));
//                                    log("Next level: " +building.getNextLevel());
                                    }
                                    if (data.startsWith("can_upgrade:")) {
                                        building.setCan_upgrade(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Can upgrade: " +building.canUpgrade());
                                    }
                                    if (data.startsWith("enough_population")) {
                                        building.setEnough_population(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough population: " +building.hasEnoughPopulation());
                                    }
                                    if (data.startsWith("max_level")) {
                                        building.setMax_level(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Max level: " +building.isMaxLevel());
                                    }
                                    if (data.startsWith("can_tear_down")) {
                                        building.setCan_tear_down(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Can tear down: " +building.canTearDown());
                                    }
                                    if (data.startsWith("enough_resources")) {
                                        building.setEnough_resources(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough resources: " +building.hasEnoughResources());
                                    }
                                    if (data.startsWith("enough_storage")) {
                                        building.setEnough_storage(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough storage: " +building.hasEnoughStorage());
                                    }
                                }
                                buildingList.add(building);
                            } else {
                                //update a current building
                                Building building = getBuilding(buildingType);
                                building.setName(buildingType.name());
                                building.setDescription(buildingType.description);
//                            log("Found " + buildingType.name() + ": " + buildings[i].substring(0, buildings[i].indexOf("resourcesForLevelReduceFactor")).replaceAll("\"", ""));
//                            log("Building name: " +building.getName());
//                            String buildingData[] = buildings[i].substring(0, buildings[i].indexOf("controller")).replaceAll("\\\\\"", "").split(",");

                                String buildingData[] = buildings[i].replaceAll("\"", "").replaceAll("\\\\", "").split(",");
                                //Example data: :trade_office}}});\n\tBuildingMain.buildings =
                                // {main:{name:Senate,description:In the Senate you can have new buildings erected or existing buildings upgraded.
                                // The higher the Senate level, the more quickly the construction work is done.,level:17,current_level:17,next_level:18,get_dependencies:[],
                                // can_upgrade:true,can_upgrade_reduced:true,enough_population:true,max_level:false,can_tear_down:true,needed_resources:{wood:2999,stone:2998,
                                // iron:1542},needed_resources_reduced:{wood:2250,stone:2249,iron:1157},enough_resources:true,enough_storage:true,pop:6,pop_tear_down:6,
                                // build_time:2:28:46,tear_down_time:2:14:13,
                                for (String data : buildingData) {
//                                log("Data: " +data);
                                    if (data.startsWith("level:")) {
                                        if (!data.contains("null")) {
                                            building.setLevel(Integer.parseInt(data.split(":")[1]));
//                                        log("level: " + building.getLevel());
                                        }
                                    }
                                    if (data.startsWith("current_level")) {
                                        building.setCurrentLevel(Integer.parseInt(data.split(":")[1]));
//                                    log("Current level: " +building.getCurrentLevel());
                                    }
                                    if (data.startsWith("next_level")) {
                                        building.setNextLevel(Integer.parseInt(data.split(":")[1]));
//                                    log("Next level: " +building.getNextLevel());
                                    }
                                    if (data.startsWith("can_upgrade:")) {
                                        building.setCan_upgrade(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Can upgrade: " +building.canUpgrade());
                                    }
                                    if (data.contains("pop:")) {
                                        building.setPopulationRequired(Integer.parseInt(data.split(":")[1]));
//                                        System.out.println("Has pop here: " +data);
                                    }
                                    if (data.startsWith("enough_population")) {
                                        building.setEnough_population(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough population: " +building.hasEnoughPopulation());
                                    }
                                    if (data.startsWith("max_level")) {
                                        building.setMax_level(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Max level: " +building.isMaxLevel());
                                    }
                                    if (data.startsWith("can_tear_down")) {
                                        building.setCan_tear_down(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Can tear down: " +building.canTearDown());
                                    }
                                    if (data.startsWith("enough_resources")) {
                                        building.setEnough_resources(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough resources: " +building.hasEnoughResources());
                                    }
                                    if (data.startsWith("enough_storage")) {
                                        building.setEnough_storage(Boolean.parseBoolean(data.split(":")[1]));
//                                    log("Enough storage: " +building.hasEnoughStorage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            for (Building building : buildingList) {
//                log("Found " +building.getName());
//            }
            if (buildingList.size() == 22) {
//                log("All buildings found");
            } else {
                log("Not all buildings found. Please create an issue on Github! Pausing...");
                GrepolisBot.pauseBot();
            }
//            for (String buildingParsed : buildingsFixed) {
//                log("Parsed: " +buildingParsed);
//            }
        }
        return true;
    }

    public void parseTownSwitchData(String data) {
//        hasConqueror = !data.contains("\\\"conqueror_units\\\":[]");
//        log("Has a conqueror: " +hasConqueror);
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

    public boolean buildABuilding() {
//        if (isBuildingQueueFull) {
        HashMap<Integer, Long> holder = new HashMap<>(buildingFinishingTimes);
        for (Map.Entry<Integer, Long> entry : holder.entrySet()) {
            Integer key = entry.getKey();
            long value = entry.getValue();
            long secondsRemaining = value - GrepolisBot.getServerUnixTime();
//            System.out.println("Time remaining on " +key + " is " +secondsRemaining + " seconds");
//            System.out.println("Value: " +value + " current server time: " +GrepolisBot.getServerUnixTime());
            if (secondsRemaining < 0) {
                buildingFinishingTimes.remove(key);
            } else if (secondsRemaining > 10 && secondsRemaining <= 300) {
                System.out.println(getName() +" automatically finished up a building that was below 5 minutes!");
                if (completeBuildingEarly(key)) {
                    buildingFinishingTimes.remove(key);
                }
            }
        }
//        }


        //Check so that towns can build docks faster
        if (rushDocksBuildings()) {
            Building.BuildingType buildingType = docksBuildingRequirementRush();
            return build(buildingType, getBuilding(buildingType).getBuildTo());
        }else if (canBuild(Building.BuildingType.theater)) {
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

    private boolean completeBuildingEarly(int orderID) {
        //{"model_url":"BuildingOrder/1210570","action_name":"buyInstant","arguments":{"order_id":1210570},"town_id":16762,"nl_init":true}
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4) {\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + server + ".grepolis.com/game/" + completeBuildingEarlyJSON(orderID) +", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
//                GrepolisBot.webView.getEngine().executeScript("BuildingMain.buildBuilding('" + building.getName() + "'," + buildingLevel + ")");
                isBuildingQueueFull = false;
            }
        });
        return true;
    }

    private String completeBuildingEarlyJSON(int orderID) {
        //{"model_url":"BuildingOrder/1210570","action_name":"buyInstant","arguments":{"order_id":1210570},"town_id":16762,"nl_init":true}
        StringBuilder sb = new StringBuilder();

        sb.append("frontend_bridge?town_id=").append(getId()).append("&action=execute&h=").append(getCsrftoken()).append("&json=' +encodeURIComponent(JSON.stringify(");

        sb.append("{\"model_url\":\"BuildingOrder/" + orderID + "\",");

        sb.append("\"action_name\":\"buyInstant\",\"arguments\":{\"order_id\":" + orderID + "},\"town_id\":" + getId());

        sb.append(",\"nl_init\":true}");

        sb.append("))");

        return  sb.toString();
    }

    public boolean parseBuildingInQueueData(String data) {
//            final Building building = getBuilding(buildingType);
//            CookieManager cm = new CookieManager();
//            URL url = new URL("https://" + server + ".grepolis.com/game/frontend_bridge?town_id=" + getId() + "&action=execute&h=" +csrftoken + "&json=%7B%22model_url%22%3A%22BuildingOrder%22%2C%22action_name%22%3A%22buildUp%22%2C%22arguments%22%3A%7B%22building_id%22%3A%22" + building.getName() + "%22%7D%2C%22town_id%22%3A" + getId() + "%2C%22nl_init%22%3Atrue%7D");
//            URLConnection conn = url.openConnection();
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
//            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//            conn.setRequestProperty("Cookie", GrepolisBot.getCookie());
//
//            conn.connect();
//
//            Reader reader = new InputStreamReader(conn.getInputStream());

        data = data.replace("BuildingInQueueData:200", "");
        JsonElement jElement = new JsonParser().parse(data);
        JsonObject jObject = jElement.getAsJsonObject();
//        System.out.println("jObject: " +jObject.toString());
        JsonObject json = jObject.getAsJsonObject("json");
        JsonArray notifications = json.getAsJsonArray("notifications");




        int order_param_id = -1;
        String param_str = null;
        if (notifications != null && !notifications.isJsonNull()) {
            for (int i = 0; i < notifications.size(); i++) {
                JsonObject jsonObject = notifications.get(i).getAsJsonObject();
                if (jsonObject != null) {
                    if (jsonObject.has("subject")) {
                        if (jsonObject.get("subject").getAsString().equals("BuildingOrder")) {
                            order_param_id = jsonObject.get("param_id").getAsInt();
                            param_str = jsonObject.get("param_str").getAsString();
                        }
                    }
                }
            }

            if (order_param_id != -1 && param_str != null) {
                JsonElement jsonElement = new JsonParser().parse(param_str.replaceAll("\\\\", ""));
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject BuildingOrder = jsonObject.getAsJsonObject("BuildingOrder");
                long completionTime = BuildingOrder.get("to_be_completed_at").getAsLong();
                buildingInQueues.add(new BuildingInQueue(order_param_id, completionTime));
                buildingFinishingTimes.put(order_param_id, completionTime);
//                System.out.println("Putting in order: " + order_param_id + " to be completed at: " + completionTime);
            }
        }


        return true;
    }

    public boolean canBuildAnything() {
        for (Building building : buildingList) {
            if (canBuild(building.getBuildingType())) {
                return true;
            }
        }
        return false;
    }

    private boolean rushDocksBuildings() {
        if (canBuild(Building.BuildingType.lumber) && getBuilding(Building.BuildingType.lumber).getLevel() < 15 && getBuilding(Building.BuildingType.lumber).getBuildTo() >= 15) {
            return true;
        } else if (canBuild(Building.BuildingType.ironer) && getBuilding(Building.BuildingType.ironer).getLevel() < 10 && getBuilding(Building.BuildingType.ironer).getBuildTo() >= 10) {
            return true;
        }

        //Senate is the first small building that's attempted to be built, so no need to add it here!
        return false;
    }

    private Building.BuildingType docksBuildingRequirementRush() {
        if (canBuild(Building.BuildingType.lumber) && getBuilding(Building.BuildingType.lumber).getLevel() < 15) {
            return Building.BuildingType.lumber;
        } else if (canBuild(Building.BuildingType.ironer) && getBuilding(Building.BuildingType.ironer).getLevel() < 10) {
            return Building.BuildingType.ironer;
        }

        return null;
    }



    private boolean canBuild(Building.BuildingType building) {
        Building actualBuilding = getBuilding(building);
//        if (actualBuilding != null) {
//            log("Building name: " + actualBuilding.getName() +
//                    " is building queue full: " + isBuildingQueueFull +
//                    " can building be upgraded: " + actualBuilding.canUpgrade() +
//                    " current level: " + actualBuilding.getLevel() +
//                    " level to build to: " + actualBuilding.getBuildTo() +
//                    " Can build: " + (actualBuilding != null && (!isBuildingQueueFull && actualBuilding.canUpgrade() && actualBuilding.getLevel() < actualBuilding.getBuildTo())));
//        }
        return actualBuilding != null && (!isBuildingQueueFull && actualBuilding.canUpgrade() && actualBuilding.getLevel() < actualBuilding.getBuildTo());
    }

    public boolean build(Building.BuildingType buildingType, final int buildingLevel) {
        final Building building = getBuilding(buildingType);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4) {\n" +
                        "        alert(\"BuildingInQueueData:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + server + ".grepolis.com/game/frontend_bridge?town_id=" + getId() + "&action=execute&h=" +csrftoken + "&json=%7B%22model_url%22%3A%22BuildingOrder%22%2C%22action_name%22%3A%22buildUp%22%2C%22arguments%22%3A%7B%22building_id%22%3A%22" + building.getName() + "%22%7D%2C%22town_id%22%3A" + getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
//                GrepolisBot.webView.getEngine().executeScript("BuildingMain.buildBuilding('" + building.getName() + "'," + buildingLevel + ")");
                log(name + " " + building.getBuildingType().inGameName + " has been added to the building queue!");
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

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(ArrayList<Building> buildingList) {
        this.buildingList = buildingList;
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


    public boolean hasConqueror() {
        return hasConqueror;
    }

    public void setHasConqueror(boolean hasConqueror) {
        this.hasConqueror = hasConqueror;
    }

    public int getIsland_x() {
        return island_x;
    }

    public void setIsland_x(int island_x) {
        this.island_x = island_x;
    }

    public int getIsland_y() {
        return island_y;
    }

    public void setIsland_y(int island_y) {
        this.island_y = island_y;
    }

    public String getIslandChunkX() {
        return String.valueOf((int) (island_x / 20.0));
    }

    public String getIslandChunkY() {
        return String.valueOf((int) (island_y / 20.0));
    }

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public boolean isResearched(AcademyResearch research) {
        for (AcademyResearch academyResearch : this.academy.getResearch()) {
            if (academyResearch.equals(research)) {
                return academyResearch.isResearched();
            }
        }
        return false;
    }

    public int getLast_wood() {
        return last_wood;
    }

    public void setLast_wood(int last_wood) {
        this.last_wood = last_wood;
    }

    public int getLast_stone() {
        return last_stone;
    }

    public void setLast_stone(int last_stone) {
        this.last_stone = last_stone;
    }

    public int getLast_iron() {
        return last_iron;
    }

    public void setLast_iron(int last_iron) {
        this.last_iron = last_iron;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getAvailablePopulation() {
        return availablePopulation;
    }

    public void setAvailablePopulation(int availablePopulation) {
        this.availablePopulation = availablePopulation;
    }

    private class BuildingInQueue {
        int param_id;
        long finishTime;

        BuildingInQueue(int param_id, long finishTime) {
            this.param_id = param_id;
            this.finishTime = finishTime;
        }

        public int getParam_id() {
            return param_id;
        }

        public void setParam_id(int param_id) {
            this.param_id = param_id;
        }

        public long getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(long finishTime) {
            this.finishTime = finishTime;
        }
    }
}
