package Grepolis;

import static Grepolis.util.MyLogger.log;

/**
 * @Author Brandon
 * Created by Brandon on 10/16/2015.
 * Time: 5:50 PM
 */
public class FarmingVillage {
    private int battlePointFarmID = 0; //specifically given to these villages!
    private int farm_town_id = 0;
    private String name;
    private int stage; //farming village level
    private int mood;
    private boolean lootable_human; //The time we can loot at again in regular time.
    private long loot = 0; //The time to loot in unix time!
    private int resourcesLooted;
    private boolean rel; //is 1 if it's owned by the player
    private boolean canFarm;
    private boolean bootyResearched;
    private Farming.IntervalToFarm intervalToFarm;
    private long lootcap_reset_at;
    private int island_x;
    private int island_y;

    public FarmingVillage(boolean bootyResearched, Farming.IntervalToFarm intervalToFarm) {
        this.bootyResearched = bootyResearched;
        this.intervalToFarm = intervalToFarm;
    }

    public boolean isLootable_human() {
        return lootable_human;
    }

    public void setLootable_human(boolean lootable_human) {
        this.lootable_human = lootable_human;
    }

    public int getFarm_town_id() {
        //Attempt to salvage how horrible their code is.
        if (farm_town_id == 0 && battlePointFarmID != 0) {
            return battlePointFarmID;
        }
        return farm_town_id;
    }

    public void setFarm_town_id(int farm_town_id) {
        this.farm_town_id = farm_town_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setLoot(long loot) {
        this.loot = loot;
    }

    public long getLoot() {
        return loot;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public boolean canFarm() {
        if (Farming.captainEnabled) {
            return canFarm;
        } else {
            if (rel) {
                if (battlePointFarmID != 0) {
                    int nextLootAmount = resourcesLooted + amountWeLoot(intervalToFarm);
//                    log("Village (" + getName() + ") can loot (" + (GrepolisBot.getServerUnixTime() >= loot && (getMaxLoot() - nextLootAmount >= amountWeLoot(intervalToFarm))) + "): " + getMaxLoot() + "- " + nextLootAmount + ">=  " + amountWeLoot(intervalToFarm));
//                    log("Village (" + getName() + ") can loot (" + (GrepolisBot.getServerUnixTime() >= loot && (getMaxLoot() - nextLootAmount >= amountWeLoot(intervalToFarm))) + "):Calculated by (village level:" +stage + "* 1000 * " + Farming.getGameSpeed() + "(game server speed))=" + getMaxLoot() + "- " + "(my calculated next loot amount):" + nextLootAmount + ">=  (my calculated next amount that we loot) " + amountWeLoot(intervalToFarm));
                    return GrepolisBot.getServerUnixTime() >= loot && (getMaxLoot() - nextLootAmount >= amountWeLoot(intervalToFarm));
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public long getLootcap_reset_at() {
        return lootcap_reset_at;
    }

    public void setLootcap_reset_at(long lootcap_reset_at) {
        this.lootcap_reset_at = lootcap_reset_at;
    }

    public void setCanFarm(boolean canFarm) {
        this.canFarm = canFarm;
    }

    public int getBattlePointFarmID() {
        return battlePointFarmID;
    }

    public void setBattlePointFarmID(int battlePointFarmID) {
        this.battlePointFarmID = battlePointFarmID;
    }

    public double getMaxLoot() {
        switch (stage) {
            case 1:
                return 2500 * Farming.getGameSpeed();

            case 2:
                return 3000 * Farming.getGameSpeed();

            case 3:
                return 3500 * Farming.getGameSpeed();

            case 4:
                return 4000 * Farming.getGameSpeed();

            case 5:
                return 4500 * Farming.getGameSpeed();

            case 6:
                return 5000 * Farming.getGameSpeed();

            default:
                return 0;
        }
    }

    public int amountWeLoot(Farming.IntervalToFarm intervalToFarm) {
        if (battlePointFarmID != 0) {
            if (!bootyResearched) {
                if (intervalToFarm.option == 1) {
                    switch (stage) {
                        case 1:
                            return (int) (13 * Farming.getGameSpeed());

                        case 2:
                            return (int) (16 * Farming.getGameSpeed());

                        case 3:
                            return (int) (18 * Farming.getGameSpeed());

                        case 4:
                            return (int) (21 * Farming.getGameSpeed());

                        case 5:
                            return (int) (24 * Farming.getGameSpeed());

                        case 6:
                            return (int) (26 * Farming.getGameSpeed());
                    }
                } else if (intervalToFarm.option == 2) {
                    switch (stage) {
                        case 1:
                            return (int) (31 * Farming.getGameSpeed());

                        case 2:
                            return (int) (36 * Farming.getGameSpeed());

                        case 3:
                            return (int) (41 * Farming.getGameSpeed());

                        case 4:
                            return (int) (47 * Farming.getGameSpeed());

                        case 5:
                            return (int) (52 * Farming.getGameSpeed());

                        case 6:
                            return (int) (57 * Farming.getGameSpeed());
                    }
                } else if (intervalToFarm.option == 3) {
                    switch (stage) {
                        case 1:
                            return (int) (68 * Farming.getGameSpeed());

                        case 2:
                            return (int) (79 * Farming.getGameSpeed());

                        case 3:
                            return (int) (91 * Farming.getGameSpeed());

                        case 4:
                            return (int) (102 * Farming.getGameSpeed());

                        case 5:
                            return (int) (113 * Farming.getGameSpeed());

                        case 6:
                            return (int) (124 * Farming.getGameSpeed());
                    }
                } else if (intervalToFarm.option == 4) {
                    switch (stage) {
                        case 1:
                            return (int) (133 * Farming.getGameSpeed());

                        case 2:
                            return (int) (155 * Farming.getGameSpeed());

                        case 3:
                            return (int) (177 * Farming.getGameSpeed());

                        case 4:
                            return (int) (200 * Farming.getGameSpeed());

                        case 5:
                            return (int) (222 * Farming.getGameSpeed());

                        case 6:
                            return (int) (244 * Farming.getGameSpeed());
                    }
                }
            } else {
                if (intervalToFarm.option == 1) {
                    switch (stage) {
                        case 1:
                            return (int) (28 * Farming.getGameSpeed());

                        case 2:
                            return (int) (34 * Farming.getGameSpeed());

                        case 3:
                            return (int) (40 * Farming.getGameSpeed());

                        case 4:
                            return (int) (45 * Farming.getGameSpeed());

                        case 5:
                            return (int) (51 * Farming.getGameSpeed());

                        case 6:
                            return (int) (57 * Farming.getGameSpeed());
                    }
                } else if(intervalToFarm.option == 2) {
                    switch (stage) {
                        case 1:
                            return (int) (66 * Farming.getGameSpeed());

                        case 2:
                            return (int) (78 * Farming.getGameSpeed());

                        case 3:
                            return (int) (89 * Farming.getGameSpeed());

                        case 4:
                            return (int) (101 * Farming.getGameSpeed());

                        case 5:
                            return (int) (112 * Farming.getGameSpeed());

                        case 6:
                            return (int) (124 * Farming.getGameSpeed());
                    }
                } else if(intervalToFarm.option == 3) {
                    switch (stage) {
                        case 1:
                            return (int) (148 * Farming.getGameSpeed());

                        case 2:
                            return (int) (171 * Farming.getGameSpeed());

                        case 3:
                            return (int) (195 * Farming.getGameSpeed());

                        case 4:
                            return (int) (219 * Farming.getGameSpeed());

                        case 5:
                            return (int) (243 * Farming.getGameSpeed());

                        case 6:
                            return (int) (267 * Farming.getGameSpeed());
                    }
                } else if(intervalToFarm.option == 4) {
                    switch (stage) {
                        case 1:
                            return (int) (286 * Farming.getGameSpeed());

                        case 2:
                            return (int) (334 * Farming.getGameSpeed());

                        case 3:
                            return (int) (382 * Farming.getGameSpeed());

                        case 4:
                            return (int) (430 * Farming.getGameSpeed());

                        case 5:
                            return (int) (477 * Farming.getGameSpeed());

                        case 6:
                            return (int) (525 * Farming.getGameSpeed());
                    }
                }
            }
        }
        return 0;
    }

    public int getIsland_x() {
        return island_x;
    }

    public void setIsland_x(int island_x) {
        this.island_x = island_x;
    }

    public int getIsland_y() {
        return island_y;
    }

    public void setIsland_y(int island_y) {
        this.island_y = island_y;
    }

    public int getResourcesLooted() {
        return resourcesLooted;
    }

    public void setResourcesLooted(int resourcesLooted) {
        this.resourcesLooted = resourcesLooted;
    }
}
