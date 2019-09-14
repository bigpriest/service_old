package makert.makert_demo.shiro;

import makert.makert_demo.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * @param authenticationToken
     * @return 认证
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户名
        String username = (String) authenticationToken.getPrincipal();
        //数据库获取数据
        String user_pwd = null;
        try {
            //获取数据库中MD5加密后的密码
            user_pwd = loginService.checkUser(username);
            if (user_pwd == null || "".equals(user_pwd)) {
                throw new UnknownAccountException("用户名不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteSource bs = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user_pwd, bs, getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String usercode = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for(String role:loginService.getRoles(usercode)){
            roles.add(role);
            for(String permission:loginService.getPermission(usercode)){
//                System.out.println(permission+":::"+usercode);
                if(loginService.getStatus(usercode,permission) == 1){
                    permissions.add(permission);
//                    System.out.println(permission+":::");
                }
            }
        }
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    public static void main(String args[]){
        Md5Hash md5Hash = new Md5Hash("12345", "admin");
//        System.out.println(md5Hash.toString());

    }
}
