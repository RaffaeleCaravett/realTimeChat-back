package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.chat.ChatRepository;
import com.example.realTimeChat.enums.MessageState;
import com.example.realTimeChat.enums.StatoNotifica;
import com.example.realTimeChat.enums.TipoChat;
import com.example.realTimeChat.notification.Notification;
import com.example.realTimeChat.notification.NotificationRepository;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public Messaggio findById(long id){
        return messageRepository.findById(id).get();
    }
    public String findByIdAndDelete(long id){
        List<User> users = messageRepository.findById(id).get().getReceiver();
        messageRepository.deleteById(id);
        userRepository.saveAll(users);
        return "deleted";
    }

    public Messaggio saveMessage(MessageDTO messageDTO)  {
        Messaggio message = new Messaggio();
        Chat chat = chatRepository.findById(messageDTO.chat_id()).get();
        message.setSender(userRepository.findById(messageDTO.sender_id()).get());
        List<User> receivers = new ArrayList<>();
        for ( Long l : messageDTO.receiver_id()){
            receivers.add(userRepository.findById(l).get());
        }
        message.setReceiver(receivers);
        message.setMessage(messageDTO.messaggio());
        message.setChat(chat);
        message.setDate_at(LocalDate.now());
        message.setMessageState(MessageState.SENT);
        List<Notification>notifications=new ArrayList<>();

            for(User u : message.getReceiver()){
                Optional<Notification> notification = notificationRepository.findByChat_IdAndReceiver_IdAndSender_Id(message.getChat().getId(),u.getId(),message.getSender().getId());
                if(notification.isPresent()){
                    Notification notification1 = notification.get();
                    notification1.setStatoNotifica(StatoNotifica.NOT_SAW);
                    notificationRepository.save(notification1);
                }else {
                    Notification notification1 = new Notification();
                    notification1.setTesto("Nuovo messaggio da " + message.getSender().getNome());
                    notification1.setStatoNotifica(StatoNotifica.NOT_SAW);
                    notification1.setChat(message.getChat());
                    notification1.setSender(message.getSender());
                    notification1.setReceiver(u);
                    notifications.add(notification1);
                    notificationRepository.save(notification1);
                }
            }
            chat.setNotifications(notifications);
            chatRepository.save(chat);
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
    public List<Messaggio> findBySenderId(long id){
        return messageRepository.findAllBySender_Id(id);
    }
}
