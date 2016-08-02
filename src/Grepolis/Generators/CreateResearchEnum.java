package Grepolis.Generators;

/**
 * @Author Brandon
 * Created by Brandon on 8/1/2016.
 * Time: 6:35 PM
 */
public class CreateResearchEnum {

    /**
     * Generates a new research enum, depending on the data given to us from the server. This is for developers only.
     * It sends it all to the command prompt, so it can be easily added and changed accordingly. Some things will have to be changed.
     * But it's a lot better than typing it all out by hand!
     * ---  NOTE ---- It is missing diplomacy
     * @param data the HTML outer document
     */
    public static void parseHTML(String data) {
        int beginningSubstring = data.indexOf("GameData.add({researches:");
        int endingSubstring = data.indexOf("GameData.add({farm_town_time_values:");
        data = data.substring(beginningSubstring, endingSubstring);

        data = data.replaceAll("\"", "");
        String[] splitData = data.split("name");

        String name = null;
        String researchPointsRequired = null;;
        String wood = null;;
        String stone = null;;
        String iron = null;;
        String id = null;;
        String academyLevelRequired = null;

        for (String researchData : splitData) {
            for (String dataType : researchData.split(",")) {
                if (dataType.startsWith(":")) {
                    name = dataType.split(":")[1];
                }
                if (dataType.contains("research_points")) {
                    researchPointsRequired = dataType.split(":")[1];
                }
                if (dataType.contains("wood")) {
                    wood = dataType.split(":")[2];
                }
                if (dataType.contains("stone")) {
                    stone = dataType.split(":")[1];
                }
                if (dataType.contains("iron")) {
                    iron = dataType.split(":")[1].replaceAll("}", "");
                }
                if (dataType.contains("id")) {
                    id = dataType.split(":")[1].replaceAll("}", "");
                }
                if (dataType.contains("academy")) {
                    academyLevelRequired = dataType.split(":")[2].replaceAll("}", "");
                }
            }
            System.out.println(id + "(\"" + name + "\"," +academyLevelRequired + ", " +researchPointsRequired + ", " + wood + ", " + stone + ", " + iron + "),");
        }
    }

}
