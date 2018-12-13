package com.example.userweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.userweb.webScoket.MyWebScoket;
import com.github.pagehelper.PageInfo;
import com.itany.pojo.User;
import com.itany.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/userserver")
public class UserController {

    @Reference
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String, Object> addUser(User user){

       Map<String,Object> map = new HashMap<>();
        String phone = user.getPhone();
        String initcode = redisTemplate.opsForValue().get("INITCODE:" + phone);
        String vercode = user.getVerCode();

        if(initcode == null){
            System.out.println(initcode+"--------initcode == null--------");
            map.put("success",false);
            map.put("msg","手机验证码已过期");
            return map;
        }
        if(!(vercode.equals(initcode))) {
            System.out.println(initcode+vercode+"--------initcode != user.getVerCode()--------");
            map.put("success",false);
            map.put("msg","手机验证码输入错误");
            return map;
        }
        try {
            userService.addUser(user);
            map.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/sendCode")
    @ResponseBody
    public Map<String,Object> sendCode(String phone){
        Map<String,Object> map = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        Random random = new Random();
        String initCode="";
        for (int i=0;i<6;i++)
        {
            initCode += random.nextInt(10);
        }
        params.put("mobile",phone);
        params.put("tpl_id","118153");
        params.put("tpl_value","%23code%23%3d"+initCode);
        params.put("key","8b43eda639d1ac3e63e160bd7b797f1a");
        params.put("dtype","json");
        try {
           // HttpClientUtil.doPost("http://v.juhe.cn/sms/send",params);
            redisTemplate.opsForValue().set("INITCODE:"+phone,initCode,600,TimeUnit.SECONDS);
            System.out.println("-----initCode-----"+initCode);
            map.put("success",true);
        }catch (Exception e ){
            map.put("success",false);
        }
       return map;
    }

    @RequestMapping("/findUsers")
    public ModelAndView findUsers(@RequestParam(defaultValue = "1")Integer pageNo,
                                  @RequestParam(defaultValue = "10")Integer pageSize){
        PageInfo<User> pageInfo =  userService.findUsers(pageNo,pageSize);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageInfo",pageInfo);
        return new ModelAndView("userlist",map);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(User user, HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();


        try {
         User u = userService.login(user);
            for (MyWebScoket myWebScoket : MyWebScoket.webScoket){
                String userName = myWebScoket.getUserName();
                if (user.getUsername().equals(userName)){
                   // myWebScoket.onMessage("该帐号在其他地方登录",myWebScoket.getSession());

                    Map<String,Object> map1= new HashMap<String,Object>();
                    map1.put("flag","1");
                    map1.put("msg", "该用户在其他地方登录");
                    myWebScoket.getSession().getBasicRemote().sendText(JSON.toJSONString(map1));
                    MyWebScoket.webScoket.remove(myWebScoket);
                }
            }
            u.setPassword(null);


            String token = user.getUsername();
            String value = redisTemplate.opsForValue().get("USER_TOKEN:"+token);
            if (value != null){
                redisTemplate.delete("USER_TOKEN:"+token);
            }
            redisTemplate.opsForValue().set("USER_TOKEN::"+token, JSON.toJSONString(u),1800,TimeUnit.SECONDS);
            Cookie cookie = new Cookie("BOOK_TOKEN",token);
            cookie.setPath("/");
            response.addCookie(cookie);
            map.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","用户名或密码错误");

        }
        return map;
    }

    }
