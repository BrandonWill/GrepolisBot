package Grepolis;

import Grepolis.IO.Loader;
import com.sun.javafx.application.PlatformImpl;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLInputElement;

import javax.swing.*;
import javax.swing.Timer;

import static Grepolis.IO.Saver.*;

public class GrepolisBot extends JPanel {

    private JFXPanel jfxPanel;
    private boolean loggedIn = false;
    public static JFrame frame;
    public static JPanel panel;
    public static WebView webView;

    private static String csrfToken = null;

    private static ArrayList<Town> towns = new ArrayList<>();

    private static String server;
    private int defaultTown;
    private static String gameURL;

    private static TextField fxUsername;
    private static TextField fxPassword;
    private static TextField serverField;
    private static TextField timeToRefresh;

    private static boolean farmersEnabled = false;

    private static volatile boolean captchaDetected = false;

    public GrepolisBot(){
        initComponents();
    }

    public static void main(String ...args){
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame();

                frame.getContentPane().add(new GrepolisBot());

                frame.setSize(new Dimension(1500, 800));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    private void initComponents(){
        Loader.load();
        jfxPanel = new JFXPanel();
        createScene();
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                save(towns);
            }
        });
    }

    /**
     * createScene
     *
     * Note: Key is that Scene needs to be created and run on "FX user thread"
     *       NOT on the AWT-EventQueue Thread
     *
     */
    private void createScene() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                final BooleanProperty loginAttempted = new SimpleBooleanProperty(false);

                webView = new WebView();


                webView.setPrefWidth(1000);
                final WebEngine engine = webView.getEngine();
                webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
                System.out.println("Browser agent changed to latest chrome version. It's now: " + webView.getEngine().getUserAgent());
                webView.getEngine().getHistory().setMaxSize(3);
                final BooleanProperty ran = new SimpleBooleanProperty(false);
                engine.documentProperty().addListener(new ChangeListener<Document>() {
                    @Override
                    public void changed(ObservableValue<? extends Document> ov, Document oldDoc, Document doc) {
                        if (doc != null && !loginAttempted.get()) {
                            if (doc.getElementsByTagName("form").getLength() > 0) {
                                HTMLFormElement form = (HTMLFormElement) doc.getElementsByTagName("form").item(0);
                                if ("/start?action=login_to_game_world".equals(form.getAttribute("action"))) {
                                    HTMLInputElement username = null;
                                    HTMLInputElement password = null;
                                    HTMLCollection nodes = form.getElements();
                                    for (int i = 0; i < nodes.getLength(); i++) {
                                        HTMLInputElement input = (HTMLInputElement) nodes.item(i);
                                        switch (input.getName()) {
                                            case "name":
                                                username = input;
                                                break;
                                            case "password":
                                                password = input;
                                                break;
                                        }
                                    }

                                    if (username != null && password != null && !ran.getValue()) {
                                        ran.set(true);
                                        loginAttempted.set(true);
                                        username.setValue(fxUsername.getText());
                                        password.setValue(fxPassword.getText());

                                        System.out.println("Logging in");
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                engine.executeScript("document.getElementsByClassName('button')[0].click()");
                                            }
                                        });


                                        System.out.println("Selecting world");
                                        int delay = 2000;

                                        ActionListener taskPerformer = new ActionListener() {
                                            public void actionPerformed(ActionEvent evt) {
                                                if (!loggedIn) {
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            engine.load("https://" + server.substring(0, 2) + ".grepolis.com/start/index?world_id=" + server + "&action=login_on_new_world");
                                                            loggedIn = true;
                                                        }
                                                    });
                                                }
                                            }
                                        };
                                        new Timer(delay, taskPerformer).start();
                                    }
                                }
                            }
                        }
                    }
                });
                engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                    @Override
                    public void changed(ObservableValue<? extends Throwable> ov, Throwable oldException, Throwable exception) {
                        System.out.println("Load Exception: " + exception);
                    }
                });

                do {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (fxUsername == null);

                GridPane inputGrid = new GridPane();
                inputGrid.setHgap(10);
                inputGrid.setVgap(10);
                inputGrid.addRow(0, new Label("Username: "), fxUsername);
                inputGrid.addRow(0, new Label("Password: " ), fxPassword);
                inputGrid.addRow(0, new Label("World: "), serverField);
                inputGrid.addRow(0, new Label("Update time: "), timeToRefresh);

                final Button fxLoginButton = new Button("Login to Grepolis");
                fxLoginButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        if (notEmpty(fxPassword.getText()) && notEmpty(fxPassword.getText()) && notEmpty(serverField.getText())) {
                            loginAttempted.set(false);
                            server = serverField.getText();
                            engine.load("https://" + server.substring(0, 2) + ".grepolis.com");
                            fxLoginButton.setDisable(true);
                        }
                    }
                });
                fxLoginButton.setDefaultButton(true);

                final Button startBot = new Button("Start bot");
                startBot.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        addAlerts();
                        defaultTown = getDefaultTownID();
                        gameURL = webView.getEngine().getLocation();
                        System.out.println(getTimeOnly(LocalDateTime.now().toString()) + " Bot enabled");
                        new Thread(new Startup()).start();
                        new Thread(new TitleUpdater()).start();
                        startBot.setDisable(true);
                    }
                });

                Button botDebugger = new Button("Bot debugger");
                botDebugger.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                webView.getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
                            }
                        });

                    }
                });

                inputGrid.addRow(0, botDebugger);

                Button queueButton = new Button("Queue");
                queueButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new QueueGUI(towns).setVisible(true);
                            }
                        });
                    }
                });

                Button farmButton = new Button("Farming");
                farmButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                if (towns.size() > 0) {
                                    new FarmersGUI(towns).setVisible(true);
                                }
                            }
                        });
                    }
                });


                ProgressIndicator fxLoadProgress = new ProgressIndicator(0);
                fxLoadProgress.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());
                fxLoadProgress.visibleProperty().bind(webView.getEngine().getLoadWorker().runningProperty());

                HBox loginPane = new HBox(10);
                loginPane.getChildren().setAll(
                        fxLoginButton,
                        fxLoadProgress,
                        startBot,
                        queueButton,
                        farmButton
                );

                final VBox layout = new VBox(10);
                layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
                layout.getChildren().addAll(
                        new Label("Enter your Grepolis Account credentials"),
                        inputGrid,
                        loginPane,
                        webView
                );
                VBox.setVgrow(webView, Priority.ALWAYS);
                Scene scene = new Scene(layout);

                fxUsername.requestFocus();
                jfxPanel.setScene(scene);



            }
        });
    }

    private boolean notEmpty(String s) {
        return s != null && !"".equals(s);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private String capitalize(String word) {
        if (word.contains("_")) {
            String[] holder = word.split("_");
            word = "";
            for (String string: holder) {
                word += string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase() + " ";
            }
            if (word.endsWith(" ")) {
                word = word.substring(0, word.length()-1);
            }
        } else {
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        return word;
    }

    public String getTimeOnly(String time) {
        return time.split("T")[1] + " ";
    }

    private boolean builtBarracksTroops = false;

    public class BuildBarracksTroops implements Runnable {
        Town town;

        public BuildBarracksTroops(Town town) {
            this.town = town;
        }

        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!builtTheBuildings);

                if (town.getBuilding(Building.BuildingType.barracks).getCurrentLevel() > 0) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().executeScript("function readBody(xhr) {\n" +
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
                                    "var barrackData;\n" +
                                    "xhr.onreadystatechange = function() {\n" +
                                    "    if (xhr.readyState == 4) {\n" +
                                    "        barrackData = readBody(xhr);\n" +
                                    "        alert(\"BarracksData:\" +xhr.status +readBody(xhr));\n" +
                                    "    }\n" +
                                    "}\n" +
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/building_barracks?town_id=" + town.getId() + "&action=index&h=" + csrfToken + "&json=%7B%22town_id%22%3A" + town.getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                                    "xhr.send(null);");
                        }
                    });
                } else {
                    builtBarracksTroops = true;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean builtDocksTroops = false;
    public class BuildDocksTroops implements Runnable {
        Town town;

        public BuildDocksTroops(Town town) {
            this.town = town;
        }

        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!builtBarracksTroops);

                if (town.getBuilding(Building.BuildingType.docks).getCurrentLevel() > 0) {

                    final boolean[] hasDocksData = {false};
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().executeScript("function readBody(xhr) {\n" +
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
                                    "var docksData;\n" +
                                    "xhr.onreadystatechange = function() {\n" +
                                    "    if (xhr.readyState == 4) {\n" +
                                    "        docksData = readBody(xhr);\n" +
                                    "        alert(\"DocksData:\" +xhr.status +readBody(xhr));\n" +
                                    "    }\n" +
                                    "}\n" +
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/building_docks?town_id=" + town.getId() + "&action=index&h=" + csrfToken + "&json=%7B%22town_id%22%3A" + town.getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                                    "xhr.send(null);");
                        }
                    });
                } else {
                    builtDocksTroops = true;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean builtTheBuildings = false;
    public class BuildTheBuildings implements Runnable {
        Town town;

        public BuildTheBuildings(Town town) {
            this.town = town;
        }

        public void run() {
            try {
                //TODO add a check to see if farmers are enabled and to check if building queue is enabled
                //TODO check if there's any buildings to even build. Why waste time loading/checking
//                town.addListenerToBuildings(webView.getEngine(), server, csrfToken);
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!obtainedCultureData);

                //Loads data exactly from the senate
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                                "var buildingData;\n" +
                                "xhr.onreadystatechange = function() {\n" +
                                "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                                "        buildingData = xhr.responseText\n" +
                                "        alert(\"BuildingData:\" +xhr.status +readBody(xhr));\n" +
                                "    }\n" +
                                "}\n" +
                                "xhr.open('GET', 'https://" + server +".grepolis.com/game/building_main?town_id=" + town.getId() + "&action=index&h=" + csrfToken + "', true);\n" +
                                "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");" +
                                "xhr.send(null);\n");
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean obtainedCultureData;
    public class StartCultureEvents implements Runnable {
        Town town;

        public StartCultureEvents(Town town) {
            this.town = town;
        }

        public void run() {
            try {
                //TODO add a check to see if farmers are enabled and to check if building queue is enabled
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!farmedTheTown);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        webView.getEngine().executeScript("var xhr = new XMLHttpRequest();\n" +
                                "var cultureData;\n" +
                                "xhr.onreadystatechange = function() {\n" +
                                "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                                "        cultureData = xhr.responseText\n" +
                                "        alert(\"CultureData:\" +xhr.status +readBody(xhr));\n" +
                                "    }\n" +
                                "}\n" +
                                "xhr.open('GET', 'https://" + server +".grepolis.com/game/building_place?town_id=" + town.getId() + "&action=culture&h=" + csrfToken + "', true);\n" +
                                "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");" +
                                "xhr.send(null);\n");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean farmedTheTown = false;
    long currentTime;
    public class FarmTheTown implements Runnable {
        Town town;

        public FarmTheTown(Town town) {
            this.town = town;
        }

        public void run() {
            try {


                Thread.sleep(3000);

                currentTime = System.currentTimeMillis();

                if (currentTime > town.getTimeToFarm()) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().executeScript("function readBody(xhr) {\n" +
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
                                    "var farmData;\n" +
                                    "xhr.onreadystatechange = function() {\n" +
                                    "    if (xhr.readyState == 4) {\n" +
                                    "        alert(\"FarmData:\" +xhr.status +readBody(xhr));\n" +
                                    "    }\n" +
                                    "}\n" +
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + town.getId() +"&action=index&h=" + csrfToken + "&json=%7B%22town_id%22%3A" + town.getId() + "%2C%22nl_init%22%3Atrue%7D', true);\n" +
                                    "xhr.send(null);");

                        }
                    });
                } else {
                    System.out.println(getTimeOnly(LocalDateTime.now().toString()) + town.getName() + " Farmers aren't ready!");
                    farmedTheTown = true;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class Startup implements Runnable {

        public Startup() {}


        @Override
        public void run() {
            final int townID = defaultTown;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
//                    webView.getEngine().load("https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + townID + "&action=index&h=" + csrfToken);
                    webView.getEngine().executeScript("function readBody(xhr) {\n" +
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
                            "var htmlData;\n" +
                            "xhr.onreadystatechange = function() {\n" +
                            "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                            "        htmlData = readBody(xhr);\n" +
                            "        alert(\"TownLoaderData:\" +xhr.status +readBody(xhr));\n" +
                            "    }\n" +
                            "}\n" +
                            "\n" +
                            "xhr.open('GET', 'https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + townID + "&action=index&h=" + csrfToken + "', true);\n" +
                            "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                            "xhr.send(null)");

                }
            });
        }
        //
    }

    private boolean townAlreadyAdded(Town town) {
        for (Town town1 : towns) {
            if (town1.getId() == town.getId()) {
                return true;
            }
        }
        return false;
    }

    private boolean changeTownName(Town town, String name, int townID) {
        for (Town town1 : towns) {
            town1.setServer(server);
            town1.setCsrftoken(csrfToken);
            if (town1.getId() == townID && !town1.getName().equals(name)) {
                System.out.println("Updating town name from " +town1.getName() + " to " +name);
                town1.setName(name);
                return true;
            }
        }
        return false;
    }

    public int getDefaultTownID() {
        final String[] html = new String[1];
        final int[] townID = {0};
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                html[0] = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
                for (String string : html[0].split(",")) {
                    if (string.contains("csrfToken") && csrfToken == null) {
                        csrfToken = string.split(":")[1].replaceAll("\"", "");
                        System.out.println("csrftoken: " +csrfToken);
                    }
                    if (string.contains("\"townId\":")) {
                        townID[0] = Integer.parseInt(string.split(":")[1].replaceAll("\"", ""));
                        System.out.println("Default town: " +townID[0]);
                    }
                }
            }
        });
        return townID[0];
    }

    private long updateTime = 0;
    private volatile boolean canContinue;
    private Town currentTown;
    public class ActualBot implements Runnable {
        public void run() {
            try {
                farmedTheTown = true;
                builtTheBuildings = true;
                obtainedCultureData = true;
                while (true) {

                    do {
                        Thread.sleep(250);
                    } while (System.currentTimeMillis() < updateTime);

                    Thread.sleep(randInt(1000, 1500));

                    Thread[] farmer = new Thread[1];
                    Thread[] culture = new Thread[1];
                    Thread[] builder = new Thread[1];
                    Thread[] barrackQueue = new Thread[1];
                    Thread[] docksQueue = new Thread[1];
                    for (int i = 0; i < towns.size(); i++) {
                        checkForCaptcha();

                        while (captchaDetected) {
                            Thread.sleep(300);
                        }

                        currentTown = towns.get(i);

                        obtainedCultureData = false;
                        builtTheBuildings = false;
                        farmedTheTown = false;
                        builtBarracksTroops = false;
                        builtDocksTroops = false;
                        if (farmer[0] != null && farmer[0].isAlive() && !farmer[0].isInterrupted()) {
                            farmer[0].interrupt();
                        }
                        if (builder[0] != null && builder[0].isAlive() && !builder[0].isInterrupted()) {
                            builder[0].interrupt();
                        }
                        if (culture[0] != null && culture[0].isAlive() && !culture[0].isInterrupted()) {
                            culture[0].interrupt();
                        }
                        if (barrackQueue[0] != null && barrackQueue[0].isAlive() && !barrackQueue[0].isInterrupted()) {
                            barrackQueue[0].interrupt();
                        }
                        if (docksQueue[0] != null && docksQueue[0].isAlive() && !docksQueue[0].isInterrupted()) {
                            docksQueue[0].interrupt();
                        }

                        if (farmersEnabled) {
                            farmer[0] = (new Thread(new FarmTheTown(towns.get(i))));
                            farmer[0].start();
                        } else {
                            farmedTheTown = true;
                        }
                        culture[0] = (new Thread(new StartCultureEvents(towns.get(i))));
                        culture[0].start();
                        builder[0] = (new Thread(new BuildTheBuildings(towns.get(i))));
                        builder[0].start();
                        barrackQueue[0] = (new Thread(new BuildBarracksTroops(towns.get(i))));
                        barrackQueue[0].start();
                        docksQueue[0] = (new Thread(new BuildDocksTroops(towns.get(i))));
                        docksQueue[0].start();
                        while (!farmedTheTown || !obtainedCultureData || !builtTheBuildings || !builtBarracksTroops || !builtDocksTroops) {
                            Thread.sleep(200);
                        }

                        if (i+1 < towns.size()) {
                            new Thread(new TownSwitcher(towns.get(i+1))).start();
//                            townSwitcher[0];
                        } else {
                            //ensures we always start at the town we're supposed to be in!
                            System.out.println();
                            new Thread(new TownSwitcher(towns.get(0))).start();

                        }

                        do {
                            Thread.sleep(250);
                        } while (!canContinue);

                    }
                    updateTime = getUpdateTime();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getUpdateTime() {
        String time = timeToRefresh.getText();
        String intervals[] = time.split(":");
        int hours = Integer.parseInt(intervals[0]);
        int minutes = Integer.parseInt(intervals[1]);
        int seconds = Integer.parseInt(intervals[2]) + randInt(-120, 120);
        return System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
    }

    public class TownSwitcher implements Runnable {
        private Town town;

        public TownSwitcher(Town town) {
            this.town = town;
        }

        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webView.getEngine().executeScript("function readBody(xhr) {\n" +
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
                            "    if (xhr.readyState == 4) {\n" +
                            "        console.log(readBody(xhr));\n" +
                            "    }\n" +
                            "}\n" +
                            "\n" +
                            "xhr.open('GET', 'https://" + server + ".grepolis.com/game/index?action=switch_town&town_id=" + town.getId() + "&h=" + csrfToken + "&json=' +encodeURIComponent(JSON.stringify(" + townSwitcherJSON() +")), true);\n" +
                            "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                            "xhr.send(null);");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    canContinue = true;
                }

            });
        }

        private String townSwitcherJSON() {
            return "{\"town_id\":" + town.getId() +",\"nl_init\":true}";
        }
    }

    public static String getGameURL() {
        return gameURL;
    }

    private class PageLoaded extends Thread {
        volatile boolean loading = true;

        public void run() {
            while (loading) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loading = !(webView.getEngine().getLoadWorker().progressProperty().getValue() == 1.0);
                    }
                });
                try {
                    Thread.sleep(randInt(300, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isLoading() {
            return loading;
        }
    }

    public class TitleUpdater implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (updateTime > 0) {
                    long timeToUpdate = updateTime - System.currentTimeMillis();
                    if (timeToUpdate > 0) {
                        long second = (timeToUpdate / 1000) % 60;
                        long minute = (timeToUpdate / (1000 * 60)) % 60;
                        long hour = (timeToUpdate / (1000 * 60 * 60)) % 24;
                        final String remainingTime = String.format("Update time: %02d:%02d:%02d", hour, minute, second);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                frame.setTitle(remainingTime);
                            }
                        });

                    }
                }

            }
        }
    }

    private void checkForCaptcha() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String html = (String) webView.getEngine().executeScript("document.documentElement.innerHTML");
                if (html.contains("id=\"captcha_curtain\"")) {
                    System.out.println("Captcha detected! Pausing bot shortly");
                    captchaDetected = true;
                }
            }
        });
    }

    public static void setTowns(ArrayList<Town> towns) {
        GrepolisBot.towns = towns;
    }

    public static ArrayList<Town> getTowns() {
        return towns;
    }


    public static TextField getFxUsername() {
        return fxUsername;
    }

    public static TextField getFxPassword() {
        return fxPassword;
    }

    public static TextField getServerField() {
        return serverField;
    }

    public static void setFxUsername(TextField fxUsername) {
        GrepolisBot.fxUsername = fxUsername;
    }

    public static void setFxPassword(TextField fxPassword) {
        GrepolisBot.fxPassword = fxPassword;
    }

    public static void setServerField(TextField serverField) {
        GrepolisBot.serverField = serverField;
    }

    public static void setServerFieldText(String serverField) {
        GrepolisBot.serverField.setText(serverField);
    }

    public static void setFxUsernameText(String username) {
        GrepolisBot.fxUsername.setText(username);
    }

    public static void setFxPasswordText(String password) {
        GrepolisBot.fxPassword.setText(password);
    }

    public static void setTimeToRefreshText(String timeToRefreshText) {
        GrepolisBot.timeToRefresh.setText(timeToRefreshText);
    }

    public static void setTimeToRefresh(TextField timeToRefresh) {
        GrepolisBot.timeToRefresh = timeToRefresh;
    }

    public static TextField getTimeToRefresh() {
        return timeToRefresh;
    }


    private void addAlerts() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
                    @Override
                    public void handle(WebEvent<String> event) {
                        String data = event.getData();
                        //Loads the towns
                        if (data.contains("TownLoaderData:")) {
                            if (data.contains("TownLoaderData:200")) {
                                loadTowns(data);
                            } else {
                                System.out.println("Error! Can't find the towns! Error log: " +event.getData());
                            }
                        }
                        //builds the buildings
                        if (data.contains("BuildingData:")) {
                            if (data.contains("BuildingData:200")) {
                                currentTown.parseHTML(data);
                                if (currentTown.buildABuilding()) {
                                    builtTheBuildings = true;
                                } else {
                                    System.out.println(getTimeOnly(LocalDateTime.now().toString()) + currentTown.getName() + " Nothing to build or building queue is full!");
                                    builtTheBuildings = true;
                                }

                            } else {
                                builtTheBuildings = true;
                                System.out.println("Error! Can't find the Buildings! Error log: " +event.getData());
                            }
                        }
                        //Starts a culture event
                        if (data.contains("CultureData:")) {
                            if (data.contains("CultureData:200")) {
                                currentTown.getCulture().parseHTML(data);
                                currentTown.getCulture().startACultureEvent();
                                obtainedCultureData = true;
                            } else {
                                obtainedCultureData = true;
                                System.out.println("Error! Can't find the culture data! Error log: " +event.getData());
                            }
                        }
                        //reads the docks data and builds a unit
                        if (data.contains("DocksData:")) {
                            if (data.contains("DocksData:200")) {
                                currentTown.getDocks().parseData(data);
                                currentTown.getDocks().buildADocksUnit();
                                builtDocksTroops = true;
                            } else {
                                builtDocksTroops = true;
                                System.out.println("Error! Can't find the docks data! Error log: " +event.getData());
                            }
                        }
                        //reads the barracks data and builds a unit
                        if (data.contains("BarracksData:")) {
                            if (data.contains("BarracksData:200")) {
                                currentTown.getBarracks().parseData(data);
                                currentTown.getBarracks().buildABarrackUnit();
                                builtBarracksTroops = true;
                            } else {
                                builtBarracksTroops = true;
                                System.out.println("Error! Can't find the barracks data! Error log: " +event.getData());
                            }
                        }
                        //Update the farm data
                        if (data.contains("FarmData:")) {
                            if (data.contains("FarmData:200")) {
                                currentTown.getFarming().parseHTML(data);
                            } else {
                                System.out.println("Error! Can't find the farm data! Error log: " +event.getData());
                            }
                        }
                        //loads the farming villages and farms them
                        if (data.contains("VillagesData:")) {
                            if (data.contains("VillagesData:200")) {
                                currentTown.getFarming().parseVillageData(data);
                                if (!currentTown.hasFullStorage()) {
                                    //TODO update this time to reflect the farming time!
                                    currentTown.setTimeToFarm(currentTime + TimeUnit.MINUTES.toMillis(5) + TimeUnit.SECONDS.toMillis(15));

                                    if (currentTown.getFarming().farmTheVillages()) {
                                        System.out.println(getTimeOnly(LocalDateTime.now().toString()) + currentTown.getName() + " has successfully farmed the villages!");
                                    } else {
                                        System.out.println(getTimeOnly(LocalDateTime.now().toString()) + currentTown.getName() + " No farmers available!");
                                    }
                                    farmedTheTown = true;
                                } else {
                                    System.out.println(getTimeOnly(LocalDateTime.now().toString()) + currentTown.getName() + " Farmers disabled. Warehouse is full.");
                                    farmedTheTown = true;
                                }

                            } else {
                                System.out.println("Error! Can't find the farming villages data! Error log: " + event.getData());
                            }
                        }
                    }
                });
            }
        });
    }



    private void loadTowns(String text) {
        String townData[] = text.split("\"id\"");

        for (String aTownData : townData) {
            if (aTownData.contains("\"name\"")) {
                Town town = new Town();

                String importantData[] = aTownData.split(",");
                town.setId(Integer.parseInt(importantData[0].replaceAll(":", "")));
                town.setName(importantData[1].split(":")[1].replaceAll("\"", ""));
                town.setServer(server);
                town.setCsrftoken(csrfToken);

                if (!townAlreadyAdded(town)) {
                    towns.add(town);
                } else {
                    changeTownName(town, town.getName(), town.getId());
                }
            }
        }
        //alphabetize the towns
        Collections.sort(towns, new Comparator<Town>() {
            @Override
            public int compare(Town town1, Town town2) {
                return (town1.getName().compareTo(town2.getName()));
            }
        });
        System.out.println("Towns found: " + towns.size());
        new Thread(new ActualBot()).start();
    }

    //TODO separate this stuff into towns!
    public static boolean isFarmersEnabled() {
        return farmersEnabled;
    }

    public static void setFarmersEnabled(boolean farmersEnabled) {
        GrepolisBot.farmersEnabled = farmersEnabled;
    }

}