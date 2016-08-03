package Grepolis;

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
    private int resourcedLooted = 0;
    private boolean rel; //is 1 if it's owned by the player
    private boolean canFarm;
    private boolean bootyResearched;
    private Farming.IntervalToFarm intervalToFarm;

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
            if (loot > 0) {
                if (battlePointFarmID != 0) {
                    int nextLootAmount = resourcedLooted + amountWeLoot(intervalToFarm);
                    if (getMaxLoot() - nextLootAmount >= nextLootAmount) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
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

    public int getMaxLoot() {
        return stage * 1000 * Farming.getGameSpeed();
    }

    public int amountWeLoot(Farming.IntervalToFarm intervalToFarm) {
        if (battlePointFarmID != 0) {
            if (bootyResearched) {
                if (intervalToFarm.option == 1) {
                    switch (stage) {
                        case 1:
                            return 12 * Farming.getGameSpeed();

                        case 2:
                            return 15 * Farming.getGameSpeed();

                        case 3:
                            return 17 * Farming.getGameSpeed();

                        case 4:
                            return 20 * Farming.getGameSpeed();

                        case 5:
                            return 22 * Farming.getGameSpeed();

                        case 6:
                            return 12 * Farming.getGameSpeed();
                    }
                } else if (intervalToFarm.option == 2) {
                    switch (stage) {
                        case 1:
                            return 29 * Farming.getGameSpeed();

                        case 2:
                            return 34 * Farming.getGameSpeed();

                        case 3:
                            return 39 * Farming.getGameSpeed();

                        case 4:
                            return 44 * Farming.getGameSpeed();

                        case 5:
                            return 49 * Farming.getGameSpeed();

                        case 6:
                            return 54 * Farming.getGameSpeed();
                    }
                } else if (intervalToFarm.option == 3) {
                    switch (stage) {
                        case 1:
                            return 64 * Farming.getGameSpeed();

                        case 2:
                            return 75 * Farming.getGameSpeed();

                        case 3:
                            return 85 * Farming.getGameSpeed();

                        case 4:
                            return 95 * Farming.getGameSpeed();

                        case 5:
                            return 106 * Farming.getGameSpeed();

                        case 6:
                            return 116 * Farming.getGameSpeed();
                    }
                } else if (intervalToFarm.option == 4) {
                    switch (stage) {
                        case 1:
                            return 125 * Farming.getGameSpeed();

                        case 2:
                            return 145 * Farming.getGameSpeed();

                        case 3:
                            return 166 * Farming.getGameSpeed();

                        case 4:
                            return 187 * Farming.getGameSpeed();

                        case 5:
                            return 208 * Farming.getGameSpeed();

                        case 6:
                            return 229 * Farming.getGameSpeed();
                    }
                }
            } else {
                if (intervalToFarm.option == 1) {
                    switch (stage) {
                        case 1:
                            return 25 * Farming.getGameSpeed();

                        case 2:
                            return 32 * Farming.getGameSpeed();

                        case 3:
                            return 36 * Farming.getGameSpeed();

                        case 4:
                            return 43 * Farming.getGameSpeed();

                        case 5:
                            return 47 * Farming.getGameSpeed();

                        case 6:
                            return 53 * Farming.getGameSpeed();
                    }
                } else if(intervalToFarm.option == 2) {
                    switch (stage) {
                        case 1:
                            return 62 * Farming.getGameSpeed();

                        case 2:
                            return 73 * Farming.getGameSpeed();

                        case 3:
                            return 83 * Farming.getGameSpeed();

                        case 4:
                            return 94 * Farming.getGameSpeed();

                        case 5:
                            return 105 * Farming.getGameSpeed();

                        case 6:
                            return 116 * Farming.getGameSpeed();
                    }
                } else if(intervalToFarm.option == 3) {
                    switch (stage) {
                        case 1:
                            return 137 * Farming.getGameSpeed();

                        case 2:
                            return 161 * Farming.getGameSpeed();

                        case 3:
                            return 182 * Farming.getGameSpeed();

                        case 4:
                            return 204 * Farming.getGameSpeed();

                        case 5:
                            return 227 * Farming.getGameSpeed();

                        case 6:
                            return 249 * Farming.getGameSpeed();
                    }
                } else if(intervalToFarm.option == 4) {
                    switch (stage) {
                        case 1:
                            return 268 * Farming.getGameSpeed();

                        case 2:
                            return 311 * Farming.getGameSpeed();

                        case 3:
                            return 356 * Farming.getGameSpeed();

                        case 4:
                            return 402 * Farming.getGameSpeed();

                        case 5:
                            return 447 * Farming.getGameSpeed();

                        case 6:
                            return 492 * Farming.getGameSpeed();
                    }
                }
            }
        }
        return 0;
    }

    public int getResourcedLooted() {
        return resourcedLooted;
    }

    public void setResourcedLooted(int resourcedLooted) {
        this.resourcedLooted = resourcedLooted;
    }
}
