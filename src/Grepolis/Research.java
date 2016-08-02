package Grepolis;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;

import static Grepolis.util.MyLogger.log;

/**
 * @Author Brandon
 * Created by Brandon on 8/1/2016.
 * Time: 9:01 AM
 */
public class Research {
    private Town town;
    private ArrayList<Researches> researches = new ArrayList<>();

    public Research(Town town) {
        this.town = town;
    }

    public static void parseHTML(String townData) {
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
                ArrayList<Researches> researches = new ArrayList<>();

                for (String researchData : townResearch.split(",")) {

                    Researches research = new Researches();

                    if (researchData.contains("nger")) {
                        research.setResearchType(Researches.ResearchType.slinger);
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
//                            System.out.println("Research name: " + researchData.split(":")[0]);
                            research.setResearchType(Researches.ResearchType.valueOf(researchData.split(":")[0]));
                            research.setResearched(Boolean.parseBoolean(researchData.split(":")[1]));
                            researches.add(research);
                        }
                    }
                }



                //Currently, there are 39 researchers. And there will always be one "0" here
                if (researches.size() != 39 && researches.size() != 0) {
                    log(Level.SEVERE, " Not all 39 researches were found! Please create issue on github! Only found: " +researches.size());
                }

                if (researchTown != null) {
                    researchTown.getResearch().setResearches(researches);
                }
            }
        }
    }


    public boolean canResearch(Researches researches) {
        Researches.ResearchType researchType = researches.getResearchType();
        return !researches.isResearched()
                && town.getBuilding(Building.BuildingType.academy).getLevel() >= researchType.academyLevelRequired
                && town.getLast_wood() >= researchType.wood
                && town.getLast_stone() >= researchType.stone
                && town.getLast_iron() >= researchType.iron;
    }

    public int getResearchPoints() {
        if (town.getBuilding(Building.BuildingType.academy) != null && town.getBuilding(Building.BuildingType.academy).getLevel() > 0) {
            int totalPossiblePoints = town.getBuilding(Building.BuildingType.academy).getLevel() * 4;
            for (Researches research : researches) {
                if (research.isResearched()) {
                    totalPossiblePoints -= research.getResearchType().researchPointsRequired;
                }
            }
            return totalPossiblePoints;
        } else {
            return 0;
        }
    }

    public ArrayList<Researches> getResearches() {
        return researches;
    }

    public void setResearches(ArrayList<Researches> researches) {
        this.researches = researches;
    }
}
