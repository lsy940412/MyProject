package com.itany.orderweb.conf;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint 
 * 注解是一个类层次的注解,它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * 连接请求可以带自定义的参数写法如下/{}里面写入参数key值
 */
@ServerEndpoint(value="/aa/{userName}")
@Component
public class MyWebSocket {

	private Session session;

     private String userName;
  
	/**
	 * concurrent包的线程安全Set，
	 * 用来存放每个客户端对应的MyWebSocket对象。
	 * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，
	 * 其中Key可以为用户标识
	 */
    public static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    
	/**
	 * 收到客户端消息后调用的方法
	 * (此处如message是json形字符串要用相应工具类解析)
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 * @throws Exception 
	 */
	@OnMessage
    public void onMessage(String message, Session session) throws Exception{

    }
	
	/**
	 * 连接建立成功调用的方法
	 * (若有自定义参数可以用@PathParam注解绑定)
	 * @param session 可选的参数 session为与某个客户端的连接会话,需要通过它来给客户端发送数据
	 */
	@OnOpen
    public void onOpen (Session session,@PathParam("userName") String userName) {
		System.out.println("当前"+userName+"连接上了");
		this.session = session;
		this.userName=userName;
		webSocketSet.add(this); // 加入set中
		   
    }

	 /**
	  * 连接关闭调用的方法
	  * 关闭从set中移除对象
	  * @param session
	  */
    @OnClose
    public void onClose (Session session) {
    	webSocketSet.remove(this);
    }
    
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        
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
}
