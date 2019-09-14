package makert.makert_demo.entity;

import java.util.Date;

public class Health {
    private int id;
    private Double bloodpressure;       //血压
    private Double bloodsugar;          //血糖
    private Double bloodfat;            //血脂
    private Double temperature;         //体温
    private String eyes;                   //眼底
    private Double bone;                //骨密度
    private int oldid;                  //老人id
    private Oldman oldman;              //老人对象
    private Date time;                  //检查时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(Double bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public Double getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(Double bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public Double getBloodfat() {
        return bloodfat;
    }

    public void setBloodfat(Double bloodfat) {
        this.bloodfat = bloodfat;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public Double getBone() {
        return bone;
    }

    public void setBone(Double bone) {
        this.bone = bone;
    }

    public int getOldid() {
        return oldid;
    }

    public void setOldid(int oldid) {
        this.oldid = oldid;
    }

    public Oldman getOldman() {
        return oldman;
    }

    public void setOldman(Oldman oldman) {
        this.oldman = oldman;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
