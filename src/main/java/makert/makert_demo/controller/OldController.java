package makert.makert_demo.controller;

import makert.makert_demo.dao.DiseaseMapper;
import makert.makert_demo.dao.OldMapper;
import makert.makert_demo.dao.RoomMapper;
import makert.makert_demo.entity.Disease;
import makert.makert_demo.entity.Oldman;
import makert.makert_demo.entity.Room;
import makert.makert_demo.entity.User;
import makert.makert_demo.service.UserService;
import makert.makert_demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/oldman")
public class OldController {
    @Resource
    OldMapper oldMapper;
    @Resource
    DiseaseMapper diseaseMapper;
    @Resource
    RoomMapper roomMapper;
    @Autowired
    UserService userService;

    //查询老人信息结合条件查询
    @GetMapping(value = "/test")
    public ModelAndView showOldInfo(String old_code, String old_name,
                                    @RequestParam(name = "roomid",defaultValue = "0") int roomid,
                                    @RequestParam(name = "currPage",defaultValue = "1") int currPage,
                                    HttpSession session){
        ModelAndView mv = new ModelAndView();
        //分页
        int totalCount = oldMapper.findoldcount(old_code,old_name,roomid);
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<Oldman> oldmanList = oldMapper.oldInfo(old_code,old_name,roomid,startRow, endRow);
        mv.addObject("oldmanList", oldmanList);
        //把room表的一些信息传到前端
        List<Room> roomList = roomMapper.findroom();
        session.setAttribute("roomlist",roomList);
        session.setAttribute("code",old_code);
        session.setAttribute("name",old_name);
        session.setAttribute("rid",roomid);
        /*--------------把数据传到前端--------------*/
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数
        mv.setViewName("old_list");
        return mv;
    }

    //老人删除
    @PostMapping("/delold")
    @ResponseBody
    public Map<String, String> delold(int[] check){
        Map<String,String> map = new HashMap<>();
        try {
            for (int i = 0; i < check.length; i++) {
                oldMapper.deleteold(check[i]);
            }
            map.put("result", "删除成功");
        }catch (Exception e){}
        return map;
    }

    //跳转添加页面
    @GetMapping("/addold")
    public ModelAndView tooldaddpage(){
        ModelAndView mv = new ModelAndView();
        List<Disease> diseaseList = diseaseMapper.findsisease();
        List<Room> roomList = roomMapper.findroom();
        List<User> userList = userService.AllInfo();
        mv.addObject("dlist",diseaseList);
        mv.addObject("rlist",roomList);
        mv.addObject("ulist",userList);
        mv.setViewName("old_add");
        return mv;
    }

    //保存添加信息
    @PostMapping("/addoldinfo")
    @ResponseBody
    public Map<String, String> addoldsave(Oldman oldman){
        Map<String,String> map = new HashMap<>();
        String code = oldman.getOld_code();
        int room_id = oldman.getRoomid();
        int peoNum = oldMapper.getPeoCount(room_id);
        if(peoNum > 0) {
            if (oldMapper.findbyoldcode(code) == null) {
                peoNum --;
                oldMapper.setPeoCount(peoNum, room_id);
                oldMapper.addoldmaninfo(oldman);
                map.put("result", "success");
            }
        }else{
            map.put("result", "fail");
        }
        return map;
    }

    //根据id回显要编辑的信息
    @GetMapping("/editold/{old_code}")
    public ModelAndView editoldman(@PathVariable("old_code") int id){
        ModelAndView mv = new ModelAndView();
        Oldman oldman = oldMapper.findbyoldid(id);
        List<Disease> diseaseList = diseaseMapper.findsisease();
        List<Room> roomList = roomMapper.findroom();
        List<User> userList = userService.AllInfo();
        mv.addObject("list",oldman);
        mv.addObject("dlist",diseaseList);
        mv.addObject("rlist",roomList);
        mv.addObject("ulist",userList);
        mv.setViewName("old_edit");
        return mv;
    }

    //保存修改
    @PostMapping("/editoldinfo")
    @ResponseBody
    public Map<String, String> editoldsave(Oldman oldman){
        Map<String, String> map = new HashMap<>();
        oldMapper.editoldinfo(oldman);
        map.put("result", "保存成功");
        return map;
    }
}
