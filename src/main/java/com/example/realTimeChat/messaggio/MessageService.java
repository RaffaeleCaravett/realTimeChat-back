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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
@Autowired
    NotificationRepository notificationRepository;

    public Messaggio findById(long id){
        return messageRepository.findById(id).get();
    }
    public String findByIdAndDelete(long id){
        messageRepository.deleteById(id);
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

        if(chat.getTipoChat().equals(TipoChat.SINGOLA)){
            if(chat.getNotifications().isEmpty() ||
                    chat.getNotifications().size()==1&&!chat.getNotifications().get(0).getReceiver().get(0).equals(message.getReceiver().get(0))
            ){
                Notification notification = new Notification();
                notification.setTesto("Nuovo messaggio da " + message.getSender().getNome());
                notification.setStatoNotifica(StatoNotifica.NOT_SAW);
                notification.setChat(message.getChat());
                notification.setSender(message.getSender());
                notification.setReceiver(message.getReceiver());
                notifications.add(notification);
            notificationRepository.save(notification);
                chat.setNotifications(notifications);
            }else if(chat.getNotifications().size()==2){
                if(chat.getNotifications().get(0).getStatoNotifica().equals(StatoNotifica.SAW)&&chat.getNotifications().get(0).getReceiver().get(0).equals(message.getReceiver().get(0))){
                    chat.getNotifications().get(0).setStatoNotifica(StatoNotifica.NOT_SAW);
                }else if(chat.getNotifications().get(1).getStatoNotifica().equals(StatoNotifica.SAW)&&chat.getNotifications().get(1).getReceiver().get(0).equals(message.getReceiver().get(0))){
                    chat.getNotifications().get(0).setStatoNotifica(StatoNotifica.NOT_SAW);
                }
            }
        }


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
