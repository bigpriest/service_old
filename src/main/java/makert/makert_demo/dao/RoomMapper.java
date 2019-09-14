package makert.makert_demo.dao;

import makert.makert_demo.entity.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper {
    //查询所有
    List<Room> roominfo(@Param("room_code") String room_code, @Param("roomtype_id") int roomtype_id, @Param("startRow") int startRow, @Param("endRow") int endRow);
    //查询记录数
    int findroomcount(@Param("room_code") String room_code, @Param("roomtype_id") int roomtype_id);
    //删除
    void delroom(int id);
    //添加房间
    void addroom(Room room);
    //根据id查找
    Room findbyid(int id);
    //编辑
    void editroom(Room room);
    //连接外表使用
    List<Room> findroom();
}
