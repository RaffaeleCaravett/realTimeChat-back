package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.chat.ChatRepository;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public long saveMessage(MessageDTO messageDTO){
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
        messageRepository.save(message);
        return message.getId();
    }

    public List<Messaggio> findByChatId(long id){
        return messageRepository.findAllByChat_Id(id);
    }
}
