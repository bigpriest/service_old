package makert.makert_demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import makert.makert_demo.dao.RoomTypeMapper;
import makert.makert_demo.entity.RoomType;
import makert.makert_demo.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/roomtype")
public class RoomTypeController {

    @Resource
    RoomTypeMapper roomTypeMapper;

    @GetMapping("/roomtypeinfo")
    public ModelAndView roomtypeinfo(String roomtypecode,String  roomtype,
                                     @RequestParam(name = "currPage",defaultValue = "1") int currPage, HttpSession session){
//        System.out.println(roomtypecode+"------**********----------"+roomtype);
        ModelAndView mv = new ModelAndView();
        //分页
        int totalCount = roomTypeMapper.findroomtypecount(roomtypecode,roomtype);
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<RoomType> roomTypeList = roomTypeMapper.roomtypeinfo(roomtypecode,roomtype,startRow,endRow);
        mv.addObject("roomTypeList",roomTypeList);
        List<RoomType> rtlists = roomTypeMapper.findroomtype();
        session.setAttribute("rtlists",rtlists);
        session.setAttribute("roomtypecode",roomtypecode);
        session.setAttribute("roomtype",roomtype);
        /*--------------把数据传到前端--------------*/
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数

        mv.setViewName("roomtypelist");
        return mv;
    };

    @GetMapping("/delroomtype")
    @ResponseBody
    public Map delroom(int id){
        Map<String,String> map = new HashMap<>();
        roomTypeMapper.delroomtype(id);
        map.put("success","success");
        return map;
    }
    //跳转添加页面
    @GetMapping("/addroomtype")
    public ModelAndView toaddroom(HttpSession session){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roomtypeadd");
        return mv;
    }

    @PostMapping("/addroomtypesave")
    public ModelAndView addroomtypesave(RoomType roomType){
        ModelAndView mv = new ModelAndView();
        roomTypeMapper.addroomtypesave(roomType);
        mv.setViewName("redirect:/roomtype/roomtypeinfo");
        return mv;
    }
    @GetMapping("/editroomtype/{id}")
    public ModelAndView findroomtypebyid(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView();
        RoomType roomType = roomTypeMapper.findroomtypebyid(id);
        mv.addObject("roomType",roomType);
        mv.setViewName("roomtypeedit");
        return mv;
    }

    @PostMapping("/editroomtypesave")
    public ModelAndView editroomtypesave(RoomType roomType){
        ModelAndView mv = new ModelAndView();
        roomTypeMapper.editroomtypesave(roomType);
        mv.setViewName("redirect:/roomtype/roomtypeinfo");
        return mv;
    }
}
