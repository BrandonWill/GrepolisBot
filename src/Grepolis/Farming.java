package Grepolis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Grepolis.util.MyLogger.log;
import static Grepolis.util.MyLogger.logError;

/**
 * @Author Brandon
 * Created by Brandon on 10/16/2015.
 * Time: 5:48 PM
 */
public class Farming {
    private Town town;
    private ArrayList<FarmingVillage> farmingVillages = new ArrayList<>();


    private boolean booty;
    private boolean diplomacy;
    private boolean enabled;
    private int trade_office;
    private int storage;
    private int wood = 0;
    private int stone = 0;
    private int iron = 0;
    private int moodToLootTo = 80;
    private IntervalToFarm intervalToFarm = IntervalToFarm.MINUTES_FIVE;

    private static boolean captainEnabled = true;
    private static int allMoodToLootTo = 80;
    private static boolean allEnabled;
    private static IntervalToFarm allIntervalToFarm = IntervalToFarm.MINUTES_FIVE;
    private static long captainExpiresAt;

    private static boolean battlePointVillages = false;
    private static double gameSpeed = 1;
    private HashMap<Integer, Integer> notMyVillages = new HashMap<>();



    public Farming(Town town) {
        this.town = town;
        if (battlePointVillages) {
            moodToLootTo = 100;
            allMoodToLootTo = 100;
        }
    }

    public void parseHTML(String townData) {
        townData = townData.replaceAll("FarmData:200", "");

        JsonElement jElement = new JsonParser().parse(townData);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonObject json = jObject.getAsJsonObject("json");
        JsonArray towns = json.getAsJsonArray("towns");

        JsonObject currentTown = null;
        for (JsonElement jsonElement : towns) {
            int townID = jsonElement.getAsJsonObject().get("id").getAsInt();
            if (townID == town.getId()) {
                currentTown = jsonElement.getAsJsonObject();
            }
        }

        if (currentTown != null) {
            storage = currentTown.get("storage_volume").getAsInt();
            booty = currentTown.get("booty").getAsBoolean();
            diplomacy = currentTown.get("diplomacy_researched").getAsBoolean();
            updateFarmIntervals();
            trade_office = currentTown.get("trade_office").getAsInt();
            JsonObject resources = currentTown.getAsJsonObject("resources");
            wood = resources.get("wood").getAsInt();
            stone = resources.get("stone").getAsInt();
            iron = resources.get("iron").getAsInt();
            town.setFullStorage(((wood == storage) && (stone == storage) && (iron == storage)));

        }
////        log("Town Data: " + townData);
//        String townString = townData.substring(townData.indexOf(town.getId() + ""));
//        String storageString = townString.substring(townString.indexOf("\"storage_volume\":"));
//
//        String currentTownData = null;
////        log("Town string: " +townString);
//        for (String data : townString.split("\"id\"")) {
//            if (data.contains(String.valueOf(town.getId()))) {
//                currentTownData = data;
//                break;
//            }
//        }
//
//        if (currentTownData != null) {
////            log("Current town data: " +currentTownData);
//            for (String data : currentTownData.split(",")) {
//                /* Not needed anymore. We load this at the start of the bot.
//                if (data.contains("\"island_x\":")) {
//                    island_x = Integer.parseInt(data.split(":")[1]);
////                    log("island_x:" +island_x);
//                }
//                if (data.contains("\"island_y\":")) {
//                    island_y = Integer.parseInt(data.split(":")[1]);
////                    log("island_y:" +island_y);
//                }
//                */
//                if (data.contains("\"booty\":")) {
//                    booty = Boolean.parseBoolean(data.split(":")[1]);
////                    log("booty researched: " +booty);
//                }
//                if (data.contains("\"diplomacy_researched\":")) {
//                    diplomacy = Boolean.parseBoolean(data.split(":")[1]);
////                    log("diplomacy researched: " +diplomacy);
//                }
//                if (data.contains("\"trade_office\":")) {
//                    trade_office = Integer.parseInt(data.split(":")[1]);
////                    log("trade_office level: " +trade_office);
//                }
//            }
//        }
//
//        wood = 0;
//        stone = 0;
//        iron = 0;
//        try {
//
//            String resourceString = townString.substring(townString.indexOf("\"resources\":{"), townString.indexOf(",\"production\""));
//            resourceString = resourceString.substring(resourceString.indexOf(":") + 1);
//            resourceString = resourceString.replaceAll("\"", "").replaceAll("}", "").replaceAll("\\{", "");
//            //.substring(resources[0].indexOf("resources_last_update"), resources[0].indexOf("island_id"))
////                        log("Resource string: " +resourceString);
//
//            for (String resource : resourceString.split(",")) {
//
//                if (resource.contains("wood")) {
//                    wood = Integer.parseInt(resource.split(":")[1]);
////                                log("Wood: " + wood);
//                }
//                if (resource.contains("stone")) {
//                    stone = Integer.parseInt(resource.split(":")[1]);
////                                log("Stone: " + stone);
//                }
//                if (resource.contains("iron")) {
//                    iron = Integer.parseInt(resource.split(":")[1]);
////                                log("Iron: " + iron);
//                }
//            }
//        } catch (Exception e) {
//            logError(e);
//            log("Can't load town resources");
//        }
////                        log("Storage string: " +storageString);
//
//
//        try {
//            storageString = storageString.substring(0, storageString.indexOf(","));
//            storage = Integer.parseInt(storageString.split(":")[1]);
//            town.setFullStorage(((wood == storage) && (stone == storage) && (iron == storage)));
////
//        } catch (Exception e) {
//            logError(e);
//            log("Can't load town storage");
//        }
        loadFarmingVillages();
    }

    /**
     * This method opens up the captain interface and loads all the farmer data from it.
     */
    public void loadFarmingVillages() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("function readBody(xhr) {\n" +
                        "    var data;\n" +
                        "    if (!xhr.responseType || xhr.responseType === \"text\") {\n" +
                        "        data = xhr.responseText;\n" +
                        "    } else if (xhr.responseType === \"document\") {\n" +
                        "        data = xhr.responseXML;\n" +
                        "    } else {\n" +
                        "        data = xhr.response;\n" +
                        "    }\n" +
                        "    return data;\n" +
                        "}\n" +
                        "\n" +
                        "var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        alert(\"VillagesData:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/farm_town_overviews" + stringForLoadingVillages() + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
//                log("Loading farming villages!");
            }
        });
    }

    private String stringForLoadingVillages() {
        StringBuilder sb = new StringBuilder();
        String town_id = "?town_id=" + town.getId();
        String action = "&action=get_farm_towns_for_town";
        String h = "&h=" + town.getCsrftoken();
        sb.append(town_id);
        sb.append(action);
        sb.append(h);

        //JSON starts here!
        sb.append("&json=' +encodeURIComponent(JSON.stringify(");

        sb.append("{\"island_x\":").append(town.getIsland_x());
        sb.append(",\"island_y\":").append(town.getIsland_y());
        sb.append(",\"current_town_id\":").append(town.getId());
        sb.append(",\"booty_researched\":").append(boolToString(booty));
        sb.append(",\"diplomacy_researched\":").append(boolToString(diplomacy));
        sb.append(",\"trade_office\":").append(trade_office);
        sb.append(",\"town_id\":").append(town.getId());
        sb.append(",\"nl_init\":true}");
        sb.append("))");

//        log("JSON data: " +sb.toString());
        return sb.toString();
    }

    private String boolToString(boolean value) {
        return value ? "1" : "\"\"";
    }

    public void parseVillageData(String villagesData) {
        //relation_status": 1




//        log("In village data! " +villagesData);
        if (villagesData.contains("[{")) {
            String allData = villagesData;
            farmingVillages.clear();
            villagesData = villagesData.substring(villagesData.indexOf("[{"), villagesData.indexOf("}]"));
            String villages[] = villagesData.split("\\{");
            for (String village : villages) {
//                log("found village data: " +village);
                FarmingVillage farmingVillage = new FarmingVillage(booty, intervalToFarm);
                for (String data : village.split(",")) {
                    if (data.contains("\"id\"")) {
                        farmingVillage.setFarm_town_id(Integer.parseInt(data.split(":")[1]));
                        farmingVillage.setCanFarm(allData.contains("farm_town_" + farmingVillage.getFarm_town_id() + " checked"));
//                        log("id:" +farmingVillage.getFarm_town_id());
                    }
                    if (data.contains("name")) {
                        farmingVillage.setName(data.split(":")[1]);
                    }
                    if (data.contains("mood")) {
                        farmingVillage.setMood(Integer.parseInt(data.split(":")[1]));
//                        log("mood:" +farmingVillage.getMood());
                    }
                    if (data.contains("lootable_human")) {
                        //saves if it's been looted or not.
                        farmingVillage.setLootable_human(data.split(":")[1].contains("at"));
                    }
                    if (data.contains("\"loot\"")) {
//                        System.out.println("loot data:" +data);
                        String timeToLoot = data.split(":")[1];
                        if (isStringDigit(timeToLoot)) {
                            farmingVillage.setLoot(Long.parseLong(timeToLoot));
                            town.setTimeToFarm(Long.parseLong(timeToLoot));
                        }
//                        System.out.println("loot: " +farmingVillage.getLoot());
                    }
                    if (data.contains("\"rel\":")) {
                        farmingVillage.setRel(data.contains("\"rel\":1"));
//                        log("loot: " +farmingVillage.isLoot());
                    }
                }
                if (farmingVillage.getFarm_town_id() != 0) {
                    farmingVillages.add(farmingVillage);
                }
            }
        }
    }

    /**
     * This opens up the farming village interface. This is only done to look like a human!
     * Once opened, it will send an alert. It then is handled inside of the addAlert() method.
     */
    public boolean openFarmingVillageInterface(final FarmingVillage farmingVillage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("function readBody(xhr) {\n" +
                        "    var data;\n" +
                        "    if (!xhr.responseType || xhr.responseType === \"text\") {\n" +
                        "        data = xhr.responseText;\n" +
                        "    } else if (xhr.responseType === \"document\") {\n" +
                        "        data = xhr.responseXML;\n" +
                        "    } else {\n" +
                        "        data = xhr.response;\n" +
                        "    }\n" +
                        "    return data;\n" +
                        "}\n" +
                        "\n" +
                        "var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        alert(\"FarmingInterfaceOpened:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/" + getFarmerInterfaceJSON(farmingVillage) + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
            }
        });
        return true;
    }

    private String getFarmerInterfaceJSON(FarmingVillage farmingVillage) {
        //{"id":????,"town_id":?????,"nl_init":true}
        StringBuilder sb = new StringBuilder();
        if (battlePointVillages) {
            sb.append("frontend_bridge?town_id=").append(town.getId()).append("&action=fetch&h=").append(town.getCsrftoken());

            sb.append("&json=' +encodeURIComponent(JSON.stringify(");

            sb.append("{\"window_type\":\"farm_town\",\"tab_type\":\"index\",\"known_data\":{\"models\":[\"PlayerKillpoints\",\"PremiumFeatures\"]," +
                    "\"collections\":[\"FarmTowns\",\"Towns\"],\"templates\":[]},\"arguments\":" +
                    "{\"farm_town_id\":" + farmingVillage.getFarm_town_id() +"},\"town_id\":" + town.getId() +",\"nl_init\":true}");

            sb.append("))");

            return sb.toString();

        } else {

            sb.append("farm_town_info?town_id=").append(town.getId()).append("&action=claim_info&h=").append(town.getCsrftoken());

            sb.append("&json=' +encodeURIComponent(JSON.stringify(");

            sb.append("{\"id\":");
            sb.append(farmingVillage.getFarm_town_id());
            sb.append(",");

            sb.append("\"town_id\":");
            sb.append(town.getId());
            sb.append(",");

            sb.append("\"nl_init\":true}");

            sb.append("))");

            return sb.toString();
        }
    }

    public boolean farmTheVillageFromMap(final FarmingVillage farmingVillage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("function readBody(xhr) {\n" +
                        "    var data;\n" +
                        "    if (!xhr.responseType || xhr.responseType === \"text\") {\n" +
                        "        data = xhr.responseText;\n" +
                        "    } else if (xhr.responseType === \"document\") {\n" +
                        "        data = xhr.responseXML;\n" +
                        "    } else {\n" +
                        "        data = xhr.response;\n" +
                        "    }\n" +
                        "    return data;\n" +
                        "}\n" +
                        "\n" +
                        "var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        alert(\"TownID," + town.getId() + ":FarmingVillageID," + farmingVillage.getFarm_town_id() + ":battlePointID," + farmingVillage.getBattlePointFarmID() + ":FarmedTheVillage:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + town.getServer() + ".grepolis.com/game/" + getFarmingVillageJSON(farmingVillage) + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
            }
        });
        return true;
    }

    private String getFarmingVillageJSON(FarmingVillage farmingVillage) {
        StringBuilder sb = new StringBuilder();

        if (battlePointVillages) {
            sb.append("frontend_bridge?town_id=").append(town.getId()).append("&action=execute&h=").append(town.getCsrftoken());

            sb.append("&json=' +encodeURIComponent(JSON.stringify(");

            sb.append("{\"model_url\":\"FarmTownPlayerRelation/" + farmingVillage.getBattlePointFarmID() +"\",\"action_name\":\"claim\",\"arguments\":" +
                    "{\"farm_town_id\":" + farmingVillage.getFarm_town_id() +",\"type\":\"resources\",\"option\":" + getOptionNumberFromInterval() + "},\"town_id\":" + town.getId() + ",\"nl_init\":true}");

            sb.append("))");

            return sb.toString();

        } else {

            sb.append("farm_town_info?town_id=").append(town.getId()).append("&action=claim_load&h=").append(town.getCsrftoken());

            sb.append("&json=' +encodeURIComponent(JSON.stringify(");

            sb.append("{\"target_id\":");
            sb.append(farmingVillage.getFarm_town_id());
            sb.append(",");

            sb.append("\"claim_type\":");
            sb.append(getFarmAmount());
            sb.append(",");

            sb.append("\"time\":");
            sb.append(String.valueOf(intervalToFarm.seconds));
            sb.append(",");

            sb.append("\"town_id\":");
            sb.append(town.getId());
            sb.append(",");

            sb.append("\"nl_init\":true}");
            sb.append("))");

//        log("sb.toString(): " +sb.toString());
        }

        return sb.toString();
    }


    /* Incorrect but functional way to parse all the villages...
     * Functions by obtaining all the villages IDs and attempts for farm them all
     * Bot will remove the ones it can't farm
     * However, we can find the farming village island_x and island_y and compare this to the town's island_x & island_y
     * This is correct way to handle it in the future so towns aren't farming 50+ villages on their first run through
     *
     * This function is confusing. It handles 2 entirely different calls from the game!
     * The first to originally get the village ids, which is required to find out more data
     * The second to actually get the mood, update the "id" given above to farm_town_id because "id" in this call is actually the battlepoint village ID (which is unique?)
     * Farm_town_id used to be unique, but it isn't anymore. 
     */
    //

    public boolean parseVillagesFromMap(String villageData) {
        //"id":????,"researchName":"Rosta","dir":"n","expansion_stage":1,"x":???,"y":???,"ox":???,"oy":???,"offer":"iron",
        // "demand":"stone","mood":84,"relation_status":1,"ratio":1.25,"loot":1469053323,
        // "lootable_human":"on 2016-07-20 at 23:22 ","looted":1469053023},"80":
//        System.out.println("VillageData: " +villageData);
        if (villageData.contains("LoadedVillagesFromMap:200")) {
            farmingVillages.clear();
            int requiredVillageX = town.getIsland_x();
            int requiredVillageY = town.getIsland_y();
            villageData = villageData.replaceAll("LoadedVillagesFromMap:200", "");

            JsonElement jElement = new JsonParser().parse(villageData);
            JsonObject jObject = jElement.getAsJsonObject();
            JsonObject json = jObject.getAsJsonObject("json");
            JsonArray data = json.getAsJsonArray("data");
            JsonObject dataObject = data.get(0).getAsJsonObject();
            JsonObject townObject = dataObject.get("towns").getAsJsonObject();

            //Alright so whoever programmed this thing made them all objects instead of array... so this is my solution
            for (int i = 0; i < 10000; i++) {
                if (townObject.get("" +i) != null) {
                    JsonObject villageObject = townObject.get("" + i).getAsJsonObject();
                    //This list contains not only towns, but farming villages! Only villages have relation_status
                    if (villageObject.has("relation_status")) {
                        int relationStatus = villageObject.get("relation_status").getAsInt();
                        int island_x = villageObject.get("x").getAsInt();
                        int island_y = villageObject.get("y").getAsInt();
                        //If we own the village, then it will be 1
                        if (relationStatus == 1 && island_x == requiredVillageX && island_y == requiredVillageY) {
                            System.out.println("Village object: " +villageObject.toString());
                            FarmingVillage farmingVillage = new FarmingVillage(booty, intervalToFarm);
                            farmingVillage.setFarm_town_id(villageObject.get("id").getAsInt());
                            farmingVillage.setIsland_x(island_x);
                            farmingVillage.setIsland_y(island_y);
                            farmingVillage.setName(villageObject.get("name").getAsString());
                            farmingVillage.setStage(villageObject.get("expansion_stage").getAsInt());
                            farmingVillage.setMood(villageObject.get("mood").getAsInt());
                            farmingVillages.add(farmingVillage);
                        }
                    }
                } else {
                    break;
                }
            }
        } else if (villageData.contains("FarmingInterfaceOpened:200")) {
            villageData = villageData.replaceAll("FarmingInterfaceOpened:200", "");

            JsonElement jElement = new JsonParser().parse(villageData);
            JsonObject jObject = jElement.getAsJsonObject();
            JsonObject json = jObject.getAsJsonObject("json");
            JsonObject collections = json.getAsJsonObject("collections");
            JsonObject farmTownPlayerRelations = collections.getAsJsonObject("FarmTownPlayerRelations");
            JsonArray data = farmTownPlayerRelations.getAsJsonArray("data");

            for (JsonElement jsonElement : data) {
                JsonObject farmingVillageObject = jsonElement.getAsJsonObject().get("d").getAsJsonObject();
                int id = farmingVillageObject.get("id").getAsInt();
                int farm_town_id = farmingVillageObject.get("farm_town_id").getAsInt();
                FarmingVillage farmingVillage = getFarmingVillage(farm_town_id);
                if (farmingVillage != null) {
                    farmingVillage.setRel(true);
                    if (!farmingVillageObject.get("lootable_at").isJsonNull()) {
                        long timeToFarm = farmingVillageObject.get("lootable_at").getAsLong();
                        farmingVillage.setLoot(timeToFarm);
                        town.setTimeToFarm(timeToFarm);
                    }
                    farmingVillage.setBattlePointFarmID(id);
                    farmingVillage.setResourcesLooted(farmingVillageObject.get("loot").getAsInt());
                    farmingVillage.setStage(farmingVillageObject.get("expansion_stage").getAsInt());
//                    log("Lootable at: " +timeToFarm);
//                    log("can farm: " +farmingVillage.canFarm());
//                    log("Farming village found in " +town.getName() + " with the researchName of: " +farmingVillage.getName());

                }
            }

        }
//        String[] parsed = villageData.split("\\{");
//        //farmingVillages.clear();
//        for (String village : parsed) {
//            if (village.contains("\"id\"") && village.contains("\"relation_status\":1")) {
////                System.out.println("Parsed: " +village);
//                FarmingVillage farmingVillage = new FarmingVillage(booty, intervalToFarm);
//
//                for (String data : village.split(",")) {
//                    if (data.contains("farm_town_id")) {
//                        String id = data.split(":")[1];
//                        if (isStringDigit(id)) {
//                            int actualID = Integer.parseInt(id);
//                            if (getFarmingVillage(actualID) != null) {
//                                farmingVillage = getFarmingVillage(actualID);
//                            } else {
//                                if (farmingVillage != null) {
//                                    farmingVillage.setFarm_town_id(actualID);
//                                }
//                            }
//                        } else {
//                            break;
//                        }
////                        System.out.println("id:" + farmingVillage.getFarm_town_id());
//                    }
//                    if (data.contains("\"id\"")) {
//                        String id = data.split(":")[1];
//                        if (isStringDigit(id)) {
//                            int actualID = Integer.parseInt(id);
//                            if (getFarmingVillage(actualID) != null) {
//                                farmingVillage = getFarmingVillage(actualID);
//                            } else {
//                                if (farmingVillage != null) {
//                                    farmingVillage.setBattlePointFarmID(actualID);
//                                }
//                            }
//                        } else {
//                            break;
//                        }
////                        System.out.println("id:" + farmingVillage.getFarm_town_id());
//                    }
//                    if (data.contains("researchName")) {
//                        if (farmingVillage != null) {
//                            farmingVillage.setName(data.split(":")[1]);
//                        }
//                    }
//                    if (data.contains("\"expansion_stage\"")) {
//                        if (farmingVillage != null) {
////                            System.out.println("Stage : " + data);
//                            farmingVillage.setStage(Integer.parseInt(data.split(":")[1]));
//                        }
//                    }
//                    if (data.contains("mood")) {
//                        if (farmingVillage != null) {
//                            farmingVillage.setMood(Integer.parseInt(data.split(":")[1]));
//                        }
////                        System.out.println("mood:" + farmingVillage.getMood());
//                    }
//                    if (data.contains("\"loot\":")) {
//                        if (farmingVillage != null) {
//                            String lootData = data.split(":")[1];
//                            farmingVillage.setResourcesLooted(Integer.parseInt(lootData));
//                        }
//                    }
//                    if (data.contains("\"lootable_at\"")) {
//                        String holder = data.split(":")[1];
//                        if (holder != null) {
//                            if (isStringDigit(holder)) {
//                                long timeToFarm = Long.parseLong(holder);
//                                if (farmingVillage != null) {
//                                    farmingVillage.setLoot(timeToFarm);
//                                }
//                                town.setTimeToFarm(timeToFarm);
//                            }
//
//                        }
//                    }
//                    if (data.contains("\"relation_status\":")) {
//                        if (farmingVillage != null) {
//                            farmingVillage.setRel(data.contains("\"relation_status\":1"));
//                        }
////                        System.out.println("relation_status: " + farmingVillage.isRel());
//                    }
//                }
//                if (farmingVillage != null && farmingVillage.getFarm_town_id() != 0) {
//                    boolean notAdded = true;
//                    for (FarmingVillage village1 : farmingVillages) {
//                        if (village1.getFarm_town_id() == farmingVillage.getFarm_town_id()) {
//                            notAdded = false;
//                        }
//                    }
//                    if (notAdded) {
////                        System.out.println("Added to the villages!");
//                        farmingVillages.add(farmingVillage);
//                    }
//                }
//            }
//        }
//        for (Map.Entry<Integer, Integer> entry : notMyVillages.entrySet()) {
//            int battleVillageID = entry.getKey();
//            int farmingVillageID = entry.getValue();
//
//
//            for (FarmingVillage farmingVillage : farmingVillages) {
//                if (farmingVillage.getFarm_town_id() == farmingVillageID && farmingVillage.getBattlePointFarmID() == battleVillageID)  {
//                    farmingVillages.remove(farmingVillage);
//                    break;
//                }
//            }
//        }


        return true;
    }

    private FarmingVillage getFarmingVillage(int id) {
        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.getFarm_town_id() == id) {
                return farmingVillage;
            }
        }
        return null;
    }

    public void loadVillagesFromMap() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("function readBody(xhr) {\n" +
                        "    var data;\n" +
                        "    if (!xhr.responseType || xhr.responseType === \"text\") {\n" +
                        "        data = xhr.responseText;\n" +
                        "    } else if (xhr.responseType === \"document\") {\n" +
                        "        data = xhr.responseXML;\n" +
                        "    } else {\n" +
                        "        data = xhr.response;\n" +
                        "    }\n" +
                        "    return data;\n" +
                        "}\n" +
                        "\n" +
                        "var xhr = new XMLHttpRequest();\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        alert(\"LoadedVillagesFromMap:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/" + villagesFromMapJSON() + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
            }
        });
    }

    private String villagesFromMapJSON() {

        StringBuilder sb = new StringBuilder();
        //{"window_type":"farm_town","tab_type":"index","known_data":{"models":["PlayerKillpoints","PremiumFeatures"],"collections":["FarmTownPlayerRelations","FarmTowns","Towns"],"templates":[]},"arguments":{"farm_town_id":xxxx},"town_id":xxxxx,"nl_init":true}
        //We are telling the game we don't know the player relations!
        //TODO in the future? We don't need this right now!

        //sb.append("frontend_bridge?town_id=").append(town.getFarm_town_id()).append("&action=fetch&h=").append(town.getCsrftoken()).append("&json=' +encodeURIComponent(JSON.stringify(");

        sb.append("map_data?town_id=").append(town.getId()).append("&action=get_chunks&h=").append(town.getCsrftoken()).append("&json=' +encodeURIComponent(JSON.stringify(");

        //{"chunks":[{"x":??,"y":??,"timestamp":0}],"town_id":?????,"nl_init":true}
        sb.append("{\"chunks\":[{\"x\":");
//        System.out.println("island_x: " +island_x + " ChunkX:" +getChunkX() + " test: " +town.getFarming().getIsland_x());
        sb.append(town.getIslandChunkX());

        sb.append(",\"y\":");
//        System.out.println("island_y: " +island_y + " ChunkY:" +getChunkY() + " test: " +town.getFarming().getIsland_y());
        sb.append(town.getIslandChunkY());

        sb.append(",\"timestamp\":");
        sb.append(0);
        sb.append("}],");

        sb.append("\"town_id\":");
        sb.append(town.getId());
        sb.append(",");

        sb.append("\"nl_init\":true}");
        sb.append("))");

//        System.out.println("Sb being encoded: " +sb.toString());

        return sb.toString();
    }

    /**
     * This method is for farming with captain.
     *
     * @return <b>True</b> if any farmers are available. <b>False</b> otherwise.
     */
    public boolean farmTheVillages() {
        if (hasAFarmerAvailable()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    GrepolisBot.webView.getEngine().executeScript("function readBody(xhr) {\n" +
                            "    var data;\n" +
                            "    if (!xhr.responseType || xhr.responseType === \"text\") {\n" +
                            "        data = xhr.responseText;\n" +
                            "    } else if (xhr.responseType === \"document\") {\n" +
                            "        data = xhr.responseXML;\n" +
                            "    } else {\n" +
                            "        data = xhr.response;\n" +
                            "    }\n" +
                            "    return data;\n" +
                            "}\n" +
                            "\n" +
                            "var xhr = new XMLHttpRequest();\n" +
                            "xhr.onreadystatechange = function() {\n" +
                            "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                            "        alert(readBody(xhr));\n" +
                            "    }\n" +
                            "}\n" +
                            "xhr.open('POST', 'https://" + town.getServer() + ".grepolis.com/game/farm_town_overviews?town_id=" + town.getId() + "&action=claim_loads&h=" + town.getCsrftoken() + "&json=' +encodeURIComponent(JSON.stringify(" + buildTheVillagesString() + ")), true);\n" +
                            "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                            "xhr.send(null);");
                }
            });
            return true;
        }
        return false;
    }


    private String buildTheVillagesString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"farm_town_ids\":[");
        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.isRel()) {
                sb.append(farmingVillage.getFarm_town_id());
                sb.append(",");
            }
        }
        if (sb.toString().contains(",")) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("],\"time_option\":");

        int farmTime = booty ? intervalToFarm.seconds * 2 : intervalToFarm.seconds;
        sb.append(String.valueOf(farmTime));

        sb.append(",\"claim_factor\":");
        sb.append(getFarmAmount());

        sb.append(",\"current_town_id\":");
        sb.append(town.getId());

        sb.append(",\"town_id\":");
        sb.append(town.getId());

        sb.append(",\"nl_init\":true}");

//        log("sb.toString(): " +sb.toString());

        return sb.toString();
    }

    private boolean hasAFarmerAvailable() {
        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.canFarm()) {
                return true;
            }
        }
        return false;
    }



    private String getFarmAmount() {
        return isMoodHighEnough() ? "\"double\"" : "\"normal\"";
    }

    private boolean isMoodHighEnough() {
        if (getMoodToLootTo() == 100) {
            return false;
        }

        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.getMood() < getMoodToLootTo()) {
                return false;
            }
        }
        return true;
    }

    private boolean isStringDigit(String number) {
        for (Character c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public void setMoodToLootTo(int moodToLootTo) {
        this.moodToLootTo = moodToLootTo;
    }

    public IntervalToFarm getIntervalToFarm() {
        return isAllEnabled() ? allIntervalToFarm : intervalToFarm;
    }

    public void setIntervalToFarm(IntervalToFarm intervalToFarm) {
        this.intervalToFarm = intervalToFarm;
    }

    public int getMoodToLootTo() {
        return isAllEnabled() ? allMoodToLootTo : moodToLootTo;
    }

    public int getStorage() {
        return storage > 0 ? storage : town.getStorage();
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getWood() {
        return wood > 0 ? wood : town.getLast_wood();
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getStone() {
        return stone > 0 ? stone : town.getLast_stone();
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getIron() {
        return iron > 0 ? iron : town.getLast_iron();
    }

    public void setIron(int iron) {
        this.iron = iron;
    }

    public boolean isEnabled() {
        return isAllEnabled() ? isAllEnabled() : enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static int getAllMoodToLootTo() {
        return allMoodToLootTo;
    }

    public static void setAllMoodToLootTo(int allMoodToLootTo) {
        Farming.allMoodToLootTo = allMoodToLootTo;
    }

    public static boolean isAllEnabled() {
        return allEnabled;
    }

    public static void setAllEnabled(boolean allEnabled) {
        Farming.allEnabled = allEnabled;
    }

    public static IntervalToFarm getAllIntervalToFarm() {
        return allIntervalToFarm;
    }

    public static void setAllIntervalToFarm(IntervalToFarm allIntervalToFarm) {
        Farming.allIntervalToFarm = allIntervalToFarm;
    }

    private int getOptionNumberFromInterval() {
        switch (intervalToFarm) {
            case MINUTES_FIVE:
                return 1;

            case MINUTES_TWENTY:
                return 2;

            case MINUTES_NINETY:
                return 3;

            case MINUTES_TWO_HUNDRED_FORTY:
                return 4;

            default:
                return 1;
        }
    }

    private void updateFarmIntervals() {
        switch (intervalToFarm) {
            case MINUTES_FIVE:
                if (diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_TEN;
                }
                break;

            case MINUTES_TEN:
                if (!diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_FIVE;
                }
                break;

            case MINUTES_TWENTY:
                if (diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_FORTY;
                }
                break;

            case MINUTES_FORTY:
                if (!diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_TWENTY;
                }
                break;

            case MINUTES_NINETY:
                if (diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_ONE_HUNDRED_EIGHTY;
                }
                break;

            case MINUTES_ONE_HUNDRED_EIGHTY:
                if (!diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_NINETY;
                }
                break;

            case MINUTES_TWO_HUNDRED_FORTY:
                if (diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_FOUR_HUNDRED_EIGHTY;
                }
                break;

            case MINUTES_FOUR_HUNDRED_EIGHTY:
                if (!diplomacy) {
                    intervalToFarm = IntervalToFarm.MINUTES_TWO_HUNDRED_FORTY;
                }
                break;
        }
    }

    public enum IntervalToFarm {
        MINUTES_FIVE(300, 1),
        MINUTES_TEN(600, 1),
        MINUTES_TWENTY(1200, 2),
        MINUTES_FORTY(2400, 2),
        MINUTES_NINETY(5400, 3),
        MINUTES_ONE_HUNDRED_EIGHTY(10800, 3),
        MINUTES_TWO_HUNDRED_FORTY(14400, 4),
        MINUTES_FOUR_HUNDRED_EIGHTY(28800, 4);

        public int seconds;
        public int option;

        IntervalToFarm(int seconds, int option) {
            this.seconds = seconds;
            this.option = option;
        }

        public int getSeconds() {
            return seconds;
        }
    }

    public static boolean isCaptainEnabled() {
        return captainEnabled;
    }

    public static void setCaptainEnabled(boolean captainEnabled) {
        Farming.captainEnabled = captainEnabled;
    }

    public ArrayList<FarmingVillage> getFarmingVillages() {
        return farmingVillages;
    }

    public static void setBattlePointVillages(boolean battlePointVillages) {
        Farming.battlePointVillages = battlePointVillages;
    }

    public static double getGameSpeed() {
        return gameSpeed;
    }

    public static void setGameSpeed(double gameSpeed) {
        Farming.gameSpeed = gameSpeed;
    }

    public static boolean hasBattlePointVillages() {
        return battlePointVillages;
    }

    public HashMap<Integer, Integer> getNotMyVillages() {
        return notMyVillages;
    }

    public void setNotMyVillages(HashMap<Integer, Integer> notMyVillages) {
        this.notMyVillages = notMyVillages;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public boolean isBooty() {
        return booty;
    }

    public void setBooty(boolean booty) {
        this.booty = booty;
    }

    public static long getCaptainExpiresAt() {
        return captainExpiresAt;
    }

    public static void setCaptainExpiresAt(long captainExpiresAt) {
        Farming.captainExpiresAt = captainExpiresAt;
    }

    public boolean isDiplomacy() {
        return diplomacy;
    }

    public void setDiplomacy(boolean diplomacy) {
        this.diplomacy = diplomacy;
    }
}
