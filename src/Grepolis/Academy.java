package Grepolis;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.logging.Level;

import static Grepolis.util.MyLogger.log;

/**
 * @Author Brandon
 * Created by Brandon on 8/1/2016.
 * Time: 9:01 AM
 */
public class Academy {
    private Town town;
    private ArrayList<AcademyResearch> research = new ArrayList<>();

    public Academy(Town town) {
        this.town = town;
    }

    public void parseHTML(String townData) {

        String townResearches = townData.substring(0, townData.indexOf("}}]}"));
        townResearches = townResearches.replaceAll("\"", "");

//        System.out.println("townResearches: " +townResearches);

        if (townResearches.contains("slinger")) {
            townResearches = townResearches.substring(townResearches.indexOf("sli"));
            townResearches = townResearches.replaceAll(" ", "");
//            System.out.println("townResearches: " +townResearches);
            String[] allTownsResearch = townResearches.split("sli");
            for (String townResearch : allTownsResearch) {

                Town researchTown = null;
                ArrayList<AcademyResearch> researches = new ArrayList<>();

                for (String researchData : townResearch.split(",")) {

                    AcademyResearch research = new AcademyResearch();
                    if (researchData.contains("true") || researchData.contains("false")) {
                        if (researchData.contains("nger")) {
                            research.setResearchType(AcademyResearch.ResearchType.slinger);
                            research.setResearched(Boolean.parseBoolean(researchData.split(":")[1]));
                            researches.add(research);
                        } else if (researchData.contains("id:")) {
                            int id = Integer.parseInt(researchData.split(":")[1].replaceAll("}", ""));
                            for (Town town : GrepolisBot.getTowns()) {
                                if (town.getId() == id) {
                                    researchTown = town;
                                }
                            }
                        } else {
                            if (researchData.length() > 2) {
//                            System.out.println("Academy researchName: " + researchData.split(":")[0]);
                                try {
                                    research.setResearchType(AcademyResearch.ResearchType.valueOf(researchData.split(":")[0]));
                                    research.setResearched(Boolean.parseBoolean(researchData.split(":")[1]));
                                    researches.add(research);
                                } catch (Exception ignored) {
                                    log("Error in finding research within the following data: " + researchData);
                                    if (researchTown != null) {
                                        log("Please create an issue on github, including: " + " Academy null: " + (researchTown.getBuilding(Building.BuildingType.academy) != null)
                                                + " Academy level: " + researchTown.getBuilding(Building.BuildingType.academy).getLevel() + " research data string: " + townResearch);
                                    }
                                }
                            }
                        }
                    }
                }

                for (AcademyResearch academyResearch1 : researches) {
                    if (academyResearch1.getResearchType().equals(AcademyResearch.ResearchType.booty)) {
                        town.getFarming().setBooty(academyResearch1.isResearched());
                    }
                }

                //Currently, there are 39 researchers. And there will always be one "0" here
                if (researches.size() != 39 && researches.size() != 0) {
                    log(Level.SEVERE, " Not all 39 research were found! Please create issue on github! Only found: " +researches.size());
                }

                if (researchTown != null) {
                    researchTown.getAcademy().setResearch(researches);
                }
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
                        "        alert(\"AcademyData:\" +xhr.status +readBody(xhr));\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/frontend_bridge" + stringForResearching(research) + ", true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
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


    public boolean canResearch(AcademyResearch academyResearch) {
        AcademyResearch.ResearchType researchType = academyResearch.getResearchType();
        return !academyResearch.isResearched()
                && town.getBuilding(Building.BuildingType.academy).getLevel() >= researchType.academyLevelRequired
                && town.getLast_wood() >= researchType.wood
                && town.getLast_stone() >= researchType.stone
                && town.getLast_iron() >= researchType.iron;
    }

    public int getResearchPoints() {
        if (town.getBuilding(Building.BuildingType.academy) != null && town.getBuilding(Building.BuildingType.academy).getLevel() > 0) {
            int totalPossiblePoints = town.getBuilding(Building.BuildingType.academy).getLevel() * 4;
            for (AcademyResearch research : research) {
                if (research.isResearched()) {
                    totalPossiblePoints -= research.getResearchType().researchPointsRequired;
                }
            }
            return totalPossiblePoints;
        } else {
            return 0;
        }
    }

    public ArrayList<AcademyResearch> getResearch() {
        return research;
    }

    public void setResearch(ArrayList<AcademyResearch> research) {
        this.research = research;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
