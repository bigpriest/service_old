package makert.makert_demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import makert.makert_demo.entity.Role;
import makert.makert_demo.entity.User;
import makert.makert_demo.entity.UserRole;
import makert.makert_demo.service.UserService;
import makert.makert_demo.util.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/userInfo")
public class UserController {

    private ModelAndView modelAndView;
    @Autowired
    private UserService userService;

    @RequiresPermissions("select")
    @GetMapping(value = "/allPerson")
    public ModelAndView AllPerson(String usercode, String username, HttpSession session,
                                  @RequestParam(name="currentPage",defaultValue = "1")int currentPage){
        modelAndView = new ModelAndView();
        int totalCount = userService.countInfo(usercode, username);
//        System.out.println(currentPage+"----------"+totalCount+":"+usercode+":"+username);
        Page page = new Page(currentPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<User> users = userService.personInfo(usercode, username,startRow, endRow);
        //把room表的一些信息传到前端
        session.setAttribute("user_code",usercode);
        session.setAttribute("user_name",username);
        /*--------------把数据传到前端--------------*/
        modelAndView.addObject("lists", users);
        modelAndView.addObject("currentPage", page.getCurrPage());   //当前页
        modelAndView.addObject("totalCount", page.getTotalCount());   //总记录数
        modelAndView.addObject("totalPage", page.getTotalPage());   //总页数
        modelAndView.setViewName("user_list");
        return modelAndView;
    }


    @GetMapping(value = "/all/{username}")
    public ModelAndView AllInfo(@PathVariable("username") String username){
        modelAndView = new ModelAndView("user_edit");
        User user = userService.getAllInfo(username);
        modelAndView.addObject("list", user);
        return modelAndView;
    }

    @ResponseBody
    @RequiresPermissions("select")
    @PostMapping(value = "/update")
    public Map<String,String> UpdateInfo(User user,HttpSession session){
        Map<String,String> map = new HashMap<>();
        userService.updateInfo(user);
        session.setAttribute("flag","false");
        map.put("result","success");
        return map;
    }

    @GetMapping(value = "/updatePwd/{usercode}")
    public ModelAndView updatePwd(@PathVariable("usercode") String usercode){
        modelAndView = new ModelAndView("pwd_edit");
        modelAndView.addObject("usercode", usercode);
        return modelAndView;
    }

    @ResponseBody
    @PostMapping(value = "/savePwd")
    public Map<String,String> savePwd(String usercode, String pwd){
//        System.out.println(usercode+":"+pwd);
        Map<String,String> map = new HashMap<>();
        try{
            ByteSource salt = ByteSource.Util.bytes(usercode);
            String user_pwd = new SimpleHash("MD5", pwd, salt, 64).toHex();
//            System.out.println(user_pwd+"-------------------");
            userService.updatePWD(usercode, user_pwd);
            map.put("result", "success");
        }catch (Exception e){
            map.put("result", "fail");
        }
        return map;
    }

    @RequiresRoles(value = {"admin","ordinary"}, logical = Logical.OR)
    @GetMapping(value = "/edit/{user_code}")
    public ModelAndView editInfo(@PathVariable("user_code")String code){
        modelAndView = new ModelAndView("user_edit");
        User user = userService.getAllInfo(code);
        modelAndView.addObject("list", user);
        return modelAndView;
    }

    @RequiresRoles(value = {"admin","ordinary"}, logical = Logical.OR)
    @GetMapping(value = "/save")
    public ModelAndView saveInfo(String user_code){
        modelAndView = new ModelAndView("user_edit");
        User user = userService.getAllInfo(user_code);
        modelAndView.addObject("list", user);
        return modelAndView;
    }

    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/delete")
    public Map<String,String> deleteInfo(int[] check){
        Map<String,String> map = new HashMap<>();
        try {
            userService.deleteRole(check);
            userService.deleteInfo(check);
            map.put("result","success");
        }catch (Exception e){
            map.put("result", "fail");
        }
        return map;
    }

    @GetMapping(value = "/role")
    public ModelAndView searchRole(String usercode, String role,
                                   @RequestParam(name = "currentPage",defaultValue = "1") int currentPage,
                                   HttpSession session){
        modelAndView = new ModelAndView();
        int totalCount = userService.getCount(usercode,role);
        Page page = new Page(currentPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
//        System.out.println(currentPage+":"+startRow+":"+endRow);
        List<UserRole> userRoles = userService.allRole(usercode,role,startRow, endRow);
        //把一些信息传到前端
        session.setAttribute("usercode",usercode);
        session.setAttribute("role",role);
        /*--------------把数据传到前端--------------*/
        modelAndView.addObject("currentPage",page.getCurrPage());   //当前页
        modelAndView.addObject("totalCount",page.getTotalCount());   //总记录数
        modelAndView.addObject("totalPage",page.getTotalPage());   //总页数
        modelAndView.addObject("list", userRoles);
        modelAndView.setViewName("user_role");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping(value = "/changePermission")
    public Map<String,String> changePer(int status,String usercode, String permission){
        Map<String,String> map = new HashMap<>();
        try{
            userService.setStatus(status, usercode, permission);
            map.put("result", "success");
        }catch (Exception e){}
        return map;
    }

    @ResponseBody
    @PostMapping(value = "/deleteRole")
    public Map<String,String> deleteRole(@RequestParam("rid") int[] check,@RequestParam("usercode")String usercode,@RequestParam("permission")String permission){
        Map<String,String> map = new HashMap<>();
//        for (int i = 0; i < check.length; i++) {
//            System.out.println(check[i]+":"+usercode+":"+permission);
//        }
        try {
            userService.deleteRole_Per(check,usercode,permission);
            map.put("result","success");
        }catch (Exception e){
            map.put("result", "fail");
        }
        return map;
    }

    @RequiresRoles(value = "admin")
    @GetMapping(value = "/addrole")
    public ModelAndView addRole(){
        modelAndView = new ModelAndView("role_add");
        modelAndView.addObject("roles",userService.getRole());
        modelAndView.addObject("codes", userService.getCode());
        return modelAndView;
    }

    @ResponseBody
    @PostMapping(value = "/insertRole")
    public Map<String,String> insertRole(@RequestParam("usercode") String usercode, @RequestParam("rolename")String rolename, @RequestParam("operation")String[] operation, @RequestParam("remark")String remark){
        Map<String,String> map = new HashMap<>();
        try {
            for (String permission : operation) {
//                System.out.println(usercode+":"+rolename+":"+permission+":"+remark);
                userService.insertRole(usercode, rolename, permission, remark);
            }
//            System.out.println("------------------------");
            map.put("result", "success");
        }catch (Exception e){
//            System.out.println("+++++++++++++++++++++++++++");
            map.put("result","fail");
        }
        return map;
    }
}