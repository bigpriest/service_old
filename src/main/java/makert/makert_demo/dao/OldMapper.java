package makert.makert_demo.dao;

import makert.makert_demo.entity.Oldman;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OldMapper {
    //查找所有老人的信息
    List<Oldman> oldInfo(@Param("old_code")String old_code, @Param("old_name")String old_name,
                                @Param("roomid")int roomid, @Param("startRow")int startRow,
                                @Param("endRow")int endRow);
    //删除老人的信息
    void deleteold(int id);

    //添加老人的信息
    void addoldmaninfo(Oldman oldman);

    //根据老人编号检查是否存在
    Oldman findbyoldcode(String old_code);

    //根据id回信息
    Oldman findbyoldid(int id);

    //修改老人信息
    void editoldinfo(Oldman oldman);

    //按条件或查询所有老人的数量
    int findoldcount(@Param("old_code") String old_code, @Param("old_name") String old_name, @Param("roomid") int roomid);
    //为了外表拿到老人信息
    List<Oldman> forgetoldname();
    //为了获得老人名字
    Oldman findoldname(int id);

    int getPeoCount(int roomid);

    void setPeoCount(@Param("peoNum") int peoNum, @Param("room_id") int room_id);
}
