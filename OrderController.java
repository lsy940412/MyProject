package com.itany.orderweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itany.orderweb.conf.MyWebSocket;
import com.itany.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@RequestMapping("/orderserver")
public class OrderController {
    @Reference
    private OrderService orderService;

    @RequestMapping("/createImage")
    public void createImage(HttpServletResponse response) throws Exception {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");

        String qrCode = orderService.getOrderCode();
        Map<EncodeHintType,Object> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bit = new MultiFormatWriter().encode(qrCode, BarcodeFormat.QR_CODE,20,20,map);
        MatrixToImageWriter.writeToStream(bit,"jpeg",response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
    @RequestMapping("/success/{username}")
    @ResponseBody
    public String paySuccess(@PathVariable("username")String username) throws Exception{
        System.out.println("-------paySuccess--------");
        CopyOnWriteArraySet<MyWebSocket> webSockets = MyWebSocket.webSocketSet;
        for(MyWebSocket webSocket : webSockets){
            if (webSocket.getUserName().equals(username)){
                webSocket.getSession().getBasicRemote().sendText("lsy");
            }
        }

        return "success";
    }



}
