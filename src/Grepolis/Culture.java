package Grepolis;

import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static Grepolis.util.MyLogger.log;

/**
 * @Author Brandon
 * Created by Brandon on 10/11/2015.
 * Time: 3:23 PM
 */
public class Culture {
    private Town town;
    private ArrayList<CultureEvent> cultureEvents = new ArrayList<>();

    public Culture(Town town) {
        this.town = town;
    }

    public boolean parseHTML(String html) {
        if (html.contains("place_box")) {
            String events[] = html.substring(html.indexOf("place_box")).split("place_box");
            for (String string : events) {
//                log("Event: " +string);
                if (string.contains("place_party") && string.contains("data-enabled=")) {
                    CultureEvent cultureEvent = getCultureEvent(CultureEvent.CultureEventType.party);
                    cultureEvent.setEnabled(true);
                    cultureEvent.setCanStart(string.contains("data-enabled=\\\"1\\\""));
                    cultureEvent.setEventRunning(string.contains("There is a celebration in the city right now!"));
                    cultureEvent.setEnoughResources(!string.contains("not_enough_resources"));
                    if (!hasCultureEvent(CultureEvent.CultureEventType.party)) {
                        cultureEvents.add(cultureEvent);
                    }
                } else if (string.contains("place_games") && string.contains("data-enabled=")) {
                    CultureEvent cultureEvent = getCultureEvent(CultureEvent.CultureEventType.games);
                    cultureEvent.setEventRunning(string.contains("There is a celebration in the city right now!"));
                    //TODO grab the user's gold to check
                    cultureEvent.setEnoughResources(false);
                    if (!hasCultureEvent(CultureEvent.CultureEventType.games)) {
                        cultureEvents.add(cultureEvent);
                    }
                } else if (string.contains("place_triumph") && string.contains("data-enabled=")) {
                    CultureEvent cultureEvent = getCultureEvent(CultureEvent.CultureEventType.triumph);
                    cultureEvent.setEnabled(false);
                    cultureEvent.setCanStart(string.contains("data-enabled=\\\"1\\\""));
                    cultureEvent.setEventRunning(string.contains("A victory procession is being held right now!"));
                    cultureEvent.setEnoughResources(false);
                    //TODO grab the user's BP to check
                    if (!hasCultureEvent(CultureEvent.CultureEventType.triumph)) {
                        cultureEvents.add(cultureEvent);
                    }
                } else if (string.contains("place_theater") && string.contains("data-enabled=")) {
                    CultureEvent cultureEvent = getCultureEvent(CultureEvent.CultureEventType.theater);
                    //TODO add theater here sometime
                    cultureEvent.setEventRunning(false);
                    cultureEvent.setEnoughResources(false);
                    if (!hasCultureEvent(CultureEvent.CultureEventType.theater)) {
                        cultureEvents.add(cultureEvent);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void startACultureEvent() {
        for (CultureEvent cultureEvent : cultureEvents) {
            if (cultureEvent.canStart()) {
                startCultureEvent(cultureEvent);
                break;
            }
        }
    }

    private void startCultureEvent(final CultureEvent cultureEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GrepolisBot.webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                        "var cultureData;\n" +
                        "xhr.onreadystatechange = function() {\n" +
                        "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                        "        cultureData = xhr.responseText\n" +
                        "    }\n" +
                        "}\n" +
                        "xhr.open('POST', 'https://" + town.getServer() + ".grepolis.com/game/building_place?town_id=" + town.getId() +"&action=start_celebration&h=" + town.getCsrftoken() + "&json=%7B%22celebration_type%22%3A%22" + cultureEvent.getCultureEventType().name() +"%22%2C%22town_id%22%3A" + town.getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send({\"celebration_type\":\"" + cultureEvent.getCultureEventType().name() + "\",\"town_id\":62795,\"nl_init\":true});");
                        log(town.getName() + " started the culture event " + cultureEvent.getCultureEventType().inGameName + "!");
                //https://####.grepolis.com/game/building_place?town_id=#####&action=start_celebration&h=###########
                //{"celebration_type":"games","town_id":xxxxx,"nl_init":true}
            }
        });
    }

    private String getTimeOnly(String time) {
        return time.split("T")[1] + " ";
    }

    public ArrayList<CultureEvent> getCultureEvents() {
        return cultureEvents;
    }

    private boolean hasCultureEvent(CultureEvent.CultureEventType cultureEventType) {
        for (CultureEvent cultureEvent : cultureEvents) {
            if (cultureEvent.getCultureEventType().equals(cultureEventType)) {
                return true;
            }
        }
        return false;
    }

    private CultureEvent getCultureEvent(CultureEvent.CultureEventType cultureEventType) {
        for (CultureEvent cultureEvent : cultureEvents) {
            if (cultureEvent.getCultureEventType().equals(cultureEventType)) {
                return cultureEvent;
            }
        }
        return new CultureEvent(cultureEventType);
    }

    public void setCultureEvents(ArrayList<CultureEvent> cultureEvents) {
        this.cultureEvents = cultureEvents;
    }

}
