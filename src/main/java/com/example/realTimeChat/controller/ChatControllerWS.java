package com.example.realTimeChat.controller;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.enums.MessageType;
import com.example.realTimeChat.messaggio.MessageService;
import com.example.realTimeChat.messaggio.Messaggio;
import com.example.realTimeChat.model.ChatMessage;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatControllerWS {
@Autowired
UserRepository userRepository;
@Autowired
MessageService messageService;
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Messaggio sendMessage(@Payload MessageDTO messageDTO) {
        return messageService.saveMessage(messageDTO);
        //ChatMessage chatMessage= new ChatMessage();
        //chatMessage.setSender(sender.getNome());
        //chatMessage.setContent(messageDTO.messaggio());
        //chatMessage.setType(MessageType.CHAT);
      //  return  chatMessage;
    }
}
