package makert.makert_demo.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginMapper {

    @Select("select password from User where usercode = #{usercode}")
    String checkUser(String usercode);

    @Select("select role from urp where usercode = #{usercode}")
    List<String> getRoles(String usercode);

    @Select("select permission from urp where usercode = #{usercode}")
    List<String> getPermission(String usercode);

    @Select("select status from urp where permission = #{permission} and rid = (select role from user where usercode = #{usercode}) and usercode = #{usercode}")
    int getStatus(@Param("usercode")String usercode, @Param("permission")String permission);

}
