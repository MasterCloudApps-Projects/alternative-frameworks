package org.acme;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint("/chat")
@ApplicationScoped
public class ChatManager {

    private static final Map<String, Map<String, User>> rooms = new ConcurrentHashMap<>();
    private static final String DUPLICATE_MSG = "{\"type\":\"system\",\"message\":\"Ya existe un usuario con ese nombre\"}";

    private User user;

    @OnOpen
    public void open(Session session) {
        this.user = new User(session);
    }

    @OnMessage
    public void handleMessage(Session session, String message) throws IOException {

        if(this.user.isValid()){
            // Broadcast message
            System.out.println(message + "from user: "+user.getName());
            rooms.get(user.getChat()).values().forEach( _user ->
                _user.send(message)
            );
        }else{
            newUser(message);
        }

    }

    @OnClose
    public void close(Session session){

        if(this.user.isValid()){
            Map<String, User> chat = rooms.get(this.user.getChat());
            chat.remove(this.user.getName());

            if(chat.isEmpty()){
                // Remove chat room if it's empty
                rooms.remove(this.user.getChat());
            }
        }

        System.out.println("Sesión cerrada");

    }

    @OnError
    public void onError(Session session, Throwable thr) {

        System.err.println("Cliente "+session.getId()+" desconectado: "+thr);

    }

    private void newUser(String message){

        JSONObject jsonMessage = new JSONObject(message);

        String chat = jsonMessage.optString("chat");
        String name = jsonMessage.optString("name");

        if( this.userExist(name) ){
            this.user.send(DUPLICATE_MSG);
        }else{
            if(!rooms.containsKey(chat)){
                // Chat doesn't exist
                rooms.put(chat, new ConcurrentHashMap<>());
            }
            this.user.setUp(name, chat);
            rooms.get(chat).put(name, this.user);
        }

    }

    private boolean userExist(String user_name){
        return rooms.values().stream().anyMatch((chat) ->
            chat.containsKey(user_name)
        );
    }

}
