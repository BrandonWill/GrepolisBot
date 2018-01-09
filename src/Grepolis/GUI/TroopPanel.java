package Grepolis.GUI;

import Grepolis.GrepolisBot;
import com.sun.javafx.application.PlatformImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.text.NumberFormat;
import java.util.Locale;

import static Grepolis.util.MyLogger.logError;


/**
 * Created by Brandon on 9/28/2016.
 */
public class TroopPanel extends JFXPanel {
    private Text troopsLabel = new Text();
    private Label swordPic = new Label();
    private Label slingerPic = new Label();
    private Label archerPic = new Label();
    private Label hoplitePic = new Label();
    private Label riderPic = new Label();
    private Label chariotPic = new Label();
    private Label catapultPic = new Label();
    private Label minotaurPic = new Label();
    private Label manticorePic = new Label();
    private Label centaurPic = new Label();
    private Label pegasusPic = new Label();
    private Label harpyPic = new Label();
    private Label medusaPic = new Label();
    private Label zyklopPic = new Label();
    private Label cerberusPic = new Label();
    private Label furyPic = new Label();
    private Label griffinPic = new Label();
    private Label calydonian_boarPic = new Label();
    private Label godsentPic = new Label();
    private Label big_transporterPic = new Label();
    private Label biremePic = new Label();
    private Label attack_shipPic = new Label();
    private Label demolition_shipPic = new Label();
    private Label small_transporterPic = new Label();
    private Label triremePic = new Label();
    private Label colonize_shipPic = new Label();
    private Label sea_monsterPic = new Label();

    private TextField swordToBuild = new TextField("0");
    private TextField slingerToBuild = new TextField("0");
    private TextField archerToBuild = new TextField("0");
    private TextField hopliteToBuild = new TextField("0");
    private TextField riderToBuild = new TextField("0");
    private TextField chariotToBuild = new TextField("0");
    private TextField catapultToBuild = new TextField("0");
    private TextField minotaurToBuild = new TextField("0");
    private TextField manticoreToBuild = new TextField("0");
    private TextField centaurToBuild = new TextField("0");
    private TextField pegasusToBuild = new TextField("0");
    private TextField harpyToBuild = new TextField("0");
    private TextField medusaToBuild = new TextField("0");
    private TextField zyklopToBuild = new TextField("0");
    private TextField cerberusToBuild = new TextField("0");
    private TextField furyToBuild = new TextField("0");
    private TextField griffinToBuild = new TextField("0");
    private TextField calydonian_boarToBuild = new TextField("0");
    private TextField godsentToBuild = new TextField("0");
    private TextField big_transporterToBuild = new TextField("0");
    private TextField biremeToBuild = new TextField("0");
    private TextField attack_shipToBuild = new TextField("0");
    private TextField demolition_shipToBuild = new TextField("0");
    private TextField small_transporterToBuild = new TextField("0");
    private TextField triremeToBuild = new TextField("0");
    private TextField colonize_shipToBuild = new TextField("0");
    private TextField sea_monsterToBuild = new TextField("0");

    public TroopPanel() {
        initComponents();
    }

    private void initComponents() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
                Scene scene = new Scene(grid, 200, 130);

                grid.setHgap(0);
                grid.setVgap(0);
                for (int i = 0; i < 27; i++) {
                    ColumnConstraints column = new ColumnConstraints(40);
                    grid.getColumnConstraints().add(column);
                }


                Font defaultFont = Font.font("Arial", 12);

                //Column 1 components
                troopsLabel = new Text("Troops (available population 0)");
                troopsLabel.setFont(defaultFont);
                swordPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "swordx40.png")));
                swordPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                swordPic.setContentDisplay(ContentDisplay.TOP);
                swordPic.setTooltip(new Tooltip("0"));

                slingerPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "slingerx40.png")));
                slingerPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                slingerPic.setContentDisplay(ContentDisplay.TOP);
                slingerPic.setTooltip(new Tooltip("0"));

                archerPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "archerx40.png")));
                archerPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                archerPic.setContentDisplay(ContentDisplay.TOP);
                archerPic.setTooltip(new Tooltip("0"));

                hoplitePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "hoplitex40.png")));
                hoplitePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                hoplitePic.setContentDisplay(ContentDisplay.TOP);
                hoplitePic.setTooltip(new Tooltip("0"));

                riderPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "riderx40.png")));
                riderPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                riderPic.setContentDisplay(ContentDisplay.TOP);
                riderPic.setTooltip(new Tooltip("0"));

                chariotPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "chariotx40.png")));
                chariotPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                chariotPic.setContentDisplay(ContentDisplay.TOP);
                chariotPic.setTooltip(new Tooltip("0"));

                catapultPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "catapultx40.png")));
                catapultPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                catapultPic.setContentDisplay(ContentDisplay.TOP);
                catapultPic.setTooltip(new Tooltip("0"));

                minotaurPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "minotaurx40.png")));
                minotaurPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                minotaurPic.setContentDisplay(ContentDisplay.TOP);
                minotaurPic.setTooltip(new Tooltip("0"));

                manticorePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "manticorex40.png")));
                manticorePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                manticorePic.setContentDisplay(ContentDisplay.TOP);
                manticorePic.setTooltip(new Tooltip("0"));

                centaurPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "centaurx40.png")));
                centaurPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                centaurPic.setContentDisplay(ContentDisplay.TOP);
                centaurPic.setTooltip(new Tooltip("0"));

                pegasusPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "pegasusx40.png")));
                pegasusPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                pegasusPic.setContentDisplay(ContentDisplay.TOP);
                pegasusPic.setTooltip(new Tooltip("0"));

                harpyPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "harpyx40.png")));
                harpyPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                harpyPic.setContentDisplay(ContentDisplay.TOP);
                harpyPic.setTooltip(new Tooltip("0"));

                medusaPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "medusax40.png")));
                medusaPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                medusaPic.setContentDisplay(ContentDisplay.TOP);
                medusaPic.setTooltip(new Tooltip("0"));

                zyklopPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "zyklopx40.png")));
                zyklopPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                zyklopPic.setContentDisplay(ContentDisplay.TOP);
                zyklopPic.setTooltip(new Tooltip("0"));

                cerberusPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "cerberusx40.png")));
                cerberusPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                cerberusPic.setContentDisplay(ContentDisplay.TOP);
                cerberusPic.setTooltip(new Tooltip("0"));

                furyPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "furyx40.png")));
                furyPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                furyPic.setContentDisplay(ContentDisplay.TOP);
                furyPic.setTooltip(new Tooltip("0"));

                griffinPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "griffinx40.png")));
                griffinPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                griffinPic.setContentDisplay(ContentDisplay.TOP);
                griffinPic.setTooltip(new Tooltip("0"));

                calydonian_boarPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "calydonian_boarx40.png")));
                calydonian_boarPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                calydonian_boarPic.setContentDisplay(ContentDisplay.TOP);
                calydonian_boarPic.setTooltip(new Tooltip("0"));

                godsentPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "godsentx40.png")));
                godsentPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                godsentPic.setContentDisplay(ContentDisplay.TOP);
                godsentPic.setTooltip(new Tooltip("0"));

                big_transporterPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "big_transporterx40.png")));
                big_transporterPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                big_transporterPic.setContentDisplay(ContentDisplay.TOP);
                big_transporterPic.setTooltip(new Tooltip("0"));

                biremePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "biremex40.png")));
                biremePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                biremePic.setContentDisplay(ContentDisplay.TOP);
                biremePic.setTooltip(new Tooltip("0"));

                attack_shipPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "attack_shipx40.png")));
                attack_shipPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                attack_shipPic.setContentDisplay(ContentDisplay.TOP);
                attack_shipPic.setTooltip(new Tooltip("0"));

                demolition_shipPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "demolition_shipx40.png")));
                demolition_shipPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                demolition_shipPic.setContentDisplay(ContentDisplay.TOP);
                demolition_shipPic.setTooltip(new Tooltip("0"));

                small_transporterPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "small_transporterx40.png")));
                small_transporterPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                small_transporterPic.setContentDisplay(ContentDisplay.TOP);
                small_transporterPic.setTooltip(new Tooltip("0"));

                triremePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "triremex40.png")));
                triremePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                triremePic.setContentDisplay(ContentDisplay.TOP);
                triremePic.setTooltip(new Tooltip("0"));

                colonize_shipPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "colonize_shipx40.png")));
                colonize_shipPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                colonize_shipPic.setContentDisplay(ContentDisplay.TOP);
                colonize_shipPic.setTooltip(new Tooltip("0"));

                sea_monsterPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Troops" + File.separator + "sea_monsterx40.png")));
                sea_monsterPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                sea_monsterPic.setContentDisplay(ContentDisplay.TOP);
                sea_monsterPic.setTooltip(new Tooltip("0"));

                swordToBuild.setFont(Font.font("Tahoma", 9));
                swordToBuild.setMinWidth(40);
                swordToBuild.setMaxWidth(40);
                swordToBuild.setAlignment(Pos.CENTER);
                swordToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        swordToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                slingerToBuild.setFont(Font.font("Tahoma", 9));
                slingerToBuild.setMinWidth(40);
                slingerToBuild.setMaxWidth(40);
                slingerToBuild.setAlignment(Pos.CENTER);
                slingerToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        slingerToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                archerToBuild.setFont(Font.font("Tahoma", 9));
                archerToBuild.setMinWidth(40);
                archerToBuild.setMaxWidth(40);
                archerToBuild.setAlignment(Pos.CENTER);
                archerToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        archerToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                hopliteToBuild.setFont(Font.font("Tahoma", 9));
                hopliteToBuild.setMinWidth(40);
                hopliteToBuild.setMaxWidth(40);
                hopliteToBuild.setAlignment(Pos.CENTER);
                hopliteToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        hopliteToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                riderToBuild.setFont(Font.font("Tahoma", 9));
                riderToBuild.setMinWidth(40);
                riderToBuild.setMaxWidth(40);
                riderToBuild.setAlignment(Pos.CENTER);
                riderToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        riderToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                chariotToBuild.setFont(Font.font("Tahoma", 9));
                chariotToBuild.setMinWidth(40);
                chariotToBuild.setMaxWidth(40);
                chariotToBuild.setAlignment(Pos.CENTER);
                chariotToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        chariotToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                catapultToBuild.setFont(Font.font("Tahoma", 9));
                catapultToBuild.setMinWidth(40);
                catapultToBuild.setMaxWidth(40);
                catapultToBuild.setAlignment(Pos.CENTER);
                catapultToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        catapultToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                minotaurToBuild.setFont(Font.font("Tahoma", 9));
                minotaurToBuild.setMinWidth(40);
                minotaurToBuild.setMaxWidth(40);
                minotaurToBuild.setAlignment(Pos.CENTER);
                minotaurToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        minotaurToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                manticoreToBuild.setFont(Font.font("Tahoma", 9));
                manticoreToBuild.setMinWidth(40);
                manticoreToBuild.setMaxWidth(40);
                manticoreToBuild.setAlignment(Pos.CENTER);
                manticoreToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        manticoreToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                centaurToBuild.setFont(Font.font("Tahoma", 9));
                centaurToBuild.setMinWidth(40);
                centaurToBuild.setMaxWidth(40);
                centaurToBuild.setAlignment(Pos.CENTER);
                centaurToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        centaurToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                pegasusToBuild.setFont(Font.font("Tahoma", 9));
                pegasusToBuild.setMinWidth(40);
                pegasusToBuild.setMaxWidth(40);
                pegasusToBuild.setAlignment(Pos.CENTER);
                pegasusToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        pegasusToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                harpyToBuild.setFont(Font.font("Tahoma", 9));
                harpyToBuild.setMinWidth(40);
                harpyToBuild.setMaxWidth(40);
                harpyToBuild.setAlignment(Pos.CENTER);
                harpyToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        harpyToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                medusaToBuild.setFont(Font.font("Tahoma", 9));
                medusaToBuild.setMinWidth(40);
                medusaToBuild.setMaxWidth(40);
                medusaToBuild.setAlignment(Pos.CENTER);
                medusaToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        medusaToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                zyklopToBuild.setFont(Font.font("Tahoma", 9));
                zyklopToBuild.setMinWidth(40);
                zyklopToBuild.setMaxWidth(40);
                zyklopToBuild.setAlignment(Pos.CENTER);
                zyklopToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        zyklopToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                cerberusToBuild.setFont(Font.font("Tahoma", 9));
                cerberusToBuild.setMinWidth(40);
                cerberusToBuild.setMaxWidth(40);
                cerberusToBuild.setAlignment(Pos.CENTER);
                cerberusToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        cerberusToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                furyToBuild.setFont(Font.font("Tahoma", 9));
                furyToBuild.setMinWidth(40);
                furyToBuild.setMaxWidth(40);
                furyToBuild.setAlignment(Pos.CENTER);
                furyToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        furyToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                griffinToBuild.setFont(Font.font("Tahoma", 9));
                griffinToBuild.setMinWidth(40);
                griffinToBuild.setMaxWidth(40);
                griffinToBuild.setAlignment(Pos.CENTER);
                griffinToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        griffinToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                calydonian_boarToBuild.setFont(Font.font("Tahoma", 9));
                calydonian_boarToBuild.setMinWidth(40);
                calydonian_boarToBuild.setMaxWidth(40);
                calydonian_boarToBuild.setAlignment(Pos.CENTER);
                calydonian_boarToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        calydonian_boarToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                godsentToBuild.setFont(Font.font("Tahoma", 9));
                godsentToBuild.setMinWidth(40);
                godsentToBuild.setMaxWidth(40);
                godsentToBuild.setAlignment(Pos.CENTER);
                godsentToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        godsentToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                big_transporterToBuild.setFont(Font.font("Tahoma", 9));
                big_transporterToBuild.setMinWidth(40);
                big_transporterToBuild.setMaxWidth(40);
                big_transporterToBuild.setAlignment(Pos.CENTER);
                big_transporterToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        big_transporterToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                biremeToBuild.setFont(Font.font("Tahoma", 9));
                biremeToBuild.setMinWidth(40);
                biremeToBuild.setMaxWidth(40);
                biremeToBuild.setAlignment(Pos.CENTER);
                biremeToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        biremeToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                attack_shipToBuild.setFont(Font.font("Tahoma", 9));
                attack_shipToBuild.setMinWidth(40);
                attack_shipToBuild.setMaxWidth(40);
                attack_shipToBuild.setAlignment(Pos.CENTER);
                attack_shipToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        attack_shipToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                demolition_shipToBuild.setFont(Font.font("Tahoma", 9));
                demolition_shipToBuild.setMinWidth(40);
                demolition_shipToBuild.setMaxWidth(40);
                demolition_shipToBuild.setAlignment(Pos.CENTER);
                demolition_shipToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        demolition_shipToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                small_transporterToBuild.setFont(Font.font("Tahoma", 9));
                small_transporterToBuild.setMinWidth(40);
                small_transporterToBuild.setMaxWidth(40);
                small_transporterToBuild.setAlignment(Pos.CENTER);
                small_transporterToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        small_transporterToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                triremeToBuild.setFont(Font.font("Tahoma", 9));
                triremeToBuild.setMinWidth(40);
                triremeToBuild.setMaxWidth(40);
                triremeToBuild.setAlignment(Pos.CENTER);
                triremeToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        triremeToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                colonize_shipToBuild.setFont(Font.font("Tahoma", 9));
                colonize_shipToBuild.setMinWidth(40);
                colonize_shipToBuild.setMaxWidth(40);
                colonize_shipToBuild.setAlignment(Pos.CENTER);
                colonize_shipToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        colonize_shipToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                sea_monsterToBuild.setFont(Font.font("Tahoma", 9));
                sea_monsterToBuild.setMinWidth(40);
                sea_monsterToBuild.setMaxWidth(40);
                sea_monsterToBuild.setAlignment(Pos.CENTER);
                sea_monsterToBuild.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        sea_monsterToBuild.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                //Row 1
                grid.add(troopsLabel, 0, 0);
//                GridPane.setMargin(automaticFestivals, new Insets(0, 0, 0, 20));
                //Row 2
                grid.add(swordPic, 0, 1);
                grid.add(slingerPic, 1, 1);
                grid.add(archerPic, 2, 1);
                grid.add(hoplitePic, 3, 1);
                grid.add(riderPic, 4, 1);
                grid.add(chariotPic, 5, 1);
                grid.add(catapultPic, 6, 1);
                grid.add(minotaurPic, 7, 1);
                grid.add(manticorePic, 8, 1);
                grid.add(centaurPic, 9, 1);
                grid.add(pegasusPic, 10, 1);
                grid.add(harpyPic, 11, 1);
                grid.add(medusaPic, 12, 1);
                grid.add(zyklopPic, 13, 1);
                grid.add(cerberusPic, 14, 1);
                grid.add(furyPic, 15, 1);
                grid.add(griffinPic, 16, 1);
                grid.add(calydonian_boarPic, 17, 1);
                grid.add(godsentPic, 18, 1);
                grid.add(big_transporterPic, 19, 1);
                grid.add(biremePic, 20, 1);
                grid.add(attack_shipPic, 21, 1);
                grid.add(demolition_shipPic, 22, 1);
                grid.add(small_transporterPic, 23, 1);
                grid.add(triremePic, 24, 1);
                grid.add(colonize_shipPic, 25, 1);
                grid.add(sea_monsterPic, 26, 1);

                //Row 3
                grid.add(swordToBuild, 0, 2);
                grid.add(slingerToBuild, 1, 2);
                grid.add(archerToBuild, 2, 2);
                grid.add(hopliteToBuild, 3, 2);
                grid.add(riderToBuild, 4, 2);
                grid.add(chariotToBuild, 5, 2);
                grid.add(catapultToBuild, 6, 2);
                grid.add(minotaurToBuild, 7, 2);
                grid.add(manticoreToBuild, 8, 2);
                grid.add(centaurToBuild, 9, 2);
                grid.add(pegasusToBuild, 10, 2);
                grid.add(harpyToBuild, 11, 2);
                grid.add(medusaToBuild, 12, 2);
                grid.add(zyklopToBuild, 13, 2);
                grid.add(cerberusToBuild, 14, 2);
                grid.add(furyToBuild, 15, 2);
                grid.add(griffinToBuild, 16, 2);
                grid.add(calydonian_boarToBuild, 17, 2);
                grid.add(godsentToBuild, 18, 2);
                grid.add(big_transporterToBuild, 19, 2);
                grid.add(biremeToBuild, 20, 2);
                grid.add(attack_shipToBuild, 21, 2);
                grid.add(demolition_shipToBuild, 22, 2);
                grid.add(small_transporterToBuild, 23, 2);
                grid.add(triremeToBuild, 24, 2);
                grid.add(colonize_shipToBuild, 25, 2);
                grid.add(sea_monsterToBuild, 26, 2);


//                grid.setStyle("-fx-border-color: black");
                grid.setPrefSize(200, 130); // Default width and height
                grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

                grid.setStyle("-fx-border-color: black;");

                setScene(scene);

            }
        });
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1100, 700));
        frame.setVisible(true);
        TroopPanel troopPanel = new TroopPanel();
        frame.add(troopPanel);
    }

    private String getImage(String imageLocation) {
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            logError(e);
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        return "file:///" + jarDir + imageLocation;
    }

    public Label getSwordPic() {
        return swordPic;
    }

    public void setSwordPic(Label swordPic) {
        this.swordPic = swordPic;
    }

    public Label getSlingerPic() {
        return slingerPic;
    }

    public void setSlingerPic(Label slingerPic) {
        this.slingerPic = slingerPic;
    }

    public Label getArcherPic() {
        return archerPic;
    }

    public void setArcherPic(Label archerPic) {
        this.archerPic = archerPic;
    }

    public Label getHoplitePic() {
        return hoplitePic;
    }

    public void setHoplitePic(Label hoplitePic) {
        this.hoplitePic = hoplitePic;
    }

    public Label getRiderPic() {
        return riderPic;
    }

    public void setRiderPic(Label riderPic) {
        this.riderPic = riderPic;
    }

    public Label getChariotPic() {
        return chariotPic;
    }

    public void setChariotPic(Label chariotPic) {
        this.chariotPic = chariotPic;
    }

    public Label getCatapultPic() {
        return catapultPic;
    }

    public void setCatapultPic(Label catapultPic) {
        this.catapultPic = catapultPic;
    }

    public Label getMinotaurPic() {
        return minotaurPic;
    }

    public void setMinotaurPic(Label minotaurPic) {
        this.minotaurPic = minotaurPic;
    }

    public Label getManticorePic() {
        return manticorePic;
    }

    public void setManticorePic(Label manticorePic) {
        this.manticorePic = manticorePic;
    }

    public Label getCentaurPic() {
        return centaurPic;
    }

    public void setCentaurPic(Label centaurPic) {
        this.centaurPic = centaurPic;
    }

    public Label getPegasusPic() {
        return pegasusPic;
    }

    public void setPegasusPic(Label pegasusPic) {
        this.pegasusPic = pegasusPic;
    }

    public Label getHarpyPic() {
        return harpyPic;
    }

    public void setHarpyPic(Label harpyPic) {
        this.harpyPic = harpyPic;
    }

    public Label getMedusaPic() {
        return medusaPic;
    }

    public void setMedusaPic(Label medusaPic) {
        this.medusaPic = medusaPic;
    }

    public Label getZyklopPic() {
        return zyklopPic;
    }

    public void setZyklopPic(Label zyklopPic) {
        this.zyklopPic = zyklopPic;
    }

    public Label getCerberusPic() {
        return cerberusPic;
    }

    public void setCerberusPic(Label cerberusPic) {
        this.cerberusPic = cerberusPic;
    }

    public Label getFuryPic() {
        return furyPic;
    }

    public void setFuryPic(Label furyPic) {
        this.furyPic = furyPic;
    }

    public Label getGriffinPic() {
        return griffinPic;
    }

    public void setGriffinPic(Label griffinPic) {
        this.griffinPic = griffinPic;
    }

    public Label getCalydonian_boarPic() {
        return calydonian_boarPic;
    }

    public void setCalydonian_boarPic(Label calydonian_boarPic) {
        this.calydonian_boarPic = calydonian_boarPic;
    }

    public Label getGodsentPic() {
        return godsentPic;
    }

    public void setGodsentPic(Label godsentPic) {
        this.godsentPic = godsentPic;
    }

    public Label getBig_transporterPic() {
        return big_transporterPic;
    }

    public void setBig_transporterPic(Label big_transporterPic) {
        this.big_transporterPic = big_transporterPic;
    }

    public Label getBiremePic() {
        return biremePic;
    }

    public void setBiremePic(Label biremePic) {
        this.biremePic = biremePic;
    }

    public Label getAttack_shipPic() {
        return attack_shipPic;
    }

    public void setAttack_shipPic(Label attack_shipPic) {
        this.attack_shipPic = attack_shipPic;
    }

    public Label getDemolition_shipPic() {
        return demolition_shipPic;
    }

    public void setDemolition_shipPic(Label demolition_shipPic) {
        this.demolition_shipPic = demolition_shipPic;
    }

    public Label getSmall_transporterPic() {
        return small_transporterPic;
    }

    public void setSmall_transporterPic(Label small_transporterPic) {
        this.small_transporterPic = small_transporterPic;
    }

    public Label getTriremePic() {
        return triremePic;
    }

    public void setTriremePic(Label triremePic) {
        this.triremePic = triremePic;
    }

    public Label getColonize_shipPic() {
        return colonize_shipPic;
    }

    public void setColonize_shipPic(Label colonize_shipPic) {
        this.colonize_shipPic = colonize_shipPic;
    }

    public Label getSea_monsterPic() {
        return sea_monsterPic;
    }

    public void setSea_monsterPic(Label sea_monsterPic) {
        this.sea_monsterPic = sea_monsterPic;
    }

    public TextField getSwordToBuild() {
        return swordToBuild;
    }

    public void setSwordToBuild(TextField swordToBuild) {
        this.swordToBuild = swordToBuild;
    }

    public TextField getSlingerToBuild() {
        return slingerToBuild;
    }

    public void setSlingerToBuild(TextField slingerToBuild) {
        this.slingerToBuild = slingerToBuild;
    }

    public TextField getArcherToBuild() {
        return archerToBuild;
    }

    public void setArcherToBuild(TextField archerToBuild) {
        this.archerToBuild = archerToBuild;
    }

    public TextField getHopliteToBuild() {
        return hopliteToBuild;
    }

    public void setHopliteToBuild(TextField hopliteToBuild) {
        this.hopliteToBuild = hopliteToBuild;
    }

    public TextField getRiderToBuild() {
        return riderToBuild;
    }

    public void setRiderToBuild(TextField riderToBuild) {
        this.riderToBuild = riderToBuild;
    }

    public TextField getChariotToBuild() {
        return chariotToBuild;
    }

    public void setChariotToBuild(TextField chariotToBuild) {
        this.chariotToBuild = chariotToBuild;
    }

    public TextField getCatapultToBuild() {
        return catapultToBuild;
    }

    public void setCatapultToBuild(TextField catapultToBuild) {
        this.catapultToBuild = catapultToBuild;
    }

    public TextField getMinotaurToBuild() {
        return minotaurToBuild;
    }

    public void setMinotaurToBuild(TextField minotaurToBuild) {
        this.minotaurToBuild = minotaurToBuild;
    }

    public TextField getManticoreToBuild() {
        return manticoreToBuild;
    }

    public void setManticoreToBuild(TextField manticoreToBuild) {
        this.manticoreToBuild = manticoreToBuild;
    }

    public TextField getCentaurToBuild() {
        return centaurToBuild;
    }

    public void setCentaurToBuild(TextField centaurToBuild) {
        this.centaurToBuild = centaurToBuild;
    }

    public TextField getPegasusToBuild() {
        return pegasusToBuild;
    }

    public void setPegasusToBuild(TextField pegasusToBuild) {
        this.pegasusToBuild = pegasusToBuild;
    }

    public TextField getHarpyToBuild() {
        return harpyToBuild;
    }

    public void setHarpyToBuild(TextField harpyToBuild) {
        this.harpyToBuild = harpyToBuild;
    }

    public TextField getMedusaToBuild() {
        return medusaToBuild;
    }

    public void setMedusaToBuild(TextField medusaToBuild) {
        this.medusaToBuild = medusaToBuild;
    }

    public TextField getZyklopToBuild() {
        return zyklopToBuild;
    }

    public void setZyklopToBuild(TextField zyklopToBuild) {
        this.zyklopToBuild = zyklopToBuild;
    }

    public TextField getCerberusToBuild() {
        return cerberusToBuild;
    }

    public void setCerberusToBuild(TextField cerberusToBuild) {
        this.cerberusToBuild = cerberusToBuild;
    }

    public TextField getFuryToBuild() {
        return furyToBuild;
    }

    public void setFuryToBuild(TextField furyToBuild) {
        this.furyToBuild = furyToBuild;
    }

    public TextField getGriffinToBuild() {
        return griffinToBuild;
    }

    public void setGriffinToBuild(TextField griffinToBuild) {
        this.griffinToBuild = griffinToBuild;
    }

    public TextField getCalydonian_boarToBuild() {
        return calydonian_boarToBuild;
    }

    public void setCalydonian_boarToBuild(TextField calydonian_boarToBuild) {
        this.calydonian_boarToBuild = calydonian_boarToBuild;
    }

    public TextField getGodsentToBuild() {
        return godsentToBuild;
    }

    public void setGodsentToBuild(TextField godsentToBuild) {
        this.godsentToBuild = godsentToBuild;
    }

    public TextField getBig_transporterToBuild() {
        return big_transporterToBuild;
    }

    public void setBig_transporterToBuild(TextField big_transporterToBuild) {
        this.big_transporterToBuild = big_transporterToBuild;
    }

    public TextField getBiremeToBuild() {
        return biremeToBuild;
    }

    public void setBiremeToBuild(TextField biremeToBuild) {
        this.biremeToBuild = biremeToBuild;
    }

    public TextField getAttack_shipToBuild() {
        return attack_shipToBuild;
    }

    public void setAttack_shipToBuild(TextField attack_shipToBuild) {
        this.attack_shipToBuild = attack_shipToBuild;
    }

    public TextField getDemolition_shipToBuild() {
        return demolition_shipToBuild;
    }

    public void setDemolition_shipToBuild(TextField demolition_shipToBuild) {
        this.demolition_shipToBuild = demolition_shipToBuild;
    }

    public TextField getSmall_transporterToBuild() {
        return small_transporterToBuild;
    }

    public void setSmall_transporterToBuild(TextField small_transporterToBuild) {
        this.small_transporterToBuild = small_transporterToBuild;
    }

    public TextField getTriremeToBuild() {
        return triremeToBuild;
    }

    public void setTriremeToBuild(TextField triremeToBuild) {
        this.triremeToBuild = triremeToBuild;
    }

    public TextField getColonize_shipToBuild() {
        return colonize_shipToBuild;
    }

    public void setColonize_shipToBuild(TextField colonize_shipToBuild) {
        this.colonize_shipToBuild = colonize_shipToBuild;
    }

    public TextField getSea_monsterToBuild() {
        return sea_monsterToBuild;
    }

    public void setSea_monsterToBuild(TextField sea_monsterToBuild) {
        this.sea_monsterToBuild = sea_monsterToBuild;
    }

    public Text getTroopsLabel() {
        return troopsLabel;
    }

    public void setTroopsLabel(Text troopsLabel) {
        this.troopsLabel = troopsLabel;
    }
}
