package com.example.realTimeChat.wscontroller;

import com.example.realTimeChat.messaggio.MessageService;
import com.example.realTimeChat.messaggio.Messaggio;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WSChatController {
    @Autowired
    MessageService messageService;
@Autowired
    UserService userService;
@MessageMapping("/chat.sendMessage")
    @SendTo("topic/public")
    public Messaggio sendMessage(@Payload MessageDTO messageDTO){
    return messageService.saveMessage(messageDTO);
}
    @MessageMapping("/chat.addUser")
    @SendTo("topic/public")
    public Messaggio addUser(@Payload MessageDTO messageDTO, SimpMessageHeaderAccessor headerAccessor){
    User user = userService.findById(messageDTO.sender_id());
    headerAccessor.getSessionAttributes().put("username", user.getNome());
        return messageService.saveMessage(messageDTO);
    }
}
