package Grepolis;

import javax.swing.*;
import java.util.Hashtable;

/**
 * @Author Brandon
 * Created by Brandon on 8/1/2016.
 * Time: 6:30 PM
 */
public class Researches {
    private ResearchType researchType;
    private boolean researched;

    public boolean isResearched() {
        return researched;
    }

    public void setResearched(boolean researched) {
        this.researched = researched;
    }

    public ResearchType getResearchType() {
        return researchType;
    }

    public void setResearchType(ResearchType researchType) {
        this.researchType = researchType;
    }


    public enum ResearchType {
        diplomacy("Diplomacy", 4, 3, 100, 400, 200), //Don't remove. Isn't in current research list, but it is checked!
        slinger("Slinger", 1, 4, 300, 500, 200),
        archer("Archer", 1, 8, 550, 100, 400),
        town_guard("City guard", 1, 3, 400, 300, 300),
        hoplite("Hoplites", 4, 8, 600, 200, 850),
        meteorology("Meteorology", 4, 4, 2500, 1700, 6500),
        espionage("Espionage", 7, 3, 900, 900, 1100),
        booty("Villagers' loyalty", 7, 6, 1300, 1300, 1300),
        pottery("Ceramics", 7, 4, 700, 1500, 900),
        rider("Horsemen", 10, 8, 1400, 700, 1800),
        architecture("Architecture", 10, 6, 1900, 2100, 1300),
        instructor("Trainer", 10, 4, 800, 1300, 1600),
        bireme("Bireme", 13, 8, 2800, 1300, 2200),
        building_crane("Crane", 13, 4, 3000, 1800, 1400),
        shipwright("Shipwright", 13, 6, 5000, 2000, 3900),
        chariot("Chariots", 16, 8, 3700, 1900, 2800),
        attack_ship("Light ship", 16, 8, 4400, 2000, 2400),
        conscription("Conscription", 16, 4, 3800, 4200, 6000),
        demolition_ship("Fire ship", 19, 8, 5300, 2600, 2700),
        catapult("Catapult", 19, 8, 5500, 2900, 3600),
        cryptography("Cryptography", 19, 6, 2500, 3000, 5100),
        democracy("Democracy", 19, 6, 3100, 3100, 4100),
        colonize_ship("Colony ship", 22, 0, 7500, 7500, 9500),
        small_transporter("Fast transport ship", 22, 8, 6500, 2800, 3200),
        plow("Plow", 22, 4, 3000, 3300, 2100),
        berth("Bunks", 22, 6, 8900, 5200, 7800),
        trireme("Trireme", 25, 8, 6500, 3800, 4700),
        phalanx("Phalanx", 25, 9, 4000, 4000, 15000),
        breach("Breakthrough", 25, 6, 8000, 8000, 9000),
        mathematics("Mathematics", 25, 6, 7100, 4400, 8600),
        ram("Battering ram", 28, 10, 7900, 9200, 14000),
        cartography("Cartography", 28, 8, 10000, 6700, 12500),
        take_over("Conquest", 28, 0, 12000, 120000, 16000),
        stone_storm("Stone Hail", 31, 4, 8500, 5900, 6600),
        temple_looting("Temple looting", 31, 6, 9200, 5300, 10000),
        divine_selection("Divine selection", 31, 10, 10000, 8000, 12000),
        combat_experience("Battle experience", 34, 6, 9800, 11400, 14200),
        strong_wine("Strong wine", 34, 4, 8000, 6500, 11000),
        set_sail("Set sail", 34, 8, 13000, 9700, 15500);

        String name;
        int academyLevelRequired;
        int researchPointsRequired;
        int wood;
        int stone;
        int iron;


        ResearchType(String name,int academyLevelRequired, int researchPointsRequired, int wood, int stone, int iron) {
            this.name = name;
            this.academyLevelRequired = academyLevelRequired;
            this.researchPointsRequired = researchPointsRequired;
            this.wood = wood;
            this.stone = stone;
            this.iron = iron;
        }
    }

}
