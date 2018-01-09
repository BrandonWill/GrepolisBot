package Grepolis;

/**
 * @Author Brandon
 * Created by Brandon on 10/9/2015.
 * Time: 1:10 AM
 */
public class Building {
    private BuildingType buildingType;
    private String name;
    private String description;
    private int level = 0;
    private int currentLevel;
    private int nextLevel;
    private int buildTo = 0;
    private int populationRequired = 0;
    private boolean enough_resources;
    private boolean enough_storage;
    private boolean can_upgrade;
    private boolean enough_population;
    private boolean max_level;
    private boolean can_tear_down;


    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public boolean hasEnoughStorage() {
        return enough_storage;
    }

    public void setEnough_storage(boolean enough_storage) {
        this.enough_storage = enough_storage;
    }

    public boolean hasEnoughResources() {
        return enough_resources;
    }

    public void setEnough_resources(boolean enough_resources) {
        this.enough_resources = enough_resources;
    }

    public boolean canUpgrade() {
        return can_upgrade;
    }

    public void setCan_upgrade(boolean can_upgrade) {
        this.can_upgrade = can_upgrade;
    }

    public boolean hasEnoughPopulation() {
        return enough_population;
    }

    public void setEnough_population(boolean enough_population) {
        this.enough_population = enough_population;
    }

    public boolean isMaxLevel() {
        return max_level;
    }

    public void setMax_level(boolean max_level) {
        this.max_level = max_level;
    }

    public boolean canTearDown() {
        return can_tear_down;
    }

    public void setCan_tear_down(boolean can_tear_down) {
        this.can_tear_down = can_tear_down;
    }

    public int getBuildTo() {
        return buildTo;
    }

    public void setBuildTo(int buildTo) {
        this.buildTo = buildTo;
    }

    public int getPopulationRequired() {
        return populationRequired;
    }

    public void setPopulationRequired(int populationRequired) {
        this.populationRequired = populationRequired;
    }

    public enum BuildingType {
        academy("Academy", "You can conduct research at the academy. Grepolis offers you various technologies that can fortify your troops, make your construction skills more effective or give you new battle techniques. The higher the expansion level of your academy, the more technologies you can discover."),
        place("Agora", "At the agora you can get an overview of your troops inside and outside of your city. You can also admire the cultural achievements of your city here and plan your next campaigns with the battle simulator."),
        barracks("Barracks", "In the barracks you can recruit regular troops and mythical units. The higher the expansion level of the barracks, the more quickly you can train your troops."),
        hide("Cave", "In the cave spies can be assigned to scout enemy cities."),
        wall("City wall", "The city wall protects your polis against enemy troops. It fortifies your ground defense and increases your troops' defense value."),
        statue("Divine Statue", "The Greek gods are difficult to please and demand many signs of your worship. With this Divine statue you can show them how important they are to you and thus increase the Divine favor produced in the temple."),
        farm("Farm", "The farm provides your workers and troops with food. You have to expand the farm for your city to grow. The higher the expansion level of the farm, the more residents can be provided for - and the more troops you can feed."),
        docks("Harbor", "The harbor is where you can expand your naval fleet. You can construct naval transport units as well as naval combat units. The speed with which your naval units are constructed depends on the expansion level of the harbor."),
        library("Library", "Knowledge is power. By building a library, you will receive 12 additional research points that you can invest in the discovery of new technologies at the academy."),
        lighthouse("Lighthouse", "The Lighthouse of Alexandria was considered one of the seven famous World Wonders. By building a smaller version near the harbor, you increase your fleet's speed by 15%, since it greatly improves the navigation."),
        trade_office("Merchant's Shop", "This increases the maximum trade ratio of the Phoenician merchant and of farming villages as well as the available trade capacity per marketplace level from 500 to 750."),
        market("Marketplace", "The marketplace gives you the opportunity to send resources to other players. You can also trade with the farming villages on your island."),
        oracle("Oracle", "Enemy spies are uncovered with the help of the Oracle. This way you know who has spied on you and you can prepare yourself for impending attacks."),
        stoner("Quarry", "In the quarry your workers are extracting the stone that is important for your city's development. The higher its upgrade level, the greater the amount of stone that is extracted."),
        main("Senate", "In the Senate you can have new buildings erected or existing buildings expanded. The higher the Senate level, the more quickly the construction work is done."),
        ironer("Silver mine", "In the silver mine your workers are scraping the precious silver ore that is used to mint the silver coins. The higher the expansion level of the mine, the more silver coins can be minted."),
        temple("Temple", "At the temple you can worship a god of your choice. Each of the Grepolis gods can enact different Divine Powers and bring different mythical units to life. For both of these you will need a Divine favor that is created by the worshiped god in the temple. The higher the expansion level of the temple, the more Divine favors will be given to you by the gods."),
        theater("Theater", "By building a theater, you can present great dramas to your population and thus increase the cultural value of your polis."),
        thermal("Thermal Baths", "Great importance was placed on hygiene in Ancient Greece. By building thermal baths, you can increase your population's health and raise your population number by 10%."),
        tower("Tower", "A mighty guard tower increases the defensive strength of your troops by 10% and turns your polis into a nearly invincible stronghold against attackers."),
        lumber("Timber camp", "In the dense forests outside your polis, your lumberjacks are harvesting the massive amounts of lumber that is needed to expand your city and supply your troops. The higher the expansion level of the timber camp, the more usable wood is produced by your lumberjacks."),
        storage("Warehouse", "Your city's resources are stored in the warehouse. The higher the expansion level of the warehouse, the more resources can be stored there. Each expansion level protects 100 of each resource against being looted.");

        String inGameName;
        String description;

        BuildingType(String inGameName, String description) {
            this.inGameName = inGameName;
            this.description = description;
        }
    }
}
