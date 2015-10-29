package Grepolis;

/**
 * @Author Brandon
 * Created by Brandon on 10/10/2015.
 * Time: 8:34 PM
 */
public class BarracksUnit {
    private UnitType unitType;
    private int totalTroops = 0;
    private int inQueue = 0;
    private int populationRequired;
    private int favorRequired;
    private int maxBuild;
    private int buildTo = 0;


    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public int getTotalTroops() {
        return totalTroops;
    }

    public void setTotalTroops(int totalTroops) {
        this.totalTroops = totalTroops;
    }

    public int getInQueue() {
        return inQueue;
    }

    public void setInQueue(int inQueue) {
        this.inQueue = inQueue;
    }

    public int getTotalCountOfTroops() {
        return totalTroops+inQueue;
    }

    public int getPopulationRequired() {
        return populationRequired;
    }

    public void setPopulationRequired(int populationRequired) {
        this.populationRequired = populationRequired;
    }

    public int getFavorRequired() {
        return favorRequired;
    }

    public void setFavorRequired(int favorRequired) {
        this.favorRequired = favorRequired;
    }

    public int getMaxBuild() {
        return maxBuild;
    }

    public void setMaxBuild(int maxBuild) {
        this.maxBuild = maxBuild;
    }

    public int getBuildTo() {
        return buildTo;
    }

    public void setBuildTo(int buildTo) {
        this.buildTo = buildTo;
    }

    public boolean shouldBuild() {
        return getTotalCountOfTroops() < buildTo && maxBuild > 0;
    }

    public int amountToBuild() {
        return getTotalCountOfTroops() + maxBuild < buildTo ? maxBuild : buildTo-getTotalCountOfTroops();
    }

    public enum UnitType {
        sword("Swordsman"),
        slinger("Slinger"),
        archer("Archer"),
        hoplite("Hoplite"),
        rider("Horseman"),
        chariot("Chariot"),
        catapult("Catapult"),
        harpy("Harpy"),
        medusa("Medusa"),
        centaur("Centaur"),
        pegasus("Pegasus"),
        zyklop("Cyclop"),
        minotaur("Minotaur"),
        manticore("Manticore"),
        griffin("Griffin"),
        calydonian_boar("Calydonian boar"),
        cerberus("Cerberus"),
        fury("Erinys"),
        godsent("Divine envoy");

        String inGameName;

        UnitType(String inGameName) {
            this.inGameName = inGameName;
        }
    }
}
