package makert.makert_demo.dao;

import makert.makert_demo.entity.RoomType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomTypeMapper {
    //查询所有
    public List<RoomType> roomtypeinfo(@Param("roomtypecode") String roomtypecode, @Param("roomtype") String roomtype, @Param("startRow") int startRow, @Param("endRow") int endRow);
    //查询记录数
    public int findroomtypecount(@Param("roomtypecode") String roomtypecode, @Param("roomtype") String roomtype);
    //删除
    public void delroomtype(int id);
    //添加类型
    public void addroomtypesave(RoomType roomType);
    //根据id回显
    public RoomType findroomtypebyid(int id);
    //编辑
    public void editroomtypesave(RoomType roomType);
    public List<RoomType> findroomtype();
}
