package com.tavisca.workshop3.chat;

import javax.websocket.Session;

public class RepeatedMessageSender implements Runnable {
    ChatHelper chatHelper = null;
    Session session = null;
    String message = "";
    public RepeatedMessageSender(String message, Session session)
    {
        this.chatHelper = new ChatHelper();
        this.message = message;
        this.session = session;
    }
    @Override
    public void run()
    {
        while(true) {
            chatHelper.sendMessage(message,session.getId());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
