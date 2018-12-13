package com.itany.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.Exception.IdIsNotExistException;
import com.itany.Exception.NameIsNotException;
import com.itany.pojo.Permission;
import com.itany.service.PermissionService;
import com.itany.vo.PermissionVo;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.modelmbean.RequiredModelMBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

/*    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String,Object> findAll(@RequestParam(defaultValue="1")Integer page,
                                          @RequestParam(defaultValue="10")Integer rows){
        Map<String,Object> map  = new HashMap<>();
        PageInfo<Permission> permissions = permissionService.findAll(page, rows);
        map.put("total",permissions.getTotal());
        map.put("rows",permissions.getList());
        return map;
    }*/
    @RequestMapping("/findByName")
    @ResponseBody
    public Map<String,Object> findByName(@RequestParam(defaultValue="1")Integer page,
                                         @RequestParam(defaultValue="10")Integer rows,
                                         @RequestParam(name = "name",required = false) String name ,
                                         @RequestParam(name = "parentName",required = false) String ParentName) {

        Map<String,Object> map  = new HashMap<>();
        PageInfo<Permission> permissions = permissionService.findByName(page,rows,name,ParentName);
            map.put("total",permissions.getTotal());
            map.put("rows",permissions.getList());
        System.out.println(name+"-----"+ParentName);

        return map;

    }

    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Map<String,Object> findById(@PathVariable(name = "id") String id){
        Map<String,Object> map = new HashMap<>();
        try {
            PermissionVo permission = permissionService.findById(Integer.parseInt(id));
            map.put("success",true);
            map.put("permission",permission);
        } catch (IdIsNotExistException e) {
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/findParent")
    @ResponseBody
    public List<Map<String,Object>> findParent(){
        List<Map<String,Object>> list = new ArrayList<>();
        List<Permission> parents = permissionService.findParent();
        for(int i = 0;i<parents.size();i++ ){
            Map<String,Object> vo = new HashMap<>();
            String name = parents.get(i).getName();
            vo.put("id",parents.get(i).getId());
            vo.put("name",name);
            list.add(vo);
        }
        return list;
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String,Object> Modify(PermissionVo vo){
        System.out.println(vo.getParentId()+"----modify-------"+vo.getName());
        Map<String,Object> map = new HashMap<>();
        try {
            permissionService.modifyById(vo);
            map.put("success",true);
            map.put("permission",vo);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }


}
