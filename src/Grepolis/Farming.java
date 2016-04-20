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

    private int island_x;
    private int island_y;
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

    public static int allMoodToLootTo = 80;
    public static boolean allEnabled;
    public static IntervalToFarm allIntervalToFarm;



    public Farming(Town town) {
        this.town = town;
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
                if (data.contains("\"island_x\":")) {
                    island_x = Integer.parseInt(data.split(":")[1]);
//                    log("island_x:" +island_x);
                }
                if (data.contains("\"island_y\":")) {
                    island_y = Integer.parseInt(data.split(":")[1]);
//                    log("island_y:" +island_y);
                }
                if (data.contains("\"booty\":")) {
                    booty = Boolean.parseBoolean(data.split(":")[1]);
//                    log("booty researched: " +booty);
                }
                if (data.contains("\"diplomacy_researched\":")) {
                    diplomacy = Boolean.parseBoolean(data.split(":")[1]);
//                    log("diplomacy researched: " +diplomacy);
                }
                if (data.contains("\"trade_office\":") ) {
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
            resourceString = resourceString.substring(resourceString.indexOf(":")+1);
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
//            log("Storage: " + storage + " full: " + ((wood == storage) && (stone == storage) && (iron == storage)));
        } catch (Exception e) {
            logError(e);
            log("Can't load town storage");
        }
        loadFarmingVillages();
    }

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
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/farm_town_overviews" + stringForLoadingVillages() +", true);\n" +
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
        String h = "&h=" +town.getCsrftoken();
        sb.append(town_id);
        sb.append(action);
        sb.append(h);

        //JSON starts here!
        sb.append("&json=' +encodeURIComponent(JSON.stringify(");

        sb.append("{\"island_x\":").append(island_x);
        sb.append(",\"island_y\":").append(island_y);
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
                        farmingVillage.setId(Integer.parseInt(data.split(":")[1]));
                        farmingVillage.setCanFarm(allData.contains("farm_town_" + farmingVillage.getId() +" checked"));
//                        log("id:" +farmingVillage.getId());
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
                        farmingVillage.setLoot(data.contains("null"));
//                        log("loot: " +farmingVillage.isLoot());
                    }
                    if (data.contains("\"rel\":")) {
                        farmingVillage.setRel(data.contains("\"rel\":1"));
//                        log("loot: " +farmingVillage.isLoot());
                    }

                }
                if (farmingVillage.getId() != 0) {
                    farmingVillages.add(farmingVillage);
                }
            }
        }
    }

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
                sb.append(farmingVillage.getId());
                sb.append(",");
            }
        }
        if (sb.toString().contains(",")) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("],\"time_option\":");

        sb.append(String.valueOf(intervalToFarm.seconds));

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
            if (farmingVillage.isCanFarm()) {
                return true;
            }
        }
        return false;
    }

    private String getFarmAmount() {
        return isMoodHighEnough() ? "\"double\"" : "\"normal\"";
    }

    private boolean isMoodHighEnough() {
        if (moodToLootTo == 100) {
            return false;
        }

        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.getMood() < moodToLootTo) {
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

}
