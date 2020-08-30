package com.neusoft.hit.backgroundmanagement.component.controller;

import com.neusoft.hit.backgroundmanagement.component.service.BusinessService;
import com.neusoft.hit.backgroundmanagement.entities.Business;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    private BusinessService businessService;

    @RequestMapping("/selectList")
    @ResponseBody
    public List<Business> selectList(){
        try {
            return businessService.selectList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/selectId")
    @ResponseBody
    public Business selectId(@RequestBody Map map){
        try {
            String id = (String) map.get("catagoryCode");
            return businessService.selectId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
