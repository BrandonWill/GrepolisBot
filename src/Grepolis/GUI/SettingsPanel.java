package Grepolis.GUI;

import com.sun.javafx.application.PlatformImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.*;
import javafx.scene.text.Font;


/**
 * Created by Brandon on 9/28/2016.
 */
public class SettingsPanel extends JFXPanel {

    public SettingsPanel() {
        initComponents();
    }

    private void initComponents() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
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

                //column 1
                grid.add(usernameLabel, 0 , 0);
                grid.add(passwordLabel, 0, 1);
                grid.add(worldLabel, 0, 2);
                grid.add(botUpdateTimeLabel, 0, 3);
                grid.add(troopUpdateTimeLabel, 0, 4);

                //column 2
                grid.add(usernameField, 1, 0);
                grid.add(passwordField, 1, 1);
                grid.add(worldField, 1, 2);
                grid.add(botUpdateTimeField, 1, 3);
                grid.add(troopUpdateTimeField, 1, 4);


                grid.setPrefSize(1100, 130); // Default width and height
                grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

                Scene scene = new Scene(grid, 1100, 130);

                setScene(scene);

            }
        });
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
}
