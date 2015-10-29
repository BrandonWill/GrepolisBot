package Grepolis;

import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import static Grepolis.util.Reversed.reversed;

/**
 * @Author Brandon
 * Created by Brandon on 10/10/2015.
 * Time: 4:23 AM
 */
public class Barracks {
    private Town town;
    private ArrayList<BarracksUnit> barracksUnits = new ArrayList<>();
    private boolean isQueueFull = false;

    public Barracks(Town town) {
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
                    BarracksUnit barracksUnit = new BarracksUnit();
//                    System.out.println(Arrays.toString(data));
                    barracksUnit.setUnitType(BarracksUnit.UnitType.valueOf(data[0]));

                    for (String actualData : data) {
                        if (actualData.startsWith("total:")) {
                            barracksUnit.setTotalTroops(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("max_build:")) {
                            barracksUnit.setMaxBuild(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("favor:")) {
                            barracksUnit.setFavorRequired(Integer.parseInt(actualData.split(":")[1]));
                        } else if (actualData.startsWith("population:")) {
                            barracksUnit.setPopulationRequired(Integer.parseInt(actualData.split(":")[1]));
                        }
                    }
                    if (hasUnit(barracksUnit.getUnitType())) {
                        BarracksUnit unit = getUnit(barracksUnit.getUnitType());
                        unit.setTotalTroops(barracksUnit.getTotalTroops());
                        unit.setMaxBuild(barracksUnit.getMaxBuild());
                        unit.setFavorRequired(barracksUnit.getFavorRequired());
                        unit.setPopulationRequired(barracksUnit.getPopulationRequired());
                    } else {
                        barracksUnits.add(barracksUnit);
                    }

                }

            }

            //update the number of units in queue!
            for (BarracksUnit barracksUnit : this.barracksUnits)  {
                barracksUnit.setInQueue(0);
            }

            int queueCount = 0;
            //Checks the unit queue. These troops ARE NOT reflected in the total call above.
            if (html.contains("unit_id")) {
                String unitQueue = html.substring(html.indexOf("unit_id"));

                String units[] = unitQueue.split("type");
                for (String queueData : units) {
                    BarracksUnit currentBarracksUnit = null;
                    for (String data : queueData.split(",")) {
                        if (data.contains("unit_id:")) {
                            currentBarracksUnit = getUnit(BarracksUnit.UnitType.valueOf(data.split(":")[1]));
                        }
                        if (currentBarracksUnit != null && data.contains("units_left:")) {
                            currentBarracksUnit.setInQueue(currentBarracksUnit.getInQueue() + Integer.parseInt(data.split(":")[1]));
//                            System.out.println("Found a total of " +currentUnit.getInQueue() + " " +currentUnit.getUnitType().inGameName + " in queue");
                            queueCount++;
                        }
                    }
//                    System.out.println("Queue data: " +queueData);

                }
            }

            isQueueFull = queueCount == 7;

            fillTroops();


            return true;
        }
        return false;
    }

    private void fillTroops() {
        for (BarracksUnit.UnitType unitType : BarracksUnit.UnitType.values()) {
            if (getUnit(unitType) == null) {
                BarracksUnit barracksUnit = new BarracksUnit();
                barracksUnit.setUnitType(unitType);
                barracksUnit.setBuildTo(0);
                barracksUnit.setTotalTroops(0);
                barracksUnit.setPopulationRequired(0);
                barracksUnit.setInQueue(0);
                barracksUnit.setMaxBuild(0);
                barracksUnit.setFavorRequired(0);
                barracksUnits.add(barracksUnit);
            }
        }
    }

    public void buildABarrackUnit() {
        if (!isQueueFull) {
            for (BarracksUnit barracksUnit : reversed(barracksUnits)) {
                if (barracksUnit.shouldBuild()) {
                    build(barracksUnit);
                    break;
                }
            }
        }
    }

    private void build(final BarracksUnit barracksUnit) {
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
                        "xhr.open('POST', 'https://"+ town.getServer() +".grepolis.com/game/building_barracks?town_id="+ town.getId() +"&action=build&h=" + town.getCsrftoken() + "&json=%7B%22unit_id%22%3A%22" +barracksUnit.getUnitType().name() +"%22%2C%22amount%22%3A" +barracksUnit.amountToBuild() +"%2C%22town_id%22%3A" +town.getId() +"%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");
                System.out.println(getTimeOnly(LocalDateTime.now().toString()) + town.getName() + " added " + barracksUnit.amountToBuild() + " " + barracksUnit.getUnitType().name() + " to queue!");
            }
        });

    }

    public String getTimeOnly(String time) {
        return time.split("T")[1] + " ";
    }

    public boolean hasUnit(BarracksUnit.UnitType unitType) {
        for (BarracksUnit barracksUnit : barracksUnits) {
            if (barracksUnit.getUnitType().equals(unitType)) {
                return true;
            }
        }
        return false;
    }

    public BarracksUnit getUnit(BarracksUnit.UnitType unitType) {
        for (BarracksUnit barracksUnit : barracksUnits) {
            if (barracksUnit.getUnitType().equals(unitType)) {
                return barracksUnit;
            }
        }
        return null;
    }



    public ArrayList<BarracksUnit> getBarracksUnits() {
        return barracksUnits;
    }

    public void setBarracksUnits(ArrayList<BarracksUnit> barracksUnits) {
        this.barracksUnits = barracksUnits;
    }

    public boolean isQueueFull() {
        return isQueueFull;
    }

    public void setIsQueueFull(boolean isQueueFull) {
        this.isQueueFull = isQueueFull;
    }
}
