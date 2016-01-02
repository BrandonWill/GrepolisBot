package Grepolis;

import Grepolis.IO.Loader;
import Grepolis.util.BrowserExtension;
import Grepolis.util.MyLogger;
import com.sun.javafx.application.PlatformImpl;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
import org.w3c.dom.html.HTMLFormElement;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;

import static Grepolis.IO.Saver.*;
import static Grepolis.util.MyLogger.log;
import static Grepolis.util.MyLogger.logError;

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

    private static TextField fxUsername;
    private static TextField fxPassword;
    private static TextField serverField;
    private static TextField timeToRefresh;
    private static Button startBot;

    private static boolean farmersEnabled = false;
    private static boolean startedBot = false;

    private static volatile boolean botIsPaused = false;
    private static volatile boolean botIsRunning = true;

    public GrepolisBot(){
        initComponents();
    }

    public static void main(String ...args){
        // Run this later:
        new MyLogger();
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
                botIsRunning = false;
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
        /*
         * This trusts ALL certificates. This isn't something one should do normally!
         * Since our browser only uses Grepolis, it's alright as long as they're not compromised.
         * This is done because Grepolis Report Converter isn't a trusted website. This allows the images to be loaded on the forums.
         */
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException ignored) {}

        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                final BooleanProperty loginAttempted = new SimpleBooleanProperty(false);

                webView = new WebView();


                webView.setPrefWidth(1000);
                final WebEngine engine = webView.getEngine();
                webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");

                log("Browser agent changed to latest chrome version. It's now: " + webView.getEngine().getUserAgent());
                webView.getEngine().getHistory().setMaxSize(3);
                engine.documentProperty().addListener(new ChangeListener<Document>() {
                    @Override
                    public void changed(ObservableValue<? extends Document> ov, Document oldDoc, Document doc) {
                        if (doc != null && !loginAttempted.get()) {
                            if (doc.getElementsByTagName("form").getLength() > 0) {
                                HTMLFormElement form = (HTMLFormElement) doc.getElementsByTagName("form").item(0);
                                if ("/glps/login_check".equals(form.getAttribute("action"))) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            //enter in username
                                            engine.executeScript("var loginElement = document.getElementById(\"login_userid\");\n" +
                                                    "var element = angular.element(loginElement);\n" +
                                                    "element.val('" + fxUsername.getText() +"');\n" +
                                                    "element.triggerHandler('input');");

                                            //enter in password
                                            engine.executeScript("var passwordElement = document.getElementById(\"login_password\");\n" +
                                                    "var element = angular.element(passwordElement);\n" +
                                                    "element.val('" + fxPassword.getText() +"');\n" +
                                                    "element.triggerHandler('input');");

                                            //click the login button
                                            engine.executeScript("var buttonElement = document.getElementById(\"login_Login\");\n" +
                                                    "var element = angular.element(buttonElement);\n" +
                                                    "element.click();");
                                        }
                                    });
                                }
                            }
                            if (doc.getElementById("worlds") != null) {
                                log("Selecting world");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        engine.load("https://" + server.substring(0, 2) + "0.grepolis.com/start?action=login_to_game_world&world=" + server);
                                    }
                                });
                            }
                        }
                    }
                });
                engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                    @Override
                    public void changed(ObservableValue<? extends Throwable> ov, Throwable oldException, Throwable exception) {
                        logError(exception);
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

                startBot = new Button("Start bot");
                startBot.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        if (!startedBot) {
                            addAlerts();
                            defaultTown = getDefaultTownID();
                            log("Bot enabled");
                            new Thread(new Startup()).start();
                            new Thread(new TitleUpdater()).start();
//                            startBot.setDisable(true);
                            startBot.setText("Pause bot");
                            startedBot = true;
                        } else {
                            pauseBot();
                        }
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

                Button GRC = new Button("Add GRC");
                GRC.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String GRC = BrowserExtension.loadGRC();
                                if (GRC != null) {
                                    webView.getEngine().executeScript(GRC);
                                }
                            }
                        });

                    }
                });
                inputGrid.addRow(0, GRC);

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

    public static void pauseBot() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                botIsPaused = !botIsPaused;
                startBot.setText(startBot.getText().equals("Pause bot") ? "Resume bot" : "Pause bot");
                webView.setDisable(false);
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
                } while (!obtainedCultureData);
                boolean forceUpdate = randInt(0, 2) == 1 || town.getBarracks().canBuildUnit();


                if (town.getBuilding(Building.BuildingType.barracks) != null && town.getBuilding(Building.BuildingType.barracks).getCurrentLevel() > 0 && forceUpdate) {
                    Thread.sleep(randInt(1250, 2500));
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
                logError(e);
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
                boolean forceUpdate = randInt(0, 2) == 1 || town.getDocks().canBuildUnit();

                if (town.getBuilding(Building.BuildingType.docks) != null && town.getBuilding(Building.BuildingType.docks).getCurrentLevel() > 0 && forceUpdate) {
                    Thread.sleep(randInt(1250, 2500));
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
                logError(e);
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
                } while (!farmedTheTown);
                boolean forceContinue;
                if (town.getBuilding(Building.BuildingType.main) != null) {
                    forceContinue = randInt(0, 2) == 1  || town.getBuilding(Building.BuildingType.main).getCurrentLevel() == 0;
                } else {
                    forceContinue = true;
                }
                boolean getBuildingData = forceContinue || town.canBuildAnything();

                if (getBuildingData) {

                    Thread.sleep(randInt(1250, 2500));

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
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/building_main?town_id=" + town.getId() + "&action=index&h=" + csrfToken + "', true);\n" +
                                    "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");" +
                                    "xhr.send(null);\n");
                        }
                    });
                } else {
                    log(currentTown.getName() + " Nothing to build or building queue is full!");
                    builtTheBuildings = true;
                }

            } catch (InterruptedException e) {
                logError(e);
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
                } while (!builtTheBuildings);

                if (town.getCulture().canStartParty()) {
                    Thread.sleep(randInt(1250, 2500));

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
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/building_place?town_id=" + town.getId() + "&action=culture&h=" + csrfToken + "', true);\n" +
                                    "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");" +
                                    "xhr.send(null);\n");
                        }
                    });
                } else {
                    obtainedCultureData = true;
                }
            } catch (InterruptedException e) {
                logError(e);
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
                                    "xhr.open('GET', 'https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + town.getId() +"&action=index&h=" + csrfToken + "&json=%7B\"town_id\"%3A" + town.getId() + "%2C\"nl_init\"%3Atrue%7D ', true);\n" +
                                    "xhr.send(null);");

                        }
                    });
                } else {
                    log(town.getName() + " Farmers aren't ready!");
                    farmedTheTown = true;
                }


            } catch (InterruptedException e) {
                logError(e);
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

    private boolean changeTownName(String name, int townID) {
        for (Town town1 : towns) {
            town1.setServer(server);
            town1.setCsrftoken(csrfToken);
            if (town1.getId() == townID && !town1.getName().equals(name)) {
                log("Updating town name from " + town1.getName() + " to " +name);
                town1.setName(name);
                return true;
            }
        }
        return false;
    }

    private int getDefaultTownID() {
        final String[] html = new String[1];
        final int[] townID = {0};
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                html[0] = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
                for (String string : html[0].split(",")) {
                    if (string.contains("csrfToken") && csrfToken == null) {
                        csrfToken = string.split(":")[1].replaceAll("\"", "");
                        log("csrftoken: " +csrfToken);
                    }
                    if (string.contains("\"townId\":")) {
                        townID[0] = Integer.parseInt(string.split(":")[1].replaceAll("\"", ""));
                        log("Default town: " +townID[0]);
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
                while (botIsRunning) {

                    //enables input in case it's time to update. Don't want to call it every 250 ms while waiting.
                    webView.setDisable(false);

                    do {
                        Thread.sleep(250);
                    } while (System.currentTimeMillis() < updateTime);

                    //disables input. Decreases chance of a ban
                    webView.setDisable(true);

                    Thread.sleep(randInt(1000, 1500));

                    Thread[] farmer = new Thread[1];
                    Thread[] culture = new Thread[1];
                    Thread[] builder = new Thread[1];
                    Thread[] barrackQueue = new Thread[1];
                    Thread[] docksQueue = new Thread[1];
                    for (int i = 0; i < towns.size(); i++) {
                        checkForCaptcha();

                        while (botIsPaused) {
                            if (webView.isDisable()) {
                                webView.setDisable(false);
                            }
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
                        } else {
                            log("\n");
                            new Thread(new TownSwitcher(towns.get(0))).start();
                        }

                        do {
                            Thread.sleep(250);
                        } while (!canContinue);

                    }
                    updateTime = getUpdateTime();
                }
            } catch (InterruptedException e) {
                logError(e);
            }
        }
    }

    private long getUpdateTime() {
        String time = timeToRefresh.getText();
        String intervals[] = time.split(":");
        int hours = Integer.parseInt(intervals[0]);
        int minutes = Integer.parseInt(intervals[1]);
        int seconds = Integer.parseInt(intervals[2]);
        long timeToUpdate = TimeUnit.HOURS.toMillis(hours) +TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
        double variance = ((double) timeToUpdate)* 0.1;
        timeToUpdate = ThreadLocalRandom.current().nextLong(timeToUpdate-(long) variance, timeToUpdate+ (long)variance);
        return System.currentTimeMillis() + timeToUpdate;
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
                        logError(e);
                    }
                    canContinue = true;
                }

            });
        }

        private String townSwitcherJSON() {
            return "{\"town_id\":" + town.getId() +",\"nl_init\":true}";
        }
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

        @Override
        public void run() {
            while (botIsRunning) {
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
                    log(Level.SEVERE, "Captcha detected! Pausing bot shortly");
                    botIsPaused = true;
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
                                log(Level.SEVERE, "Error! Can't find the towns! Error log: " + event.getData());
                                pauseBot();
                            }
                        }
                        //builds the buildings
                        if (data.contains("BuildingData:")) {
                            if (data.contains("BuildingData:200")) {
                                currentTown.parseHTML(data);
                                if (currentTown.buildABuilding()) {
                                    builtTheBuildings = true;
                                } else {
                                    log(currentTown.getName() + " Nothing to build or building queue is full!");
                                    builtTheBuildings = true;
                                }

                            } else {
                                builtTheBuildings = true;
                                log(Level.SEVERE, "Error! Can't find the Buildings! Error log: " + event.getData());
                                pauseBot();
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
                                log(Level.SEVERE, "Error! Can't find the culture data! Error log: " + event.getData());
                                pauseBot();
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
                                log(Level.SEVERE, "Error! Can't find the docks data! Error log: " + event.getData());
                                pauseBot();
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
                                log(Level.SEVERE, "Error! Can't find the barracks data! Error log: " + event.getData());
                                pauseBot();
                            }
                        }
                        //Update the farm data
                        if (data.contains("FarmData:")) {
                            if (data.contains("FarmData:200")) {
                                currentTown.getFarming().parseHTML(data);
                            } else {
                                log(Level.SEVERE, "Error! Can't find the farm data! Error log: " + event.getData());
                                pauseBot();
                            }
                        }
                        //loads the farming villages and farms them
                        if (data.contains("VillagesData:")) {
                            if (data.contains("VillagesData:200")) {
                                currentTown.getFarming().parseVillageData(data);
                                if (!currentTown.hasFullStorage()) {
                                    //TODO update this time to reflect the farming time!
                                    currentTown.setTimeToFarm(currentTime + TimeUnit.SECONDS.toMillis(Farming.getTimeToFarm().seconds));

                                    if (currentTown.getFarming().farmTheVillages()) {
                                        log(currentTown.getName() + " has successfully farmed the villages!");
                                    } else {
                                        log(currentTown.getName() + " No farmers available!");
                                    }
                                    farmedTheTown = true;
                                } else {
                                    log(currentTown.getName() + " Farmers disabled. Warehouse is full.");
                                    farmedTheTown = true;
                                }

                            } else {
                                log(Level.SEVERE, "Error! Can't find the farming villages data! Error log: " + event.getData());
                                pauseBot();
                            }
                        }
                    }
                });
            }
        });
    }



    private void loadTowns(String text) {
        ArrayList<Town> townList = new ArrayList<>();
        String townData[] = text.split("\"id\"");

        for (String aTownData : townData) {
            if (aTownData.contains("\"name\"")) {
//                System.out.println("Town data: " +aTownData);
                Town town = new Town();

                String importantData[] = aTownData.split(",");
//                System.out.println("Important data: " + Arrays.toString(importantData));
                town.setId(Integer.parseInt(importantData[0].replaceAll(":", "")));
                town.setName(importantData[1].split(":")[1].replaceAll("\"", ""));
                town.setServer(server);
                town.setCsrftoken(csrfToken);
//                System.out.println("Town id: " +town.getId());
//                System.out.println("Town name: " +town.getName());

                townList.add(town);

                if (!townAlreadyAdded(town)) {
                    towns.add(town);
                } else {
                    changeTownName(town.getName(), town.getId());
                }
            }
        }

        //Check to remove towns that have been lost!
        //Don't want to destroy every town if there's an error loading them!
        if (townList.size() > 0) {
            //Can't modify array lists while searching through them
            ArrayList<Town> lostTowns = new ArrayList<>();
            for (Town town : towns) {
                if (!ownTheTown(town, townList)) {
                    lostTowns.add(town);
                }
            }

            for (Town lostTown : lostTowns) {
                Town townToRemove = getLostTown(lostTown.getId());
                if (townToRemove != null) {
                    towns.remove(townToRemove);
                    log(Level.WARNING, townToRemove.getName() + " wasn't found! Bot is removing it from the list of current towns.");
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
        log("Towns found: " + towns.size());
//        log("Town[0] id and name: " + towns.get(0).getId() + " : " + towns.get(0).getName());
        new Thread(new ActualBot()).start();
    }

    private boolean ownTheTown(Town town, ArrayList<Town> townList) {
        for (Town currentTown : townList) {
            if (town.getId() == currentTown.getId()) {
                return true;
            }
        }
        return false;
    }

    private Town getLostTown(int townID) {
        for (Town town : towns) {
            if (town.getId() == townID) {
                return town;
            }
        }
        return null;
    }

    //TODO separate this stuff into towns!
    public static boolean isFarmersEnabled() {
        return farmersEnabled;
    }

    public static void setFarmersEnabled(boolean farmersEnabled) {
        GrepolisBot.farmersEnabled = farmersEnabled;
    }

}