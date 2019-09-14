package makert.makert_demo.entity;

import java.sql.Time;

public class Activity {
    private int id;
    private String dailyactive;
    private Time activetime;
    private String dailytip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDailyactive() {
        return dailyactive;
    }

    public void setDailyactive(String dailyactive) {
        this.dailyactive = dailyactive;
    }

    public Time getActivetime() {
        return activetime;
    }

    public void setActivetime(Time activetime) {
        this.activetime = activetime;
    }

    public String getDailytip() {
        return dailytip;
    }

    public void setDailytip(String dailytip) {
        this.dailytip = dailytip;
    }
}
