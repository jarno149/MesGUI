/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController
{
    @Autowired
    private SimpMessagingTemplate wsTemplate;
    
    @MessageMapping("/socket")
    @SendTo("/livedata")
    public String incomingMessage(String data) throws Exception
    {
        return data;
    }
    
    public void sendMessage(Object data)
    {
        wsTemplate.convertAndSend("/livedata", data);
    }
}
