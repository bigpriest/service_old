package makert.makert_demo.controller;

import makert.makert_demo.dao.HealthMapper;
import makert.makert_demo.dao.OldMapper;
import makert.makert_demo.entity.Health;
import makert.makert_demo.entity.Oldman;
import makert.makert_demo.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/health")
public class OldHealthController {

    @Resource
    HealthMapper healthMapper;
    @Resource
    OldMapper oldMapper;

    @GetMapping("/oldhealth")
    public ModelAndView showoldhealthinfo(@RequestParam(name = "oldid",defaultValue = "0") int oldid,
                                          @RequestParam(name = "currPage",defaultValue = "1") int currPage,
                                          HttpSession session){
        ModelAndView mv = new ModelAndView();
        int totalCount = healthMapper.findhealthcount(oldid);
        System.out.println(totalCount+"!!!!!!!!");
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<Health> healthList = healthMapper.findoldhealth(oldid,startRow,endRow);
        List<Oldman> oldmenlist = oldMapper.forgetoldname();
        mv.addObject("healthList",healthList);
        session.setAttribute("oldlist",oldmenlist);
        session.setAttribute("oldkey",oldid);
        /*--------------把数据传到前端--------------*/
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数
        mv.setViewName("healthlist");
        return mv;
    }
    //跳转添加页面
    @GetMapping("/addhealth")
    public ModelAndView tohealthadd(HttpSession session){
        ModelAndView mv = new ModelAndView();
        List<Oldman> oldmanList = oldMapper.forgetoldname();
        session.setAttribute("oldList",oldmanList);
        mv.setViewName("healthadd");
        return mv;
    }
    //添加保存，在模态框显示需要修改
    @PostMapping("/addhealth")
    public ModelAndView healthsave(Health health){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        try {
            Date time = df.parse(df.format(new Date()));
            health.setTime(time);
            healthMapper.addhealth(health);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/health/oldhealth");
        return mv;
    }

    //删除
    @PostMapping("/delhealth")
    @ResponseBody
    public Map<String,String> delhealth(int[] data){
        Map<String,String> map = new HashMap<>();
        try {
            for (int i = 0; i < data.length; i++) {
                healthMapper.delhealth(data[i]);
            }
            map.put("success", "success");
        }catch (Exception e){}
        return map;
    }

    //根据id回显信息
    @GetMapping("/edithealth/{id}")
    public ModelAndView findbyid(@PathVariable("id") int id){
        Health health = healthMapper.findbyid(id);
        Oldman oldman = oldMapper.findoldname(health.getOldid());
        ModelAndView mv = new ModelAndView();
        mv.addObject("health",health);
        mv.addObject("oldman",oldman);
        mv.setViewName("healthedit");
        return mv;
    }

    //保存编辑信息，由于在模态框显示需要修改
    @PostMapping("/edithealthsave")
    public ModelAndView edithealthsave(Health health){
        ModelAndView mv = new ModelAndView();
        healthMapper.edithealth(health);
        mv.setViewName("redirect:/health/oldhealth");
        return mv;
    }
}
