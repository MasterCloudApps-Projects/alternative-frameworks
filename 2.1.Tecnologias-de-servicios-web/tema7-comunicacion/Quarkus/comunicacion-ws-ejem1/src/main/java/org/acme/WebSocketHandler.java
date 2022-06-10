package org.acme;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/notifications")         
@ApplicationScoped
public class WebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    Map<String, Session> sessions = new ConcurrentHashMap<>(); 

    @OnOpen
    public void onOpen(Session session) {
        logger.info("User connected "+session.getId());
		
		session.getAsyncRemote().sendObject("Hello user");
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("User disconnected "+session.getId());

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Error:" + throwable.getMessage());
		
		session.getAsyncRemote().sendObject("Echo: "+ throwable.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("Message received:" + message);
		
		session.getAsyncRemote().sendObject("Echo: "+ message);
    }


    
}
