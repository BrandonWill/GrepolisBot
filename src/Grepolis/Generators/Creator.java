package Grepolis.Generators;

import Grepolis.AcademyResearch;
import Grepolis.GUI.AcademyPanel;
import Grepolis.GUI.BuildingPanel;
import Grepolis.GUI.QueuePanel;
import Grepolis.GUI.TroopPanel;

/**
 * Created by Brandon on 5/18/2017.
 */
public class Creator {

    public static void main(String args[]) {
        getAcademyStuff();

    }

    public static void getResearchStuff() {
        for (int i = 0; i < AcademyResearch.ResearchType.values().length; i++) {
            System.out.println("private Label "+AcademyResearch.ResearchType.values()[i].name() +"ResearchPic = new Label();");
        }
    }

    public static void getBuildingStuff() {
        for (int i = 0; i < BuildingPanel.class.getDeclaredFields().length; i++) {
            if (BuildingPanel.class.getDeclaredFields()[i].getName().contains("Pic")) {
                String withoutPic = BuildingPanel.class.getDeclaredFields()[i].getName().replaceAll("Pic", "");

                String textToReplace = "         try {\n" +
                        "            Platform.runLater(() -> {\n" +
                        "                buildingPanel.getMainLevelToBuild().getValueFactory().setValue(town.getBuilding(Building.BuildingType." + withoutPic + ").getBuildTo());\n" +
                        "                buildingPanel.getMainPic().setText(String.valueOf(town.getBuilding(Building.BuildingType." +withoutPic +").getLevel()));\n" +
                        "                buildingPanel.getMainPic().setTextFill(town.getBuilding(Building.BuildingType." +withoutPic +").getLevel() == town.getBuilding(Building.BuildingType." + withoutPic +").getBuildTo() ? Color.GREEN : Color.RED);\n" +
                        "            });\n" +
                        "        } catch (Exception ignored) {\n" +
                        "            Platform.runLater(() -> {\n" +
                        "                buildingPanel.getMainLevelToBuild().getValueFactory().setValue(0);\n" +
                        "                buildingPanel.getMainPic().setText(String.valueOf(0));\n" +
                        "                buildingPanel.getMainPic().setTextFill(Color.BLACK);\n" +
                        "            });\n" +
                        "        }";

                System.out.println(textToReplace.replaceAll("Main", capitalize(withoutPic)));


                System.out.println();
            }

        }
    }

    public static void getAcademyStuff() {
        for (int i = 0; i < AcademyPanel.class.getDeclaredFields().length; i++) {
            if (AcademyPanel.class.getDeclaredFields()[i].getName().contains("ResearchPic")) {
                String withoutPic = AcademyPanel.class.getDeclaredFields()[i].getName().replaceAll("ResearchPic", "");
                String capitalizedName = capitalize(withoutPic);

                String textToReplace = "        try {\n" +
                        "            Platform.runLater(() -> {\n" +
                        "                if (town.getAcademy().getResearch(AcademyResearch.ResearchType." + withoutPic +").shouldResearch()) {\n" +
                        "                   academyPanel.get" + capitalizedName +"ResearchPic().setGraphic(new ImageView(getImage(File.separator + \"Images\" + File.separator + \"Research\" + File.separator + \"" +withoutPic +".png\")));\n" +
                        "                } else {\n" +
                        "                    ImageView temp" +capitalizedName +"ResearchPic = new ImageView(getImage(File.separator + \"Images\" + File.separator + \"Research\" + File.separator + \"" +withoutPic +".png\"));\n" +
                        "                    temp" + capitalizedName +"ResearchPic = Image.imageToGrayscale(temp" +capitalizedName +"ResearchPic);\n" +
                        "                    academyPanel.get" +capitalizedName +"ResearchPic().setGraphic(temp" +capitalizedName +"ResearchPic);\n" +
                        "                }\n" +
                        "                if (town.getAcademy().isResearched(AcademyResearch.ResearchType." +withoutPic +")) {\n" +
                        "                    academyPanel.get" + capitalizedName +"ResearchPic().setText(\"Researched\");\n" +
                        "                    academyPanel.get" +capitalizedName +"ResearchPic().setTextFill(Color.GREEN);\n" +
                        "                } else {\n" +
                        "                    academyPanel.get" +capitalizedName +"ResearchPic().setText(\"Researched\");\n" +
                        "                    academyPanel.get" +capitalizedName +"ResearchPic().setTextFill(Color.RED);\n" +
                        "                }\n" +
                        "            });\n" +
                        "\n" +
                        "        } catch (Exception ignored) {\n" +
                        "            ImageView temp" +capitalizedName +"ResearchPic = new ImageView(getImage(File.separator + \"Images\" + File.separator + \"Research\" + File.separator + \"" +withoutPic +".png\"));\n" +
                        "            temp" +capitalizedName +"ResearchPic = Image.imageToGrayscale(temp" +capitalizedName +"ResearchPic);\n" +
                        "            academyPanel.get" +capitalizedName +"ResearchPic().setGraphic(temp" +capitalizedName +"ResearchPic);\n" +
                        "            academyPanel.get" +capitalizedName +"ResearchPic().setText(\"Researched\");\n" +
                        "            academyPanel.get" +capitalizedName +"ResearchPic().setTextFill(Color.RED);\n" +
                        "        }";

                System.out.println(textToReplace);


                System.out.println();
            }

        }
    }

    public static void getPicValues() {
        for (int i = 0; i < QueuePanel.class.getDeclaredFields().length; i++) {
            if (QueuePanel.class.getDeclaredFields()[i].getName().contains("Pic")) {
                System.out.println("private Label " +QueuePanel.class.getDeclaredFields()[i].getName() + " = new Label();");
            }

        }
    }

    public static void createTroopPanel() {
        for (int i = 0; i < TroopPanel.class.getDeclaredFields().length; i++) {
            if (TroopPanel.class.getDeclaredFields()[i].getName().contains("Pic")) {
                String withoutPic = TroopPanel.class.getDeclaredFields()[i].getName().replaceAll("Pic", "");

                String textToReplace = "        try {\n" +
                        "            troopPanel.getSwordToBuild().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType." + withoutPic + ").getBuildTo()));\n" +
                        "            troopPanel.getSwordPic().setText(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType." + withoutPic + ").getTotalTroops()));\n" +
                        "            troopPanel.getSwordPic().setTooltip(new Tooltip(String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType." + withoutPic + ").getTotalTroops() + \"(\" + String.valueOf(town.getBarracks().getUnit(BarracksUnit.UnitType." + withoutPic + ").getInQueue() + \" in queue)\"))));\n" +
                        "        } catch (Exception ignored) {\n" +
                        "            troopPanel.getSwordPic().setText(String.valueOf(0));\n" +
                        "            troopPanel.getSwordPic().setTooltip(new Tooltip(String.valueOf(0)));\n" +
                        "            troopPanel.getSwordToBuild().setText(String.valueOf(0));\n" +
                        "        }";

                System.out.println(textToReplace.replaceAll("Sword", capitalize(withoutPic)));


                System.out.println();
            }

        }
    }

    /**
     * This was used for printing enums before using strings
     *
     * @param word Text to capitalize.
     * @return Text with the first letter capitalized.
     */
    private static String capitalize(String word) {
        if (word.contains("_")) {
            String[] holder = word.split("_");
            word = "";
            for (String string : holder) {
                word += string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase() +"_"; //+"_"
            }
            if (word.endsWith(" ") || word.endsWith("_")) {
                word = word.substring(0, word.length() - 1);
            }
        } else {
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        return word;
    }
}
