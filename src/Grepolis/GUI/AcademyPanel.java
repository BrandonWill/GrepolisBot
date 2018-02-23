package Grepolis.GUI;

import Grepolis.AcademyResearch;
import Grepolis.Town;
import Grepolis.util.Image;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static Grepolis.util.Image.getImage;

public class AcademyPanel extends JFXPanel {

    //Row 1
    private Label slingerResearchPic = new Label();
    private Label hopliteResearchPic = new Label();
    private Label espionageResearchPic = new Label();
    private Label riderResearchPic = new Label();
    private Label biremeResearchPic = new Label();
    private Label chariotResearchPic = new Label();
    private Label demolition_shipResearchPic = new Label();
    private Label small_transporterResearchPic = new Label();
    private Label triremeResearchPic = new Label();
    private Label ramResearchPic = new Label();
    private Label stone_stormResearchPic = new Label();
    private Label combat_experienceResearchPic = new Label();
    //Row 2
    private Label archerResearchPic = new Label();
    private Label meteorologyResearchPic = new Label();
    private Label bootyResearchPic = new Label();
    private Label architectureResearchPic = new Label();
    private Label building_craneResearchPic = new Label();
    private Label attack_shipResearchPic = new Label();
    private Label catapultResearchPic = new Label();
    private Label plowResearchPic = new Label();
    private Label phalanxResearchPic = new Label();
    private Label cartographyResearchPic = new Label();
    private Label temple_lootingResearchPic = new Label();
    private Label strong_wineResearchPic = new Label();

    //row 3
    private Label town_guardResearchPic = new Label();
    private Label potteryResearchPic = new Label();
    private Label instructorResearchPic = new Label();
    private Label shipwrightResearchPic = new Label();
    private Label conscriptionResearchPic = new Label();
    private Label cryptographyResearchPic = new Label();
    private Label berthResearchPic = new Label();
    private Label breachResearchPic = new Label();
    private Label take_overResearchPic = new Label();
    private Label divine_selectionResearchPic = new Label();
    private Label set_sailResearchPic = new Label();

    //Row 4
    private Label colonize_shipResearchPic = new Label();
    private Label democracyResearchPic = new Label();
    private Label mathematicsResearchPic = new Label();


    public AcademyPanel() {
        initComponents();
    }

    private void initComponents() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
                Scene scene = new Scene(grid, 550, 130);

                grid.setHgap(5);
                grid.setVgap(6);

                Font defaultFont = Font.font("Helvetica", 8);

                ImageView tempSlingerResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png"));
                tempSlingerResearchPic = Image.imageToGrayscale(tempSlingerResearchPic);
                slingerResearchPic = new Label("", tempSlingerResearchPic);
                slingerResearchPic.setContentDisplay(ContentDisplay.TOP);
                slingerResearchPic.setFont(defaultFont);
                slingerResearchPic.setTextFill(Color.RED);
                slingerResearchPic.setText("Researched");
                slingerResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.slinger);
                    }
                });

                ImageView tempHopliteResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png"));
                tempHopliteResearchPic = Image.imageToGrayscale(tempHopliteResearchPic);
                hopliteResearchPic = new Label("", tempHopliteResearchPic);
                hopliteResearchPic.setContentDisplay(ContentDisplay.TOP);
                hopliteResearchPic.setFont(defaultFont);
                hopliteResearchPic.setTextFill(Color.RED);
                hopliteResearchPic.setText("Researched");
                hopliteResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.hoplite);
                    }
                });

                ImageView tempEspionageResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png"));
                tempEspionageResearchPic = Image.imageToGrayscale(tempEspionageResearchPic);
                espionageResearchPic = new Label("", tempEspionageResearchPic);
                espionageResearchPic.setContentDisplay(ContentDisplay.TOP);
                espionageResearchPic.setFont(defaultFont);
                espionageResearchPic.setTextFill(Color.RED);
                espionageResearchPic.setText("Researched");
                espionageResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.espionage);
                    }
                });

                ImageView tempRiderResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png"));
                tempRiderResearchPic = Image.imageToGrayscale(tempRiderResearchPic);
                riderResearchPic = new Label("", tempRiderResearchPic);
                riderResearchPic.setContentDisplay(ContentDisplay.TOP);
                riderResearchPic.setFont(defaultFont);
                riderResearchPic.setTextFill(Color.RED);
                riderResearchPic.setText("Researched");
                riderResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.rider);
                    }
                });

                ImageView tempBiremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png"));
                tempBiremeResearchPic = Image.imageToGrayscale(tempBiremeResearchPic);
                biremeResearchPic = new Label("", tempBiremeResearchPic);
                biremeResearchPic.setContentDisplay(ContentDisplay.TOP);
                biremeResearchPic.setFont(defaultFont);
                biremeResearchPic.setTextFill(Color.RED);
                biremeResearchPic.setText("Researched");
                biremeResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.bireme);
                    }
                });

                ImageView tempChariotResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png"));
                tempChariotResearchPic = Image.imageToGrayscale(tempChariotResearchPic);
                chariotResearchPic = new Label("", tempChariotResearchPic);
                chariotResearchPic.setContentDisplay(ContentDisplay.TOP);
                chariotResearchPic.setFont(defaultFont);
                chariotResearchPic.setTextFill(Color.RED);
                chariotResearchPic.setText("Researched");
                chariotResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.chariot);
                    }
                });

                ImageView tempDemolitionShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png"));
                tempDemolitionShipResearchPic = Image.imageToGrayscale(tempDemolitionShipResearchPic);
                demolition_shipResearchPic = new Label("", tempDemolitionShipResearchPic);
                demolition_shipResearchPic.setContentDisplay(ContentDisplay.TOP);
                demolition_shipResearchPic.setFont(defaultFont);
                demolition_shipResearchPic.setTextFill(Color.RED);
                demolition_shipResearchPic.setText("Researched");
                demolition_shipResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.demolition_ship);
                    }
                });

                ImageView tempSmallTransporterResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png"));
                tempSmallTransporterResearchPic = Image.imageToGrayscale(tempSmallTransporterResearchPic);
                small_transporterResearchPic = new Label("", tempSmallTransporterResearchPic);
                small_transporterResearchPic.setContentDisplay(ContentDisplay.TOP);
                small_transporterResearchPic.setFont(defaultFont);
                small_transporterResearchPic.setTextFill(Color.RED);
                small_transporterResearchPic.setText("Researched");
                small_transporterResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.small_transporter);
                    }
                });

                ImageView tempTriremeResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png"));
                tempTriremeResearchPic = Image.imageToGrayscale(tempTriremeResearchPic);
                triremeResearchPic = new Label("", tempTriremeResearchPic);
                triremeResearchPic.setContentDisplay(ContentDisplay.TOP);
                triremeResearchPic.setFont(defaultFont);
                triremeResearchPic.setTextFill(Color.RED);
                triremeResearchPic.setText("Researched");
                triremeResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.trireme);
                    }
                });

                ImageView tempRamResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png"));
                tempRamResearchPic = Image.imageToGrayscale(tempRamResearchPic);
                ramResearchPic = new Label("", tempRamResearchPic);
                ramResearchPic.setContentDisplay(ContentDisplay.TOP);
                ramResearchPic.setFont(defaultFont);
                ramResearchPic.setTextFill(Color.RED);
                ramResearchPic.setText("Researched");
                ramResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.ram);
                    }
                });

                ImageView tempStoneHailResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_hail.png"));
                tempStoneHailResearchPic = Image.imageToGrayscale(tempStoneHailResearchPic);
                stone_stormResearchPic = new Label("", tempStoneHailResearchPic);
                stone_stormResearchPic.setContentDisplay(ContentDisplay.TOP);
                stone_stormResearchPic.setFont(defaultFont);
                stone_stormResearchPic.setTextFill(Color.RED);
                stone_stormResearchPic.setText("Researched");
                stone_stormResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.stone_storm);
                    }
                });

                ImageView tempCombatExperienceResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png"));
                tempCombatExperienceResearchPic = Image.imageToGrayscale(tempCombatExperienceResearchPic);
                combat_experienceResearchPic = new Label("", tempCombatExperienceResearchPic);
                combat_experienceResearchPic.setContentDisplay(ContentDisplay.TOP);
                combat_experienceResearchPic.setFont(defaultFont);
                combat_experienceResearchPic.setTextFill(Color.RED);
                combat_experienceResearchPic.setText("Researched");
                combat_experienceResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.combat_experience);
                    }
                });

                ImageView tempArcherResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png"));
                tempArcherResearchPic = Image.imageToGrayscale(tempArcherResearchPic);
                archerResearchPic = new Label("", tempArcherResearchPic);
                archerResearchPic.setContentDisplay(ContentDisplay.TOP);
                archerResearchPic.setFont(defaultFont);
                archerResearchPic.setTextFill(Color.RED);
                archerResearchPic.setText("Researched");
                archerResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.archer);
                    }
                });

                ImageView tempMeteorologyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png"));
                tempMeteorologyResearchPic = Image.imageToGrayscale(tempMeteorologyResearchPic);
                meteorologyResearchPic = new Label("", tempMeteorologyResearchPic);
                meteorologyResearchPic.setContentDisplay(ContentDisplay.TOP);
                meteorologyResearchPic.setFont(defaultFont);
                meteorologyResearchPic.setTextFill(Color.RED);
                meteorologyResearchPic.setText("Researched");
                meteorologyResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.meteorology);
                    }
                });

                ImageView tempBootyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png"));
                tempBootyResearchPic = Image.imageToGrayscale(tempBootyResearchPic);
                bootyResearchPic = new Label("", tempBootyResearchPic);
                bootyResearchPic.setContentDisplay(ContentDisplay.TOP);
                bootyResearchPic.setFont(defaultFont);
                bootyResearchPic.setTextFill(Color.RED);
                bootyResearchPic.setText("Researched");
                bootyResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.booty);
                    }
                });

                ImageView tempArchitectureResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png"));
                tempArchitectureResearchPic = Image.imageToGrayscale(tempArchitectureResearchPic);
                architectureResearchPic = new Label("", tempArchitectureResearchPic);
                architectureResearchPic.setContentDisplay(ContentDisplay.TOP);
                architectureResearchPic.setFont(defaultFont);
                architectureResearchPic.setTextFill(Color.RED);
                architectureResearchPic.setText("Researched");
                architectureResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.architecture);
                    }
                });

                ImageView tempBuilding_CraneResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png"));
                tempBuilding_CraneResearchPic = Image.imageToGrayscale(tempBuilding_CraneResearchPic);
                building_craneResearchPic = new Label("", tempBuilding_CraneResearchPic);
                building_craneResearchPic.setContentDisplay(ContentDisplay.TOP);
                building_craneResearchPic.setFont(defaultFont);
                building_craneResearchPic.setTextFill(Color.RED);
                building_craneResearchPic.setText("Researched");
                building_craneResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.building_crane);
                    }
                });

                ImageView tempAttack_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png"));
                tempAttack_ShipResearchPic = Image.imageToGrayscale(tempAttack_ShipResearchPic);
                attack_shipResearchPic = new Label("", tempAttack_ShipResearchPic);
                attack_shipResearchPic.setContentDisplay(ContentDisplay.TOP);
                attack_shipResearchPic.setFont(defaultFont);
                attack_shipResearchPic.setTextFill(Color.RED);
                attack_shipResearchPic.setText("Researched");
                attack_shipResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.attack_ship);
                    }
                });

                ImageView tempCatapultResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png"));
                tempCatapultResearchPic = Image.imageToGrayscale(tempCatapultResearchPic);
                catapultResearchPic = new Label("", tempCatapultResearchPic);
                catapultResearchPic.setContentDisplay(ContentDisplay.TOP);
                catapultResearchPic.setFont(defaultFont);
                catapultResearchPic.setTextFill(Color.RED);
                catapultResearchPic.setText("Researched");
                catapultResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.catapult);
                    }
                });

                ImageView tempPlowResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png"));
                tempPlowResearchPic = Image.imageToGrayscale(tempPlowResearchPic);
                plowResearchPic = new Label("", tempPlowResearchPic);
                plowResearchPic.setContentDisplay(ContentDisplay.TOP);
                plowResearchPic.setFont(defaultFont);
                plowResearchPic.setTextFill(Color.RED);
                plowResearchPic.setText("Researched");
                plowResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.plow);
                    }
                });

                ImageView tempPhalanxResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png"));
                tempPhalanxResearchPic = Image.imageToGrayscale(tempPhalanxResearchPic);
                phalanxResearchPic = new Label("", tempPhalanxResearchPic);
                phalanxResearchPic.setContentDisplay(ContentDisplay.TOP);
                phalanxResearchPic.setFont(defaultFont);
                phalanxResearchPic.setTextFill(Color.RED);
                phalanxResearchPic.setText("Researched");
                phalanxResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.phalanx);
                    }
                });

                ImageView tempCartographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png"));
                tempCartographyResearchPic = Image.imageToGrayscale(tempCartographyResearchPic);
                cartographyResearchPic = new Label("", tempCartographyResearchPic);
                cartographyResearchPic.setContentDisplay(ContentDisplay.TOP);
                cartographyResearchPic.setFont(defaultFont);
                cartographyResearchPic.setTextFill(Color.RED);
                cartographyResearchPic.setText("Researched");
                cartographyResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.cartography);
                    }
                });

                ImageView tempTemple_LootingResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png"));
                tempTemple_LootingResearchPic = Image.imageToGrayscale(tempTemple_LootingResearchPic);
                temple_lootingResearchPic = new Label("", tempTemple_LootingResearchPic);
                temple_lootingResearchPic.setContentDisplay(ContentDisplay.TOP);
                temple_lootingResearchPic.setFont(defaultFont);
                temple_lootingResearchPic.setTextFill(Color.RED);
                temple_lootingResearchPic.setText("Researched");
                temple_lootingResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.temple_looting);
                    }
                });

                ImageView tempStrong_WineResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png"));
                tempStrong_WineResearchPic = Image.imageToGrayscale(tempStrong_WineResearchPic);
                strong_wineResearchPic = new Label("", tempStrong_WineResearchPic);
                strong_wineResearchPic.setContentDisplay(ContentDisplay.TOP);
                strong_wineResearchPic.setFont(defaultFont);
                strong_wineResearchPic.setTextFill(Color.RED);
                strong_wineResearchPic.setText("Researched");
                strong_wineResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.strong_wine);
                    }
                });

                ImageView tempTown_GuardResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png"));
                tempTown_GuardResearchPic = Image.imageToGrayscale(tempTown_GuardResearchPic);
                town_guardResearchPic = new Label("", tempTown_GuardResearchPic);
                town_guardResearchPic.setContentDisplay(ContentDisplay.TOP);
                town_guardResearchPic.setFont(defaultFont);
                town_guardResearchPic.setTextFill(Color.RED);
                town_guardResearchPic.setText("Researched");
                town_guardResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.town_guard);
                    }
                });

                ImageView tempPotteryResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png"));
                tempPotteryResearchPic = Image.imageToGrayscale(tempPotteryResearchPic);
                potteryResearchPic = new Label("", tempPotteryResearchPic);
                potteryResearchPic.setContentDisplay(ContentDisplay.TOP);
                potteryResearchPic.setFont(defaultFont);
                potteryResearchPic.setTextFill(Color.RED);
                potteryResearchPic.setText("Researched");
                potteryResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.pottery);
                    }
                });

                ImageView tempInstructorResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png"));
                tempInstructorResearchPic = Image.imageToGrayscale(tempInstructorResearchPic);
                instructorResearchPic = new Label("", tempInstructorResearchPic);
                instructorResearchPic.setContentDisplay(ContentDisplay.TOP);
                instructorResearchPic.setFont(defaultFont);
                instructorResearchPic.setTextFill(Color.RED);
                instructorResearchPic.setText("Researched");
                instructorResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.instructor);
                    }
                });

                ImageView tempShipwrightResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png"));
                tempShipwrightResearchPic = Image.imageToGrayscale(tempShipwrightResearchPic);
                shipwrightResearchPic = new Label("", tempShipwrightResearchPic);
                shipwrightResearchPic.setContentDisplay(ContentDisplay.TOP);
                shipwrightResearchPic.setFont(defaultFont);
                shipwrightResearchPic.setTextFill(Color.RED);
                shipwrightResearchPic.setText("Researched");
                shipwrightResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.shipwright);
                    }
                });

                ImageView tempConscriptionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png"));
                tempConscriptionResearchPic = Image.imageToGrayscale(tempConscriptionResearchPic);
                conscriptionResearchPic = new Label("", tempConscriptionResearchPic);
                conscriptionResearchPic.setContentDisplay(ContentDisplay.TOP);
                conscriptionResearchPic.setFont(defaultFont);
                conscriptionResearchPic.setTextFill(Color.RED);
                conscriptionResearchPic.setText("Researched");
                conscriptionResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.conscription);
                    }
                });

                ImageView tempCryptographyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png"));
                tempCryptographyResearchPic = Image.imageToGrayscale(tempCryptographyResearchPic);
                cryptographyResearchPic = new Label("", tempCryptographyResearchPic);
                cryptographyResearchPic.setContentDisplay(ContentDisplay.TOP);
                cryptographyResearchPic.setFont(defaultFont);
                cryptographyResearchPic.setTextFill(Color.RED);
                cryptographyResearchPic.setText("Researched");
                cryptographyResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.cryptography);
                    }
                });

                ImageView tempBerthResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png"));
                tempBerthResearchPic = Image.imageToGrayscale(tempBerthResearchPic);
                berthResearchPic = new Label("", tempBerthResearchPic);
                berthResearchPic.setContentDisplay(ContentDisplay.TOP);
                berthResearchPic.setFont(defaultFont);
                berthResearchPic.setTextFill(Color.RED);
                berthResearchPic.setText("Researched");
                berthResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.berth);
                    }
                });

                ImageView tempBreachResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png"));
                tempBreachResearchPic = Image.imageToGrayscale(tempBreachResearchPic);
                breachResearchPic = new Label("", tempBreachResearchPic);
                breachResearchPic.setContentDisplay(ContentDisplay.TOP);
                breachResearchPic.setFont(defaultFont);
                breachResearchPic.setTextFill(Color.RED);
                breachResearchPic.setText("Researched");
                breachResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.breach);
                    }
                });

                ImageView tempTake_OverResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png"));
                tempTake_OverResearchPic = Image.imageToGrayscale(tempTake_OverResearchPic);
                take_overResearchPic = new Label("", tempTake_OverResearchPic);
                take_overResearchPic.setContentDisplay(ContentDisplay.TOP);
                take_overResearchPic.setFont(defaultFont);
                take_overResearchPic.setTextFill(Color.RED);
                take_overResearchPic.setText("Researched");
                take_overResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.take_over);
                    }
                });

                ImageView tempDivine_SelectionResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png"));
                tempDivine_SelectionResearchPic = Image.imageToGrayscale(tempDivine_SelectionResearchPic);
                divine_selectionResearchPic = new Label("", tempDivine_SelectionResearchPic);
                divine_selectionResearchPic.setContentDisplay(ContentDisplay.TOP);
                divine_selectionResearchPic.setFont(defaultFont);
                divine_selectionResearchPic.setTextFill(Color.RED);
                divine_selectionResearchPic.setText("Researched");
                divine_selectionResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.divine_selection);
                    }
                });

                ImageView tempSet_SailResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png"));
                tempSet_SailResearchPic = Image.imageToGrayscale(tempSet_SailResearchPic);
                set_sailResearchPic = new Label("", tempSet_SailResearchPic);
                set_sailResearchPic.setContentDisplay(ContentDisplay.TOP);
                set_sailResearchPic.setFont(defaultFont);
                set_sailResearchPic.setTextFill(Color.RED);
                set_sailResearchPic.setText("Researched");
                set_sailResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.set_sail);
                    }
                });

                ImageView tempColonize_ShipResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png"));
                tempColonize_ShipResearchPic = Image.imageToGrayscale(tempColonize_ShipResearchPic);
                colonize_shipResearchPic = new Label("", tempColonize_ShipResearchPic);
                colonize_shipResearchPic.setContentDisplay(ContentDisplay.TOP);
                colonize_shipResearchPic.setFont(defaultFont);
                colonize_shipResearchPic.setTextFill(Color.RED);
                colonize_shipResearchPic.setText("Researched");
                colonize_shipResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.colonize_ship);
                    }
                });

                ImageView tempDemocracyResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png"));
                tempDemocracyResearchPic = Image.imageToGrayscale(tempDemocracyResearchPic);
                democracyResearchPic = new Label("", tempDemocracyResearchPic);
                democracyResearchPic.setContentDisplay(ContentDisplay.TOP);
                democracyResearchPic.setFont(defaultFont);
                democracyResearchPic.setTextFill(Color.RED);
                democracyResearchPic.setText("Researched");
                democracyResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.democracy);
                    }
                });

                ImageView tempMathematicsResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png"));
                tempMathematicsResearchPic = Image.imageToGrayscale(tempMathematicsResearchPic);
                mathematicsResearchPic = new Label("", tempMathematicsResearchPic);
                mathematicsResearchPic.setContentDisplay(ContentDisplay.TOP);
                mathematicsResearchPic.setFont(defaultFont);
                mathematicsResearchPic.setTextFill(Color.RED);
                mathematicsResearchPic.setText("Researched");
                mathematicsResearchPic.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateResearchValue(AcademyResearch.ResearchType.mathematics);
                    }
                });

                //row 0 (gap)
                Label academyText = new Label("Academy");
                grid.add(academyText, 0 ,0);


                //row 1
                grid.add(new Label("1    *    *"), 0, 1);
                grid.add(new Label("4    *    *"), 1, 1);
                grid.add(new Label("7    *    *"), 2, 1);
                grid.add(new Label("10   *    *"), 3, 1);
                grid.add(new Label("13   *    *"), 4, 1);
                grid.add(new Label("16   *    *"), 5, 1);
                grid.add(new Label("19   *    *"), 6, 1);
                grid.add(new Label("22    *    *"), 7, 1);
                grid.add(new Label("25    *    *"), 8, 1);
                grid.add(new Label("28    *    *"), 9, 1);
                grid.add(new Label("31    *    *"), 10, 1);
                grid.add(new Label("34   *    *"), 11, 1);

                //row 2
                grid.add(slingerResearchPic, 0, 2);
                grid.add(hopliteResearchPic, 1, 2);
                grid.add(espionageResearchPic, 2, 2);
                grid.add(riderResearchPic, 3, 2);
                grid.add(biremeResearchPic, 4, 2);
                grid.add(chariotResearchPic, 5, 2);
                grid.add(demolition_shipResearchPic, 6, 2);
                grid.add(small_transporterResearchPic, 7, 2);
                grid.add(triremeResearchPic, 8, 2);
                grid.add(ramResearchPic, 9, 2);
                grid.add(stone_stormResearchPic, 10, 2);
                grid.add(combat_experienceResearchPic, 11, 2);
                //row 3
                grid.add(archerResearchPic, 0, 3);
                grid.add(meteorologyResearchPic, 1, 3);
                grid.add(bootyResearchPic, 2, 3);
                grid.add(architectureResearchPic, 3, 3);
                grid.add(building_craneResearchPic, 4, 3);
                grid.add(attack_shipResearchPic, 5, 3);
                grid.add(catapultResearchPic, 6, 3);
                grid.add(plowResearchPic, 7, 3);
                grid.add(phalanxResearchPic, 8, 3);
                grid.add(cartographyResearchPic, 9, 3);
                grid.add(temple_lootingResearchPic, 10, 3);
                grid.add(strong_wineResearchPic, 11, 3);
                //row 4
                grid.add(town_guardResearchPic, 0, 4);

                grid.add(potteryResearchPic, 2, 4);
                grid.add(instructorResearchPic, 3, 4);
                grid.add(shipwrightResearchPic, 4, 4);
                grid.add(conscriptionResearchPic, 5, 4);
                grid.add(cryptographyResearchPic, 6, 4);
                grid.add(berthResearchPic, 7, 4);
                grid.add(breachResearchPic, 8, 4);
                grid.add(take_overResearchPic, 9, 4);
                grid.add(divine_selectionResearchPic, 10, 4);
                grid.add(set_sailResearchPic, 11, 4);
                //row 5

                grid.add(colonize_shipResearchPic, 4, 5);

                grid.add(democracyResearchPic, 6, 5);

                grid.add(mathematicsResearchPic, 8, 5);

                grid.setPrefSize(200, 130); // Default width and height
//                grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

                grid.setStyle("-fx-border-color: black;");

                setScene(scene);
            }
        });
    }

    private void updateResearchValue(AcademyResearch.ResearchType researchType) {
        Town currentTown = QueuePanel.getCurrentSelectedTown();
        AcademyResearch currentResearch = currentTown.getAcademy().getResearch(researchType);
        boolean shouldResearch = !currentTown.getAcademy().getResearch(researchType).shouldResearch();
//        System.out.println("Before -- Research type: " + researchType.name() + " value: " +currentTown.getAcademy().getResearch(researchType).shouldResearch());
        currentResearch.setShouldResearch(shouldResearch);
//        System.out.println("After -- Research type: " + researchType.name() + " value: " +currentTown.getAcademy().getResearch(researchType).shouldResearch());

        switch (researchType) {
            case slinger:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        slingerResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png")));
                    } else {
                        slingerResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "slinger.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        slingerResearchPic.setText("Researched");
                        slingerResearchPic.setTextFill(Color.GREEN);
                    } else {
                        slingerResearchPic.setText("Researched");
                        slingerResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case hoplite:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        hopliteResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png")));
                    } else {
                        hopliteResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "hoplite.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        hopliteResearchPic.setText("Researched");
                        hopliteResearchPic.setTextFill(Color.GREEN);
                    } else {
                        hopliteResearchPic.setText("Researched");
                        hopliteResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case espionage:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        espionageResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png")));
                    } else {
                        espionageResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "espionage.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        espionageResearchPic.setText("Researched");
                        espionageResearchPic.setTextFill(Color.GREEN);
                    } else {
                        espionageResearchPic.setText("Researched");
                        espionageResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case rider:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        riderResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png")));
                    } else {
                        riderResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "rider.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        riderResearchPic.setText("Researched");
                        riderResearchPic.setTextFill(Color.GREEN);
                    } else {
                        riderResearchPic.setText("Researched");
                        riderResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case bireme:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        biremeResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png")));
                    } else {
                        biremeResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "bireme.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        biremeResearchPic.setText("Researched");
                        biremeResearchPic.setTextFill(Color.GREEN);
                    } else {
                        biremeResearchPic.setText("Researched");
                        biremeResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case chariot:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        chariotResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png")));
                    } else {
                        chariotResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "chariot.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        chariotResearchPic.setText("Researched");
                        chariotResearchPic.setTextFill(Color.GREEN);
                    } else {
                        chariotResearchPic.setText("Researched");
                        chariotResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case demolition_ship:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        demolition_shipResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png")));
                    } else {
                        demolition_shipResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "demolition_ship.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        demolition_shipResearchPic.setText("Researched");
                        demolition_shipResearchPic.setTextFill(Color.GREEN);
                    } else {
                        demolition_shipResearchPic.setText("Researched");
                        demolition_shipResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case small_transporter:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        small_transporterResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png")));
                    } else {
                        small_transporterResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "small_transporter.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        small_transporterResearchPic.setText("Researched");
                        small_transporterResearchPic.setTextFill(Color.GREEN);
                    } else {
                        small_transporterResearchPic.setText("Researched");
                        small_transporterResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case trireme:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        triremeResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png")));
                    } else {
                        triremeResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "trireme.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        triremeResearchPic.setText("Researched");
                        triremeResearchPic.setTextFill(Color.GREEN);
                    } else {
                        triremeResearchPic.setText("Researched");
                        triremeResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case ram:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        ramResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png")));
                    } else {
                        ramResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "ram.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        ramResearchPic.setText("Researched");
                        ramResearchPic.setTextFill(Color.GREEN);
                    } else {
                        ramResearchPic.setText("Researched");
                        ramResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case stone_storm:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        stone_stormResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_storm.png")));
                    } else {
                        stone_stormResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "stone_storm.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        stone_stormResearchPic.setText("Researched");
                        stone_stormResearchPic.setTextFill(Color.GREEN);
                    } else {
                        stone_stormResearchPic.setText("Researched");
                        stone_stormResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case combat_experience:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        combat_experienceResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png")));
                    } else {
                        combat_experienceResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "combat_experience.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        combat_experienceResearchPic.setText("Researched");
                        combat_experienceResearchPic.setTextFill(Color.GREEN);
                    } else {
                        combat_experienceResearchPic.setText("Researched");
                        combat_experienceResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case archer:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        archerResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png")));
                    } else {
                        archerResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "archer.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        archerResearchPic.setText("Researched");
                        archerResearchPic.setTextFill(Color.GREEN);
                    } else {
                        archerResearchPic.setText("Researched");
                        archerResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case meteorology:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        meteorologyResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png")));
                    } else {
                        meteorologyResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "meteorology.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        meteorologyResearchPic.setText("Researched");
                        meteorologyResearchPic.setTextFill(Color.GREEN);
                    } else {
                        meteorologyResearchPic.setText("Researched");
                        meteorologyResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case booty:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        bootyResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png")));
                    } else {
                        bootyResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "booty.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        bootyResearchPic.setText("Researched");
                        bootyResearchPic.setTextFill(Color.GREEN);
                    } else {
                        bootyResearchPic.setText("Researched");
                        bootyResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case architecture:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        architectureResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png")));
                    } else {
                        architectureResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "architecture.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        architectureResearchPic.setText("Researched");
                        architectureResearchPic.setTextFill(Color.GREEN);
                    } else {
                        architectureResearchPic.setText("Researched");
                        architectureResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case building_crane:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        building_craneResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png")));
                    } else {
                        building_craneResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "building_crane.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        building_craneResearchPic.setText("Researched");
                        building_craneResearchPic.setTextFill(Color.GREEN);
                    } else {
                        building_craneResearchPic.setText("Researched");
                        building_craneResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case attack_ship:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        attack_shipResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png")));
                    } else {
                        attack_shipResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "attack_ship.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        attack_shipResearchPic.setText("Researched");
                        attack_shipResearchPic.setTextFill(Color.GREEN);
                    } else {
                        attack_shipResearchPic.setText("Researched");
                        attack_shipResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case catapult:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        catapultResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png")));
                    } else {
                        catapultResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "catapult.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        catapultResearchPic.setText("Researched");
                        catapultResearchPic.setTextFill(Color.GREEN);
                    } else {
                        catapultResearchPic.setText("Researched");
                        catapultResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case plow:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        plowResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png")));
                    } else {
                        plowResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "plow.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        plowResearchPic.setText("Researched");
                        plowResearchPic.setTextFill(Color.GREEN);
                    } else {
                        plowResearchPic.setText("Researched");
                        plowResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case phalanx:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        phalanxResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png")));
                    } else {
                        phalanxResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "phalanx.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        phalanxResearchPic.setText("Researched");
                        phalanxResearchPic.setTextFill(Color.GREEN);
                    } else {
                        phalanxResearchPic.setText("Researched");
                        phalanxResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case cartography:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        cartographyResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png")));
                    } else {
                        cartographyResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cartography.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        cartographyResearchPic.setText("Researched");
                        cartographyResearchPic.setTextFill(Color.GREEN);
                    } else {
                        cartographyResearchPic.setText("Researched");
                        cartographyResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case temple_looting:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        temple_lootingResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png")));
                    } else {
                        temple_lootingResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "temple_looting.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        temple_lootingResearchPic.setText("Researched");
                        temple_lootingResearchPic.setTextFill(Color.GREEN);
                    } else {
                        temple_lootingResearchPic.setText("Researched");
                        temple_lootingResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case strong_wine:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        strong_wineResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png")));
                    } else {
                        strong_wineResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "strong_wine.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        strong_wineResearchPic.setText("Researched");
                        strong_wineResearchPic.setTextFill(Color.GREEN);
                    } else {
                        strong_wineResearchPic.setText("Researched");
                        strong_wineResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case town_guard:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        town_guardResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png")));
                    } else {
                        town_guardResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "town_guard.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        town_guardResearchPic.setText("Researched");
                        town_guardResearchPic.setTextFill(Color.GREEN);
                    } else {
                        town_guardResearchPic.setText("Researched");
                        town_guardResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case pottery:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        potteryResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png")));
                    } else {
                        potteryResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "pottery.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        potteryResearchPic.setText("Researched");
                        potteryResearchPic.setTextFill(Color.GREEN);
                    } else {
                        potteryResearchPic.setText("Researched");
                        potteryResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case instructor:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        instructorResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png")));
                    } else {
                        instructorResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "instructor.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        instructorResearchPic.setText("Researched");
                        instructorResearchPic.setTextFill(Color.GREEN);
                    } else {
                        instructorResearchPic.setText("Researched");
                        instructorResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case shipwright:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        shipwrightResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png")));
                    } else {
                        shipwrightResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "shipwright.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        shipwrightResearchPic.setText("Researched");
                        shipwrightResearchPic.setTextFill(Color.GREEN);
                    } else {
                        shipwrightResearchPic.setText("Researched");
                        shipwrightResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case conscription:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        conscriptionResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png")));
                    } else {
                        conscriptionResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "conscription.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        conscriptionResearchPic.setText("Researched");
                        conscriptionResearchPic.setTextFill(Color.GREEN);
                    } else {
                        conscriptionResearchPic.setText("Researched");
                        conscriptionResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case cryptography:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        cryptographyResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png")));
                    } else {
                        cryptographyResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "cryptography.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        cryptographyResearchPic.setText("Researched");
                        cryptographyResearchPic.setTextFill(Color.GREEN);
                    } else {
                        cryptographyResearchPic.setText("Researched");
                        cryptographyResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case berth:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        berthResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png")));
                    } else {
                        berthResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "berth.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        berthResearchPic.setText("Researched");
                        berthResearchPic.setTextFill(Color.GREEN);
                    } else {
                        berthResearchPic.setText("Researched");
                        berthResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case breach:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        breachResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png")));
                    } else {
                        breachResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "breach.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        breachResearchPic.setText("Researched");
                        breachResearchPic.setTextFill(Color.GREEN);
                    } else {
                        breachResearchPic.setText("Researched");
                        breachResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case take_over:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        take_overResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png")));
                    } else {
                        take_overResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "take_over.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        take_overResearchPic.setText("Researched");
                        take_overResearchPic.setTextFill(Color.GREEN);
                    } else {
                        take_overResearchPic.setText("Researched");
                        take_overResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case divine_selection:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        divine_selectionResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png")));
                    } else {
                        divine_selectionResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "divine_selection.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        divine_selectionResearchPic.setText("Researched");
                        divine_selectionResearchPic.setTextFill(Color.GREEN);
                    } else {
                        divine_selectionResearchPic.setText("Researched");
                        divine_selectionResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case set_sail:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        set_sailResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png")));
                    } else {
                        set_sailResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "set_sail.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        set_sailResearchPic.setText("Researched");
                        set_sailResearchPic.setTextFill(Color.GREEN);
                    } else {
                        set_sailResearchPic.setText("Researched");
                        set_sailResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case colonize_ship:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        colonize_shipResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png")));
                    } else {
                        colonize_shipResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "colonize_ship.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        colonize_shipResearchPic.setText("Researched");
                        colonize_shipResearchPic.setTextFill(Color.GREEN);
                    } else {
                        colonize_shipResearchPic.setText("Researched");
                        colonize_shipResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case democracy:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        democracyResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png")));
                    } else {
                        democracyResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "democracy.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        democracyResearchPic.setText("Researched");
                        democracyResearchPic.setTextFill(Color.GREEN);
                    } else {
                        democracyResearchPic.setText("Researched");
                        democracyResearchPic.setTextFill(Color.RED);
                    }
                });
                break;

            case mathematics:
                Platform.runLater(() -> {
                    if (currentResearch.shouldResearch()) {
                        mathematicsResearchPic.setGraphic(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png")));
                    } else {
                        mathematicsResearchPic.setGraphic(Image.imageToGrayscale(new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "mathematics.png"))));
                    }
                    if (currentTown.getAcademy().isResearched(researchType)) {
                        mathematicsResearchPic.setText("Researched");
                        mathematicsResearchPic.setTextFill(Color.GREEN);
                    } else {
                        mathematicsResearchPic.setText("Researched");
                        mathematicsResearchPic.setTextFill(Color.RED);
                    }
                });
                break;
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1100, 700));
        frame.setVisible(true);
        AcademyPanel academyPanel = new AcademyPanel();
        frame.add(academyPanel);
    }

    public Label getSlingerResearchPic() {
        return slingerResearchPic;
    }

    public void setSlingerResearchPic(Label slingerResearchPic) {
        this.slingerResearchPic = slingerResearchPic;
    }

    public Label getHopliteResearchPic() {
        return hopliteResearchPic;
    }

    public void setHopliteResearchPic(Label hopliteResearchPic) {
        this.hopliteResearchPic = hopliteResearchPic;
    }

    public Label getEspionageResearchPic() {
        return espionageResearchPic;
    }

    public void setEspionageResearchPic(Label espionageResearchPic) {
        this.espionageResearchPic = espionageResearchPic;
    }

    public Label getRiderResearchPic() {
        return riderResearchPic;
    }

    public void setRiderResearchPic(Label riderResearchPic) {
        this.riderResearchPic = riderResearchPic;
    }

    public Label getBiremeResearchPic() {
        return biremeResearchPic;
    }

    public void setBiremeResearchPic(Label biremeResearchPic) {
        this.biremeResearchPic = biremeResearchPic;
    }

    public Label getChariotResearchPic() {
        return chariotResearchPic;
    }

    public void setChariotResearchPic(Label chariotResearchPic) {
        this.chariotResearchPic = chariotResearchPic;
    }

    public Label getDemolition_ShipResearchPic() {
        return demolition_shipResearchPic;
    }

    public void setDemolition_shipResearchPic(Label demolition_shipResearchPic) {
        this.demolition_shipResearchPic = demolition_shipResearchPic;
    }

    public Label getSmall_TransporterResearchPic() {
        return small_transporterResearchPic;
    }

    public void setSmall_transporterResearchPic(Label small_transporterResearchPic) {
        this.small_transporterResearchPic = small_transporterResearchPic;
    }

    public Label getTriremeResearchPic() {
        return triremeResearchPic;
    }

    public void setTriremeResearchPic(Label triremeResearchPic) {
        this.triremeResearchPic = triremeResearchPic;
    }

    public Label getRamResearchPic() {
        return ramResearchPic;
    }

    public void setRamResearchPic(Label ramResearchPic) {
        this.ramResearchPic = ramResearchPic;
    }

    public Label getStone_StormResearchPic() {
        return stone_stormResearchPic;
    }

    public void setStone_stormResearchPic(Label stone_stormResearchPic) {
        this.stone_stormResearchPic = stone_stormResearchPic;
    }

    public Label getCombat_ExperienceResearchPic() {
        return combat_experienceResearchPic;
    }

    public void setCombat_experienceResearchPic(Label combat_experienceResearchPic) {
        this.combat_experienceResearchPic = combat_experienceResearchPic;
    }

    public Label getArcherResearchPic() {
        return archerResearchPic;
    }

    public void setArcherResearchPic(Label archerResearchPic) {
        this.archerResearchPic = archerResearchPic;
    }

    public Label getMeteorologyResearchPic() {
        return meteorologyResearchPic;
    }

    public void setMeteorologyResearchPic(Label meteorologyResearchPic) {
        this.meteorologyResearchPic = meteorologyResearchPic;
    }

    public Label getBootyResearchPic() {
        return bootyResearchPic;
    }

    public void setBootyResearchPic(Label bootyResearchPic) {
        this.bootyResearchPic = bootyResearchPic;
    }

    public Label getArchitectureResearchPic() {
        return architectureResearchPic;
    }

    public void setArchitectureResearchPic(Label architectureResearchPic) {
        this.architectureResearchPic = architectureResearchPic;
    }

    public Label getBuilding_CraneResearchPic() {
        return building_craneResearchPic;
    }

    public void setBuilding_craneResearchPic(Label building_craneResearchPic) {
        this.building_craneResearchPic = building_craneResearchPic;
    }

    public Label getAttack_ShipResearchPic() {
        return attack_shipResearchPic;
    }

    public void setAttack_shipResearchPic(Label attack_shipResearchPic) {
        this.attack_shipResearchPic = attack_shipResearchPic;
    }

    public Label getCatapultResearchPic() {
        return catapultResearchPic;
    }

    public void setCatapultResearchPic(Label catapultResearchPic) {
        this.catapultResearchPic = catapultResearchPic;
    }

    public Label getPlowResearchPic() {
        return plowResearchPic;
    }

    public void setPlowResearchPic(Label plowResearchPic) {
        this.plowResearchPic = plowResearchPic;
    }

    public Label getPhalanxResearchPic() {
        return phalanxResearchPic;
    }

    public void setPhalanxResearchPic(Label phalanxResearchPic) {
        this.phalanxResearchPic = phalanxResearchPic;
    }

    public Label getCartographyResearchPic() {
        return cartographyResearchPic;
    }

    public void setCartographyResearchPic(Label cartographyResearchPic) {
        this.cartographyResearchPic = cartographyResearchPic;
    }

    public Label getTemple_LootingResearchPic() {
        return temple_lootingResearchPic;
    }

    public void setTemple_lootingResearchPic(Label temple_lootingResearchPic) {
        this.temple_lootingResearchPic = temple_lootingResearchPic;
    }

    public Label getStrong_WineResearchPic() {
        return strong_wineResearchPic;
    }

    public void setStrong_wineResearchPic(Label strong_wineResearchPic) {
        this.strong_wineResearchPic = strong_wineResearchPic;
    }

    public Label getTown_GuardResearchPic() {
        return town_guardResearchPic;
    }

    public void setTown_guardResearchPic(Label town_guardResearchPic) {
        this.town_guardResearchPic = town_guardResearchPic;
    }

    public Label getPotteryResearchPic() {
        return potteryResearchPic;
    }

    public void setPotteryResearchPic(Label potteryResearchPic) {
        this.potteryResearchPic = potteryResearchPic;
    }

    public Label getInstructorResearchPic() {
        return instructorResearchPic;
    }

    public void setInstructorResearchPic(Label instructorResearchPic) {
        this.instructorResearchPic = instructorResearchPic;
    }

    public Label getShipwrightResearchPic() {
        return shipwrightResearchPic;
    }

    public void setShipwrightResearchPic(Label shipwrightResearchPic) {
        this.shipwrightResearchPic = shipwrightResearchPic;
    }

    public Label getConscriptionResearchPic() {
        return conscriptionResearchPic;
    }

    public void setConscriptionResearchPic(Label conscriptionResearchPic) {
        this.conscriptionResearchPic = conscriptionResearchPic;
    }

    public Label getCryptographyResearchPic() {
        return cryptographyResearchPic;
    }

    public void setCryptographyResearchPic(Label cryptographyResearchPic) {
        this.cryptographyResearchPic = cryptographyResearchPic;
    }

    public Label getBerthResearchPic() {
        return berthResearchPic;
    }

    public void setBerthResearchPic(Label berthResearchPic) {
        this.berthResearchPic = berthResearchPic;
    }

    public Label getBreachResearchPic() {
        return breachResearchPic;
    }

    public void setBreachResearchPic(Label breachResearchPic) {
        this.breachResearchPic = breachResearchPic;
    }

    public Label getTake_OverResearchPic() {
        return take_overResearchPic;
    }

    public void setTake_overResearchPic(Label take_overResearchPic) {
        this.take_overResearchPic = take_overResearchPic;
    }

    public Label getDivine_SelectionResearchPic() {
        return divine_selectionResearchPic;
    }

    public void setDivine_selectionResearchPic(Label divine_selectionResearchPic) {
        this.divine_selectionResearchPic = divine_selectionResearchPic;
    }

    public Label getSet_SailResearchPic() {
        return set_sailResearchPic;
    }

    public void setSet_sailResearchPic(Label set_sailResearchPic) {
        this.set_sailResearchPic = set_sailResearchPic;
    }

    public Label getColonize_ShipResearchPic() {
        return colonize_shipResearchPic;
    }

    public void setColonize_shipResearchPic(Label colonize_shipResearchPic) {
        this.colonize_shipResearchPic = colonize_shipResearchPic;
    }

    public Label getDemocracyResearchPic() {
        return democracyResearchPic;
    }

    public void setDemocracyResearchPic(Label democracyResearchPic) {
        this.democracyResearchPic = democracyResearchPic;
    }

    public Label getMathematicsResearchPic() {
        return mathematicsResearchPic;
    }

    public void setMathematicsResearchPic(Label mathematicsResearchPic) {
        this.mathematicsResearchPic = mathematicsResearchPic;
    }
}
