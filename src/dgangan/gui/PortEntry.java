package dgangan.gui;

import java.util.List;

public class PortEntry {

    private String name;

    private String description;
    private String status;
    private String speed;
    private String duplex;

    public PortEntry(String name, String description, String status, String duplex, String speed) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.speed = speed;
        this.duplex = duplex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setDuplex(String duplex) {
        this.duplex = duplex;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDuplex() {
        return duplex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getSpeedPassed(List<String> speedValues){
        return !speedValues.contains(getSpeed());
    }

    public boolean getDuplexPassed(){
        return !getDuplex().endsWith("half");
    }

    public boolean getPassed(List<String> speedValues){
        return getSpeedPassed(speedValues) && getDuplexPassed();
    }
}
