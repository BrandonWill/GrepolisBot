package Grepolis.GUI;

import Grepolis.GrepolisBot;
import com.sun.javafx.application.PlatformImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;

import java.awt.*;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;


/**
 * Created by Brandon on 9/28/2016.
 */
public class SettingsPanel extends JFXPanel {
    private static boolean hasAutomaticFestivals = true;

    public SettingsPanel() {
        initComponents();
    }

    private void initComponents() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
                Scene scene = new Scene(grid, 1100, 130);

                grid.setHgap(5);
                grid.setVgap(6);



                Font defaultFont = Font.font("Arial", 12);

                //Column 1 components
                Text usernameLabel = new Text("Username:");
                usernameLabel.setFont(defaultFont);
                Text passwordLabel = new Text("Password:");
                passwordLabel.setFont(defaultFont);
                Text worldLabel = new Text("World:");
                worldLabel.setFont(defaultFont);
                Text botUpdateTimeLabel = new Text("Bot Update Time:");
                botUpdateTimeLabel.setFont(Font.font("Arial", 12));
                Text troopUpdateTimeLabel = new Text("Troop Update Time:");
                troopUpdateTimeLabel.setFont(Font.font("Arial", 12));
                Text checkboxLabel = new Text("Automatic City Festivals:");
                checkboxLabel.setFont(Font.font("Arial", 12));

                //Column 2 components - these check to see if any data was loaded beforehand!
                if (usernameField == null) {
                    usernameField = new TextField();
                }
                usernameField.setPrefSize(100, 20);
                usernameField.setFont(defaultFont);
                if (passwordField == null) {
                    passwordField = new PasswordField();
                }
                passwordField.setPrefSize(100, 20);
                passwordField.setFont(defaultFont);
                if (worldField == null) {
                    worldField = new TextField("enXX");
                }
                worldField.setPrefSize(100, 20);
                worldField.setFont(defaultFont);
                if (botUpdateTimeField == null) {
                    botUpdateTimeField = new TextField("00:06:00");
                }
                botUpdateTimeField.setPrefSize(100, 20);
                botUpdateTimeField.setFont(defaultFont);
                if (troopUpdateTimeField == null) {
                    troopUpdateTimeField = new TextField("00:06:00");
                }
                if (automaticFestivals == null) {
                    automaticFestivals = new CheckBox();
                }
                if (hasAutomaticFestivals) {
                    automaticFestivals.setSelected(true);
                }
                automaticFestivals.setFont(defaultFont);
                updateCheckboxColor(automaticFestivals);

                automaticFestivals.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        hasAutomaticFestivals = newValue;
                        updateCheckboxColor(automaticFestivals);
                    }
                });

//                automaticFestivals.setPadding(new Insets(0, 0, 0, 20)); //(top/right/bottom/left)

                //column 1
                grid.add(usernameLabel, 0 , 0);
                grid.add(passwordLabel, 0, 1);
                grid.add(worldLabel, 0, 2);
                grid.add(botUpdateTimeLabel, 0, 3);
                grid.add(troopUpdateTimeLabel, 0, 4);
                grid.add(checkboxLabel, 0, 5);
                grid.setVgap(10);
//                GridPane.setMargin(automaticFestivals, new Insets(0, 0, 0, 20));
                //column 2
                grid.add(usernameField, 1, 0);
                grid.add(passwordField, 1, 1);
                grid.add(worldField, 1, 2);
                grid.add(botUpdateTimeField, 1, 3);
                grid.add(troopUpdateTimeField, 1, 4);
                grid.add(automaticFestivals, 1, 5);



//                grid.setStyle("-fx-border-color: black");
//                grid.setPrefSize(1100, 130); // Default width and height
                grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);


                setScene(scene);

            }
        });
    }

    private static void updateCheckboxColor(CheckBox checkBox) {
        checkBox.getStylesheets().clear();
        if (hasAutomaticFestivals) {
            try {
                checkBox.getStylesheets().add(new URL("file:///" + getCSS("CheckboxChecked.css")).toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                checkBox.getStylesheets().add(new URL("file:///" + getCSS("CheckboxUnchecked.css")).toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String args[]) {
//        JFrame frame = new JFrame();
//        frame.setMinimumSize(new Dimension(1100,700));
//        //frame.add(new JLabel("test"));
//        frame.setVisible(true);
//        SettingsPanel settingsPanel2 = new SettingsPanel();
//        frame.add(settingsPanel2);
//    }

    private static volatile PasswordField passwordField;
    private static volatile TextField botUpdateTimeField;
    private static volatile TextField usernameField;
    private static volatile TextField worldField;
    private static volatile TextField troopUpdateTimeField;
    private static volatile CheckBox automaticFestivals;

    public static boolean hasAutomaticFestivals() {
        return hasAutomaticFestivals;
    }

    public static void setHasAutomaticFestivals(boolean hasAutomaticFestivals) {
        SettingsPanel.hasAutomaticFestivals = hasAutomaticFestivals;
        if (automaticFestivals != null) {
            updateCheckboxColor(automaticFestivals);
        }
    }

    public static TextField getPasswordField() {
        return passwordField;
    }

    public static void setPasswordField(PasswordField passwordField, String password) {
        SettingsPanel.passwordField = passwordField;
        passwordField.setText(password);
    }

    public static TextField getBotUpdateTimeField() {
        return botUpdateTimeField;
    }

    public static void setBotUpdateTimeField(TextField botUpdateTimeField) {
        SettingsPanel.botUpdateTimeField = botUpdateTimeField;
    }

    public static TextField getUsernameField() {
        return usernameField;
    }

    public static void setUsernameField(TextField usernameField) {
        SettingsPanel.usernameField = usernameField;
    }

    public static TextField getWorldField() {
        return worldField;
    }

    public static void setWorldField(TextField worldField) {
        SettingsPanel.worldField = worldField;
    }

    public static TextField getTroopUpdateTimeField() {
        return troopUpdateTimeField;
    }

    public static void setTroopUpdateTimeField(TextField troopUpdateTimeField) {
        SettingsPanel.troopUpdateTimeField = troopUpdateTimeField;
    }

    public static CheckBox getAutomaticFestivals() {
        return automaticFestivals;
    }

    public static void setAutomaticFestivals(CheckBox automaticFestivals) {
        SettingsPanel.automaticFestivals = automaticFestivals;
    }

    private static String getCSS(String fileName) {
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
            File directory = new File(jarDir + File.separator + "CSS" + File.separator);
            //System.out.println("Loading from: " + jarDir + File.separator + loadLocation + File.separator + fileName);
            if (!directory.exists()) {
                directory.mkdir();
            }
                return jarDir + File.separator + "CSS" + File.separator + fileName;
        }
        return null;
    }
}
