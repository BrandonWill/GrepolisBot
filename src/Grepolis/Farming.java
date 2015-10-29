package Grepolis;

import javafx.application.Platform;

import java.util.ArrayList;

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
    private int trade_office;
    private static int moodToLootTo = 80;
    private static TimeToFarm timeToFarm = TimeToFarm.MINUTES_FIVE;


    public Farming(Town town) {
        this.town = town;
    }

    public void parseHTML(String townData) {
//                                System.out.println("Town ID: " +getId() + "... Resource[0] string: " +resources[0]);
//                        System.out.println("town id found: " +resources[0].substring(resources[0].indexOf(getId() + "")));
        String townString = townData.substring(townData.indexOf(town.getId() + ""));
        String storageString = townString.substring(townString.indexOf("\"storage_volume\":"));

        String currentTownData = null;
//        System.out.println("Town string: " +townString);
        for (String data : townString.split("\"id\"")) {
            if (data.contains(String.valueOf(town.getId()))) {
                currentTownData = data;
                break;
            }
        }

        if (currentTownData != null) {
//            System.out.println("Current town data: " +currentTownData);
            for (String data : currentTownData.split(",")) {
                if (data.contains("\"island_x\":")) {
                    island_x = Integer.parseInt(data.split(":")[1]);
//                    System.out.println("island_x:" +island_x);
                }
                if (data.contains("\"island_y\":")) {
                    island_y = Integer.parseInt(data.split(":")[1]);
//                    System.out.println("island_y:" +island_y);
                }
                if (data.contains("\"booty\":")) {
                    booty = Boolean.parseBoolean(data.split(":")[1]);
//                    System.out.println("booty researched: " +booty);
                }
                if (data.contains("\"diplomacy_researched\":")) {
                    diplomacy = Boolean.parseBoolean(data.split(":")[1]);
//                    System.out.println("diplomacy researched: " +diplomacy);
                }
                if (data.contains("\"trade_office\":") ) {
                    trade_office = Integer.parseInt(data.split(":")[1]);
//                    System.out.println("trade_office level: " +trade_office);
                }
            }
        }

        int wood = 0;
        int stone = 0;
        int iron = 0;
        try {

            String resourceString = townString.substring(townString.indexOf("resources_last_update"), townString.indexOf("island_id"));
            resourceString = resourceString.replaceAll("\"", "");
            //.substring(resources[0].indexOf("resources_last_update"), resources[0].indexOf("island_id"))
//                        System.out.println("Resource string: " +resourceString);

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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't load town resources");
        }
//                        System.out.println("Storage string: " +storageString);



        try {
            storageString = storageString.substring(0, storageString.indexOf(","));
            int storage = Integer.parseInt(storageString.split(":")[1]);
            town.setFullStorage(((wood == storage) && (stone == storage) && (iron == storage)));
        } catch (Exception e) {
            System.out.println("Can't load town storage");
            e.printStackTrace();
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
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/farm_town_overviews?town_id=" + town.getId() + "&action=get_farm_towns_for_town&h=" + town.getCsrftoken() + "d&json=%7B%22island_x%22%3A" + island_x + "%2C%22island_y%22%3A" + island_y + "%2C%22booty_researched%22%3A" + boolToString(booty) +"%2C%22trade_office%22%3A" + trade_office + "%2C%22diplomacy_researched%22%3A" + boolToString(diplomacy) +"%2C%22town_id%22%3A" + town.getId() +"%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
//                System.out.println("Loading farming villages!");
            }
        });
    }

    private String boolToString(boolean value) {
        return value ? "1" : "%22%22";
    }

    public void parseVillageData(String villagesData) {
//        System.out.println("In village data! " +villagesData);
        if (villagesData.contains("[{")) {
            String allData = villagesData;
            farmingVillages.clear();
            villagesData = villagesData.substring(villagesData.indexOf("[{"), villagesData.indexOf("}]"));
            String villages[] = villagesData.split("\\{");
            for (String village : villages) {
//                System.out.println("found village data: " +village);
                FarmingVillage farmingVillage = new FarmingVillage();
                for (String data : village.split(",")) {
                    if (data.contains("\"id\"")) {
                        farmingVillage.setId(Integer.parseInt(data.split(":")[1]));
                        farmingVillage.setCanFarm(allData.contains("farm_town_" + farmingVillage.getId() +" checked"));
//                        System.out.println("id:" +farmingVillage.getId());
                    }
                    if (data.contains("name")) {
                        farmingVillage.setName(data.split(":")[1]);
                    }
                    if (data.contains("mood")) {
                        farmingVillage.setMood(Integer.parseInt(data.split(":")[1]));
//                        System.out.println("mood:" +farmingVillage.getMood());
                    }
                    if (data.contains("lootable_human")) {
                        //saves if it's been looted or not.
                        farmingVillage.setLootable_human(data.split(":")[1].contains("at"));
                    }
                    if (data.contains("\"loot\"")) {
                        farmingVillage.setLoot(data.contains("null"));
//                        System.out.println("loot: " +farmingVillage.isLoot());
                    }
                    if (data.contains("\"rel\":")) {
                        farmingVillage.setRel(data.contains("\"rel\":1"));
//                        System.out.println("loot: " +farmingVillage.isLoot());
                    }

                }
                if (farmingVillage.getId() != 0) {
                    farmingVillages.add(farmingVillage);
                }
            }
        }

    }

    //{"farm_town_ids":[7848,7843,7842,7845,7846,7847,7844],"time_option":300,"claim_factor":"normal","current_town_id":23917,"town_id":32288,"nl_init":true}
    //json=%7B%22farm_town_ids%22%3A%5B7848%2C7843%2C7842%2C7845%2C7846%2C7847%2C7844%5D%2C%22time_option%22%3A300%2C%22claim_factor%22%3A%22normal%22%2C%22current_town_id%22%3A23917%2C%22town_id%22%3A32288%2C%22nl_init%22%3Atrue%7D

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
                            "        console.log(readBody(xhr));\n" +
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

        sb.append(String.valueOf(timeToFarm.seconds));

        sb.append(",\"claim_factor\":");
        sb.append(getFarmAmount());

        sb.append(",\"current_town_id\":");
        sb.append(town.getId());

        sb.append(",\"town_id\":");
        sb.append(town.getId());

        sb.append(",\"nl_init\":true}");

//        System.out.println("sb.toString(): " +sb.toString());

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
        for (FarmingVillage farmingVillage : farmingVillages) {
            if (farmingVillage.getMood() < moodToLootTo) {
                return false;
            }
        }
        return true;
    }

    public static void setMoodToLootTo(int moodToLootTo) {
        Farming.moodToLootTo = moodToLootTo;
    }

    public static TimeToFarm getTimeToFarm() {
        return timeToFarm;
    }

    public static void setTimeToFarm(TimeToFarm timeToFarm) {
        Farming.timeToFarm = timeToFarm;
    }

    public int getMoodToLootTo() {
        return moodToLootTo;
    }

    public enum TimeToFarm {
        MINUTES_FIVE(300),
        MINUTES_TEN(600),
        MINUTES_TWENTY(1200),
        MINUTES_FORTY(2400),
        MINUTES_NINETY(5400),
        MINUTES_ONE_HUNDRED_EIGHTY(10800),
        MINUTES_TWO_HUNDRED_FORTY(14400),
        MINUTES_FOUR_HUNDRED_EIGHTY(28800);

        int seconds;
        TimeToFarm(int seconds) {
            this.seconds = seconds;
        }
    }
}
