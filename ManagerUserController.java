package com.itany.controller;


import com.github.pagehelper.PageInfo;
import com.itany.pojo.ManagerUser;
import com.itany.service.ManagerUserService;
import com.itany.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/managerUser")
public class ManagerUserController {
    @Autowired
    private ManagerUserService managerUserService;

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String,Object> findAll(@RequestParam(name = "username",required = false) String username,
                                      @RequestParam(defaultValue="1")Integer page,
                                      @RequestParam(defaultValue="10")Integer rows){
        Map<String,Object> map = new HashMap<>();
        PageInfo<ManagerUser> pageInfo = managerUserService.findAllByName(username,page,rows);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;

    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<TreeVo> findAllRoleName(){
        return managerUserService.findAllRoleName();
    }

    @RequestMapping("/addManagerUser")
    @ResponseBody
    public Map<String,Object> addManagerUser(@RequestParam(name="username") String username,
                                             @RequestParam(name = "password") String password,
                                             @RequestParam(name = "roleIds") String roleIds){
        Map<String,Object> map = new HashMap<>();
        try {
            managerUserService.addManagerUser(username,password,roleIds);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
        }
        return map;

    }



}
