package Grepolis.GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static Grepolis.util.Image.getImage;

public class BuildingPanel extends JFXPanel {
    private static final PseudoClass PRESSED = PseudoClass.getPseudoClass("pressed");

    private Text buildingsLabel = new Text();
    private Label academyPic = new Label();
    private Label barracksPic = new Label();
    private Label docksPic = new Label();
    private Label farmPic = new Label();
    private Label hidePic = new Label();
    private Label ironerPic = new Label();
    private Label libraryPic = new Label();
    private Label lighthousePic = new Label();
    private Label lumberPic = new Label();
    private Label mainPic = new Label();
    private Label marketPic = new Label();
    private Label oraclePic = new Label();
    private Label statuePic = new Label();
    private Label stonerPic = new Label();
    private Label storagePic = new Label();
    private Label templePic = new Label();
    private Label theaterPic = new Label();
    private Label thermalPic = new Label();
    private Label towerPic = new Label();
    private Label trade_officePic = new Label();
    private Label wallPic = new Label();

    private Spinner<Integer> academyLevelToBuild = new Spinner<>();
    private Spinner<Integer> barracksLevelToBuild = new Spinner<>();
    private Spinner<Integer> docksLevelToBuild = new Spinner<>();
    private Spinner<Integer> farmLevelToBuild = new Spinner<>();
    private Spinner<Integer> hideLevelToBuild = new Spinner<>();
    private Spinner<Integer> ironerLevelToBuild = new Spinner<>();
    private Spinner<Integer> libraryLevelToBuild = new Spinner<>();
    private Spinner<Integer> lighthouseLevelToBuild = new Spinner<>();
    private Spinner<Integer> lumberLevelToBuild = new Spinner<>();
    private Spinner<Integer> mainLevelToBuild = new Spinner<>();
    private Spinner<Integer> marketLevelToBuild = new Spinner<>();
    private Spinner<Integer> oracleLevelToBuild = new Spinner<>();
    private Spinner<Integer> statueLevelToBuild = new Spinner<>();
    private Spinner<Integer> stonerLevelToBuild = new Spinner<>();
    private Spinner<Integer> storageLevelToBuild = new Spinner<>();
    private Spinner<Integer> templeLevelToBuild = new Spinner<>();
    private Spinner<Integer> theaterLevelToBuild = new Spinner<>();
    private Spinner<Integer> thermalLevelToBuild = new Spinner<>();
    private Spinner<Integer> towerLevelToBuild = new Spinner<>();
    private Spinner<Integer> trade_officeLevelToBuild = new Spinner<>();
    private Spinner<Integer> wallLevelToBuild = new Spinner<>();

    public BuildingPanel() {
        initComponents();
    }

    private void initComponents() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
                Scene scene = new Scene(grid, 200, 130);

                grid.setHgap(0);
                grid.setVgap(0);
                for (int i = 0; i < 21; i++) {
                    ColumnConstraints column = new ColumnConstraints(40);
                    grid.getColumnConstraints().add(column);
                }


                Font defaultFont = Font.font("Arial", 12);
                buildingsLabel = new Text("Buildings");
                buildingsLabel.setFont(defaultFont);
                academyPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "academy.png")));
                academyPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                academyPic.setContentDisplay(ContentDisplay.TOP);

                barracksPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "barracks.png")));
                barracksPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                barracksPic.setContentDisplay(ContentDisplay.TOP);

                docksPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "docks.png")));
                docksPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                docksPic.setContentDisplay(ContentDisplay.TOP);

                farmPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "farm.png")));
                farmPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                farmPic.setContentDisplay(ContentDisplay.TOP);

                hidePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "hide.png")));
                hidePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                hidePic.setContentDisplay(ContentDisplay.TOP);

                ironerPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "ironer.png")));
                ironerPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                ironerPic.setContentDisplay(ContentDisplay.TOP);

                libraryPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "library.png")));
                libraryPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                libraryPic.setContentDisplay(ContentDisplay.TOP);

                lighthousePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "lighthouse.png")));
                lighthousePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                lighthousePic.setContentDisplay(ContentDisplay.TOP);

                lumberPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "lumber.png")));
                lumberPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                lumberPic.setContentDisplay(ContentDisplay.TOP);

                mainPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "main.png")));
                mainPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                mainPic.setContentDisplay(ContentDisplay.TOP);

                marketPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "market.png")));
                marketPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                marketPic.setContentDisplay(ContentDisplay.TOP);

                oraclePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "oracle.png")));
                oraclePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                oraclePic.setContentDisplay(ContentDisplay.TOP);

                statuePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "statue.png")));
                statuePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                statuePic.setContentDisplay(ContentDisplay.TOP);

                stonerPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "stoner.png")));
                stonerPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                stonerPic.setContentDisplay(ContentDisplay.TOP);

                storagePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "storage.png")));
                storagePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                storagePic.setContentDisplay(ContentDisplay.TOP);

                templePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "temple.png")));
                templePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                templePic.setContentDisplay(ContentDisplay.TOP);

                theaterPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "theater.png")));
                theaterPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                theaterPic.setContentDisplay(ContentDisplay.TOP);

                thermalPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "thermal.png")));
                thermalPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                thermalPic.setContentDisplay(ContentDisplay.TOP);

                towerPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "tower.png")));
                towerPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                towerPic.setContentDisplay(ContentDisplay.TOP);

                trade_officePic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "trade_office.png")));
                trade_officePic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                trade_officePic.setContentDisplay(ContentDisplay.TOP);

                wallPic = new Label("0", new ImageView(getImage(File.separator + "Images" + File.separator + "Buildings" + File.separator + "wall.png")));
                wallPic.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
                wallPic.setContentDisplay(ContentDisplay.TOP);

                //Spinners
                IncrementHandler handler = new IncrementHandler();

                academyLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 37, 0));
                academyLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                academyLevelToBuild.setEditable(true);
                academyLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                academyLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                barracksLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
                barracksLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                barracksLevelToBuild.setEditable(true);
                barracksLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                barracksLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                docksLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
                docksLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                docksLevelToBuild.setEditable(true);
                docksLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                docksLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                farmLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0));
                farmLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                farmLevelToBuild.setEditable(true);
                farmLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                farmLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                hideLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
                hideLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                hideLevelToBuild.setEditable(true);
                hideLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                hideLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                ironerLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0));
                ironerLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                ironerLevelToBuild.setEditable(true);
                ironerLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                ironerLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                libraryLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                libraryLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                libraryLevelToBuild.setEditable(true);
                libraryLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                libraryLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                lighthouseLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                lighthouseLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                lighthouseLevelToBuild.setEditable(true);
                lighthouseLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                lighthouseLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                lumberLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0));
                lumberLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                lumberLevelToBuild.setEditable(true);
                lumberLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                lumberLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                mainLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));
                mainLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                mainLevelToBuild.setEditable(true);
                mainLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                mainLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                marketLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
                marketLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                marketLevelToBuild.setEditable(true);
                marketLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                marketLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                oracleLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                oracleLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                oracleLevelToBuild.setEditable(true);
                oracleLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                oracleLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                statueLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                statueLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                statueLevelToBuild.setEditable(true);
                statueLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                statueLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                stonerLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0));
                stonerLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                stonerLevelToBuild.setEditable(true);
                stonerLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                stonerLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                storageLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 35, 0));
                storageLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                storageLevelToBuild.setEditable(true);
                storageLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                storageLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                templeLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
                templeLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                templeLevelToBuild.setEditable(true);
                templeLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                templeLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                theaterLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                theaterLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                theaterLevelToBuild.setEditable(true);
                theaterLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                theaterLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                thermalLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                thermalLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                thermalLevelToBuild.setEditable(true);
                thermalLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                thermalLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                towerLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                towerLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                towerLevelToBuild.setEditable(true);
                towerLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                towerLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                trade_officeLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0));
                trade_officeLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                trade_officeLevelToBuild.setEditable(true);
                trade_officeLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                trade_officeLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });
                wallLevelToBuild.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));
                wallLevelToBuild.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                wallLevelToBuild.setEditable(true);
                wallLevelToBuild.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
                wallLevelToBuild.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                });

                //row 1
                grid.add(buildingsLabel ,0 ,0);

                //row 2
                grid.add(mainPic, 0, 1);
                grid.add(hidePic, 1, 1);
                grid.add(storagePic, 2, 1);
                grid.add(farmPic, 3, 1);
                grid.add(lumberPic, 4, 1);
                grid.add(stonerPic, 5, 1);
                grid.add(ironerPic, 6, 1);
                grid.add(marketPic, 7, 1);
                grid.add(docksPic, 8, 1);
                grid.add(barracksPic, 9, 1);
                grid.add(wallPic, 10, 1);
                grid.add(academyPic, 11, 1);
                grid.add(templePic, 12, 1);
                grid.add(theaterPic, 13, 1);
                grid.add(thermalPic, 14, 1);
                grid.add(libraryPic, 15, 1);
                grid.add(lighthousePic, 16, 1);
                grid.add(towerPic, 17, 1);
                grid.add(statuePic, 18, 1);
                grid.add(oraclePic, 19, 1);
                grid.add(trade_officePic, 20, 1);

                //row 3
                grid.add(mainLevelToBuild, 0, 2);
                grid.add(hideLevelToBuild, 1, 2);
                grid.add(storageLevelToBuild, 2, 2);
                grid.add(farmLevelToBuild, 3, 2);
                grid.add(lumberLevelToBuild, 4, 2);
                grid.add(stonerLevelToBuild, 5, 2);
                grid.add(ironerLevelToBuild, 6, 2);
                grid.add(marketLevelToBuild, 7, 2);
                grid.add(docksLevelToBuild, 8, 2);
                grid.add(barracksLevelToBuild, 9, 2);
                grid.add(wallLevelToBuild, 10, 2);
                grid.add(academyLevelToBuild, 11, 2);
                grid.add(templeLevelToBuild, 12, 2);
                grid.add(theaterLevelToBuild, 13, 2);
                grid.add(thermalLevelToBuild, 14, 2);
                grid.add(libraryLevelToBuild, 15, 2);
                grid.add(lighthouseLevelToBuild, 16, 2);
                grid.add(towerLevelToBuild, 17, 2);
                grid.add(statueLevelToBuild, 18, 2);
                grid.add(oracleLevelToBuild, 19, 2);
                grid.add(trade_officeLevelToBuild, 20, 2);

                grid.setPrefSize(200, 130); // Default width and height
//                grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

                grid.setStyle("-fx-border-color: black;");

                setScene(scene);
            }
        });
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Uses the system's look and feel. Without this, sometimes Nimbus theme appears for no reason and nothing is aligned.
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }
                JFrame frame = new JFrame();
                frame.setMinimumSize(new Dimension(1100, 700));
                frame.setVisible(true);
                BuildingPanel buildingPanel = new BuildingPanel();
                frame.add(buildingPanel);
            }
        });

    }

    private class IncrementHandler implements EventHandler<MouseEvent> {

        private Spinner spinner;
        private boolean increment;
        private long startTimestamp;
        private int currentFrame = 0;
        private int previousFrame = 0;

        private static final long DELAY = 1000l * 1000L * 250L; // 0.25 sec
        private Node button;

        private final AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (now - startTimestamp >= DELAY) {
                    // Single or holded mouse click
                    if (currentFrame == previousFrame || currentFrame % 10 == 0) {
                        if (increment) {
                            spinner.increment();
                        }
                        else {
                            spinner.decrement();
                        }
                    }
                }
                ++currentFrame;
            }
        };

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Spinner source = (Spinner) event.getSource();
                Node node = event.getPickResult().getIntersectedNode();

                Boolean increment = null;
                // find which kind of button was pressed and if one was pressed
                while (increment == null && node != source) {
                    if (node.getStyleClass().contains("increment-arrow-button")) {
                        increment = Boolean.TRUE;
                    } else if (node.getStyleClass().contains("decrement-arrow-button")) {
                        increment = Boolean.FALSE;
                    } else {
                        node = node.getParent();
                    }
                }
                if (increment != null) {
                    event.consume();
                    source.requestFocus();
                    spinner = source;
                    this.increment = increment;

                    // timestamp to calculate the delay
                    startTimestamp = System.nanoTime();

                    button = node;

                    // update for css styling
                    node.pseudoClassStateChanged(PRESSED, true);

                    // first value update
                    timer.handle(startTimestamp + DELAY);

                    // trigger timer for more updates later
                    timer.start();
                }
            }
        }

        public void stop() {
            previousFrame = currentFrame;
            timer.stop();
            if (button != null) {
                button.pseudoClassStateChanged(PRESSED, false);
                button = null;
                spinner = null;
            }
        }
    }

    public Text getBuildingsLabel() {
        return buildingsLabel;
    }

    public void setBuildingsLabel(Text buildingsLabel) {
        this.buildingsLabel = buildingsLabel;
    }

    public Label getAcademyPic() {
        return academyPic;
    }

    public void setAcademyPic(Label academyPic) {
        this.academyPic = academyPic;
    }

    public Label getBarracksPic() {
        return barracksPic;
    }

    public void setBarracksPic(Label barracksPic) {
        this.barracksPic = barracksPic;
    }

    public Label getDocksPic() {
        return docksPic;
    }

    public void setDocksPic(Label docksPic) {
        this.docksPic = docksPic;
    }

    public Label getFarmPic() {
        return farmPic;
    }

    public void setFarmPic(Label farmPic) {
        this.farmPic = farmPic;
    }

    public Label getHidePic() {
        return hidePic;
    }

    public void setHidePic(Label hidePic) {
        this.hidePic = hidePic;
    }

    public Label getIronerPic() {
        return ironerPic;
    }

    public void setIronerPic(Label ironerPic) {
        this.ironerPic = ironerPic;
    }

    public Label getLibraryPic() {
        return libraryPic;
    }

    public void setLibraryPic(Label libraryPic) {
        this.libraryPic = libraryPic;
    }

    public Label getLighthousePic() {
        return lighthousePic;
    }

    public void setLighthousePic(Label lighthousePic) {
        this.lighthousePic = lighthousePic;
    }

    public Label getLumberPic() {
        return lumberPic;
    }

    public void setLumberPic(Label lumberPic) {
        this.lumberPic = lumberPic;
    }

    public Label getMainPic() {
        return mainPic;
    }

    public void setMainPic(Label mainPic) {
        this.mainPic = mainPic;
    }

    public Label getMarketPic() {
        return marketPic;
    }

    public void setMarketPic(Label marketPic) {
        this.marketPic = marketPic;
    }

    public Label getOraclePic() {
        return oraclePic;
    }

    public void setOraclePic(Label oraclePic) {
        this.oraclePic = oraclePic;
    }

    public Label getStatuePic() {
        return statuePic;
    }

    public void setStatuePic(Label statuePic) {
        this.statuePic = statuePic;
    }

    public Label getStonerPic() {
        return stonerPic;
    }

    public void setStonerPic(Label stonerPic) {
        this.stonerPic = stonerPic;
    }

    public Label getStoragePic() {
        return storagePic;
    }

    public void setStoragePic(Label storagePic) {
        this.storagePic = storagePic;
    }

    public Label getTemplePic() {
        return templePic;
    }

    public void setTemplePic(Label templePic) {
        this.templePic = templePic;
    }

    public Label getTheaterPic() {
        return theaterPic;
    }

    public void setTheaterPic(Label theaterPic) {
        this.theaterPic = theaterPic;
    }

    public Label getThermalPic() {
        return thermalPic;
    }

    public void setThermalPic(Label thermalPic) {
        this.thermalPic = thermalPic;
    }

    public Label getTowerPic() {
        return towerPic;
    }

    public void setTowerPic(Label towerPic) {
        this.towerPic = towerPic;
    }

    public Label getTrade_officePic() {
        return trade_officePic;
    }

    public void setTrade_officePic(Label trade_officePic) {
        this.trade_officePic = trade_officePic;
    }

    public Label getWallPic() {
        return wallPic;
    }

    public void setWallPic(Label wallPic) {
        this.wallPic = wallPic;
    }

    public Spinner<Integer> getAcademyLevelToBuild() {
        return academyLevelToBuild;
    }

    public void setAcademyLevelToBuild(Spinner<Integer> academyLevelToBuild) {
        this.academyLevelToBuild = academyLevelToBuild;
    }

    public Spinner<Integer> getBarracksLevelToBuild() {
        return barracksLevelToBuild;
    }

    public void setBarracksLevelToBuild(Spinner<Integer> barracksLevelToBuild) {
        this.barracksLevelToBuild = barracksLevelToBuild;
    }

    public Spinner<Integer> getDocksLevelToBuild() {
        return docksLevelToBuild;
    }

    public void setDocksLevelToBuild(Spinner<Integer> docksLevelToBuild) {
        this.docksLevelToBuild = docksLevelToBuild;
    }

    public Spinner<Integer> getFarmLevelToBuild() {
        return farmLevelToBuild;
    }

    public void setFarmLevelToBuild(Spinner<Integer> farmLevelToBuild) {
        this.farmLevelToBuild = farmLevelToBuild;
    }

    public Spinner<Integer> getHideLevelToBuild() {
        return hideLevelToBuild;
    }

    public void setHideLevelToBuild(Spinner<Integer> hideLevelToBuild) {
        this.hideLevelToBuild = hideLevelToBuild;
    }

    public Spinner<Integer> getIronerLevelToBuild() {
        return ironerLevelToBuild;
    }

    public void setIronerLevelToBuild(Spinner<Integer> ironerLevelToBuild) {
        this.ironerLevelToBuild = ironerLevelToBuild;
    }

    public Spinner<Integer> getLibraryLevelToBuild() {
        return libraryLevelToBuild;
    }

    public void setLibraryLevelToBuild(Spinner<Integer> libraryLevelToBuild) {
        this.libraryLevelToBuild = libraryLevelToBuild;
    }

    public Spinner<Integer> getLighthouseLevelToBuild() {
        return lighthouseLevelToBuild;
    }

    public void setLighthouseLevelToBuild(Spinner<Integer> lighthouseLevelToBuild) {
        this.lighthouseLevelToBuild = lighthouseLevelToBuild;
    }

    public Spinner<Integer> getLumberLevelToBuild() {
        return lumberLevelToBuild;
    }

    public void setLumberLevelToBuild(Spinner<Integer> lumberLevelToBuild) {
        this.lumberLevelToBuild = lumberLevelToBuild;
    }

    public Spinner<Integer> getMainLevelToBuild() {
        return mainLevelToBuild;
    }

    public void setMainLevelToBuild(Spinner<Integer> mainLevelToBuild) {
        this.mainLevelToBuild = mainLevelToBuild;
    }

    public Spinner<Integer> getMarketLevelToBuild() {
        return marketLevelToBuild;
    }

    public void setMarketLevelToBuild(Spinner<Integer> marketLevelToBuild) {
        this.marketLevelToBuild = marketLevelToBuild;
    }

    public Spinner<Integer> getOracleLevelToBuild() {
        return oracleLevelToBuild;
    }

    public void setOracleLevelToBuild(Spinner<Integer> oracleLevelToBuild) {
        this.oracleLevelToBuild = oracleLevelToBuild;
    }

    public Spinner<Integer> getStatueLevelToBuild() {
        return statueLevelToBuild;
    }

    public void setStatueLevelToBuild(Spinner<Integer> statueLevelToBuild) {
        this.statueLevelToBuild = statueLevelToBuild;
    }

    public Spinner<Integer> getStonerLevelToBuild() {
        return stonerLevelToBuild;
    }

    public void setStonerLevelToBuild(Spinner<Integer> stonerLevelToBuild) {
        this.stonerLevelToBuild = stonerLevelToBuild;
    }

    public Spinner<Integer> getStorageLevelToBuild() {
        return storageLevelToBuild;
    }

    public void setStorageLevelToBuild(Spinner<Integer> storageLevelToBuild) {
        this.storageLevelToBuild = storageLevelToBuild;
    }

    public Spinner<Integer> getTempleLevelToBuild() {
        return templeLevelToBuild;
    }

    public void setTempleLevelToBuild(Spinner<Integer> templeLevelToBuild) {
        this.templeLevelToBuild = templeLevelToBuild;
    }

    public Spinner<Integer> getTheaterLevelToBuild() {
        return theaterLevelToBuild;
    }

    public void setTheaterLevelToBuild(Spinner<Integer> theaterLevelToBuild) {
        this.theaterLevelToBuild = theaterLevelToBuild;
    }

    public Spinner<Integer> getThermalLevelToBuild() {
        return thermalLevelToBuild;
    }

    public void setThermalLevelToBuild(Spinner<Integer> thermalLevelToBuild) {
        this.thermalLevelToBuild = thermalLevelToBuild;
    }

    public Spinner<Integer> getTowerLevelToBuild() {
        return towerLevelToBuild;
    }

    public void setTowerLevelToBuild(Spinner<Integer> towerLevelToBuild) {
        this.towerLevelToBuild = towerLevelToBuild;
    }

    public Spinner<Integer> getTrade_officeLevelToBuild() {
        return trade_officeLevelToBuild;
    }

    public void setTrade_officeLevelToBuild(Spinner<Integer> trade_officeLevelToBuild) {
        this.trade_officeLevelToBuild = trade_officeLevelToBuild;
    }

    public Spinner<Integer> getWallLevelToBuild() {
        return wallLevelToBuild;
    }

    public void setWallLevelToBuild(Spinner<Integer> wallLevelToBuild) {
        this.wallLevelToBuild = wallLevelToBuild;
    }
}
