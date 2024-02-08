package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.chat.ChatRepository;
import com.example.realTimeChat.controller.ChatControllerWS;
import com.example.realTimeChat.enums.MessageState;
import com.example.realTimeChat.enums.MessageType;
import com.example.realTimeChat.model.ChatMessage;
import com.example.realTimeChat.payloads.entities.ChatDTO;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;


    public Messaggio findById(long id){
        return messageRepository.findById(id).get();
    }
    public String findByIdAndDelete(long id){
        messageRepository.deleteById(id);
        return "deleted";
    }

    public Messaggio saveMessage(MessageDTO messageDTO){
        Messaggio message = new Messaggio();
        message.setSender(userRepository.findById(messageDTO.sender_id()).get());
        List<User> receivers = new ArrayList<>();
        for ( Long l : messageDTO.receiver_id()){
            receivers.add(userRepository.findById(l).get());
        }
        message.setReceiver(receivers);
        message.setMessage(messageDTO.messaggio());
        message.setChat(chatRepository.findById(messageDTO.chat_id()).get());
        message.setDate_at(LocalDate.now());
        message.setMessageState(MessageState.SENT);
        return messageRepository.save(message);

    }

    public List<Messaggio> findByChatId(long id){
        return messageRepository.findAllByChat_Id(id);
    }
    public long findByIdAndUpdate(long id, MessageDTO messageDTO){
        Messaggio messaggio = messageRepository.findById(id).get();

        messaggio.setMessage(messageDTO.messaggio());
        messaggio.setMessageState(MessageState.valueOf(messageDTO.stato()));
        messageRepository.save(messaggio);

        return messaggio.getId();
    }
}
