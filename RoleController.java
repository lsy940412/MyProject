package com.itany.controller;

import com.github.pagehelper.PageInfo;
import com.itany.pojo.Role;
import com.itany.vo.TreeVo;
import com.itany.service.RoleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleSevice roleSevice;


    @RequestMapping("/findByName")
    @ResponseBody
    public Map<String,Object> findByName(@RequestParam(name = "name",required = false) String name,
                                         @RequestParam(defaultValue="1")Integer page,
                                         @RequestParam(defaultValue="10")Integer rows){
        Map<String,Object> map = new HashMap<>();
        PageInfo<Role> pageInfo = roleSevice.findByName(name, page, rows);
        System.out.println(pageInfo.getList().get(0).getName()+"---------"+pageInfo.getList().get(0).getList());
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;

    }
    @RequestMapping("/findPermission/{id}")
    @ResponseBody
    public List<TreeVo> findPermission(@PathVariable Integer id){
         return roleSevice.findById(id);
    }


    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(@RequestParam(name="id") Integer id ,
                                      @RequestParam(name = "name") String name ,
                                      @RequestParam(name = "list") String list){
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(id+name+list+"----controller------");
            roleSevice.modifyById(id,list,name);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
        }

        return map;
    }

    @RequestMapping("/remove/{id}")
    @ResponseBody
    public Map<String,Object> remove(@PathVariable Integer id ){
        Map<String,Object> map = new HashMap<>();
        try{
            roleSevice.removeRoleById(id);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);

        }
        return map;
    }
}
