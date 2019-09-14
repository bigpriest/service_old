package makert.makert_demo.controller;

import makert.makert_demo.dao.RoomMapper;
import makert.makert_demo.dao.RoomTypeMapper;
import makert.makert_demo.entity.Room;
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
@RequestMapping("/room")
public class RoomController {

    @Resource
    RoomMapper roomMapper;
    @Resource
    RoomTypeMapper roomTypeMapper;

    @GetMapping("/roominfo")
    public ModelAndView roominfo(String room_code,@RequestParam(name = "roomtype_id",defaultValue = "0") int roomtype_id,
                                 @RequestParam(name = "currPage",defaultValue = "1") int currPage, HttpSession session){
        ModelAndView mv = new ModelAndView();
        //分页
        int totalCount = roomMapper.findroomcount(room_code, roomtype_id);
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<Room> roomList = roomMapper.roominfo(room_code,roomtype_id,startRow,endRow);
        List<RoomType> roomTypeList = roomTypeMapper.findroomtype();
        mv.addObject("roomList",roomList);
        session.setAttribute("roomTypeList",roomTypeList);
        session.setAttribute("room_code",room_code);
        session.setAttribute("roomtype_id",roomtype_id);
        /*--------------把数据传到前端--------------*/
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数
        mv.setViewName("roomlist");
        return mv;
    };

    @GetMapping("/delroom")
    @ResponseBody
    public Map delroom(int id){
        Map<String,String> map = new HashMap<>();
        roomMapper.delroom(id);
        map.put("success","success");
        return map;
    }
    //跳转添加页面
    @GetMapping("/addroom")
    public ModelAndView toaddroom(HttpSession session){
        ModelAndView mv = new ModelAndView();
        List<RoomType> roomTypeList = roomTypeMapper.findroomtype();
        session.setAttribute("roomTypeList",roomTypeList);
        mv.setViewName("roomadd");
        return mv;
    }
    //添加保存
    @PostMapping("/addroomsave")
    public ModelAndView addroomsave(Room room){
        ModelAndView mv = new ModelAndView();
        roomMapper.addroom(room);
        mv.setViewName("redirect:/room/roominfo");
        return mv;
    }

    @GetMapping("/editroom/{id}")
    public ModelAndView findroombyid(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView();
        Room room = roomMapper.findbyid(id);
        List<RoomType> roomTypeList = roomTypeMapper.findroomtype();
        mv.addObject("roomTypeList",roomTypeList);
        mv.addObject("room",room);
        mv.setViewName("roomedit");
        return mv;
    }

    @PostMapping("/editroomsave")
    public ModelAndView editroomsave(Room room){
        ModelAndView mv = new ModelAndView();
        roomMapper.editroom(room);
        mv.setViewName("redirect:/room/roominfo");
        return mv;
    }
}
