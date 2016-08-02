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
    private boolean rel; //is 1 if it's owned by the player
    private boolean canFarm;

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
                return GrepolisBot.getServerUnixTime() >= loot;
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
}
