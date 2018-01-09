package Grepolis;

import Grepolis.GUI.BotFrame;
import Grepolis.GUI.SettingsPanel;
import Grepolis.IO.Loader;
import Grepolis.util.BrowserExtension;
import Grepolis.util.MyLogger;
import com.sun.deploy.util.SystemUtils;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static Grepolis.util.MyLogger.log;
import static Grepolis.util.MyLogger.logError;

public class GrepolisBot {

    private JFXPanel jfxPanel;
    private static BotFrame botFrame;
    public static WebView webView;

    private static String csrfToken = null;
    private static int playerID;
    private long botUpdateTime = 0;
    private long troopUpdateTime = 0;

    private static ArrayList<Town> towns = new ArrayList<>();


    private static String server;
    private int defaultTown;

    private static Button startBot;

    private static boolean startedBot = false;

    private static volatile boolean botIsPaused = false;
    private static volatile boolean botIsRunning = false;
    private static volatile boolean loadedVillagesFromMap = false;
    private static volatile boolean openedFarmInterface = false;
    private static volatile boolean restartBot = false;
    private static volatile int currentTownIndex = 0;
    private static volatile String cookie;
    private static volatile boolean buildTroops = true;

    private HashMap<Integer, Boolean> townHasFarms = new HashMap<>();

    public GrepolisBot() {
        initComponents();
    }

    public static void main(String... args) {
//        System.out.println("Java version: " +System.getProperty("java.version"));
        double javaVersion = Double.parseDouble(Runtime.class.getPackage().getSpecificationVersion());
        //Checks to see if Java version is higher than 8.
        if (javaVersion >= 1.8) {
            new MyLogger(); //initiates the logger. This logs into the bot and the console
            Loader.load(); //Load all the towns, if they exist
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Uses the system's look and feel. Without this, sometimes Nimbus theme appears for no reason and nothing is aligned.
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ignored) {
                    }
                    botFrame = new BotFrame(towns);
                    botFrame.setSize(new Dimension(1500, 800));
                    botFrame.setVisible(true);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Your current java version is: " + Runtime.class.getPackage().getSpecificationVersion() + "\n"
                    + "You need to have Java 8 or higher to run this program. In order to fix this: \n"
                    + "1. Uninstall older versions of Java\n"
                    + "2. Install the newest version of Java (Google download java)");
        }
    }

    private void initComponents() {
        jfxPanel = new JFXPanel();

        //Custom browser zooming. WebView doesn't currently have this.

        //Resets the zoom to 1:1 ratio
        Action resetZoom = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                PlatformImpl.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (webView != null) {
                            webView.setScaleX(1);
                            webView.setScaleY(1);
                        }
                    }
                });
            }
        };

        jfxPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK), "resetZoom");
        jfxPanel.getActionMap().put("resetZoom", resetZoom);

        //Zooms in by a factor of 1.1
        Action zoomIn = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                PlatformImpl.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (webView != null) {
                            webView.setScaleX(webView.getScaleX() * 1.1);
                            webView.setScaleY(webView.getScaleY() * 1.1);
                        }
                    }
                });
            }
        };

        jfxPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK), "zoomIn");
        jfxPanel.getActionMap().put("zoomIn", zoomIn);

        //zooms out by a factor of 1.1
        Action zoomOut = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                PlatformImpl.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (webView != null) {
                            webView.setScaleX(webView.getScaleX() / 1.1);
                            webView.setScaleY(webView.getScaleY() / 1.1);
                        }
                    }
                });
            }
        };

        jfxPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK), "zoomOut");
        jfxPanel.getActionMap().put("zoomOut", zoomOut);


        createScene();

    }

    /**
     * createScene
     * <p/>
     * Note: Key is that Scene needs to be created and run on "FX user thread"
     * NOT on the AWT-EventQueue Thread
     */
    private void createScene() {
        /*
         * This trusts ALL certificates. This isn't something one should do normally!
         * Since our browser only uses Grepolis, it's alright as long as they're not compromised.
         * This is done because Grepolis Report Converter isn't a trusted website. This allows the images to be loaded on the forums.
         */
        TrustManager[] trustAllCerts = new TrustManager[]{
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
        } catch (GeneralSecurityException ignored) {
        }

        //Browser code

        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                //adds in the XHRHTMLRequest listener
                final BooleanProperty loginAttempted = new SimpleBooleanProperty(false);

                webView = new WebView();


                webView.setPrefWidth(1000);
                final WebEngine engine = webView.getEngine();
                webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
                webView.getEngine().setUserDataDirectory(new File("localStorage"));
                webView.getEngine().setJavaScriptEnabled(true);
                log("Browser agent changed to latest chrome version. It's now: " + webView.getEngine().getUserAgent());
                webView.getEngine().getHistory().setMaxSize(3);
                //Every request received is handled by alerts!
                addAlerts();
                engine.documentProperty().addListener(new ChangeListener<Document>() {
                    @Override
                    public void changed(ObservableValue<? extends Document> ov, Document oldDoc, Document doc) {
                        //Every time a new page is loaded, this XHR request listener is added.
                        //It does take a small amount of time to be attached sadly, which means it misses 3 requests on the initial login.
                        //Perhaps this could be fixed by writing my own WebView class?
                        engine.executeScript("var s_ajaxListener = new Object();\n" +
                                "s_ajaxListener.tempOpen = window.XMLHttpRequest.prototype.open;\n" +
                                "s_ajaxListener.tempSend = window.XMLHttpRequest.prototype.send;\n" +
                                "s_ajaxListener.callback = function () {\n" +
                                "}\n" +
                                "\n" +
                                "window.XMLHttpRequest.prototype.open = function(a,b) {\n" +
                                "  if (!a) var a='';\n" +
                                "  if (!b) var b='';\n" +
                                "  s_ajaxListener.tempOpen.apply(this, arguments);\n" +
                                "  s_ajaxListener.method = a;  \n" +
                                "  s_ajaxListener.url = b;\n" +
                                "  if (a.toLowerCase() == 'get') {\n" +
                                "    s_ajaxListener.data = b.split('?');\n" +
                                "    s_ajaxListener.data = s_ajaxListener.data[1];\n" +
                                "  }\n" +
                                "}\n" +
                                "\n" +
                                "window.XMLHttpRequest.prototype.send = function(a,b) {\n" +
                                "  if (!a) var a='';\n" +
                                "  if (!b) var b='';\n" +
                                "  s_ajaxListener.tempSend.apply(this, arguments);\n" +
                                "  if(s_ajaxListener.method.toLowerCase() == 'post')s_ajaxListener.data = a;\n" +
                                "  s_ajaxListener.callback();\n" +
                                "}\n" +
                                "function readBody(xhr) {\n" +
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
                                "var oldXHR = window.XMLHttpRequest;\n" +
                                "\n" +
                                "function newXHR() {\n" +
                                "    var realXHR = new oldXHR();\n" +
                                "    realXHR.addEventListener(\"readystatechange\", function() {\n" +
                                "        if(realXHR.readyState==4 && realXHR.status==200){\n" +
                                "            alert(\"XHR Reader:\" +realXHR.status +readBody(realXHR) +\" URL:\" +s_ajaxListener.url);\n" +
                                "        }\n" +
                                "    }, false);\n" +
                                "    return realXHR;\n" +
                                "}\n" +
                                "window.XMLHttpRequest = newXHR;");
                        if (doc != null && !loginAttempted.get() && !botIsPaused) {
                            //Checks the page to see if the login form is there.
                            //Will function if a user accidentally right clicks "go back"
                            if (doc.getElementsByTagName("form").getLength() > 0) {
                                HTMLFormElement form = (HTMLFormElement) doc.getElementsByTagName("form").item(0);
                                if ("/glps/login_check".equals(form.getAttribute("action"))) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            //They've switched to Vue.js, why this over angular? Who knows. My guess is a new developer
                                            engine.executeScript("vmLogin.$data.formData.userid.value = '" + SettingsPanel.getUsernameField().getText() + "';\n" +
                                                    "vmLogin.$data.formData.password.value = '" + SettingsPanel.getPasswordField().getText() + "';\n" +
                                                    "document.getElementById('login_Login').click();");
//
//                                            //enter in password
//                                            engine.executeScript("var passwordElement = document.getElementById(\"login_password\");\n" +
//                                                    "var element = angular.element(passwordElement);\n" +
//                                                    "element.val('" + SettingsPanel.getPasswordField().getText() + "');\n" +
//                                                    "element.triggerHandler('input');");
//
//                                            //click the login button
//                                            engine.executeScript("var buttonElement = document.getElementById(\"login_Login\");\n" +
//                                                    "var element = angular.element(buttonElement);\n" +
//                                                    "element.click();");

                                            /* This is the old angular login code. In case they go back! */
                                            //enter in username
//                                            engine.executeScript("var loginElement = document.getElementById(\"login_userid\");\n" +
//                                                    "var element = angular.element(loginElement);\n" +
//                                                    "element.val('" + SettingsPanel.getUsernameField().getText() + "');\n" +
//                                                    "element.triggerHandler('input');");
//
//                                            //enter in password
//                                            engine.executeScript("var passwordElement = document.getElementById(\"login_password\");\n" +
//                                                    "var element = angular.element(passwordElement);\n" +
//                                                    "element.val('" + SettingsPanel.getPasswordField().getText() + "');\n" +
//                                                    "element.triggerHandler('input');");
//
//                                            //click the login button
//                                            engine.executeScript("var buttonElement = document.getElementById(\"login_Login\");\n" +
//                                                    "var element = angular.element(buttonElement);\n" +
//                                                    "element.click();");
                                        }
                                    });
                                }
                            }
                            //Selects the world if the user is already logged in.
                            //Will function if a user accidentally right clicks "go back"
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
                //This is a developer tool. If we suck at JS and there's an error executing, it'll tell us.
                engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                    @Override
                    public void changed(ObservableValue<? extends Throwable> ov, Throwable oldException, Throwable exception) {
                        logError(exception);
                    }
                });

                //Java FX beginning. Self-explanatory buttons

                GridPane inputGrid = new GridPane();
                inputGrid.setHgap(10);
                inputGrid.setVgap(10);

                final Button fxLoginButton = new Button("Login to Grepolis");
                fxLoginButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent t) {
                        if (notEmpty(SettingsPanel.getPasswordField().getText()) && notEmpty(SettingsPanel.getUsernameField().getText()) && notEmpty(SettingsPanel.getWorldField().getText())) {
                            loginAttempted.set(false);
                            server = SettingsPanel.getWorldField().getText();
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
                            startedBot = true;
                            log("Bot enabled");
                            new Thread(new Startup()).start();
                            new Thread(new TitleUpdater()).start();
//                            startBot.setDisable(true);
                            startBot.setText("Pause bot");

                        } else {
                            pauseBot();
                        }
                    }
                });

                //Loads firebug directly from their website so we can see errors in real time.
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
                                } else {
                                    log(Level.SEVERE, "GRC returned null");
                                }
                            }
                        });

                    }
                });
                inputGrid.addRow(0, GRC);

                ProgressIndicator fxLoadProgress = new ProgressIndicator(0);
                fxLoadProgress.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());
                fxLoadProgress.visibleProperty().bind(webView.getEngine().getLoadWorker().runningProperty());

                HBox loginPane = new HBox(10);
                loginPane.getChildren().setAll(
                        fxLoginButton,
                        fxLoadProgress,
                        startBot,
                        botDebugger,
                        GRC
                );

                final VBox layout = new VBox(10);
                //CSS styling for the login layout
                layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
                layout.getChildren().addAll(
                        loginPane,
                        webView
                );
                //Ensures it will resize with the frame
                VBox.setVgrow(webView, Priority.ALWAYS);
                Scene scene = new Scene(layout);

                jfxPanel.setScene(scene);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        botFrame.getjTabbedPane1().addTab("Browser", jfxPanel);
                    }
                });


            }
        });
    }

    /**
     * Pauses the bot
     * Prefer to keep it public because I may reorganize everything one day.
     */
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

    private void restartBot() {
//        botIsRunning = true;
//        restartBot = false;
//
//        new Thread(new Startup()).start();
//        new Thread(new TitleUpdater()).start();
    }

    private boolean notEmpty(String s) {
        return s != null && !"".equals(s);
    }

    /**
     * @param min minimum number
     * @param max maximum number
     * @return a random integer between min and max
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * This was used for printing enums before using strings
     *
     * @param word Text to capitalize.
     * @return Text with the first letter capitalized.
     */
    private String capitalize(String word) {
        if (word.contains("_")) {
            String[] holder = word.split("_");
            word = "";
            for (String string : holder) {
                word += string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase() + " ";
            }
            if (word.endsWith(" ")) {
                word = word.substring(0, word.length() - 1);
            }
        } else {
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        return word;
    }

    private boolean builtBarracksTroops = false;

    public HashMap<Integer, Boolean> getTownHasFarms() {
        return townHasFarms;
    }

    public void setTownHasFarms(HashMap<Integer, Boolean> townHasFarms) {
        this.townHasFarms = townHasFarms;
    }

    public void setBotFrame(BotFrame botFrame) {
        GrepolisBot.botFrame = botFrame;
    }

    /**
     * Sends the request to begin building troops in the barracks
     */
    public class BuildBarracksTroops implements Runnable {
        Town town;

        /**
         * This is used in case it's moved outside of GrepolisBot. We can technically use currentTown here.
         *
         * @param town sets the town here
         */
        public BuildBarracksTroops(Town town) {
            this.town = town;
        }

        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!obtainedCultureData);

                //Barracks can be null if it isn't built yet. Normally isn't a problem, but required before trying to obtain its' level.
                if (buildTroops && town.getBuilding(Building.BuildingType.barracks) != null && town.getBuilding(Building.BuildingType.barracks).getCurrentLevel() > 0) {

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

                if (town.getBuilding(Building.BuildingType.docks) != null && town.getBuilding(Building.BuildingType.docks).getCurrentLevel() > 0) {
                    Thread.sleep(randInt(1250, 2500));
                    Platform.runLater(() ->
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
                            "xhr.send(null);"));
                } else {
                    builtDocksTroops = true;
                }


            } catch (InterruptedException e) {
                logError(e);
            }
        }
    }

    private static boolean[] researchedTheTown;

    public class Researcher implements Runnable {
        Town town;

        public Researcher(Town town) {
            this.town = town;
        }

        public void run() {
            try {
                //TODO This has to run every time, like everything else does. Only loads 1 town's research.
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!builtTheBuildings);

                //Only works on newer servers with battle point villages. Not sure why, but it isn't a good idea to support the older servers.
                if (Farming.hasBattlePointVillages() && town.getBuilding(Building.BuildingType.academy) != null && town.getBuilding(Building.BuildingType.academy).getLevel() > 0) {
                    Thread.sleep(randInt(1250, 2500));

                    //Loads data exactly from the Academy
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
                                    "xhr.open('GET', 'https://" + town.getServer() + ".grepolis.com/game/frontend_bridge" + stringForLoadingAcademy() + ", true);\n" +
                                    "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                                    "xhr.send(null);");
                        }
                    });
                } else {
                    researchedTheTown[currentTownIndex] = true;
                }

            } catch (InterruptedException e) {
                logError(e);
            }
        }

        private String stringForLoadingAcademy() {
            //{"window_type":"academy","tab_type":"research","known_data":{"models":["Player","PlayerLedger","PremiumFeatures"],
            // "collections":["TownBuildings","TownResearches","ResearchOrders","BuildingOrders","Towns","PlayerHeroes"],
            // "templates":[]},"town_id":xxxxx,"nl_init":true}
            StringBuilder sb = new StringBuilder();
            String town_id = "?town_id=" + town.getId();
            String action = "&action=fetch";
            String h = "&h=" + town.getCsrftoken();
            sb.append(town_id);
            sb.append(action);
            sb.append(h);

            //JSON starts here!
            sb.append("&json=' +encodeURIComponent(JSON.stringify(");

            sb.append("{\"window_type\":\"academy\",\"tab_type\":\"research\",\"known_data\":{\"models\":[\"Player\",\"PlayerLedger\",\"PremiumFeatures\"],\"collections\":[\"TownBuildings\",\"BuildingOrders\",\"Towns\",\"PlayerHeroes\"],\"templates\":[]},\"town_id\":" + town.getId() + ",\"nl_init\":true}");


            sb.append("))");
            //System.out.println("String: " +sb.toString());
            return sb.toString();
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
                do {
                    Thread.sleep(randInt(250, 500));
                } while (!farmedTheTown);


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

                do {
                    Thread.sleep(randInt(250, 500));
                } while (!researchedTheTown[currentTownIndex]);

//                log("Checking for culture event step 1.");

                if (SettingsPanel.hasAutomaticFestivals() && town.getCulture().canStartParty()) {
                    log("Checking for culture event step 2.");
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
    private long currentTime;

    public class FarmTheTown implements Runnable {
        Town town;

        public FarmTheTown(Town town) {
            this.town = town;
        }

        public void run() {
            try {

                long minuteInMilliseconds = 60000;
                if (Farming.isCaptainEnabled() && (getServerUnixTime() + minuteInMilliseconds) > Farming.getCaptainExpiresAt()) {
                    log("Captain detected as expiring soon, so bot is changing to farming without captain");
                    Farming.setCaptainEnabled(false);
                }


                Thread.sleep(3000);

                currentTime = getServerUnixTime();

                if (currentTime > town.getTimeToFarm()) {

                    if (Farming.isCaptainEnabled()) {
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
                                        "xhr.open('GET', 'https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + town.getId() + "&action=index&h=" + csrfToken + "&json=%7B\"town_id\"%3A" + town.getId() + "%2C\"nl_init\"%3Atrue%7D ', true);\n" +
                                        "xhr.send(null);");

                            }
                        });
                    } else {
                        loadedVillagesFromMap = false;
                        town.getFarming().getFarmingVillages().clear();

//                        System.out.println("Loading farmers");
                        if (!town.hasFullStorage()) {
                            town.getFarming().loadVillagesFromMap();

                            //Threaded so must wait for them to load
                            while (!loadedVillagesFromMap) {
                                Thread.sleep(randInt(100, 500));
                            }

                            log("Farming beginning in: " + town.getName());
                            final List<FarmingVillage> farmingVillages = Collections.synchronizedList(town.getFarming().getFarmingVillages());
                            final int villageSize = farmingVillages.size();
                            for (int i = 0; i < villageSize; i++) {
                                openedFarmInterface = false;

                                if (i < farmingVillages.size()) {
                                    town.getFarming().openFarmingVillageInterface(farmingVillages.get(i));
                                } else {
                                    break;
                                }

                                while (!openedFarmInterface) {
                                    Thread.sleep(randInt(100, 500));
                                }

                                if (i < farmingVillages.size() && farmingVillages.get(i).canFarm()) {
                                    if (i < farmingVillages.size() && town.getFarming().farmTheVillageFromMap(farmingVillages.get(i))) {
//                                        log("The farming village " + farmingVillages.get(i).getName() + " was farmed successfully!");
                                        Thread.sleep(randInt(500, 1000));
                                    }
                                } else {
                                    if (i < farmingVillages.size()) {
                                        log(Level.WARNING, "The farming village " + farmingVillages.get(i).getName() + " isn't ready to be farmed or is farmed to its' cap!");
                                    } else {
                                        break;
                                    }
                                }
                            }
                            log("Farming ending in: " + town.getName());
                        } else {
                            log(currentTown.getName() + " Farmers disabled. Warehouse is full.");
                        }
                        farmedTheTown = true;
                    }
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

        public Startup() {
        }


        @Override
        public void run() {
            if (Farming.isCaptainEnabled()) {
                final int townID = defaultTown;
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
                                "var htmlData;\n" +
                                "xhr.onreadystatechange = function() {\n" +
                                "    if (xhr.readyState == 4 && typeof xhr !='undefined') {\n" +
                                "        htmlData = readBody(xhr);\n" +
                                "        alert(\"TownFarmingData:\" +xhr.status +readBody(xhr));\n" +
                                "    }\n" +
                                "}\n" +
                                "\n" +
                                "xhr.open('GET', 'https://" + server + ".grepolis.com/game/farm_town_overviews?town_id=" + townID + "&action=index&h=" + csrfToken + "', true);\n" +
                                "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                                "xhr.send(null)");

                    }
                });
            } else {
                if (!botIsRunning) {
                    new Thread(new ActualBot()).start();
                }
            }
        }
        //
    }

    /**
     * Updates the town if it's already added.
     *
     * @param town Town to check if it's already added.
     * @return
     */
    private boolean townAlreadyAdded(Town town) {
        for (Town town1 : towns) {
            if (town1.getId() == town.getId()) {
                town1.setIsland_x(town.getIsland_x());
                town1.setIsland_y(town.getIsland_y());
                town1.setFullStorage(town.hasFullStorage());
                town1.setLast_wood(town.getLast_wood());
                town1.setLast_stone(town.getLast_stone());
                town1.setLast_iron(town.getLast_iron());
                return true;
            }
        }
        return false;
    }

    /**
     * Update town names based on their townID.
     *
     * @param name   town name that was found.
     * @param townID townID that the name belongs to.
     * @return <b>true</b> if the town name was updated.
     */
    private boolean changeTownName(String name, int townID) {
        for (Town town1 : towns) {
            town1.setServer(server);
            town1.setCsrftoken(csrfToken);
            if (town1.getId() == townID && !town1.getName().equals(name) && !name.contains("}")) {
                log("Updating town name from " + town1.getName() + " to " + name);
                town1.setName(name);
                return true;
            }
        }
        return false;
    }


    private volatile boolean canContinue;
    private Town currentTown;

    /**
     * The bot actually running through the towns.
     */
    public class ActualBot implements Runnable {
        public void run() {
            try {
                botIsRunning = true;
                restartBot = false;
                farmedTheTown = true;
                builtTheBuildings = true;
                obtainedCultureData = true;
                while (botIsRunning) {

                    //enables input in case it's time to update. Don't want to call it every 250 ms while waiting.
                    webView.setDisable(false);

                    do {
                        Thread.sleep(250);
                    } while (System.currentTimeMillis() < botUpdateTime);

                    buildTroops = System.currentTimeMillis() > troopUpdateTime;

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
                            if (restartBot) {
                                break;
                            }
                        }

                        if (restartBot) {
                            break;
                        }

                        currentTown = towns.get(i);

                        if (!currentTown.hasConqueror()) {
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

                            if (towns.get(i).getFarming().isEnabled() && (townHasFarms.get(towns.get(i).getId()) != null || !Farming.isCaptainEnabled())) {
                                farmer[0] = (new Thread(new FarmTheTown(towns.get(i))));
                                farmer[0].start();
                            } else {
                                if (!towns.get(i).getFarming().isEnabled()) {
                                    log(currentTown.getName() + " farmers are disabled!");
                                }
//                                if (townHasFarms.get(towns.get(i).getFarm_town_id()) == null) {
//                                    log(currentTown.getName() + " doesn't have any farmers or they're not loaded yet!");
//                                }
                                farmedTheTown = true;
                            }
//                            researchedTheTown[i] = true;
                            currentTownIndex = i;
                            if (!researchedTheTown[i]) {
                                (new Thread(new Researcher(towns.get(i)))).start();
                            }
                            builder[0] = (new Thread(new BuildTheBuildings(towns.get(i))));
                            builder[0].start();
                            culture[0] = (new Thread(new StartCultureEvents(towns.get(i))));
                            culture[0].start();
                            barrackQueue[0] = (new Thread(new BuildBarracksTroops(towns.get(i))));
                            barrackQueue[0].start();
                            docksQueue[0] = (new Thread(new BuildDocksTroops(towns.get(i))));
                            docksQueue[0].start();
                            while (!farmedTheTown || !obtainedCultureData || !builtTheBuildings || !builtBarracksTroops || !builtDocksTroops) {
                                if (restartBot) {
                                    break;
                                }
                                Thread.sleep(200);
                            }
                        } else {
                            log(currentTown.getName() + " has a conqueror! Skipping town!");
                        }

                        canContinue = false;

                        if (i + 1 < towns.size()) {
                            new Thread(new TownSwitcher(towns.get(i + 1))).start();
                        } else {
                            log("\n");
                            if (towns.size() > 1) {
                                new Thread(new TownSwitcher(towns.get(0))).start();
                            } else {
                                canContinue = true;
                            }
                        }

                        do {
                            Thread.sleep(randInt(250, 500));
                        } while (!canContinue);

                    }
                    if (restartBot) {
                        break;
                    }

                    botUpdateTime = getBotUpdateTime();
                    troopUpdateTime = getTroopUpdateTime();
                }


                if (restartBot) {
                    botIsRunning = false;
                    log("An error has been detected. The bot has stopped.");

//                    botUpdateTime = getBotUpdateTime();
//
//                    do {
//                        Thread.sleep(250);
//                    } while (System.currentTimeMillis() < botUpdateTime);
//
//                    Thread.sleep(30000);
//
//                    restartBot();
                }
            } catch (InterruptedException e) {
                logError(e);
            }
        }
    }

    /**
     * This adds a variance to the update time so it isn't farming at exactly every 5 minutes.
     *
     * @return the time to update from the GUI.
     */
    private long getBotUpdateTime() {
        String time = SettingsPanel.getBotUpdateTimeField().getText();
        String intervals[] = time.split(":");
        int hours = Integer.parseInt(intervals[0]);
        int minutes = Integer.parseInt(intervals[1]);
        int seconds = Integer.parseInt(intervals[2]);
        long timeToUpdate = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
        double variance = ((double) timeToUpdate) * 0.1;
        timeToUpdate = ThreadLocalRandom.current().nextLong(timeToUpdate - (long) variance, timeToUpdate + (long) variance);
        return System.currentTimeMillis() + timeToUpdate;
    }

    private long getTroopUpdateTime() {
        String botUpdatetime = SettingsPanel.getBotUpdateTimeField().getText();
        String troopUpdateTime = SettingsPanel.getTroopUpdateTimeField().getText();
        //If they have the same interval, we want them to update at the time same!
        if (botUpdatetime.equals(troopUpdateTime)) {
            return this.botUpdateTime;
        } else if (buildTroops) {
            String intervals[] = troopUpdateTime.split(":");
            int hours = Integer.parseInt(intervals[0]);
            int minutes = Integer.parseInt(intervals[1]);
            int seconds = Integer.parseInt(intervals[2]);
            long timeToUpdate = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
            double variance = ((double) timeToUpdate) * 0.1;
            timeToUpdate = ThreadLocalRandom.current().nextLong(timeToUpdate - (long) variance, timeToUpdate + (long) variance);
            return System.currentTimeMillis() + timeToUpdate;
        } else {
            return this.troopUpdateTime;
        }
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
                            "        alert(\"TownSwitchData:\" +xhr.status +readBody(xhr));\n" +
                            "    }\n" +
                            "}\n" +
                            "\n" +
                            "xhr.open('POST', 'https://" + server + ".grepolis.com/game/frontend_bridge?town_id=" + town.getId() + "&action=execute&h=" + csrfToken + "&json=' +encodeURIComponent(JSON.stringify(" + townSwitcherJSON() + ")), true);\n" +
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
            return "{\"model_url\":\"CommandsMenuBubble/1294219\",\"action_name\":\"forceUpdate\",\"arguments\":{},\"town_id\":" + town.getId() + ",\"nl_init\":true}";
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
            long botTimeToUpdate = 0;
            long troopTimeToUpdate = 0;
            long pauseTime = 0;
            while (startedBot || restartBot) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (botUpdateTime > 0 || troopTimeToUpdate > 0) {
                    if (!botIsPaused) {
                        if (pauseTime > 0) {
                            botUpdateTime += System.currentTimeMillis() - pauseTime;
                            troopUpdateTime += System.currentTimeMillis() - pauseTime;
                            pauseTime = 0;
                        }
                        botTimeToUpdate = botUpdateTime - System.currentTimeMillis();
                        troopTimeToUpdate = troopUpdateTime - System.currentTimeMillis();
                    } else {
                        if (pauseTime == 0) {
                            pauseTime = System.currentTimeMillis();
                        }
                    }
                    if (!botIsPaused && botTimeToUpdate > 0) {
                        long botSecond = (botTimeToUpdate / 1000) % 60;
                        long botMinute = (botTimeToUpdate / (1000 * 60)) % 60;
                        long botHour = (botTimeToUpdate / (1000 * 60 * 60)) % 24;
                        long troopSecond = (troopTimeToUpdate / 1000) % 60;
                        long troopMinute = (troopTimeToUpdate / (1000 * 60)) % 60;
                        long troopHour = (troopTimeToUpdate / (1000 * 60 * 60)) % 24;
                        troopSecond = troopSecond < 0 ? 0 : troopSecond;
                        troopMinute = troopMinute < 0 ? 0 : troopMinute;
                        troopHour = troopHour < 0 ? 0 : troopHour;
                        final String remainingTime = String.format("Bot Update Time: %02d:%02d:%02d Troop Update Time: %02d:%02d:%02d", botHour, botMinute, botSecond, troopHour, troopMinute, troopSecond);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                botFrame.setTitle(remainingTime);
                            }
                        });

                    }
                }

            }
        }
    }

    /**
     * Captchas are currently removed from the game.
     */
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

    private void getAllTownData() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Accesses the towns!
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
                        "xhr.open('GET', 'https://" + server + ".grepolis.com/game/frontend_bridge?town_id=" + defaultTown + "&action=refetch&h=" + csrfToken + "&json=%7B%22collections%22%3A%7B%22Towns%22%3A%5B%5D%7D%2C%22town_id%22%3A" + defaultTown + "%2C%22nl_init%22%3Afalse%7D', true);\n" +
                        "xhr.setRequestHeader(\"X-Requested-With\", \"XMLHttpRequest\");\n" +
                        "xhr.send(null);");


            }
        });
    }

    private void addTownsWithFarms(String text) {
        String townData[] = text.split("\"id\"");

        for (String aTownData : townData) {
            if (aTownData.contains("\"player_id\"")) {
//                System.out.println("Town data: " +aTownData);

                String importantData[] = aTownData.split(",");
//                System.out.println("Important data: " + Arrays.toString(importantData));
                int townID = Integer.parseInt(importantData[0].replaceAll(":", ""));
                townHasFarms.put(townID, true);
            }
        }
        log("Towns with farms found: " + townHasFarms.size());

        if (!botIsRunning) {
            botIsRunning = true;
            new Thread(new ActualBot()).start();
        }
    }

    private void addAlerts() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
                    @Override
                    public void handle(WebEvent<String> event) {
                        String data = event.getData();
                        String URL = null;
                        //Reads all XHR events
                        if (data.contains("XHR Reader:")) {
                            if (data.contains("\"TownGroupTowns\":{\"data\":")) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        String html = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
                                        cookie = (String) webView.getEngine().executeScript("document.cookie");
                                        //                                       CreateResearchEnum.parseHTML(html);
                                        for (String string : html.split(",")) {
                                            if (string.contains("csrfToken") && csrfToken == null) {
                                                csrfToken = string.split(":")[1].replaceAll("\"", "");
                                                log("csrftoken: " + csrfToken);
                                            }
                                            if (string.contains("\"townId\":")) {
                                                defaultTown = Integer.parseInt(string.split(":")[1].replaceAll("\"", ""));
                                                log("Default town: " + defaultTown);
                                            }
                                            if (string.contains("\"captain\":")) {
                                                if (string.contains("null")) {
                                                    Farming.setCaptainEnabled(false);
                                                    log(Level.SEVERE, "--------Captain wasn't found!-----");
                                                } else {
                                                    if (string.contains(":")) {
                                                        String expiresAt = string.split(":")[1];
                                                        if (isStringDigit(expiresAt)) {
                                                            long expires = Long.parseLong(expiresAt);
                                                            Farming.setCaptainExpiresAt(expires);
                                                            //100 is the gold cost of the captain
                                                            if (expires != 100 && getServerUnixTime() >= expires) {
                                                                Farming.setCaptainEnabled(false);
                                                                log(Level.SEVERE, "--------Captain detected as expired!-----");
                                                            } else if (getServerUnixTime() <= expires && botIsRunning) {
                                                                Farming.setCaptainEnabled(true);
                                                                new Thread(new Startup()).start();                     log(Level.FINE, "--------Captain detected as enabled!-----");
//                                                                new Thread(new Startup()).start();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (string.contains("player_timezone")) {
                                                String timezone = string.split(":")[1].replaceAll("\\\\", "").replaceAll("\"", "");
//                                                log("Timezone detected: " +timezone);
                                                TimeZone tz = TimeZone.getTimeZone(timezone);
                                                Calendar c = Calendar.getInstance(tz);
                                                log("Verify server time is: " +c.get(java.util.Calendar.HOUR_OF_DAY)+":"+c.get(java.util.Calendar.MINUTE)+":"+c.get(java.util.Calendar.SECOND));
                                            }
                                            if (string.contains("\"battlepoint_villages\":true")) {
                                                log("Battle point villages have been detected. Setting all loot mood to 100!");
                                                Farming.setBattlePointVillages(true);
                                                Farming.setAllMoodToLootTo(100);
                                            }
                                            if (string.contains("\"game_speed\"")) {
                                                String gameSpeed = string.split(":")[1];
                                                Farming.setGameSpeed(Double.parseDouble(gameSpeed));
                                            }
                                            if (string.contains("player_id")) {
                                                playerID = Integer.parseInt(string.split("\"player_id\":")[1]);
                                            }
                                        }
                                        getAllTownData();
                                    }
                                });
                            }
                            if (data.contains("Town") && data.contains("player_id") && data.contains("storage")) {
                                //this loads all the towns and it updates them!
                                loadTowns(data);
                            }
//                            if (data.contains("URL:")) {
//                                data = data.split("URL:")[1];
//                                System.out.println("XHR event detected: " + data);
//                            }

                        }
                        //Test
                        if (data.contains("available_population")) {
//                            System.out.println("Pop: " +data);
                            Town town = null;
                            int townID = 0;
                            int availablePopulation = 0;
                            String fixingData = data.replaceAll("\\\\", "");
                            fixingData = fixingData.replaceAll("\"", "");

                            String[] splitData = fixingData.split(",");
                            String townLocation = fixingData.contains("param_id") ? "param_id" : "id";

                            for (String fixedData : splitData) {
                                if (town == null && fixedData.contains(townLocation) && !fixedData.contains("player")) {
                                    townID = Integer.parseInt(fixedData.split(":")[1]);
                                    town = getTownFromID(townID);
                                }
                                if (fixedData.contains("available_population")) {
                                    availablePopulation = Integer.parseInt(fixedData.split(":")[1]);
                                }
                            }

                            if (town != null) {
                                town.setAvailablePopulation(availablePopulation);
//                                System.out.println(town.getName() + " has available population: " + town.getAvailablePopulation());
                            }

//                            URL = data.split("URL:")[1];
                        }
                        //checks the farming village return data.
                        if (data.contains("FarmedTheVillage:")) {
                            if (data.contains("FarmedTheVillage:200")) {

                                int townID = 0;
                                int battlePointID = 0;
                                int farmingVillage = 0;
                                String name = null;
                                String[] dataPoints = data.split(":");
                                for (String dataPoint : dataPoints) {
                                    if (dataPoint.contains("TownID,")) {
                                        townID = Integer.parseInt(dataPoint.split(",")[1]);
                                    }
                                    if (dataPoint.contains("FarmingVillageID,")) {
                                        farmingVillage = Integer.parseInt(dataPoint.split(",")[1]);
                                    }
                                    if (dataPoint.contains("battlePointID,")) {
                                        battlePointID = Integer.parseInt(dataPoint.split(",")[1]);
                                    }
//                                    if (dataPoint.contains("farmVillageName,")) {
//                                        name = dataPoint.split(",")[1];
//                                    }
                                }
                                for (FarmingVillage village : currentTown.getFarming().getFarmingVillages()) {
                                    if (village.getBattlePointFarmID() == battlePointID) {
                                        name = village.getName();
                                    }
                                }

//                                if (data.contains("error")) {
//                                    log("inside of error log:townID," +townID + ":farmingVillageID," +farmingVillage +
//                                            ":battlePointID," + battlePointID + ":name," +name + ":" + data);
//                                }

                                if (townID > 0 && battlePointID > 0 && farmingVillage > 0 && data.contains("error")) {
                                    Town town = getTownFromID(townID);
                                    if (town != null) {
                                        town.getFarming().getNotMyVillages().put(battlePointID, farmingVillage);
                                        log(Level.SEVERE, "Detected a farming village in the wrong location. Will fix on next run through!");
                                    }
                                } else {
                                    log(Level.INFO, name + " has been successfully farmed!");
                                }


                            } else {
                                log(Level.WARNING, "Error receiving farm town data! Pausing bot");
                                restartBot = true;
                            }
                        }

//                      Adds a check for towns with farmers
                        if (data.contains("TownFarmingData:")) {
                            if (data.contains("TownFarmingData:200")) {
                                addTownsWithFarms(data);
                            } else {
                                log(Level.WARNING, "Error! Can't find the towns with farms! Error log: " + event.getData());
                                log(Level.WARNING, "Captain either not enabled or catastrophic error occurred!");
                                if (!botIsRunning) {
                                    new Thread(new ActualBot()).start();
                                }
                            }
                        }
                        //Checks for conqueror in the town
                        if (data.contains("TownSwitchData:")) {
                            if (data.contains("TownSwitchData:200")) {
                                currentTown.parseTownSwitchData(data);
                            } else {
                                log(Level.SEVERE, "Error! Can't find the town switcher data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        if (data.contains("AcademyData:")) {
                            if (data.contains("AcademyData:200")) {
                                currentTown.getResearch().parseHTML(data);
                                researchedTheTown[currentTownIndex] = true;
                            } else {
                                researchedTheTown[currentTownIndex] = true;
                                log(Level.SEVERE, "Error! Can't find the Academy data! Error log: " + event.getData());
                                restartBot = true;
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
                                //builtTheBuildings = true;
                                log(Level.SEVERE, "Error! Can't find the Buildings! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        //Starts a culture event
                        if (data.contains("CultureData:")) {
                            if (data.contains("CultureData:200")) {
                                currentTown.getCulture().parseHTML(data);
                                currentTown.getCulture().startACultureEvent();
                                obtainedCultureData = true;
                            } else {
                                //obtainedCultureData = true;
                                log(Level.SEVERE, "Error! Can't find the culture data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        //reads the docks data and builds a unit
                        if (data.contains("DocksData:")) {
                            if (data.contains("DocksData:200")) {
                                currentTown.getDocks().parseData(data);
                                currentTown.getDocks().buildADocksUnit();
                                builtDocksTroops = true;
                            } else {
                                //builtDocksTroops = true;
                                log(Level.SEVERE, "Error! Can't find the docks data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        //reads the barracks data and builds a unit
                        if (data.contains("BarracksData:")) {
                            if (data.contains("BarracksData:200")) {
                                currentTown.getBarracks().parseData(data);
                                currentTown.getBarracks().buildABarrackUnit();
                                builtBarracksTroops = true;
                            } else {
                                //builtBarracksTroops = true;
                                log(Level.SEVERE, "Error! Can't find the barracks data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        //Update the farm data
                        if (data.contains("FarmData:")) {
                            if (data.contains("FarmData:200")) {
                                currentTown.getFarming().parseHTML(data);
                            } else {
                                log(Level.SEVERE, "Error! Can't find the farm data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        //loads the farming villages and farms them
                        if (data.contains("VillagesData:")) {
                            if (data.contains("VillagesData:200")) {
                                currentTown.getFarming().parseVillageData(data);
                                if (!currentTown.hasFullStorage()) {
                                    //currentTown.setTimeToFarm(currentTime + TimeUnit.SECONDS.toMillis(currentTown.getFarming().getIntervalToFarm().getSeconds()));

                                    if (currentTown.getFarming().farmTheVillages()) {
                                        log(currentTown.getName() + " has successfully farmed the villages!");
                                    } else {
                                        log(currentTown.getName() + " No farmers available!");
                                    }
                                    farmedTheTown = true;
                                } else {
                                    log(currentTown.getName() + " Farmers disabled. Warehouse is full. Or farmers aren't ready!");
                                    farmedTheTown = true;
                                }

                            } else {
                                log(Level.SEVERE, "Error! Can't find the farming villages data! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        if (data.contains("FarmingInterfaceOpened:")) {
                            if (data.contains("FarmingInterfaceOpened:200")) {
                                if (currentTown.getFarming().parseVillagesFromMap(data)) {
                                    openedFarmInterface = true;
                                }
                            } else {
                                log(Level.SEVERE, "Error! Can't open the farming interface! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        if (data.contains("LoadedVillagesFromMap:")) {
                            if (data.contains("LoadedVillagesFromMap:200")) {
                                if (currentTown.getFarming().parseVillagesFromMap(data)) {
                                    loadedVillagesFromMap = true;
                                }
                            } else {
                                log(Level.SEVERE, "Error! Was unable to load the farming villages from the map! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                        if (data.contains("BuildingInQueueData")) {
                            if (data.contains("BuildingInQueueData:200")) {
//                                System.out.println("data: " +data);
                                currentTown.parseBuildingInQueueData(data);
                            } else {
                                log(Level.SEVERE, "Error! Was unable to load the data after putting a building in queue! Error log: " + event.getData());
                                restartBot = true;
                            }
                        }
                    }
                });
            }
        });
    }


    private boolean saidonce = false;

    private void loadTowns(String text) {
        ArrayList<Town> townList = new ArrayList<>();
        text = text.replaceAll("\\\\", "");
        String townData[] = text.split("player_id");

//        System.out.println("Loading a town!");

        for (String aTownData : townData) {
            if (aTownData.contains("last_wood")) {
                aTownData = aTownData.replaceAll("\"", "");
                //System.out.println("Town data: " +aTownData);
                Town town = new Town();

                int storage = 0;
                int wood = 0;
                int stone = 0;
                int iron = 0;

                String importantData[] = aTownData.split(",");
                for (String data : importantData) {
                    if (data.startsWith("name:")) {
                        town.setName(data.split(":")[1]);
                    }
                    if (data.startsWith("id:")) {
                        String id = data.split(":")[1];
                        if (isStringDigit(id)) {
                            town.setId(Integer.parseInt(id));
                        } else {
                            break;
                        }
                    }
                    if (data.startsWith("island_x")) {
                        town.setIsland_x(Integer.parseInt(data.split(":")[1]));
                    }
                    if (data.startsWith("island_y")) {
                        town.setIsland_y(Integer.parseInt(data.split(":")[1]));
                    }
                    if (data.startsWith("last_wood")) {
                        wood = Integer.parseInt(data.split(":")[1]);
                    }
                    if (data.startsWith("last_stone")) {
                        stone = Integer.parseInt(data.split(":")[1]);
                    }
                    if (data.startsWith("last_iron")) {
                        iron = Integer.parseInt(data.split(":")[1]);
                    }
                    if (data.startsWith("storage")) {
                        storage = Integer.parseInt(data.split(":")[1]);
                    }

                }

                town.setLast_wood(wood);
                town.setLast_stone(iron);
                town.setLast_stone(stone);
                town.setStorage(storage);
                town.setFullStorage(((wood == storage) && (stone == storage) && (iron == storage)));

//                String importantData[] = aTownData.split(",");
////                System.out.println("Important data: " + Arrays.toString(importantData));
//                town.setFarm_town_id(Integer.parseInt(importantData[0].replaceAll(":", "")));
//                town.setName(importantData[1].split(":")[1].replaceAll("\"", ""));
                town.setServer(server);
                town.setCsrftoken(csrfToken);
//                System.out.println("Town id: " +town.getFarm_town_id());
//                System.out.println("Town name: " +town.getName());
//                System.out.println("island_x: " + town.getFarming().getIsland_x());
//                System.out.println("island_y: " + town.getFarming().getIsland_y());
//                System.out.println("Wood: " + wood);
//                System.out.println("Stone: " + stone);
//                System.out.println("Iron: " + iron);
//                System.out.println("Storage: " + storage + " full: " + ((wood == storage) && (stone == storage) && (iron == storage)));

                townList.add(town);

                //this will find the town and update the resources after the first run.
                townAlreadyAdded(town);


                if (!saidonce) {
                    if (!townAlreadyAdded(town)) {
                        towns.add(town);
                    } else {
                        changeTownName(town.getName(), town.getId());
                    }
                }
            }
        }

        //Check to remove towns that have been lost!
        //Don't want to destroy every town if there's an error loading them!
        if (!saidonce) {
            if (townList.size() > 0) {
                //Can't modify array lists while searching through them
                ArrayList<Town> lostTowns = new ArrayList<>();
                for (Town town : towns) {
                    if (!ownTheTown(town, townList)) {
                        lostTowns.add(town);
                    }
                }

                for (Town lostTown : lostTowns) {
                    Town townToRemove = getTownFromID(lostTown.getId());
                    if (townToRemove != null) {
                        towns.remove(townToRemove);
                        log(Level.WARNING, townToRemove.getName() + " wasn't found! Bot is removing it from the list of current towns.");
                    }

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
        if (!saidonce) {
            log("Towns found: " + towns.size());
            researchedTheTown = new boolean[towns.size()];
            saidonce = true;
        }

        botFrame.setTowns(towns);
    }

    private boolean ownTheTown(Town town, ArrayList<Town> townList) {
        for (Town currentTown : townList) {
            if (town.getId() == currentTown.getId()) {
                return true;
            }
        }
        return false;
    }

    private Town getTownFromID(int townID) {
        for (Town town : towns) {
            if (town.getId() == townID) {
                return town;
            }
        }
        return null;
    }

    public static long getServerUnixTime() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        Calendar c = Calendar.getInstance(tz);
        return c.getTimeInMillis() / 1000;
    }

    public JFXPanel getJfxPanel() {
        return jfxPanel;
    }

    public static void setBotIsRunning(boolean botIsRunning) {
        GrepolisBot.botIsRunning = botIsRunning;
    }

    private boolean isStringDigit(String number) {
        for (Character c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String getCookie() {
        return cookie;
    }

    public static void setCookie(String cookie) {
        GrepolisBot.cookie = cookie;
    }
}
