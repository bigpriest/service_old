package makert.makert_demo.entity;

public class Oldman {
    private int id;
    private String old_code;
    private String old_name;
    private int old_age;
    private int old_gender;
    private int disease_id;
    private Disease disease;
    private int roomid;
    private Room room;
    private int user_id;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOld_code() {
        return old_code;
    }

    public void setOld_code(String old_code) {
        this.old_code = old_code;
    }

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public int getOld_age() {
        return old_age;
    }

    public void setOld_age(int old_age) {
        this.old_age = old_age;
    }

    public int getOld_gender() {
        return old_gender;
    }

    public void setOld_gender(int old_gender) {
        this.old_gender = old_gender;
    }

    public int getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(int disease_id) {
        this.disease_id = disease_id;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
