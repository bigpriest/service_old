package makert.makert_demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import makert.makert_demo.dao.ActivityMapper;
import makert.makert_demo.entity.Activity;
import makert.makert_demo.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/active")
public class ActivityController {
    @Resource
    ActivityMapper activityMapper;

    //查询所有安排（包含条件查询）
    @RequestMapping("/oldactive")
    public ModelAndView findactiveinfo(String dailyactive,@RequestParam(name = "currPage",defaultValue = "1") int currPage, HttpSession session){
        ModelAndView mv = new ModelAndView("输出输出："+dailyactive);
        //分页
        System.out.println();
        int totalCount = activityMapper.findactivecount(dailyactive);
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<Activity> activityList = activityMapper.activeinfo(dailyactive,startRow,endRow);
        mv.addObject("activityList",activityList);
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数
        session.setAttribute("activekey",dailyactive);
        mv.setViewName("activelist");
        return mv;
    }
    //跳转添加页面
    @GetMapping("/addactive")
    public ModelAndView toactiveadd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("activeadd");
        return mv;
    }
    //保存添加信息
    @PostMapping("/addactive")
    public ModelAndView activeaddsave(Activity activity){
        ModelAndView mv =new ModelAndView();
        activityMapper.addactive(activity);
        mv.setViewName("redirect:/active/oldactive");
        return mv;
    }

    //删除
    @PostMapping("/delactive")
    @ResponseBody
    public Map<String,String> delactive(int[] check){
        Map<String,String> map = new HashMap<>();
        try {
            for (int i = 0; i < check.length; i++) {
                activityMapper.delactive(check[i]);
            }
            map.put("success", "success");
        }catch(Exception e){}
        return map;
    }

    //根据id回显信息
    @GetMapping("/editactive/{id}")
    public ModelAndView findbyactiveid(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView();
        Activity activity = activityMapper.findbyactiveid(id);
        mv.addObject("activity",activity);
        mv.setViewName("activeedit");
        return mv;
    }

    @PostMapping("/editactivesave")
    public ModelAndView editactivesave(Activity activity){
        ModelAndView mv = new ModelAndView();
        activityMapper.editactive(activity);
        mv.setViewName("redirect:/active/oldactive");
        return mv;
    }
}
