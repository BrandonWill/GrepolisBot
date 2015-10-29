package Grepolis;

/**
 * @Author Brandon
 * Created by Brandon on 10/16/2015.
 * Time: 5:50 PM
 */
public class FarmingVillage {
    private int id;
    private String name;
    private int stage; //farming village level
    private int mood;
    private boolean lootable_human; //can loot again
    private boolean loot; //true if the village is NOT owned. Not true for 2.97
    private boolean rel; //is 1 if it's owned by the player
    private boolean canFarm;

    public boolean isLootable_human() {
        return lootable_human;
    }

    public void setLootable_human(boolean lootable_human) {
        this.lootable_human = lootable_human;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setLoot(boolean loot) {
        this.loot = loot;
    }

    public boolean isLoot() {
        return loot;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public boolean isCanFarm() {
        return canFarm;
    }

    public void setCanFarm(boolean canFarm) {
        this.canFarm = canFarm;
    }
}
