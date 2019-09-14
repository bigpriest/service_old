package makert.makert_demo.service;

import makert.makert_demo.dao.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    LoginMapper loginMapper;

    public String checkUser(String usercode){
        return loginMapper.checkUser(usercode);
    }

    public List<String> getRoles(String usercode){
        return loginMapper.getRoles(usercode);
    }

    public List<String> getPermission(String usercode){
        return loginMapper.getPermission(usercode);
    }

    public int getStatus(String usercode, String permission){
        return loginMapper.getStatus(usercode,permission);
    };
}
