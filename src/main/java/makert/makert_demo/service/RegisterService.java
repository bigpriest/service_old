package makert.makert_demo.service;

import makert.makert_demo.dao.RegisterMapper;
import makert.makert_demo.dao.UserMapper;
import makert.makert_demo.entity.User;
import makert.makert_demo.entity.UserPer;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    RegisterMapper registerMapper;
    @Autowired
    UserMapper userMapper;

    public boolean registerUser(String usercode, String password) throws Exception{
        ByteSource salt = ByteSource.Util.bytes(usercode);
        String user_pwd = new SimpleHash("MD5", password, salt, 64).toHex();
        List<User> map = registerMapper.queryAllUser(usercode);
        Date createTime = new Date(System.currentTimeMillis());
        if(map.isEmpty()){
            User user = new User();
            user.setUsercode(usercode);
            user.setPassword(user_pwd);
            user.setRole(2);
            user.setCreateTime(createTime);
            registerMapper.registerUser(user);
            UserPer userPer = new UserPer();
            userPer.setPid(4);
            userPer.setRid(2);
            userPer.setUid(userMapper.searchUid(usercode));
            userPer.setStatus(1);
            userPer.setRemark("具有查询权限");
            userMapper.insertRole(userPer);
            return true;
        }
        return false;
    }
}
