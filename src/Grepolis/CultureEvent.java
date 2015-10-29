package Grepolis;

/**
 * @Author Brandon
 * Created by Brandon on 10/11/2015.
 * Time: 5:34 PM
 */
public class CultureEvent {
    private CultureEventType cultureEventType;
    private boolean enabled;
    private boolean eventRunning;
    private boolean enoughResources;
    private boolean canStart;

    public CultureEvent(CultureEventType cultureEventType) {
        this.cultureEventType = cultureEventType;
    }

    public boolean canStart() {
        return enabled && canStart;
    }

    public boolean isEventRunning() {
        return eventRunning;
    }

    @Deprecated
    public void setEventRunning(boolean eventRunning) {
        this.eventRunning = eventRunning;
    }

    @Deprecated
    public void setEnoughResources(boolean enoughResources) {
        this.enoughResources = enoughResources;
    }

    public CultureEventType getCultureEventType() {
        return cultureEventType;
    }

    public void setCultureEventType(CultureEventType cultureEventType) {
        this.cultureEventType = cultureEventType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }


    public enum CultureEventType {
        party("City festival"),
        games("Olympic games"),
        triumph("Victory procession"),
        theater("Theater");

        String inGameName;

        CultureEventType(String inGameName) {
            this.inGameName = inGameName;
        }
    }
}
