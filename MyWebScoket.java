package com.example.userweb.webScoket;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/webSocket/{userName}")
@Component
public class MyWebScoket {

    private Session session;

    private String userName;

    public static CopyOnWriteArrayList<MyWebScoket> webScoket = new CopyOnWriteArrayList<>();

    @OnOpen
    public void  onOpen(@PathParam("userName") String userName,Session session){
        this.session = session;
        this.userName = userName;
        webScoket.add(this);

    }

    @OnClose
    public void onClose(Session session){
        System.out.println("关闭"+this.getUserName());
        webScoket.remove(this);
    }

    public Session getSession() {
        return session;
    }


    public void setSession(Session session) {
        this.session = session;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static CopyOnWriteArrayList<MyWebScoket> getWebScoket() {
        return webScoket;
    }

    public static void setWebScoket(CopyOnWriteArrayList<MyWebScoket> webScoket) {
        MyWebScoket.webScoket = webScoket;
    }
}
