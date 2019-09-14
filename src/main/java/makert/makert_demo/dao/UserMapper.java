package makert.makert_demo.dao;

import makert.makert_demo.entity.User;
import makert.makert_demo.entity.UserPer;
import makert.makert_demo.entity.UserRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select({"<script>"+
            "select * from User"+
            "<where>"+
            "   <if test='usercode != null and usercode != &apos;&apos; '>"+
            "       and usercode like CONCAT('%',#{usercode},'%')"+
            "   </if>"+
            "   <if test='username != null and username != &apos;&apos; '>"+
            "       and username like CONCAT('%',#{username},'%')"+
            "   </if>"+
            "</where>"+
            "limit #{startRow},#{endRow}"+
            "</script>"})
    List<User> personInfo(@Param("usercode")String usercode, @Param("username")String username, @Param("startRow")int startRow, @Param("endRow")int endRow);

    @Select("select * from User")
    List<User> AllInfo();

    @Select("select * from User where usercode = #{usercode}")
    User getAllInfo(String usercode);

    @Update("update User set username = #{username}, gender = #{gender}, birthday = #{birthday}, telphone = #{telphone}, address = #{address}, age=year(from_days(datediff(now(), birthday))) where usercode = #{usercode}")
    void updateInfo(User user);

    @Select({"<script>"+
            "select count(*) from User"+
            "<where>"+
            "   <if test='usercode != null and usercode != &apos;&apos; '>"+
            "       and usercode like CONCAT('%',#{usercode},'%')"+
            "   </if>"+
            "   <if test='username != null and username != &apos;&apos; '>"+
            "       and username like CONCAT('%',#{username},'%')"+
            "   </if>"+
            "</where>"+
            "</script>"})
    int countInfo(@Param("usercode")String usercode, @Param("username")String username);

    @Delete("delete from User where id = #{id}")
    void deleteInfo(int id);

    @Select({"<script>"+
            "select (@id:=@id+1)id ,urp.* from urp,(select @id:=0) as id"+
            "<where>"+
            "   <if test='usercode != null and usercode != &apos;&apos; '>"+
            "       and usercode like CONCAT('%',#{usercode},'%')"+
            "   </if>"+
            "   <if test='role != null and role != &apos;&apos; '>"+
            "       and role like CONCAT('%',#{role},'%')"+
            "   </if>"+
            "</where>"+
            "limit #{startRow},#{endRow}"+
            "</script>"})
    List<UserRole> allRole(@Param("usercode")String usercode, @Param("role")String role, @Param("startRow")int startRow, @Param("endRow")int endRow);

    @Update("UPDATE role_permission rp " +
            "SET rp.`status`= #{status} " +
            "where (uid,rid) in (select id, role from user where usercode = #{usercode}) " +
            "and pid in(select id from permission where permission = #{permission}) ")
    int setStatus(@Param("status")int status, @Param("usercode")String usercode, @Param("permission")String permission);

    @Select({"<script>"+
            "select count(*) from urp"+
            "<where>"+
            "   <if test='usercode != null and usercode != &apos;&apos; '>"+
            "       and usercode like CONCAT('%',#{usercode},'%')"+
            "   </if>"+
            "   <if test='role != null and role != &apos;&apos; '>"+
            "       and role like CONCAT('%',#{role},'%')"+
            "   </if>"+
            "</where>"+
            "</script>"})
    int getCount(@Param("usercode")String usercode, @Param("role")String role);

    @Select("select rid from role where role = #{rolename}")
    int searchRid(@Param("rolename")String rolename);

    @Select("select id from permission where permission = #{permission}")
    int searchPid(@Param("permission")String permission);

    @Select("select id from user where usercode = #{usercode}")
    int searchUid(@Param("usercode")String usercode);

    @Select("select role from role")
    List<String> getRoles();

    @Select("select usercode from user")
    List<String> getCode();

    @Select("select count(*) from role_permission where uid=#{uid} and pid=#{pid} and rid=#{rid}")
    int isExist(@Param("uid") int uid, @Param("pid")int pid, @Param("rid")int rid);

    @Insert("insert into role_permission(uid, pid, rid, remark, status) values(#{uid},#{pid},#{rid},#{remark},#{status})")
    void insertRole(UserPer userPer);

    @Delete("delete from role_permission where uid = #{uid}")
    void deleteRole(@Param("uid") int uid);

    @Delete("delete from role_permission where rid=#{rid} and uid = #{uid} and pid = #{pid}")
    void deleteRole_Per(@Param("rid") int rid, @Param("uid")int uid, @Param("pid")int pid);

    @Update("update user set password = #{pwd} where usercode = #{usercode}")
    void updatePWD(@Param("usercode")String usercode, @Param("pwd")String pwd);
}
