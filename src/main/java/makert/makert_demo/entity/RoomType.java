package makert.makert_demo.entity;

import java.util.Date;

public class RoomType {
    private int id;
    private String roomtypecode;
    private String roomtype;
    private String description;
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomtypecode() {
        return roomtypecode;
    }

    public void setRoomtypecode(String roomtypecode) {
        this.roomtypecode = roomtypecode;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
