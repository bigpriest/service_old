package makert.makert_demo.service;

import makert.makert_demo.dao.UserMapper;
import makert.makert_demo.entity.User;
import makert.makert_demo.entity.UserPer;
import makert.makert_demo.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> personInfo(String usercode, String username, int startRow, int endRow) {
        return userMapper.personInfo(usercode,username, startRow, endRow);
    }

    public List<User> AllInfo(){
        return userMapper.AllInfo();
    }

    public User getAllInfo(String usercode){
        return userMapper.getAllInfo(usercode);
    }

    public void updateInfo(User user){
        userMapper.updateInfo(user);
    }

    public int countInfo(String usercode, String username){
        return userMapper.countInfo(usercode, username);
    }

    public void deleteInfo(int[] ids){
        for (int id:ids) {
            userMapper.deleteInfo(id);
            System.out.println(id+":=======");
        }
    }

    public List<UserRole> allRole(String usercode,String role,int startRow, int endRow){
        return userMapper.allRole(usercode, role, startRow, endRow);
    }

    public int setStatus(int status, String usercode, String permission){
        return userMapper.setStatus(status, usercode, permission);
    }

    public int getCount(String usercode, String role){
        return userMapper.getCount(usercode, role);
    }

    public List<String> getRole(){
        return userMapper.getRoles();
    }

    public List<String> getCode(){
        return userMapper.getCode();
    }

    public void insertRole(String usercode, String rolename, String permission, String remark){
        int uid = userMapper.searchUid(usercode);
        int pid = userMapper.searchPid(permission);
        int rid = userMapper.searchRid(rolename);
        System.out.println(uid+":"+pid+":"+rid);
        UserPer userPer = new UserPer();
        userPer.setPid(pid);
        userPer.setUid(uid);
        userPer.setRid(rid);
        userPer.setRemark(remark);
        userPer.setStatus(1);
        if(userMapper.isExist(uid, pid, rid) == 0){
            userMapper.insertRole(userPer);
        }
    }

    public void deleteRole(int[] ids){
        for (int id:ids) {
            userMapper.deleteRole(id);
        }
    }

    public void deleteRole_Per(int[] ids,String usercode, String permission){
        int pid = userMapper.searchPid(permission);
        int uid = userMapper.searchUid(usercode);
        for (int id:ids) {
            userMapper.deleteRole_Per(id,uid,pid);
        }
    }

    public void updatePWD(String usercode, String pwd){
        userMapper.updatePWD(usercode, pwd);
    }
}
