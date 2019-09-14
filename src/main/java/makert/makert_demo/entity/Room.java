package makert.makert_demo.entity;

import java.util.Date;

public class Room {
    private int id;
    private String room_code;
    private int roomtype_id;
    private RoomType roomType;
    private String room_address;
    private String room_descript;
    private int peoNum;
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public int getRoomtype_id() {
        return roomtype_id;
    }

    public void setRoomtype_id(int roomtype_id) {
        this.roomtype_id = roomtype_id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getRoom_address() {
        return room_address;
    }

    public void setRoom_address(String room_address) {
        this.room_address = room_address;
    }

    public String getRoom_descript() {
        return room_descript;
    }

    public void setRoom_descript(String room_descript) {
        this.room_descript = room_descript;
    }

    public int getPeoNum() {
        return peoNum;
    }

    public void setPeoNum(int peoNum) {
        this.peoNum = peoNum;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
