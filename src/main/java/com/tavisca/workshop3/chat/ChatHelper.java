package com.tavisca.workshop3.chat;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHelper {
    public static HashMap<String, Session> sessionMap = new HashMap<>();

    public void sendMessage(String message, String senderID){
        Pattern pattern = Pattern.compile("@(.*):(.*)");
        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            String reciverId = matcher.group(1);
            String messageToSend = matcher.group(2);

            Session receiverSession = sessionMap.get(reciverId);
            try {
                receiverSession.getBasicRemote().sendText("From: "+ senderID+ messageToSend);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
