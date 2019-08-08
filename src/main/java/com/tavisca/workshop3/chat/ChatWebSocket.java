package com.tavisca.workshop3.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")//can give any name instead of endpoint as it's the access point
public class ChatWebSocket {
    private ChatHelper chatHelper;
    public ChatWebSocket(){
        this.chatHelper = new ChatHelper();
    }

    /*@OnOpen
    public void onOpen(Session session){
        System.out.println("Open connection: "+session.getId());//assigning an ID to web socket by api only
        ChatHelper.sessionMap.put(session.getId(), session);
    }*/
    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println("Open connection: "+session.getId());
        ChatHelper.sessionMap.put(session.getId(),session);
        String message = "@"+session.getId()+":"+"Hello "+session.getId();
        Thread repeatedSender = new Thread(new RepeatedMessageSender(message,session));
        repeatedSender.start();
    }


    @OnClose
    public void onClose(Session session){
        System.out.println("Closing connection: "+session.getId());//closing connection for that ID
        ChatHelper.sessionMap.remove(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Received message from: "+session.getId() +" Message is "+ message);

        try {
            session.getBasicRemote().sendText(message.toUpperCase());//session contains basic information of the client
                                                                    // and the getBasicRemote gives an object of client
                                                                    //to help send message to him/it
            chatHelper.sendMessage(message, session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
