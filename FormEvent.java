package siteSurvey;

import java.util.EventObject;

public class FormEvent extends EventObject {
    private String tower;
    private String tech;
    private String snrT;
    private String snrC;
    private String rssiT;
    private String rssiC;
    private String down;
    private String up;
    private String name;
    private String addr;
    private String date;
    
    public FormEvent(Object source) {
        super(source);
    }
    public FormEvent(Object source, String tower, String tech, String snrT, String snrC, 
                     String rssiT, String rssiC, String down, String up) {
        super(source);
        this.tower = tower;
        this.tech = tech;
        this.snrT = snrT;
        this.snrC = snrC;
        this.rssiT = rssiT;
        this.rssiC = rssiC;
        this.down = down;
        this.up = up;
    }
    public FormEvent(Object source, String name, String addr, String date) {
        super(source);
        this.name = name;
        this.addr = addr;
        this.date = date;
    }
    public String getTower() {
        return tower;
    }
    public String getTech() {
        return tech;
    }
    public String getSnrT() {
        return snrT;
    }
    public String getSnrC() {
        return snrC;
    }
    public String getRssiT() {
        return rssiT;
    }
    public String getRssiC() {
        return rssiC;
    }
    public String getDown() {
        return down;
    }
    public String getUp() {
        return up;
    }
    public String getName() {
        return name;
    }
    public String getAddr() {
        return addr;
    }
    public String getDate() {
        return date;
    }
}
