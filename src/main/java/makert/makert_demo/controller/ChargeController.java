package makert.makert_demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import makert.makert_demo.dao.ChargeMapper;
import makert.makert_demo.dao.OldMapper;
import makert.makert_demo.entity.Charge;
import makert.makert_demo.entity.Oldman;
import makert.makert_demo.util.Page;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Resource
    ChargeMapper chargeMapper;
    @Resource
    OldMapper oldMapper;

    @GetMapping("/payinfo")
    public ModelAndView payinfo(@RequestParam(defaultValue = "0") int oldid,
                                @RequestParam(defaultValue = "0")int ispay,
                                @RequestParam(name = "currPage",defaultValue = "1") int currPage,
                                HttpSession session){
        ModelAndView mv = new ModelAndView();
        //分页
        int totalCount = chargeMapper.findpaycount(oldid,ispay);
        Page page = new Page(currPage,5, totalCount);
        int startRow = (page.getCurrPage()-1)*page.getPageSize();
        int endRow = page.getPageSize();
        List<Charge> chargeList = chargeMapper.findpayinfo(oldid,ispay,startRow,endRow);
        List<Oldman> oldmanList = oldMapper.forgetoldname();
        mv.addObject("chargeList",chargeList);
        session.setAttribute("oldmanList",oldmanList);
        session.setAttribute("oldid",oldid);
        session.setAttribute("ispay",ispay);
        mv.addObject("currPage",page.getCurrPage());   //当前页
        mv.addObject("totalCount",page.getTotalCount());   //总记录数
        mv.addObject("totalPage",page.getTotalPage());   //总页数
        mv.setViewName("chargelist");
        return mv;
    }

    @GetMapping("/delcharge")
    @ResponseBody
    public Map delcharge(int id){
        Map<String,String> map = new HashMap<>();
        chargeMapper.delcharge(id);
        map.put("success","success");
        return map;
    }

    @GetMapping("/addcharge")
    public ModelAndView toaddcharge(){
        ModelAndView mv = new ModelAndView();
        List<Oldman> oldmanList = oldMapper.forgetoldname();
        mv.addObject("oldmanList",oldmanList);
        mv.setViewName("chargeadd");
        return mv;
    }

    @PostMapping("/addchargesave")
    public ModelAndView addchargesave(Charge charge){
        ModelAndView mv = new ModelAndView();
        chargeMapper.addchargesave(charge);
        mv.setViewName("redirect:/charge/payinfo");
        return mv;
    }

    @GetMapping("/editcharge/{id}")
    public ModelAndView findchargebyid(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView();
        Charge charge = chargeMapper.findchargebyid(id);
        Oldman oldman = oldMapper.findoldname(charge.getOldid());
        mv.addObject("charge",charge);
        mv.addObject("oldman",oldman);
        mv.setViewName("chargeedit");
        return mv;
    }

    @PostMapping("/editchargesave")
    public ModelAndView editchargesave(Charge charge){
        ModelAndView mv = new ModelAndView();
        chargeMapper.editchargesave(charge);
        mv.setViewName("redirect:/charge/payinfo");
        return mv;
    }
    //处理日期问题
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
