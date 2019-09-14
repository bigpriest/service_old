package makert.makert_demo.controller;

import makert.makert_demo.entity.User;
import makert.makert_demo.service.LoginService;
import makert.makert_demo.service.RegisterService;
import makert.makert_demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/larController")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;
    UsernamePasswordToken token;
    ModelAndView mView;

    @RequestMapping(value = "/log")
    public ModelAndView Index(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logo")
    public ModelAndView logo(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/welcome")
    public ModelAndView Welcome(){
        return new ModelAndView("welcome");
    }

    @RequestMapping(value = "/error")
    public ModelAndView Error(){
        return new ModelAndView("error");
    }

    @GetMapping(value = "/logout")
    public ModelAndView checkUser(){
        try{
            SecurityUtils.getSubject().logout();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("login");
    }

    @PostMapping(value="/login")
    public ModelAndView submitLogin(HttpSession session, String username, String password) {
        mView = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        String flag = "false";
        try{
            token = new UsernamePasswordToken(username, password);
            subject.login(token);
            session.setAttribute("username", username);
            User user = userService.getAllInfo(username);
            if(user.getAddress()==null || user.getBirthday()==null || user.getGender()==null || user.getTelphone()==null){
                flag = "true";
            }else {
                flag = "false";
            }
            mView.setViewName("index");
            session.setAttribute("flag", flag);
            mView.addObject("result", "success");
        } catch (Exception e) {
            mView.addObject("result", "fail");
            mView.setViewName("login");
        }
        return mView;
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public Map<String, String> submitRegister(String usercode, String pwd){
        Map<String,String> map = new HashMap<>();
        System.out.println(usercode+":"+ pwd);
        boolean flag = false;
        try {
            flag = registerService.registerUser(usercode, pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(flag == true){
           map.put("result", "注册成功");
        }else {
            map.put("result", "注册失败！用户已存在");
        }
        return map;
    }
}