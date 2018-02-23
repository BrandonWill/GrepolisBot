package Grepolis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

import static Grepolis.util.MyLogger.log;

/**
 * @Author Brandon
 * Created by Brandon on 8/1/2016.
 * Time: 9:01 AM
 */
public class Academy {
    private Town town;
    private ArrayList<AcademyResearch> researches = new ArrayList<>();
    private ArrayList<ResearchOrderQueue> researchOrderQueue = new ArrayList<>();
    private static int maxResearchQueueSize = 2;

    public Academy(Town town) {
        this.town = town;
    }

    public void parseHTML(String townData) {
        updateResearchOrderQueue();

        townData = townData.replaceAll("AcademyData:200", "");

        JsonElement jElement = new JsonParser().parse(townData);
        JsonObject jObject = jElement.getAsJsonObject();
        JsonObject json = jObject.getAsJsonObject("json");
//        System.out.println("Json: " +json.toString());
        JsonObject collections = json.getAsJsonObject("collections");
        JsonObject townResearches = collections.getAsJsonObject("TownResearches");
        JsonArray townResearchesData = townResearches.getAsJsonArray("data");
        for (JsonElement jsonElement : townResearchesData) {
            JsonObject dObject = jsonElement.getAsJsonObject().get("d").getAsJsonObject();
            if (dObject.has("slinger")) {
//                System.out.println(dObject.toString());
                Town researchTown = GrepolisBot.getTownByID(dObject.get("id").getAsInt());
                if (researchTown != null) {
                    for (Map.Entry<String, JsonElement> entry : dObject.entrySet()) {
//                        System.out.println(entry.getKey() + "/" + entry.getValue());
                        if (!entry.getKey().equals("id")) {
                            AcademyResearch research = new AcademyResearch();
                            research.setResearchType(AcademyResearch.ResearchType.valueOf(entry.getKey()));
                            research.setResearched(entry.getValue().getAsBoolean());

                            if (researchTown.getAcademy().getResearch(research.getResearchType()) == null) {
                                researchTown.getAcademy().getResearches().add(research);
                            } else {
                                researchTown.getAcademy().getResearch(research.getResearchType()).setResearched(research.isResearched());
                            }
                        }
                    }

                }
            }
        }

        if (collections.has("ResearchOrders")) {
//            System.out.println("Collections: " +collections.toString());
            //If there is nothing being researched, it returns a JsonArray, not Object so try catch block is used.
            try {
                JsonObject researchOrders = collections.getAsJsonObject("ResearchOrders");
                JsonArray researchOrdersData = researchOrders.getAsJsonArray("data");
                for (JsonElement jsonElement : researchOrdersData) {
                    JsonObject dObject = jsonElement.getAsJsonObject().get("d").getAsJsonObject();
                    if (dObject.has("town_id")) {
//                System.out.println(dObject.toString());
                        Town researchTown = GrepolisBot.getTownByID(dObject.get("town_id").getAsInt());
                        ResearchOrderQueue researchOrder = new ResearchOrderQueue();
                        researchOrder.setTownID(dObject.get("town_id").getAsInt());
                        if (researchTown != null) {
                            for (Map.Entry<String, JsonElement> entry : dObject.entrySet()) {
//                        System.out.println(entry.getKey() + "/" + entry.getValue());
                                if (entry.getKey().equals("id")) {
                                    researchOrder.setId(entry.getValue().getAsInt());
                                }
                                if (entry.getKey().equals("research_type")) {
                                    researchOrder.setResearchType(AcademyResearch.ResearchType.valueOf(entry.getValue().getAsString()));
                                }
                                if (entry.getKey().equals("to_be_completed_at")) {
                                    researchOrder.setTo_be_completed_at(entry.getValue().getAsLong());
                                }
                                if (entry.getKey().equals("created_at")) {
                                    researchOrder.setCreated_at(entry.getValue().getAsLong());
                                }
                                if (entry.getKey().equals("updated_at")) {
                                    researchOrder.setUpdated_at(entry.getValue().getAsLong());
                                }
                            }

                            boolean researchAlreadyInQueue = false;
                            for (ResearchOrderQueue researchOrderQueue : researchTown.getAcademy().getResearchOrderQueue()) {
                                if (researchOrderQueue.getResearchType().equals(researchOrder.getResearchType())) {
                                    researchAlreadyInQueue = true;
                                }
                            }

                            if (!researchAlreadyInQueue) {
                                researchTown.getAcademy().getResearchOrderQueue().add(researchOrder);
                            }
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

        for (AcademyResearch academyResearch1 : researches) {
            if (academyResearch1.getResearchType().equals(AcademyResearch.ResearchType.booty)) {
                town.getFarming().setBooty(academyResearch1.isResearched());
            }
        }

//        System.out.println("Number of Research Orders in " +town.getName() + ": " +getNumberOfResearchOrders());

        //Currently, there are 39 researchers. And there will always be one "0" here
        if (researches.size() != 39 && researches.size() != 0) {
            System.out.println("Researches: " +researches.toString());
            log(Level.SEVERE, " Not all 39 research were found! Please create issue on github! Only found: " +researches.size());
        }
    }

    public void fillResearches() {
        for (AcademyResearch.ResearchType researchType : AcademyResearch.ResearchType.values()) {
            if (getResearch(researchType) == null) {
                AcademyResearch academyResearch = new AcademyResearch();
                academyResearch.setResearched(false);
                academyResearch.setResearchType(researchType);
                researches.add(academyResearch);
            }
        }
    }

    public void research(AcademyResearch research) {
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
                        "        alert(\"AcademyResearchData:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + town.getServer() + ".grepolis.com/game/frontend_bridge" + stringForResearching(research) + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
                log(town.getName() + " added " + research.getResearchType().researchName + " to the Academy queue!");
            }
        });
    }

    private String stringForResearching(AcademyResearch research) {
        //{"window_type":"academy","tab_type":"research","known_data":{"models":["Player","PlayerLedger","PremiumFeatures"],
        // "collections":["TownBuildings","TownResearches","ResearchOrders","BuildingOrders","Towns","PlayerHeroes"],
        // "templates":[]},"town_id":xxxxx,"nl_init":true}
        StringBuilder sb = new StringBuilder();
        String town_id = "?town_id=" + town.getId();
        String action = "&action=execute";
        String h = "&h=" + town.getCsrftoken();
        sb.append(town_id);
        sb.append(action);
        sb.append(h);

        //JSON starts here!
        sb.append("&json=' +encodeURIComponent(JSON.stringify(");
        sb.append("{\"model_url\":\"ResearchOrder\",\"action_name\":\"research\",\"arguments\":{\"id\":\"");
        sb.append(research.getResearchType().name()).append("\"},\"town_id\":");
        sb.append(town.getId()).append(",\"nl_init\":true}");

        sb.append("))");
        //System.out.println("String: " +sb.toString());
        return sb.toString();
    }

    public boolean isResearched(AcademyResearch.ResearchType research) {
        for (AcademyResearch academyResearch : researches) {
            if (academyResearch.getResearchType().equals(research)) {
                return academyResearch.isResearched();
            }
        }
        return false;
    }

    public void researchAnItem() {
        for (AcademyResearch research : researches) {
            //Establish we should in fact try to research this item
            if (canResearch(research) && !researchQueueFull()) {
                research(research);
                break;
            }
        }
    }

    public boolean researchQueueFull() {
        return getNumberOfResearchOrders() >= maxResearchQueueSize;
    }

    private boolean beingResearched(AcademyResearch.ResearchType researchType) {
        for (ResearchOrderQueue researchOrderQueue : researchOrderQueue) {
            if (researchOrderQueue.getResearchType().equals(researchType)) {
                return true;
            }
        }
        return false;
    }

    private void updateResearchOrderQueue() {
        ArrayList<ResearchOrderQueue> tempResearchOrderQueue = new ArrayList<>();
        for (ResearchOrderQueue researchOrderQueue : researchOrderQueue) {
            if (GrepolisBot.getServerUnixTime() >= researchOrderQueue.getTo_be_completed_at()) {
                tempResearchOrderQueue.add(researchOrderQueue);
            }
        }

        for (ResearchOrderQueue researchOrderQueue1 : tempResearchOrderQueue) {
            if (researchOrderQueue.contains(researchOrderQueue1)) {
                researchOrderQueue.remove(researchOrderQueue1);
            }
        }
    }


    public boolean canResearch(AcademyResearch research) {
        AcademyResearch.ResearchType researchType = research.getResearchType();
        return !research.isResearched() && research.shouldResearch() && !beingResearched(research.getResearchType())
                && town.getBuilding(Building.BuildingType.academy).getCurrentLevel() >= researchType.academyLevelRequired
                && town.getFarming().getWood() >= researchType.wood
                && town.getFarming().getStone() >= researchType.stone
                && town.getFarming().getIron() >= researchType.iron
                && getResearchPoints() >= researchType.researchPointsRequired;
    }

    public int getResearchPoints() {
        if (town.getBuilding(Building.BuildingType.academy) != null && town.getBuilding(Building.BuildingType.academy).getLevel() > 0) {
            int totalPossiblePoints = town.getBuilding(Building.BuildingType.academy).getCurrentLevel() * 4;
            for (AcademyResearch research : researches) {
                if (research.isResearched()) {
                    totalPossiblePoints -= research.getResearchType().researchPointsRequired;
                }
            }
            return totalPossiblePoints;
        } else {
            return 0;
        }
    }

    public AcademyResearch getResearch(AcademyResearch.ResearchType researchType) {
        for (AcademyResearch academyResearch : researches) {
            if (academyResearch.getResearchType().equals(researchType)) {
                return academyResearch;
            }
        }
        return null;
    }

    public ArrayList<AcademyResearch> getResearches() {
        return researches;
    }

    public void setResearches(ArrayList<AcademyResearch> researches) {
        this.researches = researches;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public ArrayList<ResearchOrderQueue> getResearchOrderQueue() {
        return researchOrderQueue;
    }

    public void setResearchOrderQueue(ArrayList<ResearchOrderQueue> researchOrderQueue) {
        this.researchOrderQueue = researchOrderQueue;
    }

    public int getNumberOfResearchOrders() {
        return researchOrderQueue.size();
    }

    public static int getMaxResearchQueueSize() {
        return maxResearchQueueSize;
    }

    public static void setMaxResearchQueueSize(int maxResearchQueueSize) {
        Academy.maxResearchQueueSize = maxResearchQueueSize;
    }

    class ResearchOrderQueue {
        private int id;
        private int townID;
        private AcademyResearch.ResearchType researchType;
        private long to_be_completed_at;
        private long created_at;
        private long updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTownID() {
            return townID;
        }

        public void setTownID(int townID) {
            this.townID = townID;
        }

        public AcademyResearch.ResearchType getResearchType() {
            return researchType;
        }

        public void setResearchType(AcademyResearch.ResearchType researchType) {
            this.researchType = researchType;
        }

        public long getTo_be_completed_at() {
            return to_be_completed_at;
        }

        public void setTo_be_completed_at(long to_be_completed_at) {
            this.to_be_completed_at = to_be_completed_at;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }
    }
}
