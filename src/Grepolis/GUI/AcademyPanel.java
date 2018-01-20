package Grepolis.GUI;

import Grepolis.util.Image;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static Grepolis.util.Image.getImage;

public class AcademyPanel extends JFXPanel {

    private Label slingerResearchPic = new Label();

    public AcademyPanel() {
        initComponents();
    }

    private void initComponents() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane grid = new GridPane();
                Scene scene = new Scene(grid, 1100, 130);

                grid.setHgap(15);
                grid.setVgap(6);

                Font defaultFont = Font.font("Arial", 12);

                ImageView tempSlingerResearchPic = new ImageView(getImage(File.separator + "Images" + File.separator + "Research" + File.separator + "Slinger.png"));
                tempSlingerResearchPic = Image.imageToGrayscale(tempSlingerResearchPic);
                slingerResearchPic = new Label("", tempSlingerResearchPic);

                //row 1
                grid.add(new Label("1    *    *"), 0, 0);
                grid.add(new Label("4    *    *"), 1, 0);
                grid.add(new Label("7    *    *"), 2, 0);
                grid.add(new Label("10   *    *"), 3, 0);
                grid.add(new Label("13   *    *"), 4, 0);
                grid.add(new Label("16   *    *"), 5, 0);
                grid.add(new Label("19   *    *"), 6, 0);
                grid.add(new Label("22    *    *"), 7, 0);
                grid.add(new Label("25    *    *"), 8, 0);
                grid.add(new Label("28    *    *"), 9, 0);
                grid.add(new Label("31    *    *"), 10, 0);
                grid.add(new Label("34   *    *"), 11, 0);
                //row 2
                grid.add(slingerResearchPic, 0, 1);

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
        AcademyPanel academyPanel = new AcademyPanel();
        frame.add(academyPanel);
    }


}
