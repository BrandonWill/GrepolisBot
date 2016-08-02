package Grepolis;

import javafx.application.Platform;

import java.util.ArrayList;

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

    public static boolean captainEnabled = true;
    public static int allMoodToLootTo = 80;
    public static boolean allEnabled;
    public static IntervalToFarm allIntervalToFarm = IntervalToFarm.MINUTES_FIVE;

    private static boolean battlePointVillages = false;


    public Farming(Town town) {
        this.town = town;
        if (battlePointVillages) {
            moodToLootTo = 100;
            allMoodToLootTo = 100;
        }
    }

    public void parseHTML(String townData) {
//        log("Town Data: " + townData);
        String townString = townData.substring(townData.indexOf(town.getId() + ""));
        String storageString = townString.substring(townString.indexOf("\"storage_volume\":"));

        String currentTownData = null;
//        log("Town string: " +townString);
        for (String data : townString.split("\"id\"")) {
            if (data.contains(String.valueOf(town.getId()))) {
                currentTownData = data;
                break;
            }
        }

        if (currentTownData != null) {
//            log("Current town data: " +currentTownData);
            for (String data : currentTownData.split(",")) {
                /* Not needed anymore. We load this at the start of the bot.
                if (data.contains("\"island_x\":")) {
                    island_x = Integer.parseInt(data.split(":")[1]);
//                    log("island_x:" +island_x);
                }
                if (data.contains("\"island_y\":")) {
                    island_y = Integer.parseInt(data.split(":")[1]);
//                    log("island_y:" +island_y);
                }
                */
                if (data.contains("\"booty\":")) {
                    booty = Boolean.parseBoolean(data.split(":")[1]);
//                    log("booty researched: " +booty);
                }
                if (data.contains("\"diplomacy_researched\":")) {
                    diplomacy = Boolean.parseBoolean(data.split(":")[1]);
//                    log("diplomacy researched: " +diplomacy);
                }
                if (data.contains("\"trade_office\":")) {
                    trade_office = Integer.parseInt(data.split(":")[1]);
//                    log("trade_office level: " +trade_office);
                }
            }
        }

        wood = 0;
        stone = 0;
        iron = 0;
        try {

            String resourceString = townString.substring(townString.indexOf("\"resources\":{"), townString.indexOf(",\"production\""));
            resourceString = resourceString.substring(resourceString.indexOf(":") + 1);
            resourceString = resourceString.replaceAll("\"", "").replaceAll("}", "").replaceAll("\\{", "");
            //.substring(resources[0].indexOf("resources_last_update"), resources[0].indexOf("island_id"))
//                        log("Resource string: " +resourceString);

            for (String resource : resourceString.split(",")) {

                if (resource.contains("wood")) {
                    wood = Integer.parseInt(resource.split(":")[1]);
//                                log("Wood: " + wood);
                }
                if (resource.contains("stone")) {
                    stone = Integer.parseInt(resource.split(":")[1]);
//                                log("Stone: " + stone);
                }
                if (resource.contains("iron")) {
                    iron = Integer.parseInt(resource.split(":")[1]);
//                                log("Iron: " + iron);
                }
            }
        } catch (Exception e) {
            logError(e);
            log("Can't load town resources");
        }
//                        log("Storage string: " +storageString);


        try {
            storageString = storageString.substring(0, storageString.indexOf(","));
            storage = Integer.parseInt(storageString.split(":")[1]);
            town.setFullStorage(((wood == storage) && (stone == storage) && (iron == storage)));
//
        } catch (Exception e) {
            logError(e);
            log("Can't load town storage");
        }
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
//        log("In village data! " +villagesData);
        if (villagesData.contains("[{")) {
            String allData = villagesData;
            farmingVillages.clear();
            villagesData = villagesData.substring(villagesData.indexOf("[{"), villagesData.indexOf("}]"));
            String villages[] = villagesData.split("\\{");
            for (String village : villages) {
//                log("found village data: " +village);
                FarmingVillage farmingVillage = new FarmingVillage();
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
                        "        alert(\"FarmedTheVillage:\" +xhr.status +readBody(xhr));\n" +
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
                    "{\"farm_town_id\":" + farmingVillage.getFarm_town_id() +",\"type\":\"resources\",\"option\":1},\"town_id\":" + town.getId() + ",\"nl_init\":true}");

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

    public boolean parseVillagesFromMap(String villageData) {
        //"id":????,"name":"Rosta","dir":"n","expansion_stage":1,"x":???,"y":???,"ox":???,"oy":???,"offer":"iron",
        // "demand":"stone","mood":84,"relation_status":1,"ratio":1.25,"loot":1469053323,
        // "lootable_human":"on 2016-07-20 at 23:22 ","looted":1469053023},"80":
        String[] parsed = villageData.split("\\{");
        //farmingVillages.clear();
        for (String village : parsed) {
            if (village.contains("\"id\"") && village.contains("\"relation_status\":1")) {
//                System.out.println("Parsed: " +village);
                FarmingVillage farmingVillage = new FarmingVillage();

                for (String data : village.split(",")) {
                    if (data.contains("farm_town_id")) {
                        String id = data.split(":")[1];
                        if (isStringDigit(id)) {
                            int actualID = Integer.parseInt(id);
                            if (getFarmingVillage(actualID) != null) {
                                farmingVillage = getFarmingVillage(actualID);
                            } else {
                                if (farmingVillage != null) {
                                    farmingVillage.setFarm_town_id(actualID);
                                }
                            }
                        } else {
                            break;
                        }
//                        System.out.println("id:" + farmingVillage.getFarm_town_id());
                    }
                    if (data.contains("\"id\"")) {
                        String id = data.split(":")[1];
                        if (isStringDigit(id)) {
                            int actualID = Integer.parseInt(id);
                            if (getFarmingVillage(actualID) != null) {
                                farmingVillage = getFarmingVillage(actualID);
                            } else {
                                if (farmingVillage != null) {
                                    farmingVillage.setBattlePointFarmID(actualID);
                                }
                            }
                        } else {
                            break;
                        }
//                        System.out.println("id:" + farmingVillage.getFarm_town_id());
                    }
                    if (data.contains("name")) {
                        if (farmingVillage != null) {
                            farmingVillage.setName(data.split(":")[1]);
                        }
                    }
                    if (data.contains("mood")) {
                        if (farmingVillage != null) {
                            farmingVillage.setMood(Integer.parseInt(data.split(":")[1]));
                        }
//                        System.out.println("mood:" + farmingVillage.getMood());
                    }
                    if (data.contains("lootable_human")) {
                        //Not multilingual function and is rather useless.
//                        farmingVillage.setLootable_human(data.split(":")[1].contains("at"));
                    }
                    if (data.contains("\"lootable_at\"")) {
                        String holder = data.split(":")[1];
                        if (holder != null) {
                            if (isStringDigit(holder)) {
                                long timeToFarm = Long.parseLong(holder);
                                if (farmingVillage != null) {
                                    farmingVillage.setLoot(timeToFarm);
                                }
                                town.setTimeToFarm(timeToFarm);
                            }

                        }
                    }
                    if (data.contains("\"relation_status\":")) {
                        if (farmingVillage != null) {
                            farmingVillage.setRel(data.contains("\"relation_status\":1"));
                        }
//                        System.out.println("relation_status: " + farmingVillage.isRel());
                    }
                }
                if (farmingVillage != null && farmingVillage.getFarm_town_id() != 0) {
                    boolean notAdded = true;
                    for (FarmingVillage village1 : farmingVillages) {
                        if (village1.getFarm_town_id() == farmingVillage.getFarm_town_id()) {
                            notAdded = false;
                        }
                    }
                    if (notAdded) {
//                        System.out.println("Added to the villages!");
                        farmingVillages.add(farmingVillage);
                    }
                }
            }
        }
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
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getIron() {
        return iron;
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

    public enum IntervalToFarm {
        MINUTES_FIVE(300),
        MINUTES_TEN(600),
        MINUTES_TWENTY(1200),
        MINUTES_FORTY(2400),
        MINUTES_NINETY(5400),
        MINUTES_ONE_HUNDRED_EIGHTY(10800),
        MINUTES_TWO_HUNDRED_FORTY(14400),
        MINUTES_FOUR_HUNDRED_EIGHTY(28800);

        public int seconds;

        IntervalToFarm(int seconds) {
            this.seconds = seconds;
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


}
