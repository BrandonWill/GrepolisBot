package Grepolis;

import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static Grepolis.util.MyLogger.log;
import static Grepolis.util.Reversed.reversed;

/**
 * @Author Brandon
 * Created by Brandon on 10/13/2015.
 * Time: 3:13 PM
 */
public class Docks {
    private Town town;
    private ArrayList<DocksUnit> docksUnits = new ArrayList<>();
    private boolean isQueueFull = false;

    public Docks(Town town) {
        this.town = town;
    }

    public boolean parseData(String html) {
        if (html.contains("UnitOrder.init")) {
            html = html.substring(html.indexOf("UnitOrder.init")+15);
            html = html.replaceAll("\"", "");
            html = html.replaceAll("\\\\", "");

            String unitData[] = html.split("id:");

            //Loads all the unit data and how many they can build
            //Don't care about population or research. If they aren't researched or you don't have enough population
            //then their max_build will reflect that.
            for (String string : unitData) {
                if (string.contains("name:")) {
                    String data[] = string.split(",");
                    DocksUnit docksUnit = new DocksUnit();
//                    log(Arrays.toString(data));
                    docksUnit.setUnitType(DocksUnit.UnitType.valueOf(data[0]));

                    for (String actualData : data) {
                        if (actualData.startsWith("total:")) {
                            docksUnit.setTotalTroops(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("max_build:")) {
                            docksUnit.setMaxBuild(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("favor:")) {
                            docksUnit.setFavorRequired(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("population:")) {
                            docksUnit.setPopulationRequired(Integer.parseInt(actualData.split(":")[1]));
                        }
                    }
                    if (hasUnit(docksUnit.getUnitType())) {
                        DocksUnit unit = getUnit(docksUnit.getUnitType());
                        unit.setTotalTroops(docksUnit.getTotalTroops());
                        unit.setMaxBuild(docksUnit.getMaxBuild());
                        unit.setFavorRequired(docksUnit.getFavorRequired());
                        unit.setPopulationRequired(docksUnit.getPopulationRequired());
                    } else {
                        docksUnits.add(docksUnit);
                    }

                }

            }

            //update the number of units in queue!
            for (DocksUnit docksUnit : this.docksUnits)  {
                docksUnit.setInQueue(0);
            }

            int queueCount = 0;
            //Checks the unit queue. These troops ARE NOT reflected in the total call above.
            if (html.contains("unit_id")) {
                String unitQueue = html.substring(html.indexOf("unit_id"));

                String units[] = unitQueue.split("type");
                for (String queueData : units) {
                    DocksUnit currentDocksUnit = null;
                    for (String data : queueData.split(",")) {
                        if (data.contains("unit_id:")) {
                            currentDocksUnit = getUnit(DocksUnit.UnitType.valueOf(data.split(":")[1]));
                        }
                        if (currentDocksUnit != null && data.contains("units_left:")) {
                            currentDocksUnit.setInQueue(currentDocksUnit.getInQueue() + Integer.parseInt(data.split(":")[1]));
//                            log("Found a total of " +currentUnit.getInQueue() + " " +currentUnit.getUnitType().inGameName + " in queue");
                            queueCount++;
                        }
                    }
//                    log("Queue data: " +queueData);

                }
            }

            isQueueFull = queueCount == 7;

            fillTroops();


            return true;
        }
        return false;
    }

    public void buildADocksUnit() {
        if (!isQueueFull) {
            for (DocksUnit dockssUnit : reversed(docksUnits)) {
                if (dockssUnit.shouldBuild()) {
                    build(dockssUnit);
                    break;
                }
            }
        }
    }

    public boolean canBuildUnit() {
        for (DocksUnit dockssUnit : reversed(docksUnits)) {
            if (dockssUnit.shouldBuild()) {
                return true;
            }
        }
        return false;
    }

    private void build(final DocksUnit docksUnit) {
        //builds the troop!
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
                        "xhr.open('POST', 'https://" + town.getServer() + ".grepolis.com/game/building_docks?town_id=" + town.getId() + "&action=build&h=" + town.getCsrftoken() + "&json=%7B%22unit_id%22%3A%22" + docksUnit.getUnitType().name() + "%22%2C%22amount%22%3A" + docksUnit.amountToBuild() + "%2C%22town_id%22%3A" + town.getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
                log(town.getName() + " added " + docksUnit.amountToBuild() + " " + docksUnit.getUnitType().inGameName + " to queue!");
            }
        });

    }

    //get docks data
    //https://xxxx.grepolis.com/game/building_docks?town_id=xxxxx&action=index&h=xxxxxx&json=%7B%22town_id%22%3Axxxxx%2C%22nl_init%22%3Atrue%7D&_=1444763737982
    //build dock unit
    //      /game/building_docks?town_id=xxxxx&action=build&h=xxxxxxxx&json=json=%7B%22unit_id%22%3A%22bireme%22%2C%22amount%22%3A1%2C%22town_id%22%3Axxxxx%2C%22nl_init%22%3Atrue%7D
    //      {"unit_id":"bireme","amount":1,"town_id":xxxxx,"nl_init":true}


    public void fillTroops() {
        for (DocksUnit.UnitType unitType : DocksUnit.UnitType.values()) {
            if (getUnit(unitType) == null) {
                DocksUnit docksUnit = new DocksUnit();
                docksUnit.setUnitType(unitType);
                docksUnit.setBuildTo(0);
                docksUnit.setTotalTroops(0);
                docksUnit.setPopulationRequired(0);
                docksUnit.setInQueue(0);
                docksUnit.setMaxBuild(0);
                docksUnit.setFavorRequired(0);
                docksUnits.add(docksUnit);
            }
        }
    }

    public boolean hasUnit(DocksUnit.UnitType unitType) {
        for (DocksUnit docksUnit : docksUnits) {
            if (docksUnit.getUnitType().equals(unitType)) {
                return true;
            }
        }
        return false;
    }

    public DocksUnit getUnit(DocksUnit.UnitType unitType) {
        for (DocksUnit docksUnit : docksUnits) {
            if (docksUnit.getUnitType().equals(unitType)) {
                return docksUnit;
            }
        }
        return null;
    }

    public ArrayList<DocksUnit> getDocksUnits() {
        return docksUnits;
    }
}
