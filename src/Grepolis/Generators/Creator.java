package Grepolis.Generators;

import Grepolis.GUI.TroopPanel;

import java.lang.reflect.Field;

/**
 * Created by Brandon on 5/18/2017.
 */
public class Creator {

    public static void main(String args[]) {
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
}
