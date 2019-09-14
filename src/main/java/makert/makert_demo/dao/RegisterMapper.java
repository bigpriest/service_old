package makert.makert_demo.dao;

import makert.makert_demo.entity.User;
import makert.makert_demo.entity.UserPer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RegisterMapper {

    @Select("select usercode, username, password from User where usercode = #{usercode}")
    List<User> queryAllUser(String username) throws Exception;

    @Insert("insert into User(usercode, username, password, gender, birthday, remark, role, createTime) values(#{usercode}, #{username}, #{password}, #{gender}, #{birthday}, #{remark}, #{role}, #{createTime})")
    void registerUser(User user) throws Exception;

}
