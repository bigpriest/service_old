package makert.makert_demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class Shiro {

    @Test
    public void Test(){
        IniSecurityManagerFactory isma=new IniSecurityManagerFactory("classpath:shiro.ini");
        //通过工厂获取会话
        SecurityManager securityManager = (SecurityManager) isma.getInstance();
        //将会话管理器设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建主体
        Subject subject=SecurityUtils.getSubject();
        //创建令牌
        UsernamePasswordToken token=new UsernamePasswordToken("zhangsan","123456");
        //主体对令牌进行登录验证
        subject.login(token);
        //判断用户的认证状态，合法性
        System.out.println(subject.isAuthenticated());
    }
}
